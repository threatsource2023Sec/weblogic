package weblogic.jms.frontend;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.TransactionRolledBackException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.JMSServiceServerLifeCycleImpl;
import weblogic.jms.backend.BEBrowserCreateRequest;
import weblogic.jms.backend.BEConsumerCloseRequest;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jms.backend.BESessionAcknowledgeRequest;
import weblogic.jms.backend.BESessionCloseRequest;
import weblogic.jms.backend.BESessionCreateRequest;
import weblogic.jms.backend.BESessionRecoverRequest;
import weblogic.jms.backend.BESessionSetRedeliveryDelayRequest;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.DDTxLoadBalancingOptimizer;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageContextImpl;
import weblogic.jms.common.JMSPeerGoneListener;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSSessionRecoverResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageStatistics;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.common.PushTarget;
import weblogic.jms.common.Sequencer;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.utils.tracing.MessageTimeStamp;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSConsumerRuntimeMBean;
import weblogic.management.runtime.JMSProducerRuntimeMBean;
import weblogic.management.runtime.JMSSessionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.interception.MessageInterceptionService;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.InterceptionPointHandle;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;
import weblogic.work.IDBasedConstraintEnforcement;
import weblogic.work.WorkManagerFactory;

public final class FESession extends RuntimeMBeanDelegate implements PushTarget, JMSSessionRuntimeMBean, Invocable, JMSPeerGoneListener, DDTxLoadBalancingOptimizer {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final long serialVersionUID = -2124132935744596179L;
   private final JMSID sessionId;
   private final FEConnection connection;
   private HashMap sequencers = new HashMap();
   private long nextSequenceNumber = 1L;
   private final JMSService service;
   private final MessageStatistics statistics = new MessageStatistics();
   private final boolean transacted;
   private final boolean xaSession;
   private volatile boolean transactionInUse;
   private boolean jmsAsyncSendTranInProgress = false;
   private TransactionManager tranManager;
   private Transaction transactedSessionTx;
   private JMSException transactedException;
   private final InvocableMonitor invocableMonitor;
   private Hashtable tranDestinations;
   private Hashtable tranPersistentDestinations;
   private Set tranDispatchers;
   private AuthenticatedSubject subjectForQOS = null;
   private final int acknowledgeMode;
   private final int originalAcknowledgeMode;
   private String pushWorkManager;
   private JMSPushEntry firstUnackedPushEntry;
   private JMSPushEntry lastUnackedPushEntry;
   private UnackedMessage firstTranStatUnackedMessage;
   private UnackedMessage lastTranStatUnackedMessage;
   private final HashMap consumers = new HashMap();
   private long consumersHighCount = 0L;
   private long consumersTotalCount = 0L;
   private final HashMap producers = new HashMap();
   private long producersHighCount = 0L;
   private long producersTotalCount = 0L;
   private final HashMap browsers = new HashMap();
   private long browsersHighCount = 0L;
   private HashMap beDispatchers = new HashMap();
   private InterceptionPointHandle receiveIPHandle = null;
   private DestinationImpl receiveIPDestination = null;
   private static Object interceptionPointLock = new Object();
   private boolean disableMultiSend = false;
   private transient int refCount;

   public FESession(FEConnection connection, String mbeanName, JMSID sessionId, boolean transacted, boolean xaSession, int acknowledgeMode, String workManager) throws ManagementException {
      super(mbeanName, connection.getRuntimeDelegate());
      this.connection = connection;
      this.sessionId = sessionId;
      this.transacted = transacted;
      this.xaSession = xaSession;
      this.invocableMonitor = connection.getFrontEnd().getInvocableMonitor();
      this.pushWorkManager = workManager;
      this.service = connection.getFrontEnd().getService();
      this.originalAcknowledgeMode = acknowledgeMode;
      if (transacted) {
         this.tranManager = TxHelper.getTransactionManager();
         this.acknowledgeMode = 2;
         this.tranDestinations = new Hashtable();
         this.tranPersistentDestinations = new Hashtable();
         this.tranDispatchers = Collections.synchronizedSet(new HashSet());
      } else {
         this.acknowledgeMode = acknowledgeMode;
      }

      String env90 = System.getProperty("weblogic.jms.DisableMultiSender");
      String env81 = System.getProperty("weblogic.jms.DisablePushEnvelope");
      if (env90 != null && Boolean.valueOf(env90) == Boolean.TRUE || env81 != null && Boolean.valueOf(env81) == Boolean.TRUE) {
         System.err.println("JMS FE Multi Sender DISABLED");
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FRONTEND/FESession (id: " + this.sessionId + ") : Disable MultiSend ");
         }

         this.disableMultiSend = true;
      }

   }

   private synchronized AuthenticatedSubject getSubjectForQOS() {
      return this.subjectForQOS;
   }

   synchronized void updateQOS() {
      AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (currentSubject != null) {
         if (this.subjectForQOS == null || this.subjectForQOS.getQOS() < currentSubject.getQOS()) {
            this.subjectForQOS = currentSubject;
         }

      }
   }

   public boolean isServerAffinityEnabled() {
      return this.connection.isServerAffinityEnabled();
   }

   public boolean isPerJVMProducerLoadBalancingEnabled() {
      return this.connection.isPerJVMProducerLoadBalancingEnabled();
   }

   public boolean visited(DistributedDestinationImpl dest) {
      return dest != null && this.tranDispatchers != null ? this.tranDispatchers.contains(dest.getDispatcherId()) : false;
   }

   public void addVisitedDispatcher(DistributedDestinationImpl dest) {
      if (dest != null && this.tranDispatchers != null) {
         this.tranDispatchers.add(dest.getDispatcherId());
      }
   }

   public void addCachedDest(DistributedDestinationImpl dest) {
      if (this.tranDestinations != null && this.tranPersistentDestinations != null && dest != null) {
         if (dest.isPersistent()) {
            this.tranDestinations.put(dest.getName(), dest);
            this.tranPersistentDestinations.put(dest.getName(), dest);
         } else {
            this.tranDestinations.put(dest.getName(), dest);
         }

      }
   }

   public DistributedDestinationImpl getCachedDest(String destinationName, boolean isPersistent) {
      if (this.tranDestinations != null && this.tranPersistentDestinations != null && destinationName != null) {
         DistributedDestinationImpl ret;
         if (isPersistent) {
            ret = (DistributedDestinationImpl)this.tranPersistentDestinations.get(destinationName);
         } else {
            ret = (DistributedDestinationImpl)this.tranDestinations.get(destinationName);
         }

         if (ret != null && ret.isStale()) {
            this.cleanFailure(ret);
            ret = null;
         }

         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            if (ret != null) {
               System.out.println("Session Pick: " + ret.getInstanceName());
            } else {
               System.out.println("Session Pick: null");
            }
         }

         return ret;
      } else {
         return null;
      }
   }

   public void cleanFailure(DestinationImpl d) {
      if (d != null) {
         if (this.tranDestinations != null) {
            this.tranDestinations.remove(d.getName());
         }

         if (this.tranPersistentDestinations != null) {
            this.tranPersistentDestinations.remove(d.getName());
         }

         if (this.tranDispatchers != null) {
            this.tranDispatchers.remove(d.getDispatcherId());
         }

      }
   }

   public void cleanAll() {
      if (this.tranDestinations != null) {
         this.tranDestinations.clear();
      }

      if (this.tranPersistentDestinations != null) {
         this.tranPersistentDestinations.clear();
      }

      if (this.tranDispatchers != null) {
         this.tranDispatchers.clear();
      }

   }

   public FEConnection getConnection() {
      return this.connection;
   }

   long getNextSequenceNumber() {
      return (long)(this.nextSequenceNumber++);
   }

   private long getSequenceNumber() {
      return this.nextSequenceNumber;
   }

   private void close(long lastSequenceNumber) throws JMSException {
      this.close(false, lastSequenceNumber, (JMSException)null);
   }

   void close(boolean allowDelayClose, long lastSequenceNumber, JMSException reasonException) throws JMSException {
      JMSException savedException = null;
      if (JMSServiceServerLifeCycleImpl.interceptionEnabled) {
         synchronized(interceptionPointLock) {
            if (this.receiveIPHandle != null && this.receiveIPDestination != null && (this.receiveIPDestination.getType() == 8 || this.receiveIPDestination.getType() == 4)) {
               try {
                  if (!this.receiveIPHandle.hasAssociation()) {
                     MessageInterceptionService.getSingleton().unRegisterInterceptionPoint(this.receiveIPHandle);
                     this.receiveIPHandle = null;
                  }
               } catch (InterceptionServiceException var79) {
                  JMSLogger.logFailedToUnregisterInterceptionPoint(var79);
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FESession.close(), Failure to unregister " + var79);
                  }
               }
            }
         }
      }

      boolean var40 = false;

      JMSPushEntry unackedPushEntry;
      HashMap dispatchers;
      HashMap sequencers;
      try {
         var40 = true;
         synchronized(this) {
            if (this.transacted && this.transactedSessionTx != null) {
               JMSException jmse = null;
               this.tranManager.forceResume(this.transactedSessionTx);

               try {
                  this.tranManager.rollback();
               } catch (SystemException var73) {
                  jmse = new weblogic.jms.common.JMSException(var73.getMessage(), var73);
               } finally {
                  this.transactedSessionTx = null;
                  this.transactedException = null;
                  this.transactionInUse = false;
                  this.jmsAsyncSendTranInProgress = false;
                  this.notifyAll();
                  this.cleanAll();
                  this.lastTranStatUnackedMessage = null;
                  this.firstTranStatUnackedMessage = null;
                  if (jmse != null) {
                     throw jmse;
                  }

               }
            }

            var40 = false;
         }
      } finally {
         if (var40) {
            synchronized(this) {
               this.transactionInUse = false;
               this.transactedException = null;
               this.jmsAsyncSendTranInProgress = false;
               this.notifyAll();
               Iterator iterator = ((HashMap)this.consumers.clone()).values().iterator();

               while(iterator.hasNext()) {
                  try {
                     JMSID myConsumerId = ((FEConsumer)iterator.next()).getJMSID();
                     if (JMSDebug.JMSCommon.isDebugEnabled()) {
                        JMSDebug.JMSCommon.debug("FRONTEND/FESession (id: " + this.sessionId + ") : Closing consumer " + myConsumerId);
                     }

                     if (reasonException != null) {
                        this.pushException(6, myConsumerId, (weblogic.jms.common.JMSException)reasonException);
                     }

                     this.consumerRemove(myConsumerId);
                  } catch (Throwable var66) {
                  }
               }

               iterator = ((HashMap)this.producers.clone()).values().iterator();

               while(iterator.hasNext()) {
                  try {
                     FEProducer myProducer = (FEProducer)iterator.next();
                     if (JMSDebug.JMSCommon.isDebugEnabled()) {
                        JMSDebug.JMSCommon.debug("FRONTEND/FESession (id: " + this.sessionId + ") : Closing Producer " + myProducer.getJMSID());
                     }

                     this.producerRemove(myProducer.getJMSID());
                     myProducer.removeDispatcher();
                  } catch (Throwable var65) {
                  }
               }

               iterator = ((HashMap)this.browsers.clone()).values().iterator();

               while(true) {
                  if (!iterator.hasNext()) {
                     unackedPushEntry = this.firstUnackedPushEntry;
                     this.firstUnackedPushEntry = this.lastUnackedPushEntry = null;
                     dispatchers = this.beDispatchers;
                     this.beDispatchers = new HashMap();
                     sequencers = this.sequencers;
                     this.sequencers = new HashMap();
                     iterator = dispatchers.values().iterator();

                     while(iterator.hasNext()) {
                        ((JMSDispatcher)iterator.next()).removeDispatcherPeerGoneListener(this);
                     }
                     break;
                  }

                  try {
                     this.browserRemove(((FEBrowser)iterator.next()).getJMSID());
                  } catch (Throwable var64) {
                  }
               }
            }

            if (lastSequenceNumber != 0L) {
               while(unackedPushEntry != null && unackedPushEntry.getFrontEndSequenceNumber() != lastSequenceNumber) {
                  unackedPushEntry = unackedPushEntry.getNextUnacked();
               }

               for(; unackedPushEntry != null; unackedPushEntry = unackedPushEntry.getPrevUnacked()) {
                  JMSDispatcher dispatcher = unackedPushEntry.getDispatcher();
                  dispatcher = (JMSDispatcher)dispatchers.remove(dispatcher.getId());

                  try {
                     if (dispatcher != null) {
                        dispatcher.dispatchSync(new BESessionCloseRequest(allowDelayClose, this.sessionId, unackedPushEntry.getBackEndSequenceNumber()));
                     }
                  } catch (Throwable var63) {
                     new weblogic.jms.common.JMSException("Error closing session", var63);
                  }
               }
            }

            Iterator iterator = dispatchers.values().iterator();
            BESessionCloseRequest request = new BESessionCloseRequest(allowDelayClose, this.sessionId, 0L);

            while(iterator.hasNext()) {
               try {
                  JMSDispatcher dispatcher = (JMSDispatcher)iterator.next();
                  dispatcher.dispatchSync(request);
                  request.clearResult();
                  request.clearDispatcherPartition4rmic();
               } catch (Throwable var62) {
                  new weblogic.jms.common.JMSException("Error closing session", var62);
               }
            }

            Iterator sit = sequencers.values().iterator();

            while(sit.hasNext()) {
               Sequencer sequencer = (Sequencer)sit.next();
               this.service.getInvocableManagerDelegate().invocableRemove(13, sequencer.getJMSID());
            }

            try {
               PrivilegedActionUtilities.unregister(this, KERNEL_ID);
            } catch (ManagementException var61) {
               JMSLogger.logErrorUnregisteringFrontEndSession(this.getConnection().getFrontEnd().getMbeanName(), this, var61);
            }

         }
      }

      synchronized(this) {
         this.transactionInUse = false;
         this.transactedException = null;
         this.jmsAsyncSendTranInProgress = false;
         this.notifyAll();
         Iterator iterator = ((HashMap)this.consumers.clone()).values().iterator();

         while(iterator.hasNext()) {
            try {
               JMSID myConsumerId = ((FEConsumer)iterator.next()).getJMSID();
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("FRONTEND/FESession (id: " + this.sessionId + ") : Closing consumer " + myConsumerId);
               }

               if (reasonException != null) {
                  this.pushException(6, myConsumerId, (weblogic.jms.common.JMSException)reasonException);
               }

               this.consumerRemove(myConsumerId);
            } catch (Throwable var72) {
            }
         }

         iterator = ((HashMap)this.producers.clone()).values().iterator();

         while(true) {
            if (!iterator.hasNext()) {
               iterator = ((HashMap)this.browsers.clone()).values().iterator();

               while(iterator.hasNext()) {
                  try {
                     this.browserRemove(((FEBrowser)iterator.next()).getJMSID());
                  } catch (Throwable var70) {
                  }
               }

               unackedPushEntry = this.firstUnackedPushEntry;
               this.firstUnackedPushEntry = this.lastUnackedPushEntry = null;
               dispatchers = this.beDispatchers;
               this.beDispatchers = new HashMap();
               sequencers = this.sequencers;
               this.sequencers = new HashMap();
               iterator = dispatchers.values().iterator();

               while(iterator.hasNext()) {
                  ((JMSDispatcher)iterator.next()).removeDispatcherPeerGoneListener(this);
               }
               break;
            }

            try {
               FEProducer myProducer = (FEProducer)iterator.next();
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("FRONTEND/FESession (id: " + this.sessionId + ") : Closing Producer " + myProducer.getJMSID());
               }

               this.producerRemove(myProducer.getJMSID());
               myProducer.removeDispatcher();
            } catch (Throwable var71) {
            }
         }
      }

      if (lastSequenceNumber != 0L) {
         while(unackedPushEntry != null && unackedPushEntry.getFrontEndSequenceNumber() != lastSequenceNumber) {
            unackedPushEntry = unackedPushEntry.getNextUnacked();
         }

         for(; unackedPushEntry != null; unackedPushEntry = unackedPushEntry.getPrevUnacked()) {
            JMSDispatcher dispatcher = unackedPushEntry.getDispatcher();
            dispatcher = (JMSDispatcher)dispatchers.remove(dispatcher.getId());

            try {
               if (dispatcher != null) {
                  dispatcher.dispatchSync(new BESessionCloseRequest(allowDelayClose, this.sessionId, unackedPushEntry.getBackEndSequenceNumber()));
               }
            } catch (Throwable var69) {
               savedException = new weblogic.jms.common.JMSException("Error closing session", var69);
            }
         }
      }

      Iterator iterator = dispatchers.values().iterator();
      BESessionCloseRequest request = new BESessionCloseRequest(allowDelayClose, this.sessionId, 0L);

      while(iterator.hasNext()) {
         try {
            JMSDispatcher dispatcher = (JMSDispatcher)iterator.next();
            dispatcher.dispatchSync(request);
            request.clearResult();
            request.clearDispatcherPartition4rmic();
         } catch (Throwable var68) {
            savedException = new weblogic.jms.common.JMSException("Error closing session", var68);
         }
      }

      Iterator sit = sequencers.values().iterator();

      while(sit.hasNext()) {
         Sequencer sequencer = (Sequencer)sit.next();
         this.service.getInvocableManagerDelegate().invocableRemove(13, sequencer.getJMSID());
      }

      try {
         PrivilegedActionUtilities.unregister(this, KERNEL_ID);
      } catch (ManagementException var67) {
         JMSLogger.logErrorUnregisteringFrontEndSession(this.getConnection().getFrontEnd().getMbeanName(), this, var67);
      }

      this.connection.sessionRemove(this);
      if (savedException != null) {
         throw savedException;
      }
   }

   private int producerCreate(FEProducerCreateRequest request) throws JMSException {
      DestinationImpl destination = request.getDestination();
      this.checkShutdownOrSuspended();
      this.checkPartition(destination);
      JMSService var10000 = this.service;
      final JMSID producerId = JMSService.getNextId();
      final String name = "producer" + producerId.getCounter();
      final DestinationImpl dest = destination;

      FEProducer producer;
      try {
         try {
            producer = (FEProducer)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() throws ManagementException, JMSException {
                  return new FEProducer(name, producerId, FESession.this, dest);
               }
            });
         } catch (PrivilegedActionException var10) {
            throw var10.getException();
         }

         this.producerAdd(producer);
      } catch (Exception var11) {
         this.cleanFailure(destination);
         if (var11 instanceof JMSException) {
            throw (JMSException)var11;
         }

         if (var11 instanceof ManagementException) {
            ManagementException me = (ManagementException)var11;
            Throwable nestedE = me.getNestedException();
            if (nestedE == null) {
               nestedE = me;
            }

            throw new weblogic.jms.common.JMSException("Error creating producer" + ((Throwable)nestedE).getMessage(), me);
         }

         throw new weblogic.jms.common.JMSException("Error creating producer " + var11.getMessage(), var11);
      }

      request.setResult(new FEProducerCreateResponse(producerId, producer.getName()));
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   void producerClose(FEProducer producer) {
      this.producerRemove(producer.getJMSID());
   }

   private synchronized void producerAdd(FEProducer producer) throws JMSException {
      if (this.producers.put(producer.getJMSID(), producer) == null) {
         this.service.getInvocableManagerDelegate().invocableAdd(9, producer);
         this.producersHighCount = Math.max(this.producersHighCount, (long)this.producers.size());
         ++this.producersTotalCount;
      }

   }

   private synchronized void producerRemove(JMSID producerId) {
      if (this.producers != null) {
         while(true) {
            IDBasedConstraintEnforcement id = IDBasedConstraintEnforcement.getInstance();
            synchronized(id) {
               if (id.getExecutingCount(producerId.getCounter()) + id.getPendingCount(producerId.getCounter()) == 0) {
                  break;
               }
            }

            try {
               Thread.sleep(500L);
            } catch (InterruptedException var6) {
            }
         }

         FEProducer producer = (FEProducer)this.producers.remove(producerId);
         this.notifyAll();
         if (producer != null) {
            producer.closeProducer();
            this.service.getInvocableManagerDelegate().invocableRemove(9, producerId);

            try {
               PrivilegedActionUtilities.unregister(producer, KERNEL_ID);
            } catch (ManagementException var5) {
               JMSLogger.logErrorUnregisteringProducer(this.getConnection().getFrontEnd().getMbeanName(), producer, var5);
            }

         }
      }
   }

   void checkShutdownOrSuspended() throws JMSException {
      this.connection.checkShutdownOrSuspended();
   }

   public synchronized JMSProducerRuntimeMBean[] getProducers() {
      JMSProducerRuntimeMBean[] retValue = new JMSProducerRuntimeMBean[this.producers.size()];
      Iterator it = this.producers.values().iterator();

      for(int i = 0; it.hasNext(); retValue[i++] = (JMSProducerRuntimeMBean)it.next()) {
      }

      return retValue;
   }

   public synchronized long getProducersCurrentCount() {
      return (long)this.producers.size();
   }

   public long getProducersHighCount() {
      return this.producersHighCount;
   }

   public synchronized long getProducersTotalCount() {
      return this.producersTotalCount;
   }

   public int getSubscriptionSharingPolicy() throws JMSException {
      this.checkShutdownOrSuspended();
      return this.connection.getSubscriptionSharingPolicyAsInt();
   }

   HashMap getConsumersMap() {
      return this.consumers;
   }

   HashMap getBEDispatchers() {
      return this.beDispatchers;
   }

   public Sequencer setUpBackEndSession(DispatcherId dispatcherId) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("FESession.setUpBackEndSession()");
      }

      boolean isStopped;
      long sequenceNumber;
      synchronized(this.connection) {
         isStopped = this.connection.isStopped();
         sequenceNumber = this.connection.getStartStopSequenceNumber();
      }

      byte peerVersion = this.connection.getPeerVersion();
      DispatcherWrapper dispatcherWrapper = this.service.createPartitionAwareDispatcherWrapper();
      synchronized(this) {
         JMSDispatcher dispatcher = (JMSDispatcher)this.beDispatchers.get(dispatcherId);
         if (dispatcher != null) {
            return (Sequencer)this.sequencers.get(dispatcherId);
         } else {
            try {
               dispatcher = this.service.dispatcherFindOrCreate(dispatcherId);
            } catch (DispatcherException var11) {
               throw new weblogic.jms.common.JMSException("Error creating session", var11);
            }

            Sequencer sequencer = new Sequencer(this, dispatcher, this.service.getDispatcherPartitionContext(), this.service.getInvocableMonitor());
            dispatcher.dispatchSync(new BESessionCreateRequest(this.service.getLocalId(), dispatcherWrapper, this.connection.getJMSID(), this.getJMSID(), sequencer.getJMSID(), this.transacted, this.xaSession, this.acknowledgeMode, isStopped, sequenceNumber, peerVersion, this.connection.getAddressAndMBeanInfo(), this.pushWorkManager));
            this.service.getInvocableManagerDelegate().invocableAdd(13, sequencer);
            this.sequencers.put(dispatcherId, sequencer);
            this.beDispatchers.put(dispatcherId, dispatcher);
            dispatcher.addDispatcherPeerGoneListener(this);
            return sequencer;
         }
      }
   }

   private int consumerCreate(final FEConsumerCreateRequest request) throws JMSException {
      this.checkShutdownOrSuspended();
      this.checkPartition(request.getDestination());
      DestinationImpl destination = null;
      FEDDHandler feDDHandler = null;
      ConsumerReconnectInfo cri = request.getConsumerReconnectInfo();
      DestinationImpl sequencer;
      if (cri != null && cri.getServerDestId() == null && request.isDurable()) {
         sequencer = request.getDestination();

         try {
            sequencer = this.connection.createDestination(sequencer.getServerName(), sequencer.getName(), sequencer.getType());
            request.setDestination(sequencer);
            cri.setServerDestId(sequencer.getDestinationId());
            request.setConsumerReconnectInfo(cri);
         } catch (Throwable var20) {
            throw JMSUtilities.jmsExceptionThrowable("Destination " + sequencer.getName() + " not found", var20);
         }
      }

      if (!request.getDestination().isQueue()) {
         String consumerName = request.getName();
         if (request.isDurable() && DDManager.isDD(request.getDestination().getName())) {
            throw new weblogic.jms.common.JMSException("Topic must not be Distributed Topic");
         }

         if (consumerName != null && this.getConnection().getConnectionClientId() == null && this.getConnection().getClientIdPolicy() == 0 && (DDManager.isDD(request.getDestination().getName()) || DDManager.findDDHandlerByMemberName(request.getDestination().getName()) != null)) {
            throw new weblogic.jms.common.JMSException("Client Id Policy must be UNRESTRICTED for distributed destination or distributed destination member: " + request.getDestination());
         }

         if (request.isDurable()) {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("in FESessionConsumer durable");
            }

            if (request.getSelector() != null && request.getSelector().trim().equals("TRUE")) {
               request.setSelector((String)null);
            }

            if (this.getConnection().getClientIdPolicy() == 0) {
               try {
                  DurableSubscription boundSubscriber = (DurableSubscription)this.service.getCtx().lookup(BEConsumerImpl.JNDINameForSubscription(BEConsumerImpl.clientIdPlusName(this.connection.getConnectionClientId(), request.getName())));
                  if (DurableSubscription.noLocalAndSelectorMatch(boundSubscriber, request.getNoLocal(), request.getSelector())) {
                     if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                        JMSDebug.JMSFrontEnd.debug("in FESessionConsumer matched consumer");
                     }

                     if (request.getDestination().equals(boundSubscriber.getDestinationImpl())) {
                        destination = boundSubscriber.getDestinationImpl();
                     }
                  } else if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FESession re/create new durable subscriber");
                  }
               } catch (NamingException var19) {
               }
            } else if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FESession re/create new durable subscriber using an Unrestricted ClientID");
            }
         } else {
            feDDHandler = DDManager.findFEDDHandlerByDDName(request.getDestination().getName());
            if (feDDHandler != null && JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("will load balance non-durable on dist Topic " + feDDHandler.getName());
            }
         }
      }

      if (destination == null && feDDHandler == null) {
         destination = request.getDestination();
         feDDHandler = DDManager.findFEDDHandlerByDDName(((DestinationImpl)destination).getName());
      }

      if (feDDHandler != null) {
         if (!request.getDestination().isQueue() && !request.isDurable()) {
            if (feDDHandler.getName().equals(request.getDestination().getName())) {
               destination = feDDHandler.consumerLoadBalance((FESession)null, request.getDestination().getName());
            } else {
               DistributedDestinationImpl dt = DDManager.findDDImplByDDName(request.getDestination().getName());
               if (dt == null) {
                  throw new JMSException("Destination not found " + request.getDestination().getName());
               }

               if (dt.isLocal()) {
                  destination = dt;
               } else {
                  destination = feDDHandler.consumerLoadBalance((FESession)null, request.getDestination().getName());
               }
            }

            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("local non durable topic available " + (destination == null ? null : ((DistributedDestinationImpl)destination).getInstanceName()));
            }
         } else {
            destination = feDDHandler.consumerLoadBalance(this, request.getDestination().getName());
         }
      } else if (destination != null && ((DestinationImpl)destination).getDestinationId() == null) {
         try {
            destination = this.connection.createDestination(((DestinationImpl)destination).getServerName(), ((DestinationImpl)destination).getName(), ((DestinationImpl)destination).getType());
         } catch (Throwable var18) {
            throw JMSUtilities.jmsExceptionThrowable("Destination " + ((DestinationImpl)destination).getName() + " not found", var18);
         }
      }

      if (destination == null) {
         throw new JMSException("Destination not found " + request.getDestination().getName());
      } else {
         sequencer = null;

         Sequencer sequencer;
         try {
            sequencer = this.setUpBackEndSession(((DestinationImpl)destination).getDispatcherId());
         } catch (JMSException var22) {
            if (destination instanceof DistributedDestinationImpl) {
               throw var22;
            }

            try {
               destination = this.connection.createDestination(((DestinationImpl)destination).getServerName(), ((DestinationImpl)destination).getName(), ((DestinationImpl)destination).getType());
               sequencer = this.setUpBackEndSession(((DestinationImpl)destination).getDispatcherId());
            } catch (JMSException var17) {
               throw var22;
            }
         }

         final DestinationImpl destinationFinal = destination;
         final Sequencer sequencerFinal = sequencer;
         JMSService var10000 = this.service;
         final JMSID consumerId = JMSService.getNextId();
         final String mbeanName = "consumer" + consumerId.getCounter();
         final AuthenticatedSubject authenticatedSubject = JMSSecurityHelper.getCurrentSubject();
         final String subject = JMSSecurityHelper.getSimpleAuthenticatedName();

         FEConsumer consumer;
         try {
            try {
               consumer = (FEConsumer)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
                  public Object run() throws ManagementException, JMSException {
                     return new FEConsumer(mbeanName, FESession.this, sequencerFinal, FESession.this.connection.getConnectionClientId(), (DestinationImpl)destinationFinal, consumerId, subject, authenticatedSubject, request);
                  }
               });
            } catch (PrivilegedActionException var16) {
               throw var16.getException();
            }

            this.consumerAdd(consumer);
         } catch (Exception var21) {
            this.cleanFailure((DestinationImpl)destination);
            if (var21 instanceof JMSException) {
               throw (JMSException)var21;
            }

            if (var21 instanceof ManagementException) {
               ManagementException me = (ManagementException)var21;
               Throwable nestedE = me.getNestedException();
               if (nestedE == null) {
                  nestedE = me;
               }

               throw new weblogic.jms.common.JMSException("Error creating consumer " + ((Throwable)nestedE).getMessage(), me);
            }

            throw new weblogic.jms.common.JMSException("Error creating consumer " + var21.getMessage(), var21);
         }

         request.setResult(new FEConsumerCreateResponse(consumer.getJMSID(), consumer.getName(), consumer.getConsumerReconnectInfo()));
         request.setState(Integer.MAX_VALUE);
         return request.getState();
      }
   }

   private synchronized FEConsumer consumerFind(JMSID consumerId) throws JMSException {
      FEConsumer consumer = (FEConsumer)this.consumers.get(consumerId);
      if (consumer != null) {
         return consumer;
      } else {
         throw new weblogic.jms.common.JMSException("Consumer not found, " + consumerId);
      }
   }

   private synchronized void consumerAdd(FEConsumer consumer) throws JMSException {
      if (this.consumers.put(consumer.getJMSID(), consumer) == null) {
         this.consumersHighCount = Math.max(this.consumersHighCount, (long)this.consumers.size());
         ++this.consumersTotalCount;
      }

      this.service.getInvocableManagerDelegate().invocableAdd(10, consumer);
   }

   synchronized FEConsumer consumerRemove(JMSID consumerId) throws JMSException {
      FEConsumer consumer = (FEConsumer)this.consumers.remove(consumerId);
      if (consumer == null) {
         throw new weblogic.jms.common.JMSException("Consumer not found, " + consumerId);
      } else {
         this.service.getInvocableManagerDelegate().invocableRemove(10, consumerId);

         try {
            PrivilegedActionUtilities.unregister(consumer, KERNEL_ID);
         } catch (ManagementException var7) {
            JMSLogger.logErrorUnregisteringConsumer(this.getConnection().getFrontEnd().getMbeanName(), consumer, var7);
         } finally {
            consumer.getBackEndDispatcher().removeDispatcherPeerGoneListener(this);
            consumer.getBackEndDispatcher().removeDispatcherPeerGoneListener(consumer);
         }

         return consumer;
      }
   }

   BEConsumerCloseRequest consumerClose(FEConsumer consumer, FEConsumerCloseRequest request) {
      long lastSequenceNumber = request.getLastSequenceNumber();
      long firstSequenceNumberNotSeen = request.getFirstSequenceNumberNotSeen();
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("Closing consumer=" + consumer.getJMSID() + ", frontend lastSequenceNumber=" + lastSequenceNumber + ", frontend firstSequenceNumberNotSeen=" + firstSequenceNumberNotSeen);
      }

      synchronized(this) {
         JMSPushEntry unackedPushEntry;
         if (lastSequenceNumber != 0L) {
            for(unackedPushEntry = this.firstUnackedPushEntry; unackedPushEntry != null && unackedPushEntry.getFrontEndSequenceNumber() != lastSequenceNumber; unackedPushEntry = unackedPushEntry.getNextUnacked()) {
            }

            while(unackedPushEntry != null && !unackedPushEntry.getConsumerId().equals(consumer.getJMSID())) {
               unackedPushEntry = unackedPushEntry.getPrevUnacked();
            }

            if (unackedPushEntry == null) {
               lastSequenceNumber = 0L;
            } else {
               lastSequenceNumber = unackedPushEntry.getBackEndSequenceNumber();
            }
         }

         if (firstSequenceNumberNotSeen != 0L) {
            for(unackedPushEntry = this.firstUnackedPushEntry; unackedPushEntry != null && unackedPushEntry.getFrontEndSequenceNumber() != firstSequenceNumberNotSeen; unackedPushEntry = unackedPushEntry.getNextUnacked()) {
            }

            if (unackedPushEntry != null && unackedPushEntry.getConsumerId().equals(consumer.getJMSID())) {
               firstSequenceNumberNotSeen = unackedPushEntry.getBackEndSequenceNumber();
            } else {
               firstSequenceNumberNotSeen = 0L;
            }
         }
      }

      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("Closing consumer=" + consumer.getJMSID() + ", backend lastSequenceNumber=" + lastSequenceNumber + ", backend firstSequenceNumberNotSeen=" + firstSequenceNumberNotSeen);
      }

      return new BEConsumerCloseRequest(consumer.getJMSID(), lastSequenceNumber, firstSequenceNumberNotSeen);
   }

   private synchronized void browserAdd(FEBrowser browser) throws JMSException {
      if (this.browsers.put(browser.getJMSID(), browser) == null) {
         this.service.getInvocableManagerDelegate().invocableAdd(11, browser);
         this.browsersHighCount = Math.max(this.browsersHighCount, (long)this.browsers.size());
      }

   }

   private int browserCreate(FEBrowserCreateRequest request) throws JMSException {
      this.checkShutdownOrSuspended();
      this.checkPartition(request.getDestination());
      switch (request.getState()) {
         case 0:
            DestinationImpl destination = request.getDestination();
            FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(destination.getName());
            if (feDDHandler != null) {
               destination = feDDHandler.consumerLoadBalance((FESession)null, destination.getName());
            }

            BEBrowserCreateRequest child = new BEBrowserCreateRequest(this.sessionId, destination.getDestinationId(), request.getMessageSelector());
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               DispatcherId dispatcherId = destination.getDispatcherId();
               request.setDispatcher(this.service.dispatcherFindOrCreate(dispatcherId));
               this.setUpBackEndSession(dispatcherId);
               request.dispatchAsync(request.getDispatcher(), child);
            } catch (DispatcherException var7) {
               throw new weblogic.jms.common.JMSException("Error creating browser", var7);
            }

            return request.getState();
         case 1:
         default:
            JMSBrowserCreateResponse response = (JMSBrowserCreateResponse)request.useChildResult(JMSBrowserCreateResponse.class);
            FEBrowser browser = new FEBrowser(this.connection, this, response.getBrowserId(), request.getDispatcher());
            this.browserAdd(browser);
            return Integer.MAX_VALUE;
      }
   }

   synchronized void browserRemove(JMSID browserId) throws JMSException {
      if (this.browsers.remove(browserId) == null) {
         throw new weblogic.jms.common.JMSException("Browser not found, " + browserId);
      } else {
         this.service.getInvocableManagerDelegate().invocableRemove(11, browserId);
      }
   }

   public synchronized JMSConsumerRuntimeMBean[] getConsumers() {
      JMSConsumerRuntimeMBean[] retValue = new JMSConsumerRuntimeMBean[this.consumers.size()];
      Iterator it = this.consumers.values().iterator();

      for(int i = 0; it.hasNext(); retValue[i++] = (JMSConsumerRuntimeMBean)it.next()) {
      }

      return retValue;
   }

   public synchronized long getConsumersCurrentCount() {
      return (long)this.consumers.size();
   }

   public synchronized long getConsumersHighCount() {
      return this.consumersHighCount;
   }

   public synchronized long getConsumersTotalCount() {
      return this.consumersTotalCount;
   }

   private int recover(FESessionRecoverRequest request) throws JMSException {
      return request.getPipelineGeneration() == 0 ? this.recover81(request) : this.recover90(request);
   }

   private int recover81(FESessionRecoverRequest request) throws JMSException {
      boolean doRollback = request.doRollback();
      int state = request.getState();
      JMSException savedException = null;
      state = this.recover81Init(state, request, doRollback);
      switch (state) {
         case 0:
            this.recover81TransactionSetup(request);
         case 1:
            long lastSequenceNumber = request.getLastSequenceNumber();
            state = 2;
            request.setState(2);
            JMSPushEntry unackedPushEntry;
            JMSPushEntry myUnackedPushEntry;
            HashMap sequencersCopy;
            synchronized(this) {
               request.setLastSequenceNumber(this.getSequenceNumber());
               myUnackedPushEntry = unackedPushEntry = this.firstUnackedPushEntry;
               this.firstUnackedPushEntry = this.lastUnackedPushEntry = null;
               sequencersCopy = (HashMap)this.sequencers.clone();
            }

            request.needOutsideResult();
            this.recover81Statistics(myUnackedPushEntry, lastSequenceNumber);
            if (lastSequenceNumber != 0L) {
               while(unackedPushEntry != null && unackedPushEntry.getFrontEndSequenceNumber() != lastSequenceNumber) {
                  unackedPushEntry = unackedPushEntry.getNextUnacked();
               }
            }

            if (unackedPushEntry != null) {
               for(; unackedPushEntry != null; unackedPushEntry = unackedPushEntry.getPrevUnacked()) {
                  Sequencer sequencer = (Sequencer)sequencersCopy.remove(unackedPushEntry.getDispatcher().getId());
                  if (sequencer != null) {
                     BESessionRecoverRequest childRequest = new BESessionRecoverRequest(this.sessionId, unackedPushEntry.getBackEndSequenceNumber(), sequencer, 0);

                     try {
                        childRequest.setNext(request.getChildRequests());
                        request.setChildRequests(childRequest);
                        request.dispatchAsync(sequencer.getDispatcher(), childRequest);
                     } catch (DispatcherException var15) {
                        if (savedException == null) {
                           savedException = new weblogic.jms.common.JMSException("Error recovering messages", var15);
                        }
                     }
                  }
               }
            }

            if (doRollback) {
               if (request.fanoutCompleteSuspendIfHaveChildren(false)) {
                  return state;
               }
            } else if (request.fanoutComplete(false)) {
               return state;
            }
         case 2:
            state = Integer.MAX_VALUE;
            request.setState(Integer.MAX_VALUE);

            for(BESessionRecoverRequest childRequest = (BESessionRecoverRequest)request.getChildRequests(); childRequest != null; childRequest = (BESessionRecoverRequest)childRequest.getNext()) {
               try {
                  Object response = childRequest.getResult();
                  childRequest.getSequencer().changeExpectedSequenceNumberCanHaveRemainder(((JMSSessionRecoverResponse)response).getSequenceNumber());
               } catch (Throwable var14) {
                  if (var14 instanceof JMSException) {
                     savedException = (JMSException)var14;
                  } else {
                     savedException = new weblogic.jms.common.JMSException("Error recovering session", var14);
                  }
               }
            }

            if (savedException != null) {
               if (!doRollback) {
                  throw savedException;
               }
            } else {
               request.setResult(new JMSSessionRecoverResponse(request.getLastSequenceNumber()));
               if (!doRollback) {
                  return state;
               }
            }
         case 3:
            break;
         default:
            return state;
      }

      if (this.transactedException == null) {
         this.transactedException = (JMSException)savedException;
      }

      this.rollbackAfterRecover();
      return state;
   }

   private int recover81Init(int state, FESessionRecoverRequest request, boolean doRollback) throws JMSException {
      if (state == 0 && !doRollback) {
         this.checkShutdownOrSuspended();
         state = 1;
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FERecover start");
         }
      }

      return state;
   }

   private final JMSPushEntry recover81Statistics(JMSPushEntry unackedPushEntry, long lastSequenceNumber) {
      JMSPushEntry found;
      for(found = null; unackedPushEntry != null; unackedPushEntry = unackedPushEntry.getNextUnacked()) {
         if (unackedPushEntry.getFrontEndSequenceNumber() <= lastSequenceNumber) {
            found = unackedPushEntry;
         }

         this.statistics.decrementPendingCount(unackedPushEntry.getMessageSize());

         FEConsumer consumer;
         try {
            consumer = this.consumerFind(unackedPushEntry.getConsumerId());
         } catch (JMSException var7) {
            continue;
         }

         consumer.statistics.decrementPendingCount(unackedPushEntry.getMessageSize());
      }

      return found;
   }

   private void recover81TransactionSetup(FESessionRecoverRequest request) {
      request.setTranInfo(1);
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FERollback start");
      }

      synchronized(this) {
         this.waitForTransactedDisinfected(false);
         if (this.transactionInUse) {
            if (this.transactedException == null) {
               this.transactedException = new weblogic.jms.common.JMSException("Only one thread may use a JMS Session at a time.");
            }
         } else {
            this.transactionInUse = true;
         }

      }
   }

   private int recover90(FESessionRecoverRequest request) throws JMSException {
      int state = request.getState();
      JMSException savedException = null;
      state = this.recover81Init(state, request, request.doRollback());
      BESessionRecoverRequest requestList;
      switch (state) {
         case 0:
            this.recover81TransactionSetup(request);
         case 1:
            int state = true;
            request.setState(2);
            synchronized(this) {
               JMSPushEntry largestRecoveredPushEntry = this.recover81Statistics(this.firstUnackedPushEntry, request.getLastSequenceNumber());
               requestList = this.get90beRecoverRequests(largestRecoveredPushEntry, request.getPipelineGeneration());
               this.firstUnackedPushEntry = this.lastUnackedPushEntry = null;
            }

            request.setChildRequests(requestList);

            while(requestList != null) {
               try {
                  BESessionRecoverRequest currentChild = (BESessionRecoverRequest)requestList;
                  requestList = (BESessionRecoverRequest)requestList.getNext();
                  currentChild.getSequencer().getDispatcher().dispatchSyncTran(currentChild);
               } catch (JMSException var9) {
                  if (savedException == null) {
                     savedException = var9;
                  }
               }
            }

            if (request.doRollback()) {
               if (savedException != null && this.transactedException == null) {
                  this.transactedException = savedException;
               }

               this.rollbackAfterRecover();
            }

            if (savedException != null) {
               throw savedException;
            }
         case 2:
            for(requestList = (BESessionRecoverRequest)request.getChildRequests(); requestList != null; requestList = (BESessionRecoverRequest)requestList.getNext()) {
               Object response = requestList.getResult();
            }

            request.setResult(new JMSSessionRecoverResponse(request.getLastSequenceNumber()));
            state = Integer.MAX_VALUE;
            request.setState(Integer.MAX_VALUE);
            return state;
         default:
            throw new AssertionError();
      }
   }

   private final BESessionRecoverRequest get90beRecoverRequests(JMSPushEntry recoveredPushEntry, int pipelineGeneration) {
      BESessionRecoverRequest beRequestList = null;
      HashMap sequencersCopy = (HashMap)this.sequencers.clone();

      while(true) {
         while(recoveredPushEntry != null) {
            Sequencer sequencer = (Sequencer)sequencersCopy.remove(recoveredPushEntry.getDispatcher().getId());
            if (sequencer == null) {
               recoveredPushEntry = recoveredPushEntry.getPrevUnacked();
            } else {
               beRequestList = this.one90beRecoverRequest(sequencer, recoveredPushEntry.getBackEndSequenceNumber(), pipelineGeneration, beRequestList);

               for(recoveredPushEntry = recoveredPushEntry.getPrevUnacked(); recoveredPushEntry != null && recoveredPushEntry.getSequencerId() == sequencer.getJMSID(); recoveredPushEntry = recoveredPushEntry.getPrevUnacked()) {
               }
            }
         }

         if (sequencersCopy.isEmpty()) {
            return beRequestList;
         }

         for(Iterator iterator = sequencersCopy.values().iterator(); iterator.hasNext(); beRequestList = this.one90beRecoverRequest((Sequencer)iterator.next(), 0L, pipelineGeneration, beRequestList)) {
         }

         return beRequestList;
      }
   }

   private BESessionRecoverRequest one90beRecoverRequest(Sequencer sequencer, long recoveredSequenceNumber, int pipelineGeneration, BESessionRecoverRequest next) {
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FESession recover message " + recoveredSequenceNumber);
      }

      BESessionRecoverRequest ret = new BESessionRecoverRequest(this.sessionId, recoveredSequenceNumber, sequencer, pipelineGeneration);
      ret.setNext(next);
      return ret;
   }

   private int acknowledge(FESessionAcknowledgeRequest request) throws JMSException {
      boolean forceRollback = false;
      Throwable commitFailure = null;
      JMSException savedException = null;
      Request first = null;
      Request last = null;
      long lastSequenceNumber = request.getLastSequenceNumber();
      boolean doCommit = request.doCommit();
      int state = request.getState();
      if (state == 0 && !doCommit) {
         this.checkShutdownOrSuspended();
         state = 1;
      }

      label486: {
         label485: {
            label484: {
               label515: {
                  JMSPushEntry unackedPushEntry;
                  switch (state) {
                     case 0:
                        request.setTranInfo(1);
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("FESession.commit " + this.hashCode());
                        }

                        boolean unackedIsEmpty;
                        synchronized(this) {
                           unackedPushEntry = this.lastUnackedPushEntry;
                           unackedIsEmpty = this.lastUnackedPushEntry == null;
                           this.waitForTransactedDisinfected(false);
                           if (this.transactionInUse) {
                              if (this.transactedException == null) {
                                 this.transactedException = new weblogic.jms.common.JMSException("Only one thread may use a JMS Session at a time.");
                              }
                           } else if (this.transactedException == null && lastSequenceNumber != 0L) {
                              while(unackedPushEntry != null && (unackedPushEntry.getFrontEndSequenceNumber() != lastSequenceNumber || this.consumers.size() > 1) && (unackedPushEntry.getFrontEndSequenceNumber() > lastSequenceNumber || this.consumers.size() <= 1)) {
                                 unackedPushEntry = unackedPushEntry.getPrevUnacked();
                              }
                           }
                        }

                        try {
                           int var13;
                           if (this.transactedException != null) {
                              forceRollback = true;
                              commitFailure = this.transactedException;
                              var13 = Integer.MAX_VALUE;
                              return var13;
                           }

                           try {
                              if (this.transactedSessionTx == null && (unackedPushEntry == null || unackedIsEmpty)) {
                                 if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                                    JMSDebug.JMSFrontEnd.debug("JMSSession.commit had no work");
                                 }

                                 var13 = Integer.MAX_VALUE;
                                 return var13;
                              }

                              this.transactedInfect();
                           } catch (Throwable var27) {
                              this.transactedSessionTx = null;
                              this.cleanAll();
                              commitFailure = var27;
                              forceRollback = true;
                              int var14 = Integer.MAX_VALUE;
                              return var14;
                           }

                           if (unackedPushEntry != null || lastSequenceNumber == 0L && !unackedIsEmpty) {
                              state = 1;
                           }
                        } finally {
                           if (state != 1) {
                              this.commitAfterAcknowledge(forceRollback, request, (Throwable)commitFailure);
                              return Integer.MAX_VALUE;
                           }

                        }
                     case 1:
                        break;
                     case 2:
                        break label515;
                     case 3:
                        break label484;
                     default:
                        break label486;
                  }

                  state = 2;
                  synchronized(this) {
                     for(unackedPushEntry = this.lastUnackedPushEntry; unackedPushEntry != null && (unackedPushEntry.getFrontEndSequenceNumber() != lastSequenceNumber || this.consumers.size() > 1) && (unackedPushEntry.getFrontEndSequenceNumber() > lastSequenceNumber || this.consumers.size() <= 1); unackedPushEntry = unackedPushEntry.getPrevUnacked()) {
                     }

                     if (unackedPushEntry != null) {
                        if ((this.firstUnackedPushEntry = unackedPushEntry.getNextUnacked()) != null) {
                           this.firstUnackedPushEntry.setPrevUnacked((JMSPushEntry)null);
                        } else {
                           this.lastUnackedPushEntry = null;
                        }
                     }
                  }

                  if (unackedPushEntry == null) {
                     if (!doCommit) {
                        request.setResult(new VoidResponse());
                     } else {
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("FESession.acknowlege unackedPushEntry null, sequenceNumber=" + lastSequenceNumber);
                        }

                        this.commitAfterAcknowledge(forceRollback, request, new weblogic.jms.common.JMSException("commit message not found"));
                     }

                     return Integer.MAX_VALUE;
                  }

                  request.needOutsideResult();

                  for(HashMap dispatchersCopy = new HashMap(); unackedPushEntry != null; unackedPushEntry = unackedPushEntry.getPrevUnacked()) {
                     try {
                        FEConsumer consumer = this.consumerFind(unackedPushEntry.getConsumerId());
                        consumer.statistics.decrementPendingCount(unackedPushEntry.getMessageSize());
                        consumer.statistics.incrementReceivedCount(unackedPushEntry.getMessageSize());
                     } catch (JMSException var23) {
                     }

                     this.statistics.decrementPendingCount(unackedPushEntry.getMessageSize());
                     this.statistics.incrementReceivedCount(unackedPushEntry.getMessageSize());
                     JMSDispatcher dispatcher = unackedPushEntry.getDispatcher();
                     BESessionAcknowledgeRequest child = new BESessionAcknowledgeRequest(this.sessionId, unackedPushEntry.getBackEndSequenceNumber());
                     if (dispatchersCopy.put(dispatcher.getId(), dispatcher) == null) {
                        if (first == null) {
                           last = child;
                           first = child;
                        } else {
                           last.setNext(child);
                           last = child;
                        }

                        try {
                           request.dispatchAsync(dispatcher, child);
                        } catch (DispatcherException var25) {
                           if (savedException == null) {
                              savedException = new weblogic.jms.common.JMSException("Error acknowledging messages", var25);
                           }
                        }
                     }
                  }

                  request.rememberChild(first);
                  if (doCommit) {
                     if (request.fanoutCompleteSuspendIfHaveChildren(true)) {
                        break label485;
                     }
                  } else if (request.fanoutComplete(true)) {
                     break label485;
                  }
               }

               if (!doCommit) {
                  Response result = this.checkChildExceptions(request);
                  request.setResult(result);
                  return Integer.MAX_VALUE;
               }
            }

            this.commitAfterAcknowledge(forceRollback, request, (Throwable)commitFailure);
            state = Integer.MAX_VALUE;
            break label486;
         }

         if (savedException != null) {
            throw savedException;
         }
      }

      request.setState(state);
      return state;
   }

   private JMSException transactedException(String text, Throwable t) {
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("throwTransactedException() B " + text, t);
      }

      if (!(t instanceof JMSException) || ((JMSException)t).getErrorCode() == null || !((JMSException)t).getErrorCode().equals("ReservedRollbackOnly")) {
         text = t.getMessage() + ":" + text;
      }

      if (this.transactedSessionTx != null) {
         try {
            int status = this.transactedSessionTx.getStatus();
            if (status == 9 || status == 4 || status == 1) {
               TransactionRolledBackException tre = new TransactionRolledBackException(text, "ReservedRollbackOnly");
               tre.initCause(t);
               return tre;
            }
         } catch (SystemException var5) {
         }
      }

      return new weblogic.jms.common.JMSException(text, t);
   }

   private void throwTransactedException(String text, Throwable t) throws JMSException {
      JMSException trbe = this.transactedException(text, t);
      if (this.transactedException == null) {
         this.transactedException = trbe;
      }

      throw trbe;
   }

   private void waitForTransactedDisinfected(boolean checkForSAF, JMSID jmsAsyncSendProducerId, long sendTimeout) throws JMSException {
      long timeout = 2L * (long)this.getTransactionTimeout(checkForSAF) * 1000L;
      if (timeout > 2147483647L) {
         timeout = 2147483647L;
      }

      long waitime = timeout;

      while(this.transactionInUse && this.producers.get(jmsAsyncSendProducerId) != null) {
         long startime = System.currentTimeMillis();

         try {
            this.wait(waitime);
         } catch (InterruptedException var12) {
         }

         if (this.transactionInUse && this.producers.get(jmsAsyncSendProducerId) != null) {
            waitime -= System.currentTimeMillis() - startime;
            if (waitime <= 0L) {
               throw new JMSException("Timed out(" + timeout + " ms) in waiting for transaction lock for producer[" + jmsAsyncSendProducerId + "]");
            }
         }
      }

      if (this.producers.get(jmsAsyncSendProducerId) == null) {
         throw new JMSException("Producer[" + jmsAsyncSendProducerId + "] is closed");
      }
   }

   private boolean waitForTransactedDisinfected(boolean checkForSAF) {
      long timeout = 2L * (long)this.getTransactionTimeout(checkForSAF) * 1000L;
      if (timeout > 2147483647L) {
         timeout = 2147483647L;
      }

      while(this.jmsAsyncSendTranInProgress) {
         long startime = System.currentTimeMillis();

         try {
            this.wait(timeout);
         } catch (InterruptedException var7) {
         }

         if (this.jmsAsyncSendTranInProgress) {
            timeout -= System.currentTimeMillis() - startime;
            if (timeout <= 0L) {
               return false;
            }
         }
      }

      return true;
   }

   int getTransactionTimeout(boolean checkForSAF) {
      if (checkForSAF) {
         return FEConnectionFactory.DEFAULT_SAF_TX_TIMEOUT;
      } else {
         int timeout;
         if (this.connection.getTransactionTimeout() > 2147483647L) {
            timeout = Integer.MAX_VALUE;
         } else if (this.connection.getTransactionTimeout() > 0L) {
            timeout = (int)this.connection.getTransactionTimeout();
         } else {
            timeout = 3600;
         }

         return timeout;
      }
   }

   public synchronized void transactedInfect() throws JMSException {
      this.transactedInfect(false, (JMSID)null, 0L);
   }

   public synchronized void transactedInfect(boolean chkforSAF, JMSID jmsAsyncSendProducerId, long sendTimeout) throws JMSException {
      if (jmsAsyncSendProducerId == null) {
         this.waitForTransactedDisinfected(chkforSAF);
      } else {
         this.waitForTransactedDisinfected(chkforSAF, jmsAsyncSendProducerId, sendTimeout);
      }

      String exceptionString;
      if (this.transactionInUse) {
         if (this.transactedException == null) {
            exceptionString = "Only one thread may use a JMS Session at a time.";
            this.throwTransactedException(exceptionString, new weblogic.jms.common.JMSException(exceptionString));
         }

         throw this.transactedException;
      } else if (this.transactedSessionTx != null && this.transactedException != null) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FESession.transactedInfect() failed ", this.transactedException);
         }

         throw this.transactedException;
      } else {
         exceptionString = null;

         try {
            if (this.transactedSessionTx != null) {
               exceptionString = "error resuming transacted session's internal transaction";
               this.tranManager.resume(this.transactedSessionTx);
               this.transactionInUse = true;
               this.jmsAsyncSendTranInProgress = jmsAsyncSendProducerId != null;
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FESession.transactedInfect() resume tx=" + TxHelper.getTransaction());
               }
            } else {
               exceptionString = "error beginning transacted session's internal transaction";
               int timeout = this.getTransactionTimeout(chkforSAF);
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FESession.transactedInfect() begin, timeout = " + timeout);
               }

               this.tranManager.setTransactionTimeout(timeout);
               this.tranManager.begin("JMS Internal");
               this.transactedSessionTx = TxHelper.getTransaction();
               this.transactionInUse = true;
               this.jmsAsyncSendTranInProgress = jmsAsyncSendProducerId != null;
               synchronized(this) {
                  this.transactedSessionTx.setName(this.getName());
               }

               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FESession.transactedInfect() begin = " + this.transactedSessionTx);
               }
            }
         } catch (Exception var10) {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FESession.transactedInfect() failed ", var10);
            }

            this.throwTransactedException(exceptionString, var10);
         }

      }
   }

   synchronized void transactedDisinfect() throws JMSException {
      try {
         javax.transaction.Transaction previous = this.tranManager.suspend();
         if (this.transactedSessionTx != null && this.transactedSessionTx != previous && previous != null) {
            Exception e = new Exception("Expected: " + this.transactedSessionTx + ", but got: " + previous + " during transaction disinfect ");
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FESession.transactedDisinfect() expected:" + this.transactedSessionTx + ", but got:" + previous + "\n" + e);
            }

            this.throwTransactedException("Error suspending transacted session's internal transaction", e);
         }

         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FESession.transactedDisinfect() suspend tx = " + this.transactedSessionTx);
         }
      } catch (SystemException var6) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FESession.pTransactedDisinfect() failed", var6);
         }

         this.throwTransactedException("SystemException suspending transacted session's internal transaction", var6);
      } finally {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("disinfected in finally");
         }

         this.transactionInUse = false;
         this.jmsAsyncSendTranInProgress = false;
         this.notifyAll();
      }

   }

   private synchronized void transactedUnackedStatistics(boolean commit) {
      for(UnackedMessage statInfo = this.firstTranStatUnackedMessage; statInfo != null; statInfo = statInfo.getNext()) {
         if (commit) {
            statInfo.commitTransactedStatistics(this);
         } else {
            statInfo.rollbackTransactedStatistics(this);
         }
      }

      this.lastTranStatUnackedMessage = this.firstTranStatUnackedMessage = null;
   }

   private void rollbackAfterRecover() throws JMSException {
      Object rollbackFailure = this.transactedException;
      boolean var19 = false;

      label278: {
         label279: {
            try {
               label280: {
                  var19 = true;
                  synchronized(this) {
                     if (this.transactedSessionTx == null) {
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("FESession.rollback had no tran");
                        }

                        var19 = false;
                        break label278;
                     }

                     try {
                        this.tranManager.forceResume(this.transactedSessionTx);
                     } catch (Exception var25) {
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("FESession.rollback resume failed", var25);
                        }

                        rollbackFailure = var25;
                        var19 = false;
                        break label280;
                     }
                  }

                  try {
                     this.tranManager.rollback();
                     var19 = false;
                  } catch (Throwable var24) {
                     rollbackFailure = var24;
                     var19 = false;
                  }
                  break label279;
               }
            } finally {
               if (var19) {
                  synchronized(this) {
                     this.transactedException = null;
                     this.transactionInUse = false;
                     this.transactedSessionTx = null;
                     this.jmsAsyncSendTranInProgress = false;
                     this.notifyAll();
                  }

                  this.cleanAll();
                  this.tranManager.forceSuspend();
                  this.transactedUnackedStatistics(false);
                  if (rollbackFailure != null) {
                     if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                        JMSDebug.JMSFrontEnd.debug("FERollback ends", (Throwable)rollbackFailure);
                     }

                     throw new weblogic.jms.common.JMSException(((Throwable)rollbackFailure).getMessage(), (Throwable)rollbackFailure);
                  }

                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FERollback end");
                  }

               }
            }

            synchronized(this) {
               this.transactedException = null;
               this.transactionInUse = false;
               this.transactedSessionTx = null;
               this.jmsAsyncSendTranInProgress = false;
               this.notifyAll();
            }

            this.cleanAll();
            this.tranManager.forceSuspend();
            this.transactedUnackedStatistics(false);
            if (rollbackFailure != null) {
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FERollback ends", (Throwable)rollbackFailure);
               }

               throw new weblogic.jms.common.JMSException(((Throwable)rollbackFailure).getMessage(), (Throwable)rollbackFailure);
            }

            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FERollback end");
            }

            return;
         }

         synchronized(this) {
            this.transactedException = null;
            this.transactionInUse = false;
            this.transactedSessionTx = null;
            this.jmsAsyncSendTranInProgress = false;
            this.notifyAll();
         }

         this.cleanAll();
         this.tranManager.forceSuspend();
         this.transactedUnackedStatistics(false);
         if (rollbackFailure != null) {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FERollback ends", (Throwable)rollbackFailure);
            }

            throw new weblogic.jms.common.JMSException(((Throwable)rollbackFailure).getMessage(), (Throwable)rollbackFailure);
         }

         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FERollback end");
         }

         return;
      }

      synchronized(this) {
         this.transactedException = null;
         this.transactionInUse = false;
         this.transactedSessionTx = null;
         this.jmsAsyncSendTranInProgress = false;
         this.notifyAll();
      }

      this.cleanAll();
      this.tranManager.forceSuspend();
      this.transactedUnackedStatistics(false);
      if (rollbackFailure != null) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FERollback ends", (Throwable)rollbackFailure);
         }

         throw new weblogic.jms.common.JMSException(((Throwable)rollbackFailure).getMessage(), (Throwable)rollbackFailure);
      } else {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FERollback end");
         }

      }
   }

   private Response checkChildExceptions(FESessionAcknowledgeRequest request) throws JMSException {
      Response result = null;

      for(weblogic.messaging.dispatcher.Request node = request.getChild(); node != null; node = node.getNext()) {
         try {
            result = (VoidResponse)node.getResult();
         } catch (JMSException var5) {
            throw var5;
         } catch (Throwable var6) {
            return FESessionAcknowledgeRequest.handleThrowable(var6);
         }
      }

      if (result == null) {
         result = new VoidResponse();
      }

      return result;
   }

   private void commitAfterAcknowledge(boolean forceRollback, FESessionAcknowledgeRequest request, Throwable commitFailure) throws JMSException {
      boolean var38 = false;

      try {
         var38 = true;
         if (commitFailure == null) {
            try {
               this.checkChildExceptions(request);
            } catch (Throwable var44) {
               commitFailure = var44;
            }
         }

         if (commitFailure == null) {
            if (this.transactedSessionTx != null) {
               if (!forceRollback) {
                  try {
                     if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                        JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck is called");
                     }

                     this.tranManager.commit();
                     this.transactedUnackedStatistics(true);
                     var38 = false;
                  } catch (Throwable var43) {
                     commitFailure = var43;
                     forceRollback = false;
                     this.transactedUnackedStatistics(false);
                     var38 = false;
                  }
               } else {
                  var38 = false;
               }
            } else {
               var38 = false;
            }
         } else {
            var38 = false;
         }
      } finally {
         if (var38) {
            synchronized(this) {
               this.transactionInUse = false;
               this.transactedException = null;
               this.transactedSessionTx = null;
               this.jmsAsyncSendTranInProgress = false;
               this.notifyAll();
            }

            this.cleanAll();

            try {
               if (forceRollback) {
                  this.transactedUnackedStatistics(false);
                  if (TxHelper.getTransaction() != null) {
                     try {
                        if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                           JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck forceRollback");
                        }

                        this.tranManager.rollback();
                     } catch (Throwable var39) {
                        request.clearResult();
                        throw JMSUtilities.jmsExceptionThrowable("commit failed, then follback exception", var39);
                     }
                  }
               }
            } finally {
               this.tranManager.forceSuspend();
            }

            if (commitFailure != null) {
               if (commitFailure instanceof TransactionRolledBackException && ((JMSException)commitFailure).getErrorCode() != null && ((JMSException)commitFailure).getErrorCode().equals("ReservedRollbackOnly")) {
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck throw error code", commitFailure);
                  }

                  request.clearResult();
                  throw JMSUtilities.jmsExceptionThrowable(commitFailure.getMessage(), commitFailure);
               }

               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck throws", commitFailure);
               }

               request.clearResult();
               throw this.transactedException("commit failure", commitFailure);
            }

            request.setResult(new VoidResponse());
         }
      }

      synchronized(this) {
         this.transactionInUse = false;
         this.transactedException = null;
         this.transactedSessionTx = null;
         this.jmsAsyncSendTranInProgress = false;
         this.notifyAll();
      }

      this.cleanAll();

      try {
         if (forceRollback) {
            this.transactedUnackedStatistics(false);
            if (TxHelper.getTransaction() != null) {
               try {
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck forceRollback");
                  }

                  this.tranManager.rollback();
               } catch (Throwable var41) {
                  request.clearResult();
                  throw JMSUtilities.jmsExceptionThrowable("commit failed, then follback exception", var41);
               }
            }
         }
      } finally {
         this.tranManager.forceSuspend();
      }

      if (commitFailure != null) {
         if (commitFailure instanceof TransactionRolledBackException && ((JMSException)commitFailure).getErrorCode() != null && ((JMSException)commitFailure).getErrorCode().equals("ReservedRollbackOnly")) {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck throw error code", commitFailure);
            }

            request.clearResult();
            throw JMSUtilities.jmsExceptionThrowable(commitFailure.getMessage(), commitFailure);
         } else {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("FESession.commitAfterAck throws", commitFailure);
            }

            request.clearResult();
            throw this.transactedException("commit failure", commitFailure);
         }
      } else {
         request.setResult(new VoidResponse());
      }
   }

   void transactionStat(FEConsumer consumer, FEProducer producer, MessageImpl message) {
      if (!this.transacted) {
         if (consumer != null) {
            consumer.statistics.incrementReceivedCount(message);
            this.statistics.incrementReceivedCount(message);
         } else {
            producer.incMessagesSentCount(message.getPayloadSize() + (long)message.getUserPropertySize());
            this.statistics.incrementSentCount(message);
         }

      } else {
         this.statistics.incrementPendingCount(message);
         if (consumer != null) {
            consumer.statistics.incrementPendingCount(message);
         } else {
            producer.incMessagesPendingCount(message.getPayloadSize() + (long)message.getUserPropertySize());
         }

         UnackedMessage unackedMessage = new UnackedMessage(consumer, producer, message);
         synchronized(this) {
            unackedMessage.setPrev(this.lastTranStatUnackedMessage);
            if (this.firstTranStatUnackedMessage == null) {
               this.firstTranStatUnackedMessage = unackedMessage;
            } else {
               this.lastTranStatUnackedMessage.setNext(unackedMessage);
            }

            this.lastTranStatUnackedMessage = unackedMessage;
         }
      }
   }

   public String getAcknowledgeMode() {
      if (this.originalAcknowledgeMode == 4) {
         return "None";
      } else if (this.originalAcknowledgeMode == 128) {
         return "Multicast None";
      } else if (this.originalAcknowledgeMode == 2) {
         return "Client";
      } else {
         return this.originalAcknowledgeMode == 1 ? "Auto" : "Dups-Ok";
      }
   }

   MessageStatistics getStatistics() {
      return this.statistics;
   }

   public long getBytesPendingCount() {
      return this.statistics.getBytesPendingCount();
   }

   public long getBytesReceivedCount() {
      return this.statistics.getBytesReceivedCount();
   }

   public long getBytesSentCount() {
      return this.statistics.getBytesSentCount();
   }

   public long getMessagesPendingCount() {
      return this.statistics.getMessagesPendingCount();
   }

   public long getMessagesReceivedCount() {
      return this.statistics.getMessagesReceivedCount();
   }

   public long getMessagesSentCount() {
      return this.statistics.getMessagesSentCount();
   }

   public boolean isTransacted() {
      return this.transacted;
   }

   private int setRedeliveryDelay(FESessionSetRedeliveryDelayRequest request) throws JMSException {
      switch (request.getState()) {
         case 0:
            this.checkShutdownOrSuspended();
            Exception savedException = null;
            Iterator iterator;
            synchronized(this) {
               iterator = ((HashMap)this.beDispatchers.clone()).values().iterator();
            }

            weblogic.messaging.dispatcher.Request last = null;
            weblogic.messaging.dispatcher.Request first = null;

            while(iterator.hasNext()) {
               BESessionSetRedeliveryDelayRequest child = new BESessionSetRedeliveryDelayRequest(this.sessionId, request.getRedeliveryDelay());
               if (first == null) {
                  last = child;
                  first = child;
               } else {
                  last.setNext(child);
                  last = child;
               }

               try {
                  request.dispatchAsync((JMSDispatcher)iterator.next(), child);
               } catch (DispatcherException var10) {
                  savedException = var10;
               }
            }

            if (savedException != null) {
               throw new weblogic.jms.common.JMSException("Error setting redelivery delay", savedException);
            } else {
               synchronized(request) {
                  request.setState(2);
                  request.rememberChild(first);
                  return 2;
               }
            }
         default:
            Response result = null;

            for(weblogic.messaging.dispatcher.Request first = request.getChild(); first != null; first = first.getNext()) {
               result = (VoidResponse)((BESessionSetRedeliveryDelayRequest)first).getResult();
            }

            if (result == null) {
               result = new VoidResponse();
            }

            request.setResult(result);
            return Integer.MAX_VALUE;
      }
   }

   void addUnackedPushEntry(JMSPushEntry pushEntry, long messageSize) {
      pushEntry.setMessageSize(messageSize);
      pushEntry.setFrontEndSequenceNumber(this.getNextSequenceNumber());
      if (this.acknowledgeMode != 4) {
         pushEntry.setPrevUnacked(this.lastUnackedPushEntry);
         pushEntry.setNextUnacked((JMSPushEntry)null);
         if (this.firstUnackedPushEntry == null) {
            this.firstUnackedPushEntry = pushEntry;
         } else {
            this.lastUnackedPushEntry.setNextUnacked(pushEntry);
         }

         this.lastUnackedPushEntry = pushEntry;
      }
   }

   public void pushMessage(MessageImpl message, JMSPushEntry pushEntry) {
      long messageSize = message.getPayloadSize() + (long)message.getUserPropertySize();
      synchronized(this) {
         if (!pushEntry.getClientResponsibleForAcknowledge()) {
            if (this.acknowledgeMode != 4) {
               this.statistics.incrementPendingCount(messageSize);
            }

            this.addUnackedPushEntry(pushEntry, messageSize);
         } else {
            this.statistics.incrementReceivedCount(messageSize);
         }

      }
   }

   private MessageImpl receiveInterceptionPoint(DestinationImpl destination, MessageImpl message) throws JMSException {
      if (JMSServiceServerLifeCycleImpl.interceptionEnabled) {
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
                  message = message.copy();
                  JMSMessageContextImpl messageContext = new JMSMessageContextImpl(message);
                  this.receiveIPHandle.process(messageContext);
                  MessageImpl var10000 = message;
                  return var10000;
               } else {
                  return null;
               }
            } catch (Exception var9) {
               throw new weblogic.jms.common.JMSException("FAILED in interception " + var9);
            }
         }
      } else {
         return null;
      }
   }

   public void pushMessage(JMSPushRequest pushRequest) {
      JMSPushRequest firstPushRequest = pushRequest;
      JMSPushRequest prevPushRequest = null;
      synchronized(this) {
         for(; pushRequest != null; pushRequest = (JMSPushRequest)pushRequest.getNext()) {
            MessageImpl message = pushRequest.getMessage();
            if (this.connection.getCompressionThreshold() < pushRequest.getCompressionThreshold()) {
               pushRequest.setCompressionThreshold(this.connection.getCompressionThreshold());
            }

            long messageSize = message.getPayloadSize() + (long)message.getUserPropertySize();
            JMSPushEntry prevPushEntry = null;
            JMSPushEntry pushEntry = pushRequest.getFirstPushEntry();

            while(pushEntry != null) {
               try {
                  FEConsumer consumer = this.consumerFind(pushEntry.getConsumerId());
                  if (pushEntry.getClientResponsibleForAcknowledge()) {
                     this.statistics.incrementReceivedCount(messageSize);
                     consumer.statistics.incrementReceivedCount(messageSize);
                     pushEntry.setFrontEndSequenceNumber(this.getNextSequenceNumber());
                  } else {
                     if (this.acknowledgeMode != 4) {
                        this.statistics.incrementPendingCount(messageSize);
                        consumer.statistics.incrementPendingCount(messageSize);
                     }

                     this.addUnackedPushEntry(pushEntry, messageSize);
                  }

                  MessageImpl newMessage = this.receiveInterceptionPoint(consumer.getDestination(), message);
                  if (newMessage != null) {
                     if (prevPushEntry == null && pushEntry.getNext() == null) {
                        pushRequest.setMessage(newMessage);
                     } else {
                        JMSPushRequest newRequest = new JMSPushRequest();
                        newRequest.setNext(firstPushRequest);
                        firstPushRequest = newRequest;
                        newRequest.setMessage(newMessage);
                        newRequest.setFirstPushEntry(pushEntry);
                        if (prevPushEntry == null) {
                           pushRequest.setFirstPushEntry(pushEntry.getNext());
                        } else {
                           prevPushEntry.setNext(pushEntry.getNext());
                        }

                        JMSPushEntry dummy = new JMSPushEntry();
                        dummy.setNext(pushEntry.getNext());
                        pushEntry.setNext((JMSPushEntry)null);
                        pushEntry = dummy;
                     }
                  } else {
                     prevPushEntry = pushEntry;
                  }
               } catch (JMSException var29) {
                  if (prevPushEntry == null) {
                     pushRequest.setFirstPushEntry(pushEntry.getNext());
                  } else {
                     prevPushEntry.setNext(pushEntry.getNext());
                  }
               } finally {
                  pushEntry = pushEntry.getNext();
               }
            }

            boolean deletedPushRequest = false;
            if (pushRequest.getFirstPushEntry() == null) {
               deletedPushRequest = true;
               if (prevPushRequest == null) {
                  firstPushRequest = (JMSPushRequest)pushRequest.getNext();
               } else {
                  prevPushRequest.setNext(pushRequest.getNext());
               }
            }

            MessageTimeStamp.record(5, pushRequest.getMessage());
            if (!deletedPushRequest) {
               prevPushRequest = pushRequest;
            }
         }
      }

      if (firstPushRequest != null) {
         try {
            JMSDispatcher dispatcher = this.connection.getClientDispatcher();
            if (dispatcher.isLocal()) {
               pushRequest = new JMSPushRequest(firstPushRequest);
            } else {
               pushRequest = firstPushRequest;
            }

            pushRequest.setInvocableType(4);
            if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
               JMSDebug.JMSMessagePath.debug("FRONTEND/FESession (id: " + this.sessionId + ") : Pushing to the client, message " + pushRequest.getMessage().getJMSMessageID());
            }

            AuthenticatedSubject subject = (AuthenticatedSubject)CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(dispatcher, this.getSubjectForQOS(), false);
            if (JMSDebug.JMSCrossDomainSecurity.isDebugEnabled()) {
               JMSDebug.JMSCrossDomainSecurity.debug("Push messages:subject to use = " + subject);
            }

            if (this.disableMultiSend && !dispatcher.isLocal()) {
               WorkManagerFactory.getInstance().getSystem().schedule(new PushDispatchThread(dispatcher, pushRequest, subject));
            } else {
               SecurityServiceManager.pushSubject(KERNEL_ID, subject);

               try {
                  dispatcher.dispatchNoReply(pushRequest);
               } finally {
                  SecurityServiceManager.popSubject(KERNEL_ID);
               }
            }
         } catch (Exception var28) {
            if (this.connection.isClosed()) {
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FESession.pushMessage(): " + var28.toString() + ", connection closed", var28);
               }
            } else {
               JMSLogger.logErrorPushingMessage(var28.toString(), var28);
            }
         }
      }

   }

   public JMSID getJMSID() {
      return this.sessionId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.connection.getDispatcherPartition4rmic();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(weblogic.messaging.dispatcher.Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.service, "FESession");
      switch (request.getMethodId()) {
         case 520:
            try {
               return this.browserCreate((FEBrowserCreateRequest)request);
            } catch (JMSException var4) {
               this.checkStaleDestination(var4, request);
               return this.browserCreate((FEBrowserCreateRequest)request);
            }
         case 2824:
            try {
               return this.consumerCreate((FEConsumerCreateRequest)request);
            } catch (JMSException var3) {
               this.checkStaleDestination(var3, request);
               return this.consumerCreate((FEConsumerCreateRequest)request);
            }
         case 4872:
            return this.producerCreate((FEProducerCreateRequest)request);
         case 6152:
            return this.acknowledge((FESessionAcknowledgeRequest)request);
         case 6408:
            this.checkShutdownOrSuspended();
            this.close(((FESessionCloseRequest)request).getLastSequenceNumber());
            break;
         case 6920:
            return this.recover((FESessionRecoverRequest)request);
         case 7176:
            this.setRedeliveryDelay((FESessionSetRedeliveryDelayRequest)request);
            break;
         default:
            throw new weblogic.jms.common.JMSException("No such method " + request.getMethodId());
      }

      request.setResult(new VoidResponse());
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   void checkStaleDestination(JMSException jmsEx, weblogic.messaging.dispatcher.Request req) throws JMSException {
      if (!isStaleDestEx(jmsEx)) {
         throw jmsEx;
      } else {
         DestinationImpl staleDest;
         if (req instanceof FEBrowserCreateRequest) {
            FEBrowserCreateRequest crRq = (FEBrowserCreateRequest)req;
            if (crRq.getNumberOfRetries() > 0) {
               throw jmsEx;
            }

            staleDest = crRq.getDestination();
            if (staleDest instanceof DistributedDestinationImpl) {
               throw jmsEx;
            }

            crRq.setDestination(this.createFromStaleDestination(staleDest));
            crRq.setNumberOfRetries(crRq.getNumberOfRetries() + 1);
            crRq.getChild().clearResult();
         } else {
            if (!(req instanceof FEConsumerCreateRequest)) {
               throw jmsEx;
            }

            FEConsumerCreateRequest crRq = (FEConsumerCreateRequest)req;
            if (crRq.getNumberOfRetries() > 0) {
               throw jmsEx;
            }

            staleDest = crRq.getDestination();
            if (staleDest instanceof DistributedDestinationImpl) {
               throw jmsEx;
            }

            crRq.setDestination(this.createFromStaleDestination(staleDest));
            crRq.setNumberOfRetries(crRq.getNumberOfRetries() + 1);
         }

         req.clearResult();
         req.setState(0);
      }
   }

   private DestinationImpl createFromStaleDestination(DestinationImpl staleDest) throws JMSException {
      DestinationImpl newDest = this.getConnection().createDestination(staleDest);
      if (newDest.getDispatcherId() == null) {
         newDest.setDispatcherId(staleDest.getDispatcherId());
      }

      return newDest;
   }

   static boolean isStaleDestEx(JMSException jmsEx) {
      Throwable t = null;

      for(t = jmsEx; t != null; t = ((Throwable)t).getCause()) {
         if (t instanceof InvalidDestinationException) {
            return true;
         }

         if (t instanceof NameNotFoundException) {
            return true;
         }
      }

      return false;
   }

   private synchronized JMSDispatcher beDispatcherRemove(DispatcherId dispatcherId) {
      JMSDispatcher dispatcher = (JMSDispatcher)this.beDispatchers.remove(dispatcherId);
      Sequencer sequencer = (Sequencer)this.sequencers.remove(dispatcherId);
      if (sequencer != null) {
         this.service.getInvocableManagerDelegate().invocableRemove(13, sequencer.getJMSID());
      }

      return dispatcher;
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

      this.beDispatcherRemove(dispatcher.getId());
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      HashMap tempProducers = (HashMap)this.producers.clone();
      HashMap tempConsumers = (HashMap)this.consumers.clone();
      xsw.writeStartElement("Session");
      xsw.writeAttribute("id", this.sessionId != null ? this.sessionId.toString() : "");
      xsw.writeAttribute("isTransacted", String.valueOf(this.transacted));
      xsw.writeAttribute("consumersCurrentCount", String.valueOf(tempConsumers.size()));
      xsw.writeAttribute("consumersHighCount", String.valueOf(this.consumersHighCount));
      xsw.writeAttribute("consumersTotalCount", String.valueOf(this.consumersTotalCount));
      xsw.writeAttribute("producersCurrentCount", String.valueOf(tempProducers.size()));
      xsw.writeAttribute("producersHighCount", String.valueOf(this.producersHighCount));
      xsw.writeAttribute("producersTotalCount", String.valueOf(this.producersTotalCount));
      xsw.writeAttribute("browsersCurrentCount", String.valueOf(this.browsers.size()));
      xsw.writeAttribute("browsersHighCount", String.valueOf(this.browsersHighCount));
      this.statistics.dump(imageSource, xsw);
      xsw.writeStartElement("Producers");
      Iterator it = tempProducers.values().iterator();

      while(it.hasNext()) {
         FEProducer fep = (FEProducer)it.next();
         fep.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeStartElement("Consumers");
      it = tempConsumers.values().iterator();

      while(it.hasNext()) {
         FEConsumer fec = (FEConsumer)it.next();
         fec.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   public void pushException(int invocableType, JMSID invocableId, JMSException exception) throws JMSException {
      JMSPushExceptionRequest pushRequest = new JMSPushExceptionRequest(invocableType, invocableId, (weblogic.jms.common.JMSException)exception);
      if (this.getSubjectForQOS() == null) {
         this.updateQOS();
      }

      AuthenticatedSubject subject = (AuthenticatedSubject)CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.getConnection().getClientDispatcher(), this.getSubjectForQOS(), false);
      SecurityServiceManager.pushSubject(KERNEL_ID, subject);

      try {
         this.getConnection().getClientDispatcher().dispatchNoReply(pushRequest);
      } catch (Exception var10) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("Failure pushing exception " + var10);
         }

         throw new JMSException("Failure pushing exception " + var10);
      } finally {
         SecurityServiceManager.popSubject(KERNEL_ID);
      }

   }

   final void checkPartition(DestinationImpl destination) throws JMSException {
      if (destination != null) {
         String connPartiton = this.getConnection().getFrontEnd().getService().getPartitionName();
         String destPartition = destination.getPartitionName();
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FESession.checkPartition: connPartiton=" + connPartiton + ", destPartition=" + destPartition);
         }

         if (!PartitionUtils.isSamePartition(connPartiton, destPartition)) {
            throw new JMSException("The given destination is not in the same partition as the JMS connection/session.");
         }
      }
   }

   private class PushDispatchThread implements Runnable {
      JMSPushRequest pushRequest;
      JMSDispatcher dispatcher;
      AuthenticatedSubject subject;

      private PushDispatchThread(JMSDispatcher pushDispatcher, JMSPushRequest pushReq, AuthenticatedSubject pushSubject) {
         this.pushRequest = pushReq;
         this.dispatcher = pushDispatcher;
         this.subject = pushSubject;
      }

      public void run() {
         SecurityServiceManager.pushSubject(FESession.KERNEL_ID, this.subject);

         try {
            this.dispatcher.dispatchNoReply(this.pushRequest);
         } catch (JMSException var5) {
            JMSLogger.logErrorPushingMessage(var5.toString(), var5);
         } finally {
            SecurityServiceManager.popSubject(FESession.KERNEL_ID);
         }

      }

      // $FF: synthetic method
      PushDispatchThread(JMSDispatcher x1, JMSPushRequest x2, AuthenticatedSubject x3, Object x4) {
         this(x1, x2, x3);
      }
   }
}
