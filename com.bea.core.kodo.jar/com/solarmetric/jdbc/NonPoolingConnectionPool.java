package com.solarmetric.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class NonPoolingConnectionPool implements ConnectionPool {
   private PoolingDataSource _ds = null;

   public void setDataSource(PoolingDataSource ds) {
      this._ds = ds;
   }

   public PoolingDataSource getDataSource() {
      return this._ds;
   }

   public Connection getConnection(ConnectionRequestInfo cri) throws SQLException {
      Connection conn = this._ds.newConnection(cri);
      PoolConnection poolConn = new PoolConnection(conn, cri, this);
      if (this._ds.getLogs().isJDBCEnabled()) {
         this._ds.getLogs().logJDBC("open: " + this._ds.getConnectionURL() + " (" + cri.getUsername() + ")", poolConn);
      }

      return poolConn;
   }

   public void returnConnection(Connection conn) {
      PoolConnection poolConn = (PoolConnection)conn;
      this._ds.getLogs().logJDBC("close", poolConn);
      poolConn.free();

      try {
         poolConn.getDelegate().close();
      } catch (SQLException var4) {
      }

   }

   public void close() {
   }
}
