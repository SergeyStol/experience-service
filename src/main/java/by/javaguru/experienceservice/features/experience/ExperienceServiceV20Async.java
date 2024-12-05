package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.industry.IndustryDto;
import by.javaguru.experienceservice.infrastructure.api.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-15
 */
@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class ExperienceServiceV20Async {

   private final ApplicationEventPublisher eventPublisher;
   private final KafkaTemplate<UUID, String> kafkaTemplateUUID;
   private final KafkaTemplate<UUID, Long> kafkaTemplateUUIDLong;
   private final KafkaTemplate<UUID, ExperienceAggregatorModel> kafkaTemplateExperienceAggregatorModel;
   private final ExperienceAggregatorService aggregator;
   private final ExperienceRepository repo;

   public UUID prepareExperienceData(UUID experienceUUID) {
      UUID queryUUID = UUID.randomUUID();
      eventPublisher.publishEvent(new PrepareExperienceEvent(this, queryUUID, experienceUUID));
      return queryUUID;
   }

   @Async
   @EventListener
   void prepareExperienceEventHandler(PrepareExperienceEvent event) {
      UUID requestUUID = event.getRequestUuid();
      kafkaTemplateUUID.send("PREPARING_EXPERIENCE_NOTIFICATION", requestUUID, "");
      log.info("Sent PREPARING_EXPERIENCE_NOTIFICATION with requestUUID={}", requestUUID);
      Experience experience = repo.getExperienceByUuid(event.getExperienceUuid());
      aggregator.addPreparingRequest(requestUUID);
      aggregator.enrichWith(experience, requestUUID);
      kafkaTemplateUUIDLong.send("GET_INDUSTRY_REQUEST", requestUUID, experience.getIndustryId());
      log.info("Sent GET_INDUSTRY_REQUEST with requestUUID={} and industryId={}",
        requestUUID, experience.getIndustryId());
      while (aggregator.notFullyEnriched(requestUUID)) {
         try {
            Thread.sleep(300);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }
      ExperienceAggregatorModel experienceAggregatorModel = aggregator.getExperienceAggregatorModel(requestUUID);
      kafkaTemplateExperienceAggregatorModel.send(
        "FULLY_ENRICHED_EXPERIENCE_NOTIFICATION",
        requestUUID,
        experienceAggregatorModel
      );
      log.info("Sent FULLY_ENRICHED_EXPERIENCE_NOTIFICATION with requestUUID={} and experienceAggregatorModel={}",
        requestUUID, experienceAggregatorModel);
   }

   @KafkaListener(
     topics = "GET_INDUSTRY_RESPONSE",
     containerFactory = "uuidIndustryDtoKafkaListenerContainerFactory"
   )
   public void getIndustryResponseListener(ConsumerRecord<UUID, IndustryDto> response) {
      UUID requestUUID = response.key();
      IndustryDto industryDto = response.value();
      log.info("Receive GET_INDUSTRY_RESPONSE with requestUUID={} and IndustryDto={}", requestUUID, industryDto);

      if (!aggregator.isRequestComposer(requestUUID)) {
         return;
      }
      aggregator.enrichWith(industryDto, requestUUID);
   }

   @KafkaListener(
     topics = "FULLY_ENRICHED_EXPERIENCE_NOTIFICATION",
     containerFactory = "uuidExperienceAggregatorModelKafkaListenerContainerFactory"
   )
   public void fullyEnrichedExperienceNotificationListener(ConsumerRecord<UUID, ExperienceAggregatorModel> response) {
      UUID requestUUID = response.key();
      ExperienceAggregatorModel experienceAggregatorModel = response.value();
      log.info("Receive FULLY_ENRICHED_EXPERIENCE_NOTIFICATION with requestUUID={} and experienceAggregatorModel={}",
        requestUUID, experienceAggregatorModel);
      aggregator.setExperienceAggregatorModel(requestUUID, experienceAggregatorModel);
      aggregator.removePreparingRequest(requestUUID);
   }

   @KafkaListener(
     topics = "PREPARING_EXPERIENCE_NOTIFICATION",
     containerFactory = "uuidStringKafkaListenerContainerFactory"
   )
   public void preparingExperienceListener(ConsumerRecord<UUID, String> response) {
      UUID requestUUID = response.key();
      log.info("Receive PREPARING_EXPERIENCE_NOTIFICATION with requestUUID={}", requestUUID);
      aggregator.addPreparingRequest(requestUUID);
   }

   public ExperienceDto getExperience(UUID requestUUID) {
      if (!aggregator.containsRequest(requestUUID)) {
         throw new NotFoundException("No request with requestUUID=" + requestUUID);
      }
      if (aggregator.notFullyEnriched(requestUUID)) {
         return null;
      }
      return aggregator.getExperienceDto(requestUUID);
   }
}
