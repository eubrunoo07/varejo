package eubrunoo07.projects.inventory_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class StockMovementResponseDTO {

    private UUID id;
    private UUID productId;
    private Integer quantity;
    private MovementType type;
    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime movementDate;

}
