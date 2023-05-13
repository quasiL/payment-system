package cz.cvut.nss.paymentprocessingservice.repository;

import cz.cvut.nss.paymentprocessingservice.model.Payment;
import cz.cvut.nss.paymentprocessingservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>  {

    List<Payment> findByFromAccountAndIsInvalidatedFalse(UserAccount userAccount);

    List<Payment> findByToAccountAndIsInvalidatedFalse(UserAccount userAccount);
}
