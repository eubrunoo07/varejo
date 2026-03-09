package eubrunoo07.projects.inventory_service.validator;

import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class StockValidator {

    public void validateStockRequest(StockRequestDTO dto){
        if(dto.getMinimumSafetyStock() < 0){
            throw new IllegalArgumentException("Minimum safety stock cannot be negative.");
        }
        if(dto.getQuantityGondola() < 0) {
            throw new IllegalArgumentException("Gondola quantity cannot be negative.");
        }
        if(dto.getQuantityWarehouse() < 0) {
            throw new IllegalArgumentException("Warehouse quantity cannot be negative.");
        }
    }

}
