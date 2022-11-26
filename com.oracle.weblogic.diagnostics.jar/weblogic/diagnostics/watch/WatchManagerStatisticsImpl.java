package weblogic.diagnostics.watch;

class WatchManagerStatisticsImpl implements WatchManagerStatistics {
   private int currentActiveAlarmsCount;
   long maxEventDataWatchEvaluationTime;
   long maxHarvesterWatchEvaluationTime;
   private int maximumActiveAlarmsCount;
   long maxLogWatchEvaluationTime;
   long minEventDataWatchEvaluationTime;
   long minHarvesterWatchEvaluationTime;
   long minLogWatchEvaluationTime;
   private long totalActiveAutomaticResetAlarms;
   private long totalActiveManualResetAlarms;
   private long totalDIMGNotificationsPerformed;
   private long totalEventDataEvaluationCycles;
   private long totalEventDataWatchesTriggered;
   private long totalEventDataWatchEvaluations;
   long totalEventDataWatchEvaluationTime;
   private long totalFailedDIMGNotifications;
   private long totalFailedJMSNotifications;
   private long totalFailedJMXNotifications;
   private long totalFailedNotifications;
   private long totalFailedSMTPNotifications;
   private long totalFailedSNMPNotifications;
   private long totalHarvesterEvaluationCycles;
   private long totalHarvesterWatchesTriggered;
   private long totalHarvesterWatchEvaluations;
   long totalHarvesterWatchEvaluationTime;
   private long totalJMSNotificationsPerformed;
   private long totalJMXNotificationsPerformed;
   private long totalLogEvaluationCycles;
   private long totalLogWatchesTriggered;
   private long totalLogWatchEvaluations;
   long totalLogWatchEvaluationTime;
   private long totalNotificationsPerformed;
   private long totalSMTPNotificationsPerformed;
   private long totalSNMPNotificationsPerformed;

   public long getAverageEventDataWatchEvaluationTime() {
      return this.totalEventDataEvaluationCycles > 0L ? this.totalEventDataWatchEvaluationTime / this.totalEventDataEvaluationCycles : 0L;
   }

   public long getAverageHarvesterWatchEvaluationTime() {
      return this.totalHarvesterEvaluationCycles > 0L ? this.totalHarvesterWatchEvaluationTime / this.totalHarvesterEvaluationCycles : 0L;
   }

   public long getAverageLogWatchEvaluationTime() {
      return this.totalLogEvaluationCycles > 0L ? this.totalLogWatchEvaluationTime / this.totalLogEvaluationCycles : 0L;
   }

   public synchronized int getCurrentActiveAlarmsCount() {
      return this.currentActiveAlarmsCount;
   }

   public int getMaximumActiveAlarmsCount() {
      return this.maximumActiveAlarmsCount;
   }

   public long getMaximumEventDataWatchEvaluationTime() {
      return this.maxEventDataWatchEvaluationTime;
   }

   public long getMaximumHarvesterWatchEvaluationTime() {
      return this.maxHarvesterWatchEvaluationTime;
   }

   public long getMaximumLogWatchEvaluationTime() {
      return this.maxLogWatchEvaluationTime;
   }

   public long getMinimumEventDataWatchEvaluationTime() {
      return this.minEventDataWatchEvaluationTime;
   }

   public long getMinimumHarvesterWatchEvaluationTime() {
      return this.minHarvesterWatchEvaluationTime;
   }

   public long getMinimumLogWatchEvaluationTime() {
      return this.minLogWatchEvaluationTime;
   }

   public long getTotalActiveAutomaticResetAlarms() {
      return this.totalActiveAutomaticResetAlarms;
   }

   public long getTotalActiveManualResetAlarms() {
      return this.totalActiveManualResetAlarms;
   }

   public long getTotalDIMGNotificationsPerformed() {
      return this.totalDIMGNotificationsPerformed;
   }

   public long getTotalEventDataEvaluationCycles() {
      return this.totalEventDataEvaluationCycles;
   }

   public long getTotalEventDataWatchesTriggered() {
      return this.totalEventDataWatchesTriggered;
   }

   public long getTotalEventDataWatchEvaluations() {
      return this.totalEventDataWatchEvaluations;
   }

   public long getTotalFailedDIMGNotifications() {
      return this.totalFailedDIMGNotifications;
   }

   public long getTotalFailedJMSNotifications() {
      return this.totalFailedJMSNotifications;
   }

   public long getTotalFailedJMXNotifications() {
      return this.totalFailedJMXNotifications;
   }

   public long getTotalFailedNotifications() {
      return this.totalFailedNotifications;
   }

   public long getTotalFailedSMTPNotifications() {
      return this.totalFailedSMTPNotifications;
   }

   public long getTotalFailedSNMPNotifications() {
      return this.totalFailedSNMPNotifications;
   }

   public long getTotalHarvesterEvaluationCycles() {
      return this.totalHarvesterEvaluationCycles;
   }

   public long getTotalHarvesterWatchesTriggered() {
      return this.totalHarvesterWatchesTriggered;
   }

   public long getTotalHarvesterWatchEvaluations() {
      return this.totalHarvesterWatchEvaluations;
   }

   public long getTotalJMSNotificationsPerformed() {
      return this.totalJMSNotificationsPerformed;
   }

   public long getTotalJMXNotificationsPerformed() {
      return this.totalJMXNotificationsPerformed;
   }

   public long getTotalLogEvaluationCycles() {
      return this.totalLogEvaluationCycles;
   }

   public long getTotalLogWatchesTriggered() {
      return this.totalLogWatchesTriggered;
   }

   public long getTotalLogWatchEvaluations() {
      return this.totalLogWatchEvaluations;
   }

   public long getTotalNotificationsPerformed() {
      return this.totalNotificationsPerformed;
   }

   public long getTotalSMTPNotificationsPerformed() {
      return this.totalSMTPNotificationsPerformed;
   }

   public long getTotalSNMPNotificationsPerformed() {
      return this.totalSNMPNotificationsPerformed;
   }

   void incrementTotalActiveAutomaticResetAlarms() {
      ++this.totalActiveAutomaticResetAlarms;
   }

   void incrementTotalActiveManualResetAlarms() {
      ++this.totalActiveManualResetAlarms;
   }

   void incrementTotalDIMGNotificationsPerformed() {
      ++this.totalDIMGNotificationsPerformed;
      this.incrementTotalNotificationsPerformed();
   }

   void incrementTotalEventDataEvaluationCycles() {
      ++this.totalEventDataEvaluationCycles;
   }

   void incrementTotalEventDataWatchesTriggered() {
      ++this.totalEventDataWatchesTriggered;
   }

   void incrementTotalEventDataWatchEvaluations(int newEvaluations) {
      this.totalEventDataWatchEvaluations += (long)newEvaluations;
   }

   void incrementTotalEventDataWatchEvaluationTime(long elapsedTime) {
      this.totalEventDataWatchEvaluationTime += elapsedTime;
      this.minEventDataWatchEvaluationTime = this.minEventDataWatchEvaluationTime <= 0L ? elapsedTime : Math.min(this.minEventDataWatchEvaluationTime, elapsedTime);
      this.maxEventDataWatchEvaluationTime = Math.max(this.maxEventDataWatchEvaluationTime, elapsedTime);
   }

   void incrementTotalFailedDIMGNotifications() {
      ++this.totalFailedDIMGNotifications;
      this.incrementTotalFailedNotifications();
   }

   void incrementTotalFailedJMSNotifications() {
      ++this.totalFailedJMSNotifications;
      this.incrementTotalFailedNotifications();
   }

   void incrementTotalFailedJMXNotifications() {
      ++this.totalFailedJMXNotifications;
      this.incrementTotalFailedNotifications();
   }

   synchronized void incrementTotalFailedNotifications() {
      ++this.totalFailedNotifications;
   }

   void incrementTotalFailedSMTPNotifications() {
      ++this.totalFailedSMTPNotifications;
      this.incrementTotalFailedNotifications();
   }

   void incrementTotalFailedSNMPNotifications() {
      ++this.totalFailedSNMPNotifications;
      this.incrementTotalFailedNotifications();
   }

   void incrementTotalHarvesterEvaluationCycles() {
      ++this.totalHarvesterEvaluationCycles;
   }

   void incrementTotalHarvesterWatchesTriggered() {
      ++this.totalHarvesterWatchesTriggered;
   }

   void incrementTotalHarvesterWatchEvaluations(int newEvaluations) {
      this.totalHarvesterWatchEvaluations += (long)newEvaluations;
   }

   void incrementTotalHarvesterWatchEvaluationTime(long elapsedTime) {
      this.totalHarvesterWatchEvaluationTime += elapsedTime;
      this.minHarvesterWatchEvaluationTime = this.minHarvesterWatchEvaluationTime <= 0L ? elapsedTime : Math.min(this.minHarvesterWatchEvaluationTime, elapsedTime);
      this.maxHarvesterWatchEvaluationTime = Math.max(this.maxHarvesterWatchEvaluationTime, elapsedTime);
   }

   void incrementTotalJMSNotificationsPerformed() {
      ++this.totalJMSNotificationsPerformed;
      this.incrementTotalNotificationsPerformed();
   }

   void incrementTotalJMXNotificationsPerformed() {
      ++this.totalJMXNotificationsPerformed;
      this.incrementTotalNotificationsPerformed();
   }

   void incrementTotalLogEvaluationCycles() {
      ++this.totalLogEvaluationCycles;
   }

   void incrementTotalLogWatchesTriggered() {
      ++this.totalLogWatchesTriggered;
   }

   void incrementTotalLogWatchEvaluations(int newEvaluations) {
      this.totalLogWatchEvaluations += (long)newEvaluations;
   }

   void incrementTotalLogWatchEvaluationTime(long elapsedTime) {
      this.totalLogWatchEvaluationTime += elapsedTime;
      this.minLogWatchEvaluationTime = this.minLogWatchEvaluationTime <= 0L ? elapsedTime : Math.min(this.minLogWatchEvaluationTime, elapsedTime);
      this.maxLogWatchEvaluationTime = Math.max(this.maxLogWatchEvaluationTime, elapsedTime);
   }

   synchronized void incrementTotalNotificationsPerformed() {
      ++this.totalNotificationsPerformed;
   }

   void incrementTotalSMTPNotificationsPerformed() {
      ++this.totalSMTPNotificationsPerformed;
      this.incrementTotalNotificationsPerformed();
   }

   void incrementTotalSNMPNotificationsPerformed() {
      ++this.totalSNMPNotificationsPerformed;
      this.incrementTotalNotificationsPerformed();
   }

   synchronized void setCurrentActiveAlarmsCount(int currentCount) {
      this.currentActiveAlarmsCount = currentCount;
      this.maximumActiveAlarmsCount = Math.max(this.maximumActiveAlarmsCount, currentCount);
   }

   public long getMaxEventDataWatchEvaluationTime() {
      return this.maxEventDataWatchEvaluationTime;
   }

   public long getMaxHarvesterWatchEvaluationTime() {
      return this.maxHarvesterWatchEvaluationTime;
   }

   public long getMaxLogWatchEvaluationTime() {
      return this.maxLogWatchEvaluationTime;
   }

   public long getMinEventDataWatchEvaluationTime() {
      return this.minEventDataWatchEvaluationTime;
   }

   public long getMinHarvesterWatchEvaluationTime() {
      return this.minHarvesterWatchEvaluationTime;
   }

   public long getMinLogWatchEvaluationTime() {
      return this.minLogWatchEvaluationTime;
   }

   public long getTotalEventDataWatchEvaluationTime() {
      return this.totalEventDataWatchEvaluationTime;
   }

   public long getTotalHarvesterWatchEvaluationTime() {
      return this.totalHarvesterWatchEvaluationTime;
   }

   public long getTotalLogWatchEvaluationTime() {
      return this.totalLogWatchEvaluationTime;
   }
}
