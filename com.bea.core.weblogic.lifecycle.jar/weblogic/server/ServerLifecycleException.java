package weblogic.server;

public class ServerLifecycleException extends Exception {
   public ServerLifecycleException() {
   }

   public ServerLifecycleException(String msg) {
      super(msg);
   }

   public ServerLifecycleException(Throwable th) {
      super(th);
   }

   public ServerLifecycleException(String msg, Throwable th) {
      super(msg, th);
   }
}
