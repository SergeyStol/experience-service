package by.javaguru.experienceservice.features.experience;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2.0/experiences/")
public class ExperienceControllerV20Async {
   private final ExperienceServiceV20Async service;

   @PostMapping("/{uuid}/prepare")
   @ResponseStatus(HttpStatus.ACCEPTED) // 202 OK
   public void prepareExperience(@PathVariable UUID uuid, HttpServletResponse httpServletResponse) {
      UUID requestUuid = service.prepareExperienceData(uuid);
      httpServletResponse.addHeader("Location", requestUuid.toString());
   }

   @GetMapping("/{requestUUID}")
   public ResponseEntity<ExperienceDtoAsync> getExperience(@PathVariable UUID requestUUID) {
      ExperienceDto experience = service.getExperience(requestUUID);
      if (experience == null) {
         return ResponseEntity
           .accepted() // 202
           .body(new ExperienceDtoAsync(requestUUID, "IN_PROGRESS"));
      }
      return ResponseEntity
        .ok() // 200
        .body(new ExperienceDtoAsync(requestUUID, "DONE", experience));
   }
}
