package eubrunoo07.projects.inventory_service.service.impl;

import eubrunoo07.projects.inventory_service.repository.StockRepository;
import eubrunoo07.projects.inventory_service.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
}
