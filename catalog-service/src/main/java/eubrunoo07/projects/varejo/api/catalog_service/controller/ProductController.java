package eubrunoo07.projects.varejo.api.catalog_service.controller;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductResponseDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.SupplyDetailsDTO;
import eubrunoo07.projects.varejo.api.catalog_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/varejo/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody@Valid ProductRequestDTO dto){
        productService.createProduct(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/supply/{sku}")
    public ResponseEntity<Void> addSupplyDetailsOnProduct(@RequestBody@Valid SupplyDetailsDTO dto, @PathVariable String sku){
        productService.addSupplyDetailsOnProduct(dto, sku);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDTO> getProductBySku(@PathVariable String sku){
        return ResponseEntity.ok().body(productService.getProductBySku(sku));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable UUID id, @RequestBody@Valid ProductRequestDTO dto){
        productService.updateProduct(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/supply/{id}")
    public ResponseEntity<Void> updateProductSupply(@PathVariable UUID id, @RequestBody@Valid SupplyDetailsDTO dto){
        productService.updateProductSupply(id, dto);
        return ResponseEntity.ok().build();
    }
}
