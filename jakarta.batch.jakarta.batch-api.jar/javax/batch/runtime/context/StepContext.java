package javax.batch.runtime.context;

import java.io.Serializable;
import java.util.Properties;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.Metric;

public interface StepContext {
   String getStepName();

   Object getTransientUserData();

   void setTransientUserData(Object var1);

   long getStepExecutionId();

   Properties getProperties();

   Serializable getPersistentUserData();

   void setPersistentUserData(Serializable var1);

   BatchStatus getBatchStatus();

   String getExitStatus();

   void setExitStatus(String var1);

   Exception getException();

   Metric[] getMetrics();
}
