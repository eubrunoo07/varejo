package eubrunoo07.projects.varejo.api.catalog_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eubrunoo07.projects.varejo.api.catalog_service.enums.KafkaEventProducerAction;
import eubrunoo07.projects.varejo.api.catalog_service.mapper.ProductMapper;
import eubrunoo07.projects.varejo.api.catalog_service.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductEventPublisher {

    @Value("${varejo.config.kafka.topics.product-events}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;

    public ProductEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ProductMapper productMapper, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.productMapper = productMapper;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, KafkaEventProducerAction action){
        try{
            var representation = productMapper.mapToRepresentation(product, action);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topic, json);
            log.info("Published product event to topic {}: {}", topic, json);
        } catch (JsonProcessingException e) {
            log.error("Failed to publish product event for product ID {}: {}", product.getId(), e.getMessage());
        }
    }
}
