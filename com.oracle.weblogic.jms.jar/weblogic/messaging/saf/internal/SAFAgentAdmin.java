package weblogic.messaging.saf.internal;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.NamingException;
import weblogic.health.HealthState;
import weblogic.jms.saf.SAFAgentRuntimeMBeanAggregator;
import weblogic.jms.saf.SAFService;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SAFAgentRuntimeMBean;
import weblogic.management.runtime.SAFConversationRuntimeMBean;
import weblogic.management.runtime.SAFRemoteEndpointRuntimeMBean;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.messaging.ID;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.store.SAFStore;
import weblogic.messaging.saf.store.SAFStoreManager;
import weblogic.messaging.saf.utils.Util;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.common.PartitionNameUtils;

public final class SAFAgentAdmin extends SAFStatisticsCommonMBeanImpl implements SAFAgentRuntimeMBean {
   static final long serialVersionUID = 4301254277290100353L;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final int SENDING_ONLY = 1;
   public static final int RECEIVING_ONLY = 2;
   public static final int BOTH = 3;
   private SAFServiceAdmin safServiceAdmin;
   private SAFAgentMBean mbean;
   private PersistentStoreMBean storeMBean;
   private SendingAgentImpl sendingAgent;
   private ReceivingAgentImpl receivingAgent;
   private int agentType;
   private HashMap remoteEndpoints;
   private int remoteEndpointsCurrentCount;
   private int remoteEndpointsHighCount;
   private int remoteEndpointsTotalCount;
   private String decoratedName;
   private GenericBeanListener changeListener;
   private static final HashMap agentBeanAttributes = new HashMap();
   private String agentName;
   private String agentConfigName;
   private LogRuntimeMBean logRuntime;
   private String alternativeAgentName;

   SAFAgentAdmin(SAFServiceAdmin safServiceAdmin, SAFAgentMBean mbean, GenericManagedDeployment deployment) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(mbean.getName()), (RuntimeMBean)null, false);
      this.decoratedName = mbean.getName();
      this.agentConfigName = GenericDeploymentManager.getUndecoratedConfigBeanName(mbean);
      SAFService.getSAFService().getRuntimeMBean().getAgent(deployment.getName()).setDelegate2(this);
      this.safServiceAdmin = safServiceAdmin;
      this.mbean = mbean;
      this.storeMBean = mbean.getStore();
      this.remoteEndpoints = new HashMap();
      SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);
      this.agentName = deployment.getName();
      TargetMBean[] targets = mbean.getTargets();
      if (targets != null && targets[0] != null && targets[0] instanceof MigratableTargetMBean) {
         this.alternativeAgentName = GenericDeploymentManager.getDecoratedDistributedInstanceName(mbean, targets[0].getName());
      } else if (!GenericDeploymentManager.isClusterTargeted(mbean) && "DOMAIN".equals(deployment.getPartitionName())) {
         this.alternativeAgentName = GenericDeploymentManager.getDecoratedDistributedInstanceName(mbean, ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName());
      }

      this.checkTargets();
      this.changeListener = new GenericBeanListener(mbean, this, agentBeanAttributes, (Map)null);
   }

   public String getDecoratedName() {
      return this.decoratedName;
   }

   public String getConfigName() {
      return this.agentConfigName;
   }

   public String getAgentName() {
      return this.agentName;
   }

   SAFServiceAdmin getSAFServiceAdmin() {
      return this.safServiceAdmin;
   }

   int getAgentType() {
      return this.agentType;
   }

   public SAFAgentMBean getMBean() {
      return this.mbean;
   }

   private void checkTargets() throws DeploymentException {
      TargetMBean[] agentTargets = this.mbean.getTargets();
      PersistentStoreMBean storeMBean = this.mbean.getStore();
      if (storeMBean != null) {
         TargetMBean[] storeTargets = storeMBean.getTargets();
         DeploymentException ex;
         if (storeTargets.length > 1) {
            ex = new DeploymentException("SAF Agent = " + this.mbean.getName() + " has a Persistent Store = " + storeMBean.getName() + " which has multiple targets");
            throw ex;
         } else if (storeTargets.length == 0) {
            ex = new DeploymentException("SAF Agent= " + this.mbean.getName() + " has a Persistent Store " + storeMBean.getName() + " which is not targetted");
            throw ex;
         } else if (agentTargets.length > 1) {
            ex = new DeploymentException("SAF Agent = " + this.mbean.getName() + " has multiple Tragets");
            throw ex;
         } else if (!agentTargets[0].getName().equals(storeTargets[0].getName())) {
            ex = new DeploymentException("SAF Agent = " + this.mbean.getName() + " Target = " + agentTargets[0].getName() + " is not the same as Targets of the Persistent Store = " + storeMBean.getName() + " Targets = " + storeTargets[0].getName());
            throw ex;
         }
      }
   }

   void start(GenericManagedDeployment deployment) throws NamingException, SAFException {
      if (!this.safServiceAdmin.isPartition()) {
         Object originalScopeMBean = PathHelper.getOriginalScopeMBean(this.mbean);
         if (!(originalScopeMBean instanceof ResourceGroupTemplateMBean) && !(originalScopeMBean instanceof ResourceGroupMBean)) {
            String typeStr = this.mbean.getServiceType();
            if ("Sending-only".equals(typeStr)) {
               this.findOrCreateSendingAgentImpl(this.agentName, deployment);
               this.agentType = 1;
            } else if ("Receiving-only".equals(typeStr)) {
               this.findOrCreateReceivingAgentImpl(this.agentName, deployment);
               this.agentType = 2;
            } else if ("Both".equals(typeStr)) {
               this.findOrCreateSendingAgentImpl(this.agentName, deployment);
               this.findOrCreateReceivingAgentImpl(this.agentName, deployment);
               this.agentType = 3;
            }

         }
      }
   }

   private SAFStore createSAFStore(String agentName, boolean isRA, GenericManagedDeployment deployment) throws SAFException {
      return SAFStoreManager.getManager().createSAFStore(this.storeMBean, agentName, isRA, deployment, this.alternativeAgentName);
   }

   private void findOrCreateSendingAgentImpl(String name, GenericManagedDeployment deployment) throws NamingException, SAFException {
      SAFStore store = this.createSAFStore(name, false, deployment);
      this.sendingAgent = (SendingAgentImpl)store.getSendingAgent();
      if (this.sendingAgent == null) {
         this.sendingAgent = new SendingAgentImpl(name, this, store);
      } else {
         this.sendingAgent.init(name, this, store);
      }

   }

   private void findOrCreateReceivingAgentImpl(String name, GenericManagedDeployment deployment) throws NamingException, SAFException {
      SAFStore store = this.createSAFStore(name, true, deployment);
      this.receivingAgent = (ReceivingAgentImpl)store.getReceivingAgent();
      if (this.receivingAgent == null) {
         this.receivingAgent = new ReceivingAgentImpl(name, this, store);
      } else {
         this.receivingAgent.init(name, this, store);
      }

   }

   Agent getSendingAgentImpl() {
      return this.sendingAgent;
   }

   Agent getReceivingAgentImpl() {
      return this.receivingAgent;
   }

   void close() {
      SAFAgentRuntimeMBeanAggregator aggr = SAFService.getSAFService().getRuntimeMBean().getAgent(this.decoratedName);
      if (aggr != null) {
         aggr.setDelegate2((SAFAgentRuntimeMBean)null);
      }

      if (this.changeListener != null) {
         this.changeListener.close();
         this.changeListener = null;
      }

      try {
         if (this.sendingAgent != null) {
            this.sendingAgent.close(false);
         }
      } catch (Exception var8) {
      }

      try {
         if (this.receivingAgent != null) {
            this.receivingAgent.close(false);
         }
      } catch (Exception var7) {
      }

      Iterator itr2 = null;
      synchronized(this) {
         itr2 = ((HashMap)this.remoteEndpoints.clone()).values().iterator();
         this.remoteEndpoints.clear();
      }

      while(itr2.hasNext()) {
         try {
            RemoteEndpointRuntimeDelegate endpoint = (RemoteEndpointRuntimeDelegate)itr2.next();
            endpoint.close();
         } catch (Exception var5) {
         }
      }

   }

   void suspend(boolean force) {
      if (this.sendingAgent != null) {
         this.sendingAgent.suspend(force);
      }

      if (this.receivingAgent != null) {
         this.receivingAgent.suspend(force);
      }

   }

   void resume() throws SAFException {
      if (this.sendingAgent != null) {
         this.sendingAgent.resume();
      }

      if (this.receivingAgent != null) {
         this.receivingAgent.resume();
      }

   }

   synchronized RemoteEndpointRuntimeDelegate findOrCreateRemoteEndpointRuntime(String url, int type, final Topic topicFinal) throws ManagementException {
      RemoteEndpointRuntimeDelegate endpoint = (RemoteEndpointRuntimeDelegate)this.remoteEndpoints.get(url);
      if (endpoint != null) {
         return endpoint;
      } else {
         final String urlFinal = url;
         final int typeFinal = type;
         final ID idFinal = Util.generateID();
         final SAFAgentAdmin thisFinal = this;

         try {
            endpoint = (RemoteEndpointRuntimeDelegate)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws ManagementException {
                  return new RemoteEndpointRuntimeDelegate(thisFinal, idFinal, urlFinal, typeFinal, topicFinal);
               }
            });
         } catch (PrivilegedActionException var10) {
            throw (ManagementException)var10.getException();
         }

         ++this.remoteEndpointsCurrentCount;
         ++this.remoteEndpointsTotalCount;
         if (this.remoteEndpointsCurrentCount > this.remoteEndpointsHighCount) {
            this.remoteEndpointsHighCount = this.remoteEndpointsCurrentCount;
         }

         this.remoteEndpoints.put(url, endpoint);
         return endpoint;
      }
   }

   synchronized RemoteEndpointRuntimeDelegate getRemoteEndpoint(String url) {
      return (RemoteEndpointRuntimeDelegate)this.remoteEndpoints.get(url);
   }

   synchronized void removeRemoteEndpoint(RemoteEndpointRuntimeDelegate endpoint) {
      RemoteEndpointRuntimeDelegate remoteEndpoint = (RemoteEndpointRuntimeDelegate)this.remoteEndpoints.remove(endpoint.getURL());
      if (remoteEndpoint != null) {
         --this.remoteEndpointsCurrentCount;
         remoteEndpoint.close();
      }

   }

   public HealthState getHealthState() {
      HealthState saState = null;
      HealthState raState = null;
      if (this.sendingAgent != null) {
         saState = this.sendingAgent.getHealthState();
      }

      if (this.receivingAgent != null) {
         saState = this.receivingAgent.getHealthState();
      }

      return this.combineState(saState, (HealthState)raState);
   }

   public synchronized SAFRemoteEndpointRuntimeMBean[] getRemoteEndpoints() {
      RemoteEndpointRuntimeDelegate[] eps = new RemoteEndpointRuntimeDelegate[this.remoteEndpoints.size()];
      this.remoteEndpoints.values().toArray(eps);
      return eps;
   }

   public synchronized long getRemoteEndpointsCurrentCount() {
      return (long)this.remoteEndpointsCurrentCount;
   }

   public synchronized long getRemoteEndpointsHighCount() {
      return (long)this.remoteEndpointsHighCount;
   }

   public synchronized long getRemoteEndpointsTotalCount() {
      return (long)this.remoteEndpointsTotalCount;
   }

   public void pauseIncoming() throws SAFException {
      if (this.sendingAgent != null) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Pausing sending agent " + this.getName() + " for incoming");
         }

         this.sendingAgent.pauseIncoming();
      }

   }

   public void resumeIncoming() throws SAFException {
      if (this.sendingAgent != null) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Resuming sending agent " + this.getName() + " for incoming");
         }

         this.sendingAgent.resumeIncoming();
      }

   }

   public boolean isPausedForIncoming() {
      return this.sendingAgent != null ? this.sendingAgent.isPausedForIncoming() : false;
   }

   public void pauseForwarding() throws SAFException {
      if (this.sendingAgent != null) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Pausing sending agent " + this.getName() + " for forwarding");
         }

         this.sendingAgent.pauseForwarding();
      }

   }

   public void resumeForwarding() throws SAFException {
      if (this.sendingAgent != null) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Resuming sending agent " + this.getName() + " for forwarding");
         }

         this.sendingAgent.resumeForwarding();
      }

   }

   public boolean isPausedForForwarding() {
      return this.sendingAgent != null ? this.sendingAgent.isPausedForForwarding() : false;
   }

   public void pauseReceiving() throws SAFException {
      if (this.receivingAgent != null) {
         if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Pausing receiving agent " + this.getName() + " for receiving");
         }

         this.receivingAgent.pauseReceiving();
      }

   }

   public void resumeReceiving() throws SAFException {
      if (this.receivingAgent != null) {
         if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("Resuming receiving agent " + this.getName() + " for receiving");
         }

         this.receivingAgent.resumeReceiving();
      }

   }

   public boolean isPausedForReceiving() {
      return this.receivingAgent != null ? this.receivingAgent.isPausedForReceiving() : false;
   }

   public SAFConversationRuntimeMBean[] getConversations() {
      return this.sendingAgent != null ? this.sendingAgent.getConversationRuntimeDelegates() : new SAFConversationRuntimeMBean[0];
   }

   public long getConversationsCurrentCount() {
      return this.sendingAgent != null ? this.sendingAgent.getConversationsCurrentCount() : 0L;
   }

   public long getConversationsHighCount() {
      return this.sendingAgent != null ? this.sendingAgent.getConversationsHighCount() : 0L;
   }

   public long getConversationsTotalCount() {
      return this.sendingAgent != null ? this.sendingAgent.getConversationsTotalCount() : 0L;
   }

   public long getMessagesCurrentCount() {
      return 0L;
   }

   public long getMessagesPendingCount() {
      return 0L;
   }

   public long getMessagesHighCount() {
      return 0L;
   }

   public long getMessagesReceivedCount() {
      return 0L;
   }

   public long getMessagesThresholdTime() {
      return 0L;
   }

   public long getBytesCurrentCount() {
      return 0L;
   }

   public long getBytesPendingCount() {
      return 0L;
   }

   public long getBytesHighCount() {
      return 0L;
   }

   public long getBytesReceivedCount() {
      return 0L;
   }

   public long getBytesThresholdTime() {
      return 0L;
   }

   public long getFailedMessagesTotal() {
      return this.sendingAgent != null ? this.sendingAgent.getFailedMessagesTotal() : 0L;
   }

   public long getDefaultRetryDelayBase() {
      return this.mbean.getDefaultRetryDelayBase();
   }

   public long getDefaultRetryDelayMaximum() {
      return this.mbean.getDefaultRetryDelayMaximum();
   }

   public double getDefaultRetryDelayMultiplier() {
      return this.mbean.getDefaultRetryDelayMultiplier();
   }

   public long getDefaultTimeToLive() {
      return this.mbean.getDefaultTimeToLive();
   }

   public long getMessageBufferSize() {
      return this.mbean.getMessageBufferSize();
   }

   public long getConversationIdleTimeMaximum() {
      return this.mbean.getConversationIdleTimeMaximum();
   }

   public long getBytesMaximum() {
      return this.mbean.getBytesMaximum();
   }

   public long getBytesThresholdHigh() {
      return this.mbean.getBytesThresholdHigh();
   }

   public long getBytesThresholdLow() {
      return this.mbean.getBytesThresholdLow();
   }

   public long getMessagesMaximum() {
      return this.mbean.getMessagesMaximum();
   }

   public long getMessagesThresholdHigh() {
      return this.mbean.getMessagesThresholdHigh();
   }

   public long getMessagesThresholdLow() {
      return this.mbean.getMessagesThresholdLow();
   }

   public int getMaximumMessageSize() {
      return this.mbean.getMaximumMessageSize();
   }

   public boolean isLoggingEnabled() {
      return this.mbean.isLoggingEnabled();
   }

   public int getWindowSize() {
      return this.mbean.getWindowSize();
   }

   public long getAcknowledgeInterval() {
      return this.mbean.getAcknowledgeInterval();
   }

   public void setDefaultRetryDelayBase(long value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setDefaultRetryDelayBase(value);
      }

   }

   public void setDefaultRetryDelayMaximum(long value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setDefaultRetryDelayMaximum(value);
      }

   }

   public void setDefaultRetryDelayMultiplier(double value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setDefaultRetryDelayMultiplier(value);
      }

   }

   public void setDefaultTimeToLive(long value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setDefaultTimeToLive(value);
      }

      if (this.receivingAgent != null) {
         this.receivingAgent.setDefaultTimeToLive(value);
      }

   }

   public void setMessageBufferSize(long value) {
   }

   public void setConversationIdleTimeMaximum(long value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setConversationIdleTimeMaximum(value);
      }

      if (this.receivingAgent != null) {
         this.receivingAgent.setConversationIdleTimeMaximum(value);
      }

   }

   public void setBytesMaximum(long value) {
   }

   public void setBytesThresholdHigh(long value) {
   }

   public void setBytesThresholdLow(long value) {
   }

   public void setMessagesMaximum(long value) {
   }

   public void setMessagesThresholdHigh(long value) {
   }

   public void setMessagesThresholdLow(long value) {
   }

   public void setMaximumMessageSize(int value) {
   }

   public void setLoggingEnabled(boolean value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setLoggingEnabled(value);
      }

   }

   public void setWindowSize(int value) {
      if (this.sendingAgent != null) {
         this.sendingAgent.setWindowSize(value);
      }

      if (this.receivingAgent != null) {
         this.receivingAgent.setWindowSize(value);
      }

   }

   public void setAcknowledgeInterval(long value) {
      if (this.receivingAgent != null) {
         this.receivingAgent.setAcknowledgementInterval(value);
      }

   }

   private HealthState combineState(HealthState saState, HealthState raState) {
      if (saState == null) {
         return raState;
      } else if (raState == null) {
         return saState;
      } else {
         return saState.getState() <= raState.getState() ? raState : saState;
      }
   }

   boolean isActiveForWSRM() {
      SAFConversationRuntimeMBean[] conversations = this.getConversations();
      if (conversations != null && conversations.length != 0) {
         return true;
      } else {
         if (this.sendingAgent != null) {
            this.sendingAgent.pauseIncoming();
         }

         if (this.receivingAgent != null) {
            this.receivingAgent.pauseReceiving();
         }

         return false;
      }
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public void setLogRuntime(LogRuntimeMBean logRuntime) {
      this.logRuntime = logRuntime;
   }

   static {
      agentBeanAttributes.put("AcknowledgeInterval", Long.TYPE);
      agentBeanAttributes.put("BytesMaximum", Long.TYPE);
      agentBeanAttributes.put("BytesThresholdHigh", Long.TYPE);
      agentBeanAttributes.put("BytesThresholdLow", Long.TYPE);
      agentBeanAttributes.put("ConversationIdleTimeMaximum", Long.TYPE);
      agentBeanAttributes.put("DefaultRetryDelayBase", Long.TYPE);
      agentBeanAttributes.put("DefaultRetryDelayMaximum", Long.TYPE);
      agentBeanAttributes.put("DefaultRetryDelayMultiplier", Double.TYPE);
      agentBeanAttributes.put("DefaultTimeToLive", Long.TYPE);
      agentBeanAttributes.put("LoggingEnabled", Boolean.TYPE);
      agentBeanAttributes.put("MaximumMessageSize", Integer.TYPE);
      agentBeanAttributes.put("MessagesMaximum", Long.TYPE);
      agentBeanAttributes.put("MessagesThresholdHigh", Long.TYPE);
      agentBeanAttributes.put("MessagesThresholdLow", Long.TYPE);
      agentBeanAttributes.put("MessageBufferSize", Long.TYPE);
      agentBeanAttributes.put("WindowSize", Integer.TYPE);
   }
}
