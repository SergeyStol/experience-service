package by.javaguru.experienceservice.tablesFiller;

import by.javaguru.experienceservice.features.duties.Duty;
import by.javaguru.experienceservice.features.duties.DutyDto;
import by.javaguru.experienceservice.features.duties.NewDutyDto;
import by.javaguru.experienceservice.features.experience.Experience;
import by.javaguru.experienceservice.features.experience.ExperienceRepository;
import by.javaguru.experienceservice.features.experience.ExperienceService;
import by.javaguru.experienceservice.features.experience.NewExperienceDto;
import by.javaguru.experienceservice.features.industry.IndustryDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-17
 */
@Component
@Profile("with-filler")
@AllArgsConstructor
public class TableFiller {

   private final ExperienceRepository repo;

   @PostConstruct
   void postConstruct() {
      Duty duty1 = new Duty(null, UUID.randomUUID(), "Developing software", null);
      Duty duty2 = new Duty(null, UUID.randomUUID(), "Code reviews", null);
      Experience experience = new Experience(
        null,
      UUID.fromString("476adb05-33b9-407b-8fd4-0112dd8d9c6b"),
      LocalDate.of(2020, 1, 1),
      LocalDate.of(2023, 1, 1),
      LocalDate.now(),
      1L,
      "Example Corp",
      "Senior Developer",
      "Developed several key features",
      "https://example.com",
      Set.of(duty1, duty2));
      duty1.setExperience(experience);
      duty2.setExperience(experience);
      repo.save(experience);
   }
}
