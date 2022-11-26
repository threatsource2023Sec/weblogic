package weblogic.diagnostics.image.descriptor;

public interface WatchManagerStatisticsBean {
   long getTotalHarvesterEvaluationCycles();

   void setTotalHarvesterEvaluationCycles(long var1);

   long getTotalHarvesterWatchEvaluations();

   void setTotalHarvesterWatchEvaluations(long var1);

   long getTotalHarvesterWatchesTriggered();

   void setTotalHarvesterWatchesTriggered(long var1);

   long getAverageHarvesterWatchEvaluationTime();

   void setAverageHarvesterWatchEvaluationTime(long var1);

   long getTotalLogEvaluationCycles();

   void setTotalLogEvaluationCycles(long var1);

   long getTotalLogWatchEvaluations();

   void setTotalLogWatchEvaluations(long var1);

   long getTotalLogWatchesTriggered();

   void setTotalLogWatchesTriggered(long var1);

   long getAverageLogWatchEvaluationTime();

   void setAverageLogWatchEvaluationTime(long var1);

   long getTotalEventDataEvaluationCycles();

   void setTotalEventDataEvaluationCycles(long var1);

   long getTotalEventDataWatchEvaluations();

   void setTotalEventDataWatchEvaluations(long var1);

   long getTotalEventDataWatchesTriggered();

   void setTotalEventDataWatchesTriggered(long var1);

   long getAverageEventDataWatchEvaluationTime();

   void setAverageEventDataWatchEvaluationTime(long var1);

   long getTotalNotificationsPerformed();

   void setTotalNotificationsPerformed(long var1);

   long getTotalFailedNotifications();

   void setTotalFailedNotifications(long var1);

   long getMinimumHarvesterWatchEvaluationTime();

   void setMinimumHarvesterWatchEvaluationTime(long var1);

   long getMaximumHarvesterWatchEvaluationTime();

   void setMaximumHarvesterWatchEvaluationTime(long var1);

   long getMinimumLogWatchEvaluationTime();

   void setMinimumLogWatchEvaluationTime(long var1);

   long getMaximumLogWatchEvaluationTime();

   void setMaximumLogWatchEvaluationTime(long var1);

   long getMinimumEventDataWatchEvaluationTime();

   void setMinimumEventDataWatchEvaluationTime(long var1);

   long getMaximumEventDataWatchEvaluationTime();

   void setMaximumEventDataWatchEvaluationTime(long var1);

   long getTotalDIMGNotificationsPerformed();

   void setTotalDIMGNotificationsPerformed(long var1);

   long getTotalFailedDIMGNotifications();

   void setTotalFailedDIMGNotifications(long var1);

   long getTotalJMXNotificationsPerformed();

   void setTotalJMXNotificationsPerformed(long var1);

   long getTotalFailedJMXNotifications();

   void setTotalFailedJMXNotifications(long var1);

   long getTotalSMTPNotificationsPerformed();

   void setTotalSMTPNotificationsPerformed(long var1);

   long getTotalFailedSMTPNotifications();

   void setTotalFailedSMTPNotifications(long var1);

   long getTotalSNMPNotificationsPerformed();

   void setTotalSNMPNotificationsPerformed(long var1);

   long getTotalFailedSNMPNotifications();

   void setTotalFailedSNMPNotifications(long var1);

   long getTotalJMSNotificationsPerformed();

   void setTotalJMSNotificationsPerformed(long var1);

   long getTotalFailedJMSNotifications();

   void setTotalFailedJMSNotifications(long var1);
}
