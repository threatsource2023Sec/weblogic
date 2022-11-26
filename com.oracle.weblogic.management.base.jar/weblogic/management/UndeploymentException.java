package weblogic.management;

public final class UndeploymentException extends ManagementException {
   private static final long serialVersionUID = 4423808334005698365L;

   public UndeploymentException(String message, Throwable t) {
      super(message, t);
   }

   public UndeploymentException(Throwable t) {
      this("", t);
   }

   public UndeploymentException(String message) {
      this(message, (Throwable)null);
   }
}
