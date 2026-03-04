package eubrunoo07.projects.inventory_service.service;

import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockResponseDTO;
import eubrunoo07.projects.inventory_service.model.Stock;

import java.util.UUID;

public interface StockService {
    void createStock(StockRequestDTO dto);

    Stock stockByProductId(UUID productId);
}
