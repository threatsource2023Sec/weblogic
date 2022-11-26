package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class UnmarshalException extends RemoteException {
   private static final long serialVersionUID = -5410103554486595708L;

   public UnmarshalException() {
   }

   public UnmarshalException(String string) {
      super(string);
   }

   public UnmarshalException(String s, Throwable e) {
      super(s, e);
   }
}
