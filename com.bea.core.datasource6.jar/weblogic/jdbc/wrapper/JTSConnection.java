package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLWarning;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.common.ResourceException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.ConnectionPoolManager;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ProfileClosedUsage;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.utils.collections.ConcurrentHashMap;

public class JTSConnection extends Connection implements Synchronization {
   private static final long serialVersionUID = 6137263484778392280L;
   private static final ConcurrentHashMap ACTIVE_TRANSACTIONS;
   private transient ConnectionEnv cc;
   private java.sql.Connection delegateConn;
   private boolean connIsClosed = false;
   private boolean isClosed = false;
   private boolean doCloseDone = false;
   private String poolId;
   private String applicationName;
   private String moduleName;
   private String compName;
   private boolean emulate2pc = false;
   private String dsName;
   private transient XAResource xar;
   private transient LoggingResource loggingResource;
   private Transaction associatedTx;
   private transient ProfileClosedUsage profileClosedUsageConn = new ProfileClosedUsage();
   private SQLWarning sqlw = null;
   private volatile boolean alreadyTryingToRollback = false;
   static boolean ignoreAutoCommitException;
   private Object doCloseLockObject = new Object();
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.wrapper.JTSConnection");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public JTSConnection() {
   }

   JTSConnection(String poolId, String applicationName, String moduleName, String compName, boolean emulate2pc) {
      this.poolId = poolId;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.compName = compName;
      this.dsName = poolId;
      this.emulate2pc = emulate2pc;
   }

   public void init(String poolName, Transaction tx, String appName, String modName, String compName, ConnectionEnv cc) throws SQLException {
      this.poolId = poolName;
      this.applicationName = appName;
      this.moduleName = modName;
      this.compName = compName;
      this.associatedTx = tx;
      this.cc = cc;
      if (cc != null) {
         this.delegateConn = cc.conn.jconn;
         cc.registerConnectionHarvestedCallback(this);
         this.profileClosedUsage.setProfiler(cc.profiler);
      }

      if (tx == null) {
         if (cc != null && !cc.autoCommit) {
            try {
               this.delegateConn.setAutoCommit(true);
            } catch (SQLException var14) {
               try {
                  try {
                     this.delegateConn.rollback();
                  } catch (SQLException var12) {
                  }

                  this.delegateConn.setAutoCommit(true);
               } catch (SQLException var13) {
                  try {
                     this.internalClose();
                  } catch (SQLException var11) {
                  }

                  throw new SQLException("setting autoCommit(true) failed with " + var14.getMessage() + "\nWe then tried rolling back and retrying, and got " + var13.getMessage());
               }
            }

            cc.autoCommit = true;
         }

      } else {
         if (cc != null && cc.autoCommit) {
            try {
               this.delegateConn.setAutoCommit(false);
            } catch (SQLException var15) {
               try {
                  this.internalClose();
               } catch (SQLException var10) {
               }

               throw var15;
            }

            cc.autoCommit = false;
         }

         Integer il = (Integer)tx.getProperty("ISOLATION LEVEL");
         if (il != null) {
            this.setTransactionIsolation(il);
         }

         putActiveConnection(this.poolId, tx.getXid(), this);
      }
   }

   public void init(String poolName, java.sql.Connection c, Transaction atx) throws SQLException {
      this.delegateConn = c;
      this.associatedTx = atx;
      this.poolId = poolName;
      putActiveConnection(this.poolId, atx == null ? null : atx.getXid(), this);
      if (this.cc != null) {
         this.cc.setUserCon(this);
         this.cc.registerConnectionHarvestedCallback(this);
         this.profileClosedUsage.setProfiler(this.cc.profiler);
      }

   }

   public void finalizeInternal() {
      try {
         if (!this.isClosed) {
            if (this.cc != null) {
               this.cc.getConnectionPool().incrementLeakedConnectionCount();
               if (this.cc.getConnectionPool().getProfiler().isResourceLeakProfilingEnabled()) {
                  JDBCLogger.logConnectionLeakWarning(this.cc.getCurrentUser());
                  this.cc.getConnectionPool().getProfiler().addLeakData(this.cc);
               }
            }

            this.close();
         }
      } catch (SQLException var2) {
      }

   }

   private void checkIfClosed() throws SQLException {
      if (this.connIsClosed) {
         SQLException where = this.profileClosedUsageConn.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException("Attempted operation on Connection that is already closed.");
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      }
   }

   public void initCopy(JTSConnection c, Transaction atx, String poolName, String appName) throws SQLException {
      this.delegateConn = c.getConnection();
      this.associatedTx = atx;
      this.vendorObj = this.delegateConn;
      this.poolId = poolName;
      this.applicationName = appName;
      this.cc = c.getConnectionEnv();
      this.stmts = c.stmts;
   }

   public final void checkIfRolledBack() throws SQLException {
      try {
         if (this.cc != null) {
            this.cc.setUsed(true);
         }

         Transaction tx = this.getAssociatedTx();
         if (tx != null) {
            int st = tx.getStatus();
            if (st != 0) {
               if (st != 7 || tx.getProperty("LLR_TX_WRITE") == null) {
                  if (tx.getProperty("LLR_TX_COMMIT") == null) {
                     if (st != 1 || tx.getProperty("DISABLE_TX_STATUS_CHECK") == null) {
                        throw new SQLException("The transaction is no longer active - status: '" + tx.getStatusAsString() + "'. No further JDBC access is allowed within this transaction.");
                     }
                  }
               }
            }
         }
      } catch (SystemException var3) {
         this.invocationExceptionHandler("checkIfRolledBack", (Object[])null, var3);
      }
   }

   public java.sql.Connection checkConnection() throws SQLException {
      this.checkHarvest();
      ConnectionEnv cc = this.getConnectionEnv();
      this.checkMTUsage(cc);
      this.checkIfRolledBack();
      return this.getOrCreateConnection();
   }

   public java.sql.Connection checkConnection(ConnectionEnv cc) throws SQLException {
      this.checkHarvest();
      this.checkMTUsage(cc);
      this.checkIfRolledBack();
      return this.getOrCreateConnection();
   }

   public String getPoolName() {
      return this.cc != null ? this.cc.getPoolName() : null;
   }

   synchronized java.sql.Connection getDelegateConn() throws SQLException {
      return this.delegateConn;
   }

   public synchronized java.sql.Connection getConnection() throws SQLException {
      this.checkIfClosed();
      if (this.cc != null) {
         this.cc.checkIfEnabled();
      }

      return this.delegateConn;
   }

   public JTSConnection getConnection(Xid xid) {
      JTSConnection ret = getActiveConnection(this.poolId, xid);
      return ret;
   }

   public synchronized java.sql.Connection getOrCreateConnection() throws SQLException {
      this.checkIfClosed();
      if (this.cc != null) {
         this.cc.checkIfEnabled();
      }

      if (this.delegateConn != null) {
         return this.delegateConn;
      } else if (this.poolId == null) {
         throw new SQLException("JTS JDBC Driver being called without a pool name");
      } else {
         Transaction tx = null;

         try {
            tx = this.getAssociatedTx();
            if (tx != null && tx.getStatus() != 0 && (tx.getStatus() != 1 || tx.getProperty("DISABLE_TX_STATUS_CHECK") == null)) {
               throw new SQLException("No JDBC connection can be made\nbecause the transaction state is\n" + this.status2String(tx.getStatus()));
            }
         } catch (SystemException var10) {
            this.invocationExceptionHandler("getOrCreateConnection", (Object[])null, var10);
         }

         long timeToLiveMillis = 0L;
         if (tx != null) {
            timeToLiveMillis = tx.getTimeToLiveMillis();
         }

         if (timeToLiveMillis <= 0L) {
            throw new SQLException("No JDBC connection can be made\nbecause the transaction has timed out\n");
         } else {
            int timeToLiveSecs = true;
            int timeToLiveSecs;
            if (timeToLiveMillis > 2000000000L) {
               timeToLiveSecs = (int)(timeToLiveMillis / 1000L);
            } else {
               timeToLiveSecs = (int)((timeToLiveMillis + 999L) / 1000L);
            }

            try {
               this.cc = ConnectionPoolManager.reserve(this.poolId, this.applicationName, this.moduleName, this.compName, timeToLiveSecs);
               this.cc.setJTS();
            } catch (Exception var9) {
               this.invocationExceptionHandler("Cannot obtain connection after " + timeToLiveSecs + " seconds. ", (Object[])null, var9);
            }

            int txStatus = 5;

            try {
               txStatus = tx.getStatus();
            } catch (SystemException var8) {
            }

            if (txStatus != 0 && tx.getProperty("DISABLE_TX_STATUS_CHECK") == null) {
               try {
                  ConnectionPoolManager.release(this.cc);
               } catch (Exception var7) {
                  throw new SQLException("Can't release pool connection!\n" + var7);
               }

               throw new SQLException("No JDBC connection can be made\nbecause the transaction state is\n" + this.status2String(txStatus));
            } else {
               this.delegateConn = this.cc.conn.jconn;
               this.vendorObj = this.delegateConn;
               if (this.cc.autoCommit) {
                  this.delegateConn.setAutoCommit(false);
                  this.cc.autoCommit = false;
               }

               Integer il = (Integer)tx.getProperty("ISOLATION LEVEL");
               if (il != null) {
                  this.setTransactionIsolation(il);
               }

               this.cc.setUsed(true);
               return this.delegateConn;
            }
         }
      }
   }

   public void setEmulateTwoPhaseCommit(boolean aEmulate2pc) {
      this.emulate2pc = aEmulate2pc;
   }

   public boolean getEmulate2PC() {
      return this.emulate2pc;
   }

   public static JTSConnection getConnAssociatedWithTx(String poolName, Transaction tx) {
      return tx == null ? null : getActiveConnection(poolName, tx.getXID());
   }

   public boolean doesPoolExist() {
      boolean poolFound = false;

      try {
         poolFound = ConnectionPoolManager.poolExists(this.poolId, this.applicationName, this.moduleName, this.compName);
      } catch (ResourceException var3) {
      }

      return poolFound;
   }

   public final void setPool(String pool) {
      this.poolId = pool;
   }

   public String getPool() {
      return this.poolId;
   }

   public ConnectionEnv getConnectionEnv() {
      return this.cc;
   }

   public SQLWarning getWarnings() throws SQLException {
      if (this.isConnClosed()) {
         return this.sqlw;
      } else {
         java.sql.Connection conn = this.getConnection();
         return conn != null ? conn.getWarnings() : null;
      }
   }

   public boolean isConnClosed() throws SQLException {
      return this.connIsClosed;
   }

   void internalClose() throws SQLException {
      if (!this.isConnClosed()) {
         synchronized(this) {
            if (this.connIsClosed) {
               return;
            }

            this.connIsClosed = true;
         }

         this.profileClosedUsageConn.saveWhereClosed();
         if (this.delegateConn != null) {
            try {
               this.sqlw = this.delegateConn.getWarnings();
            } catch (Exception var10) {
            }
         }

         if (this.cc != null) {
            try {
               if (this.cc.isPooled()) {
                  this.closeAllResultSets();
                  this.closeAllStatements(true);
                  ConnectionPoolManager.release(this.cc);
               } else {
                  this.closeAllStatements(true);
                  this.cc.destroy();
               }
            } catch (Exception var8) {
               this.invocationExceptionHandler("Error while releasing connection back to pool: ", (Object[])null, var8);
            } finally {
               this.cc = null;
               this.delegateConn = null;
            }
         } else if (this.delegateConn != null) {
            this.delegateConn.close();
         }

         if (this.getAssociatedTx() != null) {
            removeActiveConnection(this.poolId, this.getAssociatedTx().getXID());
         }

      }
   }

   public void beforeCompletion() {
   }

   public void afterCompletion(int status) {
      try {
         this.internalClose();
      } catch (SQLException var3) {
      }

   }

   public final XAResource getXAResource() {
      if (this.xar == null) {
         this.xar = new JTSEmulateXAResourceImpl(this);
      }

      return this.xar;
   }

   public final LoggingResource getLoggingResource() throws SQLException {
      if (this.loggingResource == null) {
         this.loggingResource = new JTSLoggableResourceImpl(this);
      }

      return this.loggingResource;
   }

   private Transaction getAssociatedTx() {
      return this.associatedTx;
   }

   public final String getAssociatedTxDetails() {
      Transaction tx = this.getAssociatedTx();
      return tx == null ? "" : "{tx = " + tx.getXID() + ":" + tx.getName() + ":" + tx.getStatusAsString() + "}";
   }

   public synchronized void internalRollback() throws SQLException {
      java.sql.Connection conn = this.getDelegateConn();

      try {
         if (this.isConnClosed()) {
            return;
         }

         if (conn != null) {
            this.cancelAllStatements();
            if (this.alreadyTryingToRollback) {
               Executor abort41Executor = new Abort41Executor();
               this.abort(abort41Executor);
               this.alreadyTryingToRollback = false;
               return;
            }

            this.alreadyTryingToRollback = true;

            try {
               this.doConnRollback(conn);
               this.alreadyTryingToRollback = false;
            } catch (Exception var12) {
               if (conn.isClosed()) {
                  return;
               }

               try {
                  this.doConnRollback(conn);
                  this.alreadyTryingToRollback = false;
               } catch (Exception var11) {
                  throw new SQLException("Two rollbacks failed. The first one threw " + var12.getMessage() + ". The second one threw " + var11.getMessage());
               }
            }

            this.cc.setLastSuccessfulConnectionUse();
         }
      } catch (Exception var13) {
         try {
            this.doConnRollback(conn);
         } catch (Exception var10) {
         }

         this.invocationExceptionHandler("internalRollback", (Object[])null, var13);
      } finally {
         this.alreadyTryingToRollback = false;
         this.internalClose();
      }

   }

   void doConnRollback(java.sql.Connection conn) throws SQLException {
      conn.rollback();
   }

   public void internalCommit() throws SQLException {
      java.sql.Connection conn = null;

      try {
         JdbcDebug.JDBCCONN.debug("JTSConnection.internalCommit this:" + this + " before getConnection conn:" + conn + " associatedTx:" + this.getAssociatedTxDetails());
         conn = this.getConnection();
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("JTSConnection.internalCommit this:" + this + " after getConnection connection:" + conn + " associatedTx:" + this.getAssociatedTxDetails());
         }

         if (conn != null) {
            conn.commit();
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("JTSConnection.internalCommit this:" + this + " after conn.commit connection:" + conn + " associatedTx:" + this.getAssociatedTxDetails());
            }

            this.cc.setLastSuccessfulConnectionUse();
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("JTSConnection.internalCommit this:" + this + " after setLastSuccessfulConnectionUse connection:" + conn + " associatedTx:" + this.getAssociatedTxDetails());
            }
         }
      } catch (SQLRecoverableException var7) {
         if (conn == null) {
            this.handleNonSQLRecoverableExceptionDuringCommit(var7);
         } else {
            this.handleSQLRecoverableException(conn, var7, " associatedTx:" + this.getAssociatedTxDetails());
         }
      } catch (SQLException var8) {
         this.handleNonSQLRecoverableExceptionDuringCommit(var8);
      } finally {
         this.internalClose();
      }

   }

   protected String getTraceInfo(String s) {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[JTS Conn] ").append(s).append(" ").append(this.getAssociatedTxDetails()).append(", conn=").append(this.delegateConn);
      if (this.delegateConn != null && this.cc != null && this.cc.getVendorId() != 102) {
         try {
            sb.append(", txIsolation=").append(this.delegateConn.getTransactionIsolation());
         } catch (SQLException var5) {
         }

         try {
            sb.append(", autoCommit=").append(this.delegateConn.getAutoCommit());
         } catch (SQLException var4) {
         }
      }

      return sb.toString();
   }

   public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
      if (this.cc != null) {
         return super.prepareStatement(sql);
      } else {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareStatement");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareStatement(sql);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var7) {
            this.invocationExceptionHandler(methodName, params, var7);
         }

         return ret;
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      if (this.cc != null) {
         return super.prepareStatement(sql, resultSetType, resultSetConcurrency);
      } else {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareStatement");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, resultSetType, resultSetConcurrency);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var9) {
            this.invocationExceptionHandler(methodName, params, var9);
         }

         return ret;
      }
   }

   public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
      if (this.cc != null) {
         return super.prepareCall(sql);
      } else {
         java.sql.CallableStatement ret = null;
         String methodName = "prepareCall";
         Object[] params = new Object[]{sql};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareCall");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareCall(sql);
            ret = CallableStatement.makeCallableStatement(val, this, sql, -1, -1);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var7) {
            this.invocationExceptionHandler(methodName, params, var7);
         }

         return ret;
      }
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      if (this.cc != null) {
         return super.prepareCall(sql, resultSetType, resultSetConcurrency);
      } else {
         java.sql.CallableStatement ret = null;
         String methodName = "prepareCall";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareCall");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareCall(sql, resultSetType, resultSetConcurrency);
            ret = CallableStatement.makeCallableStatement(val, this, sql, resultSetType, resultSetConcurrency);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var9) {
            this.invocationExceptionHandler(methodName, params, var9);
         }

         return ret;
      }
   }

   public final void setAutoCommit(boolean isAutoCommit) throws SQLException {
      String methodName = "setAutoCommit";
      Object[] params = new Object[]{isAutoCommit};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.checkIfRolledBack();
         if (TransactionHelper.getTransactionHelper().getTransaction() != null && isAutoCommit) {
            throw new SQLException("Cannot set auto commit to \"true\" when in distributed transaction.");
         }

         java.sql.Connection conn = this.getOrCreateConnection();
         conn.setAutoCommit(isAutoCommit);
         if (this.cc != null) {
            this.cc.autoCommit = isAutoCommit;
         }

         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public final void commit() throws SQLException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[1];
            var3.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.preProcess(var3);
         var3.resetPostBegin();
      }

      label110: {
         try {
            if (TransactionHelper.getTransactionHelper().getTransaction() != null) {
               throw new SQLException("Cannot call Connection.commit in distributed transaction.  Transaction Manager will commit the resource manager when the distributed transaction is committed.");
            }

            try {
               this.checkConnection().commit();
            } catch (SQLException var6) {
               int code = var6.getErrorCode();
               if (ignoreAutoCommitException && code == 17273) {
                  break label110;
               }

               this.invocationExceptionHandler("commit", (Object[])null, var6);
            }
         } catch (Throwable var7) {
            if (var3 != null) {
               var3.th = var7;
               InstrumentationSupport.postProcess(var3);
            }

            throw var7;
         }

         if (var3 != null) {
            InstrumentationSupport.postProcess(var3);
         }

         return;
      }

      if (var3 != null) {
         InstrumentationSupport.postProcess(var3);
      }

   }

   public final void rollback() throws SQLException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[1];
            var3.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.preProcess(var3);
         var3.resetPostBegin();
      }

      label110: {
         try {
            if (TransactionHelper.getTransactionHelper().getTransaction() != null) {
               throw new SQLException("Cannot call Connection.rollback in distributed transaction.  Transaction Manager will commit the resource manager when the distributed transaction is committed.");
            }

            try {
               this.checkConnection().rollback();
            } catch (SQLException var6) {
               int code = var6.getErrorCode();
               if (ignoreAutoCommitException && code == 17274) {
                  break label110;
               }

               this.invocationExceptionHandler("rollback", (Object[])null, var6);
            }
         } catch (Throwable var7) {
            if (var3 != null) {
               var3.th = var7;
               InstrumentationSupport.postProcess(var3);
            }

            throw var7;
         }

         if (var3 != null) {
            InstrumentationSupport.postProcess(var3);
         }

         return;
      }

      if (var3 != null) {
         InstrumentationSupport.postProcess(var3);
      }

   }

   public final void close() throws SQLException {
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

   public final void close(boolean me) throws SQLException {
      try {
         if (me) {
            this.internalClose();
         }
      } catch (SQLException var3) {
      }

   }

   public boolean isClosed() throws SQLException {
      String methodName = "isClosed";
      Object[] params = new Object[0];
      boolean ret = this.isClosed;

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         if (!ret) {
            if (this.delegateConn == null) {
               ret = true;
            } else {
               ret = this.delegateConn.isClosed();
            }

            if (ret) {
               try {
                  this.internalClose();
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

   public int getTransactionIsolation() throws SQLException {
      int ret = 0;
      String methodName = "getTransactionIsolation";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.checkIfRolledBack();
         java.sql.Connection conn = this.getOrCreateConnection();
         ret = conn.getTransactionIsolation();
         this.postInvocationHandlerNoWrap(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setTransactionIsolation(int transactionIsolation) throws SQLException {
      String methodName = "setTransactionIsolation";
      Object[] params = new Object[]{transactionIsolation};

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.checkIfRolledBack();
         java.sql.Connection conn = this.getOrCreateConnection();
         if (conn.getTransactionIsolation() != transactionIsolation) {
            conn.setTransactionIsolation(transactionIsolation);
            if (this.cc != null) {
               this.cc.setDirtyIsolationLevel(transactionIsolation);
            }
         }

         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   protected final void doClose(boolean forcedClose, int type) throws SQLException {
      String methodName = "close";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandlerNoCheck(methodName, params);
         this.isClosed = true;
         this.profileClosedUsage.saveWhereClosed();
         if (!this.isConnClosed()) {
            boolean didClose = false;
            synchronized(this.doCloseLockObject) {
               if (!this.doCloseDone) {
                  this.doCloseDone = true;
                  didClose = true;
               }
            }

            if (didClose) {
               String poolName;
               if (forcedClose) {
                  poolName = null;
                  String userName = null;
                  if (this.cc != null) {
                     poolName = this.cc.getPoolName();
                     userName = this.cc.getCurrentUser();
                  }

                  this.logDoClose(type, poolName, this.toString());
               }

               if (this.getAssociatedTx() == null) {
                  java.sql.Connection conn = this.getConnection();
                  if (this.cc != null) {
                     try {
                        if (this.cc.isPooled() && !this.cc.isInfected()) {
                           this.closeAllStatements(true);
                           ConnectionPoolManager.release(this.cc);
                        } else {
                           this.closeAllStatements(true);
                           this.cc.destroy();
                        }
                     } catch (Exception var8) {
                        this.invocationExceptionHandler("Error while releasing connection back to pool: ", params, var8);
                     }
                  } else if (conn != null) {
                     conn.close();
                  }

                  poolName = null;
               }
            }
         }

         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var10) {
         this.invocationExceptionHandler(methodName, params, var10);
      }

   }

   public final void setDataSourceName(String name) {
      this.dsName = name;
   }

   public final String getDataSourceName() {
      return this.dsName;
   }

   private String status2String(int status) {
      switch (status) {
         case 0:
            return "Active";
         case 1:
            return "Marked Rollback";
         case 2:
            return "Prepared";
         case 3:
            return "Committed";
         case 4:
            return "Rolledback";
         case 5:
            return "Unknown";
         case 6:
            return "No Transaction";
         case 7:
            return "Preparing";
         case 8:
            return "Committing";
         case 9:
            return "Rolling Back";
         default:
            return "Unknown";
      }
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      if (this.cc != null) {
         return super.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      } else {
         java.sql.CallableStatement ret = null;
         String methodName = "prepareCall";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency, resultSetHoldability};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareCall");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareCall(sql, resultSetType, resultSetConcurrency);
            ret = CallableStatement.makeCallableStatement(val, this, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var10) {
            this.invocationExceptionHandler(methodName, params, var10);
         }

         return ret;
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      if (this.cc != null) {
         return super.prepareStatement(sql, autoGeneratedKeys);
      } else {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, autoGeneratedKeys};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareStatement");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareStatement(sql, autoGeneratedKeys);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1, -1, autoGeneratedKeys, (int[])null, (String[])null);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var8) {
            this.invocationExceptionHandler(methodName, params, var8);
         }

         return ret;
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      if (this.cc != null) {
         return super.prepareStatement(sql, columnIndexes);
      } else {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, columnIndexes};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareStatement");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareStatement(sql, columnIndexes);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1, -1, -1, columnIndexes, (String[])null);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var8) {
            this.invocationExceptionHandler(methodName, params, var8);
         }

         return ret;
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      if (this.cc != null) {
         return super.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      } else {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, resultSetType, resultSetConcurrency, resultSetHoldability};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareStatement");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, resultSetType, resultSetConcurrency, resultSetHoldability, -1, (int[])null, (String[])null);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var10) {
            this.invocationExceptionHandler(methodName, params, var10);
         }

         return ret;
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      if (this.cc != null) {
         return super.prepareStatement(sql, columnNames);
      } else {
         java.sql.PreparedStatement ret = null;
         String methodName = "prepareStatement";
         Object[] params = new Object[]{sql, columnNames};

         try {
            this.preInvocationHandlerNoCheck(methodName, params);
            if (sql == null) {
               throw new SQLException("Null SQL statement passed to prepareStatement");
            }

            java.sql.Connection conn = this.checkConnection();
            Object val = conn.prepareStatement(sql, columnNames);
            ret = PreparedStatement.makePreparedStatement(val, this, sql, -1, -1, -1, -1, (int[])null, columnNames);
            this.postInvocationHandlerNoWrap(methodName, params, ret);
         } catch (Exception var8) {
            this.invocationExceptionHandler(methodName, params, var8);
         }

         return ret;
      }
   }

   private static JTSConnection putActiveConnection(String poolId, Xid xid, JTSConnection jtsConn) {
      Map poolXids = (Map)ACTIVE_TRANSACTIONS.get(poolId);
      if (poolXids == null) {
         synchronized(ACTIVE_TRANSACTIONS) {
            poolXids = (Map)ACTIVE_TRANSACTIONS.get(poolId);
            if (poolXids == null) {
               poolXids = new ConcurrentHashMap();
               ACTIVE_TRANSACTIONS.put(poolId, poolXids);
            }
         }
      }

      return (JTSConnection)((Map)poolXids).put(xid, jtsConn);
   }

   private static JTSConnection getActiveConnection(String poolId, Xid xid) {
      Map poolXids = (Map)ACTIVE_TRANSACTIONS.get(poolId);
      return poolXids == null ? null : (JTSConnection)poolXids.get(xid);
   }

   private static JTSConnection removeActiveConnection(String poolId, Xid xid) {
      Map poolXids = (Map)ACTIVE_TRANSACTIONS.get(poolId);
      return poolXids == null ? null : (JTSConnection)poolXids.remove(xid);
   }

   static {
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Rollback_Around_Low");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Close_Around_Medium");
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Commit_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSConnection.java", "weblogic.jdbc.wrapper.JTSConnection", "commit", "()V", 715, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Commit_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Commit_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSConnection.java", "weblogic.jdbc.wrapper.JTSConnection", "rollback", "()V", 735, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Rollback_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Rollback_Around_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSConnection.java", "weblogic.jdbc.wrapper.JTSConnection", "close", "()V", 756, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Close_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium};
      ACTIVE_TRANSACTIONS = new ConcurrentHashMap();
      ignoreAutoCommitException = Boolean.valueOf(System.getProperty("weblogic.jdbc.ignoreAutoCommitException", "true"));
   }

   protected class Abort41Executor implements Executor {
      public void execute(Runnable runnable) {
         runnable.run();
      }
   }
}
