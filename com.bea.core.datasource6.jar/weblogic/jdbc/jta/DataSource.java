package weblogic.jdbc.jta;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.sql.XADataSource;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourceCleanupHandler;
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
import weblogic.jdbc.common.internal.ConnectionPoolProfiler;
import weblogic.jdbc.common.internal.FeatureNotSupportedException;
import weblogic.jdbc.common.internal.HAConnectionPool;
import weblogic.jdbc.common.internal.JDBCConnectionPool;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.internal.ParentLogger;
import weblogic.jdbc.common.internal.VendorId;
import weblogic.jdbc.common.internal.XAAffinityCallback;
import weblogic.jdbc.common.internal.XAConnectionEnvFactory;
import weblogic.jdbc.common.rac.RACAffinityContextException;
import weblogic.jdbc.common.rac.RACAffinityContextHelperFactory;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.jdbc.extensions.WLDataSource;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JTAConnection;
import weblogic.jdbc.wrapper.TxInfo;
import weblogic.jdbc.wrapper.XA;
import weblogic.jdbc.wrapper.XAConnection;
import weblogic.transaction.TimedOutException;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.XAResource;
import weblogic.transaction.internal.ServerTransactionImpl;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.XAUtils;

public final class DataSource extends ParentLogger implements Driver, javax.sql.DataSource, WLDataSource, XAResource {
   private Properties props;
   private String poolID;
   private JDBCConnectionPool pool;
   private ConnectionPoolProfiler profiler;
   private String applicationName;
   private String moduleName;
   private String compName;
   private String user;
   private String password;
   private int loginTimeout;
   private PrintWriter logWriter;
   private volatile XADataSource rmDataSource;
   private int connId = 0;
   private int jdbcxaDebugLevel = 10;
   private int vid = -1;
   private boolean keepXAConnTillTxComplete = true;
   private boolean keepConnAfterLocalTx = true;
   private boolean keepConnAfterGlobalTx = false;
   private boolean keepConnOpenOnRelease = false;
   private boolean callRecoverOnlyOnce = false;
   private boolean callRecoverStartAndEnd = false;
   private boolean supportSetTxIsolation = true;
   private boolean supportSetTxIsolationUponEnlistment = true;
   private boolean rollbackLocalTxUponConnClose = false;
   private boolean xaResumeAsJoin = false;
   private boolean ociDriver = false;
   private String txInfoPropName;
   private transient String txMPPropName;
   private int xaTransactionTimeout;
   private int xaRetryDurationSeconds = 0;
   private long xaRetryDurationMillis;
   private int xaRetryIntervalSeconds;
   private boolean enableResourceHealthMonitoring = true;
   private String testTableName;
   private String doneRegSyncPropName;
   private String disableSetTxIsoPropName;
   private int ORATMSERIALIZABLE = 1024;
   private int ORATMNOMIGRATE = 2;
   private boolean useDatabaseCredentials;
   private transient Hashtable xaRegistrationProperties;
   private String xaResourceRegistrationName;
   static boolean recoverRollbackAtFinally;
   static boolean debugConditionalResourceStartException;
   static boolean debugConditionalResourcePrepareException;
   private int oracleVersion = 0;
   private boolean gotOracleVersion = false;
   static final long serialVersionUID = -8924218472784391015L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.jta.DataSource");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Connection_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Connection_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Commit_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Rollback_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Start_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Rollback_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Start_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Is_SameRM_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Commit_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Start_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Prepare_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Commit_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Rollback_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_End_Before_High;
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

   public String toString() {
      return this.poolID;
   }

   public void setProperties(Properties props) {
      this.init(props);
   }

   public void setXAResourceRegistrationName(String name) {
      this.xaResourceRegistrationName = name;
   }

   private synchronized void init(Properties props) {
      if (this.props == null && props != null) {
         this.props = (Properties)props.clone();
         this.poolID = (String)props.get("connectionPoolID");
         this.txInfoPropName = ("weblogic.jdbc.jta." + this.poolID).intern();
         this.txMPPropName = ("weblogic.jdbc.mp." + this.poolID).intern();
         this.disableSetTxIsoPropName = ("DisableTxIsoSet." + this.poolID).intern();
         this.doneRegSyncPropName = ("doneRegistrationSynchronization." + this.poolID).intern();
         String poolScope = (String)props.get("connectionPoolScope");
         String appName = null;
         if (poolScope != null && !poolScope.equalsIgnoreCase("global")) {
            appName = JDBCHelper.getHelper().getCurrentApplicationName();
            if (appName != null) {
               this.applicationName = appName;
            } else {
               this.applicationName = (String)props.get("applicationName");
            }

            this.moduleName = (String)props.get("moduleName");
            this.compName = (String)props.get("compName");
         }

         this.user = (String)props.get("user");
         this.password = (String)props.get("password");
         this.useDatabaseCredentials = Boolean.valueOf((String)props.get("useDatabaseCredentials"));
         this.jdbcxaDebugLevel = JdbcDebug.getDebugLevel(props);
         JdbcDebug.setDataSource(this.poolID, this);
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.log(this, "DataSource properties:" + props);
         }

         String xato = (String)props.get("xaTransactionTimeout");
         if (xato != null) {
            this.xaTransactionTimeout = Integer.parseInt(xato);
         }

         String xards = (String)props.get("xaRetryDurationSeconds");
         if (xards != null) {
            int tempRetryDurationSec = Integer.parseInt(xards);
            if (this.xaRetryDurationSeconds > 0 && tempRetryDurationSec == 0) {
               Exception e = new Exception(this.poolID + ": Resetting the xaRetryDurationSeconds to 0");
               JDBCLogger.logStackTrace(e);
            }

            this.xaRetryDurationSeconds = tempRetryDurationSec;
            this.xaRetryDurationMillis = (long)this.xaRetryDurationSeconds * 1000L;
         }

         String xaris = (String)props.get("xaRetryIntervalSeconds");
         if (xaris != null) {
            this.xaRetryIntervalSeconds = Integer.parseInt(xaris);
         }

         try {
            this.setXAConnEnvFactoryDS();
         } catch (Exception var8) {
         }

         try {
            String drivername = (String)props.get("drivername");
            if (drivername != null && (this.vid = VendorId.get(drivername)) != -1) {
               JdbcDebug.log(this, "Using Driver " + VendorId.toString(this.vid));
            } else {
               this.vid = VendorId.get(this.getXADataSource().getClass().getName());
            }
         } catch (Exception var9) {
         }

         this.enableResourceHealthMonitoring = this.getBooleanProp(props, "enableResourceHealthMonitoring", true);
         this.testTableName = this.getStringProp(props, "testTableName", (String)null);
         this.initVendorWorkarounds(props);
      }
   }

   public boolean isVendor(int vid) {
      return this.vid == vid;
   }

   public String getVendorName() {
      return VendorId.toString(this.vid);
   }

   public boolean getKeepXAConnTillTxComplete() {
      return this.keepXAConnTillTxComplete;
   }

   public boolean getKeepConnAfterLocalTx() {
      return this.keepConnAfterLocalTx;
   }

   public boolean getKeepConnAfterGlobalTx() {
      return this.keepConnAfterGlobalTx;
   }

   private boolean getBooleanProp(Properties props, String propName, boolean def) {
      Object value = props.get(propName);
      return value != null ? ((String)value).equalsIgnoreCase("true") : def;
   }

   private int getIntProp(Properties props, String propName, int def) {
      Object value = props.get(propName);
      return value != null ? Integer.parseInt((String)value) : def;
   }

   private String getStringProp(Properties props, String propName, String def) {
      Object value = props.get(propName);
      return value != null ? (String)value : def;
   }

   private void initVendorWorkarounds(Properties props) {
      this.keepXAConnTillTxComplete = this.getBooleanProp(props, "keepXAConnTillTxComplete", true);
      this.keepConnOpenOnRelease = this.getBooleanProp(props, "keepLogicalConnOpenOnRelease", false);
      this.keepConnAfterLocalTx = this.getBooleanProp(props, "keepConnAfterLocalTx", false);
      this.keepConnAfterGlobalTx = this.getBooleanProp(props, "keepConnAfterGlobalTx", false);
      this.callRecoverOnlyOnce = this.getBooleanProp(props, "callRecoverOnlyOnce", false);
      this.rollbackLocalTxUponConnClose = this.getBooleanProp(props, "rollbackLocalTxUponConnClose", false);
      switch (this.vid) {
         case 0:
            this.callRecoverOnlyOnce = true;
            this.supportSetTxIsolation = true;
         case 1:
         case 3:
         case 6:
         case 14:
         default:
            break;
         case 2:
            this.keepConnOpenOnRelease = true;
            break;
         case 4:
         case 10:
            this.keepXAConnTillTxComplete = true;
            break;
         case 5:
            this.supportSetTxIsolationUponEnlistment = false;
            this.supportSetTxIsolation = false;
            break;
         case 7:
         case 9:
         case 15:
            this.keepConnOpenOnRelease = true;
            this.keepXAConnTillTxComplete = true;
            if (props != null) {
               props.setProperty("callXAEndAtTxTimeout", "true");
               if (this.isVendor(15)) {
                  props.setProperty("callXAEndWithTMSUCCESSInsteadOfTMSUSPEND", "true");
               }
            }
            break;
         case 8:
            this.callRecoverStartAndEnd = true;
            this.keepXAConnTillTxComplete = true;
            if (props != null) {
               props.setProperty("callXAEndAtTxTimeout", "true");
               props.setProperty("callXASetTransactionTimeout", "true");
            }
            break;
         case 11:
            this.keepConnOpenOnRelease = true;
            this.supportSetTxIsolation = false;
            break;
         case 12:
            this.supportSetTxIsolation = false;
            break;
         case 13:
            this.callRecoverOnlyOnce = true;
      }

      try {
         JDBCConnectionPool[] pools = ConnectionPoolManager.getConnectionPools(this.poolID, this.applicationName, this.moduleName, this.compName);
         this.pool = pools[0];
         this.profiler = (ConnectionPoolProfiler)this.pool.getProfiler();
         if (this.pool.isNativeXA()) {
            this.xaResumeAsJoin = true;
            this.keepXAConnTillTxComplete = false;
         }

         if (this.vid == 0) {
            String url = this.pool.getURL();
            if (url != null) {
               String s = url.toLowerCase(Locale.ENGLISH);
               if (s.indexOf("oci") != -1) {
                  this.ociDriver = true;
               }
            }
         }
      } catch (Exception var5) {
      }

   }

   private boolean driverHasAbort(XAConnection xaConn) throws Exception {
      ConnectionEnv ce = xaConn.getConnectionEnv();
      return ce.conn.isAbortSupported();
   }

   private void callDriverAbort(XAConnection xaConn) throws Exception {
      ConnectionEnv ce = xaConn.getConnectionEnv();
      if (ce.conn.isAbortSupported()) {
         ce.conn.invokeAbort(xaConn);
      }

   }

   private void makeSureThisConnectionIsNeverUsedAgain(XAConnection xaConn) throws Exception {
      ConnectionEnv connEnv = xaConn.getConnectionEnv();
      if (connEnv != null) {
         connEnv.setInfected(true);
         connEnv.setRefreshNeeded(true);
         connEnv.disable();
         connEnv.pool.removeResource(connEnv);
      }

   }

   private void callResetLastSuccessfulConnectionUse(XAConnection xaConn) {
      ConnectionEnv connEnv = xaConn.getConnectionEnv();
      if (connEnv != null) {
         connEnv.resetLastSuccessfulConnectionUse();
      }

   }

   public boolean supportsLocal() {
      return true;
   }

   public boolean keepConnOpenOnRelease() {
      return this.keepConnOpenOnRelease;
   }

   public boolean supportSetTxIsolation() {
      return this.supportSetTxIsolation;
   }

   public boolean rollbackLocalTxUponConnClose() {
      return this.rollbackLocalTxUponConnClose;
   }

   public boolean isMultiPoolDataSource() {
      return this.pool.isMemberDS();
   }

   public Connection connect(String url, Properties info) throws SQLException {
      this.init(info);
      return this.getConnection();
   }

   public boolean acceptsURL(String url) throws SQLException {
      return true;
   }

   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      throw new SQLException("Not supported");
   }

   public int getMajorVersion() {
      return 2;
   }

   public int getMinorVersion() {
      return 0;
   }

   public boolean jdbcCompliant() {
      return true;
   }

   public void registerConnectionLabelingCallback(ConnectionLabelingCallback cbk) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void removeConnectionLabelingCallback() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Connection getConnection(Properties labels) throws SQLException {
      return this.getConnectionInternal((String)null, (String)null, labels);
   }

   private Connection getConnectionInternal(String username, String password, Properties labels) throws SQLException {
      Connection ret = null;
      if (JdbcDebug.isEnabled((DataSource)this, 20)) {
         JdbcDebug.enter(this, "DataSource.getConnection()");
      }

      SQLException se = null;

      try {
         int id;
         synchronized(this) {
            id = this.connId++;
         }

         boolean needTxContext = true;
         Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
         if (tx == null) {
            needTxContext = false;
         }

         XAConnection xaConn = this.refreshXAConnAndEnlist((XAConnection)null, (JTAConnection)null, needTxContext, labels, username, password);
         Object connWrapper = null;

         try {
            connWrapper = JDBCWrapperFactory.getWrapper(2, xaConn, false);
         } catch (Exception var17) {
            JDBCLogger.logStackTrace(var17);
         }

         if (connWrapper != null) {
            ((JTAConnection)connWrapper).init(this, id, xaConn);
         }

         xaConn.getConnectionEnv().setResourceCleanupHandler((ResourceCleanupHandler)connWrapper);
         if (xaConn.getOwner() == null) {
            xaConn.setOwner(connWrapper);
         } else {
            xaConn.addConnection((JTAConnection)connWrapper);
         }

         ret = (Connection)connWrapper;
      } catch (SQLException var19) {
         se = var19;
      } finally {
         if (se == null) {
            if (JdbcDebug.isEnabled((DataSource)this, 20)) {
               JdbcDebug.leave(this, "DataSource.getConnection returns " + ret);
            }

         }

         if (JdbcDebug.isEnabled((DataSource)this, 20)) {
            JdbcDebug.err((DataSource)this, "DataSource.getConnection", se);
         }

         throw se;
      }

      return ret;
   }

   public Connection getConnection(String username, String password, Properties labels) throws SQLException {
      if (this.useDatabaseCredentials || (username == null || username.equals(this.user)) && (password == null || password.equals(this.password))) {
         return this.getConnectionInternal(username, password, labels);
      } else {
         throw new SQLException("Value of user and password must match those of the data source properties");
      }
   }

   public void registerConnectionInitializationCallback(ConnectionInitializationCallback cbk) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public void unregisterConnectionInitializationCallback() throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Connection getConnection() throws SQLException {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var1.argsCapture) {
            var1.args = new Object[1];
            var1.args[0] = this;
         }

         if (var1.monitorHolder[0] != null) {
            var1.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.preProcess(var1);
         }

         if (var1.monitorHolder[2] != null) {
            var1.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         var1.resetPostBegin();
      }

      Connection var10000;
      try {
         var10000 = this.getConnection((Properties)null);
      } catch (Throwable var3) {
         if (var1 != null) {
            var1.th = var3;
            var1.ret = null;
            if (var1.monitorHolder[1] != null) {
               var1.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var1);
               InstrumentationSupport.process(var1);
            }

            if (var1.monitorHolder[0] != null) {
               var1.monitorIndex = 0;
               InstrumentationSupport.postProcess(var1);
            }
         }

         throw var3;
      }

      if (var1 != null) {
         var1.ret = var10000;
         if (var1.monitorHolder[1] != null) {
            var1.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         if (var1.monitorHolder[0] != null) {
            var1.monitorIndex = 0;
            InstrumentationSupport.postProcess(var1);
         }
      }

      return var10000;
   }

   public Connection getConnection(String username, String password) throws SQLException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[3];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = username;
            var10000[2] = password;
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.preProcess(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      Connection var6;
      try {
         var6 = this.getConnection(username, password, (Properties)null);
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            var3.ret = null;
            if (var3.monitorHolder[1] != null) {
               var3.monitorIndex = 1;
               InstrumentationSupport.createDynamicJoinPoint(var3);
               InstrumentationSupport.process(var3);
            }

            if (var3.monitorHolder[0] != null) {
               var3.monitorIndex = 0;
               InstrumentationSupport.postProcess(var3);
            }
         }

         throw var5;
      }

      if (var3 != null) {
         var3.ret = var6;
         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.postProcess(var3);
         }
      }

      return var6;
   }

   public PrintWriter getLogWriter() throws SQLException {
      return this.logWriter;
   }

   public void setLogWriter(PrintWriter out) throws SQLException {
      this.getXADataSource().setLogWriter(out);
      this.logWriter = out;
   }

   public void setLoginTimeout(int seconds) throws SQLException {
      this.getXADataSource().setLoginTimeout(seconds);
      this.loginTimeout = seconds;
   }

   public int getLoginTimeout() throws SQLException {
      return this.loginTimeout;
   }

   private XAConnectionEnvFactory getXAConnEnvFactory() throws Exception {
      JDBCConnectionPool[] pools = ConnectionPoolManager.getConnectionPools(this.poolID, this.applicationName, this.moduleName, this.compName);
      return (XAConnectionEnvFactory)pools[0].getResourceFactory();
   }

   public XADataSource getXADataSource() throws SQLException {
      if (this.rmDataSource == null) {
         synchronized(this) {
            if (this.rmDataSource == null) {
               try {
                  this.rmDataSource = this.getXAConnEnvFactory().getXADataSource();
               } catch (Exception var4) {
                  throw new SQLException("Cannot obtain XADataSource from Connection Pool");
               }
            }
         }
      }

      return this.rmDataSource;
   }

   private int oracleTMNOMIGRATE(int flags) {
      if (this.vid == 0 && !this.ociDriver) {
         if (!this.gotOracleVersion) {
            try {
               this.gotOracleVersion = true;
               this.oracleVersion = this.getXAConnEnvFactory().getDriverMajorVersion() * 100 + this.getXAConnEnvFactory().getDriverMinorVersion();
            } catch (Exception var3) {
            }
         }

         if (this.oracleVersion >= 1002) {
            if ((flags & 134217728) != 0 || (flags & 33554432) != 0) {
               if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                  JdbcDebug.log(this, "oracleTMNOMIGRATE about to return " + XA.flagsToString(flags) + " | ORATMNOMIGRATE");
               }

               return flags | this.ORATMNOMIGRATE;
            }

            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.log(this, "oracleTMNOMIGRATE about to return " + XA.flagsToString(flags));
            }
         }

         return flags;
      } else {
         return flags;
      }
   }

   public int getDelistFlag() {
      int flags = 67108864;
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      TxInfo txInfo = null;
      XAConnection xaConn = null;
      if (tx != null) {
         txInfo = this.getTxInfo(tx);
         if (txInfo != null) {
            xaConn = txInfo.getXAConnection();
         }

         if (xaConn != null && !this.xaResumeAsJoin && !xaConn.canReleaseToPool()) {
            flags = 33554432;
         }
      }

      if (JdbcDebug.isEnabled((DataSource)this, 20)) {
         String msg = "DataSource.getDelistFlag:" + Integer.toHexString(flags);
         if (txInfo != null) {
            msg = msg + ", txInfo.xaConn:" + xaConn;
         }

         JdbcDebug.log(this, msg);
      }

      return flags;
   }

   public boolean detectedUnavailable() {
      if (!this.enableResourceHealthMonitoring) {
         if (JdbcDebug.isEnabled((DataSource)this, 20)) {
            JdbcDebug.log(this, "DataSource[" + this.poolID + "].detectedUnavailable enableResourceHealthMonitoring is false");
         }

         return true;
      } else if (this.testTableName != null && !this.testTableName.equals("")) {
         XAConnection xaConn = null;
         ConnectionEnv ce = null;

         try {
            ce = ConnectionPoolManager.reserve(this.poolID, this.applicationName, this.moduleName, this.compName, -1);
            xaConn = (XAConnection)ce.conn.jconn;
         } catch (Exception var9) {
            if (JdbcDebug.isEnabled((DataSource)this, 20)) {
               JdbcDebug.err((DataSource)this, "DataSource[" + this.poolID + "].detectedUnavailable unable to obtain XAConnection", var9);
            }

            return true;
         }

         if (xaConn == null) {
            return true;
         } else {
            xaConn.setDataSource(this);

            boolean var4;
            try {
               int result = ce.test();
               if (result == 1 || result == 0) {
                  if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                     JdbcDebug.log(this, "DataSource[" + this.poolID + "].detectedUnavailable test query of " + this.testTableName + " OKAY");
                  }

                  var4 = true;
                  return var4;
               }

               if (result != -1) {
                  return true;
               }

               ce.setInfected(true);
               if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                  JdbcDebug.log(this, "DataSource[" + this.poolID + "].detectedUnavailable test query of " + this.testTableName + "test failed");
               }

               var4 = false;
               return var4;
            } catch (Exception var10) {
               if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                  JdbcDebug.err((DataSource)this, "DataSource[" + this.poolID + "].detectedUnavailable test query of " + this.testTableName, var10);
               }

               var4 = false;
            } finally {
               xaConn.releaseToPool();
            }

            return var4;
         }
      } else {
         if (JdbcDebug.isEnabled((DataSource)this, 20)) {
            JdbcDebug.log(this, "DataSource[" + this.poolID + "].detectedUnavailable testTableName not specified");
         }

         return true;
      }
   }

   public void start(Xid xid, int flags) throws XAException {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var12.argsCapture) {
            var12.args = InstrumentationSupport.toSensitive(3);
         }

         if (var12.monitorHolder[0] != null) {
            var12.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var12);
            InstrumentationSupport.process(var12);
         }

         if (var12.monitorHolder[1] != null) {
            var12.monitorIndex = 1;
            InstrumentationSupport.process(var12);
         }

         var12.resetPostBegin();
      }

      try {
         Transaction tx = (Transaction)this.getTM().getTransaction(xid);
         if (tx != null) {
            TxInfo txInfo = this.getTxInfo(tx);
            XAConnection xaConn = null;
            XAException xae = null;

            try {
               if (txInfo != null) {
                  xaConn = txInfo.getXAConnection();
               }

               if (xaConn != null) {
                  javax.transaction.xa.XAResource xaRes = xaConn.getXAResource();
                  flags = this.oracleTMNOMIGRATE(flags);
                  flags = this.processTxIsolationProp(tx, flags);
                  if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                     JdbcDebug.enter(this, tx, "XAResource.start(" + XA.xidToString(xid) + ", " + XA.flagsToString(flags) + "), xaRes:" + xaRes);
                  }

                  try {
                     if (xaConn.user_autocommit_state) {
                        try {
                           xaConn.setAutoCommit(false);
                        } catch (Exception var32) {
                        }
                     }

                     xaRes.start(xid, flags);
                  } catch (Throwable var34) {
                     if (this.profiler.isResourceLastUsageProfilingEnabled()) {
                        xaConn.getConnectionEnv().setCurrentError(var34);
                        xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
                     }

                     throw var34;
                  }

                  if (txInfo != null) {
                     synchronized(txInfo) {
                        txInfo.setEnded(false);
                        txInfo.setTxInfoXid(xid);
                        txInfo.setEnlisted(true);
                        txInfo.setCancelStmtCompleted(false);
                        txInfo.setAborted(false);
                        txInfo.setRollbackCalled(false);
                     }
                  }

                  if (this.keepXAConnTillTxComplete) {
                     String regSet = null;
                     if (txInfo != null) {
                        synchronized(txInfo) {
                           if (txInfo.getProperties() != null) {
                              regSet = txInfo.getProperties().getProperty(this.doneRegSyncPropName);
                           }

                           if (regSet == null || regSet != null && !Boolean.valueOf(regSet)) {
                              tx.registerSynchronization(new CallbackHandler(tx, txInfo));
                              txInfo.getProperties().setProperty(this.doneRegSyncPropName, "true");
                           }
                        }
                     }
                  }

                  if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                     JdbcDebug.leave(this, tx, "XAResource.start returns");
                  }
               } else {
                  xae = XA.createException("Internal error during start for XAResource '" + this.poolID + "'", -3);
                  if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                     JdbcDebug.err((DataSource)this, tx, "Internal error", xae);
                  }
               }
            } catch (SQLException var35) {
               if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                  JdbcDebug.err((DataSource)this, tx, "SQL error: " + var35.getErrorCode(), var35);
               }

               this.removeTxAssoc(tx, true);
               xae = XA.createException(var35, "start failed for XAResource '" + this.poolID + "': " + var35.getMessage(), -3);
            } catch (XAException var36) {
               if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                  JdbcDebug.err((DataSource)this, tx, XAUtils.appendOracleXAResourceInfo(var36, "XA error: ") + " " + XA.errToString(var36.errorCode), var36);
               }

               xae = XA.createException(var36, "Unexpected error during start for XAResource '" + this.poolID + "': " + XAUtils.appendOracleXAResourceInfo(var36, "XA error: ") + ": " + var36.getMessage(), var36.errorCode);
               this.removeTxAssoc(tx, true);
            } catch (Throwable var37) {
               if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                  JdbcDebug.err(this, tx, "Unexpected error", var37);
               }

               this.removeTxAssoc(tx, true);
               xae = XA.createException(var37, "Unexpected error during start for XAResource '" + this.poolID + "': " + var37.getMessage(), -3);
            } finally {
               if (xae != null) {
                  if (debugConditionalResourceStartException && tx instanceof ServerTransactionImpl) {
                     ((ServerTransactionImpl)tx).printDebugMessages();
                  }

                  throw xae;
               }

            }
         }
      } catch (Throwable var39) {
         if (var12 != null) {
            var12.th = var39;
            if (var12.monitorHolder[2] != null) {
               var12.monitorIndex = 2;
               InstrumentationSupport.process(var12);
            }
         }

         throw var39;
      }

      if (var12 != null && var12.monitorHolder[2] != null) {
         var12.monitorIndex = 2;
         InstrumentationSupport.process(var12);
      }

   }

   public void end(Xid xid, int flags) throws XAException {
      LocalHolder var16;
      if ((var16 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var16.argsCapture) {
            var16.args = InstrumentationSupport.toSensitive(3);
         }

         if (var16.monitorHolder[1] != null) {
            var16.monitorIndex = 1;
            InstrumentationSupport.process(var16);
         }

         if (var16.monitorHolder[2] != null) {
            var16.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var16);
            InstrumentationSupport.process(var16);
         }

         var16.resetPostBegin();
      }

      try {
         label1165: {
            Transaction tx = (Transaction)this.getTM().getTransaction(xid);
            TxInfo txInfo = null;
            javax.transaction.xa.XAResource xaRes = null;
            XAConnection xaConn = null;
            XAException xae = null;
            boolean var45 = false;

            String impTx;
            label1166: {
               label1167: {
                  try {
                     var45 = true;
                     if (tx != null) {
                        txInfo = this.getTxInfo(tx);
                        if (txInfo == null) {
                           txInfo = new TxInfo((XAConnection)null);
                           this.setTxInfo(tx, txInfo);
                        }

                        synchronized(txInfo) {
                           xaConn = txInfo.getXAConnection();
                           if (xaConn == null) {
                              xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                              txInfo.setXAConnection(xaConn);
                              xaConn.setOwner(this);
                              xaConn.setTransaction(tx);
                           }
                        }
                     } else if (xaConn == null) {
                        xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                     }

                     xaRes = xaConn.getXAResource();
                     flags = this.oracleTMNOMIGRATE(flags);
                     if (tx != null && txInfo != null && xaConn != null && tx.isTxAsyncTimeout() && !txInfo.isCancelStmtCompleted()) {
                        xaConn.cancelAllStatements();
                        if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                           JdbcDebug.log(this, tx, "XAResource.end call statement.cancel in tx:" + XA.xidToString(xid) + ",  xaRes:" + xaRes);
                        }

                        txInfo.setCancelStmtCompleted(true);
                     }

                     try {
                        if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                           JdbcDebug.enter(this, tx, "XAResource.end(" + XA.xidToString(xid) + ", " + XA.flagsToString(flags) + "), xaRes:" + xaRes);
                        }

                        xaRes.end(xid, flags);
                     } catch (Throwable var55) {
                        if (this.profiler.isResourceLastUsageProfilingEnabled()) {
                           xaConn.getConnectionEnv().setCurrentError(var55);
                           xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
                        }

                        throw var55;
                     }

                     if (txInfo != null) {
                        txInfo.setEnded(true);
                     }

                     if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                        JdbcDebug.leave(this, tx, "XAResource.end returns");
                        var45 = false;
                     } else {
                        var45 = false;
                     }
                     break label1166;
                  } catch (XAException var56) {
                     if (tx != null) {
                        String impTx = (String)tx.getProperty("IMP_TX_STATUS_ROLLEDBACK");
                        if (impTx != null && !Boolean.valueOf(impTx) && JdbcDebug.isEnabled((DataSource)this, 10)) {
                           JdbcDebug.err((DataSource)this, tx, XAUtils.appendOracleXAResourceInfo(var56, "XA error: ") + " " + XA.errToString(var56.errorCode), var56);
                        }
                     }

                     xae = XA.createException(var56, "Unexpected error during end for XAResource '" + this.poolID + "': " + XAUtils.appendOracleXAResourceInfo(var56, "XA error: ") + ": " + var56.getMessage(), var56.errorCode);
                     var45 = false;
                  } catch (Throwable var57) {
                     if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                        JdbcDebug.err(this, tx, "Unexpected error", var57);
                     }

                     xae = XA.createException(var57, "Unexpected error during end for XAResource '" + this.poolID + "': " + var57.getMessage(), -3);
                     var45 = false;
                     break label1167;
                  } finally {
                     if (var45) {
                        if (txInfo != null) {
                           synchronized(txInfo) {
                              txInfo.setEnlisted(false);
                           }
                        }

                        if ((flags == 67108864 || flags == 536870912) && !this.keepXAConnTillTxComplete || xae != null) {
                           if (xaConn != null && xaConn.user_autocommit_state) {
                              try {
                                 xaConn.setAutoCommit(true);
                              } catch (Exception var46) {
                              }
                           }

                           if (txInfo != null) {
                              if (txInfo.getXAConnection() != null) {
                                 this.removeTxAssoc(tx, xae != null);
                              }
                           } else if (xaConn != null) {
                              xaConn.releaseToPool();
                           }
                        }

                        if (xae != null) {
                           if (tx == null) {
                              throw xae;
                           }

                           String impTx = (String)tx.getProperty("IMP_TX_STATUS_ROLLEDBACK");
                           if (impTx != null && !Boolean.valueOf(impTx)) {
                              throw xae;
                           }
                        }

                     }
                  }

                  if (txInfo != null) {
                     synchronized(txInfo) {
                        txInfo.setEnlisted(false);
                     }
                  }

                  if ((flags == 67108864 || flags == 536870912) && !this.keepXAConnTillTxComplete || xae != null) {
                     if (xaConn != null && xaConn.user_autocommit_state) {
                        try {
                           xaConn.setAutoCommit(true);
                        } catch (Exception var50) {
                        }
                     }

                     if (txInfo != null) {
                        if (txInfo.getXAConnection() != null) {
                           this.removeTxAssoc(tx, xae != null);
                        }
                     } else if (xaConn != null) {
                        xaConn.releaseToPool();
                     }
                  }

                  if (xae != null) {
                     if (tx == null) {
                        throw xae;
                     }

                     impTx = (String)tx.getProperty("IMP_TX_STATUS_ROLLEDBACK");
                     if (impTx != null && !Boolean.valueOf(impTx)) {
                        throw xae;
                     }
                  }
                  break label1165;
               }

               if (txInfo != null) {
                  synchronized(txInfo) {
                     txInfo.setEnlisted(false);
                  }
               }

               if ((flags == 67108864 || flags == 536870912) && !this.keepXAConnTillTxComplete || xae != null) {
                  if (xaConn != null && xaConn.user_autocommit_state) {
                     try {
                        xaConn.setAutoCommit(true);
                     } catch (Exception var48) {
                     }
                  }

                  if (txInfo != null) {
                     if (txInfo.getXAConnection() != null) {
                        this.removeTxAssoc(tx, xae != null);
                     }
                  } else if (xaConn != null) {
                     xaConn.releaseToPool();
                  }
               }

               if (xae != null) {
                  if (tx == null) {
                     throw xae;
                  }

                  impTx = (String)tx.getProperty("IMP_TX_STATUS_ROLLEDBACK");
                  if (impTx != null && !Boolean.valueOf(impTx)) {
                     throw xae;
                  }
               }
               break label1165;
            }

            if (txInfo != null) {
               synchronized(txInfo) {
                  txInfo.setEnlisted(false);
               }
            }

            if ((flags == 67108864 || flags == 536870912) && !this.keepXAConnTillTxComplete || xae != null) {
               if (xaConn != null && xaConn.user_autocommit_state) {
                  try {
                     xaConn.setAutoCommit(true);
                  } catch (Exception var52) {
                  }
               }

               if (txInfo != null) {
                  if (txInfo.getXAConnection() != null) {
                     this.removeTxAssoc(tx, xae != null);
                  }
               } else if (xaConn != null) {
                  xaConn.releaseToPool();
               }
            }

            if (xae != null) {
               if (tx == null) {
                  throw xae;
               }

               impTx = (String)tx.getProperty("IMP_TX_STATUS_ROLLEDBACK");
               if (impTx != null && !Boolean.valueOf(impTx)) {
                  throw xae;
               }
            }
         }
      } catch (Throwable var59) {
         if (var16 != null) {
            var16.th = var59;
            if (var16.monitorHolder[0] != null) {
               var16.monitorIndex = 0;
               InstrumentationSupport.process(var16);
            }
         }

         throw var59;
      }

      if (var16 != null && var16.monitorHolder[0] != null) {
         var16.monitorIndex = 0;
         InstrumentationSupport.process(var16);
      }

   }

   public int prepare(Xid xid) throws XAException {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var12.argsCapture) {
            var12.args = InstrumentationSupport.toSensitive(2);
         }

         if (var12.monitorHolder[1] != null) {
            var12.monitorIndex = 1;
            InstrumentationSupport.process(var12);
         }

         if (var12.monitorHolder[2] != null) {
            var12.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var12);
            InstrumentationSupport.process(var12);
         }

         var12.resetPostBegin();
      }

      int var10000;
      try {
         int ret = 0;
         Transaction tx = (Transaction)this.getTM().getTransaction(xid);
         TxInfo txInfo = null;
         XAConnection xaConn = null;
         javax.transaction.xa.XAResource xaRes = null;
         XAException xae = null;

         try {
            if (tx != null) {
               txInfo = this.getTxInfo(tx);
               if (txInfo == null) {
                  txInfo = new TxInfo((XAConnection)null);
                  this.setTxInfo(tx, txInfo);
               }

               if (txInfo != null) {
                  synchronized(txInfo) {
                     xaConn = txInfo.getXAConnection();
                  }

                  if (xaConn == null) {
                     xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                     xaConn.setOwner(this);
                     xaConn.setTransaction(tx);
                     synchronized(txInfo) {
                        txInfo.setXAConnection(xaConn);
                     }
                  }
               }
            } else if (xaConn == null) {
               xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
            }

            xaRes = xaConn.getXAResource();
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.enter(this, tx, "XAResource.prepare(" + XA.xidToString(xid) + "), xaRes:" + xaRes);
            }

            try {
               ret = xaRes.prepare(xid);
            } catch (Throwable var31) {
               if (this.profiler.isResourceLastUsageProfilingEnabled()) {
                  xaConn.getConnectionEnv().setCurrentError(var31);
                  xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
               }

               throw var31;
            }

            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.leave(this, tx, "XAResource.prepare returns");
            }
         } catch (SQLException var32) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, tx, "SQL error:" + var32.getErrorCode(), var32);
            }

            xae = XA.createException(var32, "prepare failed for XAResource '" + this.poolID + "': " + var32.getMessage(), -3);
         } catch (XAException var33) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, XAUtils.appendOracleXAResourceInfo(var33, "XA error: ") + " " + XA.errToString(var33.errorCode), var33);
            }

            xae = XA.createException(var33, "Unexpected error during prepare for XAResource '" + this.poolID + "': " + XAUtils.appendOracleXAResourceInfo(var33, "XA error: ") + ": " + var33.getMessage(), var33.errorCode);
         } catch (Throwable var34) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err(this, tx, "Unexpected error", var34);
            }

            xae = XA.createException(var34, "Unexpected error during prepare for XAResource '" + this.poolID + "': " + var34.getMessage(), -3);
         } finally {
            if (xae != null && debugConditionalResourcePrepareException && tx instanceof ServerTransactionImpl) {
               ((ServerTransactionImpl)tx).printDebugMessages();
            }

            if (!this.keepXAConnTillTxComplete || xae != null || ret == 3) {
               if (txInfo != null) {
                  this.removeTxAssoc(tx, xae != null);
               } else if (xaConn != null) {
                  xaConn.releaseToPool();
               }
            }

            if (xae != null) {
               this.setXARetryNeeded(tx);
               throw xae;
            }

         }

         var10000 = ret;
      } catch (Throwable var36) {
         if (var12 != null) {
            var12.th = var36;
            var12.ret = InstrumentationSupport.convertToObject(0);
            if (var12.monitorHolder[0] != null) {
               var12.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var12);
               InstrumentationSupport.process(var12);
            }
         }

         throw var36;
      }

      if (var12 != null) {
         var12.ret = InstrumentationSupport.convertToObject(var10000);
         if (var12.monitorHolder[0] != null) {
            var12.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var12);
            InstrumentationSupport.process(var12);
         }
      }

      return var10000;
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      LocalHolder var13;
      if ((var13 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         if (var13.argsCapture) {
            var13.args = InstrumentationSupport.toSensitive(3);
         }

         if (var13.monitorHolder[1] != null) {
            var13.monitorIndex = 1;
            InstrumentationSupport.process(var13);
         }

         if (var13.monitorHolder[2] != null) {
            var13.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var13);
            InstrumentationSupport.process(var13);
         }

         var13.resetPostBegin();
      }

      try {
         Transaction tx = (Transaction)this.getTM().getTransaction(xid);
         TxInfo txInfo = null;
         XAConnection xaConn = null;
         javax.transaction.xa.XAResource xaRes = null;
         XAException xae = null;

         try {
            if (tx != null) {
               txInfo = this.getTxInfo(tx);
               if (txInfo != null) {
                  synchronized(txInfo) {
                     xaConn = txInfo.getXAConnection();
                  }

                  if (xaConn == null) {
                     xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                     xaConn.setOwner(this);
                     xaConn.setTransaction(tx);
                     synchronized(txInfo) {
                        txInfo.setXAConnection(xaConn);
                     }
                  }
               }
            }

            if (xaConn == null) {
               xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
            }

            xaRes = xaConn.getXAResource();
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.enter(this, tx, "XAResource.commit(" + XA.xidToString(xid) + ", " + onePhase + "), xaRes:" + xaRes);
            }

            try {
               xaRes.commit(xid, onePhase);
            } catch (Throwable var43) {
               if (this.profiler.isResourceLastUsageProfilingEnabled()) {
                  xaConn.getConnectionEnv().setCurrentError(var43);
                  xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
               }

               throw var43;
            }

            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.leave(this, tx, "XAResource.commit returns");
            }
         } catch (SQLException var44) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, tx, "SQL error: " + var44.getErrorCode(), var44);
            }

            xae = XA.createException("commit failed for XAResource '" + this.poolID + "': " + var44.getMessage(), -3);
         } catch (XAException var45) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, XAUtils.appendOracleXAResourceInfo(var45, "XA error: ") + " " + XA.errToString(var45.errorCode), var45);
            }

            xae = XA.createException(var45, "Unexpected error during commit for XAResource '" + this.poolID + "': " + XAUtils.appendOracleXAResourceInfo(var45, "XA error: ") + ": " + var45.getMessage(), var45.errorCode);
         } catch (Throwable var46) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err(this, tx, "Unexpected error", var46);
            }

            xae = XA.createException(var46, "Unexpected error during commit for XAResource '" + this.poolID + "': " + var46.getMessage(), -3);
         } finally {
            if (xaConn != null && xaConn.user_autocommit_state) {
               try {
                  xaConn.setAutoCommit(true);
               } catch (Exception var40) {
               }
            }

            this.commitFinallyBlock(onePhase, tx, txInfo, xaConn, xaRes, xae);
         }
      } catch (Throwable var48) {
         if (var13 != null) {
            var13.th = var48;
            if (var13.monitorHolder[0] != null) {
               var13.monitorIndex = 0;
               InstrumentationSupport.process(var13);
            }
         }

         throw var48;
      }

      if (var13 != null && var13.monitorHolder[0] != null) {
         var13.monitorIndex = 0;
         InstrumentationSupport.process(var13);
      }

   }

   private void commitFinallyBlock(boolean onePhase, Transaction tx, TxInfo txInfo, XAConnection xaConn, javax.transaction.xa.XAResource xaRes, XAException xae) throws XAException {
      if (txInfo != null) {
         this.removeTxAssoc(tx, xae != null);
      } else if (xaConn != null) {
         xaConn.releaseToPool();
      }

      if (xae != null) {
         this.setXARetryNeeded(tx);
         if (!onePhase && this.isXARetryNeeded(tx) && (xae.errorCode == -4 || xae.errorCode == -7)) {
            if (JdbcDebug.isEnabled((DataSource)this, 20)) {
               JdbcDebug.err((DataSource)this, tx, "reset error code " + xae.errorCode + " to weblogic.transaction.XAException.XAER_RMRETRY to retry the commit on " + this.poolID + " with " + xaRes + " at " + System.currentTimeMillis(), xae);
            }

            xae.errorCode = 200;
         }

         this.setMPTxProp(tx, (String)null);
         this.clearXAAffinityContext(tx);
         throw xae;
      }
   }

   public void rollback(Xid xid) throws XAException {
      LocalHolder var12;
      if ((var12 = LocalHolder.getInstance(_WLDF$INST_JPFLD_6, _WLDF$INST_JPFLD_JPMONS_6)) != null) {
         if (var12.argsCapture) {
            var12.args = InstrumentationSupport.toSensitive(2);
         }

         if (var12.monitorHolder[0] != null) {
            var12.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var12);
            InstrumentationSupport.process(var12);
         }

         if (var12.monitorHolder[2] != null) {
            var12.monitorIndex = 2;
            InstrumentationSupport.process(var12);
         }

         var12.resetPostBegin();
      }

      try {
         Transaction tx = (Transaction)this.getTM().getTransaction(xid);
         TxInfo txInfo = null;
         XAConnection xaConn = null;
         javax.transaction.xa.XAResource xaRes = null;
         XAException xae = null;

         try {
            if (tx != null) {
               txInfo = this.getTxInfo(tx);
               if (txInfo != null) {
                  synchronized(txInfo) {
                     xaConn = txInfo.getXAConnection();
                  }

                  if (xaConn == null) {
                     xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                     xaConn.setOwner(this);
                     xaConn.setTransaction(tx);
                     synchronized(txInfo) {
                        txInfo.setXAConnection(xaConn);
                     }
                  }
               }
            }

            if (xaConn == null) {
               xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
            }

            xaRes = xaConn.getXAResource();
            if (tx != null && xaConn != null && txInfo != null && tx.isTxAsyncTimeout() && !txInfo.isAborted() && txInfo.isRollbackCalled() && this.driverHasAbort(xaConn)) {
               try {
                  this.callDriverAbort(xaConn);
                  if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                     JdbcDebug.log(this, tx, "XAResource.rollback called abort in tx:" + XA.xidToString(xid) + ",  xaRes:" + xaRes);
                  }

                  txInfo.setAborted(true);
                  this.makeSureThisConnectionIsNeverUsedAgain(xaConn);
                  xae = XA.createException("Unexpected error during rollback the connection has been abortedfor XAResource '" + this.poolID, -3);
                  throw xae;
               } catch (Exception var45) {
                  if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                     JdbcDebug.err((DataSource)this, tx, "XAResource.rollback called abort that failed in tx:" + XA.xidToString(xid) + ",  xaRes:" + xaRes, var45);
                  }
               }
            }

            try {
               if (JdbcDebug.isEnabled((DataSource)this, 10)) {
                  JdbcDebug.enter(this, tx, "XAResource.rollback(" + XA.xidToString(xid) + "), xaRes:" + xaRes);
               }

               xaConn.getConnectionEnv().setInUse();
               xaRes.rollback(xid);
               xaConn.getConnectionEnv().setNotInUse();
               if (this.isXARetryNeeded(tx)) {
                  if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                     JdbcDebug.err((DataSource)this, tx, "throw weblogic.transaction.XAException.XAER_RMRETRY to retry the rollback (if not one-phase commit) on " + this.poolID + " with " + xaRes + " at " + System.currentTimeMillis(), (Throwable)null);
                  }

                  throw XA.createException("rollback result unknown for XAResource '" + this.poolID + "'", 200);
               }

               if (txInfo != null) {
                  txInfo.setRollbackCalled(true);
                  txInfo.setTxInfoXid((Xid)null);
               }
            } catch (Throwable var44) {
               if (this.profiler.isResourceLastUsageProfilingEnabled()) {
                  xaConn.getConnectionEnv().setCurrentError(var44);
                  xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
               }

               throw var44;
            }

            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.leave(this, tx, "XAResource.rollback returns");
            }
         } catch (SQLException var46) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, tx, "SQL error: " + var46.getErrorCode(), var46);
            }

            xae = XA.createException(var46, "rollback failed for XAResource '" + this.poolID + "': " + var46.getMessage(), -3);
         } catch (XAException var47) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, tx, "XA error: " + XA.errToString(var47.errorCode), var47);
            }

            xae = XA.createException(var47, "Unexpected error during rollback for XAResource '" + this.poolID + "': : " + XAUtils.appendOracleXAResourceInfo(var47, "XA error: ") + var47.getMessage(), var47.errorCode);
         } catch (Throwable var48) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err(this, tx, "Unexpected error", var48);
            }

            xae = XA.createException(var48, "Unexpected error during rollback for XAResource '" + this.poolID + "': " + var48.getMessage(), -3);
         } finally {
            if (txInfo != null) {
               if (xaConn != null && xaConn.user_autocommit_state && !txInfo.isAborted()) {
                  try {
                     xaConn.setAutoCommit(true);
                  } catch (Exception var41) {
                  }
               }

               this.removeTxAssoc(tx, xae != null);
            } else if (xaConn != null) {
               xaConn.releaseToPool();
            }

            if (xae != null) {
               this.setXARetryNeeded(tx);
               this.setMPTxProp(tx, (String)null);
               this.clearXAAffinityContext(tx);
               throw xae;
            }

         }
      } catch (Throwable var50) {
         if (var12 != null) {
            var12.th = var50;
            if (var12.monitorHolder[1] != null) {
               var12.monitorIndex = 1;
               InstrumentationSupport.process(var12);
            }
         }

         throw var50;
      }

      if (var12 != null && var12.monitorHolder[1] != null) {
         var12.monitorIndex = 1;
         InstrumentationSupport.process(var12);
      }

   }

   public Xid[] recover(int flag) throws XAException {
      Xid[] ret = null;
      XAConnection xaConn = null;
      javax.transaction.xa.XAResource xaRes = null;
      XAException xae = null;

      Throwable t;
      try {
         if (!this.callRecoverOnlyOnce && !this.callRecoverStartAndEnd || flag == 16777216) {
            xaConn = this.getXAConnectionFromPool((Transaction)null, (Properties)null, (String)null, (String)null);
            xaRes = xaConn.getXAResource();
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.enter(this, "XAResource.recover(flag:" + XA.flagsToString(flag) + ", xaRes:" + xaRes);
            }

            try {
               if (this.callRecoverStartAndEnd && flag == 16777216) {
                  ret = xaRes.recover(25165824);
               } else {
                  ret = xaRes.recover(flag);
               }
            } catch (Throwable var21) {
               t = var21;
               if (this.profiler.isResourceLastUsageProfilingEnabled()) {
                  xaConn.getConnectionEnv().setCurrentError(var21);
                  xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
               }

               throw var21;
            } finally {
               if (recoverRollbackAtFinally && this.vid == 0 && xaConn != null) {
                  xaConn.getConnection().rollback();
               }

            }

            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.leave(this, "XAResource.recover returns " + (ret == null ? 0 : ret.length) + " xids.");
            }

            return ret;
         }

         t = null;
      } catch (SQLException var23) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, "SQL error: " + var23.getErrorCode(), var23);
         }

         xae = XA.createException(var23, "recover failed for XAResource '" + this.poolID + "': " + var23.getMessage(), -3);
         return ret;
      } catch (XAException var24) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, "XA error: " + XA.errToString(var24.errorCode), var24);
         }

         xae = XA.createException(var24, "Unexpected error during recover for XAResource '" + this.poolID + "': " + XAUtils.appendOracleXAResourceInfo(var24, "XA error: ") + var24.getMessage(), var24.errorCode);
         return ret;
      } catch (Throwable var25) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err(this, "Unexpected error", var25);
         }

         xae = XA.createException(var25, "Unexpected error during recover for XAResource '" + this.poolID + "': " + var25.getMessage(), -3);
         return ret;
      } finally {
         if (xaConn != null) {
            xaConn.releaseToPool();
         }

         if (xae != null) {
            throw xae;
         }

      }

      return t;
   }

   public void forget(Xid xid) throws XAException {
      XAConnection xaConn = null;
      javax.transaction.xa.XAResource xaRes = null;
      XAException xae = null;

      try {
         xaConn = this.getXAConnectionFromPool((Transaction)null, (Properties)null, (String)null, (String)null);
         xaRes = xaConn.getXAResource();
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.enter(this, "XAResource.forget(" + XA.xidToString(xid) + "), xaRes:" + xaRes);
         }

         try {
            xaRes.forget(xid);
         } catch (Throwable var12) {
            if (this.profiler.isResourceLastUsageProfilingEnabled()) {
               xaConn.getConnectionEnv().setCurrentError(var12);
               xaConn.getConnectionEnv().setCurrentErrorTimestamp(new Date());
            }

            throw var12;
         }

         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.leave(this, "XAResource.forget returns");
         }
      } catch (SQLException var13) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, "SQL error: " + var13.getErrorCode(), var13);
         }

         xae = XA.createException(var13, "forget failed for XAResource '" + this.poolID + "': " + var13.getMessage(), -3);
      } catch (XAException var14) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, "XA error: " + XA.errToString(var14.errorCode), var14);
         }

         xae = XA.createException(var14, "Unexpected error during recover for XAResource '" + this.poolID + "': " + XAUtils.appendOracleXAResourceInfo(var14, "XA error: ") + var14.getMessage(), var14.errorCode);
      } catch (Throwable var15) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err(this, "Unexpected error", var15);
         }

         xae = XA.createException(var15, "Unexpected error during forget for XAResource '" + this.poolID + "': " + var15.getMessage(), -3);
      } finally {
         if (xaConn != null) {
            xaConn.releaseToPool();
         }

         if (xae != null) {
            throw xae;
         }

      }

   }

   public boolean isSameRM(javax.transaction.xa.XAResource xares) throws XAException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_7, _WLDF$INST_JPFLD_JPMONS_7)) != null) {
         if (var3.argsCapture) {
            var3.args = InstrumentationSupport.toSensitive(2);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[5] != null) {
            var3.monitorIndex = 5;
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[6] != null) {
            var3.monitorIndex = 6;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      boolean var10000;
      label235: {
         label236: {
            try {
               if (xares != this) {
                  if (xares instanceof DataSource) {
                     DataSource jtads = (DataSource)xares;
                     if (this.xaResourceRegistrationName != null && this.xaResourceRegistrationName.equals(jtads.xaResourceRegistrationName)) {
                        var10000 = true;
                        break label235;
                     }
                  }

                  var10000 = false;
                  break label236;
               }

               var10000 = true;
            } catch (Throwable var5) {
               if (var3 != null) {
                  var3.th = var5;
                  var3.ret = InstrumentationSupport.convertToObject(false);
                  if (var3.monitorHolder[4] != null) {
                     var3.monitorIndex = 4;
                     InstrumentationSupport.createDynamicJoinPoint(var3);
                     InstrumentationSupport.process(var3);
                  }

                  if (var3.monitorHolder[3] != null) {
                     var3.monitorIndex = 3;
                     InstrumentationSupport.createDynamicJoinPoint(var3);
                     InstrumentationSupport.process(var3);
                  }

                  if (var3.monitorHolder[0] != null) {
                     var3.monitorIndex = 0;
                     InstrumentationSupport.createDynamicJoinPoint(var3);
                     InstrumentationSupport.process(var3);
                  }
               }

               throw var5;
            }

            if (var3 != null) {
               var3.ret = InstrumentationSupport.convertToObject(var10000);
               if (var3.monitorHolder[4] != null) {
                  var3.monitorIndex = 4;
                  InstrumentationSupport.createDynamicJoinPoint(var3);
                  InstrumentationSupport.process(var3);
               }

               if (var3.monitorHolder[3] != null) {
                  var3.monitorIndex = 3;
                  InstrumentationSupport.createDynamicJoinPoint(var3);
                  InstrumentationSupport.process(var3);
               }

               if (var3.monitorHolder[0] != null) {
                  var3.monitorIndex = 0;
                  InstrumentationSupport.createDynamicJoinPoint(var3);
                  InstrumentationSupport.process(var3);
               }
            }

            return var10000;
         }

         if (var3 != null) {
            var3.ret = InstrumentationSupport.convertToObject(var10000);
            if (var3.monitorHolder[4] != null) {
               var3.monitorIndex = 4;
               InstrumentationSupport.createDynamicJoinPoint(var3);
               InstrumentationSupport.process(var3);
            }

            if (var3.monitorHolder[3] != null) {
               var3.monitorIndex = 3;
               InstrumentationSupport.createDynamicJoinPoint(var3);
               InstrumentationSupport.process(var3);
            }

            if (var3.monitorHolder[0] != null) {
               var3.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var3);
               InstrumentationSupport.process(var3);
            }
         }

         return var10000;
      }

      if (var3 != null) {
         var3.ret = InstrumentationSupport.convertToObject(var10000);
         if (var3.monitorHolder[4] != null) {
            var3.monitorIndex = 4;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[3] != null) {
            var3.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }
      }

      return var10000;
   }

   public int getTransactionTimeout() throws XAException {
      if (this.xaTransactionTimeout > 0) {
         return this.xaTransactionTimeout;
      } else {
         Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
         TxInfo txInfo = null;
         javax.transaction.xa.XAResource xaRes = null;
         XAConnection xaConn = null;

         int var5;
         try {
            if (tx != null) {
               txInfo = this.getTxInfo(tx);
               if (txInfo == null) {
                  txInfo = new TxInfo((XAConnection)null);
                  this.setTxInfo(tx, txInfo);
               }

               if (txInfo != null) {
                  synchronized(txInfo) {
                     xaConn = txInfo.getXAConnection();
                  }

                  if (xaConn == null) {
                     xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                     xaConn.setOwner(this);
                     xaConn.setTransaction(tx);
                     synchronized(txInfo) {
                        txInfo.setXAConnection(xaConn);
                     }
                  }
               }
            } else if (xaConn == null) {
               xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
            }

            xaRes = xaConn.getXAResource();
            var5 = xaRes.getTransactionTimeout();
         } catch (Throwable var15) {
            throw XA.createException(var15, "Unexpected error during getTransactionTimeout for XAResource '" + this.poolID + "': " + var15.getMessage(), -3);
         } finally {
            if (txInfo == null && xaConn != null) {
               xaConn.releaseToPool();
            }

         }

         return var5;
      }
   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      TxInfo txInfo = null;
      javax.transaction.xa.XAResource xaRes = null;
      XAConnection xaConn = null;

      boolean var6;
      try {
         if (tx != null) {
            txInfo = this.getTxInfo(tx);
            if (txInfo == null) {
               txInfo = new TxInfo((XAConnection)null);
               this.setTxInfo(tx, txInfo);
            }

            if (txInfo != null) {
               synchronized(txInfo) {
                  xaConn = txInfo.getXAConnection();
               }

               if (xaConn == null) {
                  xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
                  xaConn.setOwner(this);
                  xaConn.setTransaction(tx);
                  synchronized(txInfo) {
                     txInfo.setXAConnection(xaConn);
                  }
               }
            }
         } else if (xaConn == null) {
            xaConn = this.getXAConnectionFromPool(tx, (Properties)null, (String)null, (String)null);
         }

         xaRes = xaConn.getXAResource();
         xaRes.setTransactionTimeout(this.xaTransactionTimeout > 0 ? this.xaTransactionTimeout : seconds);
         var6 = true;
      } catch (Throwable var16) {
         throw XA.createException(var16, "Unexpected error during setTransactionTimeout for XAResource '" + this.poolID + "': " + var16.getMessage(), -3);
      } finally {
         if (txInfo == null && xaConn != null) {
            xaConn.releaseToPool();
         }

      }

      return var6;
   }

   public XAConnection refreshXAConnAndEnlist(XAConnection inXAConn, JTAConnection conn, boolean needsTxCtx, Properties labels, String username, String password) throws SQLException {
      Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      XAConnection rtnXAConn = null;
      SQLException se = null;
      if (JdbcDebug.isEnabled((DataSource)this, 20)) {
         JdbcDebug.enter(this, tx, "DataSource.refreshXAConnAndEnlist(" + inXAConn + ", " + conn + ", " + needsTxCtx + ")");
      }

      TxInfo txInfo = null;

      try {
         if (tx != null && needsTxCtx) {
            XAConnection txInfoXAConn = null;
            txInfo = this.getTxInfo(tx);
            if (txInfo != null) {
               txInfoXAConn = txInfo.getXAConnection();
            }

            if (txInfo != null && txInfoXAConn != null) {
               rtnXAConn = txInfo.getXAConnection();
               if (rtnXAConn != inXAConn) {
                  rtnXAConn.addConnection(conn);
               }
            } else {
               if (inXAConn != null && inXAConn.getOwner() == conn) {
                  rtnXAConn = inXAConn;
                  inXAConn.setOwner(this);
                  inXAConn.setTransaction(tx);
                  inXAConn.addConnection(conn);
               } else {
                  rtnXAConn = this.getXAConnectionFromPool(tx, labels, username, password);
                  rtnXAConn.setOwner(this);
                  rtnXAConn.setTransaction(tx);
                  rtnXAConn.addConnection(conn);
               }

               if (this.keepConnAfterGlobalTx) {
                  rtnXAConn.setOriginalOwner(conn);
               }

               if (txInfo == null) {
                  txInfo = new TxInfo(rtnXAConn);
                  this.setTxInfo(tx, txInfo);
               }

               txInfo.setXAConnection(rtnXAConn);
            }

            if (JdbcDebug.isEnabled((DataSource)this, 20)) {
               JdbcDebug.log(this, tx, "XA conn assoc with tx:" + rtnXAConn);
            }
         } else if (this.canReuseXAConn(conn, inXAConn, needsTxCtx)) {
            rtnXAConn = inXAConn;
         } else {
            rtnXAConn = this.getXAConnectionFromPool((Transaction)null, labels, username, password);
            rtnXAConn.setOwner(conn);
            rtnXAConn.setTransaction((Transaction)null);
         }
      } catch (SQLException var17) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, tx, "Error while obtaining XA conn", var17);
         }

         if (var17 instanceof SQLFeatureNotSupportedException) {
            se = var17;
         } else {
            se = new SQLException("Cannot obtain XAConnection", var17);
         }
      } catch (Exception var18) {
         String header = "Unexpected exception while obtaining XAConnection ";
         if (var18 instanceof XAException) {
            header = header + XAUtils.appendOracleXAResourceInfo((XAException)var18, header);
         }

         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, tx, header, var18);
         }

         se = new SQLException(header + StackTraceUtils.throwable2StackTrace(var18));
      }

      if (se != null) {
         if (rtnXAConn != null && rtnXAConn != inXAConn && rtnXAConn.getOwner() == conn) {
            rtnXAConn.releaseToPool();
         }

         throw se;
      } else {
         try {
            if (tx != null && needsTxCtx && !txInfo.isEnlisted()) {
               this.enlist(tx);
            }

            if (JdbcDebug.isEnabled((DataSource)this, 20)) {
               JdbcDebug.leave(this, tx, "DataSource.refreshXAConnAndEnlist(XA conn:" + inXAConn + ", conn:" + conn + ", needsTxCtx:" + needsTxCtx + ") returns " + rtnXAConn);
            }
         } catch (Exception var16) {
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.err((DataSource)this, tx, "Error while enlisting", var16);
            }

            boolean txTimedOutException = this.isExceptionTimeoutException(var16);
            if (JdbcDebug.isEnabled((DataSource)this, 10)) {
               JdbcDebug.leave(this, tx, "DataSource.refreshXAConnAndEnlist txTimedOutException :" + txTimedOutException);
            }

            se = new SQLException("Unexpected exception while enlisting XAConnection " + StackTraceUtils.throwable2StackTrace(var16));
            txInfo = this.getTxInfo(tx);
            if (txInfo != null) {
               Xid txInfoXid = txInfo.getTxInfoXid();
               if (!this.keepXAConnTillTxComplete || !txTimedOutException || txInfoXid == null) {
                  try {
                     if (txInfoXid != null && rtnXAConn.getXAResource() != null && !txInfo.isEnded()) {
                        javax.transaction.xa.XAResource xaRes = rtnXAConn.getXAResource();
                        if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                           JdbcDebug.log(this, tx, "Calling xa.end before returning the connection to the pool xid: " + txInfoXid);
                           JdbcDebug.log(this, tx, "XAResource.end(Xid:" + XA.xidToString(txInfoXid) + ", flags:TMFAIL), xaRes:" + xaRes);
                        }

                        xaRes.end(txInfoXid, 536870912);
                        txInfo.setEnded(true);
                     }
                  } catch (Exception var15) {
                  }

                  if (tx != null) {
                     if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                        JdbcDebug.log(this, tx, "Calling refreshXAConnAndEnlist.setTxInfo to null " + tx);
                     }

                     this.removeTxAssoc(tx, true);
                  }
               }
            }

            throw se;
         }

         if (tx != null && rtnXAConn != inXAConn) {
            this.setTxIsolationFromTxProp(tx, rtnXAConn);
         }

         return rtnXAConn;
      }
   }

   private boolean isExceptionTimeoutException(Exception ex) {
      Throwable cause = ex.getCause();
      return cause instanceof TimedOutException;
   }

   private boolean canReuseXAConn(JTAConnection conn, XAConnection xaConn, boolean needsTxCtx) {
      return xaConn != null;
   }

   private void setTxIsolationFromTxProp(Transaction tx, XAConnection xaConn) throws SQLException {
      TxInfo txInfo = null;
      if (tx != null) {
         txInfo = this.getTxInfo(tx);
      }

      Integer i = null;
      if (tx != null) {
         i = (Integer)tx.getProperty("ISOLATION LEVEL");
      }

      if (i != null) {
         String disableSet = null;
         if (txInfo != null) {
            synchronized(txInfo) {
               if (txInfo.getProperties() != null) {
                  disableSet = txInfo.getProperties().getProperty(this.disableSetTxIsoPropName);
               }
            }
         }

         if (disableSet != null && Boolean.valueOf(disableSet)) {
            return;
         }

         xaConn.setTransactionIsolation(i);
      }

   }

   private void enlist(Transaction tx) throws SQLException {
      SQLException se = null;

      try {
         tx.enlistResource(this);
      } catch (SystemException var6) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, tx, "SystemEx error", var6);
         }

         se = new SQLException("XA error: " + XA.errToString(var6.errorCode) + " " + var6.getMessage(), "", var6.errorCode);
      } catch (RollbackException var7) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, tx, "Rollback", var7);
         }

         Throwable cause = null;
         Throwable rollbackReason = tx.getRollbackReason();
         if (rollbackReason instanceof TimedOutException) {
            cause = new TimedOutException(var7.getMessage());
         }

         se = new SQLException("Transaction rolled back: " + var7.getMessage());
         if (cause != null) {
            se.initCause(cause);
         }
      } catch (Exception var8) {
         if (JdbcDebug.isEnabled((DataSource)this, 10)) {
            JdbcDebug.err((DataSource)this, tx, "Unexpected ex", var8);
         }

         se = new SQLException("Unexpected exception: " + var8.getMessage());
      }

      if (se != null) {
         throw se;
      }
   }

   private void setMPTxProp(Transaction tx, String poolName) {
      if (tx != null) {
         tx.setProperty(this.txMPPropName, poolName);
      }

   }

   private String getMPTxProp(Transaction tx) {
      String val = null;
      if (tx != null) {
         val = (String)tx.getProperty(this.txMPPropName);
      }

      return val;
   }

   private Object getXAAffinityContext(Transaction tx) {
      if (tx == null) {
         return null;
      } else {
         if (this.pool instanceof HAConnectionPool) {
            AffinityCallback xacb = ((HAConnectionPool)this.pool).getXAAffinityCallback();
            if (xacb != null && xacb instanceof XAAffinityCallback) {
               return ((XAAffinityCallback)xacb).getConnectionAffinityContext(tx);
            }
         }

         return null;
      }
   }

   private void clearXAAffinityContext(Transaction tx) {
      if (tx != null) {
         if (this.pool instanceof HAConnectionPool) {
            AffinityCallback xacb = ((HAConnectionPool)this.pool).getXAAffinityCallback();
            if (xacb != null && xacb instanceof XAAffinityCallback) {
               ((XAAffinityCallback)xacb).setConnectionAffinityContext(tx, (Object)null);
            }
         }

      }
   }

   private XAConnection getXAConnectionFromPool(Transaction tx, Properties labels, String username, String password) throws SQLException {
      ConnectionEnv ce = null;
      String poolToReserve = this.poolID;
      int waitSecs = -2;
      if (JdbcDebug.isEnabled((DataSource)this, 20)) {
         JdbcDebug.enter(this, tx, "DataSource.getXAConnFromPool(" + tx + ")");
      }

      RACInstance affinityInstance;
      if (tx != null) {
         try {
            if (tx.getStatus() == 0) {
               waitSecs = (int)(tx.getTimeToLiveMillis() / 1000L);
               if (waitSecs <= 0) {
                  waitSecs = -1;
               }
            }
         } catch (Exception var13) {
         }

         if (this.pool instanceof HAConnectionPool) {
            HAConnectionPool hacp = (HAConnectionPool)this.pool;
            affinityInstance = null;
            Object affinityContext = this.getXAAffinityContext(tx);
            if (affinityContext != null) {
               try {
                  affinityInstance = RACAffinityContextHelperFactory.getInstance().createRACInstance(affinityContext);
                  ce = hacp.getConnectionToInstance(affinityInstance, waitSecs, labels);
               } catch (ResourceException | RACAffinityContextException var14) {
                  if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                     JdbcDebug.err((DataSource)this, "error attempting to get or create connection to honor XA affinity", var14);
                  }

                  throw new SQLException("Unable to get or create connection to honor strict XA affinity" + affinityInstance, var14);
               }
            }
         } else if (this.isMultiPoolDataSource() && this.xaRetryDurationSeconds != 0) {
            poolToReserve = this.getMPTxProp(tx);
            if (poolToReserve == null) {
               poolToReserve = this.poolID;
            }
         }
      }

      try {
         if (ce == null) {
            try {
               ce = ConnectionPoolManager.reserve(poolToReserve, this.applicationName, this.moduleName, this.compName, waitSecs, labels, username, password);
            } catch (Exception var15) {
               if (this.xaRetryDurationSeconds == 0 || !this.isMultiPoolDataSource() || this.getMPTxProp(tx) == null) {
                  throw var15;
               }

               if (!(var15 instanceof ResourceException) && !(var15 instanceof SQLException)) {
                  throw var15;
               }

               ce = ConnectionPoolManager.reserve(this.poolID, this.applicationName, this.moduleName, this.compName, waitSecs, labels);
            }
         }

         XAConnection xaConn = (XAConnection)((ConnectionEnv)ce).conn.jconn;
         xaConn.setDataSource(this);
         ((ConnectionEnv)ce).setUsed(true);
         if (JdbcDebug.isEnabled((DataSource)this, 5)) {
            int numXAConn = JdbcDebug.incNumXAConn(this);
            if (JdbcDebug.isEnabled((DataSource)this, 20)) {
               JdbcDebug.log(this, tx, "jta.DataSource reserve connection from " + this.poolID + " and get connection of " + ce + " for " + tx);
               JdbcDebug.leave(this, tx, "DataSource.getXAConnFromPool returns" + xaConn + ", Num XAConn:" + numXAConn);
            }
         }

         return xaConn;
      } catch (Exception var16) {
         if (ce != null) {
            ((ConnectionEnv)ce).checkIfEnabled();
         }

         affinityInstance = null;
         String str;
         if (waitSecs != -2) {
            str = "Creation of XAConnection for pool " + this.poolID + " failed after waitSecs:" + waitSecs;
         } else {
            str = "Creation of XAConnection for pool " + this.poolID + " failed after default wait time configured for pool";
         }

         if (JdbcDebug.isEnabled((DataSource)this, 20)) {
            JdbcDebug.err((DataSource)this, tx, str, var16);
         }

         if (var16 instanceof FeatureNotSupportedException) {
            throw new SQLFeatureNotSupportedException(var16.toString());
         } else {
            throw new SQLException(StackTraceUtils.throwable2StackTrace(var16));
         }
      }
   }

   private void removeTxAssoc(Transaction tx, boolean failure) {
      if (JdbcDebug.isEnabled((DataSource)this, 20)) {
         JdbcDebug.enter(this, tx, "DataSource.removeTxAssoc(" + tx + ")");
      }

      if (tx != null) {
         TxInfo txInfo = this.getTxInfo(tx);
         XAConnection xaConnToRelease = null;
         if (txInfo != null) {
            synchronized(txInfo) {
               XAConnection xaConn = txInfo.getXAConnection();
               if (xaConn != null && (xaConn.getOwner() == this || failure)) {
                  xaConnToRelease = xaConn;
                  if (failure) {
                     this.callResetLastSuccessfulConnectionUse(xaConn);
                  }

                  txInfo.setXAConnection((XAConnection)null);
               } else if (JdbcDebug.isEnabled((DataSource)this, 20)) {
                  JdbcDebug.log(this, tx, "DataSource.removeTxAssoc not returning txInfo.xaConn: xaConn=" + xaConn + ", failure=" + failure + ", owner=" + (xaConn == null ? null : xaConn.getOwner()));
               }
            }
         }

         if (xaConnToRelease != null) {
            if (!this.getKeepConnAfterGlobalTx()) {
               xaConnToRelease.releaseToPool();
            } else {
               xaConnToRelease.restoreOriginalOwner();
            }
         }
      }

      if (JdbcDebug.isEnabled((DataSource)this, 20)) {
         JdbcDebug.leave(this, tx, "DataSource.removeTxAssoc returns");
      }

   }

   private void setXARetryNeeded(Transaction tx) {
      if (this.xaRetryDurationSeconds != 0) {
         if (tx != null) {
            TxInfo txInfo = this.getTxInfo(tx);
            if (txInfo == null) {
               txInfo = new TxInfo((XAConnection)null);
               this.setTxInfo(tx, txInfo);
            }

            if (txInfo.getXARetryTimeout() == 0L) {
               txInfo.setXARetryTimeout(System.currentTimeMillis() + this.xaRetryDurationMillis);
            }
         }

      }
   }

   private boolean isXARetryNeeded(Transaction tx) {
      if (this.xaRetryDurationSeconds == 0) {
         return false;
      } else {
         boolean ret = false;
         if (tx != null) {
            TxInfo txInfo = this.getTxInfo(tx);
            if (txInfo != null && txInfo.getXARetryTimeout() > System.currentTimeMillis()) {
               ret = true;
            }
         }

         return ret;
      }
   }

   private TransactionManager getTM() {
      return (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   public TxInfo getTxInfo(Transaction tx) {
      return tx != null ? (TxInfo)tx.getLocalProperty(this.txInfoPropName) : null;
   }

   public void setTxInfo(Transaction tx, TxInfo info) {
      if (tx != null) {
         tx.setLocalProperty(this.txInfoPropName, info);
      }

   }

   private int processTxIsolationProp(Transaction tx, int flags) throws SQLException {
      Integer i = (Integer)tx.getProperty("ISOLATION LEVEL");
      if (i != null) {
         int newIsoLevel = i;
         XAConnection xaConn = this.getTxInfo(tx).getXAConnection();
         if (xaConn.getTransactionIsolation() == newIsoLevel) {
            this.getTxInfo(tx).getProperties().setProperty(this.disableSetTxIsoPropName, "true");
            return flags;
         }

         if (!this.supportSetTxIsolationUponEnlistment) {
            throw new SQLException("Due to vendor limitations, setting transaction isolation upon enlistment for \"" + this.getVendorName() + "\" JDBC XA driver is not supported.");
         }

         if (!this.getKeepXAConnTillTxComplete() && !this.xaResumeAsJoin) {
            throw new SQLException("Cannot set transaction isolation level for the XA connection if the XA connection pool does not have \"KeepXAConnTillTxComplete\" attribute set to true.  Note that, however, setting this attribute means that each XA connection is associated with the global transaction until it completes and may limit scalability.");
         }

         if (this.vid == 0) {
            if (flags == 0) {
               flags |= this.mapIsoLevelToVendorFlags(newIsoLevel);
            }

            this.getTxInfo(tx).getProperties().setProperty(this.disableSetTxIsoPropName, "true");
            return flags;
         }
      }

      return flags;
   }

   public int mapIsoLevelToVendorFlags(int newIsoLevel) throws SQLException {
      if (newIsoLevel != 2 && newIsoLevel != 4) {
         if (newIsoLevel == 8) {
            return this.ORATMSERIALIZABLE;
         } else {
            String level;
            if (newIsoLevel == 0) {
               level = "Connection.TRANSACTION_NONE";
            } else if (newIsoLevel == 1) {
               level = "Connection.TRANSACTION_READ_UNCOMMITTED";
            } else {
               level = "" + newIsoLevel;
            }

            throw new SQLException("Cannot map desired Isolation Level \"" + level + "\" to proprietary flag for vendor " + this.getVendorName());
         }
      } else {
         return 0;
      }
   }

   public void setXARegistrationProperties(Hashtable properties) {
      this.xaRegistrationProperties = properties;
   }

   public Hashtable getXARegistrationProperties() {
      return this.xaRegistrationProperties;
   }

   private void setXAConnEnvFactoryDS() throws ResourceException {
      ConnectionPoolManager.setDataSource(this.poolID, this.applicationName, this.moduleName, this.compName, this);
   }

   public void setDebugLevel(int level) {
      this.jdbcxaDebugLevel = level;
   }

   public int getDebugLevel() {
      return this.jdbcxaDebugLevel;
   }

   public Object unwrap(Class iface) throws SQLException {
      if (!iface.isInterface()) {
         throw new SQLException("not an interface");
      } else if (iface.isInstance(this)) {
         return iface.cast(this);
      } else {
         throw new SQLException(this + " is not an instance of " + iface);
      }
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }

   public Connection getConnectionToInstance(String instanceName) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Connection getConnectionToInstance(Connection sameAsThisOne) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Connection createConnection(Properties additionalproperties) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public Connection createConnectionToInstance(String instance, Properties additionalproperties) throws SQLException {
      throw new UnsupportedOperationException();
   }

   static {
      _WLDF$INST_FLD_JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium");
      _WLDF$INST_FLD_JDBC_After_Connection_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Connection_Internal");
      _WLDF$INST_FLD_JDBC_Before_Connection_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Connection_Internal");
      _WLDF$INST_FLD_JDBC_After_Commit_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Commit_Internal");
      _WLDF$INST_FLD_JDBC_Before_Rollback_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Rollback_Internal");
      _WLDF$INST_FLD_JDBC_Before_Start_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Start_Internal");
      _WLDF$INST_FLD_JDBC_After_Rollback_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Rollback_Internal");
      _WLDF$INST_FLD_JDBC_After_Start_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Start_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Is_SameRM_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Is_SameRM_Before_High");
      _WLDF$INST_FLD_JDBC_Before_Commit_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Commit_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Start_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Start_Before_High");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Prepare_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Prepare_Before_High");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Commit_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Commit_Before_High");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Rollback_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Rollback_Before_Low");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_End_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_End_Before_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "getConnection", "()Ljava/sql/Connection;", 594, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium", "JDBC_Before_Connection_Internal", "JDBC_After_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium, _WLDF$INST_FLD_JDBC_After_Connection_Internal, _WLDF$INST_FLD_JDBC_Before_Connection_Internal};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "getConnection", "(Ljava/lang/String;Ljava/lang/String;)Ljava/sql/Connection;", 600, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium", "JDBC_Before_Connection_Internal", "JDBC_After_Connection_Internal"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCPoolStringRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Datasource_Get_Connection_Around_Medium, _WLDF$INST_FLD_JDBC_After_Connection_Internal, _WLDF$INST_FLD_JDBC_Before_Connection_Internal};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "start", "(Ljavax/transaction/xa/Xid;I)V", 815, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Before_Start_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Start_Before_High, _WLDF$INST_FLD_JDBC_After_Start_Internal};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "end", "(Ljavax/transaction/xa/Xid;I)V", 922, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_End_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "prepare", "(Ljavax/transaction/xa/Xid;)I", 1044, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Prepare_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "commit", "(Ljavax/transaction/xa/Xid;Z)V", 1140, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Commit_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      _WLDF$INST_JPFLD_6 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "rollback", "(Ljavax/transaction/xa/Xid;)V", 1256, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_6 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Before_Rollback_Internal, _WLDF$INST_FLD_JDBC_After_Rollback_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Rollback_Before_Low};
      _WLDF$INST_JPFLD_7 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "DataSource.java", "weblogic.jdbc.jta.DataSource", "isSameRM", "(Ljavax/transaction/xa/XAResource;)Z", 1515, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_7 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Before_Rollback_Internal, _WLDF$INST_FLD_JDBC_Before_Start_Internal, _WLDF$INST_FLD_JDBC_After_Rollback_Internal, _WLDF$INST_FLD_JDBC_After_Start_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Is_SameRM_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      recoverRollbackAtFinally = Boolean.valueOf(System.getProperty("weblogic.jdbc.jta.recoverRollbackAtFinally", "false"));
      debugConditionalResourceStartException = Boolean.valueOf(System.getProperty("weblogic.jdbc.is.debug.conditional.resourceStartException", "false"));
      debugConditionalResourcePrepareException = Boolean.valueOf(System.getProperty("weblogic.jdbc.is.debug.conditional.resourcePrepareException", "false"));
   }
}
