package cz.cvut.nss.paymentprocessingservice.repository;

import cz.cvut.nss.paymentprocessingservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findByAccount(String account);

    @Modifying
    @Query("UPDATE UserAccount u SET u.balance = :newBalance WHERE u.id = :id")
    void updateBalance(@Param("id") Integer id, @Param("newBalance") BigDecimal newBalance);
}
