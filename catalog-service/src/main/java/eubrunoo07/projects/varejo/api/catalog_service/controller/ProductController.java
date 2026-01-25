package eubrunoo07.projects.varejo.api.catalog_service.controller;

import eubrunoo07.projects.varejo.api.catalog_service.dto.ProductRequestDTO;
import eubrunoo07.projects.varejo.api.catalog_service.dto.SupplyDetailsDTO;
import eubrunoo07.projects.varejo.api.catalog_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
