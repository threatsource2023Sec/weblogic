package weblogic.deployment;

public class ServiceRefProcessorException extends Exception {
   private static final long serialVersionUID = -382860002880769139L;

   public ServiceRefProcessorException() {
   }

   public ServiceRefProcessorException(String message) {
      super(message);
   }

   public ServiceRefProcessorException(String message, Throwable cause) {
      super(message, cause);
   }

   public ServiceRefProcessorException(Throwable cause) {
      super(cause);
   }
}
