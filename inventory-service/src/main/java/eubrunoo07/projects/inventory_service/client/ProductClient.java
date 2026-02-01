package eubrunoo07.projects.inventory_service.client;

import eubrunoo07.projects.inventory_service.client.representation.ProductRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "products", url = "${varejo.config.clients.catalog-service.url}")
public interface ProductClient {

    @GetMapping("/{id}")
    public ResponseEntity<ProductRepresentation> getProductById(@PathVariable UUID id);

}
