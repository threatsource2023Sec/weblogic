package javax.resource.spi.work;

public interface WorkContextLifecycleListener {
   void contextSetupComplete();

   void contextSetupFailed(String var1);
}
