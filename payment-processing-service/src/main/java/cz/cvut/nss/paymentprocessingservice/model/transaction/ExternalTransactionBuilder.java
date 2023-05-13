package cz.cvut.nss.paymentprocessingservice.model.transaction;

import org.springframework.stereotype.Component;

@Component
public class ExternalTransactionBuilder extends TransactionBuilder {

    public ExternalTransaction getResult() {
        ExternalTransaction externalTransaction = new ExternalTransaction();
        externalTransaction.setTransactionDate(date);
        externalTransaction.setStatus(status);
        externalTransaction.setPayment(payment);
        externalTransaction.setPaymentSystem(paymentSystem);
        return externalTransaction;
    }
}
