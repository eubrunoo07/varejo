package eubrunoo07.projects.inventory_service.repository;

import eubrunoo07.projects.inventory_service.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
}
