package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Properties;

public interface OracleHelper {
   boolean isReplayDriver();

   void replayBeginRequest(ConnectionEnv var1, int var2) throws SQLException;

   void replayEndRequest(ConnectionEnv var1) throws SQLException;

   Properties getConnectionInfo(ConnectionEnv var1);

   ReplayStatisticsSnapshot getReplayStatistics(ConnectionEnv var1);

   void clearReplayStatistics(ConnectionEnv var1);

   void registerConnectionInitializationCallback(ConnectionEnv var1) throws SQLException;

   void setProxyObject(Object var1, ConnectionEnv var2);

   ConnectionEnv getProxyObject(Object var1);

   String getInstanceName(ConnectionEnv var1);

   String getPDBName(ConnectionEnv var1);

   String getServiceName(ConnectionEnv var1);

   Object getLogicalTransactionId(Object var1) throws SQLException;
}
