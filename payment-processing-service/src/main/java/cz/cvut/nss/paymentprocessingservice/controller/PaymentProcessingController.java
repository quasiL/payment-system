package cz.cvut.nss.paymentprocessingservice.controller;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.repository.PaymentProcessingRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PaymentProcessingController {

    private PaymentProcessingRepository paymentProcessingRepository;

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return paymentProcessingRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void createPayment(@RequestBody Payment payment) {
        paymentProcessingRepository.save(payment);
    }
}
