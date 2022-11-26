package weblogic.connector.configuration.validation;

import java.util.Set;
import javax.validation.ConstraintViolationException;

public class WLSConstraintViolationException extends ConstraintViolationException {
   private static final long serialVersionUID = -7714650123409297417L;
   private final transient Set constraintViolations;

   public WLSConstraintViolationException(String message, Set constraintViolations) {
      super(message, (Set)null);
      this.constraintViolations = constraintViolations;
   }

   public WLSConstraintViolationException(Set constraintViolations) {
      super((Set)null);
      this.constraintViolations = constraintViolations;
   }

   public Set getConstraintViolations() {
      return this.constraintViolations;
   }
}
