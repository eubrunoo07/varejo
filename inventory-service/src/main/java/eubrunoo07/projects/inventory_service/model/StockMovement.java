package eubrunoo07.projects.inventory_service.model;

import eubrunoo07.projects.inventory_service.enums.MovementType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_movements")
@Data
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID productId;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private MovementType type;
    private String reason;
    private LocalDateTime movementDate;

    @PrePersist
    protected void prePersist(){
        this.movementDate = LocalDateTime.now();
    }

}
