package com.moviebooking.paymentservice.repository;

import com.moviebooking.paymentservice.entity.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface paymentRepo extends MongoRepository<Payment, ObjectId> {
    Optional<Payment> findByPaymentId(String paymentId);
}
