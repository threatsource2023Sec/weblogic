package weblogic.management.runtime;

import java.io.PrintWriter;

public interface TaskRuntimeMBean extends RuntimeMBean {
   String PROGRESS_SUCCESS = "success";
   String PROGRESS_FAILED = "failed";
   String PROGRESS_PROCESSING = "processing";
   String PROGRESS_PENDING = "pending";

   String getDescription();

   String getStatus();

   String getProgress();

   boolean isRunning();

   long getBeginTime();

   long getEndTime();

   TaskRuntimeMBean[] getSubTasks();

   TaskRuntimeMBean getParentTask();

   void cancel() throws Exception;

   void printLog(PrintWriter var1);

   Exception getError();

   boolean isSystemTask();

   void setSystemTask(boolean var1);
}
