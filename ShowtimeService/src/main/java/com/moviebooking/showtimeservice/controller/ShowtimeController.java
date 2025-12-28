package com.moviebooking.showtimeservice.controller;

import com.moviebooking.showtimeservice.DTO.ShowtimeSearchDTO;
import com.moviebooking.showtimeservice.DTO.SeatLockRequest;
import com.moviebooking.showtimeservice.DTO.SeatConfirmRequest;
import com.moviebooking.showtimeservice.Service.ShowTimeService;
import com.moviebooking.showtimeservice.entity.Showtime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtime")
@CrossOrigin("*")
public class ShowtimeController {

    @Autowired
    private ShowTimeService service;

    // 1️⃣ ADD SHOWTIME
    @PostMapping("/addShowTime")
    public ResponseEntity<?> addShowtime(@RequestBody Showtime showtime) {
        return service.addShowTime(showtime);
    }

    // 2️⃣ GET ALL SHOWTIMES
    @GetMapping("/getAllShowTime")
    public ResponseEntity<?> getAllShowTimes() {
        return service.getShowTime();
    }

    // 3️⃣ TEMP LOCK SEATS (NO bookingId)
    @PostMapping("/lock-seats")
    public ResponseEntity<?> lockSeats(@RequestBody SeatLockRequest request) {

        if (request.getShowTimeId() == null || request.getShowTimeId().isBlank()) {
            return ResponseEntity.badRequest().body("showTimeId is required");
        }

        if (!ObjectId.isValid(request.getShowTimeId())) {
            return ResponseEntity.badRequest().body("Invalid showTimeId");
        }

        return service.lockSeats(
                new ObjectId(request.getShowTimeId()),
                request.getSeats()
        );
    }

    // 4️⃣ CONFIRM SEATS (payment success)
    @PostMapping("/confirm-seats")
    public ResponseEntity<?> confirmSeats(@RequestBody SeatConfirmRequest request) {

        if (request.getShowTimeId() == null || request.getBookingId() == null) {
            return ResponseEntity.badRequest()
                    .body("showTimeId and bookingId are required");
        }

        if (!ObjectId.isValid(request.getShowTimeId())
                || !ObjectId.isValid(request.getBookingId())) {
            return ResponseEntity.badRequest()
                    .body("Invalid ObjectId format");
        }

        return service.confirmSeats(
                new ObjectId(request.getShowTimeId()),
                new ObjectId(request.getBookingId())
        );
    }

    // 5️⃣ RELEASE SEATS (expired TEMP_LOCKED)
    @PostMapping("/release-seats")
    public ResponseEntity<?> releaseSeats(@RequestBody SeatConfirmRequest request) {

        if (request.getShowTimeId() == null) {
            return ResponseEntity.badRequest().body("showTimeId is required");
        }

        if (!ObjectId.isValid(request.getShowTimeId())) {
            return ResponseEntity.badRequest().body("Invalid showTimeId");
        }

        return service.releaseSeats(
                new ObjectId(request.getShowTimeId())
        );
    }

    // 6️⃣ GET SHOWTIMES BY MOVIE + THEATRE + DATE
    @PostMapping("/getByTMD")
    public List<Showtime> getShowtimesByMovieTheatreAndDate(
            @RequestBody ShowtimeSearchDTO dto) {

        return service.getShowtimesByMovieTheatreAndDate(
                dto.getMovieId(),
                dto.getTheatreId(),
                dto.getDate()
        );
    }
}
