package eubrunoo07.projects.varejo.api.catalog_service.service;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import jakarta.validation.Valid;

public interface ProductService {
    Product createProduct(@Valid ProductRequestDTO dto);
}
