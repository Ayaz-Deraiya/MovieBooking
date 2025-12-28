package com.moviebooking.showtimeservice.DTO;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class ShowtimeSearchDTO {

    private ObjectId movieId;
    private ObjectId theatreId;
    private LocalDate date;

    public ObjectId getMovieId() {
        return movieId;
    }

    public void setMovieId(ObjectId movieId) {
        this.movieId = movieId;
    }

    public ObjectId getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(ObjectId theatreId) {
        this.theatreId = theatreId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
