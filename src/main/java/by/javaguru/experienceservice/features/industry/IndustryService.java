package by.javaguru.experienceservice.features.industry;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergey Stol
 * 2024-11-17
 */
@Service
@AllArgsConstructor
public class IndustryService {

   private final IndustryClient industryClient;

   public IndustryDto getIndustry(Long id) {
      return industryClient.getIndustryById(id);
   }

   public IndustryDto addIndustry(IndustryDto industryDto) {
      return industryClient.saveIndustry(industryDto);
   }

}
