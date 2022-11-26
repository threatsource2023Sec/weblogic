package weblogic.nodemanager.mbean;

import java.io.IOException;

public interface NodeManagerTask {
   String TASK_IN_PROGRESS = "TASK IN PROGRESS";
   String COMPLETED = "TASK COMPLETED";
   String FAILED = "FAILED";
   String CANCELLED = "CANCELLED";

   long getBeginTime();

   long getEndTime();

   Exception getError();

   String getStatus();

   void cancel();

   boolean isFinished();

   void waitForFinish() throws InterruptedException;

   void waitForFinish(long var1) throws InterruptedException;

   void cleanup() throws IOException;
}
