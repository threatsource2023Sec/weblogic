package weblogic.management.runtime;

public interface ComponentConcurrentRuntimeMBean extends ComponentRuntimeMBean {
   boolean addManagedThreadFactoryRuntime(ManagedThreadFactoryRuntimeMBean var1);

   ManagedThreadFactoryRuntimeMBean[] getManagedThreadFactoryRuntimes();

   boolean addManagedExecutorServiceRuntime(ManagedExecutorServiceRuntimeMBean var1);

   ManagedExecutorServiceRuntimeMBean[] getManagedExecutorServiceRuntimes();

   boolean addManagedScheduledExecutorServiceRuntime(ManagedScheduledExecutorServiceRuntimeMBean var1);

   ManagedScheduledExecutorServiceRuntimeMBean[] getManagedScheduledExecutorServiceRuntimes();
}
