package weblogic.jdbc.extensions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;

public interface WLDataSource {
   Connection getConnection(Properties var1) throws SQLException;

   Connection getConnection(String var1, String var2, Properties var3) throws SQLException;

   void registerConnectionLabelingCallback(ConnectionLabelingCallback var1) throws SQLException;

   void removeConnectionLabelingCallback() throws SQLException;

   void registerConnectionInitializationCallback(ConnectionInitializationCallback var1) throws SQLException;

   void unregisterConnectionInitializationCallback() throws SQLException;

   Connection getConnectionToInstance(String var1) throws SQLException;

   Connection getConnectionToInstance(Connection var1) throws SQLException;

   Connection createConnection(Properties var1) throws SQLException;

   Connection createConnectionToInstance(String var1, Properties var2) throws SQLException;
}
