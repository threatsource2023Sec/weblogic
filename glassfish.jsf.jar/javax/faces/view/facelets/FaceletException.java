package javax.faces.view.facelets;

import javax.faces.FacesException;

public class FaceletException extends FacesException {
   private static final long serialVersionUID = 1L;

   public FaceletException() {
   }

   public FaceletException(String message) {
      super(message);
   }

   public FaceletException(Throwable cause) {
      super(cause);
   }

   public FaceletException(String message, Throwable cause) {
      super(message, cause);
   }
}
