package com.moviebooking.showtimeservice.entity;

public class PriceCategory {
    private int price;
    private int startSeat;
    private int endSeat;

    // Getters and setters
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStartSeat() {
        return startSeat;
    }

    public void setStartSeat(int startSeat) {
        this.startSeat = startSeat;
    }

    public int getEndSeat() {
        return endSeat;
    }

    public void setEndSeat(int endSeat) {
        this.endSeat = endSeat;
    }
}
