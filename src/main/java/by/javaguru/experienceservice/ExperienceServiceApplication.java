package by.javaguru.experienceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ExperienceServiceApplication {

   public static void main(String[] args) {
      SpringApplication.run(ExperienceServiceApplication.class, args);
   }
}
