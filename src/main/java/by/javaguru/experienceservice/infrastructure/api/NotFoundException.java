package by.javaguru.experienceservice.infrastructure.api;

/**
 * @author Sergey Stol
 * 2024-11-15
 */
public class NotFoundException extends RuntimeException {
   public NotFoundException(String msg) {
      super(msg);
   }

   public NotFoundException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
