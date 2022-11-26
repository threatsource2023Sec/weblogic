package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class UnknownHostException extends RemoteException {
   private static final long serialVersionUID = -5788722460866507765L;

   public UnknownHostException() {
   }

   public UnknownHostException(String s) {
      super(s);
   }

   public UnknownHostException(String s, Throwable e) {
      super(s, e);
   }
}
