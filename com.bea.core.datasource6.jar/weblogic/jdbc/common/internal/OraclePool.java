package weblogic.jdbc.common.internal;

import java.sql.Connection;
import java.sql.SQLException;

public interface OraclePool {
   Class getDriverClass() throws ClassNotFoundException;

   void replayInitialize(Connection var1) throws SQLException;
}
