package weblogic.spring.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.SpringViewRuntimeMBean;

public class SpringViewRuntimeMBeanImpl extends SpringBaseRuntimeMBeanImpl implements SpringViewRuntimeMBean {
   private long viewRenderCount;
   private long failedViewRenders;
   private double elapsedTimes;

   public SpringViewRuntimeMBeanImpl(Object applicationContext, String name, Object delegate) throws ManagementException {
      super(applicationContext, delegate, name, false);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringViewRuntimeMBeanImpl(" + name + ")");
      }

   }

   public synchronized long getRenderCount() {
      return this.viewRenderCount;
   }

   public synchronized long getRenderFailedCount() {
      return this.failedViewRenders;
   }

   public synchronized double getAverageRenderTime() {
      return this.viewRenderCount == 0L ? 0.0 : this.elapsedTimes / 1000000.0 / (double)this.viewRenderCount;
   }

   public synchronized void addRender(boolean successful, long elapsedTimeNanos) {
      ++this.viewRenderCount;
      if (!successful) {
         ++this.failedViewRenders;
      }

      this.elapsedTimes += (double)elapsedTimeNanos;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringViewRuntimeMBeanImpl.addRender() : " + this.name);
      }

   }
}
