package eubrunoo07.projects.inventory_service.service.impl;

import eubrunoo07.projects.inventory_service.client.ProductClient;
import eubrunoo07.projects.inventory_service.client.representation.ProductRepresentation;
import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import eubrunoo07.projects.inventory_service.mapper.StockMapper;
import eubrunoo07.projects.inventory_service.model.Stock;
import eubrunoo07.projects.inventory_service.repository.StockRepository;
import eubrunoo07.projects.inventory_service.service.StockService;
import eubrunoo07.projects.inventory_service.validator.StockValidator;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductClient productClient;
    private final StockValidator validator;
    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepository stockRepository, ProductClient productClient, StockValidator validator, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.productClient = productClient;
        this.validator = validator;
        this.stockMapper = stockMapper;
    }

    @Override
    public void createStock(StockRequestDTO dto) {
        if(productClient.getProductById(dto.getProductId()).getBody().getId() == null) {
            throw new IllegalArgumentException("Product with ID " + dto.getProductId() + " does not exist.");
        }
        if(stockRepository.existsByProductId(dto.getProductId())){
            throw new IllegalArgumentException("Stock for product with ID " + dto.getProductId() + " already exists.");
        }
        validator.validateStockRequest(dto);
        Stock stock = stockMapper.map(dto);
        stockRepository.save(stock);
    }
}
