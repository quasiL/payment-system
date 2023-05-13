package cz.cvut.nss.paymentprocessingservice.model.transaction;

import cz.cvut.nss.paymentprocessingservice.model.Payment;

import java.time.LocalDateTime;

public interface ITransactionBuilder {

    void setTransactionDate(LocalDateTime date);

    void setTransactionStatus(TransactionStatus status);

    void setPayment(Payment payment);

    void setPaymentSystem(String paymentSystem);
}
