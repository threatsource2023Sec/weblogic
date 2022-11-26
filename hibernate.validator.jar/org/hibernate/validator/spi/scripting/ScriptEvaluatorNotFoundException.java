package org.hibernate.validator.spi.scripting;

import javax.validation.ValidationException;
import org.hibernate.validator.Incubating;

@Incubating
public class ScriptEvaluatorNotFoundException extends ValidationException {
   public ScriptEvaluatorNotFoundException() {
   }

   public ScriptEvaluatorNotFoundException(String message) {
      super(message);
   }

   public ScriptEvaluatorNotFoundException(Throwable cause) {
      super(cause);
   }

   public ScriptEvaluatorNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
