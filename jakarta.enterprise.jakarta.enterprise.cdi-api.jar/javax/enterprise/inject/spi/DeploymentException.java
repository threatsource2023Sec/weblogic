package javax.enterprise.inject.spi;

public class DeploymentException extends RuntimeException {
   private static final long serialVersionUID = 2604707587772339984L;

   public DeploymentException(String message, Throwable t) {
      super(message, t);
   }

   public DeploymentException(String message) {
      super(message);
   }

   public DeploymentException(Throwable t) {
      super(t);
   }
}
