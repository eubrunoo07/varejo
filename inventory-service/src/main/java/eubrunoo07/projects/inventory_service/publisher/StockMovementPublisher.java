package eubrunoo07.projects.inventory_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eubrunoo07.projects.inventory_service.enums.KafkaEventProducerAction;
import eubrunoo07.projects.inventory_service.mapper.StockMapper;
import eubrunoo07.projects.inventory_service.model.StockMovement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockMovementPublisher {
    @Value("${varejo.config.kafka.topics.inventory-events}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final StockMapper stockMapper;
    private final ObjectMapper objectMapper;

    public StockMovementPublisher(KafkaTemplate<String, String> kafkaTemplate, StockMapper stockMapper, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.stockMapper = stockMapper;
        this.objectMapper = objectMapper;
    }

    public void publishInventoryEvent(StockMovement stockMovement, KafkaEventProducerAction action){
        try{
            var representation = stockMapper.mapToRepresentation(stockMovement, action);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topic, json);
            log.info("Published inventory event to topic {}: {}", topic, json);
        } catch (JsonProcessingException e) {
            log.error("Failed to publish product event for product ID {}: {}", stockMovement.getId(), e.getMessage());
        }
    }
}
