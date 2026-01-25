package eubrunoo07.projects.varejo.api.catalog_service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SupplyDetails {

    private String supplierCompany;
    private Integer leadTimeDays;
    private Integer minOrderQuantity;
    private Integer shelfLifeDays;

}
