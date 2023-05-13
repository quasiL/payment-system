package cz.cvut.nss.paymentprocessingservice.model.transaction;

import org.springframework.stereotype.Component;

@Component
public class InternalTransactionBuilder extends TransactionBuilder {

    public InternalTransaction getResult() {
        InternalTransaction internalTransaction = new InternalTransaction();
        internalTransaction.setTransactionDate(date);
        internalTransaction.setStatus(status);
        internalTransaction.setPayment(payment);
        return internalTransaction;
    }
}
