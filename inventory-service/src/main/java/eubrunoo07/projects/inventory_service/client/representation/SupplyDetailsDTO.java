package eubrunoo07.projects.inventory_service.client.representation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyDetailsDTO {

    @NotBlank(message = "Supplier company cannot be blank")
    private String supplierCompany;
    @NotNull(message = "Lead time days cannot be null")
    private Integer leadTimeDays;
    @NotNull(message = "Min order quantity cannot be null")
    private Integer minOrderQuantity;
    @NotNull(message = "Shelf life days cannot be null")
    private Integer shelfLifeDays;

}
