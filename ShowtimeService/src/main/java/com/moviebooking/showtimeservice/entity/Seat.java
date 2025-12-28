package com.moviebooking.showtimeservice.entity;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class Seat {

    private Integer seatNumber;

    private SeatState state;

    private ObjectId bookingId;       // who locked / booked it

    private LocalDateTime lockedAt;    // when temp lock started

    private LocalDateTime expiresAt;   // only for TEMP_LOCKED

    // getters & setters

    public ObjectId getBookingId() {
        return bookingId;
    }

    public void setBookingId(ObjectId bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(LocalDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatState getState() {
        return state;
    }

    public void setState(SeatState state) {
        this.state = state;
    }
}
