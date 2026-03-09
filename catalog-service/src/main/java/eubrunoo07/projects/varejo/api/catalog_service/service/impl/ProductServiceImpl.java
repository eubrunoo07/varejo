package eubrunoo07.projects.varejo.api.catalog_service.service.impl;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductResponseDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.SupplyDetailsDTO;
import eubrunoo07.projects.varejo.api.catalog_service.enums.KafkaEventProducerAction;
import eubrunoo07.projects.varejo.api.catalog_service.enums.ProductCategory;
import eubrunoo07.projects.varejo.api.catalog_service.mapper.ProductMapper;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import eubrunoo07.projects.varejo.api.catalog_service.publisher.ProductEventPublisher;
import eubrunoo07.projects.varejo.api.catalog_service.repository.ProductRepository;
import eubrunoo07.projects.varejo.api.catalog_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductEventPublisher productEventPublisher;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductEventPublisher productEventPublisher) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productEventPublisher = productEventPublisher;
    }

    @Override
    public Product createProduct(ProductRequestDTO dto) {
        Product product = productMapper.map(dto);
        product.setSku(generateSkuCode(product.getName(), product.getCategory().name()));
        product = productRepository.save(product);
        productEventPublisher.publishProductEvent(product, KafkaEventProducerAction.PRODUCT_CREATED);
        return product;
    }

    @Override
    public void addSupplyDetailsOnProduct(SupplyDetailsDTO dto, String sku) {

        if(dto.getLeadTimeDays() < 1){
            throw new IllegalArgumentException("Lead time days must be at least 1.");
        }
        if(dto.getShelfLifeDays() < 0){
            throw new IllegalArgumentException("Shelf life days must be at least 0.");
        }
        if(dto.getMinOrderQuantity() < 1){
            throw new IllegalArgumentException("Minimum order quantity must be at least 1.");
        }

        Product product = productRepository.findBySku(sku).orElseThrow(() -> new IllegalArgumentException("Product with SKU " + sku + " not found."));
        product.setSupplyDetails(productMapper.map(dto));
        product = productRepository.save(product);
        productEventPublisher.publishProductEvent(product, KafkaEventProducerAction.PRODUCT_SUPPLY_CREATED);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.map(products);
    }

    @Override
    public ProductResponseDTO getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found."));
        return productMapper.map(product);
    }

    @Override
    public ProductResponseDTO getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku).orElseThrow(() -> new IllegalArgumentException("Product with SKU " + sku + " not found."));
        return productMapper.map(product);
    }

    @Override
    public void updateProduct(UUID id, ProductRequestDTO dto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found."));
        BeanUtils.copyProperties(dto, existingProduct, "id", "sku", "supplyDetails");
        existingProduct.setCategory(ProductCategory.valueOf(dto.getCategory()));
        productRepository.save(existingProduct);
    }

    @Override
    public void updateProductSupply(UUID id, SupplyDetailsDTO dto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found."));
        BeanUtils.copyProperties(dto, existingProduct.getSupplyDetails());
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found."));
        existingProduct.setActive(false);
        productRepository.save(existingProduct);
    }

    private String generateSkuCode(String name, String category){
        String namePart = name.length() >= 3 ? name.substring(0, 3).toUpperCase() : name.toUpperCase();
        String categoryPart = category.length() >= 3 ? category.substring(0, 3).toUpperCase() : category.toUpperCase();
        long timestampPart = System.currentTimeMillis() % 10000; // Last 4 digits of current time in milliseconds
        return namePart + "-" + categoryPart + "-" + String.format("%04d", timestampPart);
    }
}
