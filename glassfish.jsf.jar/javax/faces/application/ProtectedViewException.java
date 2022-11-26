package javax.faces.application;

import javax.faces.FacesException;

public class ProtectedViewException extends FacesException {
   private static final long serialVersionUID = -1906819977415598769L;

   public ProtectedViewException(Throwable rootCause) {
      super(rootCause);
   }

   public ProtectedViewException(String message, Throwable cause) {
      super(message, cause);
   }

   public ProtectedViewException(String message) {
      super(message);
   }

   public ProtectedViewException() {
   }
}
