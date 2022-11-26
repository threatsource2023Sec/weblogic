package weblogic.jdbc.common.rac.internal;

import java.sql.Connection;
import oracle.jdbc.replay.ReplayStatistics;
import oracle.jdbc.replay.ReplayableConnection;
import oracle.jdbc.replay.ReplayableConnection.StatisticsReportType;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ReplayStatisticsSnapshot;

public class OracleReplayStatistics {
   public static ReplayStatisticsSnapshot getReplayStatistics(ConnectionEnv rce) {
      ReplayStatisticsSnapshot ret = null;
      Connection replayableConnection = rce.conn.jconn;
      if (!(replayableConnection instanceof ReplayableConnection)) {
         return null;
      } else {
         ReplayStatistics stats = ((ReplayableConnection)replayableConnection).getReplayStatistics(StatisticsReportType.FOR_CURRENT_CONNECTION);
         ret = new ReplayStatisticsSnapshot();
         ret.totalRequests = stats.getTotalRequests();
         ret.totalCompletedRequests = stats.getTotalCompletedRequests();
         ret.totalCalls = stats.getTotalCalls();
         ret.totalProtectedCalls = stats.getTotalProtectedCalls();
         ret.totalCallsAffectedByOutages = stats.getTotalCallsAffectedByOutages();
         ret.totalCallsTriggeringReplay = stats.getTotalCallsTriggeringReplay();
         ret.totalCallsAffectedByOutagesDuringReplay = stats.getTotalCallsAffectedByOutagesDuringReplay();
         ret.successfulReplayCount = stats.getSuccessfulReplayCount();
         ret.failedReplayCount = stats.getFailedReplayCount();
         ret.replayDisablingCount = stats.getReplayDisablingCount();
         ret.totalReplayAttempts = stats.getTotalReplayAttempts();
         return ret;
      }
   }

   public static void clearReplayStatistics(ConnectionEnv rce) {
      ReplayStatisticsSnapshot ret = null;
      Connection replayableConnection = rce.conn.jconn;
      if (replayableConnection instanceof ReplayableConnection) {
         ((ReplayableConnection)replayableConnection).clearReplayStatistics(StatisticsReportType.FOR_CURRENT_CONNECTION);
      }
   }
}
