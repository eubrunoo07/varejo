package eubrunoo07.projects.inventory_service.service;

import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;

public interface StockService {
    void createStock(StockRequestDTO dto);
}
