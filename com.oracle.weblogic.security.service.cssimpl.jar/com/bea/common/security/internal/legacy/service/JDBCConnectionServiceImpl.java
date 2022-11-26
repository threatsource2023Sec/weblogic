package com.bea.common.security.internal.legacy.service;

import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.service.NamedSQLConnectionLookupService;
import com.bea.common.security.service.NamedSQLConnectionNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import weblogic.security.spi.JDBCConnectionService;
import weblogic.security.spi.JDBCConnectionServiceException;

public class JDBCConnectionServiceImpl implements JDBCConnectionService {
   private NamedSQLConnectionLookupService namedSQLConn;

   public JDBCConnectionServiceImpl(NamedSQLConnectionLookupService namedSQLConn) {
      this.namedSQLConn = namedSQLConn;
   }

   public Connection getConnection(String sqlConnectionName) throws SQLException, JDBCConnectionServiceException {
      try {
         return this.namedSQLConn.getConnection(sqlConnectionName);
      } catch (NamedSQLConnectionNotFoundException var3) {
         throw new JDBCConnectionServiceException(ServiceLogger.getCouldNotGetConnectionForSQLName(sqlConnectionName), var3);
      }
   }

   public void releaseConnection(Connection connection) throws SQLException {
      this.namedSQLConn.releaseConnection(connection);
   }
}
