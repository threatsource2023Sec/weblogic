package weblogic.jms.frontend;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.jms.ConnectionConsumer;
import javax.jms.IllegalStateException;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEBrowserCreateRequest;
import weblogic.jms.backend.BEConnection;
import weblogic.jms.backend.BEConnectionConsumerCloseRequest;
import weblogic.jms.backend.BEConnectionConsumerCommon;
import weblogic.jms.backend.BEConnectionConsumerCreateRequest;
import weblogic.jms.backend.BEConnectionStartRequest;
import weblogic.jms.backend.BEConnectionStopRequest;
import weblogic.jms.backend.BEDestinationCreateRequest;
import weblogic.jms.backend.BEServerSessionPoolCreateRequest;
import weblogic.jms.backend.BETemporaryDestinationDestroyRequest;
import weblogic.jms.client.JMSConnectionConsumer;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DestinationPeerGoneAdapter;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSConnectionConsumerCreateResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationCreateResponse;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPeerGoneListener;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.PeerVersionable;
import weblogic.jms.common.ServerSessionPoolHelper;
import weblogic.jms.common.SingularAggregatable;
import weblogic.jms.common.SingularAggregatableManager;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JMSSessionRuntimeMBean;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;

public final class FEConnection implements JMSPeerGoneListener, Invocable, PeerVersionable {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final long serialVersionUID = -3238866726804665330L;
   private long startStopSequenceNumber;
   private final HashMap temporaryDestinations = new HashMap();
   private final HashMap connectionConsumers = new HashMap();
   private final HashMap sessions = new HashMap();
   private final HashMap browsers = new HashMap();
   public static final String JMS_CONNECTION_CLIENTID = "weblogic.jms.connection.clientid";
   private boolean allowedToSetClientId = true;
   private boolean closed = false;
   private JMSDispatcher clientDispatcher;
   private final FEConnectionFactory connectionFactory;
   private final String mbeanName;
   private final JMSID connectionId;
   private String clientId;
   private int clientIdPolicy;
   private int subscriptionSharingPolicy;
   private final int deliveryMode;
   private final FrontEnd frontEnd;
   private final int priority;
   private final long timeToDeliver;
   private final long timeToLive;
   private final long sendTimeout;
   private final long redeliveryDelay;
   private final boolean userTransactionsEnabled;
   private final boolean allowCloseInOnMessage;
   private final long transactionTimeout;
   private final int messagesMaximum;
   private final int overrunPolicy;
   private final int acknowledgePolicy;
   private final boolean loadBalancingEnabled;
   private final int producerLoadBalancingPolicy;
   private final boolean serverAffinityEnabled;
   private final String unitOfOrder;
   private final boolean isLocal;
   private boolean stopped = true;
   private String clientAddress = null;
   private static TransactionManager tranManager;
   private final InvocableMonitor invocableMonitor;
   private int state = 0;
   private byte peerVersion;
   private FEConnectionRuntimeDelegate myRuntimeDelegate;
   private int compressionThreshold;
   private int producerLoadBalancingPolicyOverride = -99;
   private static HashSet overrideLogs = new HashSet(0);
   private transient int refCount;

   public final int getCompressionThreshold() {
      return this.compressionThreshold;
   }

   public FEConnection(FEConnectionFactory connectionFactory, String mbeanName, JMSID connectionId, JMSDispatcher clientDispatcher, int deliveryMode, int priority, long timeToDeliver, long timeToLive, long sendTimeout, long redeliveryDelay, String clientId, int clientIdPolicy, int subscriptionSharingPolicy, long transactionTimeout, boolean userTransactionsEnabled, boolean allowCloseInOnMessage, int messagesMaximum, int overrunPolicy, int acknowledgePolicy, boolean loadBalancingEnabled, int producerLoadBalancingPolicy, boolean serverAffinityEnabled, String unitOfOrder, int compressionThreshold) throws JMSException, ManagementException {
      this.connectionFactory = connectionFactory;
      this.frontEnd = connectionFactory.getFrontEnd();
      this.mbeanName = mbeanName;
      this.connectionId = connectionId;
      this.clientDispatcher = clientDispatcher;
      this.deliveryMode = deliveryMode;
      this.priority = priority;
      this.timeToDeliver = timeToDeliver;
      this.timeToLive = timeToLive;
      this.sendTimeout = sendTimeout;
      this.redeliveryDelay = redeliveryDelay;
      this.transactionTimeout = transactionTimeout;
      this.userTransactionsEnabled = userTransactionsEnabled;
      this.allowCloseInOnMessage = allowCloseInOnMessage;
      this.messagesMaximum = messagesMaximum;
      this.overrunPolicy = overrunPolicy;
      this.acknowledgePolicy = acknowledgePolicy;
      this.loadBalancingEnabled = loadBalancingEnabled;
      this.producerLoadBalancingPolicy = producerLoadBalancingPolicy;
      this.serverAffinityEnabled = serverAffinityEnabled;
      this.unitOfOrder = unitOfOrder;
      this.compressionThreshold = compressionThreshold;
      this.clientIdPolicy = clientIdPolicy;
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
      if (clientId != null) {
         this.setConnectionClientId(clientId);
      }

      this.setProducerLoadBalancingPolicyOverride();
      this.invocableMonitor = connectionFactory.getInvocableMonitor();
      clientDispatcher.addDispatcherPeerGoneListener(this);
      if (clientDispatcher.isLocal()) {
         this.isLocal = true;
         this.clientAddress = "local";
      } else {
         this.isLocal = false;
         this.clientAddress = ServerHelper.getClientAddress();
      }

      synchronized(this) {
         if (tranManager == null) {
            tranManager = TxHelper.getTransactionManager();
         }
      }

      final String fmbeanName = mbeanName;
      final FEConnection fthis = this;
      final JMSService fservice = this.frontEnd.getService();

      try {
         this.myRuntimeDelegate = (FEConnectionRuntimeDelegate)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException, JMSException {
               return new FEConnectionRuntimeDelegate(fmbeanName, fthis, fservice);
            }
         });
      } catch (PrivilegedActionException var35) {
         Exception e = var35.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else if (e instanceof ManagementException) {
            throw (ManagementException)e;
         } else {
            throw new weblogic.jms.common.JMSException("Error creating connection : " + mbeanName, e);
         }
      }
   }

   FEConnectionRuntimeDelegate getRuntimeDelegate() {
      return this.myRuntimeDelegate;
   }

   JMSID getConnectionId() {
      return this.connectionId;
   }

   int getOverrunPolicy() {
      return this.overrunPolicy;
   }

   int getAcknowledgePolicy() {
      return this.acknowledgePolicy;
   }

   int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   boolean isLocal() {
      return this.isLocal;
   }

   boolean isLoadBalancingEnabled() {
      return this.loadBalancingEnabled;
   }

   boolean isServerAffinityEnabled() {
      return this.serverAffinityEnabled;
   }

   private void setProducerLoadBalancingPolicyOverride() {
      this.producerLoadBalancingPolicyOverride = -99;
      if (this.connectionFactory != null && !this.connectionFactory.isDefaultConnectionFactory()) {
         String cfNameFull = this.connectionFactory.getName();
         String[] cfNameParsed = cfNameFull.split("!");
         if (cfNameParsed.length == 2) {
            String cfModule = cfNameParsed[0];
            String cfName = cfNameParsed[1];
            String prop = "weblogic.jms.ProducerLoadBalancingPolicy";
            String cfprop = prop + "." + cfModule;
            String val = System.getProperty(cfprop);
            if (val != null) {
               prop = cfprop;
            } else {
               val = System.getProperty(prop);
            }

            if (val != null) {
               synchronized(overrideLogs) {
                  String keyStr = cfModule + "." + cfName + "." + prop + "." + val;

                  try {
                     this.producerLoadBalancingPolicyOverride = FEConnectionFactory.getProducerLoadBalancingPolicyAsInt(val);
                     if (!overrideLogs.contains(keyStr)) {
                        JMSLogger.logInfoProducerLoadBalancingPolicy(cfName, cfModule, val, prop);
                     }
                  } catch (Throwable var12) {
                     keyStr = keyStr + ".Error";
                     if (!overrideLogs.contains(keyStr)) {
                        JMSLogger.logErrorProducerLoadBalancingPolicy(cfName, cfModule, val, prop);
                     }
                  }

                  overrideLogs.add(keyStr);
               }
            }
         }
      }
   }

   boolean isPerJVMProducerLoadBalancingEnabled() {
      int checkMe = this.producerLoadBalancingPolicyOverride == -99 ? this.producerLoadBalancingPolicy : this.producerLoadBalancingPolicyOverride;
      return checkMe == 1;
   }

   synchronized long getStartStopSequenceNumber() {
      return this.startStopSequenceNumber;
   }

   int getPriority() {
      return this.priority;
   }

   int getDeliveryMode() {
      return this.deliveryMode;
   }

   long getTimeToDeliver() {
      return this.timeToDeliver;
   }

   long getTimeToLive() {
      return this.timeToLive;
   }

   long getSendTimeout() {
      return this.sendTimeout;
   }

   public long getRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   boolean userTransactionsEnabled() {
      return this.userTransactionsEnabled;
   }

   boolean getAllowCloseInOnMessage() {
      return this.allowCloseInOnMessage;
   }

   long getTransactionTimeout() {
      return this.transactionTimeout;
   }

   boolean getAttachJMSXUserID() {
      return this.connectionFactory == null ? false : this.connectionFactory.isAttachJMSXUserId();
   }

   String getUnitOfOrder() {
      return this.unitOfOrder;
   }

   JMSDispatcher getClientDispatcher() throws JMSException {
      JMSDispatcher retDispatcher = this.clientDispatcher;
      if (retDispatcher == null) {
         throw new IllegalStateException("Connection is closed");
      } else {
         return retDispatcher;
      }
   }

   FEConnectionFactory getConnectionFactory() {
      return this.connectionFactory;
   }

   synchronized void markSuspending() {
      if (!this.closed && 0 == (this.state & 18)) {
         this.state = 2;
      }
   }

   synchronized void markShuttingDown() {
      if (!this.closed && 0 == (this.state & 16)) {
         this.state = 8;
      }
   }

   private synchronized boolean isSuspended() {
      return (this.state & 2) != 0;
   }

   synchronized void checkShutdownOrSuspended() throws JMSException {
      if (this.closed) {
         throw new weblogic.jms.common.JMSException("Connection is closed");
      } else if ((this.state & 26) != 0) {
         throw new weblogic.jms.common.JMSException("JMS server is shutdown or suspended");
      }
   }

   private synchronized void removeClientID() {
      if (this.clientId != null) {
         String jndiName = "weblogic.jms.connection.clientid." + this.clientId;
         boolean isBindVersioned = JMSServerUtilities.isBindVersioned();
         if (isBindVersioned) {
            JMSServerUtilities.unsetBindApplicationVersionIdContext();
         }

         try {
            JMSService service = this.frontEnd.getService();
            SingularAggregatableManager sam = service.getSingularAggregatableManagerWithJMSException();
            sam.singularUnbind(jndiName);
         } catch (JMSException var8) {
         } finally {
            if (isBindVersioned) {
               JMSServerUtilities.setBindApplicationVersionIdContext();
            }

         }

         this.clientId = null;
      }
   }

   void normalClose() throws JMSException {
      this.close(false, (JMSException)null);
   }

   void close(boolean allowDelayClose, JMSException reasonException) throws JMSException {
      this.close(allowDelayClose, reasonException, (Dispatcher)null);
   }

   void close(boolean allowDelayClose, JMSException reasonException, Dispatcher dws) throws JMSException {
      Exception savedException = null;
      this.frontEnd.getService().getInvocableManagerDelegate().invocableRemove(7, this.connectionId);
      synchronized(this) {
         try {
            if (!this.closed) {
               this.allowedToSetClientId = false;
               this.closed = true;
               this.clientDispatcher.removeDispatcherPeerGoneListener(this);

               try {
                  this.stop(dws);
               } catch (JMSException var96) {
                  if (savedException == null) {
                     savedException = var96;
                  }
               }

               Iterator iterator = ((HashMap)this.sessions.clone()).values().iterator();

               while(iterator.hasNext()) {
                  try {
                     ((FESession)iterator.next()).close(allowDelayClose, 0L, reasonException);
                  } catch (JMSException var102) {
                     if (savedException == null) {
                        savedException = var102;
                     }
                  }
               }

               Object response = null;
               iterator = ((HashMap)this.temporaryDestinations.clone()).values().iterator();
               JMSDispatcher dispatcher = null;

               while(iterator.hasNext()) {
                  DestinationImpl destination = (DestinationImpl)iterator.next();

                  try {
                     dispatcher = this.frontEnd.getService().dispatcherFindOrCreate(destination.getDispatcherId());
                     dispatcher.dispatchSync(new BETemporaryDestinationDestroyRequest(destination.getBackEndId().getId(), destination.getDestinationId()));
                  } catch (DispatcherException var99) {
                     if (savedException == null) {
                        savedException = var99;
                     }
                  } catch (JMSException var100) {
                     if (savedException == null) {
                        savedException = var100;
                     }
                  } finally {
                     if (dispatcher != null && !dispatcher.isLocal()) {
                        dispatcher.removeDispatcherPeerGoneListener(new DestinationPeerGoneAdapter(destination, (FEConnection)null));
                     }

                     dispatcher = null;
                  }
               }

               iterator = ((HashMap)this.connectionConsumers.clone()).values().iterator();

               while(iterator.hasNext()) {
                  try {
                     BEConnectionConsumerCommon connectionConsumer = (BEConnectionConsumerCommon)iterator.next();
                     this.connectionConsumerClose(connectionConsumer.getJMSID());
                  } catch (JMSException var98) {
                     if (savedException == null) {
                        savedException = var98;
                     }
                  }
               }

               iterator = ((HashMap)this.browsers.clone()).values().iterator();

               while(iterator.hasNext()) {
                  try {
                     this.browserRemove(((FEBrowser)iterator.next()).getJMSID());
                  } catch (JMSException var97) {
                     if (savedException == null) {
                        savedException = var97;
                     }
                  }
               }

               try {
                  if (this.myRuntimeDelegate != null) {
                     PrivilegedActionUtilities.unregister(this.myRuntimeDelegate, KERNEL_ID);
                  }
               } catch (ManagementException var95) {
                  JMSLogger.logErrorUnregisteringFrontEndConnection(this.frontEnd.getMbeanName(), this, var95);
                  if (savedException == null) {
                     savedException = var95;
                  }
               }

               if (savedException == null) {
                  return;
               }

               if (savedException instanceof JMSException) {
                  throw (JMSException)savedException;
               }

               throw new weblogic.jms.common.JMSException("Error closing connection", (Throwable)savedException);
            }
         } finally {
            this.removeClientID();
            if (this.clientDispatcher != null) {
               try {
                  this.frontEnd.getService().removeDispatcherReference(this.clientDispatcher);
               } catch (DispatcherException var93) {
                  throw new weblogic.jms.common.JMSException(var93);
               } finally {
                  this.clientDispatcher = null;
               }
            }

            this.myRuntimeDelegate = null;
         }

      }
   }

   public synchronized boolean isStopped() {
      return this.stopped;
   }

   boolean isClosed() {
      return this.closed;
   }

   private synchronized void start() throws JMSException {
      ++this.startStopSequenceNumber;
      this.stopped = false;
      this.allowedToSetClientId = false;
      Iterator iterator = this.sessions.values().iterator();
      HashMap dispatchers = new HashMap();

      while(iterator.hasNext()) {
         FESession session = (FESession)iterator.next();
         synchronized(session) {
            HashMap sessionDispatchers = session.getBEDispatchers();
            Iterator dispatcherIterator = sessionDispatchers.values().iterator();

            while(dispatcherIterator.hasNext()) {
               JMSDispatcher dispatcher = (JMSDispatcher)dispatcherIterator.next();
               dispatchers.put(dispatcher.getId(), dispatcher);
            }
         }
      }

      if (!this.connectionConsumers.isEmpty()) {
         JMSDispatcher dispatcher = this.frontEnd.getService().localDispatcherFind();
         dispatchers.put(dispatcher.getId(), dispatcher);
      }

      Throwable savedThrowable = null;
      iterator = dispatchers.values().iterator();

      while(iterator.hasNext()) {
         JMSDispatcher dispatcher = (JMSDispatcher)iterator.next();

         try {
            dispatcher.dispatchSync(new BEConnectionStartRequest(this.connectionId, this.startStopSequenceNumber));
         } catch (Throwable var9) {
            savedThrowable = var9;
         }
      }

      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FEConnection.start() started " + this);
      }

      if (savedThrowable != null) {
         throw new weblogic.jms.common.JMSException(savedThrowable.toString(), savedThrowable);
      }
   }

   synchronized void stop() throws JMSException {
      this.stop((Dispatcher)null);
   }

   synchronized void stop(Dispatcher dws) throws JMSException {
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FEConnection.stop() " + this + " dws " + dws);
      }

      ++this.startStopSequenceNumber;
      if (this.stopped) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FEConnection.stop(): connection " + this + " dws " + dws + " stopped already, returns");
         }

      } else {
         this.stopped = true;
         this.allowedToSetClientId = false;
         HashMap backEndDispatchers = new HashMap();
         Iterator iterator = this.sessions.values().iterator();

         while(iterator.hasNext()) {
            FESession session = (FESession)iterator.next();
            synchronized(session) {
               HashMap consumers = session.getConsumersMap();
               Iterator consumerIt = consumers.values().iterator();

               while(consumerIt.hasNext()) {
                  FEConsumer consumer = (FEConsumer)consumerIt.next();
                  JMSDispatcher beDispatcher = consumer.getBackEndDispatcher();
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FEConnection.stop() " + this + " dws " + dws + " session " + session.getId() + " consumer " + consumer.getId());
                  }

                  if (dws != null && beDispatcher.getId().isSameServer(dws.getId())) {
                     if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                        JMSDebug.JMSFrontEnd.debug("FEConnection.stop() " + this + " isSameServer skip backend stop request");
                     }
                  } else {
                     try {
                        backEndDispatchers.put(beDispatcher.getId(), beDispatcher);
                     } catch (Exception var13) {
                     }
                  }
               }
            }
         }

         if (!this.connectionConsumers.isEmpty()) {
            JMSDispatcher dispatcher = this.frontEnd.getService().localDispatcherFind();
            backEndDispatchers.put(dispatcher.getId(), dispatcher);
         }

         iterator = backEndDispatchers.values().iterator();
         Exception re = null;

         while(iterator.hasNext()) {
            JMSDispatcher beDispatcher = (JMSDispatcher)iterator.next();

            try {
               beDispatcher.dispatchSync(new BEConnectionStopRequest(this.connectionId, this.startStopSequenceNumber, this.isSuspended()));
            } catch (Exception var12) {
               re = var12;
            }
         }

         if (re != null) {
            throw new weblogic.jms.common.JMSException(re.toString(), re);
         } else {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FEConnection.stop() " + this + " dws " + dws + " returns");
            }

         }
      }
   }

   private int sessionCreate(FESessionCreateRequest request) throws JMSException {
      this.checkShutdownOrSuspended();
      JMSID sessionId = this.sessionCreateHelper(request);
      FESession session = this.sessionFind(sessionId);
      request.setResult(new FESessionCreateResponse(sessionId, session.getName()));
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   private JMSID sessionCreateHelper(FESessionCreateRequest request) throws JMSException {
      this.allowedToSetClientId = false;
      this.getFrontEnd().getService();
      JMSID sessionID = JMSService.getNextId();
      String name = "session" + sessionID.getCounter();
      SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

      FESession session;
      try {
         session = new FESession(this, name, sessionID, request.getTransacted(), request.getXASession(), request.getAcknowledgeMode(), request.getPushWorkManager());
      } catch (ManagementException var9) {
         throw new weblogic.jms.common.JMSException("Error creating front end session: " + var9, var9);
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

      this.sessionAdd(session, this.myRuntimeDelegate);
      return sessionID;
   }

   private synchronized void sessionAdd(FESession session, FEConnectionRuntimeDelegate stableRuntimeDelegate) throws JMSException {
      if (stableRuntimeDelegate == null) {
         throw new weblogic.jms.common.JMSException("connection has already closed " + this.getJMSID());
      } else {
         if (this.sessions.put(session.getJMSID(), session) == null) {
            stableRuntimeDelegate.increaseSessionsTotalCount();
            stableRuntimeDelegate.setSessionsHighCount(Math.max(stableRuntimeDelegate.getSessionsHighCount(), (long)this.sessions.size()));
            this.frontEnd.getService().getInvocableManagerDelegate().invocableAdd(8, session);
         }

      }
   }

   synchronized void sessionRemove(FESession session) {
      this.sessions.remove(session.getJMSID());
      this.frontEnd.getService().getInvocableManagerDelegate().invocableRemove(8, session.getJMSID());
   }

   private FESession sessionFind(JMSID sessionId) throws JMSException {
      FESession session = (FESession)this.sessions.get(sessionId);
      if (session != null) {
         return session;
      } else {
         throw new weblogic.jms.common.JMSException("Session not found");
      }
   }

   private synchronized void addTemporaryDestination(DestinationImpl destination) {
      this.temporaryDestinations.put(destination.getDestinationId(), destination);
   }

   public synchronized DestinationImpl removeTemporaryDestination(JMSID destinationId) {
      return (DestinationImpl)this.temporaryDestinations.remove(destinationId);
   }

   public DestinationImpl createDestination(DestinationImpl destination) throws JMSException {
      return this.createDestination(destination.getServerName(), destination.getName(), destination.getType());
   }

   public DestinationImpl createDestination(String backEndName, String destinationName, int destType) throws JMSException {
      JMSServerId refreshedBackEndId = this.frontEnd.getService().getFEManager().refreshBackEndId(backEndName);
      DestinationImpl destination;
      if ((destination = this.frontEnd.findBackEndDestination(refreshedBackEndId, destinationName)) == null) {
         JMSDispatcher beDispatcher;
         try {
            beDispatcher = this.frontEnd.getService().dispatcherFindOrCreate(refreshedBackEndId.getDispatcherId());
         } catch (DispatcherException var8) {
            throw new weblogic.jms.common.JMSException("Back End JMSDispatcher not found");
         }

         Object response = beDispatcher.dispatchSync(new BEDestinationCreateRequest(refreshedBackEndId.getId(), destinationName, destType, false));
         destination = ((JMSDestinationCreateResponse)response).getDestination();
         this.frontEnd.addBackEndDestination(refreshedBackEndId, destinationName, destination, this);
         this.checkShutdownOrSuspended();
      }

      return destination;
   }

   private static String debugClassCastException(String jndiName, String backEndName, Object found) {
      String info = "destination for jndi " + jndiName + " has backend " + backEndName + " found object <";
      if (found == null) {
         info = info + found + ">";
      } else {
         info = info + found.getClass().getName() + " " + found + ">";
      }

      return info;
   }

   private int serverSessionPoolCreate(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspended();
      if (!ServerSessionPoolHelper.isDomainCF(this.connectionFactory.getJNDIName(), ManagementService.getRuntimeAccess(KERNEL_ID).getDomain())) {
         throw new weblogic.jms.common.JMSException("Server session pool has to use a connection factory that is configured in the same scope");
      } else {
         FEServerSessionPoolCreateRequest request = (FEServerSessionPoolCreateRequest)invocableRequest;

         try {
            JMSDispatcher dispatcher = this.frontEnd.getService().dispatcherFindOrCreate(request.getBackEndId().getDispatcherId());
            Response response = dispatcher.dispatchSync(new BEServerSessionPoolCreateRequest(request.getBackEndId().getId(), this.connectionFactory.getJMSConnectionFactory(), request.getSessionMaximum(), request.getAcknowledgeMode(), request.isTransacted(), request.getMessageListenerClass(), request.getClientData()));
            request.setResult(response);
         } catch (DispatcherException var5) {
            throw new weblogic.jms.common.JMSException("Error creating server session pool", var5);
         }

         request.setState(Integer.MAX_VALUE);
         return request.getState();
      }
   }

   private synchronized void connectionConsumerAdd(BEConnectionConsumerCommon connectionConsumer) throws JMSException {
      this.checkShutdownOrSuspended();
      if (this.connectionConsumers.put(connectionConsumer.getJMSID(), connectionConsumer) == null) {
         this.myRuntimeDelegate.increaseConnectionConsumersCurrentCount();
         if (this.myRuntimeDelegate.getConnectionConsumersCurrentCount() > this.myRuntimeDelegate.getConnectionConsumersHighCount()) {
            this.myRuntimeDelegate.increaseConnectionConsumersTotalCount();
         }
      }

   }

   private synchronized void connectionConsumerRemove(BEConnectionConsumerCommon connectionConsumer) {
      if (this.connectionConsumers.remove(connectionConsumer.getJMSID()) != null) {
         this.myRuntimeDelegate.decreaseConnectionConsumersCurrentCount();
      }

   }

   private synchronized BEConnectionConsumerCommon connectionConsumerFind(JMSID connectionConsumerId) throws JMSException {
      BEConnectionConsumerCommon connectionConsumer = (BEConnectionConsumerCommon)this.connectionConsumers.get(connectionConsumerId);
      if (connectionConsumer != null) {
         return connectionConsumer;
      } else {
         throw new weblogic.jms.common.JMSException("ConnectionConsumer not found");
      }
   }

   private int connectionConsumerCreate(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspended();
      FEConnectionConsumerCreateRequest request = (FEConnectionConsumerCreateRequest)invocableRequest;
      DestinationImpl destination = request.getDestination();
      ServerSessionPool serverSessionPool = request.getServerSessionPool();
      if (!ServerSessionPoolHelper.isDomainDestination(destination.getName(), ManagementService.getRuntimeAccess(KERNEL_ID).getDomain())) {
         throw new weblogic.jms.common.JMSException("Connection consumer has to use a destination that is configured in the same scope");
      } else {
         FEDDHandler feDDHandler = null;

         assert destination != null;

         feDDHandler = DDManager.findFEDDHandlerByDDName(destination.getName());
         if (feDDHandler != null) {
            destination = feDDHandler.connectionConsumerLoadBalance(destination.getName());
         }

         if (destination.getDestinationId() == null) {
            destination = this.createDestination(destination.getServerName(), destination.getName(), destination.getType());
         }

         JMSServerId backEndId = destination.getBackEndId();
         JMSID destinationId = destination.getDestinationId();
         boolean stopped;
         long startStopSequenceNumber;
         synchronized(this) {
            stopped = this.stopped;
            startStopSequenceNumber = this.startStopSequenceNumber;
         }

         Response response;
         try {
            response = this.frontEnd.getService().dispatcherFindOrCreate(destination.getDispatcherId()).dispatchSync(new BEConnectionConsumerCreateRequest(backEndId, this.connectionId, serverSessionPool, this, destinationId, request.isDurable(), request.getMessageSelector(), request.getMessagesMaximum(), stopped, startStopSequenceNumber));
         } catch (DispatcherException var13) {
            throw new weblogic.jms.common.JMSException("Error creating connection consumer", var13);
         }

         ConnectionConsumer connectionConsumer = ((JMSConnectionConsumerCreateResponse)response).getConnectionConsumer();
         this.connectionConsumerAdd((BEConnectionConsumerCommon)connectionConsumer);
         if (!this.isLocal) {
            ConnectionConsumer connectionConsumer = new JMSConnectionConsumer(this.connectionId, serverSessionPool, ((BEConnectionConsumerCommon)connectionConsumer).getJMSID());
            ((JMSConnectionConsumerCreateResponse)response).setConnectionConsumer(connectionConsumer);
         }

         request.setResult(response);
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      }
   }

   private void connectionConsumerClose(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspended();
      FEConnectionConsumerCloseRequest request = (FEConnectionConsumerCloseRequest)invocableRequest;
      this.connectionConsumerClose(request.getConnectionConsumerId());
   }

   private void connectionConsumerClose(JMSID consumerId) throws JMSException {
      BEConnectionConsumerCommon connectionConsumer = this.connectionConsumerFind(consumerId);
      this.connectionConsumerRemove(connectionConsumer);
      JMSDispatcher beDispatcher = ((BEConnection)this.frontEnd.getService().getInvocableManagerDelegate().invocableFind(15, this.connectionId)).getDispatcher();
      BEConnectionConsumerCloseRequest request = new BEConnectionConsumerCloseRequest(this.connectionId, consumerId);
      beDispatcher.dispatchSync(request);
   }

   public String getConnectionClientId() {
      return this.clientId;
   }

   public int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   private int setConnectionClientId(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspended();
      FEConnectionSetClientIdRequest request = (FEConnectionSetClientIdRequest)invocableRequest;
      String clientID = request.getClientId();
      this.clientIdPolicy = request.getClientIdPolicy();
      String jndiName = "weblogic.jms.connection.clientid." + clientID;
      if (this.clientIdPolicy == 1) {
         if (request.getState() == 0) {
            this.checkIfAllowedToSetClientID();
         }

         this.clientId = clientID;
         request.setResult(new VoidResponse());
         return Integer.MAX_VALUE;
      } else {
         JMSService service = this.frontEnd.getService();
         SingularAggregatableManager sm = service.getSingularAggregatableManagerWithJMSException();
         switch (request.getState()) {
            case 0:
               this.checkIfAllowedToSetClientID();
               FEClientIDSingularAggregatable focascia = new FEClientIDSingularAggregatable(clientID, this.connectionId);
               request.setFocascia(focascia);
               request.setState(1);
               sm.singularBindStart(jndiName, focascia, request);
               return request.getState();
            case 1:
               boolean isBindVersioned = JMSServerUtilities.isBindVersioned();
               if (isBindVersioned) {
                  JMSServerUtilities.unsetBindApplicationVersionIdContext();
               }

               try {
                  String reason;
                  if ((reason = sm.singularBindFinish((SingularAggregatable)request.getFocascia(), request)) != null) {
                     throw new InvalidClientIDException("Client id, " + clientID + ", is in use.  The reason for rejection is \"" + reason + "\"");
                  }
               } finally {
                  if (isBindVersioned) {
                     JMSServerUtilities.setBindApplicationVersionIdContext();
                  }

               }

               this.clientId = clientID;
               if (request.hasResults()) {
                  request.getResult();
                  request.setResult(new VoidResponse());
               }

               return Integer.MAX_VALUE;
            default:
               throw new JMSException("Unknown state: " + request.getState());
         }
      }
   }

   private void checkIfAllowedToSetClientID() throws IllegalStateException {
      if (this.clientId != null) {
         throw new IllegalStateException("ClientID has already been set");
      } else if (!this.allowedToSetClientId) {
         throw new IllegalStateException("ClientID needs to be set first thing after creating connection");
      }
   }

   private void setConnectionClientId(String clientID) throws JMSException {
      this.checkIfAllowedToSetClientID();
      if (this.clientIdPolicy == 0) {
         String jndiName = "weblogic.jms.connection.clientid." + clientID;
         FEClientIDSingularAggregatable focascia = new FEClientIDSingularAggregatable(clientID, this.connectionId);
         JMSService service = this.frontEnd.getService();
         SingularAggregatableManager sm = service.getSingularAggregatableManagerWithJMSException();
         boolean isBindVersioned = JMSServerUtilities.isBindVersioned();
         if (isBindVersioned) {
            JMSServerUtilities.unsetBindApplicationVersionIdContext();
         }

         try {
            String reason;
            if ((reason = sm.singularBind(jndiName, focascia)) != null) {
               throw new InvalidClientIDException("Client id, " + clientID + ", is in use.  The reason for rejection is \"" + reason + "\"");
            }
         } finally {
            if (isBindVersioned) {
               JMSServerUtilities.setBindApplicationVersionIdContext();
            }

         }
      }

      this.clientId = clientID;
   }

   public String getSubscriptionSharingPolicy() {
      return FEConnectionFactory.getSubscriptionSharingPolicyAsString(this.subscriptionSharingPolicy);
   }

   public int getSubscriptionSharingPolicyAsInt() {
      return this.subscriptionSharingPolicy;
   }

   public String toString() {
      return this.mbeanName;
   }

   public int incrementRefCount() {
      return ++this.refCount;
   }

   public int decrementRefCount() {
      return --this.refCount;
   }

   public void dispatcherPeerGone(Exception e, Dispatcher dispatcher) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("FEConnection.jmsPeerGone() " + this + " dws " + dispatcher.getId() + " clientDispatcher " + this.clientDispatcher.getId());
      }

      AuthenticatedSubject currentSubject = JMSSecurityHelper.getCurrentSubject();
      if (currentSubject == null || JMSSecurityHelper.isServerIdentity(currentSubject)) {
         currentSubject = SubjectUtils.getAnonymousSubject();
      }

      SecurityServiceManager.pushSubject(KERNEL_ID, currentSubject);

      try {
         this.close(true, (JMSException)null, dispatcher);
      } catch (JMSException var8) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("peer gone exception", var8);
         }
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

   }

   public FrontEnd getFrontEnd() {
      return this.frontEnd;
   }

   public synchronized HashMap getSessionMap() {
      return this.sessions;
   }

   public synchronized HashMap getBrowserMap() {
      return this.browsers;
   }

   public synchronized HashMap getConnectionConsumerMap() {
      return this.connectionConsumers;
   }

   public synchronized HashMap getTemporaryDestinationMap() {
      return this.temporaryDestinations;
   }

   public JMSSessionRuntimeMBean[] getSessions() {
      return this.myRuntimeDelegate.getSessions();
   }

   public long getSessionsCurrentCount() {
      return this.myRuntimeDelegate.getSessionsCurrentCount();
   }

   public long getSessionsHighCount() {
      return this.myRuntimeDelegate.getSessionsHighCount();
   }

   public long getSessionsTotalCount() {
      return this.myRuntimeDelegate.getSessionsTotalCount();
   }

   public synchronized void setPeerVersion(byte peerVersion) {
      this.peerVersion = peerVersion;
   }

   public synchronized byte getPeerVersion() {
      return this.peerVersion;
   }

   public void finalize() {
      try {
         super.finalize();
         this.removeClientID();
      } catch (Throwable var2) {
      }

   }

   private int destroyTemporaryDestination(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspended();
      DestinationImpl tempDestination = null;
      FETemporaryDestinationDestroyRequest request = (FETemporaryDestinationDestroyRequest)invocableRequest;
      switch (request.getState()) {
         case 0:
            tempDestination = (DestinationImpl)this.temporaryDestinations.get(request.getDestinationId());
            if (tempDestination == null) {
               request.setResult(new VoidResponse());
               request.setState(Integer.MAX_VALUE);
               return Integer.MAX_VALUE;
            } else {
               BETemporaryDestinationDestroyRequest child = new BETemporaryDestinationDestroyRequest(tempDestination.getBackEndId().getId(), request.getDestinationId());
               synchronized(request) {
                  request.rememberChild(child);
                  request.setState(1);
               }

               try {
                  request.setDispatcher(this.frontEnd.getService().dispatcherFindOrCreate(tempDestination.getDispatcherId()));
                  request.dispatchAsync(request.getDispatcher(), child);
               } catch (DispatcherException var7) {
                  throw new weblogic.jms.common.JMSException("Error deleting temporary destination", var7);
               }

               return request.getState();
            }
         case 1:
         default:
            request.useChildResult(VoidResponse.class);
            tempDestination = this.removeTemporaryDestination(request.getDestinationId());
            if (!request.getDispatcher().isLocal()) {
               request.getDispatcher().removeDispatcherPeerGoneListener(new DestinationPeerGoneAdapter(tempDestination, (FEConnection)null));
            }

            return Integer.MAX_VALUE;
      }
   }

   private int createTemporaryDestination(Request request) throws JMSException {
      this.checkShutdownOrSuspended();
      FETempDestinationFactory frontEndTempDestinationFactory = this.frontEnd.getService().getFEManager().getTemporaryDestinationFactory();
      boolean isDefaultConnectionFactory = this.connectionFactory.isDefaultConnectionFactory();
      DestinationImpl destination = null;
      synchronized(this) {
         destination = (DestinationImpl)frontEndTempDestinationFactory.createTempDestination(this.frontEnd.getService().getLocalId(), this.getJMSID(), this.stopped, ((FETemporaryDestinationCreateRequest)request).getDestType(), this.startStopSequenceNumber, this.getAddressAndMBeanInfo(), isDefaultConnectionFactory, this.connectionFactory.getDeploymentModuleType(), this.connectionFactory.getDeploymentModuleName());
      }

      this.addTemporaryDestination(destination);

      JMSDispatcher beDispatcher;
      try {
         beDispatcher = this.frontEnd.getService().dispatcherFindOrCreate(destination.getDispatcherId());
      } catch (DispatcherException var7) {
         throw new weblogic.jms.common.JMSException("Error creating temporary destination", var7);
      }

      if (!beDispatcher.isLocal()) {
         beDispatcher.addDispatcherPeerGoneListener(new DestinationPeerGoneAdapter(destination, this));
      }

      request.setState(Integer.MAX_VALUE);
      request.setResult(new FETemporaryDestinationCreateResponse(destination));
      return Integer.MAX_VALUE;
   }

   private synchronized void browserAdd(FEBrowser browser) throws JMSException {
      if (this.browsers.put(browser.getJMSID(), browser) == null) {
         this.frontEnd.getService().getInvocableManagerDelegate().invocableAdd(11, browser);
         this.myRuntimeDelegate.setBrowsersHighCount(Math.max(this.myRuntimeDelegate.getBrowsersHighCount(), (long)this.browsers.size()));
         this.myRuntimeDelegate.increaseBrowsersTotalCount();
      }

   }

   private int browserCreate(Request invocableRequest) throws JMSException {
      this.checkShutdownOrSuspended();
      FEBrowserCreateRequest request = (FEBrowserCreateRequest)invocableRequest;
      switch (request.getState()) {
         case 0:
            DestinationImpl destination = request.getDestination();
            FEDDHandler feDDHandler = null;

            assert destination != null;

            feDDHandler = DDManager.findFEDDHandlerByDDName(destination.getName());
            if (feDDHandler != null) {
               destination = feDDHandler.consumerLoadBalance((FESession)null, destination.getName());
            }

            BEBrowserCreateRequest child = new BEBrowserCreateRequest((JMSID)null, destination.getDestinationId(), request.getMessageSelector());
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               request.setDispatcher(this.frontEnd.getService().dispatcherFindOrCreate(destination.getDispatcherId()));
               request.dispatchAsync(request.getDispatcher(), child);
               break;
            } catch (DispatcherException var8) {
               throw new weblogic.jms.common.JMSException("Error creating browser", var8);
            }
         case 1:
            JMSBrowserCreateResponse response = (JMSBrowserCreateResponse)request.useChildResult(JMSBrowserCreateResponse.class);
            FEBrowser browser = new FEBrowser(this, (FESession)null, response.getBrowserId(), request.getDispatcher());
            this.browserAdd(browser);
      }

      return request.getState();
   }

   synchronized void browserRemove(JMSID browserId) throws JMSException {
      if (this.browsers.remove(browserId) == null) {
         throw new weblogic.jms.common.JMSException("Browser not found, " + browserId);
      } else {
         this.frontEnd.getService().getInvocableManagerDelegate().invocableRemove(11, browserId);
      }
   }

   String getAddressAndMBeanInfo() {
      return this.clientAddress + "|" + this.frontEnd.getService().getMbeanName();
   }

   String getAddress() {
      return this.clientAddress.substring(1);
   }

   public JMSID getJMSID() {
      return this.connectionId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.frontEnd.getService().getDispatcherPartitionContext();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.frontEnd.getService(), "FEConnection");
      switch (request.getMethodId()) {
         case 519:
            return this.browserCreate(request);
         case 1031:
            this.checkShutdownOrSuspended();
            this.normalClose();
            break;
         case 1287:
            this.connectionConsumerClose(request);
            break;
         case 1543:
            return this.connectionConsumerCreate(request);
         case 1799:
            return this.setConnectionClientId(request);
         case 2055:
            this.checkShutdownOrSuspended();
            this.start();
            break;
         case 2311:
            this.checkShutdownOrSuspended();
            this.stop();
            break;
         case 5895:
            return this.serverSessionPoolCreate(request);
         case 6663:
            return this.sessionCreate((FESessionCreateRequest)request);
         case 7431:
            return this.destroyTemporaryDestination(request);
         case 7687:
            return this.createTemporaryDestination(request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + request.getMethodId());
      }

      request.setResult(new VoidResponse());
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Connection");
      xsw.writeAttribute("id", this.connectionId != null ? this.connectionId.toString() : "");
      xsw.writeAttribute("clientID", this.clientId != null ? this.clientId : "");
      xsw.writeAttribute("browsersCurrentCount", String.valueOf(this.browsers.size()));
      xsw.writeAttribute("deliveryMode", this.deliveryMode == 2 ? "persistent" : "non-persistent");
      xsw.writeAttribute("priority", String.valueOf(this.priority));
      xsw.writeAttribute("timeToDeliver", String.valueOf(this.timeToDeliver));
      xsw.writeAttribute("timeToLive", String.valueOf(this.timeToLive));
      xsw.writeAttribute("sendTimeout", String.valueOf(this.sendTimeout));
      xsw.writeAttribute("redeliveryDelay", String.valueOf(this.redeliveryDelay));
      xsw.writeAttribute("userTransactionsEnabled", String.valueOf(this.userTransactionsEnabled));
      xsw.writeAttribute("messagesMaximum", String.valueOf(this.messagesMaximum));
      xsw.writeAttribute("overrunPolicy", String.valueOf(this.overrunPolicy));
      xsw.writeAttribute("acknowledgePolicy", String.valueOf(this.acknowledgePolicy));
      xsw.writeAttribute("loadBalancingEnabled", String.valueOf(this.loadBalancingEnabled));
      xsw.writeAttribute("producerLoadBalancingPolicy", String.valueOf(this.producerLoadBalancingPolicy));
      xsw.writeAttribute("serverAffinityEnabled", String.valueOf(this.serverAffinityEnabled));
      xsw.writeAttribute("unifOfOrder", this.unitOfOrder != null ? this.unitOfOrder : "");
      xsw.writeAttribute("isLocal", String.valueOf(this.isLocal));
      xsw.writeAttribute("clientAddress", this.clientAddress != null ? this.clientAddress : "");
      xsw.writeAttribute("state", JMSService.getStateName(this.state));
      xsw.writeAttribute("peerVersion", String.valueOf(this.peerVersion));
      xsw.writeStartElement("Sessions");
      HashMap tempSessions = (HashMap)this.sessions.clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempSessions.size()));
      Iterator it = tempSessions.values().iterator();

      while(it.hasNext()) {
         FESession fes = (FESession)it.next();
         fes.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeStartElement("ConnectionConsumers");
      HashMap tempConnectionConsumers = (HashMap)this.connectionConsumers.clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempConnectionConsumers.size()));
      it = tempConnectionConsumers.values().iterator();

      while(it.hasNext()) {
         BEConnectionConsumerCommon consumer = (BEConnectionConsumerCommon)it.next();
         consumer.dumpRef(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeStartElement("TemporaryDestinations");
      HashMap tempTempDestinations = (HashMap)this.temporaryDestinations.clone();
      xsw.writeAttribute("currentCount", String.valueOf(tempTempDestinations.size()));
      it = tempTempDestinations.values().iterator();

      while(it.hasNext()) {
         DestinationImpl dest = (DestinationImpl)it.next();
         JMSDiagnosticImageSource.dumpDestinationImpl(xsw, dest);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }
}
