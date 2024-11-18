package by.javaguru.experienceservice.features.industry;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Sergey Stol
 * 2024-11-17
 */
@FeignClient(name = "industry-service", url = "http://localhost:8081/v1.0/industries")
public interface IndustryClient {
   @GetMapping("/{id}")
   IndustryDto getIndustryById(@PathVariable Long id);

   @PostMapping
   IndustryDto saveIndustry(@RequestBody IndustryDto industryDto);
}
