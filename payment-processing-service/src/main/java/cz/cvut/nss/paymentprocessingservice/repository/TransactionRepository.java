package cz.cvut.nss.paymentprocessingservice.repository;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.model.transaction.Transaction;
import cz.cvut.nss.paymentprocessingservice.model.transaction.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findByPayment(Payment payment);
}
