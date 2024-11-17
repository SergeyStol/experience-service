package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.infrastructure.api.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

   public ExperienceDto getExperienceById(UUID uuid) {
      Experience experience = repo.getExperienceByUuid(uuid);
      if (experience == null) {
         throw new NotFoundException("Can't find experience with UUID=" + uuid);
      }
      return mapper.toDto(experience);
   }

   public ExperienceDto addExperience(NewExperienceDto newExperienceDto) {
      Experience entity = mapper.toEntity(newExperienceDto);
      entity.getDuties().forEach(duty -> duty.setExperience(entity));
      Experience savedExperience = repo.save(entity);
      return mapper.toDto(savedExperience);
   }

   @Transactional
   public void removeExperience(UUID uuid) {
      repo.removeByUuid(uuid);
   }
}
