package weblogic.ejb;

public final class NonDestructiveRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 7729973007125942760L;

   public NonDestructiveRuntimeException() {
   }

   public NonDestructiveRuntimeException(String msg) {
      super(msg);
   }

   public NonDestructiveRuntimeException(Throwable th) {
      super(th);
   }

   public NonDestructiveRuntimeException(String msg, Throwable th) {
      super(msg, th);
   }
}
