package eubrunoo07.projects.inventory_service.client.representation;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductRepresentation {
    private UUID id;
    private String sku;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Boolean active;
    private SupplyDetailsDTO supplyDetails;
}
