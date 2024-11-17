package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.duties.DutyDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-16
 */
public record ExperienceDto(
  UUID uuid,
  LocalDate periodFrom,
  LocalDate periodTo,
  LocalDate presentTime,
  String industry,
  String company,
  String position,
  String achievements,
  String link,
  Set<DutyDto> duties
) {}