package eubrunoo07.projects.inventory_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "stock")
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID productId;
    private Integer quantityGondola;
    private Integer quantityWarehouse;
    private Integer minimumSafetyStock;

}
