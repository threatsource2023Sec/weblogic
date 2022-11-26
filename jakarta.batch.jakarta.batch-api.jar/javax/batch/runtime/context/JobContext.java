package javax.batch.runtime.context;

import java.util.Properties;
import javax.batch.runtime.BatchStatus;

public interface JobContext {
   String getJobName();

   Object getTransientUserData();

   void setTransientUserData(Object var1);

   long getInstanceId();

   long getExecutionId();

   Properties getProperties();

   BatchStatus getBatchStatus();

   String getExitStatus();

   void setExitStatus(String var1);
}
