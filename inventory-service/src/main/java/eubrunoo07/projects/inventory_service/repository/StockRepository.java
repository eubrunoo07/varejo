package eubrunoo07.projects.inventory_service.repository;

import eubrunoo07.projects.inventory_service.model.Stock;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {
    Optional<Stock> findByProductId(UUID productId);
    boolean existsByProductId(UUID productId);
    @Query("""
       SELECT s.productId
       FROM Stock s
       WHERE (s.quantityGondola + s.quantityWarehouse) < s.minimumSafetyStock
       """)
    List<UUID> findProductsInLowStock();
}
