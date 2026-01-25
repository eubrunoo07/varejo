package eubrunoo07.projects.varejo.api.catalog_service.mapper;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductResponseDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.SupplyDetailsDTO;
import eubrunoo07.projects.varejo.api.catalog_service.enums.ProductCategory;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import eubrunoo07.projects.varejo.api.catalog_service.model.SupplyDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product map(ProductRequestDTO dto){
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        product.setCategory(ProductCategory.valueOf(dto.getCategory()));
        product.setSupplyDetails(null);
        return product;
    }

    public ProductResponseDTO map(Product product){
        SupplyDetailsDTO supplyDetails;
        if(product.getSupplyDetails() == null){
            supplyDetails = null;
        } else{
            supplyDetails = SupplyDetailsDTO
                    .builder()
                    .supplierCompany(product.getSupplyDetails().getSupplierCompany())
                    .leadTimeDays(product.getSupplyDetails().getLeadTimeDays())
                    .minOrderQuantity(product.getSupplyDetails().getMinOrderQuantity())
                    .shelfLifeDays(product.getSupplyDetails().getShelfLifeDays())
                    .build();
        }

        return ProductResponseDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sku(product.getSku())
                .price(product.getPrice())
                .active(product.getActive())
                .category(product.getCategory().name())
                .supplyDetails(supplyDetails)
                .build();
    }

    public SupplyDetails map(SupplyDetailsDTO dto){
        SupplyDetails supplyDetails = new SupplyDetails();
        BeanUtils.copyProperties(dto, supplyDetails);
        return supplyDetails;
    }

}
