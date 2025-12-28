package com.moviebooking.showtimeservice.repository;

import com.mongodb.internal.connection.Time;
import com.moviebooking.showtimeservice.entity.Showtime;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ShowtimeRepository extends MongoRepository<Showtime, ObjectId> {
    List<Showtime> findByMovieIdAndTheatreIdAndStartTimeBetween(
            ObjectId movieId,
            ObjectId theatreId,
            LocalDateTime start,
            LocalDateTime end
    );
}
