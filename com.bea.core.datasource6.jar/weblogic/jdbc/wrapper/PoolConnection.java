package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.SQLWarning;
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

public class PoolConnection extends Connection {
   private static final long serialVersionUID = 9066620943313441691L;
   private transient ConnectionEnv cc;
   private java.sql.Connection delegateConn;
   private boolean connIsClosed = false;
   private String poolName = "Pool name not yet set";
   private boolean transientConnection = false;
   private SQLWarning sqlw = null;
   private Object doCloseLockObject = new Object();
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.wrapper.PoolConnection");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public void init(ConnectionEnv aCc) throws SQLException {
      this.cc = aCc;
      this.transientConnection = false;
      if (this.cc != null) {
         this.delegateConn = this.cc.conn.jconn;
         this.poolName = this.cc.getPoolName();
         this.cc.registerConnectionHarvestedCallback(this);
         this.profileClosedUsage.setProfiler(this.cc.profiler);
      }

      String re_init_failures = null;
      Exception first_prob = null;
      if (this.cc != null && !this.cc.autoCommit) {
         try {
            try {
               if (this.delegateConn.getClass().getName().indexOf("sybase") != -1) {
                  this.delegateConn.rollback();
               }

               this.delegateConn.setAutoCommit(true);
            } catch (Exception var7) {
               re_init_failures = "First we got " + var7.getMessage();
               this.delegateConn.rollback();
               this.delegateConn.setAutoCommit(true);
            }

            this.cc.autoCommit = true;
         } catch (Exception var8) {
            re_init_failures = re_init_failures + ", then we got " + var8.getMessage();

            try {
               this.doClose(true, 4);
            } catch (Exception var6) {
            }

            if (var8 instanceof SQLRecoverableException) {
               SQLRecoverableException sre = new SQLRecoverableException("Failed to setAutoCommit to true for pool connection: " + re_init_failures);
               sre.initCause((Throwable)first_prob);
               throw sre;
            }

            SQLException se = new SQLException("Failed to setAutoCommit to true for pool connection: " + re_init_failures);
            se.initCause((Throwable)first_prob);
            throw se;
         }
      }

   }

   public void initTransient(ConnectionEnv aCc) throws SQLException {
      this.cc = aCc;
      this.transientConnection = true;
      if (this.cc != null) {
         this.delegateConn = this.cc.conn.jconn;
         this.poolName = this.cc.getPoolName();
      }

      if (this.cc != null && !this.cc.autoCommit) {
         try {
            this.delegateConn.setAutoCommit(true);
            this.cc.autoCommit = true;
         } catch (Exception var3) {
         }
      }

   }

   public java.sql.Connection checkConnection() throws SQLException {
      this.checkHarvest();
      this.checkMTUsage(this.cc);
      if (this.connIsClosed) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException("Connection has already been closed.");
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         this.cc.checkIfEnabled();
         return this.delegateConn;
      }
   }

   public java.sql.Connection checkConnection(ConnectionEnv cc) throws SQLException {
      this.checkHarvest();
      this.checkMTUsage(cc);
      if (this.connIsClosed) {
         SQLException where = this.profileClosedUsage.addClosedUsageProfilingRecord();
         SQLException sqle = new SQLException("Connection has already been closed.");
         if (where != null) {
            sqle.initCause(where);
         }

         throw sqle;
      } else {
         cc.checkIfEnabled();
         return this.delegateConn;
      }
   }

   public String getPoolName() {
      return this.poolName;
   }

   public ConnectionEnv getConnectionEnv() {
      return this.cc;
   }

   public void finalizeInternal() {
      try {
         if (!this.connIsClosed) {
            this.cc.getConnectionPool().incrementLeakedConnectionCount();
            if (this.cc.getConnectionPool().getProfiler().isResourceLeakProfilingEnabled()) {
               JDBCLogger.logConnectionLeakWarning(this.cc.getCurrentUser());
               this.cc.getConnectionPool().getProfiler().addLeakData(this.cc);
            }

            this.close();
         }
      } catch (Exception var2) {
      }

   }

   public void refresh() throws Exception {
      this.checkConnection();
      this.cc.pool.getResourceFactory().refreshResource(this.cc);
      if (this.cc.drcpEnabled) {
         this.cc.OracleAttachServerConnection();
      }

   }

   protected String getTraceInfo(String s) {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[Pool Conn] ").append(s).append(", conn = ").append(this.delegateConn);
      if (this.delegateConn != null) {
         try {
            sb.append(", txIsolationLevel=").append(this.delegateConn.getTransactionIsolation());
         } catch (SQLException var5) {
         }

         try {
            sb.append(", autoCommit=").append(this.delegateConn.getAutoCommit());
         } catch (SQLException var4) {
         }
      }

      return sb.toString();
   }

   public void close() throws SQLException {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
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
      boolean ret = this.connIsClosed;

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

   public void setAutoCommit(boolean isAutoCommit) throws SQLException {
      String methodName = "setAutoCommit";
      Object[] params = new Object[]{isAutoCommit};

      try {
         this.preInvocationHandler(methodName, params);
         this.delegateConn.setAutoCommit(isAutoCommit);
         this.cc.autoCommit = isAutoCommit;
         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public void setTransactionIsolation(int transactionIsolation) throws SQLException {
      String methodName = "setTransactionIsolation";
      Object[] params = new Object[]{transactionIsolation};

      try {
         this.preInvocationHandler(methodName, params);
         this.delegateConn.setTransactionIsolation(transactionIsolation);
         this.cc.setDirtyIsolationLevel(transactionIsolation);
         this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public SQLWarning getWarnings() throws SQLException {
      if (this.connIsClosed) {
         return this.sqlw;
      } else {
         return this.delegateConn != null ? this.delegateConn.getWarnings() : null;
      }
   }

   protected final void doClose(boolean forcedClose, int type) throws SQLException {
      if (!this.connIsClosed) {
         if (!this.transientConnection) {
            String methodName = "close";
            Object[] params = new Object[0];

            try {
               this.preInvocationHandlerNoCheck(methodName, params);

               try {
                  synchronized(this.doCloseLockObject) {
                     if (this.connIsClosed) {
                        return;
                     }

                     this.connIsClosed = true;
                  }

                  this.profileClosedUsage.saveWhereClosed();
                  if (forcedClose) {
                     this.logDoClose(type, this.cc.getPoolName(), this.toString());
                  }

                  try {
                     this.sqlw = this.delegateConn.getWarnings();
                  } catch (Exception var7) {
                  }

                  this.closeAllResultSets();
                  this.closeAllStatements(true);
                  ConnectionPoolManager.release(this.cc);
                  this.cc = null;
                  this.vendorObj = null;
               } catch (Exception var9) {
                  SQLException sqle = new SQLException("Exception when releasing connection to pool:\n" + var9);
                  sqle.initCause(var9);
                  throw sqle;
               }

               this.postInvocationHandlerNoWrap(methodName, params, (Object)null);
            } catch (Exception var10) {
               this.invocationExceptionHandler(methodName, params, var10);
            }

         }
      }
   }

   static {
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Close_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "PoolConnection.java", "weblogic.jdbc.wrapper.PoolConnection", "close", "()V", 175, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Close_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Close_Around_Medium};
   }
}
