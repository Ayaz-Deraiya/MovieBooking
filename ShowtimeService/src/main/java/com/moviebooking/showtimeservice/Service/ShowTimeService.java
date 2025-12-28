package com.moviebooking.showtimeservice.Service;

import com.moviebooking.showtimeservice.entity.Seat;
import com.moviebooking.showtimeservice.entity.SeatState;
import com.moviebooking.showtimeservice.entity.Showtime;
import com.moviebooking.showtimeservice.repository.ShowtimeRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ShowTimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    // 1️⃣ ADD SHOWTIME
    public ResponseEntity<?> addShowTime(Showtime showtime) {
        showtimeRepository.save(showtime);
        return ResponseEntity.status(HttpStatus.CREATED).body(showtime);
    }

    // 2️⃣ GET ALL SHOWTIMES
    public ResponseEntity<?> getShowTime() {
        return ResponseEntity.ok(showtimeRepository.findAll());
    }

    // 3️⃣ TEMP LOCK SEATS (NO bookingId here)
    public ResponseEntity<?> lockSeats(
            ObjectId showTimeId,
            List<Integer> seatNumbers
    ) {
        Showtime showtime = showtimeRepository.findById(showTimeId)
                .orElse(null);

        if (showtime == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Showtime not found");
        }

        if (showtime.getSeats() == null) {
            showtime.setSeatsOccupied(new ArrayList<>());
        }

        List<Seat> occupiedSeats = showtime.getSeats();

        // 1️⃣ Validate availability
        for (Integer seatNo : seatNumbers) {
            if (findSeat(showtime, seatNo) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Seat " + seatNo + " not available");
            }
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusMinutes(5);

        // 2️⃣ Lock seats
        for (Integer seatNo : seatNumbers) {
            Seat seat = new Seat();
            seat.setSeatNumber(seatNo);
            seat.setState(SeatState.TEMP_LOCKED);
            seat.setLockedAt(now);
            seat.setExpiresAt(expiry);
            seat.setBookingId(null);

            occupiedSeats.add(seat);
        }

        showtimeRepository.save(showtime);
        return ResponseEntity.ok("Seats temporarily locked");
    }

    // 4️⃣ CONFIRM SEATS (payment success)
    public ResponseEntity<?> confirmSeats(
            ObjectId showTimeId,
            ObjectId bookingId
    ) {
        Showtime showtime = showtimeRepository.findById(showTimeId)
                .orElse(null);

        if (showtime == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Showtime not found");
        }

        for (Seat seat : showtime.getSeats()) {
            if (seat.getState() == SeatState.TEMP_LOCKED) {
                seat.setState(SeatState.BOOKED);
                seat.setBookingId(bookingId);
                seat.setExpiresAt(null);
            }
        }

        showtimeRepository.save(showtime);
        return ResponseEntity.ok("Seats permanently booked");
    }

    // 5️⃣ RELEASE SEATS (payment failed / expired)
    public ResponseEntity<?> releaseSeats(ObjectId showTimeId) {

        Showtime showtime = showtimeRepository.findById(showTimeId)
                .orElse(null);

        if (showtime == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Showtime not found");
        }

        // REMOVE temp locked seats
        showtime.getSeats().removeIf(seat ->
                seat.getState() == SeatState.TEMP_LOCKED &&
                        seat.getExpiresAt().isBefore(LocalDateTime.now())
        );

        showtimeRepository.save(showtime);
        return ResponseEntity.ok("Expired seats released");
    }

    // 6️⃣ FIND SHOWTIMES BY MOVIE + THEATRE + DATE
    public List<Showtime> getShowtimesByMovieTheatreAndDate(
            ObjectId movieId,
            ObjectId theatreId,
            LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return showtimeRepository
                .findByMovieIdAndTheatreIdAndStartTimeBetween(
                        movieId, theatreId, start, end
                );
    }

    // 🔎 Utility: seat exists = NOT AVAILABLE
    private Seat findSeat(Showtime showtime, Integer seatNo) {
        if (showtime.getSeats() == null) return null;

        return showtime.getSeats()
                .stream()
                .filter(s -> s.getSeatNumber().equals(seatNo))
                .findFirst()
                .orElse(null);
    }
}
