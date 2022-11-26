package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface WLDFWatchManagerRuntimeMBean extends RuntimeMBean {
   long getTotalHarvesterEvaluationCycles();

   long getTotalHarvesterWatchEvaluations();

   long getTotalHarvesterWatchesTriggered();

   long getAverageHarvesterWatchEvaluationTime();

   long getTotalLogEvaluationCycles();

   long getTotalLogWatchEvaluations();

   long getTotalLogWatchesTriggered();

   long getAverageLogWatchEvaluationTime();

   long getTotalEventDataEvaluationCycles();

   long getTotalEventDataWatchEvaluations();

   long getTotalEventDataWatchesTriggered();

   long getAverageEventDataWatchEvaluationTime();

   int getCurrentActiveAlarmsCount();

   int getMaximumActiveAlarmsCount();

   long getTotalActiveManualResetAlarms();

   long getTotalActiveAutomaticResetAlarms();

   long getTotalNotificationsPerformed();

   long getTotalFailedNotifications();

   String[] getActiveAlarmWatches() throws ManagementException;

   void resetWatchAlarm(String var1) throws ManagementException;

   long getMinimumHarvesterWatchEvaluationTime();

   long getMaximumHarvesterWatchEvaluationTime();

   long getMinimumLogWatchEvaluationTime();

   long getMaximumLogWatchEvaluationTime();

   long getMinimumEventDataWatchEvaluationTime();

   long getMaximumEventDataWatchEvaluationTime();

   long getTotalDIMGNotificationsPerformed();

   long getTotalFailedDIMGNotifications();

   long getTotalJMXNotificationsPerformed();

   long getTotalFailedJMXNotifications();

   long getTotalSMTPNotificationsPerformed();

   long getTotalFailedSMTPNotifications();

   long getTotalSNMPNotificationsPerformed();

   long getTotalFailedSNMPNotifications();

   long getTotalJMSNotificationsPerformed();

   long getTotalFailedJMSNotifications();
}
