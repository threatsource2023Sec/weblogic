package org.apache.openjpa.lib.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public class ReportingSQLException extends SQLException {
   private final transient Statement _stmnt;
   private final SQLException _sqle;

   public ReportingSQLException(SQLException sqle, Statement stmnt, String sql) {
      super(getExceptionMessage(sqle, stmnt, sql));
      this._sqle = sqle;
      this._stmnt = stmnt;
      this.setNextException(sqle);
   }

   public ReportingSQLException(SQLException sqle, String sql) {
      this(sqle, (Statement)null, sql);
   }

   public ReportingSQLException(SQLException sqle, Statement stmnt) {
      this(sqle, stmnt, (String)null);
   }

   public String getSQLState() {
      return this._sqle.getSQLState();
   }

   public int getErrorCode() {
      return this._sqle.getErrorCode();
   }

   public Statement getStatement() {
      return this._stmnt;
   }

   private static String getExceptionMessage(SQLException sqle, Statement stmnt, String sql) {
      try {
         if (stmnt != null) {
            return sqle.getMessage() + " {" + stmnt + "} " + "[code=" + sqle.getErrorCode() + ", state=" + sqle.getSQLState() + "]";
         } else {
            return sql != null ? sqle.getMessage() + " {" + sql + "} " + "[code=" + sqle.getErrorCode() + ", state=" + sqle.getSQLState() + "]" : sqle.getMessage() + " " + "[code=" + sqle.getErrorCode() + ", state=" + sqle.getSQLState() + "]";
         }
      } catch (Throwable var4) {
         return sqle.getMessage();
      }
   }
}
