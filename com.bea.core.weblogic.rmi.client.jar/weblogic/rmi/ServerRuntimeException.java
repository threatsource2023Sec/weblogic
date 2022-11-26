package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class ServerRuntimeException extends RemoteException {
   private static final long serialVersionUID = 2761688195912354662L;

   public ServerRuntimeException() {
   }

   public ServerRuntimeException(String s, Throwable e) {
      super(s, e);
   }
}
