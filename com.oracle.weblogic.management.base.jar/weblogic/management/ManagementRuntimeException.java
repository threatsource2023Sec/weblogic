package weblogic.management;

public class ManagementRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 6968866866835556481L;

   public ManagementRuntimeException() {
   }

   public ManagementRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public ManagementRuntimeException(String message) {
      super(message);
   }

   public ManagementRuntimeException(Throwable t) {
      super(t);
   }
}
