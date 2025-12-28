package com.moviebooking.bookingservice.schedular;

import com.moviebooking.bookingservice.entity.Booking;
import com.moviebooking.bookingservice.entity.BookingStatus;
import com.moviebooking.bookingservice.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Component
public class BookingCleanupScheduler {

    @Autowired
    private BookingRepository bookingRepository;

    // 1️⃣ Expire PAYMENT_PENDING bookings after 5 minutes
    @Scheduled(fixedRate = 60000) // every 1 minute
    public void expirePendingBookings() {

        LocalDateTime now = LocalDateTime.now();

        List<Booking> expired =
                bookingRepository.findByStatusAndExpiresAtBefore(
                        BookingStatus.PAYMENT_PENDING,
                        now
                );

        for (Booking booking : expired) {
            booking.setStatus(BookingStatus.EXPIRED);
            booking.setUpdatedAt(now);
            bookingRepository.save(booking);
        }
    }

    // 2️⃣ Delete EXPIRED / CANCELLED bookings after 1 minute
    @Scheduled(fixedRate = 60000)
    public void deleteOldBookings() {

        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(1);

        List<Booking> expired =
                bookingRepository.findByStatusAndUpdatedAtBefore(
                        BookingStatus.EXPIRED,
                        cutoff
                );

        List<Booking> cancelled =
                bookingRepository.findByStatusAndUpdatedAtBefore(
                        BookingStatus.CANCELLED,
                        cutoff
                );

        bookingRepository.deleteAll(expired);
        bookingRepository.deleteAll(cancelled);
    }
}
