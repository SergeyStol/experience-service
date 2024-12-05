package by.javaguru.experienceservice.infrastructure.kafka;

import by.javaguru.experienceservice.features.industry.IndustryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author Sergey Stol
 * 2024-12-05
 */

@Slf4j
public class IndustryDtoDeserializer implements Deserializer<IndustryDto> {
   private final ObjectMapper objectMapper = new ObjectMapper();

   public IndustryDtoDeserializer() {
      objectMapper.registerModule(new JavaTimeModule());
   }

   @Override
   public IndustryDto deserialize(String s, byte[] data) {
      try {
         return objectMapper.readValue(data, IndustryDto.class);
      } catch (Exception e) {
         log.error("Can't deserialize bytes to IndustryDto. Reason: {}", e.getMessage());
         throw new RuntimeException("Error deserializing IndustryDto", e);
      }
   }
}
