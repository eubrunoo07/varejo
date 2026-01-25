package eubrunoo07.projects.varejo.api.catalog_service.service.impl;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.mapper.ProductMapper;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import eubrunoo07.projects.varejo.api.catalog_service.repository.ProductRepository;
import eubrunoo07.projects.varejo.api.catalog_service.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product createProduct(ProductRequestDTO dto) {
        Product product = productMapper.map(dto);
        product.setSku(generateSkuCode(product.getName(), product.getCategory().name()));
        return productRepository.save(product);
    }

    private String generateSkuCode(String name, String category){
        String namePart = name.length() >= 3 ? name.substring(0, 3).toUpperCase() : name.toUpperCase();
        String categoryPart = category.length() >= 3 ? category.substring(0, 3).toUpperCase() : category.toUpperCase();
        long timestampPart = System.currentTimeMillis() % 10000; // Last 4 digits of current time in milliseconds
        return namePart + "-" + categoryPart + "-" + String.format("%04d", timestampPart);
    }
}
