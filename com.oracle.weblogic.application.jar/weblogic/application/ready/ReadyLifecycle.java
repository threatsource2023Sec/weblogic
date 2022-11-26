package weblogic.application.ready;

public interface ReadyLifecycle extends Ready {
   void register();

   void register(String var1);

   void unregister();

   void unregister(String var1);

   int getReadyStatus();

   int getGlobalRuntimeOnlyStatus();

   int getPartitionOnlyStatus();
}
