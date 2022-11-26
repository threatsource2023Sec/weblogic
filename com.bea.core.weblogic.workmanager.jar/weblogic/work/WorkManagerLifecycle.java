package weblogic.work;

public interface WorkManagerLifecycle {
   int RUNNING = 1;
   int WAIT_FOR_PENDING_TX = 2;
   int SHUTDOWN = 3;

   int getState();

   void start();

   boolean isShutdown();

   void shutdown(ShutdownCallback var1);

   void forceShutdown();

   String getName();
}
