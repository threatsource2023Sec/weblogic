package weblogic.connector.external;

public class MissingPropertiesException extends EndpointActivationException {
   public MissingPropertiesException(String message) {
      super(message, false);
   }
}
