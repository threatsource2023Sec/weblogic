package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class ConnectException extends RemoteException {
   private static final long serialVersionUID = -6142238183593381015L;

   public ConnectException() {
   }

   public ConnectException(String string) {
      super(string);
   }

   public ConnectException(String string, Throwable e) {
      super(string, e);
   }
}
