package weblogic.work;

public interface WorkManagerService extends WorkManager, WorkManagerLifecycle {
   WorkManager getDelegate();

   void cleanup();

   void startRMIGracePeriod(WorkListener var1);

   void endRMIGracePeriod();

   public interface WorkListener {
      void preScheduleWork();

      void postScheduleWork();
   }
}
