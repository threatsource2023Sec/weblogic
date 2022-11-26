package javax.faces.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

public class ValidatorException extends FacesException {
   private FacesMessage message;

   public ValidatorException(FacesMessage message) {
      super(message.getSummary());
      this.message = message;
   }

   public ValidatorException(FacesMessage message, Throwable cause) {
      super(message.getSummary(), cause);
      this.message = message;
   }

   public FacesMessage getFacesMessage() {
      return this.message;
   }
}
