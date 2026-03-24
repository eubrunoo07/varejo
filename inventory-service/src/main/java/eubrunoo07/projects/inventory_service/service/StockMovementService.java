package eubrunoo07.projects.inventory_service.service;

import eubrunoo07.projects.inventory_service.dto.StockMovementRequestDTO;
import eubrunoo07.projects.inventory_service.dto.StockMovementResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public interface StockMovementService {
    StockMovementResponseDTO registryEntry(StockMovementRequestDTO dto);
}
