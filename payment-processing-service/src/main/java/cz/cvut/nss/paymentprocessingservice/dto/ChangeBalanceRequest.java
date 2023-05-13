package cz.cvut.nss.paymentprocessingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBalanceRequest {

    private String account;
    private BigDecimal amount;
}
