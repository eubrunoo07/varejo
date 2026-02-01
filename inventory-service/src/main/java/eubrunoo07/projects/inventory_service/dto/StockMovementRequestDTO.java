package eubrunoo07.projects.inventory_service.dto;

import eubrunoo07.projects.inventory_service.enums.MovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequestDTO {

    @NotNull(message = "Product cannot be null")
    private UUID productId;
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
    @NotBlank(message = "Type cannot be blank")
    private String type;
    @NotBlank(message = "Reason cannot be blank")
    private String reason;

}
