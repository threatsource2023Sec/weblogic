package weblogic.application;

public class CannotUpdateException extends ModuleException {
   static final long serialVersionUID = -1266358938626281615L;

   public CannotUpdateException(String msg) {
      super(msg);
   }

   public CannotUpdateException(Throwable th) {
      super(th);
   }

   public CannotUpdateException(String msg, Throwable th) {
      super(msg, th);
   }
}
