package weblogic.jms.saf;

import java.io.IOException;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import weblogic.application.ModuleException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.forwarder.ReplyHandler;
import weblogic.jms.safclient.agent.internal.ClientEnvironmentFactoryImpl;
import weblogic.logging.jms.JMSMessageLoggerFactory;
import weblogic.logging.jms.JMSSAFMessageLogger;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.JMSSAFMessageLogFileMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.SAFRemoteEndpointRuntimeMBean;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.saf.internal.SAFThresholdHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServiceFailureException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.admin.util.PartitionFileSystemUtils;
import weblogic.store.xa.PersistentStoreXA;

public class SAFAgentAdmin {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final HashMap BACKEND_ATTRIBUTES = new HashMap();
   private static final HashMap SAF_AGENT_ATTRIBUTES = new HashMap();
   private static final String SAF_MULTI_HUB = "weblogic.jms.saf.MultiHub";
   private static final boolean safMultiHubEnabled = initSAFMultiHub();
   private BackEnd backEnd;
   private GenericBeanListener backendChangeListener;
   private GenericBeanListener safChangeListener;
   private HashSet endpointRuntimes = new HashSet();
   private Set importedDestinations = Collections.synchronizedSet(new HashSet());
   private final boolean receivingOnly;
   private long defaultRetryDelayBase;
   private long defaultRetryDelayMaximum;
   private double defaultRetryDelayMultiplier;
   private int windowSize;
   private long defaultTimeToLive;
   private long windowInterval;
   private final HashMap remoteContexts = new HashMap();
   private boolean loggingEnabled;
   private long remoteEndpointsTotalCount;
   private long remoteEndpointsHighCount;
   private SAFAgentRuntimeMBeanAggregator runtimeMBean;

   SAFAgentAdmin(boolean receivingOnly) {
      this.receivingOnly = receivingOnly;
   }

   void prepare(GenericManagedDeployment deployment) throws DeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFAgentAdmin.prepare(" + deployment + "): this@" + this.hashCode() + ", receivingOnly=" + this.receivingOnly);
      }

      JMSService jmsService;
      try {
         jmsService = JMSService.getStartedService();
      } catch (ServiceFailureException | JMSException | ManagementException var17) {
         throw new DeploymentException(var17);
      }

      SAFAgentMBean mbean = (SAFAgentMBean)deployment.getMBean();
      this.loggingEnabled = mbean.isLoggingEnabled();
      if (!this.receivingOnly) {
         SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

         try {
            TargetMBean[] parentOfSAFAgent = mbean.getTargets();
            String alternativeSAFName = null;
            String safName;
            if (parentOfSAFAgent[0] instanceof MigratableTargetMBean) {
               safName = GenericDeploymentManager.getDecoratedDistributedInstanceName(mbean, parentOfSAFAgent[0].getName());
            } else {
               safName = deployment.getName();
               if (!GenericDeploymentManager.isClusterTargeted(mbean) && "DOMAIN".equals(deployment.getPartitionName())) {
                  alternativeSAFName = GenericDeploymentManager.getDecoratedDistributedInstanceName(mbean, ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName());
               }
            }

            this.backEnd = new BackEnd(safName, mbean, "SAFAgent", jmsService, alternativeSAFName);
         } catch (ManagementException var15) {
            throw new DeploymentException(var15);
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

         this.backEnd.setThresholdHandler(new SAFThresholdHandler(this.backEnd.getKernel(), this.backEnd.getName()));
         this.initializeChangeListeners(mbean);

         try {
            jmsService.getBEDeployer().addBackEnd(this.backEnd);
         } catch (JMSException var14) {
            throw new AssertionError(var14);
         }
      }

      try {
         this.runtimeMBean = new SAFAgentRuntimeMBeanAggregator(deployment.getName(), new SAFAgentRuntimeMBeanImpl(deployment.getName(), this));
      } catch (ManagementException var13) {
         throw new DeploymentException(var13);
      }

      SAFService.getSAFService().getRuntimeMBean().addAgent(this.runtimeMBean);
   }

   void activate(GenericManagedDeployment deployment) throws DeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFAgentAdmin.activate(" + deployment + "): this@" + this.hashCode() + ", receivingOnly=" + this.receivingOnly);
      }

      SAFAgentMBean mbean = (SAFAgentMBean)deployment.getMBean();
      if (!this.receivingOnly) {
         this.closeChangeListeners();
         this.initializeChangeListeners(mbean);
         this.backEnd.setPersistentStore(findPersistentStore(deployment));
         this.backEnd.setPagingDirectory(this.findPagingDirectory(mbean));
         this.backEnd.setJMSMessageLogger(this.findJMSSAFMessageLogger(mbean));

         try {
            this.backEnd.open();
         } catch (JMSException var4) {
            throw new DeploymentException(var4);
         }
      }
   }

   void deactivate() throws UndeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFAgentAdmin.deactivate(): this@" + this.hashCode() + ", receivingOnly=" + this.receivingOnly);
      }

      if (!this.receivingOnly) {
         this.removeJMSSAFMessageLogger();
         this.backEnd.close();
      }
   }

   public void unprepare() throws UndeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFAgentAdmin.unprepare(): this@" + this.hashCode() + ", receivingOnly=" + this.receivingOnly);
      }

      if (!this.receivingOnly) {
         JMSService jmsService = this.backEnd.getJmsService();
         this.backEnd.destroy();

         try {
            PrivilegedActionUtilities.unregister(this.runtimeMBean, KERNEL_ID);
         } catch (ManagementException var6) {
            throw new UndeploymentException(var6);
         }

         SAFService.getSAFService().getRuntimeMBean().removeAgent(this.runtimeMBean);
         this.closeChangeListeners();
         jmsService.getBEDeployer().removeBackEnd(this.backEnd);
         synchronized(this.remoteContexts) {
            Iterator i = this.remoteContexts.values().iterator();

            while(i.hasNext()) {
               RemoteContextAgent rc = (RemoteContextAgent)i.next();
               rc.closeListeners();
            }

         }
      }
   }

   private void initializeChangeListeners(SAFAgentMBean mbean) throws DeploymentException {
      this.backendChangeListener = new GenericBeanListener(mbean, this.backEnd, BACKEND_ATTRIBUTES, (Map)null);
      this.backendChangeListener.setCustomizer(this.backEnd);
      this.safChangeListener = new GenericBeanListener(mbean, this, SAF_AGENT_ATTRIBUTES, (Map)null);

      try {
         if (this.backendChangeListener != null) {
            this.backendChangeListener.initialize();
         }

         this.safChangeListener.initialize();
      } catch (ManagementException var3) {
         throw new DeploymentException(var3);
      }
   }

   private void closeChangeListeners() {
      if (this.backendChangeListener != null) {
         this.backendChangeListener.close();
      }

      this.safChangeListener.close();
   }

   SAFAgentRuntimeMBeanAggregator getRuntimeMBean() {
      return this.runtimeMBean;
   }

   private static PersistentStoreXA findPersistentStore(GenericManagedDeployment deployment) throws DeploymentException {
      SAFAgentMBean mbean = (SAFAgentMBean)deployment.getMBean();
      PersistentStoreXA ret;
      if (mbean.getStore() != null) {
         String storeName = mbean.getStore().getName();
         if (deployment.isClustered()) {
            if (deployment.isDistributed()) {
               storeName = GenericDeploymentManager.getDecoratedDistributedInstanceName(mbean.getStore(), deployment.getInstanceName());
            } else {
               storeName = GenericDeploymentManager.getDecoratedSingletonInstanceName(mbean.getStore(), deployment.getInstanceName());
            }
         }

         ret = (PersistentStoreXA)PersistentStoreManager.getManager().getStore(storeName);
         if (ret == null) {
            throw new DeploymentException("The persistent store \"" + storeName + "\" does not exist");
         }
      } else {
         ret = (PersistentStoreXA)PersistentStoreManager.getManager().getDefaultStore();
         if (ret == null) {
            throw new DeploymentException("The default persistent store does not exist");
         }
      }

      return ret;
   }

   void addImportedDestination(ImportedDestination id) {
      this.importedDestinations.add(id);
   }

   void removeImportedDestination(ImportedDestination id) {
      this.importedDestinations.remove(id);
   }

   private String findPagingDirectory(SAFAgentMBean mbean) {
      JMSService jmsService = this.backEnd.getJmsService();
      String serverName = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName();
      ComponentInvocationContext cic = jmsService.getComponentInvocationContext();
      String dir = PartitionFileSystemUtils.locatePagingDirectory(cic, serverName, mbean.getPagingDirectory());
      return dir;
   }

   synchronized void addRemoteEndpointRuntimeMBean(SAFRemoteEndpointRuntimeMBean endpointRuntime) throws ModuleException {
      ++this.remoteEndpointsTotalCount;
      if (this.remoteEndpointsTotalCount > this.remoteEndpointsHighCount) {
         this.remoteEndpointsHighCount = this.remoteEndpointsTotalCount;
      }

      this.endpointRuntimes.add(endpointRuntime);
   }

   synchronized void removeRemoteEndpointRuntimeMBean(SAFRemoteEndpointRuntimeMBean endpointRuntime) throws ModuleException {
      --this.remoteEndpointsTotalCount;
      this.endpointRuntimes.remove(endpointRuntime);
   }

   public RemoteContextAgent findOrCreateRemoteContext(String rcBeanFullyQualifiedName, SAFRemoteContextBean rcBean) {
      synchronized(this.remoteContexts) {
         RemoteContextAgent ret = (RemoteContextAgent)this.remoteContexts.get(rcBeanFullyQualifiedName);
         if (ret == null) {
            ReplyHandler replyHandler = rcBean != null ? new SAFOutgoingReplyHandler(rcBean.getReplyToSAFRemoteContextName()) : new SAFOutgoingReplyHandler((String)null);
            ret = new RemoteContextAgent(rcBeanFullyQualifiedName, rcBean, replyHandler, new ClientEnvironmentFactoryImpl());
            ret.setRetryDelayBase(this.defaultRetryDelayBase);
            ret.setRetryDelayMaximum(this.defaultRetryDelayMaximum);
            ret.setRetryDelayMultiplier(this.defaultRetryDelayMultiplier);
            ret.setWindowSize(this.windowSize);
            ret.setWindowInterval(this.windowInterval);
            this.remoteContexts.put(rcBeanFullyQualifiedName, ret);
         }

         return ret;
      }
   }

   SAFRemoteEndpointRuntimeMBean[] getRemoteEndpoints() {
      SAFRemoteEndpointRuntimeMBean[] ret = new SAFRemoteEndpointRuntimeMBean[this.endpointRuntimes.size()];
      return (SAFRemoteEndpointRuntimeMBean[])((SAFRemoteEndpointRuntimeMBean[])this.endpointRuntimes.toArray(ret));
   }

   public long getRemoteEndpointsCurrentCount() {
      return (long)this.endpointRuntimes.size();
   }

   public long getRemoteEndpointsHighCount() {
      return this.remoteEndpointsHighCount;
   }

   public long getRemoteEndpointsTotalCount() {
      return this.remoteEndpointsTotalCount;
   }

   public BackEnd getBackEnd() {
      return this.backEnd;
   }

   boolean isLoggingEnabled() {
      return this.loggingEnabled;
   }

   public void setDefaultRetryDelayBase(long defaultRetryDelayBase) {
      this.defaultRetryDelayBase = defaultRetryDelayBase;
      synchronized(this.remoteContexts) {
         Iterator i = this.remoteContexts.values().iterator();

         while(i.hasNext()) {
            RemoteContextAgent rc = (RemoteContextAgent)i.next();
            rc.setRetryDelayBase(defaultRetryDelayBase);
         }

      }
   }

   public void setDefaultRetryDelayMaximum(long defaultRetryDelayMaximum) {
      this.defaultRetryDelayMaximum = defaultRetryDelayMaximum;
      synchronized(this.remoteContexts) {
         Iterator i = this.remoteContexts.values().iterator();

         while(i.hasNext()) {
            RemoteContextAgent rc = (RemoteContextAgent)i.next();
            rc.setRetryDelayMaximum(defaultRetryDelayMaximum);
         }

      }
   }

   public void setDefaultRetryDelayMultiplier(double defaultRetryDelayMultiplier) {
      this.defaultRetryDelayMultiplier = defaultRetryDelayMultiplier;
      synchronized(this.remoteContexts) {
         Iterator i = this.remoteContexts.values().iterator();

         while(i.hasNext()) {
            RemoteContextAgent rc = (RemoteContextAgent)i.next();
            rc.setRetryDelayMultiplier(defaultRetryDelayMultiplier);
         }

      }
   }

   public void setWindowSize(int defaultWindowSize) {
      this.windowSize = defaultWindowSize;
      synchronized(this.remoteContexts) {
         Iterator i = this.remoteContexts.values().iterator();

         while(i.hasNext()) {
            RemoteContextAgent rc = (RemoteContextAgent)i.next();
            rc.setWindowSize(defaultWindowSize);
         }

      }
   }

   public void setLoggingEnabled(boolean loggingEnabled) {
      this.loggingEnabled = loggingEnabled;
   }

   public void setConversationIdleTimeMaximum(long conversationIdleTimeMax) {
   }

   public void setAcknowledgeInterval(long acknowledgeInterval) {
   }

   public void setDefaultTimeToLive(long defaultTimeToLive) {
      this.defaultTimeToLive = defaultTimeToLive;
      Iterator ids = this.importedDestinations.iterator();

      while(ids.hasNext()) {
         ImportedDestination id = (ImportedDestination)ids.next();
         id.setAgentTimeToLiveOverride(defaultTimeToLive);
      }

   }

   long getDefaultTimeToLive() {
      return this.defaultTimeToLive;
   }

   public void setWindowInterval(long windowInterval) {
      this.windowInterval = windowInterval;
      synchronized(this.remoteContexts) {
         Iterator i = this.remoteContexts.values().iterator();

         while(i.hasNext()) {
            RemoteContextAgent rc = (RemoteContextAgent)i.next();
            rc.setWindowInterval(windowInterval);
         }

      }
   }

   long getWindowInterval() {
      return this.windowInterval;
   }

   public void setIncomingPausedAtStartup(boolean incomingPausedAtStartup) {
      if (!this.receivingOnly) {
         this.backEnd.setProductionPausedAtStartup("" + incomingPausedAtStartup);
      }
   }

   public void setForwardingPausedAtStartup(boolean forwardingPausedAtStartup) {
      if (!this.receivingOnly) {
         this.backEnd.setConsumptionPausedAtStartup("" + forwardingPausedAtStartup);
      }
   }

   public void setReceivingPausedAtStartup(boolean receivingPausedAtStartup) {
   }

   private JMSSAFMessageLogger findJMSSAFMessageLogger(SAFAgentMBean mbean) throws DeploymentException {
      try {
         JMSSAFMessageLogFileMBean logFile = mbean.getJMSSAFMessageLogFile();
         return JMSMessageLoggerFactory.findOrCreateJMSSAFMessageLogger(logFile, this.runtimeMBean);
      } catch (IOException var3) {
         throw new DeploymentException("Cannot find or create JMS message log file for saf agent " + mbean.getName(), var3);
      }
   }

   private static boolean initSAFMultiHub() {
      boolean rtn = true;

      try {
         String safMultiHub = System.getProperty("weblogic.jms.saf.MultiHub");
         if (safMultiHub != null && safMultiHub.equalsIgnoreCase("false")) {
            rtn = false;
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("SAF Multi Hub Enabled: " + rtn);
         }
      } catch (RuntimeException var2) {
         var2.printStackTrace();
      }

      return rtn;
   }

   public boolean isSAFMultiHubEnabled() {
      return safMultiHubEnabled;
   }

   private void removeJMSSAFMessageLogger() {
      try {
         JMSMessageLoggerFactory.removeJMSSAFMessageLogger(this.runtimeMBean);
      } catch (IOException var2) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Exception thrown during removal of" + this.runtimeMBean.getJMSSAFAgentRuntime().getName() + " logger ");
            var2.printStackTrace();
         }
      }

   }

   static {
      BACKEND_ATTRIBUTES.put("BytesMaximum", Long.TYPE);
      BACKEND_ATTRIBUTES.put("BytesThresholdHigh", Long.TYPE);
      BACKEND_ATTRIBUTES.put("BytesThresholdLow", Long.TYPE);
      BACKEND_ATTRIBUTES.put("MessagesMaximum", Long.TYPE);
      BACKEND_ATTRIBUTES.put("MessagesThresholdHigh", Long.TYPE);
      BACKEND_ATTRIBUTES.put("MessagesThresholdLow", Long.TYPE);
      BACKEND_ATTRIBUTES.put("MaximumMessageSize", Integer.TYPE);
      BACKEND_ATTRIBUTES.put("MessageBufferSize", Long.TYPE);
      BACKEND_ATTRIBUTES.put("StoreMessageCompressionEnabled", Boolean.TYPE);
      BACKEND_ATTRIBUTES.put("PagingMessageCompressionEnabled", Boolean.TYPE);
      BACKEND_ATTRIBUTES.put("MessageCompressionOptions", String.class);
      BACKEND_ATTRIBUTES.put("MessageCompressionOptionsOverride", String.class);
      SAF_AGENT_ATTRIBUTES.put("DefaultRetryDelayBase", Long.TYPE);
      SAF_AGENT_ATTRIBUTES.put("DefaultRetryDelayMaximum", Long.TYPE);
      SAF_AGENT_ATTRIBUTES.put("DefaultRetryDelayMultiplier", Double.TYPE);
      SAF_AGENT_ATTRIBUTES.put("WindowSize", Integer.TYPE);
      SAF_AGENT_ATTRIBUTES.put("LoggingEnabled", Boolean.TYPE);
      SAF_AGENT_ATTRIBUTES.put("ConversationIdleTimeMaximum", Long.TYPE);
      SAF_AGENT_ATTRIBUTES.put("AcknowledgeInterval", Long.TYPE);
      SAF_AGENT_ATTRIBUTES.put("DefaultTimeToLive", Long.TYPE);
      SAF_AGENT_ATTRIBUTES.put("WindowInterval", Long.TYPE);
      SAF_AGENT_ATTRIBUTES.put("IncomingPausedAtStartup", Boolean.TYPE);
      SAF_AGENT_ATTRIBUTES.put("ForwardingPausedAtStartup", Boolean.TYPE);
      SAF_AGENT_ATTRIBUTES.put("ReceivingPausedAtStartup", Boolean.TYPE);
   }
}
