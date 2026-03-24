package eubrunoo07.projects.inventory_service.controller;

import eubrunoo07.projects.inventory_service.dto.StockMovementRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockMovementResponseDTO;
import eubrunoo07.projects.inventory_service.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/varejo/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping("/entry")
    public ResponseEntity<StockMovementResponseDTO> entry(@RequestBody@Valid StockMovementRequestDTO dto){
        return ResponseEntity.ok(stockMovementService.registryEntry(dto));
    }
}
