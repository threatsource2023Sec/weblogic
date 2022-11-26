package weblogic.jms.frontend;

import java.rmi.NoSuchObjectException;
import java.security.AccessController;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.event.EventContext;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.common.internal.PeerInfo;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.client.JMSXAConnection;
import weblogic.jms.client.JMSXAConnectionFactory;
import weblogic.jms.client.WLConnectionImpl;
import weblogic.jms.common.BeanHelper;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSOBSHelper;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.rmi.server.UnicastRemoteObject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;

public final class FEConnectionFactory implements ObjectChangeListener {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String EMPTY_STRING = "";
   public static final String DEFAULT_DELIVERY_MODE_STRING = "Persistent";
   public static final int DEFAULT_DELIVERY_MODE = 2;
   public static final int DEFAULT_PRIORITY = 4;
   public static final String DEFAULT_TIME_TO_DELIVER_STRING = "0";
   public static final long DEFAULT_TIME_TO_DELIVER = 0L;
   public static final long DEFAULT_TIME_TO_LIVE = 0L;
   public static final long DEFAULT_SEND_TIMEOUT = 10L;
   public static final long DEFAULT_REDELIVERY_DELAY = 0L;
   public static final int DEFAULT_TRANSACTION_TIMEOUT = 3600;
   public static final int DEFAULT_MESSAGES_MAXIMUM = 10;
   public static final boolean DEFAULT_USER_TRANSACTIONS_ENABLED = true;
   public static final String DEFAULT_MESSAGE_OVERRUN_POLICY_STRING = "KeepOld";
   public static final int DEFAULT_MESSAGE_OVERRUN_POLICY = 0;
   public static final int DEFAULT_ACKNOWLEDGE_POLICY = 1;
   public static final int DEFAULT_FLOW_MINIMUM = 50;
   public static final int DEFAULT_FLOW_MAXIMUM = 500;
   public static final int DEFAULT_FLOW_INTERVAL = 60;
   public static final int DEFAULT_FLOW_STEPS = 10;
   public static final int DEFAULT_CLIENT_ID_POLICY = 0;
   public static final int DEFAULT_SAF_TX_TIMEOUT = Integer.getInteger("weblogic.SAFTXTimeout", 3600);
   public static final String DEFAULT_SECURITY_POLICY = "ThreadBased";
   private transient GenericBeanListener connectionFactoryBeanListener;
   private transient GenericBeanListener defaultDeliveryParamsBeanListener;
   private transient GenericBeanListener clientParamsBeanListener;
   private transient GenericBeanListener transactionParamsBeanListener;
   private transient GenericBeanListener flowControlParamsBeanListener;
   private transient GenericBeanListener loadBalancingParamsBeanListener;
   private transient GenericBeanListener securityParamsBeanListener;
   private String fullyQualifiedName;
   private JMSConnectionFactory globalBoundConnectionFactory;
   private JMSConnectionFactory localBoundConnectionFactory;
   private JMSConnectionFactory appBoundConnectionFactory;
   private JMSConnectionFactoryBean connectionFactoryBean;
   private boolean bound;
   private boolean localBound;
   private boolean applicationBound;
   private boolean defaultConnectionFactory;
   private final FrontEnd frontEnd;
   private String name;
   private String moduleName;
   private String EARModuleName;
   private String jndiName;
   private String localJNDIName;
   private Context EARNamingContext;
   private Object jndiNameRebindLock;
   private boolean didUnbind;
   private boolean defaultTargetingEnabled;
   private String deliveryMode;
   private int priority;
   private String timeToDeliver;
   private long timeToLive;
   private long sendTimeout;
   private long redeliveryDelay;
   private long transactionTimeout;
   private boolean xaConnectionFactoryEnabled;
   private String clientId;
   private String clientIdPolicy;
   private String subscriptionSharingPolicy;
   private boolean allowCloseInOnMessage;
   private int messagesMaximum;
   private String overrunPolicy;
   private String acknowledgePolicy;
   private int compressionThreshold;
   private boolean flowControlEnabled;
   private int flowMinimum;
   private int flowMaximum;
   private int flowSteps;
   private int flowInterval;
   private boolean loadBalancingEnabled;
   private boolean serverAffinityEnabled;
   private String producerLoadBalancingPolicy;
   private boolean attachJMSXUserID;
   private String securityPolicy;
   private String synchronousPrefetchMode;
   private String oneWaySendMode;
   private int oneWaySendWindowSize;
   private String reconnectPolicy;
   private long reconnectBlockingMillis;
   private long totalReconnectPeriodMillis;
   private String defaultUnitOfOrder;
   private final JMSID factoryId;
   private int state;
   private boolean isJMSResourceDefinition;
   private int deploymentModuleType;
   private String deploymentModuleName;
   private InvocableMonitor rgInvocableMonitor;
   WebLogicMBean dscope;

   public FEConnectionFactory(FrontEnd frontEnd, String name, String jndiName, boolean allowCloseInOnMessage, boolean xaConnectionFactoryEnabled, String acknowledgePolicy) {
      this(frontEnd, name, jndiName, allowCloseInOnMessage, xaConnectionFactoryEnabled, acknowledgePolicy, true, true);
   }

   public FEConnectionFactory(FrontEnd frontEnd, String name, String jndiName, boolean allowCloseInOnMessage, boolean xaConnectionFactoryEnabled, String acknowledgePolicy, boolean loadBalancingEnabled, boolean serverAffinityEnabled) {
      this.connectionFactoryBean = null;
      this.defaultConnectionFactory = false;
      this.jndiName = "";
      this.localJNDIName = "";
      this.jndiNameRebindLock = new Object();
      this.compressionThreshold = Integer.MAX_VALUE;
      this.loadBalancingEnabled = true;
      this.serverAffinityEnabled = true;
      this.producerLoadBalancingPolicy = JMSConstants.PRODUCER_LB_POLICY_DEFAULT;
      this.attachJMSXUserID = false;
      this.synchronousPrefetchMode = "disabled";
      this.oneWaySendMode = "disabled";
      this.oneWaySendWindowSize = 1;
      this.reconnectPolicy = JMSConstants.RECONNECT_POLICY_PRODUCER;
      this.reconnectBlockingMillis = 60000L;
      this.totalReconnectPeriodMillis = -1L;
      this.state = 0;
      this.deploymentModuleType = 0;
      this.deploymentModuleName = null;
      this.rgInvocableMonitor = null;
      this.dscope = null;
      frontEnd.getService();
      this.factoryId = JMSService.getNextId();
      this.frontEnd = frontEnd;
      this.name = name;
      this.jndiName = StringUtils.isEmptyString(jndiName) ? "" : jndiName;
      this.setupDefaultDeliveryParams();
      this.setupDefaultClientParams();
      this.setupDefaultTransactionParams();
      this.setupDefaultFlowControlParams();
      this.setupDefaultLoadBalancingParams();
      this.setupDefaultSecurityParams();
      this.xaConnectionFactoryEnabled = xaConnectionFactoryEnabled;
      this.allowCloseInOnMessage = allowCloseInOnMessage;
      this.acknowledgePolicy = acknowledgePolicy;
      this.serverAffinityEnabled = serverAffinityEnabled;
      this.loadBalancingEnabled = loadBalancingEnabled;
      this.setFullyQualifiedName((String)null);
      this.defaultConnectionFactory = true;
   }

   public FEConnectionFactory(FrontEnd frontEnd, JMSConnectionFactoryBean cfBean, String paramEARModuleName, String moduleName, Context paramEARNamingContext, boolean isJMSResourceDefinition, ApplicationContext appCtx) {
      this.connectionFactoryBean = null;
      this.defaultConnectionFactory = false;
      this.jndiName = "";
      this.localJNDIName = "";
      this.jndiNameRebindLock = new Object();
      this.compressionThreshold = Integer.MAX_VALUE;
      this.loadBalancingEnabled = true;
      this.serverAffinityEnabled = true;
      this.producerLoadBalancingPolicy = JMSConstants.PRODUCER_LB_POLICY_DEFAULT;
      this.attachJMSXUserID = false;
      this.synchronousPrefetchMode = "disabled";
      this.oneWaySendMode = "disabled";
      this.oneWaySendWindowSize = 1;
      this.reconnectPolicy = JMSConstants.RECONNECT_POLICY_PRODUCER;
      this.reconnectBlockingMillis = 60000L;
      this.totalReconnectPeriodMillis = -1L;
      this.state = 0;
      this.deploymentModuleType = 0;
      this.deploymentModuleName = null;
      this.rgInvocableMonitor = null;
      this.dscope = null;
      frontEnd.getService();
      this.factoryId = JMSService.getNextId();
      this.frontEnd = frontEnd;
      this.EARModuleName = paramEARModuleName;
      this.moduleName = moduleName;
      this.connectionFactoryBean = cfBean;
      this.name = JMSBeanHelper.getDecoratedName(moduleName, cfBean.getName());
      this.EARNamingContext = paramEARNamingContext;
      this.isJMSResourceDefinition = isJMSResourceDefinition;
      this.initDeploymentModuleTypeName(appCtx);
      this.dscope = JMSModuleHelper.getDeploymentScope(((ApplicationContextInternal)appCtx).getBasicDeploymentMBean());
      if (this.isInResourceGroup((ResourceGroupMBean)null)) {
         this.rgInvocableMonitor = new InvocableMonitor(frontEnd.getInvocableMonitor());
      }

   }

   private void initDeploymentModuleTypeName(ApplicationContext appCtx) {
      this.deploymentModuleType = 0;
      this.deploymentModuleName = null;
      ApplicationContextInternal appctx = (ApplicationContextInternal)appCtx;
      if (appctx.getAppDeploymentMBean() != null) {
         this.deploymentModuleName = appctx.getAppDeploymentMBean().getName();
      } else {
         SystemResourceMBean sysres = appctx.getSystemResourceMBean();
         this.deploymentModuleType = 1;
         this.deploymentModuleName = appctx.getSystemResourceMBean().getName();
      }

   }

   int getDeploymentModuleType() {
      return this.deploymentModuleType;
   }

   String getDeploymentModuleName() {
      return this.deploymentModuleName;
   }

   private void setFullyQualifiedName(String paramFullyQualifiedName) {
      this.fullyQualifiedName = paramFullyQualifiedName;
   }

   private JMSConnectionFactory computeJMSConnectionFactory() {
      return (JMSConnectionFactory)(this.xaConnectionFactoryEnabled ? new JMSXAConnectionFactory(new FEConnectionFactoryImpl(this), this.fullyQualifiedName, this.frontEnd.getService().getPartitionName(), JMSOBSHelper.convertSecurityPolicyToInt(this.securityPolicy)) : new JMSConnectionFactory(new FEConnectionFactoryImpl(this), this.fullyQualifiedName, this.frontEnd.getService().getPartitionName(), JMSOBSHelper.convertSecurityPolicyToInt(this.securityPolicy)));
   }

   public void setupDefaultDeliveryParams() {
      this.deliveryMode = "Persistent";
      this.priority = 4;
      this.timeToDeliver = "0";
      this.timeToLive = 0L;
      this.sendTimeout = 10L;
      this.redeliveryDelay = 0L;
   }

   public void setupDefaultClientParams() {
      this.messagesMaximum = 10;
      this.overrunPolicy = "KeepOld";
      this.clientIdPolicy = "Restricted";
      this.subscriptionSharingPolicy = weblogic.management.configuration.JMSConstants.SUBSCRIPTION_EXCLUSIVE;
   }

   public void setupDefaultTransactionParams() {
      this.transactionTimeout = 3600L;
      this.xaConnectionFactoryEnabled = false;
   }

   public void setupDefaultFlowControlParams() {
      this.flowControlEnabled = true;
      this.flowMinimum = 50;
      this.flowMaximum = 500;
      this.flowInterval = 60;
      this.flowSteps = 10;
      this.oneWaySendMode = "disabled";
      this.oneWaySendWindowSize = 1;
   }

   public void setupDefaultLoadBalancingParams() {
      this.loadBalancingEnabled = true;
      this.serverAffinityEnabled = true;
      this.producerLoadBalancingPolicy = JMSConstants.PRODUCER_LB_POLICY_DEFAULT;
   }

   public void setupDefaultSecurityParams() {
      this.attachJMSXUserID = false;
      this.securityPolicy = "ThreadBased";
   }

   private void initialize() throws ModuleException {
      try {
         this.initializeBeanUpdateListeners();
      } catch (ManagementException var3) {
         throw new ModuleException(var3.getMessage(), var3);
      }

      try {
         this.valJNDIName(this.jndiName);
         this.valLocalJNDIName(this.localJNDIName);
      } catch (BeanUpdateRejectedException var2) {
         throw new ModuleException(var2.getMessage(), var2);
      }
   }

   JMSConnection connectionCreateInternal(DispatcherWrapper clientDispatcher, boolean allowXAConnection) throws JMSException {
      this.checkShutdownOrSuspended();
      PeerInfo peerInfo = clientDispatcher.getPeerInfo();
      JMSService jmsService = this.frontEnd.getService();
      DispatcherPartitionContext dpc = jmsService.getDispatcherPartitionContext();
      DispatcherWrapper serverDispatcherWrapper = jmsService.createPartitionAwareDispatcherWrapper();
      JMSID connectionId = JMSService.getNextId();
      String name = "connection" + connectionId.getCounter();
      int fDeliveryMode = this.getDefaultDeliveryModeAsInt();
      long fTransactionTimeout = this.transactionTimeout;
      int fmessagesMaximum = this.messagesMaximum;
      String fClientId = this.clientId;

      FEConnection connection;
      try {
         JMSDispatcher normalizedClientDispatcher = dpc.dispatcherAdapterOrPartitionAdapter(clientDispatcher);
         connection = new FEConnection(this, name, connectionId, normalizedClientDispatcher, fDeliveryMode, this.priority, Long.parseLong(this.timeToDeliver), this.timeToLive, this.sendTimeout, this.redeliveryDelay, fClientId, this.getClientIdPolicyAsInt(), getSubscriptionSharingPolicyAsInt(this.subscriptionSharingPolicy), fTransactionTimeout, this.xaConnectionFactoryEnabled, this.allowCloseInOnMessage, fmessagesMaximum, this.getMulticastOverrunPolicyAsInt(), this.getAcknowledgePolicyAsInt(), this.loadBalancingEnabled, getProducerLoadBalancingPolicyAsInt(this.producerLoadBalancingPolicy), this.serverAffinityEnabled, this.defaultUnitOfOrder, this.compressionThreshold);
      } catch (DispatcherException | ManagementException var17) {
         throw new weblogic.jms.common.JMSException("Error creating connection: " + name, var17);
      }

      dpc.getInvocableManagerDelegate().invocableAdd(7, connection);
      return (JMSConnection)(this.xaConnectionFactoryEnabled && allowXAConnection ? new JMSXAConnection(connection.getJMSID(), connection.getConnectionClientId(), this.getClientIdPolicyAsInt(), getSubscriptionSharingPolicyAsInt(this.subscriptionSharingPolicy), connection.getDeliveryMode(), connection.getPriority(), connection.getTimeToDeliver(), connection.getTimeToLive(), connection.getSendTimeout(), connection.getRedeliveryDelay(), connection.getTransactionTimeout(), connection.userTransactionsEnabled(), connection.getAllowCloseInOnMessage(), connection.getMessagesMaximum(), connection.getOverrunPolicy(), connection.getAcknowledgePolicy(), connection.isLocal(), serverDispatcherWrapper, this.flowControlEnabled, this.flowMinimum, this.flowMaximum, this.flowInterval, this.flowSteps, this.defaultUnitOfOrder, connection, ManagementService.getRuntimeAccess(KERNEL_ID).getServerName(), connection.getRuntimeDelegate().getName(), peerInfo, this.compressionThreshold, 0, 0, 1, WLConnectionImpl.validateAndConvertReconnectPolicy(this.reconnectPolicy), this.reconnectBlockingMillis, this.totalReconnectPeriodMillis) : new JMSConnection(connection.getJMSID(), connection.getConnectionClientId(), this.getClientIdPolicyAsInt(), getSubscriptionSharingPolicyAsInt(this.subscriptionSharingPolicy), connection.getDeliveryMode(), connection.getPriority(), connection.getTimeToDeliver(), connection.getTimeToLive(), connection.getSendTimeout(), connection.getRedeliveryDelay(), connection.getTransactionTimeout(), connection.userTransactionsEnabled(), connection.getAllowCloseInOnMessage(), connection.getMessagesMaximum(), connection.getOverrunPolicy(), connection.getAcknowledgePolicy(), connection.isLocal(), serverDispatcherWrapper, this.flowControlEnabled, this.flowMinimum, this.flowMaximum, this.flowInterval, this.flowSteps, this.xaConnectionFactoryEnabled, this.defaultUnitOfOrder, connection, ManagementService.getRuntimeAccess(KERNEL_ID).getServerName(), connection.getRuntimeDelegate().getName(), peerInfo, this.compressionThreshold, JMSConnection.convertPrefetchMode(this.synchronousPrefetchMode), this.frontEnd.getService().getUse81StyleExecuteQueues() ? 0 : JMSConnection.convertOneWaySendMode(this.oneWaySendMode), this.oneWaySendWindowSize, WLConnectionImpl.validateAndConvertReconnectPolicy(this.reconnectPolicy), this.reconnectBlockingMillis, this.totalReconnectPeriodMillis));
   }

   private void globalbind(String globalJndiName) throws JMSException {
      if (!this.bound && !StringUtils.isEmptyString(globalJndiName)) {
         try {
            JMSConnectionFactory connectionFactory = this.computeJMSConnectionFactory();
            PrivilegedActionUtilities.bindAsSU(this.frontEnd.getService().getCtx(true), globalJndiName, connectionFactory, KERNEL_ID);
            this.globalBoundConnectionFactory = connectionFactory;
            this.bound = true;
         } catch (NamingException var3) {
            throw new weblogic.jms.common.JMSException("Error binding connection factory (name = " + this.name + ") to (jndi name = " + globalJndiName + ")", var3);
         }
      }

   }

   private void localbind(String localJNDIName) throws JMSException {
      if (!this.localBound && !StringUtils.isEmptyString(localJNDIName)) {
         try {
            JMSConnectionFactory connectionFactory = this.computeJMSConnectionFactory();
            PrivilegedActionUtilities.bindAsSU(this.frontEnd.getService().getCtx(false), localJNDIName, connectionFactory, KERNEL_ID);
            this.localBound = true;
            this.localBoundConnectionFactory = connectionFactory;
         } catch (NamingException var3) {
            throw new weblogic.jms.common.JMSException("Error binding connection factory (name = " + this.name + ") to (local jndi name = " + localJNDIName + ")", var3);
         }
      }

   }

   private String constructAppscopedJNDIName() {
      return this.EARModuleName == null ? null : this.EARModuleName + "#" + this.getEntityName();
   }

   private void appscopedbind() throws JMSException {
      String appscopedJNDIName = this.constructAppscopedJNDIName();
      if (!this.applicationBound && appscopedJNDIName != null && this.EARNamingContext != null) {
         try {
            JMSConnectionFactory connectionFactory = this.computeJMSConnectionFactory();
            PrivilegedActionUtilities.bindAsSU(this.EARNamingContext, appscopedJNDIName, connectionFactory, KERNEL_ID);
            this.applicationBound = true;
            this.appBoundConnectionFactory = connectionFactory;
         } catch (NamingException var3) {
            throw new weblogic.jms.common.JMSException("Error binding connection factory (name = " + this.name + ") to (application jndi name = " + appscopedJNDIName + ")", var3);
         }
      }
   }

   private void appscopedbind(String jndiName) throws JMSException {
      String portableJNDIName = JMSServerUtilities.getPortableJNDIName(jndiName);

      try {
         JMSConnectionFactory connectionFactory = this.computeJMSConnectionFactory();
         PrivilegedActionUtilities.bindAsSU(this.EARNamingContext, portableJNDIName, connectionFactory, KERNEL_ID);
         this.appBoundConnectionFactory = connectionFactory;
      } catch (NamingException var4) {
         throw new weblogic.jms.common.JMSException("Error binding connection factory (name = " + this.name + ") to (application jndi name = " + jndiName + ")", var4);
      }
   }

   private void globalunbind(String globalJNDIName, String newName) {
      EventContext eventContext = (EventContext)this.frontEnd.getService().getCtx(true);
      if (!StringUtils.isEmptyString(globalJNDIName) && this.bound) {
         if (newName != null) {
            try {
               eventContext.addNamingListener(globalJNDIName, 0, this);
            } catch (NamingException var24) {
               JMSLogger.logErrorEstablishingJNDIListener(this.name, var24.toString());
               return;
            }

            synchronized(this.jndiNameRebindLock) {
               this.didUnbind = false;
            }
         }

         try {
            PrivilegedActionUtilities.unbindAsSU(eventContext, globalJNDIName, KERNEL_ID);
            UnicastRemoteObject.unexportObject(this.globalBoundConnectionFactory, true);
            if (newName != null) {
               synchronized(this.jndiNameRebindLock) {
                  for(int lcv = 0; lcv < 15; ++lcv) {
                     try {
                        this.jndiNameRebindLock.wait(20000L);
                     } catch (InterruptedException var22) {
                     }

                     if (this.didUnbind) {
                        break;
                     }

                     JMSLogger.logInfoWaitForUnbind(this.name, globalJNDIName, newName);
                  }

                  if (!this.didUnbind) {
                     JMSLogger.logErrorWaitForUnbind(this.name, globalJNDIName, newName);
                  }
               }
            }

            this.bound = false;
         } catch (NoSuchObjectException var26) {
         } catch (NamingException var27) {
         } finally {
            if (newName != null) {
               try {
                  eventContext.removeNamingListener(this);
               } catch (NamingException var21) {
                  JMSLogger.logErrorRemovingJNDIListener(this.name, var21.toString());
               }
            }

         }
      }

   }

   private void localunbind(String localJNDIName) {
      if (!StringUtils.isEmptyString(localJNDIName) && this.localBound) {
         try {
            PrivilegedActionUtilities.unbindAsSU(this.frontEnd.getService().getCtx(false), localJNDIName, KERNEL_ID);
            UnicastRemoteObject.unexportObject(this.localBoundConnectionFactory, true);
            this.localBound = false;
         } catch (NoSuchObjectException var3) {
         } catch (NamingException var4) {
         }
      }

   }

   private void appscopedunbind() {
      String appscopedJNDIName = this.constructAppscopedJNDIName();
      if (this.applicationBound && appscopedJNDIName != null && this.EARNamingContext != null) {
         try {
            PrivilegedActionUtilities.unbindAsSU(this.EARNamingContext, appscopedJNDIName, KERNEL_ID);
            UnicastRemoteObject.unexportObject(this.appBoundConnectionFactory, true);
            this.applicationBound = false;
         } catch (NoSuchObjectException var3) {
         } catch (NamingException var4) {
         }

      }
   }

   private void appscopedunbind(String jndiName) {
      try {
         jndiName = JMSServerUtilities.getPortableJNDIName(jndiName);
         PrivilegedActionUtilities.unbindAsSU(this.EARNamingContext, jndiName, KERNEL_ID);
         UnicastRemoteObject.unexportObject(this.appBoundConnectionFactory, true);
      } catch (NoSuchObjectException var3) {
      } catch (NamingException var4) {
      }

   }

   public void bind() throws JMSException {
      if (this.isJMSResourceDefinition) {
         this.appscopedbind(this.jndiName);
      } else {
         this.globalbind(this.jndiName);
         this.localbind(this.localJNDIName);
         this.appscopedbind();
      }

      synchronized(this) {
         this.state = 4;
      }
   }

   public void unbind() {
      if (this.isJMSResourceDefinition) {
         this.appscopedunbind(this.jndiName);
      } else {
         this.globalunbind(this.jndiName, (String)null);
         this.localunbind(this.localJNDIName);
         this.appscopedunbind();
      }
   }

   synchronized void markSuspending() {
      if ((this.state & 27) == 0) {
         this.state = 2;
      }
   }

   void waitForInvocablesCompletionForResourceGroup(boolean force) {
      if (this.rgInvocableMonitor != null) {
         if (force) {
            this.rgInvocableMonitor.forceInvocablesCompletion();
         }

         this.rgInvocableMonitor.waitForInvocablesCompletion();
      }
   }

   public void suspend() {
      synchronized(this) {
         if (this.state == 1) {
            return;
         }

         this.state = 1;
      }

      this.unbind();
   }

   synchronized void markShuttingDown() {
      if ((this.state & 24) == 0) {
         this.state = 8;
      }
   }

   public void shutdown() {
      synchronized(this) {
         if (this.state == 16) {
            return;
         }

         this.state = 16;
      }

      this.unbind();
   }

   private synchronized void checkShutdownOrSuspended() throws JMSException {
      if ((this.state & 27) != 0) {
         throw new weblogic.jms.common.JMSException("JMS server is shutdown or suspended");
      }
   }

   public FrontEnd getFrontEnd() {
      return this.frontEnd;
   }

   public JMSID getId() {
      return this.factoryId;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getName() {
      return this.name;
   }

   public void setDefaultTargetingEnabled(boolean paramDefaultTargetingEnabled) {
      this.defaultTargetingEnabled = paramDefaultTargetingEnabled;
   }

   public boolean isDefaultTargetingEnabled() {
      return this.defaultTargetingEnabled;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void valJNDIName(String proposedJNDIName) throws BeanUpdateRejectedException {
      proposedJNDIName = JMSServerUtilities.transformJNDIName(proposedJNDIName);
      if (!StringUtils.isEmptyString(proposedJNDIName)) {
         if (this.state != 4 || this.jndiName == null || !this.jndiName.equals(proposedJNDIName)) {
            Context context = this.frontEnd.getService().getCtx();

            Object object;
            try {
               object = context.lookup(proposedJNDIName);
            } catch (NameNotFoundException var6) {
               return;
            } catch (NamingException var7) {
               throw new BeanUpdateRejectedException(var7.getMessage(), var7);
            }

            if (!(object instanceof JMSConnectionFactory)) {
               throw new BeanUpdateRejectedException("The proposed JNDI name \"" + proposedJNDIName + "\" for connection factory \"" + this.name + "\" is already bound by another object of type \"" + object.getClass().getName() + "\"");
            } else {
               JMSConnectionFactory proposedJCF = (JMSConnectionFactory)object;
               String localFullyQualifiedName = this.defaultConnectionFactory ? "" : this.name;
               if (!proposedJCF.getFullyQualifiedName().equals(localFullyQualifiedName)) {
                  throw new BeanUpdateRejectedException("The proposed JNDI name \"" + proposedJNDIName + "\" for connection factory \"" + this.name + "\" is already bound by another connection factory \"" + proposedJCF.getFullyQualifiedName() + "\"");
               }
            }
         }
      }
   }

   public void setJNDIName(String proposedJNDIName) throws IllegalArgumentException {
      proposedJNDIName = JMSServerUtilities.transformJNDIName(proposedJNDIName);
      String oldJNDIName = this.jndiName;
      this.jndiName = StringUtils.isEmptyString(proposedJNDIName) ? "" : proposedJNDIName;
      if (this.state == 4) {
         if (this.jndiName == "") {
            if (this.bound) {
               this.globalunbind(oldJNDIName, this.jndiName);
            }

         } else {
            if (oldJNDIName == "" || !this.jndiName.equals(oldJNDIName)) {
               if (this.bound) {
                  this.globalunbind(oldJNDIName, this.jndiName);
               }

               try {
                  this.globalbind(this.jndiName);
               } catch (JMSException var4) {
                  JMSLogger.logErrorBindCF(this.name, var4);
                  this.frontEnd.connectionFactoryRemove(this);
                  throw new IllegalArgumentException("Error binding connection factory name : " + this.name + "to jndi name: " + this.jndiName);
               }
            }

         }
      }
   }

   public String getLocalJNDIName() {
      return this.localJNDIName;
   }

   public void valLocalJNDIName(String proposedLocalJNDIName) throws BeanUpdateRejectedException {
      proposedLocalJNDIName = JMSServerUtilities.transformJNDIName(proposedLocalJNDIName);
      if (!StringUtils.isEmptyString(proposedLocalJNDIName)) {
         if (this.state != 4 || !this.localJNDIName.equals(proposedLocalJNDIName)) {
            Context context = this.frontEnd.getService().getCtx();

            Object object;
            try {
               object = context.lookup(proposedLocalJNDIName);
            } catch (NameNotFoundException var6) {
               return;
            } catch (NamingException var7) {
               throw new BeanUpdateRejectedException(var7.getMessage(), var7);
            }

            if (!(object instanceof JMSConnectionFactory)) {
               throw new BeanUpdateRejectedException("The proposed JNDI name " + proposedLocalJNDIName + " for connection factory " + this.name + " is already bound by another object of type " + (object == null ? "null" : object.getClass().getName()));
            } else {
               JMSConnectionFactory proposedJCF = (JMSConnectionFactory)object;
               String localFullyQualifiedName = this.defaultConnectionFactory ? "" : this.name;
               if (!proposedJCF.getFullyQualifiedName().equals(localFullyQualifiedName)) {
                  throw new BeanUpdateRejectedException("The proposed JNDI name " + proposedLocalJNDIName + " for connection factory " + this.name + " is already bound by another connection factory " + proposedJCF.getFullyQualifiedName());
               }
            }
         }
      }
   }

   public void setLocalJNDIName(String proposedLocalJNDIName) throws IllegalArgumentException {
      proposedLocalJNDIName = JMSServerUtilities.transformJNDIName(proposedLocalJNDIName);
      String oldLocalJNDIName = this.localJNDIName;
      this.localJNDIName = StringUtils.isEmptyString(proposedLocalJNDIName) ? "" : proposedLocalJNDIName;
      if (this.state == 4) {
         if (this.localJNDIName == "") {
            if (this.localBound) {
               this.localunbind(oldLocalJNDIName);
            }

         } else {
            if (oldLocalJNDIName == "" || !this.localJNDIName.equals(oldLocalJNDIName)) {
               if (this.localBound) {
                  this.localunbind(oldLocalJNDIName);
               }

               try {
                  this.localbind(this.localJNDIName);
               } catch (JMSException var4) {
                  JMSLogger.logErrorBindCF(this.name, var4);
                  this.frontEnd.connectionFactoryRemove(this);
                  throw new IllegalArgumentException("Error binding connection factory name : " + this.name + " to local jndi name: " + this.localJNDIName);
               }
            }

         }
      }
   }

   public String getDefaultDeliveryMode() {
      return this.deliveryMode;
   }

   public int getDefaultDeliveryModeAsInt() {
      int deliveryModeInt = 2;
      if (this.deliveryMode != null && !this.deliveryMode.equalsIgnoreCase("No-Delivery")) {
         if (this.deliveryMode.equalsIgnoreCase("Persistent")) {
            deliveryModeInt = 2;
         } else if (this.deliveryMode.equalsIgnoreCase("Non-Persistent")) {
            deliveryModeInt = 1;
         }
      } else {
         deliveryModeInt = 2;
      }

      return deliveryModeInt;
   }

   public void setDefaultDeliveryMode(String deliveryMode) throws IllegalArgumentException {
      if (deliveryMode != null && !deliveryMode.equalsIgnoreCase("No-Delivery")) {
         if (!deliveryMode.equalsIgnoreCase("Persistent") && !deliveryMode.equalsIgnoreCase("Non-Persistent")) {
            throw new IllegalArgumentException("Invalid delivery mode");
         } else {
            this.deliveryMode = deliveryMode;
         }
      } else {
         this.deliveryMode = "Persistent";
      }
   }

   public int getDefaultPriority() {
      return this.priority;
   }

   public void setDefaultPriority(int priority) {
      this.priority = priority;
   }

   public long getDefaultTimeToDeliverAsLong() {
      return Long.parseLong(this.timeToDeliver);
   }

   public String getDefaultTimeToDeliver() {
      return this.timeToDeliver;
   }

   public void setDefaultTimeToDeliver(String timeToDeliver) {
      this.timeToDeliver = timeToDeliver;
   }

   public long getDefaultTimeToLive() {
      return this.timeToLive;
   }

   public void setDefaultTimeToLive(long timeToLive) {
      this.timeToLive = timeToLive;
   }

   public long getDefaultRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   public void setDefaultRedeliveryDelay(long redeliveryDelay) {
      this.redeliveryDelay = redeliveryDelay;
   }

   public int getDefaultCompressionThreshold() {
      return this.compressionThreshold;
   }

   public void setDefaultCompressionThreshold(int threshold) {
      this.compressionThreshold = threshold;
   }

   public void setDefaultUnitOfOrder(String defaultUnitOfOrder) {
      this.defaultUnitOfOrder = defaultUnitOfOrder;
   }

   public String getDefaultUnitOfOrder() {
      return this.defaultUnitOfOrder;
   }

   public String getSynchronousPrefetchMode() {
      return this.synchronousPrefetchMode;
   }

   public void setSynchronousPrefetchMode(String synchronousPrefetchMode) throws IllegalArgumentException {
      if (synchronousPrefetchMode != null && !synchronousPrefetchMode.equals("enabled") && !synchronousPrefetchMode.equals("disabled") && !synchronousPrefetchMode.equals("topicSubscriberOnly")) {
         throw new IllegalArgumentException("Invalid synchronous prefetch mode");
      } else {
         this.synchronousPrefetchMode = synchronousPrefetchMode;
      }
   }

   public String getOneWaySendMode() {
      return this.oneWaySendMode;
   }

   public void setOneWaySendMode(String oneWaySendMode) throws IllegalArgumentException {
      if (oneWaySendMode != null && !oneWaySendMode.equals("enabled") && !oneWaySendMode.equals("disabled") && !oneWaySendMode.equals("topicOnly")) {
         throw new IllegalArgumentException("Invalid one way send mode");
      } else {
         this.oneWaySendMode = oneWaySendMode;
      }
   }

   public int getOneWaySendWindowSize() {
      return this.oneWaySendWindowSize;
   }

   public void setOneWaySendWindowSize(int oneWaySendWindowSize) throws IllegalArgumentException {
      this.oneWaySendWindowSize = oneWaySendWindowSize;
   }

   public String getReconnectPolicy() {
      return this.reconnectPolicy;
   }

   public void setReconnectPolicy(String reconnectPolicy) {
      WLConnectionImpl.validateAndConvertReconnectPolicy(reconnectPolicy);
      this.reconnectPolicy = reconnectPolicy;
   }

   public long getReconnectBlockingMillis() {
      return this.reconnectBlockingMillis;
   }

   public void setReconnectBlockingMillis(long reconnectBlockingMillis) {
      WLConnectionImpl.validateReconnectMillis(reconnectBlockingMillis);
      if (reconnectBlockingMillis == -1L || reconnectBlockingMillis > this.totalReconnectPeriodMillis) {
         reconnectBlockingMillis = this.totalReconnectPeriodMillis;
      }

      this.reconnectBlockingMillis = reconnectBlockingMillis;
   }

   public long getTotalReconnectPeriodMillis() {
      return this.totalReconnectPeriodMillis;
   }

   public void setTotalReconnectPeriodMillis(long totalReconnectPeriodMillis) {
      WLConnectionImpl.validateReconnectMillis(totalReconnectPeriodMillis);
      this.totalReconnectPeriodMillis = totalReconnectPeriodMillis;
   }

   public int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public void setMessagesMaximum(int messagesMaximum) throws IllegalArgumentException {
      if (messagesMaximum >= -1 && messagesMaximum != 0) {
         this.messagesMaximum = messagesMaximum;
      } else {
         throw new IllegalArgumentException("Invalid MessagesMaximum");
      }
   }

   public String getClientId() {
      return this.clientId;
   }

   public void setClientId(String clientId) {
      if (clientId != null && clientId.length() == 0) {
         this.clientId = null;
      } else {
         this.clientId = clientId;
      }
   }

   public String getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public int getClientIdPolicyAsInt() {
      if (this.clientIdPolicy.equals("Restricted")) {
         return 0;
      } else {
         return this.clientIdPolicy.equals("Unrestricted") ? 1 : 0;
      }
   }

   public void setClientIdPolicy(String clientIdPolicy) throws IllegalArgumentException {
      if (clientIdPolicy == null) {
         this.clientIdPolicy = "Restricted";
      } else if (!clientIdPolicy.equals("Restricted") && !clientIdPolicy.equals("Unrestricted")) {
         throw new IllegalArgumentException("Unrecognized ClientIdPolicy " + clientIdPolicy);
      } else {
         this.clientIdPolicy = clientIdPolicy;
      }
   }

   public String getSubscriptionSharingPolicy() {
      return this.subscriptionSharingPolicy;
   }

   public static int getSubscriptionSharingPolicyAsInt(String policy) {
      if (policy.equals(JMSConstants.SUBSCRIPTION_EXCLUSIVE)) {
         return 0;
      } else if (policy.equals(JMSConstants.SUBSCRIPTION_SHARABLE)) {
         return 1;
      } else {
         throw new IllegalArgumentException("Unrecognized SubscriptionSharingPolicy " + policy);
      }
   }

   public static String getSubscriptionSharingPolicyAsString(int policy) {
      switch (policy) {
         case 0:
            return JMSConstants.SUBSCRIPTION_EXCLUSIVE;
         case 1:
            return JMSConstants.SUBSCRIPTION_SHARABLE;
         default:
            throw new IllegalArgumentException("Unrecognized SubscriptionSharingPolicy " + policy);
      }
   }

   public void setSubscriptionSharingPolicy(String subscriptionSharingPolicy) throws IllegalArgumentException {
      if (subscriptionSharingPolicy == null) {
         this.subscriptionSharingPolicy = JMSConstants.SUBSCRIPTION_EXCLUSIVE;
      } else if (!subscriptionSharingPolicy.equals(JMSConstants.SUBSCRIPTION_EXCLUSIVE) && !subscriptionSharingPolicy.equals(JMSConstants.SUBSCRIPTION_SHARABLE)) {
         throw new IllegalArgumentException("Unrecognized Subscription Sharing Policy " + subscriptionSharingPolicy);
      } else {
         this.subscriptionSharingPolicy = subscriptionSharingPolicy;
      }
   }

   public boolean isAllowCloseInOnMessage() {
      return this.allowCloseInOnMessage;
   }

   public void setAllowCloseInOnMessage(boolean allowCloseInOnMessage) {
      this.allowCloseInOnMessage = allowCloseInOnMessage;
   }

   public String getAcknowledgePolicy() {
      return this.acknowledgePolicy;
   }

   public int getAcknowledgePolicyAsInt() {
      int acknowledgePolicyInt = 1;
      if (this.acknowledgePolicy == null) {
         acknowledgePolicyInt = 1;
      } else if (this.acknowledgePolicy.equalsIgnoreCase("All")) {
         acknowledgePolicyInt = 1;
      } else if (this.acknowledgePolicy.equalsIgnoreCase("Previous")) {
         acknowledgePolicyInt = 2;
      } else if (this.acknowledgePolicy.equalsIgnoreCase("One")) {
         acknowledgePolicyInt = 3;
      }

      return acknowledgePolicyInt;
   }

   public void setAcknowledgePolicy(String acknowledgePolicy) throws IllegalArgumentException {
      if (acknowledgePolicy == null) {
         this.acknowledgePolicy = "All";
      } else if (!acknowledgePolicy.equalsIgnoreCase("All") && !acknowledgePolicy.equalsIgnoreCase("Previous") && !acknowledgePolicy.equalsIgnoreCase("One")) {
         throw new IllegalArgumentException("Invalid acknowledgePolicy");
      } else {
         this.acknowledgePolicy = acknowledgePolicy;
      }
   }

   public String getMulticastOverrunPolicy() {
      return this.overrunPolicy;
   }

   public int getMulticastOverrunPolicyAsInt() {
      int overrunPolicyInt = 0;
      if (this.overrunPolicy == null) {
         overrunPolicyInt = 0;
      } else if (this.overrunPolicy.equalsIgnoreCase("KeepOld")) {
         overrunPolicyInt = 0;
      } else if (this.overrunPolicy.equalsIgnoreCase("KeepNew")) {
         overrunPolicyInt = 1;
      }

      return overrunPolicyInt;
   }

   public void setMulticastOverrunPolicy(String overrunPolicy) throws IllegalArgumentException {
      if (overrunPolicy == null) {
         this.overrunPolicy = "KeepOld";
      } else if (!overrunPolicy.equalsIgnoreCase("KeepOld") && !overrunPolicy.equalsIgnoreCase("KeepNew")) {
         throw new IllegalArgumentException("Invalid multicast overrun policy for connection factory");
      } else {
         this.overrunPolicy = overrunPolicy;
      }
   }

   public boolean isXAConnectionFactoryEnabled() {
      return this.xaConnectionFactoryEnabled;
   }

   public void setXAConnectionFactoryEnabled(boolean xaConnectionFactoryEnabled) {
      this.xaConnectionFactoryEnabled = xaConnectionFactoryEnabled;
   }

   public long getTransactionTimeout() {
      return this.transactionTimeout;
   }

   public void setTransactionTimeout(long transactionTimeout) throws IllegalArgumentException {
      if (transactionTimeout >= 0L && transactionTimeout <= 2147483647L) {
         this.transactionTimeout = transactionTimeout;
      } else {
         throw new IllegalArgumentException("Invalid TransactionTimeout");
      }
   }

   public boolean isFlowControlEnabled() {
      return this.flowControlEnabled;
   }

   public void setFlowControlEnabled(boolean flowControlEnabled) {
      this.flowControlEnabled = flowControlEnabled;
   }

   public int getFlowInterval() {
      return this.flowInterval;
   }

   public void setFlowInterval(int flowInterval) {
      this.flowInterval = flowInterval;
   }

   public int getFlowMaximum() {
      return this.flowMaximum;
   }

   public void setFlowMaximum(int flowMaximum) throws IllegalArgumentException {
      this.flowMaximum = flowMaximum;
   }

   public int getFlowMinimum() {
      return this.flowMinimum;
   }

   public void setFlowMinimum(int flowMinimum) throws IllegalArgumentException {
      this.flowMinimum = flowMinimum;
   }

   public int getFlowSteps() {
      return this.flowSteps;
   }

   public void setFlowSteps(int flowSteps) {
      this.flowSteps = flowSteps;
   }

   public boolean isLoadBalancingEnabled() {
      return this.loadBalancingEnabled;
   }

   public void setLoadBalancingEnabled(boolean loadBalancingEnabled) {
      this.loadBalancingEnabled = loadBalancingEnabled;
   }

   public boolean isServerAffinityEnabled() {
      return this.serverAffinityEnabled;
   }

   public void setServerAffinityEnabled(boolean serverAffinityEnabled) {
      this.serverAffinityEnabled = serverAffinityEnabled;
   }

   public String getProducerLoadBalancingPolicy() {
      return this.producerLoadBalancingPolicy;
   }

   public static int getProducerLoadBalancingPolicyAsInt(String policy) {
      if (policy.equals(JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER)) {
         return 0;
      } else if (policy.equals(JMSConstants.PRODUCER_LB_POLICY_PER_JVM)) {
         return 1;
      } else {
         throw new IllegalArgumentException("Unrecognized ProducerLoadBalancingPolicy " + policy);
      }
   }

   public static String getProducerLoadBalancingPolicyAsString(int policy) {
      switch (policy) {
         case 0:
            return JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER;
         case 1:
            return JMSConstants.PRODUCER_LB_POLICY_PER_JVM;
         default:
            throw new IllegalArgumentException("Unrecognized ProducerLoadBalancingPolicy " + policy);
      }
   }

   public void setProducerLoadBalancingPolicy(String policy) throws IllegalArgumentException {
      if (policy == null) {
         this.producerLoadBalancingPolicy = JMSConstants.PRODUCER_LB_POLICY_DEFAULT;
      } else if (!policy.equals(JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER) && !policy.equals(JMSConstants.PRODUCER_LB_POLICY_PER_JVM)) {
         throw new IllegalArgumentException("Unrecognized ProducerLoadBalancingPolicy " + policy);
      } else {
         this.producerLoadBalancingPolicy = policy;
      }
   }

   public void setAttachJMSXUserId(boolean attachJMSXUserID) {
      this.attachJMSXUserID = attachJMSXUserID;
   }

   public boolean isAttachJMSXUserId() {
      return this.attachJMSXUserID;
   }

   public void setSecurityPolicy(String securityPolicy) {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("FEConnectionFactory:setSecurityPolicy securityPolicy=" + securityPolicy + ", this.securityPolicy=" + this.securityPolicy + ", bound=" + this.bound + ", globalBoundConnectionFactory=" + this.globalBoundConnectionFactory + ", localBound=" + this.localBound + ", localBoundConnectionFactory=" + this.localBoundConnectionFactory + ", applicationBound=" + this.applicationBound + ", appBoundConnectionFactory=" + this.appBoundConnectionFactory);
      }

      if (this.globalBoundConnectionFactory != null && this.bound) {
         this.globalBoundConnectionFactory.setSecurityPolicy(securityPolicy);

         try {
            PrivilegedActionUtilities.rebindAsSU(this.frontEnd.getService().getCtx(true), this.jndiName, this.globalBoundConnectionFactory, KERNEL_ID);
         } catch (NamingException var7) {
            this.globalBoundConnectionFactory.setSecurityPolicy(this.securityPolicy);
            throw new IllegalArgumentException("Error binding connection factory (name = " + this.name + ") to (jndi name = " + this.jndiName + ")", var7);
         }
      }

      if (this.localBoundConnectionFactory != null && this.localBound) {
         this.localBoundConnectionFactory.setSecurityPolicy(securityPolicy);

         try {
            PrivilegedActionUtilities.rebindAsSU(this.frontEnd.getService().getCtx(false), this.localJNDIName, this.localBoundConnectionFactory, KERNEL_ID);
         } catch (NamingException var6) {
            this.localBoundConnectionFactory.setSecurityPolicy(this.securityPolicy);
            throw new IllegalArgumentException("Error binding connection factory (name = " + this.name + ") to (local jndi name = " + this.localJNDIName + ")", var6);
         }
      }

      if (this.appBoundConnectionFactory != null && this.applicationBound) {
         this.appBoundConnectionFactory.setSecurityPolicy(securityPolicy);
         String appscopedJNDIName;
         if (this.isJMSResourceDefinition) {
            try {
               appscopedJNDIName = JMSServerUtilities.getPortableJNDIName(this.jndiName);
               PrivilegedActionUtilities.rebindAsSU(this.EARNamingContext, appscopedJNDIName, this.appBoundConnectionFactory, KERNEL_ID);
            } catch (NamingException var5) {
               this.appBoundConnectionFactory.setSecurityPolicy(this.securityPolicy);
               throw new IllegalArgumentException("Error binding connection factory (name = " + this.name + ") to (application jndi name = " + this.jndiName + ")", var5);
            }
         } else {
            appscopedJNDIName = this.constructAppscopedJNDIName();

            try {
               PrivilegedActionUtilities.rebindAsSU(this.EARNamingContext, appscopedJNDIName, this.appBoundConnectionFactory, KERNEL_ID);
            } catch (NamingException var4) {
               this.appBoundConnectionFactory.setSecurityPolicy(this.securityPolicy);
               throw new IllegalArgumentException("Error binding connection factory (name = " + this.name + ") to (application jndi name = " + appscopedJNDIName + ")", var4);
            }
         }
      }

      this.securityPolicy = securityPolicy;
   }

   public String getSecurityPolicy() {
      return this.securityPolicy;
   }

   public long getSendTimeout() {
      return this.sendTimeout;
   }

   public void setSendTimeout(long sendTimeout) {
      this.sendTimeout = sendTimeout;
   }

   public JMSConnectionFactory getJMSConnectionFactory() {
      if (this.globalBoundConnectionFactory != null) {
         return this.globalBoundConnectionFactory;
      } else if (this.localBoundConnectionFactory != null) {
         return this.localBoundConnectionFactory;
      } else {
         return this.appBoundConnectionFactory != null ? this.appBoundConnectionFactory : this.computeJMSConnectionFactory();
      }
   }

   public void prepare() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Preparing connection factory: " + this.name);
      }

      if ("WebLogic_Debug_CON_fail_prepare".equals(this.connectionFactoryBean.getName())) {
         throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_prepare will force the prepare to fail");
      } else {
         try {
            this.frontEnd.getService().ensureInitialized();
         } catch (JMSException var3) {
            throw new ModuleException("JMS Service is not initialized", var3);
         }

         if (this.frontEnd.connectionFactoryFind(this.name) != null) {
            throw new ModuleException("ConnectionFactory " + this.name + " already exists");
         } else {
            try {
               this.initialize();
               this.frontEnd.connectionFactoryAdd(this);
            } catch (Exception var2) {
               JMSLogger.logErrorCreateCF(this.name, var2);
               throw new ModuleException("Error preparing connection factory " + this.name, var2);
            }

            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Successfully prepared connection factory: " + this.name);
            }

         }
      }
   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Activating connection factory " + this.name);
      }

      if ("WebLogic_Debug_CON_fail_activate".equals(this.connectionFactoryBean.getName())) {
         throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_activate will force the activate to fail");
      } else {
         this.connectionFactoryBean = paramWholeModule.lookupConnectionFactory(this.getEntityName());
         this.unregisterBeanUpdateListeners();
         this.registerBeanUpdateListeners();
         if (this.frontEnd.getService().isActive()) {
            try {
               this.setFullyQualifiedName(this.name);
               this.bind();
               JMSLogger.logCFactoryDeployed(this.name);
            } catch (JMSException var3) {
               JMSLogger.logErrorBindCF(this.name, var3);
               throw new ModuleException("Error binding connection factory " + this.name, var3);
            }
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("Successfully activated connection factory: " + this.name);
         }

      }
   }

   public void deactivate() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("De-activating connection factory: " + this.name);
      }

      try {
         this.frontEnd.getService().ensureInitialized();
      } catch (JMSException var3) {
         throw new ModuleException("JMS Service is not initialized", var3);
      }

      FEConnectionFactory connectionFactory = this.frontEnd.connectionFactoryFind(this.name);
      if (connectionFactory == null) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("Error de-activating ConnectionFactory " + this.name + ": instance not found");
         }

         throw new ModuleException("Error de-activating a non-existent connection factory " + this.name + "(jndi: " + this.jndiName + ")");
      } else {
         try {
            this.unregisterBeanUpdateListeners();
            connectionFactory.unbind();
         } catch (Exception var4) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Error de-activating ConnectionFactory " + this + ": not currently in ACTIVATED state");
            }

            throw new ModuleException("Error de-activating" + this.name + "(jndi: " + this.jndiName + ") not currently in ACTIVATED state");
         }

         if ("WebLogic_Debug_CON_fail_deactivate".equals(this.connectionFactoryBean.getName())) {
            throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_deactivate will force the deactivate to fail");
         } else {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Successfully de-activated connection factory: " + this.name);
            }

         }
      }
   }

   public void unprepare() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Un-preparing connection factory: " + this.name + ", jndiName=" + this.jndiName + ", localJNDIName=" + this.localJNDIName);
      }

      try {
         this.frontEnd.getService().ensureInitialized();
      } catch (JMSException var4) {
         throw new ModuleException("JMS Service is not initialized" + var4);
      }

      FEConnectionFactory connectionFactory = this.frontEnd.connectionFactoryFind(this.name);
      if (connectionFactory == null) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("Error un-preparing ConnectionFactory " + this.name + ": instance not found");
         }

         throw new ModuleException("Error un-preparing a non-existent connection factory " + this.name + "(jndi: " + this.jndiName + ")");
      } else {
         try {
            this.frontEnd.connectionFactoryRemove(this);
         } catch (Exception var3) {
            JMSLogger.logErrorCreateCF(this.name, var3);
            throw new ModuleException("Error un-preparing connection factory " + this.name, var3);
         }

         if ("WebLogic_Debug_CON_fail_unprepare".equals(this.connectionFactoryBean.getName())) {
            throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_unprepare will force the unprepare to fail");
         } else {
            if (this.jndiName != null && this.jndiName != "") {
               JMSService jmsService = this.frontEnd.getService();
               jmsService.fireConnectionFactoryRemovalToListener(this.jndiName, this.dscope);
            }

            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Successfully un-prepared connection factory: " + this.name);
            }

         }
      }
   }

   public void destroy() throws ModuleException {
      if ("WebLogic_Debug_CON_fail_destroy".equals(this.connectionFactoryBean.getName())) {
         throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_destroy will force the destroy to fail");
      }
   }

   public String getEntityName() {
      return this.connectionFactoryBean != null ? this.connectionFactoryBean.getName() : null;
   }

   JMSConnectionFactoryBean getConnectionFactoryBean() {
      return this.connectionFactoryBean;
   }

   InvocableMonitor getInvocableMonitor() {
      return this.rgInvocableMonitor != null ? this.rgInvocableMonitor : this.frontEnd.getInvocableMonitor();
   }

   boolean isInResourceGroup(ResourceGroupMBean rgmbean) {
      if (this.isDefaultConnectionFactory()) {
         return false;
      } else if (rgmbean != null) {
         return JMSModuleHelper.isScopeEqual(this.dscope, rgmbean);
      } else {
         return this.dscope instanceof ResourceGroupMBean || this.dscope instanceof ResourceGroupTemplateMBean;
      }
   }

   private void initializeBeanUpdateListeners() throws ManagementException {
      DescriptorBean descriptor = (DescriptorBean)this.connectionFactoryBean;
      this.connectionFactoryBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.connectionFactoryBeanSignatures);
      this.connectionFactoryBeanListener.initialize();
      descriptor = (DescriptorBean)this.connectionFactoryBean.getDefaultDeliveryParams();
      this.defaultDeliveryParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.defaultDeliveryParamsBeanSignatures);
      this.defaultDeliveryParamsBeanListener.initialize();
      descriptor = (DescriptorBean)this.connectionFactoryBean.getClientParams();
      this.clientParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.clientParamsBeanSignatures);
      this.clientParamsBeanListener.initialize();
      descriptor = (DescriptorBean)this.connectionFactoryBean.getTransactionParams();
      this.transactionParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.transactionParamsBeanSignatures);
      this.transactionParamsBeanListener.initialize();
      descriptor = (DescriptorBean)this.connectionFactoryBean.getFlowControlParams();
      this.flowControlParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.flowControlParamsBeanSignatures);
      this.flowControlParamsBeanListener.initialize();
      descriptor = (DescriptorBean)this.connectionFactoryBean.getLoadBalancingParams();
      this.loadBalancingParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.loadBalancingParamsBeanSignatures);
      this.loadBalancingParamsBeanListener.initialize();
      descriptor = (DescriptorBean)this.connectionFactoryBean.getSecurityParams();
      this.securityParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.securityParamsBeanSignatures);
      this.securityParamsBeanListener.initialize();
   }

   public void registerBeanUpdateListeners() {
      DescriptorBean descriptor;
      if (this.connectionFactoryBeanListener != null) {
         this.connectionFactoryBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean;
         this.connectionFactoryBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.connectionFactoryBeanSignatures);
      }

      if (this.defaultDeliveryParamsBeanListener != null) {
         this.defaultDeliveryParamsBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean.getDefaultDeliveryParams();
         this.defaultDeliveryParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.defaultDeliveryParamsBeanSignatures);
      }

      if (this.clientParamsBeanListener != null) {
         this.clientParamsBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean.getClientParams();
         this.clientParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.clientParamsBeanSignatures);
      }

      if (this.transactionParamsBeanListener != null) {
         this.transactionParamsBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean.getTransactionParams();
         this.transactionParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.transactionParamsBeanSignatures);
      }

      if (this.flowControlParamsBeanListener != null) {
         this.flowControlParamsBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean.getFlowControlParams();
         this.flowControlParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.flowControlParamsBeanSignatures);
      }

      if (this.loadBalancingParamsBeanListener != null) {
         this.loadBalancingParamsBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean.getLoadBalancingParams();
         this.loadBalancingParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.loadBalancingParamsBeanSignatures);
      }

      if (this.securityParamsBeanListener != null) {
         this.securityParamsBeanListener.open();
      } else {
         descriptor = (DescriptorBean)this.connectionFactoryBean.getSecurityParams();
         this.securityParamsBeanListener = new GenericBeanListener(descriptor, this, BeanHelper.securityParamsBeanSignatures);
      }

   }

   public void unregisterBeanUpdateListeners() {
      if (this.securityParamsBeanListener != null) {
         this.securityParamsBeanListener.close();
         this.securityParamsBeanListener = null;
      }

      if (this.loadBalancingParamsBeanListener != null) {
         this.loadBalancingParamsBeanListener.close();
         this.loadBalancingParamsBeanListener = null;
      }

      if (this.flowControlParamsBeanListener != null) {
         this.flowControlParamsBeanListener.close();
         this.flowControlParamsBeanListener = null;
      }

      if (this.transactionParamsBeanListener != null) {
         this.transactionParamsBeanListener.close();
         this.transactionParamsBeanListener = null;
      }

      if (this.clientParamsBeanListener != null) {
         this.clientParamsBeanListener.close();
         this.clientParamsBeanListener = null;
      }

      if (this.defaultDeliveryParamsBeanListener != null) {
         this.defaultDeliveryParamsBeanListener.close();
         this.defaultDeliveryParamsBeanListener = null;
      }

      if (this.connectionFactoryBeanListener != null) {
         this.connectionFactoryBeanListener.close();
         this.connectionFactoryBeanListener = null;
      }

   }

   public boolean isDefaultConnectionFactory() {
      return this.defaultConnectionFactory;
   }

   public void objectChanged(NamingEvent evt) {
      if (evt.getType() == 1) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("Got the unadvertise for " + this.name + " jndiName=" + this.jndiName);
         }

         synchronized(this.jndiNameRebindLock) {
            this.didUnbind = true;
            this.jndiNameRebindLock.notify();
         }
      }

   }

   public void namingExceptionThrown(NamingExceptionEvent evt) {
      JMSLogger.logJNDIDynamicChangeException(this.name, evt.getException().toString());
      synchronized(this.jndiNameRebindLock) {
         this.jndiNameRebindLock.notify();
      }
   }
}
