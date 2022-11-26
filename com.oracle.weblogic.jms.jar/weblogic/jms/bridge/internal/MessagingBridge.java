package weblogic.jms.bridge.internal;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.jms.BytesMessage;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import javax.transaction.SystemException;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.integration.DiagnosticIntegrationManager;
import weblogic.diagnostics.integration.DiagnosticIntegrationManager.Factory;
import weblogic.jms.BridgeLogger;
import weblogic.jms.PartitionBridgeService;
import weblogic.jms.bridge.AdapterConnection;
import weblogic.jms.bridge.AdapterConnectionFactory;
import weblogic.jms.bridge.AdapterMetaData;
import weblogic.jms.bridge.ConnectionSpec;
import weblogic.jms.bridge.LocalTransaction;
import weblogic.jms.bridge.ResourceTransactionRolledBackException;
import weblogic.jms.bridge.SourceConnection;
import weblogic.jms.bridge.TargetConnection;
import weblogic.jms.common.MessageImpl;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.BridgeDestinationCommonMBean;
import weblogic.management.configuration.BridgeDestinationMBean;
import weblogic.management.configuration.BridgeLegalHelper;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.MessagingBridgeRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.common.PartitionNameUtils;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManager;

public final class MessagingBridge extends RuntimeMBeanDelegate implements Runnable, MessageListener, ExceptionListener, MessagingBridgeRuntimeMBean, TimerListener {
   static final long serialVersionUID = 289399667450808114L;
   private static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private static final String OBS_FACTORY = "weblogic.jms.WLInitialContextFactory";
   private static final int STATE_INITIALIZING = 0;
   private static final int STATE_INITIALIZED = 1;
   private static final int STATE_STARTED = 2;
   private static final int STATE_CONNECTED = 3;
   private static final int STATE_RUNNING = 4;
   private static final int STATE_CONTINUE = 5;
   private static final int STATE_STOPPING = 6;
   private static final int STATE_STOPPED = 7;
   private static final int STATE_TOBESTARTED = 8;
   private static final int STATE_RESTARTING = 9;
   private static final int STATE_SHUTTING_DOWN = 10;
   private static final int STATE_CLOSED = 11;
   private static final int STATE_SUSPENDED = 12;
   private final Object stateLock = new Object();
   private static final int TRANSACTION_MODE_NONE = 1;
   private static final int TRANSACTION_MODE_LOCAL = 2;
   private static final int TRANSACTION_MODE_XARESOURCE = 3;
   private static final int QOS_EXACTLY_ONCE = 0;
   private static final int QOS_DUP_OKAY = 1;
   private static final int QOS_ATMOST_ONCE = 2;
   private static final int POLICY_AUTO = 0;
   private static final int POLICY_SCHEDULED = 1;
   private static final int POLICY_MANUAL = 2;
   private static final int MAX_BATCHES_TO_PROCESS = 10;
   private static final int SCANUNIT_INTERNAL = 1000;
   private static final int AUTO_ACK_IGNORE_XA = 99;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String targetAdapterJndi;
   private Properties sourceProps;
   private AdapterMetaData sourceMetaData;
   private String sourceAdapterJndi;
   private Properties targetProps;
   private AdapterMetaData targetMetaData;
   private AdapterConnectionFactory sourceAdapterFactory;
   private ConnectionSpec sourceConnSpec;
   private SourceConnection sourceConn;
   private AdapterConnectionFactory targetAdapterFactory;
   private TargetConnection targetConn;
   private ConnectionSpec targetConnSpec;
   private MessagingBridgeMBean mbean;
   private int qos;
   private int forwardingPolicy;
   private boolean qosDegradAllowed;
   private boolean asyncEnabled = true;
   private boolean durabilityEnabled = true;
   private int transactionTimeout;
   private long maximumIdleTime;
   private String selector;
   private String scheduleTime;
   private boolean preserveMsgProperty;
   private String fullName;
   private int batchSize;
   private long batchInterval;
   private boolean idle;
   private boolean async;
   private boolean xaSupported;
   private boolean sourceXASupported;
   private boolean localTXSupported;
   private boolean stopped;
   private static final int EXACTLY_ONCE = 4;
   private static final int DUPLICATE_OKAY_LOCAL_TX = 3;
   private static final int DUPLICATE_OKAY_ACK = 2;
   private static final int DUPLICATE_OKAY_XA = 1;
   private static final int ATMOST_ONCE = 0;
   private int workMode;
   private RetryTimeController retryController;
   private boolean running;
   private boolean logBeginForwarding;
   private WorkManager workManager;
   private TransactionManager tm;
   private PartitionBridgeService partitionBridgeService;
   private static final long RELOOKUP_ADAPTERS_MILLISECONDS = 10000L;
   private long scanUnit = 1000L;
   private long lookupRetryTimeCurrent;
   private long connRetryTimeCurrent;
   private long connRetryTimeNext;
   private long onMessageIdleCurrent;
   private int logCount;
   private long flushingTime;
   private int state;
   private boolean sameMessageFormat;
   int health = 0;
   private ArrayList reasons = new ArrayList();
   private String[] stateCache = new String[2];
   private ClassLoader savedClassLoader;
   private static final String EOL = getEOL();
   private static final HashMap bridgeSignatures = new HashMap();
   private TimerManager timerManager;
   private Timer timer;
   private boolean lookupAdapterRetry = false;
   private WLDFServerDiagnosticMBean diagnosticMBean;
   GenericBeanListener bridgeListener;
   private DiagnosticIntegrationManager integrationManager = this.getDiagnosticIntegrationManager();

   public MessagingBridge(GenericManagedDeployment deployment, PartitionBridgeService partitionBridgeService) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(deployment.getName()));
      this.fullName = deployment.getName();
      this.mbean = (MessagingBridgeMBean)deployment.getMBean();
      this.partitionBridgeService = partitionBridgeService;
      this.state = 0;
      TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
      this.timerManager = tmf.getDefaultTimerManager();
      this.retryController = new RetryTimeController();
      this.logBeginForwarding = true;
      this.health = 0;
      this.reasons.add(0, "Created.");
      if (this.parent instanceof PartitionRuntimeMBean) {
         ((PartitionRuntimeMBean)this.parent).addMessagingBridgeRuntime(this);
      } else {
         ManagementService.getRuntimeAccess(kernelId).getServerRuntime().addMessagingBridgeRuntime(this);
      }

      this.diagnosticMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getServerDiagnosticConfig();
      Thread currentThread = Thread.currentThread();
      this.savedClassLoader = currentThread.getContextClassLoader();
   }

   public void initialize() throws MessagingBridgeException {
      BridgeDestinationCommonMBean source = this.mbean.getSourceDestination();
      String sourceICF = "weblogic.jndi.WLInitialContextFactory";
      String targetICF = "weblogic.jndi.WLInitialContextFactory";
      if (source == null) {
         BridgeLogger.logErrorNoSource(this.name);
         throw new MessagingBridgeConfigurationException("A messaging bridge must have a source destination configured");
      } else {
         this.sourceAdapterJndi = this.getInternalJNDIName("Source", PartitionNameUtils.stripDecoratedPartitionName(source.getName()), source.getAdapterJNDIName());
         if (!(source instanceof JMSBridgeDestinationMBean)) {
            this.sourceProps = this.copyProperties(((BridgeDestinationMBean)source).getProperties());
         } else {
            JMSBridgeDestinationMBean s = (JMSBridgeDestinationMBean)source;
            sourceICF = s.getInitialContextFactory();
            if ((s.getConnectionURL() == null || s.getConnectionURL().length() == 0) && !sourceICF.equals("weblogic.jndi.WLInitialContextFactory") && !sourceICF.equals("weblogic.jms.WLInitialContextFactory")) {
               BridgeLogger.logErrorInvalidURL(s.getName());
               throw new MessagingBridgeConfigurationException("Connection URL of target destination cannot be null when the bridge talks to third-party JMS providers directly.");
            }

            if (s.getConnectionFactoryJNDIName() == null || s.getDestinationJNDIName() == null) {
               BridgeLogger.logErrorNeedsJNDINames(s.getName());
               throw new MessagingBridgeConfigurationException("A bridge destination for JMS has to have a connection factory JNDI name and a destination JNDI name configured.");
            }

            this.sourceProps = BridgeLegalHelper.createProperties(((JMSBridgeDestinationMBean)source).getConnectionURL(), ((JMSBridgeDestinationMBean)source).getInitialContextFactory(), ((JMSBridgeDestinationMBean)source).getConnectionFactoryJNDIName(), ((JMSBridgeDestinationMBean)source).getDestinationJNDIName(), ((JMSBridgeDestinationMBean)source).getDestinationType());
         }

         if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
            String propsStr = new String();
            propsStr = propsStr + EOL + "  AdapterJNDIName=" + this.sourceAdapterJndi;
            propsStr = propsStr + EOL + "  Classpath=" + source.getClasspath();

            String key;
            for(Enumeration enmt = this.sourceProps.propertyNames(); enmt.hasMoreElements(); propsStr = propsStr + EOL + "  " + key + " = " + this.sourceProps.get(key)) {
               key = (String)enmt.nextElement();
            }

            BridgeDebug.MessagingBridgeStartup.debug("Bridge " + this.name + "'s source configurations are:" + propsStr + EOL);
         }

         BridgeDestinationCommonMBean target = this.mbean.getTargetDestination();
         if (target == null) {
            BridgeLogger.logErrorNoTarget(this.name);
            throw new MessagingBridgeConfigurationException("A messaging bridge must have a target destination configured");
         } else {
            this.targetAdapterJndi = this.getInternalJNDIName("Target", PartitionNameUtils.stripDecoratedPartitionName(target.getName()), target.getAdapterJNDIName());
            if (!(target instanceof JMSBridgeDestinationMBean)) {
               this.targetProps = this.copyProperties(((BridgeDestinationMBean)target).getProperties());
            } else {
               JMSBridgeDestinationMBean t = (JMSBridgeDestinationMBean)target;
               targetICF = t.getInitialContextFactory();
               if ((t.getConnectionURL() == null || t.getConnectionURL().length() == 0) && !targetICF.equals("weblogic.jndi.WLInitialContextFactory") && !targetICF.equals("weblogic.jms.WLInitialContextFactory")) {
                  BridgeLogger.logErrorInvalidURL(t.getName());
                  throw new MessagingBridgeConfigurationException("Connection URL of target destination cannot be null when the bridge talks to third-party JMS providers directly.");
               }

               if (t.getConnectionFactoryJNDIName() == null || t.getDestinationJNDIName() == null) {
                  BridgeLogger.logErrorNeedsJNDINames(t.getName());
                  throw new MessagingBridgeConfigurationException("A bridge destination for JMS has to have a connection factory  JNDI name and a destination JNDI name configured.");
               }

               this.targetProps = BridgeLegalHelper.createProperties(((JMSBridgeDestinationMBean)target).getConnectionURL(), ((JMSBridgeDestinationMBean)target).getInitialContextFactory(), ((JMSBridgeDestinationMBean)target).getConnectionFactoryJNDIName(), ((JMSBridgeDestinationMBean)target).getDestinationJNDIName(), ((JMSBridgeDestinationMBean)target).getDestinationType());
            }

            if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
               String propsStr = new String();
               propsStr = propsStr + EOL + "  AdapterJNDIName=" + this.targetAdapterJndi;
               propsStr = propsStr + EOL + "  Classpath=" + target.getClasspath();

               String key;
               for(Enumeration enmt = this.targetProps.propertyNames(); enmt.hasMoreElements(); propsStr = propsStr + EOL + "  " + key + " = " + this.targetProps.get(key)) {
                  key = (String)enmt.nextElement();
               }

               BridgeDebug.MessagingBridgeStartup.debug("Bridge " + this.name + "'s target configurations are:" + propsStr + EOL);
            }

            if (sourceICF.equals("weblogic.jms.WLInitialContextFactory")) {
               this.sourceProps.put("InitialContextFactory", "weblogic.jndi.WLInitialContextFactory");
               if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
                  BridgeDebug.MessagingBridgeStartup.debug("The given sourceICF is 'weblogic.jms.WLInitialContextFactory',change to 'weblogic.jms.WLInitialContextFactory' since we restrict OBS in Message Bridge.");
               }
            }

            if (targetICF.equals("weblogic.jms.WLInitialContextFactory")) {
               this.targetProps.put("InitialContextFactory", "weblogic.jndi.WLInitialContextFactory");
               if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
                  BridgeDebug.MessagingBridgeStartup.debug("The given targetICF is 'weblogic.jms.WLInitialContextFactory',change to 'weblogic.jms.WLInitialContextFactory' since we restrict OBS in Message Bridge.");
               }
            }

            if (!BridgeLegalHelper.notSameDestinations(this.sourceProps, this.targetProps)) {
               BridgeLogger.logErrorSameSourceTarget(this.name);
               throw new MessagingBridgeConfigurationException("A messaging bridge's source destination cannot be the same as the target destination.");
            } else {
               this.stopped = !this.mbean.isStarted();
               this.asyncEnabled = this.mbean.isAsyncEnabled();
               this.durabilityEnabled = this.mbean.isDurabilityEnabled();
               long retryMin = (long)this.mbean.getReconnectDelayMinimum() * 1000L;
               long retryMax = (long)this.mbean.getReconnectDelayMaximum() * 1000L;
               long retryInc = (long)this.mbean.getReconnectDelayIncrease() * 1000L;
               this.transactionTimeout = this.mbean.getTransactionTimeout();
               this.maximumIdleTime = (long)this.mbean.getIdleTimeMaximum() * 1000L;
               if (this.transactionTimeout <= 0) {
                  this.transactionTimeout = 1;
               }

               if (retryMin > retryMax) {
                  retryMax = retryMin;
               }

               this.retryController.init(retryMin, retryInc, retryMax);
               this.qosDegradAllowed = this.mbean.isQOSDegradationAllowed();
               this.selector = this.mbean.getSelector();
               this.batchSize = this.mbean.getBatchSize();
               this.batchInterval = this.mbean.getBatchInterval();
               if (this.batchInterval < 0L) {
                  this.flushingTime = (long)(800 * this.transactionTimeout);
               } else {
                  this.flushingTime = this.batchInterval;
               }

               this.scheduleTime = this.mbean.getScheduleTime();
               this.preserveMsgProperty = this.mbean.getPreserveMsgProperty();
               String policyStr = this.mbean.getForwardingPolicy();
               if (policyStr == null) {
                  this.forwardingPolicy = 0;
               } else if (policyStr.equalsIgnoreCase("AUTOMATIC")) {
                  this.forwardingPolicy = 0;
               } else if (policyStr.equalsIgnoreCase("MANUAL")) {
                  this.forwardingPolicy = 2;
               } else if (policyStr.equalsIgnoreCase("SCHEDULED")) {
                  this.forwardingPolicy = 1;
               }

               String qosStr = this.mbean.getQualityOfService();
               if (qosStr == null) {
                  this.qos = 0;
               } else if (qosStr.equalsIgnoreCase("EXACTLY-ONCE")) {
                  this.qos = 0;
               } else if (qosStr.equalsIgnoreCase("DUPLICATE-OKAY")) {
                  this.qos = 1;
               } else if (qosStr.equalsIgnoreCase("ATMOST-ONCE")) {
                  this.qos = 2;
               }

               this.bridgeListener = new GenericBeanListener(this.mbean, this, bridgeSignatures);

               try {
                  this.bridgeListener.initialize();
               } catch (ManagementException var14) {
                  this.throwMessagingBridgeException("Messaging bridge bean update listener initialization failed", var14);
               }

               this.state = 1;
               if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
                  BridgeDebug.MessagingBridgeStartup.debug("Bridge " + this.name + " is successfully initialized");
               }

            }
         }
      }
   }

   public void resume() {
      try {
         this.resume((MessagingBridgeMBean)null);
      } catch (MessagingBridgeException var2) {
      }

   }

   public void resume(MessagingBridgeMBean mbean) throws MessagingBridgeException {
      GenericBeanListener listener = null;
      synchronized(this.stateLock) {
         if (this.state != 1 && this.state != 12) {
            return;
         }

         if (mbean != null) {
            this.mbean = mbean;
            if (this.bridgeListener != null) {
               listener = this.bridgeListener;
               this.bridgeListener = null;
            }
         }
      }

      if (mbean != null) {
         if (listener != null) {
            listener.close();
         }

         this.initialize();
      }

      if (!this.partitionBridgeService.findAdapterAndRegister(this.sourceAdapterJndi, this)) {
         if (this.lookupAdapterRetry) {
            BridgeLogger.logWarningAdapterNotFound(this.name, this.sourceAdapterJndi);
            synchronized(this.stateLock) {
               this.health = 1;
               this.reasons.add(0, "WARN: Failed to find the source adapter.");
            }
         } else {
            this.lookupAdapterRetry = true;
         }

      } else if (!this.partitionBridgeService.findAdapterAndRegister(this.targetAdapterJndi, this)) {
         BridgeLogger.logWarningAdapterNotFound(this.name, this.targetAdapterJndi);
         synchronized(this.stateLock) {
            this.health = 1;
            this.reasons.add(0, "WARN: Failed to find the target adapter.");
         }
      } else {
         synchronized(this.stateLock) {
            if (this.timer != null) {
               this.timer.cancel();
            }

            this.timer = this.timerManager.scheduleAtFixedRate(this, 0L, this.scanUnit);
            this.health = 0;
            this.reasons.add(0, "Found both of the adapters and making the connections");
            if (this.isStopped()) {
               this.health = 1;
               this.reasons.add(0, "WARN: Stopped by administrator.");
               BridgeLogger.logInfoInitiallyStopped(this.name);
               return;
            }

            this.state = 1;
            this.running = true;
         }

         this.scheduleThis();
      }
   }

   private void startInternal() throws MessagingBridgeException {
      boolean failed = false;

      String classPath;
      try {
         Context ctx = null;

         try {
            ctx = getContext();
         } catch (NamingException var14) {
            throw new MessagingBridgeException("Failed to get initial context");
         }

         this.sourceAdapterFactory = (AdapterConnectionFactory)ctx.lookup(this.sourceAdapterJndi);
         this.targetAdapterFactory = (AdapterConnectionFactory)ctx.lookup(this.targetAdapterJndi);

         try {
            ctx.close();
         } catch (Exception var13) {
         }

         this.sourceMetaData = this.sourceAdapterFactory.getMetaData();
         this.targetMetaData = this.targetAdapterFactory.getMetaData();
         this.sameMessageFormat = this.sourceMetaData.getNativeMessageFormat().equals(this.targetMetaData.getNativeMessageFormat());
         classPath = null;
         String sourceTransactionSupport = this.sourceAdapterFactory.getTransactionSupport();
         byte sourceTXMode;
         if (sourceTransactionSupport.equals("XATransaction")) {
            sourceTXMode = 3;
         } else if (sourceTransactionSupport.equals("LocalTransaction")) {
            sourceTXMode = 2;
         } else {
            sourceTXMode = 1;
         }

         String targetTransactionSupport = this.targetAdapterFactory.getTransactionSupport();
         byte targetTXMode;
         if (targetTransactionSupport.equals("XATransaction")) {
            targetTXMode = 3;
         } else if (targetTransactionSupport.equals("LocalTransaction")) {
            targetTXMode = 2;
         } else {
            targetTXMode = 1;
         }

         if (sourceTXMode == 3) {
            this.sourceXASupported = true;
            if (targetTXMode == 3) {
               this.xaSupported = true;
            }
         } else if (sourceTXMode == 2) {
            this.localTXSupported = true;
         }
      } catch (NamingException var16) {
         failed = true;
         if (this.logCount++ == 5) {
            BridgeLogger.logInfoAdaptersLookupFailed(this.name, var16);
            this.logCount = 0;
         }
      } catch (ResourceException var17) {
         BridgeLogger.logErrorFailGetAdpInfo(this.name, var17);
         failed = true;
      } catch (Throwable var18) {
         BridgeLogger.logStackTrace(var18);
         failed = true;
      }

      synchronized(this.stateLock) {
         if (failed) {
            this.health = 1;
            if (this.sourceAdapterFactory == null) {
               this.reasons.add(0, "WARN: failed to look up the source adapter.");
            } else {
               this.reasons.add(0, "WARN: failed to look up the target adapter.");
            }

            return;
         }

         this.health = 0;
         this.reasons.add(0, "Found two adapters and about to make connections.");
      }

      this.logCount = 0;
      BridgeLogger.logInfoAdaptersFound(this.name);
      this.sourceConnSpec = null;
      String sourceUserName = this.mbean.getSourceDestination().getUserName();
      if (sourceUserName != null && sourceUserName.length() > 0) {
         this.sourceProps.put(new String("username"), sourceUserName);
         classPath = this.mbean.getSourceDestination().getUserPassword();
         if (classPath == null) {
            classPath = "";
         }

         this.sourceProps.put(new String("password"), classPath);
      }

      this.sourceProps.put(new String("name"), this.name);
      this.sourceProps.put(new String("fullName"), this.fullName);
      if (this.durabilityEnabled) {
         this.sourceProps.put(new String("durability"), new String("true"));
      } else {
         this.sourceProps.put(new String("durability"), new String("false"));
      }

      if (this.selector != null) {
         this.sourceProps.put(new String("selector"), this.selector);
      }

      classPath = this.mbean.getSourceDestination().getClasspath();
      if (classPath != null) {
         this.sourceProps.put(new String("classpath"), classPath);
      }

      if (this.sourceProps != null && this.sourceProps.size() > 0) {
         try {
            this.sourceConnSpec = this.sourceAdapterFactory.createConnectionSpec(this.sourceProps);
         } catch (ResourceException var12) {
            BridgeLogger.logErrorInvalidSourceProps(this.name);
            return;
         }
      }

      this.targetConnSpec = null;
      this.targetProps.put(new String("name"), this.name);
      this.targetProps.put(new String("fullName"), this.fullName);
      String targetUserName = this.mbean.getTargetDestination().getUserName();
      if (targetUserName != null && targetUserName.length() > 0) {
         this.targetProps.put(new String("username"), targetUserName);
         String targetUserPassword = this.mbean.getTargetDestination().getUserPassword();
         if (targetUserPassword == null) {
            targetUserPassword = "";
         }

         this.targetProps.put(new String("password"), targetUserPassword);
      }

      classPath = this.mbean.getTargetDestination().getClasspath();
      if (classPath != null) {
         this.targetProps.put(new String("classpath"), classPath);
      }

      if (this.preserveMsgProperty) {
         this.targetProps.put(new String("preserveMsgProperty"), "true");
      }

      if (this.targetProps != null && this.targetProps.size() > 0) {
         try {
            this.targetConnSpec = this.targetAdapterFactory.createConnectionSpec(this.targetProps);
         } catch (ResourceException var11) {
            BridgeLogger.logErrorInvalidTargetProps(this.name);
            return;
         }
      }

      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("BridgeBridge " + this.name + " forwarding policy is " + this.policyToString(this.forwardingPolicy));
      }

      synchronized(this.stateLock) {
         this.state = 2;
      }

      this.retryController.reset();
      this.connRetryTimeNext = this.retryController.getNextRetryTime();
   }

   private void getConnections() {
      BridgeLogger.logInfoGetConnections(this.name);
      synchronized(this.stateLock) {
         if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " In getConnections: isStopped = " + this.isStopped());
         }

         if (this.isShutdownOrSuspended() || this.isStopped()) {
            this.logBeginForwarding = true;
            BridgeLogger.logInfoShuttingdown(this.name);
            return;
         }
      }

      SourceConnection sourceConn = null;
      TargetConnection targetConn = null;
      boolean failed = false;
      Throwable exception = null;

      try {
         if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + " Getting source connection");
         }

         synchronized(this) {
            this.sourceConn = this.sourceAdapterFactory.getSourceConnection(this.sourceConnSpec);
            sourceConn = this.sourceConn;
         }
      } catch (Throwable var28) {
         failed = true;
         exception = var28;
      }

      if (!failed && sourceConn != null) {
         if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + " Successfully got connection to the source destination");
         }

         synchronized(this.stateLock) {
            this.health = 0;
            this.reasons.add(0, "Connected to the source.");
         }

         if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + " Getting target connection");
         }

         try {
            synchronized(this) {
               this.targetConn = this.targetAdapterFactory.getTargetConnection(this.targetConnSpec);
               targetConn = this.targetConn;
            }
         } catch (Throwable var24) {
            failed = true;
            exception = var24;
         }

         if (!failed && targetConn != null) {
            if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + " Successfully got connection to the target destination");
            }

            synchronized(this.stateLock) {
               this.health = 0;
               this.reasons.add(0, "Connected to the target.");
            }

            int workMode = false;

            int workMode;
            try {
               synchronized(this) {
                  synchronized(this.stateLock) {
                     this.workMode = this.determineWorkMode(this.sourceMetaData);
                     workMode = this.workMode;
                  }
               }
            } catch (MessagingBridgeException var29) {
               BridgeLogger.logErrorQOSNotAvail(this.name, this.mbean.getQualityOfService());
               if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
                  var29.printStackTrace();
               }

               try {
                  this.shutdown();
               } catch (MessagingBridgeException var16) {
               }

               return;
            }

            if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + " WorkMode = " + this.workModeToString(workMode));
            }

            BridgeLogger.logInfoWorkMode(this.name, this.mbean.getQualityOfService(), this.workModeToQOS(workMode));

            try {
               if (workMode == 2) {
                  sourceConn.setAcknowledgeMode(2);
               }

               if (workMode == 0) {
                  sourceConn.setAcknowledgeMode(99);
               }
            } catch (Throwable var18) {
               this.prepareForRebegin(var18);
            }

            synchronized(this.stateLock) {
               this.state = 3;
            }
         } else {
            synchronized(this) {
               if (this.sourceConn != null) {
                  this.closeConnection(this.sourceConn);
                  this.sourceConn = null;
                  sourceConn = null;
               }
            }

            synchronized(this.stateLock) {
               this.connRetryTimeNext = this.retryController.getNextRetryTime();
               BridgeLogger.logErrorFailedToConnectToTarget(this.name, this.createExceptionWithLinkedExceptionInfo(exception), this.connRetryTimeNext / 1000L);
               this.health = 1;
               this.reasons.add(0, "WARN: failed to connect to the target.");
            }
         }
      } else {
         synchronized(this.stateLock) {
            this.connRetryTimeNext = this.retryController.getNextRetryTime();
            BridgeLogger.logErrorFailedToConnectToSource(this.name, this.createExceptionWithLinkedExceptionInfo(exception), this.connRetryTimeNext / 1000L);
            this.health = 1;
            this.reasons.add(0, "WARN: failed to connect to the source.");
         }
      }
   }

   private void beginForwarding() {
      synchronized(this.stateLock) {
         if (this.logBeginForwarding) {
            BridgeLogger.logInfoBeginForwaring(this.name);
            this.logBeginForwarding = false;
         } else if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
            BridgeLogger.logInfoBeginForwaring(this.name);
         }

         if (this.isNotAvail()) {
            this.logBeginForwarding = true;
            BridgeLogger.logInfoShuttingdown(this.name);
            return;
         }

         this.health = 0;
         this.reasons.add(0, "Forwarding messages.");
      }

      try {
         synchronized(this) {
            label62: {
               this.sourceConn.setExceptionListener(this);
               this.targetConn.setExceptionListener(this);
               synchronized(this.stateLock) {
                  if (!this.async) {
                     break label62;
                  }

                  this.sourceConn.setMessageListener(this);
                  if (this.maximumIdleTime <= 0L) {
                     this.maximumIdleTime = 60000L;
                  }

                  this.idle = true;
                  this.state = 4;
                  this.running = false;
               }

               return;
            }
         }

         this.processMessages();
      } catch (NullPointerException var7) {
         this.prepareForRebegin(var7);
      } catch (Throwable var8) {
         this.prepareForRebegin(var8);
      }

   }

   public void run() {
      if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " in run(): state = " + this.state);
      }

      label255: {
         switch (this.getInternalState()) {
            case 1:
               synchronized(this.stateLock) {
                  if (this.isStopped()) {
                     this.running = false;
                     return;
                  }
               }

               try {
                  this.startInternal();
               } catch (Exception var27) {
                  synchronized(this.stateLock) {
                     this.running = false;
                     return;
                  }
               }

               synchronized(this.stateLock) {
                  if (this.state != 2) {
                     this.running = false;
                     return;
                  }
               }
            case 2:
               break;
            case 3:
            case 5:
               break label255;
            case 4:
            case 10:
            default:
               synchronized(this.stateLock) {
                  this.running = false;
                  if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                     BridgeDebug.MessagingBridgeRuntime.debug("Internal error -- Invalid state: " + this.state);
                  }

                  return;
               }
            case 6:
            case 7:
            case 11:
            case 12:
               synchronized(this.stateLock) {
                  this.running = false;
                  return;
               }
            case 8:
               try {
                  this.stopForwarding();
               } catch (Exception var21) {
               }

               synchronized(this.stateLock) {
                  this.state = 2;
                  this.running = false;
                  return;
               }
            case 9:
               try {
                  synchronized(this.stateLock) {
                     if (this.isStopped()) {
                        this.running = false;
                        return;
                     }

                     if (this.async && this.onMessageIdleCurrent >= this.maximumIdleTime) {
                        if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                           BridgeLogger.logInfoAsyncReconnect(this.name);
                        }
                     } else {
                        BridgeLogger.logInfoSyncReconnect(this.name);
                     }
                  }

                  this.stopForwarding();
                  synchronized(this.stateLock) {
                     this.state = 2;
                  }

                  this.getConnections();
                  this.beginForwarding();
               } catch (Exception var31) {
               }

               synchronized(this.stateLock) {
                  this.running = false;
                  return;
               }
         }

         synchronized(this.stateLock) {
            if (this.isStopped()) {
               this.running = false;
               return;
            }
         }

         try {
            this.getConnections();
         } catch (Exception var29) {
            if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " Failed to get connections because of " + var29);
            }

            synchronized(this.stateLock) {
               this.running = false;
               return;
            }
         }

         synchronized(this.stateLock) {
            if (this.state != 3) {
               this.running = false;
               return;
            }
         }
      }

      synchronized(this.stateLock) {
         if (this.isStopped()) {
            this.running = false;
            return;
         }
      }

      try {
         this.beginForwarding();
      } catch (Exception var24) {
      }

      synchronized(this.stateLock) {
         this.running = false;
      }
   }

   private DiagnosticIntegrationManager getDiagnosticIntegrationManager() {
      try {
         return (DiagnosticIntegrationManager)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return Factory.getInstance();
            }
         });
      } catch (PrivilegedActionException var2) {
         return null;
      }
   }

   private TransactionManager getTransactionManager() {
      synchronized(this.stateLock) {
         if (this.tm == null && this.workMode == 4 || this.workMode == 1) {
            this.tm = TxHelper.getTransactionManager();

            try {
               this.tm.setTransactionTimeout(this.transactionTimeout);
            } catch (SystemException var4) {
            }
         }

         return this.tm;
      }
   }

   private void closeConnection(AdapterConnection conn) {
      try {
         if (conn != null) {
            conn.close();
         }
      } catch (ResourceException var3) {
      }

   }

   private synchronized void cleanup() {
      if (this.sourceConn != null) {
         if (!this.getAsync()) {
            try {
               this.sourceConn.recover();
            } catch (Exception var2) {
            }
         }

         this.closeConnection(this.sourceConn);
         this.sourceConn = null;
      }

      if (this.targetConn != null) {
         this.closeConnection(this.targetConn);
         this.targetConn = null;
      }

   }

   private void stopForwarding() {
      boolean stoppedSaved;
      synchronized(this.stateLock) {
         if (this.isShutdownOrSuspended() || this.state == 6 || this.state == 7) {
            return;
         }

         this.state = 6;
         stoppedSaved = this.stopped;
      }

      boolean var15 = false;

      try {
         var15 = true;
         synchronized(this) {
            if (this.sourceConn != null) {
               try {
                  if (!this.getAsync()) {
                     this.sourceConn.recover();
                  }
               } catch (Exception var16) {
               }

               this.closeConnection(this.sourceConn);
            }

            if (this.targetConn != null) {
               this.closeConnection(this.targetConn);
            }

            var15 = false;
         }
      } finally {
         if (var15) {
            synchronized(this.stateLock) {
               if (stoppedSaved && !this.stopped) {
                  this.state = 2;
               } else {
                  this.state = 7;
               }
            }

            BridgeLogger.logInfoStopped(this.name);
         }
      }

      synchronized(this.stateLock) {
         if (stoppedSaved && !this.stopped) {
            this.state = 2;
         } else {
            this.state = 7;
         }
      }

      BridgeLogger.logInfoStopped(this.name);
   }

   public void onMessage(Message msg) {
      Thread currentThread = Thread.currentThread();
      ClassLoader clSave = currentThread.getContextClassLoader();

      try {
         currentThread.setContextClassLoader(this.savedClassLoader);
         this.onMessageInternal(msg);
      } finally {
         currentThread.setContextClassLoader(clSave);
      }

   }

   private void onMessageInternal(Message msg) {
      String oldMessageId = null;
      if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) received message: " + EOL + this.constructLogForReceivedMessage(msg));

         try {
            oldMessageId = msg.getJMSMessageID();
         } catch (JMSException var11) {
         }
      }

      boolean needRecover = false;
      int workMode = false;
      TransactionManager tm = null;
      int workMode;
      synchronized(this.stateLock) {
         this.idle = false;
         if (this.isNotAvail()) {
            needRecover = true;
         }

         workMode = this.workMode;
         tm = this.getTransactionManager();
      }

      if (needRecover && this.sourceConn != null) {
         synchronized(this.stateLock) {
            this.logBeginForwarding = true;
            BridgeLogger.logInfoShuttingdown(this.name);
         }

         try {
            this.sourceConn.recover();
            if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) recovered: " + msg.getJMSMessageID());
            }
         } catch (Exception var9) {
         }

      } else {
         try {
            if (workMode == 4 || workMode == 1) {
               tm.begin("Messaging Bridge");
               this.sourceConn.associateTransaction(msg);
               if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                  Transaction tx = TxHelper.getTransaction();
                  Object xidString = tx == null ? null : tx.getXID();
                  BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) associated msg " + msg.getJMSMessageID() + " with transaction " + xidString);
               }
            }

            Message msgCopy = this.targetConn.createMessage(msg);
            this.sendWithWorkContext(this.targetConn, msgCopy, msg instanceof MessageImpl);
            if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
               BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) successfully sent message: " + EOL + this.constructLogForSentMessage(oldMessageId, msg));
            }

            if (workMode != 4 && workMode != 1) {
               if (workMode == 2) {
                  this.sourceConn.acknowledge(msg);
                  if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                     BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) acknowledged the msg");
                  }
               }
            } else {
               tm.commit();
               if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                  BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) committed the transaction");
               }
            }
         } catch (Throwable var13) {
            try {
               if (workMode != 4 && workMode != 1) {
                  if (workMode == 2) {
                     if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                        BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) calling recover");
                     }

                     this.sourceConn.recover();
                  }
               } else {
                  tm.rollback();
                  if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                     BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (onMessage()) rolled back the transaction");
                  }
               }
            } catch (Exception var12) {
            }

            this.prepareForRebegin(var13);
         }

      }
   }

   void processMessages() throws MessagingBridgeException {
      if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " Entering processMessages() ------ ");
      }

      int workMode = false;
      TransactionManager tm = null;
      int workMode;
      synchronized(this.stateLock) {
         if (this.async) {
            this.throwMessagingBridgeException("Messaging bridge internal error", (Exception)null);
         }

         workMode = this.workMode;
         tm = this.getTransactionManager();
      }

      for(int i = 0; i < 10; ++i) {
         long batchBeginTime = System.currentTimeMillis();
         LocalTransaction ltx = null;
         synchronized(this) {
            int batchSizeSaved;
            long flushingTimeSaved;
            long maximumIdleTimeSaved;
            label236: {
               int batchSizeSaved = false;
               long batchIntervalSaved = 0L;
               flushingTimeSaved = 0L;
               maximumIdleTimeSaved = 0L;
               synchronized(this.stateLock) {
                  if (!this.isNotAvail() && this.sourceConn != null && this.targetConn != null) {
                     this.state = 5;
                     batchSizeSaved = this.batchSize;
                     batchIntervalSaved = this.batchInterval;
                     flushingTimeSaved = this.flushingTime;
                     maximumIdleTimeSaved = this.maximumIdleTime;
                     break label236;
                  }

                  this.logBeginForwarding = true;
                  BridgeLogger.logInfoShuttingdown(this.name);
               }

               return;
            }

            try {
               Message msg;
               if (workMode != 4 && workMode != 1) {
                  if (workMode == 3) {
                     ltx = this.sourceConn.getLocalTransaction();
                     ltx.begin();
                     msg = this.sourceConn.receive(flushingTimeSaved);
                  } else {
                     msg = this.sourceConn.receive(maximumIdleTimeSaved);
                  }
               } else {
                  if (tm == null) {
                     tm = this.getTransactionManager();
                  }

                  tm.begin("Messaging Bridge");
                  msg = this.sourceConn.receive(flushingTimeSaved);
               }

               String oldMessageId = null;
               if (msg != null) {
                  int j = 0;
                  Message msgCopy;
                  if (workMode == 4 || workMode == 1 || workMode == 3) {
                     for(; msg != null && j < batchSizeSaved - 1; ++j) {
                        if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                           BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (processMessages()) received message: " + EOL + this.constructLogForReceivedMessage(msg));

                           try {
                              oldMessageId = msg.getJMSMessageID();
                           } catch (JMSException var25) {
                           }
                        }

                        msgCopy = this.targetConn.createMessage(msg);
                        this.sendWithWorkContext(this.targetConn, msgCopy, msg instanceof MessageImpl);
                        if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                           BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (processMessages()) successfully sent message: " + EOL + this.constructLogForSentMessage(oldMessageId, msg));
                        }

                        if (workMode != 4 && workMode != 1) {
                           msg = this.sourceConn.receive(0L);
                        } else {
                           long flushingTimeLeft = flushingTimeSaved - (System.currentTimeMillis() - batchBeginTime);
                           if (flushingTimeLeft < 0L) {
                              flushingTimeLeft = 0L;
                           }

                           msg = this.sourceConn.receive(flushingTimeLeft);
                        }
                     }
                  }

                  if (msg != null) {
                     if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                        BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (processMessages()) received message: " + EOL + this.constructLogForReceivedMessage(msg));

                        try {
                           oldMessageId = msg.getJMSMessageID();
                        } catch (JMSException var24) {
                        }
                     }

                     msgCopy = this.targetConn.createMessage(msg);
                     this.sendWithWorkContext(this.targetConn, msgCopy, msg instanceof MessageImpl);
                     if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                        BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (processMessages()) successfully sent message: " + EOL + this.constructLogForSentMessage(oldMessageId, msg));
                     }

                     if (workMode == 2) {
                        this.sourceConn.acknowledge(msg);
                        if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                           BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (processMessages()) successfully acknowledged message: " + oldMessageId);
                        }
                     }
                  }

                  if (workMode != 4 && workMode != 1) {
                     if (workMode == 3) {
                        ltx.commit();
                     }
                  } else {
                     tm.commit();
                     if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
                        BridgeDebug.MessagingBridgeRuntime.debug("Bridge: " + this.name + " (processMessages()) committed the transaction");
                     }
                  }
               } else {
                  try {
                     if (workMode != 4 && workMode != 1) {
                        if (workMode == 3) {
                           ltx.rollback();
                        } else if (workMode == 2) {
                           this.sourceConn.recover();
                        }
                     } else {
                        tm.rollback();
                     }
                  } catch (Exception var26) {
                  }
               }
            } catch (Throwable var29) {
               if (!(var29 instanceof ResourceTransactionRolledBackException)) {
                  BridgeLogger.logErrorProcessMsgs(this.name, this.createExceptionWithLinkedExceptionInfo(var29));

                  try {
                     if (workMode != 4 && workMode != 1) {
                        if (workMode == 3) {
                           ltx.rollback();
                        } else if (workMode == 2) {
                           this.sourceConn.recover();
                        }
                     } else {
                        tm.rollback();
                     }
                  } catch (Exception var28) {
                  }

                  synchronized(this.stateLock) {
                     this.state = 9;
                  }

                  this.throwMessagingBridgeException("Messaging bridge operation failed", var29);
               }
            }
         }
      }

   }

   private void sendWithWorkContext(TargetConnection targetConn, Message targetMsg, boolean sourceIsWLS) throws ResourceException, IOException {
      if (!sourceIsWLS && this.diagnosticMBean != null && this.diagnosticMBean.isDiagnosticContextEnabled()) {
         Correlation correlation = this.integrationManager.newCorrelation();
         this.integrationManager.activateCorrelation(correlation);
         String dcid = correlation.getECID();
         if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " Send with work context DCID: " + dcid);
         }

         targetConn.send(targetMsg);
      } else {
         targetConn.send(targetMsg);
      }
   }

   public void onException(JMSException t) {
      this.prepareForRebegin(t);
   }

   private void prepareForRebegin(Throwable t) {
      boolean needRun = false;
      synchronized(this.stateLock) {
         if (this.state == 8 || this.state == 2 || this.state == 6) {
            return;
         }

         BridgeLogger.logInfoReconnect(this.name, this.createExceptionWithLinkedExceptionInfo(t));
         this.state = 8;
         if (this.async) {
            this.running = false;
         }

         if (!this.running) {
            needRun = true;
            this.running = true;
         }

         this.logBeginForwarding = true;
         this.health = 1;
         this.reasons.add(0, "WARN: failed and will reconnect later.");
         this.retryController.reset();
         this.connRetryTimeNext = this.retryController.getNextRetryTime();
      }

      if (needRun) {
         this.scheduleThis();
      }

   }

   private int determineWorkMode(AdapterMetaData srcMetaData) throws MessagingBridgeException {
      if (srcMetaData.supportsAsynchronousMode() && this.asyncEnabled) {
         this.async = true;
      } else {
         this.async = false;
      }

      if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + ": both source and target adapters support XA = " + this.xaSupported);
      }

      if (this.qosDegradAllowed) {
         BridgeLogger.logInfoQOSDegradationAllowed(this.name);
      } else {
         BridgeLogger.logInfoQOSDegradationNotAllowed(this.name);
      }

      boolean supportsMDBTX = false;
      boolean supportsXA = false;

      try {
         supportsMDBTX = srcMetaData.supportsMDBTransaction() && this.sourceConn.getMetaData().implementsMDBTransaction();
         supportsXA = this.xaSupported && this.sourceConn.getMetaData().isXAConnection() && this.targetConn.getMetaData().isXAConnection();
      } catch (ResourceException var5) {
      }

      if (BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeRuntime.debug("Bridge " + this.name + " supportsMDBTX = " + supportsMDBTX + " supportsXA = " + supportsXA + " async = " + this.async);
      }

      switch (this.qos) {
         case 0:
            if (supportsXA) {
               if (this.async && !supportsMDBTX) {
                  this.async = false;
               }

               return 4;
            } else if (!this.qosDegradAllowed) {
               throw new MessagingBridgeException("QOS cannot be satisfied");
            }
         case 1:
            if (srcMetaData.supportsAcknowledgement()) {
               return 2;
            } else if (this.sourceXASupported) {
               if (!supportsMDBTX) {
                  this.async = false;
               }

               return 1;
            } else if (this.localTXSupported) {
               this.async = false;
               return 3;
            } else if (!this.qosDegradAllowed) {
               throw new MessagingBridgeException("QOS cannot be satisfied");
            }
         case 2:
            return 0;
         default:
            throw new MessagingBridgeException("Invalid quality of service");
      }
   }

   private String workModeToString(int mode) {
      switch (mode) {
         case 0:
            return "Atmost-once";
         case 1:
            return "Duplicate-okay-xa";
         case 2:
            return "Duplicate-okay-ack";
         case 3:
            return "Duplicate-okay-local";
         case 4:
            return "Exactly-once";
         default:
            return "Mode not supported";
      }
   }

   private String workModeToQOS(int mode) {
      switch (mode) {
         case 0:
            return "Atmost_once";
         case 1:
         case 2:
         case 3:
            return "Duplicate-okay";
         case 4:
            return "Exactly-once";
         default:
            return "Mode not supported";
      }
   }

   public void suspend(boolean force) {
      synchronized(this.stateLock) {
         if (this.state == 11 || this.state == 12) {
            return;
         }

         this.state = 12;
         this.health = 1;
         this.reasons.add(0, "WARN: Bridge " + this.name + " is suspended because of server suspension.");
         if (this.timer != null) {
            this.timer.cancel();
         }
      }

      this.cleanup();
   }

   public void shutdown() throws MessagingBridgeException {
      GenericBeanListener listener = null;
      synchronized(this.stateLock) {
         if (this.state == 11) {
            return;
         }

         this.state = 11;
         this.logBeginForwarding = true;
         BridgeLogger.logInfoShutdown(this.name);
         if (this.timer != null) {
            this.timer.cancel();
         }

         if (this.bridgeListener != null) {
            listener = this.bridgeListener;
            this.bridgeListener = null;
         }
      }

      try {
         if (listener != null) {
            listener.close();
         }

         this.cleanup();
      } catch (Exception var13) {
      } finally {
         try {
            this.unregister();
            if (this.parent instanceof PartitionRuntimeMBean) {
               ((PartitionRuntimeMBean)this.parent).removeMessagingBridgeRuntime(this);
            } else {
               ManagementService.getRuntimeAccess(kernelId).getServerRuntime().removeMessagingBridgeRuntime(this);
            }
         } catch (Exception var12) {
         }

      }

   }

   public MessagingBridgeMBean getMBean() {
      return this.mbean;
   }

   public void markShuttingDown() {
      synchronized(this.stateLock) {
         if (this.state != 10 && this.state != 11) {
            this.state = 10;
         }

      }
   }

   private boolean isShutdownOrSuspended() {
      return this.state == 11 || this.state == 10 || this.state == 12;
   }

   private boolean isStopped() {
      return this.stopped;
   }

   private boolean isNotAvail() {
      return this.state == 11 || this.state == 10 || this.state == 12 || this.state == 8 || this.state == 2 || this.state == 6 || this.state == 7 || this.stopped;
   }

   private void throwMessagingBridgeException(String info, Throwable e) throws MessagingBridgeException {
      MessagingBridgeException mbe = new MessagingBridgeException(info);
      if (e != null) {
         if (e instanceof Exception) {
            mbe.setLinkedException((Exception)e);
         } else {
            mbe.setLinkedThrowable(e);
         }
      }

      throw mbe;
   }

   private String policyToString(int policy) {
      switch (policy) {
         case 0:
            return new String("AUTO");
         case 1:
            return new String("SCHEDULED");
         case 2:
            return new String("MANUAL");
         default:
            return null;
      }
   }

   private Properties copyProperties(Properties copyee) {
      Properties copyer = new Properties();
      if (copyee != null) {
         Enumeration enmt = copyee.propertyNames();

         while(enmt.hasMoreElements()) {
            String key = (String)enmt.nextElement();
            copyer.put(key, copyee.get(key));
         }
      }

      return copyer;
   }

   private int getInternalState() {
      synchronized(this.stateLock) {
         return this.state;
      }
   }

   public void setReconnectDelayMinimum(int newVal) {
      synchronized(this.retryController) {
         long oldVal = this.retryController.getInitial() / 1000L;
         this.retryController.setInitial((long)newVal * 1000L);
         BridgeLogger.logInfoAttributeChanged(this.name, "ReconnectDelayMinimum", oldVal, (long)newVal);
      }
   }

   public void setReconnectDelayIncrease(int newVal) {
      synchronized(this.retryController) {
         long oldVal = this.retryController.getIncrement() / 1000L;
         this.retryController.setIncrement((long)newVal * 1000L);
         BridgeLogger.logInfoAttributeChanged(this.name, "ReconnectDelayIncrease", oldVal, (long)newVal);
      }
   }

   public void setReconnectDelayMaximum(int newVal) {
      synchronized(this.retryController) {
         long oldVal = this.retryController.getMaximum() / 1000L;
         this.retryController.setMaximum((long)newVal * 1000L);
         BridgeLogger.logInfoAttributeChanged(this.name, "ReconnectDelayMaximum", oldVal, (long)newVal);
      }
   }

   public void setTransactionTimeout(int paramTransactionTimeout) {
      synchronized(this.stateLock) {
         int oldVal = this.transactionTimeout;
         if (paramTransactionTimeout <= 0) {
            paramTransactionTimeout = 1;
         }

         if (this.batchInterval < 0L) {
            this.flushingTime = (long)(800 * paramTransactionTimeout);
         }

         this.transactionTimeout = paramTransactionTimeout;
         if (this.tm == null) {
            this.tm = TxHelper.getTransactionManager();
         }

         try {
            this.tm.setTransactionTimeout(this.transactionTimeout);
         } catch (SystemException var6) {
         }

         BridgeLogger.logInfoAttributeChanged(this.name, "TransactionTimeout", (long)oldVal, (long)this.transactionTimeout);
      }
   }

   private boolean getAsync() {
      synchronized(this.stateLock) {
         return this.async;
      }
   }

   public void setIdleTimeMaximum(int newVal) {
      synchronized(this.stateLock) {
         long oldVal = this.maximumIdleTime / 1000L;
         this.maximumIdleTime = (long)newVal * 1000L;
         if (this.maximumIdleTime <= 0L && this.async) {
            this.maximumIdleTime = 60000L;
         }

         BridgeLogger.logInfoAttributeChanged(this.name, "IdleTimeMaximum", oldVal, this.maximumIdleTime / 1000L);
      }
   }

   public void setBatchSize(int newVal) {
      synchronized(this.stateLock) {
         int oldVal = this.batchSize;
         this.batchSize = newVal;
         BridgeLogger.logInfoAttributeChanged(this.name, "BatchSize", (long)oldVal, (long)this.batchSize);
      }
   }

   public void setBatchInterval(long newVal) {
      synchronized(this.stateLock) {
         long oldVal = this.batchInterval;
         this.batchInterval = newVal;
         if (this.batchInterval < 0L) {
            this.flushingTime = (long)(800 * this.transactionTimeout);
         } else {
            this.flushingTime = this.batchInterval;
         }

         BridgeLogger.logInfoAttributeChanged(this.name, "BatchInterval", oldVal, this.batchInterval);
      }
   }

   public void setStarted(boolean started) {
      boolean callScheduleThis;
      if (started) {
         callScheduleThis = false;
         synchronized(this.stateLock) {
            if (!this.isStopped()) {
               return;
            }

            if (this.isShutdownOrSuspended()) {
               BridgeLogger.logFailedStart(this.name);
               return;
            }

            this.stopped = false;
            this.health = 0;
            this.reasons.add(0, "Started by administrator.");
            BridgeLogger.logInfoAttributeStartedChanged(this.name, "false", "true");
            if (this.state == 1) {
               if (this.timer != null) {
                  this.timer.cancel();
               }

               this.timer = this.timerManager.scheduleAtFixedRate(this, 0L, this.scanUnit);
               callScheduleThis = true;
            }

            if (this.state == 7) {
               this.state = 2;
            }

            if (this.async) {
               this.running = false;
            }

            if (!this.running) {
               this.running = true;
               callScheduleThis = true;
            }
         }

         if (callScheduleThis) {
            this.scheduleThis();
         }
      } else {
         callScheduleThis = false;
         synchronized(this.stateLock) {
            if (this.isStopped()) {
               return;
            }

            this.stopped = true;
            this.logBeginForwarding = true;
            this.health = 1;
            this.reasons.add(0, "WARN: Stopped by administrator.");
            BridgeLogger.logInfoAttributeStartedChanged(this.name, "true", "false");
            if (this.state == 1 || this.state == 2) {
               return;
            }

            if (this.state == 4 || this.state == 5 || this.state == 8 || this.state == 3) {
               callScheduleThis = true;
            }
         }

         if (callScheduleThis) {
            try {
               this.stopForwarding();
            } catch (Exception var8) {
            }

            synchronized(this.stateLock) {
               this.running = false;
            }
         }
      }

   }

   private void doTrigger() {
      if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " doTrigger(): state = " + this.state + " stopped = " + this.stopped);
      }

      synchronized(this.stateLock) {
         if (this.isStopped()) {
            return;
         }

         switch (this.state) {
            case 1:
               if (this.lookupRetryTimeCurrent >= 10000L) {
                  this.lookupRetryTimeCurrent = 0L;
               }

               this.lookupRetryTimeCurrent += this.scanUnit;
               if (this.lookupRetryTimeCurrent < 10000L) {
                  return;
               }

               if (this.running) {
                  return;
               }

               this.running = true;
               break;
            case 2:
               this.connRetryTimeCurrent += this.scanUnit;
               if (this.connRetryTimeCurrent < this.connRetryTimeNext) {
                  return;
               }

               if (this.connRetryTimeCurrent >= this.connRetryTimeNext) {
                  this.connRetryTimeCurrent = 0L;
               }

               if (this.running) {
                  return;
               }

               this.running = true;
               break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
               if (this.running) {
                  return;
               }

               this.running = true;
               break;
            case 4:
               if (!this.idle) {
                  return;
               }

               if (this.onMessageIdleCurrent >= this.maximumIdleTime) {
                  this.onMessageIdleCurrent = 0L;
               }

               this.onMessageIdleCurrent += this.scanUnit;
               if (this.onMessageIdleCurrent < this.maximumIdleTime) {
                  return;
               }

               this.state = 9;
               if (this.running) {
                  return;
               }

               this.running = true;
               break;
            default:
               return;
         }

         if (BridgeDebug.MessagingBridgeRuntimeVerbose.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeRuntimeVerbose.debug("Bridge " + this.name + " doTrigger(): about to run scheduleThis() state = " + this.state);
         }
      }

      this.scheduleThis();
   }

   public static Context getContext() throws NamingException {
      Environment env = new Environment();
      env.setCreateIntermediateContexts(true);
      env.setReplicateBindings(true);
      return env.getInitialContext();
   }

   public void stop() {
      throw new UnsupportedOperationException("This method is not implemented on runtime mbean. The way of start/stop a bridge at runtime is to change the Started attribute on the configuration mbean");
   }

   public void start() {
      throw new UnsupportedOperationException("This method is not implemented on runtime mbean. The way of start/stop a bridge at runtime is to change the Started attribute on the configuration mbean");
   }

   public String getState() {
      String state1 = null;
      synchronized(this.stateLock) {
         if (this.stateCache[0] == null || this.stateCache[1] == null) {
            switch (this.health) {
               case 0:
                  this.stateCache[0] = "Active";
                  this.stateCache[1] = (String)this.reasons.get(0);
                  break;
               case 1:
                  this.stateCache[0] = "Inactive";
                  this.stateCache[1] = (String)this.reasons.get(0);
            }
         }

         if (this.stateCache[0] != null) {
            state1 = this.stateCache[0];
            this.stateCache[0] = null;
         }

         return state1;
      }
   }

   public String getDescription() {
      String state2 = null;
      synchronized(this.stateLock) {
         if (this.stateCache[0] == null || this.stateCache[1] == null) {
            switch (this.health) {
               case 0:
                  this.stateCache[0] = "Active";
                  this.stateCache[1] = (String)this.reasons.get(0);
                  break;
               case 1:
                  this.stateCache[0] = "Inactive";
                  this.stateCache[1] = (String)this.reasons.get(0);
            }
         }

         if (this.stateCache[1] != null) {
            state2 = this.stateCache[1];
            this.stateCache[1] = null;
         }

         return state2;
      }
   }

   private Exception createExceptionWithLinkedExceptionInfo(Throwable e) {
      if (!BridgeDebug.MessagingBridgeStartup.isDebugEnabled() && !BridgeDebug.MessagingBridgeRuntime.isDebugEnabled()) {
         return e instanceof Exception ? (Exception)e : new Exception(e.getMessage());
      } else {
         String emsg = StackTraceUtils.throwable2StackTrace(e);
         if (e instanceof ResourceException) {
            Exception le = ((ResourceException)e).getLinkedException();
            if (le != null) {
               emsg = emsg + "-------------- Linked Exception ------------" + EOL + StackTraceUtils.throwable2StackTrace(le);
               Exception le2 = null;
               if (le instanceof ResourceException) {
                  le2 = ((ResourceException)le).getLinkedException();
               }

               if (le instanceof JMSException) {
                  le2 = ((JMSException)le).getLinkedException();
               }

               if (le2 != null) {
                  emsg = emsg + "-------------- Linked Exception 2 ------------" + EOL + StackTraceUtils.throwable2StackTrace(le2);
                  Exception le3 = null;
                  if (le2 instanceof JMSException) {
                     le3 = ((JMSException)le2).getLinkedException();
                  }

                  if (le3 != null) {
                     emsg = emsg + "-------------- Linked Exception 3 ------------" + EOL + StackTraceUtils.throwable2StackTrace(le3);
                  }
               }
            }
         }

         return new Exception(emsg);
      }
   }

   private String constructLogForReceivedMessage(Message msg) {
      String logString = null;
      logString = this.getJMSHeaders(msg);
      Transaction tx = TxHelper.getTransaction();
      Object xidString = tx == null ? null : tx.getXID();
      logString = logString + EOL + "  Transaction Id: " + xidString;
      if (msg instanceof TextMessage) {
         try {
            String text = ((TextMessage)msg).getText();
            logString = logString + EOL + "  " + (text == null ? "null" : (text.length() < 53 ? text : text.substring(0, 50) + "..."));
         } catch (JMSException var6) {
            logString = logString + EOL + "  Failed to get text. " + var6.toString();
         }
      }

      logString = logString + EOL + "isWLS?" + (msg instanceof MessageImpl);
      return logString;
   }

   private String constructLogForSentMessage(String oldID, Message msg) {
      String logString = "  JMS Message Class: ";
      if (msg instanceof TextMessage) {
         logString = logString + "TextMessage";
      }

      if (msg instanceof ObjectMessage) {
         logString = logString + "ObjectMessage";
      }

      if (msg instanceof BytesMessage) {
         logString = logString + "BytesMessage";
      }

      if (msg instanceof MapMessage) {
         logString = logString + "MapMessage";
      }

      if (msg instanceof StreamMessage) {
         logString = logString + "StreamMessage";
      }

      try {
         logString = logString + EOL + "  Old JMS MessageID: " + oldID;
         logString = logString + EOL + "  New JMS MessageID: " + msg.getJMSMessageID();
         if (msg instanceof TextMessage) {
            String text = ((TextMessage)msg).getText();
            logString = logString + EOL + "  " + (text == null ? "null" : (text.length() <= 53 ? text : text.substring(0, 50) + "..."));
         }
      } catch (JMSException var5) {
         logString = logString + EOL + "  Failed to get fields. " + var5.toString();
      }

      logString = logString + EOL;
      return logString;
   }

   private String getJMSHeaders(Message msg) {
      String logString = "  JMS Message Class: ";
      if (msg instanceof TextMessage) {
         logString = logString + "TextMessage";
      }

      if (msg instanceof ObjectMessage) {
         logString = logString + "ObjectMessage";
      }

      if (msg instanceof BytesMessage) {
         logString = logString + "BytesMessage";
      }

      if (msg instanceof MapMessage) {
         logString = logString + "MapMessage";
      }

      if (msg instanceof StreamMessage) {
         logString = logString + "StreamMessage";
      }

      try {
         String correlationString = msg.getJMSCorrelationID();
         if (correlationString != null && correlationString.length() > 53) {
            correlationString = correlationString.substring(0, 50) + "...";
         }

         String typeString = msg.getJMSType();
         if (typeString != null && typeString.length() > 53) {
            typeString = typeString.substring(0, 50) + "...";
         }

         logString = logString + EOL + "  JMSMessageID: " + msg.getJMSMessageID();
         logString = logString + EOL + "  JMSCorrelationID: " + correlationString;
         logString = logString + EOL + "  JMSDeliveryMode: " + (msg.getJMSDeliveryMode() == 1 ? "NON_PERSISTENT" : "PERSISTENT");
         logString = logString + EOL + "  JMSDestination: " + msg.getJMSDestination();
         logString = logString + EOL + "  JMSExpiration: " + dateToString(msg.getJMSExpiration());
         logString = logString + EOL + "  JMSPriority: " + msg.getJMSPriority();
         logString = logString + EOL + "  JMSRedelivered: " + msg.getJMSRedelivered();
         logString = logString + EOL + "  JMSReplyTo: " + msg.getJMSReplyTo();
         logString = logString + EOL + "  JMSTimestamp: " + dateToString(msg.getJMSTimestamp());
         logString = logString + EOL + "  JMSType: " + typeString;
      } catch (JMSException var6) {
         logString = logString + EOL + "  Failed to get all headers. " + var6.toString();
      }

      return logString;
   }

   private static String dateToString(long time) {
      return time <= 0L ? "0" : time + " (" + new Date(time) + ")";
   }

   private static String getEOL() {
      String EOL = System.getProperty("line.separator");
      if (EOL == null) {
         EOL = "\n";
      }

      return EOL;
   }

   public void timerExpired(Timer timer) {
      this.doTrigger();
   }

   private void scheduleThis() {
      if (this.workManager == null) {
         this.workManager = this.partitionBridgeService.getWorkManager();
      }

      this.workManager.schedule(this);
   }

   private String getInternalJNDIName(String destinationType, String destinationName, String jndiName) {
      if ("eis.jms.WLSConnectionFactoryJNDIXA".equals(jndiName)) {
         this.partitionBridgeService.findAdapterAndRegister(jndiName, this);
         BridgeLogger.logInfoAdapterJNDINameChanged(this.name, destinationType, destinationName, jndiName, "eis.jms.internal.WLSConnectionFactoryJNDIXA");
         return "eis.jms.internal.WLSConnectionFactoryJNDIXA";
      } else if ("eis.jms.WLSConnectionFactoryJNDINoTX".equals(jndiName)) {
         this.partitionBridgeService.findAdapterAndRegister(jndiName, this);
         BridgeLogger.logInfoAdapterJNDINameChanged(this.name, destinationType, destinationName, jndiName, "eis.jms.internal.WLSConnectionFactoryJNDINoTX");
         return "eis.jms.internal.WLSConnectionFactoryJNDINoTX";
      } else {
         BridgeLogger.logInfoConfiguredAdapterJNDIName(this.name, destinationType, destinationName, jndiName);
         return jndiName;
      }
   }

   static {
      bridgeSignatures.put("ReconnectDelayMinimum", Integer.TYPE);
      bridgeSignatures.put("ReconnectDelayIncrease", Integer.TYPE);
      bridgeSignatures.put("ReconnectDelayMaximum", Integer.TYPE);
      bridgeSignatures.put("TransactionTimeout", Integer.TYPE);
      bridgeSignatures.put("IdleTimeMaximum", Integer.TYPE);
      bridgeSignatures.put("BatchSize", Integer.TYPE);
      bridgeSignatures.put("BatchInterval", Long.TYPE);
      bridgeSignatures.put("Started", Boolean.TYPE);
   }

   class RetryTimeController {
      long nextTime;
      long initial;
      long inc;
      long max;

      void init(long initial, long inc, long max) {
         this.initial = initial;
         this.nextTime = initial;
         this.inc = inc;
         this.max = max;
      }

      synchronized void reset() {
         this.nextTime = this.initial - this.inc;
      }

      synchronized long getNextRetryTime() {
         if (this.nextTime <= this.max) {
            this.nextTime += this.inc;
         } else {
            this.nextTime = this.max + this.inc;
         }

         return this.nextTime - this.inc;
      }

      synchronized void setInitial(long newValue) {
         this.initial = newValue;
         if (this.initial > this.max) {
            this.max = this.initial;
         }

      }

      synchronized long getInitial() {
         return this.initial;
      }

      synchronized void setIncrement(long newValue) {
         this.inc = newValue;
      }

      synchronized long getIncrement() {
         return this.inc;
      }

      synchronized void setMaximum(long newValue) {
         this.max = newValue;
         if (this.initial > this.max) {
            this.initial = this.max;
         }

      }

      synchronized long getMaximum() {
         return this.max;
      }
   }
}
