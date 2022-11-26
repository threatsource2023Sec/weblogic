package weblogic.work.concurrent;

import javax.naming.Context;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.spi.ContextProvider;

public interface ConcurrentManagedObject {
   int SHUTDOWN = 0;
   int STARTED = 1;
   int TYPE_JSR236_REGULAR = 0;
   int TYPE_PARTITION_LEVEL = 1;
   int TYPE_APPLICATION_LEVEL = 2;

   String getName();

   String getAppId();

   String getModuleId();

   String getPartitionName();

   ContextProvider getContextSetupProcessor();

   ClassLoader getPartitionClassLoader();

   boolean start();

   boolean stop();

   void terminate();

   boolean isStarted();

   String getJSR236Class();

   Object getOrCreateApplicationDelegator(ClassLoader var1, Context var2);

   Object getOrCreateJSR236Delegator(String var1, String var2, ClassLoader var3, Context var4);

   void updateContexts(String var1, String var2, ClassLoader var3);

   int getCMOType();

   WorkManager getWorkManager();

   void shutdownThreadsSubmittedBy(String var1);
}
