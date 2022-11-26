package weblogic.ejb.container.internal;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;
import javax.jms.XATopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.spi.security.PasswordCredential;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import weblogic.deployment.jms.ForeignOpaqueReference;
import weblogic.deployment.jms.JMSConnectionHelper;
import weblogic.deployment.jms.JMSSessionPoolManager;
import weblogic.deployment.jms.MDBSession;
import weblogic.deployment.jms.ObjectBasedSecurityAware;
import weblogic.deployment.jms.PooledConnectionFactory;
import weblogic.deployment.jms.WrappedClassManager;
import weblogic.deployment.jms.WrappedTransactionalSession;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenManagerIntf;
import weblogic.ejb.spi.SecurityPlugin;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.deployer.DeployerConstants.DefaultNames;
import weblogic.jms.extensions.DestinationDetail;
import weblogic.jms.extensions.MDBTransaction;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLSession;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.rmi.cluster.ThreadPreferredHost;
import weblogic.rmi.spi.HostID;
import weblogic.security.UsernameAndPassword;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.EJBResource;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.NotYetInitializedException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.timers.Timer;
import weblogic.transaction.TransactionManager;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.MaxThreadsConstraint;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;
import weblogic.work.WorkManagerHelper;
import weblogic.work.WorkManagerImpl;

public final class JMSConnectionPoller extends MDConnectionManager {
   static final int MAX_JMS_ERRORS = 3;
   static final int MAX_ERROR_COUNT = Integer.getInteger("weblogic.ejb.container.MaxMDBErrors", 10);
   static final int ERROR_SLEEP_TIME = Integer.getInteger("weblogic.ejb.container.MDBErrorSleepTime", 5) * 1000;
   private static final String COMPLETION_TIMEOUT_OVERRIDE_PROPERTY = "weblogic.CompletionTimeoutSecondsOverride";
   private static final boolean IS_COMPLETION_TIMEOUT_CONFIGURED = Integer.getInteger("weblogic.CompletionTimeoutSecondsOverride") != null;
   private static final int COMPLETION_TIMEOUT_SECONDS;
   private static final String PROVIDERS_NEED_CONTINUOUS_POLLING;
   private static final boolean USE_81_STYLE_POLLING;
   private static final int DEFAULT_THREAD_COUNT_FOR_MDBS;
   private static final String XA_RESOURCE_NAME_PREFIX = "weblogic.ejb.container.JMSConnectionPoller.";
   private static final int POLLER_EXIT_WAIT = 60000;
   private static final String AQ_JMS_INITIAL_CONTEXT_FACTORY = "oracle.jms.AQjmsInitialContextFactory";
   private static final boolean NO_LOCAL_MSGS = false;
   private static final int WORK_MODE_ASYNC_NOTRAN = 1;
   private static final int WORK_MODE_ASYNC_2PC = 2;
   private static final int WORK_MODE_SYNC_2PC = 3;
   private static final int WORK_MODE_SYNC_NOTRAN = 4;
   private static final AuthenticatedSubject KERNEL_ID;
   private final String dispatchPolicyName;
   private final WorkManager wm;
   private final AuthenticatedSubject runAsSubject;
   private final String xaResourceName;
   private final WrappedClassManager jmsWrapperManager = new WrappedClassManager();
   private JMSMessagePoller poller;
   private JMSPollerManager pm;
   private Connection connection;
   private Session[] sessions;
   private XASession[] xaSessions;
   private MessageConsumer[] consumers;
   private Hashtable foreignJNDIEnv;
   private Hashtable foreignDestJNDIEnv;
   private Destination destination;
   private XAResource registeredResource;
   private boolean isForeign;
   private int workMode;
   private String messageSelector;
   private int acknowledgeMode;
   private boolean isAQJMS = false;
   private boolean dynamicSessionClose;
   private boolean needsContinuousPolling;
   private boolean needsContinuousPollingInitialized;
   private boolean disconnectInProgress;
   private final Object contextSubjectLock = new Object();
   private AuthenticatedSubject contextSubject;
   private String partitionName;
   private final ExceptionListener exListener = new ExceptionListenerBridge();

   public JMSConnectionPoller(MessageDrivenBeanInfo mdbi, MessageDrivenEJBRuntimeMBean mb) throws WLDeploymentException {
      super(mdbi, mb);
      this.dispatchPolicyName = this.info.getDispatchPolicy() != null ? this.info.getDispatchPolicy() : "weblogic.kernel.Default";
      this.wm = this.info.getCustomWorkManager();
      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNEL_ID);
      this.xaResourceName = "weblogic.ejb.container.JMSConnectionPoller." + ((MessageDrivenManagerIntf)this.info.getBeanManager()).getUniqueGlobalId();

      try {
         this.runAsSubject = this.info.getRunAsSubject();
      } catch (PrincipalNotFoundException var5) {
         this.runtimeMBean.setLastException(var5);
         throw new WLDeploymentException(var5.toString());
      }

      this.partitionName = ComponentInvocationContextManager.getInstance(KERNEL_ID).getCurrentComponentInvocationContext().getPartitionName();
   }

   public static boolean getCredentials(MessageDrivenBeanInfo info, StringBuffer userName, StringBuffer password) throws NotYetInitializedException, InvalidParameterException, PrincipalNotFoundException {
      if (info.getSecurityPlugin() != null) {
         String pluginClass = info.getSecurityPlugin().getPluginClass();
         Object plugin = null;

         try {
            plugin = Class.forName(pluginClass).newInstance();
         } catch (ClassNotFoundException var12) {
            EJBLogger.logPluginClassNotFound(info.getEJBName(), pluginClass);
         } catch (InstantiationException var13) {
            EJBLogger.logPluginClassInstantiationError(info.getEJBName(), pluginClass);
         } catch (IllegalAccessException var14) {
            EJBLogger.logPluginClassIllegalAccess(info.getEJBName(), pluginClass);
         }

         if (plugin instanceof SecurityPlugin) {
            String key = info.getSecurityPlugin().getKey();
            UsernameAndPassword uap = ((SecurityPlugin)plugin).getCredentials(key);
            userName.append(uap.getUsername());
            password.append(new String(uap.getPassword()));
            return true;
         } else {
            EJBLogger.logPluginClassNotImplment(info.getEJBName(), pluginClass);
            return false;
         }
      } else {
         EJBResource res = new EJBResource(info.getDeploymentInfo().getApplicationId(), info.getDeploymentInfo().getModuleId(), info.getEJBName(), "onMessage", (String)null, (String[])null);
         CredentialManager cm = (CredentialManager)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.CREDENTIALMANAGER);
         Object[] creds = cm.getCredentials(KERNEL_ID, info.getRunAsSubject(), res, (ContextHandler)null, "weblogic.UserPassword");
         boolean result = false;
         if (creds != null) {
            Object[] var7 = creds;
            int var8 = creds.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Object next = var7[var9];
               if (next instanceof PasswordCredential) {
                  PasswordCredential userPassword = (PasswordCredential)next;
                  userName.append(userPassword.getUserName());
                  password.append(userPassword.getPassword());
                  result = true;
               }
            }
         }

         return result;
      }
   }

   private Connection getXAConnection(XAConnectionFactory cf, String userName, String password, boolean hasCredentials) throws JMSException {
      if (this.info.isDestinationQueue()) {
         if (hasCredentials) {
            return (Connection)(cf instanceof XAQueueConnectionFactory ? ((XAQueueConnectionFactory)cf).createXAQueueConnection(userName, password) : cf.createXAConnection(userName, password));
         } else {
            return (Connection)(cf instanceof XAQueueConnectionFactory ? ((XAQueueConnectionFactory)cf).createXAQueueConnection() : cf.createXAConnection());
         }
      } else if (hasCredentials) {
         return (Connection)(cf instanceof XATopicConnectionFactory ? ((XATopicConnectionFactory)cf).createXATopicConnection(userName, password) : cf.createXAConnection(userName, password));
      } else {
         return (Connection)(cf instanceof XATopicConnectionFactory ? ((XATopicConnectionFactory)cf).createXATopicConnection() : cf.createXAConnection());
      }
   }

   private Connection getConnection(ConnectionFactory cf, String userName, String password, boolean hasCredentials) throws JMSException {
      if (this.info.isDestinationQueue()) {
         if (hasCredentials) {
            return (Connection)(cf instanceof QueueConnectionFactory ? ((QueueConnectionFactory)cf).createQueueConnection(userName, password) : cf.createConnection(userName, password));
         } else {
            return (Connection)(cf instanceof QueueConnectionFactory ? ((QueueConnectionFactory)cf).createQueueConnection() : cf.createConnection());
         }
      } else if (hasCredentials) {
         return (Connection)(cf instanceof TopicConnectionFactory ? ((TopicConnectionFactory)cf).createTopicConnection(userName, password) : cf.createConnection(userName, password));
      } else {
         return (Connection)(cf instanceof TopicConnectionFactory ? ((TopicConnectionFactory)cf).createTopicConnection() : cf.createConnection());
      }
   }

   private Destination getDestination(Context ctx, String destinationName) throws WLDeploymentException {
      Loggable l;
      try {
         if (this.info.isDestinationQueue()) {
            Queue q = (Queue)ctx.lookup(destinationName);
            if (q instanceof DestinationImpl && !((DestinationImpl)q).isQueue()) {
               l = EJBLogger.logJndiNameWasNotAJMSDestinationLoggable(destinationName);
               throw new WLDeploymentException(l.getMessageText());
            } else {
               return q;
            }
         } else {
            Topic t = (Topic)ctx.lookup(destinationName);
            if (t instanceof DestinationImpl && !((DestinationImpl)t).isTopic()) {
               l = EJBLogger.logJndiNameWasNotAJMSDestinationLoggable(destinationName);
               throw new WLDeploymentException(l.getMessageText());
            } else {
               return t;
            }
         }
      } catch (NamingException var5) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("NamingException looking up destination: " + var5);
         }

         l = EJBLogger.logJmsDestinationNotFoundLoggable(destinationName);
         throw new WLDeploymentException(l.getMessageText(), var5);
      } catch (ClassCastException var6) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("ClassCastException looking up destination: " + var6);
         }

         l = EJBLogger.logJndiNameWasNotAJMSDestinationLoggable(destinationName);
         throw new WLDeploymentException(l.getMessageText(), var6);
      }
   }

   void processOnException(Throwable e) {
      this.runtimeMBean.setLastException(e);
      if (debugLogger.isDebugEnabled()) {
         this.debug("JMS failure detected on destination " + this.getDestinationJndi() + ". The exception was: " + getAllExceptionText(e));
      }

      synchronized(this.stateLock) {
         if (this.state == 2) {
            this.state = 6;
         } else if (this.state == 5) {
            this.state = 7;
         }

         if (debugLogger.isDebugEnabled()) {
            this.debugState();
         }
      }

      WorkManagerFactory.getInstance().getSystem().schedule(new ProcessOnExceptionThread());
   }

   static String getAllExceptionText(Throwable throwable) {
      StringBuilder msgBuf = new StringBuilder(throwable.toString());

      for(Throwable t = throwable.getCause(); t != null; t = t.getCause()) {
         msgBuf.append(PlatformConstants.EOL);
         msgBuf.append("Nested exception: ");
         msgBuf.append(t.toString());
      }

      if (throwable instanceof JMSException) {
         JMSException jmse = (JMSException)throwable;
         if (jmse.getLinkedException() != null && jmse.getLinkedException() != throwable.getCause()) {
            msgBuf.append(PlatformConstants.EOL);
            msgBuf.append("Linked exception: ");
            msgBuf.append(getAllExceptionText(jmse.getLinkedException()));
         }
      }

      return msgBuf.toString();
   }

   protected final Transaction beginTransaction(TransactionManager txMgr, String txName, int txTimeout) throws NotSupportedException, SystemException {
      if (!IS_COMPLETION_TIMEOUT_CONFIGURED) {
         txMgr.begin(txName, txTimeout);
      } else {
         Map map = new HashMap();
         map.put("name", txName);
         map.put("transaction-timeout", txTimeout);
         map.put("completion-timeout-seconds", COMPLETION_TIMEOUT_SECONDS);
         txMgr.begin(map);
      }

      return txMgr.getTransaction();
   }

   protected void logException(Exception e) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("The Message-Driven Bean " + this.info.getEJBName() + " is unable to connect to the JMS destination " + this.mgr.getDDJNDIName() + ". The Error was: " + StackTraceUtilsClient.throwable2StackTrace(e));
      }

      if (e instanceof RuntimeException) {
         EJBLogger.logMDBUnableToConnectToJMS(this.info.getEJBName(), this.getDestinationJndi(), getAllExceptionText(e) + "Stack Trace for RuntimeException was:" + StackTraceUtilsClient.throwable2StackTrace(e));
      } else {
         EJBLogger.logMDBUnableToConnectToJMS(this.info.getEJBName(), this.getDestinationJndi(), getAllExceptionText(e));
      }

   }

   private void startMessagePollerThread() {
      if (this.poller != null) {
         this.poller.start();
         WorkManagerImpl.executeDaemonTask(this.info.getEJBName(), 10, this.poller);
      } else if (this.pm != null) {
         this.pm.start();
      }

   }

   private synchronized void stopMessagePollers() {
      if (this.poller != null) {
         this.poller.stop();
      } else if (this.pm != null) {
         this.pm.stop();
      }

   }

   private synchronized void waitForMessagePollerExit(long waitTime, boolean deletePoller) {
      if (this.poller != null) {
         synchronized(this.poller) {
            this.poller.stop();
            if (this.poller.getRunning()) {
               try {
                  this.poller.wait(waitTime);
               } catch (InterruptedException var7) {
               }
            }

            if (deletePoller) {
               this.poller = null;
            }
         }
      } else if (this.pm != null) {
         this.pm.waitForPollersToStop(waitTime);
         if (deletePoller) {
            this.pm = null;
         }
      }

   }

   protected void connect() throws WLDeploymentException, JMSException, SystemException {
      assert this.getState() != 2;

      boolean isConnectionSuspendedOnStart = false;
      boolean var9 = false;

      try {
         var9 = true;
         if (debugLogger.isDebugEnabled()) {
            this.debugState();
         }

         ++this.reconnectionCount;
         this.createJMSConnection(false);
         this.startMessagePollerThread();
         if (this.getState() != 3) {
            this.setState(2);
         }

         if (debugLogger.isDebugEnabled()) {
            this.debugWithState("Connected to JMS Destination");
         }

         if (this.mgr.shouldConnectionSuspendOnStart()) {
            isConnectionSuspendedOnStart = true;
            if (this.mgr.isConnectionSuspendOnStartPropertySet()) {
               EJBLogger.logMDBSuspendedOnDeployment(this.info.getDisplayName(), this.getDestinationJndi(), "weblogic.mdbs.suspendConnectionOnStart");
            } else {
               EJBLogger.logMDBSuspendedOnConnect(this.info.getDisplayName(), this.getDestinationJndi());
            }

            this.suspend(true);
            var9 = false;
         } else {
            this.startJMSConnection();
            var9 = false;
         }
      } catch (WLDeploymentException var10) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Failed to connect to JMS. " + var10);
         }

         throw var10;
      } catch (JMSException var11) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Failed to connect to JMS. " + getAllExceptionText(var11));
         }

         this.setState(6);
         throw var11;
      } catch (RuntimeException | SystemException var12) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Failed to connect to JMS. " + StackTraceUtilsClient.throwable2StackTrace(var12));
         }

         throw var12;
      } finally {
         if (var9) {
            boolean isJMSConnectionAlive = this.getState() == 2 || isConnectionSuspendedOnStart;
            this.runtimeMBean.setJMSConnectionAlive(isJMSConnectionAlive);
            if (isJMSConnectionAlive) {
               this.runtimeMBean.setConnectionStatus("Connected");
            } else {
               this.info.reSetUsernameAndPassword();
               this.runtimeMBean.setConnectionStatus("re-connecting");
            }

            if (this.getState() == 6) {
               this.stopMessagePollers();
            }

         }
      }

      boolean isJMSConnectionAlive = this.getState() == 2 || isConnectionSuspendedOnStart;
      this.runtimeMBean.setJMSConnectionAlive(isJMSConnectionAlive);
      if (isJMSConnectionAlive) {
         this.runtimeMBean.setConnectionStatus("Connected");
      } else {
         this.info.reSetUsernameAndPassword();
         this.runtimeMBean.setConnectionStatus("re-connecting");
      }

      if (this.getState() == 6) {
         this.stopMessagePollers();
      }

   }

   protected void disconnect(boolean checkJMSExceptions) throws JMSException {
      if (debugLogger.isDebugEnabled()) {
         this.debugWithState("Disconnect called");
      }

      synchronized(this.stateLock) {
         if (this.disconnectInProgress) {
            return;
         }

         this.disconnectInProgress = true;
      }

      boolean success = false;
      boolean var16 = false;

      try {
         var16 = true;
         this.stopMessagePollers();
         SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getRightSubject(this.getContextSubject()));
         if (this.connection != null) {
            this.stopJMSConnection();
         }

         this.waitForMessagePollerExit(60000L, true);
         if (this.getState() == 6 && this.registeredResource != null) {
            try {
               TransactionService.getWeblogicTransactionManager().unregisterResource(this.xaResourceName, true);
            } catch (Exception var19) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("Error unregistering XA resource: " + this.xaResourceName + " " + StackTraceUtilsClient.throwable2StackTrace(var19));
               }
            }
         }

         this.registeredResource = null;
         int var4;
         int var5;
         if (this.consumers != null) {
            MessageConsumer[] var3 = this.consumers;
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
               MessageConsumer consumer = var3[var5];
               this.releaseJMSResource(consumer, checkJMSExceptions);
            }

            this.consumers = null;
         }

         if (this.sessions != null) {
            Session[] var22 = this.sessions;
            var4 = var22.length;

            for(var5 = 0; var5 < var4; ++var5) {
               Session session = var22[var5];
               this.releaseJMSResource(session, checkJMSExceptions);
            }

            this.sessions = null;
         }

         if (this.xaSessions != null) {
            XASession[] var23 = this.xaSessions;
            var4 = var23.length;

            for(var5 = 0; var5 < var4; ++var5) {
               XASession xaSession = var23[var5];
               this.releaseJMSResource(xaSession, checkJMSExceptions);
            }

            this.xaSessions = null;
         }

         this.releaseJMSResource(this.connection, checkJMSExceptions);
         success = true;
         var16 = false;
      } finally {
         if (var16) {
            synchronized(this.stateLock) {
               this.disconnectInProgress = false;
               if (success) {
                  if (this.state != 3 && this.state != 4) {
                     if (this.state == 5) {
                        this.state = 7;
                     } else {
                        this.state = 1;
                     }
                  } else {
                     this.state = 4;
                  }
               }
            }

            this.destination = null;
            SecurityHelper.popRunAsSubject(KERNEL_ID);
            if (debugLogger.isDebugEnabled()) {
               this.debugState();
            }

         }
      }

      synchronized(this.stateLock) {
         this.disconnectInProgress = false;
         if (success) {
            if (this.state != 3 && this.state != 4) {
               if (this.state == 5) {
                  this.state = 7;
               } else {
                  this.state = 1;
               }
            } else {
               this.state = 4;
            }
         }
      }

      this.destination = null;
      SecurityHelper.popRunAsSubject(KERNEL_ID);
      if (debugLogger.isDebugEnabled()) {
         this.debugState();
      }

   }

   private void releaseJMSResource(Object jmsResource, boolean needThrowJmsException) throws JMSException {
      if (jmsResource != null) {
         try {
            if (jmsResource instanceof Connection) {
               ((Connection)jmsResource).close();
            }

            if (jmsResource instanceof XASession) {
               ((XASession)jmsResource).close();
            } else if (jmsResource instanceof Session) {
               ((Session)jmsResource).setMessageListener((MessageListener)null);
               ((Session)jmsResource).close();
            }

            if (jmsResource instanceof MessageConsumer) {
               ((MessageConsumer)jmsResource).close();
            }
         } catch (Exception var4) {
            if (needThrowJmsException) {
               throw EJBRuntimeUtils.asJMSException(var4.getMessage(), var4);
            }
         }
      }

   }

   public void deleteDurableSubscriber(String clientId) {
      if (this.info.isDestinationTopic() && this.info.isDurableSubscriber()) {
         clientId = this.decorateWithPartition(clientId);
         String subName = this.getSubNameWithPartition();

         try {
            this.createJMSConnection(true);
            this.startMessagePollerThread();
            if (this.connection instanceof WLConnection && this.info.getTopicMessagesDistributionMode() > 0) {
               ((WLSession)this.sessions[0]).unsubscribe((Topic)this.destination, subName);
            } else {
               this.sessions[0].unsubscribe(subName);
            }

            this.runtimeMBean.setJmsClientID("");
            EJBLogger.logMDBDurableSubscriptionDeletion(clientId, this.info.getEJBName());
         } catch (JMSException | SystemException | WLDeploymentException var12) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("Exception unsubscribing, JMS Client Id = " + clientId + ", MDB = " + this.info.getEJBName() + " - " + StackTraceUtilsClient.throwable2StackTrace(var12));
            }
         } finally {
            try {
               this.disconnect(false);
            } catch (Exception var11) {
            }

         }

      }
   }

   private void startJMSConnection() {
      if (this.getState() == 2) {
         EJBLogger.logMDBReConnectedToJMS(this.info.getEJBName(), debugLogger.isDebugEnabled() ? this.mgr.getDDJNDIName() : this.getDestinationJndi());
         if (this.connection != null) {
            try {
               SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getContextSubject());
               this.connection.start();
               this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsString(2));
            } catch (JMSException var5) {
               this.runtimeMBean.setLastException(var5);
               if (debugLogger.isDebugEnabled()) {
                  this.debug("Exception starting Connection: " + getAllExceptionText(var5));
               }

               this.setState(6);
            } finally {
               SecurityHelper.popRunAsSubject(KERNEL_ID);
            }

         }
      }
   }

   private void stopJMSConnection() {
      try {
         this.connection.stop();
      } catch (JMSException var5) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Exception stopping Connection: " + var5);
         }

         if (this.getState() == 5) {
            this.setState(7);
         }
      } finally {
         if (!this.scheduleResume) {
            this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsStringInStartCase(3) + " at " + new Date() + " by the user");
         }

      }

   }

   private int determineWorkMode() throws JMSException, WLDeploymentException {
      if (!this.info.isOnMessageTransacted()) {
         if (this.isAQJMS && this.info.getMinimizeAQSessions()) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("MDB " + this.info.getEJBName() + " will poll synchronously with no transactions");
            }

            if (!this.info.isDestinationTopic() || this.info.isDurableSubscriber()) {
               this.dynamicSessionClose = true;
            }

            return 4;
         } else {
            if (debugLogger.isDebugEnabled()) {
               this.debug("MDB " + this.info.getEJBName() + " will poll asynchronously with no transactions");
            }

            return 1;
         }
      } else if (!(this.connection instanceof XAConnection)) {
         Loggable l = EJBLogger.logproviderIsNotTransactedButMDBIsTransactedLoggable(this.info.getEJBName());
         throw new WLDeploymentException(l.getMessageText());
      } else if (!this.isAQJMS) {
         if (this.info.getMaxMessagesInTransaction() > 1) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("MDB " + this.info.getEJBName() + " will poll synchronously, two-phase transaction, multiple messages in transaction");
            }

            return 3;
         } else {
            Object tmpSession;
            if (this.connection instanceof XAQueueConnection) {
               tmpSession = ((XAQueueConnection)this.connection).createXAQueueSession();
            } else if (this.connection instanceof XATopicConnection) {
               tmpSession = ((XATopicConnection)this.connection).createXATopicSession();
            } else if (this.connection instanceof XAConnection) {
               tmpSession = ((XAConnection)this.connection).createXASession();
            } else if (this.connection instanceof QueueConnection) {
               tmpSession = ((QueueConnection)this.connection).createQueueSession(true, 1);
            } else if (this.connection instanceof TopicConnection) {
               tmpSession = ((TopicConnection)this.connection).createTopicSession(true, 1);
            } else {
               tmpSession = this.connection.createSession(true, 1);
            }

            if (tmpSession instanceof MDBTransaction) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("MDB " + this.info.getEJBName() + " will poll asynchronously using MDBTransaction");
               }

               ((Session)tmpSession).close();
               return 2;
            } else {
               ((Session)tmpSession).close();
               if (debugLogger.isDebugEnabled()) {
                  this.debug("MDB " + this.info.getEJBName() + " will poll synchronously using XA");
               }

               return 3;
            }
         }
      } else {
         if (this.info.getMinimizeAQSessions() && (!this.info.isDestinationTopic() || this.info.isDurableSubscriber())) {
            this.dynamicSessionClose = true;
         }

         return 3;
      }
   }

   private void setUpTopicSessions(int concurrentSessions, int maxConcurrentInstances) throws JMSException, SystemException {
      MDListener[] listeners = new MDListener[concurrentSessions];
      this.sessions = new Session[concurrentSessions];
      this.consumers = new MessageConsumer[concurrentSessions];
      this.xaSessions = new XASession[concurrentSessions];
      if ((this.acknowledgeMode == 1 || this.acknowledgeMode == 3) && maxConcurrentInstances > 1 && this.workMode != 4) {
         this.acknowledgeMode = 2;
      }

      if (this.workMode == 3 && maxConcurrentInstances > 1) {
         if (this.mgr.getTopicMessagesDistributionMode() == 0) {
            EJBLogger.logUsingSingleThreadForMDBTopic(this.info.getEJBName());
         }

         maxConcurrentInstances = 1;
      }

      if (this.workMode == 4 && maxConcurrentInstances > 1) {
         maxConcurrentInstances = 1;
      }

      if (!(this.connection instanceof WLConnection) && !this.info.isOnMessageTransacted() && maxConcurrentInstances > 1) {
         EJBLogger.logUsingSingleThreadForNonXAMDBTopic(this.info.getEJBName());
         maxConcurrentInstances = 1;
      }

      if (this.needsSetForwardFilter(this.mgr.getDestination())) {
         if (this.messageSelector != null && this.messageSelector.trim().length() != 0) {
            this.messageSelector = "NOT JMS_WL_DDForwarded AND (" + this.messageSelector + ")";
         } else {
            this.messageSelector = "NOT JMS_WL_DDForwarded";
         }

         if (debugLogger.isDebugEnabled()) {
            this.debug("MessageSelector on " + this.info.getEJBName() + " is : " + this.messageSelector);
         }
      }

      boolean isJMS11 = this.isTopicConnJms11();
      boolean isDestinationOnRemoteDomain = this.isDestinationOnRemoteDomain();
      boolean useAcknowledgePreviousMode = false;
      boolean syncNoTranMode = this.workMode == 4;

      for(int i = 0; i < concurrentSessions; ++i) {
         MDBSession wrappedMDBSession = this.setUpTopicSessionAt(i, isJMS11);
         if (i == 0 && this.workMode == 1 && this.acknowledgeMode == 2 && this.sessions[i] instanceof WLSession) {
            useAcknowledgePreviousMode = true;
         }

         listeners[i] = new MDListener((MDListener)null, this, maxConcurrentInstances <= 1 ? 0 : maxConcurrentInstances, this.sessions[i], wrappedMDBSession, this.acknowledgeMode, this.runtimeMBean, this.info, this.mgr, useAcknowledgePreviousMode, isDestinationOnRemoteDomain, this.wm, this.runAsSubject, syncNoTranMode);
         if (this.workMode != 3 && this.workMode != 4) {
            try {
               SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getContextSubject());
               this.consumers[i].setMessageListener(listeners[i]);
               this.connection.setExceptionListener(this.exListener);
               if (this.sessions[i] instanceof WLSession) {
                  ((WLSession)this.sessions[i]).setExceptionListener(this.exListener);
               }
            } finally {
               SecurityHelper.popRunAsSubject(KERNEL_ID);
            }
         } else {
            if (this.xaSessions != null && this.xaSessions[i] != null && !(this.xaSessions[i] instanceof WLSession) && this.registeredResource == null) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("Registering XA resource " + this.xaResourceName);
               }

               TransactionManager tm = TransactionService.getWeblogicTransactionManager();
               this.registeredResource = ((WrappedTransactionalSession)this.sessions[i]).getXAResource();
               tm.registerResource(this.xaResourceName, this.registeredResource, this.enlistmentProperties(true));
               tm.setTransactionTimeout(this.info.getTransactionTimeoutSeconds());
            }

            if (this.dynamicSessionClose) {
               assert i == 0 : "For dynamicSessionClose, there is only one session created";

               this.pm = new JMSPollerManager(this.info.getEJBName(), this, this.consumers, listeners, this.wm, false, this.destination, this.dynamicSessionClose);
            } else if (this.mgr.getTopicMessagesDistributionMode() == 0) {
               this.poller = new JMSMessagePoller(this.info.getEJBName(), this, (JMSMessagePoller)null, this.consumers[i], listeners[i], this.wm, i, this.dynamicSessionClose);
            } else if (i == concurrentSessions - 1 && (this.mgr.getTopicMessagesDistributionMode() == 2 || this.mgr.getTopicMessagesDistributionMode() == 1)) {
               this.pm = new JMSPollerManager(this.info.getEJBName(), this, this.consumers, listeners, this.wm, false, this.destination, this.dynamicSessionClose);
            }
         }
      }

   }

   private MDBSession setUpTopicSessionAt(int i, boolean isJMS11) throws JMSException {
      this.ensureJMSResourceNotNull(this.connection, "Session");
      Session wrappedMDBSession;
      switch (this.workMode) {
         case 1:
            if (isJMS11) {
               this.sessions[i] = this.connection.createSession(false, this.acknowledgeMode);
            } else {
               this.sessions[i] = ((TopicConnection)this.connection).createTopicSession(false, this.acknowledgeMode);
            }
            break;
         case 2:
            if (isJMS11) {
               this.xaSessions[i] = ((XAConnection)this.connection).createXASession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               this.sessions[i] = this.xaSessions[i].getSession();
            } else {
               this.xaSessions[i] = ((XATopicConnection)this.connection).createXATopicSession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               this.sessions[i] = ((XATopicSession)this.xaSessions[i]).getTopicSession();
            }
            break;
         case 3:
            if (isJMS11) {
               this.xaSessions[i] = ((XAConnection)this.connection).createXASession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               wrappedMDBSession = this.xaSessions[i].getSession();
               this.sessions[i] = (Session)JMSSessionPoolManager.getWrappedMDBPollerSession(wrappedMDBSession, this.xaSessions[i], 3, wrappedMDBSession instanceof WLSession, this.xaResourceName, this.jmsWrapperManager);
            } else {
               this.xaSessions[i] = ((XATopicConnection)this.connection).createXATopicSession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               TopicSession vendorSession = ((XATopicSession)this.xaSessions[i]).getTopicSession();
               this.sessions[i] = (Session)JMSSessionPoolManager.getWrappedMDBPollerSession(vendorSession, this.xaSessions[i], 2, vendorSession instanceof WLSession, this.xaResourceName, this.jmsWrapperManager);
            }
            break;
         case 4:
            if (isJMS11) {
               this.sessions[i] = this.connection.createSession(false, 2);
            } else {
               this.sessions[i] = ((TopicConnection)this.connection).createTopicSession(false, 2);
            }
            break;
         default:
            throw new IllegalArgumentException("Unknown work mode: " + this.workMode);
      }

      this.perhapsSetMessagesMaximum(this.sessions[i], i != 0);
      wrappedMDBSession = null;
      MDBSession wrappedMDBSession;
      if (isJMS11) {
         wrappedMDBSession = JMSSessionPoolManager.getWrappedMDBSession(this.sessions[i], 3, this.jmsWrapperManager);
      } else {
         wrappedMDBSession = JMSSessionPoolManager.getWrappedMDBSession(this.sessions[i], 2, this.jmsWrapperManager);
      }

      if (this.info.isDurableSubscriber()) {
         String subName = this.getSubNameWithPartition();
         if (isJMS11) {
            this.consumers[i] = this.sessions[i].createDurableSubscriber((Topic)this.destination, subName, this.messageSelector, false);
         } else {
            this.consumers[i] = ((TopicSession)this.sessions[i]).createDurableSubscriber((Topic)this.destination, subName, this.messageSelector, false);
         }
      } else if (isJMS11) {
         this.consumers[i] = this.sessions[i].createConsumer(this.destination, this.messageSelector, false);
      } else {
         this.consumers[i] = ((TopicSession)this.sessions[i]).createSubscriber((Topic)this.destination, this.messageSelector, false);
      }

      return wrappedMDBSession;
   }

   private void ensureJMSResourceNotNull(Object jmsResourceToValidate, String jmsResource2Create) throws JMSException {
      if (jmsResourceToValidate == null) {
         Loggable loggable = EJBLogger.logErrorCreatingJMSResourceLoggable(jmsResource2Create, this.info.getEJBName(), this.getDestinationJndi());
         throw new JMSException(loggable.getMessage());
      }
   }

   private void perhapsSetMessagesMaximum(Session s, boolean skipLogging) throws JMSException {
      if (this.info.getSessionMessagesMaximum() != 0) {
         if (s instanceof WrappedTransactionalSession) {
            s = (Session)((WrappedTransactionalSession)s).getVendorObj();
         }

         if (s instanceof WLSession) {
            ((WLSession)s).setMessagesMaximum(this.info.getSessionMessagesMaximum());
         } else if (!skipLogging) {
            EJBLogger.logMessagesMaximumIgnoredForNonWLJMS();
         }
      }

   }

   private void setUpQueueSessions(int numThreads) throws JMSException, SystemException {
      boolean xaUsed = this.workMode == 2 || this.workMode == 3;
      boolean syncMode = this.workMode == 3 || this.workMode == 4;
      boolean isJMS11 = this.isQueueConnJms11();
      boolean use81StylePolling = USE_81_STYLE_POLLING || this.info.getUse81StylePolling();
      boolean continuousPolling = this.needsContinuousJMSMessagePolling();
      MDListener[] listeners = syncMode && !use81StylePolling ? new MDListener[numThreads] : null;
      if (isJMS11) {
         this.sessions = new Session[numThreads];
         if (xaUsed) {
            this.xaSessions = new XASession[numThreads];
         }

         this.consumers = new MessageConsumer[numThreads];
      } else {
         this.sessions = new QueueSession[numThreads];
         if (xaUsed) {
            this.xaSessions = new XAQueueSession[numThreads];
         }

         this.consumers = new QueueReceiver[numThreads];
      }

      try {
         SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getContextSubject());
         int initializationCount = this.dynamicSessionClose ? 1 : numThreads;
         boolean isDestinationOnRemoteDomain = this.isDestinationOnRemoteDomain();
         boolean syncNoTranMode = this.workMode == 4;

         for(int i = 0; i < numThreads; ++i) {
            MDBSession wrappedMDBSession = null;
            if (i < initializationCount) {
               wrappedMDBSession = this.setUpQueueSessionAt(i, isJMS11);
            }

            MDListener mdListener = new MDListener((MDListener)null, this, 0, this.sessions[i], wrappedMDBSession, this.acknowledgeMode, this.runtimeMBean, this.info, this.mgr, false, isDestinationOnRemoteDomain, this.wm, this.runAsSubject, syncNoTranMode);
            if (syncMode) {
               if (use81StylePolling) {
                  if (continuousPolling) {
                     if (i == 0) {
                        this.poller = new ContinuousJMSMessagePoller(this.info.getEJBName(), this, (JMSMessagePoller)null, this.consumers[0], mdListener, this.wm, 0);
                     } else {
                        this.poller.addChild(new ContinuousJMSMessagePoller(this.info.getEJBName(), this, this.poller, this.consumers[i], mdListener, this.wm, i));
                     }
                  } else if (i == 0) {
                     this.poller = new JMSMessagePoller(this.info.getEJBName(), this, (JMSMessagePoller)null, this.consumers[0], mdListener, this.wm, 0, this.dynamicSessionClose);
                  } else {
                     this.poller.addChild(new JMSMessagePoller(this.info.getEJBName(), this, this.poller, this.consumers[i], mdListener, this.wm, i, this.dynamicSessionClose));
                  }
               } else {
                  listeners[i] = mdListener;
               }
            } else {
               this.consumers[i].setMessageListener(mdListener);
               if (this.sessions[i] instanceof WLSession) {
                  ((WLSession)this.sessions[i]).setExceptionListener(this.exListener);
               }
            }
         }

         if (syncMode) {
            if (this.xaSessions != null && this.xaSessions[0] != null && !(this.xaSessions[0] instanceof WLSession) && this.registeredResource == null) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("Registering XA resource " + this.xaResourceName);
               }

               TransactionManager tm = TransactionService.getWeblogicTransactionManager();
               this.registeredResource = ((WrappedTransactionalSession)this.sessions[0]).getXAResource();
               tm.registerResource(this.xaResourceName, this.registeredResource, this.enlistmentProperties(false));
               tm.setTransactionTimeout(this.info.getTransactionTimeoutSeconds());
            }

            if (!use81StylePolling) {
               this.pm = new JMSPollerManager(this.info.getEJBName(), this, this.consumers, listeners, this.wm, continuousPolling, this.destination, this.dynamicSessionClose);
            }
         } else {
            this.connection.setExceptionListener(this.exListener);
         }
      } finally {
         SecurityHelper.popRunAsSubject(KERNEL_ID);
      }

   }

   private MDBSession setUpQueueSessionAt(int i, boolean isJMS11) throws JMSException {
      this.ensureJMSResourceNotNull(this.connection, "Session");
      switch (this.workMode) {
         case 1:
            this.sessions[i] = (Session)(isJMS11 ? this.connection.createSession(false, this.acknowledgeMode) : ((QueueConnection)this.connection).createQueueSession(false, this.acknowledgeMode));
            break;
         case 2:
            if (isJMS11) {
               this.xaSessions[i] = ((XAConnection)this.connection).createXASession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               this.sessions[i] = this.xaSessions[i].getSession();
            } else {
               this.xaSessions[i] = ((XAQueueConnection)this.connection).createXAQueueSession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               this.sessions[i] = ((XAQueueSession)this.xaSessions[i]).getQueueSession();
            }
            break;
         case 3:
            if (isJMS11) {
               this.xaSessions[i] = ((XAConnection)this.connection).createXASession();
               this.ensureJMSResourceNotNull(this.xaSessions[i], "Session");
               Session vendorSession = this.xaSessions[i].getSession();
               this.sessions[i] = (Session)JMSSessionPoolManager.getWrappedMDBPollerSession(vendorSession, this.xaSessions[i], 3, vendorSession instanceof WLSession, this.xaResourceName, this.jmsWrapperManager);
            } else {
               XAQueueSession xaQueueSession = ((XAQueueConnection)this.connection).createXAQueueSession();
               this.xaSessions[i] = xaQueueSession;
               this.ensureJMSResourceNotNull(xaQueueSession, "Session");
               QueueSession vendorSession = xaQueueSession.getQueueSession();
               this.sessions[i] = (QueueSession)JMSSessionPoolManager.getWrappedMDBPollerSession(vendorSession, this.xaSessions[i], 1, vendorSession instanceof WLSession, this.xaResourceName, this.jmsWrapperManager);
            }
            break;
         case 4:
            if (isJMS11) {
               this.sessions[i] = this.connection.createSession(false, 2);
            } else {
               this.sessions[i] = ((QueueConnection)this.connection).createQueueSession(false, 2);
            }
            break;
         default:
            throw new IllegalArgumentException("Unknown work mode: " + this.workMode);
      }

      this.perhapsSetMessagesMaximum(this.sessions[i], i != 0);
      MDBSession wrappedMDBSession;
      if (isJMS11) {
         wrappedMDBSession = JMSSessionPoolManager.getWrappedMDBSession(this.sessions[i], 3, this.jmsWrapperManager);
         this.consumers[i] = this.sessions[i].createConsumer(this.destination, this.messageSelector);
      } else {
         wrappedMDBSession = JMSSessionPoolManager.getWrappedMDBSession(this.sessions[i], 1, this.jmsWrapperManager);
         this.consumers[i] = ((QueueSession)this.sessions[i]).createReceiver((Queue)this.destination, this.messageSelector);
      }

      return wrappedMDBSession;
   }

   MessageConsumer reCreateMessageConsumer(Destination dest, int i) throws JMSException {
      String ms = this.consumers[i].getMessageSelector();
      this.consumers[i].close();
      if (!(this.connection instanceof QueueConnection) && !(this.connection instanceof XAQueueConnection)) {
         this.consumers[i] = this.sessions[i].createConsumer(dest, ms);
      } else {
         this.consumers[i] = ((QueueSession)this.sessions[i]).createReceiver((Queue)dest, ms);
      }

      return this.consumers[i];
   }

   private boolean needsSetForwardFilter(DestinationDetail dd) {
      return this.info.getDistributedDestinationConnection() == 1 && dd.getType() == 5 || this.info.getTopicMessagesDistributionMode() == 2 && dd.getType() == 5;
   }

   void dynamicCloseSession(int index) throws JMSException {
      if (!this.dynamicSessionClose) {
         throw new IllegalStateException();
      } else {
         MessageConsumer mc = this.consumers[index];
         this.consumers[index] = null;
         Session sess = this.sessions[index];
         this.sessions[index] = null;
         XASession xasess = null;
         if (this.xaSessions != null) {
            xasess = this.xaSessions[index];
            this.xaSessions[index] = null;
         }

         Throwable closeExp = null;

         try {
            if (mc != null) {
               mc.close();
            }
         } catch (Throwable var7) {
            closeExp = var7;
         }

         try {
            if (sess != null) {
               sess.close();
            }
         } catch (Throwable var9) {
            if (closeExp == null) {
               closeExp = var9;
            }
         }

         try {
            if (xasess != null) {
               xasess.close();
            }
         } catch (Throwable var8) {
            if (closeExp == null) {
               closeExp = var8;
            }
         }

         if (closeExp != null) {
            if (closeExp instanceof JMSException) {
               throw (JMSException)closeExp;
            } else {
               JMSException wrapit = new JMSException("Error closing JMS session");
               wrapit.initCause(closeExp);
               throw wrapit;
            }
         }
      }
   }

   CreateSessionResult dynamicCreateSession(int index) throws JMSException {
      if (!this.dynamicSessionClose) {
         throw new IllegalStateException();
      } else {
         assert this.consumers[index] == null : "MessageConsumer at index " + index + " is not null";

         assert this.sessions[index] == null : "Session at index " + index + " is not null";

         assert this.consumers[index] == null : "XASession at index " + index + " is not null";

         MDBSession wrappedMDBSession = null;

         try {
            wrappedMDBSession = this.info.isDestinationQueue() ? this.setUpQueueSessionAt(index, this.isQueueConnJms11()) : this.setUpTopicSessionAt(index, this.isTopicConnJms11());
         } finally {
            if (wrappedMDBSession == null) {
               try {
                  this.dynamicCloseSession(index);
               } catch (Exception var9) {
               }
            }

         }

         return new CreateSessionResult(this.sessions[index], wrappedMDBSession, this.consumers[index]);
      }
   }

   private boolean needsContinuousJMSMessagePolling() {
      if (!this.needsContinuousPollingInitialized) {
         String connectionClassName = this.connection.getClass().getName();
         if (debugLogger.isDebugEnabled()) {
            this.debug("Connection class: " + connectionClassName);
         }

         if (!connectionClassName.startsWith("com.tibco.tibjms") && !connectionClassName.startsWith("progress.message.jimpl.xa")) {
            try {
               String providerName = this.connection.getMetaData().getJMSProviderName().toLowerCase(Locale.ENGLISH);
               if (debugLogger.isDebugEnabled()) {
                  this.debug("JMS Provider name: " + providerName);
               }

               if (providerName != null && providerName.contains("tibco")) {
                  this.needsContinuousPolling = true;
               } else if (PROVIDERS_NEED_CONTINUOUS_POLLING != null && PROVIDERS_NEED_CONTINUOUS_POLLING.toLowerCase(Locale.ENGLISH).contains(providerName)) {
                  this.needsContinuousPolling = true;
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("weblogic.mdb.JMSProviders.NeedContinuousPolling system property contains provider: " + providerName);
                  }
               }
            } catch (Throwable var3) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("provider name not found. getMetaData may not implemented.");
               }
            }
         } else {
            this.needsContinuousPolling = true;
         }

         if (debugLogger.isDebugEnabled()) {
            this.debug("needsContinuousPolling :" + this.needsContinuousPolling);
         }

         this.needsContinuousPollingInitialized = true;
      }

      return this.needsContinuousPolling;
   }

   private void createJMSConnection(boolean forUnsubscribe) throws WLDeploymentException, JMSException, SystemException {
      if (!this.info.isDestinationQueue() && !this.info.isDestinationTopic()) {
         throw new AssertionError("Unkown JMS Destination type");
      } else {
         if (this.connection != null) {
            SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getRightSubject(this.getContextSubject()));

            try {
               this.connection.close();
            } catch (Exception var64) {
            } finally {
               SecurityHelper.popRunAsSubject(KERNEL_ID);
               this.connection = null;
            }
         }

         if (debugLogger.isDebugEnabled()) {
            this.debug("Creating JMS Connection for MDB " + this.info.getEJBName() + " with acknowledge mode = " + this.mgr.getMessageSelector() + ", Transacted = " + this.info.isOnMessageTransacted() + ", Bean-managed = " + this.info.usesBeanManagedTx());
         }

         this.messageSelector = this.mgr.getMessageSelector();
         this.acknowledgeMode = this.info.getAcknowledgeMode();
         int maxConcurrentInstances = this.calculateMaxConcurrentInstances();
         Context initialContext = null;
         int concurrentSessions = 1;

         try {
            SecurityHelper.pushRunAsSubject(KERNEL_ID, this.runAsSubject);
            StringBuffer userName = new StringBuffer();
            StringBuffer password = new StringBuffer();
            boolean hasCredentials = false;

            try {
               hasCredentials = getCredentials(this.info, userName, password);
            } catch (Exception var63) {
            }

            String destinationJndiName = this.getDestinationJndi();
            String connFactoryJndiName = this.mgr.getConnectionFactoryJNDIName();

            try {
               if (hasCredentials) {
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("Obtained credentials using Credential Mapper for user " + userName.toString());
                  }

                  initialContext = this.info.getInitialContext(userName.toString(), password.toString());
                  synchronized(this.contextSubjectLock) {
                     this.contextSubject = this.getRightSubject(this.info.getSubject());
                  }
               } else if (this.mgr.getProviderURL() == null || this.mgr.getProviderURL().trim().length() == 0) {
                  hasCredentials = this.getForeignJMSCredentials(userName, password);
                  if (hasCredentials && debugLogger.isDebugEnabled()) {
                     this.debug("Obtained credentials using ForeignJMSConnectionFactoryMBean for user " + userName.toString());
                  }

                  connFactoryJndiName = this.getRemoteJndiName(connFactoryJndiName);
                  destinationJndiName = this.getRemoteJndiName(destinationJndiName);
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("DestinationJNDIName for foreignJNDI " + destinationJndiName + " connFactoryJndiName " + connFactoryJndiName);
                  }

                  initialContext = this.getForeignContext(this.foreignJNDIEnv, true);
               }

               if (initialContext == null && !this.isForeign() || this.isConfiguredAQJMSForeignServer()) {
                  initialContext = this.getInitialContextFromInfo();
                  connFactoryJndiName = this.mgr.getConnectionFactoryJNDIName();
                  destinationJndiName = this.getDestinationJndi();
                  synchronized(this.contextSubjectLock) {
                     this.contextSubject = this.getRightSubject(this.info.getSubject());
                  }
               }

               if (initialContext == null) {
                  throw new NamingException("JNDI Environment is unavailable");
               }
            } catch (NamingException var70) {
               Loggable l = EJBLogger.logFailedJNDIContextToJMSProviderLoggable();
               throw new WLDeploymentException(l.getMessageText(), var70);
            }

            SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getContextSubject());

            try {
               if (!this.isForeignJMSVendor(this.mgr.getDestination()) && !this.mgr.isNonDDMD()) {
                  this.destination = this.mgr.getDestination().getDestination();
               } else {
                  Context destInitialContext = initialContext;
                  if (this.foreignDestJNDIEnv != null && !this.isConfiguredAQJMSForeignServer()) {
                     destInitialContext = this.getForeignContext(this.foreignDestJNDIEnv, false);
                  }

                  this.destination = this.getDestination(destInitialContext, destinationJndiName);
                  if (!destInitialContext.equals(initialContext)) {
                     this.closeQuietly(destInitialContext);
                  }
               }

               HostID hostId = null;
               if (this.destination instanceof DestinationImpl) {
                  hostId = ThreadPreferredHost.get();
                  ThreadPreferredHost.set(JMSServerUtilities.getHostId(initialContext, (DestinationImpl)this.destination));
               }

               if (connFactoryJndiName.equals("java:comp/DefaultJMSConnectionFactory")) {
                  connFactoryJndiName = DefaultNames.PLATFORM_DEFAULT.getJndiName();
               }

               try {
                  Loggable l;
                  try {
                     Object obj = initialContext.lookup(connFactoryJndiName);
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("Looked up ConnectionFactory in JNDI named: " + connFactoryJndiName + ", found an object of class " + obj.getClass());
                     }

                     if (obj instanceof ObjectBasedSecurityAware && ((ObjectBasedSecurityAware)obj).isOBSEnabled()) {
                        if (debugLogger.isDebugEnabled()) {
                           this.debug("The JMS connection factory with JNDI name " + connFactoryJndiName + " is object-based-security enabled and is not supported by MDB.");
                        }

                        throw new WLDeploymentException(EJBLogger.logUnsupportedOBSEnabledCFLoggable(this.info.getEJBName(), connFactoryJndiName).getMessage());
                     }

                     if (obj instanceof PooledConnectionFactory) {
                        l = EJBLogger.logJndiNameWasPooledConnectionFactoryLoggable(this.info.getEJBName(), connFactoryJndiName);
                        throw new WLDeploymentException(l.getMessage());
                     }

                     if (this.supportsMultipleConnections(this.mgr.getDestination())) {
                        if (debugLogger.isDebugEnabled()) {
                           this.debug("Under this mode, container creates" + maxConcurrentInstances + " connections to destination " + destinationJndiName);
                        }

                        concurrentSessions = maxConcurrentInstances;
                        maxConcurrentInstances = 1;
                     }

                     try {
                        if (!(obj instanceof XAConnectionFactory) && !(obj instanceof XAQueueConnectionFactory) && !(obj instanceof XATopicConnectionFactory)) {
                           if (!(obj instanceof ConnectionFactory) && !(obj instanceof QueueConnectionFactory) && !(obj instanceof TopicConnectionFactory)) {
                              l = EJBLogger.logJndiNameWasNotAJMSConnectionFactoryLoggable(connFactoryJndiName);
                              throw new WLDeploymentException(l.getMessageText());
                           }

                           if (this.info.isOnMessageTransacted()) {
                              l = EJBLogger.logJndiNameWasNotAXAJMSConnectionFactoryLoggable(connFactoryJndiName);
                              throw new WLDeploymentException(l.getMessage());
                           }

                           this.connection = this.getConnection((ConnectionFactory)obj, userName.toString(), password.toString(), hasCredentials);
                        } else {
                           this.connection = this.getXAConnection((XAConnectionFactory)obj, userName.toString(), password.toString(), hasCredentials);
                        }
                     } catch (JMSException var66) {
                        if (debugLogger.isDebugEnabled()) {
                           this.debug("Error creating connection - " + var66);
                        }

                        Loggable l = EJBLogger.logJmsExceptionWhileCreatingConnectionLoggable();
                        throw new WLDeploymentException(l.getMessageText(), var66);
                     }
                  } catch (NamingException var67) {
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("NamingException looking up connection factory: " + var67);
                     }

                     l = EJBLogger.logJmsConnectionFactoryNotFoundLoggable(connFactoryJndiName);
                     throw new WLDeploymentException(l.getMessageText(), var67);
                  }
               } finally {
                  if (this.destination instanceof DestinationImpl) {
                     ThreadPreferredHost.set(hostId);
                  }

               }

               if (this.info.isDestinationTopic()) {
                  String clientId = this.decorateWithPartition(this.mgr.getJMSClientId());
                  if (this.connection instanceof WLConnection && this.mgr.getTopicMessagesDistributionMode() > 0 && this.mgr.getDestination().isAdvancedTopicSupported()) {
                     WLConnection wlc = (WLConnection)this.connection;
                     if (this.supportsMultipleConnections(this.mgr.getDestination())) {
                        wlc.setClientID(clientId, WLConnection.CLIENT_ID_POLICY_UNRESTRICTED);
                        wlc.setSubscriptionSharingPolicy(WLConnection.SUBSCRIPTION_SHARABLE);
                     } else {
                        wlc.setClientID(clientId, WLConnection.CLIENT_ID_POLICY_RESTRICTED);
                        wlc.setSubscriptionSharingPolicy(WLConnection.SUBSCRIPTION_EXCLUSIVE);
                     }

                     this.runtimeMBean.setJmsClientID(clientId);
                  } else if (this.info.isDurableSubscriber()) {
                     this.runtimeMBean.setJmsClientID(clientId);
                     if (this.connection.getClientID() == null) {
                        this.connection.setClientID(clientId);
                     }
                  }
               }

               if (!forUnsubscribe) {
                  this.isAQJMS = this.connection != null && this.connection.getClass().getName().startsWith("oracle.jms");
                  this.workMode = this.determineWorkMode();
                  if (this.connection instanceof WLConnection) {
                     ((WLConnection)this.connection).setDispatchPolicy(this.dispatchPolicyName);
                     ((WLConnection)this.connection).setReconnectPolicy(JMSConstants.RECONNECT_POLICY_NONE);
                  } else if (!WorkManagerHelper.isDefault(this.wm) && this.info.isDestinationQueue() && (this.workMode == 1 || this.workMode == 2)) {
                     EJBLogger.logMDBDispatchPolicyIgnored(this.info.getEJBName(), this.dispatchPolicyName);
                  }

                  if (this.info.isDestinationTopic()) {
                     this.setUpTopicSessions(concurrentSessions, maxConcurrentInstances);
                  } else {
                     this.setUpQueueSessions(maxConcurrentInstances);
                  }

                  this.connection = JMSConnectionHelper.getXAConnectionToUse(this.connection);
                  return;
               }

               this.sessions = (Session[])(this.connection instanceof TopicConnection ? new TopicSession[]{((TopicConnection)this.connection).createTopicSession(false, this.acknowledgeMode)} : new Session[]{this.connection.createSession(false, this.acknowledgeMode)});
            } finally {
               SecurityHelper.popRunAsSubject(KERNEL_ID);
            }
         } finally {
            if (initialContext != null) {
               SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getContextSubject());
               this.closeQuietly(initialContext);
               SecurityHelper.popRunAsSubject(KERNEL_ID);
            }

            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

      }
   }

   boolean isAQJMS() {
      return this.isAQJMS;
   }

   private boolean supportsMultipleConnections(DestinationDetail dd) {
      if (dd == null) {
         return false;
      } else {
         int type = dd.getType();
         return (type == 6 || type == 1 || type == 5) && (this.info.getTopicMessagesDistributionMode() == 2 || this.info.getTopicMessagesDistributionMode() == 1);
      }
   }

   private int calculateMaxConcurrentInstances() {
      boolean isDefaultWM = WorkManagerHelper.isDefault(this.wm);
      MaxThreadsConstraint maxThreadsConstraint = !isDefaultWM ? WorkManagerHelper.getMaxThreadsConstraint(this.wm) : null;
      int maxConcurrentInstances;
      if (maxThreadsConstraint != null) {
         maxConcurrentInstances = maxThreadsConstraint.getCount();
         if (debugLogger.isDebugEnabled()) {
            this.debug("Custom WorkManager with MaxThreadsConstraint is specified, use MaxThreadsConstraint of maxConcurrentInstances=" + maxConcurrentInstances);
         }
      } else if (!isDefaultWM && this.wm.getType() == 2) {
         maxConcurrentInstances = this.wm.getConfiguredThreadCount();
         if (debugLogger.isDebugEnabled()) {
            this.debug("Custom ExecuteThread is specified, use ExecuteThreadCount maxConcurrentInstances=" + maxConcurrentInstances);
         }
      } else if (this.wm.getType() == 2) {
         maxConcurrentInstances = this.wm.getConfiguredThreadCount() / 2 + 1;
         if (debugLogger.isDebugEnabled()) {
            this.debug("Default ExecuteThread, use ExecuteThreadCount/2+1 maxConcurrentInstances=" + maxConcurrentInstances);
         }
      } else {
         maxConcurrentInstances = DEFAULT_THREAD_COUNT_FOR_MDBS;
         if (debugLogger.isDebugEnabled()) {
            this.debug("No custom ExecuteThread or WorkManager with MaxThreadsConstraint is specified, use default thread size maxConcurrentInstances=" + maxConcurrentInstances);
         }
      }

      if (this.info.getMaxConcurrentInstances() < maxConcurrentInstances && debugLogger.isDebugEnabled()) {
         this.debug("Thread size is limited by max-beans-in-free-pool, new maxConcurrentInstances=" + maxConcurrentInstances);
      }

      maxConcurrentInstances = maxConcurrentInstances == -1 ? 16 : maxConcurrentInstances;
      return Math.min(this.info.getMaxConcurrentInstances(), maxConcurrentInstances);
   }

   public synchronized boolean suspend(boolean byUser) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("JMS connection for MDB " + this.info.getEJBName() + " is suspending");
      }

      synchronized(this.stateLock) {
         if (this.state == 2) {
            this.state = 5;
         } else if (this.state != 3 && this.state != 4 && this.state != 5) {
            this.state = 7;
         }
      }

      this.stopMessagePollers();
      Timer t = this.timer;
      if (t != null) {
         t.cancel();
      }

      this.timer = null;
      if (byUser && this.scheduleResume) {
         this.initDeliveryFailureParams();
         t = this.timer;
         if (t != null) {
            t.cancel();
         }

         this.timer = null;
      }

      this.waitForMessagePollerExit(60000L, false);
      if (this.connection != null) {
         try {
            SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getRightSubject(this.getContextSubject()));
            this.stopJMSConnection();
         } finally {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }
      }

      if (debugLogger.isDebugEnabled()) {
         this.debugState();
      }

      this.runtimeMBean.incrementSuspendCount();
      return this.getState() != 7;
   }

   public synchronized boolean resume(boolean byUser) {
      return this.resume(byUser, false);
   }

   public synchronized boolean resume(boolean byUser, boolean fromContainerInternal) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("JMSConnectionPoller.resume(" + byUser + ", " + fromContainerInternal + ") for MDB " + this.info.getEJBName());
      }

      if (!fromContainerInternal) {
         this.info.disableManagerStartSuspended();
         if (byUser) {
            this.mgr.disableCheckMDBS_TO_SUSPEND_ON_STARTUP();
         }
      }

      if (byUser && this.scheduleResume) {
         this.initDeliveryFailureParams();
         this.timer.cancel();
         this.timer = null;
      }

      if (debugLogger.isDebugEnabled()) {
         this.debugState();
      }

      int newState;
      synchronized(this.stateLock) {
         if (this.state == 4) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("Stopped resuming undeployed JMS connection for MDB " + this.info.getEJBName());
            }

            return false;
         }

         if (this.state == 5 && this.connection != null) {
            this.state = 2;
         } else {
            this.state = 6;
         }

         newState = this.state;
      }

      this.startJMSConnection();
      if (newState != 2) {
         this.scheduleReconnection();
      } else {
         this.startMessagePollerThread();
      }

      if (debugLogger.isDebugEnabled()) {
         this.debugState();
      }

      return this.getState() == 2;
   }

   private boolean getForeignJMSCredentials(StringBuffer userNameBuf, StringBuffer passwdBuf) {
      try {
         Object obj = this.lookupForeignObject(this.mgr.getConnectionFactoryJNDIName());
         boolean foundCreds = false;
         SecurityManager.pushSubject(KERNEL_ID, KERNEL_ID);

         try {
            if (obj instanceof ForeignOpaqueReference) {
               ForeignOpaqueReference fOpqRef = (ForeignOpaqueReference)obj;
               if (fOpqRef.isFactory()) {
                  if (!this.isEmpty(fOpqRef.getUsername())) {
                     foundCreds = true;
                     userNameBuf.append(fOpqRef.getUsername());
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("Found credentials for connection factory with username " + userNameBuf.toString());
                     }
                  }

                  if (!this.isEmpty(fOpqRef.getPassword())) {
                     foundCreds = true;
                     passwdBuf.append(fOpqRef.getPassword());
                  }
               }
            }
         } finally {
            SecurityManager.popSubject(KERNEL_ID);
         }

         if (!foundCreds && debugLogger.isDebugEnabled()) {
            this.debug("No credentials associated with foreign jms connection factory");
         }

         return foundCreds;
      } catch (NamingException var10) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Error getting credentials associated with foreign jmsconnectionfactory: " + StackTraceUtilsClient.throwable2StackTrace(var10));
         }

         return false;
      }
   }

   private boolean isConfiguredAQJMSForeignServer() {
      return this.foreignJNDIEnv != null && "oracle.jms.AQjmsInitialContextFactory".equals(this.foreignJNDIEnv.get("java.naming.factory.initial"));
   }

   private String getRemoteJndiName(String jndiName) {
      String foreignJNDIName = jndiName;

      try {
         Object obj = this.lookupForeignObject(jndiName);
         if (obj instanceof ForeignOpaqueReference) {
            ForeignOpaqueReference fOpqRef = (ForeignOpaqueReference)obj;
            foreignJNDIName = fOpqRef.getRemoteJNDIName();
            if (this.foreignJNDIEnv == null && fOpqRef.isFactory()) {
               this.foreignJNDIEnv = fOpqRef.getJNDIEnvironment();
               this.isForeign = true;
            } else if (this.foreignDestJNDIEnv == null && !fOpqRef.isFactory()) {
               this.foreignDestJNDIEnv = fOpqRef.getJNDIEnvironment();
               this.isForeign = true;
               if (this.foreignJNDIEnv == null) {
                  this.foreignJNDIEnv = this.foreignDestJNDIEnv;
               }
            }
         } else if (debugLogger.isDebugEnabled()) {
            this.debug("Cannot find foreign JMS jndi name for " + jndiName);
         }
      } catch (NamingException var5) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Cannot get foreign JMS jndi name " + StackTraceUtilsClient.throwable2StackTrace(var5));
         }
      }

      return foreignJNDIName;
   }

   private boolean isForeign() {
      return this.isForeign && this.foreignJNDIEnv != null;
   }

   private AuthenticatedSubject getCurrentSubject() {
      return SecurityManager.getCurrentSubject(KERNEL_ID);
   }

   private Context getForeignContext(Hashtable props, boolean setContextSubject) {
      if (props == null) {
         return null;
      } else {
         Context foreignCtx = null;
         SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getRightSubject(this.getCurrentSubject()));

         try {
            foreignCtx = new InitialContext(props);
            synchronized(this.contextSubjectLock) {
               if (setContextSubject) {
                  this.contextSubject = this.getRightSubject(this.getCurrentSubject());
               }
            }
         } catch (NamingException var11) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("Cannot get initial context using foreign JNDI properties " + props + ". " + StackTraceUtilsClient.throwable2StackTrace(var11));
            }
         } finally {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

         return foreignCtx;
      }
   }

   private Context getInitialContextFromInfo() throws NamingException {
      SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getRightSubject(this.getCurrentSubject()));

      Context var1;
      try {
         var1 = this.info.getInitialContext();
      } finally {
         SecurityHelper.popRunAsSubject(KERNEL_ID);
      }

      return var1;
   }

   protected int getMaxMessagesInTransaction() {
      return !this.info.isOnMessageTransacted() ? 1 : this.info.getMaxMessagesInTransaction();
   }

   private boolean isDestinationOnRemoteDomain() {
      try {
         return CrossDomainSecurityManager.getCrossDomainSecurityUtil().isRemoteDomain(this.mgr.getProviderURL());
      } catch (IOException var2) {
         return true;
      }
   }

   private AuthenticatedSubject getRightSubject(AuthenticatedSubject subject) {
      return subject != null && !SecurityServiceManager.isKernelIdentity(subject) && !SecurityServiceManager.isServerIdentity(subject) && (!this.info.getIsRemoteSubjectExists() || !subject.equals(this.runAsSubject)) ? subject : (AuthenticatedSubject)CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.mgr.getProviderURL(), subject);
   }

   private Object lookupForeignObject(String jndiName) throws NamingException {
      Context context = this.getInitialContextFromInfo();
      SecurityHelper.pushRunAsSubject(KERNEL_ID, this.getRightSubject(this.info.getSubject()));

      Object var3;
      try {
         var3 = context.lookupLink(jndiName);
      } finally {
         this.closeQuietly(context);
         SecurityHelper.popRunAsSubject(KERNEL_ID);
      }

      return var3;
   }

   private AuthenticatedSubject getContextSubject() {
      synchronized(this.contextSubjectLock) {
         return this.contextSubject;
      }
   }

   Object doPrivilegedJMSAction(PrivilegedExceptionAction pea) throws JMSException {
      AuthenticatedSubject subject = this.getRightSubject(this.getContextSubject());

      try {
         return subject.doAs(KERNEL_ID, pea);
      } catch (PrivilegedActionException var4) {
         if (var4.getCause() instanceof JMSException) {
            throw (JMSException)var4.getCause();
         } else {
            throw new AssertionError(var4);
         }
      }
   }

   private boolean isForeignJMSVendor(DestinationDetail dd) {
      return dd.getType() == 2 || dd.getType() == 3;
   }

   private Properties enlistmentProperties(boolean isDynamic) {
      Properties p = new Properties();
      p.setProperty("weblogic.transaction.registration.type", isDynamic ? "dynamic" : "standard");
      p.setProperty("weblogic.transaction.registration.settransactiontimeout", Boolean.TRUE.toString());
      return p;
   }

   private boolean isQueueConnJms11() {
      return this.isAQJMS || !(this.connection instanceof QueueConnection) && !(this.connection instanceof XAQueueConnection);
   }

   private boolean isTopicConnJms11() {
      return this.isAQJMS || !(this.connection instanceof TopicConnection) && !(this.connection instanceof XATopicConnection);
   }

   private boolean isEmpty(String s) {
      return s == null || s.length() == 0;
   }

   private void closeQuietly(Context ctx) {
      try {
         ctx.close();
      } catch (NamingException var3) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Exception closing Context - " + StackTraceUtilsClient.throwable2StackTrace(var3));
         }
      }

   }

   private void debug(String s) {
      debugLogger.debug("[JMSConnectionPoller] " + s);
   }

   private String decorateWithPartition(String value) {
      return !this.partitionName.equalsIgnoreCase("DOMAIN") && !this.info.isTopicSubscriptionSharingInPartitions() ? value + "_" + this.partitionName : value;
   }

   private String getSubNameWithPartition() {
      return this.connection instanceof WLConnection ? this.mgr.getSubscriberName() : this.decorateWithPartition(this.mgr.getSubscriberName());
   }

   static {
      COMPLETION_TIMEOUT_SECONDS = !IS_COMPLETION_TIMEOUT_CONFIGURED ? 0 : Integer.getInteger("weblogic.CompletionTimeoutSecondsOverride");
      PROVIDERS_NEED_CONTINUOUS_POLLING = System.getProperty("weblogic.mdb.JMSProviders.NeedContinuousPolling");
      USE_81_STYLE_POLLING = Boolean.getBoolean("weblogic.mdb.message.81StylePolling");
      DEFAULT_THREAD_COUNT_FOR_MDBS = Integer.getInteger("weblogic.mdb.DefaultThreadCount", 16);
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private class ProcessOnExceptionThread implements Runnable {
      private ProcessOnExceptionThread() {
      }

      public void run() {
         if (MDConnectionManager.debugLogger.isDebugEnabled()) {
            JMSConnectionPoller.this.debugState();
         }

         if (JMSConnectionPoller.this.getState() == 6) {
            JMSConnectionPoller.this.stopMessagePollers();
         }

         if (JMSConnectionPoller.this.getState() != 3) {
            Thread currentThread = Thread.currentThread();
            ClassLoader clSave = currentThread.getContextClassLoader();
            currentThread.setContextClassLoader(JMSConnectionPoller.this.info.getClassLoader());

            try {
               JMSConnectionPoller.this.scheduleReconnection();
            } finally {
               currentThread.setContextClassLoader(clSave);
            }
         }

      }

      // $FF: synthetic method
      ProcessOnExceptionThread(Object x1) {
         this();
      }
   }

   static final class CreateSessionResult {
      final Session session;
      final MDBSession wrappedSession;
      final MessageConsumer consumer;

      CreateSessionResult(Session s, MDBSession wrappedSession, MessageConsumer mc) {
         this.session = s;
         this.wrappedSession = wrappedSession;
         this.consumer = mc;
      }
   }

   private final class ExceptionListenerBridge implements ExceptionListener {
      private ExceptionListenerBridge() {
      }

      public void onException(JMSException e) {
         JMSConnectionPoller.this.processOnException(e);
      }

      // $FF: synthetic method
      ExceptionListenerBridge(Object x1) {
         this();
      }
   }
}
