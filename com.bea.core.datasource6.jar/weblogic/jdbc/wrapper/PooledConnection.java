package weblogic.jdbc.wrapper;

import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import weblogic.common.resourcepool.ResourceUnusableException;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.PoolUnavailableSQLException;
import weblogic.utils.StackTraceUtils;

public class PooledConnection extends JDBCWrapperImpl implements javax.sql.PooledConnection, java.sql.Connection {
   private javax.sql.PooledConnection pooledConn;
   private java.sql.Connection connHandle;
   private ConnectionEnv ce;
   private boolean defaultReadOnly;
   private String defaultCatalog;
   private boolean isClosed = false;
   private boolean oracleDriver = false;

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
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

      this.getConnection();
   }

   public void preInvocationHandlerNoCheck(String methodName, Object[] params) throws Exception {
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
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

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      super.postInvocationHandler(methodName, params, ret);
      if (this.ce != null) {
         this.ce.setNotInUse();
      }

      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         String s = methodName + " returns";
         if (ret != null) {
            s = s + " " + ret;
         }

         this.trace(s);
      }

      return ret;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws SQLException {
      this.removeConnFromPoolIfFatalError(t, this.getConnectionEnv());
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         String s = methodName + "(";
         if (params != null && params.length > 0) {
            for(int i = 0; i < params.length - 1; ++i) {
               s = s + params[i] + ", ";
            }

            s = s + params[params.length - 1] + ") throws ";
         } else {
            s = s + "unknown) throws: ";
         }

         s = s + StackTraceUtils.throwable2StackTrace(t);
         this.trace(s);
      }

      ConnectionEnv cc = this.getConnectionEnv();
      if (cc != null) {
         cc.resetLastSuccessfulConnectionUse();
      }

      if (t instanceof ResourceUnusableException) {
         throw new PoolUnavailableSQLException(t.getMessage());
      } else if (t instanceof SQLException) {
         throw (SQLException)t;
      } else if (t instanceof SecurityException) {
         throw (SecurityException)t;
      } else {
         throw new SQLException(methodName + ", Exception = " + t.getMessage());
      }
   }

   public void init(javax.sql.PooledConnection pooledConn, ConnectionEnv ce, java.sql.Connection connHandle) {
      this.pooledConn = pooledConn;
      this.ce = ce;
      this.connHandle = connHandle;
      if (connHandle != null && connHandle.getClass().getName().startsWith("oracle.jdbc")) {
         this.oracleDriver = true;
      }

      this.initConnState();
   }

   public void cleanup(boolean doingClose) {
      this.resetConnState();
      if (doingClose) {
         this.closeConnHandle();
      }

   }

   public void destroy() {
      if (!this.isClosed) {
         this.isClosed = true;

         try {
            this.closeConnHandle();
         } catch (Throwable var3) {
         }

         try {
            this.pooledConn.close();
         } catch (Throwable var2) {
         }

      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[").append(this.pooledConn.toString()).append(", connHandle=").append(this.connHandle).append("]");
      return sb.toString();
   }

   public ConnectionEnv getConnectionEnv() {
      return this.ce;
   }

   public void trace(String s) {
      JdbcDebug.JDBCCONN.debug("[" + this + "] " + s);
   }

   public void trace(String s, Exception e) {
      JdbcDebug.JDBCCONN.debug("[" + this + "] " + s);
   }

   public void addConnectionEventListener(ConnectionEventListener listener) {
      String methodName = "addConnectionEventListener";
      Object[] params = new Object[]{listener};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.pooledConn.addConnectionEventListener(listener);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         Exception e = var7;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (Exception var6) {
         }
      }

   }

   public void removeConnectionEventListener(ConnectionEventListener listener) {
      String methodName = "removeConnectionEventListener";
      Object[] params = new Object[]{listener};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.pooledConn.removeConnectionEventListener(listener);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         Exception e = var7;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (Exception var6) {
         }
      }

   }

   public java.sql.Connection getConnection() throws SQLException {
      if (this.isClosed) {
         throw new SQLException("PooledConnection is closed");
      } else {
         if (this.connHandle == null) {
            this.connHandle = this.pooledConn.getConnection();
            this.vendorObj = this.connHandle;
         }

         return this.connHandle;
      }
   }

   public void close() throws SQLException {
      if (!this.isClosed) {
         String methodName = "close";
         Object[] params = new Object[0];

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            this.isClosed = true;
            this.cleanup(true);
            this.pooledConn.close();
            this.postInvocationHandler(methodName, params, (Object)null);
         } catch (Exception var4) {
            this.invocationExceptionHandler(methodName, params, var4);
         }

      }
   }

   public java.sql.Statement createStatement() throws SQLException {
      java.sql.Statement stmt = null;
      String methodName = "createStatement";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         stmt = this.getConnection().createStatement();
         this.postInvocationHandler(methodName, params, stmt);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return stmt;
   }

   public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
      java.sql.PreparedStatement pstmt = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         pstmt = this.getConnection().prepareStatement(sql);
         this.postInvocationHandler(methodName, params, pstmt);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return pstmt;
   }

   public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
      java.sql.CallableStatement stmt = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         stmt = this.getConnection().prepareCall(sql);
         this.postInvocationHandler(methodName, params, stmt);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return stmt;
   }

   public boolean isClosed() throws SQLException {
      String methodName = "isClosed";
      Object[] params = new Object[0];
      boolean ret = this.pooledConn == null || this.connHandle == null;

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         if (!ret && this.oracleDriver) {
            ret = this.connHandle.isClosed();
            if (ret) {
               try {
                  this.close();
               } catch (Exception var5) {
               }
            }
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public void setTransactionIsolation(int level) throws SQLException {
      int currentTransactionIsolation = false;
      String methodName = "setTransactionIsolation";
      Object[] params = new Object[]{level};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         java.sql.Connection conn = this.getConnection();
         int currentTransactionIsolation = conn.getTransactionIsolation();
         if (level != currentTransactionIsolation) {
            conn.setTransactionIsolation(level);
            if (this.ce != null) {
               this.ce.setDirtyIsolationLevel(level);
            }
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      java.sql.Statement stmt = null;
      String methodName = "createStatement";
      Object[] params = new Object[]{resultSetType, resultSetConcurrency};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         stmt = this.getConnection().createStatement(resultSetType, resultSetConcurrency);
         this.postInvocationHandler(methodName, params, stmt);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return stmt;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      java.sql.PreparedStatement pstmt = null;
      String methodName = "preparedStatement";
      Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         pstmt = this.getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
         this.postInvocationHandler(methodName, params, pstmt);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return pstmt;
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      java.sql.CallableStatement stmt = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         stmt = this.getConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
         this.postInvocationHandler(methodName, params, stmt);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return stmt;
   }

   public void setAutoCommit(boolean autoCommit) throws SQLException {
      String methodName = "setAutoCommit";
      Object[] params = new Object[]{autoCommit};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setAutoCommit(autoCommit);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public void commit() throws SQLException {
      String methodName = "commit";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().commit();
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

   }

   public void rollback() throws SQLException {
      String methodName = "rollback";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().rollback();
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

   }

   public void setReadOnly(boolean readOnly) throws SQLException {
      String methodName = "setReadOnly";
      Object[] params = new Object[]{readOnly};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setReadOnly(readOnly);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public boolean getAutoCommit() throws SQLException {
      boolean flag = false;
      String methodName = "getAutoCommit";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         flag = this.getConnection().getAutoCommit();
         this.postInvocationHandler(methodName, params, flag);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return flag;
   }

   public String nativeSQL(String sql) throws SQLException {
      String ret = null;
      String methodName = "nativeSQL";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().nativeSQL(sql);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public java.sql.DatabaseMetaData getMetaData() throws SQLException {
      java.sql.DatabaseMetaData ret = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getMetaData();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public boolean isReadOnly() throws SQLException {
      boolean ret = false;
      String methodName = "isReadOnly";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().isReadOnly();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setCatalog(String catalog) throws SQLException {
      String methodName = "setCatalog";
      Object[] params = new Object[]{catalog};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setCatalog(catalog);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public String getCatalog() throws SQLException {
      String ret = null;
      String methodName = "getCatalog";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getCatalog();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Map getTypeMap() throws SQLException {
      Map ret = null;
      String methodName = "getTypeMap";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getTypeMap();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setTypeMap(Map map) throws SQLException {
      String methodName = "setTypeMap";
      Object[] params = new Object[]{map};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setTypeMap(map);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public int getTransactionIsolation() throws SQLException {
      int ret = 0;
      String methodName = "getTransactionIsolation";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getTransactionIsolation();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public SQLWarning getWarnings() throws SQLException {
      SQLWarning ret = null;
      String methodName = "getWarnings";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getWarnings();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void clearWarnings() throws SQLException {
      String methodName = "clearWarnings";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().clearWarnings();
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

   }

   public void rollback(Savepoint s) throws SQLException {
      String methodName = "rollback";
      Object[] params = new Object[]{s};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().rollback(s);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public java.sql.PreparedStatement prepareStatement(String a, int b, int c, int d) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{a, b, c, d};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().prepareStatement(a, b, c, d);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String a, int b) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{a, b};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().prepareStatement(a, b);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String a, int[] b) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{a, b};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().prepareStatement(a, b);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.PreparedStatement prepareStatement(String a, String[] b) throws SQLException {
      java.sql.PreparedStatement ret = null;
      String methodName = "prepareStatement";
      Object[] params = new Object[]{a, b};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().prepareStatement(a, b);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.CallableStatement prepareCall(String a, int b, int c, int d) throws SQLException {
      java.sql.CallableStatement ret = null;
      String methodName = "prepareCall";
      Object[] params = new Object[]{a, b, c, d};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().prepareCall(a, b, c, d);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var9) {
         this.invocationExceptionHandler(methodName, params, var9);
      }

      return ret;
   }

   public int getHoldability() throws SQLException {
      return this.getConnection().getHoldability();
   }

   public void setHoldability(int a) throws SQLException {
      String methodName = "getHoldability";
      Object[] params = new Object[]{a};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setHoldability(a);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public java.sql.Statement createStatement(int a, int b, int c) throws SQLException {
      java.sql.Statement ret = null;
      String methodName = "createStatement";
      Object[] params = new Object[]{a, b, c};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createStatement(a, b, c);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public Savepoint setSavepoint() throws SQLException {
      Savepoint ret = null;
      String methodName = "setSavepoint";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().setSavepoint();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Savepoint setSavepoint(String a) throws SQLException {
      Savepoint ret = null;
      String methodName = "setSavepoint";
      Object[] params = new Object[]{a};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().setSavepoint(a);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public void releaseSavepoint(Savepoint a) throws SQLException {
      String methodName = "releaseSavepoint";
      Object[] params = new Object[]{a};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().releaseSavepoint(a);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   private void initConnState() {
      try {
         java.sql.Connection conn = this.getConnection();

         try {
            this.defaultReadOnly = conn.isReadOnly();
         } catch (Exception var4) {
         }

         try {
            this.defaultCatalog = conn.getCatalog();
         } catch (Exception var3) {
         }
      } catch (Exception var5) {
      }

   }

   private void resetConnState() {
      java.sql.Connection conn = this.connHandle;
      if (conn != null) {
         try {
            if (conn.isReadOnly() != this.defaultReadOnly) {
               conn.setReadOnly(this.defaultReadOnly);
            }
         } catch (SQLException var4) {
         }

         try {
            if (this.defaultCatalog != null && conn.getCatalog() != null && !conn.getCatalog().equals(this.defaultCatalog)) {
               conn.setCatalog(this.defaultCatalog);
            }
         } catch (SQLException var3) {
         }
      }

   }

   private void closeConnHandle() {
      if (this.connHandle != null) {
         try {
            this.connHandle.close();
         } catch (Exception var2) {
         }

         this.connHandle = null;
      }

   }

   public void addStatementEventListener(StatementEventListener statementeventlistener) {
   }

   public void removeStatementEventListener(StatementEventListener statementeventlistener) {
   }

   public java.sql.Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      java.sql.Array ret = null;
      String methodName = "createArrayOf";
      Object[] params = new Object[]{typeName, elements};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createArrayOf(typeName, elements);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public java.sql.Blob createBlob() throws SQLException {
      java.sql.Blob ret = null;
      String methodName = "createBlob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createBlob();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public java.sql.Clob createClob() throws SQLException {
      java.sql.Clob ret = null;
      String methodName = "createClob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createClob();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public NClob createNClob() throws SQLException {
      NClob ret = null;
      String methodName = "createNClob";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createNClob();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public SQLXML createSQLXML() throws SQLException {
      SQLXML ret = null;
      String methodName = "createSQLXML";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createSQLXML();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public java.sql.Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      java.sql.Struct ret = null;
      String methodName = "createStruct";
      Object[] params = new Object[]{typeName, attributes};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().createStruct(typeName, attributes);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public boolean isValid(int timeout) throws SQLException {
      boolean ret = false;
      String methodName = "isValid";
      if (timeout < 0) {
         throw new SQLException("timeout must not be less than 0");
      } else {
         Object[] params = new Object[]{timeout};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            ret = this.getConnection().isValid(timeout);
            this.postInvocationHandler(methodName, params, ret);
            return ret;
         } catch (Exception var6) {
            if (var6 instanceof SQLException) {
               throw (SQLException)var6;
            } else {
               throw new SQLException(var6.getMessage());
            }
         }
      }
   }

   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      String methodName = "setClientInfo";
      Object[] params = new Object[]{name, value};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setClientInfo(name, value);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc != null) {
            cc.setRestoreClientInfoFlag();
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var9) {
         Exception e = var9;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (SQLClientInfoException var7) {
            throw var7;
         } catch (SQLException var8) {
            throw new SQLClientInfoException(var8.getMessage(), var8.getSQLState(), var8.getErrorCode(), (Map)null, var8.getCause());
         }
      }

   }

   public void setClientInfo(Properties properties) throws SQLClientInfoException {
      String methodName = "setClientInfo";
      Object[] params = new Object[]{properties};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setClientInfo(properties);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc != null) {
            cc.setRestoreClientInfoFlag();
         }

         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         Exception e = var8;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (SQLClientInfoException var6) {
            throw var6;
         } catch (SQLException var7) {
            throw new SQLClientInfoException(var7.getMessage(), var7.getSQLState(), var7.getErrorCode(), (Map)null, var7.getCause());
         }
      }

   }

   public String getClientInfo(String name) throws SQLException {
      String ret = null;
      String methodName = "getClientInfo";
      Object[] params = new Object[]{name};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getClientInfo(name);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Properties getClientInfo() throws SQLException {
      Properties ret = null;
      String methodName = "getClientInfo";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getClientInfo();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setSchema(String schema) throws SQLException {
      String methodName = "setSchema";
      Object[] params = new Object[]{schema};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setSchema(schema);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public String getSchema() throws SQLException {
      String ret = null;
      String methodName = "getSchema";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getSchema();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void abort(Executor executor) throws SQLException {
      String methodName = "abort";
      Object[] params = new Object[]{executor};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().abort(executor);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      String methodName = "setNetworkTimeout";
      Object[] params = new Object[]{executor, milliseconds};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setNetworkTimeout(executor, milliseconds);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public int getNetworkTimeout() throws SQLException {
      int ret = 0;
      String methodName = "getNetworkTimeout";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         ret = this.getConnection().getNetworkTimeout();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }
}
