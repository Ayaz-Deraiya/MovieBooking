package com.moviebooking.showtimeservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

public class UpdateOccupiedSeat {
    @JsonProperty("Id")
    private String Id;
    private List<Integer> seats;

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public String getShowTimeId() {
        return Id;
    }

    public void setShowTimeId(String showTimeId) {
        this.Id = showTimeId;
    }
}
