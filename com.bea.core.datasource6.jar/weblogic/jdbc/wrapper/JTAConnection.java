package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Map;
import java.util.Properties;
import javax.transaction.xa.XAResource;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.jta.DataSource;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;

public class JTAConnection extends Connection {
   private static final long serialVersionUID = 1491819608666582144L;
   private DataSource ds;
   private String dsName;
   private int id;
   private boolean isClosed = false;
   private XAConnection xaConn;
   private String catalog;
   private boolean readOnlySet = false;
   private boolean readOnly;
   private Map typeMap;
   private boolean checked_for_non_tx_use = false;
   Throwable traceAtLocalTxStart = null;
   private boolean userAutoCommitState = false;
   private volatile boolean keepConnectionAfterLocalTx = true;
   private SQLWarning sqlw = null;
   private Object doCloseLockObject = new Object();
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.wrapper.JTAConnection");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public void init(DataSource ds, int id, XAConnection xaConn) {
      this.ds = ds;
      this.id = id;
      this.dsName = ds.toString();
      this.xaConn = xaConn;
      if (xaConn != null) {
         ConnectionEnv cc = xaConn.getConnectionEnv();
         if (cc != null) {
            cc.registerConnectionHarvestedCallback(this);
            this.profileClosedUsage.setProfiler(cc.profiler);
         }
      }

      this.vendorObj = xaConn;
      this.keepConnectionAfterLocalTx = ds.getKeepConnAfterLocalTx();
      if (xaConn != null && ds.getKeepConnAfterGlobalTx()) {
         xaConn.setOriginalOwner(this);
      }

      try {
         this.setConnStateIfNeeded(this.xaConn);
      } catch (Exception var5) {
      }

      if (JdbcDebug.isEnabled((DataSource)ds, 20)) {
         JdbcDebug.incNumConn(ds);
      }

   }

   public java.sql.Connection checkConnection() throws SQLException {
      return this.checkConnection((ConnectionEnv)null);
   }

   public java.sql.Connection checkConnection(ConnectionEnv cc) throws SQLException {
      this.checkHarvest();
      cc = this.getConnectionEnv();
      this.checkMTUsage(cc);
      if (cc != null) {
         cc.setUsed(true);
      }

      boolean needsTxContext = true;
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      if (tx == null) {
         needsTxContext = false;
      }

      java.sql.Connection c = this.getXAConn(needsTxContext);
      cc = this.getConnectionEnv();
      if (cc != null) {
         cc.checkIfEnabled();
      }

      if (!this.checked_for_non_tx_use) {
         this.checked_for_non_tx_use = true;
         if (TransactionHelper.getTransactionHelper().getTransaction() == null) {
            try {
               this.xaConn.setAutoCommit(true);
               this.xaConn.user_autocommit_state = true;
               this.userAutoCommitState = true;
            } catch (Exception var6) {
            }
         }
      }

      return c;
   }

   public ConnectionEnv getConnectionEnv() {
      return this.xaConn != null ? this.xaConn.getConnectionEnv() : null;
   }

   public String getPoolName() {
      return this.xaConn != null ? this.xaConn.getConnectionEnv().getPoolName() : null;
   }

   protected String getTraceInfo(String s) {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[JTA Conn] ").append(s).append(", conn = ").append(this.xaConn);
      return sb.toString();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[").append(this.getClass().getName()).append("-").append(this.dsName).append("-").append(this.id).append(", ");
      if (this.xaConn != null) {
         try {
            sb.append(this.xaConn.getConnection());
         } catch (SQLException var3) {
         }
      } else {
         sb.append("null");
      }

      sb.append("]");
      return sb.toString();
   }

   DataSource getDataSource() {
      return this.ds;
   }

   public void addStatement(Statement stmt, Object val) {
      if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
         JdbcDebug.log(this.ds, "addStatement conn:" + this + ", stmt:" + stmt);
      }

      super.addStatement(stmt, val);
      if (this.xaConn != null) {
         this.xaConn.addStatement(stmt);
      }

   }

   public Object removeStatement(Statement stmt) {
      if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
         JdbcDebug.log(this.ds, "removeStatement conn:" + this + ", stmt:" + stmt);
      }

      Object val = super.removeStatement(stmt);
      if (val == null) {
         return null;
      } else {
         if (this.xaConn != null) {
            this.xaConn.removeStatement(stmt);
         }

         return val;
      }
   }

   public void closeAllStatements() {
      super.closeAllStatements(false, true);
   }

   private void resetConnState() {
      this.catalog = null;
      this.readOnlySet = false;
      this.typeMap = null;
   }

   public void disassociateXAConn(XAConnection inXAConn) {
      if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
         JdbcDebug.enter(this.ds, "Connection.disassociateXAConn(" + inXAConn + ")");
      }

      synchronized(this) {
         if (inXAConn == this.xaConn) {
            this.xaConn = null;
            this.closeAllResultSets();
            this.closeAllStatements();
         }
      }

      if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
         JdbcDebug.enter(this.ds, "Connection.disassociateXAConn returns");
      }

   }

   public void getXAConnAndEnlist() throws SQLException {
      this.getXAConn(true);
   }

   private java.sql.Connection getXAConn(boolean needsTxCtx) throws SQLException {
      if (this.isClosed) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException("Connection closed");
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         XAConnection newXAConn = this.ds.refreshXAConnAndEnlist(this.xaConn, this, needsTxCtx, (Properties)null, (String)null, (String)null);
         if (newXAConn != this.xaConn) {
            this.releaseXAConnIfNeeded();
            synchronized(this) {
               if (this.xaConn == null && newXAConn.getOwner() == null) {
                  newXAConn = this.ds.refreshXAConnAndEnlist((XAConnection)null, this, needsTxCtx, (Properties)null, (String)null, (String)null);
               }

               if (newXAConn.getOwner() == null && JdbcDebug.isEnabled((DataSource)this.ds, 10)) {
                  JdbcDebug.err((DataSource)this.ds, "Connection.getXAConn null owner for xaConn", new Exception());
               }

               this.xaConn = newXAConn;
               this.setConnStateIfNeeded(this.xaConn);
               this.recoverAutoCommitState();
               this.vendorObj = this.xaConn;
            }
         }

         return this.xaConn;
      }
   }

   private void recoverAutoCommitState() throws SQLException {
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      if (tx == null) {
         this.xaConn.setAutoCommit(this.userAutoCommitState);
         this.xaConn.local_tx_to_clean_up = !this.userAutoCommitState;
      } else if (!this.userAutoCommitState) {
         this.xaConn.setAutoCommit(this.userAutoCommitState);
      }

      if (this.xaConn != null) {
         this.xaConn.user_autocommit_state = this.userAutoCommitState;
      }

   }

   public XAResource getXAResource() {
      return this.xaConn == null ? null : this.xaConn.getVendorXAResource();
   }

   private void releaseXAConnIfNeeded() {
      synchronized(this) {
         if (this.xaConn != null) {
            this.closeAllResultSets();
            this.closeAllStatements();
            if (this.xaConn.getOwner() != this && this.xaConn.getOwner() != null) {
               this.xaConn.removeConnection(this);
            } else {
               try {
                  this.xaConn.releaseToPool();
               } catch (Exception var4) {
                  if (JdbcDebug.isEnabled((DataSource)this.ds, 10)) {
                     JdbcDebug.err((DataSource)this.ds, "Error while releasing XAConn", var4);
                  }
               }

               this.xaConn = null;
            }
         }

      }
   }

   private void setConnStateIfNeeded(java.sql.Connection conn) throws SQLException {
      if (this.catalog != null && (conn.getCatalog() == null || !conn.getCatalog().equals(this.catalog))) {
         conn.setCatalog(this.catalog);
      }

      if (this.readOnlySet) {
         conn.setReadOnly(this.readOnly);
      }

      if (this.typeMap != null && conn.getTypeMap() != this.typeMap) {
         conn.setTypeMap(this.typeMap);
      }

      ConnectionEnv env = this.getConnectionEnv();
      if (env != null) {
         env.setIdentity();
      }

   }

   public void setAutoCommit(boolean autoCommit) throws SQLException {
      String methodName = "setAutoCommit";
      Object[] params = new Object[]{autoCommit};

      try {
         this.preInvocationHandler(methodName, params);
         Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
         if (tx == null) {
            this.getXAConn(false);
            this.xaConn.local_tx_to_clean_up = !autoCommit;
         } else {
            if (autoCommit) {
               throw new SQLException("Cannot set auto-commit mode when using distributed transactions");
            }

            this.getXAConn(true);
         }

         this.xaConn.setAutoCommit(autoCommit);
         this.xaConn.user_autocommit_state = autoCommit;
         this.userAutoCommitState = autoCommit;
         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public boolean getAutoCommit() throws SQLException {
      boolean ret = false;
      String methodName = "getAutoCommit";
      Object[] params = new Object[]{null};

      try {
         this.preInvocationHandler(methodName, params);
         Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
         if (tx == null) {
            this.getXAConn(false);
         } else {
            this.getXAConn(true);
         }

         ret = this.xaConn.getAutoCommit();
         this.postInvocationHandlerNoWrap(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void commit() throws SQLException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[1];
            var6.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      try {
         String methodName = "commit";
         Object[] params = new Object[0];

         try {
            this.preInvocationHandler(methodName, params);
            Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
            if (tx != null) {
               throw new SQLException("Cannot call commit when using distributed transactions");
            }

            synchronized(this) {
               if (this.xaConn != null && !this.xaConn.getAutoCommit()) {
                  this.xaConn.commit();
                  this.xaConn.getConnectionEnv().setLastSuccessfulConnectionUse();
                  if (!this.keepConnectionAfterLocalTx && !this.ds.getKeepConnAfterLocalTx()) {
                     this.xaConn.releaseToPool();
                     this.xaConn = null;
                     this.closeAllResultSets();
                     this.closeAllStatements();
                  }
               }
            }

            this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
         } catch (Exception var11) {
            this.invocationExceptionHandler(methodName, params, var11);
         }
      } catch (Throwable var12) {
         if (var6 != null) {
            var6.th = var12;
            InstrumentationSupport.postProcess(var6);
         }

         throw var12;
      }

      if (var6 != null) {
         InstrumentationSupport.postProcess(var6);
      }

   }

   public void rollback() throws SQLException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[1];
            var6.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      try {
         String methodName = "clearCallableStatement";
         Object[] params = new Object[0];

         try {
            this.preInvocationHandler(methodName, params);
            Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
            if (tx != null) {
               throw new SQLException("Cannot call rollback when using distributed transactions");
            }

            synchronized(this) {
               if (this.xaConn != null) {
                  this.cancelAllStatements();
                  if (!this.xaConn.getAutoCommit()) {
                     this.xaConn.rollback();
                     this.xaConn.getConnectionEnv().setLastSuccessfulConnectionUse();
                     if (!this.keepConnectionAfterLocalTx && !this.ds.getKeepConnAfterLocalTx()) {
                        this.xaConn.releaseToPool();
                        this.xaConn = null;
                        this.closeAllResultSets();
                        this.closeAllStatements();
                     }
                  } else {
                     this.xaConn.rollback();
                  }
               }
            }

            this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
         } catch (Exception var11) {
            this.invocationExceptionHandler(methodName, params, var11);
         }
      } catch (Throwable var12) {
         if (var6 != null) {
            var6.th = var12;
            InstrumentationSupport.postProcess(var6);
         }

         throw var12;
      }

      if (var6 != null) {
         InstrumentationSupport.postProcess(var6);
      }

   }

   public void finalizeInternal() {
      try {
         if (!this.isClosed) {
            ConnectionEnv cc = this.getConnectionEnv();
            if (cc != null) {
               cc.getConnectionPool().incrementLeakedConnectionCount();
               if (cc.getConnectionPool().getProfiler().isResourceLeakProfilingEnabled()) {
                  JDBCLogger.logConnectionLeakWarning(cc.getCurrentUser());
                  cc.getConnectionPool().getProfiler().addLeakData(cc);
               }
            }

            this.close();
         }
      } catch (Exception var2) {
      }

   }

   public void close() throws SQLException {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var1.argsCapture) {
            var1.args = new Object[1];
            var1.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var1);
         InstrumentationSupport.preProcess(var1);
         var1.resetPostBegin();
      }

      try {
         this.doClose(false, 0);
      } catch (Throwable var3) {
         if (var1 != null) {
            var1.th = var3;
            InstrumentationSupport.postProcess(var1);
         }

         throw var3;
      }

      if (var1 != null) {
         InstrumentationSupport.postProcess(var1);
      }

   }

   public boolean isClosed() throws SQLException {
      String methodName = "isClosed";
      Object[] params = new Object[0];
      boolean ret = this.isClosed;

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         if (!ret) {
            if (this.xaConn != null) {
               ret = this.xaConn.isClosed();
            }

            if (ret) {
               try {
                  this.doClose(true, 3);
               } catch (Exception var5) {
               }
            }
         }

         this.postInvocationHandlerNoWrap(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public void setTransactionIsolation(int level) throws SQLException {
      String methodName = "setTransactionIsolation";
      Object[] params = new Object[]{level};

      try {
         this.preInvocationHandler(methodName, params);
         if (this.ds == null) {
            throw new SQLException("Unexpected error.  DataSource is null, please report to BEA support.");
         }

         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.enter(this.ds, "JTAConnection.setTransactionIsolation(" + JdbcDebug.txIsolationToString(level) + "), conn:" + this);
         }

         if (!this.ds.supportSetTxIsolation()) {
            if (this.getXAConn(false).getTransactionIsolation() == level) {
               this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
               return;
            }

            throw new SQLException("Due to vendor limitations, setting transaction isolation for \"" + this.ds.getVendorName() + "\" JDBC XA driver is not supported.");
         }

         if (!this.ds.getKeepXAConnTillTxComplete()) {
            throw new SQLException("Cannot set transaction isolation level for the XA connection if the XA connection pool does not have \"KeepXAConnTillTxComplete\" attribute set to true.  Note that, however, setting this attribute means that each XA connection is associated with the global transaction until it completes and may limit scalability.");
         }

         try {
            this.getXAConn(true);
            this.xaConn.setTransactionIsolation(level);
            if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
               JdbcDebug.leave(this.ds, "JTA Connection.setTransactionIsolation returns");
            }
         } catch (SQLException var5) {
            if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
               JdbcDebug.err((DataSource)this.ds, "JTA Connection.setTransactionIsolation(" + JdbcDebug.txIsolationToString(level) + ")", var5);
            }
         }

         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public int getTransactionIsolation() throws SQLException {
      int isolation = 0;
      String methodName = "getTransactionIsolation";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         isolation = this.xaConn.getTransactionIsolation();
         if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
            JdbcDebug.log(this.ds, "JTA Connection.getTransactionIsolation rtns " + JdbcDebug.txIsolationToString(isolation) + ", conn:" + this);
         }

         this.postInvocationHandlerNoWrap(methodName, params, isolation);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return isolation;
   }

   public void setReadOnly(boolean readOnly) throws SQLException {
      String methodName = "setReadOnly";
      Object[] params = new Object[]{readOnly};

      try {
         this.preInvocationHandler(methodName, params);
         this.readOnly = readOnly;
         this.readOnlySet = true;
         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public boolean isReadOnly() throws SQLException {
      boolean ret = false;
      String methodName = "isReadOnly";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         if (this.readOnlySet) {
            ret = this.readOnly;
         } else {
            ret = this.getXAConn(false).isReadOnly();
         }

         this.postInvocationHandlerNoWrap(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setCatalog(String catalog) throws SQLException {
      this.checkConnection();
      this.catalog = catalog;
   }

   public String getCatalog() throws SQLException {
      this.checkConnection();
      return this.catalog != null ? this.catalog : this.getXAConn(false).getCatalog();
   }

   public Map getTypeMap() throws SQLException {
      this.checkConnection();
      return this.xaConn.getTypeMap();
   }

   public void setTypeMap(Map map) throws SQLException {
      this.checkConnection();
      this.typeMap = map;
      this.xaConn.setTypeMap(map);
   }

   public SQLWarning getWarnings() throws SQLException {
      if (this.isClosed) {
         return this.sqlw;
      } else {
         return this.xaConn != null ? this.xaConn.getWarnings() : null;
      }
   }

   protected void doClose(boolean forcedClose, int type) throws SQLException {
      if (!this.isClosed) {
         String methodName = "close";
         Object[] params = new Object[0];

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            boolean didClose = false;
            synchronized(this.doCloseLockObject) {
               if (!this.isClosed) {
                  this.isClosed = true;
                  didClose = true;
               }
            }

            if (didClose) {
               this.profileClosedUsage.saveWhereClosed();
               if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
                  JdbcDebug.decNumConn(this.ds);
                  JdbcDebug.enter(this.ds, "JTAConnection.close()");

                  try {
                     this.sqlw = this.xaConn.getWarnings();
                  } catch (Exception var8) {
                  }
               }

               if (forcedClose) {
                  String poolName = null;
                  String userName = null;
                  if (this.xaConn != null) {
                     poolName = this.xaConn.getConnectionEnv().getPoolName();
                     userName = this.xaConn.getConnectionEnv().getCurrentUser();
                  }

                  this.logDoClose(type, poolName, this.toString());
               }

               this.resetConnState();
               this.releaseXAConnIfNeeded();
               this.stmts.clear();
               if (JdbcDebug.isEnabled((DataSource)this.ds, 20)) {
                  JdbcDebug.leave(this.ds, "JTAConnection.close returns");
               }
            }

            this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
         } catch (Exception var10) {
            this.invocationExceptionHandler(methodName, params, var10);
         }

      }
   }

   public void setKeepConnectionAfterLocalTx(boolean flag) {
      this.keepConnectionAfterLocalTx = flag;
   }

   public boolean isKeepConnectionAfterLocalTx() {
      return this.keepConnectionAfterLocalTx;
   }

   static {
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Close_Around_Medium");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Rollback_Around_Low");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Commit_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTAConnection.java", "weblogic.jdbc.wrapper.JTAConnection", "commit", "()V", 371, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Commit_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTAConnection.java", "weblogic.jdbc.wrapper.JTAConnection", "rollback", "()V", 403, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Rollback_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTAConnection.java", "weblogic.jdbc.wrapper.JTAConnection", "close", "()V", 464, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Close_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium};
   }
}
