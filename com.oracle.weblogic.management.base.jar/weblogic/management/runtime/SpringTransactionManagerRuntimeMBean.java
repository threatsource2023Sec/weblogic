package weblogic.management.runtime;

public interface SpringTransactionManagerRuntimeMBean extends RuntimeMBean {
   String getBeanId();

   String getApplicationContextDisplayName();

   long getRollbackCount();

   long getCommitCount();

   long getResumeCount();

   long getSuspendCount();
}
