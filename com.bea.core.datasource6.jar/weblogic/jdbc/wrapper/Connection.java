package weblogic.jdbc.wrapper;

import java.io.IOException;
import java.io.Serializable;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLRecoverableException;
import java.sql.SQLXML;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import oracle.ucp.ConnectionHarvestingCallback;
import weblogic.common.ResourceException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.RollbackSQLException;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.ConnectionPoolProfiler;
import weblogic.jdbc.common.internal.HAConnectionEnv;
import weblogic.jdbc.common.internal.JDBCConnectionPool;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ProfileClosedUsage;
import weblogic.jdbc.common.internal.WLDataSourceImpl;
import weblogic.jdbc.common.rac.RACPooledConnectionState;
import weblogic.jdbc.extensions.WLConnection;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

public abstract class Connection extends JDBCWrapperImpl implements InteropWriteReplaceable, Serializable, ResourceCleanupHandler, Runnable, WLConnection {
   private static final long serialVersionUID = -4427101532821502653L;
   protected HashMap stmts = new HashMap(3);
   private WLDataSourceImpl ds = null;
   private String poolName = null;
   protected boolean connectionHarvestable = false;
   protected ConnectionHarvestingCallback connectionHarvestingCallback;
   private boolean harvested = false;
   protected transient ProfileClosedUsage profileClosedUsage = new ProfileClosedUsage();
   protected static final int INACTIVE = 1;
   protected static final int HARVESTED = 2;
   protected static final int CLOSED = 3;
   protected static final int INITFAILURE = 4;
   private static final String GET_LTXID_OUTCOME_WRAPPER = "DECLARE PROCEDURE GET_LTXID_OUTCOME_WRAPPER(  ltxid IN RAW,  is_committed OUT NUMBER ) IS   call_completed BOOLEAN;   committed BOOLEAN; BEGIN   DBMS_APP_CONT.GET_LTXID_OUTCOME(ltxid, committed, call_completed);   if committed then is_committed := 1;  else is_committed := 0; end if; END; BEGIN GET_LTXID_OUTCOME_WRAPPER(?,?); END;";
   protected HashSet rsets = new HashSet(1);
   static boolean ignoreAutoCommitException;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.wrapper.Connection");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Statement_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Statement_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Create_Statement_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;
   static final JoinPoint _WLDF$INST_JPFLD_5;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_5;
   static final JoinPoint _WLDF$INST_JPFLD_6;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_6;
   static final JoinPoint _WLDF$INST_JPFLD_7;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_7;
   static final JoinPoint _WLDF$INST_JPFLD_8;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_8;
   static final JoinPoint _WLDF$INST_JPFLD_9;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_9;
   static final JoinPoint _WLDF$INST_JPFLD_10;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_10;
   static final JoinPoint _WLDF$INST_JPFLD_11;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_11;
   static final JoinPoint _WLDF$INST_JPFLD_12;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_12;
   static final JoinPoint _WLDF$INST_JPFLD_13;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_13;
   static final JoinPoint _WLDF$INST_JPFLD_14;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_14;
   static final JoinPoint _WLDF$INST_JPFLD_15;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_15;
   static final JoinPoint _WLDF$INST_JPFLD_16;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_16;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc != null) {
            cc.setNotInUse();
         }

         return null;
      } else {
         try {
            if (ret instanceof java.sql.CallableStatement) {
               ret = CallableStatement.makeCallableStatement((java.sql.CallableStatement)ret, this, (String)((String)params[0]), -1, -1);
            } else if (ret instanceof java.sql.PreparedStatement) {
               ret = PreparedStatement.makePreparedStatement((java.sql.PreparedStatement)ret, this, (String)((String)params[0]), -1, -1);
            } else if (ret instanceof java.sql.Statement) {
               ret = Statement.makeStatement((java.sql.Statement)ret, this, (String)null, -1, -1);
            }
         } catch (Exception var8) {
            JDBCLogger.logStackTrace(var8);
            throw var8;
         } finally {
            super.postInvocationHandler(methodName, params, ret);
         }

         return ret;
      }
   }

   public Object postInvocationHandlerNoWrap(String methodName, Object[] params, Object ret) throws Exception {
      super.postInvocationHandler(methodName, params, ret);
      return ret;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      this.checkConnection();
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc != null) {
         cc.setInUse();
      }

   }

   public void preInvocationHandlerNoCheck(String methodName, Object[] params) throws Exception {
      ConnectionEnv cc = this.getConnectionEnv();
      this.checkMTUsage(cc);
      super.preInvocationHandler(methodName, params);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      Object var10000;
      try {
         var10000 = JDBCHelper.getHelper().interopReplace(this, peerInfo);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = null;
            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = var10000;
         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }
      }

      return var10000;
   }

   public void setRMIDataSource(WLDataSourceImpl ds) {
      this.ds = ds;
   }

   public WLDataSourceImpl getRMIDataSource() {
      return this.ds;
   }

   public void setPoolName(String PoolName) {
      this.poolName = PoolName;
   }

   public boolean isSameInstance(java.sql.Connection c2) {
      Properties p1 = this.getServerSessionInfo();
      if (p1 == null) {
         return false;
      } else {
         Properties p2;
         if (c2 instanceof Connection) {
            p2 = ((Connection)c2).getServerSessionInfo();
         } else {
            p2 = null;
         }

         if (p2 == null) {
            return false;
         } else {
            String s1 = p1.getProperty("DATABASE_NAME");
            if (s1 == null) {
               return false;
            } else {
               String s2 = p2.getProperty("DATABASE_NAME");
               if (s2 != null && s1.equals(s2)) {
                  s1 = p1.getProperty("INSTANCE_NAME");
                  if (s1 == null) {
                     return false;
                  } else {
                     s2 = p2.getProperty("INSTANCE_NAME");
                     if (s2 != null && s1.equals(s2)) {
                        s1 = p1.getProperty("SERVICE_NAME");
                        if (s1 == null) {
                           return false;
                        } else {
                           s2 = p2.getProperty("SERVICE_NAME");
                           return s2 != null && s1.equals(s2);
                        }
                     } else {
                        return false;
                     }
                  }
               } else {
                  return false;
               }
            }
         }
      }
   }

   private Properties getServerSessionInfo() {
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc == null) {
         return null;
      } else if (cc.conn.getServerSessionInfo == null) {
         return null;
      } else {
         try {
            return (Properties)cc.conn.getServerSessionInfo.invoke(cc.conn.jconn, (Object[])null);
         } catch (Throwable var3) {
            return null;
         }
      }
   }

   public String getPoolName() {
      return this.poolName;
   }

   protected void checkMTUsage(ConnectionEnv cc) {
      if (cc != null) {
         ConnectionPoolProfiler profiler = (ConnectionPoolProfiler)cc.getConnectionPool().getProfiler();
         if (profiler.isResourceMTUsageProfilingEnabled()) {
            long resvThreadId = cc.getReservingThreadId();
            long currThreadId = Thread.currentThread().getId();
            if (currThreadId != resvThreadId) {
               profiler.addConnMTUsageData(StackTraceUtils.throwable2StackTrace(new Exception()), cc.getCurrentUser(), new Date());
            }
         }
      }

   }

   public abstract java.sql.Connection checkConnection() throws SQLException;

   public abstract java.sql.Connection checkConnection(ConnectionEnv var1) throws SQLException;

   public abstract ConnectionEnv getConnectionEnv();

   protected abstract void doClose(boolean var1, int var2) throws SQLException;

   public abstract boolean isClosed() throws SQLException;

   protected void logDoClose(int type, String poolName, String connName) {
      switch (type) {
         case 1:
            JDBCLogger.logForcedCloseConnInactive(poolName, connName);
            break;
         case 2:
            JDBCLogger.logForcedCloseConnHarvested(poolName, connName);
            break;
         case 3:
            JDBCLogger.logForcedCloseConnClosed(poolName, connName);
            break;
         case 4:
            JDBCLogger.logForcedCloseConnInitfailure(poolName, connName);
      }

   }

   public final void forcedCleanup() {
      String force_problems = "";

      try {
         try {
            ConnectionEnv cc = this.getConnectionEnv();
            if (cc != null) {
               cc.forcedCleanup();
               if (cc.conn.jconn != null && !cc.conn.jconn.getAutoCommit()) {
                  cc.conn.jconn.rollback();
               }

               cc.getConnectionPool().incrementLeakedConnectionCount();
            }
         } catch (Exception var3) {
            force_problems = force_problems + var3.getMessage() + "\n";
         }

         this.doClose(true, 1);
      } catch (Exception var4) {
         JDBCLogger.logCloseFailed(this.toString(), force_problems + var4.toString());
      }

   }

   public final void forceClose() {
      try {
         this.doClose(true, 0);
      } catch (SQLException var2) {
      }

   }

   public void clearStatementCache() throws SQLException {
      String methodName = "clearStatementCache";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc == null) {
            throw new SQLException(JDBCUtil.getTextFormatter().featureNotSupported());
         }

         cc.clearCache();
         super.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var4) {
         this.invocationExceptionHandler(methodName, params, var4);
      }

   }

   public boolean clearCallableStatement(String sql) throws SQLException {
      boolean ret = false;
      String methodName = "clearCallableStatement";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.clearStatementInternal(true, sql, -1, -1);
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   protected boolean clearStatementInternal(boolean isCallable, String sql, int resType, int resConcurrency) throws SQLException {
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc == null) {
         throw new SQLException(JDBCUtil.getTextFormatter().featureNotSupported());
      } else {
         return sql == null ? false : cc.clearStatement(isCallable, sql, resType, resConcurrency);
      }
   }

   public boolean clearCallableStatement(String sql, int resType, int resConcurrency) throws SQLException {
      boolean ret = false;
      String methodName = "clearCallableStatement";
      Object[] params = new Object[]{sql, resType, resConcurrency};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.clearStatementInternal(true, sql, resType, resConcurrency);
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public boolean clearPreparedStatement(String sql) throws SQLException {
      boolean ret = false;
      String methodName = "clearPreparedStatement";
      Object[] params = new Object[]{sql};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.clearStatementInternal(false, sql, -1, -1);
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean clearPreparedStatement(String sql, int resType, int resConcurrency) throws SQLException {
      boolean ret = false;
      String methodName = "clearPreparedStatement";
      Object[] params = new Object[]{sql, resType, resConcurrency};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.clearStatementInternal(false, sql, resType, resConcurrency);
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   public void run() {
      try {
         ConnectionEnv cc = this.getConnectionEnv();
         PooledResourceInfo[] infoList = new PooledResourceInfo[1];
         ConnectionPoolManager.getPool(cc.getPoolName(), cc.getAppName(), cc.getModuleName(), cc.getCompName()).createResources(1, infoList);
      } catch (Exception var3) {
      }

   }

   public java.sql.Connection getVendorConnection() throws SQLException {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
      }

      java.sql.Connection var10000;
      try {
         var10000 = this.internalGetVendorConnection(false);
      } catch (Throwable var3) {
         if (var1 != null) {
            var1.th = var3;
            var1.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         throw var3;
      }

      if (var1 != null) {
         var1.ret = var10000;
         InstrumentationSupport.createDynamicJoinPoint(var1);
         InstrumentationSupport.process(var1);
      }

      return var10000;
   }

   public java.sql.Connection getVendorConnectionSafe() throws SQLException {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
      }

      java.sql.Connection var10000;
      try {
         var10000 = this.internalGetVendorConnection(true);
      } catch (Throwable var3) {
         if (var1 != null) {
            var1.th = var3;
            var1.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         throw var3;
      }

      if (var1 != null) {
         var1.ret = var10000;
         InstrumentationSupport.createDynamicJoinPoint(var1);
         InstrumentationSupport.process(var1);
      }

      return var10000;
   }

   private java.sql.Connection internalGetVendorConnection(boolean safe) throws SQLException {
      java.sql.Connection ret = null;
      String methodName;
      if (safe) {
         methodName = "getVendorConnectionSafe";
      } else {
         methodName = "getVendorConnection";
      }

      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc == null) {
            throw new SQLException(JDBCUtil.getTextFormatter().featureNotSupported());
         }

         if (!cc.isInfected()) {
            try {
               JDBCConnectionPool pool = ConnectionPoolManager.getPool(cc.getPoolName(), cc.getAppName(), cc.getModuleName(), cc.getCompName());
               if (!safe && pool != null && pool.isRemoveInfectedConnectionEnabled()) {
                  cc.setInfected(true);
                  cc.setRefreshNeeded(true);
                  pool.removeConnection(cc);
                  if (!pool.isCreateConnectionInline()) {
                     WorkManagerFactory.getInstance().getSystem().schedule(this);
                  }
               }
            } catch (Exception var7) {
               JDBCLogger.logStackTrace(var7);
            }
         }

         ret = (java.sql.Connection)((java.sql.Connection)this.getVendorObj());
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var8) {
         this.invocationExceptionHandler(methodName, params, var8);
      }

      return ret;
   }

   java.sql.Connection getRealConnection() {
      return (java.sql.Connection)this.vendorObj;
   }

   public void addStatement(Statement stmt, Object val) {
      synchronized(this.stmts) {
         this.stmts.put(stmt, val);
      }
   }

   public Object removeStatement(Statement stmt) {
      synchronized(this.stmts) {
         return this.stmts.remove(stmt);
      }
   }

   public Object getStatement(Statement stmt) {
      synchronized(this.stmts) {
         return this.stmts.get(stmt);
      }
   }

   public void closeAllStatements(boolean explicit) {
      this.closeAllStatements(explicit, false);
   }

   public void closeAllStatements(boolean explicit, boolean isJTA) {
      HashMap currStmts;
      synchronized(this.stmts) {
         if (this.stmts.size() == 0) {
            return;
         }

         currStmts = (HashMap)this.stmts.clone();
      }

      Iterator i = currStmts.keySet().iterator();

      while(i.hasNext()) {
         Statement stmt = (Statement)i.next();

         try {
            stmt.internalClose(explicit);
         } catch (Exception var7) {
            JDBCLogger.logStackTrace(var7);
         }

         if (isJTA) {
            XAConnection xaConn = (XAConnection)this.getRealConnection();
            if (xaConn != null) {
               xaConn.removeStatement(stmt);
            } else {
               JDBCLogger.logStackTrace(new Exception());
            }
         }
      }

   }

   public void cancelAllStatements() {
      HashMap currStmts;
      synchronized(this.stmts) {
         currStmts = (HashMap)this.stmts.clone();
      }

      Iterator iter = currStmts.keySet().iterator();

      while(iter.hasNext()) {
         Statement stmt = (Statement)iter.next();

         try {
            stmt.cancel();
         } catch (SQLFeatureNotSupportedException var5) {
         } catch (Exception var6) {
            JDBCLogger.logStackTrace(var6);
         }
      }

   }

   public void trace(String s) {
      JdbcDebug.JDBCSQL.debug("[" + this + "] " + s);
   }

   public void trace(String s, Exception e) {
      JdbcDebug.JDBCSQL.debug("[" + this + "] " + s);
   }

   public void traceConn(String s) {
      JdbcDebug.JDBCCONN.debug("[" + this + "] " + s);
   }

   public void traceConn(String s, Exception e) {
      JdbcDebug.JDBCCONN.debug("[" + this + "] " + s);
   }

   protected abstract String getTraceInfo(String var1);

   public java.sql.DatabaseMetaData getMetaData() throws SQLException {
      java.sql.DatabaseMetaData ret = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.getMetaData();
         if (ret != null) {
            ret = DatabaseMetaData.makeDatabaseMetaData(ret, this);
         }

         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public java.sql.Statement createStatement() throws SQLException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var5.argsCapture) {
            var5.args = InstrumentationSupport.toSensitive(1);
         }

         if (var5.monitorHolder[0] != null) {
            var5.monitorIndex = 0;
            InstrumentationSupport.preProcess(var5);
         }

         if (var5.monitorHolder[2] != null) {
            var5.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.process(var5);
         }

         var5.resetPostBegin();
      }

      java.sql.Statement var10000;
      try {
         java.sql.Statement ret = null;
         String methodName = "CreateStatement";
         Object[] params = new Object[0];

         try {
            this.preInvocationHandler(methodName, params);
            java.sql.Connection conn = this.checkConnection();
            ret = conn.createStatement();
            ret = Statement.makeStatement(ret, this, (String)null, -1, -1);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var8) {
            this.invocationExceptionHandler(methodName, params, var8);
         }

         var10000 = ret;
      } catch (Throwable var9) {
         if (var5 != null) {
            var5.th = var9;
            var5.ret = null;
            if (var5.monitorHolder[1] != null) {
               var5.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var5);
               InstrumentationSupport.process(var5);
            }

            if (var5.monitorHolder[0] != null) {
               var5.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var5);
               InstrumentationSupport.postProcess(var5);
            }
         }

         throw var9;
      }

      if (var5 != null) {
         var5.ret = var10000;
         if (var5.monitorHolder[1] != null) {
            var5.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.process(var5);
         }

         if (var5.monitorHolder[0] != null) {
            var5.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.postProcess(var5);
         }
      }

      return var10000;
   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      LocalHolder var7;
      if ((var7 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var7.argsCapture) {
            var7.args = InstrumentationSupport.toSensitive(3);
         }

         if (var7.monitorHolder[0] != null) {
            var7.monitorIndex = 0;
            InstrumentationSupport.preProcess(var7);
         }

         if (var7.monitorHolder[2] != null) {
            var7.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var7);
            InstrumentationSupport.process(var7);
         }

         var7.resetPostBegin();
      }

      java.sql.Statement var10000;
      try {
         java.sql.Statement ret = null;
         String methodName = "createStatement";
         Object[] params = new Object[]{resultSetType, resultSetConcurrency};

         try {
            this.preInvocationHandler(methodName, params);
            java.sql.Connection conn = this.checkConnection();
            ret = conn.createStatement(resultSetType, resultSetConcurrency);
            ret = Statement.makeStatement(ret, this, (String)null, resultSetType, resultSetConcurrency);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var10) {
            this.invocationExceptionHandler(methodName, params, var10);
         }

         var10000 = ret;
      } catch (Throwable var11) {
         if (var7 != null) {
            var7.th = var11;
            var7.ret = null;
            if (var7.monitorHolder[1] != null) {
               var7.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var7);
               InstrumentationSupport.process(var7);
            }

            if (var7.monitorHolder[0] != null) {
               var7.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var7);
               InstrumentationSupport.postProcess(var7);
            }
         }

         throw var11;
      }

      if (var7 != null) {
         var7.ret = var10000;
         if (var7.monitorHolder[1] != null) {
            var7.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var7);
            InstrumentationSupport.process(var7);
         }

         if (var7.monitorHolder[0] != null) {
            var7.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var7);
            InstrumentationSupport.postProcess(var7);
         }
      }

      return var10000;
   }

   public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         if (var8.argsCapture) {
            var8.args = new Object[2];
            Object[] var10000 = var8.args;
            var10000[0] = this;
            var10000[1] = sql;
         }

         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.preProcess(var8);
         }

         var8.resetPostBegin();
      }

      java.sql.PreparedStatement var14;
      try {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPS());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(false, sql);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var11) {
            this.invocationExceptionHandler(methodName, params, var11);
         }

         var14 = ret;
      } catch (Throwable var12) {
         if (var8 != null) {
            var8.th = var12;
            var8.ret = null;
            if (var8.monitorHolder[2] != null) {
               var8.monitorIndex = 2;
               InstrumentationSupport.postProcess(var8);
            }

            if (var8.monitorHolder[0] != null) {
               var8.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.process(var8);
            }
         }

         throw var12;
      }

      if (var8 != null) {
         var8.ret = var14;
         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.postProcess(var8);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }
      }

      return var14;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_6, _WLDF$INST_JPFLD_JPMONS_6)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[4];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = InstrumentationSupport.convertToObject(resultSetType);
            var10000[3] = InstrumentationSupport.convertToObject(resultSetConcurrency);
         }

         if (var10.monitorHolder[1] != null) {
            var10.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.process(var10);
         }

         if (var10.monitorHolder[2] != null) {
            var10.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.preProcess(var10);
         }

         var10.resetPostBegin();
      }

      java.sql.PreparedStatement var16;
      try {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPS());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(false, sql, resultSetType, resultSetConcurrency);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, resultSetType, resultSetConcurrency);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var13) {
            this.invocationExceptionHandler(methodName, params, var13);
         }

         var16 = ret;
      } catch (Throwable var14) {
         if (var10 != null) {
            var10.th = var14;
            var10.ret = null;
            if (var10.monitorHolder[2] != null) {
               var10.monitorIndex = 2;
               InstrumentationSupport.postProcess(var10);
            }

            if (var10.monitorHolder[0] != null) {
               var10.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var10);
               InstrumentationSupport.process(var10);
            }
         }

         throw var14;
      }

      if (var10 != null) {
         var10.ret = var16;
         if (var10.monitorHolder[2] != null) {
            var10.monitorIndex = 2;
            InstrumentationSupport.postProcess(var10);
         }

         if (var10.monitorHolder[0] != null) {
            var10.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.process(var10);
         }
      }

      return var16;
   }

   public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_7, _WLDF$INST_JPFLD_JPMONS_7)) != null) {
         if (var8.argsCapture) {
            var8.args = new Object[2];
            Object[] var10000 = var8.args;
            var10000[0] = this;
            var10000[1] = sql;
         }

         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.preProcess(var8);
         }

         var8.resetPostBegin();
      }

      java.sql.CallableStatement var14;
      try {
         java.sql.CallableStatement ret = null;
         String methodName = "prepareCall";
         Object[] params = new Object[]{sql};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPC());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(true, sql);
            ret = CallableStatement.makeCallableStatement(val, this, sql, -1, -1);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var11) {
            this.invocationExceptionHandler(methodName, params, var11);
         }

         var14 = ret;
      } catch (Throwable var12) {
         if (var8 != null) {
            var8.th = var12;
            var8.ret = null;
            if (var8.monitorHolder[2] != null) {
               var8.monitorIndex = 2;
               InstrumentationSupport.postProcess(var8);
            }

            if (var8.monitorHolder[0] != null) {
               var8.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.process(var8);
            }
         }

         throw var12;
      }

      if (var8 != null) {
         var8.ret = var14;
         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.postProcess(var8);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }
      }

      return var14;
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_8, _WLDF$INST_JPFLD_JPMONS_8)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[4];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = InstrumentationSupport.convertToObject(resultSetType);
            var10000[3] = InstrumentationSupport.convertToObject(resultSetConcurrency);
         }

         if (var10.monitorHolder[1] != null) {
            var10.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.process(var10);
         }

         if (var10.monitorHolder[2] != null) {
            var10.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.preProcess(var10);
         }

         var10.resetPostBegin();
      }

      java.sql.CallableStatement var16;
      try {
         java.sql.CallableStatement ret = null;
         String methodName = "prepaceCall";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPC());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(true, sql, resultSetType, resultSetConcurrency);
            ret = CallableStatement.makeCallableStatement(val, this, sql, resultSetType, resultSetConcurrency);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var13) {
            this.invocationExceptionHandler(methodName, params, var13);
         }

         var16 = ret;
      } catch (Throwable var14) {
         if (var10 != null) {
            var10.th = var14;
            var10.ret = null;
            if (var10.monitorHolder[2] != null) {
               var10.monitorIndex = 2;
               InstrumentationSupport.postProcess(var10);
            }

            if (var10.monitorHolder[0] != null) {
               var10.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var10);
               InstrumentationSupport.process(var10);
            }
         }

         throw var14;
      }

      if (var10 != null) {
         var10.ret = var16;
         if (var10.monitorHolder[2] != null) {
            var10.monitorIndex = 2;
            InstrumentationSupport.postProcess(var10);
         }

         if (var10.monitorHolder[0] != null) {
            var10.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.process(var10);
         }
      }

      return var16;
   }

   public void addResultSet(ResultSet rset) {
      synchronized(this.rsets) {
         this.rsets.add(rset);
      }
   }

   public void removeResultSet(ResultSet rset) {
      synchronized(this.rsets) {
         this.rsets.remove(rset);
      }
   }

   public void closeAllResultSets() {
      HashSet curRsets;
      synchronized(this.rsets) {
         curRsets = (HashSet)this.rsets.clone();
         this.rsets.clear();
      }

      Iterator iter = curRsets.iterator();

      while(iter.hasNext()) {
         ResultSet rset = (ResultSet)iter.next();

         try {
            rset.internalClose(false);
         } catch (Exception var5) {
         }
      }

   }

   public void setFailed() throws SQLException {
      String methodName = "setFailed";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc == null) {
            throw new SQLException(JDBCUtil.getTextFormatter().featureNotSupported());
         }

         if (!cc.isInfected()) {
            try {
               JDBCConnectionPool pool = ConnectionPoolManager.getPool(cc.getPoolName(), cc.getAppName(), cc.getModuleName(), cc.getCompName());
               cc.setInfected(true);
               cc.setRefreshNeeded(true);
               if (pool != null) {
                  pool.removeConnection(cc);
                  if (!pool.isCreateConnectionInline()) {
                     WorkManagerFactory.getInstance().getSystem().schedule(this);
                  }
               }
            } catch (Exception var5) {
               JDBCLogger.logStackTrace(var5);
            }
         }

         super.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void clearWarnings() throws SQLException {
      String methodName = "clearWarnings";

      try {
         this.preInvocationHandler(methodName, (Object[])null);
         java.sql.Connection conn = this.checkConnection();
         conn.clearWarnings();
         super.postInvocationHandler(methodName, (Object[])null, (Object)null);
      } catch (Exception var3) {
         this.invocationExceptionHandler(methodName, (Object[])null, var3);
      }

   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_9, _WLDF$INST_JPFLD_JPMONS_9)) != null) {
         if (var8.argsCapture) {
            var8.args = InstrumentationSupport.toSensitive(4);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.preProcess(var8);
         }

         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         var8.resetPostBegin();
      }

      java.sql.Statement var10000;
      try {
         java.sql.Statement ret = null;
         String methodName = "createStatement";
         Object[] params = new Object[]{resultSetType, resultSetConcurrency, resultSetHoldability};

         try {
            this.preInvocationHandler(methodName, params);
            java.sql.Connection conn = this.checkConnection();
            ret = conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
            ret = Statement.makeStatement(ret, this, (String)null, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var11) {
            this.invocationExceptionHandler(methodName, params, var11);
         }

         var10000 = ret;
      } catch (Throwable var12) {
         if (var8 != null) {
            var8.th = var12;
            var8.ret = null;
            if (var8.monitorHolder[1] != null) {
               var8.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.process(var8);
            }

            if (var8.monitorHolder[0] != null) {
               var8.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.postProcess(var8);
            }
         }

         throw var12;
      }

      if (var8 != null) {
         var8.ret = var10000;
         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.postProcess(var8);
         }
      }

      return var10000;
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_10, _WLDF$INST_JPFLD_JPMONS_10)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[5];
            Object[] var10000 = var11.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = InstrumentationSupport.convertToObject(resultSetType);
            var10000[3] = InstrumentationSupport.convertToObject(resultSetConcurrency);
            var10000[4] = InstrumentationSupport.convertToObject(resultSetHoldability);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      java.sql.CallableStatement var17;
      try {
         java.sql.CallableStatement ret = null;
         String methodName = "prepaceCall";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency, resultSetHoldability};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPC());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(true, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            ret = CallableStatement.makeCallableStatement(val, this, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var14) {
            this.invocationExceptionHandler(methodName, params, var14);
         }

         var17 = ret;
      } catch (Throwable var15) {
         if (var11 != null) {
            var11.th = var15;
            var11.ret = null;
            InstrumentationSupport.postProcess(var11);
         }

         throw var15;
      }

      if (var11 != null) {
         var11.ret = var17;
         InstrumentationSupport.postProcess(var11);
      }

      return var17;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_11, _WLDF$INST_JPFLD_JPMONS_11)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[3];
            Object[] var10000 = var9.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = InstrumentationSupport.convertToObject(autoGeneratedKeys);
         }

         InstrumentationSupport.createDynamicJoinPoint(var9);
         InstrumentationSupport.preProcess(var9);
         var9.resetPostBegin();
      }

      java.sql.PreparedStatement var15;
      try {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, autoGeneratedKeys};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPS());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(false, sql, -1, -1, -1, autoGeneratedKeys, (int[])null, (String[])null);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1, -1, autoGeneratedKeys, (int[])null, (String[])null);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var12) {
            this.invocationExceptionHandler(methodName, params, var12);
         }

         var15 = ret;
      } catch (Throwable var13) {
         if (var9 != null) {
            var9.th = var13;
            var9.ret = null;
            InstrumentationSupport.postProcess(var9);
         }

         throw var13;
      }

      if (var9 != null) {
         var9.ret = var15;
         InstrumentationSupport.postProcess(var9);
      }

      return var15;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_12, _WLDF$INST_JPFLD_JPMONS_12)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[3];
            Object[] var10000 = var9.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = columnIndexes;
         }

         InstrumentationSupport.createDynamicJoinPoint(var9);
         InstrumentationSupport.preProcess(var9);
         var9.resetPostBegin();
      }

      java.sql.PreparedStatement var15;
      try {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, columnIndexes};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPS());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(false, sql, -1, -1, -1, -1, columnIndexes, (String[])null);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1, -1, -1, columnIndexes, (String[])null);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var12) {
            this.invocationExceptionHandler(methodName, params, var12);
         }

         var15 = ret;
      } catch (Throwable var13) {
         if (var9 != null) {
            var9.th = var13;
            var9.ret = null;
            InstrumentationSupport.postProcess(var9);
         }

         throw var13;
      }

      if (var9 != null) {
         var9.ret = var15;
         InstrumentationSupport.postProcess(var9);
      }

      return var15;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      LocalHolder var11;
      if ((var11 = LocalHolder.getInstance(_WLDF$INST_JPFLD_13, _WLDF$INST_JPFLD_JPMONS_13)) != null) {
         if (var11.argsCapture) {
            var11.args = new Object[5];
            Object[] var10000 = var11.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = InstrumentationSupport.convertToObject(resultSetType);
            var10000[3] = InstrumentationSupport.convertToObject(resultSetConcurrency);
            var10000[4] = InstrumentationSupport.convertToObject(resultSetHoldability);
         }

         InstrumentationSupport.createDynamicJoinPoint(var11);
         InstrumentationSupport.preProcess(var11);
         var11.resetPostBegin();
      }

      java.sql.PreparedStatement var17;
      try {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency, resultSetHoldability};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPS());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(false, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var14) {
            this.invocationExceptionHandler(methodName, params, var14);
         }

         var17 = ret;
      } catch (Throwable var15) {
         if (var11 != null) {
            var11.th = var15;
            var11.ret = null;
            InstrumentationSupport.postProcess(var11);
         }

         throw var15;
      }

      if (var11 != null) {
         var11.ret = var17;
         InstrumentationSupport.postProcess(var11);
      }

      return var17;
   }

   public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_14, _WLDF$INST_JPFLD_JPMONS_14)) != null) {
         if (var9.argsCapture) {
            var9.args = new Object[3];
            Object[] var10000 = var9.args;
            var10000[0] = this;
            var10000[1] = sql;
            var10000[2] = columnNames;
         }

         InstrumentationSupport.createDynamicJoinPoint(var9);
         InstrumentationSupport.preProcess(var9);
         var9.resetPostBegin();
      }

      java.sql.PreparedStatement var15;
      try {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, columnNames};

         try {
            this.preInvocationHandler(methodName, params);
            if (sql == null) {
               throw new SQLException(JDBCUtil.getTextFormatter().nullPS());
            }

            ConnectionEnv cc = this.getConnectionEnv();
            if (cc == null) {
               SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
               SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
               if (where != null) {
                  sqle.initCause(where);
               }

               throw sqle;
            }

            Object val = cc.getCachedStatement(false, sql, -1, -1, -1, -1, (int[])null, columnNames);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1, -1, -1, (int[])null, columnNames);
            super.postInvocationHandler(methodName, params, ret);
         } catch (Exception var12) {
            this.invocationExceptionHandler(methodName, params, var12);
         }

         var15 = ret;
      } catch (Throwable var13) {
         if (var9 != null) {
            var9.th = var13;
            var9.ret = null;
            InstrumentationSupport.postProcess(var9);
         }

         throw var13;
      }

      if (var9 != null) {
         var9.ret = var15;
         InstrumentationSupport.postProcess(var9);
      }

      return var15;
   }

   public boolean clearCachedStatement(Statement statement) throws SQLException {
      boolean ret = false;
      String methodName = "clearCachedStatement";
      Object[] params = new Object[]{statement};

      try {
         ret = this.clearCachedStatementInternal(statement);
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean clearCachedStatementInternal(Statement statement) throws SQLException {
      boolean ret = false;
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc == null) {
         throw new SQLException(JDBCUtil.getTextFormatter().featureNotSupported());
      } else {
         return statement == null ? false : cc.clearStatement(statement);
      }
   }

   public java.sql.Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      java.sql.Array ret = null;
      String methodName = "createArrayOf";
      Object[] params = new Object[]{typeName, elements};

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.createArrayOf(typeName, elements);
         ret = Array.makeArray(ret, (java.sql.Connection)this);
         super.postInvocationHandler(methodName, params, ret);
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.createBlob();
         ret = Blob.makeBlob(ret, (java.sql.Connection)this);
         super.postInvocationHandler(methodName, params, ret);
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.createClob();
         ret = Clob.makeClob(ret, (java.sql.Connection)this);
         super.postInvocationHandler(methodName, params, ret);
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.createNClob();
         ret = WrapperNClob.makeNClob(ret, (java.sql.Connection)this);
         super.postInvocationHandler(methodName, params, ret);
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.createSQLXML();
         ret = WrapperSQLXML.makeSQLXML(ret, (java.sql.Connection)this);
         super.postInvocationHandler(methodName, params, ret);
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         this.unwrapArray(attributes, conn);
         ret = conn.createStruct(typeName, attributes);
         ret = Struct.makeStruct(ret, (java.sql.Connection)this);
         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   private void unwrapArray(Object[] array, java.sql.Connection conn) throws SQLException {
      if (array != null) {
         for(int i = 0; i < array.length; ++i) {
            Object ret = array[i];
            if (ret instanceof JDBCWrapperImpl) {
               ret = ((JDBCWrapperImpl)ret).getVendorObj();
            }

            array[i] = ret;
         }

      }
   }

   public boolean isValid() throws SQLException {
      return this.isValid(15);
   }

   public boolean isValid(int timeout) throws SQLException {
      boolean ret = false;
      String methodName = "isValid";
      Object[] params = new Object[]{timeout};
      if (timeout < 0) {
         throw new SQLException(JDBCUtil.getTextFormatter().negativeTimeout());
      } else {
         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            java.sql.Connection conn = null;
            boolean connCheckOk = false;

            try {
               conn = this.checkConnection();
               connCheckOk = true;
            } catch (SQLException var24) {
            }

            if (connCheckOk) {
               ConnectionEnv env = this.getConnectionEnv();
               if (env == null) {
                  ret = true;
                  this.postInvocationHandlerNoWrap(methodName, params, ret);
                  return ret;
               }

               if (env instanceof HAConnectionEnv) {
                  HAConnectionEnv hace = (HAConnectionEnv)env;
                  RACPooledConnectionState rpcs = hace.getRACPooledConnectionState();
                  if (rpcs.closeOnRelease()) {
                     ret = false;
                     this.postInvocationHandlerNoWrap(methodName, params, ret);
                     return ret;
                  }
               }

               if (env.pool.getStateAsInt() == 102) {
                  ret = false;
                  this.postInvocationHandlerNoWrap(methodName, params, ret);
                  return ret;
               }

               if (env.supportIsValid()) {
                  ret = conn.isValid(timeout);
               } else if (env.conn.hasPingDatabaseMethod) {
                  try {
                     int value = (Integer)env.conn.pingDatabase.invoke(conn, timeout);
                     ret = value == env.conn.pingDatabaseOk;
                  } catch (Exception var23) {
                  }
               } else {
                  String testSql = env.pool.getResourceFactory().getTestQuery();
                  if (testSql == null) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled() && env.pool.getResourceFactory().isTestValidationFailed()) {
                        JdbcDebug.JDBCCONN.debug("isValid: skip execute test query since it failed to pass verification when creating connection pool. The test query or TestTableName is possibly wrong.");
                     }

                     ret = true;
                  } else {
                     java.sql.PreparedStatement ps = null;

                     try {
                        ps = conn.prepareStatement(testSql);
                        ps.setQueryTimeout(timeout);
                        ps.execute();
                        ret = true;
                     } catch (SQLException var22) {
                     } finally {
                        if (ps != null) {
                           try {
                              ps.close();
                           } catch (Exception var21) {
                           }
                        }

                     }
                  }
               }
            }

            this.postInvocationHandlerNoWrap(methodName, params, ret);
            return ret;
         } catch (Exception var26) {
            if (var26 instanceof SQLException) {
               throw (SQLException)var26;
            } else {
               throw new SQLException(var26.getMessage());
            }
         }
      }
   }

   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      String methodName = "setClientInfo";
      Object[] params = new Object[]{name, value};

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         conn.setClientInfo(name, value);
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         conn.setClientInfo(properties);
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

   public void close(int opt) throws SQLException {
      String methodName = "OracleConnection.close(int opt)";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         if (conn == null) {
            return;
         }

         ConnectionEnv cc = this.getConnectionEnv();
         if (cc == null) {
            return;
         }

         if (!cc.hasOracleProxyConnectionCloseMethod()) {
            throw new SQLException(JDBCUtil.getTextFormatter().closeNotSupported(conn.getClass().getName()));
         }

         cc.OracleProxyConnectionClose();
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public void openProxySession(int type, Properties prop) throws SQLException {
      String methodName = "OracleConnection.openProxySession(int type, Properties props)";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         if (conn == null) {
            return;
         }

         ConnectionEnv cc = this.getConnectionEnv();
         if (cc == null) {
            return;
         }

         if (!cc.hasOracleOpenProxySession()) {
            throw new SQLException(JDBCUtil.getTextFormatter().proxyNotSupported(conn.getClass().getName()));
         }

         cc.OracleOpenProxySession(type, prop);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

   }

   public void applyConnectionLabel(String key, String value) throws SQLException {
      if (this.isClosed()) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         this.checkHarvest();
         ConnectionEnv ce = this.getConnectionEnv();
         if (ce != null) {
            if (ce.drcpEnabled) {
               throw new SQLException(JDBCUtil.getTextFormatter().applyOnDRCP());
            }

            if (key == null || key.equals("")) {
               throw new SQLException(JDBCUtil.getTextFormatter().invalidLabelKey(key));
            }

            if (value == null) {
               value = "";
            }

            ce.addLabel(key, value);
         }

      }
   }

   public Properties getConnectionLabels() throws SQLException {
      if (this.isClosed()) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         this.checkHarvest();
         ConnectionEnv ce = this.getConnectionEnv();
         if (ce == null) {
            return null;
         } else if (ce.drcpEnabled) {
            throw new SQLException(JDBCUtil.getTextFormatter().getConnectionLabelsDRCP());
         } else {
            Properties labels = ce.getLabels();
            return labels != null && labels.size() == 0 ? null : labels;
         }
      }
   }

   public Properties getUnmatchedConnectionLabels(Properties requestedLabels) throws SQLException {
      if (this.isClosed()) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         this.checkHarvest();
         if (requestedLabels != null && requestedLabels.size() != 0) {
            ConnectionEnv ce = this.getConnectionEnv();
            if (ce == null) {
               return null;
            } else if (ce.drcpEnabled) {
               throw new SQLException(JDBCUtil.getTextFormatter().getUnmatchedConnectionLabelsDRCP());
            } else {
               Properties labels = ce.getLabels();
               Properties unmatchedLabels = new Properties();
               if (labels != null && !labels.isEmpty()) {
                  Iterator it = requestedLabels.entrySet().iterator();

                  while(it.hasNext()) {
                     Map.Entry label = (Map.Entry)it.next();
                     String key = (String)label.getKey();
                     String value = (String)label.getValue();
                     if (!labels.containsKey(key)) {
                        unmatchedLabels.setProperty(key, value);
                     } else {
                        String currentValue = labels.getProperty(key);
                        if (currentValue != null && !currentValue.equals(value)) {
                           unmatchedLabels.setProperty(key, value);
                        }
                     }
                  }

                  if (unmatchedLabels.size() == 0) {
                     return null;
                  } else {
                     return unmatchedLabels;
                  }
               } else {
                  unmatchedLabels.putAll(requestedLabels);
                  return unmatchedLabels;
               }
            }
         } else {
            return null;
         }
      }
   }

   public void removeConnectionLabel(String key) throws SQLException {
      if (this.isClosed()) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().connectionClosed2());
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         this.checkHarvest();
         ConnectionEnv ce = this.getConnectionEnv();
         if (ce != null) {
            if (ce.drcpEnabled) {
               throw new SQLException(JDBCUtil.getTextFormatter().removeConnectionLabelDRCP());
            }

            ce.removeLabel(key);
         }

      }
   }

   public void setConnectionHarvestable(boolean connectionHarvestable) throws SQLException {
      if (this.isClosed()) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException(JDBCUtil.getTextFormatter().badHarvestable(connectionHarvestable));
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else if (this.harvested) {
         throw new SQLException(JDBCUtil.getTextFormatter().accessHarvested());
      } else {
         ConnectionEnv ce = this.getConnectionEnv();
         if (ce != null) {
            ce.setConnectionHarvestable(connectionHarvestable);
         }

         this.connectionHarvestable = connectionHarvestable;
      }
   }

   public boolean isConnectionHarvestable() throws SQLException {
      if (this.harvested) {
         throw new SQLException(JDBCUtil.getTextFormatter().accessHarvested());
      } else {
         return this.connectionHarvestable;
      }
   }

   public void registerConnectionHarvestingCallback(ConnectionHarvestingCallback cbk) throws SQLException {
      if (this.harvested) {
         throw new SQLException(JDBCUtil.getTextFormatter().accessHarvested());
      } else if (this.connectionHarvestingCallback != null) {
         throw new SQLException(JDBCUtil.getTextFormatter().harvestCallbackSet());
      } else {
         this.connectionHarvestingCallback = cbk;
         ConnectionEnv ce = this.getConnectionEnv();
         if (ce != null) {
            ce.registerConnectionHarvestingCallback(cbk);
         }

      }
   }

   public void removeConnectionHarvestingCallback() throws SQLException {
      if (this.harvested) {
         throw new SQLException(JDBCUtil.getTextFormatter().accessHarvested());
      } else {
         this.connectionHarvestingCallback = null;
         ConnectionEnv ce = this.getConnectionEnv();
         if (ce != null) {
            ce.removeConnectionHarvestingCallback();
         }

      }
   }

   public void connectionHarvested() {
      this.harvested = true;

      try {
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc != null && cc.profiler != null && cc.profiler.isResourceLeakProfilingEnabled()) {
            this.doClose(true, 2);
         } else {
            this.doClose(false, 0);
         }
      } catch (Exception var2) {
      }

   }

   protected void checkHarvest() throws SQLException {
      if (this.harvested) {
         throw new SQLException(JDBCUtil.getTextFormatter().accessHarvested());
      }
   }

   public void setSchema(String schema) throws SQLException {
      String methodName = "setSchema";
      Object[] params = new Object[]{schema};

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         conn.setSchema(schema);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public String getSchema() throws SQLException {
      String methodName = "getSchema";
      String ret = null;
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.getSchema();
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
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         conn.abort(executor);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      String methodName = "setNetworkTimeout";
      Object[] params = new Object[]{executor, milliseconds};

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         conn.setNetworkTimeout(executor, milliseconds);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

   }

   public int getNetworkTimeout() throws SQLException {
      String methodName = "getNetworkTimeout";
      int ret = 0;
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         java.sql.Connection conn = this.checkConnection();
         ret = conn.getNetworkTimeout();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void commit() throws SQLException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_15, _WLDF$INST_JPFLD_JPMONS_15)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[1];
            var5.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.preProcess(var5);
         var5.resetPostBegin();
      }

      try {
         String methodName = "commit";
         Object[] params = new Object[0];
         java.sql.Connection conn = null;

         try {
            this.preInvocationHandler(methodName, params);
            conn = this.checkConnection();
            conn.commit();
            this.postInvocationHandler(methodName, params, (Object)null);
         } catch (SQLRecoverableException var9) {
            this.handleSQLRecoverableException(false, methodName, params, conn, var9, "");
         } catch (Exception var10) {
            this.handleNonSQLRecoverableExceptionDuringCommit(methodName, params, var10);
         }
      } catch (Throwable var11) {
         if (var5 != null) {
            var5.th = var11;
            InstrumentationSupport.postProcess(var5);
         }

         throw var11;
      }

      if (var5 != null) {
         InstrumentationSupport.postProcess(var5);
      }

   }

   void handleSQLRecoverableException(java.sql.Connection conn, SQLRecoverableException originalException, String additionalDebug) throws SQLException {
      this.handleSQLRecoverableException(true, (String)null, (Object[])null, conn, originalException, additionalDebug);
   }

   void handleSQLRecoverableException(boolean isJTSConnection, String methodName, Object[] params, java.sql.Connection conn, SQLRecoverableException originalException, String additionalDebug) throws SQLException {
      boolean isCommitOutcomeEnabled = this.isCommitOutcomeEnabled();
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("handleSQLRecoverableException this:" + this + " connection:" + conn + " SQLRecoverableException from commit:" + originalException + " isCommitOutcomeEnabled:" + isCommitOutcomeEnabled + additionalDebug);
      }

      if (!isCommitOutcomeEnabled) {
         if (isJTSConnection) {
            this.handleNonSQLRecoverableExceptionDuringCommit(originalException);
         } else {
            this.handleNonSQLRecoverableExceptionDuringCommit(methodName, params, originalException);
         }
      }

      boolean commitOutcome = false;
      Object ltxid = this.getLogicalTransactionId(conn, additionalDebug);

      try {
         commitOutcome = getTransactionOutcome(conn, ltxid);
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("handleSQLRecoverableException this:" + this + " connection:" + conn + " got commit outcome on first attempt:" + commitOutcome + additionalDebug);
         }
      } catch (SQLException var20) {
         boolean isCommitOutcomeDetermined = false;
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("handleSQLRecoverableException this:" + this + " connection:" + conn + " exception during initial attempt to resolve commit outcome:" + var20 + additionalDebug);
         }

         int currentRetryCount = 0;
         long startTime = System.currentTimeMillis();

         while(!isCommitOutcomeDetermined) {
            this.getConnectionEnv().getConnectionPool().incrementCommitOutcomeRetryTotalCount();
            ++currentRetryCount;

            try {
               commitOutcome = this.getTransactionOutcomeWithNewConnection(ltxid);
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JdbcDebug.JDBCCONN.debug("handleSQLRecoverableException this:" + this + " connection:" + conn + " current commit outcome retry count:" + currentRetryCount + " commitOutcome resolved:" + commitOutcome + additionalDebug);
               }

               isCommitOutcomeDetermined = true;
               if (commitOutcome) {
                  this.getConnectionEnv().getConnectionPool().incrementResolvedAsCommittedTotalCount();
               } else {
                  this.getConnectionEnv().getConnectionPool().incrementResolvedAsNotCommittedTotalCount();
               }
            } catch (SQLException var19) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JdbcDebug.JDBCCONN.debug("handleSQLRecoverableException this:" + this + " connection:" + conn + " current commit outcome retry count:" + currentRetryCount + " Exception encountered:" + var19 + additionalDebug);
               }

               if (System.currentTimeMillis() - startTime > (long)(this.getCommitOutcomeRetrySecondsValue() * 1000)) {
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("handleSQLRecoverableException this:" + this + " connection:" + conn + " current commit outcome retry count:" + currentRetryCount + " commit outcome retry seconds exceeded.   Throwing original Exception:" + originalException + additionalDebug);
                  }

                  this.getConnectionEnv().getConnectionPool().incrementUnresolvedTotalCount();
                  throw originalException;
               }

               try {
                  Thread.sleep(3000L);
               } catch (InterruptedException var18) {
               }
            }
         }
      }

      if (!commitOutcome) {
         try {
            if (isJTSConnection) {
               this.handleNonSQLRecoverableExceptionDuringCommit(originalException);
            } else {
               this.handleNonSQLRecoverableExceptionDuringCommit(methodName, params, originalException);
            }
         } catch (SQLException var17) {
            throw new RollbackSQLException(var17);
         }
      }

   }

   void handleNonSQLRecoverableExceptionDuringCommit(String methodName, Object[] params, Exception e) throws SQLException {
      if (e instanceof SQLException) {
         int code = ((SQLException)e).getErrorCode();
         if (ignoreAutoCommitException && code == 17273) {
            try {
               this.postInvocationHandler(methodName, params, (Object)null);
               return;
            } catch (Exception var6) {
               this.invocationExceptionHandler(methodName, params, var6);
            }
         }
      }

      this.invocationExceptionHandler(methodName, params, e);
   }

   void handleNonSQLRecoverableExceptionDuringCommit(Exception e) throws SQLException {
      if (e instanceof SQLException) {
         int code = ((SQLException)e).getErrorCode();
         if (ignoreAutoCommitException && code == 17273) {
            return;
         }
      }

      this.invocationExceptionHandler("internalCommit", (Object[])null, e);
   }

   boolean getTransactionOutcomeWithNewConnection(Object ltxid) throws SQLException {
      JDBCConnectionPool cp = null;
      ConnectionEnv ce = null;

      boolean var4;
      try {
         cp = ConnectionPoolManager.getPool(this.poolName, this.getConnectionEnv().getAppName(), this.getConnectionEnv().getModuleName(), this.getConnectionEnv().getCompName());
         ce = cp.reserveInternal(-2);
         if (ce != null && ce.conn != null && ce.conn.jconn != null) {
            java.sql.Connection newConn = ce.conn.jconn;
            boolean var5 = getTransactionOutcome(newConn, ltxid);
            return var5;
         }

         var4 = false;
      } catch (ResourceException var15) {
         throw new SQLException(var15);
      } finally {
         try {
            if (ce != null) {
               cp.release(ce);
            }
         } catch (ResourceException var14) {
         }

      }

      return var4;
   }

   Object getLogicalTransactionId(Object connection, String additionalDebug) throws SQLException {
      Object ltxid = this.getConnectionEnv().pool.getOracleHelper().getLogicalTransactionId(connection);
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("Connection:" + connection + " getLogicalTransactionId:" + ltxid + additionalDebug);
      }

      return ltxid;
   }

   boolean isCommitOutcomeEnabled() throws SQLException {
      Properties properties = this.getConnectionEnv().getDriverProperties();
      return properties != null && properties.getProperty("weblogic.jdbc.commitOutcomeEnabled") != null && properties.getProperty("weblogic.jdbc.commitOutcomeEnabled").equalsIgnoreCase("true") && this.getConnectionEnv().pool.getOracleVersion() >= 1202;
   }

   int getCommitOutcomeRetrySecondsValue() throws SQLException {
      Properties properties = this.getConnectionEnv().getDriverProperties();
      if (properties != null && properties.getProperty("weblogic.jdbc.commitOutcomeRetryMaxSeconds") != null) {
         String retrySecondsProperty = null;

         try {
            retrySecondsProperty = properties.getProperty("weblogic.jdbc.commitOutcomeRetryMaxSeconds");
            JdbcDebug.JDBCCONN.debug("Connection.getCommitOutcomeRetrySecondsValue Override for weblogic.jdbc.commitOutcomeRetryMaxSeconds driver property. Value:" + Integer.parseInt(retrySecondsProperty));
            return Integer.parseInt(retrySecondsProperty);
         } catch (NumberFormatException var4) {
            JdbcDebug.JDBCCONN.debug("Connection.getCommitOutcomeRetrySecondsValue Non-integer value provided for weblogic.jdbc.commitOutcomeRetryMaxSeconds driver property. Value:" + retrySecondsProperty, var4);
            return 120;
         }
      } else {
         return 120;
      }
   }

   static boolean getTransactionOutcome(java.sql.Connection conn, Object ltxid) throws SQLException {
      boolean committed = false;
      java.sql.CallableStatement cstmt = null;

      try {
         cstmt = conn.prepareCall("DECLARE PROCEDURE GET_LTXID_OUTCOME_WRAPPER(  ltxid IN RAW,  is_committed OUT NUMBER ) IS   call_completed BOOLEAN;   committed BOOLEAN; BEGIN   DBMS_APP_CONT.GET_LTXID_OUTCOME(ltxid, committed, call_completed);   if committed then is_committed := 1;  else is_committed := 0; end if; END; BEGIN GET_LTXID_OUTCOME_WRAPPER(?,?); END;");
         cstmt.setObject(1, ltxid);
         cstmt.registerOutParameter(2, -7);
         cstmt.execute();
         committed = cstmt.getBoolean(2);
      } finally {
         if (cstmt != null) {
            cstmt.close();
         }

      }

      return committed;
   }

   public void rollback() throws SQLException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_16, _WLDF$INST_JPFLD_JPMONS_16)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[1];
            var6.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      label127: {
         try {
            String methodName = "rollback";
            Object[] params = new Object[0];

            try {
               this.preInvocationHandlerNoCheck(methodName, params);
               ConnectionEnv cc = this.getConnectionEnv();
               if (cc != null && cc.conn != null && cc.conn.jconn != null) {
                  cc.conn.jconn.rollback();
               }

               this.postInvocationHandler(methodName, params, (Object)null);
            } catch (Exception var11) {
               label118: {
                  if (var11 instanceof SQLException) {
                     int code = ((SQLException)var11).getErrorCode();
                     if (ignoreAutoCommitException && code == 17274) {
                        try {
                           this.postInvocationHandler(methodName, params, (Object)null);
                           break label118;
                        } catch (Exception var10) {
                           this.invocationExceptionHandler(methodName, params, var10);
                        }
                     }
                  }

                  this.invocationExceptionHandler(methodName, params, var11);
               }
            }
            break label127;
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

         return;
      }

      if (var6 != null) {
         InstrumentationSupport.postProcess(var6);
      }

   }

   public int getReplayAttemptCount() {
      String methodName = "getReplayAttemptCount";
      Object[] params = new Object[0];
      int ret = 0;

      try {
         this.preInvocationHandler(methodName, params);
         ConnectionEnv cc = this.getConnectionEnv();
         if (cc != null) {
            ret = cc.getReplayAttemptCount();
         }

         super.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
      }

      return ret;
   }

   static {
      _WLDF$INST_FLD_JDBC_After_Statement_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Statement_Internal");
      _WLDF$INST_FLD_JDBC_Before_Statement_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Statement_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Rollback_Around_Low");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Create_Statement_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Create_Statement_Around_Medium");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Prepare_Around_Medium");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Commit_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "interopWriteReplace", "(Lweblogic/common/internal/PeerInfo;)Ljava/lang/Object;", 152, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "getVendorConnection", "()Ljava/sql/Connection;", 430, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "getVendorConnectionSafe", "()Ljava/sql/Connection;", 438, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "createStatement", "()Ljava/sql/Statement;", 597, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Create_Statement_Around_Medium", "JDBC_After_Statement_Internal", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Create_Statement_Around_Medium, _WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "createStatement", "(II)Ljava/sql/Statement;", 620, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Create_Statement_Around_Medium", "JDBC_After_Statement_Internal", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Create_Statement_Around_Medium, _WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareStatement", "(Ljava/lang/String;)Ljava/sql/PreparedStatement;", 644, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Statement_Internal", "JDBC_Diagnostic_Connection_Prepare_Around_Medium", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_6 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareStatement", "(Ljava/lang/String;II)Ljava/sql/PreparedStatement;", 678, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Statement_Internal", "JDBC_Diagnostic_Connection_Prepare_Around_Medium", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_6 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_7 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareCall", "(Ljava/lang/String;)Ljava/sql/CallableStatement;", 711, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Statement_Internal", "JDBC_Diagnostic_Connection_Prepare_Around_Medium", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true)}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_7 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_8 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareCall", "(Ljava/lang/String;II)Ljava/sql/CallableStatement;", 744, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_After_Statement_Internal", "JDBC_Diagnostic_Connection_Prepare_Around_Medium", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null}), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_8 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_9 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "createStatement", "(III)Ljava/sql/Statement;", 864, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Create_Statement_Around_Medium", "JDBC_After_Statement_Internal", "JDBC_Before_Statement_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCStatementRenderer", false, true), (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_9 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Create_Statement_Around_Medium, _WLDF$INST_FLD_JDBC_After_Statement_Internal, _WLDF$INST_FLD_JDBC_Before_Statement_Internal};
      _WLDF$INST_JPFLD_10 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareCall", "(Ljava/lang/String;III)Ljava/sql/CallableStatement;", 896, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Prepare_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_10 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_11 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareStatement", "(Ljava/lang/String;I)Ljava/sql/PreparedStatement;", 936, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Prepare_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_11 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_12 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareStatement", "(Ljava/lang/String;[I)Ljava/sql/PreparedStatement;", 970, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Prepare_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_12 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_13 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareStatement", "(Ljava/lang/String;III)Ljava/sql/PreparedStatement;", 1006, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Prepare_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_13 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_14 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "prepareStatement", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/sql/PreparedStatement;", 1046, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Prepare_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("sql", "weblogic.diagnostics.instrumentation.gathering.JDBCSqlStringRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_14 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Prepare_Around_Medium};
      _WLDF$INST_JPFLD_15 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "commit", "()V", 1769, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Commit_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_15 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium};
      _WLDF$INST_JPFLD_16 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "Connection.java", "weblogic.jdbc.wrapper.Connection", "rollback", "()V", 1975, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Rollback_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_16 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low};
      ignoreAutoCommitException = Boolean.valueOf(System.getProperty("weblogic.jdbc.ignoreAutoCommitException", "true"));
   }
}
