package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class DatabaseMetaData extends JDBCWrapperImpl {
   protected java.sql.DatabaseMetaData dbmd = null;
   protected Connection conn = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         java.sql.ResultSet var5;
         try {
            if (!(ret instanceof java.sql.ResultSet)) {
               Object var11 = ret;
               return var11;
            }

            java.sql.ResultSet rs_ret = ResultSet.makeResultSet((java.sql.ResultSet)ret, this.conn, (Statement)null);
            if (this.conn != null) {
               this.conn.addResultSet((ResultSet)rs_ret);
            }

            var5 = rs_ret;
         } catch (Exception var9) {
            JDBCLogger.logStackTrace(var9);
            return ret;
         } finally {
            super.postInvocationHandler(methodName, params, ret);
         }

         return var5;
      }
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      this.conn.checkConnection();
   }

   public ConnectionEnv getConnectionEnv() {
      return this.conn == null ? null : this.conn.getConnectionEnv();
   }

   public void init(java.sql.DatabaseMetaData dbmd, Connection conn) {
      this.dbmd = dbmd;
      this.conn = conn;
   }

   public static java.sql.DatabaseMetaData makeDatabaseMetaData(java.sql.DatabaseMetaData dbmd, Connection conn) {
      if (conn != null && conn instanceof Connection) {
         ConnectionEnv cc = conn.getConnectionEnv();
         if (cc != null && !cc.isWrapJdbc()) {
            return dbmd;
         }
      }

      DatabaseMetaData wrapperDatabaseMetaData = (DatabaseMetaData)JDBCWrapperFactory.getWrapper(8, dbmd, false);
      wrapperDatabaseMetaData.init(dbmd, conn);
      return (java.sql.DatabaseMetaData)wrapperDatabaseMetaData;
   }

   public java.sql.Connection getConnection() throws SQLException {
      String methodName = "getConnection";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         super.postInvocationHandler(methodName, params, this.conn);
      } catch (Exception var6) {
         Exception e = var6;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (Exception var5) {
            throw new SQLException(var5.getMessage());
         }
      }

      return (java.sql.Connection)this.conn;
   }

   public java.sql.ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
      java.sql.ResultSet ret = null;
      String methodName = "getPseudoColumns";
      Object[] params = new Object[]{catalog, schemaPattern, tableNamePattern, columnNamePattern};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.dbmd.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public boolean generatedKeyAlwaysReturned() throws SQLException {
      boolean ret = false;
      String methodName = "generatedKeyAlwaysReturned";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.dbmd.generatedKeyAlwaysReturned();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }
}
