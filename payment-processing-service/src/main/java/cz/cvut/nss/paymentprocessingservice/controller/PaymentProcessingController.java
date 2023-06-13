package cz.cvut.nss.paymentprocessingservice.controller;

import cz.cvut.nss.paymentprocessingservice.dto.ChangeBalanceRequest;
import cz.cvut.nss.paymentprocessingservice.dto.PaymentRequest;
import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.model.UserAccount;
import cz.cvut.nss.paymentprocessingservice.service.PaymentProcessingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentProcessingController {

    private PaymentProcessingService paymentProcessingService;

    @GetMapping("/user_payments/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> getUserPayments(@PathVariable String accountNumber) throws InterruptedException {
        Thread.sleep(500);
        return paymentProcessingService.getAllUserRelatedPayments(accountNumber);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentProcessingService.createNewPayment(paymentRequest);
    }

    @GetMapping("/user_account/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccount getUserAccountByAccount(@PathVariable String accountNumber) {
        return paymentProcessingService.getUserAccountByAccount(accountNumber);
    }

    @GetMapping("/user_account/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccount getUserAccountByAccount(@PathVariable Integer id) {
        return paymentProcessingService.getUserAccountById(id);
    }

    @PostMapping("/user_account/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addToUserBalance(@RequestBody ChangeBalanceRequest request) {
        paymentProcessingService.addToUserBalance(request);
    }

    @PostMapping("/user_account/subtract")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subtractFromUserBalance(@RequestBody ChangeBalanceRequest request) {
        paymentProcessingService.subtractFromUserBalance(request);
    }
}
