package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class NoSuchObjectException extends RemoteException {
   private static final long serialVersionUID = 1629979988050259745L;

   public NoSuchObjectException() {
   }

   public NoSuchObjectException(String string) {
      super(string);
   }
}
