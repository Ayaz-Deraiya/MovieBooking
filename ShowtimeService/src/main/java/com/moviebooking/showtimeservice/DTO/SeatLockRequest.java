package com.moviebooking.showtimeservice.DTO;
import java.util.*;

public class SeatLockRequest {
    private String showTimeId;
    private List<Integer> seats;

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public String getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId) {
        this.showTimeId = showTimeId;
    }
}

