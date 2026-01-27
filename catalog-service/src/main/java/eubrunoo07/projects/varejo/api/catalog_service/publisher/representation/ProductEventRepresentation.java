package eubrunoo07.projects.varejo.api.catalog_service.publisher.representation;

import eubrunoo07.projects.varejo.api.catalog_service.enums.KafkaEventProducerAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEventRepresentation {

    private KafkaEventProducerAction action;
    private UUID productId;
    private String sku;
    private Integer leadTimeDays;
    private Integer shelfLifeDays;

}
