package by.javaguru.experienceservice.infrastructure.kafka;

import by.javaguru.experienceservice.features.experience.ExperienceAggregatorModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @author Sergey Stol
 * 2024-12-05
 */
@Slf4j
public class ExperienceAggregatorModelSerializer implements Serializer<ExperienceAggregatorModel> {
   private final ObjectMapper objectMapper = new ObjectMapper();

   public ExperienceAggregatorModelSerializer() {
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   }

   @Override
   public byte[] serialize(String topic, ExperienceAggregatorModel experienceAggregatorModel) {
      try {
         return experienceAggregatorModel != null ? objectMapper.writeValueAsString(experienceAggregatorModel).getBytes(StandardCharsets.UTF_8) : null;
      } catch (JsonProcessingException e) {
         log.error("Can't serialize experienceAggregatorModel={}. Reason: {}",
           experienceAggregatorModel, e.getMessage());
         throw new RuntimeException(e);
      }
   }

}
