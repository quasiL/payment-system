package cz.cvut.nss.paymentprocessingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private boolean isInternal;
}
