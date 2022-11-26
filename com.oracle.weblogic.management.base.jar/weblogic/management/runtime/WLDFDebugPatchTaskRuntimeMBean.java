package weblogic.management.runtime;

public interface WLDFDebugPatchTaskRuntimeMBean extends TaskRuntimeMBean {
   String SCHEDULED = "SCHEDULED";
   String RUNNING = "RUNNING";
   String FAILED = "FAILED";
   String FINISHED = "FINISHED";
   String CANCELLED = "CANCELLED";

   boolean isActivationTask();

   String getPatches();

   String getApplicationName();

   String getModuleName();
}
