package by.javaguru.experienceservice.features.industry;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.ws.rs.ServiceUnavailableException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "industry-service", path = "/v1.0/industries/")
public interface IndustryClient {
   @CircuitBreaker(name = "get-industry-by-id", fallbackMethod = "getIndustryByIdFallback")
   @GetMapping("/{id}")
   IndustryDto getIndustryById(@PathVariable Long id);

   @Retry(name = "save-industry", fallbackMethod = "saveIndustryFallback")
   @PostMapping
   IndustryDto saveIndustry(@RequestBody IndustryDto industryDto);

   default IndustryDto getIndustryByIdFallback(Long id, Throwable e) {
      return new IndustryDto(-1L, "Broken reference");
   }

   default IndustryDto saveIndustryFallback(IndustryDto industryDto, Throwable ex) {
      throw new ServiceUnavailableException("industry-service is unavailable");
   }
}
