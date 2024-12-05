package by.javaguru.experienceservice.features.experience;

import by.javaguru.experienceservice.features.industry.IndustryDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sergey Stol
 * 2024-12-04
 */
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExperienceAggregatorModel {
   private Experience experience;
   private IndustryDto industryDto;
   private final AtomicInteger counter = new AtomicInteger(2);

   private final int ttl = 20;
   private final Instant createdDate = Instant.now();

   public void setExperience(Experience experience) {
      if (experience == this.experience || Objects.equals(experience, this.experience)) {
         return;
      }

      if (this.experience == null && experience != null) {
         counter.decrementAndGet();
      } else if (this.experience != null && experience == null) {
         counter.incrementAndGet();
      }
      this.experience = experience;
   }

   public void setIndustryDto(IndustryDto industryDto) {
      if (industryDto == this.industryDto) {
         return;
      }

      if (this.industryDto == null && industryDto != null) {
         counter.decrementAndGet();
      } else if (this.industryDto != null && industryDto == null) {
         counter.incrementAndGet();
      }
      this.industryDto = industryDto;
   }

   public boolean isAlive() {
      Instant now = Instant.now();
      Instant expirationTime = getCreatedDate().plus(getTtl(), ChronoUnit.SECONDS);
      return now.isBefore(expirationTime);
   }
}
