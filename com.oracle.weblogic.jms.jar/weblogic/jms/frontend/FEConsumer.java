package weblogic.jms.frontend;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Vector;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.JMSServiceServerLifeCycleImpl;
import weblogic.jms.backend.BEConsumerCloseRequest;
import weblogic.jms.backend.BEConsumerCreateRequest;
import weblogic.jms.backend.BEConsumerCreateResponse;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jms.backend.BEConsumerIncrementWindowCurrentRequest;
import weblogic.jms.backend.BEConsumerIsActiveRequest;
import weblogic.jms.backend.BEConsumerIsActiveResponse;
import weblogic.jms.backend.BEConsumerReceiveRequest;
import weblogic.jms.backend.BEConsumerSetListenerRequest;
import weblogic.jms.backend.BERemoveSubscriptionRequest;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.DSManager;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSConsumerReceiveResponse;
import weblogic.jms.common.JMSConsumerSetListenerResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageContextImpl;
import weblogic.jms.common.JMSPeerGoneListener;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageStatistics;
import weblogic.jms.common.Sequencer;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.ConsumerClosedException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSConsumerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.interception.MessageInterceptionService;
import weblogic.messaging.interception.exceptions.InterceptionProcessorException;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.CarrierCallBack;
import weblogic.messaging.interception.interfaces.InterceptionPointHandle;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class FEConsumer extends RuntimeMBeanDelegate implements JMSConsumerRuntimeMBean, Invocable, JMSPeerGoneListener, CarrierCallBack {
   static final long serialVersionUID = -8556954068817891651L;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private JMSID consumerId;
   private JMSDispatcher backEndDispatcher;
   private Sequencer sequencer;
   public static final String JNDI_SUBSCRIPTIONNAME = "weblogic.jms.internal.subscription";
   final MessageStatistics statistics = new MessageStatistics();
   private InvocableMonitor invocableMonitor;
   private FESession session;
   private DestinationImpl destination;
   private String selector;
   private boolean isDurable = false;
   private boolean isLocal = true;
   private String subject = null;
   private AuthenticatedSubject authenticatedSubject;
   private InterceptionPointHandle receiveIPHandle = null;
   private DestinationImpl receiveIPDestination = null;
   private static final int DONE = 1;
   private static final int IN_PROGRESS = 2;
   private Request currentRequest;
   private static Object interceptionPointLock = new Object();
   private ConsumerReconnectInfo consumerReconnectInfo;
   private int subscriptionSharingPolicy;
   private static final int CHECK_JNDI_UPDATE_MAX_TOTAL_WAIT_TIME = 30000;
   private static final int CHECK_JNDI_UPDATE_INITIAL_WAIT_TIME = 1;
   private static final int CHECK_JNDI_UPDATE_MAX_WAIT_TIME = 16;
   private transient int refCount;

   public FEConsumer(String mbeanName, FESession session, Sequencer sequencer, String clientId, DestinationImpl destination, JMSID consumerId, String subject, AuthenticatedSubject authenticatedSubject, FEConsumerCreateRequest request) throws JMSException, ManagementException {
      super(mbeanName, session);

      try {
         int flag = 1;
         this.session = session;
         this.sequencer = sequencer;
         this.destination = destination;
         this.consumerId = consumerId;
         this.subject = subject;
         this.authenticatedSubject = authenticatedSubject;
         this.subscriptionSharingPolicy = request.getSubscriptionSharingPolicy();
         if (this.subscriptionSharingPolicy == -1) {
            this.subscriptionSharingPolicy = this.session.getSubscriptionSharingPolicy();
         }

         try {
            this.backEndDispatcher = session.getConnection().getFrontEnd().getService().dispatcherFindOrCreate(destination.getDispatcherId());
            if (!this.backEndDispatcher.isLocal()) {
               this.isLocal = false;
            }
         } catch (DispatcherException var38) {
            throw new weblogic.jms.common.JMSException("Error creating consumer", var38);
         }

         this.isDurable = request.isDurable();
         FrontEnd frontEnd = session.getConnection().getFrontEnd();
         this.invocableMonitor = frontEnd.getInvocableMonitor();
         this.selector = request.getSelector();
         DurableSubscription newDS;
         if (this.isDurable) {
            if (this.getSession().getConnection().getClientIdPolicy() == 0) {
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("in FEConsumer durable with restricted clientID");
               }

               DurableSubscription sub = DSManager.manager().lookup(BEConsumerImpl.JNDINameForSubscription(BEConsumerImpl.clientIdPlusName(clientId, request.getName())));
               if (sub != null) {
                  newDS = new DurableSubscription(BEConsumerImpl.clientIdPlusName(clientId, request.getName(), this.getSession().getConnection().getClientIdPolicy(), destination.getTopicName(), destination.getServerName()), destination, request.getSelector(), request.getNoLocal(), this.getSession().getConnection().getClientIdPolicy(), this.subscriptionSharingPolicy);
                  Vector dsVector = sub.getDSVector();
                  int i = false;

                  for(int i = 0; i < dsVector.size(); ++i) {
                     DurableSubscription subInstance = (DurableSubscription)dsVector.elementAt(i);
                     if (subInstance.equalsForSerialized(newDS)) {
                        flag = 0;
                     } else {
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("in FEConsumer create new consumer");
                        }

                        JMSServerId oldBackEndId = subInstance.getBackEndId();
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("in FEConsumer first remove old consumer");
                        }

                        JMSDispatcher beDispatcher;
                        try {
                           beDispatcher = this.getSession().getConnection().getFrontEnd().getService().dispatcherFindOrCreate(oldBackEndId.getDispatcherId());
                        } catch (DispatcherException var37) {
                           throw new weblogic.jms.common.JMSException("Error creating consumer", var37);
                        }

                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("in FEConnection remove consumer");
                        }

                        try {
                           SecurityServiceManager.pushSubject(KERNEL_ID, authenticatedSubject);
                           beDispatcher.dispatchSync(new BERemoveSubscriptionRequest(oldBackEndId, subInstance.getDestinationImpl().getTopicName(), clientId, this.getSession().getConnection().getClientIdPolicy(), request.getName()));
                        } finally {
                           SecurityServiceManager.popSubject(KERNEL_ID);
                        }

                        this.waitForJNDIUpdate(clientId, request.getName());
                     }
                  }
               }
            } else if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("in FEConsumer durable with unrestricted clientID");
            }
         } else if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("in FEConsumer not durable");
         }

         newDS = null;

         Response result;
         try {
            SecurityServiceManager.pushSubject(KERNEL_ID, authenticatedSubject);
            result = this.backEndDispatcher.dispatchSync(new BEConsumerCreateRequest(session.getConnection().getJMSID(), session.getJMSID(), consumerId, clientId, this.getSession().getConnection().getClientIdPolicy(), request.getName(), this.isDurable, destination.getDestinationId(), request.getSelector(), request.getNoLocal(), request.getMessagesMaximum(), flag, request.getRedeliveryDelay(), subject, request.getConsumerReconnectInfo(), this.subscriptionSharingPolicy));
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

         if (!this.isLocal) {
            this.backEndDispatcher.addDispatcherPeerGoneListener(this.getSession());
            this.backEndDispatcher.addDispatcherPeerGoneListener(this);
         }

         this.consumerReconnectInfo = ((BEConsumerCreateResponse)result).getConsumerReconnectInfo();
      } catch (Exception var39) {
         try {
            PrivilegedActionUtilities.unregister(this, KERNEL_ID);
         } catch (ManagementException var34) {
         }

         if (var39 instanceof RuntimeException) {
            throw (RuntimeException)var39;
         } else if (var39 instanceof JMSException) {
            throw (JMSException)var39;
         } else if (var39 instanceof ManagementException) {
            throw (ManagementException)var39;
         } else {
            throw new weblogic.jms.common.JMSException(var39);
         }
      }
   }

   private void waitForJNDIUpdate(String clientId, String subName) throws JMSException {
      String jndiName = BEConsumerImpl.JNDINameForSubscription(BEConsumerImpl.clientIdPlusName(clientId, subName));
      Context ctx = this.session.getConnection().getFrontEnd().getService().getCtx();
      int totalWaitTime = 0;
      int waitTime = 1;

      while(totalWaitTime <= 30000) {
         try {
            ctx.lookup(jndiName);
         } catch (NameNotFoundException var9) {
            return;
         } catch (NamingException var10) {
            return;
         }

         try {
            Thread.sleep((long)waitTime);
         } catch (InterruptedException var8) {
         }

         totalWaitTime += waitTime;
         if (waitTime < 16) {
            waitTime *= 2;
         }
      }

   }

   public ConsumerReconnectInfo getConsumerReconnectInfo() {
      return this.consumerReconnectInfo;
   }

   JMSDispatcher getBackEndDispatcher() {
      return this.backEndDispatcher;
   }

   private FESession getSession() {
      return this.session;
   }

   private Sequencer getSequencer() {
      return this.sequencer;
   }

   private void close(FEConsumerCloseRequest request) throws JMSException {
      this.session.checkShutdownOrSuspended();
      BEConsumerCloseRequest beRequest = this.session.consumerClose(this, request);
      if (JMSServiceServerLifeCycleImpl.interceptionEnabled) {
         synchronized(interceptionPointLock) {
            if (this.receiveIPHandle != null && this.getDestination() != null && (this.getDestination().getType() == 8 || this.getDestination().getType() == 4)) {
               try {
                  if (!this.receiveIPHandle.hasAssociation()) {
                     MessageInterceptionService.getSingleton().unRegisterInterceptionPoint(this.receiveIPHandle);
                     this.receiveIPHandle = null;
                  }
               } catch (InterceptionServiceException var11) {
                  JMSLogger.logFailedToUnregisterInterceptionPoint(var11);
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FEConsumer.close(), Failure to unregister " + var11);
                  }
               }
            }
         }
      }

      try {
         this.backEndDispatcher.dispatchSync(beRequest);
      } finally {
         this.session.consumerRemove(this.consumerId);
      }

   }

   private int pushException(Request invocableRequest) throws JMSException {
      JMSPushExceptionRequest request = (JMSPushExceptionRequest)invocableRequest;

      try {
         this.getSession().pushException(6, this.consumerId, request.getException());
      } catch (Exception var4) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("Error pushing exception ", var4);
         }
      }

      if (request.getException() instanceof ConsumerClosedException) {
         this.session.consumerRemove(this.consumerId);
      }

      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   private int setListener(Request invocableRequest) throws JMSException {
      this.session.checkShutdownOrSuspended();
      FEConsumerSetListenerRequest request = (FEConsumerSetListenerRequest)invocableRequest;
      if (request.getHasListener()) {
         this.session.updateQOS();
      }

      switch (request.getState()) {
         case 0:
            BEConsumerSetListenerRequest child = new BEConsumerSetListenerRequest(this.consumerId, request.getHasListener(), request.getLastSequenceNumber());
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               request.dispatchAsync(this.getBackEndDispatcher(), child);
               break;
            } catch (DispatcherException var6) {
               throw new weblogic.jms.common.JMSException("Error setting listener", var6);
            }
         default:
            request.useChildResult(JMSConsumerSetListenerResponse.class);
      }

      return request.getState();
   }

   private int incrementWindow(Request invocableRequest) throws JMSException {
      this.session.checkShutdownOrSuspended();
      FEConsumerIncrementWindowCurrentRequest request = (FEConsumerIncrementWindowCurrentRequest)invocableRequest;
      JMSServerUtilities.anonDispatchNoReply(new BEConsumerIncrementWindowCurrentRequest(this.consumerId, request.getWindowIncrement(), request.getClientResponsibleForAcknowledge()), this.backEndDispatcher);
      request.setResult(new VoidResponse());
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   private int incrementWindowOneWay(Request invocableRequest) throws JMSException {
      this.session.checkShutdownOrSuspended();
      FEConsumerIncrementWindowCurrentOneWayRequest request = (FEConsumerIncrementWindowCurrentOneWayRequest)invocableRequest;
      JMSServerUtilities.anonDispatchNoReply(new BEConsumerIncrementWindowCurrentRequest(this.consumerId, request.getWindowIncrement(), request.getClientResponsibleForAcknowledge()), this.backEndDispatcher);
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   public void onCallBack(boolean continueOn) {
      this.currentRequest.resumeExecution(true);
   }

   public void onException(InterceptionProcessorException exception) {
      System.out.println("Processor throws exception" + exception);
      this.currentRequest.resumeExecution(true);
   }

   private int receiveInterceptionPoint(DestinationImpl destination, MessageImpl message) throws JMSException {
      if (!JMSServiceServerLifeCycleImpl.interceptionEnabled) {
         return 1;
      } else {
         synchronized(interceptionPointLock) {
            if (this.receiveIPHandle != null && this.receiveIPDestination != destination) {
               try {
                  MessageInterceptionService.getSingleton().unRegisterInterceptionPoint(this.receiveIPHandle);
               } catch (InterceptionServiceException var8) {
                  throw new AssertionError("Failure to unregister" + var8);
               }

               this.receiveIPHandle = null;
            }

            if (this.receiveIPHandle == null) {
               this.receiveIPDestination = destination;
               String[] ip = new String[]{destination.getServerName(), destination.getName(), "Receive"};
               if (ip[0] == null) {
                  ip[0] = new String();
               }

               if (ip[1] == null) {
                  ip[1] = new String();
               }

               try {
                  this.receiveIPHandle = MessageInterceptionService.getSingleton().registerInterceptionPoint("JMS", ip);
               } catch (InterceptionServiceException var7) {
                  throw new weblogic.jms.common.JMSException("FAILED registerInterceptionPoint " + var7);
               }
            }

            try {
               if (this.receiveIPHandle.hasAssociation()) {
                  JMSMessageContextImpl messageContext = new JMSMessageContextImpl(message);
                  this.receiveIPHandle.processAsync(messageContext, this);
                  byte var10000 = 2;
                  return var10000;
               }
            } catch (Exception var9) {
               throw new weblogic.jms.common.JMSException("FAILED in interception " + var9);
            }

            return 1;
         }
      }
   }

   private int receive(Request invocableRequest) throws JMSException {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("FEConsumer.receive()");
      }

      this.session.checkShutdownOrSuspended();
      FEConsumerReceiveRequest request = (FEConsumerReceiveRequest)invocableRequest;
      switch (request.getState()) {
         case 0:
            if (this.session.isTransacted()) {
               this.session.transactedInfect();
            }

            BEConsumerReceiveRequest child = new BEConsumerReceiveRequest(this.consumerId, request.getTimeout());
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               request.dispatchAsync(this.getBackEndDispatcher(), child);
            } catch (DispatcherException var19) {
               throw new weblogic.jms.common.JMSException("Error receiving message", var19);
            }
         default:
            return request.getState();
         case 1:
            try {
               JMSConsumerReceiveResponse receiveResponse = (JMSConsumerReceiveResponse)request.useChildResult(JMSConsumerReceiveResponse.class);
               if (receiveResponse == null) {
                  throw new AssertionError("receive got a null response");
               }

               MessageImpl messageImpl = receiveResponse.getMessage();
               if (messageImpl != null) {
                  receiveResponse.setCompressionThreshold(this.session.getConnection().getCompressionThreshold());
               }

               if (messageImpl != null) {
                  receiveResponse.setCompressionThreshold(this.session.getConnection().getCompressionThreshold());
                  if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
                     JMSDebug.JMSMessagePath.debug("FRONTEND/FEConsumer (id: " + this.consumerId + ") : Receipt of message " + messageImpl.getJMSMessageID());
                  }

                  if (receiveResponse.isTransactional()) {
                     this.session.transactionStat(this, (FEProducer)null, messageImpl);
                  } else {
                     boolean clientResponsibleForAcknowledge = messageImpl.getClientResponsibleForAcknowledge();
                     if (clientResponsibleForAcknowledge) {
                        this.statistics.incrementReceivedCount(messageImpl);
                        this.getSession().getStatistics().incrementReceivedCount(messageImpl);
                     } else {
                        this.statistics.incrementPendingCount(messageImpl);
                        this.getSession().getStatistics().incrementPendingCount(messageImpl);
                     }

                     long messageSizeNCR4A;
                     if (clientResponsibleForAcknowledge) {
                        messageSizeNCR4A = -1L;
                     } else {
                        messageSizeNCR4A = messageImpl.getPayloadSize() + (long)messageImpl.getUserPropertySize();
                     }

                     long sequenceNumber = receiveResponse.getSequenceNumber();
                     JMSPushEntry pushEntry;
                     synchronized(this.session) {
                        pushEntry = new JMSPushEntry(this.getSequencer().getJMSID(), this.consumerId, sequenceNumber, this.session.getNextSequenceNumber(), messageImpl.getDeliveryCount(), 0);
                        pushEntry.setClientResponsibleForAcknowledge(clientResponsibleForAcknowledge);
                        pushEntry.setDispatcher(this.getBackEndDispatcher());
                        if (!clientResponsibleForAcknowledge) {
                           this.session.addUnackedPushEntry(pushEntry, messageSizeNCR4A);
                        }
                     }

                     receiveResponse.setSequenceNumber(pushEntry.getFrontEndSequenceNumber());
                  }
               }
            } finally {
               if (this.session.isTransacted()) {
                  this.session.transactedDisinfect();
               }

            }

            return Integer.MAX_VALUE;
      }
   }

   public long getBytesPendingCount() {
      return this.statistics.getBytesPendingCount();
   }

   public long getBytesReceivedCount() {
      return this.statistics.getBytesReceivedCount();
   }

   public String getSubscriptionSharingPolicy() {
      return FEConnectionFactory.getSubscriptionSharingPolicyAsString(this.subscriptionSharingPolicy);
   }

   public String getClientID() {
      return this.session.getConnection().getConnectionClientId();
   }

   public String getClientIDPolicy() {
      return this.session.getConnection().getClientIdPolicy() == 1 ? JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED_STRING : JMSConstants.CLIENT_ID_POLICY_RESTRICTED_STRING;
   }

   public String getDestinationName() {
      return this.destination.getName();
   }

   public String getMemberDestinationName() {
      return this.destination.getMemberName();
   }

   public DestinationImpl getDestination() {
      return this.destination;
   }

   public long getMessagesPendingCount() {
      return this.statistics.getMessagesPendingCount();
   }

   public long getMessagesReceivedCount() {
      return this.statistics.getMessagesReceivedCount();
   }

   public boolean isActive() throws RemoteException {
      boolean var2;
      try {
         SecurityServiceManager.pushSubject(KERNEL_ID, this.authenticatedSubject);
         Object response = this.backEndDispatcher.dispatchSync(new BEConsumerIsActiveRequest(this.getJMSID()));
         var2 = ((BEConsumerIsActiveResponse)response).consumerIsActive;
      } catch (Throwable var6) {
         throw new RemoteException("Error setting consumer state, " + var6.toString());
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

      return var2;
   }

   public boolean isDurable() {
      return this.isDurable;
   }

   public String getSelector() {
      return this.selector;
   }

   public JMSID getJMSID() {
      return this.consumerId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.session.getDispatcherPartition4rmic();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.session.getConnection().getFrontEnd().getService(), "FEConsumer");
      switch (request.getMethodId()) {
         case 2570:
            this.close((FEConsumerCloseRequest)request);
            break;
         case 3082:
            this.incrementWindow(request);
            break;
         case 3338:
            return this.receive(request);
         case 3594:
            return this.setListener(request);
         case 15370:
            return this.pushException(request);
         case 17418:
            this.incrementWindowOneWay(request);
            break;
         default:
            throw new weblogic.jms.common.JMSException("No such method " + request.getMethodId());
      }

      request.setResult(new VoidResponse());
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   public int incrementRefCount() {
      return ++this.refCount;
   }

   public int decrementRefCount() {
      return --this.refCount;
   }

   public void dispatcherPeerGone(Exception e, Dispatcher dispatcher) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("FEConsumer.jmsPeerGone()");
      }

      FESession session = this.getSession();

      try {
         session.consumerRemove(this.getJMSID());
      } catch (Exception var5) {
      }

      try {
         session.pushException(6, this.consumerId, new ConsumerClosedException((MessageConsumer)null, "Connection to JMSServer was lost"));
      } catch (Throwable var6) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("remote error?", var6);
         }
      }

   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Consumer");
      xsw.writeAttribute("id", this.consumerId != null ? this.consumerId.toString() : "");
      xsw.writeAttribute("isDurable", String.valueOf(this.isDurable));
      xsw.writeAttribute("isLocal", String.valueOf(this.isLocal));
      xsw.writeAttribute("selector", this.selector != null ? this.selector : "");
      this.statistics.dump(imageSource, xsw);
      xsw.writeEndElement();
   }
}
