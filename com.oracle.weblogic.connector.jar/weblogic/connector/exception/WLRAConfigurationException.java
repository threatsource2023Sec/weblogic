package weblogic.connector.exception;

public class WLRAConfigurationException extends RAException {
   public WLRAConfigurationException() {
   }

   public WLRAConfigurationException(String message) {
      super(message);
   }

   public WLRAConfigurationException(String message, Throwable cause) {
      super(message, cause);
   }

   public WLRAConfigurationException(Throwable cause) {
      super(cause);
   }
}
