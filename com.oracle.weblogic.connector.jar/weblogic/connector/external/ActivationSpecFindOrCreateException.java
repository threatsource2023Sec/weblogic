package weblogic.connector.external;

public class ActivationSpecFindOrCreateException extends EndpointActivationException {
   public ActivationSpecFindOrCreateException(String message) {
      super(message, false);
   }

   public ActivationSpecFindOrCreateException(String message, Throwable cause) {
      super(message, cause, false);
   }
}
