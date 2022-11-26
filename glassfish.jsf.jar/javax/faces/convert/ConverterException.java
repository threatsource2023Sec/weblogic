package javax.faces.convert;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

public class ConverterException extends FacesException {
   private static final long serialVersionUID = 7371795075636746246L;
   private FacesMessage facesMessage;

   public ConverterException() {
   }

   public ConverterException(String message) {
      super(message);
   }

   public ConverterException(Throwable cause) {
      super(cause);
   }

   public ConverterException(String message, Throwable cause) {
      super(message, cause);
   }

   public ConverterException(FacesMessage message) {
      super(message.getSummary());
      this.facesMessage = message;
   }

   public ConverterException(FacesMessage message, Throwable cause) {
      super(message.getSummary(), cause);
      this.facesMessage = message;
   }

   public FacesMessage getFacesMessage() {
      return this.facesMessage;
   }
}
