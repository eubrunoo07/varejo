package eubrunoo07.projects.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDTO {

    private UUID id;
    private UUID productId;
    private Integer quantityGondola;
    private Integer quantityWarehouse;
    private Integer minimumSafetyStock;

}
