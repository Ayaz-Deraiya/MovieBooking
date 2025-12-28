package com.moviebooking.showtimeservice.Schedular;

import com.moviebooking.showtimeservice.entity.Seat;
import com.moviebooking.showtimeservice.entity.SeatState;
import com.moviebooking.showtimeservice.entity.Showtime;
import com.moviebooking.showtimeservice.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class cleanupExpiredSeatLocks{
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredSeatLocks() {
        LocalDateTime now = LocalDateTime.now();
        for (Showtime showtime : showtimeRepository.findAll()) {
            for (Seat seat : showtime.getSeats()) {
                if (seat.getState() == SeatState.TEMP_LOCKED &&
                        seat.getExpiresAt().isBefore(now)) {

                    seat.setState(SeatState.AVAILABLE);
                    seat.setBookingId(null);
                    seat.setLockedAt(null);
                    seat.setExpiresAt(null);
                }
            }
            showtimeRepository.save(showtime);
        }
    }

}
