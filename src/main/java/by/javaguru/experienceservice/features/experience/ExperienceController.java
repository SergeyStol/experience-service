package by.javaguru.experienceservice.features.experience;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/v1.0/experiences/")
public class ExperienceController {

   private final ExperienceService service;

   // 400 - Bad request
   // 404 - Not found
   @GetMapping("/{uuid}")
   @ResponseStatus(HttpStatus.OK) // 200 OK
   public ExperienceDto getExperience(@PathVariable UUID uuid) {
      return service.getExperienceById(uuid);
   }

   // 400 - Bad request
   // 409 - Not found
   @PostMapping
   @ResponseStatus(HttpStatus.CREATED) // 201 CREATED
   public ExperienceDto addExperience(@RequestBody NewExperienceDto newExperienceDto) {
      return service.addExperience(newExperienceDto);
   }

   @DeleteMapping("/{uuid}")
   @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content
   public void deleteVoid(@PathVariable UUID uuid) {
      service.removeExperience(uuid);
   }
}
