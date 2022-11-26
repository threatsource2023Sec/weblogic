package weblogic.spring.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.SpringTransactionManagerRuntimeMBean;

public class SpringTransactionManagerRuntimeMBeanImpl extends SpringBaseRuntimeMBeanImpl implements SpringTransactionManagerRuntimeMBean {
   private long rollbackCount;
   private long commitCount;
   private long resumeCount;
   private long suspendCount;
   private long failedRollbacks;
   private long failedCommits;
   private long failedResumes;
   private long failedSuspends;

   public SpringTransactionManagerRuntimeMBeanImpl(Object applicationContext, String name, Object delegate) throws ManagementException {
      super(applicationContext, delegate, name, false);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionManagerRuntimeMBeanImpl(" + name + ")");
      }

   }

   public synchronized long getRollbackCount() {
      return this.rollbackCount;
   }

   public synchronized long getCommitCount() {
      return this.commitCount;
   }

   public synchronized long getResumeCount() {
      return this.resumeCount;
   }

   public synchronized long getSuspendCount() {
      return this.suspendCount;
   }

   public synchronized long getRollbackFailedCount() {
      return this.failedRollbacks;
   }

   public synchronized long getCommitFailedCount() {
      return this.failedCommits;
   }

   public synchronized long getResumeFailedCount() {
      return this.failedResumes;
   }

   public synchronized long getSuspendFailedCount() {
      return this.failedSuspends;
   }

   public synchronized void addRollback(boolean successful) {
      ++this.rollbackCount;
      if (!successful) {
         ++this.failedRollbacks;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionManagerRuntimeMBeanImpl.addRollback() : " + this.name);
      }

   }

   public synchronized void addCommit(boolean successful) {
      ++this.commitCount;
      if (!successful) {
         ++this.failedCommits;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionManagerRuntimeMBeanImpl.addCommit() : " + this.name);
      }

   }

   public synchronized void addResume(boolean successful) {
      ++this.resumeCount;
      if (!successful) {
         ++this.failedResumes;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionManagerRuntimeMBeanImpl.addResume() : " + this.name);
      }

   }

   public synchronized void addSuspend(boolean successful) {
      ++this.suspendCount;
      if (!successful) {
         ++this.failedSuspends;
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionManagerRuntimeMBeanImpl.addSuspend() : " + this.name);
      }

   }
}
