package cz.cvut.nss.paymentprocessingservice.model.transaction;

import cz.cvut.nss.paymentprocessingservice.model.Payment;

import java.time.LocalDateTime;

public abstract class TransactionBuilder implements ITransactionBuilder {

    protected LocalDateTime date;
    protected TransactionStatus status;
    protected Payment payment;
    protected String paymentSystem;

    @Override
    public void setTransactionDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public void setTransactionStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public void setPaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }
}
