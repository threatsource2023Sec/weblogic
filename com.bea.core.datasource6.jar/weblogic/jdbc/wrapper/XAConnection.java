package weblogic.jdbc.wrapper;

import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.common.resourcepool.ResourceUnusableException;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.PoolUnavailableSQLException;
import weblogic.jdbc.jta.DataSource;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.XIDFactory;
import weblogic.utils.StackTraceUtils;

public class XAConnection extends JDBCWrapperImpl implements javax.sql.XAConnection, java.sql.Connection {
   private DataSource ds;
   private String dsName;
   private javax.sql.XAConnection rmXAConn;
   private java.sql.Connection rmConn;
   private ConnectionEnv ce;
   private XAResource rmXARes;
   private HashSet conns = new HashSet(10);
   private volatile boolean isClosed = false;
   private Transaction tx;
   private Object owner;
   private Object originalOwner;
   private HashSet stmts = new HashSet(3);
   private int defaultTxIsolation;
   private boolean defaultReadOnly;
   private boolean weSetReadOnly = false;
   private String defaultCatalog;
   public boolean user_autocommit_state = false;
   public boolean local_tx_to_clean_up = false;
   private boolean current_autocommit_state = false;
   private Object conns_object = new Object();
   private Object stmts_object = new Object();
   private boolean they_changed_the_catalog = false;
   private String cur_catalog = null;

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
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
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
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

      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
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
      if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
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
         throw new SQLException(methodName + ", Exception = " + StackTraceUtils.throwable2StackTrace(t));
      }
   }

   public void init(javax.sql.XAConnection rmXAConn, ConnectionEnv ce, java.sql.Connection conn, String dsName) {
      this.rmXAConn = rmXAConn;
      this.rmConn = conn;
      this.ce = ce;
      this.dsName = dsName;
      this.initDefaultParams();
   }

   private void initDefaultParams() {
      try {
         java.sql.Connection conn = this.getConnection();

         try {
            this.defaultTxIsolation = conn.getTransactionIsolation();
         } catch (Exception var5) {
         }

         try {
            this.defaultReadOnly = conn.isReadOnly();
         } catch (Exception var4) {
         }

         try {
            this.defaultCatalog = conn.getCatalog();
         } catch (Exception var3) {
         }
      } catch (Exception var6) {
      }

   }

   private void sybaseRollback(java.sql.Connection connection, String s) {
      if (JdbcDebug.isEnabled((DataSource)this.ds, 10)) {
         JdbcDebug.log(this.ds, this + " Will rollback on this connection after " + s + ", for Sybase as a workaround.");
      }

      try {
         connection.rollback();
      } catch (Exception var4) {
         if (JdbcDebug.isEnabled((DataSource)this.ds, 10)) {
            JdbcDebug.log(this.ds, this + " Rollback failed after " + s + ", for Sybase as a workaround.");
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[").append(this.rmXAConn.toString()).append(", owner=").append(this.owner).append(", rmConn=").append(this.rmConn).append("]");
      return sb.toString();
   }

   public DataSource getDataSource() {
      return this.ds;
   }

   public void setDataSource(DataSource ds) {
      this.ds = ds;
      if (ds != null) {
         this.dsName = ds.toString();
      }

   }

   public ConnectionEnv getConnectionEnv() {
      return this.ce;
   }

   public synchronized void setTransaction(Transaction tx) {
      this.tx = tx;
   }

   public synchronized Transaction getTransaction() {
      return this.tx;
   }

   public synchronized void setOwner(Object obj) {
      this.owner = obj;
   }

   public synchronized Object getOwner() {
      return this.owner;
   }

   public synchronized void setOriginalOwner(Object obj) {
      this.originalOwner = obj;
   }

   public synchronized Object getOriginalOwner() {
      return this.originalOwner;
   }

   public synchronized void restoreOriginalOwner() {
      this.owner = this.originalOwner;
      this.originalOwner = null;
   }

   public void addConnection(JTAConnection conn) {
      synchronized(this.conns_object) {
         if (this.conns != null) {
            if (conn != null) {
               this.conns.add(conn);
            }
         }
      }
   }

   public void removeConnection(JTAConnection conn) {
      boolean removeTxAssoc = false;
      synchronized(this.conns_object) {
         if (this.conns == null) {
            return;
         }

         if (conn == null) {
            return;
         }

         if (!this.conns.remove(conn)) {
            return;
         }

         removeTxAssoc = this.conns.size() == 0;
      }

      conn.disassociateXAConn(this);
      if (removeTxAssoc) {
         this.removeTxAssocIfNeeded();
      }

   }

   private void removeAllConnections() {
      if (this.conns != null) {
         HashSet curConns = null;
         synchronized(this.conns_object) {
            if (this.conns == null) {
               return;
            }

            curConns = (HashSet)this.conns.clone();
            this.conns.clear();
         }

         Iterator iter = curConns.iterator();

         while(iter.hasNext()) {
            JTAConnection conn = (JTAConnection)iter.next();
            conn.disassociateXAConn(this);
         }

      }
   }

   public void addStatement(Statement stmt) {
      synchronized(this.stmts_object) {
         if (this.stmts != null) {
            this.stmts.add(stmt);
         }

      }
   }

   public void removeStatement(Statement stmt) {
      boolean removeTxAssoc = false;
      synchronized(this.stmts_object) {
         if (this.stmts != null) {
            if (!this.stmts.remove(stmt)) {
               return;
            }

            removeTxAssoc = this.stmts.size() == 0;
         }
      }

      if (removeTxAssoc) {
         this.removeTxAssocIfNeeded();
      }

   }

   private void removeTxAssocIfNeeded() {
      if (this.tx != null) {
         boolean release = false;
         if (this.ds == null || this.ds.getKeepConnAfterGlobalTx()) {
            return;
         }

         TxInfo info = this.ds.getTxInfo(this.tx);
         if (info != null) {
            synchronized(info) {
               synchronized(this.conns_object) {
                  synchronized(this.stmts_object) {
                     if (info != null && this.stmts != null && this.conns != null) {
                        release = info.isEnded() && this.stmts.size() == 0 && this.conns.size() == 0;
                        if (release && !this.ds.getKeepXAConnTillTxComplete()) {
                           this.ds.setTxInfo(this.tx, (TxInfo)null);
                           this.releaseToPool();
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void closeAllStatements() {
      HashSet curStmts;
      synchronized(this.stmts_object) {
         if (this.stmts == null) {
            return;
         }

         curStmts = (HashSet)this.stmts.clone();
         this.stmts.clear();
      }

      Iterator iter = curStmts.iterator();

      while(iter.hasNext()) {
         Statement stmt = (Statement)iter.next();

         try {
            if (stmt.isInUse()) {
               try {
                  stmt.cancel();
               } catch (Exception var5) {
               }
            }

            stmt.internalClose(false);
         } catch (Exception var6) {
         }
      }

   }

   public boolean canReleaseToPool() {
      synchronized(this.stmts_object) {
         if (this.stmts == null) {
            return true;
         } else {
            return this.stmts.size() == 0;
         }
      }
   }

   private void cleanup() {
      this.closeAllStatements();
      this.removeAllConnections();
      this.resetConnState();
      if (this.ds != null && !this.ds.keepConnOpenOnRelease() && !this.hasCachedPreparedStatement()) {
         this.closeConn();
      }

      synchronized(this) {
         this.rmXARes = null;
         this.tx = null;
         this.owner = null;
         this.originalOwner = null;
      }
   }

   private void closeConn() {
      try {
         if (this.rmConn != null) {
            this.rmConn.close();
            this.rmConn = null;
         }
      } catch (SQLException var2) {
      }

   }

   public void releaseToPool() {
      this.cleanup();

      try {
         ConnectionPoolManager.release(this.ce);
      } catch (Throwable var2) {
      }

   }

   private void resetConnState() {
      java.sql.Connection conn = this.rmConn;
      if (conn != null) {
         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.enter(this.ds, "resetConnState(), conn:" + conn);
         }

         boolean clearedTx = false;
         if (this.current_autocommit_state) {
            try {
               conn.setAutoCommit(false);
               this.current_autocommit_state = false;
            } catch (Exception var8) {
            }
         } else if (this.local_tx_to_clean_up) {
            if (this.ce.endLocalTxOnXaConWithCommit) {
               try {
                  conn.commit();
               } catch (Exception var7) {
               }
            } else {
               try {
                  conn.rollback();
               } catch (Exception var6) {
               }
            }

            clearedTx = true;
         }

         if (!clearedTx && this.ce.conn.isOracleGetTransactionStateSupported() && this.ce.conn.isOracleLocalTransactionStarted()) {
            if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
               JdbcDebug.log((String)this.dsName, (Xid)null, "open transaction on connection " + this.ce + ", attempting to perform local " + (this.ce.endLocalTxOnXaConWithCommit ? "commit" : "rollback"), new Throwable());
            }

            if (this.ce.endLocalTxOnXaConWithCommit) {
               try {
                  conn.commit();
               } catch (Exception var9) {
                  if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
                     JdbcDebug.log((String)this.dsName, (Xid)null, "local commit failed", var9);
                  }
               }
            } else {
               try {
                  conn.rollback();
               } catch (Exception var10) {
                  if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
                     JdbcDebug.log((String)this.dsName, (Xid)null, "local rollback failed", var10);
                  }
               }
            }
         }

         try {
            if (this.weSetReadOnly) {
               this.weSetReadOnly = false;
               conn.setReadOnly(this.defaultReadOnly);
            }
         } catch (SQLException var5) {
         }

         try {
            if (this.they_changed_the_catalog) {
               conn.setCatalog(this.defaultCatalog);
            }
         } catch (SQLException var4) {
         }

         if (this.ce.getVendorId() == 4) {
            this.sybaseRollback(conn, "resetConnState()");
         }

         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.leave(this.ds, "resetConnState returns, conn:" + conn);
         }
      }

   }

   public XAResource getXAResource() throws SQLException {
      try {
         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.enter(this.ds, "getXAResource()");
         }

         if (this.rmXARes == null) {
            if (this.ds == null) {
               if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
                  JdbcDebug.leave(this.ds, "getXAResource returns");
               }

               return this.rmXAConn.getXAResource();
            }

            this.rmXARes = this.rmXAConn.getXAResource();
         }

         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.leave(this.ds, "getXAResource returns");
         }
      } catch (Exception var2) {
         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.err((DataSource)this.ds, "getXAResource", var2);
         }
      }

      return this.rmXARes;
   }

   public java.sql.Connection getConnection() throws SQLException {
      if (this.isClosed) {
         throw new SQLException("XAConnection is closed");
      } else {
         if (this.rmConn == null) {
            if (this.rmXAConn == null) {
               throw new SQLException("XAConnection is closed");
            }

            this.rmConn = this.rmXAConn.getConnection();
            if (this.rmConn == null) {
               throw new SQLException("XAConnection is closed");
            }

            this.vendorObj = this.rmConn;
         }

         return this.rmConn;
      }
   }

   public void destroy() {
      if (!this.isClosed) {
         try {
            synchronized(this) {
               if (this.isClosed) {
                  return;
               }

               this.isClosed = true;
            }

            try {
               this.closeConn();
            } catch (Throwable var9) {
            }

            synchronized(this) {
               this.rmXARes = null;
               this.tx = null;
               this.owner = null;
            }

            synchronized(this.conns_object) {
               this.conns = null;
            }

            synchronized(this.stmts_object) {
               this.stmts = null;
            }

            this.rmXAConn.close();
         } catch (Throwable var11) {
         }

      }
   }

   public void close() throws SQLException {
      if (!this.isClosed) {
         String methodName = "close";
         Object[] params = new Object[0];

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            synchronized(this) {
               if (this.isClosed) {
                  return;
               }

               this.isClosed = true;
            }

            this.cleanup();
            if (this.ds != null && this.ds.keepConnOpenOnRelease()) {
               this.closeConn();
            }

            synchronized(this.conns_object) {
               this.conns = null;
            }

            synchronized(this.stmts_object) {
               this.stmts = null;
            }

            this.rmXAConn.close();
            this.postInvocationHandler(methodName, params, (Object)null);
         } catch (Exception var10) {
            this.invocationExceptionHandler(methodName, params, var10);
         }

      }
   }

   public void addConnectionEventListener(ConnectionEventListener listener) {
      String methodName = "addConnectoinEventListener";
      Object[] params = new Object[]{listener};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.rmXAConn.addConnectionEventListener(listener);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
      }

   }

   public void removeConnectionEventListener(ConnectionEventListener listener) {
      String methodName = "removeConnectoinEventListener";
      Object[] params = new Object[]{listener};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.rmXAConn.removeConnectionEventListener(listener);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
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
      boolean ret = this.isClosed;

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         if (!ret) {
            this.getConnection();
            ret = this.rmConn.isClosed();
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

   XAResource getVendorXAResource() {
      return this.rmXARes;
   }

   public void resetTransactionIsolation(int isolationLevel) {
      if (this.ds != null && this.ds.getKeepXAConnTillTxComplete()) {
         TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
         Transaction suspendTx = null;
         if (JdbcDebug.isEnabled((DataSource)this.ds, 10)) {
            JdbcDebug.enter(this.ds, "XAConnection.resetTransactionIsolation(" + JdbcDebug.txIsolationToString(isolationLevel) + "), " + this);
         }

         try {
            suspendTx = (Transaction)tm.forceSuspend();
            tm.begin("JDBC Internal");
            Xid tmpXid = ((Transaction)tm.getTransaction()).getXID();

            try {
               tm.commit();
            } catch (Exception var18) {
            }

            String branch = "weblogic.jdbc.jta.XAConnection";
            Xid xid = XIDFactory.createXID(tmpXid.getFormatId(), tmpXid.getGlobalTransactionId(), branch.getBytes());
            XAResource xar = this.getXAResource();
            if (xar != null) {
               try {
                  if (this.ds != null && this.ds.isVendor(0)) {
                     xar.start(xid, 0 | this.ds.mapIsoLevelToVendorFlags(isolationLevel));
                  } else {
                     xar.start(xid, 0);
                     this.setTransactionIsolation(isolationLevel);
                  }
               } finally {
                  xar.end(xid, 67108864);
                  xar.commit(xid, true);
               }
            }

            if (JdbcDebug.isEnabled((DataSource)this.ds, 10)) {
               JdbcDebug.leave(this.ds, "XAConnection.resetTransactionIsolation returns");
            }
         } catch (Exception var20) {
            if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
               JdbcDebug.err((DataSource)this.ds, "XAConnection.resetTransactionIsolation", var20);
            }
         } finally {
            if (suspendTx != null) {
               tm.forceResume(suspendTx);
            }

         }

      }
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
      } catch (Exception var10) {
         this.invocationExceptionHandler(methodName, params, var10);
      } finally {
         if (this.ce.getVendorId() == 4) {
            this.sybaseRollback(this.getConnection(), "setTransactionIsolation(" + JdbcDebug.txIsolationToString(level) + ")");
         }

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

   private boolean hasCachedPreparedStatement() {
      return this.ce.getStatementCacheSize() > 0;
   }

   public void setAutoCommit(boolean autoCommit) throws SQLException {
      String methodName = "setAutoCommit";
      Object[] params = new Object[]{autoCommit};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setAutoCommit(autoCommit);
         this.current_autocommit_state = autoCommit;
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      } finally {
         if (this.ce.getVendorId() == 4) {
            this.sybaseRollback(this.getConnection(), "setAutoCommit(" + autoCommit + ")");
         }

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
         if (readOnly != this.defaultReadOnly) {
            this.weSetReadOnly = true;
            this.getConnection().setReadOnly(readOnly);
         }

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
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      } finally {
         if (this.ce.getVendorId() == 4) {
            this.sybaseRollback(this.getConnection(), "getAutoCommit()");
         }

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
      this.they_changed_the_catalog = true;
      String methodName = "setCatalog";
      Object[] params = new Object[]{catalog};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.getConnection().setCatalog(catalog);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      this.cur_catalog = catalog;
      if (this.cur_catalog != null && this.defaultCatalog != null && this.cur_catalog.equals(this.defaultCatalog)) {
         this.they_changed_the_catalog = false;
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
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      } finally {
         if (this.ce.getVendorId() == 4) {
            this.sybaseRollback(this.getConnection(), "getTransactionIsolation()");
         }

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
         if (this.getConnection() != null) {
            this.getConnection().clearWarnings();
         }

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

   public void trace(String s) {
      JdbcDebug.log("[" + this + "] " + s);
   }

   public void trace(String s, Exception e) {
      JdbcDebug.log("[" + this + "] " + s);
   }

   public void cancelAllStatements() {
      synchronized(this.conns_object) {
         if (this.conns != null) {
            try {
               Iterator i = this.conns.iterator();

               while(i.hasNext()) {
                  JTAConnection j = (JTAConnection)((JTAConnection)i.next());
                  j.cancelAllStatements();
               }
            } catch (Exception var5) {
            }

         }
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
            if (this.getConnection() == null) {
               ret = false;
            } else {
               ret = this.getConnection().isValid(timeout);
            }

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
