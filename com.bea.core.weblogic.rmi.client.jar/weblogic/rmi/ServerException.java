package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class ServerException extends RemoteException {
   private static final long serialVersionUID = -4172747097307177996L;

   public ServerException() {
   }

   public ServerException(String s) {
      super(s);
   }

   public ServerException(String s, Throwable e) {
      super(s, e);
   }
}
