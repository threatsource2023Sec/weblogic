package weblogic.jdbc.common.internal;

import java.io.PrintWriter;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import oracle.ucp.jdbc.HarvestableConnection;
import oracle.ucp.jdbc.LabelableConnection;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ObjectLifeCycle;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.extensions.WLDataSource;
import weblogic.jdbc.rmi.internal.RmiDriverSettings;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JTSEmulateXAResourceImpl;
import weblogic.jdbc.wrapper.JTSLoggableResourceImpl;
import weblogic.jdbc.wrapper.PoolConnection;
import weblogic.jndi.CrossPartitionAware;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.XAResource;
import weblogic.utils.Debug;

public final class WLDataSourceImpl extends ParentLogger implements DataSource, WLDataSource, DataSourceMetaData, ObjectLifeCycle, CrossPartitionAware {
   private static final AuthenticatedSubject KERNELID = getKernelID();
   private PrincipalAuthenticator pa;
   private RmiDriverSettings rmiSettings;
   protected String driverClass;
   protected String driverUrl;
   protected Properties driverProps;
   protected String poolName;
   protected String appName;
   protected String moduleName;
   protected String compName;
   protected String partitionName;
   protected String jtaRegistrationName;
   protected boolean verbose;
   protected boolean useDriver;
   protected boolean useDataSource;
   protected Driver driverInstance;
   private boolean txDataSource;
   private String[] jndiNames;
   private String scope;
   private Context jdbcCtx;
   private boolean isLoggingResource;
   private boolean useDatabaseCredentials;
   private ClassLoader classLoader;
   private boolean useBI_UserName;
   private Context ctxGlobal;
   private boolean crossPartitionEnabled;
   volatile boolean active;
   private static final boolean QUALIFY_RM_NAME = Boolean.parseBoolean(System.getProperty("weblogic.jdbc.qualifyRMName", "true"));
   private static final boolean remoteEnabled = Boolean.valueOf(System.getProperty("weblogic.jdbc.remoteEnabled", "true"));

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public WLDataSourceImpl(String poolName, String appName, String moduleName, RmiDriverSettings rmiSettings, String[] jndiNames, Context jdbcCtx, String scope, boolean useDatabaseCredentials, ClassLoader classLoader, boolean useBI_UserName) throws SQLException {
      this(poolName, appName, moduleName, (String)null, rmiSettings, jndiNames, jdbcCtx, scope, useDatabaseCredentials, classLoader, useBI_UserName, (String)null, false);
   }

   public WLDataSourceImpl(String poolName, String appName, String moduleName, String compName, RmiDriverSettings rmiSettings, String[] jndiNames, Context jdbcCtx, String scope, boolean useDatabaseCredentials, ClassLoader classLoader, boolean useBI_UserName, String partitionName, boolean crossPartitionEnabled) throws SQLException {
      this.pa = null;
      this.verbose = false;
      this.driverInstance = null;
      this.txDataSource = false;
      this.useBI_UserName = false;
      this.ctxGlobal = null;
      this.active = false;
      this.jndiNames = jndiNames;
      this.poolName = poolName;
      this.appName = appName;
      this.moduleName = moduleName;
      this.compName = compName;
      this.rmiSettings = rmiSettings;
      this.scope = scope;
      this.useDriver = false;
      this.jdbcCtx = jdbcCtx;
      this.classLoader = classLoader;
      this.useDatabaseCredentials = useDatabaseCredentials;
      this.useBI_UserName = useBI_UserName;
      this.partitionName = partitionName;
      this.crossPartitionEnabled = crossPartitionEnabled;
   }

   public WLDataSourceImpl(String poolName, String appName, String moduleName, String driverClass, String driverUrl, Properties driverProps, boolean instantiateDriver, RmiDriverSettings rmiSettings, String[] jndiNames, Context jdbcCtx, String scope, boolean useDatabaseCredentials, ClassLoader classLoader, boolean useBI_UserName, boolean crossPartitionEnabled) throws SQLException {
      this.pa = null;
      this.verbose = false;
      this.driverInstance = null;
      this.txDataSource = false;
      this.useBI_UserName = false;
      this.ctxGlobal = null;
      this.active = false;
      if (this.verbose) {
         String msg = "time=" + System.currentTimeMillis() + " : init \n\turl=" + driverUrl + "\n\tclass=" + driverClass;
         JDBCUtil.JDBCInternal.debug(msg);
      }

      this.poolName = poolName;
      this.appName = appName;
      this.moduleName = moduleName;
      this.driverClass = driverClass;
      this.driverUrl = driverUrl;
      this.driverProps = driverProps;
      this.rmiSettings = rmiSettings;
      this.jndiNames = jndiNames;
      this.scope = scope;
      this.jdbcCtx = jdbcCtx;
      this.classLoader = classLoader;
      this.useDatabaseCredentials = useDatabaseCredentials;
      this.useBI_UserName = useBI_UserName;
      this.partitionName = this.getPartitionNameFromDriverProps();
      this.crossPartitionEnabled = crossPartitionEnabled;

      try {
         if (instantiateDriver) {
            this.driverInstance = (Driver)DataSourceUtil.loadDriver(driverClass, classLoader);
         } else {
            DataSourceUtil.loadDriverClass(driverClass, classLoader);
         }
      } catch (ClassNotFoundException var17) {
         throw new SQLException(var17.toString());
      } catch (InstantiationException var18) {
         throw new SQLException(var18.toString());
      } catch (IllegalAccessException var19) {
         throw new SQLException(var19.toString());
      }

      this.initTxDataSource();
      this.useDataSource = true;
   }

   public WLDataSourceImpl(String poolName, String appName, String moduleName, String driverClass, String driverUrl, Properties driverProps, boolean instantiateDriver, RmiDriverSettings rmiSettings, String[] jndiNames, Context jdbcCtx, String scope, boolean isLoggingResource, boolean useDatabaseCredentials, ClassLoader classLoader, boolean useBI_UserName, boolean crossPartitionEnabled) throws SQLException {
      this.pa = null;
      this.verbose = false;
      this.driverInstance = null;
      this.txDataSource = false;
      this.useBI_UserName = false;
      this.ctxGlobal = null;
      this.active = false;
      if (this.verbose) {
         String msg = "time=" + System.currentTimeMillis() + " : init \n\turl=" + driverUrl + "\n\tclass=" + driverClass;
         JDBCUtil.JDBCInternal.debug(msg);
      }

      this.poolName = poolName;
      this.appName = appName;
      this.moduleName = moduleName;
      this.driverClass = driverClass;
      this.driverUrl = driverUrl;
      this.driverProps = driverProps;
      this.rmiSettings = rmiSettings;
      this.jndiNames = jndiNames;
      this.scope = scope;
      this.isLoggingResource = isLoggingResource;
      this.jdbcCtx = jdbcCtx;
      this.classLoader = classLoader;
      this.useDatabaseCredentials = useDatabaseCredentials;
      this.useBI_UserName = useBI_UserName;
      this.partitionName = this.getPartitionNameFromDriverProps();
      this.crossPartitionEnabled = crossPartitionEnabled;

      try {
         if (instantiateDriver) {
            this.driverInstance = (Driver)DataSourceUtil.loadDriver(driverClass, classLoader);
         } else {
            DataSourceUtil.loadDriverClass(driverClass, classLoader);
         }
      } catch (ClassNotFoundException var18) {
         throw new SQLException(var18.toString());
      } catch (InstantiationException var19) {
         throw new SQLException(var19.toString());
      } catch (IllegalAccessException var20) {
         throw new SQLException(var20.toString());
      }

      this.initTxDataSource();
      this.useDriver = true;
   }

   private void initTxDataSource() throws SQLException {
      String appId;
      if (this.driverInstance instanceof XAResource || this.driverInstance instanceof weblogic.jdbc.jts.Driver && !this.isLoggingResource) {
         this.poolName = (String)this.driverProps.get("connectionPoolID");
         if (this.poolName != null) {
            if (this.driverInstance instanceof weblogic.jdbc.jta.DataSource) {
               ((weblogic.jdbc.jta.DataSource)this.driverInstance).setProperties(this.driverProps);
            }

            appId = (String)this.driverProps.get("applicationName");
            String moduleId = null;
            String compId = null;
            if (appId != null) {
               moduleId = (String)this.driverProps.get("moduleName");
               compId = (String)this.driverProps.get("compName");
            }

            this.jtaRegistrationName = JDBCUtil.getDecoratedName(this.poolName, appId, moduleId, compId);
            if (QUALIFY_RM_NAME) {
               this.jtaRegistrationName = this.jtaRegistrationName + "_" + JDBCHelper.getHelper().getDomainName();
            }

            if (this.driverInstance instanceof weblogic.jdbc.jta.DataSource) {
               ((weblogic.jdbc.jta.DataSource)this.driverInstance).setXAResourceRegistrationName(this.jtaRegistrationName);
            }

            try {
               ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(this.jtaRegistrationName);
            } catch (SystemException var12) {
            }

            try {
               Hashtable registrationProperties = new Hashtable();
               registrationProperties.put("weblogic.transaction.registration.type", "dynamic");
               registrationProperties.put("weblogic.transaction.resource.type", "datasource");
               String callSetTransactionTimeout = (String)this.driverProps.get("callXASetTransactionTimeout");
               if (callSetTransactionTimeout != null && "true".equals(callSetTransactionTimeout)) {
                  registrationProperties.put("weblogic.transaction.registration.settransactiontimeout", "true");
               }

               String s;
               try {
                  s = null;
                  s = (String)this.driverProps.get("callXAEndAtTxTimeout");
                  if (s != null) {
                     if (s.equals("false")) {
                        registrationProperties.put("weblogic.transaction.registration.asynctimeoutdelist", "false");
                     } else if (s.equals("true")) {
                        registrationProperties.put("weblogic.transaction.registration.asynctimeoutdelist", "true");
                     }
                  }
               } catch (Throwable var10) {
               }

               try {
                  s = null;
                  s = (String)this.driverProps.get("callXAEndWithTMSUCCESSInsteadOfTMSUSPEND");
                  if (s != null) {
                     if (s.equals("false")) {
                        registrationProperties.put("weblogic.transaction.registration.setdelistTMSUCCESSInsteadOfTMSUSPEND", "false");
                     } else if (s.equals("true")) {
                        registrationProperties.put("weblogic.transaction.registration.setdelistTMSUCCESSInsteadOfTMSUSPEND", "true");
                     }
                  }
               } catch (Throwable var9) {
               }

               s = (String)this.driverProps.get("xaRetryDurationSeconds");
               String xaRetryInterval;
               if (s != null) {
                  registrationProperties.put("weblogic.transaction.registration.recoverRetryDurationSeconds", Integer.valueOf(s));
                  xaRetryInterval = (String)this.driverProps.get("xaRetryIntervalSeconds");
                  if (xaRetryInterval != null) {
                     registrationProperties.put("weblogic.transaction.registration.recoverRetryIntervalSeconds", Integer.valueOf(xaRetryInterval));
                  }
               }

               xaRetryInterval = (String)this.driverProps.get("FirstResourceCommit");
               if (xaRetryInterval != null && xaRetryInterval.equals("true")) {
                  registrationProperties.put("weblogic.transaction.first.resource.commit", "true");
               }

               if (this.driverInstance instanceof weblogic.jdbc.jta.DataSource) {
                  ((weblogic.jdbc.jta.DataSource)this.driverInstance).setXARegistrationProperties(registrationProperties);
                  registrationProperties.put("weblogic.transaction.resource.manager.name", ((weblogic.jdbc.jta.DataSource)this.driverInstance).getVendorName());
                  String localResourceAssignmentEnabled = (String)this.driverProps.get("weblogic.jdbc.localResourceAssignmentEnabled");
                  if (localResourceAssignmentEnabled != null && localResourceAssignmentEnabled.equalsIgnoreCase("false")) {
                     registrationProperties.put("weblogic.transaction.registration.localassignment", "false");
                  }
               }

               if (this.driverInstance instanceof weblogic.jdbc.jts.Driver) {
                  registrationProperties.put("weblogic.transaction.registration.localassignment", "false");
                  ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerResource(this.jtaRegistrationName, new JTSEmulateXAResourceImpl(this.poolName, this.driverProps), registrationProperties);
               } else {
                  ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerResource(this.jtaRegistrationName, (XAResource)this.driverInstance, registrationProperties);
               }
            } catch (SystemException var11) {
               throw new SQLException("Cannot register XAResource '" + this.poolName + "': " + var11.getMessage());
            }
         }
      }

      appId = (String)this.driverProps.get("jdbcTxDataSource");
      this.txDataSource = appId != null && appId.equals("true");
   }

   private String getPartitionNameFromDriverProps() {
      return this.driverProps != null ? this.driverProps.getProperty("PartitionName") : null;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getCompName() {
      return this.compName;
   }

   public void setPoolName(String name) {
      this.poolName = name;
   }

   public String[] getJNDINames() {
      return this.jndiNames;
   }

   public Properties getDriverProperties() {
      return this.driverProps != null ? this.driverProps : new Properties();
   }

   public RmiDriverSettings getDriverSettings() {
      return this.rmiSettings != null ? this.rmiSettings : new RmiDriverSettings();
   }

   public Connection getConnection(String username, String password) throws SQLException {
      return this.getConnection(username, password, this.getRequestedLabelsFromCallback());
   }

   public Connection getConnection(String username, String password, final Properties labels) throws SQLException {
      if (!this.useDatabaseCredentials && !this.useBI_UserName) {
         try {
            return (Connection)SecurityServiceManager.runAs(KERNELID, this.getSubject(username, password), new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  return WLDataSourceImpl.this.getConnection(labels);
               }
            });
         } catch (Exception var5) {
            JDBCUtil.wrapAndThrowResourceException(var5, (String)null);
            throw new SQLException("unexpected internal code path");
         }
      } else {
         return this.getConnectionInternal(username, password, labels);
      }
   }

   public void setLogWriter(PrintWriter out) throws SQLException {
   }

   public PrintWriter getLogWriter() throws SQLException {
      return null;
   }

   public void setLoginTimeout(int seconds) throws SQLException {
   }

   public int getLoginTimeout() throws SQLException {
      return 0;
   }

   void recoverLoggingResourceTransactions() throws SystemException {
      if (this.isLoggingResource) {
         String name = this.poolName;
         if (name == null) {
            name = (String)this.driverProps.get("connectionPoolID");
         }

         if (JdbcDebug.JTAJDBC.isDebugEnabled()) {
            JdbcDebug.log(this.poolName, "Registering JDBC LLR pool " + name + " with TM");
         }

         if (!remoteEnabled) {
            JDBCLogger.logLLRWarningRemoteJDBCDisabled(this.poolName);
         }

         JTSLoggableResourceImpl lr = new JTSLoggableResourceImpl(name);
         ServerTransactionManager tm = (ServerTransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
         tm.registerLoggingResourceTransactions(lr, false);
         tm.registerCoordinatorService(name, lr);
      }
   }

   private AuthenticatedSubject getSubject(String username, String password) throws SQLException {
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(KERNELID);
      if (username != null) {
         String realmName = "weblogicDEFAULT";
         this.pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(KERNELID, realmName, ServiceType.AUTHENTICATION);
         Debug.assertion(this.pa != null);

         try {
            SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);
            subject = this.pa.authenticate(handler);
         } catch (LoginException var6) {
            throw new SQLException("User: " + username + ", failed to be authenticated.");
         }
      }

      return subject;
   }

   private Connection getPoolConnection(String username, String password, Properties labels) throws SQLException {
      AuthenticatedSubject subject = null;
      if (!this.useDatabaseCredentials && !this.useBI_UserName) {
         if (this.driverProps != null) {
            username = (String)this.driverProps.get("user");
         }

         if (this.driverProps != null) {
            password = (String)this.driverProps.get("password");
         }

         subject = this.getSubject(username, password);
      } else {
         subject = SecurityServiceManager.getCurrentSubject(KERNELID);
      }

      ConnectionEnv cc = null;

      try {
         cc = ConnectionPoolManager.reserve(subject, this.poolName, this.appName, this.moduleName, this.compName, -2, labels, username, password);
      } catch (Exception var8) {
         JDBCUtil.wrapAndThrowResourceException(var8, (String)null);
      }

      Connection conn = this.getPoolConnectionObj(cc);
      return conn;
   }

   private ConnectionLabelingCallback getConnectionLabelingCallback() throws SQLException {
      try {
         JDBCConnectionPool cp = ConnectionPoolManager.getPool(this.poolName, this.appName, this.moduleName, this.compName);
         return cp != null ? cp.getLabelingCallback() : null;
      } catch (ResourceException var3) {
         SQLException sqle = new SQLException("unable to obtain connection pool " + this.poolName);
         sqle.initCause(var3);
         throw sqle;
      }
   }

   private Properties getRequestedLabelsFromCallback() throws SQLException {
      Properties requestedLabels = null;
      ConnectionLabelingCallback clc = this.getConnectionLabelingCallback();
      if (clc != null && clc instanceof oracle.ucp.jdbc.ConnectionLabelingCallback) {
         requestedLabels = ((oracle.ucp.jdbc.ConnectionLabelingCallback)clc).getRequestedLabels();
      }

      return requestedLabels;
   }

   private void checkActive() throws SQLException {
      if (!this.active) {
         throw new SQLException("DataSource " + this.poolName + " is not active");
      }
   }

   public Connection getConnection() throws SQLException {
      return this.getConnection(this.getRequestedLabelsFromCallback());
   }

   public Connection getConnection(Properties labels) throws SQLException {
      return this.getConnectionInternal((String)null, (String)null, labels);
   }

   private Connection getConnectionInternal(String username, String password, Properties labels) throws SQLException {
      this.checkActive();
      if (labels != null && this.getConnectionLabelingCallback() == null && !this.useBI_UserName && !labels.containsKey("_weblogic.jdbc.instanceName") && !labels.containsKey("_weblogic.jdbc.properties")) {
         throw new SQLException("No connection labeling callback registered for data source " + this.poolName);
      } else {
         Connection conn;
         if (this.useDataSource) {
            if ((!this.useDatabaseCredentials || username == null) && (username == null || !this.useBI_UserName)) {
               conn = ((WLDataSource)this.driverInstance).getConnection(labels);
            } else {
               conn = ((WLDataSource)this.driverInstance).getConnection(username, password, labels);
            }
         } else if (this.useDriver) {
            Properties props = null;
            if (labels != null || this.useDatabaseCredentials && username != null) {
               props = (Properties)this.driverProps.clone();
               if (labels != null) {
                  props.put("RequestedLabels", labels);
               }

               if (this.useDatabaseCredentials && username != null) {
                  props.put("useDatabaseCredentials", "true");
                  props.put("user", username);
                  if (password != null) {
                     props.put("password", password);
                  }
               }
            } else if (this.useBI_UserName) {
               props = (Properties)this.driverProps.clone();
               props.put("IMPERSONATE", username);
            } else {
               props = this.driverProps;
            }

            if (this.driverInstance != null) {
               conn = this.driverInstance.connect(this.driverUrl, props);
            } else {
               conn = getConnection0(this.driverUrl, props);
            }
         } else {
            conn = this.getPoolConnection(username, password, labels);
         }

         try {
            ((weblogic.jdbc.wrapper.Connection)conn).setRMIDataSource(this);
            ((weblogic.jdbc.wrapper.Connection)conn).setPoolName(this.poolName);
         } catch (Exception var10) {
         }

         boolean needsConfigure = true;
         ConnectionEnv ce = null;
         if (conn instanceof weblogic.jdbc.wrapper.Connection) {
            ce = ((weblogic.jdbc.wrapper.Connection)conn).getConnectionEnv();
            if (ce != null && !ce.isNeedsLabelingConfigure()) {
               ((weblogic.jdbc.wrapper.Connection)conn).getConnectionEnv().setNeedsLabelingConfigure(true);
               needsConfigure = false;
            }

            JDBCConnectionPool cp = null;

            try {
               cp = ConnectionPoolManager.getPool(this.poolName, this.appName, this.moduleName, this.compName);
            } catch (Exception var9) {
            }

            boolean harvesting = false;
            if (cp != null) {
               harvesting = cp.getConnectionHarvestTriggerCount() != -1;
            }

            ((HarvestableConnection)conn).setConnectionHarvestable(harvesting);
         } else if (conn instanceof Remote && !(conn instanceof LabelableConnection)) {
            needsConfigure = false;
         }

         if (needsConfigure) {
            ConnectionLabelingCallback clc = this.getConnectionLabelingCallback();
            if (clc != null) {
               try {
                  if (ce != null) {
                     ce.labelConfigure(clc, labels, conn);
                  } else {
                     clc.configure(labels, conn);
                  }
               } catch (RuntimeException var11) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug(clc.getClass().getName() + ".configure() error: labels=" + labels + ", connection=" + conn + ": " + var11);
                  }

                  this.releaseConnection(conn);
                  throw var11;
               } catch (Error var12) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug(clc.getClass().getName() + ".configure() error: labels=" + labels + ", connection=" + conn + ": " + var12);
                  }

                  this.releaseConnection(conn);
                  throw var12;
               }
            }
         }

         return conn;
      }
   }

   private static Connection getConnection0(final String url, final Properties props) throws SQLException {
      try {
         return (Connection)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws SQLException {
               return DriverManager.getConnection(url, props);
            }
         });
      } catch (PrivilegedActionException var3) {
         throw (SQLException)var3.getException();
      }
   }

   private void releaseConnection(Connection conn) {
      if (conn instanceof weblogic.jdbc.wrapper.Connection) {
         try {
            ConnectionPoolManager.release(((weblogic.jdbc.wrapper.Connection)conn).getConnectionEnv());
         } catch (ResourceException var3) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("error releasing connection after ConnectionLabelingCallback.configure() error: " + var3);
            }
         }
      }

   }

   protected Connection getPoolConnectionObj(ConnectionEnv cc) throws SQLException {
      cc.checkIfEnabled();
      cc.setNotInUse();
      PoolConnection poolConnection = (PoolConnection)JDBCWrapperFactory.getWrapper(0, cc.conn.jconn, false);
      poolConnection.init(cc);
      cc.setResourceCleanupHandler(poolConnection);
      return (Connection)poolConnection;
   }

   public boolean isTxDataSource() {
      return this.txDataSource;
   }

   public void start(Object rmiDataSource) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > DS:start (10)");
      }

      this.active = true;

      try {
         if (this.jdbcCtx != null) {
            if ("Global".equals(this.scope)) {
               this.ctxGlobal = JDBCUtil.getContext();
               JDBCUtil.bindAll(this.ctxGlobal, this.jndiNames, rmiDataSource);
            } else {
               JDBCUtil.localBindAll(this.jdbcCtx, this.jndiNames, rmiDataSource);

               try {
                  JDBCUtil.localBindAll(this.jdbcCtx, new String[]{this.poolName}, rmiDataSource);
               } catch (Exception var3) {
               }
            }
         }
      } catch (NamingException var4) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* DS:start (20)");
         }

         throw new ResourceException(var4.getMessage());
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < DS:start (100)");
      }

   }

   public void resume() throws ResourceException {
   }

   public void suspend(boolean unused) throws ResourceException {
   }

   public void forceSuspend(boolean unused) throws ResourceException {
   }

   public void shutdown() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > DS:shutdown (10)");
      }

      this.active = false;
      if (this.driverInstance instanceof XAResource || this.driverInstance instanceof weblogic.jdbc.jts.Driver && !this.isLoggingResource && this.jtaRegistrationName != null) {
         try {
            if ("Global".equals(this.scope)) {
               ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(this.jtaRegistrationName);
            } else {
               ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(this.jtaRegistrationName, true);
            }
         } catch (SystemException var3) {
         }
      }

      try {
         if (this.jdbcCtx != null) {
            if ("Global".equals(this.scope)) {
               if (this.ctxGlobal != null) {
                  JDBCUtil.unBindAll(this.ctxGlobal, this.jndiNames);
               }
            } else {
               JDBCUtil.localUnBindAll(this.jdbcCtx, this.jndiNames);

               try {
                  JDBCUtil.localUnBindAll(this.jdbcCtx, new String[]{this.poolName});
               } catch (Exception var2) {
               }
            }
         }
      } catch (NamingException var4) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* DS:shutdown (20)");
         }

         throw new ResourceException(var4.getMessage());
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < DS:shutdown (100)");
      }

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

   public void registerConnectionLabelingCallback(ConnectionLabelingCallback cbk) throws SQLException {
      try {
         JDBCConnectionPool cp = ConnectionPoolManager.getPool(this.poolName, this.appName, this.moduleName, this.compName);
         if (cp == null) {
            String name = JDBCUtil.getDecoratedName(this.poolName, this.appName, this.moduleName, this.compName);
            if (ConnectionPoolManager.getMultiPool(this.poolName, this.appName, this.moduleName, this.compName) != null) {
               throw new SQLException("Pool " + name + " is a MultiPool. Cannot accept a labeling callback.");
            } else {
               throw new SQLException("Pool " + name + " does not exist");
            }
         } else if (cbk != null && cp.getLabelingCallback() != null) {
            throw new SQLException("ConnectionLabelingCallback already registered for pool " + this.poolName + "@" + this.appName + "@" + this.moduleName);
         } else {
            cp.setLabelingCallback(cbk);
         }
      } catch (ResourceException var4) {
         SQLException sqle = new SQLException("unable to obtain connection pool " + this.poolName);
         sqle.initCause(var4);
         throw sqle;
      }
   }

   public void removeConnectionLabelingCallback() throws SQLException {
      try {
         JDBCConnectionPool cp = ConnectionPoolManager.getPool(this.poolName, this.appName, this.moduleName, this.compName);
         if (cp == null) {
            String name = JDBCUtil.getDecoratedName(this.poolName, this.appName, this.moduleName, this.compName);
            if (ConnectionPoolManager.getMultiPool(this.poolName, this.appName, this.moduleName, this.compName) != null) {
               throw new SQLException("Pool " + name + " is a MultiPool. It cannot have a labeling callback.");
            } else {
               throw new SQLException("Pool " + name + " does not exist");
            }
         } else {
            cp.setLabelingCallback((ConnectionLabelingCallback)null);
         }
      } catch (ResourceException var3) {
         SQLException sqle = new SQLException("unable to obtain connection pool " + this.poolName);
         sqle.initCause(var3);
         throw sqle;
      }
   }

   public void registerConnectionInitializationCallback(ConnectionInitializationCallback cbk) throws SQLException {
      try {
         JDBCConnectionPool cp = ConnectionPoolManager.getPool(this.poolName, this.appName, this.moduleName, this.compName);
         if (cp == null) {
            String name = JDBCUtil.getDecoratedName(this.poolName, this.appName, this.moduleName, this.compName);
            if (ConnectionPoolManager.getMultiPool(this.poolName, this.appName, this.moduleName, this.compName) != null) {
               throw new SQLException("Pool " + name + " is a MultiPool. Cannot accept an initialization callback.");
            } else {
               throw new SQLException("Pool " + name + " does not exist");
            }
         } else if (cbk != null && cp.getInitializationCallback() != null) {
            throw new SQLException("ConnectionInitializationCallback already registered for pool " + this.poolName + "@" + this.appName + "@" + this.moduleName);
         } else {
            cp.setInitializationCallback(cbk);
         }
      } catch (ResourceException var4) {
         SQLException sqle = new SQLException("unable to obtain connection pool " + this.poolName);
         sqle.initCause(var4);
         throw sqle;
      }
   }

   public void unregisterConnectionInitializationCallback() throws SQLException {
      try {
         JDBCConnectionPool cp = ConnectionPoolManager.getPool(this.poolName, this.appName, this.moduleName, this.compName);
         if (cp == null) {
            String name = JDBCUtil.getDecoratedName(this.poolName, this.appName, this.moduleName, this.compName);
            if (ConnectionPoolManager.getMultiPool(this.poolName, this.appName, this.moduleName, this.compName) != null) {
               throw new SQLException("Pool " + name + " is a MultiPool. It cannot have an initialization callback.");
            } else {
               throw new SQLException("Pool " + name + " does not exist");
            }
         } else {
            cp.setInitializationCallback((ConnectionInitializationCallback)null);
         }
      } catch (ResourceException var3) {
         SQLException sqle = new SQLException("unable to obtain connection pool " + this.poolName);
         sqle.initCause(var3);
         throw sqle;
      }
   }

   public Connection getConnectionToInstance(String instanceName) throws SQLException {
      Properties internalLabels = new Properties();
      internalLabels.setProperty("_weblogic.jdbc.instanceName", instanceName);
      return this.getConnection(internalLabels);
   }

   public Connection getConnectionToInstance(Connection sameAsThisOne) throws SQLException {
      if (sameAsThisOne instanceof weblogic.jdbc.wrapper.Connection) {
         ConnectionEnv ce = ((weblogic.jdbc.wrapper.Connection)sameAsThisOne).getConnectionEnv();
         if (ce instanceof RACConnectionEnv) {
            RACConnectionEnv racce = (RACConnectionEnv)ce;
            RACInstance racInstance = racce.getRACInstance();
            if (racInstance == null) {
               throw new SQLException("Unable to determine instance");
            }

            return this.getConnectionToInstance(racInstance.getInstance());
         }
      }

      throw new SQLException("Invalid connection argument: " + sameAsThisOne);
   }

   public Connection createConnection(Properties additionalProperties) throws SQLException {
      if (additionalProperties == null) {
         additionalProperties = new Properties();
      }

      Properties internalLabels = new Properties();
      internalLabels.put("_weblogic.jdbc.properties", additionalProperties);
      return this.getConnectionInternal((String)null, (String)null, internalLabels);
   }

   public Connection createConnectionToInstance(String instance, Properties additionalProperties) throws SQLException {
      if (instance == null) {
         throw new SQLException("Unable to determine instance");
      } else {
         Properties internalLabels = new Properties();
         internalLabels.setProperty("_weblogic.jdbc.instanceName", instance);
         if (additionalProperties == null) {
            additionalProperties = new Properties();
         }

         internalLabels.put("_weblogic.jdbc.properties", additionalProperties);
         return this.getConnectionInternal((String)null, (String)null, internalLabels);
      }
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public boolean isAccessAllowed() {
      return this.crossPartitionEnabled;
   }
}
