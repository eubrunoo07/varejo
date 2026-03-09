package eubrunoo07.projects.inventory_service.service.impl;

import eubrunoo07.projects.inventory_service.repository.StockMovementRepository;
import eubrunoo07.projects.inventory_service.service.StockMovementService;
import org.springframework.stereotype.Service;

@Service
public class StockMovementImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public StockMovementImpl(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }
}
