package javax.faces.validator;

import java.util.Collection;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

public class ValidatorException extends FacesException {
   private static final long serialVersionUID = 6459492016772012827L;
   private FacesMessage message;
   private Collection messages;

   public ValidatorException(FacesMessage message) {
      super(message.getSummary());
      this.message = message;
   }

   public ValidatorException(Collection messages) {
      this.messages = messages;
   }

   public ValidatorException(FacesMessage message, Throwable cause) {
      super(message.getSummary(), cause);
      this.message = message;
   }

   public ValidatorException(Collection messages, Throwable cause) {
      super(messages.isEmpty() ? "" : ((FacesMessage)messages.iterator().next()).getSummary(), cause);
      this.messages = messages;
   }

   public FacesMessage getFacesMessage() {
      FacesMessage result = this.message;
      if (null == result && null != this.messages && !this.messages.isEmpty()) {
         result = (FacesMessage)this.messages.iterator().next();
      }

      return result;
   }

   public Collection getFacesMessages() {
      return this.messages;
   }
}
