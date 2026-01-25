package eubrunoo07.projects.varejo.api.catalog_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private UUID id;
    private String sku;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Boolean active;
    private SupplyDetailsDTO supplyDetails;

}
