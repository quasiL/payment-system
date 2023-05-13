package cz.cvut.nss.paymentprocessingservice.model.transaction;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("INTERNAL")
public class InternalTransaction extends Transaction {
}
