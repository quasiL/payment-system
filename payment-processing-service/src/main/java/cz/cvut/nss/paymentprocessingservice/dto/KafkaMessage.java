package cz.cvut.nss.paymentprocessingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class KafkaMessage {

    private Integer id;

    public KafkaMessage(@JsonProperty Integer id) {
        this.id = id;
    }
}