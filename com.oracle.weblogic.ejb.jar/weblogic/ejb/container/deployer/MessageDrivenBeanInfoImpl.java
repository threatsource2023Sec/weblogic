package weblogic.ejb.container.deployer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.enterprise.deploy.shared.ModuleType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.naming.MessageDestinationInfoRegistry;
import weblogic.application.naming.MessageDestinationReference;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.connector.external.EndpointActivationUtils;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.EJBCheckerFactory;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.Ejb30MessageDrivenBeanClassChecker;
import weblogic.ejb.container.compliance.mdb.MessageDrivenBeanCheckerFactory;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.dd.DDDefaults;
import weblogic.ejb.container.deployer.mbimpl.MethodInfoImpl;
import weblogic.ejb.container.interfaces.BaseEJBHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenManagerIntf;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.internal.ClientViewDescriptor;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.JMSConnectionPoller;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.manager.MessageDrivenManager;
import weblogic.ejb.container.timer.MDBTimerManagerFactory;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.j2ee.descriptor.ActivationConfigBean;
import weblogic.j2ee.descriptor.ActivationConfigPropertyBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean;
import weblogic.j2ee.descriptor.wl.SecurityPluginBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.CrossDomainSecurityUtil;
import weblogic.jms.extensions.DestinationAvailabilityListener;
import weblogic.jms.extensions.DestinationDetail;
import weblogic.jms.extensions.JMSDestinationAvailabilityHelper;
import weblogic.jms.extensions.RegistrationHandle;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.annotation.BeaSynthetic.Helper;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.CombinedIterator;
import weblogic.utils.reflect.MethodText;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

final class MessageDrivenBeanInfoImpl extends BeanInfoImpl implements MessageDrivenBeanInfo {
   private static final String JMS_INITIALCONTEXT_FATORY = "weblogic.jms.WLInitialContextFactory";
   private static final String MESSAGE_DRIVEN_METHOD_NAME = "onMessage";
   private static final Set acpSet;
   private static final AuthenticatedSubject KERNEL_ID;
   private final boolean isJmsBased;
   private final boolean implementsMessageListener;
   private final boolean exposesNoInterfaceClientView;
   private final boolean callByReference;
   private final boolean minimizeAQSessions;
   private final boolean receiveNoWaitAQ;
   private final boolean usesBeanManagedTx;
   private final boolean isDurableSubscriber;
   private final String messagingTypeInterfaceName;
   private final String generatedBeanClassName;
   private final String generatedBeanInterfaceName;
   private final String messageDrivenLocalObjectClassName;
   private final String messageDestinationLink;
   private final Class messagingTypeInterfaceClass;
   private final String currServerName;
   private final String domainName;
   private final ActivationConfigBean activationConfigBean;
   private final SecurityPluginBean securityPlugin;
   private final MessageDrivenBeanUpdateListener ul;
   private final MDBTimerManagerFactory timerManagerFactory;
   private boolean isDestinationQueue;
   private boolean isDestinationTopic;
   private boolean isInactive;
   private boolean generateUniqueJmsClientId;
   private boolean deleteDurableSubscription;
   private boolean use81StylePolling;
   private boolean hasCredentials;
   private boolean isRemoteSubjectExists;
   private volatile int jmsPollingIntervalSeconds;
   private int acknowledgeMode = 1;
   private int maxConcurrentInstances;
   private int initSuspendSeconds;
   private int maxSuspendSeconds;
   private int distributedDestinationConnection = -1;
   private int maxMessagesInTransaction;
   private int sessionMessagesMaximum = 0;
   private int topicMessagesDistributionMode = 0;
   private int topicMessagesPartitionMode = 0;
   private int mdbDestinationPollIntervalMillis = 0;
   private String destinationJNDIName;
   private String connectionFactoryResourceLink;
   private String destinationResourceLink;
   private String messageSelector;
   private String initialContextFactory;
   private String providerURL;
   private String connectionFactoryJNDIName;
   private final String jmsClientIdBase;
   private String jmsClientId;
   private String subscriptionName;
   private String resourceAdapterJndiName;
   private AuthenticatedSubject subject;
   private StringBuffer userName;
   private StringBuffer password;
   private WorkManager wm;
   private String adapterVersion;
   private EJBRuntimeHolder ejbComponentRuntime;
   private Class generatedBeanInterface;
   private Class generatedBeanClass;
   private Class messageDrivenLocalObjectClass;
   private MethodInfo onMessageMethodInfo;
   private MethodDescriptor onMessageMethodDescriptor;
   private final Map messagingTypeMethods = new HashMap();
   private final Map mdbMethodDescriptors = new HashMap();
   private final List mdManagerList = new ArrayList();
   private final List subscriptionDeleteList = new LinkedList();
   private final AtomicReference defaultMDManager = new AtomicReference();
   private final AtomicReference destAvailListener = new AtomicReference();
   private volatile boolean mgrStartSuspended = true;

   public MessageDrivenBeanInfoImpl(DeploymentInfo di, CompositeDescriptor cdesc, GenericClassLoader moduleCL) throws ClassNotFoundException, WLDeploymentException {
      super(di, cdesc, moduleCL);
      MessageDrivenBeanBean mb = (MessageDrivenBeanBean)cdesc.getBean();
      MessageDrivenDescriptorBean desc = cdesc.getWlBean().getMessageDrivenDescriptor();
      this.messagingTypeInterfaceName = cdesc.getMessagingTypeName() != null ? cdesc.getMessagingTypeName() : MessageListener.class.getName();
      this.usesBeanManagedTx = "bean".equalsIgnoreCase(mb.getTransactionType());
      if (mb.getMessageDestinationType() != null) {
         this.setDestination(mb.getMessageDestinationType());
      }

      this.activationConfigBean = mb.getActivationConfig();
      Map activationMap = new HashMap();
      if (this.activationConfigBean != null) {
         ActivationConfigPropertyBean[] var7 = this.activationConfigBean.getActivationConfigProperties();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            ActivationConfigPropertyBean acp = var7[var9];
            String acpName = acp.getActivationConfigPropertyName().toUpperCase(Locale.ENGLISH);
            activationMap.put(acpName, acp.getActivationConfigPropertyValue());
            if (!acpSet.contains(acpName)) {
               EJBLogger.logWarningNotFoundMDBActionConfigPropertyName(this.getDisplayName(), acp.getActivationConfigPropertyName());
            }
         }
      }

      this.ensureMetadataIsNotConflicting(cdesc, activationMap);
      if (debugLogger.isDebugEnabled()) {
         Iterator var14 = activationMap.entrySet().iterator();

         while(var14.hasNext()) {
            Map.Entry e = (Map.Entry)var14.next();
            debugLogger.debug("Activation Config: <" + (String)e.getKey() + " : " + (String)e.getValue() + ">");
         }
      }

      if (activationMap.get("RESOURCEADAPTERJNDINAME") != null && desc.getResourceAdapterJNDIName() == null) {
         this.resourceAdapterJndiName = (String)activationMap.get("RESOURCEADAPTERJNDINAME");
      } else {
         this.resourceAdapterJndiName = desc.getResourceAdapterJNDIName();
         if (desc.getResourceAdapterJNDIName() != null && activationMap.get("RESOURCEADAPTERJNDINAME") != null) {
            this.logACPOverridden("resourceAdapterJNDIName", this.resourceAdapterJndiName);
         }
      }

      this.messageSelector = (String)activationMap.get("MESSAGESELECTOR");
      if (this.messageSelector == null) {
         this.messageSelector = "";
      }

      if (activationMap.get("ACKNOWLEDGEMODE") != null) {
         this.acknowledgeMode = this.acknowledgeMode2Int((String)activationMap.get("ACKNOWLEDGEMODE"));
      }

      if (activationMap.get("DESTINATIONTYPE") != null) {
         this.setDestination((String)activationMap.get("DESTINATIONTYPE"));
      }

      this.isDurableSubscriber = "durable".equalsIgnoreCase((String)activationMap.get("SUBSCRIPTIONDURABILITY"));
      if (activationMap.get("INACTIVE") != null) {
         this.isInactive = Boolean.parseBoolean((String)activationMap.get("INACTIVE"));
         if (this.isInactive) {
            EJBLogger.logMDBInactive(this.getEJBName());
         } else {
            EJBLogger.logMDBActive(this.getEJBName());
         }
      }

      this.messageDestinationLink = mb.getMessageDestinationLink();
      this.maxConcurrentInstances = this.getCachingDescriptor().getMaxBeansInFreePool();
      if (activationMap.get("DESTINATIONJNDINAME") != null && cdesc.getDestinationJNDIName() == null) {
         this.destinationJNDIName = (String)activationMap.get("DESTINATIONJNDINAME");
      } else {
         this.destinationJNDIName = cdesc.getDestinationJNDIName();
         if (cdesc.getDestinationJNDIName() != null && activationMap.get("DESTINATIONJNDINAME") != null) {
            this.logACPOverridden("destinationJNDIName", this.destinationJNDIName);
         } else if (cdesc.getDestinationJNDIName() == null) {
            this.destinationJNDIName = (String)activationMap.get("DESTINATIONLOOKUP");
         }
      }

      if (activationMap.get("INITIALCONTEXTFACTORY") != null && !isSet("InitialContextFactory", desc)) {
         this.initialContextFactory = (String)activationMap.get("INITIALCONTEXTFACTORY");
      } else {
         this.initialContextFactory = desc.getInitialContextFactory();
         if (isSet("InitialContextFactory", desc) && activationMap.get("INITIALCONTEXTFACTORY") != null) {
            this.logACPOverridden("initialContextFactory", this.initialContextFactory);
         }
      }

      if (activationMap.get("PROVIDERURL") != null && desc.getProviderUrl() == null) {
         this.providerURL = (String)activationMap.get("PROVIDERURL");
      } else {
         this.providerURL = desc.getProviderUrl();
         if (desc.getProviderUrl() != null && activationMap.get("PROVIDERURL") != null) {
            this.logACPOverridden("providerUrl", this.providerURL);
         }
      }

      if (activationMap.get("CONNECTIONFACTORYJNDINAME") != null && !isSet("ConnectionFactoryJNDIName", desc)) {
         this.connectionFactoryJNDIName = (String)activationMap.get("CONNECTIONFACTORYJNDINAME");
      } else {
         this.connectionFactoryJNDIName = desc.getConnectionFactoryJNDIName();
         if (isSet("ConnectionFactoryJNDIName", desc) && activationMap.get("CONNECTIONFACTORYJNDINAME") != null) {
            this.logACPOverridden("connectionFactoryJNDIName", this.connectionFactoryJNDIName);
         } else if (!isSet("ConnectionFactoryJNDIName", desc) && activationMap.get("CONNECTIONFACTORYLOOKUP") != null) {
            this.connectionFactoryJNDIName = (String)activationMap.get("CONNECTIONFACTORYLOOKUP");
         }
      }

      if (activationMap.get("CONNECTIONFACTORYRESOURCELINK") != null && desc.getConnectionFactoryResourceLink() == null) {
         this.connectionFactoryResourceLink = (String)activationMap.get("CONNECTIONFACTORYRESOURCELINK");
      } else {
         this.connectionFactoryResourceLink = desc.getConnectionFactoryResourceLink();
         if (desc.getConnectionFactoryResourceLink() != null && activationMap.get("CONNECTIONFACTORYRESOURCELINK") != null) {
            this.logACPOverridden("connectionFactoryResourceLink", this.connectionFactoryResourceLink);
         }
      }

      if (activationMap.get("DESTINATIONRESOURCELINK") != null && desc.getDestinationResourceLink() == null) {
         this.destinationResourceLink = (String)activationMap.get("DESTINATIONRESOURCELINK");
      } else {
         this.destinationResourceLink = desc.getDestinationResourceLink();
         if (desc.getDestinationResourceLink() != null && activationMap.get("DESTINATIONRESOURCELINK") != null) {
            this.logACPOverridden("destinationResourceLink", this.destinationResourceLink);
         }
      }

      if (activationMap.get("JMSPOLLINGINTERVALSECONDS") != null && !isSet("JmsPollingIntervalSeconds", desc)) {
         this.jmsPollingIntervalSeconds = retrieveNumericACProp(activationMap, "JMSPOLLINGINTERVALSECONDS");
         if (this.jmsPollingIntervalSeconds <= 0) {
            this.jmsPollingIntervalSeconds = desc.getJmsPollingIntervalSeconds();
         }
      } else {
         this.jmsPollingIntervalSeconds = desc.getJmsPollingIntervalSeconds();
         if (isSet("JmsPollingIntervalSeconds", desc) && activationMap.get("JMSPOLLINGINTERVALSECONDS") != null) {
            this.logACPOverridden("jmsPollingIntervalSeconds", Integer.toString(this.jmsPollingIntervalSeconds));
         }
      }

      if (activationMap.get("INITSUSPENDSECONDS") != null && !isSet("InitSuspendSeconds", desc)) {
         this.initSuspendSeconds = retrieveNumericACProp(activationMap, "INITSUSPENDSECONDS");
      } else {
         this.initSuspendSeconds = desc.getInitSuspendSeconds();
         if (isSet("InitSuspendSeconds", desc) && activationMap.get("INITSUSPENDSECONDS") != null) {
            this.logACPOverridden("initSuspendSeconds", Integer.toString(this.initSuspendSeconds));
         }
      }

      if (activationMap.get("MAXSUSPENDSECONDS") != null && !isSet("MaxSuspendSeconds", desc)) {
         this.maxSuspendSeconds = retrieveNumericACProp(activationMap, "MAXSUSPENDSECONDS");
      } else {
         this.maxSuspendSeconds = desc.getMaxSuspendSeconds();
         if (isSet("MaxSuspendSeconds", desc) && activationMap.get("MAXSUSPENDSECONDS") != null) {
            this.logACPOverridden("maxSuspendSeconds", Integer.toString(this.maxSuspendSeconds));
         }
      }

      if (activationMap.get("MAXMESSAGESINTRANSACTION") != null && !isSet("MaxMessagesInTransaction", desc)) {
         this.maxMessagesInTransaction = retrieveNumericACProp(activationMap, "MAXMESSAGESINTRANSACTION");
      } else {
         this.maxMessagesInTransaction = desc.getMaxMessagesInTransaction();
         if (isSet("MaxMessagesInTransaction", desc) && activationMap.get("MAXMESSAGESINTRANSACTION") != null) {
            this.logACPOverridden("maxMessagesInTransaction", Integer.toString(this.maxMessagesInTransaction));
         }
      }

      this.securityPlugin = desc.isSecurityPluginSet() ? desc.getSecurityPlugin() : null;
      if (isServer()) {
         RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNEL_ID);
         this.currServerName = ra.getServer().getName();
         this.domainName = ra.getDomain().getName();
      } else {
         this.currServerName = "";
         this.domainName = "";
      }

      if (activationMap.get("JMSCLIENTID") != null && desc.getJmsClientId() == null) {
         this.jmsClientIdBase = (String)activationMap.get("JMSCLIENTID");
      } else if (desc.getJmsClientId() != null) {
         this.jmsClientIdBase = desc.getJmsClientId();
         if (activationMap.get("JMSCLIENTID") != null) {
            this.logACPOverridden("jmsClientId", this.jmsClientIdBase);
         }
      } else if (activationMap.get("CLIENTID") != null) {
         this.jmsClientIdBase = (String)activationMap.get("CLIENTID");
      } else {
         this.jmsClientIdBase = this.getEJBName();
      }

      this.jmsClientId = this.jmsClientIdBase;
      this.generateUniqueJmsClientId = desc.isGenerateUniqueJmsClientId();
      if (activationMap.get("DURABLESUBSCRIPTIONDELETION") != null && !isSet("DurableSubscriptionDeletion", desc)) {
         this.deleteDurableSubscription = Boolean.parseBoolean((String)activationMap.get("DURABLESUBSCRIPTIONDELETION"));
      } else {
         this.deleteDurableSubscription = desc.isDurableSubscriptionDeletion();
         if (isSet("DurableSubscriptionDeletion", desc) && activationMap.get("DURABLESUBSCRIPTIONDELETION") != null) {
            this.logACPOverridden("durableSubscriptionDeletion", Boolean.toString(this.deleteDurableSubscription));
         }
      }

      if (activationMap.get("USE81STYLEPOLLING") != null && !isSet("Use81StylePolling", desc)) {
         this.use81StylePolling = Boolean.parseBoolean((String)activationMap.get("USE81STYLEPOLLING"));
      } else {
         this.use81StylePolling = desc.isUse81StylePolling();
         if (isSet("Use81StylePolling", desc) && activationMap.get("USE81STYLEPOLLING") != null) {
            this.logACPOverridden("use81StylePolling", Boolean.toString(this.use81StylePolling));
         }
      }

      if (activationMap.get("MINIMIZEAQSESSIONS") != null) {
         this.minimizeAQSessions = Boolean.parseBoolean((String)activationMap.get("MINIMIZEAQSESSIONS"));
      } else {
         this.minimizeAQSessions = Boolean.getBoolean("weblogic.mdb.message.MinimizeAQSessions");
      }

      if (activationMap.get("AQMDBRECEIVENOWAIT") != null) {
         this.receiveNoWaitAQ = Boolean.parseBoolean((String)activationMap.get("AQMDBRECEIVENOWAIT"));
      } else {
         this.receiveNoWaitAQ = Boolean.getBoolean("weblogic.ejb.container.AQMDBReceiveNoWait");
      }

      if (activationMap.get("MDBDESTINATIONPOLLINTERVALMILLIS") != null) {
         this.mdbDestinationPollIntervalMillis = retrieveNumericACProp(activationMap, "MDBDESTINATIONPOLLINTERVALMILLIS");
      } else {
         this.mdbDestinationPollIntervalMillis = Integer.getInteger("weblogic.ejb.container.MDBDestinationPollIntervalMillis", 0);
      }

      if (this.mdbDestinationPollIntervalMillis <= 0) {
         this.mdbDestinationPollIntervalMillis = 0;
      }

      int ret = false;
      int ret;
      Loggable l;
      if (activationMap.get("DISTRIBUTEDDESTINATIONCONNECTION") != null && !isSet("DistributedDestinationConnection", desc)) {
         ret = this.convertToInt(DDConstants.DEST_CONN_MODES, (String)activationMap.get("DISTRIBUTEDDESTINATIONCONNECTION"));
         if (ret < 0) {
            l = EJBLogger.logIllegalDistributedDestinationConnectionValueLoggable(this.getDisplayName(), (String)activationMap.get("DISTRIBUTEDDESTINATIONCONNECTION"));
            throw new WLDeploymentException(l.getMessage());
         }

         this.distributedDestinationConnection = ret;
      } else {
         this.distributedDestinationConnection = this.convertToInt(DDConstants.DEST_CONN_MODES, desc.getDistributedDestinationConnection());
         if (isSet("DistributedDestinationConnection", desc) && activationMap.get("DISTRIBUTEDDESTINATIONCONNECTION") != null) {
            this.logACPOverridden("distributedDestinationConnection", desc.getDistributedDestinationConnection());
         }
      }

      if (activationMap.get("TOPICMESSAGESDISTRIBUTIONMODE") != null) {
         ret = this.convertToInt(DDConstants.TOPIC_MESSAGE_DISTRIBUTION_MODES, (String)activationMap.get("TOPICMESSAGESDISTRIBUTIONMODE"));
         if (ret < 0) {
            l = EJBLogger.logIllegalTopicMessagesDistributionModeValueLoggable(this.getDisplayName(), (String)activationMap.get("TOPICMESSAGESDISTRIBUTIONMODE"));
            throw new WLDeploymentException(l.getMessage());
         }

         this.topicMessagesDistributionMode = ret;
      }

      Loggable l;
      if (activationMap.get("MESSAGESMAXIMUM") != null) {
         try {
            this.sessionMessagesMaximum = Integer.parseInt((String)activationMap.get("MESSAGESMAXIMUM"));
            if (this.sessionMessagesMaximum == 0) {
               l = EJBLogger.logInvalidMaximumNumberOfMessagesSpecifiedLoggable(this.getDisplayName(), (String)activationMap.get("MESSAGESMAXIMUM"));
               throw new WLDeploymentException(l.getMessage());
            }

            if (this.sessionMessagesMaximum < 0) {
               this.sessionMessagesMaximum = -1;
            }
         } catch (NumberFormatException var13) {
            l = EJBLogger.logInvalidMaximumNumberOfMessagesSpecifiedLoggable(this.getDisplayName(), (String)activationMap.get("MESSAGESMAXIMUM"));
            throw new WLDeploymentException(l.getMessage(), var13);
         }
      }

      if (activationMap.get("SUBSCRIPTIONNAME") != null) {
         this.subscriptionName = (String)activationMap.get("SUBSCRIPTIONNAME");
      }

      if (activationMap.get("TOPICMESSAGEPARTITIONMODE") != null) {
         ret = this.convertToInt(DDConstants.ACP_KEY_TOPIC_MESSAGE_PARTITION_MODES, (String)activationMap.get("TOPICMESSAGEPARTITIONMODE"));
         if (ret < 0) {
            l = EJBLogger.logIllegalTopicMessagePartitionModeValueLoggable(this.getDisplayName(), (String)activationMap.get("TOPICMESSAGEPARTITIONMODE"));
            throw new WLDeploymentException(l.getMessage());
         }

         this.topicMessagesPartitionMode = ret;
      }

      this.callByReference = cdesc.useCallByReference();
      NamingConvention nc = new NamingConvention(this.getBeanClassName(), this.getEJBName());
      this.isJmsBased = MessageListener.class.getName().equals(this.messagingTypeInterfaceName) && this.resourceAdapterJndiName == null && (this.destinationResourceLink != null || this.messageDestinationLink != null || this.destinationJNDIName != null);
      this.messageDrivenLocalObjectClassName = this.isJmsBased ? null : nc.getMdLocalObjectClassName();
      this.messagingTypeInterfaceClass = this.loadClass(this.messagingTypeInterfaceName);
      this.exposesNoInterfaceClientView = !this.isJmsBased && this.messagingTypeInterfaceClass.getMethods().length == 0;
      this.initializeMethodInfos();
      this.implementsMessageListener = !this.isEJB30() || this.getMessagingTypeInterfaceClass().isAssignableFrom(this.getBeanClass());
      if (!this.exposesNoInterfaceClientView && this.implementsMessageListener) {
         this.generatedBeanClassName = null;
      } else {
         this.generatedBeanClassName = nc.getGeneratedBeanClassName();
      }

      if (!this.exposesNoInterfaceClientView) {
         this.generatedBeanInterfaceName = null;
      } else {
         this.generatedBeanInterfaceName = nc.getGeneratedBeanInterfaceName();
      }

      this.timerManagerFactory = this.isTimerDriven() ? new MDBTimerManagerFactory() : null;
      this.ul = new MessageDrivenBeanUpdateListener(this);
      if (!this.isJmsBased) {
         if (this.exposesNoInterfaceClientView) {
            if (debugLogger.isDebugEnabled() && (this.resourceAdapterJndiName == null || this.resourceAdapterJndiName.trim().isEmpty())) {
               debug("ResourceAdapter JNDI not specified for " + this.getEJBName() + " container will try to auto-wire");
            }
         } else if (this.resourceAdapterJndiName == null || this.resourceAdapterJndiName.trim().isEmpty()) {
            l = EJBLogger.logNoMdbDestinationConfiguredLoggable(this.getDisplayName());
            throw new WLDeploymentException(l.getMessageText());
         }
      }

      try {
         Ejb30MessageDrivenBeanClassChecker.ensureBeanClassMethodsExist(this.getEJBName(), this.messagingTypeInterfaceClass, this.getBeanClass());
      } catch (ComplianceException var12) {
         throw new WLDeploymentException(var12.getMessage(), var12);
      }
   }

   private void logACPOverridden(String propName, String propVal) {
      EJBLogger.logOverridenActivationConfigProperty(this.getDisplayName(), propName, propVal);
   }

   private void ensureMetadataIsNotConflicting(CompositeDescriptor cdesc, Map acpMap) throws WLDeploymentException {
      MessageDrivenDescriptorBean desc = cdesc.getWlBean().getMessageDrivenDescriptor();
      boolean linkConfigured = acpMap.containsKey("CONNECTIONFACTORYRESOURCELINK") || desc.getConnectionFactoryResourceLink() != null || acpMap.containsKey("DESTINATIONRESOURCELINK") || desc.getDestinationResourceLink() != null;
      boolean mutexWithLink = acpMap.containsKey("DESTINATIONJNDINAME") || cdesc.getDestinationJNDIName() != null || acpMap.containsKey("INITIALCONTEXTFACTORY") || isSet("InitialContextFactory", desc) || acpMap.containsKey("PROVIDERURL") || desc.getProviderUrl() != null || acpMap.containsKey("CONNECTIONFACTORYJNDINAME") || isSet("ConnectionFactoryJNDIName", desc);
      if (linkConfigured && mutexWithLink) {
         throw new WLDeploymentException(EJBLogger.logConflictingResourceLinkConfigurationLoggable(this.getDisplayName()).getMessage());
      } else {
         this.checkIfMetadataConflicting(acpMap, "DESTINATIONLOOKUP", "DESTINATIONJNDINAME", "Destination lookup name");
         this.checkIfMetadataConflicting(acpMap, "CONNECTIONFACTORYLOOKUP", "CONNECTIONFACTORYJNDINAME", "Connection Factory lookup name");
         this.checkIfMetadataConflicting(acpMap, "CLIENTID", "JMSCLIENTID", "JMS client id");
         if (acpMap.containsKey("INITIALCONTEXTFACTORY") && "weblogic.jms.WLInitialContextFactory".equals(acpMap.get("INITIALCONTEXTFACTORY"))) {
            throw new WLDeploymentException(EJBLogger.logInvalidOBSInitialContextFactoryLoggable(this.getDisplayName(), "weblogic.jms.WLInitialContextFactory").getMessage());
         }
      }
   }

   private void checkIfMetadataConflicting(Map acpMap, String stdKey, String wlsKey, String settingFor) throws WLDeploymentException {
      if (acpMap.containsKey(wlsKey) && acpMap.containsKey(stdKey)) {
         if (!((String)acpMap.get(wlsKey)).equals(acpMap.get(stdKey))) {
            throw new WLDeploymentException(EJBLogger.logConflictingActivationConfigPropertiesLoggable(this.getDisplayName(), stdKey.toLowerCase(), wlsKey.toLowerCase(), settingFor).getMessage());
         }

         if (debugLogger.isDebugEnabled()) {
            EJBLogger.logPreferStandardPropertyOverWLSProperty(this.getDisplayName(), stdKey.toLowerCase(), wlsKey.toLowerCase());
         }
      }

   }

   private static int retrieveNumericACProp(Map activationMap, String name) throws WLDeploymentException {
      try {
         return Integer.parseInt((String)activationMap.get(name));
      } catch (NumberFormatException var3) {
         throw new WLDeploymentException(EJBLogger.logNonNumericValueForACPExceptionLoggable(name).getMessage());
      }
   }

   private void detectDestinationByJNDIName(final String jndiName) {
      if (!this.isDestinationQueue() && !this.isDestinationTopic()) {
         try {
            final Context ctx = this.getInitialContext();
            Object destination = this.doPrivilegedAction(this.getSubject(), new PrivilegedExceptionAction() {
               public Object run() throws NamingException {
                  return ctx.lookup(jndiName);
               }
            });
            if (destination instanceof Queue) {
               this.setDestination("javax.jms.Queue");
            } else if (destination instanceof Topic) {
               this.setDestination("javax.jms.Topic");
            } else if (debugLogger.isDebugEnabled()) {
               debug("Unknown message destination type " + destination);
            }
         } catch (Exception var4) {
            if (debugLogger.isDebugEnabled()) {
               debug("No message destination found to associate with MessageDrivenBean " + this.getEJBName() + " : " + StackTraceUtilsClient.throwable2StackTrace(var4));
            }
         }

      }
   }

   public void init() {
   }

   public boolean implementsMessageListener() {
      return this.implementsMessageListener;
   }

   public boolean exposesNoInterfaceClientView() {
      return this.exposesNoInterfaceClientView;
   }

   public Class getGeneratedBeanInterface() {
      if (this.generatedBeanInterface == null) {
         this.generatedBeanInterface = this.loadForSure(this.generatedBeanInterfaceName);
      }

      return this.generatedBeanInterface;
   }

   public String getBeanClassNameToInstantiate() {
      return this.implementsMessageListener && !this.exposesNoInterfaceClientView ? this.getBeanClassName() : this.generatedBeanClassName;
   }

   public Class getGeneratedBeanClass() {
      if (this.generatedBeanClass == null) {
         this.generatedBeanClass = this.loadForSure(this.generatedBeanClassName);
      }

      return this.generatedBeanClass;
   }

   public Class getBeanClassToInstantiate() {
      return this.implementsMessageListener && !this.exposesNoInterfaceClientView ? this.getBeanClass() : this.getGeneratedBeanClass();
   }

   private void setDestination(String dt) {
      if (!this.isDestinationQueue() && !this.isDestinationTopic()) {
         if ("javax.jms.Queue".equals(dt)) {
            this.isDestinationQueue = true;
         } else if ("javax.jms.Topic".equals(dt)) {
            this.isDestinationTopic = true;
         } else if (this.getIsJMSBased()) {
            throw new AssertionError("Unknown destination type : " + dt);
         }

      }
   }

   private int acknowledgeMode2Int(String ackString) throws WLDeploymentException {
      int result = 1;
      if ("dups-ok-acknowledge".equalsIgnoreCase(ackString)) {
         result = 3;
      } else if (!"auto-acknowledge".equalsIgnoreCase(ackString)) {
         if ("no_acknowledge".equalsIgnoreCase(ackString)) {
            throw new WLDeploymentException(ackString + " is no longer a valid acknowledgement mode.");
         }

         if ("multicast_no_acknowledge".equals(ackString)) {
            throw new WLDeploymentException(ackString + " is no longer a valid acknowledgement mode.");
         }

         if (null != ackString && this.getIsJMSBased()) {
            throw new AssertionError("Unknown ACKNOWLEDGE MODE : " + ackString);
         }
      }

      return result;
   }

   public Context getInitialContext() throws NamingException {
      AuthenticatedSubject callerId = null;

      try {
         callerId = this.getRunAsSubject();
      } catch (PrincipalNotFoundException var3) {
         EJBLogger.logStackTrace(var3);
      }

      if (this.userName == null || this.password == null || callerId != null && (this.userName.length() == 0 || this.password.length() == 0)) {
         this.reSetUsernameAndPassword();
      }

      if (this.hasCredentials) {
         return this.getInitialContext(this.userName.toString(), this.password.toString());
      } else {
         if (callerId == null) {
            callerId = this.getCurrentSubject();
         }

         return (Context)this.doPrivilegedAction(callerId, new PrivilegedExceptionAction() {
            public Context run() throws Exception {
               Hashtable env = new Hashtable();
               env.put("java.naming.factory.initial", MessageDrivenBeanInfoImpl.this.initialContextFactory);
               env.put("weblogic.jndi.allowGlobalResourceLookup", "true");
               if (null != MessageDrivenBeanInfoImpl.this.providerURL) {
                  env.put("java.naming.provider.url", MessageDrivenBeanInfoImpl.this.providerURL);
               }

               Context ctx = new InitialContext(env);
               MessageDrivenBeanInfoImpl.this.cacheCurrentSubject();
               return ctx;
            }
         });
      }
   }

   public void reSetUsernameAndPassword() {
      this.userName = new StringBuffer();
      this.password = new StringBuffer();
      this.hasCredentials = false;

      try {
         this.hasCredentials = JMSConnectionPoller.getCredentials(this, this.userName, this.password);
      } catch (Exception var2) {
         EJBLogger.logStackTrace(var2);
      }

   }

   public Context getInitialContext(final String userName, final String password) throws NamingException {
      return (Context)this.doPrivilegedAction(this.getCurrentSubject(), new PrivilegedExceptionAction() {
         public Context run() throws Exception {
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial", MessageDrivenBeanInfoImpl.this.initialContextFactory);
            env.put("weblogic.jndi.allowGlobalResourceLookup", "true");
            if (null != MessageDrivenBeanInfoImpl.this.providerURL) {
               env.put("java.naming.provider.url", MessageDrivenBeanInfoImpl.this.providerURL);
            }

            env.put("java.naming.security.principal", userName);
            env.put("java.naming.security.credentials", password);
            Context ctx = new InitialContext(env);
            MessageDrivenBeanInfoImpl.this.cacheCurrentSubject();
            return ctx;
         }
      });
   }

   public boolean isDeliveryTransacted(Method m) throws NoSuchMethodException {
      if (this.exposesNoInterfaceClientView()) {
         this.getBeanClass().getMethod(m.getName(), m.getParameterTypes());
      } else {
         this.getMessagingTypeInterfaceClass().getMethod(m.getName(), m.getParameterTypes());
      }

      return !this.usesBeanManagedTx() && 1 == ((MethodInfo)this.messagingTypeMethods.get(getMethodSignature(m))).getTransactionAttribute();
   }

   void setInitialContextFactory(String val) {
      this.initialContextFactory = val;
   }

   void setProviderURL(String val) {
      this.providerURL = val;
   }

   void setConnectionFactoryJNDIName(String val) {
      this.connectionFactoryJNDIName = val;
   }

   void setConnectionFactoryResourceLink(String val) {
      this.connectionFactoryResourceLink = val;
   }

   void setJmsClientId(String val) {
      this.jmsClientId = val;
   }

   void setDestinationResourceLink(String val) {
      this.destinationResourceLink = val;
   }

   void setInitSuspendSeconds(int val) {
      this.initSuspendSeconds = val;
   }

   void setMaxSuspendSeconds(int val) {
      this.maxSuspendSeconds = val;
   }

   void setDistributedDestinationConnection(String val) {
      this.setDistributedDestinationConnection(this.convertToInt(DDConstants.DEST_CONN_MODES, val));
   }

   void setDistributedDestinationConnection(int val) {
      this.distributedDestinationConnection = val;
   }

   void setGenerateUniqueJmsClientId(boolean val) {
      this.generateUniqueJmsClientId = val;
   }

   void setDurableSubscriptionDeletion(boolean val) {
      this.deleteDurableSubscription = val;
   }

   void setMaxMessagesInTransaction(int val) {
      this.maxMessagesInTransaction = val;
   }

   void setUse81StylePolling(boolean val) {
      this.use81StylePolling = val;
   }

   void setResourceAdapterJndiName(String val) {
      this.resourceAdapterJndiName = val;
   }

   void setDestinationJNDIName(String val) {
      this.destinationJNDIName = val;
   }

   void setTopicMessagesDistributionMode(String val) {
      this.topicMessagesDistributionMode = this.convertToInt(DDConstants.TOPIC_MESSAGE_DISTRIBUTION_MODES, val);
   }

   public boolean usesBeanManagedTx() {
      return this.usesBeanManagedTx;
   }

   public String getMessageSelector() {
      return this.messageSelector;
   }

   public int getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   public boolean isDestinationQueue() {
      return this.isDestinationQueue;
   }

   public boolean isDestinationTopic() {
      return this.isDestinationTopic;
   }

   public boolean isDurableSubscriber() {
      return this.isDurableSubscriber;
   }

   public int getMaxConcurrentInstances() {
      return this.maxConcurrentInstances;
   }

   public String getProviderURL() {
      return this.providerURL;
   }

   public String getDestinationName() {
      return this.destinationJNDIName;
   }

   public String getConnectionFactoryJNDIName() {
      return this.connectionFactoryJNDIName;
   }

   public int getJMSPollingIntervalSeconds() {
      return this.jmsPollingIntervalSeconds;
   }

   public int getInitSuspendSeconds() {
      return this.initSuspendSeconds;
   }

   public int getMaxSuspendSeconds() {
      return this.maxSuspendSeconds;
   }

   public SecurityPluginBean getSecurityPlugin() {
      return this.securityPlugin;
   }

   public String computeUniqueGlobalId(String ddMemberName, TargetMBean tm) {
      String qualifier = this.currServerName;
      if (tm == null) {
         if (isServer()) {
            ServerMBean serv = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
            if (serv != null) {
               ClusterMBean cls = serv.getCluster();
               if (cls != null && serv.getJTAMigratableTarget() != null) {
                  qualifier = cls.getName();
               }
            }
         }
      } else {
         qualifier = tm.getName();
      }

      String uniqueGlobalId = this.domainName + "_" + qualifier + "_" + this.getIsIdenticalKey();
      if (this.getDistributedDestinationConnection() == 1) {
         uniqueGlobalId = uniqueGlobalId + "_" + ddMemberName;
      }

      return uniqueGlobalId;
   }

   public String computeJmsClientId(String ddMemberName, TargetMBean tm) {
      switch (this.getTopicMessagesDistributionMode()) {
         case 0:
            if (!this.generateUniqueJmsClientId) {
               return this.jmsClientId;
            }

            String uniqueGlobalId = this.domainName + "_" + (tm == null ? this.currServerName : tm.getName()) + "_" + this.getIsIdenticalKey();
            if (this.getDistributedDestinationConnection() == 1) {
               uniqueGlobalId = uniqueGlobalId + "_" + ddMemberName;
            }

            return this.jmsClientIdBase + "_" + uniqueGlobalId;
         case 1:
            return this.jmsClientIdBase + "_" + this.domainName + "_" + this.currServerName;
         case 2:
            return this.jmsClientIdBase;
         default:
            throw new IllegalArgumentException("Unexpected message distribution mode");
      }
   }

   public String computeSubscriptionName(String jmsClientId) {
      if (this.subscriptionName != null && this.subscriptionName.length() > 0) {
         return this.subscriptionName;
      } else {
         return this.getTopicMessagesDistributionMode() > 0 ? this.getEJBName() : jmsClientId;
      }
   }

   public boolean getDeleteDurableSubscription() {
      return this.deleteDurableSubscription;
   }

   public int getMaxMessagesInTransaction() {
      return this.maxMessagesInTransaction;
   }

   public boolean getUse81StylePolling() {
      return this.use81StylePolling;
   }

   public boolean getMinimizeAQSessions() {
      return this.minimizeAQSessions;
   }

   public boolean isReceiveNoWaitAQ() {
      return this.receiveNoWaitAQ;
   }

   public int getMdbDestinationPollIntervalMillis() {
      return this.mdbDestinationPollIntervalMillis;
   }

   public String getResourceAdapterJndiName() {
      return this.resourceAdapterJndiName;
   }

   public boolean useCallByReference() {
      return this.callByReference;
   }

   public boolean getIsJMSBased() {
      return this.isJmsBased;
   }

   public ActivationConfigBean getActivationConfigBean() {
      return this.activationConfigBean;
   }

   public boolean getIsInactive() {
      return this.isInactive;
   }

   public WorkManager getCustomWorkManager() {
      return this.wm;
   }

   public void setResourceAdapterVersion(String value) {
      this.adapterVersion = value;
   }

   public String getResourceAdapterVersion() {
      return this.adapterVersion;
   }

   public MDBTimerManagerFactory getTimerManagerFactory() {
      return this.timerManagerFactory;
   }

   public int getSessionMessagesMaximum() {
      return this.sessionMessagesMaximum;
   }

   public boolean isTopicSubscriptionSharingInPartitions() {
      return 1 == this.topicMessagesPartitionMode;
   }

   public MessageDrivenManagerIntf getBeanManagerInstance(EJBRuntimeHolder runtime) {
      return new MessageDrivenManager(runtime);
   }

   public MessageDrivenManagerIntf getBeanManager() {
      return (MessageDrivenManagerIntf)super.getBeanManager();
   }

   private List getManagersListCopy() {
      return new ArrayList(this.mdManagerList);
   }

   public void removeManager(BeanManager mgr) {
      this.mdManagerList.remove(mgr);
   }

   private synchronized void cacheCurrentSubject() {
      this.subject = this.getCurrentSubject();
   }

   public synchronized AuthenticatedSubject getSubject() {
      return this.subject;
   }

   public boolean getIsRemoteSubjectExists() {
      if (!this.isRemoteSubjectExists) {
         this.isRemoteSubjectExists = CrossDomainSecurityManager.getCrossDomainSecurityUtil().ifRemoteSubjectExists(this.providerURL);
      }

      return this.isRemoteSubjectExists;
   }

   public boolean isOnMessageTransacted() {
      return 1 == this.onMessageMethodInfo.getTransactionAttribute();
   }

   public Integer getOnMessageTxIsolationLevel() {
      int isolationLevel = this.onMessageMethodInfo.getTxIsolationLevel();
      return isolationLevel == -1 ? null : isolationLevel;
   }

   public MethodInfo getOnMessageMethodInfo() {
      return this.onMessageMethodInfo;
   }

   public MethodDescriptor getOnMessageMethodDescriptor() {
      return this.onMessageMethodDescriptor;
   }

   public void assignDefaultTXAttributesIfNecessary(int jtaConfigTimeout) {
      super.setupTxTimeout(jtaConfigTimeout);
      Set methodInfos = new HashSet();
      if (this.getIsJMSBased()) {
         methodInfos.add(this.onMessageMethodInfo);
      } else {
         methodInfos.addAll(this.messagingTypeMethods.values());
      }

      if (this.isTimerDriven()) {
         methodInfos.addAll(this.getAllTimerMethodInfos());
      }

      if (this.usesBeanManagedTx()) {
         Iterator var3 = methodInfos.iterator();

         while(var3.hasNext()) {
            MethodInfo mi = (MethodInfo)var3.next();
            mi.setTransactionAttribute((short)0);
         }
      } else {
         StringBuilder sb = new StringBuilder();
         short txAttr = DDDefaults.getBeanMethodTransactionAttribute(this.isEJB30());
         sb.append(this.assignDefaultTXAttributesIfNecessary("beanClass", methodInfos, txAttr));
         if (sb.length() > 0 && !this.isEJB30()) {
            EJBLogger.logEJBUsesDefaultTXAttribute(this.getDisplayName(), "NotSupported", sb.toString());
         }
      }

   }

   protected short getTxAttribute(MethodInfo mi, Class c) {
      return this.usesBeanManagedTx() ? 0 : mi.getTransactionAttribute();
   }

   public Iterator getAllMethodInfosIterator() {
      List iterators = new ArrayList();
      if (!this.getIsJMSBased()) {
         iterators.add(this.messagingTypeMethods.values().iterator());
      } else {
         iterators.add(Collections.singletonList(this.onMessageMethodInfo).iterator());
      }

      if (this.isTimerDriven()) {
         iterators.add(this.getAllTimerMethodInfos().iterator());
      }

      return new CombinedIterator(iterators);
   }

   public MethodDescriptor getMDBMethodDescriptor(Method m) {
      return (MethodDescriptor)this.mdbMethodDescriptors.get(m.toString());
   }

   public Class getMessageDrivenLocalObjectClass() {
      if (this.messageDrivenLocalObjectClass == null) {
         this.messageDrivenLocalObjectClass = this.loadForSure(this.messageDrivenLocalObjectClassName);
      }

      return this.messageDrivenLocalObjectClass;
   }

   public String getMessagingTypeInterfaceName() {
      return this.messagingTypeInterfaceName;
   }

   public Class getMessagingTypeInterfaceClass() {
      return this.messagingTypeInterfaceClass;
   }

   public Collection getAllMessagingTypeMethodInfos() {
      return this.messagingTypeMethods.values();
   }

   private void initializeMethodInfos() throws WLDeploymentException {
      if (this.getIsJMSBased()) {
         try {
            Method m = this.getBeanClass().getMethod("onMessage", Message.class);
            this.onMessageMethodInfo = MethodInfoImpl.createMethodInfoImpl(m, (String)null);
         } catch (NoSuchMethodException var7) {
            throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().BEAN_CLASS_IMPLEMENTS_MESSAGE_LISTENER(this.getDisplayName(), this.getMessagingTypeInterfaceName()));
         }
      } else {
         try {
            Method[] methods = !this.exposesNoInterfaceClientView() ? this.messagingTypeInterfaceClass.getMethods() : EJBMethodsUtil.getNoInterfaceViewBusinessMethods(this.getBeanClass());
            Method[] var10 = methods;
            int var3 = methods.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Method m = var10[var4];
               MethodInfoImpl mi = MethodInfoImpl.createMethodInfoImpl(m, "MessageEndpoint");
               this.messagingTypeMethods.put(mi.getSignature(), mi);
            }
         } catch (Exception var8) {
            Loggable l = EJBLogger.logunableToInitializeInterfaceMethodInfoLoggable(this.getEJBName(), StackTraceUtilsClient.throwable2StackTrace(var8));
            throw new WLDeploymentException(l.getMessageText(), var8);
         }
      }

   }

   private void setMethodDescriptors(Class c, ClientViewDescriptor cvDesc) throws WLDeploymentException {
      if (this.getIsJMSBased()) {
         try {
            Method m = this.getBeanClass().getMethod("onMessage", Message.class);
            this.onMessageMethodDescriptor = this.createMethodDescriptor(m, c, this.onMessageMethodInfo, cvDesc);
         } catch (NoSuchMethodException var13) {
            throw new AssertionError("Should not reach");
         }
      } else {
         Method[] var14 = c.getMethods();
         int var4 = var14.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var14[var5];
            if (!Helper.isBeaSyntheticMethod(m)) {
               MethodInfo methodInfo = (MethodInfo)this.messagingTypeMethods.get(getMethodSignature(m));
               if (methodInfo != null) {
                  MethodText mt = new MethodText();
                  mt.setMethod(m);
                  mt.setOptions(128);
                  String mdName = "eo_" + mt.toString();
                  MethodDescriptor md = this.setMethodDescriptor((BaseEJBHomeIntf)null, m, c, methodInfo, mdName, cvDesc);

                  try {
                     Method intfMethod = this.exposesNoInterfaceClientView() ? this.getBeanClass().getMethod(m.getName(), m.getParameterTypes()) : this.getMessagingTypeInterfaceClass().getMethod(m.getName(), m.getParameterTypes());
                     this.mdbMethodDescriptors.put(intfMethod.toString(), md);
                  } catch (NoSuchMethodException var12) {
                  }
               }
            }
         }
      }

   }

   public void prepare(Environment env) throws WLDeploymentException {
      super.prepare(env);
      if ((!this.isJmsBased || this.destinationResourceLink != null || this.messageDestinationLink != null || this.destinationJNDIName != null) && (this.isJmsBased || this.exposesNoInterfaceClientView() || this.resourceAdapterJndiName != null && !this.resourceAdapterJndiName.trim().isEmpty())) {
         ClientViewDescriptor cvDesc;
         if (this.getIsJMSBased()) {
            cvDesc = new ClientViewDescriptor(MessageListener.class, "MessageEndpoint", true, false, this);
            this.setMethodDescriptors(this.getBeanClass(), cvDesc);
         } else {
            cvDesc = new ClientViewDescriptor(this.exposesNoInterfaceClientView() ? this.getGeneratedBeanClass() : this.getMessagingTypeInterfaceClass(), "MessageEndpoint", true, false, this);
            this.setMethodDescriptors(this.getMessageDrivenLocalObjectClass(), cvDesc);
            if (debugLogger.isDebugEnabled()) {
               debug("Dumping Messaging Type MethodInfos for: " + this.getDisplayName());
               debug("Messaging Type MethodInfos: " + this.messagingTypeMethods.values());
               debug("Dumping Messaging Type MethodDescriptors for: " + this.getDisplayName());
               this.dumpMethodDescriptorFields(this.getMessageDrivenLocalObjectClass().getFields(), (Object)null);
            }
         }

      } else {
         Loggable l = EJBLogger.logNoMdbDestinationConfiguredLoggable(this.getDisplayName());
         throw new WLDeploymentException(l.getMessageText());
      }
   }

   public void activate(Map c, Map qc) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("activate() for ejb:" + this.getEJBName());
      }

      this.subscriptionDeleteList.clear();
      this.isRemoteSubjectExists = CrossDomainSecurityManager.getCrossDomainSecurityUtil().ifRemoteSubjectExists(this.providerURL);
      if (this.getIsJMSBased()) {
         this.detectDestinationByJNDIName(this.destinationJNDIName);
      }

      if (this.messageDestinationLink != null) {
         this.resolveMessageDestinationLink();
      }

      this.resolveDestinationResourceLink();
      this.resolveConnectionFactoryResourceLink();
      String dispatchPolicyName = this.getDispatchPolicy();
      WorkManagerFactory wmf = WorkManagerFactory.getInstance();
      WorkManager defaultWM = wmf.find("weblogic.kernel.Default");
      this.wm = wmf.find(dispatchPolicyName, this.getDeploymentInfo().getApplicationId(), (String)null);
      if (dispatchPolicyName != null) {
         if (defaultWM.getType() == 1 && this.wm == defaultWM) {
            EJBLogger.logMDBUnknownDispatchPolicyWM(this.getEJBName(), dispatchPolicyName);
         } else if (defaultWM.getType() == 2 && !Kernel.isDispatchPolicy(dispatchPolicyName)) {
            EJBLogger.logMDBUnknownDispatchPolicy(this.getEJBName(), dispatchPolicyName);
         }
      }

      if (!this.getIsJMSBased()) {
         if (this.exposesNoInterfaceClientView() && (this.getResourceAdapterJndiName() == null || this.getResourceAdapterJndiName().trim().isEmpty())) {
            String jndiName = this.getAutoWirableResourcAdaptersJndi();
            if (debugLogger.isDebugEnabled()) {
               debug("Auto-wiring " + this.getEJBName() + " to Resource Adapter with JNDI " + jndiName);
            }

            this.setResourceAdapterJndiName(jndiName);
         }

         this.createBeanManagerForJCA();
      }

   }

   private String getAutoWirableResourcAdaptersJndi() throws WLDeploymentException {
      List raJndiNames = EndpointActivationUtils.accessor.getRAJndiName(this.getMessagingTypeInterfaceName(), this.getDeploymentInfo().getApplicationId());
      if (raJndiNames != null && raJndiNames.size() == 1) {
         return (String)raJndiNames.get(0);
      } else {
         Loggable l = EJBLogger.logNoMdbDestinationConfiguredLoggable(this.getDisplayName());
         throw new WLDeploymentException(l.getMessageText());
      }
   }

   public void deployMessageDrivenBeans() throws WLDeploymentException {
      if (!this.getIsJMSBased()) {
         if (this.mdManagerList.isEmpty()) {
            EJBLogger.logErrorOnStartMDBs(this.getDisplayName());
         } else {
            MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)this.mdManagerList.get(0);
            mgr.setIsDeployed(true);
            this.startManager(mgr);
         }
      } else {
         while(true) {
            if (this.destAvailListener.get() != null || this.destAvailListener.compareAndSet((Object)null, new DestinationAvailabilityListenerImpl())) {
               if (this.destAvailListener.get() != null) {
                  ((DestinationAvailabilityListenerImpl)this.destAvailListener.get()).register();
               }
               break;
            }
         }
      }

   }

   public void perhapsStartTimerManager() {
      Iterator var1 = this.getManagersListCopy().iterator();

      while(var1.hasNext()) {
         BeanManager bm = (BeanManager)var1.next();
         bm.perhapsStartTimerManager();
      }

   }

   void startManager(MessageDrivenManagerIntf mgr) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("startManager() for ejb:" + this.getEJBName());
      }

      Thread currentThread = Thread.currentThread();
      ClassLoader cl = currentThread.getContextClassLoader();
      EJBRuntimeUtils.pushEnvironment(this.getEnvBuilder().getRootContext());
      currentThread.setContextClassLoader(this.getClassLoader());

      try {
         mgr.start();
         mgr.setIsDeployed(true);
      } finally {
         EJBRuntimeUtils.popEnvironment();
         currentThread.setContextClassLoader(cl);
      }

   }

   void resolveDestinationResourceLink() {
      if (this.destinationResourceLink != null) {
         this.destinationJNDIName = EnvUtils.resolveResourceLink(this.getDeploymentInfo().getApplicationId(), this.destinationResourceLink);
      }

   }

   void resolveConnectionFactoryResourceLink() {
      if (this.connectionFactoryResourceLink != null) {
         this.connectionFactoryJNDIName = EnvUtils.resolveResourceLink(this.getDeploymentInfo().getApplicationId(), this.connectionFactoryResourceLink);
      }

   }

   private void resolveMessageDestinationLink() throws WLDeploymentException {
      ApplicationAccess aa = ApplicationAccess.getApplicationAccess();
      ApplicationContextInternal appCtx = aa.getCurrentApplicationContext();
      Collection ctxs = new HashSet();
      Module[] var4 = appCtx.getApplicationModules();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Module m = var4[var6];
         if (ModuleType.EJB.toString().equals(m.getType()) || ModuleType.WAR.toString().equals(m.getType())) {
            ctxs.add(appCtx.getModuleContext(m.getId()));
         }
      }

      MessageDestinationInfoRegistry.MessageDestinationInfo mdi;
      if (this.messageDestinationLink.contains("#")) {
         mdi = MessageDestinationReference.findByPath(this.messageDestinationLink, ctxs, this.getDeploymentInfo().getModuleURI());
      } else if (this.messageDestinationLink.contains("/")) {
         mdi = MessageDestinationReference.findByModuleName(this.messageDestinationLink, ctxs);
      } else {
         mdi = MessageDestinationReference.findByName(this.messageDestinationLink, ctxs);
      }

      if (mdi == null) {
         Loggable l = EJBLogger.logUnableToResolveMDBMessageDestinationLinkLoggable(this.messageDestinationLink, this.getEJBName(), this.getDeploymentInfo().getModuleId());
         throw new WLDeploymentException(l.getMessage());
      } else {
         MessageDestinationDescriptorBean mdd = mdi.getMessageDestinationDescriptor();
         if (mdd != null) {
            this.destinationResourceLink = mdd.getDestinationResourceLink();
            if (this.destinationResourceLink == null) {
               this.destinationJNDIName = this.transformJndiName(mdd.getDestinationJNDIName());
               this.initialContextFactory = mdd.getInitialContextFactory();
               this.providerURL = mdd.getProviderUrl();
            } else {
               this.destinationJNDIName = EnvUtils.resolveResourceLink(this.getDeploymentInfo().getApplicationId(), this.destinationResourceLink);
            }
         }

         MessageDestinationBean mdb = mdi.getMessageDestination();
         if (this.destinationJNDIName == null && mdb != null) {
            if (mdb.getLookupName() != null) {
               this.destinationJNDIName = mdb.getLookupName();
            } else if (mdb.getMappedName() != null) {
               this.destinationJNDIName = mdb.getMappedName();
            }
         }

      }
   }

   private AuthenticatedSubject getCurrentSubject() {
      return SecurityManager.getCurrentSubject(KERNEL_ID);
   }

   private AuthenticatedSubject getRemoteSubject() {
      CrossDomainSecurityUtil cdsu = CrossDomainSecurityManager.getCrossDomainSecurityUtil();
      return (AuthenticatedSubject)cdsu.getRemoteSubject(this.providerURL, (AbstractSubject)null);
   }

   MigratableTargetMBean getMtMBean(DestinationDetail dd) {
      String mtName = dd.getMigratableTargetName();
      MigratableTargetMBean mt = null;
      if (mtName != null) {
         mt = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().lookupMigratableTarget(mtName);
         if (mt != null && mt.getServerNames() != null && !mt.getServerNames().contains(this.currServerName)) {
            mt = null;
         }
      }

      return mt;
   }

   public void updateImplClassLoader() throws WLDeploymentException {
      super.updateImplClassLoader();
      Iterator var1 = this.getManagersListCopy().iterator();

      while(var1.hasNext()) {
         BeanManager bm = (BeanManager)var1.next();
         bm.beanImplClassChangeNotification();
      }

   }

   void setMessageSelector(String selector) {
      this.messageSelector = selector;
   }

   void updateMaxBeansInFreePool(int max) {
      this.maxConcurrentInstances = max;
      Iterator var2 = this.getManagersListCopy().iterator();

      while(var2.hasNext()) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var2.next();
         mgr.getPool().updateMaxBeansInFreePool(max);
      }

      if (debugLogger.isDebugEnabled()) {
         debug("Updated MaxBeansInFreePool to " + max + " for EJB " + this.getDisplayName());
      }

   }

   void updatePoolIdleTimeoutSeconds(int seconds) {
      Iterator var2 = this.getManagersListCopy().iterator();

      while(var2.hasNext()) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var2.next();
         mgr.getPool().updateIdleTimeoutSeconds(seconds);
      }

      if (debugLogger.isDebugEnabled()) {
         debug("Updated Pool IdleTimeoutSeconds to " + seconds + " for EJB " + this.getDisplayName());
      }

   }

   void updateJMSPollingIntervalSeconds(int seconds) {
      this.jmsPollingIntervalSeconds = seconds;
      if (debugLogger.isDebugEnabled()) {
         debug("Updated JMSPollingIntervalSeconds to " + seconds + " for EJB " + this.getDisplayName());
      }

   }

   public void undeployManagers() {
      Iterator var1 = this.getManagersListCopy().iterator();

      while(var1.hasNext()) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var1.next();
         mgr.undeploy();
         if (this.isDestinationTopic() && this.isDurableSubscriber() && mgr.getDeleteDurableSubscription()) {
            this.subscriptionDeleteList.add(mgr);
         }
      }

   }

   synchronized void undeployManagers(boolean needUnsubscribe) {
      List allMgrs = this.getManagersListCopy();
      this.mdManagerList.clear();
      MessageDrivenManagerIntf defaultMgr = (MessageDrivenManagerIntf)this.defaultMDManager.getAndSet((Object)null);
      if (defaultMgr != null) {
         allMgrs.add(defaultMgr);
      }

      Iterator var4 = allMgrs.iterator();

      MessageDrivenManagerIntf mgr;
      while(var4.hasNext()) {
         mgr = (MessageDrivenManagerIntf)var4.next();
         mgr.undeploy();
      }

      if (this.isDurableSubscriber() && needUnsubscribe) {
         var4 = allMgrs.iterator();

         while(var4.hasNext()) {
            mgr = (MessageDrivenManagerIntf)var4.next();
            mgr.remove();
         }
      }

   }

   private static void debug(String s) {
      debugLogger.debug("[MessageDrivenBeanInfoImpl] " + s);
   }

   public String getDestinationResourceLink() {
      return this.destinationResourceLink;
   }

   public void setEJBComponentRuntime(EJBRuntimeHolder compRTMBean) {
      this.ejbComponentRuntime = compRTMBean;
   }

   private Object doPrivilegedAction(AuthenticatedSubject as, PrivilegedExceptionAction pea) throws NamingException {
      if (as == null || SecurityServiceManager.isKernelIdentity(as) || SecurityServiceManager.isServerIdentity(as) || this.getIsRemoteSubjectExists()) {
         as = this.getRemoteSubject();
      }

      try {
         return as.doAs(KERNEL_ID, pea);
      } catch (PrivilegedActionException var4) {
         if (var4.getCause() instanceof NamingException) {
            throw (NamingException)var4.getCause();
         } else {
            throw new AssertionError(var4);
         }
      }
   }

   private static boolean isSet(String attribute, Object obj) {
      return ((DescriptorBean)obj).isSet(attribute);
   }

   private int convertToInt(List values, String val) {
      for(int index = 0; index < values.size(); ++index) {
         if (((String)values.get(index)).equalsIgnoreCase(val)) {
            return index;
         }
      }

      return -1;
   }

   public int getDistributedDestinationConnection() {
      return this.distributedDestinationConnection;
   }

   public int getTopicMessagesDistributionMode() {
      return this.topicMessagesDistributionMode;
   }

   public void unRegister() {
      if (this.destAvailListener.get() != null) {
         ((DestinationAvailabilityListenerImpl)this.destAvailListener.get()).unRegister();
      }

   }

   public void deleteDurableSubscribers() {
      Iterator var1 = this.subscriptionDeleteList.iterator();

      while(var1.hasNext()) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var1.next();
         mgr.remove();
      }

      this.subscriptionDeleteList.clear();
   }

   private void createBeanManagerForJCA() throws WLDeploymentException {
      this.setupBeanManager(this.ejbComponentRuntime);
      MessageDrivenManagerIntf bm = this.getBeanManager();
      bm.setup((BaseEJBRemoteHomeIntf)null, (BaseEJBLocalHomeIntf)null, this, this.getEnvBuilder().getRootContext(), this.getRuntimeHelper().getSecurityHelper());
      this.mdManagerList.add(bm);
   }

   MessageDrivenManagerIntf createBeanManagerFor(DestinationDetail dd) {
      return this.createBeanManagerFor(dd, this.getMtMBean(dd));
   }

   MessageDrivenManagerIntf createDefaultBeanManager() {
      return this.createBeanManagerFor((DestinationDetail)null, (MigratableTargetMBean)null);
   }

   private MessageDrivenManagerIntf createBeanManagerFor(DestinationDetail dd, MigratableTargetMBean mt) {
      try {
         if (debugLogger.isDebugEnabled()) {
            if (dd != null && this.isDistributedDestination(dd)) {
               debug("Creating Bean Manager for member: " + dd.toString());
            } else {
               debug("Creating Bean Manager for destination: " + this.destinationJNDIName);
            }
         }

         this.setupBeanManager(this.ejbComponentRuntime);
         MessageDrivenManagerIntf bm = this.getBeanManager();
         bm.setup(this, this.getEnvBuilder().getRootContext(), this.destinationJNDIName, mt, dd, this.getRuntimeHelper().getSecurityHelper());
         return bm;
      } catch (WLDeploymentException var4) {
         throw new IllegalStateException(var4);
      }
   }

   private boolean isDistributedDestination(DestinationDetail dd) {
      return dd.getType() == 4 || dd.getType() == 6 || dd.getType() == 5;
   }

   void reRegister(boolean needUnsubscribe) {
      this.undeployManagers(needUnsubscribe);
      Thread currentThread = Thread.currentThread();
      ClassLoader savedCL = currentThread.getContextClassLoader();
      currentThread.setContextClassLoader(this.getModuleClassLoader());

      try {
         if (this.getIsJMSBased()) {
            this.unRegister();
            if (this.destAvailListener.get() == null) {
               throw new IllegalStateException("DestinationAvailabilityListener is not present");
            }
         } else {
            this.createBeanManagerForJCA();
         }

         this.deployMessageDrivenBeans();
      } catch (Exception var8) {
         EJBLogger.logStackTrace(var8);
      } finally {
         currentThread.setContextClassLoader(savedCL);
      }

   }

   void reConnect(boolean needUnsubscribe) {
      List allMgrs = this.getManagersListCopy();
      MessageDrivenManagerIntf defaultMgr = (MessageDrivenManagerIntf)this.defaultMDManager.getAndSet((Object)null);
      if (defaultMgr != null) {
         allMgrs.add(defaultMgr);
      }

      Iterator var4 = allMgrs.iterator();

      MessageDrivenManagerIntf mgr;
      while(var4.hasNext()) {
         mgr = (MessageDrivenManagerIntf)var4.next();
         mgr.stop();
      }

      var4 = allMgrs.iterator();

      while(var4.hasNext()) {
         mgr = (MessageDrivenManagerIntf)var4.next();
         mgr.resetMessageConsumer(needUnsubscribe);
      }

   }

   public void processInactive(boolean inActive) throws BeanUpdateFailedException {
      this.isInactive = inActive;
      Iterator var2;
      MessageDrivenManagerIntf mgr;
      if (!this.isInactive) {
         if (this.getIsJMSBased()) {
            ((DestinationAvailabilityListenerImpl)this.destAvailListener.get()).registerWithJMS();
         } else {
            try {
               var2 = this.mdManagerList.iterator();

               while(var2.hasNext()) {
                  mgr = (MessageDrivenManagerIntf)var2.next();
                  this.startManager(mgr);
               }

               EJBLogger.logMDBActive(this.getEJBName());
            } catch (WLDeploymentException var6) {
               Loggable l = EJBLogger.logInactiveMDBStartFailLoggable(this.getEJBName(), var6);
               EJBLogger.logStackTraceAndMessage(l.getMessageText(), var6);
               Iterator var4 = this.mdManagerList.iterator();

               while(var4.hasNext()) {
                  MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var4.next();
                  mgr.stop();
               }

               this.isInactive = true;
               EJBLogger.logMDBInactive(this.getEJBName());
               throw new BeanUpdateFailedException(l.getMessage());
            }
         }
      } else {
         var2 = this.getManagersListCopy().iterator();

         while(var2.hasNext()) {
            mgr = (MessageDrivenManagerIntf)var2.next();
            mgr.stop();
         }

         if (this.getIsJMSBased()) {
            ((DestinationAvailabilityListenerImpl)this.destAvailListener.get()).unRegister();
         }

         EJBLogger.logMDBInactive(this.getEJBName());
      }

   }

   public void suspendManagers() {
      this.mgrStartSuspended = true;
      Iterator var1 = this.getManagersListCopy().iterator();

      while(var1.hasNext()) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var1.next();
         mgr.suspend();
      }

   }

   public void resumeManagers() {
      this.mgrStartSuspended = false;
      Iterator var1 = this.getManagersListCopy().iterator();

      while(var1.hasNext()) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var1.next();
         mgr.resume();
      }

   }

   public boolean shouldManagerStartSuspended() {
      return this.mgrStartSuspended;
   }

   public void disableManagerStartSuspended() {
      this.mgrStartSuspended = false;
   }

   public void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescriptor, ApplicationContextInternal appCtx) {
      this.ul.addBeanUpdateListener(wlBean, ejbDescriptor);
   }

   public void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescriptor, ApplicationContextInternal appCtx) {
      this.ul.removeBeanUpdateListener(wlBean, ejbDescriptor);
   }

   public EJBCheckerFactory getEJBCheckerFactory(DeploymentInfo di) {
      return new MessageDrivenBeanCheckerFactory(di, this);
   }

   static {
      Set tmpSet = new HashSet();
      Field[] var1 = weblogic.ejb.spi.DDConstants.class.getFields();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Field field = var1[var3];
         if (field.getName().startsWith("ACP_KEY") && field.getType().isAssignableFrom(String.class)) {
            try {
               tmpSet.add((String)field.get((Object)null));
            } catch (IllegalAccessException var6) {
            }
         }
      }

      acpSet = Collections.unmodifiableSet(tmpSet);
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   final class QueueConnectionHandler extends AbstractConnectionHandler {
      QueueConnectionHandler(boolean isDD) {
         super(isDD);
      }

      boolean complianceCheck(List destDetails) {
         if (MessageDrivenBeanInfoImpl.this.getTopicMessagesDistributionMode() > 0) {
            EJBLogger.logInvalidConfigurationForTopicMessagesDistributionMode(MessageDrivenBeanInfoImpl.this.getDisplayName());
            return false;
         } else {
            if (MessageDrivenBeanInfoImpl.this.getDistributedDestinationConnection() < 0) {
               MessageDrivenBeanInfoImpl.this.setDistributedDestinationConnection(0);
            }

            return true;
         }
      }

      void computeConnectionMode(DestinationDetail destDetail) {
         if (!destDetail.isLocalCluster()) {
            this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.EVERY_MEMBER;
         } else {
            this.connMode = MessageDrivenBeanInfoImpl.this.getDistributedDestinationConnection() <= 0 ? MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER : MessageDrivenBeanInfoImpl.ConnectionMode.EVERY_MEMBER;
         }

         if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
            this.debug("Destination at JNDI name : " + destDetail.getJNDIName() + " is a " + (destDetail.isLocalCluster() ? "Local" : "Remote") + " Distributed Queue. Connection mode determined to be " + this.connMode);
         }

      }
   }

   final class TopicConnectionHandler extends AbstractConnectionHandler {
      private final boolean isPartitioned;

      TopicConnectionHandler(boolean isDD, boolean isPartitioned) {
         super(isDD);
         this.isPartitioned = isPartitioned;
      }

      boolean complianceCheck(List destDetails) {
         if (MessageDrivenBeanInfoImpl.this.getTopicMessagesDistributionMode() < 0) {
            MessageDrivenBeanInfoImpl.this.topicMessagesDistributionMode = this.isPartitioned ? 2 : 0;
         }

         Iterator var2;
         DestinationDetail dd;
         if (MessageDrivenBeanInfoImpl.this.getTopicMessagesDistributionMode() == 0) {
            if (this.isPartitioned) {
               EJBLogger.logIllegalPermutationOnPDTAndComp(MessageDrivenBeanInfoImpl.this.getDisplayName());
               return false;
            }

            if (MessageDrivenBeanInfoImpl.this.isDurableSubscriber()) {
               var2 = destDetails.iterator();

               while(var2.hasNext()) {
                  dd = (DestinationDetail)var2.next();
                  if (dd.getType() == 5 && !dd.isLocalCluster()) {
                     EJBLogger.logIllegalSubscriptionOnDurRemoteRDT(MessageDrivenBeanInfoImpl.this.getDisplayName(), MessageDrivenBeanInfoImpl.this.getDestinationName());
                     return false;
                  }
               }
            }

            if (MessageDrivenBeanInfoImpl.this.getDistributedDestinationConnection() < 0) {
               MessageDrivenBeanInfoImpl.this.setDistributedDestinationConnection(0);
            }
         } else {
            var2 = destDetails.iterator();

            while(var2.hasNext()) {
               dd = (DestinationDetail)var2.next();
               if (dd.getType() == 3 || !dd.isAdvancedTopicSupported()) {
                  EJBLogger.logInvalidConfigurationForPre1033(MessageDrivenBeanInfoImpl.this.getDisplayName(), "topicMessagesDistributionMode");
                  return false;
               }
            }
         }

         return true;
      }

      void computeConnectionMode(DestinationDetail destDetail) {
         int distributionMode = MessageDrivenBeanInfoImpl.this.getTopicMessagesDistributionMode();
         if (distributionMode == 0) {
            this.connMode = !destDetail.isLocalCluster() ? MessageDrivenBeanInfoImpl.ConnectionMode.ONLY_ONE_MEMBER : MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER_PRE_10_3_3;
         } else if (!destDetail.isLocalCluster()) {
            if (!MessageDrivenBeanInfoImpl.this.isDurableSubscriber() && destDetail.getType() == 5 && distributionMode == 1) {
               this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.ONLY_ONE_MEMBER;
               MessageDrivenBeanInfoImpl.this.setDistributedDestinationConnection(0);
            } else {
               this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.EVERY_MEMBER;
               MessageDrivenBeanInfoImpl.this.setDistributedDestinationConnection(1);
            }
         } else if (MessageDrivenBeanInfoImpl.this.getDistributedDestinationConnection() == 1) {
            this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.EVERY_MEMBER;
         } else if (MessageDrivenBeanInfoImpl.this.getDistributedDestinationConnection() == 0) {
            if (this.isPartitioned && distributionMode == 1) {
               EJBLogger.logOverridenLocalOnlyWithEveryMember(MessageDrivenBeanInfoImpl.this.getDisplayName());
               this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.EVERY_MEMBER;
            } else {
               this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER;
            }
         } else if (2 == distributionMode) {
            this.connMode = MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER;
         } else if (1 == distributionMode) {
            this.connMode = this.isPartitioned ? MessageDrivenBeanInfoImpl.ConnectionMode.EVERY_MEMBER : MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER;
         }

         if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
            this.debug("Destination at JNDI name : " + destDetail.getJNDIName() + " is a " + (destDetail.isLocalCluster() ? "Local" : "Remote") + (this.isPartitioned ? " Partitioned " : " Replicated ") + " Distributed Topic and '" + (String)DDConstants.TOPIC_MESSAGE_DISTRIBUTION_MODES.get(distributionMode) + "' is configured. Connection mode determined to be " + this.connMode);
         }

      }

      void undeployAndActivate(MessageDrivenManagerIntf mgr) {
         if (this.isDD && !this.isPartitioned) {
            if (this.connMode == MessageDrivenBeanInfoImpl.ConnectionMode.ONLY_ONE_MEMBER) {
               if (this.managerCreated.compareAndSet(true, false)) {
                  mgr.undeploy();
               } else {
                  this.debug("Duplicate remove event for destination " + mgr.getDDMemberName());
               }

               this.perhapsActivateBackupMember();
            } else if (this.connMode == MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER_PRE_10_3_3) {
               if ((mgr.getTargetMBean() == null || !(mgr.getTargetMBean() instanceof MigratableTargetMBean)) && !this.managerCreated.compareAndSet(true, false)) {
                  this.debug("Duplicate remove event for destination " + mgr.getDDMemberName());
               } else {
                  mgr.undeploy();
               }

               this.perhapsActivateBackupMember();
            } else {
               mgr.undeploy();
               if (this.connMode == MessageDrivenBeanInfoImpl.ConnectionMode.LOCAL_MEMBER) {
                  this.perhapsActivateBackupMember();
               }
            }
         } else {
            mgr.undeploy();
         }

      }

      private void perhapsActivateBackupMember() {
         if (!this.getBackupDestinationDetails().isEmpty()) {
            MessageDrivenManagerIntf addedManager = null;
            DestinationDetail theCandidate;
            switch (this.connMode) {
               case EVERY_MEMBER:
               default:
                  break;
               case LOCAL_MEMBER:
                  addedManager = MessageDrivenBeanInfoImpl.this.createBeanManagerFor((DestinationDetail)this.getBackupDestinationDetails().get(0));
                  break;
               case ONLY_ONE_MEMBER:
                  List backupDestList = new LinkedList(this.getBackupDestinationDetails());
                  if (!backupDestList.isEmpty() && this.managerCreated.compareAndSet(false, true)) {
                     theCandidate = null;
                     Iterator var4 = backupDestList.iterator();

                     while(var4.hasNext()) {
                        DestinationDetail dest = (DestinationDetail)var4.next();
                        theCandidate = dest;
                        if (dest.isLocalWLSServer()) {
                           break;
                        }
                     }

                     addedManager = MessageDrivenBeanInfoImpl.this.createBeanManagerFor(theCandidate);
                  }
                  break;
               case LOCAL_MEMBER_PRE_10_3_3:
                  theCandidate = (DestinationDetail)this.getBackupDestinationDetails().get(0);
                  if (MessageDrivenBeanInfoImpl.this.getMtMBean(theCandidate) != null || theCandidate.isLocalWLSServer() && this.managerCreated.compareAndSet(false, true)) {
                     addedManager = MessageDrivenBeanInfoImpl.this.createBeanManagerFor(theCandidate);
                  }
            }

            if (addedManager != null) {
               try {
                  MessageDrivenBeanInfoImpl.this.startManager(addedManager);
                  MessageDrivenBeanInfoImpl.this.mdManagerList.add(addedManager);
               } catch (WLDeploymentException var6) {
                  EJBLogger.logStackTrace(var6);
               }
            }

         }
      }
   }

   abstract class AbstractConnectionHandler {
      final boolean isDD;
      ConnectionMode connMode;
      final AtomicBoolean managerCreated = new AtomicBoolean(false);
      final List backupDestMembersList = new ArrayList();

      AbstractConnectionHandler(boolean isDD) {
         this.isDD = isDD;
      }

      boolean resolveDestinationWorkMode(List destList) {
         if (!this.complianceCheck(destList)) {
            return false;
         } else {
            if (!destList.isEmpty()) {
               Thread currentThread = Thread.currentThread();
               ClassLoader cl = currentThread.getContextClassLoader();
               EJBRuntimeUtils.pushEnvironment(MessageDrivenBeanInfoImpl.this.getEnvBuilder().getRootContext());
               currentThread.setContextClassLoader(MessageDrivenBeanInfoImpl.this.getClassLoader());

               try {
                  if (this.isDD) {
                     this.computeConnectionMode((DestinationDetail)destList.get(0));
                     this.activateManagersForDD(destList);
                  } else {
                     Iterator var4 = destList.iterator();

                     while(var4.hasNext()) {
                        DestinationDetail dd = (DestinationDetail)var4.next();
                        this.activateManagerForNonDD(dd);
                     }
                  }
               } finally {
                  EJBRuntimeUtils.popEnvironment();
                  currentThread.setContextClassLoader(cl);
               }
            }

            return true;
         }
      }

      abstract boolean complianceCheck(List var1);

      abstract void computeConnectionMode(DestinationDetail var1);

      private void activateManagersForDD(List destMembers) {
         List newMgrs = this.createManagersForDD(destMembers);
         Iterator var3 = newMgrs.iterator();

         while(var3.hasNext()) {
            MessageDrivenManagerIntf mgrx = (MessageDrivenManagerIntf)var3.next();

            try {
               mgrx.updateStatus(1);
               MessageDrivenBeanInfoImpl.this.startManager(mgrx);
               mgrx.perhapsStartTimerManager();
            } catch (Exception var6) {
               EJBLogger.logStackTrace(var6);
            }
         }

         MessageDrivenBeanInfoImpl.this.mdManagerList.addAll(newMgrs);
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)MessageDrivenBeanInfoImpl.this.defaultMDManager.getAndSet((Object)null);
         if (mgr != null) {
            if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
               this.debug("Default BeanManager @" + mgr.hashCode() + " will be undeployed  because the destination is distributed.");
            }

            mgr.undeploy();
         }

      }

      private void activateManagerForNonDD(DestinationDetail dest) {
         MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)MessageDrivenBeanInfoImpl.this.defaultMDManager.getAndSet((Object)null);
         if (mgr == null) {
            throw new IllegalStateException("Default BeanManager is null");
         } else {
            try {
               mgr.enableDestination(dest, MessageDrivenBeanInfoImpl.this.getMtMBean(dest));
               TargetMBean tm = mgr.getTargetMBean();
               if (mgr.isNonDDMD() && tm != null) {
                  if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
                     this.debug("Destination with JNDI name : " + dest.getJNDIName() + " is on a migratable target, so register it with Migration Framework.");
                  }

                  try {
                     ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
                     MigrationManager mm = (MigrationManager)sl.getService(MigrationManager.class, new Annotation[0]);
                     mm.register((Migratable)mgr, (MigratableTargetMBean)tm);
                  } catch (MultiException | IllegalStateException var7) {
                     throw new MigrationException(var7);
                  }
               } else {
                  MessageDrivenBeanInfoImpl.this.startManager(mgr);
               }
            } catch (Exception var8) {
               EJBLogger.logStackTraceAndMessage("Error starting the MessageDrivenBean " + MessageDrivenBeanInfoImpl.this.getEJBName(), var8);
            }

            try {
               mgr.perhapsStartTimerManager();
            } catch (Exception var6) {
               EJBLogger.logStackTrace(var6);
            }

            if (!MessageDrivenBeanInfoImpl.this.mdManagerList.contains(mgr)) {
               MessageDrivenBeanInfoImpl.this.mdManagerList.add(mgr);
            }

         }
      }

      private boolean filterMultipleJMSServer(DestinationDetail dest, List mgrs) {
         if (dest.getType() == 5 && MessageDrivenBeanInfoImpl.this.getDistributedDestinationConnection() != 1 && MessageDrivenBeanInfoImpl.this.getTopicMessagesDistributionMode() != 2) {
            if (!mgrs.isEmpty()) {
               return true;
            }

            Iterator var3 = MessageDrivenBeanInfoImpl.this.getManagersListCopy().iterator();

            while(var3.hasNext()) {
               MessageDrivenManagerIntf mgr = (MessageDrivenManagerIntf)var3.next();
               if (dest.getWLSServerName().equals(mgr.getDestination().getWLSServerName())) {
                  return true;
               }
            }
         }

         return false;
      }

      void handleDestinationsUnavailable(List list) {
         List removedBackupDestDetails = new ArrayList();
         List tempBackupMembersList = new ArrayList(this.getBackupDestinationDetails());
         Iterator var4 = list.iterator();

         while(true) {
            while(var4.hasNext()) {
               DestinationDetail removedDest = (DestinationDetail)var4.next();
               Iterator var6 = tempBackupMembersList.iterator();

               while(var6.hasNext()) {
                  DestinationDetail dtMember = (DestinationDetail)var6.next();
                  if (dtMember.getMemberConfigName().equals(removedDest.getMemberConfigName())) {
                     if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
                        this.debug("Distributed Destination member [" + removedDest.getMemberConfigName() + "] has been removed");
                     }

                     removedBackupDestDetails.add(dtMember);
                     break;
                  }
               }
            }

            this.getBackupDestinationDetails().removeAll(removedBackupDestDetails);
            List tempActiveMDManagerList = MessageDrivenBeanInfoImpl.this.getManagersListCopy();
            Iterator var10 = list.iterator();

            while(var10.hasNext()) {
               DestinationDetail removedDD = (DestinationDetail)var10.next();
               Iterator var13 = tempActiveMDManagerList.iterator();

               while(var13.hasNext()) {
                  MessageDrivenManagerIntf mgrx = (MessageDrivenManagerIntf)var13.next();
                  if (mgrx.getDDMemberName().equals(removedDD.getMemberConfigName())) {
                     this.undeployAndActivate(mgrx);
                  }
               }
            }

            if (MessageDrivenBeanInfoImpl.this.mdManagerList.isEmpty() && MessageDrivenBeanInfoImpl.this.defaultMDManager.get() == null) {
               MessageDrivenManagerIntf mgr = MessageDrivenBeanInfoImpl.this.createDefaultBeanManager();
               mgr.updateStatus(0);
               MessageDrivenBeanInfoImpl.this.defaultMDManager.compareAndSet((Object)null, mgr);
            }

            return;
         }
      }

      void undeployAndActivate(MessageDrivenManagerIntf manager) {
         manager.undeploy();
      }

      List getBackupDestinationDetails() {
         return this.backupDestMembersList;
      }

      private List createManagersForDD(List destList) {
         List newMDManagers = new ArrayList();
         Iterator var7;
         DestinationDetail destx;
         switch (this.connMode) {
            case EVERY_MEMBER:
               var7 = destList.iterator();

               while(var7.hasNext()) {
                  destx = (DestinationDetail)var7.next();
                  newMDManagers.add(MessageDrivenBeanInfoImpl.this.createBeanManagerFor(destx));
               }

               return newMDManagers;
            case LOCAL_MEMBER:
               var7 = destList.iterator();

               while(var7.hasNext()) {
                  destx = (DestinationDetail)var7.next();
                  if (destx.isLocalWLSServer()) {
                     if (!this.filterMultipleJMSServer(destx, newMDManagers)) {
                        newMDManagers.add(MessageDrivenBeanInfoImpl.this.createBeanManagerFor(destx));
                     } else {
                        this.getBackupDestinationDetails().add(destx);
                     }
                  }
               }

               return newMDManagers;
            case ONLY_ONE_MEMBER:
               DestinationDetail theLastOne = null;
               Iterator var4 = destList.iterator();

               while(var4.hasNext()) {
                  DestinationDetail destxx = (DestinationDetail)var4.next();
                  theLastOne = destxx;
                  if (destxx.isLocalWLSServer()) {
                     break;
                  }
               }

               List dtMemberList = new LinkedList(destList);
               if (theLastOne != null && this.managerCreated.compareAndSet(false, true)) {
                  dtMemberList.remove(theLastOne);
                  newMDManagers.add(MessageDrivenBeanInfoImpl.this.createBeanManagerFor(theLastOne));
               }

               this.getBackupDestinationDetails().addAll(dtMemberList);
               return newMDManagers;
            case LOCAL_MEMBER_PRE_10_3_3:
               Iterator var5 = destList.iterator();

               while(true) {
                  while(var5.hasNext()) {
                     DestinationDetail dest = (DestinationDetail)var5.next();
                     if (MessageDrivenBeanInfoImpl.this.getMtMBean(dest) != null) {
                        newMDManagers.add(MessageDrivenBeanInfoImpl.this.createBeanManagerFor(dest));
                     } else if (dest.isLocalWLSServer() && this.managerCreated.compareAndSet(false, true)) {
                        newMDManagers.add(MessageDrivenBeanInfoImpl.this.createBeanManagerFor(dest));
                     } else {
                        this.getBackupDestinationDetails().add(dest);
                     }
                  }

                  return newMDManagers;
               }
            default:
               throw new AssertionError();
         }
      }

      void debug(String s) {
         BeanInfoImpl.debugLogger.debug("[AbstractConnectionHandler] " + s);
      }
   }

   private static enum ConnectionMode {
      EVERY_MEMBER,
      LOCAL_MEMBER,
      ONLY_ONE_MEMBER,
      LOCAL_MEMBER_PRE_10_3_3;
   }

   final class DestinationAvailabilityListenerImpl implements DestinationAvailabilityListener {
      private final Hashtable envProps = new Hashtable();
      private RegistrationHandle registrationHandle;
      private volatile AbstractConnectionHandler connHandler;

      public void onDestinationsAvailable(String destJNDIName, List list) {
         if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
            this.debug("DestinationsAvailable Event for [" + destJNDIName + "], destinations list: " + list);
         }

         if (list != null && !list.isEmpty()) {
            if (this.connHandler == null) {
               synchronized(this) {
                  if (this.connHandler == null) {
                     int type = ((DestinationDetail)list.get(0)).getType();
                     switch (type) {
                        case 0:
                        case 2:
                           if (!MessageDrivenBeanInfoImpl.this.isDestinationQueue() && !MessageDrivenBeanInfoImpl.this.isDestinationTopic()) {
                              MessageDrivenBeanInfoImpl.this.isDestinationQueue = true;
                           }

                           this.connHandler = MessageDrivenBeanInfoImpl.this.new QueueConnectionHandler(false);
                           break;
                        case 1:
                        case 3:
                           if (!MessageDrivenBeanInfoImpl.this.isDestinationQueue() && !MessageDrivenBeanInfoImpl.this.isDestinationTopic()) {
                              MessageDrivenBeanInfoImpl.this.isDestinationTopic = true;
                           }

                           this.connHandler = MessageDrivenBeanInfoImpl.this.new TopicConnectionHandler(false, false);
                           break;
                        case 4:
                           if (!MessageDrivenBeanInfoImpl.this.isDestinationQueue() && !MessageDrivenBeanInfoImpl.this.isDestinationTopic()) {
                              MessageDrivenBeanInfoImpl.this.isDestinationQueue = true;
                           }

                           this.connHandler = MessageDrivenBeanInfoImpl.this.new QueueConnectionHandler(true);
                           break;
                        case 5:
                           if (!MessageDrivenBeanInfoImpl.this.isDestinationQueue() && !MessageDrivenBeanInfoImpl.this.isDestinationTopic()) {
                              MessageDrivenBeanInfoImpl.this.isDestinationTopic = true;
                           }

                           this.connHandler = MessageDrivenBeanInfoImpl.this.new TopicConnectionHandler(true, false);
                           break;
                        case 6:
                           if (!MessageDrivenBeanInfoImpl.this.isDestinationQueue() && !MessageDrivenBeanInfoImpl.this.isDestinationTopic()) {
                              MessageDrivenBeanInfoImpl.this.isDestinationTopic = true;
                           }

                           this.connHandler = MessageDrivenBeanInfoImpl.this.new TopicConnectionHandler(true, true);
                     }
                  }
               }
            }

            if (this.connHandler != null && !this.connHandler.resolveDestinationWorkMode(list)) {
               this.unRegister();
            }

         }
      }

      public void onDestinationsUnavailable(String destJNDIName, List list) {
         if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
            this.debug("DestinationsUnavailable Event for [" + destJNDIName + "], destinations list: " + list);
         }

         if (list != null && !list.isEmpty()) {
            this.connHandler.handleDestinationsUnavailable(list);
         }

      }

      public void onFailure(String destJNDIName, Exception ex) {
         if (!BeanInfoImpl.debugLogger.isDebugEnabled() && ex instanceof NameNotFoundException) {
            EJBLogger.logMDBUnableToConnectToJMS(MessageDrivenBeanInfoImpl.this.getEJBName(), destJNDIName, "The destination for the MessageDrivenBean " + MessageDrivenBeanInfoImpl.this.getDisplayName() + " could not be resolved at this time. Please ensure the destination is  available at the JNDI name " + destJNDIName + ".  The EJB container will periodically attempt to resolve this MessageDrivenBean destination and additional warnings may be issued.");
         } else {
            EJBLogger.logMDBUnableToConnectToJMS(MessageDrivenBeanInfoImpl.this.getEJBName(), destJNDIName, StackTraceUtilsClient.throwable2StackTrace(ex));
         }

      }

      void register() throws WLDeploymentException {
         if (MessageDrivenBeanInfoImpl.this.isOnMessageTransacted()) {
            MessageDrivenBeanInfoImpl.this.acknowledgeMode = 2;
         }

         if (MessageDrivenBeanInfoImpl.this.getDestinationName() == null && MessageDrivenBeanInfoImpl.this.messageDestinationLink == null) {
            throw new WLDeploymentException(EJBLogger.logNoDestinationJNDINameSpecifiedLoggable().getMessageText());
         } else {
            MessageDrivenBeanInfoImpl.this.reSetUsernameAndPassword();
            this.envProps.put("java.naming.factory.initial", MessageDrivenBeanInfoImpl.this.initialContextFactory);
            this.envProps.put("weblogic.jndi.allowGlobalResourceLookup", "true");
            if (MessageDrivenBeanInfoImpl.this.providerURL != null) {
               this.envProps.put("java.naming.provider.url", MessageDrivenBeanInfoImpl.this.providerURL);
            }

            if (MessageDrivenBeanInfoImpl.this.hasCredentials) {
               this.envProps.put("java.naming.security.principal", MessageDrivenBeanInfoImpl.this.userName.toString());
               this.envProps.put("java.naming.security.credentials", MessageDrivenBeanInfoImpl.this.password.toString());
               this.createDefaultManagerAndRegister();
            } else {
               AuthenticatedSubject callerId = null;

               try {
                  callerId = MessageDrivenBeanInfoImpl.this.getRunAsSubject();
                  if (callerId == null) {
                     callerId = MessageDrivenBeanInfoImpl.this.getCurrentSubject();
                  }

                  if (callerId == null || SecurityServiceManager.isKernelIdentity(callerId) || SecurityServiceManager.isServerIdentity(callerId) || MessageDrivenBeanInfoImpl.this.getIsRemoteSubjectExists()) {
                     callerId = MessageDrivenBeanInfoImpl.this.getRemoteSubject();
                  }
               } catch (PrincipalNotFoundException var7) {
                  EJBLogger.logStackTrace(var7);
               }

               SecurityHelper.pushRunAsSubject(MessageDrivenBeanInfoImpl.KERNEL_ID, callerId);

               try {
                  this.createDefaultManagerAndRegister();
               } finally {
                  SecurityHelper.popRunAsSubject(MessageDrivenBeanInfoImpl.KERNEL_ID);
               }
            }

            if (BeanInfoImpl.debugLogger.isDebugEnabled()) {
               this.debug("Deploying JMS based MDB, Destination:" + MessageDrivenBeanInfoImpl.this.getDestinationName() + " isTransactional:" + MessageDrivenBeanInfoImpl.this.isOnMessageTransacted());
            }

         }
      }

      private void createDefaultManagerAndRegister() {
         MessageDrivenBeanInfoImpl.this.defaultMDManager.set(MessageDrivenBeanInfoImpl.this.createDefaultBeanManager());
         if (!MessageDrivenBeanInfoImpl.this.getIsInactive()) {
            ((MessageDrivenManagerIntf)MessageDrivenBeanInfoImpl.this.defaultMDManager.get()).updateStatus(0);
            this.registerWithJMS();
         } else {
            ((MessageDrivenManagerIntf)MessageDrivenBeanInfoImpl.this.defaultMDManager.get()).updateStatus(5);
         }

      }

      void registerWithJMS() {
         this.registrationHandle = JMSDestinationAvailabilityHelper.getInstance().register(this.envProps, MessageDrivenBeanInfoImpl.this.getDestinationName(), this, MessageDrivenBeanInfoImpl.this.getEnvBuilder().getRootContext());
      }

      public void unRegister() {
         if (this.connHandler != null) {
            this.connHandler.getBackupDestinationDetails().clear();
            this.connHandler = null;
         }

         if (this.registrationHandle != null) {
            this.registrationHandle.unregister();
         }

      }

      private void debug(String s) {
         BeanInfoImpl.debugLogger.debug("[DestinationAvailabilityListenerImpl] " + s);
      }
   }
}
