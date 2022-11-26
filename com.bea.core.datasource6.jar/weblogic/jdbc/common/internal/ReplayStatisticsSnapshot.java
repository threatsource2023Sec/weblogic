package weblogic.jdbc.common.internal;

public class ReplayStatisticsSnapshot {
   public long totalRequests = -1L;
   public long totalCompletedRequests = -1L;
   public long totalCalls = -1L;
   public long totalProtectedCalls = -1L;
   public long totalCallsAffectedByOutages = -1L;
   public long totalCallsTriggeringReplay = -1L;
   public long totalCallsAffectedByOutagesDuringReplay = -1L;
   public long successfulReplayCount = -1L;
   public long failedReplayCount = -1L;
   public long replayDisablingCount = -1L;
   public long totalReplayAttempts = -1L;

   public synchronized void initialize(long value) {
      this.totalRequests = value;
      this.totalCompletedRequests = value;
      this.totalCalls = value;
      this.totalProtectedCalls = value;
      this.totalCallsAffectedByOutages = value;
      this.totalCallsTriggeringReplay = value;
      this.totalCallsAffectedByOutagesDuringReplay = value;
      this.successfulReplayCount = value;
      this.failedReplayCount = value;
      this.replayDisablingCount = value;
      this.totalReplayAttempts = value;
   }

   public synchronized void increment(ReplayStatisticsSnapshot stats) {
      if (stats != null) {
         this.totalRequests += stats.totalRequests;
         this.totalCompletedRequests += stats.totalCompletedRequests;
         this.totalCalls += stats.totalCalls;
         this.totalProtectedCalls += stats.totalProtectedCalls;
         this.totalCallsAffectedByOutages += stats.totalCallsAffectedByOutages;
         this.totalCallsTriggeringReplay += stats.totalCallsTriggeringReplay;
         this.totalCallsAffectedByOutagesDuringReplay += stats.totalCallsAffectedByOutagesDuringReplay;
         this.successfulReplayCount += stats.successfulReplayCount;
         this.failedReplayCount += stats.failedReplayCount;
         this.replayDisablingCount += stats.replayDisablingCount;
         this.totalReplayAttempts += stats.totalReplayAttempts;
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      sb.append("totalRequests: ").append(this.totalRequests).append(", ");
      sb.append("totalCompletedRequests: ").append(this.totalCompletedRequests).append(", ");
      sb.append("totalCalls: ").append(this.totalCalls).append(", ");
      sb.append("totalProtectedCalls: ").append(this.totalProtectedCalls).append(", ");
      sb.append("totalCallsAffectedByOutages: ").append(this.totalCallsAffectedByOutages).append(", ");
      sb.append("totalCallsTriggeringReplay: ").append(this.totalCallsTriggeringReplay).append(", ");
      sb.append("totalCallsAffectedByOutagesDuringReplay: ").append(this.totalCallsAffectedByOutagesDuringReplay).append(", ");
      sb.append("successfulReplayCount: ").append(this.successfulReplayCount).append(", ");
      sb.append("failedReplayCount: ").append(this.failedReplayCount).append(", ");
      sb.append("replayDisablingCount: ").append(this.replayDisablingCount).append(", ");
      sb.append("totalReplayAttempts: ").append(this.totalReplayAttempts);
      sb.append("}");
      return sb.toString();
   }
}
