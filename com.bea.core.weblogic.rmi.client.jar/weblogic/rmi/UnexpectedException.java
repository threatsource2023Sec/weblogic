package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class UnexpectedException extends RemoteException {
   private static final long serialVersionUID = 877170394816497579L;

   public UnexpectedException() {
   }

   public UnexpectedException(String s) {
      super(s);
   }

   public UnexpectedException(String s, Throwable e) {
      super(s, e);
   }
}
