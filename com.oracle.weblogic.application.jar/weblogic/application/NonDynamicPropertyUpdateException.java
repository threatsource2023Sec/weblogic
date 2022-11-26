package weblogic.application;

public class NonDynamicPropertyUpdateException extends CannotUpdateException {
   static final long serialVersionUID = -1266358938626281615L;

   public NonDynamicPropertyUpdateException(String msg) {
      super(msg);
   }

   public NonDynamicPropertyUpdateException(Throwable th) {
      super(th);
   }

   public NonDynamicPropertyUpdateException(String msg, Throwable th) {
      super(msg, th);
   }
}
