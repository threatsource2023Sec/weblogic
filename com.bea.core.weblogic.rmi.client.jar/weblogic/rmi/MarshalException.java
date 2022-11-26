package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class MarshalException extends RemoteException {
   private static final long serialVersionUID = 4099908369491071108L;

   public MarshalException() {
   }

   public MarshalException(String string) {
      super(string);
   }

   public MarshalException(String string, Throwable e) {
      super(string, e);
   }
}
