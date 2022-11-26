package weblogic.jms;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.glassfish.hk2.api.MultiException;
import weblogic.application.ModuleException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.deployment.jms.JMSSessionPool;
import weblogic.deployment.jms.JMSSessionPoolManager;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.image.ImageManager;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.backend.BEConnection;
import weblogic.jms.backend.BEManager;
import weblogic.jms.backend.BEUOOObjectHandler;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.backend.udd.UDDEntityHelper;
import weblogic.jms.client.JMSSession;
import weblogic.jms.common.CDS;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.DSManager;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSTargetsListener;
import weblogic.jms.common.LeaderManager;
import weblogic.jms.common.SecurityChecker;
import weblogic.jms.common.ServerCrossDomainSecurityUtil;
import weblogic.jms.common.SingularAggregatableManager;
import weblogic.jms.common.TimedSecurityParticipant;
import weblogic.jms.dd.DDScheduler;
import weblogic.jms.deployer.BEDeployer;
import weblogic.jms.deployer.FEDeployer;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.ServerDispatcherManager;
import weblogic.jms.dotnet.proxy.ProxyManager;
import weblogic.jms.frontend.FEConnection;
import weblogic.jms.frontend.FEConnectionFactory;
import weblogic.jms.frontend.FEManager;
import weblogic.jms.frontend.FrontEnd;
import weblogic.jms.module.JMSResourceDefinitionManager;
import weblogic.jms.module.TargetingHelper;
import weblogic.jms.module.observers.JMSObserver;
import weblogic.jms.multicast.JMSTDMSocket;
import weblogic.jms.multicast.JMSTDMSocketIPM;
import weblogic.jms.multicast.JMSTMSocket;
import weblogic.jndi.Environment;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JMSConnectionRuntimeMBean;
import weblogic.management.runtime.JMSPooledConnectionRuntimeMBean;
import weblogic.management.runtime.JMSRuntimeMBean;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.management.utils.ManagedServiceShutdownException;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherImpl;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.path.Key;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.path.internal.PathObjectHandler;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFException;
import weblogic.rmi.internal.OIDManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.JMSResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public final class JMSService extends RuntimeMBeanDelegate implements JMSRuntimeMBean {
   static final long serialVersionUID = -992732582587191590L;
   public static final int STATE_INITIALIZING = 0;
   public static final int STATE_SUSPENDED = 1;
   public static final int STATE_SUSPENDING = 2;
   public static final int STATE_STARTED = 4;
   public static final int STATE_SHUTTING_DOWN = 8;
   public static final int STATE_CLOSED = 16;
   public static final int STATE_DELETING = 32;
   public static final int STATE_DELETED = 64;
   public static final int STATE_BOOTING = 128;
   public static final int STATE_PAUSING_PRODUCTION = 256;
   public static final int STATE_PAUSED_PRODUCTION = 512;
   public static final int STATE_RESUMING_PRODUCTION = 1024;
   public static final int STATE_PAUSING_INSERTION = 2048;
   public static final int STATE_PAUSED_INSERTION = 4096;
   public static final int STATE_RESUMING_INSERTION = 8192;
   public static final int STATE_PAUSING_CONSUMPTION = 16384;
   public static final int STATE_PAUSED_CONSUMPTION = 32768;
   public static final int STATE_RESUMING_CONSUMPTION = 65536;
   public static final int STATE_ADVERTISED_IN_CLUSTER_JNDI = 131072;
   public static final int STATE_ADVERTISED_IN_LOCAL_JNDI = 262144;
   private static boolean imageSourceRegistered = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String[] stateName = new String[]{"initializing", "suspended", "suspending", "started", "shutting_down", "closed", "deleting", "deleted", "booting", "pausing_production", "paused_production", "resuming_production", "pausing_insertion", "paused_insertion", "resuming_insertion", "pausing_consumption", "paused_consumption", "resuming_consumption", "advertised_in_cluster_jndi", "advertised_in_local_jndi", "unknown"};
   private static final String SECURITY_CHECK_INTERVAL_PROP = "weblogic.jms.securityCheckInterval";
   private static final String MULTICAST_SEND_DELAY_PROP = "weblogic.jms.extensions.multicast.sendDelay";
   private static final String DEBUG_JNDI_NOT_PUSH_CIC_PROP = "weblogic.jms.debugJndiNotPushCIC";
   private static boolean debugJndiNotPushCIC = false;
   public static final String BACKEND_JNDI = "weblogic.jms.backend";
   static JMSServiceSingleton jmsServiceSingleton;
   private final UDDEntityHelper uddEntityHelper;
   private final JMSResourceDefinitionManager resourceDefinitionManager;
   private int state = 0;
   private final Object shutdownLock;
   private boolean initialized;
   private volatile boolean startedFirstTime;
   private JMSDispatcher dispatcher;
   private FrontEnd frontEnd;
   private static LeaderManager leaderManager;
   private ProxyManager proxyManager;
   private Context ctx;
   private Context nonReplicatedCtx;
   private final InvocableManagerDelegate invocableManagerDelegate;
   private final InvocableMonitor invocableMonitor;
   private final FEManager feManager;
   private final BEManager beManager;
   private final SingularAggregatableManager singularAggregatableManager;
   private ConnectionFactoryListener connectionFactoryListener = null;
   private ServerMBean serverMBean;
   private DomainMBean domainMBean;
   private String mbeanName;
   JMSDebug jmsDebug = new JMSDebug();
   private JMSTDMSocket dgmSock;
   private JMSTMSocket mSock;
   private int multicastDelay;
   private static HashMap jmsServices = new HashMap();
   private boolean isStoppedOrShutdown;
   private static ConcurrentMap startsInProgress = new ConcurrentHashMap();
   private final BEDeployer beDeployer;
   private final FEDeployer feDeployer;
   private static final long SECURITY_CHECK_INTERVAL = 60000L;
   private TimerManager securityTimerManager;
   private SecurityChecker securityChecker;
   private Timer securityTimer;
   private static long securityCheckInterval = 60000L;
   private static final HashMap wlsServerSignatures = new HashMap();
   private GenericBeanListener wlsServerListener;
   private HashSet jmsServerListeners = new HashSet();
   private ArrayList jmsServerBeanListeners = new ArrayList();
   private boolean use81StyleExecuteQueues;
   private final ComponentInvocationContext cic;
   private DispatcherPartitionContext dispatcherPartitionContext = null;

   public String getServerName() {
      return this.serverMBean == null ? null : this.serverMBean.getName();
   }

   public String getClusterName() {
      return this.serverMBean == null ? null : (this.serverMBean.getCluster() == null ? null : this.serverMBean.getCluster().getName());
   }

   public String getDomainName() {
      return this.domainMBean == null ? null : this.domainMBean.getName();
   }

   private JMSService(ComponentInvocationContext cic) throws ManagementException {
      super(ManagementService.getRuntimeAccess(kernelId).getServerName() + ".jms");
      this.cic = cic;
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         String msg = "JMSService cic is " + cic;
         JMSDebug.JMSCommon.debug(msg, new Exception("debug only, no failure " + msg));
      }

      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("JMS service is initialized for partition " + cic.getPartitionName());
      }

      JMSEnvironment.getJMSEnvironment().getLocalDispatcherId();
      if (this.parent instanceof PartitionRuntimeMBean) {
         if (((PartitionRuntimeMBean)this.parent).getJMSRuntime() == null) {
            ((PartitionRuntimeMBean)this.parent).setJMSRuntime(this);
         }
      } else {
         ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setJMSRuntime(this);
      }

      this.mbeanName = this.getName();
      this.beDeployer = new BEDeployer(this);
      this.feDeployer = new FEDeployer(this);
      this.shutdownLock = this.beDeployer.getShutdownLock();
      this.feDeployer.setShutdownLock(this.shutdownLock);
      JMSSecurityHelper.getSecurityHelper();
      new PathObjectHandler();
      PathObjectHandler.setObjectHandler((byte)1, new BEUOOObjectHandler());
      if (securityCheckInterval > 0L) {
         this.securityTimerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms.security.checkers." + getSafePartitionKey(cic), WorkManagerFactory.getInstance().getDefault());
         this.securityChecker = new SecurityChecker(this);
      }

      this.uddEntityHelper = new UDDEntityHelper(this.getPartitionName());
      this.resourceDefinitionManager = JMSResourceDefinitionManager.getInstance();
      this.invocableMonitor = new InvocableMonitor((InvocableMonitor)null);
      this.invocableManagerDelegate = InvocableManagerDelegate.createDelegate(this.getPartitionId());
      this.feManager = new FEManager(this.invocableMonitor, this);
      this.invocableManagerDelegate.addManager(1, this.feManager);
      this.beManager = new BEManager(this.invocableMonitor, this);
      this.invocableManagerDelegate.addManager(2, this.beManager);
      this.singularAggregatableManager = new SingularAggregatableManager(this);
   }

   public InvocableManagerDelegate getInvocableManagerDelegate() {
      return this.invocableManagerDelegate;
   }

   private static void enableAQJMSMsgListenerSyncMode() {
      String aqjmsuseJmsNotification = "oracle.jms.useJmsNotification";
      if (Kernel.isServer() && null == System.getProperty("oracle.jms.useJmsNotification")) {
         System.setProperty("oracle.jms.useJmsNotification", "false");
      }

   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public FEManager getFEManager() {
      return this.feManager;
   }

   public BEManager getBEManager() {
      return this.beManager;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.cic;
   }

   public UDDEntityHelper getUddEntityHelper() {
      return this.uddEntityHelper;
   }

   public JMSResourceDefinitionManager getJMSResourceDefinitionManager() {
      return this.resourceDefinitionManager;
   }

   public void removeDispatcherReference(JMSDispatcher dispatcher) throws DispatcherException {
      this.dispatcherPartitionContext.removeDispatcherReference(dispatcher);
   }

   public DispatcherPartitionContext getDispatcherPartitionContext() {
      return this.dispatcherPartitionContext;
   }

   public JMSDispatcher registerDispatcher(DispatcherWrapper dispatcherWrapper) throws DispatcherException {
      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("JMSService: registering dispatchWrapper " + dispatcherWrapper.getId() + " from " + dispatcherWrapper.getPartitionName());
      }

      return this.dispatcherPartitionContext.registerDispatcher(dispatcherWrapper);
   }

   public JMSDispatcher dispatcherFindOrCreate(DispatcherId dispatcherId) throws DispatcherException {
      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("JMSService: Looking up " + dispatcherId + " from " + this.getComponentInvocationContext().getPartitionName());
      }

      return this.dispatcherPartitionContext.dispatcherFindOrCreate(this.ctx, dispatcherId);
   }

   public JMSDispatcher localDispatcherFind() throws JMSException {
      return this.dispatcherPartitionContext.getLocalDispatcher();
   }

   public DispatcherId getLocalId() {
      return this.dispatcher.getId();
   }

   public DispatcherWrapper createPartitionAwareDispatcherWrapper() throws JMSException {
      return this.dispatcherPartitionContext.getLocalDispatcherWrapper();
   }

   public BEDeployer getBEDeployer() {
      return this.beDeployer;
   }

   public FEDeployer getFEDeployer() {
      return this.feDeployer;
   }

   public static final String getStateName(int i) {
      if (i == 0) {
         return stateName[0];
      } else {
         StringBuffer buf = new StringBuffer();
         boolean didOnce = false;

         for(int j = 1; j < stateName.length - 1; ++j) {
            if ((i & 1 << j - 1) != 0) {
               if (didOnce) {
                  buf.append(", ");
               } else {
                  didOnce = true;
               }

               buf.append(stateName[j]);
            }
         }

         if (!didOnce) {
            return stateName[stateName.length - 1];
         } else {
            return buf.toString();
         }
      }
   }

   public static JMSService createAndStartService() throws ManagementException, JMSException, ServiceFailureException {
      checkServer();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String partitionKey = getSafePartitionKey(cic);
      Class var2 = JMSService.class;
      synchronized(JMSService.class) {
         JMSService svc = (JMSService)jmsServices.get(partitionKey);
         if (svc != null) {
            throw new ServiceFailureException("JMS service for partition " + partitionKey + " already exist");
         } else {
            if (jmsServiceSingleton == null) {
               jmsServiceSingleton = JMSServiceSingleton.getService();
            }

            if (!imageSourceRegistered) {
               imageSourceRegistered = true;
               ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
               imageManager.registerImageSource("JMS", new JMSDiagnosticImageSource());
            }

            svc = new JMSService(cic);
            svc.start();
            jmsServices.put(partitionKey, svc);
            return svc;
         }
      }
   }

   static JMSService getServiceForOldModel() throws ManagementException, JMSException, ServiceFailureException {
      checkServer();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionKey = getSafePartitionKey(cic);
      Class var2 = JMSService.class;
      synchronized(JMSService.class) {
         JMSService service = (JMSService)jmsServices.get(partitionKey);
         if (service == null) {
            if (jmsServiceSingleton == null) {
               jmsServiceSingleton = JMSServiceSingleton.getService();
            }

            if (!imageSourceRegistered) {
               imageSourceRegistered = true;
               ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
               imageManager.registerImageSource("JMS", new JMSDiagnosticImageSource());
            }

            service = new JMSService(cic);
            jmsServices.put(partitionKey, service);
            service.start();
         }

         return service;
      }
   }

   public static synchronized JMSService getJMSServiceWithPartitionName(String partitionName) {
      checkServer();
      return (JMSService)jmsServices.get(partitionName);
   }

   public static synchronized JMSService getJMSServiceWithModuleException(String partitionName) throws ModuleException {
      checkServer();
      JMSService svc = (JMSService)jmsServices.get(partitionName);
      if (svc == null) {
         throw new ModuleException("JMSService for partition " + partitionName + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static synchronized JMSService getJMSServiceWithIllegalStateException(String partitionName) throws IllegalStateException {
      checkServer();
      JMSService svc = (JMSService)jmsServices.get(partitionName);
      if (svc == null) {
         throw new IllegalStateException("JMSService for partition " + partitionName + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static synchronized JMSService removeJMSService(String pName) {
      checkServer();
      JMSService removed = (JMSService)jmsServices.remove(pName);
      if (removed != null) {
         removed.isStoppedOrShutdown = true;
      }

      return removed;
   }

   private static JMSService getJMSServiceWithRuntimeException() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new RuntimeException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithModuleException() throws ModuleException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new ModuleException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithDeploymentException() throws DeploymentException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new DeploymentException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithUndeploymentException() throws UndeploymentException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new UndeploymentException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithManagedServiceShutdownException() throws ManagedServiceShutdownException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new ManagedServiceShutdownException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithBeanUpdateRejectedException() throws BeanUpdateRejectedException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new BeanUpdateRejectedException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithManagementException() throws ManagementException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new ManagementException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithSAFException() throws SAFException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new SAFException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getJMSServiceWithJMSException() throws JMSException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new JMSException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   public static JMSService getStartedService() throws ManagementException, JMSException, ServiceFailureException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
      String pk = getSafePartitionKey(cic);
      JMSService svc = getJMSServiceWithPartitionName(pk);
      if (svc == null) {
         throw new ManagementException("JMSService for partition " + pk + " has been shutdown or not started");
      } else {
         return svc;
      }
   }

   void stopAll(boolean forceful) {
      Class var3 = JMSService.class;
      ArrayList svcs;
      synchronized(JMSService.class) {
         svcs = new ArrayList(jmsServices.values());
      }

      Iterator itr = svcs.iterator();

      while(itr.hasNext()) {
         JMSService svc = (JMSService)itr.next();

         try {
            svc.stopWithCIC(forceful);
            removeJMSService(svc.getPartitionName());
         } catch (ServiceFailureException var7) {
            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("Warning: Exception on stop JMS service " + svc + ": " + var7.getMessage(), var7);
            }
         }
      }

   }

   private void initializeJMSServerListeners() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      JMSServerMBean[] jmsServers = domain.getJMSServers();

      for(int lcv = 0; lcv < jmsServers.length; ++lcv) {
         DescriptorBean db = jmsServers[lcv];
         JMSServerBeanListener beanListener = new JMSServerBeanListener(jmsServers[lcv]);
         db.addBeanUpdateListener(beanListener);
         synchronized(this.jmsServerBeanListeners) {
            this.jmsServerBeanListeners.add(new DescriptorAndListener(db, beanListener));
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Listening for changes to JMSServer " + jmsServers[lcv].getName());
         }
      }

   }

   private void removeJMSServerListeners() {
      synchronized(this.jmsServerBeanListeners) {
         Iterator it = this.jmsServerBeanListeners.iterator();

         while(it.hasNext()) {
            DescriptorAndListener dal = (DescriptorAndListener)it.next();
            DescriptorBean db = dal.getDescriptorBean();
            JMSServerBeanListener jsbl = dal.getListener();
            db.removeBeanUpdateListener(jsbl);
            jsbl.close();
            it.remove();
         }

      }
   }

   private void initializeObservers() {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      runtime.addAccessCallbackClass(JMSObserver.class.getName());
   }

   void start() throws JMSException, ServiceFailureException {
      if (!this.startedFirstTime) {
         if (!this.initialize()) {
            return;
         }

         this.startedFirstTime = true;
      }

      synchronized(this.shutdownLock) {
         this.state = 4;
      }

      HealthMonitorService.register(this.mbeanName, this, false);
      this.initializeObservers();
      this.initializeJMSServerListeners();
      this.feDeployer.initialize(this.frontEnd);
      if (this.serverMBean.isJMSDefaultConnectionFactoriesEnabled()) {
         this.ensureInitialized();
      }

      if (this.frontEnd != null) {
         this.frontEnd.resume();
      }

      if ("DOMAIN".equals(this.getPartitionName()) && this.proxyManager != null) {
         this.proxyManager.resume();
      }

      JMSLogger.logJMSActive();
   }

   private boolean initialize() throws JMSException {
      try {
         Environment env = new Environment();
         env.setCreateIntermediateContexts(true);
         env.setReplicateBindings(true);
         this.ctx = env.getInitialContext();
         env = new Environment();
         env.setCreateIntermediateContexts(true);
         env.setReplicateBindings(false);
         this.nonReplicatedCtx = env.getInitialContext();
      } catch (NamingException var7) {
         JMSLogger.logErrorInitialCtx(var7);
         throw new weblogic.jms.common.JMSException(var7);
      }

      synchronized(this.shutdownLock) {
         if (this.isShutdown()) {
            this.state = 0;
         }
      }

      this.serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      this.domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (leaderManager == null) {
         leaderManager = LeaderManager.setupLeaderManager(JMSID.create());
      }

      this.initializeDispatcher();
      if (this.frontEnd == null) {
         this.frontEnd = new FrontEnd(this);
      }

      if (this.proxyManager == null) {
         try {
            Class ror = Class.forName("weblogic.jms.dotnet.proxy.internal.ProxyManagerImpl");
            Method getMethod = ror.getMethod("getProxyManager");
            this.proxyManager = (ProxyManager)getMethod.invoke((Object)null);
         } catch (ClassNotFoundException var3) {
            throw new AssertionError(var3);
         } catch (NoSuchMethodException var4) {
            throw new AssertionError(var4);
         } catch (IllegalAccessException var5) {
            throw new AssertionError(var5);
         } catch (InvocationTargetException var6) {
            throw new AssertionError(var6);
         }
      }

      JMSLogger.logJMSInitialized();
      return true;
   }

   public final void ensureInitialized() throws JMSException {
      this.checkShutdown();
      synchronized(this) {
         if (!this.initialized) {
            this.multicastDelay = 0;

            try {
               String delayString = System.getProperty("weblogic.jms.extensions.multicast.sendDelay");
               if (delayString != null) {
                  this.multicastDelay = Integer.parseInt(delayString);
               }
            } catch (SecurityException var5) {
            }

            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("JMS is initialized");
            }

            this.initialized = true;
            this.wlsServerListener = new GenericBeanListener(this.serverMBean, this, wlsServerSignatures);
         }
      }
   }

   public void openMulticastSendSocket() throws JMSException {
      if (this.mSock == null) {
         try {
            this.dgmSock = new JMSTDMSocketIPM();
            this.mSock = new JMSTMSocket((JMSSession)null, this.dgmSock, this.multicastDelay, 0);
         } catch (IOException var2) {
            if (this.mSock != null) {
               this.mSock.close();
            }

            if (this.dgmSock != null) {
               this.dgmSock.close();
            }

            this.mSock = null;
            this.dgmSock = null;
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("can not open multicast socket " + var2.toString());
            }

            JMSLogger.logErrorMulticastOpen(var2);
            throw new JMSException(var2.toString());
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Multicast socket is opened");
         }

      }
   }

   private void stopWithCIC(boolean force) throws ServiceFailureException {
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var3 = null;

      try {
         this.stop(force);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   void stop(boolean force) throws ServiceFailureException {
      this.stop(force, false);
   }

   void stopPartitionWithNotification(boolean force) throws ServiceFailureException {
      this.stop(force, true);
   }

   private void stop(boolean force, boolean shouldNotifyClientDueToPartitionShutdown) throws ServiceFailureException {
      boolean var18 = false;

      label393: {
         try {
            var18 = true;
            synchronized(this.shutdownLock) {
               if ((this.state & 17) != 0) {
                  var18 = false;
                  break label393;
               }

               this.state = 8;
               this.removeJMSServerListeners();
               if (this.wlsServerListener != null) {
                  this.wlsServerListener.close();
                  this.wlsServerListener = null;
               }

               BackEnd[] backEnds = this.beDeployer.getBackEnds();

               for(int inc = 0; inc < backEnds.length; ++inc) {
                  backEnds[inc].markShuttingDown();
               }

               if (this.frontEnd != null) {
                  this.frontEnd.markShuttingDown();
               }

               if (force) {
                  this.getInvocableMonitor().forceInvocablesCompletion();
               }
            }

            if (this.frontEnd != null) {
               this.frontEnd.prepareForSuspend(force);
            }

            if (!force) {
               this.getInvocableMonitor().waitForInvocablesCompletion();
            }

            DDScheduler.drain();
            Throwable thrown = DDScheduler.waitForComplete();
            if (thrown != null && JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("Warning: JMS service stop on shudown DDScheduler: " + thrown);
            }

            LeaderManager.getLeaderManager().removePartitionHashbyName();
            DSManager.removeDSManager();
            if (this.frontEnd != null) {
               if (shouldNotifyClientDueToPartitionShutdown) {
                  this.frontEnd.shutdownPartitionWithNotification();
               } else {
                  this.frontEnd.shutdown();
               }
            }

            if ("DOMAIN".equals(this.cic.getPartitionName()) && this.proxyManager != null) {
               this.proxyManager.shutdown(force);
            }

            if (this.feDeployer != null) {
               this.feDeployer.shutdown();
            }

            HealthMonitorService.unregister(this.mbeanName);

            try {
               PrivilegedActionUtilities.unregister(this, kernelId);
            } catch (ManagementException var22) {
            }

            CDS.removeCDS(this.getPartitionName());
            var18 = false;
         } finally {
            if (var18) {
               InvocableManagerDelegate.removeDelegate(this.getPartitionId());
               if (this.securityTimerManager != null) {
                  this.stopSecurityChecks();
                  this.securityTimerManager.stop();
               }

               this.dispatcherPartitionContext.removeJMSDispatcherManager();
               if (this.parent instanceof PartitionRuntimeMBean) {
                  ((PartitionRuntimeMBean)this.parent).setJMSRuntime((JMSRuntimeMBean)null);
               }

               if (this.dispatcher != null && this.dispatcher.getDelegate() != null) {
                  if (this.dispatcher.getDelegate() instanceof DispatcherImpl && ((DispatcherImpl)this.dispatcher.getDelegate()).getFastDispatcher() != null) {
                     OIDManager.getInstance().removeServerReference(OIDManager.getInstance().getServerReference(((DispatcherImpl)this.dispatcher.getDelegate()).getFastDispatcher()));
                  }

                  if (OIDManager.getInstance().getServerReference(this.dispatcher.getDelegate()) != null) {
                     OIDManager.getInstance().removeServerReference(OIDManager.getInstance().getServerReference(this.dispatcher.getDelegate()));
                  }

                  this.dispatcherPartitionContext.removeDispatcherWrapperStateReference();
               }

               this.securityChecker = null;
               synchronized(this.shutdownLock) {
                  this.state = 16;
               }

               JMSLogger.logJMSShutdown();
            }
         }

         InvocableManagerDelegate.removeDelegate(this.getPartitionId());
         if (this.securityTimerManager != null) {
            this.stopSecurityChecks();
            this.securityTimerManager.stop();
         }

         this.dispatcherPartitionContext.removeJMSDispatcherManager();
         if (this.parent instanceof PartitionRuntimeMBean) {
            ((PartitionRuntimeMBean)this.parent).setJMSRuntime((JMSRuntimeMBean)null);
         }

         if (this.dispatcher != null && this.dispatcher.getDelegate() != null) {
            if (this.dispatcher.getDelegate() instanceof DispatcherImpl && ((DispatcherImpl)this.dispatcher.getDelegate()).getFastDispatcher() != null) {
               OIDManager.getInstance().removeServerReference(OIDManager.getInstance().getServerReference(((DispatcherImpl)this.dispatcher.getDelegate()).getFastDispatcher()));
            }

            if (OIDManager.getInstance().getServerReference(this.dispatcher.getDelegate()) != null) {
               OIDManager.getInstance().removeServerReference(OIDManager.getInstance().getServerReference(this.dispatcher.getDelegate()));
            }

            this.dispatcherPartitionContext.removeDispatcherWrapperStateReference();
         }

         this.securityChecker = null;
         synchronized(this.shutdownLock) {
            this.state = 16;
         }

         JMSLogger.logJMSShutdown();
         return;
      }

      InvocableManagerDelegate.removeDelegate(this.getPartitionId());
      if (this.securityTimerManager != null) {
         this.stopSecurityChecks();
         this.securityTimerManager.stop();
      }

      this.dispatcherPartitionContext.removeJMSDispatcherManager();
      if (this.parent instanceof PartitionRuntimeMBean) {
         ((PartitionRuntimeMBean)this.parent).setJMSRuntime((JMSRuntimeMBean)null);
      }

      if (this.dispatcher != null && this.dispatcher.getDelegate() != null) {
         if (this.dispatcher.getDelegate() instanceof DispatcherImpl && ((DispatcherImpl)this.dispatcher.getDelegate()).getFastDispatcher() != null) {
            OIDManager.getInstance().removeServerReference(OIDManager.getInstance().getServerReference(((DispatcherImpl)this.dispatcher.getDelegate()).getFastDispatcher()));
         }

         if (OIDManager.getInstance().getServerReference(this.dispatcher.getDelegate()) != null) {
            OIDManager.getInstance().removeServerReference(OIDManager.getInstance().getServerReference(this.dispatcher.getDelegate()));
         }

         this.dispatcherPartitionContext.removeDispatcherWrapperStateReference();
      }

      this.securityChecker = null;
      synchronized(this.shutdownLock) {
         this.state = 16;
      }

      JMSLogger.logJMSShutdown();
   }

   public HealthState getHealthState() {
      int health = 0;
      ArrayList symptoms = new ArrayList(6);
      BackEnd[] backEnds = this.getBEDeployer().getBackEnds();

      for(int i = 0; i < backEnds.length; ++i) {
         HealthState healthState = backEnds[i].getHealthState();
         health = Math.max(healthState.getState(), health);
         Symptom[] var6 = healthState.getSymptoms();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Symptom symptom = var6[var8];
            symptoms.add(symptom);
         }
      }

      return new HealthState(health, (Symptom[])symptoms.toArray(new Symptom[symptoms.size()]));
   }

   public boolean isActive() {
      synchronized(this.shutdownLock) {
         return (this.state & 4) != 0;
      }
   }

   public boolean isShutdown() {
      synchronized(this.shutdownLock) {
         return (this.state & 24) != 0;
      }
   }

   public void checkShutdown() throws JMSException {
      synchronized(this.shutdownLock) {
         if (this.isShutdown()) {
            throw new weblogic.jms.common.JMSException("JMS server shutdown: " + this.state);
         }
      }
   }

   public void checkShutdownOrSuspended(String operation) throws JMSException {
      synchronized(this.shutdownLock) {
         if ((this.state & 27) != 0) {
            throw new JMSException("Failed to " + operation + " because JMS server shutdown or suspended");
         }
      }
   }

   public FEConnectionFactory getDefaultConnectionFactory(String name) {
      return this.getFEDeployer().getDefaultConnectionFactory(name);
   }

   public static String getSafePartitionKey(ComponentInvocationContext cic) {
      if (cic == null) {
         return "DOMAIN";
      } else {
         String partitionName = cic.getPartitionName();
         return partitionName == null ? "DOMAIN" : partitionName;
      }
   }

   public static String getSafePartitionNameFromThread() {
      checkServer();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return getSafePartitionKey(cic);
   }

   public static boolean isPartition(String partitionName) {
      checkServer();
      return partitionName != null && !"".equals(partitionName) && !"DOMAIN".equals(partitionName);
   }

   public static void checkThreadInJMSServicePartition(JMSService service, String className) throws JMSException {
      String pn = getSafePartitionNameFromThread();
      if (!pn.equals(service.getPartitionName())) {
         throw new JMSException("Unexpected " + className + " invocation partition " + pn + " on thread, expected " + service.getPartitionName());
      }
   }

   private static boolean checkServer() {
      if (!KernelStatus.isServer()) {
         throw new UnsupportedOperationException("This method is not allowed to be called outside of WebLogic Server.");
      } else {
         return true;
      }
   }

   public SingularAggregatableManager getSingularAggregatableManagerWithJMSException() throws JMSException {
      if (this.isStoppedOrShutdown) {
         weblogic.jms.common.JMSException jmsException = new weblogic.jms.common.JMSException("JMSService for partition " + this.getPartitionName() + " has been shutdown or not started");
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("JMSService.getSingularAggregatableManager ", jmsException);
         }

         throw jmsException;
      } else {
         return this.singularAggregatableManager;
      }
   }

   public void bindWithCICSU(boolean replicated, String jndiName, Object jndiVal, AuthenticatedSubject kernelId) throws NamingException, ManagementException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         ComponentInvocationContext threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (this.cic.equals(threadCIC)) {
            JMSDebug.JMSCommon.debug("JMSService bindWithCICSU this.cic matches thread " + this.debugCIC(threadCIC) + " " + debugJndiNotPushCIC + "weblogic.jms.debugJndiNotPushCIC");
         } else {
            JMSDebug.JMSCommon.debug("JMSService bindWithCICSU this.cic is [" + this.debugCIC(this.cic) + "] thread cic is [" + this.debugCIC(threadCIC) + " ] " + debugJndiNotPushCIC + "weblogic.jms.debugJndiNotPushCIC");
         }
      }

      if (this.isStoppedOrShutdown) {
         ManagementException me = new ManagementException("JMSService for partition " + this.getPartitionName() + " has been shutdown or not started");
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("JMSService bindWithCICSU", me);
         }

         throw me;
      } else {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(debugJndiNotPushCIC ? null : this.cic);
         Throwable var6 = null;

         try {
            PrivilegedActionUtilities.bindAsSU(this.getCtx(replicated), jndiName, jndiVal, kernelId);
         } catch (Throwable var15) {
            var6 = var15;
            throw var15;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var14) {
                     var6.addSuppressed(var14);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }

   public void unbindWithCICSU(boolean replicated, String jndiName, AuthenticatedSubject kernelId) throws NamingException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         ComponentInvocationContext threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (this.cic.equals(threadCIC)) {
            JMSDebug.JMSCommon.debug("JMSService unbindWithCICSU this.cic matches thread " + this.debugCIC(threadCIC) + " " + debugJndiNotPushCIC + "weblogic.jms.debugJndiNotPushCIC");
         } else {
            JMSDebug.JMSCommon.debug("JMSService unbindWithCICSU this.cic is [" + this.debugCIC(this.cic) + "] thread cic is [" + this.debugCIC(threadCIC) + " ] " + debugJndiNotPushCIC + "weblogic.jms.debugJndiNotPushCIC");
         }
      }

      if (JMSDebug.JMSCommon.isDebugEnabled() && this.isStoppedOrShutdown) {
         JMSDebug.JMSCommon.debug("JMSService unbindWithCICSU JMSService for partition " + this.getPartitionName() + " has been shutdown or not started");
      }

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(debugJndiNotPushCIC ? null : this.cic);
      Throwable var5 = null;

      try {
         PrivilegedActionUtilities.unbindAsSU(this.getCtx(replicated), jndiName, kernelId);
      } catch (Throwable var14) {
         var5 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var5 != null) {
               try {
                  mic.close();
               } catch (Throwable var13) {
                  var5.addSuppressed(var13);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public Object lookupWithCIC(boolean replicated, String jndiName) throws NamingException, ManagementException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         ComponentInvocationContext threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (this.cic.equals(threadCIC)) {
            JMSDebug.JMSCommon.debug("JMSService lookupWithCIC this.cic matches thread " + this.debugCIC(threadCIC) + " " + debugJndiNotPushCIC + "weblogic.jms.debugJndiNotPushCIC");
         } else {
            JMSDebug.JMSCommon.debug("JMSService lookupWithCIC this.cic is [" + this.debugCIC(this.cic) + "] thread cic is [" + this.debugCIC(threadCIC) + " ] " + debugJndiNotPushCIC + "weblogic.jms.debugJndiNotPushCIC");
         }
      }

      if (this.isStoppedOrShutdown) {
         ManagementException me = new ManagementException("JMSService for partition " + this.getPartitionName() + " has been shutdown or not started");
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("JMSService lookupWithCIC", me);
         }

         throw me;
      } else {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(debugJndiNotPushCIC ? null : this.cic);
         Throwable var4 = null;

         Object var5;
         try {
            var5 = this.getCtx(replicated).lookup(jndiName);
         } catch (Throwable var14) {
            var4 = var14;
            throw var14;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var13) {
                     var4.addSuppressed(var13);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var5;
      }
   }

   public String debugCIC(ComponentInvocationContext cic) {
      return cic + " id-class " + (cic != null ? System.identityHashCode(cic) + cic.getClass().getName() : "Nil");
   }

   public static Context getContextWithManagementException() throws ManagementException {
      return getJMSServiceWithManagementException().ctx;
   }

   public static Context getContextWithManagementException(boolean replicated) throws ManagementException {
      return replicated ? getJMSServiceWithManagementException().ctx : getJMSServiceWithManagementException().nonReplicatedCtx;
   }

   public static Context getContextWithModuleException(boolean replicated) throws ModuleException {
      return replicated ? getJMSServiceWithModuleException().ctx : getJMSServiceWithModuleException().nonReplicatedCtx;
   }

   public static Context getContextWithBeanUpdateRejectedException(boolean replicated) throws BeanUpdateRejectedException {
      return replicated ? getJMSServiceWithBeanUpdateRejectedException().ctx : getJMSServiceWithBeanUpdateRejectedException().nonReplicatedCtx;
   }

   public Context getCtx() {
      return this.ctx;
   }

   public Context getCtx(boolean replicated) {
      return replicated ? this.ctx : this.nonReplicatedCtx;
   }

   public PathHelper.ServerInfo findOrCreateServerInfo(String pathServiceJndiName, Key key) throws NamingException {
      PathHelper ph = PathHelper.partitionAwareFindOrCreate(this.ctx, pathServiceJndiName, this.cic);
      return ph.findOrCreateServerInfo(key);
   }

   public synchronized JMSServerId getNextServerId() {
      return new JMSServerId(JMSID.create(), this.dispatcher.getId());
   }

   public static JMSID getNextId() {
      return JMSID.create();
   }

   public static JMSMessageId getNextMessageId() {
      return JMSMessageId.create();
   }

   public JMSConnectionRuntimeMBean[] getConnections() {
      return this.feManager.getConnections();
   }

   public long getConnectionsCurrentCount() {
      return this.feManager.getConnectionsCurrentCount();
   }

   public long getConnectionsHighCount() {
      return this.feManager.getConnectionsHighCount();
   }

   public long getConnectionsTotalCount() {
      return this.feManager.getConnectionsTotalCount();
   }

   public JMSServerRuntimeMBean[] getJMSServers() {
      BackEnd[] backEnds = null;
      HashMap jmsServersMap = new HashMap();
      synchronized(this.shutdownLock) {
         backEnds = this.beDeployer.getBackEnds();
         if (backEnds != null && backEnds.length > 0) {
            for(int i = 0; i < backEnds.length; ++i) {
               if (backEnds[i].getConfigType().equals("JMSServer")) {
                  jmsServersMap.put(backEnds[i].getRuntimeMBean().getName(), backEnds[i].getRuntimeMBean());
               }
            }
         }

         JMSServerRuntimeMBean[] jmsServers = new JMSServerRuntimeMBean[jmsServersMap.size()];
         return (JMSServerRuntimeMBean[])jmsServersMap.values().toArray(jmsServers);
      }
   }

   public Set getJMSServerNames() {
      BackEnd[] backEnds = null;
      Set jmsServersNames = new HashSet();
      synchronized(this.shutdownLock) {
         backEnds = this.beDeployer.getBackEnds();
         if (backEnds != null && backEnds.length > 0) {
            for(int i = 0; i < backEnds.length; ++i) {
               if (backEnds[i].getConfigType().equals("JMSServer")) {
                  jmsServersNames.add(backEnds[i].getName());
               }
            }
         }

         return jmsServersNames;
      }
   }

   public long getJMSServersCurrentCount() {
      synchronized(this.shutdownLock) {
         return (long)this.getBEDeployer().getBackEndsMap().size();
      }
   }

   public long getJMSServersHighCount() {
      synchronized(this.shutdownLock) {
         return this.getBEDeployer().getBackEndsHighCount();
      }
   }

   public long getJMSServersTotalCount() {
      synchronized(this.shutdownLock) {
         return this.getBEDeployer().getBackEndsTotalCount();
      }
   }

   public JMSPooledConnectionRuntimeMBean[] getJMSPooledConnections() {
      HashMap runtimeMap = new HashMap();
      JMSSessionPoolManager jmsSessionPoolManager = JMSSessionPoolManager.getSessionPoolManager();
      HashMap sessionPools = jmsSessionPoolManager.getSessionPools();
      Iterator it = sessionPools.values().iterator();

      while(it.hasNext()) {
         JMSSessionPool sp = (JMSSessionPool)it.next();
         JMSPooledConnectionRuntimeMBean pcrt = (JMSPooledConnectionRuntimeMBean)sp.getJMSSessionPoolRuntime();
         if (pcrt != null) {
            runtimeMap.put(sp.getName(), pcrt);
         }
      }

      JMSPooledConnectionRuntimeMBean[] runtimes = new JMSPooledConnectionRuntimeMBean[runtimeMap.size()];
      return (JMSPooledConnectionRuntimeMBean[])runtimeMap.values().toArray(runtimes);
   }

   public void setJMSDefaultConnectionFactoriesEnabled(boolean enabled) {
      try {
         if (enabled) {
            this.getFEDeployer().deployDefaultConnectionFactories();
         } else {
            this.getFEDeployer().undeployDefaultConnectionFactories();
         }
      } catch (JMSException var3) {
         JMSLogger.logErrorDeployingDefaultFactories(var3.toString());
      }

   }

   public String getMbeanName() {
      return this.mbeanName;
   }

   public JMSTMSocket getMulticastSocket() {
      if (this.mSock == null) {
         try {
            this.openMulticastSendSocket();
         } catch (JMSException var2) {
         }
      }

      return this.mSock;
   }

   private void initializeDispatcher() throws JMSException {
      this.use81StyleExecuteQueues = ManagementService.getRuntimeAccess(kernelId).getServer().getUse81StyleExecuteQueues();
      ServerDispatcherManager sdm = new ServerDispatcherManager(JMSEnvironment.getJMSEnvironment());
      this.dispatcherPartitionContext = sdm.createDispatcherPartitionContext(this.cic.getPartitionId(), this.cic.getPartitionName(), this.use81StyleExecuteQueues, this.invocableManagerDelegate, this.cic);
      this.dispatcher = this.dispatcherPartitionContext.getLocalDispatcher();
      DispatcherWrapper dispatcherWrapper = this.createPartitionAwareDispatcherWrapper();
      String messagingName = "weblogic.messaging.dispatcher.S:" + this.dispatcher.getId();

      try {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("Binding dispatcher to '" + this.name + "'");
         }

         PrivilegedActionUtilities.rebindAsSU(this.getCtx(), messagingName, dispatcherWrapper, kernelId);
      } catch (NamingException var5) {
         throw new AssertionError(var5);
      }
   }

   public static String getDestinationName(Destination dest) {
      return dest instanceof DistributedDestinationImpl ? ((DistributedDestinationImpl)dest).getInstanceName() : ((DestinationImpl)dest).getName();
   }

   public boolean shouldMessageLogNonDurableSubscriber() {
      return jmsServiceSingleton.isMessageLogNonDurableSubscriber();
   }

   public boolean shouldMessageLogAll() {
      return jmsServiceSingleton.isMessageLogAll();
   }

   public FrontEnd getFrontEnd() {
      return this.frontEnd;
   }

   public void registerSecurityParticipant(JMSResource resource, TimedSecurityParticipant participant) {
      if (securityCheckInterval > 0L) {
         if (!this.securityTimerManager.isStopping()) {
            this.securityChecker.registerWithChecker(resource, participant);
         }
      }
   }

   public void unregisterSecurityParticipant(TimedSecurityParticipant participant) {
      this.securityChecker.unregisterWithChecker(participant);
   }

   public boolean isSecurityCheckerStop() {
      return securityCheckInterval == 0L;
   }

   public void fireUpSecurityChecks() {
      if (securityCheckInterval > 0L) {
         if (!this.securityTimerManager.isStopping()) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("Firing up periodic security checks");
            }

            ClassLoader callerClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

            try {
               this.securityTimer = this.securityTimerManager.schedule(this.securityChecker, 1000L, securityCheckInterval);
               return;
            } catch (IllegalStateException var6) {
               if (!this.securityTimerManager.isStopping()) {
                  throw var6;
               }
            } finally {
               Thread.currentThread().setContextClassLoader(callerClassLoader);
            }

         }
      }
   }

   public void stopSecurityChecks() {
      if (this.securityTimer != null && !this.securityTimer.isCancelled()) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("Stopping periodic security checks");
         }

         this.securityTimer.cancel();
         this.securityTimer = null;
      }

   }

   void postDeploymentStart() throws ServiceFailureException {
      String partitionName;
      try {
         List mgs = ((MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0])).getMigratableTargetsMarkedNotReadyToActivate();
         if (mgs != null) {
            Iterator var2 = mgs.iterator();

            while(var2.hasNext()) {
               partitionName = (String)var2.next();
               ((MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0])).markMigratableTargetReadyToActivate(partitionName);
            }
         }
      } catch (MultiException | IllegalStateException var19) {
         throw new ServiceFailureException(var19);
      }

      Iterator var20 = jmsServices.entrySet().iterator();

      while(var20.hasNext()) {
         Map.Entry entry = (Map.Entry)var20.next();
         partitionName = (String)entry.getKey();
         JMSService service = (JMSService)entry.getValue();
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().createComponentInvocationContext(partitionName);
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("JMSService postDeploymentStart manufactured cic " + this.debugCIC(cic));
         }

         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
         Throwable var7 = null;

         try {
            service.getBEDeployer().postDeploymentsStart();
         } catch (Throwable var17) {
            var7 = var17;
            throw var17;
         } finally {
            if (mic != null) {
               if (var7 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var16) {
                     var7.addSuppressed(var16);
                  }
               } else {
                  mic.close();
               }
            }

         }
      }

   }

   void postDeploymentStop() {
      Iterator var1 = jmsServices.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         String partitionName = (String)entry.getKey();
         JMSService service = (JMSService)entry.getValue();
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().createComponentInvocationContext(partitionName);
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("JMSService postDeploymentStop manufactured cic " + this.debugCIC(cic));
         }

         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
         Throwable var7 = null;

         try {
            service.getBEDeployer().postDeploymentsStop();
         } catch (Throwable var16) {
            var7 = var16;
            throw var16;
         } finally {
            if (mic != null) {
               if (var7 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var15) {
                     var7.addSuppressed(var15);
                  }
               } else {
                  mic.close();
               }
            }

         }
      }

   }

   void postDeploymentHalt() {
   }

   void shutdownConnectionsForResourceGroup(ResourceGroupMBean rgmbean, boolean force) {
      JMSLogger.logShutdownConnectionsForResourceGroup(rgmbean.getName());
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Shutdown connections for resource group [" + rgmbean + "] in JMS service of partition " + this.getPartitionName());
      }

      if (this.frontEnd != null) {
         this.frontEnd.shutdownConnectionsForResourceGroup(rgmbean, force);
      }

   }

   public static void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("JMSs");
      Class var2 = JMSService.class;
      synchronized(JMSService.class) {
         Iterator var3 = jmsServices.entrySet().iterator();

         while(true) {
            if (!var3.hasNext()) {
               break;
            }

            Map.Entry entry = (Map.Entry)var3.next();
            JMSService jmsService = (JMSService)entry.getValue();
            jmsService.dumpInternal(imageSource, xsw, (String)entry.getKey());
         }
      }

      xsw.writeEndElement();
   }

   private void dumpInternal(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw, String partitionKey) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("JMS");
      xsw.writeAttribute("partitionKey", partitionKey);
      xsw.writeAttribute("id", this.frontEnd.getFrontEndId().toString());
      xsw.writeAttribute("serverName", this.mbeanName);
      xsw.writeAttribute("state", getStateName(this.state));
      xsw.writeAttribute("connectionsCurrentCount", String.valueOf(this.getConnectionsCurrentCount()));
      xsw.writeAttribute("connectionsHighCount", String.valueOf(this.getConnectionsHighCount()));
      xsw.writeAttribute("connectionsTotalCount", String.valueOf(this.getConnectionsTotalCount()));
      xsw.writeAttribute("jmsServersCurrentCount", String.valueOf(this.getJMSServersCurrentCount()));
      xsw.writeAttribute("jmsServersHighCount", String.valueOf(this.getJMSServersHighCount()));
      xsw.writeAttribute("jmsServersTotalCount", String.valueOf(this.getJMSServersTotalCount()));
      JMSDiagnosticImageSource.dumpHealthStateElement(xsw, this.getHealthState());
      xsw.writeStartElement("Connections");
      FEConnection[] feConnections = this.feManager.getFEConnections();
      if (feConnections != null && feConnections.length > 0) {
         for(int i = 0; i < feConnections.length; ++i) {
            feConnections[i].dump(imageSource, xsw);
         }
      }

      xsw.writeEndElement();
      xsw.writeStartElement("JMSServers");
      HashMap tempBackEnds;
      synchronized(this.shutdownLock) {
         HashMap backEnds = this.getBEDeployer().getBackEndsMap();
         tempBackEnds = (HashMap)backEnds.clone();
      }

      Iterator it = tempBackEnds.values().iterator();

      while(it.hasNext()) {
         BackEnd be = (BackEnd)it.next();
         be.dump(imageSource, xsw);
      }

      xsw.writeStartElement("Connections");
      BEConnection[] beConnections = this.beManager.getBEConnections();
      if (beConnections != null && beConnections.length > 0) {
         for(int i = 0; i < beConnections.length; ++i) {
            beConnections[i].dump(imageSource, xsw);
         }
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   public boolean getUse81StyleExecuteQueues() {
      return this.use81StyleExecuteQueues;
   }

   public void setConnectionFactoryListener(ConnectionFactoryListener listener) {
      synchronized(this) {
         this.connectionFactoryListener = listener;
      }
   }

   public void fireConnectionFactoryRemovalToListener(String jndiName, WebLogicMBean deploymentScope) {
      ConnectionFactoryListener listener;
      synchronized(this) {
         listener = this.connectionFactoryListener;
      }

      if (listener != null) {
         listener.onConnectionFactoryRemoval(jndiName, deploymentScope);
      }

   }

   public void addJMSServerListener(JMSTargetsListener listener) {
      synchronized(this.jmsServerListeners) {
         this.jmsServerListeners.add(listener);
      }
   }

   public void removeJMSServerListener(JMSTargetsListener listener) {
      synchronized(this.jmsServerListeners) {
         this.jmsServerListeners.remove(listener);
      }
   }

   private void fireListenersPrepare(DomainMBean domainBean, JMSServerMBean jmsServer, int event, boolean migrationInProgress) throws BeanUpdateRejectedException {
      boolean success = false;
      LinkedList preparedListeners = new LinkedList();
      synchronized(this.jmsServerListeners) {
         boolean var16 = false;

         Iterator it;
         JMSTargetsListener jsl;
         try {
            var16 = true;
            it = this.jmsServerListeners.iterator();

            while(true) {
               if (!it.hasNext()) {
                  success = true;
                  var16 = false;
                  break;
               }

               jsl = (JMSTargetsListener)it.next();
               jsl.prepareUpdate(domainBean, jmsServer, event, migrationInProgress);
               preparedListeners.addLast(jsl);
            }
         } finally {
            if (var16) {
               if (!success) {
                  Iterator it = preparedListeners.iterator();

                  while(it.hasNext()) {
                     JMSTargetsListener jsl = (JMSTargetsListener)it.next();
                     jsl.rollbackUpdate();
                  }
               }

            }
         }

         if (!success) {
            it = preparedListeners.iterator();

            while(it.hasNext()) {
               jsl = (JMSTargetsListener)it.next();
               jsl.rollbackUpdate();
            }
         }

      }
   }

   private void fireListenersActivateOrRollback(boolean forActivate) {
      synchronized(this.jmsServerListeners) {
         Iterator it = this.jmsServerListeners.iterator();

         while(it.hasNext()) {
            JMSTargetsListener jsl = (JMSTargetsListener)it.next();
            if (forActivate) {
               jsl.activateUpdate();
            } else {
               jsl.rollbackUpdate();
            }
         }

      }
   }

   public void startAddJMSServers(GenericManagedDeployment deployment, boolean migrationInProgress) throws BeanUpdateRejectedException {
      JMSServerMBean toAdd = (JMSServerMBean)deployment.getMBean();

      DomainMBean domain;
      try {
         domain = JMSLegalHelper.getDomain(toAdd);
      } catch (IllegalArgumentException var6) {
         throw new BeanUpdateRejectedException(var6.getMessage(), var6);
      }

      this.fireListenersPrepare(domain, toAdd, 1, migrationInProgress);
   }

   public void finishAddJMSServers(GenericManagedDeployment deployment, boolean forActivate) {
      this.fireListenersActivateOrRollback(forActivate);
      JMSServerMBean toAdd = (JMSServerMBean)deployment.getMBean();
      JMSServerBeanListener jsbl = new JMSServerBeanListener(toAdd);
      toAdd.addBeanUpdateListener(jsbl);
      synchronized(this.jmsServerBeanListeners) {
         this.jmsServerBeanListeners.add(new DescriptorAndListener(toAdd, jsbl));
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Listening for changes to JMSServer " + toAdd.getName());
      }

   }

   public void startRemoveJMSServers(GenericManagedDeployment deployment, boolean migrationInProgress) throws BeanUpdateRejectedException {
      DomainMBean domain = null;
      JMSServerMBean toRemove = (JMSServerMBean)deployment.getMBean();

      try {
         domain = JMSLegalHelper.getDomain(toRemove);
      } catch (IllegalArgumentException var6) {
         throw new BeanUpdateRejectedException(var6.getMessage(), var6);
      }

      this.fireListenersPrepare(domain, toRemove, 0, migrationInProgress);
   }

   public void finishRemoveJMSServers(GenericManagedDeployment deployment, boolean forActivate) {
      this.fireListenersActivateOrRollback(forActivate);
      JMSServerMBean toRemove = (JMSServerMBean)deployment.getMBean();
      synchronized(this.jmsServerBeanListeners) {
         Iterator it = this.jmsServerBeanListeners.iterator();

         while(true) {
            if (!it.hasNext()) {
               break;
            }

            DescriptorAndListener dal = (DescriptorAndListener)it.next();
            DescriptorBean db = dal.getDescriptorBean();
            JMSServerBeanListener jsbl = dal.getListener();
            if (jsbl.jmsServer.getName().equals(toRemove.getName())) {
               db.removeBeanUpdateListener(jsbl);
               jsbl.close();
               it.remove();
            }
         }
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Not listening for changes to removed JMSServer " + toRemove.getName());
      }

   }

   public String getPartitionName() {
      return this.cic.getPartitionName();
   }

   public String getPartitionId() {
      return this.cic.getPartitionId();
   }

   static {
      wlsServerSignatures.put("JMSDefaultConnectionFactoriesEnabled", Boolean.TYPE);
      CrossDomainSecurityManager.setCrossDomainSecurityUtil(new ServerCrossDomainSecurityUtil());
      enableAQJMSMsgListenerSyncMode();
      String property = null;

      try {
         property = System.getProperty("weblogic.jms.securityCheckInterval");
         if (property != null) {
            int parseInt = Integer.parseInt(property);
            securityCheckInterval = (long)parseInt;
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("INFO: Using a JMS security check interval of " + securityCheckInterval);
            }
         }
      } catch (NumberFormatException var3) {
         securityCheckInterval = 60000L;
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Warning: Unable to set securityCheckInterval to " + property, var3);
         }
      } catch (Throwable var4) {
         securityCheckInterval = 60000L;
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("WARNING: Using a JMS security check interval of " + securityCheckInterval, var4);
         }
      }

      property = null;

      try {
         property = System.getProperty("weblogic.jms.debugJndiNotPushCIC");
         debugJndiNotPushCIC = "true".equalsIgnoreCase(property);
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("INFO: Using " + debugJndiNotPushCIC + " " + "weblogic.jms.debugJndiNotPushCIC");
         }
      } catch (Throwable var2) {
         debugJndiNotPushCIC = false;
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("WARNING: Using " + debugJndiNotPushCIC + " " + "weblogic.jms.debugJndiNotPushCIC");
         }
      }

   }

   private static class DescriptorAndListener {
      private DescriptorBean db;
      private JMSServerBeanListener listener;

      private DescriptorAndListener(DescriptorBean paramDB, JMSServerBeanListener paramListener) {
         this.db = paramDB;
         this.listener = paramListener;
      }

      private DescriptorBean getDescriptorBean() {
         return this.db;
      }

      private JMSServerBeanListener getListener() {
         return this.listener;
      }

      // $FF: synthetic method
      DescriptorAndListener(DescriptorBean x0, JMSServerBeanListener x1, Object x2) {
         this(x0, x1);
      }
   }

   private class JMSServerBeanListener implements BeanUpdateListener {
      private JMSServerMBean jmsServer;
      private JMSServerMBean proposedJMSServer;
      private MigratableTargetMBean migratableTarget;
      int numFound;
      boolean jmsServerChanged;

      private JMSServerBeanListener(JMSServerMBean paramJMSServer) {
         this.jmsServer = paramJMSServer;
         TargetMBean[] targets = this.jmsServer.getTargets();
         if (targets.length >= 1) {
            TargetMBean target = targets[0];
            if (target instanceof MigratableTargetMBean) {
               this.migratableTarget = (MigratableTargetMBean)target;
               this.migratableTarget.addBeanUpdateListener(this);
            }

         }
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         if (this.migratableTarget == null) {
            this.jmsServerChanged = true;
         } else {
            DescriptorBean db = event.getProposedBean();
            if (db instanceof JMSServerMBean) {
               this.jmsServerChanged = true;
            } else {
               this.jmsServerChanged = false;
            }
         }

         boolean localFound = false;
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            BeanUpdateEvent.PropertyUpdate update = updates[lcv];
            if (this.jmsServerChanged && update.getPropertyName().equals("Targets")) {
               localFound = true;
               break;
            }

            if (!this.jmsServerChanged && update.getPropertyName().equals("UserPreferredServer")) {
               localFound = true;
               break;
            }
         }

         if (localFound) {
            ++this.numFound;
            this.proposedJMSServer = this.jmsServerChanged ? (JMSServerMBean)event.getProposedBean() : this.jmsServer;

            DomainMBean domainBean;
            try {
               domainBean = this.jmsServerChanged ? JMSLegalHelper.getDomain(this.proposedJMSServer) : JMSLegalHelper.getDomain((WebLogicMBean)event.getProposedBean());
            } catch (IllegalArgumentException var8) {
               throw new BeanUpdateRejectedException(var8.getMessage(), var8);
            }

            synchronized(this) {
               if (!this.isLocalJMSServerForUDD(this.proposedJMSServer) && !this.isLocallyTargeted(this.proposedJMSServer)) {
                  JMSService.this.fireListenersPrepare(domainBean, this.jmsServer, 2, false);
               }

            }
         }
      }

      public void activateUpdate(BeanUpdateEvent event) {
         if (this.numFound > 0) {
            --this.numFound;
            synchronized(this) {
               if (!this.isLocalJMSServerForUDD(this.proposedJMSServer) && !this.isLocallyTargeted(this.proposedJMSServer)) {
                  JMSService.this.fireListenersActivateOrRollback(true);
               }
            }

            if (this.jmsServerChanged) {
               MigratableTargetMBean oldTarget = this.migratableTarget;
               if (oldTarget != null) {
                  oldTarget.removeBeanUpdateListener(this);
               }

               this.migratableTarget = null;
               TargetMBean[] jmsServerTargets = this.jmsServer.getTargets();
               if (jmsServerTargets.length >= 1) {
                  TargetMBean jmsServerTarget = jmsServerTargets[0];
                  if (jmsServerTarget instanceof MigratableTargetMBean) {
                     this.migratableTarget = (MigratableTargetMBean)jmsServerTarget;
                     this.migratableTarget.addBeanUpdateListener(this);
                  }

               }
            }
         }
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         if (this.numFound > 0) {
            --this.numFound;
            synchronized(this) {
               if (!this.isLocalJMSServerForUDD(this.proposedJMSServer) && !this.isLocallyTargeted(this.proposedJMSServer)) {
                  JMSService.this.fireListenersActivateOrRollback(false);
               }

            }
         }
      }

      private void close() {
         if (this.migratableTarget != null) {
            this.migratableTarget.removeBeanUpdateListener(this);
         }
      }

      private boolean isLocallyTargeted(JMSServerMBean jmsServerMBean) {
         ServerMBean server = ManagementService.getRuntimeAccess(JMSService.kernelId).getServer();
         return server == null ? false : TargetingHelper.isLocallyTargeted(jmsServerMBean, server.getCluster() == null ? null : server.getCluster().getName(), server.getName());
      }

      private boolean isLocalJMSServerForUDD(JMSServerMBean js) {
         return JMSService.this.uddEntityHelper.isJMSServerLocal(GenericDeploymentManager.getDeploymentInstanceName(js, ManagementService.getRuntimeAccess(JMSService.kernelId).getServer().getName()));
      }

      // $FF: synthetic method
      JMSServerBeanListener(JMSServerMBean x1, Object x2) {
         this(x1);
      }
   }
}
