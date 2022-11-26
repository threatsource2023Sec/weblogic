package weblogic.management.runtime;

public interface ProgressMBean extends RuntimeMBean {
   String IN_PROGRESS = "IN_PROGRESS";
   String FINAL = "FINAL";
   String FAILED = "FAILED";

   String getName();

   String[] getCurrentWork();

   String getState();
}
