package weblogic.management.runtime;

public interface ConcurrentManagedObjectsRuntimeMBean extends RuntimeMBean {
   int getRunningLongRunningRequests();

   int getRunningThreadsCount();

   long getRejectedLongRunningRequests();

   long getRejectedNewThreadRequests();

   boolean addManagedExecutorServiceRuntime(ManagedExecutorServiceRuntimeMBean var1);

   ManagedExecutorServiceRuntimeMBean[] getManagedExecutorServiceRuntimes();

   boolean addManagedScheduledExecutorServiceRuntime(ManagedScheduledExecutorServiceRuntimeMBean var1);

   ManagedScheduledExecutorServiceRuntimeMBean[] getManagedScheduledExecutorServiceRuntimes();

   boolean addManagedThreadFactoryRuntime(ManagedThreadFactoryRuntimeMBean var1);

   ManagedThreadFactoryRuntimeMBean[] getManagedThreadFactoryRuntimes();
}
