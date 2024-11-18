package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.duties.NewDutyDto;
import by.javaguru.experienceservice.features.industry.IndustryDto;

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
  IndustryDto industry,
  String company,
  String position,
  String achievements,
  String link,
  Set<NewDutyDto> duties
) {}
