package by.javaguru.experienceservice.features.experience;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sergey Stol
 * 2024-11-16
 */
@Mapper(
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ExperienceMapper {
   Experience toEntity(NewExperienceDto newExperienceDto);

   ExperienceDto toDto(Experience experience);

}
