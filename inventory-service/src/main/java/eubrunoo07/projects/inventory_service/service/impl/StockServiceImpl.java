package eubrunoo07.projects.inventory_service.service.impl;

import eubrunoo07.projects.inventory_service.client.ProductClient;
import eubrunoo07.projects.inventory_service.client.representation.ProductRepresentation;
import eubrunoo07.projects.inventory_service.dto.LowStockProductDTO;
import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import eubrunoo07.projects.inventory_service.mapper.StockMapper;
import eubrunoo07.projects.inventory_service.model.Stock;
import eubrunoo07.projects.inventory_service.repository.StockRepository;
import eubrunoo07.projects.inventory_service.service.StockService;
import eubrunoo07.projects.inventory_service.validator.StockValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public Stock stockByProductId(UUID productId) {
        return stockRepository
                .findByProductId(productId).orElseThrow(() ->
                        new IllegalArgumentException("Stock for product with ID " + productId + " not found."));
    }

    @Override
    public void adjustSafetyStock(UUID productId, int safetyStock) {
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() ->
                new IllegalArgumentException("Stock for product with ID " + productId + " not found."));
        stock.setMinimumSafetyStock(safetyStock);
        stockRepository.save(stock);
    }

    @Override
    public List<LowStockProductDTO> getLowInventoryProducts() {
        List<UUID> lowStockProductIds = stockRepository.findProductsInLowStock();
        List<LowStockProductDTO> response = new ArrayList<>();
        for (UUID id : lowStockProductIds) {
            //Product
            ProductRepresentation product = productClient.getProductById(id).getBody();
            //Stock
            Stock stock = stockRepository.findByProductId(id).orElseThrow(() ->
                    new IllegalArgumentException("Stock for product with ID " + id + " not found."));

            LowStockProductDTO lowStockProductDTO = createLowStockProductData(id, product, stock);
            response.add(lowStockProductDTO);
        }
        return response;
    }

    private LowStockProductDTO createLowStockProductData(UUID id, ProductRepresentation product, Stock stock) {
        LowStockProductDTO lowStockProductDTO = new LowStockProductDTO();
        lowStockProductDTO.setProductId(id);
        lowStockProductDTO.setSku(product.getSku());
        lowStockProductDTO.setName(product.getName());
        lowStockProductDTO.setMinimumSafetyStock(stock.getMinimumSafetyStock());
        lowStockProductDTO.setWarehouseQuantity(stock.getQuantityWarehouse());
        lowStockProductDTO.setGondolaQuantity(stock.getQuantityGondola());
        lowStockProductDTO.setTotalQuantity(stock.getQuantityWarehouse() + stock.getQuantityGondola());
        return lowStockProductDTO;
    }
}
