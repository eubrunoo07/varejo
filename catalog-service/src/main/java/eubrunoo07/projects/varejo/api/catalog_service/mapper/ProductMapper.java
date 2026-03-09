package eubrunoo07.projects.varejo.api.catalog_service.mapper;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductResponseDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.SupplyDetailsDTO;
import eubrunoo07.projects.varejo.api.catalog_service.enums.KafkaEventProducerAction;
import eubrunoo07.projects.varejo.api.catalog_service.enums.ProductCategory;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import eubrunoo07.projects.varejo.api.catalog_service.model.SupplyDetails;
import eubrunoo07.projects.varejo.api.catalog_service.publisher.representation.ProductEventRepresentation;
import eubrunoo07.projects.varejo.api.catalog_service.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    private final ProductRepository productRepository;

    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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

    public List<ProductResponseDTO> map(List<Product> products){
        List<ProductResponseDTO> response = new ArrayList<>();
        products.forEach(product -> {
            SupplyDetailsDTO supplyDetails = null;
            if(product.getSupplyDetails() != null){
                supplyDetails = map(product.getSupplyDetails());
            }

            ProductResponseDTO productResponse = ProductResponseDTO
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
            response.add(productResponse);
        });
        return response;
    }

    public SupplyDetails map(SupplyDetailsDTO dto){
        SupplyDetails supplyDetails = new SupplyDetails();
        BeanUtils.copyProperties(dto, supplyDetails);
        return supplyDetails;
    }

    public SupplyDetailsDTO map(SupplyDetails supplyDetails){
        return SupplyDetailsDTO
                .builder()
                .supplierCompany(supplyDetails.getSupplierCompany())
                .leadTimeDays(supplyDetails.getLeadTimeDays())
                .minOrderQuantity(supplyDetails.getMinOrderQuantity())
                .shelfLifeDays(supplyDetails.getShelfLifeDays())
                .build();
    }

    public ProductEventRepresentation mapToRepresentation(Product product, KafkaEventProducerAction action) {
        if(product.getSupplyDetails() == null){
            return ProductEventRepresentation
                    .builder()
                    .action(action)
                    .productId(product.getId())
                    .sku(product.getSku())
                    .shelfLifeDays(null)
                    .leadTimeDays(null)
                    .build();
        }
        else{
            return ProductEventRepresentation
                    .builder()
                    .action(action)
                    .productId(product.getId())
                    .sku(product.getSku())
                    .shelfLifeDays(product.getSupplyDetails().getShelfLifeDays())
                    .leadTimeDays(product.getSupplyDetails().getLeadTimeDays())
                    .build();
        }
    }
}
