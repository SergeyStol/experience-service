package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.industry.IndustryDto;
import by.javaguru.experienceservice.features.industry.IndustryService;
import by.javaguru.experienceservice.infrastructure.api.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-15
 */
@Service
@AllArgsConstructor
public class ExperienceService {

   private final ExperienceRepository repo;
   private final ExperienceMapper mapper;
   private final IndustryService industryService;

   public ExperienceDto getExperienceById(UUID uuid) {
      Experience experience = repo.getExperienceByUuid(uuid);
      if (experience == null) {
         throw new NotFoundException("Can't find experience with UUID=" + uuid);
      }
      IndustryDto industry = industryService.getIndustry(experience.getIndustryId());
      return mapper.toDto(experience, industry);
   }

   public ExperienceDto addExperience(NewExperienceDto newExperienceDto) {
      IndustryDto industryDto = newExperienceDto.industry();
      Long industryId = null;
      if (industryDto != null) {
         if (industryDto.id() == null) {
            industryDto = industryService.addIndustry(industryDto);
            // TODO: is it could be 409? (already exists - conflict)
            industryId = industryDto.id();
         } else {
            // TODO: check if exists by id
            industryId = industryDto.id();
         }
      }
      Experience experience = mapper.toEntity(newExperienceDto, industryId);
      experience.getDuties().forEach(duty -> duty.setExperience(experience));
      Experience savedExperience = repo.save(experience);
      return mapper.toDto(savedExperience, industryDto);
   }

   @Transactional
   public void removeExperience(UUID uuid) {
      repo.removeByUuid(uuid);
   }
}
