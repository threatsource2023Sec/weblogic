package weblogic.jdbc.common.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import oracle.ucp.ConnectionAffinityCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACModuleFactory;
import weblogic.jdbc.common.rac.RACModuleFailoverEvent;
import weblogic.jdbc.common.rac.RACModulePool;
import weblogic.jdbc.extensions.AffinityCallback;
import weblogic.jdbc.extensions.DataAffinityCallback;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class HASharingConnectionPool extends SharingConnectionPool implements HAJDBCConnectionPool, RACModulePool {
   private static final int DEFAULT_TIMER_INTERVAL = 5;
   private RACModule racModule;
   private ConnectionAffinityCallback.AffinityPolicy affinityPolicy;
   private AffinityCallback xaAffinityCallback;
   private SessionAffinityCallback sessionAffinityCallback;
   private String affinityContextKey;
   private int timerInterval = 5;
   private Timer maintenanceTimer;
   private MaintenanceTimerListener maintenanceTimerListener;
   private AtomicBoolean doingDraining = new AtomicBoolean();

   public HASharingConnectionPool(JDBCDataSourceBean dsBean, JDBCConnectionPool sharedPool, String appName, String moduleName, String compName) throws ResourceException {
      super(dsBean, sharedPool, appName, moduleName, compName);
      String policy = dsBean.getJDBCOracleParams().getAffinityPolicy();
      if ("Transaction".equals(policy)) {
         this.affinityPolicy = ConnectionAffinityCallback.AffinityPolicy.TRANSACTION_BASED_AFFINITY;
      } else if ("Session".equals(policy)) {
         this.affinityPolicy = ConnectionAffinityCallback.AffinityPolicy.WEBSESSION_BASED_AFFINITY;
      } else {
         if ("Data".equals(policy)) {
            throw new ResourceException("Data Affinity not supported with shared pooling");
         }

         this.affinityPolicy = null;
      }

      if (this.getPDBServiceName() != null) {
         this.xaAffinityCallback = new XAAffinityCallback(this);
         this.sessionAffinityCallback = new SessionAffinityCallback(this);
         this.racModule = RACModuleFactory.createInstance(this);
         ((HAJDBCConnectionPool)sharedPool).initOns(this.racModule);
      } else {
         this.racModule = ((HAJDBCConnectionPool)sharedPool).getRACModule();
      }

   }

   public void start(Object initInfo) throws ResourceException {
      super.start(initInfo);
      this.startMaintenanceTimer();
   }

   public void shutdown() throws ResourceException {
      this.stopMaintenanceTimer();
      this.stopRACModule();
      super.shutdown();
   }

   protected void initializeGroups() {
      super.initializeGroups();
   }

   public void addHADataSourceRuntime(HAJDBCConnectionPool pool, HADataSourceRuntime runtime) {
      ((HAJDBCConnectionPool)this.sharedPool).addHADataSourceRuntime(pool, runtime);
   }

   public HADataSourceRuntime removeHADataSourceRuntime(HAJDBCConnectionPool pool) {
      return ((HAJDBCConnectionPool)this.sharedPool).removeHADataSourceRuntime(pool);
   }

   public void createInstanceRuntime(HAJDBCConnectionPool pool, ResourcePoolGroup instanceGroup, String instanceName) {
      ((HAJDBCConnectionPool)this.sharedPool).createInstanceRuntime(pool, instanceGroup, instanceName);
   }

   public RACModule getRACModule() {
      return this.racModule != null ? this.racModule : ((HAJDBCConnectionPool)this.sharedPool).getRACModule();
   }

   public OracleHelper getOracleHelper() {
      return this.getRACModule();
   }

   public void initOns(RACModule racModule) throws ResourceException {
      ((HAJDBCConnectionPool)this.sharedPool).initOns(racModule);
   }

   public int getWeightForInstance(String instanceName) {
      return this.getRACModule().getInstanceWeight(instanceName);
   }

   public boolean getAffForInstance(String instanceName) {
      return this.getRACModule().getInstanceAffValue(instanceName);
   }

   public String getServiceName() {
      return this.getPDBServiceName() != null ? this.getPDBServiceName() : ((HAJDBCConnectionPool)this.sharedPool).getServiceName();
   }

   public String getDatabaseName() {
      return ((HAJDBCConnectionPool)this.sharedPool).getDatabaseName();
   }

   public long getFailedAffinityBasedBorrowCount() {
      return this.getRACModule().getFailedAffinityBasedBorrowCount();
   }

   public long getFailedRCLBBasedBorrowCount() {
      return this.getRACModule().getFailedRCLBBasedBorrowCount();
   }

   public long getSuccessfulAffinityBasedBorrowCount() {
      return this.getRACModule().getSuccessfulAffinityBasedBorrowCount();
   }

   public long getSuccessfulRCLBBasedBorrowCount() {
      return this.getRACModule().getSuccessfulRCLBBasedBorrowCount();
   }

   public void getAvailableAndBorrowedConnections(List available, List borrowed) {
      PooledResourceInfo pri = new LabelingConnectionInfo(this.getPDBName(), this.getPDBServiceName(), (Properties)null);
      List matchingAvailable = this.sharedPool.getAvailableMatching(pri);
      if (matchingAvailable != null) {
         available.addAll(matchingAvailable);
      }

      List matchingReserved = this.sharedPool.getReservedMatching(pri);
      if (matchingReserved != null) {
         borrowed.addAll(matchingReserved);
      }

   }

   public void getAvailableAndBorrowedConnections(List available, List borrowed, List instances, boolean removeAvailable) {
      if (instances == null) {
         throw new AssertionError("instances argument null");
      } else {
         PooledResourceInfo pri = new HAPooledResourceInfo(this.getPDBName(), this.getPDBServiceName(), instances);
         List matchingAvailable = this.sharedPool.getAvailableMatching(pri);
         if (matchingAvailable != null) {
            available.addAll(matchingAvailable);
            if (removeAvailable) {
               ((HAJDBCConnectionPool)this.sharedPool).removeFromAvailableForProcessing(matchingAvailable);
            }
         }

         List matchingReserved = this.sharedPool.getReservedMatching(pri);
         if (matchingReserved != null) {
            borrowed.addAll(matchingReserved);
         }

      }
   }

   public List getAvailableConnections(RACInstance instance, boolean remove) {
      List toReturn = null;
      PooledResourceInfo pri = new HAPooledResourceInfo(this.getPDBName(), this.getPDBServiceName(), instance);
      List matchingAvailable = this.sharedPool.getAvailableMatching(pri);
      if (matchingAvailable != null) {
         toReturn = new ArrayList();
         toReturn.addAll(matchingAvailable);
         if (remove) {
            ((HAJDBCConnectionPool)this.sharedPool).removeFromAvailableForProcessing(matchingAvailable);
         }
      }

      return toReturn;
   }

   public List getReservedConnections(RACInstance instance) {
      List toReturn = null;
      PooledResourceInfo pri = new HAPooledResourceInfo(this.getPDBName(), this.getPDBServiceName(), instance);
      List matchingAvailable = this.sharedPool.getReservedMatching(pri);
      if (matchingAvailable != null) {
         toReturn = new ArrayList();
         toReturn.addAll(matchingAvailable);
      }

      return toReturn;
   }

   public boolean doDraining(HAJDBCConnectionPool haPool) {
      return ((HAJDBCConnectionPool)this.sharedPool).doDraining(haPool);
   }

   public void initAffinityKeyIfNecessary() throws ResourceException {
      if (this.affinityContextKey == null) {
         if (this.getPDBServiceName() != null) {
            ((HAJDBCConnectionPool)this.sharedPool).initAffinityKeyIfNecessary();
            String databaseName = ((HAJDBCConnectionPool)this.sharedPool).getDatabaseName();
            this.affinityContextKey = HAUtil.getInstance().getAffinityContextKey(databaseName, this.getPDBServiceName());
         }
      }
   }

   public String getAffinityContextKey() {
      return this.affinityContextKey != null ? this.affinityContextKey : ((HAConnectionPool)this.sharedPool).getAffinityContextKey();
   }

   public ResourcePoolGroup getPoolGroup() {
      return this.getGroup("service_pdbname", JDBCUtil.getServicePDBGroupName(this.getServiceName(), this.getPDBName()));
   }

   public ResourcePoolGroup getGroupForInstance(String instance) {
      return this.getGroup("service_pdbname_instance", JDBCUtil.getServicePDBInstanceGroupName(this.getServiceName(), this.getPDBName(), instance));
   }

   public PooledResourceInfo getPooledResourceInfo(RACInstance instance, Properties labels) {
      PooledResourceInfo ret = new HAPooledResourceInfo(this.dsBean.getJDBCDriverParams().getUrl(), instance, this.getPDBName(), labels, (Properties)null);
      return ret;
   }

   public void resetStatistics() {
      super.resetStatistics();
      List knownInstances = this.racModule.getInstances();
      Iterator var2 = knownInstances.iterator();

      while(var2.hasNext()) {
         RACInstance instance = (RACInstance)var2.next();
         ResourcePoolGroup pdbinstanceGroup = this.getGroup("service_pdbname_instance", JDBCUtil.getServicePDBInstanceGroupName(this.getServiceName(), this.getPDBName(), instance.getInstance()));
         if (pdbinstanceGroup != null) {
            pdbinstanceGroup.resetStatistics();
         }
      }

   }

   public RACConnectionEnv getExistingConnectionToInstance(RACInstance instance, int waitSeconds, Properties requestedLabels) throws ResourceException {
      return this.getExistingConnectionToInstance(this, instance, waitSeconds, requestedLabels);
   }

   public HAConnectionEnv getExistingConnectionToInstance(HAJDBCConnectionPool haPool, RACInstance instance, int waitSeconds, Properties requestedLabels) throws ResourceException {
      if (instance != null) {
         ResourcePoolGroup instanceGroup = this.getGroupForInstance(instance.getInstance());
         if (instanceGroup == null || !instanceGroup.isEnabled()) {
            return null;
         }
      }

      SwitchingContextManager.getInstance().push(this.switchingContext);

      HAConnectionEnv var9;
      try {
         var9 = ((HAConnectionPool)this.sharedPool).getExistingConnectionToInstance(haPool, instance, waitSeconds, requestedLabels);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var9;
   }

   public RACConnectionEnv getExistingConnection(int waitSeconds, Properties requestedLabels) throws ResourceException {
      SwitchingContextManager.getInstance().push(this.switchingContext);

      HAConnectionEnv var3;
      try {
         var3 = (HAConnectionEnv)((HAConnectionPool)this.sharedPool).getExistingConnection(waitSeconds, requestedLabels);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var3;
   }

   public RACConnectionEnv createConnectionToInstance(RACInstance instance) throws ResourceException {
      SwitchingContextManager.getInstance().push(this.switchingContext);

      HAConnectionEnv var2;
      try {
         var2 = ((HAConnectionPool)this.sharedPool).createConnectionToInstance(instance);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var2;
   }

   public RACConnectionEnv createTemporaryConnection() throws ResourceException {
      SwitchingContextManager.getInstance().push(this.switchingContext);

      RACConnectionEnv var1;
      try {
         var1 = ((HAConnectionPool)this.sharedPool).createTemporaryConnection();
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var1;
   }

   public void removePooledResource(RACConnectionEnv conn) throws ResourceException {
      ((RACModulePool)this.sharedPool).removePooledResource(conn);
   }

   public int getMaxPoolSize() {
      return ((RACModulePool)this.sharedPool).getMaxPoolSize();
   }

   public int getMinPoolSize() {
      return ((RACModulePool)this.sharedPool).getMinPoolSize();
   }

   public String getPoolName() {
      return this.getName();
   }

   public int getRemainingPoolCapacity() {
      return ((RACModulePool)this.sharedPool).getRemainingPoolCapacity();
   }

   public int getCurrentPoolCapacity() {
      return ((RACModulePool)this.sharedPool).getCurrentPoolCapacity();
   }

   public String getJDBCURL() {
      return ((RACModulePool)this.sharedPool).getJDBCURL();
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException {
      return this.reserve(this.racModule, user, waitSeconds, requestedLabels, username, password);
   }

   public ConnectionEnv reserve(RACModule racModule, AuthenticatedSubject user, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException {
      this.checkStateForReserve();
      SwitchingContextManager.getInstance().push(this.switchingContext);

      ConnectionEnv var7;
      try {
         var7 = ((HAJDBCConnectionPool)this.sharedPool).reserve(racModule, user, waitSeconds, requestedLabels, username, password);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var7;
   }

   public boolean removeFromAvailableForProcessing(List resources) {
      return ((HAJDBCConnectionPool)this.sharedPool).removeFromAvailableForProcessing(resources);
   }

   public void fcfDownEvent(RACModuleFailoverEvent event) throws ResourceException {
      this.fcfDownEvent(this, this.racModule, event);
   }

   public void fcfDownEvent(HAJDBCConnectionPool haPool, RACModule racModule, RACModuleFailoverEvent event) throws ResourceException {
      ((HAJDBCConnectionPool)this.sharedPool).fcfDownEvent(haPool, racModule, event);
   }

   public int fcfUpEvent(RACModuleFailoverEvent event) throws ResourceException {
      return this.fcfUpEvent(this, this.racModule, event);
   }

   public int fcfUpEvent(HAJDBCConnectionPool haPool, RACModule racModule, RACModuleFailoverEvent event) throws ResourceException {
      return ((HAJDBCConnectionPool)this.sharedPool).fcfUpEvent(haPool, racModule, event);
   }

   public ConnectionAffinityCallback.AffinityPolicy getAffinityPolicy() {
      return this.affinityPolicy;
   }

   public AffinityCallback getXAAffinityCallback() {
      return this.xaAffinityCallback != null ? this.xaAffinityCallback : ((RACModulePool)this.sharedPool).getXAAffinityCallback();
   }

   public AffinityCallback getSessionAffinityCallback() {
      return (AffinityCallback)(this.sessionAffinityCallback != null ? this.sessionAffinityCallback : ((RACModulePool)this.sharedPool).getSessionAffinityCallback());
   }

   public DataAffinityCallback getDataAffinityCallback() {
      return null;
   }

   public RACConnectionEnv reserveInternalResource() throws ResourceException {
      SwitchingContextManager.getInstance().push(this.switchingContext);

      HAConnectionEnv var1;
      try {
         var1 = (HAConnectionEnv)((RACModulePool)this.sharedPool).reserveInternalResource();
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var1;
   }

   public RACModulePool getSharedRACModulePool() {
      return (RACModulePool)this.getSharedPool();
   }

   public void switchToRootPartition(ConnectionEnv ce) throws ResourceException {
      ((RACModulePool)this.sharedPool).switchToRootPartition(ce);
   }

   public Class getDriverClass() throws ClassNotFoundException {
      return ((OraclePool)this.sharedPool).getDriverClass();
   }

   public void replayInitialize(Connection connection) throws SQLException {
      ((OraclePool)this.sharedPool).replayInitialize(connection);
   }

   public boolean isXA() {
      return ((HAJDBCConnectionPool)this.sharedPool).isXA();
   }

   private final void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("HAConnectionPool[" + this.getName() + "]: " + msg);
   }

   public String toString() {
      return "HASharingConnectionPool(" + this.getName() + ")";
   }

   private void startMaintenanceTimer() {
      this.maintenanceTimerListener = new MaintenanceTimerListener();
      TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("SharedPoolTimerManager");
      if (timerManager != null) {
         this.maintenanceTimer = timerManager.scheduleAtFixedRate(this.maintenanceTimerListener, (long)(this.timerInterval * 1000), (long)(this.timerInterval * 1000));
      }

   }

   private void stopMaintenanceTimer() {
      if (this.maintenanceTimer != null) {
         this.maintenanceTimer.cancel();
      }

   }

   private void stopRACModule() throws ResourceException {
      if (this.racModule != null && this.racModule != ((HAJDBCConnectionPool)this.sharedPool).getRACModule()) {
         JDBCLogger.logUnregisteringForFANEvents(this.getName(), this.serviceName, this.racModule.getONSConfiguration());
         this.racModule.stop();
      }

   }

   private class MaintenanceTimerListener implements NakedTimerListener {
      private MaintenanceTimerListener() {
      }

      public void timerExpired(Timer timer) {
         if (HASharingConnectionPool.this.doingDraining.compareAndSet(false, true)) {
            try {
               HASharingConnectionPool.this.doDraining(HASharingConnectionPool.this);
            } finally {
               HASharingConnectionPool.this.doingDraining.set(false);
            }
         }

      }

      // $FF: synthetic method
      MaintenanceTimerListener(Object x1) {
         this();
      }
   }
}
