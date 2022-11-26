package weblogic.connector.exception;

public class RAConfigurationException extends RAException {
   public RAConfigurationException() {
   }

   public RAConfigurationException(String message) {
      super(message);
   }

   public RAConfigurationException(String message, Throwable cause) {
      super(message, cause);
   }

   public RAConfigurationException(Throwable cause) {
      super(cause);
   }
}
