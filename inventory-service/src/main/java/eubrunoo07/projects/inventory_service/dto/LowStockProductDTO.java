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
public class LowStockProductDTO {

    private UUID productId;
    private String name;
    private String sku;
    private Integer gondolaQuantity;
    private Integer warehouseQuantity;
    private Integer totalQuantity;
    private Integer minimumSafetyStock;

}
