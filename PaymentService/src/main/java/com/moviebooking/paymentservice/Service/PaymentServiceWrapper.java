package com.moviebooking.paymentservice.Service;

import com.moviebooking.paymentservice.DTO.PaymentDTO;
import com.moviebooking.paymentservice.DTO.PaymentResponseDTO;
import com.moviebooking.paymentservice.DTO.createPaymentDTO;
import com.moviebooking.paymentservice.entity.paymentStatus;
import com.moviebooking.paymentservice.repository.paymentRepo;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;


@Service
public class PaymentServiceWrapper {


    private final paymentRepo paymentRepository;
    private final PaypalService paypalService;

    public PaymentServiceWrapper(PaypalService paypalService,
                                 paymentRepo paymentRepository) {
        this.paypalService = paypalService;
        this.paymentRepository = paymentRepository;
    }

    @Autowired
    private RestTemplate restTemplate;
    private static final String SHOWTIME_BASE = "http://localhost:8084/showtime";
    private static final String BOOKING_BASE = "http://localhost:8085/booking";


    @Value("${paypal.cancelUrl}")
    private String cancelUrl;

    @Value("${paypal.successUrl}")
    private String successUrl;

    public ResponseEntity<?> createPayment(createPaymentDTO cPaymentDTO) {
        try {
             Payment payment = paypalService.createPayment(
                    cPaymentDTO.getAmount(),
                    "USD",
                    "paypal",
                    "sale",
                    "movie Booking",
                    cancelUrl,
                    successUrl);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return ResponseEntity.ok().body(links.getHref());
                }
            }
            return ResponseEntity.badRequest().body("No approval_url found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> executePayment(PaymentDTO paymentDTO) {
        try {
            Payment payment =
                    paypalService.executePayment(
                            paymentDTO.getPaymentId(),
                            paymentDTO.getPayerId());

            com.moviebooking.paymentservice.entity.Payment payDoc =
                    paymentRepository.findByPaymentId(paymentDTO.getPaymentId())
                            .orElseThrow(() -> new RuntimeException("Payment not found"));

            payDoc.setPayerId(paymentDTO.getPayerId());

            if ("approved".equals(payment.getState())) {
                payDoc.setStatus(paymentStatus.SUCCESS);
                paymentRepository.save(payDoc);

                // 1️⃣ Confirm booking
                Map<String, String> bookingReq = new HashMap<>();
                bookingReq.put("bookingId", payDoc.getBookingId());
                bookingReq.put("paymentId", payDoc.getPaymentId());

                restTemplate.postForEntity(
                        BOOKING_BASE + "/confirmBooking",
                        bookingReq,
                        Void.class
                );

                // 2️⃣ Confirm seats
                Map<String, String> seatReq = new HashMap<>();
                seatReq.put("showTimeId", payDoc.getShowTimeId());
                seatReq.put("bookingId", payDoc.getBookingId());

                restTemplate.postForEntity(
                        SHOWTIME_BASE + "/confirm-seats",
                        seatReq,
                        Void.class
                );

                return ResponseEntity.ok(paymentToDTO(payment));
            } else {
                payDoc.setStatus(paymentStatus.FAILED);
                paymentRepository.save(payDoc);

                Map<String, String> seatReq = new HashMap<>();
                seatReq.put("showTimeId", payDoc.getShowTimeId());
                seatReq.put("bookingId", payDoc.getBookingId());

                restTemplate.postForEntity(
                        SHOWTIME_BASE + "/release-seats",
                        seatReq,
                        Void.class
                );

                return ResponseEntity.badRequest().body("Payment failed");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public PaymentResponseDTO paymentToDTO(Payment payment) {

        if (payment == null) {
            return null;
        }

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setState(payment.getState());
        dto.setIntent(payment.getIntent());
        dto.setCreateTime(payment.getCreateTime());
        dto.setUpdateTime(payment.getUpdateTime());

        // Payer info
        if (payment.getPayer() != null && payment.getPayer().getPayerInfo() != null) {
            PaymentResponseDTO.PayerDTO payerDTO = new PaymentResponseDTO.PayerDTO();
            payerDTO.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
            payerDTO.setEmail(payment.getPayer().getPayerInfo().getEmail());
            payerDTO.setFirstName(payment.getPayer().getPayerInfo().getFirstName());
            payerDTO.setLastName(payment.getPayer().getPayerInfo().getLastName());
            dto.setPayer(payerDTO);
        }

        // Transactions
        List<PaymentResponseDTO.TransactionDTO> txnDTOs = getTransactionDTOS(payment);
        dto.setTransactions(txnDTOs);

        // Links
        List<PaymentResponseDTO.LinkDTO> linkDTOs = new ArrayList<>();
        if (payment.getLinks() != null) {
            for (Links link : payment.getLinks()) {
                PaymentResponseDTO.LinkDTO linkDTO = new PaymentResponseDTO.LinkDTO();
                linkDTO.setHref(link.getHref());
                linkDTO.setRel(link.getRel());
                linkDTO.setMethod(link.getMethod());
                linkDTOs.add(linkDTO);
            }
        }
        dto.setLinks(linkDTOs);

        return dto;
    }

    private static List<PaymentResponseDTO.TransactionDTO> getTransactionDTOS(Payment payment) {
        List<PaymentResponseDTO.TransactionDTO> txnDTOs = new ArrayList<>();
        if (payment.getTransactions() != null) {
            for (Transaction txn : payment.getTransactions()) {
                PaymentResponseDTO.TransactionDTO txnDTO = new PaymentResponseDTO.TransactionDTO();
                txnDTO.setDescription(txn.getDescription());

                if (txn.getAmount() != null) {
                    PaymentResponseDTO.AmountDTO amtDTO = new PaymentResponseDTO.AmountDTO();
                    amtDTO.setTotal(txn.getAmount().getTotal());
                    amtDTO.setCurrency(txn.getAmount().getCurrency());
                    txnDTO.setAmount(amtDTO);
                }

                txnDTOs.add(txnDTO);
            }
        }
        return txnDTOs;
    }
}
