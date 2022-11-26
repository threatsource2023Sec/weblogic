package com.bea.adaptive.mbean.typing;

public class RegistrationStatistics {
   private long totalProcessingTimeNanos;
   private long totalExecutions;
   private long totalProcessedRegistrationEvents;
   private long totalTimeMillis;
   private long totalTransientsCount;
   private long currentTransientsCount;
   private long currentIntervalTimeMillis = 1L;
   private int currentProcessedCount;
   private long maxTransientsRatePerInterval;
   private long totalEventsReceived;

   public long getAverageTransientsPerSec() {
      return this.totalTimeMillis > 0L ? this.totalTransientsCount / (this.totalTimeMillis / 1000L) : 0L;
   }

   public long getTotalProcessingTimeNanos() {
      return this.totalProcessingTimeNanos;
   }

   public long getAverageProcessingTimeNanos() {
      return this.totalExecutions > 0L ? this.totalProcessingTimeNanos / this.totalExecutions : 0L;
   }

   public long getTotalExecutions() {
      return this.totalExecutions;
   }

   public long getTotalProcessedRegistrationEvents() {
      return this.totalProcessedRegistrationEvents;
   }

   public long getTotalTransientsCount() {
      return this.totalTransientsCount;
   }

   public long getCurrentTransientsCount() {
      return this.currentTransientsCount;
   }

   public long getCurrentIntervalRatePerSec() {
      if (this.currentIntervalTimeMillis > 0L) {
         long intervalSecs = this.currentIntervalTimeMillis / 1000L;
         if (intervalSecs > 0L) {
            return (long)this.currentProcessedCount / intervalSecs;
         }
      }

      return 0L;
   }

   public long getCurrentTransientRatePerSec() {
      if (this.currentIntervalTimeMillis > 0L) {
         long intervalSecs = this.currentIntervalTimeMillis / 1000L;
         if (intervalSecs > 0L) {
            return this.currentTransientsCount / intervalSecs;
         }
      }

      return 0L;
   }

   public long getCurrentIntervalTimeMillis() {
      return this.currentIntervalTimeMillis;
   }

   public long getMaxTransientsPerInterval() {
      return this.maxTransientsRatePerInterval;
   }

   void updateStatistics(int processedCount, long processingTimeNanos, long totalTransientsHandled, long totalEvents, long timeDeltaMillis) {
      ++this.totalExecutions;
      this.totalEventsReceived = totalEvents;
      this.currentProcessedCount = processedCount;
      this.totalProcessedRegistrationEvents += (long)this.currentProcessedCount;
      this.totalProcessingTimeNanos += processingTimeNanos;
      this.currentIntervalTimeMillis = timeDeltaMillis;
      this.totalTimeMillis += this.currentIntervalTimeMillis;
      this.currentTransientsCount = totalTransientsHandled - this.totalTransientsCount;
      this.maxTransientsRatePerInterval = Math.max(this.currentTransientsCount, this.maxTransientsRatePerInterval);
      this.totalTransientsCount = totalTransientsHandled;
   }

   public long getTotalEventsReceived() {
      return this.totalEventsReceived;
   }

   public double getPercentEventsFiltered() {
      double percentFiltered = 0.0;
      if (this.totalEventsReceived > 0L) {
         long filteredEvents = this.totalEventsReceived - this.totalProcessedRegistrationEvents;
         percentFiltered = (double)filteredEvents / (double)this.totalEventsReceived * 100.0;
      }

      return percentFiltered;
   }
}
