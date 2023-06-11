package cz.cvut.nss.paymentprocessingservice.controller;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.model.transaction.Transaction;
import cz.cvut.nss.paymentprocessingservice.service.PaymentProcessingService;
import cz.cvut.nss.paymentprocessingservice.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private PaymentProcessingService paymentProcessingService;

    @GetMapping("/payment_id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction getTransaction(@PathVariable Integer id) {
        return transactionService.getTransactionByPayment(paymentProcessingService.getPaymentById(id));
    }

    @PostMapping("/add/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@PathVariable Integer id) {
        Payment payment = paymentProcessingService.getPaymentById(id);
        transactionService.createTransactionFromPayment(payment);
    }
}
