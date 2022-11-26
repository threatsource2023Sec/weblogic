package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class AccessException extends RemoteException {
   private static final long serialVersionUID = 8181477218149941450L;

   public AccessException() {
   }

   public AccessException(String string) {
      super(string);
   }

   public AccessException(String string, Throwable e) {
      super(string, e);
   }
}
