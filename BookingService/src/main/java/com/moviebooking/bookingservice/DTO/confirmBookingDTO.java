package com.moviebooking.bookingservice.DTO;

import org.bson.types.ObjectId;

public class confirmBookingDTO {
    private ObjectId paymentId;
    private ObjectId bookingId;

    public ObjectId getBookingId() {
        return bookingId;
    }

    public void setBookingId(ObjectId bookingId) {
        this.bookingId = bookingId;
    }

    public ObjectId getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(ObjectId paymentId) {
        this.paymentId = paymentId;
    }
}
