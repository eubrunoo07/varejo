package eubrunoo07.projects.inventory_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRequestDTO {

    @NotNull(message = "Product cannot be null")
    private UUID productId;
    @NotNull(message = "Quantity in gondola cannot be null")
    private Integer quantityGondola;
    @NotNull(message = "Quantity in warehouse cannot be null")
    private Integer quantityWarehouse;
    @NotNull(message = "Minimum safety stock cannot be null")
    private Integer minimumSafetyStock;

}
