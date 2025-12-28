package com.moviebooking.bookingservice.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.time.*;
@Document(collection = "Booking")
public class Booking {
    @Id
    private ObjectId bookingId;

    private ObjectId showTimeId;

    private String userId;

    private List<Integer> seatsBooked;

    private Double Amount;

    private BookingStatus status;

    private ObjectId paymentId;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private LocalDateTime updatedAt;

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public ObjectId getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(ObjectId paymentId) {
        this.paymentId = paymentId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Double getAmount() {return Amount;}

    public void setAmount(Double amount) {Amount = amount;}

    public ObjectId getBookingId() {
        return bookingId;
    }

    public void setBookingId(ObjectId bookingId) {
        this.bookingId = bookingId;
    }

    public List<Integer> getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(List<Integer> seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public ObjectId getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(ObjectId showTimeId) {
        this.showTimeId = showTimeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {this.userId = userId;}
}
