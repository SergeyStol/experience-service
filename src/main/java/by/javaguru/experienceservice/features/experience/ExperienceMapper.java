package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.industry.IndustryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
   @Mapping(target = "industryId", expression = "java(industryId)")
   Experience toEntity(NewExperienceDto newExperienceDto, Long industryId);
   @Mapping(target = "industry", expression = "java(industry)")
   ExperienceDto toDto(Experience experience, IndustryDto industry);

   @Mapping(target = ".", source = "experience") //
   @Mapping(source = "industryDto", target = "industry")
   ExperienceDto toDto(ExperienceAggregatorModel model);
}
