package weblogic.management.runtime;

public interface SpringTransactionTemplateRuntimeMBean extends RuntimeMBean {
   String getBeanId();

   String getApplicationContextDisplayName();

   long getExecuteCount();

   long getExecuteFailedCount();

   double getAverageExecuteTime();
}
