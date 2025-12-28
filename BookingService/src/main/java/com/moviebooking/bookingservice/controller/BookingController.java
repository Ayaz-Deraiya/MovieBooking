package com.moviebooking.bookingservice.controller;

import com.moviebooking.bookingservice.DTO.confirmBookingDTO;
import com.moviebooking.bookingservice.Service.BookingService;import com.moviebooking.bookingservice.entity.Booking;import com.moviebooking.bookingservice.repository.BookingRepository;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import java.util.*;



@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    
    @PostMapping("/addBooking")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        return bookingService.addBooking(booking);
    }

    @GetMapping("/getBookings")
    public ResponseEntity<?> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/confirmBooking")
    public ResponseEntity<?> confirmBooking(@RequestBody confirmBookingDTO confirmBookingDTO) {
        return bookingService.confirmBooking(confirmBookingDTO.getBookingId(),confirmBookingDTO.getPaymentId());
    }
}
