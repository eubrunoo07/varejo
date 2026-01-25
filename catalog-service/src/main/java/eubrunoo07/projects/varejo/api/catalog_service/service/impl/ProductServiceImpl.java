package eubrunoo07.projects.varejo.api.catalog_service.service.impl;

import eubrunoo07.projects.varejo.api.catalog_service.repository.ProductRepository;
import eubrunoo07.projects.varejo.api.catalog_service.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
