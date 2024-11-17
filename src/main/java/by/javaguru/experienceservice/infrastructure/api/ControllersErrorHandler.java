package by.javaguru.experienceservice.infrastructure.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Sergey Stol
 * 2024-11-15
 */
@ControllerAdvice
public class ControllersErrorHandler extends ResponseEntityExceptionHandler {

   // 404
   @ExceptionHandler(NotFoundException.class)
   public ResponseEntity<Object> notFoundException(NotFoundException notFoundException) {
      return new ResponseEntity<>(
        notFoundException.getMessage(),
        HttpStatus.NOT_FOUND); // 404
   }
}
