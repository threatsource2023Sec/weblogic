package weblogic.spring.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.SpringViewResolverRuntimeMBean;

public class SpringViewResolverRuntimeMBeanImpl extends SpringBaseRuntimeMBeanImpl implements SpringViewResolverRuntimeMBean {
   private long viewResolveCount;
   private long failedViewResolves;
   private double elapsedTimes;

   public SpringViewResolverRuntimeMBeanImpl(Object applicationContext, String name, Object delegate) throws ManagementException {
      super(applicationContext, delegate, name, false);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringViewResolverRuntimeMBeanImpl(" + name + ")");
      }

   }

   public synchronized long getResolveViewNameCount() {
      return this.viewResolveCount;
   }

   public synchronized long getResolveViewNameFailedCount() {
      return this.failedViewResolves;
   }

   public synchronized double getAverageResolveViewNameTime() {
      return this.viewResolveCount == 0L ? 0.0 : this.elapsedTimes / 1000000.0 / (double)this.viewResolveCount;
   }

   public synchronized void addViewResolved(boolean successful, long elapsedTimeNanos) {
      ++this.viewResolveCount;
      if (!successful) {
         ++this.failedViewResolves;
      }

      this.elapsedTimes += (double)elapsedTimeNanos;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringViewResolverRuntimeMBeanImpl.addViewResolved() : " + this.name);
      }

   }
}
