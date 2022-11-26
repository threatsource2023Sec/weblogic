package weblogic.jdbc.common.rac.internal;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import oracle.jdbc.OracleConnection;
import oracle.ucp.jdbc.JDBCConnectionPoolStatistics;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolXADataSource;
import weblogic.utils.StackTraceUtils;

public class UCPRuntime {
   private PoolDataSource pds;

   public UCPRuntime(DataSource ds) {
      try {
         this.pds = (PoolDataSource)ds;
      } catch (ClassCastException var3) {
         throw new RuntimeException("Invalid UCP driver class name - must be or extend oracle.ucp.jdbc.PoolDataSource");
      }
   }

   public int getCurrCapacity() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getTotalConnectionsCount();
   }

   public int getActiveConnectionsCurrentCount() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getBorrowedConnectionsCount();
   }

   public int getNumAvailable() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getAvailableConnectionsCount();
   }

   public long getReserveRequestCount() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1L : stats.getCumulativeConnectionBorrowedCount();
   }

   public int getActiveConnectionsAverageCount() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getAverageBorrowedConnectionsCount();
   }

   public int getCurrCapacityHighCount() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getPeakConnectionsCount();
   }

   public int getConnectionsTotalCount() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getConnectionsCreatedCount();
   }

   public int getNumUnavailable() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1 : stats.getTotalConnectionsCount() - stats.getAvailableConnectionsCount();
   }

   public long getWaitingForConnectionSuccessTotal() {
      JDBCConnectionPoolStatistics stats = this.pds.getStatistics();
      return stats == null ? -1L : stats.getCumulativeSuccessfulConnectionWaitCount();
   }

   public String testPool() {
      XAConnection xaconn = null;
      Connection conn = null;

      PoolXADataSource pxads;
      try {
         String sql;
         try {
            boolean validate = this.pds.getValidateConnectionOnBorrow();
            sql = this.pds.getSQLForValidateConnection();
            if (this.pds instanceof PoolXADataSource) {
               pxads = (PoolXADataSource)this.pds;
               xaconn = pxads.getXAConnection();
               conn = xaconn.getConnection();
            } else {
               conn = this.pds.getConnection();
            }

            if (conn != null) {
               if (validate) {
                  pxads = null;
                  return pxads;
               }

               if (!(conn instanceof OracleConnection)) {
                  String var37 = "UCP not configured to validate connections";
                  return var37;
               }

               int flag = ((OracleConnection)conn).pingDatabase();
               String var6;
               switch (flag) {
                  case -3:
                     var6 = "OracleConnection.pingDatabase() returned DATABASE_TIMEOUT";
                     return var6;
                  case -2:
                     var6 = "OracleConnection.pingDatabase() returned DATABASE_NOTOK";
                     return var6;
                  case -1:
                     var6 = "OracleConnection.pingDatabase() returned DATABASE_CLOSED";
                     return var6;
                  case 0:
                     var6 = null;
                     return var6;
                  default:
                     var6 = "OracleConnection.pingDatabase() returned unexpected value " + flag;
                     return var6;
               }
            }

            pxads = null;
         } catch (SQLException var34) {
            sql = StackTraceUtils.throwable2StackTrace(var34);
            return sql;
         }
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var33) {
            }
         }

         if (xaconn != null) {
            try {
               xaconn.close();
            } catch (SQLException var32) {
            }
         }

      }

      return pxads;
   }
}
