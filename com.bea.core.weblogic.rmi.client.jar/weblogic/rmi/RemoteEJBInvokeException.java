package weblogic.rmi;

public class RemoteEJBInvokeException extends java.rmi.RemoteException {
   private static final long serialVersionUID = 1642386207949767201L;

   public RemoteEJBInvokeException() {
   }

   public RemoteEJBInvokeException(String s) {
      super(s);
   }

   public RemoteEJBInvokeException(String s, Throwable th) {
      super(s, th);
   }
}
