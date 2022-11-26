package javax.faces.component.search;

import javax.faces.FacesException;

public class ComponentNotFoundException extends FacesException {
   private static final long serialVersionUID = -8962632721771880921L;

   public ComponentNotFoundException() {
   }

   public ComponentNotFoundException(String message) {
      super(message);
   }

   public ComponentNotFoundException(Throwable cause) {
      super(cause);
   }

   public ComponentNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
