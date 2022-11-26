package weblogic.jdbc.common.rac.internal;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.ConnectionRetrievalInfo;
import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.ConnectionAffinityCallback.AffinityPolicy;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.JDBCConnectionRetrievalInfo;
import oracle.ucp.jdbc.oracle.FailoverablePooledConnection;
import oracle.ucp.jdbc.oracle.OracleFailoverEvent;
import oracle.ucp.jdbc.oracle.OracleLoadBalancingEvent;
import oracle.ucp.jdbc.oracle.RACCallback;
import oracle.ucp.jdbc.oracle.RACManager;
import oracle.ucp.jdbc.oracle.RACManagerFactory;
import oracle.ucp.util.TaskManager;
import oracle.ucp.util.TimerManager;
import oracle.ucp.util.UCPTaskManagerImpl;
import oracle.ucp.util.UCPTimerManagerImpl;
import weblogic.common.ResourceException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jdbc.common.internal.HASharingConnectionPool;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.rac.RACAffinityContextException;
import weblogic.jdbc.common.rac.RACAffinityContextHelper;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACInstanceFactory;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACModuleFailoverEvent;
import weblogic.jdbc.common.rac.RACModulePool;
import weblogic.jdbc.common.rac.RACPooledConnectionState;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.jdbc.extensions.DataAffinityCallback;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class UCPRACModuleImpl extends OracleHelperImpl implements RACModule, RACCallback {
   private static final DebugLogger JDBCONS = DebugLogger.getDebugLogger("DebugJDBCONS");
   private static final DebugLogger JDBCUCP = DebugLogger.getDebugLogger("DebugJDBCUCP");
   private RACModulePool pool;
   private RACManager racManager;
   private String onsConfiguration;
   private Map currentInstances = new HashMap();
   private AtomicBoolean ucpStarted = new AtomicBoolean();
   private boolean onsDebugEnabled;
   private JDBCConnectionRetrievalInfo emptyRetrievalInfo = new JDBCConnectionRetrievalInfo((String)null, (String)null);
   private ConnectionAffinityCallback ucpAffinityCallback;
   private ConnectionAffinityCallback.AffinityPolicy ucpAffinityPolicy;
   private static TaskManager taskManager;
   private static TimerManager timerManager;
   private RACAffinityContextHelper racAffinityContextHelper;
   private volatile boolean inStart = false;
   private volatile boolean startFailure = false;
   private ServiceTopology serviceTopology;

   public UCPRACModuleImpl(RACModulePool pool) throws ResourceException {
      super(pool);
      this.pool = pool;
      this.serviceTopology = new ServiceTopology(pool);
      this.racAffinityContextHelper = RACAffinityContextHelperImpl.getInstance();

      try {
         Properties props = null;
         if (this.isReplayDriver()) {
            props = new Properties();
            props.setProperty("oracle.ucp.jdbc.oracle.Replayable", "true");
         }

         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("creating UCP RACModule with args: " + props + ", " + taskManager + ", " + timerManager);
         }

         this.racManager = RACManagerFactory.getRACManager(props, taskManager, timerManager);
      } catch (UniversalConnectionPoolException var3) {
         throw new ResourceException("unable to instantiate UCP RACManager", var3);
      }
   }

   private boolean isUCPStarted() {
      return this.ucpStarted.get();
   }

   ConnectionAffinityCallback.AffinityPolicy getUCPAffinityPolicy() {
      return this.ucpAffinityPolicy;
   }

   RACModulePool getPool() {
      return this.pool;
   }

   public void connectionOpened(RACConnectionEnv hace) throws ResourceException {
      if (this.isUCPStarted() || this.inStart) {
         this.addToCurrentInstances(hace.getRACInstance());

         try {
            this.racManager.connectionOpened((FailoverablePooledConnection)hace.getRACPooledConnectionState());
         } catch (UniversalConnectionPoolException var3) {
            throw new ResourceException(var3);
         }
      }
   }

   public void connectionClosed(RACConnectionEnv hace) throws ResourceException {
      if (this.isUCPStarted()) {
         try {
            this.racManager.connectionClosed((FailoverablePooledConnection)hace.getRACPooledConnectionState());
         } catch (UniversalConnectionPoolException var3) {
            throw new ResourceException(var3);
         }
      }
   }

   public RACConnectionEnv getConnection(Properties requestedLabels) throws ResourceException {
      if (!this.isUCPStarted()) {
         return null;
      } else {
         RACModulePool racModulePool = this.getRACModulePool();
         RACConnectionEnv ret = null;
         ConnectionAffinityCallback.AffinityPolicy policyInEffect = this.ucpAffinityPolicy;

         RACConnectionEnv var8;
         try {
            AffinityCallback xacb = racModulePool.getXAAffinityCallback();
            DataAffinityCallback retrievalInfo;
            AffinityCallback pooledConnection;
            if (this.ucpAffinityPolicy == AffinityPolicy.DATA_BASED_AFFINITY) {
               retrievalInfo = racModulePool.getDataAffinityCallback();
               if (retrievalInfo == null) {
                  pooledConnection = racModulePool.getSessionAffinityCallback();
                  if (pooledConnection != null && pooledConnection.isApplicationContextAvailable()) {
                     policyInEffect = AffinityPolicy.WEBSESSION_BASED_AFFINITY;
                  } else {
                     policyInEffect = AffinityPolicy.TRANSACTION_BASED_AFFINITY;
                  }

                  this.ucpAffinityCallback.setAffinityPolicy(policyInEffect);
               }
            }

            if (this.ucpAffinityPolicy == AffinityPolicy.WEBSESSION_BASED_AFFINITY) {
               AffinityCallback sessionCallback = racModulePool.getSessionAffinityCallback();
               if (sessionCallback == null || !sessionCallback.isApplicationContextAvailable() || xacb != null && xacb.isApplicationContextAvailable()) {
                  policyInEffect = AffinityPolicy.TRANSACTION_BASED_AFFINITY;
                  this.ucpAffinityCallback.setAffinityPolicy(policyInEffect);
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("setting affinity policy from " + AffinityPolicy.WEBSESSION_BASED_AFFINITY + " to " + policyInEffect);
                  }
               }
            }

            retrievalInfo = null;
            JDBCConnectionRetrievalInfo retrievalInfo;
            if (requestedLabels != null) {
               retrievalInfo = new JDBCConnectionRetrievalInfo((String)null, (String)null, requestedLabels);
            } else {
               retrievalInfo = this.emptyRetrievalInfo;
            }

            pooledConnection = null;
            RACPooledConnectionState pooledConnection;
            if (policyInEffect == null) {
               pooledConnection = (RACPooledConnectionState)this.racManager.selectConnectionPerRCLB(retrievalInfo);
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("RACManager.selectConnectionPerRCLB() returned " + pooledConnection);
               }
            } else {
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("selecting connection with AffinityPolicy=" + policyInEffect);
               }

               pooledConnection = (RACPooledConnectionState)this.racManager.selectConnectionPerRCLBAndAffinity(retrievalInfo);
               if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                  this.debug("RACManager.selectConnectionPerRCLBAndAffinity() returned " + pooledConnection);
               }
            }

            if (pooledConnection != null) {
               var8 = pooledConnection.getConnection();
               return var8;
            }

            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("RACManager RCLB miss");
            }

            var8 = null;
         } catch (UniversalConnectionPoolException var12) {
            throw new ResourceException(var12);
         } finally {
            if (policyInEffect != this.ucpAffinityPolicy && this.ucpAffinityCallback != null) {
               this.ucpAffinityCallback.setAffinityPolicy(this.ucpAffinityPolicy);
            }

         }

         return var8;
      }
   }

   public void setONSConfiguration(String ons) {
      this.onsConfiguration = ons;
   }

   public String getONSConfiguration() {
      return this.onsConfiguration;
   }

   public void start() throws ResourceException {
      if (this.isUCPStarted() && JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("start() UCP RAC Module already started");
      }

      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("starting UCP RAC module");
      }

      if (JDBCUCP.isDebugEnabled()) {
         try {
            JDBCUCP.debug("enabling UCP logging");
            Logger.getLogger("oracle.ucp").setLevel(Level.FINEST);
            UniversalConnectionPoolManager mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
            mgr.setLogLevel(Level.FINEST);
         } catch (UniversalConnectionPoolException var6) {
            JDBCUCP.debug(var6.toString());
         }
      }

      if (this.onsDebugEnabled != JDBCONS.isDebugEnabled()) {
         this.onsDebugEnabled = JDBCONS.isDebugEnabled();
      }

      try {
         this.racManager.registerRACCallback(this);
         ConnectionAffinityCallback.AffinityPolicy policy = this.pool.getAffinityPolicy();
         if (policy != null) {
            this.ucpAffinityPolicy = RACModuleUtils.getUCPAffinityPolicy(policy);
            this.ucpAffinityCallback = new UCPDataAffinityCallbackImpl(this.ucpAffinityPolicy, this);
            this.racManager.registerConnectionAffinityCallback(this.ucpAffinityCallback);
         }

         this.racManager.setONSConfiguration(this.onsConfiguration);
         this.inStart = true;
         this.racManager.start();
         this.ucpStarted.set(true);
      } catch (UniversalConnectionPoolException var7) {
         this.startFailure = false;
         throw new ResourceException("unable to initialize UCP RACManager", var7);
      } finally {
         this.inStart = false;
         if (this.startFailure) {
            this.startFailure = false;
            throw new ResourceException("unable to initialize UCP RACManager");
         }

      }

      if (this.pool instanceof HASharingConnectionPool) {
         this.initializeServiceTopology();
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("service " + this.pool.getServiceName() + " running on " + this.serviceTopology);
         }
      }

   }

   public void stop() throws ResourceException {
      if (!this.isUCPStarted()) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("stop() UCP RAC Module already stopped");
         }

      } else {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("stopping UCP RAC module");
         }

         try {
            if (this.ucpAffinityCallback != null) {
               this.racManager.unregisterConnectionAffinityCallback(this.ucpAffinityCallback);
               this.ucpAffinityCallback = null;
               this.ucpAffinityPolicy = null;
            }

            this.racManager.unregisterRACCallback(this);
            this.racManager.stop();
         } catch (UniversalConnectionPoolException var5) {
            throw new ResourceException("error stopping UCP RACManager", var5);
         } finally {
            this.ucpStarted.set(false);
         }

      }
   }

   public void processConnectionsOnDownEvent(RACModuleFailoverEvent event, List available, List borrowed) throws ResourceException {
      if (!this.isUCPStarted()) {
         throw new ResourceException("UCP not started");
      } else {
         OracleFailoverEvent ofe = ((RACModuleFailoverEventImpl)event).getOracleFailoverEvent();

         try {
            FailoverablePooledConnection[] aconns = this.toArray(available);
            FailoverablePooledConnection[] bconns = this.toArray(borrowed);
            this.racManager.markDownConnectionsForDownEvent(aconns, bconns, ofe);
            this.racManager.cleanupConnectionsForDownEvent(aconns, bconns, ofe);
         } catch (UniversalConnectionPoolException var7) {
            throw new ResourceException(var7);
         }
      }
   }

   public int processConnectionsOnUpEvent(RACModuleFailoverEvent event, List available, List borrowed, int minCapacity, int maxCapacity) throws ResourceException {
      if (!this.isUCPStarted()) {
         throw new ResourceException("UCP not started");
      } else {
         OracleFailoverEvent ofe = ((RACModuleFailoverEventImpl)event).getOracleFailoverEvent();
         FailoverablePooledConnection[] aconns = this.toArray(available);
         FailoverablePooledConnection[] bconns = this.toArray(borrowed);

         try {
            int ret = this.racManager.processUpEvent(aconns, bconns, minCapacity, maxCapacity, ofe);
            return ret;
         } catch (UniversalConnectionPoolException var11) {
            throw new ResourceException(var11);
         }
      }
   }

   public RACPooledConnectionState createRACPooledConnectionState(RACConnectionEnv hace) throws ResourceException {
      RACPooledConnectionState state = new RACPooledConnectionStateImpl(hace);
      this.addToCurrentInstances(state.getRACInstance());
      return state;
   }

   public List getInstancesForHost(String host) {
      ArrayList instances = new ArrayList();
      synchronized(this.currentInstances) {
         Iterator var4 = this.currentInstances.values().iterator();

         while(var4.hasNext()) {
            RACInstance racInstance = (RACInstance)var4.next();
            if (racInstance.getHost().equals(host)) {
               instances.add(racInstance.getInstance());
            }
         }

         return instances;
      }
   }

   public int getInstanceWeight(String instanceName) {
      instanceName = instanceName.toLowerCase();
      synchronized(this.currentInstances) {
         RACInstance racInstance = (RACInstance)this.currentInstances.get(instanceName);
         return racInstance != null ? racInstance.getPercent() : -1;
      }
   }

   public boolean getInstanceAffValue(String instanceName) {
      instanceName = instanceName.toLowerCase();
      synchronized(this.currentInstances) {
         RACInstance racInstance = (RACInstance)this.currentInstances.get(instanceName);
         return racInstance != null ? racInstance.getAff() : false;
      }
   }

   public RACInstance getRACInstance(String instanceName) {
      instanceName = instanceName.toLowerCase();
      synchronized(this.currentInstances) {
         return (RACInstance)this.currentInstances.get(instanceName);
      }
   }

   public RACInstance getOrCreateRACInstance(String instanceName) throws ResourceException {
      RACInstance racInstance = this.getRACInstance(instanceName);
      if (racInstance == null) {
         this.pool.initAffinityKeyIfNecessary();
         String serviceName = this.pool.getServiceName();
         String databaseName = this.pool.getDatabaseName();
         if (serviceName != null && databaseName != null) {
            return RACInstanceFactory.getInstance().create(databaseName, instanceName, serviceName);
         }
      }

      return racInstance;
   }

   public List getInstances() {
      return new ArrayList(this.currentInstances.values());
   }

   public void markConnectionGood(RACConnectionEnv hace) {
      hace.getRACPooledConnectionState().markConnectionGood();
   }

   public long getFailedRCLBBasedBorrowCount() {
      return this.racManager.getFailedRCLBBasedBorrowCount();
   }

   public long getSuccessfulRCLBBasedBorrowCount() {
      return this.racManager.getSuccessfulRCLBBasedBorrowCount();
   }

   public long getFailedAffinityBasedBorrowCount() {
      return this.racManager.getFailedAffinityBasedBorrowCount();
   }

   public long getSuccessfulAffinityBasedBorrowCount() {
      return this.racManager.getSuccessfulAffinityBasedBorrowCount();
   }

   public Set getServiceInstanceNames() {
      this.initializeServiceTopology();
      return this.serviceTopology.getServiceInstanceNames();
   }

   private void initializeServiceTopology() {
      if (!this.serviceTopology.isInitialized()) {
         try {
            this.serviceTopology.initialize();
            List racInstances = this.serviceTopology.getRACInstances();
            Iterator var2 = racInstances.iterator();

            while(var2.hasNext()) {
               RACInstance racInstance = (RACInstance)var2.next();
               this.addToCurrentInstances(racInstance);
            }
         } catch (SQLException var4) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("error getting service topology for service " + this.pool.getServiceName() + ": " + StackTraceUtils.throwable2StackTrace(var4));
            }
         }

      }
   }

   public FailoverablePooledConnection getAvailableConnectionToInstance(ConnectionRetrievalInfo retrievalInfo, oracle.ucp.jdbc.oracle.RACInstance racinstance) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("getAvailableConnectionToInstance() racinstance=[" + racinstance + "], connectionretrievalinfo=" + retrievalInfo + ", isRCLB=" + this.racManager.isRuntimeLoadBalancingEnabled());
      }

      if (racinstance.getService() == null) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getAvailableConnectionToInstance() called with null service name: " + racinstance + " - ignoring...");
         }

         return null;
      } else {
         RACModulePool racModulePool = this.getRACModulePool();
         RACInstance instance = RACModuleUtils.createRACInstance(racinstance);

         try {
            RACConnectionEnv ce = racModulePool.getExistingConnectionToInstance(instance, -1, retrievalInfo.getLabels());
            if (ce != null) {
               return (FailoverablePooledConnection)ce.getRACPooledConnectionState();
            } else {
               if (this.ucpAffinityCallback != null && this.ucpAffinityCallback.getAffinityPolicy() == AffinityPolicy.TRANSACTION_BASED_AFFINITY) {
                  Object connectionAffinityContext = this.ucpAffinityCallback.getConnectionAffinityContext();
                  if (connectionAffinityContext != null) {
                     String affinityContextInstance = null;

                     try {
                        affinityContextInstance = this.racAffinityContextHelper.getInstanceName(connectionAffinityContext);
                     } catch (RACAffinityContextException var9) {
                        if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                           this.debug("getAvailableConnectionToInstance() unable to obtain instance name from transaction affinity context: " + var9);
                        }

                        throw new ResourceException("failed to obtain instance from transaction affinity context " + connectionAffinityContext, var9);
                     }

                     if (racinstance != null && racinstance.getInstance().equalsIgnoreCase(affinityContextInstance)) {
                        try {
                           ce = racModulePool.createConnectionToInstance(instance);
                        } catch (ResourceException var10) {
                           if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                              this.debug("getAvailableConnectionToInstance() createConnectionToInstance(" + instance + ") failed: " + var10);
                           }

                           throw var10;
                        }

                        if (ce != null) {
                           if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                              this.debug("getAvailableConnectionToInstance() returning new connection to " + racinstance);
                           }

                           return (FailoverablePooledConnection)ce.getRACPooledConnectionState();
                        }

                        if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                           this.debug("getAvailableConnectionToInstance() Unable to open new connection to instance " + racinstance + " to honor transaction affinity context");
                        }

                        throw new ResourceException("Unable to open new connection to instance " + racinstance + " to honor transaction affinity context");
                     }
                  }
               }

               return null;
            }
         } catch (ResourceException var11) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("getAvailableConnectionToInstance() connRetrievalInfo=" + retrievalInfo + ", racInstance=" + racinstance + " failed: " + var11);
            }

            throw new UniversalConnectionPoolException(var11);
         }
      }
   }

   public Collection getAvailableConnections(ConnectionRetrievalInfo retrievalInfo) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("getAvailableConnections() connectionretrievalinfo=" + retrievalInfo + ", isRCLB=" + this.racManager.isRuntimeLoadBalancingEnabled());
      }

      RACModulePool racModulePool = this.getRACModulePool();

      try {
         RACConnectionEnv ce = racModulePool.getExistingConnection(-1, retrievalInfo.getLabels());
         if (ce != null) {
            Collection c = new ArrayList(1);
            c.add((FailoverablePooledConnection)ce.getRACPooledConnectionState());
            return c;
         } else {
            return null;
         }
      } catch (ResourceException var5) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getAvailableConnections() connRetrievalInfo=" + retrievalInfo + " failed: " + var5);
         }

         throw new UniversalConnectionPoolException(var5);
      }
   }

   public int getMaxPoolSize() {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("getMaxPoolSize()");
      }

      return this.pool.getMaxPoolSize();
   }

   public int getMinPoolSize() {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("getMinPoolSize()");
      }

      return this.pool.getMinPoolSize();
   }

   public String getPoolName() {
      return this.pool.getPoolName();
   }

   public int getRoomToGrowPool() {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("getRoomToGrowPool()");
      }

      return this.pool.getRemainingPoolCapacity();
   }

   public int getTotalConnectionsCount() {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("getTotalConnectionsCount()");
      }

      return this.pool.getCurrentPoolCapacity();
   }

   public String getUrl() {
      return this.pool.getJDBCURL();
   }

   public boolean getValidateConnectionOnBorrow() {
      return false;
   }

   public void initiateDownEventProcessing(OracleFailoverEvent ofe) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("initiateDownEventProcessing() event=" + ofe);
      }

      String instanceName = ofe.getInstanceName();
      if (instanceName != null && instanceName.length() > 0 && this.serviceTopology.removeInstance(ofe.getServiceName(), instanceName, ofe.getDbUniqueName(), ofe.getHostName()) && JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("initiateDownEventProcessing() updated service topology " + this.serviceTopology);
      }

      try {
         this.pool.fcfDownEvent(new RACModuleFailoverEventImpl(ofe));
      } catch (ResourceException var4) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("initiateDownEventProcessing() error: " + StackTraceUtils.throwable2StackTrace(var4));
         }

         throw new UniversalConnectionPoolException(var4);
      }
   }

   public int initiateUpEventProcessing(OracleFailoverEvent ofe) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("initiateUpEventProcessing() event=" + ofe);
      }

      String instanceName = ofe.getInstanceName();
      if (instanceName != null && instanceName.length() > 0 && this.serviceTopology.addInstance(ofe.getServiceName(), instanceName, ofe.getDbUniqueName(), ofe.getHostName()) && JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("initiateUpEventProcessing() updated service topology " + this.serviceTopology);
      }

      try {
         return this.pool.fcfUpEvent(new RACModuleFailoverEventImpl(ofe));
      } catch (ResourceException var4) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("initiateUpEventProcessing() error: " + StackTraceUtils.throwable2StackTrace(var4));
         }

         throw new UniversalConnectionPoolException(var4);
      }
   }

   public boolean isValid(FailoverablePooledConnection fpc) {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("isValid() fpc=" + fpc);
      }

      return false;
   }

   public void lbaEventOccurred(OracleLoadBalancingEvent event) throws UniversalConnectionPoolException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("lbaEventOccurred() event=" + this.lbaEventToString(event));
      }

      if (JdbcDebug.JDBCONS.isDebugEnabled()) {
         JdbcDebug.JDBCONS.debug("Received load-balancing event [" + this.pool.getPoolName() + "]: " + this.lbaEventToString(event));
      }

      Set lbinstances = event.getRACInstances();
      synchronized(this.currentInstances) {
         Collection instances = this.currentInstances.values();
         Iterator var5 = instances.iterator();

         while(var5.hasNext()) {
            RACInstance instance = (RACInstance)var5.next();
            boolean found = false;
            Iterator var8 = lbinstances.iterator();

            while(var8.hasNext()) {
               oracle.ucp.jdbc.oracle.RACInstance lbinstance = (oracle.ucp.jdbc.oracle.RACInstance)var8.next();
               String instanceName = instance.getInstance();
               if (instanceName != null && instanceName.equalsIgnoreCase(lbinstance.getInstance())) {
                  found = true;
                  instance.setPercent(lbinstance.getPercent());

                  try {
                     instance.setAff(RACModuleUtils.isAffSetForInstance(instanceName, event.getEventBody()));
                  } catch (UnsupportedEncodingException var13) {
                     if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                        this.debug("lbaEventOccurred() error parsing event body to obtain aff flag for instance " + instanceName + ": " + var13);
                     }
                  }
                  break;
               }
            }

            if (!found) {
               instance.setPercent(1);
            }
         }

      }
   }

   public FailoverablePooledConnection openNewConnection(String url, oracle.ucp.jdbc.oracle.RACInstance racInstance) throws UniversalConnectionPoolException {
      String instanceName = null;
      RACInstance instance = null;
      if (racInstance != null) {
         instanceName = racInstance.getInstance();
         instance = RACModuleUtils.createRACInstance(racInstance);
      }

      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         this.debug("openNewConnection() url=" + url + ", instance=" + instanceName);
      }

      try {
         RACConnectionEnv ret = null;
         if (this.inStart && instance == null) {
            ret = this.pool.createTemporaryConnection();
         } else {
            ret = this.pool.createConnectionToInstance(instance);
         }

         if (ret == null) {
            this.startFailure = true;
            throw new UniversalConnectionPoolException("Failed to get connection");
         } else {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("openNewConnection() created FailoverablePooledConnection: " + ret.getRACPooledConnectionState());
            }

            return (FailoverablePooledConnection)ret.getRACPooledConnectionState();
         }
      } catch (ResourceException var6) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("openNewConnection(): failed to get connection to instance " + instanceName);
            this.debug(StackTraceUtils.throwable2StackTrace(var6));
         }

         this.startFailure = true;
         throw new UniversalConnectionPoolException(var6);
      } catch (RuntimeException var7) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("openNewConnection(): unexpected exception: " + StackTraceUtils.throwable2StackTrace(var7));
         }

         this.startFailure = true;
         throw var7;
      }
   }

   private FailoverablePooledConnection[] toArray(List connList) {
      FailoverablePooledConnection[] ret = new FailoverablePooledConnection[connList.size()];
      int i = 0;

      RACConnectionEnv hace;
      for(Iterator var4 = connList.iterator(); var4.hasNext(); ret[i++] = (FailoverablePooledConnection)hace.getRACPooledConnectionState()) {
         hace = (RACConnectionEnv)var4.next();
      }

      return ret;
   }

   private String lbaEventToString(OracleLoadBalancingEvent event) {
      StringBuilder sb = new StringBuilder();
      Set instances = event.getRACInstances();
      if (instances != null && !instances.isEmpty()) {
         Iterator it = instances.iterator();
         oracle.ucp.jdbc.oracle.RACInstance first = (oracle.ucp.jdbc.oracle.RACInstance)it.next();
         String service = first.getService();
         if (service != null) {
            sb.append("service=" + service + ", ");
         }

         String database = first.getDatabase();
         if (database != null) {
            sb.append("database=" + database + ", ");
         }
      }

      sb.append("event=" + new String(event.getEventBody()));
      return sb.toString();
   }

   private RACModulePool getRACModulePool() {
      return this.pool;
   }

   private void addToCurrentInstances(RACInstance racInstance) {
      if (racInstance != null && !this.currentInstances.containsKey(racInstance.getInstance())) {
         synchronized(this.currentInstances) {
            this.currentInstances.put(racInstance.getInstance(), racInstance);
         }
      }

   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("UCPRACModuleImpl [" + this.pool.getPoolName() + "]: " + msg);
   }

   public String toString() {
      return "UCPRACModuleImpl(" + this.pool + ")";
   }

   static {
      if (Boolean.valueOf(System.getProperty("weblogic.jdbc.fan.useCommonj", "true"))) {
         if (Boolean.valueOf(System.getProperty("weblogic.jdbc.fan.useWM", "false"))) {
            WorkManager wlWorkManager = WorkManagerFactory.getInstance().findOrCreate("jdbc-fan-wm", 1, -1);
            taskManager = new WLTaskManager(wlWorkManager);
            weblogic.timers.TimerManager wlTimerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("jdbc-fan-tm", wlWorkManager);
            timerManager = new WLTimerManager(wlTimerManager);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("configuring UCP to use WL Work and Timer Managers: " + wlWorkManager + ", " + wlTimerManager);
            }
         } else {
            taskManager = new WLTaskManager();
            weblogic.timers.TimerManager wlTimerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
            timerManager = new WLTimerManager(wlTimerManager);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               JdbcDebug.JDBCRAC.debug("configuring UCP to use Java Threads and WL Timer Manager: " + wlTimerManager);
            }
         }

         UniversalConnectionPoolManagerImpl.setTaskManager(taskManager);
         UniversalConnectionPoolManagerImpl.setTimerManager(timerManager);
      } else {
         taskManager = new UCPTaskManagerImpl();
         taskManager.start();
         timerManager = new UCPTimerManagerImpl();
         timerManager.start();
         UniversalConnectionPoolManagerImpl.setTaskManager(taskManager);
         UniversalConnectionPoolManagerImpl.setTimerManager(timerManager);
      }

      if (JDBCONS.isDebugEnabled()) {
         JDBCONS.debug("setting oracle.ons.debug=true");
         System.setProperty("oracle.ons.debug", Boolean.toString(JDBCONS.isDebugEnabled()));
      }

      try {
         ONSUtil.initializeNotificationManager();
      } catch (ResourceException var2) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled() && JdbcDebug.JDBCRAC.isDebugEnabled()) {
            JdbcDebug.JDBCRAC.debug("ONS WorkloadManager not initialized: " + var2.getMessage());
         }
      }

   }
}
