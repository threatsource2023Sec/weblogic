package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.NoneLogFactory;
import org.apache.openjpa.lib.util.Localizer;

public class DataSourceLogs {
   private static final Localizer _loc = Localizer.forPackage(DataSourceLogs.class);
   private Log _jdbcLog = null;
   private Log _sqlLog = null;

   public Log getJDBCLog() {
      return (Log)(this._jdbcLog == null ? NoneLogFactory.NoneLog.getInstance() : this._jdbcLog);
   }

   public void setJDBCLog(Log log) {
      this._jdbcLog = log;
   }

   public boolean isJDBCEnabled() {
      return this._jdbcLog != null && this._jdbcLog.isTraceEnabled();
   }

   public Log getSQLLog() {
      return (Log)(this._sqlLog == null ? NoneLogFactory.NoneLog.getInstance() : this._sqlLog);
   }

   public void setSQLLog(Log log) {
      this._sqlLog = log;
   }

   public boolean isSQLEnabled() {
      return this._sqlLog != null && this._sqlLog.isTraceEnabled();
   }

   public void logJDBC(String msg, Connection conn) {
      log(msg, conn, this._jdbcLog);
   }

   public void logJDBC(String msg, long startTime, Connection conn) {
      log(msg, conn, this._jdbcLog, startTime);
   }

   public void logSQL(String msg, Connection conn) {
      log(msg, conn, this._sqlLog);
   }

   public void logSQL(String msg, long startTime, Connection conn) {
      log(msg, conn, this._sqlLog, startTime);
   }

   private static void log(String msg, Connection conn, Log log) {
      log(msg, conn, log, -1L);
   }

   private static void log(String msg, Connection conn, Log log, long startTime) {
      if (log != null && log.isTraceEnabled()) {
         long totalTime = -1L;
         if (startTime != -1L) {
            totalTime = System.currentTimeMillis() - startTime;
         }

         StringBuffer buf = new StringBuffer(25 + msg.length());
         buf.append("<t ").append(Thread.currentThread().hashCode());
         if (conn != null) {
            buf.append(", ").append(conn);
         }

         buf.append("> ");
         if (totalTime != -1L) {
            buf.append("[").append(totalTime).append(" ms] ");
         }

         buf.append(msg);
         log.trace(_loc.get("datasource-trace-data", (Object)buf.toString()));
      }
   }
}
