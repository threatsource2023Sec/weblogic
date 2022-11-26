package weblogic.connector.external;

public class ResourceAdapterNotFoundException extends EndpointActivationException {
   public ResourceAdapterNotFoundException(String message) {
      super(message, true);
   }
}
