package by.javaguru.experienceservice.features.experience;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-15
 */
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
   Experience getExperienceByUuid(UUID uuid);

   void removeByUuid(UUID uuid);
}
