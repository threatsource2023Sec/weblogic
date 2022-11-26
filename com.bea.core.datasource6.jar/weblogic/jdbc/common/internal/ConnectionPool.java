package weblogic.jdbc.common.internal;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.resource.spi.security.PasswordCredential;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import oracle.ucp.jdbc.LabelableConnection;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.IGroupingPooledResourceLinkedList;
import weblogic.common.resourcepool.IGroupingPooledResourceSet;
import weblogic.common.resourcepool.ObjectLifeCycleException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ReserveReleaseInterceptor;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.common.resourcepool.ResourceDisabledException;
import weblogic.common.resourcepool.ResourceLimitException;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.common.resourcepool.ResourcePoolImpl;
import weblogic.common.resourcepool.ResourcePoolMaintainer;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.common.resourcepool.ResourceSystemException;
import weblogic.common.resourcepool.ResourceUnavailableException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.rac.OracleHelperFactory;
import weblogic.jdbc.extensions.DriverInterceptor;
import weblogic.jdbc.jta.DataSource;
import weblogic.kernel.AuditableThread;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.JDBCResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.wrapper.WrapperClassFile;

public abstract class ConnectionPool extends ResourcePoolImpl implements ResourcePoolMaintainer, JDBCConnectionPool, OraclePool, ReserveReleaseInterceptor {
   public static final String CP_PROP_DRIVER = "Driver";
   public static final String CP_PROP_URL = "Url";
   public static final String CP_PROP_DBG_LVL = "DebugLevel";
   public static final String CP_PROP_TEST_NAME = "testName";
   public static final String CP_PROP_INIT_NAME = "initName";
   public static final String CP_PROP_FATAL_ERROR_CODES = "FatalErrorCodes";
   public static final String CP_PROP_CACHE_SIZE = "PSCacheSize";
   public static final String CP_PROP_CACHE_TYPE = "PSCacheType";
   public static final String CP_PROP_USE_XA_INTERFACE = "UseXAInterface";
   public static final String CP_PROP_REQUESTED_LABELS = "RequestedLabels";
   public static final int DEFAULT_WAIT = -2;
   public static final int UNKNOWN = -1000;
   public static final int SIZE = -1001;
   public static final int CURRENTINUSE = -1002;
   private static final boolean verbose = false;
   public JDBCDataSourceBean dsBean;
   private String appName;
   private String moduleName;
   private String compName;
   private String mpName;
   private boolean active;
   private boolean isMemberDS;
   private ConnectionPoolProfiler profiler;
   private ReadWriteLock cacheLock;
   private HashSet userCache;
   private DriverInterceptor driverInterceptor;
   private boolean initializedSecurity;
   private boolean getConnectionInfoForUser;
   private boolean oracleOptimizeUtf8Conversion;
   private boolean wrapTypes;
   private boolean wrapJdbc;
   private JDBCResource cachedResvRes;
   private AuthorizationManager am;
   OracleHelper oracleHelper;
   ReplayStatisticsSnapshot replayStatisticsSnapshot;
   ReplayStatisticsSnapshot closedConnectionReplayStatistics;
   protected int replayInitiationTimeout;
   private AtomicInteger userCacheTime;
   protected AtomicInteger harvestTime;
   private AtomicInteger leakProfileTime;
   protected boolean localValidateOnly;
   private volatile boolean suspendShuttingDown;
   private static final AuthenticatedSubject KERNELID = getKernelID();
   public final ConnectionPoolConfig config;
   protected ClassLoader classLoader;
   protected ConnectionLabelingCallback labelingCallback;
   protected ConnectionInitializationCallback initializationCallback;
   protected int connectionHarvestMaxCount;
   protected int connectionHarvestTriggerCount;
   protected ComponentInvocationContext ownerCIC;
   protected int oracleVersion;
   protected AtomicBoolean sharedPool;
   protected AtomicInteger sharedPoolReferenceCounter;
   protected AtomicLong repurposeCount;
   protected AtomicLong failedRepurposeCount;
   protected AtomicLong resolvedAsCommittedTotalCount;
   protected AtomicLong resolvedAsNotCommittedTotalCount;
   protected AtomicLong unresolvedTotalCount;
   protected AtomicLong commitOutcomeRetryTotalCount;
   protected SwitchingContext rootSwitchingContext;
   protected DataSource jtaDataSource;
   private boolean continueMakeResourceAttemptsAfterFailureSysProp;
   protected static final AuditableThreadLocal connections = AuditableThreadLocalFactory.createThreadLocal(new ConnectionThreadLocal());
   private boolean createConnectionInline;
   Resource resource;
   Method setDBMSIdentityMethod;
   Method clearDBMSIdentityMethod;
   String clientInfo;
   private Map physicalConnectionMap;
   private boolean hasSetProxyObject;

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public ConnectionPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) {
      this((JDBCDataSourceBean)dsBean, appName, moduleName, (String)compName, (ClassLoader)null);
   }

   public ConnectionPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, ClassLoader classLoader) {
      this((String)appName, moduleName, compName, (ConnectionPoolConfig)(new DataSourceConnectionPoolConfig(dsBean, classLoader, appName, moduleName, compName)), classLoader);
      this.dsBean = dsBean;
      this.setupDriverInterceptor();
      this.setupConnectionLabelingCallback();
      this.setupConnectionHarvesting();
      this.setupConnectionInitializationCallback();
      this.setXAMultiPoolName(JDBCHelper.getHelper().getXAMultiPoolName(dsBean));
   }

   public ConnectionPool(String appName, String moduleName, String compName, ConnectionPoolConfig config, ClassLoader classLoader) {
      this.dsBean = null;
      this.active = false;
      this.isMemberDS = false;
      this.profiler = new ConnectionPoolProfiler(this);
      this.cacheLock = new ReentrantReadWriteLock();
      this.userCache = new HashSet();
      this.initializedSecurity = false;
      this.getConnectionInfoForUser = false;
      this.oracleOptimizeUtf8Conversion = false;
      this.wrapTypes = true;
      this.wrapJdbc = true;
      this.cachedResvRes = null;
      this.am = null;
      this.oracleHelper = null;
      this.replayStatisticsSnapshot = null;
      this.replayInitiationTimeout = 3600;
      this.userCacheTime = new AtomicInteger(0);
      this.harvestTime = new AtomicInteger(0);
      this.leakProfileTime = new AtomicInteger(0);
      this.suspendShuttingDown = false;
      this.connectionHarvestMaxCount = 1;
      this.connectionHarvestTriggerCount = -1;
      this.oracleVersion = -1;
      this.sharedPool = new AtomicBoolean();
      this.sharedPoolReferenceCounter = new AtomicInteger(0);
      this.repurposeCount = new AtomicLong(0L);
      this.failedRepurposeCount = new AtomicLong(0L);
      this.resolvedAsCommittedTotalCount = new AtomicLong(0L);
      this.resolvedAsNotCommittedTotalCount = new AtomicLong(0L);
      this.unresolvedTotalCount = new AtomicLong(0L);
      this.commitOutcomeRetryTotalCount = new AtomicLong(0L);
      this.continueMakeResourceAttemptsAfterFailureSysProp = Boolean.getBoolean("weblogic.jdbc.continueMakeResourceAttemptsAfterFailure");
      this.createConnectionInline = false;
      this.setDBMSIdentityMethod = null;
      this.clearDBMSIdentityMethod = null;
      this.clientInfo = null;
      this.physicalConnectionMap = Collections.synchronizedMap(new WeakHashMap());
      this.hasSetProxyObject = false;
      super.setShrinkFactor(0.5F);
      this.config = config;
      this.appName = appName;
      this.moduleName = moduleName;
      this.compName = compName;
      this.classLoader = classLoader;
      this.oracleOptimizeUtf8Conversion = config.isOracleOptimizeUtf8Conversion();
      this.wrapTypes = config.isWrapTypes();
      this.wrapJdbc = config.isWrapJdbc();
      this.sharedPool.set(config.isSharedPool());
   }

   public void start(Object unused) throws ResourceException {
      this.start(unused, this.isMemberDS);
   }

   public void start(Object unused, boolean isMemberDS) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):start (10)");
      }

      if (this.config != null && this.config.getDriver() != null && this.config.getDriver().startsWith("oracle.jdbc.")) {
         try {
            DataSourceUtil.loadDriverClass("oracle.jdbc.LogicalTransactionId", this.classLoader);

            try {
               DataSourceUtil.loadDriverClass("oracle.jdbc.replay.ReplayStatistics", this.classLoader);

               try {
                  DataSourceUtil.loadDriverClass("oracle.jdbc.replay.OracleXADataSourceImpl", this.classLoader);
                  this.oracleVersion = 1220;
               } catch (Throwable var6) {
                  this.oracleVersion = 1202;
               }
            } catch (Throwable var7) {
               this.oracleVersion = 1201;
            }
         } catch (Throwable var8) {
            this.oracleVersion = 1100;
         }
      } else {
         this.oracleVersion = -1;
      }

      this.initOracleHelper();
      if (this.oracleHelper != null && this.oracleHelper.isReplayDriver() && this.oracleVersion >= 1202) {
         this.replayStatisticsSnapshot = new ReplayStatisticsSnapshot();
         this.closedConnectionReplayStatistics = new ReplayStatisticsSnapshot();
         this.closedConnectionReplayStatistics.initialize(0L);
      }

      this.isMemberDS = isMemberDS;

      try {
         this.doStart();
      } catch (ResourceException var9) {
         JDBCHelper jdbcHelper = JDBCHelper.getHelper();
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):start (50) is Member of a MultiDataSource" + isMemberDS + " is an LLR DataSource: " + jdbcHelper.isLLRPool(this.dsBean) + " the property weblogic.llr.table is set " + jdbcHelper.isLLRTablePerDataSource(this.name));
         }

         if (jdbcHelper.isLLRPool(this.dsBean) && !isMemberDS && jdbcHelper.isLLRTablePerDataSource(this.name)) {
            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            tm.registerFailedLoggingResource(var9);
         }

         throw var9;
      }

      if (!this.initializedSecurity) {
         this.initSecurity();
         this.initializedSecurity = true;
      }

      this.initOwnerCIC();
      this.active = true;
      if (!this.testOnRelease && !this.testOnReserve && this.testSecs <= 0) {
         this.localValidateOnly = true;
         if (this.config != null && this.config.getDriver().startsWith("oracle.jdbc.")) {
            this.testOnReserve = true;
         }
      } else {
         this.localValidateOnly = false;
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):start (100)");
      }

   }

   public boolean isLocalValidateOnly() {
      return this.localValidateOnly;
   }

   protected void initOwnerCIC() {
      if (this.ownerCIC == null) {
         ComponentInvocationContext cic = this.getComponentInvocationContextManager().getCurrentComponentInvocationContext();
         if (cic != null) {
            this.ownerCIC = cic;
         } else {
            String partitionName = this.getPartitionName();
            if (partitionName != null) {
               this.ownerCIC = this.getComponentInvocationContextManager().createComponentInvocationContext(partitionName, this.appName, JDBCHelper.getHelper().getVersionId(this.appName), this.moduleName, this.compName);
            } else {
               this.ownerCIC = this.getComponentInvocationContextManager().createComponentInvocationContext("DOMAIN", this.appName, JDBCHelper.getHelper().getVersionId(this.appName), this.moduleName, this.compName);
            }
         }

      }
   }

   public void initOracleHelper() throws ResourceException {
      if (this.oracleVersion != -1) {
         if (this.oracleHelper == null) {
            try {
               this.oracleHelper = OracleHelperFactory.createInstance(this);
            } catch (Throwable var2) {
            }
         }

      }
   }

   public void resume() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):resume (10)");
      }

      super.resume();
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):resume (100)");
      }

   }

   public void suspend(boolean shuttingDown, int operationSecs) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):suspend (10)");
      }

      this.suspendShuttingDown = shuttingDown;

      try {
         super.suspend(shuttingDown, operationSecs);
      } finally {
         this.suspendShuttingDown = false;
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):suspend (100)");
      }

   }

   public void forceSuspend(boolean shuttingDown) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):forceSuspend (10)");
      }

      super.forceSuspend(shuttingDown);
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):forceSuspend (100)");
      }

   }

   public void shutdown() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):shutdown (10)");
      }

      this.active = false;
      super.shutdown();
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):shutdown (100)");
      }

   }

   public void activate() throws IllegalStateException, ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):activate (10)");
      }

      try {
         this.start((Object)null);
         this.resume();
         ConnectionPoolManager.addPool(this.name, this.appName, this.moduleName, this.compName, this);
      } catch (ResourceException var2) {
         JDBCLogger.logPoolActivateFailed(this.name, this.appName, this.moduleName, var2.toString());
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):activate (20)");
         }

         if (var2 instanceof ObjectLifeCycleException) {
            throw new IllegalStateException(var2.toString());
         }

         throw var2;
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):activate (100)");
      }

   }

   public void deactivate() throws IllegalStateException, ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):deactivate");
      }

      try {
         this.suspend(true, 0);
         this.shutdown();
         ConnectionPoolManager.removePool(this.name, this.appName, this.moduleName, this.compName);
      } catch (ResourceException var2) {
         JDBCLogger.logPoolDeactivateFailed(this.name, this.appName, this.moduleName, var2.toString());
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):deactivate (20)");
         }

         if (var2 instanceof ObjectLifeCycleException) {
            throw new IllegalStateException(var2.toString());
         }

         throw var2;
      }

      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):deactivate (100)");
      }

   }

   public void setShrinkFrequencySeconds(int newVal) {
      if (newVal == 0) {
         this.allowShrinking = false;
      } else {
         this.allowShrinking = true;
      }

      super.setShrinkFrequencySeconds(newVal);
   }

   public void setTestFrequencySeconds(int newVal) {
      super.setTestFrequencySeconds(newVal);
   }

   public void setCountOfRefreshFailuresTillDisable(int newVal) {
      super.setCountOfRefreshFailuresTillDisable(newVal);
   }

   public void setCountOfTestFailuresTillFlush(int newVal) {
      super.setCountOfTestFailuresTillFlush(newVal);
   }

   public void setStatementCacheSize(int newVal) {
      synchronized(this) {
         PooledResource[] resList = this.getResources();
         if (resList != null) {
            for(int i = 0; i < resList.length; ++i) {
               ((ConnectionEnv)((ConnectionEnv)resList[i])).setStatementCacheSize(newVal);
            }
         }
      }

      if (this.resFactory != null) {
         ((JDBCResourceFactory)this.resFactory).setStatementCacheSize(newVal);
      }

   }

   public void setProfileType(int newVal) {
      this.profiler.setProfileType(newVal);
   }

   public void setProfileConnectionLeakTimeoutSeconds(int newVal) {
      this.profiler.setProfileConnectionLeakTimeoutSeconds(newVal);
   }

   public void setSecondsToTrustAnIdlePoolConnection(int newVal) {
      synchronized(this) {
         PooledResource[] resList = this.getResources();
         if (resList != null) {
            for(int i = 0; i < resList.length; ++i) {
               ((ConnectionEnv)((ConnectionEnv)resList[i])).setSecondsToTrustAnIdlePoolConnection(newVal);
            }
         }
      }

      ((JDBCResourceFactory)this.resFactory).setSecondsToTrustAnIdlePoolConnection(newVal);
   }

   public ConnectionEnv reserveInternal(int waitSeconds) throws ResourceException {
      return this.reserve((AuthenticatedSubject)null, waitSeconds, true, (Properties)null);
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds) throws ResourceException {
      return this.reserve(user, waitSeconds, false, (Properties)null);
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, Properties requestedLabels) throws ResourceException {
      return this.reserve(user, waitSeconds, false, requestedLabels);
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException {
      return this.reserve(user, waitSeconds, false, requestedLabels, username, password);
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, boolean internalUse, Properties requestedLabels) throws ResourceException {
      return this.reserve(user, waitSeconds, internalUse, requestedLabels, (String)null, (String)null);
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, boolean internalUse, Properties requestedLabels, String username, String password) throws ResourceException {
      if (!internalUse && !this.active) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):reserve (10)");
         }

         throw new ResourceDisabledException(JDBCUtil.getTextFormatter().dsInactive(this.name));
      } else {
         if (!internalUse) {
            this.doAuthorizationCheck(user);
         }

         if (requestedLabels != null && requestedLabels.containsKey("_weblogic.jdbc.instanceName")) {
            throw new FeatureNotSupportedException(JDBCUtil.getTextFormatter().badConnectionToInstance());
         } else {
            SwitchingContext switchingContext = SwitchingContextManager.getInstance().get();
            ConnectionEnv ce = null;

            ConnectionEnv var33;
            try {
               Properties additionalProperties = null;
               if (this.config.isPinnedToThread()) {
                  if (requestedLabels != null) {
                     throw new ResourceException(JDBCUtil.getTextFormatter().badLabelPinned());
                  }

                  ce = this.reservePinnedToThread(waitSeconds, internalUse);
               } else {
                  PooledResourceInfo ci = null;
                  if (requestedLabels != null && requestedLabels.get("_weblogic.jdbc.properties") != null) {
                     additionalProperties = (Properties)requestedLabels.get("_weblogic.jdbc.properties");
                     ci = new PropertiesConnectionInfo(additionalProperties);
                     requestedLabels = null;
                  } else if (this.getConnectionInfoForUser) {
                     if (user == null) {
                        user = SecurityServiceManager.getCurrentSubject(KERNELID);
                     }

                     if (requestedLabels != null) {
                        throw new ResourceException(JDBCUtil.getTextFormatter().badLabelIdentity());
                     }

                     if (switchingContext != null) {
                        throw new ResourceException("shared pooling not supported with identity-based pooling or impersonation");
                     }

                     ci = this.getConnectionInfo(user, username, password, internalUse);
                  }

                  if (switchingContext != null) {
                     ci = new LabelingConnectionInfo(switchingContext.getPDBName(), switchingContext.getPDBServiceName(), (Properties)null);
                     ce = (ConnectionEnv)super.reserveResource(-1, (PooledResourceInfo)ci, internalUse, false);
                     if (ce == null) {
                        ci = null;
                     }
                  }

                  if (ce == null) {
                     if (requestedLabels != null) {
                        ci = new LabelingConnectionInfo(requestedLabels);
                     }

                     if (waitSeconds == -2) {
                        ce = (ConnectionEnv)super.reserveResource((PooledResourceInfo)ci, internalUse);
                     } else {
                        ce = (ConnectionEnv)super.reserveResource(waitSeconds, (PooledResourceInfo)ci, internalUse, true);
                     }
                  }
               }

               if (additionalProperties != null) {
                  ce.setInfected(true);
                  ce.setRefreshNeeded(true);
                  JDBCConnectionPool pool = ConnectionPoolManager.getPool(ce.getPoolName(), ce.getAppName(), ce.getModuleName(), ce.getCompName());
                  if (pool == null) {
                     throw new ResourceException(JDBCUtil.getTextFormatter().badPool(ce.getPoolName()));
                  }

                  pool.removeConnection(ce);
               }

               this.processOracleProxySession(user, username, password, ce, internalUse);
               var33 = ce;
            } catch (ResourceUnavailableException | ResourceLimitException var30) {
               if (this.profiler.isResourceLeakProfilingEnabled()) {
                  synchronized(this) {
                     Iterator it = this.reserved.iterator();

                     while(it.hasNext()) {
                        ConnectionEnv res = (ConnectionEnv)it.next();
                        if (!res.profileRecordLogged) {
                           this.profiler.addLeakData(res);
                           res.profileRecordLogged = true;
                        }
                     }
                  }
               }

               throw var30;
            } finally {
               if (requestedLabels != null && ce != null && ce.drcpEnabled) {
                  try {
                     ConnectionPoolManager.release(ce);
                  } catch (ResourceException var26) {
                  }

                  ce = null;
                  throw new ResourceException(JDBCUtil.getTextFormatter().badLabelPooled());
               }

               this.beginRequest(this.oracleHelper, ce);
               if (ce != null && this.labelingCallback == null && this.initializationCallback != null) {
                  try {
                     ce.connectionInitialize(this.initializationCallback);
                  } catch (Exception var28) {
                     try {
                        ConnectionPoolManager.release(ce);
                     } catch (ResourceException var27) {
                        throw new ResourceException(JDBCUtil.getTextFormatter().badCICallback(var27.getMessage()), var28);
                     }

                     throw new ResourceException(JDBCUtil.getTextFormatter().badCICallback2(), var28);
                  }
               }

            }

            return var33;
         }
      }
   }

   protected ConnectionEnv reservePinnedToThread(int waitSeconds, boolean internalUse) throws ResourceException, ResourceDisabledException {
      ConnectionStore pinned = (ConnectionStore)connections.get();
      if (pinned == null) {
         throw new RuntimeException(JDBCUtil.getTextFormatter().badPinnedAuditable());
      } else {
         ConnectionEnv ce;
         if ((ce = (ConnectionEnv)pinned.get(this)) == null) {
            if (this.createConnectionInline) {
               synchronized(this) {
                  if (waitSeconds == -2) {
                     ce = (ConnectionEnv)super.reserveResource((PooledResourceInfo)null, internalUse);
                  } else {
                     ce = (ConnectionEnv)super.reserveResource(waitSeconds, (PooledResourceInfo)null, internalUse, true);
                  }
               }
            } else if (waitSeconds == -2) {
               ce = (ConnectionEnv)super.reserveResource((PooledResourceInfo)null, internalUse);
            } else {
               ce = (ConnectionEnv)super.reserveResource(waitSeconds, (PooledResourceInfo)null, internalUse, true);
            }

            if (ce.isRefreshNeeded()) {
               this.refresh(ce);
               ce.initialize();
               ce.setRefreshNeeded(false);
            }

            ce.setResourceCleanupHandler(ce);
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Pinned: Pinned " + ce + " to thread " + Thread.currentThread());
            }
         } else {
            if (!internalUse && this.state != 101) {
               throw new ResourceDisabledException(JDBCUtil.getTextFormatter().poolAllocate(this.name, this.getDerivedState()));
            }

            if (ce.isRefreshNeeded()) {
               this.refresh(ce);
               ce.initialize();
               ce.setRefreshNeeded(false);
            }

            if (ce.isCleanupNeeded()) {
               ce.cleanup();
               ce.setCleanupNeeded(false);
            }

            if (ce.test() == -1) {
               this.refresh(ce);
               ce.initialize();
               if (ce.test() == -1) {
                  ce.setRefreshNeeded(true);
                  throw new ResourceException(JDBCUtil.getTextFormatter().badConnect());
               }
            }

            ce.setTestNeeded(false);
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Pinned: Reserved " + ce + " for thread " + Thread.currentThread());
            }
         }

         ce.setUsed(true);
         ce.setOwner(Thread.currentThread());
         return ce;
      }
   }

   protected boolean continueMakeResourceAttemptsAfterFailure() {
      if (this.config.isStartupRetryEnabled() && JDBCHelper.getHelper().isServerStarting()) {
         return false;
      } else {
         return this.config.isContinueMakeResourceAttemptsAfterFailure() || this.continueMakeResourceAttemptsAfterFailureSysProp;
      }
   }

   protected PooledResource makeResource(PooledResourceInfo pri) throws ResourceException {
      if (this.config.isStartupRetryEnabled() && JDBCHelper.getHelper().isServerStarting() && !JDBCHelper.getHelper().isServerStarted()) {
         int attempt = 1;

         while(attempt <= this.config.getStartupRetryCount() + 1) {
            try {
               return super.makeResource(pri);
            } catch (ResourceException var6) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JdbcDebug.JDBCCONN.debug("CP(" + this.name + ") makeResource attempt " + attempt + " failed", var6);
               }

               if (attempt >= this.config.getStartupRetryCount() + 1) {
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("CP(" + this.name + ") makeResource failed after " + attempt + " attempts");
                  }

                  throw new ResourceSystemException("failed to create resource after " + attempt + " attempts", var6);
               }

               if (this.config.getStartupRetryDelaySeconds() > 0) {
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("CP(" + this.name + ") makeResource retrying after " + this.config.getStartupRetryDelaySeconds() + " seconds");
                  }

                  try {
                     Thread.sleep((long)(this.config.getStartupRetryDelaySeconds() * 1000));
                  } catch (InterruptedException var5) {
                  }
               }

               ++attempt;
            }
         }

         return null;
      } else {
         return super.makeResource(pri);
      }
   }

   public PooledResource matchResource(PooledResourceInfo pri) throws ResourceException {
      ConnectionLabelingCallback clc = this.labelingCallback;
      if (clc != null && pri instanceof LabelingConnectionInfo) {
         LabelingConnectionInfo lci = (LabelingConnectionInfo)pri;
         Properties requestedLabels = lci.getLabels();
         synchronized(this) {
            int leastCost = Integer.MAX_VALUE;
            int bestMatch = -1;
            boolean bestMatchHighCost = false;

            int i;
            for(i = 0; i < this.available.size(); ++i) {
               ConnectionEnv res = (ConnectionEnv)this.available.get(i);
               Properties currentLabels = res.getLabels();
               if (currentLabels == null) {
                  currentLabels = new Properties();
               }

               int cost = clc.cost(requestedLabels, currentLabels);
               if (cost == 0) {
                  res = (ConnectionEnv)this.available.remove(i);
                  res.setNeedsLabelingConfigure(false);
                  return res;
               }

               boolean isHighCost = cost >= this.config.getConnectionLabelingHighCost();
               if (cost < leastCost) {
                  leastCost = cost;
                  bestMatch = i;
                  bestMatchHighCost = isHighCost;
               }
            }

            if (bestMatch == -1) {
               return null;
            } else {
               if (bestMatchHighCost) {
                  i = this.getCurrCapacity();
                  if (i < this.getMinCapacity() || i < this.config.getHighCostConnectionReuseThreshold()) {
                     return null;
                  }
               }

               ConnectionEnv res = (ConnectionEnv)this.available.remove(bestMatch);
               res.setNeedsLabelingConfigure(true);
               return res;
            }
         }
      } else {
         return super.matchResource(pri);
      }
   }

   protected ComponentInvocationContextManager getComponentInvocationContextManager() {
      return ComponentInvocationContextManager.getInstance();
   }

   protected Callable getAuthorizationCallable(final AuthenticatedSubject user) {
      return new Callable() {
         public Object call() throws Exception {
            JDBCUtil.checkPermission(user, ConnectionPool.KERNELID, ConnectionPool.this.am, "ConnectionPool", ConnectionPool.this.name, ConnectionPool.this.appName, ConnectionPool.this.moduleName, ConnectionPool.this.compName, "reserve", (String)null, ConnectionPool.this.cachedResvRes);
            return null;
         }
      };
   }

   protected final void doAuthorizationCheck(AuthenticatedSubject user) throws ResourceException {
      boolean doAuthCheck = true;
      int securityCacheTimeoutSeconds = this.config.getSecurityCacheTimeoutSeconds();
      if (securityCacheTimeoutSeconds > 0) {
         if (user == null) {
            user = SecurityServiceManager.getCurrentSubject(KERNELID);
         }

         if (user != null) {
            this.cacheLock.readLock().lock();

            try {
               if (this.userCache.contains(user)) {
                  doAuthCheck = false;
               }
            } finally {
               this.cacheLock.readLock().unlock();
            }
         }
      }

      if (doAuthCheck) {
         try {
            this.getComponentInvocationContextManager();
            ComponentInvocationContextManager.runAs(KERNELID, this.ownerCIC, this.getAuthorizationCallable(user));
         } catch (ExecutionException var15) {
            Throwable cause = var15.getCause();
            if (cause instanceof ResourceException) {
               throw (ResourceException)cause;
            }

            if (cause instanceof RuntimeException) {
               throw (RuntimeException)cause;
            }

            throw new RuntimeException(cause);
         }

         if (securityCacheTimeoutSeconds > 0 && user != null) {
            this.cacheLock.writeLock().lock();

            try {
               this.userCache.add(user);
            } finally {
               this.cacheLock.writeLock().unlock();
            }
         }
      }

   }

   private ConnectionInfo getConnectionInfo(AuthenticatedSubject user) {
      return this.getConnectionInfo(user, (String)null, (String)null, false);
   }

   private ConnectionInfo getConnectionInfo(AuthenticatedSubject user, boolean internal) {
      return this.getConnectionInfo(user, (String)null, (String)null, internal);
   }

   private ConnectionInfo getConnectionInfo(AuthenticatedSubject user, String username, String password, boolean internal) {
      ConnectionInfo ret = this.config.getDefaultConnectionInfo();
      Vector dbmsClients = null;
      if (this.isOracleProxySession() && internal) {
         ret = null;
      }

      if (this.config.isIdentityBasedConnectionPoolingEnabled() && this.isUseDatabaseCredentials()) {
         try {
            if (username != null) {
               ret = new ConnectionInfo(username, password);
            }
         } catch (Exception var12) {
         }

         return ret;
      } else {
         dbmsClients = this.getDBMSIdentity(user);
         boolean useWlsOrBIId = false;

         try {
            useWlsOrBIId = this.config.getDriverProperties().containsKey("IMPERSONATE");
         } catch (Exception var14) {
         }

         if (dbmsClients != null && !dbmsClients.isEmpty()) {
            ret = new ConnectionInfo(((PasswordCredential)dbmsClients.elementAt(0)).getUserName(), ((PasswordCredential)dbmsClients.elementAt(0)).getPassword());
         } else if (useWlsOrBIId) {
            String user_id = "";
            if (username != null) {
               user_id = username;
            } else {
               Set set = user.getPrincipals();
               Iterator itr = set.iterator();
               if (itr.hasNext()) {
                  Object o = itr.next();
                  user_id = o.toString();
               }
            }

            try {
               ret = new ConnectionInfo((String)((String)this.config.getDriverProperties().get("user")), (String)((String)this.config.getDriverProperties().get("password")), user_id);
            } catch (Exception var13) {
            }
         }

         if (ret != null) {
            JdbcDebug.JDBCCONN.debug("Identity: Requesting ConnectionInfo: " + ret.getUsername() + "//" + ret.getWLUserID());
         } else {
            JdbcDebug.JDBCCONN.debug("Identity: Requesting null ConnectionInfo");
         }

         return ret;
      }
   }

   protected void refresh(ConnectionEnv ce) throws ResourceException {
      if (this.resFactory instanceof XAConnectionEnvFactory) {
         ((XAConnectionEnvFactory)this.resFactory).refreshResource(ce, false);
      } else {
         this.resFactory.refreshResource(ce);
      }

   }

   protected void createCollections() {
      super.createCollections();
      if (this.isSharedPool()) {
         this.available = new IndexedGroupingPooledResourceLinkedList();
         this.reserved = new IndexedGroupingPooledResourceSet();
      }

   }

   protected ConnectionStoreDelegate createConnectionStoreDelegate() {
      return new ConnectionStoreDelegate();
   }

   public void release(ConnectionEnv resource) throws ResourceException {
      boolean var16 = false;

      try {
         var16 = true;
         this.endRequest(this.oracleHelper, resource);
         var16 = false;
      } finally {
         if (var16) {
            XAException xae = null;
            String currentErrorStack = null;
            String var10 = "Unknown";
            String lastUserStack = null;
            if (this.profiler.isConnectionLocalTxLeakProfilingEnabled() && resource.conn != null && resource.conn.isOracleGetTransactionStateSupported() && resource.conn.isOracleLocalTransactionStarted()) {
               this.profiler.addConnLocalTxLeakData(StackTraceUtils.throwable2StackTrace(new Exception()), resource.getCurrentUser(), new Date());
            }

            if (this.profiler.isResourceLastUsageProfilingEnabled()) {
               if (resource.getCurrentError() != null) {
                  currentErrorStack = StackTraceUtils.throwable2StackTrace(resource.getCurrentError());
                  xae = (XAException)resource.getCurrentError();
                  if (resource.getLastUser() != null) {
                     lastUserStack = StackTraceUtils.throwable2StackTrace(resource.getLastUser());
                  }

                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     String stackTrace = "XA operation failed : ";
                     if (xae.errorCode == -4) {
                        stackTrace = stackTrace + " XAER_NOTA ";
                     }

                     stackTrace = stackTrace + currentErrorStack;
                     JdbcDebug.JDBCCONN.debug(stackTrace);
                     if (resource.getLastUser() != null) {
                        stackTrace = "Connection " + resource + " previously reserved from Pool " + resource.getPoolName() + " by: \n" + lastUserStack;
                        JdbcDebug.JDBCCONN.debug(stackTrace);
                     } else {
                        stackTrace = "Connection " + resource + " used for the first time. Internal error, contact BEA Technical Support..";
                        JdbcDebug.JDBCCONN.debug(stackTrace);
                     }
                  }

                  if (xae.errorCode != -4) {
                     JDBCLogger.logErrorMessage("XA operation failed : " + currentErrorStack);
                     if (resource.getLastUser() != null) {
                        JDBCLogger.logErrorMessage("Connection " + resource + " previously reserved from Pool " + resource.getPoolName() + " by: \n" + lastUserStack);
                     } else {
                        JDBCLogger.logErrorMessage("Connection " + resource + " used for the first time. Internal error, contact BEA Technical Support..");
                     }
                  }

                  this.profiler.addConnLastUsageData(currentErrorStack, lastUserStack, resource.getCurrentErrorTimestamp());
                  resource.setCurrentError((Throwable)null);
                  resource.setCurrentErrorTimestamp((Date)null);
               }

               resource.setLastUser(new Throwable());
            }

            if (this.config.isPinnedToThread() && !resource.isInfected()) {
               this.releasePinnedToThread(resource);
               return;
            }

            if (this.isOracleProxySession()) {
               resource.OracleProxyConnectionClose();
            }

            if (this.isSharedPool() && resource.isRepurposeOnRelease() && resource.getSwitchingContext() != null) {
               if (!this.isRootPartitionGroupEnabled(resource)) {
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     this.debug("Root partition not enabled. Destroying pooled resource " + resource);
                  }

                  resource.destroyForFlush(false);
                  this.removeResource(resource);
                  return;
               }

               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("switching connection to root partition (close-on-release): " + resource);
               }

               try {
                  this.switchToRootPartition(resource);
                  resource.setRepurposeOnRelease(false);
                  super.releaseResource(resource);
               } catch (ResourceException var17) {
                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     this.debug("error switching connection to root partition: " + resource + ": " + StackTraceUtils.throwable2StackTrace(var17));
                  }

                  resource.destroyForFlush(false);
                  this.removeResource(resource);
                  return;
               }
            } else {
               super.releaseResource(resource);
            }

         }
      }

      XAException xae = null;
      String currentErrorStack = null;
      String lastUser = "Unknown";
      String lastUserStack = null;
      if (this.profiler.isConnectionLocalTxLeakProfilingEnabled() && resource.conn != null && resource.conn.isOracleGetTransactionStateSupported() && resource.conn.isOracleLocalTransactionStarted()) {
         this.profiler.addConnLocalTxLeakData(StackTraceUtils.throwable2StackTrace(new Exception()), resource.getCurrentUser(), new Date());
      }

      if (this.profiler.isResourceLastUsageProfilingEnabled()) {
         if (resource.getCurrentError() != null) {
            currentErrorStack = StackTraceUtils.throwable2StackTrace(resource.getCurrentError());
            xae = (XAException)resource.getCurrentError();
            if (resource.getLastUser() != null) {
               lastUserStack = StackTraceUtils.throwable2StackTrace(resource.getLastUser());
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               String stackTrace = "XA operation failed : ";
               if (xae.errorCode == -4) {
                  stackTrace = stackTrace + " XAER_NOTA ";
               }

               stackTrace = stackTrace + currentErrorStack;
               JdbcDebug.JDBCCONN.debug(stackTrace);
               if (resource.getLastUser() != null) {
                  stackTrace = "Connection " + resource + " previously reserved from Pool " + resource.getPoolName() + " by: \n" + lastUserStack;
                  JdbcDebug.JDBCCONN.debug(stackTrace);
               } else {
                  stackTrace = "Connection " + resource + " used for the first time. Internal error, contact BEA Technical Support..";
                  JdbcDebug.JDBCCONN.debug(stackTrace);
               }
            }

            if (xae.errorCode != -4) {
               JDBCLogger.logErrorMessage("XA operation failed : " + currentErrorStack);
               if (resource.getLastUser() != null) {
                  JDBCLogger.logErrorMessage("Connection " + resource + " previously reserved from Pool " + resource.getPoolName() + " by: \n" + lastUserStack);
               } else {
                  JDBCLogger.logErrorMessage("Connection " + resource + " used for the first time. Internal error, contact BEA Technical Support..");
               }
            }

            this.profiler.addConnLastUsageData(currentErrorStack, lastUserStack, resource.getCurrentErrorTimestamp());
            resource.setCurrentError((Throwable)null);
            resource.setCurrentErrorTimestamp((Date)null);
         }

         resource.setLastUser(new Throwable());
      }

      if (this.config.isPinnedToThread() && !resource.isInfected()) {
         this.releasePinnedToThread(resource);
      } else {
         if (this.isOracleProxySession()) {
            resource.OracleProxyConnectionClose();
         }

         if (this.isSharedPool() && resource.isRepurposeOnRelease() && resource.getSwitchingContext() != null) {
            if (!this.isRootPartitionGroupEnabled(resource)) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("Root partition not enabled. Destroying pooled resource " + resource);
               }

               resource.destroyForFlush(false);
               this.removeResource(resource);
               return;
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.debug("switching connection to root partition (close-on-release): " + resource);
            }

            try {
               this.switchToRootPartition(resource);
               resource.setRepurposeOnRelease(false);
               super.releaseResource(resource);
            } catch (ResourceException var18) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("error switching connection to root partition: " + resource + ": " + StackTraceUtils.throwable2StackTrace(var18));
               }

               resource.destroyForFlush(false);
               this.removeResource(resource);
               return;
            }
         } else {
            super.releaseResource(resource);
         }

      }
   }

   protected boolean isRootPartitionGroupEnabled(ConnectionEnv ce) {
      return this.isEnabled();
   }

   protected void releasePinnedToThread(ConnectionEnv resource) throws ResourceException {
      ConnectionStore pinned;
      if (resource.getOwner() != Thread.currentThread()) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("Pinned: Release other thread's connection: " + resource);
            String stackTrace = resource.getStackTrace();
            stackTrace = stackTrace + StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + Thread.currentThread() + " release other's conn " + resource));
            JdbcDebug.JDBCCONN.debug(stackTrace);
         }

         if (this.createConnectionInline) {
            resource.setCleanupNeeded(true);
         } else {
            resource.cleanup();
         }

         resource.setTestNeeded(true);
         pinned = (ConnectionStore)connections.get((AuditableThread)resource.getOwner());
         if (pinned == null) {
            throw new RuntimeException(JDBCUtil.getTextFormatter().badPinnedAuditable());
         } else {
            if (this.config.isOnePinnedConnectionOnly() && !pinned.isEmpty(this)) {
               if (this.isOracleProxySession()) {
                  resource.OracleProxyConnectionClose();
               }

               super.releaseResource(resource);
            } else {
               pinned.put(this, resource);
            }

         }
      } else {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("Pinned: Release pinned connection " + resource + " with thread " + Thread.currentThread());
         }

         resource.cleanup();
         if (this.testOnRelease && resource.test() == -1) {
            this.refresh(resource);
            resource.initialize();
            if (resource.test() == -1) {
               resource.setTestNeeded(true);
            }
         }

         pinned = (ConnectionStore)connections.get();
         if (pinned == null) {
            throw new RuntimeException(JDBCUtil.getTextFormatter().badPinnedAuditable());
         } else {
            if (this.config.isOnePinnedConnectionOnly() && !pinned.isEmpty(this)) {
               if (this.isOracleProxySession()) {
                  resource.OracleProxyConnectionClose();
               }

               super.releaseResource(resource);
            } else {
               pinned.put(this, resource);
            }

         }
      }
   }

   public PooledResourceFactory initPooledResourceFactory(Properties initInfo) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):initFactory (10)");
      }

      boolean isha = HAUtil.isHADataSource(this.dsBean);

      try {
         if (DataSourceUtil.isXADataSource(this.config.getDriver(), this.classLoader) && "true".equalsIgnoreCase(initInfo.getProperty("UseXAInterface"))) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("  CP(" + this.name + "):initFactory (20)");
            }

            XAConnectionEnvFactory newXAConnEnvFactory = null;
            if (isha) {
               newXAConnEnvFactory = HAUtil.getInstance().createXAConnectionEnvFactory(this, this.appName, this.moduleName, this.compName, this.mpName, initInfo);
            } else {
               newXAConnEnvFactory = new XAConnectionEnvFactory(this, this.appName, this.moduleName, this.compName, this.mpName, initInfo);
            }

            return newXAConnEnvFactory;
         } else if (DataSourceUtil.isConnectionPoolDataSource(this.config.getDriver(), this.classLoader)) {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("  CP(" + this.name + "):initFactory (25)");
            }

            if (isha) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  JdbcDebug.JDBCRAC.debug("Creating HAPooledConnectionEnvFactory");
               }

               return HAUtil.getInstance().createPooledConnectionEnvFactory(this, this.appName, this.moduleName, this.compName, initInfo);
            } else {
               return new PooledConnectionEnvFactory(this, this.appName, this.moduleName, this.compName, initInfo);
            }
         } else {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug("  CP(" + this.name + "):initFactory (30)");
            }

            if (isha) {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  JdbcDebug.JDBCRAC.debug("Creating HAConnectionEnvFactory");
               }

               return HAUtil.getInstance().createConnectionEnvFactory(this, this.appName, this.moduleName, this.compName, initInfo);
            } else {
               return new ConnectionEnvFactory(this, this.appName, this.moduleName, this.compName, initInfo);
            }
         }
      } catch (SQLException var4) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CP(" + this.name + "):initFactory (40)");
         }

         throw new ResourceException(JDBCUtil.getTextFormatter().badFactoryInit(var4.getMessage()));
      }
   }

   public void incrementLeakedConnectionCount() {
      super.incrementNumLeaked();
   }

   public ResourcePoolProfiler getProfiler() {
      return this.profiler;
   }

   public ResourcePoolMaintainer getMaintainer() {
      return this;
   }

   public DriverInterceptor getDriverInterceptor() {
      return this.driverInterceptor != null && !JDBCHelper.getHelper().isRMISecure() ? this.driverInterceptor : null;
   }

   public String getDriverVersion() {
      return this.config.getDriver();
   }

   public boolean isEnabled() {
      return this.state == 101;
   }

   public long getPrepStmtCacheAccessCount() {
      long result = 0L;
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            result += ((ConnectionEnv)((ConnectionEnv)resList[i])).getPrepStmtCacheAccessCount();
         }
      }

      return result;
   }

   public long getPrepStmtCacheAddCount() {
      long result = 0L;
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            result += ((ConnectionEnv)((ConnectionEnv)resList[i])).getPrepStmtCacheAddCount();
         }
      }

      return result;
   }

   public long getPrepStmtCacheDeleteCount() {
      long result = 0L;
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            result += ((ConnectionEnv)((ConnectionEnv)resList[i])).getPrepStmtCacheDeleteCount();
         }
      }

      return result;
   }

   public int getPrepStmtCacheCurrentSize() {
      int result = 0;
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            result += ((ConnectionEnv)((ConnectionEnv)resList[i])).getPrepStmtCacheCurrentSize();
         }
      }

      return result;
   }

   public int getPrepStmtCacheHitCount() {
      int result = 0;
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            result += ((ConnectionEnv)((ConnectionEnv)resList[i])).getPrepStmtCacheHits();
         }
      }

      return result;
   }

   public int getPrepStmtCacheMissCount() {
      int result = 0;
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            result += ((ConnectionEnv)((ConnectionEnv)resList[i])).getPrepStmtCacheMisses();
         }
      }

      return result;
   }

   public void clearStatementCache() throws Exception {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "clearStatementCache");
      JDBCLogger.logClearStmtCache(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      PooledResource[] resList = this.getResources();
      if (resList != null) {
         for(int i = 0; i < resList.length; ++i) {
            ((ConnectionEnv)((ConnectionEnv)resList[i])).clearCache();
         }
      }

   }

   public void shrink() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "shrink");
      JDBCLogger.logShrink(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      super.shrink();
   }

   public void reset() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "reset");
      JDBCLogger.logReset(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      if (this.createConnectionInline) {
         synchronized(this) {
            ListIterator listIter = this.available.listIterator(0);

            while(listIter.hasNext()) {
               ((ConnectionEnv)((ConnectionEnv)listIter.next())).setRefreshNeeded(true);
            }

            Iterator iter = this.reserved.iterator();

            while(iter.hasNext()) {
               ((ConnectionEnv)((ConnectionEnv)iter.next())).setRefreshNeeded(true);
            }
         }
      } else {
         this.suspend(false, 0);
         super.refresh();
         this.resume();
      }

   }

   public void suspendExternal(int operationSecs) throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "suspend");
      JDBCLogger.logSuspending(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.suspend(false, operationSecs);
   }

   public void forceSuspendExternal() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "forceSuspend");
      JDBCLogger.logForceSuspending(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.forceSuspend(false);
   }

   public void resumeExternal() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "resume");
      JDBCLogger.logResuming(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.resume();
   }

   public void startExternal() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "start");
      JDBCLogger.logStarting(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.start((Object)null);
      this.resume();
      this.registerResource();
   }

   public void shutdownExternal(int operationSecs) throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "shutdown");
      JDBCLogger.logShutting(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.suspend(true, operationSecs);
      this.shutdown();
      this.unregisterResource();
   }

   public void forceShutdownExternal() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "forceShutdown");
      JDBCLogger.logForceShutting(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.forceSuspend(true);
      this.shutdown();
      this.unregisterResource();
   }

   private void unregisterResource() throws ResourceException {
      if (this.resFactory instanceof XAConnectionEnvFactory) {
         XAConnectionEnvFactory xacef = (XAConnectionEnvFactory)this.resFactory;

         try {
            xacef.unregisterResource(false);
         } catch (SystemException var3) {
            throw new ResourceException(var3);
         }
      }

   }

   private void registerResource() throws ResourceException {
      if (this.resFactory instanceof XAConnectionEnvFactory) {
         XAConnectionEnvFactory xacef = (XAConnectionEnvFactory)this.resFactory;

         try {
            xacef.registerResource();
         } catch (SystemException var3) {
            throw new ResourceException(var3);
         }
      }

   }

   public void disableDroppingUsers() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "disableDroppingUsers");
      JDBCLogger.logDisableDropping(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.forceSuspend(false);
   }

   public void disableFreezingUsers() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "disableFreezingUsers");
      JDBCLogger.logDisableFreezing(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      this.suspend(false);
   }

   public void enable() throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "enable");
      JDBCLogger.logEnable(this.name, SubjectUtils.getUsername(SecurityServiceManager.getCurrentSubject(KERNELID)));
      super.resume();
   }

   public Properties getProperties() throws ResourceException {
      if (this.am != null) {
         JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "getProperties");
      }

      return this.config.getDriverProperties();
   }

   public boolean poolExists(String poolName) throws ResourceException {
      JDBCUtil.checkPermission((AuthenticatedSubject)null, KERNELID, this.am, "ConnectionPool", this.name, this.appName, this.moduleName, this.compName, "admin", "poolExists");
      return ConnectionPoolManager.poolExists(poolName, (String)null, (String)null, (String)null);
   }

   public void resetStatistics() {
      super.resetStatistics();
      this.repurposeCount.set(0L);
      this.failedRepurposeCount.set(0L);
      this.resolvedAsCommittedTotalCount.set(0L);
      this.resolvedAsNotCommittedTotalCount.set(0L);
      this.unresolvedTotalCount.set(0L);
      this.commitOutcomeRetryTotalCount.set(0L);
      if (this.closedConnectionReplayStatistics != null) {
         this.closedConnectionReplayStatistics.initialize(0L);
      }

      PooledResource[] prs = this.getResources();
      if (prs != null) {
         PooledResource[] var2 = prs;
         int var3 = prs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PooledResource pr = var2[var4];
            ((ConnectionEnv)pr).resetStatistics();
         }
      }

   }

   public List getAvailableMatching(PooledResourceInfo pri) {
      return this.available instanceof IGroupingPooledResourceLinkedList ? ((IGroupingPooledResourceLinkedList)this.available).getSubList(pri) : null;
   }

   public List getReservedMatching(PooledResourceInfo pri) {
      return this.reserved instanceof IGroupingPooledResourceSet ? ((IGroupingPooledResourceSet)this.reserved).getSubList(pri) : null;
   }

   public void disableCountOfRefreshFailuresTillDisable() {
      this.countToDisablePool = 0;
   }

   public void removeConnection(ConnectionEnv cc) {
      synchronized(this) {
         if (this.reserved.remove(cc)) {
            this.decrementGroupCapacities(cc);
         } else {
            this.dumpPool((PrintWriter)null);
         }

      }
   }

   public boolean removeResource(ConnectionEnv cc) {
      synchronized(this) {
         if (this.available.remove(cc)) {
            this.decrementGroupCapacities(cc);
            return true;
         } else if (this.reserved.remove(cc)) {
            this.decrementGroupCapacities(cc);
            return true;
         } else if (this.dead.remove(cc)) {
            this.decrementGroupCapacities(cc);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean isRemoveInfectedConnectionEnabled() {
      return this.config.isRemoveInfectedConnectionEnabled();
   }

   public String getResourceName() {
      return JDBCUtil.getDecoratedName(this.name, this.appName, this.moduleName, this.compName);
   }

   public int getInfo(int which) {
      switch (which) {
         case -1002:
            return this.available.size();
         case -1001:
            return this.getCurrCapacity();
         default:
            return -1000;
      }
   }

   public JDBCDataSourceBean getJDBCDataSource() {
      return this.dsBean;
   }

   public void setJDBCDataSource(JDBCDataSourceBean dsBean) {
      this.dsBean = dsBean;
      this.config.setJDBCDataSourceBean(dsBean);
   }

   public JDBCResourceFactory getResourceFactory() {
      return (JDBCResourceFactory)this.resFactory;
   }

   public int getXARetryDurationSeconds() {
      return this.config.getXaRetryDurationSeconds();
   }

   public boolean areConnsBeingTested() {
      return this.testOnReserve || this.testOnRelease;
   }

   public boolean getTestOnReserve() {
      return this.testOnReserve;
   }

   boolean getTestOnRelease() {
      return this.testOnRelease;
   }

   boolean getTestOnCreate() {
      return this.testOnCreate;
   }

   public int getTestSeconds() {
      return this.testSecs;
   }

   public String getName() {
      return this.name;
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

   public int getInactiveSeconds() {
      return this.inactiveSecs;
   }

   void clearUserCache() {
      synchronized(this.userCache) {
         this.userCache.clear();
      }
   }

   void doHarvest() {
      if (this.connectionHarvestTriggerCount != -1) {
         if (this.getNumAvailable() <= this.connectionHarvestTriggerCount) {
            if (this.getCurrCapacity() >= this.getMaxCapacity()) {
               int harvestCnt = 0;
               ConnectionEnv[] harvestResources;
               synchronized(this) {
                  int resCnt = this.reserved.size();
                  ConnectionEnv[] rlist = (ConnectionEnv[])((ConnectionEnv[])this.reserved.toArray(new ConnectionEnv[resCnt]));
                  Arrays.sort(rlist, new LastUseComparator());
                  harvestResources = new ConnectionEnv[resCnt];

                  for(int i = 0; i < resCnt; ++i) {
                     ConnectionEnv res = rlist[i];

                     try {
                        if (!res.isConnectionHarvestableAndLock()) {
                           continue;
                        }
                     } catch (SQLException var12) {
                        continue;
                     }

                     harvestResources[harvestCnt++] = res;
                     if (harvestCnt == this.connectionHarvestMaxCount) {
                        break;
                     }
                  }
               }

               for(int i = 0; i < harvestCnt; ++i) {
                  try {
                     if (harvestResources[i].connectionHarvestingCallback != null) {
                        try {
                           harvestResources[i].connectionHarvestingCallback.cleanup();
                        } catch (Throwable var10) {
                           JDBCLogger.logExceptionFromConnectionHarvestingCallback(var10.getMessage());
                           if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                              String stackTrace = StackTraceUtils.throwable2StackTrace(var10);
                              JdbcDebug.JDBCCONN.debug(stackTrace);
                           }
                        }
                     }

                     if (harvestResources[i].connectionHarvestedCallback != null) {
                        harvestResources[i].connectionHarvestedCallback.connectionHarvested();
                     }

                     this.releaseResource(harvestResources[i]);
                  } catch (Exception var11) {
                  }
               }

            }
         }
      }
   }

   public void setMaintenanceFrequencySeconds(int newVal) {
      super.setMaintenanceFrequencySeconds(newVal);
   }

   public void performMaintenance() {
      if (((DataSourceConnectionPoolConfig)this.config).getSecurityCacheTimeoutSeconds() > 0 && this.userCacheTime.addAndGet(this.maintSecs) >= ((DataSourceConnectionPoolConfig)this.config).getSecurityCacheTimeoutSeconds()) {
         this.userCacheTime.set(0);
         this.clearUserCache();
      }

      if (this.config.getHarvestingFrequencySeconds() > 0 && this.harvestTime.addAndGet(this.maintSecs) >= this.config.getHarvestingFrequencySeconds()) {
         this.harvestTime.set(0);
         this.doHarvest();
      }

      if (this.profiler.isResourceLeakProfilingEnabled() && this.profiler.getProfileConnectionLeakTimeoutSeconds() > 0 && this.leakProfileTime.addAndGet(this.maintSecs) >= this.profiler.getProfileConnectionLeakTimeoutSeconds()) {
         this.leakProfileTime.set(0);
         synchronized(this) {
            Iterator it = this.reserved.iterator();

            while(it.hasNext()) {
               ConnectionEnv res = (ConnectionEnv)it.next();
               if (!res.profileRecordLogged) {
                  if (res.getUsed()) {
                     res.setUsed(false);
                  } else {
                     this.profiler.addLeakData(res);
                     res.profileRecordLogged = true;
                  }
               }
            }
         }
      }

      if (this.suspendShuttingDown && this.available.size() > 0) {
         PooledResource[] resources = null;
         synchronized(this) {
            int numAvlToRemove = this.available.size();
            if (numAvlToRemove > 0) {
               resources = new PooledResource[numAvlToRemove];

               for(int lcv = 0; lcv < numAvlToRemove; ++lcv) {
                  resources[lcv] = (PooledResource)this.available.removeLast();
               }
            }
         }

         if (resources != null) {
            for(int lcv = 0; lcv < resources.length; ++lcv) {
               this.destroyResource(resources[lcv]);
            }
         }
      }

   }

   private void setXAMultiPoolName(String myName) {
      this.mpName = myName;
   }

   public void setTestTableName(String newTest) {
      JDBCResourceFactory factory = this.getResourceFactory();
      String old = factory.getTestQuery();
      if (old == null || !old.equals(newTest)) {
         ConnectionEnv cc = null;

         try {
            cc = this.reserveInternal(-2);
            JDBCLogger.logTestNameChange(this.name, newTest);
            synchronized(factory) {
               factory.clearTestValidation();
               factory.initializeTest(cc, newTest);
            }

            synchronized(this) {
               ListIterator listIter = this.available.listIterator(0);

               while(listIter.hasNext()) {
                  ((ConnectionEnv)((ConnectionEnv)listIter.next())).clearTestStatement();
               }

               Iterator iter = this.reserved.iterator();

               while(listIter.hasNext()) {
                  ((ConnectionEnv)((ConnectionEnv)listIter.next())).clearTestStatement();
               }
            }
         } catch (ResourceException var21) {
            JDBCLogger.logErrorMessage(var21.getMessage());
         } finally {
            try {
               if (cc != null) {
                  this.release(cc);
               }
            } catch (Exception var18) {
            }

         }

      }
   }

   private boolean isStartupCriticalBoot() {
      return this.config.isStartupCritical() && !JDBCHelper.getHelper().isAdminServer() && JDBCHelper.getHelper().isServerStarting() && !JDBCHelper.getHelper().isServerStarted();
   }

   protected boolean isStartupCreateRetryDisabled() {
      return this.isStartupCriticalBoot();
   }

   protected int getStartInitialCapacity() {
      return this.initialCapacity == 0 && this.isStartupCriticalBoot() ? 1 : this.initialCapacity;
   }

   private void doStart() throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CP(" + this.name + "):doStart (10) ");
      }

      try {
         super.start(this.config.getPoolProperties());
      } catch (ResourceException var2) {
         if (this.isStartupCriticalBoot()) {
            JDBCHelper.getHelper().setServerFailedState(new ResourceException(JDBCUtil.getTextFormatter().criticalDataSourceFailed(this.name) + ": " + var2.getLocalizedMessage()));
         }

         throw var2;
      }

      this.profiler.setProfileType(this.config.getProfileType());
      this.profiler.setProfileConnectionLeakTimeoutSeconds(this.config.getProfileConnectionLeakTimeoutSeconds());
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" < CP(" + this.name + "):doStart (100)");
      }

   }

   private void initSecurity() {
      if (!KernelStatus.isJ2eeClient()) {
         this.am = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNELID, "weblogicDEFAULT", ServiceType.AUTHORIZE);
         if (this.am == null) {
            throw new RuntimeException(JDBCUtil.getTextFormatter().unavailableSecurity());
         } else {
            String m = this.moduleName;
            if (this.compName != null) {
               m = m + "@" + this.compName;
            }

            this.cachedResvRes = new JDBCResource(this.appName, m, "ConnectionPool", this.name, "reserve");

            try {
               if (this.config != null && this.config.getDriverProperties() != null && (this.config.isIdentityBasedConnectionPoolingEnabled() || this.config.getDriverProperties().containsKey("IMPERSONATE"))) {
                  this.getConnectionInfoForUser = true;
               }
            } catch (ResourceException var3) {
            }

         }
      }
   }

   public Vector getDBMSIdentity(AuthenticatedSubject user) {
      if (user == null) {
         return null;
      } else {
         Vector creds = null;

         try {
            CredentialManager cm = (CredentialManager)SecurityServiceManager.getSecurityService(KERNELID, "weblogicDEFAULT", ServiceType.CREDENTIALMANAGER);
            if (cm != null) {
               if (this.resource == null) {
                  String m = this.moduleName;
                  if (this.compName != null) {
                     m = m + "@" + this.compName;
                  }

                  this.resource = new JDBCResource(this.appName, m, "ConnectionPool", this.name, "reserve");
               }

               Object[] objs = cm.getCredentials(KERNELID, user, this.resource, (ContextHandler)null, "weblogic.UserPassword");
               if (objs != null) {
                  creds = new Vector();
                  Object[] var5 = objs;
                  int var6 = objs.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     Object obj = var5[var7];
                     creds.add(obj);
                  }
               }
            }
         } catch (Throwable var9) {
         }

         return creds;
      }
   }

   public Object setDBMSIdentity(Object vendorObject, Object id) throws Throwable {
      if (vendorObject != null && id != null) {
         if (this.clientInfo != null) {
            ((Connection)vendorObject).setClientInfo(this.clientInfo, (String)id);
            return null;
         } else if (this.setDBMSIdentityMethod != null) {
            this.setDBMSIdentityMethod.invoke(vendorObject, id);
            return null;
         } else {
            Object ret = null;
            Class cls = vendorObject.getClass();

            try {
               if (cls.getName().indexOf("oracle") != -1) {
                  cls.getClassLoader().loadClass("oracle.jdbc.LogicalTransactionId");
                  ((Connection)vendorObject).setClientInfo("OCSID.CLIENTID", (String)id);
                  this.clientInfo = "OCSID.CLIENTID";
                  this.config.setCredentialMappingEnabled(true);
                  return null;
               }
            } catch (Throwable var8) {
            }

            try {
               this.setDBMSIdentityMethod = vendorObject.getClass().getMethod("setClientIdentifier", String.class);
               this.setDBMSIdentityMethod = WrapperClassFile.getInterfaceMethod(this.setDBMSIdentityMethod);
               if (this.setDBMSIdentityMethod != null) {
                  this.clearDBMSIdentityMethod = vendorObject.getClass().getMethod("clearClientIdentifier", String.class);
                  this.clearDBMSIdentityMethod = WrapperClassFile.getInterfaceMethod(this.clearDBMSIdentityMethod);
               }

               this.config.setCredentialMappingEnabled(true);
            } catch (Throwable var7) {
               this.config.setCredentialMappingEnabled(false);
            }

            if (cls.getName().indexOf(".db2.") != -1) {
               try {
                  ((Connection)vendorObject).setClientInfo("ClientUser", (String)id);
                  this.clientInfo = "ClientUser";
                  this.config.setCredentialMappingEnabled(true);
                  return null;
               } catch (Exception var9) {
               }
            }

            if (this.setDBMSIdentityMethod == null) {
               try {
                  this.setDBMSIdentityMethod = vendorObject.getClass().getMethod("setDB2ClientUser", String.class);
                  this.setDBMSIdentityMethod = WrapperClassFile.getInterfaceMethod(this.setDBMSIdentityMethod);
                  if (this.setDBMSIdentityMethod != null) {
                     Method getDBMSIdentityMethod = vendorObject.getClass().getMethod("getDB2ClientUser", (Class[])null);
                     getDBMSIdentityMethod = WrapperClassFile.getInterfaceMethod(getDBMSIdentityMethod);
                     if (getDBMSIdentityMethod != null) {
                        ret = getDBMSIdentityMethod.invoke(vendorObject, (Object[])null);
                     }
                  }

                  this.config.setCredentialMappingEnabled(true);
               } catch (Throwable var6) {
                  this.config.setCredentialMappingEnabled(false);
               }
            }

            if (this.setDBMSIdentityMethod != null) {
               this.setDBMSIdentityMethod.invoke(vendorObject, id);
               return ret;
            } else {
               throw new SQLException(JDBCUtil.getTextFormatter().identityNotSupported());
            }
         }
      } else {
         return null;
      }
   }

   public void clearDBMSIdentity(Object vendorObject, Object id, Object initId) throws Throwable {
      if (this.clientInfo != null) {
         ((Connection)vendorObject).setClientInfo(this.clientInfo, "");
      } else if (this.clearDBMSIdentityMethod != null) {
         this.clearDBMSIdentityMethod.invoke(vendorObject, id);
      } else {
         if (this.setDBMSIdentityMethod == null) {
            throw new SQLException(JDBCUtil.getTextFormatter().identityNotSupported());
         }

         this.setDBMSIdentityMethod.invoke(vendorObject, initId);
      }

   }

   protected void setupDriverInterceptor() {
      String className = this.dsBean.getJDBCConnectionPoolParams().getDriverInterceptor();
      if (className != null) {
         Object classObject;
         try {
            classObject = DataSourceUtil.loadDriver(className);
         } catch (Exception var4) {
            JDBCLogger.logInterceptorClassLoadFailed(className, var4.toString());
            return;
         }

         if (classObject instanceof DriverInterceptor) {
            JDBCLogger.logInterceptorClassLoaded(className);
            this.driverInterceptor = (DriverInterceptor)classObject;
         } else {
            JDBCLogger.logInterceptorClassBadType(className, "weblogic.jdbc.extensions.DriverInterceptor");
         }
      }
   }

   protected void setupConnectionLabelingCallback() {
      String className = this.dsBean.getJDBCConnectionPoolParams().getConnectionLabelingCallback();
      if (className != null) {
         Object classObject;
         try {
            classObject = DataSourceUtil.loadDriver(className);
         } catch (Exception var4) {
            JDBCLogger.logConnectionLabelingCallbackClassLoadFailed(className, var4.toString());
            return;
         }

         if (classObject instanceof ConnectionLabelingCallback) {
            JDBCLogger.logConnectionLabelingCallbackClassLoaded(className);
            this.labelingCallback = (ConnectionLabelingCallback)classObject;
         } else {
            JDBCLogger.logConnectionLabelingCallbackClassBadType(className, ConnectionLabelingCallback.class.getName());
         }
      }
   }

   protected void setupConnectionHarvesting() {
      int max = this.dsBean.getJDBCConnectionPoolParams().getMaxCapacity();
      int cnt = this.dsBean.getJDBCConnectionPoolParams().getConnectionHarvestMaxCount();
      if (cnt >= 1 && cnt <= max) {
         this.connectionHarvestMaxCount = cnt;
         cnt = this.dsBean.getJDBCConnectionPoolParams().getConnectionHarvestTriggerCount();
         if (cnt >= -1 && cnt <= max) {
            this.connectionHarvestTriggerCount = cnt;
         } else {
            throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidTriggerCount(cnt, this.maxCapacity));
         }
      } else {
         throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidHarvestMaxCount(max));
      }
   }

   protected void setupConnectionInitializationCallback() {
      this.replayInitiationTimeout = this.dsBean.getJDBCOracleParams().getReplayInitiationTimeout();
      String className = this.dsBean.getJDBCOracleParams().getConnectionInitializationCallback();
      if (className != null) {
         Object classObject;
         try {
            classObject = DataSourceUtil.loadDriver(className);
         } catch (Exception var4) {
            JDBCLogger.logConnectionInitializationCallbackClassLoadFailed(className, var4.toString());
            return;
         }

         if (classObject instanceof ConnectionInitializationCallback) {
            JDBCLogger.logConnectionInitializationCallbackClassLoaded(className);
            this.initializationCallback = (ConnectionInitializationCallback)classObject;
         } else {
            JDBCLogger.logConnectionInitializationCallbackClassBadType(className, ConnectionInitializationCallback.class.getName());
         }
      }
   }

   protected PooledResource refreshOldestAvailResource(PooledResourceInfo resInfo) throws ResourceException {
      PooledResource ret = (PooledResource)((PooledResource)super.available.removeLast());
      if (ret != null) {
         ret.setPooledResourceInfo((PooledResourceInfo)null);
      }

      return ret;
   }

   public void setDataSource(DataSource ds) {
      this.jtaDataSource = ds;
   }

   public DataSource getJTADataSource() {
      return this.jtaDataSource;
   }

   public int getStateAsInt() {
      return this.state;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public boolean isCredentialMappingEnabled() {
      return this.config.isCredentialMappingEnabled();
   }

   public boolean isCreateConnectionInline() {
      return this.config.isCreateConnectionInline();
   }

   public boolean isIdentityBasedConnectionPoolingEnabled() {
      return this.config.isIdentityBasedConnectionPoolingEnabled();
   }

   public boolean isNativeXA() {
      return this.config.isNativeXA();
   }

   public boolean isOracleOptimizeUtf8Conversion() {
      return this.oracleOptimizeUtf8Conversion;
   }

   public void setOracleOptimizeUtf8Conversion(boolean flag) {
      this.oracleOptimizeUtf8Conversion = flag;
   }

   public void setWrapTypes(boolean flag) {
      this.wrapTypes = flag;
   }

   public boolean isWrapTypes() {
      return !this.wrapJdbc ? false : this.wrapTypes;
   }

   public void setWrapJdbc(boolean flag) {
      this.wrapJdbc = flag;
   }

   public boolean isWrapJdbc() {
      return this.wrapJdbc;
   }

   public String getURL() {
      return this.config.getURL();
   }

   public ConnectionPoolConfig getConfig() {
      return this.config;
   }

   public ConnectionLabelingCallback getLabelingCallback() {
      return this.labelingCallback;
   }

   public void setLabelingCallback(ConnectionLabelingCallback labelingCallback) throws SQLException {
      this.labelingCallback = labelingCallback;
   }

   public ConnectionInitializationCallback getInitializationCallback() {
      return this.initializationCallback;
   }

   public void setInitializationCallback(ConnectionInitializationCallback initializationCallback) throws SQLException {
      this.initializationCallback = initializationCallback;
   }

   int getConnectionHarvestMaxCount() {
      return this.connectionHarvestMaxCount;
   }

   public void setConnectionHarvestMaxCount(int cnt) throws SQLException {
      if (cnt >= 1 && cnt <= this.maxCapacity) {
         this.connectionHarvestMaxCount = cnt;
      } else {
         throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidHarvestMaxCount(this.maxCapacity));
      }
   }

   public int getConnectionHarvestTriggerCount() {
      return this.connectionHarvestTriggerCount;
   }

   public void setConnectionHarvestTriggerCount(int cnt) throws SQLException {
      if (cnt >= -1 && cnt <= this.maxCapacity) {
         boolean wason = this.connectionHarvestTriggerCount != -1;
         boolean ison = cnt != -1;
         if (wason != ison) {
            synchronized(this) {
               ListIterator listIter = this.available.listIterator(0);

               while(listIter.hasNext()) {
                  ((ConnectionEnv)((ConnectionEnv)listIter.next())).setConnectionHarvestable(ison);
               }
            }
         }

         this.connectionHarvestTriggerCount = cnt;
      } else {
         throw new IllegalArgumentException(JDBCUtil.getTextFormatter().invalidTriggerCount(cnt, this.maxCapacity));
      }
   }

   public long getRepurposeCount() {
      return this.repurposeCount.get();
   }

   public long getFailedRepurposeCount() {
      return this.failedRepurposeCount.get();
   }

   public long getResolvedAsCommittedTotalCount() {
      return this.resolvedAsCommittedTotalCount.get();
   }

   public void incrementResolvedAsCommittedTotalCount() {
      this.resolvedAsCommittedTotalCount.incrementAndGet();
   }

   public long getResolvedAsNotCommittedTotalCount() {
      return this.resolvedAsNotCommittedTotalCount.get();
   }

   public void incrementResolvedAsNotCommittedTotalCount() {
      this.resolvedAsNotCommittedTotalCount.incrementAndGet();
   }

   public long getUnresolvedTotalCount() {
      return this.unresolvedTotalCount.get();
   }

   public void incrementUnresolvedTotalCount() {
      this.unresolvedTotalCount.incrementAndGet();
   }

   public long getCommitOutcomeRetryTotalCount() {
      return this.commitOutcomeRetryTotalCount.get();
   }

   public void incrementCommitOutcomeRetryTotalCount() {
      this.commitOutcomeRetryTotalCount.incrementAndGet();
   }

   protected boolean isOracleProxySession() {
      return this.dsBean != null && this.dsBean.getJDBCOracleParams() != null ? this.dsBean.getJDBCOracleParams().isOracleProxySession() : false;
   }

   private boolean isUseDatabaseCredentials() {
      return this.dsBean != null && this.dsBean.getJDBCOracleParams() != null ? this.dsBean.getJDBCOracleParams().isUseDatabaseCredentials() : false;
   }

   protected void processOracleProxySession(AuthenticatedSubject user, String username, String password, ConnectionEnv ce, boolean internal) throws ResourceException {
      if (this.config.isIdentityBasedConnectionPoolingEnabled()) {
         if (this.isOracleProxySession()) {
            throw new ResourceException(JDBCUtil.getTextFormatter().badIdentityProxy());
         }
      } else {
         if (this.isUseDatabaseCredentials() && username != null) {
            this.openOracleProxySession(username, password, ce);
            ce.saveUser = username;
            if (password != null) {
               ce.savePassword = password.toCharArray();
            }
         } else if (this.isOracleProxySession()) {
            if (user != null && this.dsBean != null) {
               JDBCPropertyBean proxyAUProp = this.dsBean.getJDBCDriverParams().getProperties().lookupProperty("weblogic.jdbc.ProxyUseAuthUser");
               if (proxyAUProp != null) {
                  String v = proxyAUProp.getValue();
                  if (v != null && v.equals("true")) {
                     Set set = user.getPrincipals();
                     Iterator itr = set.iterator();
                     if (itr.hasNext()) {
                        Object o = itr.next();
                        username = o.toString();
                        this.openOracleProxySession(username, (String)null, ce);
                        ce.saveUser = username;
                        ce.savePassword = null;
                        return;
                     }
                  }
               }
            }

            ConnectionInfo ci = this.getConnectionInfo(user, internal);
            if (ci != null && ci.getUsername() != null) {
               this.openOracleProxySession(ci.getUsername(), ci.getPassword(), ce);
               ce.saveUser = ci.getUsername();
               if (ci.getPassword() != null) {
                  ce.savePassword = ci.getPassword().toCharArray();
               } else {
                  ce.savePassword = null;
               }
            }
         }

      }
   }

   private void openOracleProxySession(String username, String password, ConnectionEnv ce) throws ResourceException {
      if (!ce.conn.hasOracleOpenProxySession) {
         try {
            ConnectionPoolManager.release(ce);
         } catch (ResourceException var7) {
            throw new ResourceException(JDBCUtil.getTextFormatter().badConnectionRelease(var7.getMessage()), var7);
         }

         throw new ResourceException(JDBCUtil.getTextFormatter().badProxyNotOracle());
      } else {
         try {
            Properties prop = new Properties();
            prop.put(ce.conn.proxyUserName, username);
            if (password != null) {
               prop.put(ce.conn.proxyUserPassword, password);
            }

            ce.OracleOpenProxySession(ce.conn.proxyTypeUserName, prop);
         } catch (SQLException var8) {
            try {
               ConnectionPoolManager.release(ce);
            } catch (ResourceException var6) {
               throw new ResourceException(JDBCUtil.getTextFormatter().badProxyRelease(var6.getMessage()), var8);
            }

            throw new ResourceException(JDBCUtil.getTextFormatter().errorProxy(), var8);
         }
      }
   }

   public Class getDriverClass() throws ClassNotFoundException {
      if (this.dsBean == null) {
         return null;
      } else {
         String driverName = this.dsBean.getJDBCDriverParams().getDriverName();
         Class driverClass = DataSourceUtil.loadDriverClass(driverName, this.classLoader);
         return driverClass;
      }
   }

   public void replayInitialize(Connection connection) throws SQLException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         JdbcDebug.JDBCRAC.debug("replayInitialize() conn=" + connection);
      }

      if (connection != null) {
         ConnectionEnv pooledConnection = this.getCachedPooledResource(connection);
         if (pooledConnection != null) {
            this.replayUpdateConnectionState(pooledConnection);
            this.replayInvokeInitializeCallbacks(pooledConnection);
         }

      }
   }

   protected void replayUpdateConnectionState(ConnectionEnv pooledConnection) throws SQLException {
      try {
         this.updateGroups(pooledConnection);
         pooledConnection.replayAutoCommit();
      } catch (ResourceException var3) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            JdbcDebug.JDBCRAC.debug("replayInitialize() failed to re-init connection " + pooledConnection + ", " + StackTraceUtils.throwable2StackTrace(var3));
         }

         throw new SQLException(var3);
      }
   }

   protected void replayInvokeInitializeCallbacks(ConnectionEnv pooledConnection) throws SQLException {
      if (pooledConnection != null) {
         try {
            ++pooledConnection.replayAttemptCount;
            this.connectionCallbacks(pooledConnection);
         } catch (SQLException var3) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("replayInitialize() failed to re-init connection " + pooledConnection + ", " + StackTraceUtils.throwable2StackTrace(var3));
            }

            throw var3;
         }
      }
   }

   public void connectionCallbacks(ConnectionEnv pooledConnection) throws SQLException {
      if (pooledConnection != null) {
         if (this.isOracleProxySession()) {
            try {
               String p = null;
               if (pooledConnection.savePassword != null) {
                  p = new String(pooledConnection.savePassword);
               }

               this.openOracleProxySession(pooledConnection.saveUser, p, pooledConnection);
            } catch (Exception var4) {
               throw new SQLException(var4.getMessage(), var4);
            }
         }

         if (pooledConnection.saveClientId != null) {
            pooledConnection.clientID = pooledConnection.saveClientId;
            pooledConnection.setIdentity();
         }

         if (this.labelingCallback != null) {
            ResourceCleanupHandler rch = pooledConnection.getResourceCleanupHandler();
            if (!(rch instanceof LabelableConnection)) {
               throw new SQLException(JDBCUtil.getTextFormatter().badProxyConnection(pooledConnection.conn.jconn.toString()));
            }

            pooledConnection.labelConfigure(this.labelingCallback, pooledConnection.getLabels(), (LabelableConnection)rch);
         } else if (this.initializationCallback != null) {
            try {
               pooledConnection.connectionInitialize(this.initializationCallback);
            } catch (Exception var3) {
               throw new SQLException(JDBCUtil.getTextFormatter().failedCICallback(var3.getMessage()));
            }
         }
      }

   }

   public void markConnectionGood(ConnectionEnv pooledConnection) {
   }

   private final void debug(String msg) {
      JdbcDebug.JDBCCONN.debug(msg);
   }

   void beginRequest(OracleHelper oh, ConnectionEnv resource) throws ResourceException {
      if (resource != null && resource.conn != null) {
         if (this.config.isInvokeBeginEndRequest()) {
            try {
               if (oh != null) {
                  this.oracleHelper.replayBeginRequest(resource, this.replayInitiationTimeout);
               } else {
                  if (!resource.conn.isBeginEndRequestSupported()) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        this.debug("beginRequest not invoked: invokeBeginEndRequest enabled but Connection.beginRequest() not supported by driver");
                     }

                     return;
                  }

                  resource.conn.invokeBeginRequest(resource.conn.jconn);
               }
            } catch (SQLException var6) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("beginRequest failed: " + StackTraceUtils.throwable2StackTrace(var6));
               }

               try {
                  ConnectionPoolManager.release(resource);
               } catch (ResourceException var5) {
                  throw new ResourceException(JDBCUtil.getTextFormatter().failedBeginRequest(var5.getMessage()), var6);
               }

               throw new ResourceException(JDBCUtil.getTextFormatter().failedBeginRequest2(), var6);
            }
         } else if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            this.debug("beginRequest not invoked: invokeBeginEndRequest false");
         }

      } else {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            this.debug("beginRequest not invoked: no connection");
         }

      }
   }

   void endRequest(OracleHelper oh, ConnectionEnv resource) throws ResourceException {
      if (resource != null && resource.conn != null) {
         if (this.config.isInvokeBeginEndRequest()) {
            try {
               if (oh != null) {
                  oh.replayEndRequest(resource);
               } else {
                  if (!resource.conn.isBeginEndRequestSupported()) {
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        this.debug("endRequest not invoked: invokeBeginEndRequest enabled but Connection.endRequest() not supported by driver");
                     }

                     return;
                  }

                  resource.conn.invokeEndRequest(resource.conn.jconn);
               }
            } catch (SQLException var8) {
               SQLException sqle = var8;

               try {
                  if (oh != null) {
                     oh.replayEndRequest(resource);
                  } else {
                     resource.conn.invokeEndRequest(resource.conn.jconn);
                  }

                  JDBCLogger.logEndRequestFailed(sqle.getMessage());
               } catch (Exception var7) {
                  try {
                     resource.forceDestroy();
                  } catch (Exception var6) {
                  }

                  if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     this.debug("endRequest failed: " + StackTraceUtils.throwable2StackTrace(var8));
                  }

                  throw new ResourceException(JDBCUtil.getTextFormatter().badEndRequest(), var8);
               }
            }
         } else if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            this.debug("beginRequest not invoked: invokeBeginEndRequest false");
         }

      } else {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            this.debug("endRequest not invoked: no connection");
         }

      }
   }

   public int getReplayInitiationTimeout() {
      return this.replayInitiationTimeout;
   }

   public void setReplayInitiationTimeout(int timeout) throws SQLException {
      if (timeout < 0) {
         timeout = 0;
      }

      this.replayInitiationTimeout = timeout;
   }

   protected void preventSelfResuming() {
      this.resumeInternallyAllowed = false;
   }

   public ReserveReleaseInterceptor getReserveReleaseInterceptor() {
      return this;
   }

   public void onReserve(PooledResource res) throws ResourceException {
      ConnectionEnv ce = (ConnectionEnv)res;
      if (ce.drcpEnabled) {
         ce.OracleAttachServerConnection();
      }

      if (this.isSharedPool()) {
         this.repurposeConnection(ce);
      }

   }

   public void onRelease(PooledResource res) throws ResourceException {
      ConnectionEnv ce = (ConnectionEnv)res;
      if (ce.drcpEnabled) {
         try {
            ce.OracleDetachServerConnection();
         } catch (Exception var4) {
            ce.setInfected(true);
            ce.setRefreshNeeded(true);
            this.removeConnection(ce);
            ce.forceDestroy();
            throw new ResourceException("failed to detach returned DRCP connection: " + var4.getMessage());
         }
      }

   }

   private void repurposeConnection(ConnectionEnv ce) throws ResourceException {
      boolean onReservedList = false;
      synchronized(this) {
         onReservedList = this.reserved.remove(ce);
         if (onReservedList) {
            ++this.beingProcessed;
         }
      }

      boolean var17 = false;

      try {
         var17 = true;
         SwitchingContext requestingSwitchingContext = SwitchingContextManager.getInstance().get();
         SwitchingContext connectionSwitchingContext = ce.getSwitchingContext();
         if (requestingSwitchingContext == null && connectionSwitchingContext != null && this.rootSwitchingContext != null && !this.rootSwitchingContext.equals(connectionSwitchingContext)) {
            requestingSwitchingContext = this.rootSwitchingContext;
         }

         if (requestingSwitchingContext != null) {
            if (!requestingSwitchingContext.equals(connectionSwitchingContext)) {
               this.repurposeCount.incrementAndGet();

               try {
                  if (connectionSwitchingContext == null || requestingSwitchingContext.requiresPDBServiceSwitch(connectionSwitchingContext)) {
                     this.switchPDB(ce, requestingSwitchingContext);
                     if (connectionSwitchingContext != null && connectionSwitchingContext.hasRoles()) {
                        connectionSwitchingContext.clearRoles();
                     }
                  }

                  if (requestingSwitchingContext.getProxyUser() != null) {
                     this.setProxyUser(ce, requestingSwitchingContext.getProxyUser(), requestingSwitchingContext.getProxyPassword());
                  } else if (requestingSwitchingContext.hasRoles() && (connectionSwitchingContext == null || !connectionSwitchingContext.hasRoles() || !connectionSwitchingContext.getRoles().equals(requestingSwitchingContext.getRoles()))) {
                     this.setRoles(ce, requestingSwitchingContext.getRoles());
                  }

                  try {
                     ce.setSwitchingContext((SwitchingContext)requestingSwitchingContext.clone());
                  } catch (CloneNotSupportedException var20) {
                     throw new ResourceException(var20);
                  }

                  this.updateGroups(ce);
                  var17 = false;
               } catch (ResourceException var21) {
                  this.failedRepurposeCount.incrementAndGet();
                  throw var21;
               }
            } else {
               var17 = false;
            }
         } else {
            var17 = false;
         }
      } catch (Throwable var22) {
         JDBCLogger.logConnectionRepurposeError(this.getName(), var22.toString());
         throw var22;
      } finally {
         if (var17) {
            if (onReservedList) {
               synchronized(this) {
                  --this.beingProcessed;
                  this.reserved.add(ce);
               }
            }

         }
      }

      if (onReservedList) {
         synchronized(this) {
            --this.beingProcessed;
            this.reserved.add(ce);
         }
      }

   }

   public void switchToRootPartition(ConnectionEnv ce) throws ResourceException {
      SwitchingContextManager.getInstance().push(ce.getRootSwitchingContext());

      try {
         this.repurposeConnection(ce);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

   }

   private void switchPDB(ConnectionEnv ce, SwitchingContext requestingSwitchingContext) throws ResourceException {
      this.switchPDB(ce, requestingSwitchingContext.getPDBName(), requestingSwitchingContext.getPDBServiceName());
   }

   private void switchPDB(ConnectionEnv ce, String newPDBName, String newPDBServiceName) throws ResourceException {
      Statement pdbStatement = null;
      String currentPDBName = null;
      String currentServiceName = null;
      if (ce.getSwitchingContext() != null) {
         currentPDBName = ce.getSwitchingContext().getPDBName();
         currentServiceName = ce.getSwitchingContext().getPDBServiceName();
      }

      try {
         ce.processingSwitch.set(true);
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("invoking  ALTER SESSION SET CONTAINER = " + newPDBName + (newPDBServiceName != null ? " SERVICE = " + newPDBServiceName : "") + " on connection " + ce);
         }

         pdbStatement = ce.conn.jconn.createStatement();
         pdbStatement.execute("ALTER SESSION SET CONTAINER = \"" + newPDBName + "\"" + (newPDBServiceName != null ? " SERVICE = \"" + newPDBServiceName + "\"" : ""));
         if (this.oracleHelper != null) {
            String servicename;
            if (newPDBName != null) {
               servicename = this.oracleHelper.getPDBName(ce);
               if (!newPDBName.equalsIgnoreCase(servicename)) {
                  throw new ResourceException("PDB " + servicename + " does not match target " + newPDBName + " after switch from " + currentPDBName + "/" + currentServiceName);
               }
            }

            if (newPDBServiceName != null) {
               servicename = this.oracleHelper.getServiceName(ce);
               if (!newPDBServiceName.equalsIgnoreCase(servicename)) {
                  throw new ResourceException("Service name " + servicename + " does not match target " + newPDBServiceName + " after switch from " + currentPDBName + "/" + currentServiceName);
               }
            }
         }
      } catch (SQLException var15) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("error switching from " + this.formatPDBServiceIdentifier(currentPDBName, currentServiceName) + " to " + this.formatPDBServiceIdentifier(newPDBName, newPDBServiceName) + " on connection " + ce, var15);
         }

         throw new ResourceException("error switching from " + this.formatPDBServiceIdentifier(currentPDBName, currentServiceName) + " to " + this.formatPDBServiceIdentifier(newPDBName, newPDBServiceName) + " due to " + var15.getMessage(), var15);
      } finally {
         ce.processingSwitch.set(false);

         try {
            if (pdbStatement != null) {
               pdbStatement.close();
            }
         } catch (SQLException var14) {
         }

      }

   }

   private String formatPDBServiceIdentifier(String pdbName, String serviceName) {
      if (pdbName == null && serviceName == null) {
         return "[]";
      } else {
         StringBuilder sb = new StringBuilder("[");
         if (pdbName != null) {
            sb.append(pdbName);
         }

         if (serviceName != null) {
            if (pdbName != null) {
               sb.append("/");
            }

            sb.append(serviceName);
         }

         sb.append("]");
         return sb.toString();
      }
   }

   protected void updateGroups(ConnectionEnv ce) throws ResourceException {
      Collection oldGroups = ce.getGroups();
      if (oldGroups != null) {
         oldGroups = new HashSet((Collection)oldGroups);
         Iterator var3 = ((Collection)oldGroups).iterator();

         while(var3.hasNext()) {
            ResourcePoolGroup group = (ResourcePoolGroup)var3.next();
            ((ResourcePoolImpl.Group)group).decrementCapacityCount();
         }
      }

      ce.initializeGroups();
      this.incrementGroupCapacities(ce);
      this.incrementCreatedCountForNewGroups(ce, (Collection)oldGroups);
   }

   private void incrementCreatedCountForNewGroups(PooledResource res, Collection oldGroups) {
      if (res != null) {
         Collection newGroups = res.getGroups();
         if (newGroups != null) {
            Iterator var4 = newGroups.iterator();

            while(true) {
               ResourcePoolGroup group;
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  group = (ResourcePoolGroup)var4.next();
               } while(oldGroups != null && oldGroups.contains(group));

               ((ResourcePoolImpl.Group)group).incrementCreatedCount();
            }
         }
      }
   }

   private void setProxyUser(ConnectionEnv ce, String proxyUser, char[] proxyPassword) throws ResourceException {
      Properties prop = new Properties();
      prop.put(ce.conn.proxyUserName, proxyUser);
      prop.put(ce.conn.proxyUserPassword, String.valueOf(proxyPassword));

      try {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("setting proxy user " + proxyUser + " on connection " + ce);
         }

         ce.OracleOpenProxySession(ce.conn.proxyTypeUserName, prop);
      } catch (SQLException var6) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("error setting proxy session to " + proxyUser + " on connection " + ce, var6);
         }

         throw new ResourceException(var6);
      }
   }

   private void setRoles(ConnectionEnv ce, Collection roles) throws ResourceException {
      if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
         JdbcDebug.JDBCCONN.debug("setting role " + roles + " on connection " + ce);
      }

      Statement roleStatement = null;

      try {
         roleStatement = ce.conn.jconn.createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append("SET ROLE ");
         int count = 0;
         Iterator var6 = roles.iterator();

         while(var6.hasNext()) {
            SwitchingContext.Role role = (SwitchingContext.Role)var6.next();
            if (count++ > 0) {
               sb.append(", ");
            }

            sb.append(role.getName());
            if (role.getPassword() != null) {
               sb.append(" IDENTIFIED BY " + String.valueOf(role.getPassword()));
            }
         }

         roleStatement.execute(sb.toString());
      } catch (SQLException var15) {
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            JdbcDebug.JDBCCONN.debug("error setting roles " + roles + " on connection " + ce, var15);
         }

         throw new ResourceException(var15);
      } finally {
         try {
            if (roleStatement != null) {
               roleStatement.close();
            }
         } catch (SQLException var14) {
            var14.printStackTrace();
         }

      }

   }

   public OracleHelper getOracleHelper() {
      return this.oracleHelper;
   }

   protected boolean returnNewlyCreatedResource(PooledResourceInfo resourceInfo) {
      if (this.config.isConnectionLabelingHighCostSet() && resourceInfo instanceof LabelingConnectionInfo) {
         return true;
      } else if (resourceInfo instanceof PropertiesConnectionInfo && ((PropertiesConnectionInfo)resourceInfo).getAdditionalProperties() != null) {
         return true;
      } else {
         return resourceInfo instanceof HAPooledResourceInfo && ((HAPooledResourceInfo)resourceInfo).getAdditionalProperties() != null ? true : true;
      }
   }

   public String getPartitionName() {
      return JDBCUtil.getPartitionName(this.dsBean);
   }

   public ConnectionEnv getCachedPooledResource(Connection physical) {
      if (this.getOracleHelper() != null && this.getOracleHelper().isReplayDriver()) {
         return this.oracleVersion >= 1202 && this.hasSetProxyObject ? this.getOracleHelper().getProxyObject(physical) : (ConnectionEnv)this.physicalConnectionMap.get(physical);
      } else {
         return null;
      }
   }

   public ConnectionEnv putCachedPooledResource(Connection physical, ConnectionEnv pooledResource) {
      if (this.getOracleHelper() != null && this.getOracleHelper().isReplayDriver()) {
         if (this.oracleVersion >= 1202 && pooledResource != null && pooledResource.conn != null && pooledResource.conn.hasSetProxyObject) {
            this.hasSetProxyObject = true;
            this.getOracleHelper().setProxyObject(physical, pooledResource);
            return pooledResource;
         } else {
            return (ConnectionEnv)this.physicalConnectionMap.put(physical, pooledResource);
         }
      } else {
         return null;
      }
   }

   public ConnectionEnv removeCachedPooledResource(Connection physical) {
      if (this.getOracleHelper() != null && this.getOracleHelper().isReplayDriver()) {
         if (this.oracleVersion >= 1202 && this.hasSetProxyObject) {
            ConnectionEnv ret = this.getOracleHelper().getProxyObject(physical);
            this.getOracleHelper().setProxyObject(physical, (ConnectionEnv)null);
            return ret;
         } else {
            return (ConnectionEnv)this.physicalConnectionMap.remove(physical);
         }
      } else {
         return null;
      }
   }

   public int getOracleVersion() {
      return this.oracleVersion;
   }

   public boolean getReplayStatistics() {
      if (this.replayStatisticsSnapshot == null) {
         return false;
      } else {
         this.replayStatisticsSnapshot.initialize(0L);
         synchronized(this) {
            ListIterator listIter = this.available.listIterator(0);

            ConnectionEnv ce;
            while(listIter.hasNext()) {
               ce = (ConnectionEnv)listIter.next();
               this.replayStatisticsSnapshot.increment(this.oracleHelper.getReplayStatistics(ce));
            }

            Iterator iter = this.reserved.iterator();

            while(iter.hasNext()) {
               ce = (ConnectionEnv)((ConnectionEnv)iter.next());
               this.replayStatisticsSnapshot.increment(this.oracleHelper.getReplayStatistics(ce));
            }

            this.replayStatisticsSnapshot.increment(this.closedConnectionReplayStatistics);
            return true;
         }
      }
   }

   public boolean clearReplayStatistics() {
      if (this.replayStatisticsSnapshot == null) {
         return false;
      } else {
         this.replayStatisticsSnapshot.initialize(0L);
         this.closedConnectionReplayStatistics.initialize(0L);
         synchronized(this) {
            ListIterator listIter = this.available.listIterator(0);

            ConnectionEnv ce;
            while(listIter.hasNext()) {
               ce = (ConnectionEnv)listIter.next();
               this.oracleHelper.clearReplayStatistics(ce);
            }

            Iterator iter = this.reserved.iterator();

            while(iter.hasNext()) {
               ce = (ConnectionEnv)((ConnectionEnv)iter.next());
               this.oracleHelper.clearReplayStatistics(ce);
            }

            return true;
         }
      }
   }

   public boolean isMemberDS() {
      return this.isMemberDS;
   }

   public boolean isSharedPool() {
      return this.sharedPool.get();
   }

   public boolean isSharingPool() {
      return false;
   }

   public int getSharedPoolReferenceCounter() {
      return this.sharedPoolReferenceCounter.get();
   }

   public int incrementSharedPoolReferenceCounter() {
      return !this.isSharedPool() ? 0 : this.sharedPoolReferenceCounter.incrementAndGet();
   }

   public int decrementSharedPoolReferenceCounter() {
      return !this.isSharedPool() ? 0 : this.sharedPoolReferenceCounter.decrementAndGet();
   }

   public SwitchingContext getRootSwitchingContext() {
      return this.rootSwitchingContext;
   }

   public void setRootSwitchingContext(SwitchingContext sc) {
      this.rootSwitchingContext = sc;
   }

   public ResourcePoolGroup getGroup(String category, String groupId) {
      return super.getGroup(category, groupId);
   }

   public ResourcePoolGroup getOrCreateGroup(String category, String groupId) {
      return super.getOrCreateGroup(category, groupId);
   }

   public ReplayStatisticsSnapshot getReplayStatisticsSnapshot() {
      return this.replayStatisticsSnapshot;
   }

   public void incrementClosedConnectionReplayStatistics(ConnectionEnv ce) {
      if (this.closedConnectionReplayStatistics != null) {
         this.closedConnectionReplayStatistics.increment(this.oracleHelper.getReplayStatistics(ce));
      }

   }

   public void repurposeMatchingAvailableAndReservedConnections(PooledResourceInfo pri) throws ResourceException {
      List availableToRepurpose = null;
      Iterator var5;
      PooledResource pr;
      synchronized(this) {
         if (this.reserved instanceof IGroupingPooledResourceSet) {
            List reservedToMark = ((IGroupingPooledResourceSet)this.reserved).getSubList(pri);
            if (reservedToMark != null) {
               var5 = reservedToMark.iterator();

               while(var5.hasNext()) {
                  pr = (PooledResource)var5.next();
                  ((ConnectionEnv)pr).setRepurposeOnRelease(true);
               }
            }
         }

         if (this.available instanceof IGroupingPooledResourceLinkedList) {
            availableToRepurpose = ((IGroupingPooledResourceLinkedList)this.available).getSubList(pri);
            if (availableToRepurpose != null && availableToRepurpose.size() > 0) {
               this.beingProcessed += availableToRepurpose.size();
               Iterator var12 = availableToRepurpose.iterator();

               while(var12.hasNext()) {
                  PooledResource pr = (PooledResource)var12.next();
                  this.available.remove(pr);
               }
            }
         }
      }

      if (availableToRepurpose != null && availableToRepurpose.size() > 0) {
         Iterator it = availableToRepurpose.iterator();

         while(it.hasNext()) {
            ConnectionEnv ce = (ConnectionEnv)it.next();

            try {
               this.switchToRootPartition(ce);
            } catch (ResourceException var10) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  this.debug("error switching connection to root partition: " + ce + ": " + StackTraceUtils.throwable2StackTrace(var10));
               }

               it.remove();
               ce.destroyForFlush(false);
               this.removeResource(ce);
            }
         }

         synchronized(this) {
            var5 = availableToRepurpose.iterator();

            while(var5.hasNext()) {
               pr = (PooledResource)var5.next();
               this.available.addFirst(pr);
            }

            this.beingProcessed -= availableToRepurpose.size();
         }
      }

   }

   public void destroyMatchingAvailableAndReservedConnections(PooledResourceInfo pri) throws ResourceException {
      synchronized(this) {
         List toDestroyAvailable;
         Iterator var4;
         PooledResource pr;
         if (this.reserved instanceof IGroupingPooledResourceSet) {
            toDestroyAvailable = ((IGroupingPooledResourceSet)this.reserved).getSubList(pri);
            if (toDestroyAvailable != null) {
               var4 = toDestroyAvailable.iterator();

               while(var4.hasNext()) {
                  pr = (PooledResource)var4.next();
                  ((ConnectionEnv)pr).destroyForFlush(false);
                  this.removeResource((ConnectionEnv)pr);
               }
            }
         }

         if (this.available instanceof IGroupingPooledResourceLinkedList) {
            toDestroyAvailable = ((IGroupingPooledResourceLinkedList)this.available).getSubList(pri);
            if (toDestroyAvailable != null) {
               var4 = toDestroyAvailable.iterator();

               while(var4.hasNext()) {
                  pr = (PooledResource)var4.next();
                  ((ConnectionEnv)pr).destroyForFlush(false);
                  this.removeResource((ConnectionEnv)pr);
               }
            }
         }

      }
   }

   public void waitForNoReservedMatching(PooledResourceInfo pri, int waitSeconds) throws ResourceException {
      for(int loop = waitSeconds * 10; loop > 0 && ((IGroupingPooledResourceSet)this.reserved).getSize(pri) != 0; --loop) {
         try {
            Thread.sleep(100L);
         } catch (InterruptedException var5) {
         }
      }

   }

   public void updateCredential(String p) throws SQLException {
      this.getResourceFactory().updateCredential(p);
   }

   protected static class ConnectionStoreDelegate {
      ConcurrentHashMap conns = new ConcurrentHashMap();
      String stackTrace = null;

      public ConnectionStoreDelegate() {
      }

      public Object get(Object key) {
         Object s = this.conns.get(key);
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            if (this.stackTrace == null) {
               this.stackTrace = StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + this + " is reserved"));
            } else {
               this.stackTrace = this.stackTrace + StackTraceUtils.throwable2StackTrace(new Exception("Pinned: " + this + " is reserved again"));
               JdbcDebug.JDBCCONN.debug(this.stackTrace);
            }
         }

         if (s == null) {
            return null;
         } else {
            Object ret = null;

            try {
               ret = ((Stack)s).pop();
            } catch (Throwable var5) {
            }

            return ret;
         }
      }

      public Object get(Object key, PooledResourceInfo pri) {
         return this.get(key);
      }

      public boolean isEmpty(Object key) {
         Stack s = (Stack)((Stack)this.conns.get(key));
         return s == null ? true : s.empty();
      }

      public void put(Object key, Object value) {
         Object s = this.conns.get(key);
         if (s == null) {
            s = new Stack();
            this.conns.put(key, s);
         }

         ((Stack)s).push(value);
         this.stackTrace = null;
      }

      public void remove(Object key, Object value) {
      }

      public boolean contains(Object key, Object value) {
         Object s = this.conns.get(key);
         return s == null ? false : ((Stack)s).contains(value);
      }

      public boolean containsType(Object key, PooledResourceInfo pri) {
         return false;
      }

      public String toString() {
         return this.conns.toString();
      }
   }

   protected static class ConnectionStore {
      ConnectionStoreDelegate delegate;

      public Object get(Object key) {
         this.initDelegate(key);
         return this.delegate.get(key);
      }

      public Object get(Object key, PooledResourceInfo pri) {
         this.initDelegate(key);
         return this.delegate.get(key, pri);
      }

      public boolean isEmpty(Object key) {
         this.initDelegate(key);
         return this.delegate.isEmpty(key);
      }

      public void put(Object key, Object value) {
         this.initDelegate(key);
         this.delegate.put(key, value);
      }

      public void remove(Object key, Object value) {
         this.initDelegate(key);
         this.delegate.remove(key, value);
      }

      public boolean contains(Object key, Object value) {
         this.initDelegate(key);
         return this.delegate.contains(key, value);
      }

      public boolean containsType(Object key, PooledResourceInfo pri) {
         this.initDelegate(key);
         return this.delegate.containsType(key, pri);
      }

      private void initDelegate(Object key) {
         if (this.delegate == null) {
            this.delegate = ((ConnectionPool)key).createConnectionStoreDelegate();
         }

      }
   }

   private static class ConnectionThreadLocal extends ThreadLocalInitialValue {
      public ConnectionThreadLocal() {
      }

      protected Object initialValue() {
         return new ConnectionStore();
      }

      protected Object resetValue(Object currentValue) {
         return currentValue;
      }
   }
}
