package eubrunoo07.projects.inventory_service.service;

import eubrunoo07.projects.inventory_service.dto.LowStockProductDTO;
import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import eubrunoo07.projects.inventory_service.model.Stock;

import java.util.List;
import java.util.UUID;

public interface StockService {
    void createStock(StockRequestDTO dto);

    Stock stockByProductId(UUID productId);

    void adjustSafetyStock(UUID productId, int safetyStock);

    List<LowStockProductDTO> getLowInventoryProducts();
}
