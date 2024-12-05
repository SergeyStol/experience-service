package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.industry.IndustryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-12-04
 */

@Service
@RequiredArgsConstructor
public class ExperienceAggregatorService {
   private final Map<UUID, ExperienceAggregatorModel> preparingExp = new HashMap<>();
   private final Set<UUID> preparingExperiences = new HashSet<>();
   private final ExperienceMapper experienceMapper;

   private synchronized ExperienceAggregatorModel getOrAddExperienceAggregatorModel(UUID requestUUID) {
      ExperienceAggregatorModel experienceAggregatorModel = preparingExp.get(requestUUID);
      if (experienceAggregatorModel == null) {
         experienceAggregatorModel = new ExperienceAggregatorModel();
         preparingExp.put(requestUUID, experienceAggregatorModel);
      }
      return experienceAggregatorModel;
   }

   public boolean notFullyEnriched(UUID requestUUID) {
      ExperienceAggregatorModel experienceAggregatorModel = preparingExp.get(requestUUID);
      if (experienceAggregatorModel == null) {
         return true;
      }
      return experienceAggregatorModel.getCounter().get() != 0;
   }

   public void enrichWith(IndustryDto industryDto, UUID requestUUID) {
      var experienceAggregatorModel = getOrAddExperienceAggregatorModel(requestUUID);
      experienceAggregatorModel.setIndustryDto(industryDto);
   }

   public void enrichWith(Experience experience, UUID requestUUID) {
      var experienceAggregatorModel = getOrAddExperienceAggregatorModel(requestUUID);
      experienceAggregatorModel.setExperience(experience);
   }

   public void addPreparingRequest(UUID uuid) {
      preparingExperiences.add(uuid);
   }

   public void removePreparingRequest(UUID uuid) {
      preparingExperiences.remove(uuid);
   }

   public boolean isRequestComposer(UUID requestUUID) {
      return preparingExp.containsKey(requestUUID);
   }

   public boolean containsRequest(UUID requestUUID) {
      return preparingExp.containsKey(requestUUID)
             || preparingExperiences.contains(requestUUID);
   }

   public ExperienceAggregatorModel getExperienceAggregatorModel(UUID uuid) {
      return preparingExp.get(uuid);
   }

   public void setExperienceAggregatorModel(UUID uuid, ExperienceAggregatorModel experienceAggregatorModel) {
      preparingExp.put(uuid, experienceAggregatorModel);
   }

   public ExperienceDto getExperienceDto(UUID requestUUID) {
      return experienceMapper.toDto(preparingExp.get(requestUUID));
   }

   @Scheduled(cron = "*/10 * * * * *")
   void clean() {
      preparingExp.entrySet().removeIf(e -> {
         if (!e.getValue().isAlive()) {
            preparingExperiences.remove(e.getKey());
            return true;
         }
         return false;
      });
   }
}
