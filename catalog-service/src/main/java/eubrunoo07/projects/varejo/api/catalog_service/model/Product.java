package eubrunoo07.projects.varejo.api.catalog_service.model;

import eubrunoo07.projects.varejo.api.catalog_service.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String sku;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    @Column
    private BigDecimal price;
    @Column
    private Boolean active;

    @Embedded
    private SupplyDetails supplyDetails;

    @PrePersist
    protected void prePersist(){
        this.active = true;
    }

}
