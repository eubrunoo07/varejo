package eubrunoo07.projects.varejo.api.catalog_service.repository;

import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
