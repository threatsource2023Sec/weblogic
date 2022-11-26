package weblogic.diagnostics.runtimecontrol.internal;

import javax.management.JMRuntimeException;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.watch.WatchManager;
import weblogic.diagnostics.watch.WatchManagerFactory;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFWatchManagerRuntimeMBean;

public class WatchManagerRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFWatchManagerRuntimeMBean {
   private WLDFResourceBean wldfResource;

   public WatchManagerRuntimeMBeanImpl(WLDFResourceBean resource, RuntimeMBean parent) throws ManagementException {
      super(resource.getWatchNotification().getName(), parent);
      this.wldfResource = resource;
   }

   public String[] getActiveAlarmWatches() {
      String[] alarmWatch = new String[0];
      WatchManager wm = this.getWatchManager();
      if (wm != null) {
         alarmWatch = wm.getActiveAlarmWatchNames();
      }

      return alarmWatch;
   }

   public long getAverageEventDataWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getAverageEventDataWatchEvaluationTime();
   }

   public long getAverageHarvesterWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getAverageHarvesterWatchEvaluationTime();
   }

   public long getAverageLogWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getAverageLogWatchEvaluationTime();
   }

   public int getCurrentActiveAlarmsCount() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0 : wm.getStatistics().getCurrentActiveAlarmsCount();
   }

   public int getMaximumActiveAlarmsCount() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0 : wm.getStatistics().getMaximumActiveAlarmsCount();
   }

   public long getMaximumEventDataWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getMaximumEventDataWatchEvaluationTime();
   }

   public long getMaximumHarvesterWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getMaximumHarvesterWatchEvaluationTime();
   }

   public long getMaximumLogWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getMaximumLogWatchEvaluationTime();
   }

   public long getMinimumEventDataWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getMinEventDataWatchEvaluationTime();
   }

   public long getMinimumHarvesterWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getMinimumHarvesterWatchEvaluationTime();
   }

   public long getMinimumLogWatchEvaluationTime() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getMinimumLogWatchEvaluationTime();
   }

   public long getTotalActiveAutomaticResetAlarms() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalActiveAutomaticResetAlarms();
   }

   public long getTotalActiveManualResetAlarms() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalActiveManualResetAlarms();
   }

   public long getTotalDIMGNotificationsPerformed() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalDIMGNotificationsPerformed();
   }

   public long getTotalEventDataEvaluationCycles() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalEventDataEvaluationCycles();
   }

   public long getTotalEventDataWatchesTriggered() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalEventDataWatchesTriggered();
   }

   public long getTotalEventDataWatchEvaluations() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalEventDataWatchEvaluations();
   }

   public long getTotalFailedDIMGNotifications() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalFailedDIMGNotifications();
   }

   public long getTotalFailedJMSNotifications() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalFailedJMSNotifications();
   }

   public long getTotalFailedJMXNotifications() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalFailedJMXNotifications();
   }

   public long getTotalFailedNotifications() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalFailedNotifications();
   }

   public long getTotalFailedSMTPNotifications() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalFailedSMTPNotifications();
   }

   public long getTotalFailedSNMPNotifications() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalFailedSNMPNotifications();
   }

   public long getTotalHarvesterEvaluationCycles() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalHarvesterEvaluationCycles();
   }

   public long getTotalHarvesterWatchesTriggered() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalHarvesterWatchesTriggered();
   }

   public long getTotalHarvesterWatchEvaluations() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalHarvesterWatchEvaluations();
   }

   public long getTotalJMSNotificationsPerformed() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalJMSNotificationsPerformed();
   }

   public long getTotalJMXNotificationsPerformed() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalJMXNotificationsPerformed();
   }

   public long getTotalLogEvaluationCycles() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalLogEvaluationCycles();
   }

   public long getTotalLogWatchesTriggered() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalLogWatchesTriggered();
   }

   public long getTotalLogWatchEvaluations() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalLogWatchEvaluations();
   }

   public long getTotalNotificationsPerformed() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalNotificationsPerformed();
   }

   public long getTotalSMTPNotificationsPerformed() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalSMTPNotificationsPerformed();
   }

   public long getTotalSNMPNotificationsPerformed() {
      WatchManager wm = this.getWatchManager();
      return wm == null ? 0L : wm.getStatistics().getTotalSNMPNotificationsPerformed();
   }

   public void resetWatchAlarm(String watchName) {
      try {
         WatchManager wm = this.getWatchManager();
         if (wm != null) {
            wm.resetWatchAlarm(watchName);
         }

      } catch (Exception var4) {
         JMRuntimeException jmex = new JMRuntimeException();
         jmex.initCause(var4);
         throw jmex;
      }
   }

   private WatchManager getWatchManager() {
      try {
         return WatchManagerFactory.getFactoryInstance("").lookupWatchManager(this.wldfResource);
      } catch (ManagementException var2) {
         return null;
      }
   }
}
