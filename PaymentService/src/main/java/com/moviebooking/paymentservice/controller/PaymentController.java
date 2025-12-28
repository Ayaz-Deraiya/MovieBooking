package com.moviebooking.paymentservice.controller;
import com.moviebooking.paymentservice.DTO.PaymentDTO;

import com.moviebooking.paymentservice.DTO.createPaymentDTO;
import com.moviebooking.paymentservice.Service.PaymentServiceWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/paymentService")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentServiceWrapper paymentServiceWrapper;
    public PaymentController(PaymentServiceWrapper paymentServiceWrapper) {
        this.paymentServiceWrapper = paymentServiceWrapper;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody createPaymentDTO createPaymentDTO) {
        return paymentServiceWrapper.createPayment(createPaymentDTO);
    }

    @PostMapping("/execute")
    public ResponseEntity<?> executePayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentServiceWrapper.executePayment(paymentDTO);
    }

}

