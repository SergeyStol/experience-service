package by.javaguru.experienceservice.features.industry;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.ServiceUnavailableException;
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

   /**
    * Adds a new industry.
    *
    * @param industryDto the IndustryDto object containing industry details to be added
    * @return the IndustryDto object containing the saved industry details
    * @throws InternalServerErrorException if the industry service is unavailable
    */
   public IndustryDto addIndustry(IndustryDto industryDto) {
      try {
         return industryClient.saveIndustry(industryDto);
      } catch (ServiceUnavailableException e) {
         throw new InternalServerErrorException("Something went wrong. Please, try again.");
      }
   }

}
