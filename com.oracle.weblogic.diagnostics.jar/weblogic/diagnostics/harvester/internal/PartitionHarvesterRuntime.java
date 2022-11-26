package weblogic.diagnostics.harvester.internal;

import javax.management.MBeanException;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.LogSupport;
import weblogic.management.ManagementException;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFPartitionHarvesterRuntimeMBean;

public class PartitionHarvesterRuntime extends RuntimeMBeanDelegate implements WLDFPartitionHarvesterRuntimeMBean, HarvesterCycleListener {
   public static final String HARVESTER_CYCLE_COMPLETED_TYPE = "weblogic.diagnostics.harvester.cycleCompleted";
   protected static final DebugLogger dbg = DebugSupport.getDebugLogger();
   private int notificationSequence = 0;
   protected MetricArchiver archiver;
   private NotificationGenerator notificationGenerator;

   public PartitionHarvesterRuntime(MetricArchiver archiver, String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
      this.archiver = archiver;
      archiver.setHarvesterCycleListener(this);
   }

   public long getAverageSamplingTime() {
      return this.archiver.isEnabled() ? this.archiver.getAverageSamplingTime() : 0L;
   }

   public long getMaximumSamplingTime() {
      return this.archiver.isEnabled() ? this.archiver.getMaximumSamplingTime() : 0L;
   }

   public long getMinimumSamplingTime() {
      return this.archiver.isEnabled() ? this.archiver.getMinimumSamplingTime() : 0L;
   }

   public long getTotalSamplingCycles() {
      return this.archiver.isEnabled() ? (long)this.archiver.getTotalSamplingCycles() : 0L;
   }

   public long getTotalSamplingTime() {
      return this.archiver.isEnabled() ? this.archiver.getTotalSamplingTime() : 0L;
   }

   void setNotificationGenerator(NotificationGenerator ng) {
      this.notificationGenerator = ng;
   }

   public void harvestCycleOccurred(long snapshotTime, String snapshotName) {
      boolean subscribed = this.notificationGenerator != null && this.notificationGenerator.isSubscribed();
      if (dbg.isDebugEnabled()) {
         dbg.debug("HarvesterRuntimeMBean " + this.getName() + " is sending notification " + this + ", subsribed=" + subscribed);
      }

      if (subscribed) {
         try {
            ++this.notificationSequence;
            this.notificationGenerator.sendNotification(new Notification("weblogic.diagnostics.harvester.cycleCompleted", this.notificationGenerator.getObjectName(), (long)this.notificationSequence, snapshotTime, snapshotName));
         } catch (MBeanException var6) {
            LogSupport.logUnexpectedException("Harvest cycle notification failed with exception.", var6);
         }
      }

   }

   public long getTotalConfiguredDataSampleCount() {
      return this.archiver.isEnabled() ? this.archiver.getTotalConfiguredDataSampleCount() : 0L;
   }

   public long getTotalImplicitDataSampleCount() {
      return this.archiver.isEnabled() ? this.archiver.getTotalImplicitDataSampleCount() : 0L;
   }
}
