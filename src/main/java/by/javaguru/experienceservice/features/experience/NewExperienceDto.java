package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.duties.Duties;
import by.javaguru.experienceservice.features.duties.NewDutyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Sergey Stol
 * 2024-11-16
 */
public record NewExperienceDto(
  LocalDate periodFrom,
  LocalDate periodTo,
  LocalDate presentTime,
  String industry,
  String company,
  String position,
  String achievements,
  String link,
  Set<NewDutyDto> duties
) {}
