package weblogic.jdbc.extensions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import oracle.ucp.jdbc.HarvestableConnection;
import oracle.ucp.jdbc.LabelableConnection;

public interface WLConnection extends LabelableConnection, HarvestableConnection {
   void clearStatementCache() throws SQLException;

   Connection getVendorConnection() throws SQLException;

   boolean clearCallableStatement(String var1) throws SQLException;

   boolean clearCallableStatement(String var1, int var2, int var3) throws SQLException;

   boolean clearPreparedStatement(String var1) throws SQLException;

   boolean clearPreparedStatement(String var1, int var2, int var3) throws SQLException;

   String getPoolName();

   void setFailed() throws SQLException;

   boolean isValid(int var1) throws SQLException;

   boolean isValid() throws SQLException;

   Connection getVendorConnectionSafe() throws SQLException;

   void applyConnectionLabel(String var1, String var2) throws SQLException;

   void removeConnectionLabel(String var1) throws SQLException;

   Properties getConnectionLabels() throws SQLException;

   Properties getUnmatchedConnectionLabels(Properties var1) throws SQLException;

   boolean isSameInstance(Connection var1);

   int getReplayAttemptCount();
}
