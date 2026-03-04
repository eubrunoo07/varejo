package eubrunoo07.projects.inventory_service.controller;

import eubrunoo07.projects.inventory_service.dto.LowStockProductDTO;
import eubrunoo07.projects.inventory_service.dto.StockRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockResponseDTO;
import eubrunoo07.projects.inventory_service.mapper.StockMapper;
import eubrunoo07.projects.inventory_service.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/varejo/stock")
public class StockController {

    private final StockService stockService;
    private final StockMapper stockMapper;

    public StockController(StockService stockService, StockMapper stockMapper) {
        this.stockService = stockService;
        this.stockMapper = stockMapper;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody@Valid StockRequestDTO dto){
        stockService.createStock(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<StockResponseDTO> stockByProductId(@PathVariable UUID productId){
        return ResponseEntity.ok(stockMapper.map(stockService.stockByProductId(productId)));
    }

    @GetMapping("/low-inventory")
    public ResponseEntity<List<LowStockProductDTO>> getLowInventoryProducts(){
        return ResponseEntity.ok(stockService.getLowInventoryProducts());
    }

    @PatchMapping("/{productId}/safety-stock")
    public ResponseEntity<Void> adjustSafetyStock(@PathVariable UUID productId, @RequestParam int safetyStock){
        stockService.adjustSafetyStock(productId, safetyStock);
        return ResponseEntity.ok().build();
    }

}
