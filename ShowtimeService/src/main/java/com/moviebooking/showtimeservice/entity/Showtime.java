package com.moviebooking.showtimeservice.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "showtime")
public class Showtime {

    @Id
    private ObjectId id;

    private ObjectId movieId;

    private ObjectId theatreId;

    private Integer screenNo;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<PriceCategory> priceByCategory; // cleaner than List<List<Integer>>

    private List<Seat> seatsOccupied;

    // Getters and setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }


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

    public Integer getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(Integer screenNo) {
        this.screenNo = screenNo;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<PriceCategory> getPriceByCategory() {
        return priceByCategory;
    }

    public void setPriceByCategory(List<PriceCategory> priceByCategory) {
        this.priceByCategory = priceByCategory;
    }

    public List<Seat> getSeats() {
        return seatsOccupied;
    }

    public void setSeatsOccupied(List<Seat> seatsOccupied) {
        this.seatsOccupied = seatsOccupied;
    }
}
