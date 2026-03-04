package eubrunoo07.projects.inventory_service.repository;

import eubrunoo07.projects.inventory_service.model.Stock;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
    boolean existsByProductId(UUID productId);
}
