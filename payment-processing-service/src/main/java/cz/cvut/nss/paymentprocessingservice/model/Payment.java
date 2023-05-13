package cz.cvut.nss.paymentprocessingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_account", referencedColumnName = "id")
    private UserAccount fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account", referencedColumnName = "id")
    private UserAccount toAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name =  "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "is_internal")
    private boolean isInternal;

    @Column(name = "is_invalidated")
    private boolean isInvalidated;
}
