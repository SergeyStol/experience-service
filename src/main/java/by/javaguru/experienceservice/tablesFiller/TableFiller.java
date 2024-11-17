package by.javaguru.experienceservice.tablesFiller;

import by.javaguru.experienceservice.features.duties.NewDutyDto;
import by.javaguru.experienceservice.features.experience.ExperienceService;
import by.javaguru.experienceservice.features.experience.NewExperienceDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Sergey Stol
 * 2024-11-17
 */
@Component
@Profile("with-filler")
@AllArgsConstructor
public class TableFiller {

   private final ExperienceService experienceService;

   @PostConstruct
   void postConstruct() {
//      NewExperienceDto experience = new NewExperienceDto();
//      experience.setPeriodFrom(LocalDate.of(2020, 1, 1));
//      experience.setPeriodTo(LocalDate.of(2023, 1, 1));
//      experience.setPresentTime(LocalDate.now());
//      experience.setIndustry("Software Development");
//      experience.setCompany("Example Corp");
//      experience.setPosition("Senior Developer");
//      experience.setAchievements("Developed several key features");
//      experience.setLink("https://example.com");
//      experience.setDuties(Set.of(new NewDutyDto("Developing software"), new NewDutyDto("Code reviews")));
//      experienceService.addExperience(experience);
   }
}
