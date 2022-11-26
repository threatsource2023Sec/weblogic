package weblogic.connector.external;

import javax.resource.ResourceException;

public class EndpointActivationException extends ResourceException {
   private boolean changeable;

   public EndpointActivationException(String message, boolean changeable) {
      super(message);
      this.changeable = changeable;
   }

   public EndpointActivationException(String message, Throwable cause, boolean changeable) {
      super(message, cause);
      this.changeable = changeable;
   }

   public boolean isChangeable() {
      return this.changeable;
   }
}
