package eubrunoo07.projects.varejo.api.catalog_service.service;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductResponseDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.SupplyDetailsDTO;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(@Valid ProductRequestDTO dto);

    void addSupplyDetailsOnProduct(@Valid SupplyDetailsDTO dto, String sku);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(UUID id);

    ProductResponseDTO getProductBySku(String sku);
}
