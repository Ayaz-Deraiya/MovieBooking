package com.moviebooking.bookingservice.Service;

import com.moviebooking.bookingservice.entity.Booking;
import com.moviebooking.bookingservice.entity.BookingStatus;
import com.moviebooking.bookingservice.repository.BookingRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // 1️⃣ CREATE BOOKING (after seats are TEMP_LOCKED by Showtime)
    public ResponseEntity<?> addBooking(Booking booking) {
        try {
            LocalDateTime now = LocalDateTime.now();

            booking.setStatus(BookingStatus.PAYMENT_PENDING);
            booking.setCreatedAt(now);
            booking.setExpiresAt(now.plusMinutes(5));

            bookingRepository.save(booking);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(booking);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    // 2️⃣ CONFIRM BOOKING (called after payment SUCCESS)
    public ResponseEntity<?> confirmBooking(ObjectId bookingId, ObjectId paymentId) {
        try {
            Optional<Booking> optionalBooking =
                    bookingRepository.findById(bookingId);

            if (optionalBooking.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Booking not found");
            }

            Booking booking = optionalBooking.get();

            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                return ResponseEntity.ok(booking); // idempotent
            }

            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setPaymentId(paymentId);

            bookingRepository.save(booking);

            return ResponseEntity.ok(booking);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    // 3️⃣ GET ALL BOOKINGS
    public ResponseEntity<?> getAllBookings() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookingRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
