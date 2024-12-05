package by.javaguru.experienceservice.infrastructure.kafka;

import by.javaguru.experienceservice.features.experience.ExperienceAggregatorModel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author Sergey Stol
 * 2024-12-05
 */
@Slf4j
public class ExperienceAggregatorModelDeserializer implements Deserializer<ExperienceAggregatorModel> {
   private final ObjectMapper objectMapper = new ObjectMapper();

   public ExperienceAggregatorModelDeserializer() {
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   }

   @Override
   public ExperienceAggregatorModel deserialize(String s, byte[] data) {
      try {
         return objectMapper.readValue(data, ExperienceAggregatorModel.class);
      } catch (Exception e) {
         log.error("Can't deserialize bytes to experienceAggregatorModel. Reason: {}", e.getMessage());
         throw new RuntimeException("Error deserializing ExperienceAggregatorModel", e);
      }
   }
}
