package javax.faces.event;

import javax.faces.FacesException;

public class AbortProcessingException extends FacesException {
   private static final long serialVersionUID = 7726524187590697427L;

   public AbortProcessingException() {
   }

   public AbortProcessingException(String message) {
      super(message);
   }

   public AbortProcessingException(Throwable cause) {
      super(cause);
   }

   public AbortProcessingException(String message, Throwable cause) {
      super(message, cause);
   }
}
