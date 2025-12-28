package com.moviebooking.bookingservice.repository;

import com.moviebooking.bookingservice.entity.Booking;
import com.moviebooking.bookingservice.entity.BookingStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.*;

public interface BookingRepository extends MongoRepository<Booking, ObjectId> {
    List<Booking> findByStatusAndExpiresAtBefore(
            BookingStatus status,
            LocalDateTime time
    );

    List<Booking> findByStatusAndUpdatedAtBefore(
            BookingStatus status,
            LocalDateTime time
    );
}
