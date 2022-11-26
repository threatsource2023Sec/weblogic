package weblogic.management;

public class ResourceGroupLifecycleException extends Exception {
   public ResourceGroupLifecycleException() {
   }

   public ResourceGroupLifecycleException(String msg) {
      super(msg);
   }

   public ResourceGroupLifecycleException(Throwable th) {
      super(th);
   }

   public ResourceGroupLifecycleException(String msg, Throwable th) {
      super(msg, th);
   }
}
