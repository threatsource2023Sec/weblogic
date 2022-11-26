package weblogic.jdbc.wrapper;

import java.sql.NClob;
import java.sql.SQLException;
import java.sql.SQLXML;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ProfileClosedUsage;
import weblogic.jdbc.extensions.DriverInterceptor;

public class ResultSet extends JDBCWrapperImpl {
   private java.sql.ResultSet rs = null;
   private Connection conn = null;
   private Statement stmt = null;
   private volatile boolean isClosed = false;
   private ProfileClosedUsage profileClosedUsage = new ProfileClosedUsage();

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof NClob) {
               ret = WrapperNClob.makeNClob((NClob)ret, (java.sql.Connection)this.conn);
            } else if (ret instanceof java.sql.Clob) {
               ret = Clob.makeClob((java.sql.Clob)ret, (java.sql.Connection)this.conn);
            } else if (ret instanceof java.sql.Blob) {
               ret = Blob.makeBlob((java.sql.Blob)ret, (java.sql.Connection)this.conn);
            } else if (ret instanceof java.sql.Ref) {
               ret = Ref.makeRef((java.sql.Ref)ret, (java.sql.Connection)this.conn);
            } else if (ret instanceof java.sql.Array) {
               ret = Array.makeArray((java.sql.Array)ret, (java.sql.Connection)this.conn);
            } else if (ret instanceof SQLXML) {
               ret = WrapperSQLXML.makeSQLXML((SQLXML)ret, (java.sql.Connection)this.conn);
            } else if (ret instanceof java.sql.ResultSetMetaData) {
               ret = ResultSetMetaData.makeResultSetMetaData((java.sql.ResultSetMetaData)ret, this.conn);
            }
         } finally {
            super.postInvocationHandler(methodName, params, ret);
         }

         return ret;
      }
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      if (this.JDBCSQLDebug) {
         String s = methodName + "(";
         if (params != null && params.length > 0) {
            for(int i = 0; i < params.length - 1; ++i) {
               s = s + params[i] + ", ";
            }

            s = s + params[params.length - 1] + ")";
         } else {
            s = s + ")";
         }

         this.trace(s);
      }

      ConnectionEnv cc = this.getConnectionEnv();
      if (cc != null) {
         DriverInterceptor cb = cc.getConnectionPool().getDriverInterceptor();
         if (cb != null) {
            cb.preInvokeCallback(this.vendorObj, methodName, params);
         }

         cc.setInUse();
      }

      if (!methodName.equals("isClosed")) {
         this.checkResultSet();
         if (cc == null || cc.isWrapTypes()) {
            this.conn.checkConnection(cc);
         }
      }

   }

   public ConnectionEnv getConnectionEnv() {
      return this.conn == null ? null : this.conn.getConnectionEnv();
   }

   public void init(java.sql.ResultSet rs, Connection conn, Statement stmt) {
      this.rs = rs;
      this.conn = conn;
      this.stmt = stmt;
      ConnectionEnv ce = this.getConnectionEnv();
      if (ce != null) {
         this.profileClosedUsage.setProfiler(ce.profiler);
      }

   }

   public static java.sql.ResultSet makeResultSet(java.sql.ResultSet rs, Connection conn, Statement stmt) {
      if (rs == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = conn.getConnectionEnv();
            if (cc != null && !cc.isWrapJdbc()) {
               return rs;
            }
         }

         ResultSet wrapperResultSet = (ResultSet)JDBCWrapperFactory.getWrapper(6, rs, false);
         wrapperResultSet.init(rs, conn, stmt);
         if (stmt != null) {
            stmt.addResultSet(wrapperResultSet);
         }

         return (java.sql.ResultSet)wrapperResultSet;
      }
   }

   public void checkResultSet() throws SQLException {
      if (this.isClosed) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException("Result set already closed");
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else if (this.rs == null) {
         throw new SQLException("Internal error: no result set available");
      }
   }

   public boolean isClosed() throws SQLException {
      boolean ret = this.isClosed;
      String methodName = "isClosed";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (!this.isClosed) {
            ret = this.rs.isClosed();
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public boolean internalIsClosed() {
      return this.isClosed;
   }

   public void internalClose(boolean notify) throws SQLException {
      if (!this.isClosed) {
         synchronized(this) {
            if (this.isClosed) {
               return;
            }

            this.isClosed = true;
         }

         this.profileClosedUsage.saveWhereClosed();
         SQLException sqle = null;

         try {
            try {
               this.rs.close();
            } catch (SQLException var9) {
               throw var9;
            }

            this.rs = null;
         } catch (SQLException var10) {
            sqle = var10;
         } finally {
            if (notify && this.stmt != null) {
               this.stmt.removeResultSet(this);
            }

            if (notify && this.conn != null) {
               this.conn.removeResultSet(this);
            }

         }

         this.stmt = null;
         if (sqle != null) {
            throw sqle;
         }
      }
   }

   public void close() throws SQLException {
      String methodName = "close";
      Object[] params = new Object[0];

      try {
         super.preInvocationHandler(methodName, params);
         if (this.isClosed) {
            super.postInvocationHandler(methodName, params, (Object)null);
            return;
         }

         this.internalClose(true);
         super.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

   }

   public java.sql.Statement getStatement() throws SQLException {
      String methodName = "getStatement";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         super.postInvocationHandler(methodName, params, this.stmt);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

      return (java.sql.Statement)this.stmt;
   }

   public void updateClob(int columnIndex, java.sql.Clob clob) throws SQLException {
      String methodName = "updateClob";
      Object[] params = new Object[]{columnIndex, clob};

      try {
         this.preInvocationHandler(methodName, params);
         if (clob instanceof JDBCWrapperImpl) {
            this.rs.updateClob(columnIndex, (java.sql.Clob)((JDBCWrapperImpl)clob).getVendorObj());
         } else {
            this.rs.updateClob(columnIndex, clob);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateClob(String columnLabel, java.sql.Clob clob) throws SQLException {
      String methodName = "updateClob";
      Object[] params = new Object[]{columnLabel, clob};

      try {
         this.preInvocationHandler(methodName, params);
         if (clob instanceof JDBCWrapperImpl) {
            this.rs.updateClob(columnLabel, (java.sql.Clob)((JDBCWrapperImpl)clob).getVendorObj());
         } else {
            this.rs.updateClob(columnLabel, clob);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateBlob(int columnIndex, java.sql.Blob blob) throws SQLException {
      String methodName = "updateBlob";
      Object[] params = new Object[]{columnIndex, blob};

      try {
         this.preInvocationHandler(methodName, params);
         if (blob instanceof JDBCWrapperImpl) {
            this.rs.updateBlob(columnIndex, (java.sql.Blob)((JDBCWrapperImpl)blob).getVendorObj());
         } else {
            this.rs.updateBlob(columnIndex, blob);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateBlob(String columnLabel, java.sql.Blob blob) throws SQLException {
      String methodName = "updateBlob";
      Object[] params = new Object[]{columnLabel, blob};

      try {
         this.preInvocationHandler(methodName, params);
         if (blob instanceof JDBCWrapperImpl) {
            this.rs.updateBlob(columnLabel, (java.sql.Blob)((JDBCWrapperImpl)blob).getVendorObj());
         } else {
            this.rs.updateBlob(columnLabel, blob);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
      String methodName = "updateNClob";
      Object[] params = new Object[]{columnIndex, nClob};

      try {
         this.preInvocationHandler(methodName, params);
         if (nClob instanceof JDBCWrapperImpl) {
            this.rs.updateNClob(columnIndex, (NClob)((JDBCWrapperImpl)nClob).getVendorObj());
         } else {
            this.rs.updateNClob(columnIndex, nClob);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
      String methodName = "updateNClob";
      Object[] params = new Object[]{columnLabel, nClob};

      try {
         this.preInvocationHandler(methodName, params);
         if (nClob instanceof JDBCWrapperImpl) {
            this.rs.updateNClob(columnLabel, (NClob)((JDBCWrapperImpl)nClob).getVendorObj());
         } else {
            this.rs.updateNClob(columnLabel, nClob);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateSQLXML(int columnIndex, SQLXML sqlxml) throws SQLException {
      String methodName = "updateSQLXML";
      Object[] params = new Object[]{columnIndex, sqlxml};

      try {
         this.preInvocationHandler(methodName, params);
         if (sqlxml instanceof JDBCWrapperImpl) {
            this.rs.updateSQLXML(columnIndex, (SQLXML)((JDBCWrapperImpl)sqlxml).getVendorObj());
         } else {
            this.rs.updateSQLXML(columnIndex, sqlxml);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void updateSQLXML(String columnLabel, SQLXML sqlxml) throws SQLException {
      String methodName = "updateSQLXML";
      Object[] params = new Object[]{columnLabel, sqlxml};

      try {
         this.preInvocationHandler(methodName, params);
         if (sqlxml instanceof JDBCWrapperImpl) {
            this.rs.updateSQLXML(columnLabel, (SQLXML)((JDBCWrapperImpl)sqlxml).getVendorObj());
         } else {
            this.rs.updateSQLXML(columnLabel, sqlxml);
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }
}
