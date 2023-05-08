package cz.cvut.nss.paymentprocessingservice.repository;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProcessingRepository extends JpaRepository<Payment, Integer>  {
}
