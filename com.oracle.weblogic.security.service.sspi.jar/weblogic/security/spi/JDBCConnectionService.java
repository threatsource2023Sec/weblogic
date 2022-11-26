package weblogic.security.spi;

import java.sql.Connection;
import java.sql.SQLException;

public interface JDBCConnectionService {
   Connection getConnection(String var1) throws SQLException, JDBCConnectionServiceException;

   void releaseConnection(Connection var1) throws SQLException;
}
