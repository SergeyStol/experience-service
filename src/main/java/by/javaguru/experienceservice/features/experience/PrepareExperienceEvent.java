package by.javaguru.experienceservice.features.experience;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-12-04
 */
@Getter
public class PrepareExperienceEvent extends ApplicationEvent {
   private final UUID requestUuid;
   private final UUID experienceUuid;

   public PrepareExperienceEvent(Object source, UUID requestUuid, UUID experienceUuid) {
      super(source);
      this.requestUuid = requestUuid;
      this.experienceUuid = experienceUuid;
   }
}
