package by.javaguru.experienceservice.features.experience;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-16
 */
@Getter
@Setter
@NoArgsConstructor
public class ExperienceDtoAsync {
   private UUID requestUUID;
   private String requestStatus;
   private ExperienceDto payload;

   public ExperienceDtoAsync(UUID requestUUID, String requestStatus) {
      this.requestUUID = requestUUID;
      this.requestStatus = requestStatus;
   }

   public ExperienceDtoAsync(UUID requestUUID, String requestStatus, ExperienceDto payload) {
      this.requestUUID = requestUUID;
      this.requestStatus = requestStatus;
      this.payload = payload;
   }
}
