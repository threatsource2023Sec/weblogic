package oracle.ucp.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionInitializationCallback {
   void initialize(Connection var1) throws SQLException;
}
