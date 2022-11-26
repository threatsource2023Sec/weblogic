package weblogic.jms.saf;

import java.security.AccessController;
import javax.jms.JMSException;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BEDestinationSecurityImpl;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.EntityName;
import weblogic.jms.forwarder.RuntimeHandler;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SAFRemoteEndpointRuntimeMBean;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.kernel.Queue;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class ImportedDestination {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String name;
   private final ImportedDestinationGroup idGroup;
   private final IDBeanHandler idBeanHandler;
   private SAFQueueImpl managedDestination;
   private String remoteJNDIName;
   private int nonPersistentQos = 1;
   private int persistentQos = 2;
   private SAFRemoteEndpointCustomizer runtimeMBean;
   private final SAFAgentAdmin safAgent;
   private String agentName;
   private String applicationId;
   private String earModuleName;
   private RemoteContextAgent remoteContext;
   private long timeToLiveDefault;
   private boolean useSafTimeToLiveDefault;
   private long agentTimeToLiveOverride;
   private EntityName entityName;
   private String destinationType;
   private String messageLoggingFormat;
   private boolean messageLoggingEnabled = false;

   ImportedDestination(IDBeanHandler idBeanHandler, ImportedDestinationGroup idGroup, String applicationId, EntityName entityName, String name, String agentName, long timeToLiveDefault, boolean useSafTimeToLiveDefault, String destinationType, String nonPersistentQos, String messageLoggingFormat, boolean messageLoggingEnabled, String persistentQos) throws ModuleException {
      this.idGroup = idGroup;
      this.applicationId = applicationId;
      this.entityName = entityName;
      this.earModuleName = entityName.getEARModuleName();
      this.name = name;
      this.agentName = agentName;
      this.timeToLiveDefault = timeToLiveDefault;
      this.useSafTimeToLiveDefault = useSafTimeToLiveDefault;
      SAFService safService = SAFService.getSAFServiceWithModuleException(idGroup.getPartitionName());
      this.safAgent = safService.getDeployer().getAgent(agentName);
      this.idBeanHandler = idBeanHandler;
      this.destinationType = destinationType;
      this.setNonPersistentQos(nonPersistentQos);
      this.setPersistentQos(persistentQos);
      this.messageLoggingFormat = messageLoggingFormat;
      this.messageLoggingEnabled = messageLoggingEnabled;
   }

   public void prepare() throws ModuleException {
      this.safAgent.addImportedDestination(this);
      BackEnd backEnd = this.safAgent.getBackEnd();

      try {
         this.managedDestination = new SAFQueueImpl(this.safAgent, backEnd.getFullSAFDestinationName(this.name), false, this.entityName, this.destinationType, backEnd.getAlternativeFullSAFDestinationName(this.name));
      } catch (JMSException var12) {
         throw new ModuleException(var12);
      }

      this.agentTimeToLiveOverride = this.safAgent.getDefaultTimeToLive();
      this.applyTimeToLive();
      this.managedDestination.setPriority(-1);
      this.managedDestination.setTimeToLive(-1L);
      this.managedDestination.setRedeliveryLimit(Integer.MAX_VALUE);
      this.managedDestination.setNonPersistentQos(this.nonPersistentQos);
      this.managedDestination.setPersistentQos(this.persistentQos);
      this.managedDestination.setLocalJNDIName(this.name + "@" + this.agentName + "@SAF");
      if (this.idBeanHandler.getSafErrorHandling() == null) {
         this.managedDestination.initializeErrorHandling((String)null);
      } else {
         this.managedDestination.initializeErrorHandling(this.idBeanHandler.getSafErrorHandling().getName());
      }

      DestinationImpl destinationImpl = new DestinationImpl(this.managedDestination.getDestinationTypeIndicator(), backEnd.getName(), backEnd.getConfigName(), backEnd.getPersistentStore() != null ? backEnd.getPersistentStore().getName() : null, this.managedDestination.getName(), this.applicationId, this.earModuleName, backEnd.getJMSServerId(), this.managedDestination.getJMSID(), this.managedDestination.getCreationTime(), this.managedDestination.getSAFExportPolicy());
      this.managedDestination.setDestinationImpl(destinationImpl);

      try {
         this.managedDestination.setQuota(backEnd.getQuota());
      } catch (BeanUpdateFailedException var11) {
         throw new ModuleException(var11.getMessage(), var11);
      }

      try {
         backEnd.addDestination(this.managedDestination);
      } catch (JMSException var10) {
         throw new ModuleException("ERROR: Unable to add destination " + this.managedDestination.getName() + " to the back end " + backEnd.getName(), var10);
      }

      SAFImportedDestinationsBean safImportedDestinationsBean = this.idGroup.getBean();
      SAFRemoteContextBean safRemoteContextBean = safImportedDestinationsBean.getSAFRemoteContext();
      String url = safRemoteContextBean != null ? safRemoteContextBean.getSAFLoginContext().getLoginURL() : null;
      SAFService safService = SAFService.getSAFServiceWithModuleException(this.idGroup.getPartitionName());

      try {
         SAFRemoteEndpointRuntimeMBean r = new SAFRemoteEndpointRuntimeMBeanImpl(backEnd.getFullSAFDestinationName(this.name), url + "/" + this.remoteJNDIName, this.managedDestination, safService.getRuntimeMBean().getAgent(this.agentName).getJMSSAFAgentRuntime(), this.managedDestination.getErrorHandler());
         this.runtimeMBean = new SAFRemoteEndpointCustomizer(((SAFRemoteEndpointRuntimeMBeanImpl)r).getDecoratedName(), (RuntimeMBean)null, r);
         this.managedDestination.setSAFRuntimeMBean(r);
         this.safAgent.addRemoteEndpointRuntimeMBean(this.runtimeMBean);
      } catch (ManagementException var9) {
         throw new ModuleException(var9);
      }

      try {
         this.managedDestination.open();
      } catch (JMSException var8) {
         throw new ModuleException(var8);
      }
   }

   public void activate() throws ModuleException {
      try {
         this.managedDestination.setMessageLoggingEnabled(this.messageLoggingEnabled);
         this.managedDestination.setMessageLoggingFormat(this.messageLoggingFormat);
         this.managedDestination.start();
      } catch (JMSException var2) {
         throw new ModuleException("ERROR: Could not activate " + this.managedDestination.getName(), var2);
      }

      this.initializeRemoteContext();
   }

   private void initializeRemoteContext() {
      this.remoteContext = this.safAgent.findOrCreateRemoteContext(this.idGroup.getRemoteSAFContextFullyQualifiedName(), this.idGroup.getRemoteContextBean());
      this.remoteContext.addForwarder(this.managedDestination.getBackEnd().getPersistentStore(), this.managedDestination.getBackEnd().getAsyncPushWorkManager(), (RuntimeHandler)this.runtimeMBean.getDelegate(), (Queue)this.managedDestination.getKernelDestination(), this.remoteJNDIName, this.nonPersistentQos, this.persistentQos, this.idGroup.getExactlyOnceLoadBalancingPolicy());
   }

   private void deinitializeRemoteContext() {
      if (this.remoteContext != null) {
         this.remoteContext.removeForwarder((Queue)this.managedDestination.getKernelDestination(), this.remoteJNDIName);
      }

   }

   public void deactivate() throws ModuleException {
      this.deinitializeRemoteContext();
   }

   public void unprepare() throws ModuleException {
      this.safAgent.removeImportedDestination(this);
      BackEnd backEnd = this.safAgent.getBackEnd();
      backEnd.removeDestination(this.managedDestination);

      try {
         PrivilegedActionUtilities.unregister(this.runtimeMBean, kernelId);
      } catch (ManagementException var3) {
         throw new ModuleException(var3);
      }

      this.safAgent.removeRemoteEndpointRuntimeMBean(this.runtimeMBean);
      BEDestinationSecurityImpl.removeJMSResource(((BEDestinationSecurityImpl)this.managedDestination.getJMSDestinationSecurity()).getJMSResource(), this.destinationType.equals("queue"), true);
      this.runtimeMBean = null;
   }

   public void destroy() throws ModuleException {
   }

   public void remove() throws ModuleException {
      this.managedDestination.adminDeletion();
   }

   public BEDestinationImpl getManagedDestination() {
      return this.managedDestination;
   }

   public void setRemoteJNDIName(String remoteJNDIName) {
      this.remoteJNDIName = remoteJNDIName;
      this.remoteContextChanged();
   }

   public void setNonPersistentQos(String nonPersistentQos) {
      if ("At-Most-Once".equals(nonPersistentQos)) {
         this.nonPersistentQos = 1;
      } else if ("Exactly-Once".equals(nonPersistentQos)) {
         this.nonPersistentQos = 2;
      } else {
         if (!"At-Least-Once".equals(nonPersistentQos)) {
            throw new IllegalArgumentException(nonPersistentQos);
         }

         this.nonPersistentQos = 3;
      }

      if (this.managedDestination != null) {
         this.managedDestination.setNonPersistentQos(this.nonPersistentQos);
      }

      this.remoteContextChanged();
   }

   public void setPersistentQos(String persistentQos) {
      if ("At-Most-Once".equals(persistentQos)) {
         this.persistentQos = 1;
      } else if ("Exactly-Once".equals(persistentQos)) {
         this.persistentQos = 2;
      } else {
         if (!"At-Least-Once".equals(persistentQos)) {
            throw new IllegalArgumentException(persistentQos);
         }

         this.persistentQos = 3;
      }

      if (this.managedDestination != null) {
         this.managedDestination.setPersistentQos(this.persistentQos);
      }

      this.remoteContextChanged();
   }

   public synchronized void setSAFErrorHandling(SAFErrorHandlingBean safErrorHandling) {
      if (safErrorHandling == null) {
         this.managedDestination.setSAFErrorHandlingName((String)null);
      } else {
         this.managedDestination.setSAFErrorHandlingName(safErrorHandling.getName());
      }

   }

   public void setTimeToLiveDefault(long timeToLiveDefault) {
      this.timeToLiveDefault = timeToLiveDefault;
      this.applyTimeToLive();
   }

   public void setUseSAFTimeToLiveDefault(boolean useSafTimeToLiveDefault) {
      this.useSafTimeToLiveDefault = useSafTimeToLiveDefault;
      this.applyTimeToLive();
   }

   void setAgentTimeToLiveOverride(long agentTimeToLiveOverride) {
      this.agentTimeToLiveOverride = agentTimeToLiveOverride;
      this.applyTimeToLive();
   }

   private void applyTimeToLive() {
      if (this.useSafTimeToLiveDefault) {
         if (this.timeToLiveDefault == -1L) {
            this.managedDestination.setTimeToLiveDefault(this.agentTimeToLiveOverride);
         } else {
            this.managedDestination.setTimeToLiveDefault(this.timeToLiveDefault);
         }
      } else {
         this.managedDestination.setTimeToLiveDefault(-1L);
      }

   }

   public void remoteContextChanged() {
      if (this.remoteContext != null) {
         this.deinitializeRemoteContext();
         this.initializeRemoteContext();
      }

   }
}
