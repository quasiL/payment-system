package cz.cvut.nss.paymentprocessingservice.service;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.model.transaction.*;
import cz.cvut.nss.paymentprocessingservice.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static cz.cvut.nss.paymentprocessingservice.MessageConstants.TRANSACTION_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final Director director;
    private final ExternalTransactionBuilder externalTransactionBuilder;
    private final InternalTransactionBuilder internalTransactionBuilder;

    public void createTransactionFromPayment(Payment payment) {
        if (payment.isInternal()) {
            director.buildInternalTransaction(internalTransactionBuilder, payment);
            transactionRepository.save(internalTransactionBuilder.getResult());
        } else {
            director.buildExternalTransaction(externalTransactionBuilder, payment);
            transactionRepository.save(externalTransactionBuilder.getResult());
        }
    }

    public Transaction getTransactionByPayment(Payment payment) {
        return transactionRepository.findByPayment(payment)
                .orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND_EXCEPTION_MESSAGE));
    }
}
