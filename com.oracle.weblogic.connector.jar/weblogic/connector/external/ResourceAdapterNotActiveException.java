package weblogic.connector.external;

public class ResourceAdapterNotActiveException extends EndpointActivationException {
   public ResourceAdapterNotActiveException(String message) {
      super(message, true);
   }
}
