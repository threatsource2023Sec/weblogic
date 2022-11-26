package weblogic.work;

public interface WorkManager {
   int SELF_TUNING_TYPE = 1;
   int FIXED_THREAD_COUNT_TYPE = 2;

   String getName();

   String getApplicationName();

   String getModuleName();

   int getType();

   int getConfiguredThreadCount();

   void schedule(Runnable var1);

   boolean executeIfIdle(Runnable var1);

   boolean scheduleIfBusy(Runnable var1);

   int getQueueDepth();

   void setInternal();

   boolean isInternal();

   boolean isThreadOwner(Thread var1);
}
