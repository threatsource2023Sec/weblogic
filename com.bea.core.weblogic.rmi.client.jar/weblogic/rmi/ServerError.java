package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class ServerError extends RemoteException {
   private static final long serialVersionUID = -5319057581686617704L;

   public ServerError(String s, Throwable e) {
      super(s, e);
   }
}
