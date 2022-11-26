package weblogic.diagnostics.watch;

public interface WatchManagerStatistics {
   long getAverageEventDataWatchEvaluationTime();

   long getAverageHarvesterWatchEvaluationTime();

   long getAverageLogWatchEvaluationTime();

   int getCurrentActiveAlarmsCount();

   int getMaximumActiveAlarmsCount();

   long getMaximumEventDataWatchEvaluationTime();

   long getMaximumHarvesterWatchEvaluationTime();

   long getMaximumLogWatchEvaluationTime();

   long getMinimumEventDataWatchEvaluationTime();

   long getMinimumHarvesterWatchEvaluationTime();

   long getMinimumLogWatchEvaluationTime();

   long getTotalActiveAutomaticResetAlarms();

   long getTotalActiveManualResetAlarms();

   long getTotalDIMGNotificationsPerformed();

   long getTotalEventDataEvaluationCycles();

   long getTotalEventDataWatchesTriggered();

   long getTotalEventDataWatchEvaluations();

   long getTotalFailedDIMGNotifications();

   long getTotalFailedJMSNotifications();

   long getTotalFailedJMXNotifications();

   long getTotalFailedNotifications();

   long getTotalFailedSMTPNotifications();

   long getTotalFailedSNMPNotifications();

   long getTotalHarvesterEvaluationCycles();

   long getTotalHarvesterWatchesTriggered();

   long getTotalHarvesterWatchEvaluations();

   long getTotalJMSNotificationsPerformed();

   long getTotalJMXNotificationsPerformed();

   long getTotalLogEvaluationCycles();

   long getTotalLogWatchesTriggered();

   long getTotalLogWatchEvaluations();

   long getTotalNotificationsPerformed();

   long getTotalSMTPNotificationsPerformed();

   long getTotalSNMPNotificationsPerformed();

   long getMaxEventDataWatchEvaluationTime();

   long getMaxHarvesterWatchEvaluationTime();

   long getMaxLogWatchEvaluationTime();

   long getMinEventDataWatchEvaluationTime();

   long getMinHarvesterWatchEvaluationTime();

   long getMinLogWatchEvaluationTime();

   long getTotalEventDataWatchEvaluationTime();

   long getTotalHarvesterWatchEvaluationTime();

   long getTotalLogWatchEvaluationTime();
}
