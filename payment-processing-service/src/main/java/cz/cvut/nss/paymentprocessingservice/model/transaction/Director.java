package cz.cvut.nss.paymentprocessingservice.model.transaction;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Director {

    public void buildInternalTransaction(ITransactionBuilder builder, Payment payment) {
        builder.setPayment(payment);
        builder.setTransactionDate(LocalDateTime.now());
        builder.setTransactionStatus(TransactionStatus.PENDING);
    }

    public void buildExternalTransaction(ITransactionBuilder builder, Payment payment) {
        builder.setPayment(payment);
        builder.setTransactionDate(LocalDateTime.now());
        builder.setTransactionStatus(TransactionStatus.PENDING);
        //TODO Add some logic for choosing a payment system based on checking the account number in the payment
        builder.setPaymentSystem("PayPal");
    }
}
