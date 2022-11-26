package javax.resource.spi;

import java.beans.PropertyDescriptor;
import javax.resource.ResourceException;

public class InvalidPropertyException extends ResourceException {
   private PropertyDescriptor[] invalidProperties;

   public InvalidPropertyException() {
   }

   public InvalidPropertyException(String message) {
      super(message);
   }

   public InvalidPropertyException(Throwable cause) {
      super(cause);
   }

   public InvalidPropertyException(String message, Throwable cause) {
      super(message, cause);
   }

   public InvalidPropertyException(String message, String errorCode) {
      super(message, errorCode);
   }

   public void setInvalidPropertyDescriptors(PropertyDescriptor[] invalidProperties) {
      this.invalidProperties = invalidProperties;
   }

   public PropertyDescriptor[] getInvalidPropertyDescriptors() {
      return this.invalidProperties;
   }
}
