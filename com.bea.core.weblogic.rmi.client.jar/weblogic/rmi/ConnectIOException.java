package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class ConnectIOException extends RemoteException {
   private static final long serialVersionUID = 3687024189427270031L;

   public ConnectIOException() {
   }

   public ConnectIOException(String string) {
      super(string);
   }

   public ConnectIOException(String string, Throwable e) {
      super(string, e);
   }
}
