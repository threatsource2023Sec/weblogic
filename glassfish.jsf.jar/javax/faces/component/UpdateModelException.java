package javax.faces.component;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

public class UpdateModelException extends FacesException {
   private static final long serialVersionUID = 6081145672680351218L;
   private FacesMessage facesMessage;

   public UpdateModelException(FacesMessage facesMessage, Throwable cause) {
      super(cause);
      this.facesMessage = facesMessage;
   }

   public FacesMessage getFacesMessage() {
      return this.facesMessage;
   }
}
