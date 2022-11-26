package weblogic.spring.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.SpringTransactionTemplateRuntimeMBean;

public class SpringTransactionTemplateRuntimeMBeanImpl extends SpringBaseRuntimeMBeanImpl implements SpringTransactionTemplateRuntimeMBean {
   private long executions;
   private long failedExecutions;
   private double elapsedTimes;

   public SpringTransactionTemplateRuntimeMBeanImpl(Object applicationContext, String name, Object delegate) throws ManagementException {
      super(applicationContext, delegate, name, false);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionTemplateRuntimeMBeanImpl(" + name + ")");
      }

   }

   public synchronized long getExecuteCount() {
      return this.executions;
   }

   public synchronized long getExecuteFailedCount() {
      return this.failedExecutions;
   }

   public synchronized double getAverageExecuteTime() {
      return this.executions == 0L ? 0.0 : this.elapsedTimes / 1000000.0 / (double)this.executions;
   }

   public synchronized void addExecute(boolean successful, long elapsedTimeNanos) {
      ++this.executions;
      if (!successful) {
         ++this.failedExecutions;
      }

      this.elapsedTimes += (double)elapsedTimeNanos;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringTransactionTemplateRuntimeMBeanImpl.addExecute() : " + this.name);
      }

   }
}
