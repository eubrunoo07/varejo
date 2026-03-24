package eubrunoo07.projects.inventory_service.service.impl;

import eubrunoo07.projects.inventory_service.client.ProductClient;
import eubrunoo07.projects.inventory_service.dto.StockMovementRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockMovementResponseDTO;
import eubrunoo07.projects.inventory_service.enums.KafkaEventProducerAction;
import eubrunoo07.projects.inventory_service.enums.MovementType;
import eubrunoo07.projects.inventory_service.mapper.StockMapper;
import eubrunoo07.projects.inventory_service.model.Stock;
import eubrunoo07.projects.inventory_service.model.StockMovement;
import eubrunoo07.projects.inventory_service.publisher.StockMovementPublisher;
import eubrunoo07.projects.inventory_service.repository.StockMovementRepository;
import eubrunoo07.projects.inventory_service.repository.StockRepository;
import eubrunoo07.projects.inventory_service.service.StockMovementService;
import eubrunoo07.projects.inventory_service.validator.StockValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StockMovementImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final StockRepository stockRepository;
    private final ProductClient productClient;
    private final StockValidator stockValidator;
    private final StockMapper stockMapper;
    private final StockMovementPublisher stockMovementPublisher;

    public StockMovementImpl(StockMovementRepository stockMovementRepository, StockRepository stockRepository, ProductClient productClient, StockValidator stockValidator, StockMapper stockMapper, StockMovementPublisher stockMovementPublisher) {
        this.stockMovementRepository = stockMovementRepository;
        this.stockRepository = stockRepository;
        this.productClient = productClient;
        this.stockValidator = stockValidator;
        this.stockMapper = stockMapper;
        this.stockMovementPublisher = stockMovementPublisher;
    }

    @Override
    public StockMovementResponseDTO registryEntry(StockMovementRequestDTO dto) {
        if(productClient.getProductById(dto.getProductId()).getBody().getId() == null){
            throw new IllegalArgumentException("Product with ID " + dto.getProductId() + " not found.");
        }
        stockValidator.validateStockMovementRequest(dto);

        StockMovement stockMovement = new StockMovement();
        BeanUtils.copyProperties(dto, stockMovement);
        stockMovement.setType(MovementType.valueOf(dto.getType()));

        StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);

        Stock productStock = stockRepository
                .findByProductId(stockMovement.getProductId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Stock for product with ID " + stockMovement.getProductId() + " not found."));
        productStock.setQuantityWarehouse(productStock.getQuantityWarehouse() + stockMovement.getQuantity());

        stockMovementPublisher.publishInventoryEvent(savedStockMovement, KafkaEventProducerAction.ENTRY_REGISTRY);

        stockRepository.save(productStock);
        return stockMapper.map(savedStockMovement);
    }
}
