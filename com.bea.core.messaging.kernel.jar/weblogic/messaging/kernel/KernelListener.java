package weblogic.messaging.kernel;

public interface KernelListener {
   void onCompletion(KernelRequest var1, Object var2);

   void onException(KernelRequest var1, Throwable var2);
}
