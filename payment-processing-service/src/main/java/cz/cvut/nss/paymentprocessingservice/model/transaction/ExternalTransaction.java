package cz.cvut.nss.paymentprocessingservice.model.transaction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("EXTERNAL")
public class ExternalTransaction extends Transaction {

    @Column(name = "payment_system")
    private String paymentSystem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExternalTransaction that = (ExternalTransaction) o;
        return Objects.equals(paymentSystem, that.paymentSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), paymentSystem);
    }
}
