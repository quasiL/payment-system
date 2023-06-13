package cz.cvut.nss.paymentprocessingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_accounts")
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "account")
    private String account;

    @Column(name = "balance")
    private BigDecimal balance;

    public void addAmount(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
