package eubrunoo07.projects.inventory_service.mapper;

import eubrunoo07.projects.inventory_service.dto.StockMovementRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockMovementResponseDTO;
import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockResponseDTO;
import eubrunoo07.projects.inventory_service.enums.MovementType;
import eubrunoo07.projects.inventory_service.model.Stock;
import eubrunoo07.projects.inventory_service.model.StockMovement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    //STOCK MOVEMENT MAPPER

    public StockMovementResponseDTO map(StockMovement stockMovement){
        return StockMovementResponseDTO.builder()
                .id(stockMovement.getId())
                .productId(stockMovement.getProductId())
                .quantity(stockMovement.getQuantity())
                .type(stockMovement.getType())
                .movementDate(stockMovement.getMovementDate())
                .build();
    }

    public StockMovement map(StockMovementRequestDTO dto){
        StockMovement stockMovement = new StockMovement();
        BeanUtils.copyProperties(dto, stockMovement);
        stockMovement.setType(MovementType.valueOf(dto.getType()));
        return stockMovement;
    }

    //STOCK MAPPER

    public StockResponseDTO map(Stock stock){
        return StockResponseDTO
                .builder()
                .id(stock.getId())
                .productId(stock.getProductId())
                .quantityGondola(stock.getQuantityGondola())
                .quantityWarehouse(stock.getQuantityWarehouse())
                .minimumSafetyStock(stock.getMinimumSafetyStock())
                .build();
    }

    public Stock map(StockRequestDTO dto){
        Stock stock = new Stock();
        BeanUtils.copyProperties(dto, stock);
        return stock;
    }
}
