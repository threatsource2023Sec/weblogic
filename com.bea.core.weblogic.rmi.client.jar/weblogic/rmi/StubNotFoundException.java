package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class StubNotFoundException extends RemoteException {
   private static final long serialVersionUID = 913924846911122536L;

   public StubNotFoundException() {
   }

   public StubNotFoundException(String s) {
      super(s);
   }

   public StubNotFoundException(String s, Throwable e) {
      super(s, e);
   }
}
