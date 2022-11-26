package oracle.ucp.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public interface LabelableConnection {
   void applyConnectionLabel(String var1, String var2) throws SQLException;

   void removeConnectionLabel(String var1) throws SQLException;

   Properties getConnectionLabels() throws SQLException;

   Properties getUnmatchedConnectionLabels(Properties var1) throws SQLException;
}
