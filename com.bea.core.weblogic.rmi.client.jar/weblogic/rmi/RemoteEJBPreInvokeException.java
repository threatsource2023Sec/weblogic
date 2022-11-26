package weblogic.rmi;

public class RemoteEJBPreInvokeException extends RemoteEJBInvokeException {
   private static final long serialVersionUID = 8115377744484502617L;

   public RemoteEJBPreInvokeException() {
   }

   public RemoteEJBPreInvokeException(String s) {
      super(s);
   }

   public RemoteEJBPreInvokeException(String s, Throwable th) {
      super(s, th);
   }
}
