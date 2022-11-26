package weblogic.diagnostics.watch;

import java.io.OutputStream;
import java.util.Date;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.descriptor.WatchAlarmStateBean;
import weblogic.diagnostics.image.descriptor.WatchImageSourceBean;
import weblogic.diagnostics.image.descriptor.WatchManagerBean;
import weblogic.diagnostics.image.descriptor.WatchManagerStatisticsBean;
import weblogic.diagnostics.image.descriptor.WatchStatisticsBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean;

public class WatchSource implements ImageSource {
   private boolean timeoutRequested;
   private WatchManagerFactory wmFactory;
   private WLDFWatchNotificationRuntimeMBean watchNotificationRuntime;

   public WatchSource(WatchManagerFactory wmFactory, WLDFWatchNotificationRuntimeMBean wnRuntime) {
      this.wmFactory = wmFactory;
      this.watchNotificationRuntime = wnRuntime;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      DescriptorManager dm = new DescriptorManager();
      Descriptor desc = dm.createDescriptorRoot(WatchImageSourceBean.class);
      DescriptorBean root = desc.getRootBean();
      WatchImageSourceBean imageSourceBean = (WatchImageSourceBean)root;

      try {
         this.recordAggregateStatistics(imageSourceBean, this.wmFactory);
         WatchManager[] var6 = this.wmFactory.listActiveWatchManagers();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            WatchManager wm = var6[var8];
            if (this.timeoutRequested) {
               break;
            }

            this.writeWatchManagerInfo(imageSourceBean, wm);
         }

         dm.writeDescriptorAsXML(root.getDescriptor(), out);
      } catch (Exception var10) {
         throw new ImageSourceCreationException(var10);
      }
   }

   public void timeoutImageCreation() {
      this.timeoutRequested = true;
   }

   private void writeWatchAlarms(WatchManagerBean wmBean, WatchManager wm) throws ManagementException {
      Watch[] alarms = wm.getActiveAlarmWatches();

      for(int i = 0; alarms != null && i < alarms.length && !this.timeoutRequested; ++i) {
         Watch watch = alarms[i];
         WatchAlarmStateBean bean = wmBean.createWatchAlarmState();
         bean.setWatchName(watch.getWatchName());
         if (watch.getAlarmType() == 2) {
            bean.setAlarmResetType("AutomaticReset");
            bean.setAlarmResetPeriod((new Date(watch.getResetTime())).toString());
         } else {
            bean.setAlarmResetType("ManualReset");
         }
      }

   }

   private void recordAggregateStatistics(WatchImageSourceBean imageSourceBean, WatchManagerFactory wmFactory) throws ManagementException {
      WatchStatisticsBean aggregateStats = imageSourceBean.createAggregateWatchStatistics();
      aggregateStats.setActiveModulesCount(wmFactory.numActiveWatchManagers());
      aggregateStats.setAverageEventDataWatchEvaluationTime(this.watchNotificationRuntime.getAverageEventDataWatchEvaluationTime());
      aggregateStats.setAverageHarvesterWatchEvaluationTime(this.watchNotificationRuntime.getAverageHarvesterWatchEvaluationTime());
      aggregateStats.setAverageLogWatchEvaluationTime(this.watchNotificationRuntime.getAverageLogWatchEvaluationTime());
      aggregateStats.setMaximumEventDataWatchEvaluationTime(this.watchNotificationRuntime.getMaximumEventDataWatchEvaluationTime());
      aggregateStats.setMaximumHarvesterWatchEvaluationTime(this.watchNotificationRuntime.getMaximumHarvesterWatchEvaluationTime());
      aggregateStats.setMaximumLogWatchEvaluationTime(this.watchNotificationRuntime.getMaximumLogWatchEvaluationTime());
      aggregateStats.setMinimumEventDataWatchEvaluationTime(this.watchNotificationRuntime.getMinimumEventDataWatchEvaluationTime());
      aggregateStats.setMinimumHarvesterWatchEvaluationTime(this.watchNotificationRuntime.getMinimumHarvesterWatchEvaluationTime());
      aggregateStats.setMinimumLogWatchEvaluationTime(this.watchNotificationRuntime.getMinimumLogWatchEvaluationTime());
      aggregateStats.setTotalEventDataEvaluationCycles(this.watchNotificationRuntime.getTotalEventDataEvaluationCycles());
      aggregateStats.setTotalEventDataWatchesTriggered(this.watchNotificationRuntime.getTotalEventDataWatchesTriggered());
      aggregateStats.setTotalEventDataWatchEvaluations(this.watchNotificationRuntime.getTotalEventDataWatchEvaluations());
      aggregateStats.setTotalFailedNotifications(this.watchNotificationRuntime.getTotalFailedNotifications());
      aggregateStats.setTotalHarvesterEvaluationCycles(this.watchNotificationRuntime.getTotalHarvesterEvaluationCycles());
      aggregateStats.setTotalHarvesterWatchesTriggered(this.watchNotificationRuntime.getTotalHarvesterWatchesTriggered());
      aggregateStats.setTotalHarvesterWatchEvaluations(this.watchNotificationRuntime.getTotalHarvesterWatchEvaluations());
      aggregateStats.setTotalLogEvaluationCycles(this.watchNotificationRuntime.getTotalLogEvaluationCycles());
      aggregateStats.setTotalLogWatchEvaluations(this.watchNotificationRuntime.getTotalLogWatchEvaluations());
      aggregateStats.setTotalLogWatchesTriggered(this.watchNotificationRuntime.getTotalLogWatchesTriggered());
      aggregateStats.setTotalNotificationsPerformed(this.watchNotificationRuntime.getTotalNotificationsPerformed());
   }

   private void writeWatchManagerStats(WatchManagerBean wmBean, WatchManager wm) {
      WatchManagerStatisticsBean wmStats = wmBean.createWatchManagerStatistics();
      WatchManagerStatistics statistics = wm.getStatistics();
      wmStats.setAverageEventDataWatchEvaluationTime(statistics.getAverageEventDataWatchEvaluationTime());
      wmStats.setAverageHarvesterWatchEvaluationTime(statistics.getAverageHarvesterWatchEvaluationTime());
      wmStats.setAverageLogWatchEvaluationTime(statistics.getAverageLogWatchEvaluationTime());
      wmStats.setMaximumEventDataWatchEvaluationTime(statistics.getMaxEventDataWatchEvaluationTime());
      wmStats.setMaximumHarvesterWatchEvaluationTime(statistics.getMaximumHarvesterWatchEvaluationTime());
      wmStats.setMaximumLogWatchEvaluationTime(statistics.getMaximumLogWatchEvaluationTime());
      wmStats.setMinimumEventDataWatchEvaluationTime(statistics.getMinimumEventDataWatchEvaluationTime());
      wmStats.setMinimumHarvesterWatchEvaluationTime(statistics.getMinimumHarvesterWatchEvaluationTime());
      wmStats.setMinimumLogWatchEvaluationTime(statistics.getMinimumLogWatchEvaluationTime());
      wmStats.setTotalDIMGNotificationsPerformed(statistics.getTotalDIMGNotificationsPerformed());
      wmStats.setTotalEventDataEvaluationCycles(statistics.getTotalEventDataEvaluationCycles());
      wmStats.setTotalEventDataWatchesTriggered(statistics.getTotalEventDataWatchesTriggered());
      wmStats.setTotalEventDataWatchEvaluations(statistics.getTotalEventDataWatchEvaluations());
      wmStats.setTotalFailedDIMGNotifications(statistics.getTotalFailedDIMGNotifications());
      wmStats.setTotalFailedJMSNotifications(statistics.getTotalFailedJMSNotifications());
      wmStats.setTotalFailedJMXNotifications(statistics.getTotalFailedJMXNotifications());
      wmStats.setTotalFailedNotifications(statistics.getTotalFailedNotifications());
      wmStats.setTotalFailedSMTPNotifications(statistics.getTotalFailedSMTPNotifications());
      wmStats.setTotalFailedSNMPNotifications(statistics.getTotalFailedSNMPNotifications());
      wmStats.setTotalHarvesterEvaluationCycles(statistics.getTotalHarvesterEvaluationCycles());
      wmStats.setTotalHarvesterWatchesTriggered(statistics.getTotalHarvesterWatchesTriggered());
      wmStats.setTotalHarvesterWatchEvaluations(statistics.getTotalHarvesterWatchEvaluations());
      wmStats.setTotalJMSNotificationsPerformed(statistics.getTotalJMSNotificationsPerformed());
      wmStats.setTotalJMXNotificationsPerformed(statistics.getTotalJMXNotificationsPerformed());
      wmStats.setTotalLogEvaluationCycles(statistics.getTotalLogEvaluationCycles());
      wmStats.setTotalLogWatchEvaluations(statistics.getTotalLogWatchEvaluations());
      wmStats.setTotalLogWatchesTriggered(statistics.getTotalLogWatchesTriggered());
      wmStats.setTotalNotificationsPerformed(statistics.getTotalNotificationsPerformed());
      wmStats.setTotalSMTPNotificationsPerformed(statistics.getTotalSMTPNotificationsPerformed());
      wmStats.setTotalSNMPNotificationsPerformed(statistics.getTotalSNMPNotificationsPerformed());
   }

   private void writeWatchManagerInfo(WatchImageSourceBean imageSourceBean, WatchManager wm) throws ManagementException {
      WatchManagerBean wmBean = imageSourceBean.createWatchManager();
      WatchConfiguration wc = wm.getWatchConfiguration();
      String moduleName = wc == null ? "unknown" : wc.getName();
      wmBean.setModuleName(moduleName);
      this.writeWatchAlarms(wmBean, wm);
      this.writeWatchManagerStats(wmBean, wm);
   }
}
