package weblogic.management.runtime;

public interface ComponentRuntimeMBean extends RuntimeMBean {
   int UNPREPARED = 0;
   int PREPARED = 1;
   int ACTIVATED = 2;
   int NEW = 3;
   int UPDATE_PENDING = 4;

   String getModuleId();

   int getDeploymentState();

   void setDeploymentState(int var1);

   boolean addWorkManagerRuntime(WorkManagerRuntimeMBean var1);

   WorkManagerRuntimeMBean[] getWorkManagerRuntimes();
}
