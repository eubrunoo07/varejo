package eubrunoo07.projects.inventory_service.publisher.representation;

import eubrunoo07.projects.inventory_service.enums.KafkaEventProducerAction;
import eubrunoo07.projects.inventory_service.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRepresentation {

    private KafkaEventProducerAction action;
    private UUID productId;
    private Integer quantity;
    private MovementType type;
    private String reason;
    private LocalDateTime movementDate;

}
