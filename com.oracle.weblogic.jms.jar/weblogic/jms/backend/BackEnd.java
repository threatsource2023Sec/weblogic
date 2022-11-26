package weblogic.jms.backend;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.naming.NamingException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.application.ModuleException;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.cluster.singleton.Leasing;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.diagnostics.utils.JMSAccessorHelper;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.LowMemoryNotificationService;
import weblogic.health.MemoryEvent;
import weblogic.health.MemoryListener;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.InvalidSubscriptionSharingException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationCreateResponse;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageOpenDataConverter;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSServerSessionPoolCreateResponse;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageStatisticsLogger;
import weblogic.jms.common.ModuleName;
import weblogic.jms.common.NonDurableSubscription;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.common.ServerSessionPoolHelper;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.store.JMSObjectHandler;
import weblogic.logging.jms.JMSMessageLogger;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSSessionPoolMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.JMSSessionPoolRuntimeMBean;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.common.ThresholdHandler;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaPolicy;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.path.Key;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.runtime.CursorRuntimeImpl;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStoreException;
import weblogic.store.TestStoreException;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.gxa.internal.GXAResourceImpl;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.internal.XidImpl;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class BackEnd implements MessageStatisticsLogger, Invocable, MemoryListener, BeanListenerCustomizer {
   static final long serialVersionUID = -3550452657980202118L;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String NORMAL_WM_NAME = ".System";
   private static final String LIMITED_WM_NAME = ".Limited";
   private static final String ASYNC_WM_NAME = ".AsyncPush";
   private static final String FORWARDER_WM_NAME = ".Forwarder";
   private Kernel kernel;
   private Quota backEndQuota;
   private HashMap BEQuotas;
   private TemporaryModule temporaryModule;
   private int temporaryCounter;
   private boolean isHostingTemporaryDestinations;
   private ModuleName temporaryTemplateResource;
   private String temporaryTemplateName;
   private final String name;
   private final String configName;
   private final String configType;
   private final Object originalScopeMBean;
   private boolean storeMessageCompressionEnabled;
   private boolean pagingMessageCompressionEnabled;
   private String messageCompressionOptions;
   private Properties messageCompressionOptionsProp;
   private Properties messageCompressionOptionsOverrideProp;
   private String messageCompressionOptionsOverride;
   private final JMSServerId backEndId;
   private HashMap durableSubscribers;
   private HashMap jms2NonDurableSharedSubscriptions;
   private final HashMap serverSessionPoolsByName;
   private final HashMap serverSessionPoolsById;
   private long serverSessionPoolsCurrentCount;
   private long serverSessionPoolsHighCount;
   private long serverSessionPoolsTotalCount;
   private long destinationsHighCount;
   private long destinationsTotalCount;
   private boolean bound;
   private boolean isMigratable;
   private boolean isLateMigrationDeactivate;
   private String targetName;
   private final HashMap name2Destination;
   private final CreateId2DestinationMapHandler createId2DestinationMapHandler;
   private TimerManager timerManager;
   private static final String BE_NAME_PREFIX = "weblogic.jms.";
   private static final String HEALTH_NAME_PREFIX = "JMSServer.";
   private PersistentStoreXA persistentStore;
   private boolean downgradeable;
   private String pagingDirectory;
   private final HashMap pagingConfig;
   private BEDurableSubscriptionStore durableSubscriptionStore;
   private BEMultiSender multiSender;
   private ThresholdHandler thresholdHandler;
   private static final int LIMITED_WM_NUM_THREADS = 8;
   private WorkManager workManager;
   private WorkManager limitedWorkManager;
   private WorkManager asyncPushWorkManager;
   private WorkManager forwarderWorkManager;
   private volatile boolean isMemoryLow;
   private BackEndTempDestinationFactory tempDestinationFactory;
   private BEServerSessionPoolFactory serverSessionPoolFactory;
   private JMSSessionPoolMBean[] sessionPoolMBeans;
   private int state;
   private int pausedState;
   private final Object shutdownLock;
   private final Object destinationDeletionLock;
   private static final String DEFAULT_SESSION_POOL_FACTORY_JNDI = "weblogic.jms.ServerSessionPoolFactory:";
   private static final double JMS_ABOVE_QUOTA_RATE = 0.9;
   private static final long JMS_ABOVE_QUOTA_TIME = 3600000L;
   private static final boolean IS_HEALTH_CRITICAL = false;
   private Exception backEndHealthException;
   private long startupTime;
   private final InvocableMonitor invocableMonitor;
   private QuotaPolicy blockingSendPolicy;
   private JMSMessageLogger jmsMessageLogger;
   private static final String PRODUCTION = "Production";
   private static final String INSERTION = "Insertion";
   private static final String CONSUMPTION = "Consumption";
   private String productionPausedAtStartup;
   private String insertionPausedAtStartup;
   private String consumptionPausedAtStartup;
   private final JMSService jmsService;
   private PathHelper pathHelper;
   private JMSServerRuntimeMBean runtimeMBean;
   private volatile boolean migrationInProgress;
   private String alternativeKernelName;

   public boolean isMigrationInProgress() {
      return this.migrationInProgress;
   }

   public void setMigrationInProgress(boolean migrationInProgress) {
      this.migrationInProgress = migrationInProgress;
   }

   public BackEnd(String name, DeploymentMBean deployedMBean, String configType, JMSService svc, String alternativeKernelName) throws ManagementException {
      this(name, deployedMBean, configType, svc);
      this.alternativeKernelName = alternativeKernelName;
   }

   public String getAlternativeKernelName() {
      return this.alternativeKernelName;
   }

   public BackEnd(String name, DeploymentMBean deployedMBean, String configType, JMSService svc) throws ManagementException {
      this.BEQuotas = new HashMap();
      this.isHostingTemporaryDestinations = false;
      this.durableSubscribers = new HashMap();
      this.jms2NonDurableSharedSubscriptions = new HashMap();
      this.serverSessionPoolsByName = new HashMap();
      this.serverSessionPoolsById = new HashMap();
      this.name2Destination = new HashMap();
      this.createId2DestinationMapHandler = new CreateId2DestinationMapHandler();
      this.pagingConfig = new HashMap();
      this.state = 0;
      this.pausedState = this.state;
      this.shutdownLock = new Object();
      this.destinationDeletionLock = new Object();
      this.backEndHealthException = null;
      this.jmsMessageLogger = null;
      this.productionPausedAtStartup = "default";
      this.insertionPausedAtStartup = "default";
      this.consumptionPausedAtStartup = "default";
      this.migrationInProgress = false;
      this.name = name;
      this.originalScopeMBean = PathHelper.getOriginalScopeMBean(deployedMBean);
      this.configName = deployedMBean.getName();
      this.configType = configType;
      this.jmsService = svc;
      if (this.jmsService == null) {
         throw new ManagementException("JMSService is not active");
      } else {
         this.backEndId = this.jmsService.getNextServerId();
         this.initializeWorkManagers();

         try {
            HashMap props = new HashMap(2);
            props.put("WorkManager", this.workManager);
            props.put("LimitedWorkManager", this.limitedWorkManager);
            this.kernel = new KernelImpl(this.name, props);
            this.thresholdHandler = new BEThresholdHandler(this.kernel, this.name);
            this.multiSender = new BEMultiSender();
            this.backEndQuota = createQuota(this.kernel, this.name + ".Quota." + System.currentTimeMillis(), Long.MAX_VALUE, Integer.MAX_VALUE, QuotaPolicy.FIFO);
         } catch (KernelException var6) {
            throw new DeploymentException(var6);
         }

         this.invocableMonitor = new InvocableMonitor(this.jmsService.getInvocableMonitor());
         this.serverSessionPoolFactory = new BEServerSessionPoolFactory(this);
         this.startupTime = System.currentTimeMillis();
         LowMemoryNotificationService.addMemoryListener(this);
         RuntimeMBean restParent = this.jmsService;
         this.runtimeMBean = new JMSServerRuntimeMBeanImpl(this, PartitionNameUtils.stripDecoratedPartitionName(name), false, restParent);
         HealthMonitorService.register("JMSServer." + name, this.runtimeMBean, false);
         this.state = 1;
      }
   }

   private static Quota createQuota(Kernel kernel, String name, long bytesMaximum, int messagesMaximum, QuotaPolicy policy) throws KernelException {
      Quota quota = kernel.createQuota(name);
      quota.setPolicy(policy);
      quota.setBytesMaximum(bytesMaximum);
      quota.setMessagesMaximum(messagesMaximum);
      return quota;
   }

   BEQuota findBEQuota(String quotaName) {
      synchronized(this.BEQuotas) {
         return (BEQuota)this.BEQuotas.get(quotaName);
      }
   }

   BEQuota createBEQuota(String quotaName, QuotaBean quotaBean) throws BeanUpdateFailedException {
      int messagesMaximum = quotaBean.getMessagesMaximum() > 2147483647L ? Integer.MAX_VALUE : (int)quotaBean.getMessagesMaximum();

      Quota kernelQuota;
      try {
         kernelQuota = createQuota(this.kernel, quotaName, quotaBean.getBytesMaximum(), messagesMaximum, QuotaPolicy.get(quotaBean.getPolicy()));
      } catch (KernelException var9) {
         throw new BeanUpdateFailedException(var9.getMessage(), var9);
      }

      BEQuota retVal = new BEQuota(quotaName, kernelQuota);
      synchronized(this.BEQuotas) {
         this.BEQuotas.put(quotaName, retVal);
         return retVal;
      }
   }

   void removeBEQuota(String quotaName) {
      BEQuota byeBye = this.findBEQuota(quotaName);
      if (byeBye != null) {
         this.kernel.deleteQuota(quotaName);
      }

      synchronized(this.BEQuotas) {
         this.BEQuotas.remove(quotaName);
      }
   }

   WorkManager getWorkManager() {
      return this.workManager;
   }

   WorkManager getThreadLimitedWorkManager() {
      return this.limitedWorkManager;
   }

   public WorkManager getAsyncPushWorkManager() {
      return this.asyncPushWorkManager;
   }

   public WorkManager getForwarderWorkManager() {
      return this.forwarderWorkManager;
   }

   private void initializeWorkManagers() {
      ServerMBean server = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
      if (server.getUse81StyleExecuteQueues()) {
         this.workManager = WorkManagerFactory.getInstance().getDefault();
         this.limitedWorkManager = WorkManagerFactory.getInstance().getDefault();
         this.asyncPushWorkManager = WorkManagerFactory.getInstance().getDefault();
         this.forwarderWorkManager = WorkManagerFactory.getInstance().getDefault();
      } else {
         this.workManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.jms." + PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("@", this.name) + ".System", 100, 1, -1);
         this.limitedWorkManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.jms." + PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("@", this.name) + ".Limited", 1, 8);
         this.asyncPushWorkManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.jms." + PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("@", this.name) + ".AsyncPush", 100, 1, -1);
         this.forwarderWorkManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.jms." + PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("@", this.name) + ".Forwarder", 100, 1, -1);
      }

      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms." + this.name, this.workManager);
   }

   public JMSMessageLogger getJMSMessageLogger() {
      return this.jmsMessageLogger;
   }

   public void setPagingDirectory(String pagingDir) {
      this.pagingDirectory = pagingDir;
   }

   public void setPagingFileLockingEnabled(boolean val) {
      this.pagingConfig.put("FileLockingEnabled", val);
   }

   public void setPagingMinWindowBufferSize(int val) {
      this.pagingConfig.put("MinWindowBufferSize", val);
   }

   public void setPagingMaxWindowBufferSize(int val) {
      this.pagingConfig.put("MaxWindowBufferSize", val);
   }

   public void setPagingIoBufferSize(int val) {
      this.pagingConfig.put("IoBufferSize", val);
   }

   public void setPagingBlockSize(int val) {
      this.pagingConfig.put("BlockSize", val);
   }

   public void setPagingMaxFileSize(long val) {
      this.pagingConfig.put("MaxFileSize", val);
   }

   public void setPersistentStore(PersistentStoreXA persistentStore) {
      this.persistentStore = persistentStore;
   }

   public PersistentStoreXA getPersistentStore() {
      return this.persistentStore;
   }

   boolean isStoreEnabled() {
      return this.persistentStore != null;
   }

   boolean isAllowsPersistentDowngrade() {
      return this.downgradeable;
   }

   public void setAllowsPersistentDowngrade(boolean paramDowngradeable) {
      this.downgradeable = paramDowngradeable;
   }

   public void setJMSMessageLogger(JMSMessageLogger jmsMessageLogger) {
      this.jmsMessageLogger = jmsMessageLogger;
   }

   BEDurableSubscriptionStore getDurableSubscriptionStore() {
      return this.durableSubscriptionStore;
   }

   BEMultiSender getMultiSender() {
      return this.multiSender;
   }

   public JMSServerId getJMSServerId() {
      return this.backEndId;
   }

   public Kernel getKernel() {
      return this.kernel;
   }

   public void setBlockingSendPolicy(String newVal) {
      if (newVal == null) {
         JMSLogger.logIllegalThresholdValue(this.name, "BlockingSendPolicy");
         throw new IllegalArgumentException("Illegal BlockingSendPolicy value of null");
      } else {
         QuotaPolicy newPolicy = QuotaPolicy.get(newVal);
         if (newPolicy != this.blockingSendPolicy) {
            this.blockingSendPolicy = newPolicy;
            this.backEndQuota.setPolicy(this.blockingSendPolicy);
         }

      }
   }

   public HealthState getHealthState() {
      HealthState healthState = null;
      if (this.backEndHealthException != null) {
         Symptom symptom = new Symptom(SymptomType.JMS_ERROR, Severity.HIGH, this.name, this.backEndHealthException.toString());
         healthState = new HealthState(3, symptom);
      } else {
         if (this.persistentStore != null) {
            ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
            PersistentStoreRuntimeMBean persistentStoreRuntime = serverRuntime.lookupPersistentStoreRuntime(this.persistentStore.getName());
            if (persistentStoreRuntime != null) {
               HealthState storeHealthState = persistentStoreRuntime.getHealthState();
               if (storeHealthState.getState() != 0) {
                  healthState = storeHealthState;
               }
            }
         }

         if (this.thresholdHandler.isArmed() && healthState == null) {
            int health = 0;
            ArrayList symptoms = new ArrayList(6);
            long timeNow = System.currentTimeMillis();
            double runningTime = (double)(timeNow - this.startupTime);
            long thresholdTime = this.thresholdHandler.getMessagesThresholdTime();
            if (thresholdTime > 3600000L) {
               symptoms.add(new Symptom(SymptomType.JMS_MSG_THRESHOLD_TIME_EXCEEDED, Severity.MEDIUM, this.name, JMSExceptionLogger.logMessagesThresholdTimeExceededLoggable(this.name).getMessage()));
               health = 1;
            } else if ((double)thresholdTime / runningTime > 0.9) {
               symptoms.add(new Symptom(SymptomType.JMS_MSG_THRESHOLD_RUNNINGTIME_EXCEEDED, Severity.MEDIUM, this.name, JMSExceptionLogger.logMessagesThresholdRunningTimeExceededLoggable(this.name).getMessage()));
               health = 1;
            }

            thresholdTime = this.thresholdHandler.getBytesThresholdTime();
            if (thresholdTime > 3600000L) {
               symptoms.add(new Symptom(SymptomType.JMS_BYTES_THRESHOLD_TIME_EXCEEDED, Severity.MEDIUM, this.name, JMSExceptionLogger.logBytesThresholdTimeExceededLoggable(this.name).getMessage()));
               health = 1;
            } else if ((double)thresholdTime / runningTime > 0.9) {
               symptoms.add(new Symptom(SymptomType.JMS_BYTES_THRESHOLD_RUNNINGTIME_EXCEEDED, Severity.MEDIUM, this.name, JMSExceptionLogger.logBytesThresholdRunningTimeExceededLoggable(this.name).getMessage()));
               health = 1;
            }

            if (symptoms.isEmpty()) {
               healthState = new HealthState(health);
            } else {
               healthState = new HealthState(health, (Symptom[])symptoms.toArray(new Symptom[symptoms.size()]));
            }
         }
      }

      if (healthState == null) {
         healthState = new HealthState(0);
      }

      healthState.setSubsystemName("JMSServer." + this.name);
      healthState.setCritical(false);
      healthState.setMBeanName(this.name);
      healthState.setMBeanType(this.configType);
      return healthState;
   }

   public void destroy() {
      if (this.temporaryModule != null) {
         synchronized(this.temporaryModule) {
            this.temporaryModule.close();
         }

         this.temporaryModule = null;
      }

      LowMemoryNotificationService.removeMemoryListener(this);
      HealthMonitorService.unregister("JMSServer." + this.name);
      if (this.runtimeMBean != null) {
         ((JMSServerRuntimeMBeanImpl)this.runtimeMBean).backendDestroyed();
      }

   }

   TimerManager getTimerManager() {
      return this.timerManager;
   }

   private final void createServerSessionPools() throws JMSException {
      if (this.sessionPoolMBeans != null) {
         if (this.sessionPoolMBeans.length > 0 && ServerSessionPoolHelper.isParentRGT(this.originalScopeMBean)) {
            throw new JMSException("Server Session Pool cannot be created inside an RG/RGT");
         } else {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("About to process " + this.sessionPoolMBeans.length + " sessionpools");
            }

            BEServerSessionPool serverSessionPool;
            for(int i = 0; i < this.sessionPoolMBeans.length; ++i) {
               JMSService var10000 = this.jmsService;
               JMSID serverSessionPoolId = JMSService.getNextId();

               try {
                  serverSessionPool = new BEServerSessionPool(this.sessionPoolMBeans[i].getName(), serverSessionPoolId, this, this.sessionPoolMBeans[i]);
                  this.serverSessionPoolAdd(serverSessionPool);
               } catch (Exception var7) {
                  JMSLogger.logErrorCreateSSP(this.name, this.sessionPoolMBeans[i].getName(), var7);
                  throw new weblogic.jms.common.JMSException("Error creating ServerSessionPool " + this.sessionPoolMBeans[i], var7);
               }
            }

            if (this.sessionPoolMBeans.length > 0) {
               JMSLogger.logServerSessionPoolsDeprecated();
            }

            JMSLogger.logCntPools(this.name, this.sessionPoolMBeans.length);
            Iterator iterator;
            synchronized(this.shutdownLock) {
               iterator = ((HashMap)this.serverSessionPoolsById.clone()).values().iterator();
            }

            while(iterator.hasNext()) {
               serverSessionPool = (BEServerSessionPool)iterator.next();
               serverSessionPool.start();
            }

            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug(this.sessionPoolMBeans.length + " sessionpools are created");
            }

         }
      }
   }

   private final void destroyServerSessionPools() {
      Iterator iterator;
      synchronized(this.shutdownLock) {
         iterator = ((HashMap)this.serverSessionPoolsByName.clone()).values().iterator();
      }

      while(iterator.hasNext()) {
         this.serverSessionPoolRemove((BEServerSessionPool)iterator.next());
      }

      synchronized(this.shutdownLock) {
         this.serverSessionPoolsByName.clear();
         this.serverSessionPoolsById.clear();
      }
   }

   private void unAdvertise() {
      if (this.bound) {
         this.bound = false;
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("unAdvertise backend " + this.getShortName() + " isMigratable " + this.isMigratable + " migrationInProgress " + this.migrationInProgress);
         }

         if (this.tempDestinationFactory != null) {
            try {
               this.jmsService.getBEDeployer().removeTempDestinationFactory(this.tempDestinationFactory);
               this.tempDestinationFactory = null;
            } catch (NamingException var4) {
            }
         }

         this.bound = false;

         try {
            PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(), "weblogic.jms.backend." + this.name, KERNEL_ID);
         } catch (NamingException var3) {
         }

         if (this.serverSessionPoolFactory != null) {
            try {
               PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(), "weblogic.jms.ServerSessionPoolFactory:" + this.getShortName(), KERNEL_ID);
            } catch (NamingException var2) {
            }
         }

         if (this.migrationInProgress && this.isLateDeactivate()) {
            this.setLateMigrationDeactivate(true);
         } else {
            this.setLateMigrationDeactivate(false);
         }

      }
   }

   private boolean isLateDeactivate() {
      try {
         String myHost = this.backEndId.getDispatcherId() != null ? this.backEndId.getDispatcherId().getName() : "";
         ClusterServices cm = Locator.locateClusterServices();
         Leasing ls = cm.getSingletonLeasingService();
         String leaseOwner = this.unwrapServerID(ls.findOwner(this.targetName));
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Check for LATE migration deactivate for " + this.getShortName() + " on " + this.targetName + " leaseOwner " + leaseOwner + " myHost " + myHost);
         }

         if (leaseOwner != null && !leaseOwner.equals(myHost)) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Detected LATE migration deactivate for " + this.getName() + " on " + this.targetName + " leaseOwner " + leaseOwner + " myHost " + myHost);
            }

            return true;
         }
      } catch (Exception var5) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Unexpected exception occurred while checking for late deactivate " + var5);
         }
      }

      return false;
   }

   private String unwrapServerID(String serverID) {
      if (serverID == null) {
         return null;
      } else {
         return serverID.indexOf("/") == -1 ? serverID : serverID.substring(serverID.indexOf("/") + 1, serverID.length());
      }
   }

   private void advertise() throws JMSException {
      try {
         this.activateFinished();
      } catch (BeanUpdateFailedException var43) {
         throw new weblogic.jms.common.JMSException(var43);
      }

      this.setLateMigrationDeactivate(false);
      boolean success = false;

      try {
         try {
            PrivilegedActionUtilities.bindAsSU(this.jmsService.getCtx(), "weblogic.jms.backend." + this.name, this.backEndId, KERNEL_ID);
         } catch (NamingException var45) {
            try {
               PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(), "weblogic.jms.backend." + this.name, this.backEndId, KERNEL_ID);
            } catch (NamingException var42) {
            }

            try {
               PrivilegedActionUtilities.bindAsSU(this.jmsService.getCtx(), "weblogic.jms.backend." + this.name, this.backEndId, KERNEL_ID);
            } catch (NamingException var44) {
               if (JMSDebug.JMSConfig.isDebugEnabled()) {
                  JMSDebug.JMSConfig.debug("Failed to bind backend to jndiname");
               }

               JMSLogger.logBackEndBindingFailed(this.name, "weblogic.jms.backend." + this.name);
               throw new weblogic.jms.common.JMSException("Error binding JMSServer into JNDI", var45);
            }
         }

         success = true;
      } finally {
         if (!success && this.tempDestinationFactory != null) {
            try {
               this.jmsService.getBEDeployer().removeTempDestinationFactory(this.tempDestinationFactory);
               this.tempDestinationFactory = null;
            } catch (NamingException var38) {
            }
         }

      }

      success = false;

      try {
         if (this.serverSessionPoolFactory != null) {
            try {
               PrivilegedActionUtilities.bindAsSU(this.jmsService.getCtx(), "weblogic.jms.ServerSessionPoolFactory:" + this.getShortName(), this.serverSessionPoolFactory, KERNEL_ID);
            } catch (NamingException var41) {
               try {
                  PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(), "weblogic.jms.ServerSessionPoolFactory:" + this.getShortName(), this.serverSessionPoolFactory, KERNEL_ID);
               } catch (NamingException var40) {
               }

               try {
                  PrivilegedActionUtilities.bindAsSU(this.jmsService.getCtx(), "weblogic.jms.ServerSessionPoolFactory:" + this.getShortName(), this.serverSessionPoolFactory, KERNEL_ID);
               } catch (NamingException var39) {
                  throw new weblogic.jms.common.JMSException("Error binding weblogic.jms.ServerSessionPoolFactory:" + this.getShortName() + var41);
               }
            }
         }

         success = true;
      } finally {
         if (!success) {
            try {
               PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(), "weblogic.jms.backend." + this.name, KERNEL_ID);
            } catch (NamingException var37) {
            }

            if (this.tempDestinationFactory != null) {
               try {
                  this.jmsService.getBEDeployer().removeTempDestinationFactory(this.tempDestinationFactory);
                  this.tempDestinationFactory = null;
               } catch (NamingException var36) {
               }
            }
         }

      }

      this.bound = true;
   }

   public void close() {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Suspending backend " + this.name + " isMigratable " + this.isMigratable + " migrationInProgress " + this.migrationInProgress);
      }

      JMSLogger.logJMSServerSuspending(this.name);
      boolean hasNotBeenSuspending = false;
      synchronized(this.shutdownLock) {
         if ((this.state & 8) == 0) {
            this.markShuttingDown();
            hasNotBeenSuspending = true;
         }
      }

      Iterator iterator;
      synchronized(this.shutdownLock) {
         synchronized(this.durableSubscribers) {
            iterator = ((HashMap)this.durableSubscribers.clone()).values().iterator();
         }
      }

      while(iterator.hasNext()) {
         try {
            DurableSubscription durSub = (DurableSubscription)iterator.next();
            durSub.close();
         } catch (JMSException var19) {
            JMSLogger.logJMSServerShutdownError(this.name, var19.toString(), var19);
         }
      }

      synchronized(this.shutdownLock) {
         synchronized(this.durableSubscribers) {
            this.durableSubscribers.clear();
         }
      }

      if (hasNotBeenSuspending) {
         this.invocableMonitor.waitForInvocablesCompletion();
      }

      synchronized(this.shutdownLock) {
         iterator = ((HashMap)this.name2Destination.clone()).values().iterator();
      }

      while(iterator.hasNext()) {
         this.removeDestination((BEDestinationImpl)iterator.next());
      }

      synchronized(this.shutdownLock) {
         this.name2Destination.clear();
         this.createId2DestinationMapHandler.clear();
      }

      this.timerManager.suspend();

      try {
         PrivilegedActionUtilities.unregister((RuntimeMBeanDelegate)this.runtimeMBean, KERNEL_ID);
      } catch (Exception var14) {
         JMSLogger.logErrorUnregisterJMSServer(this.name, var14);
      }

      try {
         if (this.durableSubscriptionStore != null) {
            this.durableSubscriptionStore.close();
         }

         if (this.kernel != null) {
            this.kernel.close();
         }
      } catch (KernelException var13) {
         JMSLogger.logJMSServerShutdownError(this.name, var13.toString(), var13);
      }

      synchronized(this.shutdownLock) {
         this.state = 16;
      }

      JMSLogger.logJMSServerSuspended(this.name);
   }

   public void open() throws JMSException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("About to start backend " + this.name + " and state =" + this.state);
      }

      JMSLogger.logJMSServerResuming(this.name);
      boolean var20 = false;

      label206: {
         try {
            var20 = true;
            synchronized(this.shutdownLock) {
               if ((this.state & 4) != 0) {
                  var20 = false;
                  break label206;
               }

               if ((this.state & 1) == 0 && (this.state & 16) == 0) {
                  throw new weblogic.jms.common.JMSException("Failed to initialize JMSServer " + this.name + ": wrong state");
               }

               this.state = 1;
               this.state |= 128;
            }

            try {
               this.kernel.setProperty("Store", this.persistentStore);
               this.kernel.setProperty("ObjectHandler", this.getJMSObjectHandler());
               this.kernel.setProperty("PagingDirectory", this.pagingDirectory);
               this.kernel.setProperty("PagingParams", this.pagingConfig);
               if (this.alternativeKernelName != null) {
                  this.kernel.setProperty("AlternativeKernelName", this.alternativeKernelName);
               }

               this.kernel.open();
            } catch (KernelException var29) {
               throw new weblogic.jms.common.JMSException(var29);
            }

            if (this.isStoreEnabled()) {
               this.durableSubscriptionStore = new BEDurableSubscriptionStore(this.name, this.persistentStore);
               this.durableSubscriptionStore.recover();
            }

            this.timerManager.resume();

            try {
               PrivilegedActionUtilities.register((RuntimeMBeanDelegate)this.runtimeMBean, KERNEL_ID);
            } catch (ManagementException var27) {
               try {
                  PrivilegedActionUtilities.unregister((RuntimeMBeanDelegate)this.runtimeMBean, KERNEL_ID);
               } catch (ManagementException var26) {
               }

               try {
                  PrivilegedActionUtilities.register((RuntimeMBeanDelegate)this.runtimeMBean, KERNEL_ID);
               } catch (ManagementException var25) {
                  throw new weblogic.jms.common.JMSException("Error in resuming JMS server " + this.name, var27);
               }
            }

            this.advertise();
            synchronized(this.shutdownLock) {
               this.checkShutdown();
               this.state = 4;
               var20 = false;
            }
         } finally {
            if (var20) {
               synchronized(this.shutdownLock) {
                  this.state &= -129;
               }
            }
         }

         synchronized(this.shutdownLock) {
            this.state &= -129;
            return;
         }
      }

      synchronized(this.shutdownLock) {
         this.state &= -129;
      }
   }

   private ObjectHandler getJMSObjectHandler() {
      JMSObjectHandler jmsObjectHandler = new JMSObjectHandler();
      return (ObjectHandler)(ObjectMessageImpl.isTestStoreExceptionEnabled() ? new ObjectHandlerTestStoreException(jmsObjectHandler) : jmsObjectHandler);
   }

   public void markShuttingDown() {
      boolean needPreSuspendOrShutdown = false;
      synchronized(this.shutdownLock) {
         if ((this.state & 16) != 0) {
            return;
         }

         needPreSuspendOrShutdown = (this.state & 3) == 0;
         this.state = 8;
         Iterator iterator = this.name2Destination.values().iterator();

         while(iterator.hasNext()) {
            ((BEDestinationImpl)iterator.next()).markShuttingDown();
         }

         iterator = this.serverSessionPoolsByName.values().iterator();

         while(true) {
            if (!iterator.hasNext()) {
               this.removeAccessor();
               break;
            }

            ((BEServerSessionPool)iterator.next()).markShuttingDown();
         }
      }

      if (needPreSuspendOrShutdown) {
         this.preSuspendOrShutdown();
      }

   }

   private boolean isShutdown() {
      return (this.state & 24) != 0;
   }

   private boolean isShutdownOrSuspended() {
      return this.isShutdown() || (this.state & 3) != 0;
   }

   private void checkShutdown() throws JMSException {
      if (this.isShutdown()) {
         throw new weblogic.jms.common.JMSException("JMSServer is shutdown");
      }
   }

   private void checkShutdown(String operation) throws JMSException {
      if (this.isShutdown()) {
         throw new weblogic.jms.common.JMSException("Failed to " + operation + " because JMSServer is shutdown");
      }
   }

   public void checkShutdownOrSuspended(String operation) throws JMSException {
      if (this.isShutdownOrSuspended()) {
         throw new weblogic.jms.common.JMSException("Failed to " + operation + " because JMSServer is shutdown or suspended");
      }
   }

   public void checkShutdownNeedLock(String operation) throws JMSException {
      synchronized(this.shutdownLock) {
         if (this.isShutdown()) {
            throw new weblogic.jms.common.JMSException("Failed to " + operation + " because JMSServer is shutdown");
         }
      }
   }

   public void checkShutdownOrSuspendedNeedLock(String operation) throws JMSException {
      synchronized(this.shutdownLock) {
         if (this.isShutdown() || (this.state & 3) != 0) {
            throw new weblogic.jms.common.JMSException("Failed to " + operation + " because JMSServer " + this.name + " is shutdown or suspended " + this.state);
         }
      }
   }

   private void preSuspendOrShutdown() {
      this.unAdvertise();
      this.destroyServerSessionPools();
   }

   public void setSessionPoolMBeans(JMSSessionPoolMBean[] sessionPoolMBeans) {
      this.sessionPoolMBeans = sessionPoolMBeans;
   }

   private void serverSessionPoolAdd(BEServerSessionPool serverSessionPool) throws JMSException {
      synchronized(this.shutdownLock) {
         this.checkShutdown("create server session pool");
         if (this.serverSessionPoolsById.put(serverSessionPool.getId(), serverSessionPool) == null && this.serverSessionPoolsByName.put(serverSessionPool.getName(), serverSessionPool) == null) {
            if (++this.serverSessionPoolsCurrentCount > this.serverSessionPoolsHighCount) {
               this.serverSessionPoolsHighCount = this.serverSessionPoolsCurrentCount;
            }

            ++this.serverSessionPoolsTotalCount;
         }

      }
   }

   private int serverSessionGet(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("get server session");
      BEServerSessionGetRequest request = (BEServerSessionGetRequest)invocableRequest;
      BEServerSessionPool ssp = this.serverSessionPoolFind(request.getServerSessionPoolId());
      if (ssp != null) {
         request.setResult(new BEServerSessionGetResponse((BEServerSession)ssp.getServerSession(ssp.getBackEndId().getDispatcherId())));
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      } else {
         throw new JMSException("Server session pool not found");
      }
   }

   private int serverSessionPoolCreate(Request invocableRequest) throws JMSException {
      if (ServerSessionPoolHelper.isParentRGT(this.originalScopeMBean)) {
         throw new JMSException("Server Session Pool cannot be created inside an RG/RGT");
      } else {
         this.checkShutdownOrSuspendedNeedLock("create server session pool");
         BEServerSessionPoolCreateRequest request = (BEServerSessionPoolCreateRequest)invocableRequest;
         JMSService var10000 = this.jmsService;
         final JMSID serverSessionPoolId = JMSService.getNextId();
         final BackEnd finalThis = this;
         final JMSConnectionFactory finalCf = request.getConnectionFactory();
         final int finalSessionsMaximum = request.getSessionsMaximum();
         final int finalAcknowledgeMode = request.getAcknowledgeMode();
         final boolean finalTransacted = request.isTransacted();
         final String finalListenerClass = request.getMessageListenerClass();
         final Serializable finalClientData = request.getClientData();
         final String finalName = "ServerSessionPool" + serverSessionPoolId.getCounter();

         BEServerSessionPool serverSessionPool;
         try {
            try {
               serverSessionPool = (BEServerSessionPool)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
                  public Object run() throws ManagementException, JMSException {
                     return new BEServerSessionPool(finalName, serverSessionPoolId, finalThis, finalCf, finalSessionsMaximum, finalAcknowledgeMode, finalTransacted, finalListenerClass, finalClientData);
                  }
               });
            } catch (PrivilegedActionException var14) {
               throw var14.getException();
            }

            this.serverSessionPoolAdd(serverSessionPool);
         } catch (Exception var15) {
            throw new weblogic.jms.common.JMSException("Error create server session pool", var15);
         }

         request.setResult(new JMSServerSessionPoolCreateResponse((ServerSessionPool)serverSessionPool.getRemoteWrapper()));
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      }
   }

   private int serverSessionPoolRemove(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("remove server session pool");
      BEServerSessionPoolCloseRequest request = (BEServerSessionPoolCloseRequest)invocableRequest;
      BEServerSessionPool ssp = this.serverSessionPoolFind(request.getServerSessionPoolId());
      if (ssp != null) {
         this.serverSessionPoolRemove(ssp);
         ssp.close();
         request.setResult(new VoidResponse());
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      } else {
         throw new JMSException("Error removing server session pool: instance not found");
      }
   }

   public void serverSessionPoolRemove(BEServerSessionPool serverSessionPool) {
      synchronized(this.shutdownLock) {
         if (this.serverSessionPoolsByName.remove(serverSessionPool.getName()) != null || this.serverSessionPoolsById.remove(serverSessionPool.getId()) != null) {
            --this.serverSessionPoolsCurrentCount;
         }
      }

      serverSessionPool.shutdown();
   }

   public BEServerSessionPool serverSessionPoolFind(JMSID serverSessionPoolId) {
      synchronized(this.shutdownLock) {
         return (BEServerSessionPool)this.serverSessionPoolsById.get(serverSessionPoolId);
      }
   }

   synchronized Queue findKernelQueue(String name) {
      return this.kernel.findQueue(name);
   }

   Queue createKernelQueue(String name, Map properties) throws JMSException {
      return this.createKernelQueue(name, properties, (String)null);
   }

   Queue createKernelQueue(String name, Map properties, String alternativeName) throws JMSException {
      if (this.alternativeKernelName != null) {
         if (properties == null) {
            properties = new HashMap();
         }

         ((Map)properties).put("AlternativeSAFQueueName", alternativeName);
      }

      try {
         return this.kernel.createQueue(name, (Map)properties);
      } catch (KernelException var5) {
         throw new weblogic.jms.common.JMSException("Can't create kernel queue", var5);
      }
   }

   synchronized Topic findKernelTopic(String name) {
      return this.kernel.findTopic(name);
   }

   Topic createKernelTopic(String name, Map properties) throws JMSException {
      try {
         return this.kernel.createTopic(name, properties);
      } catch (KernelException var4) {
         throw new weblogic.jms.common.JMSException("Can't create kernel topic", var4);
      }
   }

   public BEDestinationImpl createTemporaryDestination(DispatcherId feDispatcherId, String beanType, JMSID connectionId, boolean connectionIsStopped, long startStopSequenceNumber, String connectionAddress) throws JMSException {
      BEDestinationImpl destination = null;
      if (this.temporaryModule == null) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logTemporaryTemplateNotConfiguredLoggable(this.name));
      } else {
         synchronized(this.temporaryModule) {
            EntityName tempName = this.temporaryModule.prepareCreateTemporaryDestination(beanType.equalsIgnoreCase("Queue"), this.temporaryCounter++);
            boolean success = false;

            try {
               destination = this.findDestination(tempName.toString());
               if (destination == null) {
                  throw new weblogic.jms.common.JMSException("ERROR: Creation of temporary destination failed to find the newly added destination");
               }

               destination.setConnectionId(connectionId);
               this.checkShutdownOrSuspendedNeedLock("create temporary destination");
               synchronized(this.shutdownLock) {
                  this.checkShutdownOrSuspended("create temporary destination");
                  BEConnection connection = null;
                  JMSDispatcher feDispatcher = null;

                  try {
                     feDispatcher = this.jmsService.dispatcherFindOrCreate(feDispatcherId);
                  } catch (DispatcherException var27) {
                     throw new weblogic.jms.common.JMSException("Error finding FE dispatcher: " + feDispatcherId, var27);
                  }

                  connection = this.jmsService.getBEManager().connectionFindOrCreate(connectionId, feDispatcher, connectionIsStopped, startStopSequenceNumber, connectionAddress);
                  connection.tempDestinationAdd(destination);
               }

               success = true;
            } finally {
               if (!success) {
                  try {
                     this.temporaryModule.rollbackCreateTemporaryDestination();
                  } catch (weblogic.jms.common.JMSException var26) {
                  }
               }

            }

            this.temporaryModule.activateCreateTemporaryDestination();
            return destination;
         }
      }
   }

   public void addDestination(BEDestinationImpl destination) throws JMSException {
      this.checkShutdown();
      synchronized(this.shutdownLock) {
         String destinationName = destination.getName();
         String createId = destination.getJMSCreateDestinationIdentifier();
         if (createId == null) {
            createId = destinationName;
         }

         if (this.createId2DestinationMapHandler.getHandlerMap().containsKey(createId)) {
            BEDestinationImpl nameConflictDestination = (BEDestinationImpl)this.createId2DestinationMapHandler.get(createId, false);
            String nameConflict = nameConflictDestination.getName();
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logCreateDestinationIdentifierNameConflictLoggable(this.name, createId, nameConflict, destinationName));
         } else {
            this.createId2DestinationMapHandler.put(createId, destination);
            if (this.name2Destination.containsKey(destinationName)) {
               this.createId2DestinationMapHandler.remove(createId);
               throw new JMSException(JMSExceptionLogger.logNameConflictLoggable(this.name, destinationName).getMessage());
            } else {
               this.name2Destination.put(destination.getName(), destination);

               try {
                  this.jmsService.getInvocableManagerDelegate().invocableAdd(20, destination);
                  if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                     JMSDebug.JMSDistTopic.debug("Added destination " + destination.getName() + destination.getJMSID());
                  }
               } catch (JMSException var8) {
                  this.name2Destination.remove(destination.getName());
                  this.createId2DestinationMapHandler.remove(createId);
                  throw var8;
               }

               ++this.destinationsTotalCount;
               this.destinationsHighCount = Math.max(this.destinationsHighCount, (long)this.name2Destination.size());
               if (JMSDebug.JMSBoot.isDebugEnabled()) {
                  JMSDebug.JMSBoot.debug("Configured destination " + destination);
               }

            }
         }
      }
   }

   public void removeDestination(BEDestinationImpl destination) {
      String createId = destination.getJMSCreateDestinationIdentifier();
      if (createId == null) {
         createId = destination.getName();
      }

      destination.markShuttingDown();
      destination.shutdown();
      synchronized(this.shutdownLock) {
         this.createId2DestinationMapHandler.remove(createId);
         this.name2Destination.remove(destination.getName());
         this.jmsService.getInvocableManagerDelegate().invocableRemove(20, destination.getJMSID());
      }
   }

   private int findDestination(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("find destination");
      BEDestinationCreateRequest request = (BEDestinationCreateRequest)invocableRequest;
      BEDestinationImpl beDestination;
      if (!request.isForCreateDestination()) {
         beDestination = this.findDestination(request.getDestinationName());
      } else {
         beDestination = this.findDestinationByCreateName(request.getDestinationName());
      }

      if (beDestination == null) {
         throw new weblogic.jms.common.JMSException("Destination " + request.getDestinationName() + " not found");
      } else if (request.getDestType() == 1 && beDestination.getDestinationImpl().isTopic()) {
         throw new weblogic.jms.common.JMSException("No destination " + request.getDestinationName() + " of type queue");
      } else if (request.getDestType() == 2 && beDestination.getDestinationImpl().isQueue()) {
         throw new weblogic.jms.common.JMSException("No destination " + request.getDestinationName() + " of type topic");
      } else if (beDestination.isStarted()) {
         request.setResult(new JMSDestinationCreateResponse(beDestination.getDestinationImpl()));
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      } else {
         throw new weblogic.jms.common.JMSException("Destination " + request.getDestinationName() + " not found");
      }
   }

   public BEDestinationImpl findDestination(String name) {
      synchronized(this.shutdownLock) {
         return (BEDestinationImpl)this.name2Destination.get(name);
      }
   }

   public BEDestinationImpl findDestinationByCreateName(String createName) {
      synchronized(this.shutdownLock) {
         return (BEDestinationImpl)this.createId2DestinationMapHandler.get(createName, true);
      }
   }

   public final long getBytesMaximum() {
      long bytesMax = this.backEndQuota.getBytesMaximum();
      return bytesMax == Long.MAX_VALUE ? -1L : bytesMax;
   }

   public void setBytesMaximum(long newVal) {
      if (newVal < 0L) {
         newVal = Long.MAX_VALUE;
      }

      this.backEndQuota.setBytesMaximum(newVal);
   }

   public long getBytesThresholdHigh() {
      return this.thresholdHandler.getBytesThresholdHigh();
   }

   public void setBytesThresholdHigh(long newVal) {
      this.thresholdHandler.setBytesThresholdHigh(newVal);
   }

   public long getBytesThresholdLow() {
      return this.thresholdHandler.getBytesThresholdLow();
   }

   public void setBytesThresholdLow(long newVal) {
      this.thresholdHandler.setBytesThresholdLow(newVal);
   }

   public final long getMessagesMaximum() {
      int msgsMax = this.backEndQuota.getMessagesMaximum();
      return msgsMax == Integer.MAX_VALUE ? -1L : (long)msgsMax;
   }

   public void setMessagesMaximum(long newVal) {
      if (newVal < 0L || newVal > 2147483647L) {
         newVal = 2147483647L;
      }

      this.backEndQuota.setMessagesMaximum((int)newVal);
   }

   public long getMessagesThresholdHigh() {
      return this.thresholdHandler.getMessagesThresholdHigh();
   }

   public void setMessagesThresholdHigh(long newVal) {
      this.thresholdHandler.setMessagesThresholdHigh(newVal);
   }

   public long getMessagesThresholdLow() {
      return this.thresholdHandler.getMessagesThresholdLow();
   }

   public void setMessagesThresholdLow(long newVal) {
      this.thresholdHandler.setMessagesThresholdLow(newVal);
   }

   public void setMaximumMessageSize(int newVal) {
      try {
         this.kernel.setProperty("MaximumMessageSize", new Integer(newVal));
      } catch (KernelException var3) {
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public void setMessageBufferSize(long newVal) {
      try {
         this.kernel.setProperty("MessageBufferSize", new Long(newVal));
      } catch (KernelException var4) {
         throw new IllegalArgumentException(var4.toString());
      }
   }

   public void setExpirationScanInterval(int newVal) {
   }

   public void setProductionPausedAtStartup(String productionPausedAtStartup) {
      this.productionPausedAtStartup = productionPausedAtStartup;
      synchronized(this.shutdownLock) {
         if ((this.state & 4) == 0) {
            if (productionPausedAtStartup.equals("true")) {
               this.pausedState |= 512;
               JMSLogger.logProductionPauseOfJMSServer(this.name);
            }

         }
      }
   }

   public String getProductionPausedAtStartup() {
      return this.productionPausedAtStartup;
   }

   public void setInsertionPausedAtStartup(String insertionPausedAtStartup) {
      this.insertionPausedAtStartup = insertionPausedAtStartup;
      synchronized(this.shutdownLock) {
         if ((this.state & 4) == 0) {
            if (insertionPausedAtStartup.equals("true")) {
               this.pausedState |= 4096;
               JMSLogger.logInsertionPauseOfJMSServer(this.name);
            }

         }
      }
   }

   public String getInsertionPausedAtStartup() {
      return this.insertionPausedAtStartup;
   }

   public void setConsumptionPausedAtStartup(String consumptionPausedAtStartup) {
      this.consumptionPausedAtStartup = consumptionPausedAtStartup;
      synchronized(this.shutdownLock) {
         if ((this.state & 4) == 0) {
            if (consumptionPausedAtStartup.equals("true")) {
               this.pausedState |= 32768;
               JMSLogger.logConsumptionPauseOfJMSServer(this.name);
            }

         }
      }
   }

   public String getConsumptionPausedAtStartup() {
      return this.consumptionPausedAtStartup;
   }

   public void startAddJMSSessionPools(JMSSessionPoolMBean addedValue) throws BeanUpdateRejectedException {
      if (ServerSessionPoolHelper.isParentRGT(this.originalScopeMBean)) {
         throw new BeanUpdateRejectedException("Server Session Pool cannot be created inside an RG/RGT");
      } else {
         String addedName = addedValue.getName();
         JMSService var10000 = this.jmsService;
         JMSID serverSessionPoolId = JMSService.getNextId();

         BEServerSessionPool serverSessionPool;
         try {
            serverSessionPool = new BEServerSessionPool(addedName, serverSessionPoolId, this, addedValue);
         } catch (ManagementException var8) {
            throw new BeanUpdateRejectedException("Could not create a session pool", var8);
         } catch (JMSException var9) {
            throw new BeanUpdateRejectedException("Could not create a session pool", var9);
         }

         synchronized(this.shutdownLock) {
            if (this.serverSessionPoolsByName.get(addedName) != null) {
               serverSessionPool.cleanup();
               if (JMSDebug.JMSConfig.isDebugEnabled()) {
                  JMSDebug.JMSConfig.debug("Error adding server session pool: instance already exists");
               }

               throw new BeanUpdateRejectedException("Error adding server session pool: instance already exists");
            } else {
               try {
                  this.serverSessionPoolAdd(serverSessionPool);
                  if ((this.state & 4) != 0) {
                     serverSessionPool.start();
                  }
               } catch (JMSException var10) {
                  throw new BeanUpdateRejectedException("Could not add or start a session pool", var10);
               }

            }
         }
      }
   }

   public void finishAddJMSSessionPools(JMSSessionPoolMBean addedValue, boolean isActivate) {
      if (!isActivate) {
         synchronized(this.shutdownLock) {
            BEServerSessionPool serverSessionPool = (BEServerSessionPool)this.serverSessionPoolsByName.get(addedValue.getName());
            if (serverSessionPool != null) {
               this.serverSessionPoolRemove(serverSessionPool);
            }
         }
      }
   }

   public void startRemoveJMSSessionPools(JMSSessionPoolMBean removedValue) throws BeanUpdateRejectedException {
      BEServerSessionPool serverSessionPool = null;
      synchronized(this.shutdownLock) {
         serverSessionPool = (BEServerSessionPool)this.serverSessionPoolsByName.get(removedValue.getName());
         if (serverSessionPool == null) {
            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("Error removing SessionPool: instance doesn't exist");
            }

            throw new BeanUpdateRejectedException("Error removing SessionPool: instance doesn't exist");
         }
      }
   }

   public void finishRemoveJMSSessionPools(JMSSessionPoolMBean removedValue, boolean isActivate) {
      if (isActivate) {
         BEServerSessionPool serverSessionPool = null;
         synchronized(this.shutdownLock) {
            serverSessionPool = (BEServerSessionPool)this.serverSessionPoolsByName.get(removedValue.getName());
            if (serverSessionPool != null) {
               this.serverSessionPoolRemove(serverSessionPool);
            }
         }
      }
   }

   public JMSSessionPoolRuntimeMBean[] getSessionPoolRuntimes() {
      synchronized(this.shutdownLock) {
         JMSSessionPoolRuntimeMBean[] serverSessionPoolRuntimeMBeans = new JMSSessionPoolRuntimeMBean[this.serverSessionPoolsByName.size()];
         Iterator iterator = this.serverSessionPoolsByName.values().iterator();

         for(int i = 0; iterator.hasNext(); ++i) {
            serverSessionPoolRuntimeMBeans[i] = (JMSSessionPoolRuntimeMBean)iterator.next();
         }

         return serverSessionPoolRuntimeMBeans;
      }
   }

   public BEServerSessionPool[] getSessionPools() {
      synchronized(this.shutdownLock) {
         BEServerSessionPool[] serverSessionPools = new BEServerSessionPool[this.serverSessionPoolsByName.size()];
         Iterator iterator = this.serverSessionPoolsByName.values().iterator();

         for(int i = 0; iterator.hasNext(); ++i) {
            serverSessionPools[i] = (BEServerSessionPool)iterator.next();
         }

         return serverSessionPools;
      }
   }

   public long getSessionPoolsCurrentCount() {
      synchronized(this.shutdownLock) {
         return this.serverSessionPoolsCurrentCount;
      }
   }

   public long getSessionPoolsHighCount() {
      synchronized(this.shutdownLock) {
         return this.serverSessionPoolsHighCount;
      }
   }

   public long getSessionPoolsTotalCount() {
      synchronized(this.shutdownLock) {
         return this.serverSessionPoolsTotalCount;
      }
   }

   public JMSDestinationRuntimeMBean[] getDestinations() {
      synchronized(this.shutdownLock) {
         JMSDestinationRuntimeMBean[] retValue = new JMSDestinationRuntimeMBean[this.name2Destination.size()];
         Iterator it = this.name2Destination.values().iterator();

         BEDestinationImpl destination;
         for(int i = 0; it.hasNext(); retValue[i++] = destination.getRuntimeMBean()) {
            destination = (BEDestinationImpl)it.next();
         }

         return retValue;
      }
   }

   public BEDestinationImpl[] getBEDestinations() {
      synchronized(this.shutdownLock) {
         BEDestinationImpl[] retValue = new BEDestinationImpl[this.name2Destination.size()];
         Iterator it = this.name2Destination.values().iterator();

         for(int i = 0; it.hasNext(); retValue[i++] = (BEDestinationImpl)it.next()) {
         }

         return retValue;
      }
   }

   public long getDestinationsCurrentCount() {
      synchronized(this.shutdownLock) {
         return (long)this.name2Destination.size();
      }
   }

   public long getDestinationsHighCount() {
      synchronized(this.shutdownLock) {
         return this.destinationsHighCount;
      }
   }

   public long getDestinationsTotalCount() {
      synchronized(this.shutdownLock) {
         return this.destinationsTotalCount;
      }
   }

   private int removeTempDestination(Request invocableRequest) throws JMSException {
      BETemporaryDestinationDestroyRequest request = (BETemporaryDestinationDestroyRequest)invocableRequest;

      try {
         this.checkShutdownOrSuspendedNeedLock("remove temporary destination");
      } catch (JMSException var8) {
         request.setResult(new VoidResponse());
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      }

      BEDestinationImpl beDestination = (BEDestinationImpl)this.jmsService.getInvocableManagerDelegate().invocableFind(20, request.getDestinationId());
      beDestination.deleteTempDestination();
      BEConnection connection = (BEConnection)this.jmsService.getInvocableManagerDelegate().invocableFind(15, beDestination.getConnectionId());
      connection.tempDestinationRemove(beDestination.getJMSID());
      synchronized(this.temporaryModule) {
         this.temporaryModule.removeTemporaryDestination(beDestination.getName());
      }

      request.setResult(new VoidResponse());
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   void addDurableSubscription(String name, DurableSubscription durSub) {
      synchronized(this.durableSubscribers) {
         this.durableSubscribers.put(name, durSub);
      }
   }

   void removeDurableSubscription(String name) {
      synchronized(this.durableSubscribers) {
         this.durableSubscribers.remove(name);
      }
   }

   Map getDurableSubscriptionsMap() {
      return this.durableSubscribers;
   }

   public DurableSubscription getDurableSubscription(String name) {
      synchronized(this.durableSubscribers) {
         return (DurableSubscription)this.durableSubscribers.get(name);
      }
   }

   public void logMessagesThresholdHigh() {
      JMSLogger.logMessagesThresholdHighServer(this.name);
   }

   NonDurableSubscription findJMS2NonDurableSharedSubscription(String key, NonDurableSubscription sub) throws InvalidSubscriptionSharingException {
      if (sub.getClientIdPolicy() != 0) {
         return null;
      } else {
         synchronized(this.jms2NonDurableSharedSubscriptions) {
            NonDurableSubscription s = (NonDurableSubscription)this.jms2NonDurableSharedSubscriptions.get(key);
            if (s != null && !s.equals(sub)) {
               throw new InvalidSubscriptionSharingException("XXXAMYShared non-durable subscription [" + sub + "] because there already exists one [" + s + "] with [" + key + "]");
            } else {
               return s;
            }
         }
      }
   }

   void addJMS2NonDurableSharedSubscription(String key, NonDurableSubscription sub) throws InvalidSubscriptionSharingException {
      if (sub.getClientIdPolicy() == 0) {
         synchronized(this.jms2NonDurableSharedSubscriptions) {
            NonDurableSubscription s = this.findJMS2NonDurableSharedSubscription(key, sub);
            if (s == null) {
               this.jms2NonDurableSharedSubscriptions.put(key, sub);
            }

         }
      }
   }

   void removeJMS2NonDurableSharedSubscription(String key, NonDurableSubscription sub) {
      if (sub.getClientIdPolicy() == 0) {
         synchronized(this.jms2NonDurableSharedSubscriptions) {
            this.jms2NonDurableSharedSubscriptions.remove(key);
         }
      }
   }

   public void logMessagesThresholdLow() {
      JMSLogger.logMessagesThresholdLowServer(this.name);
   }

   public void logBytesThresholdHigh() {
      JMSLogger.logBytesThresholdHighServer(this.name);
   }

   public void logBytesThresholdLow() {
      JMSLogger.logBytesThresholdLowServer(this.name);
   }

   public long getMessagesCurrentCount() {
      return (long)(this.kernel.getStatistics().getMessagesCurrent() - this.kernel.getStatistics().getMessagesPending());
   }

   public long getMessagesHighCount() {
      return (long)this.kernel.getStatistics().getMessagesHigh();
   }

   public long getMessagesPendingCount() {
      return (long)this.kernel.getStatistics().getMessagesPending();
   }

   public long getMessagesReceivedCount() {
      return this.kernel.getStatistics().getMessagesReceived();
   }

   public long getBytesCurrentCount() {
      return this.kernel.getStatistics().getBytesCurrent() - this.kernel.getStatistics().getBytesPending();
   }

   public long getBytesHighCount() {
      return this.kernel.getStatistics().getBytesHigh();
   }

   public long getBytesPendingCount() {
      return this.kernel.getStatistics().getBytesPending();
   }

   public long getBytesReceivedCount() {
      return this.kernel.getStatistics().getBytesReceived();
   }

   public long getMessagesThresholdTime() {
      return this.thresholdHandler.getMessagesThresholdTime();
   }

   public long getBytesThresholdTime() {
      return this.thresholdHandler.getBytesThresholdTime();
   }

   public int getMessagesPageableCurrentCount() {
      return this.kernel.getStatistics().getUnpagedMessages();
   }

   public long getBytesPageableCurrentCount() {
      return this.kernel.getStatistics().getUnpagedBytes();
   }

   public int getMessagesPagedOutTotalCount() {
      return this.kernel.getStatistics().getMessagesPagedOut();
   }

   public int getMessagesPagedInTotalCount() {
      return this.kernel.getStatistics().getMessagesPagedIn();
   }

   public long getBytesPagedOutTotalCount() {
      return this.kernel.getStatistics().getBytesPagedOut();
   }

   public long getBytesPagedInTotalCount() {
      return this.kernel.getStatistics().getBytesPagedIn();
   }

   public long getPagingAllocatedWindowBufferBytes() {
      return this.kernel.getStatistics().getPagingAllocatedWindowBufferBytes();
   }

   public long getPagingAllocatedIoBufferBytes() {
      return this.kernel.getStatistics().getPagingAllocatedIoBufferBytes();
   }

   public long getPagingPhysicalWriteCount() {
      return this.kernel.getStatistics().getPagingPhysicalWriteCount();
   }

   public String getName() {
      return this.name;
   }

   public String getConfigName() {
      return this.configName;
   }

   public void setStoreMessageCompressionEnabled(boolean paramStoreMessageCompressionEnabled) {
      this.storeMessageCompressionEnabled = paramStoreMessageCompressionEnabled;
   }

   public void setPagingMessageCompressionEnabled(boolean paramPagingMessageCompressionEnabled) {
      this.pagingMessageCompressionEnabled = paramPagingMessageCompressionEnabled;
   }

   public void setMessageCompressionOptions(String paramMessageCompressionOptions) {
      this.messageCompressionOptions = paramMessageCompressionOptions;
      this.messageCompressionOptionsProp = MessageImpl.convertCompressionOptionStringToProperty(this.messageCompressionOptions);
   }

   public void setMessageCompressionOptionsOverride(String paramMessageCompressionOptionsOverride) {
      this.messageCompressionOptionsOverride = paramMessageCompressionOptionsOverride;
      this.messageCompressionOptionsOverrideProp = MessageImpl.convertCompressionOptionOverrideStringToProperty(this.messageCompressionOptionsOverride);
   }

   public boolean isStoreMessageCompressionEnabled() {
      return this.storeMessageCompressionEnabled;
   }

   public boolean isPagingMessageCompressionEnabled() {
      return this.pagingMessageCompressionEnabled;
   }

   public String getMessageCompressionOptions() {
      return this.messageCompressionOptions;
   }

   public String getMessageCompressionOptionsOverride() {
      return this.messageCompressionOptionsOverride;
   }

   Properties getMessageCompressionOptionsProp() {
      return this.messageCompressionOptionsProp;
   }

   Properties getMessageCompressionOptionsOverrideProp() {
      return this.messageCompressionOptionsOverrideProp;
   }

   public String getConfigType() {
      return this.configType;
   }

   public String toString() {
      return "(" + System.identityHashCode(this) + ", name=" + this.name + ")";
   }

   public JMSID getJMSID() {
      return this.backEndId.getId();
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.jmsService.getDispatcherPartitionContext();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 11534:
            return this.findDestination(request);
         case 12302:
            return this.serverSessionGet(request);
         case 12558:
            return this.serverSessionPoolRemove(request);
         case 12814:
            return this.serverSessionPoolCreate(request);
         case 15118:
            return this.removeTempDestination(request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + request.getMethodId());
      }
   }

   protected void finalize() {
      try {
         super.finalize();
      } catch (Throwable var3) {
      }

      if (this.bound) {
         try {
            PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(), "weblogic.jms.backend." + this.name, KERNEL_ID);
            this.bound = false;
         } catch (Throwable var2) {
         }
      }

   }

   public Object getDestinationDeletionLock() {
      return this.destinationDeletionLock;
   }

   boolean needsFlowControl() {
      return this.isMemoryLow || this.thresholdHandler.isArmed();
   }

   public void memoryChanged(MemoryEvent event) {
      if (event.getEventType() == 1) {
         JMSLogger.logFlowControlEnabledDueToLowMemory(this.name);
         this.isMemoryLow = true;
         this.suspendMessageLogging();
      } else if (event.getEventType() == 0) {
         this.isMemoryLow = false;
         this.resumeMessageLogging();
      }

   }

   public void pauseProduction() throws JMSException {
      this.pauseProduction(true);
   }

   public void pauseProduction(boolean log) throws JMSException {
      synchronized(this.shutdownLock) {
         if (!this.isProductionPaused()) {
            this.pausedState |= 256;
            this.pauseDestinations("Production", log);
            this.pausedState &= -257;
            this.pausedState |= 512;
         }
      }

      if (log) {
         JMSLogger.logProductionPauseOfJMSServer(this.name);
      }

   }

   public void resumeProduction() throws JMSException {
      this.resumeProduction(true);
   }

   public void resumeProduction(boolean log) throws JMSException {
      synchronized(this.shutdownLock) {
         this.pausedState |= 1024;
         this.resumeDestinations("Production", log);
         this.pausedState &= -1025;
         this.pausedState &= -513;
      }

      if (log) {
         JMSLogger.logProductionResumeOfJMSServer(this.name);
      }

   }

   public boolean isProductionPaused() {
      return (this.pausedState & 768) != 0;
   }

   public String getProductionPausedState() {
      if ((this.pausedState & 256) != 0) {
         return "Production-Pausing";
      } else {
         return (this.pausedState & 512) != 0 ? "Production-Paused" : "Production-Enabled";
      }
   }

   public void pauseInsertion() throws JMSException {
      synchronized(this.shutdownLock) {
         this.pausedState |= 2048;
         this.pauseDestinations("Insertion", true);
         this.pausedState &= -2049;
         this.pausedState |= 4096;
      }

      JMSLogger.logInsertionPauseOfJMSServer(this.name);
   }

   public void resumeInsertion() throws JMSException {
      synchronized(this.shutdownLock) {
         this.pausedState |= 8192;
         this.resumeDestinations("Insertion", true);
         this.pausedState &= -8193;
         this.pausedState &= -4097;
      }

      JMSLogger.logInsertionResumeOfJMSServer(this.name);
   }

   public boolean isInsertionPaused() {
      return (this.pausedState & 6144) != 0;
   }

   public String getInsertionPausedState() {
      if ((this.pausedState & 2048) != 0) {
         return "Insertion-Pausing";
      } else {
         return (this.pausedState & 4096) != 0 ? "Insertion-Paused" : "Insertion-Enabled";
      }
   }

   public void pauseConsumption() throws JMSException {
      this.pauseConsumption(true);
   }

   public void pauseConsumption(boolean log) throws JMSException {
      synchronized(this.shutdownLock) {
         this.pausedState |= 16384;
         this.pauseDestinations("Consumption", log);
         this.pausedState &= -16385;
         this.pausedState |= 32768;
      }

      if (log) {
         JMSLogger.logConsumptionPauseOfJMSServer(this.name);
      }

   }

   public void resumeConsumption() throws JMSException {
      this.resumeConsumption(true);
   }

   public void resumeConsumption(boolean log) throws JMSException {
      synchronized(this.shutdownLock) {
         this.pausedState |= 65536;
         this.resumeDestinations("Consumption", log);
         this.pausedState &= -65537;
         this.pausedState &= -32769;
      }

      if (log) {
         JMSLogger.logConsumptionResumeOfJMSServer(this.name);
      }

   }

   public boolean isConsumptionPaused() {
      return (this.pausedState & '쀀') != 0;
   }

   public String getConsumptionPausedState() {
      if ((this.pausedState & 16384) != 0) {
         return "Consumption-Pausing";
      } else {
         return (this.pausedState & '耀') != 0 ? "Consumption-Paused" : "Consumption-Enabled";
      }
   }

   private void pauseDestinations(String operation, boolean log) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("pausing " + operation + " on all the destinations hosted by this JMSServer");
      BEDestinationImpl[] beDestinations = this.getBEDestinations();

      for(int i = 0; i < beDestinations.length; ++i) {
         BEDestinationImpl dest = beDestinations[i];
         if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
            JMSDebug.JMSPauseResume.debug("Performing " + operation + " pause operation on " + dest.getName());
         }

         if (operation.equals("Production")) {
            dest.pauseProduction(log);
         } else if (operation.equals("Insertion")) {
            dest.pauseInsertion();
         } else if (operation.equals("Consumption")) {
            dest.pauseConsumption(log);
         }
      }

   }

   private void resumeDestinations(String operation, boolean log) throws JMSException {
      this.checkShutdownOrSuspendedNeedLock("resuming " + operation + " on all the destinations hosted by this JMSServer");
      BEDestinationImpl[] beDestinations = this.getBEDestinations();

      for(int i = 0; i < beDestinations.length; ++i) {
         BEDestinationImpl dest = beDestinations[i];
         if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
            JMSDebug.JMSPauseResume.debug("Performing " + operation + " resume operation on " + dest.getName());
         }

         if (operation.equals("Production")) {
            dest.resumeProduction(log);
         } else if (operation.equals("Insertion")) {
            dest.resumeInsertion();
         } else if (operation.equals("Consumption")) {
            dest.resumeConsumption(log);
         }
      }

   }

   public Quota getQuota() {
      return this.backEndQuota;
   }

   public void postDeploymentsStart() {
      if ((this.state & 4) != 0) {
         try {
            this.createServerSessionPools();
         } catch (JMSException var7) {
            System.out.println("ERROR: Could not create server session pools: " + var7);
         }

         try {
            if (this.durableSubscriptionStore != null) {
               this.durableSubscriptionStore.deleteOrphanedSubscriptions();
            }
         } catch (JMSException var6) {
            JMSDebug.JMSBoot.debug("Error deleting orphaned topic subscriptions", var6);
         }

         Collection kernelDests = this.kernel.getDestinations();
         Iterator i = kernelDests.iterator();

         while(i.hasNext()) {
            Destination deadDest = (Destination)i.next();
            if (deadDest.isSuspended(16384) && !deadDest.isCreated()) {
               if (JMSDebug.JMSBoot.isDebugEnabled()) {
                  JMSDebug.JMSBoot.debug("Deleting orphaned kernel destination " + deadDest.getName());
               }

               try {
                  KernelRequest request = new KernelRequest();
                  deadDest.delete(request);
               } catch (KernelException var5) {
                  JMSDebug.JMSBoot.debug("Error deleting kernel destination", var5);
               }
            }
         }

         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug(this.name + "is started");
         }

         JMSLogger.logJMSServerDeployed(this.name);
      }
   }

   public void postDeploymentsStop() {
      if ((this.state & 16) != 0) {
         this.destroyServerSessionPools();
      }
   }

   private GXAResourceImpl getGXAResource() {
      PersistentStoreXA store = (PersistentStoreXA)this.kernel.getProperty("Store");
      if (store == null) {
         return null;
      } else {
         GXAResourceImpl gxa = null;

         try {
            gxa = (GXAResourceImpl)store.getGXAResource();
            return gxa;
         } catch (PersistentStoreException var4) {
            return null;
         }
      }
   }

   private String[] convertXidsToStrings(Xid[] xids) {
      if (xids == null) {
         return null;
      } else {
         String[] strings = new String[xids.length];

         for(int i = 0; i < strings.length; ++i) {
            strings[i] = xids[i].toString();
         }

         return strings;
      }
   }

   public String[] getTransactions() {
      GXAResourceImpl gxa = this.getGXAResource();
      return gxa == null ? null : this.convertXidsToStrings(gxa.getXIDs(-1));
   }

   public String[] getPendingTransactions() {
      GXAResourceImpl gxa = this.getGXAResource();
      return gxa == null ? null : this.convertXidsToStrings(gxa.getXIDs(3));
   }

   public Integer getTransactionStatus(String xidString) {
      GXAResourceImpl gxa = this.getGXAResource();
      if (gxa == null) {
         return new Integer(6);
      } else {
         Xid xid = XidImpl.create(xidString);
         int status = gxa.getStatus(xid);
         switch (status) {
            case 0:
            case 1:
               return new Integer(0);
            case 2:
               return new Integer(7);
            case 3:
               return new Integer(2);
            case 4:
               return new Integer(4);
            case 5:
               return new Integer(1);
            case 6:
               return new Integer(3);
            default:
               return new Integer(5);
         }
      }
   }

   public String getMessages(String xidString, Integer timeout) throws ManagementException {
      Xid xid = XidImpl.create(xidString);
      Cursor cursor = null;

      try {
         cursor = this.kernel.createCursor(xid);
      } catch (KernelException var6) {
         throw new ManagementException("Error creating message cursor for Xid " + xidString + " on server " + this.getName(), var6);
      }

      JMSMessageCursorDelegate delegate = new JMSMessageCursorDelegate((CursorRuntimeImpl)this.runtimeMBean, new JMSMessageOpenDataConverter(false), cursor, new JMSMessageOpenDataConverter(true), timeout);
      ((CursorRuntimeImpl)this.runtimeMBean).addCursorDelegate(delegate);
      return delegate.getHandle();
   }

   public Void forceCommit(String xidString) throws ManagementException {
      GXAResourceImpl gxa = this.getGXAResource();
      if (gxa == null) {
         throw new ManagementException("Resource not available for performing forceCommit operation.");
      } else {
         Xid xid = XidImpl.create(xidString);

         try {
            if (gxa.getStatus(xid) == 3) {
               gxa.commit(xid, false);
            } else {
               gxa.commit(xid, true);
            }

            JMSLogger.logAdminForceCommit(this.name, xidString);
            return null;
         } catch (XAException var5) {
            JMSLogger.logAdminForceCommitError(this.name, xidString, var5);
            throw new ManagementException("Error on forceCommit of JMS transaction branch " + xidString + ". ", var5);
         }
      }
   }

   public Void forceRollback(String xidString) throws ManagementException {
      GXAResourceImpl gxa = this.getGXAResource();
      if (gxa == null) {
         throw new ManagementException("Resource not available for performing forceRollback operation.");
      } else {
         Xid xid = XidImpl.create(xidString);

         try {
            gxa.rollback(xid);
            JMSLogger.logAdminForceRollback(this.name, xidString);
            return null;
         } catch (XAException var5) {
            JMSLogger.logAdminForceRollbackError(this.name, xidString, var5);
            throw new ManagementException("Error on forceRollback of JMS transaction branch " + xidString + ". ", var5);
         }
      }
   }

   private final void suspendMessageLogging() {
      BEDestinationImpl[] beDestinations = this.getBEDestinations();

      for(int i = 0; i < beDestinations.length; ++i) {
         BEDestinationImpl beDestination = beDestinations[i];
         if (beDestination.isMessageLoggingEnabled()) {
            try {
               beDestination.suspendMessageLogging();
            } catch (JMSException var5) {
            }
         }
      }

   }

   private final void resumeMessageLogging() {
      BEDestinationImpl[] beDestinations = this.getBEDestinations();

      for(int i = 0; i < beDestinations.length; ++i) {
         BEDestinationImpl beDestination = beDestinations[i];
         if (beDestination.isMessageLoggingEnabled()) {
            try {
               beDestination.resumeMessageLogging();
            } catch (JMSException var5) {
            }
         }
      }

   }

   public final boolean isMemoryLow() {
      return this.isMemoryLow;
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("JMSServer");
      xsw.writeAttribute("name", this.name != null ? this.name : "");
      xsw.writeAttribute("id", this.backEndId != null ? this.backEndId.toString() : "");
      String quotaName;
      if (this.backEndQuota == null || (quotaName = this.backEndQuota.getName()) == null) {
         quotaName = "";
      }

      xsw.writeAttribute("quotaName", quotaName);
      xsw.writeAttribute("destinationsHighCount", String.valueOf(this.destinationsHighCount));
      xsw.writeAttribute("destinationsTotalCount", String.valueOf(this.destinationsTotalCount));
      xsw.writeAttribute("serverSessionPoolsCurrentCount", String.valueOf(this.serverSessionPoolsCurrentCount));
      xsw.writeAttribute("serverSessionPoolsHighCount", String.valueOf(this.serverSessionPoolsHighCount));
      xsw.writeAttribute("serverSessionPoolsTotalCount", String.valueOf(this.serverSessionPoolsTotalCount));
      xsw.writeStartElement("Health");
      HealthState hs = this.getHealthState();
      JMSDiagnosticImageSource.dumpHealthStateElement(xsw, hs);
      xsw.writeEndElement();
      xsw.writeStartElement("Destinations");
      HashMap tempName2Destination = (HashMap)this.name2Destination.clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempName2Destination.size()));
      Iterator it = tempName2Destination.values().iterator();

      while(it.hasNext()) {
         BEDestinationImpl dest = (BEDestinationImpl)it.next();
         dest.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeStartElement("DurableSubscribers");
      HashMap tempDurableSubscribers = (HashMap)this.durableSubscribers.clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempDurableSubscribers.size()));
      it = tempDurableSubscribers.values().iterator();

      while(it.hasNext()) {
         DurableSubscription durSub = (DurableSubscription)it.next();
         if (durSub.getConsumer() != null) {
            durSub.getConsumer().dumpRef(imageSource, xsw);
         }
      }

      xsw.writeEndElement();
      xsw.writeStartElement("ServerSessionPools");
      BEServerSessionPool[] pools = this.getSessionPools();
      if (pools != null && pools.length != 0) {
         xsw.writeAttribute("currentCount", String.valueOf(pools.length));

         for(int i = 0; i < pools.length; ++i) {
            pools[i].dump(imageSource, xsw);
         }
      } else {
         xsw.writeAttribute("currentCount", "0");
      }

      xsw.writeEndElement();
      ((KernelImpl)this.kernel).dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   public void setHostingTemporaryDestinations(boolean isHosting) {
      this.isHostingTemporaryDestinations = isHosting;
   }

   public void setTemporaryTemplateResource(String paramTemporaryTemplateResource) {
      if (paramTemporaryTemplateResource == null) {
         this.temporaryTemplateResource = null;
      } else {
         this.temporaryTemplateResource = new ModuleName(paramTemporaryTemplateResource, (String)null);
      }

      if (this.temporaryModule != null) {
         this.temporaryModule.setAuxiliaryModuleName(this.temporaryTemplateResource);
      }

   }

   public void setTemporaryTemplateName(String paramTemporaryTemplateName) {
      this.temporaryTemplateName = paramTemporaryTemplateName;
   }

   public void activateFinished() throws BeanUpdateFailedException {
      if (this.isHostingTemporaryDestinations) {
         if (this.temporaryTemplateResource != null) {
            DomainMBean domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
            JMSSystemResourceMBean resource = null;
            if (this.originalScopeMBean instanceof DomainMBean) {
               resource = ((DomainMBean)this.originalScopeMBean).lookupJMSSystemResource(this.temporaryTemplateResource.toString());
            } else {
               if (!(this.originalScopeMBean instanceof ResourceGroupTemplateMBean)) {
                  throw new AssertionError("The scope of BackEnd must be an instance of either DomainMBean, ResourceGroupMBean or ResourceGroupTemplateMBean");
               }

               resource = ((ResourceGroupTemplateMBean)this.originalScopeMBean).lookupJMSSystemResource(this.temporaryTemplateResource.toString());
            }

            JMSBean resourceBean = resource.getJMSResource();
            TemplateBean domainTemporaryTemplate = resourceBean.lookupTemplate(this.temporaryTemplateName);
            if (this.temporaryModule != null) {
               this.temporaryModule.setTemplate(domainTemporaryTemplate);
            } else {
               this.temporaryModule = new TemporaryModule(this.name, domainTemporaryTemplate, this.temporaryTemplateResource);
            }
         } else if (this.temporaryModule != null) {
            this.temporaryModule.setTemplate((TemplateBean)null);
         } else {
            this.temporaryModule = new TemporaryModule(this.name);
         }

         if ((this.state & 16) == 0 && this.tempDestinationFactory == null) {
            try {
               this.tempDestinationFactory = new BackEndTempDestinationFactory(this);
               this.jmsService.getBEDeployer().addTempDestinationFactory(this.tempDestinationFactory);
            } catch (NamingException var6) {
               throw new BeanUpdateFailedException(var6.toString(), var6);
            }
         }
      } else {
         if (this.temporaryModule != null) {
            this.temporaryModule.setTemplate((TemplateBean)null);
         }

         if (this.tempDestinationFactory != null) {
            try {
               this.jmsService.getBEDeployer().removeTempDestinationFactory(this.tempDestinationFactory);
               this.tempDestinationFactory = null;
            } catch (NamingException var5) {
               throw new BeanUpdateFailedException(var5.toString());
            }
         }
      }

   }

   public String getFullSAFDestinationName(String destName) {
      return destName + "@" + this.name;
   }

   public String getAlternativeFullSAFDestinationName(String destName) {
      return this.getAlternativeKernelName() == null ? null : destName + "@" + this.getAlternativeKernelName();
   }

   public void setThresholdHandler(ThresholdHandler handler) {
      this.thresholdHandler = handler;
   }

   public void setHealthFailed(Exception e) {
      this.backEndHealthException = e;
      HealthMonitorService.subsystemFailed("JMSServer." + this.getName(), e.toString());
   }

   public void setHealthFailedNonFatal(Exception e) {
      this.backEndHealthException = e;
      HealthMonitorService.subsystemFailedNonFatal("JMSServer." + this.getName(), e.toString());
   }

   public void setIsMigratable(boolean migratable) {
      this.isMigratable = migratable;
   }

   protected synchronized boolean isLateMigrationDeactivate() {
      return this.isLateMigrationDeactivate;
   }

   protected synchronized void setLateMigrationDeactivate(boolean lateMigrationDeactivate) {
      this.isLateMigrationDeactivate = lateMigrationDeactivate;
   }

   public void setTarget(String targetName) {
      this.targetName = targetName;
   }

   public JMSService getJmsService() {
      return this.jmsService;
   }

   public String getPathServiceJndiName() {
      return PathHelper.pathServiceJndiNameFromScopeMBean(this.originalScopeMBean);
   }

   public PathHelper getPathHelper() throws NamingException {
      PathHelper pathHelper = this.pathHelper;
      if (pathHelper == null) {
         pathHelper = this.pathHelper = PathHelper.partitionAwareFindOrCreate(this.jmsService.getCtx(true), this.getPathServiceJndiName(), this.jmsService.getComponentInvocationContext());
      }

      return pathHelper;
   }

   public PathHelper.ServerInfo findOrCreateServerInfo(Key key) throws NamingException {
      return this.getPathHelper().findOrCreateServerInfo(key);
   }

   public boolean isStart() {
      synchronized(this.shutdownLock) {
         return (this.state & 4) != 0;
      }
   }

   public JMSServerRuntimeMBean getRuntimeMBean() {
      return this.runtimeMBean;
   }

   public boolean isClusterTargeted() {
      return !this.name.equals(this.configName);
   }

   public int determineDeploymentMemberType() {
      byte initialDeploymentMemberType;
      byte initialFlagStatus;
      if (this.isClusterTargeted()) {
         ServerMBean server = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
         if (server.isDynamicallyCreated()) {
            initialDeploymentMemberType = 1;
            initialFlagStatus = 16;
         } else {
            initialDeploymentMemberType = 2;
            initialFlagStatus = 32;
         }
      } else {
         initialDeploymentMemberType = 3;
         initialFlagStatus = 64;
      }

      int deploymentMemberType = initialDeploymentMemberType | initialFlagStatus;
      if (this.getConfigType().equals("SAFAgent")) {
         deploymentMemberType |= 256;
      }

      if (this.originalScopeMBean instanceof ResourceGroupMBean) {
         deploymentMemberType |= 512;
      } else if (this.originalScopeMBean instanceof ResourceGroupTemplateMBean) {
         deploymentMemberType |= 1024;
      }

      return deploymentMemberType;
   }

   public String getShortName() {
      return this.runtimeMBean.getName();
   }

   private void removeAccessor() {
      ServerRuntimeMBean server = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
      String partitionName = PartitionUtils.getPartitionName();
      Object accessRuntime;
      if (PartitionUtils.isDomain(partitionName)) {
         accessRuntime = server.getWLDFRuntime().getWLDFAccessRuntime();
      } else {
         accessRuntime = server.lookupPartitionRuntime(partitionName).getWLDFPartitionRuntime().getWLDFPartitionAccessRuntime();
      }

      try {
         if (accessRuntime != null && this.runtimeMBean != null) {
            if (this.getConfigType().equals("JMSServer")) {
               ((WLDFAccessRuntimeMBean)accessRuntime).removeAccessor(JMSAccessorHelper.getLogicalNameForJMSMessageLog(this.runtimeMBean.getName()));
            } else {
               ((WLDFAccessRuntimeMBean)accessRuntime).removeAccessor(JMSAccessorHelper.getLogicalNameForJMSSAFMessageLog(this.runtimeMBean.getName()));
            }
         }
      } catch (ManagementException var5) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Exception thrown during removal of " + this.runtimeMBean.getName() + " WLDF accessor");
            var5.printStackTrace();
         }
      }

   }

   private class CreateId2DestinationMapHandler {
      final HashMap createId2Destination;

      private CreateId2DestinationMapHandler() {
         this.createId2Destination = new HashMap();
      }

      void CreateId2DestinationMapHandler() {
      }

      void put(String name, Object obj) {
         this.createId2Destination.put(name, obj);
      }

      void clear() {
         this.createId2Destination.clear();
      }

      void remove(String name) {
         this.createId2Destination.remove(name);
      }

      Object get(String name, boolean createAPI) {
         Object obj = this.createId2Destination.get(name);
         if (createAPI && obj == null) {
            Set entries = this.createId2Destination.entrySet();
            Iterator var5 = entries.iterator();

            Map.Entry entry;
            do {
               if (!var5.hasNext()) {
                  return null;
               }

               entry = (Map.Entry)var5.next();
            } while(!PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", (String)entry.getKey()).equals(name));

            return entry.getValue();
         } else {
            return obj;
         }
      }

      HashMap getHandlerMap() {
         return this.createId2Destination;
      }

      // $FF: synthetic method
      CreateId2DestinationMapHandler(Object x1) {
         this();
      }
   }

   private static class TemplateListener implements BeanUpdateListener {
      private String backEndName;
      private String tempDestinationName;
      private JMSBean temporaryModule;
      private TemplateBean listenToMe;
      private boolean isQueue;
      private boolean updateInProgress;

      private TemplateListener(String paramBackEndName, String paramTempDestinationName, JMSBean paramTemporaryModule, TemplateBean paramListenToMe, boolean paramIsQueue) {
         this.updateInProgress = false;
         this.backEndName = paramBackEndName;
         this.tempDestinationName = paramTempDestinationName;
         this.temporaryModule = paramTemporaryModule;
         this.listenToMe = paramListenToMe;
         this.isQueue = paramIsQueue;
         DescriptorBean db = (DescriptorBean)this.listenToMe;
         db.addBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getThresholds();
         db.addBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getDeliveryParamsOverrides();
         db.addBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getDeliveryFailureParams();
         db.addBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getMulticast();
         db.addBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getMessageLoggingParams();
         db.addBeanUpdateListener(this);
      }

      private JMSBean getWholeModuleClone(JMSBean toClone) {
         DescriptorBean db = (DescriptorBean)toClone;
         return (JMSBean)((Descriptor)db.getDescriptor().clone()).getRootBean();
      }

      public synchronized void prepareUpdate(BeanUpdateEvent updateEvent) throws BeanUpdateRejectedException {
         if (!this.updateInProgress) {
            this.updateInProgress = true;
            synchronized(this.temporaryModule) {
               DescriptorBean db = updateEvent.getProposedBean();
               if (db == null) {
                  throw new BeanUpdateRejectedException("ERROR: proposed bean was null in update of temporary template for " + this.backEndName);
               } else {
                  if (!(db instanceof TemplateBean)) {
                     db = db.getParentBean();
                  }

                  if (db != null && db instanceof TemplateBean) {
                     TemplateBean proposedTemplate = (TemplateBean)db;
                     DescriptorBean temporaryModuleDescriptorBean = (DescriptorBean)this.temporaryModule;
                     JMSBean clonedTree = this.getWholeModuleClone(this.temporaryModule);
                     TemplateBean clonedTemplate = clonedTree.lookupTemplate(this.listenToMe.getName());
                     clonedTree.destroyTemplate(clonedTemplate);
                     clonedTemplate = clonedTree.createTemplate(this.listenToMe.getName());
                     DestinationBean clonedDestination = this.isQueue ? clonedTree.lookupQueue(this.tempDestinationName) : clonedTree.lookupTopic(this.tempDestinationName);
                     ((DestinationBean)clonedDestination).setTemplate(clonedTemplate);
                     QuotaBean originalQuota;
                     String originalEDName;
                     String proposedEDName;
                     if (proposedTemplate.isSet("Quota")) {
                        originalQuota = this.listenToMe.getQuota();
                        originalEDName = originalQuota == null ? "" : originalQuota.getName();
                        QuotaBean proposedQuota = proposedTemplate.getQuota();
                        proposedEDName = proposedQuota == null ? "" : proposedQuota.getName();
                        if (!originalEDName.equals(proposedEDName)) {
                           QuotaBean quotaToRemove = clonedTree.lookupQuota(originalEDName);
                           if (quotaToRemove != null) {
                              clonedTree.destroyQuota(quotaToRemove);
                           }

                           if (proposedQuota != null) {
                              clonedTree.createQuota(proposedEDName);
                           }
                        }
                     } else {
                        originalQuota = this.listenToMe.getQuota();
                        if (originalQuota != null) {
                           QuotaBean quotaToRemove = clonedTree.lookupQuota(originalQuota.getName());
                           if (quotaToRemove != null) {
                              clonedTree.destroyQuota(quotaToRemove);
                           }
                        }
                     }

                     DestinationBean originalED;
                     if (proposedTemplate.getDeliveryFailureParams().isSet("ErrorDestination")) {
                        originalED = this.listenToMe.getDeliveryFailureParams().getErrorDestination();
                        originalEDName = originalED == null ? "" : originalED.getName();
                        DestinationBean proposedED = proposedTemplate.getDeliveryFailureParams().getErrorDestination();
                        proposedEDName = proposedED == null ? "" : proposedED.getName();
                        if (!originalEDName.equals(proposedEDName)) {
                           if (originalED != null) {
                              DestinationBean destinationToRemove = JMSModuleHelper.findDestinationBean(originalEDName, clonedTree);
                              if (destinationToRemove != null) {
                                 JMSBeanHelper.destroyDestination(clonedTree, destinationToRemove);
                              }
                           }

                           if (proposedED != null) {
                              if (proposedED instanceof QueueBean) {
                                 clonedTree.createQueue(proposedEDName);
                              } else {
                                 clonedTree.createTopic(proposedEDName);
                              }
                           }
                        }
                     } else {
                        originalED = this.listenToMe.getDeliveryFailureParams().getErrorDestination();
                        if (originalED != null) {
                           DestinationBean destinationToRemove = JMSModuleHelper.findDestinationBean(originalED.getName(), clonedTree);
                           if (destinationToRemove != null) {
                              JMSBeanHelper.destroyDestination(clonedTree, destinationToRemove);
                           }
                        }
                     }

                     try {
                        JMSBeanHelper.copyTemplateBean(clonedTemplate, clonedTree, proposedTemplate);
                     } catch (ManagementException var16) {
                        throw new BeanUpdateRejectedException("ERROR: Could not copy the proposed template bean in update of temporary template for " + this.backEndName, var16);
                     }

                     Descriptor temporaryModuleDescriptor = temporaryModuleDescriptorBean.getDescriptor();
                     Descriptor proposedModuleDescriptor = ((DescriptorBean)clonedTree).getDescriptor();

                     try {
                        temporaryModuleDescriptor.prepareUpdate(proposedModuleDescriptor);
                     } catch (DescriptorUpdateRejectedException var15) {
                        throw new BeanUpdateRejectedException("ERROR: Could not prepare the temporary destination module of " + this.backEndName, var15);
                     }

                  } else {
                     throw new BeanUpdateRejectedException("ERROR: normalized proposed bean was null in update of temporary template for " + this.backEndName);
                  }
               }
            }
         }
      }

      public synchronized void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         if (this.updateInProgress) {
            this.updateInProgress = false;
            synchronized(this.temporaryModule) {
               Descriptor temporaryModuleDescriptor = ((DescriptorBean)this.temporaryModule).getDescriptor();

               try {
                  temporaryModuleDescriptor.activateUpdate();
               } catch (DescriptorUpdateFailedException var6) {
                  throw new BeanUpdateFailedException(var6.getMessage(), var6);
               }

            }
         }
      }

      public synchronized void rollbackUpdate(BeanUpdateEvent event) {
         if (this.updateInProgress) {
            this.updateInProgress = false;
            synchronized(this.temporaryModule) {
               Descriptor temporaryModuleDescriptor = ((DescriptorBean)this.temporaryModule).getDescriptor();
               temporaryModuleDescriptor.rollbackUpdate();
            }
         }
      }

      private void close() {
         DescriptorBean db = (DescriptorBean)this.listenToMe;
         db.removeBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getThresholds();
         db.removeBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getDeliveryParamsOverrides();
         db.removeBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getDeliveryFailureParams();
         db.removeBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getMulticast();
         db.removeBeanUpdateListener(this);
         db = (DescriptorBean)this.listenToMe.getMessageLoggingParams();
         db.removeBeanUpdateListener(this);
      }

      // $FF: synthetic method
      TemplateListener(String x0, String x1, JMSBean x2, TemplateBean x3, boolean x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   private static class BeansAndSauce {
      private JMSBean bean;
      private JMSModuleManagedEntity sauce;
      private TemplateListener listener;
      private boolean isQueue;

      private BeansAndSauce(JMSBean paramBean, JMSModuleManagedEntity paramSauce, boolean paramIsQueue) {
         this.bean = paramBean;
         this.sauce = paramSauce;
         this.isQueue = paramIsQueue;
      }

      private JMSBean getBean() {
         return this.bean;
      }

      private JMSModuleManagedEntity getSauce() {
         return this.sauce;
      }

      private void setListener(TemplateListener paramListener) {
         this.listener = paramListener;
      }

      private TemplateListener getListener() {
         return this.listener;
      }

      private boolean isQueue() {
         return this.isQueue;
      }

      // $FF: synthetic method
      BeansAndSauce(JMSBean x0, JMSModuleManagedEntity x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class TemporaryModule {
      private static final DestinationEntityProvider DESTINATION_PROVIDER = new DestinationEntityProvider();
      private String backEndName;
      private TemplateBean template;
      private ModuleName auxiliaryModuleName;
      private HashMap entityMap;
      private String proposedTemporaryDestinationName;

      private TemporaryModule(String paramBackEndName, TemplateBean paramTemplate, ModuleName paramAuxiliaryModuleName) {
         this.entityMap = new HashMap();
         this.backEndName = paramBackEndName;
         this.template = paramTemplate;
         this.auxiliaryModuleName = paramAuxiliaryModuleName;
      }

      private TemporaryModule(String paramBackEndName) {
         this(paramBackEndName, (TemplateBean)null, new ModuleName(paramBackEndName, (String)null));
      }

      private void setTemplate(TemplateBean paramTemplate) {
         this.template = paramTemplate;
      }

      private void setAuxiliaryModuleName(ModuleName paramAuxiliaryModuleName) {
         this.auxiliaryModuleName = paramAuxiliaryModuleName;
      }

      private void close() {
         Iterator it = this.entityMap.keySet().iterator();

         while(it.hasNext()) {
            String key = (String)it.next();
            BeansAndSauce bas = (BeansAndSauce)this.entityMap.get(key);
            TemplateListener listener = bas.getListener();
            if (listener != null) {
               listener.close();
            }
         }

         this.entityMap.clear();
      }

      private EntityName prepareCreateTemporaryDestination(boolean isQueue, int counter) throws weblogic.jms.common.JMSException {
         JMSBean singleModule = (JMSBean)(new DescriptorManager()).createDescriptorRoot(JMSBean.class).getRootBean();
         TemplateBean singleTemplate = null;
         if (this.template != null) {
            singleTemplate = singleModule.createTemplate(this.template.getName());
            QuotaBean quotaBean = this.template.getQuota();
            if (quotaBean != null) {
               singleModule.createQuota(quotaBean.getName());
            }

            DestinationBean errorDestination = this.template.getDeliveryFailureParams().getErrorDestination();
            if (errorDestination != null) {
               if (errorDestination instanceof QueueBean) {
                  singleModule.createQueue(errorDestination.getName());
               } else {
                  singleModule.createTopic(errorDestination.getName());
               }
            }

            String[] destinationKeyNames = this.template.getDestinationKeys();
            if (destinationKeyNames.length > 0) {
               DomainMBean domain = ManagementService.getRuntimeAccess(BackEnd.KERNEL_ID).getDomain();
               JMSSystemResourceMBean resource = null;
               resource = domain.lookupJMSSystemResource(this.auxiliaryModuleName.toString());
               JMSBean resourceBean = resource.getJMSResource();

               for(int i = 0; i < destinationKeyNames.length; ++i) {
                  try {
                     JMSBeanHelper.copyDestinationKeyBean(singleModule, resourceBean, destinationKeyNames[i]);
                  } catch (ManagementException var15) {
                     throw new weblogic.jms.common.JMSException(var15);
                  }
               }
            }

            try {
               JMSBeanHelper.copyTemplateBean(singleTemplate, singleModule, this.template);
            } catch (ManagementException var14) {
               throw new weblogic.jms.common.JMSException(var14);
            }
         }

         Object addedBean;
         if (isQueue) {
            this.proposedTemporaryDestinationName = this.backEndName + ".TemporaryQueue" + counter;
            QueueBean tempQueue = singleModule.createQueue(this.proposedTemporaryDestinationName);
            addedBean = tempQueue;
         } else {
            this.proposedTemporaryDestinationName = this.backEndName + ".TemporaryTopic" + counter;
            TopicBean tempTopic = singleModule.createTopic(this.proposedTemporaryDestinationName);
            addedBean = tempTopic;
         }

         if (singleTemplate != null) {
            ((DestinationBean)addedBean).setTemplate(singleTemplate);
         }

         ((DestinationBean)addedBean).getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");

         try {
            JMSModuleManagedEntity entity = DESTINATION_PROVIDER.createTemporaryEntity(singleModule, (NamedEntityBean)addedBean, this.backEndName, this.auxiliaryModuleName);
            entity.prepare();
            this.entityMap.put(JMSBeanHelper.getDecoratedName(this.backEndName, this.proposedTemporaryDestinationName), new BeansAndSauce(singleModule, entity, isQueue));
         } catch (ModuleException var13) {
            throw new weblogic.jms.common.JMSException(var13);
         }

         return new EntityName(this.backEndName, (String)null, this.proposedTemporaryDestinationName);
      }

      private void rollbackCreateTemporaryDestination() throws weblogic.jms.common.JMSException {
         weblogic.jms.common.JMSException anException = null;
         String entityName = JMSBeanHelper.getDecoratedName(this.backEndName, this.proposedTemporaryDestinationName);
         this.proposedTemporaryDestinationName = null;
         BeansAndSauce bas = (BeansAndSauce)this.entityMap.remove(entityName);
         JMSModuleManagedEntity entity = bas.getSauce();

         try {
            entity.unprepare();
            entity.destroy();
            entity.remove();
         } catch (ModuleException var6) {
            anException = new weblogic.jms.common.JMSException(var6);
         }

         if (anException != null) {
            throw anException;
         }
      }

      private void activateCreateTemporaryDestination() throws weblogic.jms.common.JMSException {
         String entityName = JMSBeanHelper.getDecoratedName(this.backEndName, this.proposedTemporaryDestinationName);
         String shortName = this.proposedTemporaryDestinationName;
         this.proposedTemporaryDestinationName = null;
         BeansAndSauce bas = (BeansAndSauce)this.entityMap.get(entityName);
         JMSModuleManagedEntity entity = bas.getSauce();
         JMSBean singleModule = bas.getBean();

         try {
            entity.activate(singleModule);
         } catch (ModuleException var7) {
            throw new weblogic.jms.common.JMSException(var7);
         }

         if (this.template != null) {
            bas.setListener(new TemplateListener(this.backEndName, shortName, singleModule, this.template, bas.isQueue()));
         }

      }

      private void removeTemporaryDestination(String temporaryDestinationName) throws weblogic.jms.common.JMSException {
         weblogic.jms.common.JMSException anException = null;
         BeansAndSauce bas = (BeansAndSauce)this.entityMap.remove(temporaryDestinationName);
         if (bas != null) {
            TemplateListener listener = bas.getListener();
            if (listener != null) {
               listener.close();
            }

            JMSModuleManagedEntity entity = bas.getSauce();

            try {
               entity.deactivate();
            } catch (ModuleException var7) {
               anException = new weblogic.jms.common.JMSException(var7);
            }

            try {
               entity.unprepare();
            } catch (ModuleException var10) {
               if (anException != null) {
                  anException.printStackTrace();
               }

               anException = new weblogic.jms.common.JMSException(var10);
            }

            try {
               entity.destroy();
            } catch (ModuleException var9) {
               if (anException != null) {
                  anException.printStackTrace();
               }

               anException = new weblogic.jms.common.JMSException(var9);
            }

            try {
               entity.remove();
            } catch (ModuleException var8) {
               if (anException != null) {
                  anException.printStackTrace();
               }

               anException = new weblogic.jms.common.JMSException(var8);
            }

            if (anException != null) {
               throw anException;
            }
         }
      }

      // $FF: synthetic method
      TemporaryModule(String x0, TemplateBean x1, ModuleName x2, Object x3) {
         this(x0, x1, x2);
      }

      // $FF: synthetic method
      TemporaryModule(String x0, Object x1) {
         this(x0);
      }
   }

   private class ObjectHandlerTestStoreException implements ObjectHandler, TestStoreException {
      ObjectHandler delegate;

      public ObjectHandlerTestStoreException(ObjectHandler delegateArg) {
         assert ObjectMessageImpl.isTestStoreExceptionEnabled() : "system property for store debug required";

         this.delegate = delegateArg;
      }

      public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
         return this.delegate.readObject(in);
      }

      public void writeObject(ObjectOutput out, Object obj) throws IOException {
         this.delegate.writeObject(out, obj);
      }

      public PersistentStoreException getTestException() {
         return null;
      }
   }
}
