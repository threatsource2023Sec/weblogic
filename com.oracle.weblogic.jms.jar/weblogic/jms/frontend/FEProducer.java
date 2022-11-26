package weblogic.jms.frontend;

import java.io.Serializable;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.common.CompletionRequest;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.JMSServiceServerLifeCycleImpl;
import weblogic.jms.backend.BEProducerSendRequest;
import weblogic.jms.backend.BEUOOMember;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DestinationPeerGoneAdapter;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSFailover;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageContextImpl;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.UOOHelper;
import weblogic.jms.dd.DDConstants;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.JMSOrderException;
import weblogic.jms.utils.tracing.MessageTimeStamp;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSProducerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherImpl;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.interception.MessageInterceptionService;
import weblogic.messaging.interception.exceptions.InterceptionException;
import weblogic.messaging.interception.exceptions.InterceptionProcessorException;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.exceptions.MessageContextException;
import weblogic.messaging.interception.interfaces.CarrierCallBack;
import weblogic.messaging.interception.interfaces.InterceptionPointHandle;
import weblogic.messaging.path.Member;
import weblogic.messaging.path.helper.KeyString;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.collections.SecondChanceCacheMap;

public final class FEProducer extends RuntimeMBeanDelegate implements JMSProducerRuntimeMBean, Invocable, CarrierCallBack, DDConstants {
   static final long serialVersionUID = -2064739049461407314L;
   private volatile boolean inSend = false;
   private final Object closeProducerLock = new Object();
   private boolean closed = false;
   private boolean inJMSAsyncSend = false;
   private final JMSID producerId;
   private final Object statisticsLock = new Object();
   private long messagesSentCount = 0L;
   private long messagesPendingCount = 0L;
   private long bytesSentCount = 0L;
   private long bytesPendingCount = 0L;
   private final FESession session;
   private DestinationImpl producerDestination;
   private JMSDispatcher producerDispatcher;
   private final InvocableMonitor invocableMonitor;
   private static final int STALE_DEST_MAP_MAXSIZE = 10;
   private final SecondChanceCacheMap staleDestsLRUMap;
   private boolean pinned;
   private HashMap pinnedDests;
   private HashMap pinnedPersistentDests;
   private static final int REQUEST_COMPLETED = 1;
   private static final int REQUEST_IN_PROGRESS = 2;
   private InterceptionPointHandle[] IPHandles = new InterceptionPointHandle[2];
   private DestinationImpl[] IPDestinations = new DestinationImpl[2];
   private static final String[] IPStrings = new String[]{"Start", "After Authorization"};
   private static final int[] IPNextStates = new int[]{6, 7};
   private static final int START_IP = 0;
   private static final int POST_AUTH_IP = 1;
   private boolean interceptionSaidContinue = false;
   private static final int DONE = 1;
   private static final int IN_PROGRESS = 2;
   private FEProducerSendRequest currentRequest;
   private InterceptionProcessorException interceptionException = null;
   private static Object cacheUOOLock = new Object();
   private static Object interceptionPointLock = new Object();
   private CacheUOOMember cacheUOOMember;
   private static int TTL_GUESS = 60000;
   private static int TTL_CONFIRMED = 3600000;
   private HashMap stickyDests;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static boolean uooForClusteredJMSServerEnabled = checkUooForClusteredJMSServerEnabled();
   static int nextGeneration = 1;

   private static final boolean checkUooForClusteredJMSServerEnabled() {
      boolean uooForClusteredJMSServerEnabledProperty = false;
      String str = System.getProperty("weblogic.jms.UooForClusteredJMSServerEnabled");
      if (str != null && str.equals("true")) {
         uooForClusteredJMSServerEnabledProperty = true;
      }

      return uooForClusteredJMSServerEnabledProperty;
   }

   public FEProducer(String mbeanName, JMSID producerId, FESession session, DestinationImpl destination) throws JMSException, ManagementException {
      super(mbeanName, session);
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FEProducer.<init>()");
      }

      this.producerId = producerId;
      this.session = session;
      this.invocableMonitor = session.getConnection().getFrontEnd().getInvocableMonitor();
      this.interpretProducerDestination(destination);
      if (this.pinned) {
         this.pinnedDests = new HashMap();
         this.pinnedPersistentDests = new HashMap();
      }

      this.staleDestsLRUMap = new SecondChanceCacheMap(this.producerDestination == null ? 10 : 1);
      if (this.producerDestination != null) {
         if (!(destination instanceof DistributedDestinationImpl)) {
            try {
               this.producerDispatcher = session.getConnection().getFrontEnd().getService().dispatcherFindOrCreate(this.producerDestination.getDispatcherId());
            } catch (DispatcherException var6) {
               if (DDManager.findDDHandlerByMemberName(this.producerDestination.getName()) != null) {
                  throw new weblogic.jms.common.JMSException("Error creating producer for destination " + this.producerDestination.getName(), var6);
               }
            }

            if (this.producerDispatcher != null) {
               this.addAndNormalizeDestination(this.producerDestination, this.producerDispatcher);
            }
         }

      }
   }

   private void interpretProducerDestination(DestinationImpl destination) throws JMSException {
      if (destination == null) {
         this.pinned = !this.session.getConnection().isLoadBalancingEnabled();
      } else {
         this.pinned = false;
         FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(destination.getName());
         if (feDDHandler != null) {
            if (!destination.isQueue() && !feDDHandler.isDDPartitionedDistributedTopic() || !this.session.getConnection().isLoadBalancingEnabled()) {
               destination = feDDHandler.producerLoadBalance(false, this.session, destination.getName());
               this.pinned = true;
            }
         } else {
            this.pinned = true;
         }

         if (!(destination instanceof DistributedDestinationImpl)) {
            boolean dispatcherFound = false;
            FEConnection connection = this.session.getConnection();
            JMSService jmsService = connection.getFrontEnd().getService();

            try {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Checking if the dispatcher " + destination.getDispatcherId() + " can be found using the connection " + connection.toString());
               }

               dispatcherFound = jmsService.dispatcherFindOrCreate(destination.getDispatcherId()) != null;
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Dispatcher " + destination.getDispatcherId() + (dispatcherFound ? "" : " not") + " found using the connection " + connection.toString());
               }
            } catch (DispatcherException var8) {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Could not find dispatcher for " + destination.getDispatcherId() + " using the connection " + connection.toString() + "; forcing (re)creation of the destination");
               }

               destination.setDestinationId((JMSID)null);
            }

            if (destination.getDestinationId() == null || !dispatcherFound) {
               try {
                  destination = connection.createDestination(destination);
               } catch (Throwable var7) {
                  throw JMSUtilities.jmsExceptionThrowable("Destination " + destination.getName() + " not found", var7);
               }
            }
         }

         this.producerDestination = destination;
      }
   }

   private void addAndNormalizeDestination(DestinationImpl destination, JMSDispatcher dispatcher) throws JMSException {
      if (destination == null) {
         throw new AssertionError("Destination should never be null here");
      } else {
         DestinationPeerGoneAdapter destPeerGoneAdapter = new DestinationPeerGoneAdapter(destination, this.session.getConnection());
         if (destPeerGoneAdapter.getId() == null) {
            if (destination.getReferenceName() != null) {
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FRONTEND/FEProducer: Destination not found '" + destination.getName() + "', with SAF reply reference name '" + destination.getReferenceName() + "'");
               }

               throw new InvalidDestinationException("Destination " + destination.getName() + " not found for SAF reply");
            } else {
               throw new InvalidDestinationException("Destination not found '" + destination.getName() + "'");
            }
         } else {
            synchronized(dispatcher) {
               DestinationPeerGoneAdapter ldestPeerGoneAdapter = (DestinationPeerGoneAdapter)dispatcher.addDispatcherPeerGoneListener(destPeerGoneAdapter);
               if (ldestPeerGoneAdapter != null && !ldestPeerGoneAdapter.equals(destPeerGoneAdapter)) {
                  DestinationImpl ldestination = ldestPeerGoneAdapter.getDestinationImpl();
                  if (ldestination != null) {
                     this.producerDestination = ldestination;
                  }

               }
            }
         }
      }
   }

   void incMessagesSentCount(long len) {
      synchronized(this.statisticsLock) {
         ++this.messagesSentCount;
         this.bytesSentCount += len;
      }
   }

   void incMessagesPendingCount(long len) {
      synchronized(this.statisticsLock) {
         ++this.messagesPendingCount;
         this.bytesPendingCount += len;
      }
   }

   void decMessagesPendingCount(long len) {
      synchronized(this.statisticsLock) {
         --this.messagesPendingCount;
         this.bytesPendingCount -= len;
      }
   }

   public long getBytesPendingCount() {
      synchronized(this.statisticsLock) {
         return this.bytesPendingCount;
      }
   }

   public long getBytesSentCount() {
      synchronized(this.statisticsLock) {
         return this.bytesSentCount;
      }
   }

   public long getMessagesPendingCount() {
      synchronized(this.statisticsLock) {
         return this.messagesPendingCount;
      }
   }

   public long getMessagesSentCount() {
      synchronized(this.statisticsLock) {
         return this.messagesSentCount;
      }
   }

   public void addPinnedDest(DestinationImpl dest) {
      if (this.pinnedDests != null && this.pinnedPersistentDests != null && dest != null) {
         if (((DistributedDestinationImpl)dest).isPersistent()) {
            this.pinnedDests.put(dest.getName(), dest);
            this.pinnedPersistentDests.put(dest.getName(), dest);
         } else {
            this.pinnedDests.put(dest.getName(), dest);
         }

      }
   }

   public DestinationImpl getPinnedDest(DestinationImpl dest, boolean isPersistent) {
      if (this.pinnedDests != null && this.pinnedPersistentDests != null && dest != null) {
         DestinationImpl ret;
         if (isPersistent) {
            ret = (DestinationImpl)this.pinnedPersistentDests.get(dest.getName());
         } else {
            ret = (DestinationImpl)this.pinnedDests.get(dest.getName());
         }

         if (ret != null && ret.isStale()) {
            this.cleanFailure(ret);
            ret = null;
         }

         return ret;
      } else {
         return null;
      }
   }

   private void cleanFailure(DestinationImpl failed) {
      if (this.pinnedDests != null && this.pinnedPersistentDests != null) {
         this.pinnedDests.remove(failed.getName());
         this.pinnedPersistentDests.remove(failed.getName());
         if (this.session != null && this.session.isTransacted()) {
            this.session.cleanFailure(failed);
         }

      }
   }

   private void responseCheck(Object response) {
   }

   private boolean sameNamedDestination(DestinationImpl dest1, DestinationImpl dest2) {
      return dest1 != null && dest2 != null && dest1.getServerName().equals(dest2.getServerName()) && dest1.getName().equals(dest2.getName());
   }

   private int interceptionPoint(int location, FEProducerSendRequest request) throws JMSException {
      if (!JMSServiceServerLifeCycleImpl.interceptionEnabled) {
         return 1;
      } else {
         synchronized(interceptionPointLock) {
            this.interceptionException = null;
            InterceptionPointHandle localHandle = this.IPHandles[location];
            DestinationImpl localDestination = this.IPDestinations[location];
            if (localHandle != null && !this.sameNamedDestination(localDestination, request.getDestination())) {
               try {
                  MessageInterceptionService.getSingleton().unRegisterInterceptionPoint(localHandle);
               } catch (InterceptionServiceException var10) {
                  throw new AssertionError("Failure to unregister" + var10);
               }

               localHandle = null;
               this.IPHandles[location] = null;
               this.IPDestinations[location] = null;
            }

            if (localHandle == null) {
               String[] ip = new String[3];
               this.IPDestinations[location] = request.getDestination();
               ip[0] = request.getDestination().getServerName();
               ip[1] = request.getDestination().getName();
               ip[2] = IPStrings[location];
               if (ip[0] == null) {
                  ip[0] = new String();
               }

               if (ip[1] == null) {
                  ip[1] = new String();
               }

               try {
                  localHandle = MessageInterceptionService.getSingleton().registerInterceptionPoint("JMS", ip);
               } catch (InterceptionServiceException var9) {
                  throw new weblogic.jms.common.JMSException("FAILED registerInterceptionPoint " + var9);
               }

               this.IPHandles[location] = localHandle;
            }

            try {
               if (localHandle.hasAssociation()) {
                  JMSMessageContextImpl messageContext = new JMSMessageContextImpl(request.getMessage());
                  messageContext.setDestination(request.getDestination());
                  messageContext.setUser(JMSSecurityHelper.getSimpleAuthenticatedName());
                  messageContext.setFailover((JMSFailover)request.getFailover());
                  request.setState(IPNextStates[location]);
                  request.needOutsideResult();
                  this.currentRequest = request;
                  localHandle.processAsync(messageContext, this);
                  byte var10000 = 2;
                  return var10000;
               }
            } catch (InterceptionException var11) {
               throw new weblogic.jms.common.JMSException("Processor: " + var11);
            } catch (MessageContextException var12) {
               throw new weblogic.jms.common.JMSException("Processor: " + var12);
            } catch (InterceptionServiceException var13) {
               throw new weblogic.jms.common.JMSException("Processor: " + var13);
            }

            return 1;
         }
      }
   }

   private void initializeRoutingCriteria(FEProducerSendRequest request) throws JMSException {
      assert request.getDestination() != null;

      String UOO = request.getMessage().getUnitOfOrder();
      if (request.getDestination() instanceof DistributedDestinationImpl || UOO != null) {
         String UOW = null;

         try {
            UOW = request.getMessage().getStringProperty("JMS_BEA_UnitOfWork");
         } catch (JMSException var5) {
            throw new AssertionError("We don't have exceptions on getProperty in the server");
         }

         if (UOO != null && UOW != null) {
            throw new weblogic.jms.common.JMSException("A JMS message cannot have both a Unit Of Order property and a Unit Of Work Property");
         } else if (request.getDestination() instanceof DistributedDestinationImpl) {
            if (UOO != null) {
               request.setUnitForRouting(UOO);
            } else {
               DDHandler ddHandler = DDManager.findDDHandlerByDDName(request.getDestination().getName());
               if (ddHandler != null && ddHandler.isUOWDestination()) {
                  request.setUnitForRouting(UOW);
               } else {
                  request.setUnitForRouting((String)null);
               }
            }

         }
      }
   }

   private void pickFirstDestination(FEProducerSendRequest request) throws JMSException {
      if (request.getDestination() == null) {
         throw new weblogic.jms.common.JMSException("Null destination");
      } else {
         this.initializeRoutingCriteria(request);
         DestinationImpl inputDestination = request.getDestination();
         if (this.pinned) {
            request.setDestination(this.getPinnedDest(request.getDestination(), request.getMessage().getAdjustedDeliveryMode() == 2));
         } else {
            request.setDestination((DestinationImpl)null);
         }

         if (request.getDestination() == null || request.getUnitForRouting() != null) {
            request.setDestination(inputDestination);
            if (request.getUnitForRouting() != null) {
               this.selectUOOMember(request, this.session);
            } else {
               request.setDestination(this.computeTypicalLoadBalance((FEDDHandler)null, request, this.session));
            }
         }

         if (this.pinned && request.getDestination() instanceof DistributedDestinationImpl) {
            this.addPinnedDest(request.getDestination());
         }

         if (request.getDestination() == null) {
            throw new weblogic.jms.common.JMSException("no failover destination");
         }
      }
   }

   private static boolean updateInitCause(String msg, Throwable throwableCopy) {
      if (throwableCopy.getCause() == null) {
         try {
            throwableCopy.initCause(new JMSException(msg));
            return false;
         } catch (Throwable var3) {
         }
      }

      return true;
   }

   private static JMSException linkedException(JMSException jmse, String msg) {
      try {
         jmse.setLinkedException(new weblogic.jms.common.JMSException(msg));
      } catch (IllegalStateException var3) {
      }

      return jmse;
   }

   private JMSException cannotFailoverException(String msg, Throwable throwableCopy) throws JMSException {
      if (throwableCopy instanceof JMSException) {
         JMSException local = (JMSException)throwableCopy;
         if (updateInitCause(msg, local) && local.getLinkedException() == null) {
            linkedException(local, msg);
         }

         throw local;
      } else {
         updateInitCause(msg, throwableCopy);
         throw JMSUtilities.throwJMSOrRuntimeException(throwableCopy);
      }
   }

   private JMSOrderException cannotFailoverOrderException(String msg, Throwable throwableCopy) throws JMSException {
      if (throwableCopy instanceof JMSException) {
         JMSException local = (JMSException)throwableCopy;
         if (updateInitCause(msg, local) && local.getLinkedException() == null) {
            linkedException(local, msg);
         }

         throw local;
      } else {
         updateInitCause(msg, throwableCopy);
         throw new JMSOrderException(msg, throwableCopy.getCause());
      }
   }

   private void determineFailOver(FEProducerSendRequest request, Throwable throwableCopy) throws JMSException {
      FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(request.getDestination().getName());
      if (feDDHandler == null) {
         throw this.cannotFailoverException("failover is null", throwableCopy);
      } else {
         JMSFailover failover = feDDHandler.getProducerFailover((DistributedDestinationImpl)request.getDestination(), throwableCopy, request.getMessage().getAdjustedDeliveryMode() == 2, this.session);
         if (failover == null) {
            throw this.cannotFailoverException("failover is null", throwableCopy);
         } else {
            request.setFailover(failover);
         }
      }
   }

   private void stickyMaybePickNext(FEProducerSendRequest request, Throwable throwableCopy) {
      if (!JMSFailover.isRecoverableFailure(throwableCopy)) {
         this.ratifyDestinationMember(request);
         request.setDestination((DestinationImpl)null);
      } else {
         if (this.stickyDests != null && this.stickyDests.get(request.getDestination().getName()) != null) {
            if (request.getNumberOfRetries() == 0) {
               DistributedDestinationImpl ddImpl = (DistributedDestinationImpl)request.getDestination();
               request.setDestination(DDManager.findDDImplByMemberName(ddImpl.getMemberName()));
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("stickyMaybePickNext: update destination " + request.getDestination() + " by membername " + ddImpl.getMemberName() + " dispatcherId " + request.getDestination().getDispatcherId());
               }

               if (request.getDestination() != null) {
                  this.ratifyDestinationMember(request);
               }
            } else {
               request.setDestination((DestinationImpl)null);
            }
         } else {
            request.setDestination(((JMSFailover)request.getFailover()).failover((DistributedDestinationImpl)request.getDestination(), throwableCopy));
         }

      }
   }

   private void pickNextDestination(FEProducerSendRequest request, Throwable throwableCopy) throws JMSException {
      if (request.getUOONoFailover()) {
         throw this.cannotFailoverOrderException("DD UOO failover not allowed for member " + request.getDestination().getMemberName() + " which is not available", throwableCopy);
      } else {
         if (request.getFailover() == null) {
            this.determineFailOver(request, throwableCopy);
         }

         FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(request.getDestination().getName());
         if (feDDHandler.getLoadBalancingPolicy() == 2) {
            this.stickyMaybePickNext(request, throwableCopy);
         } else {
            request.setDestination(((JMSFailover)request.getFailover()).failover((DistributedDestinationImpl)request.getDestination(), throwableCopy));
         }

         if (request.getDestination() == null) {
            if (throwableCopy instanceof JMSException) {
               throw this.cannotFailoverException("No destination to failover. ", throwableCopy);
            } else {
               throw new weblogic.jms.common.JMSException("No failover destination. ", throwableCopy);
            }
         }
      }
   }

   private void checkAndProcessStaleness(FEProducerSendRequest request) throws weblogic.jms.common.JMSException {
      DestinationImpl currDest = request.getDestination();
      boolean isStale = currDest.isStale();
      DestinationImpl newerDest = (DestinationImpl)this.staleDestsLRUMap.get(currDest);
      if (newerDest != null) {
         request.setDispatcher((JMSDispatcher)null);
         request.setDestination(newerDest);
      } else if (isStale) {
         request.setDispatcher((JMSDispatcher)null);
         request.setUpPushPopSubject(true);

         try {
            if (request.getPushPopSubject()) {
               SecurityServiceManager.pushSubject(KERNEL_ID, (AuthenticatedSubject)request.getAuthenticatedSubject());
            }

            newerDest = this.session.getConnection().createDestination(currDest);
            this.staleDestsLRUMap.put(currDest, newerDest);
            request.setDestination(newerDest);
         } catch (JMSException var9) {
            throw new weblogic.jms.common.JMSException("JMSException while creating destination. ", var9);
         } finally {
            if (request.getPushPopSubject()) {
               SecurityServiceManager.popSubject(KERNEL_ID);
            }

         }

      }
   }

   private void updateProducerDestination(FEProducerSendRequest request) throws JMSException {
      if (this.producerDestination != null) {
         DestinationImpl oldDestination = this.producerDestination;
         this.addAndNormalizeDestination(request.getDestination(), request.getDispatcher());
         if (this.producerDispatcher != null) {
            this.producerDispatcher.removeDispatcherPeerGoneListener(new DestinationPeerGoneAdapter(oldDestination, (FEConnection)null));
            this.producerDispatcher = request.getDispatcher();
         }

      }
   }

   private void findDispatcher(FEProducerSendRequest request) throws JMSException {
      if (request.getDispatcher() == null && request.getDestination() != null) {
         try {
            DestinationImpl dest = request.getDestination();
            DispatcherId dispatcherId = request.getDestination().getDispatcherId();
            if (dest instanceof DistributedDestinationImpl && dispatcherId == null && dest.getBackEndId() != null && dest.getBackEndId().getDispatcherId() != null) {
               dispatcherId = dest.getBackEndId().getDispatcherId();
            }

            SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);
            request.setDispatcher(this.session.getConnection().getFrontEnd().getService().dispatcherFindOrCreate(dispatcherId));
         } catch (DispatcherException var7) {
            throw new weblogic.jms.common.JMSException("Error producing message for destination " + request.getDestination().getName(), var7);
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

         this.updateProducerDestination(request);
      }

   }

   private void setupTransactionRelated(FEProducerSendRequest request) throws JMSException {
      if (this.session.isTransacted() && !request.isInfected()) {
         if (request.getMessage().propertyExists("JMS_BEA_SAF_SEQUENCE_NAME")) {
            this.session.transactedInfect(true, request.isJMSAsyncSend() ? this.getJMSID() : null, request.getSendTimeout());
         } else {
            this.session.transactedInfect(false, request.isJMSAsyncSend() ? this.getJMSID() : null, request.getSendTimeout());
         }

         request.setInfected(true);
      }

   }

   private int doDispatch(FEProducerSendRequest request, FEProducerSendRequest parentRequest) throws JMSException {
      request.getMessage().setClientId(this.session.getConnection().getConnectionClientId());
      BEProducerSendRequest backendRequest = new BEProducerSendRequest(request.getDestination().getId(), request.getMessage(), request.getDestination().isQueue() ? null : this.session.getConnection().getJMSID(), !request.getDestination().isQueue() && request.useSessionId() ? this.session.getJMSID() : null, request.getSendTimeout(), this.producerId, parentRequest.isJMSAsyncSend());
      request.setBackendRequest(backendRequest);
      if (request.getDestination() instanceof DistributedDestinationImpl) {
         backendRequest.setCheckUOO(request.getCheckUOO());
      }

      if (JMSDebug.JMSMessagePath.isDebugEnabled() || JMSDebug.JMSDispatcher.isDebugEnabled() || JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         messageOrFrontEndDebug("FRONTEND/FEProducer: Dispatching message to BACKEND/BEDestination " + request.getMessage().getId() + " request " + request + "[parentRequest.jmsAsyncSend=" + parentRequest.isJMSAsyncSend() + "] dispatcher " + request.getDispatcher().getId() + " dest " + request.getDestination());
      }

      try {
         if (parentRequest.isJMSAsyncSend()) {
            parentRequest.dispatchAsyncWithId(request.getDispatcher(), backendRequest, this.session.getJMSID().getCounter());
         } else {
            parentRequest.dispatchAsync(request.getDispatcher(), backendRequest);
         }
      } catch (DispatcherException var13) {
         throw new weblogic.jms.common.JMSException("Error sending message", var13);
      }

      if (request.isNoResponse()) {
         return 1;
      } else {
         int debugState = -117;
         int returnState = -117;

         byte var7;
         try {
            synchronized(backendRequest) {
               if (backendRequest.getState() == Integer.MAX_VALUE) {
                  returnState = 1;
                  var7 = 1;
                  return var7;
               }

               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  debugState = backendRequest.getState();
               }

               if (backendRequest.getState() != -42 || !backendRequest.hasResults() && !request.hasResults()) {
                  returnState = 2;
                  var7 = 2;
                  return var7;
               }

               returnState = 1;
               var7 = 1;
            }
         } finally {
            if (debugState != -117) {
               JMSDebug.JMSFrontEnd.debug("feproducer doDispatch state=" + debugState + ", returnState=" + returnState + ", hasResults=" + backendRequest.hasResults() + ", " + backendRequest);
            }

         }

         return var7;
      }
   }

   private static void messageOrFrontEndDebug(String debug) {
      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug(debug);
      } else {
         JMSDebug.JMSFrontEnd.debug(debug);
      }

   }

   private void updateStatistics(FEProducerSendRequest request) {
      MessageImpl message = request.getMessage();
      if (!request.isInfected() && !this.session.isTransacted()) {
         this.incMessagesSentCount(message.getPayloadSize() + (long)message.getUserPropertySize());
         this.session.getStatistics().incrementSentCount(message);
      } else {
         this.session.transactionStat((FEConsumer)null, this, message);
      }

   }

   private void cleanupTransactionRelated(FEProducerSendRequest request) throws JMSException {
      if (request.isInfected()) {
         this.session.transactedDisinfect();
         request.setInfected(false);
      }

   }

   private void sendRetryDestination(FEProducerSendRequest request) throws JMSException {
      Throwable throwableCopy = null;

      while(request.getState() != Integer.MAX_VALUE) {
         switch (request.getState()) {
            case 3:
               request.setUpPushPopSubject(true);
               this.setupFailover(request);
               this.throwIfUOOFanoutMessage(request, throwableCopy);
               this.pickNextDestination(request, throwableCopy);
               request.setNumberOfRetries(request.getNumberOfRetries() + 1);
               break;
            case 4:
               this.pickFirstDestination(request);
               AuthenticatedSubject authenticatedSubject = JMSSecurityHelper.getCurrentSubject();
               request.setAuthenticatedSubject(authenticatedSubject);
            case 2:
               if (request.getDestination() != null && this.interceptionPoint(1, request) == 2) {
                  return;
               }
            case 5:
               Throwable cause;
               try {
                  this.findDispatcher(request);
               } catch (JMSException var15) {
                  cause = var15.getCause();
                  if (cause instanceof DispatcherException) {
                     throwableCopy = cause;
                     request.setState(3);
                     break;
                  }

                  throw var15;
               }

               this.setupTransactionRelated(request);
               request.setState(1);

               try {
                  if (request.getPushPopSubject()) {
                     SecurityServiceManager.pushSubject(KERNEL_ID, (AuthenticatedSubject)request.getAuthenticatedSubject());
                  }

                  if (this.doDispatch(request, request) == 2) {
                     return;
                  }
               } catch (JMSOrderException var20) {
                  this.setupFailover(request);
                  this.throwIfUOOFanoutMessage(request, var20);
                  this.processUOOCache(request, (Object)null, var20);
                  break;
               } catch (JMSException var21) {
                  cause = var21.getCause();
                  if (cause instanceof DispatcherException) {
                     throwableCopy = cause;
                     request.setState(3);
                     break;
                  }

                  throw var21;
               } finally {
                  if (request.getPushPopSubject()) {
                     SecurityServiceManager.popSubject(KERNEL_ID);
                  }

               }
            case 1:
               Response response;
               try {
                  this.setupTransactionRelated(request);
                  if (request.getBackendRequest().hasResults()) {
                     response = request.getBackendRequest().getResult();
                  } else {
                     request.getBackendRequest().waitForNotRunningResult();
                     response = request.getBackendRequest().getResult();
                  }

                  request.setResult(response);
               } catch (Error var16) {
                  throw var16;
               } catch (RuntimeException var17) {
                  throw var17;
               } catch (JMSOrderException var18) {
                  this.setupFailover(request);
                  this.throwIfUOOFanoutMessage(request, var18);
                  this.processUOOCache(request, (Object)null, var18);
                  continue;
               } catch (Throwable var19) {
                  JMSOrderException jmsoe = this.findOrderExceptionCause(var19);
                  if (jmsoe != null) {
                     this.setupFailover(request);
                     this.throwIfUOOFanoutMessage(request, jmsoe);
                     this.processUOOCache(request, (Object)null, jmsoe);
                     continue;
                  }

                  if (!JMSFailover.isRecoverableFailure(var19)) {
                     request.setState(Integer.MAX_VALUE);
                     request.resumeRequest(var19, request.getBackendRequest().isCollocated());
                     return;
                  }

                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("isRecoverableFailure so RETRY " + var19);
                  }

                  throwableCopy = var19;
                  request.setState(3);
                  continue;
               }

               this.responseCheck(response);
               this.processUOOCache(request, response, (JMSOrderException)null);
               this.ratifyDestinationMember(request);
               request.setState(Integer.MAX_VALUE);
               return;
         }
      }

   }

   private void throwIfUOOFanoutMessage(FEProducerSendRequest request, Throwable throwable) throws JMSException {
      if (request.getMessage().getControlOpcode() == 196608) {
         throw this.cannotFailoverException("control DD cannot failover", throwable);
      }
   }

   private void ratifyDestinationMember(FEProducerSendRequest request) {
      FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(request.getDestination().getName());
      if (feDDHandler != null) {
         if (feDDHandler.getLoadBalancingPolicy() == 2) {
            if (this.stickyDests == null) {
               this.stickyDests = new HashMap();
            }

            this.stickyDests.put(request.getDestination().getName(), request.getDestination());
         }
      }
   }

   private void setupFailover(FEProducerSendRequest request) {
      this.cleanFailure(request.getDestination());
      request.setDispatcher((JMSDispatcher)null);
      request.clearState();
      request.setState(2);
   }

   private void validateMessageOnSingleDestination(FEProducerSendRequest request) throws JMSException {
      this.initializeRoutingCriteria(request);
   }

   private void sendSingleDestination(FEProducerSendRequest request) throws JMSException {
      while(request.getState() != Integer.MAX_VALUE) {
         switch (request.getState()) {
            case 4:
               AuthenticatedSubject authenticatedSubject = JMSSecurityHelper.getCurrentSubject();
               request.setAuthenticatedSubject(authenticatedSubject);
               this.validateMessageOnSingleDestination(request);
               if (this.producerDestination == null) {
                  this.pickFirstDestination(request);
                  if (request.getDestination() instanceof DistributedDestinationImpl) {
                     request.setState(2);
                     this.sendRetryDestination(request);
                     return;
                  }
               } else {
                  request.setDispatcher(this.producerDispatcher);
               }
            case 2:
               this.checkAndProcessStaleness(request);
               if (this.interceptionPoint(1, request) == 2) {
                  return;
               }
            case 5:
               try {
                  this.findDispatcher(request);
               } catch (JMSException var8) {
                  this.handleStaleDest(request, var8);
                  request.clearResult();
                  request.setState(2);
                  break;
               }

               this.setupTransactionRelated(request);
               request.setState(1);

               try {
                  if (request.getPushPopSubject()) {
                     SecurityServiceManager.pushSubject(KERNEL_ID, (AuthenticatedSubject)request.getAuthenticatedSubject());
                  }

                  if (this.doDispatch(request, request) == 2) {
                     return;
                  }
               } finally {
                  if (request.getPushPopSubject()) {
                     SecurityServiceManager.popSubject(KERNEL_ID);
                  }

               }
            case 1:
               try {
                  Response response = null;
                  this.setupTransactionRelated(request);
                  if (request.isNoResponse()) {
                     request.setState(Integer.MAX_VALUE);
                  } else {
                     response = request.getBackendRequest().getResult();
                     this.responseCheck(response);
                     request.setResult(response);
                     request.setState(Integer.MAX_VALUE);
                  }

                  return;
               } catch (JMSException var9) {
                  this.handleStaleDest(request, var9);
               }
            case 3:
               request.clearResult();
               request.setState(2);
         }
      }

   }

   private void handleStaleDest(FEProducerSendRequest req, JMSException jmsEx) throws JMSException {
      if (FESession.isStaleDestEx(jmsEx) && req.getNumberOfRetries() == 0) {
         req.getDestination().markStale();
         req.setNumberOfRetries(req.getNumberOfRetries() + 1);
         req.setState(3);
      } else {
         throw jmsEx;
      }
   }

   private void checkInterceptionReturn(FEProducerSendRequest request, int nextState) throws JMSException {
      if (this.interceptionException != null) {
         throw new JMSException("Interception exception" + this.interceptionException);
      } else {
         if (!this.interceptionSaidContinue) {
            this.session.getConnection().getFrontEnd().getService();
            JMSMessageId messageId = JMSService.getNextMessageId();
            request.setResult(new JMSProducerSendResponse(messageId));
            request.setState(Integer.MAX_VALUE);
         } else {
            request.setState(nextState);
         }

      }
   }

   public void onCallBack(boolean continueOn) {
      this.interceptionSaidContinue = continueOn;
      this.currentRequest.resumeExecution(true);
      this.currentRequest = null;
   }

   public void onException(InterceptionProcessorException exception) {
      this.interceptionException = exception;
      this.currentRequest.resumeExecution(true);
      this.currentRequest = null;
   }

   private void waitForJMSAsyncSendComplete(boolean checkForSAF) throws JMSException {
      long timeout = 2L * (long)this.session.getTransactionTimeout(checkForSAF) * 1000L;
      if (timeout > 2147483647L) {
         timeout = 2147483647L;
      }

      long waitime = timeout;
      synchronized(this.closeProducerLock) {
         while(this.inJMSAsyncSend && !this.closed) {
            long startime = System.currentTimeMillis();

            try {
               this.closeProducerLock.wait(waitime);
            } catch (InterruptedException var11) {
            }

            if (this.inJMSAsyncSend && !this.closed) {
               waitime -= System.currentTimeMillis() - startime;
               if (waitime <= 0L) {
                  throw new JMSException("Timed out(" + timeout + " ms) in waiting for previous JMS Async Send to complete in producer[" + this.getJMSID() + "]");
               }
            }
         }

         if (this.closed) {
            throw new JMSException("Producer[" + this.getJMSID() + "] is closed");
         } else {
            this.inJMSAsyncSend = true;
         }
      }
   }

   private void jmsAsyncSendComplete() {
      synchronized(this.closeProducerLock) {
         this.inJMSAsyncSend = false;
         this.closeProducerLock.notifyAll();
      }
   }

   private int send(FEProducerSendRequest request) throws JMSException {
      this.session.checkShutdownOrSuspended();
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("FEProducer.send() : state = " + request.getState());
      }

      boolean loop;
      do {
         boolean notAnException = false;
         loop = false;

         try {
            switch (request.getState()) {
               case 0:
                  MessageTimeStamp.record(1, request.getMessage());
                  if (this.inSend) {
                  }

                  this.inSend = true;
                  if (request.isJMSAsyncSend()) {
                     this.waitForJMSAsyncSendComplete(request.getMessage().propertyExists("JMS_BEA_SAF_SEQUENCE_NAME"));
                  }

                  MessageImpl message = request.getMessage();
                  boolean newID = false;
                  if (message.getId() == null) {
                     this.session.getConnection().getFrontEnd().getService();
                     message.setId(JMSService.getNextMessageId());
                     newID = true;
                     if (JMSDebug.JMSMessagePath.isDebugEnabled() || JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                        messageOrFrontEndDebug("FRONTEND/FEProducer: Assigning message ID " + message.getId());
                     }
                  }

                  if (message.getJMSExpiration() != 0L) {
                     message.setJMSExpiration((newID ? message.getJMSTimestamp() : System.currentTimeMillis()) + message.getJMSExpiration());
                  }

                  if (message.getDeliveryTime() >= 0L) {
                     message.setDeliveryTime((newID ? message.getJMSTimestamp() : System.currentTimeMillis()) + message.getDeliveryTime());
                  }

                  if (message.isForwardable()) {
                     message.incForwardsCount();
                     message.requestJMSXUserID(false);
                  } else {
                     if (this.session.getConnection().getAttachJMSXUserID()) {
                        message.requestJMSXUserID(true);
                     } else {
                        message.requestJMSXUserID(false);
                     }

                     message.setJMSXUserID((String)null);
                  }

                  if (this.producerDestination != null) {
                     request.setDestination(this.producerDestination);
                  }

                  this.session.getConnection().getFrontEnd().getSAFReplyHandler().process(message);
                  if (this.interceptionPoint(0, request) == 2) {
                     int var6 = request.getState();
                     return var6;
                  }

                  request.setState(4);
               case 1:
               case 2:
               case 3:
               case 4:
               case 5:
               default:
                  DestinationImpl destination = request.getDestination();
                  DestinationImpl destinationForInterop = this.getDestinationForInterop(destination);
                  if (destinationForInterop != null) {
                     destination = destinationForInterop;
                     request.setDestination(destinationForInterop);
                  }

                  if (destination instanceof DistributedDestinationImpl) {
                     this.sendRetryDestination(request);
                  } else {
                     this.sendSingleDestination(request);
                  }

                  notAnException = true;
                  break;
               case 6:
                  this.checkInterceptionReturn(request, 4);
                  loop = true;
                  break;
               case 7:
                  this.checkInterceptionReturn(request, 5);
                  loop = true;
                  break;
               case 8:
                  int var4 = this.releaseFanoutComplete(request);
                  return var4;
            }
         } finally {
            if (!notAnException) {
               this.inSend = false;
               if (request.isJMSAsyncSend()) {
                  this.jmsAsyncSendComplete();
               }

               this.cleanupTransactionRelated(request);
            }

         }
      } while(loop);

      if (request.getState() == Integer.MAX_VALUE) {
         this.inSend = false;
         if (request.isJMSAsyncSend()) {
            this.jmsAsyncSendComplete();
         }

         this.updateStatistics(request);
      }

      this.cleanupTransactionRelated(request);
      return request.getState();
   }

   private DestinationImpl getDestinationForInterop(DestinationImpl destination) {
      if (destination instanceof DistributedDestinationImpl) {
         return null;
      } else {
         DestinationImpl retDestination = DDManager.findDDImplByDDName(destination.getName());
         return retDestination;
      }
   }

   public JMSID getJMSID() {
      return this.producerId;
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

   public int invoke(Request invocableRequest) throws JMSException {
      switch (invocableRequest.getMethodId()) {
         case 4617:
            this.session.checkShutdownOrSuspended();
            this.session.producerClose(this);
            this.removeDispatcher();
            invocableRequest.setResult(new VoidResponse());
            invocableRequest.setState(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
         case 5129:
            FEProducerSendRequest request = (FEProducerSendRequest)invocableRequest;
            this.session.checkPartition(request.getDestination());
            if (request.isJMSAsyncSend() && !DispatcherImpl.FASTDISPATCH) {
               throw new JMSException("JMS asynchronous send is not supported when JMSFastDispatchEnabled=false (front-end)");
            } else {
               if (request.getMessage().getControlOpcode() != 65536) {
                  return this.send(request);
               }

               return this.controlSequenceReleaseFanout(request);
            }
         default:
            throw new weblogic.jms.common.JMSException("No such method " + invocableRequest.getMethodId());
      }
   }

   private int controlSequenceReleaseFanout(FEProducerSendRequest masterRequest) throws JMSException {
      DestinationImpl destination;
      if (this.producerDestination != null) {
         destination = this.producerDestination;
      } else {
         destination = masterRequest.getDestination();
      }

      if (!(destination instanceof DistributedDestinationImpl)) {
         return this.send(masterRequest);
      } else {
         FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(masterRequest.getDestination().getName());
         if (feDDHandler == null) {
            return this.send(masterRequest);
         } else {
            DDHandler ddHandler = feDDHandler.getDDHandler();
            int size = ddHandler.getNumberOfMembers();
            if (size == 0) {
               return this.send(masterRequest);
            } else {
               JMSException delayedThowable = null;
               FEProducerSendRequest[] subRequest = new FEProducerSendRequest[size];

               int i;
               try {
                  SecurityServiceManager.pushSubject(KERNEL_ID, KERNEL_ID);

                  for(i = 0; i < size; ++i) {
                     DestinationImpl memberDest = ddHandler.getMemberByIndex(i).getDDImpl();
                     subRequest[i] = new FEProducerSendRequest(this.producerId, masterRequest.getMessage().cloneit(), memberDest, masterRequest.getSendTimeout(), masterRequest.getCompressionThreshold());

                     try {
                        subRequest[i].setDispatcher(this.session.getConnection().getFrontEnd().getService().dispatcherFindOrCreate(memberDest.getDispatcherId()));
                     } catch (DispatcherException var17) {
                        delayedThowable = new weblogic.jms.common.JMSException(var17);
                     }
                  }
               } finally {
                  SecurityServiceManager.popSubject(KERNEL_ID);
               }

               masterRequest.needOutsideResult();

               for(i = 0; i < size; ++i) {
                  try {
                     if (subRequest[i].getDispatcher() != null) {
                        this.doDispatch(subRequest[i], masterRequest);
                     } else {
                        assert delayedThowable != null;
                     }
                  } catch (JMSException var19) {
                     if (delayedThowable == null) {
                        delayedThowable = var19;
                     }
                  }
               }

               if (delayedThowable != null) {
                  throw delayedThowable;
               } else {
                  masterRequest.setSubRequest(subRequest);
                  synchronized(masterRequest) {
                     if (!masterRequest.fanoutCompleteSuspendIfHaveChildren(false)) {
                        masterRequest.setState(8);
                        return 8;
                     }
                  }

                  return this.releaseFanoutComplete(masterRequest);
               }
            }
         }
      }
   }

   private int releaseFanoutComplete(FEProducerSendRequest masterRequest) throws JMSException {
      synchronized(masterRequest) {
         masterRequest.setState(Integer.MAX_VALUE);
         if (this.hasProducerResult(masterRequest)) {
            return Integer.MAX_VALUE;
         }
      }

      FEProducerSendRequest[] subRequests = masterRequest.getSubRequest();
      JMSProducerSendResponse lastResponse = null;

      for(int i = 0; i < subRequests.length; ++i) {
         synchronized(subRequests[i]) {
            if (subRequests[i].hasResults()) {
               lastResponse = (JMSProducerSendResponse)subRequests[i].getResult();
               break;
            }
         }
      }

      assert lastResponse != null;

      synchronized(masterRequest) {
         if (this.hasProducerResult(masterRequest)) {
            return Integer.MAX_VALUE;
         } else {
            masterRequest.setResult(lastResponse);
            return Integer.MAX_VALUE;
         }
      }
   }

   private boolean hasProducerResult(FEProducerSendRequest masterRequest) throws JMSException {
      Object masterResponse = null;
      if (masterRequest.hasResults()) {
         masterResponse = masterRequest.getResult();
         if (masterResponse instanceof JMSProducerSendResponse) {
            return true;
         }
      }

      assert masterResponse == null;

      return false;
   }

   void closeProducer() {
      synchronized(this.closeProducerLock) {
         this.closed = true;
         this.closeProducerLock.notifyAll();
      }

      synchronized(cacheUOOLock) {
         this.cacheUOOMember = null;
      }

      if (JMSServiceServerLifeCycleImpl.interceptionEnabled) {
         synchronized(interceptionPointLock) {
            InterceptionPointHandle startIPHandle = this.IPHandles[0];
            InterceptionPointHandle postAuthIPHandle = this.IPHandles[1];
            DestinationImpl startIPDestination = this.IPDestinations[0];
            DestinationImpl postAuthIPDestination = this.IPDestinations[1];
            if (startIPDestination != null && (startIPDestination.getType() == 8 || startIPDestination.getType() == 4)) {
               try {
                  if (startIPHandle != null && !startIPHandle.hasAssociation()) {
                     MessageInterceptionService.getSingleton().unRegisterInterceptionPoint(startIPHandle);
                     this.IPHandles[0] = null;
                     this.IPDestinations[0] = null;
                  }
               } catch (InterceptionServiceException var11) {
                  JMSLogger.logFailedToUnregisterInterceptionPoint(var11);
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FEProducer.close(), Failure to unregister startIPHandle " + var11);
                  }
               }
            }

            if (postAuthIPDestination != null && (postAuthIPDestination.getType() == 8 || postAuthIPDestination.getType() == 4)) {
               try {
                  if (postAuthIPHandle != null && !postAuthIPHandle.hasAssociation()) {
                     MessageInterceptionService.getSingleton().unRegisterInterceptionPoint(postAuthIPHandle);
                     this.IPHandles[1] = null;
                     this.IPDestinations[1] = null;
                  }
               } catch (InterceptionServiceException var10) {
                  JMSLogger.logFailedToUnregisterInterceptionPoint(var10);
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("FEProducer.close(), Failure to unregister postAuthIPHandle " + var10);
                  }
               }
            }
         }
      }

   }

   private void selectUOOMember(FEProducerSendRequest request, FESession session) throws JMSException {
      if (request.getDestination() instanceof DistributedDestinationImpl) {
         FEDDHandler feDDHandler = DDManager.findFEDDHandlerByDDName(request.getDestination().getName());
         DDHandler ddHandler;
         if (feDDHandler != null && (ddHandler = feDDHandler.getDDHandler()) != null) {
            String routing = feDDHandler.getUnitOfOrderRouting();
            boolean isHashUOORouting = "Hash".equals(routing);
            if (isHashUOORouting && !uooForClusteredJMSServerEnabled && ddHandler.hashingThrowsOrderException()) {
               throw new JMSOrderException(JMSExceptionLogger.logUOONotSupportedOnClusteredJMSServerLoggable(ddHandler.getName()).getMessage());
            } else {
               request.setFEDDHandler(feDDHandler);
               request.setUooNoFailover(true);
               if (isHashUOORouting) {
                  request.setDestination(UOOHelper.getHashBasedDestination(feDDHandler, request.getUnitForRouting()));
               } else {
                  if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                     PathHelper.PathSvcVerbose.debug("FEProducer DD:" + request.getDestination().getName());
                  }

                  assert "PathService".equals(routing);

                  request.setCheckUOO(2097152);
                  synchronized(cacheUOOLock) {
                     KeyString key;
                     if (this.cacheUOOMember != null && this.cacheUOOMember.getKey().getStringId().equals(request.getUnitForRouting()) && this.cacheUOOMember.getKey().getAssemblyId().equals(request.getDestination().getName())) {
                        key = this.cacheUOOMember.getKey();
                     } else {
                        this.cacheUOOMember = null;
                        key = new KeyString((byte)1, request.getDestination().getName().intern(), request.getUnitForRouting().intern());
                     }

                     if (this.cacheUOOMember != null && this.cacheUOOMember.isCancelled()) {
                        this.cacheUOOMember = null;
                     }

                     String pathServiceJndiName = request.getPathServiceJndiName();
                     if (pathServiceJndiName == null) {
                        request.setPathServiceJndiName(pathServiceJndiName = UOOHelper.getPathServiceJndiName(feDDHandler, key));
                     }

                     request.setUOOInfo(key);
                     PathHelper.ServerInfo serverInfo = (PathHelper.ServerInfo)request.getServerInfo();

                     BEUOOMember cache;
                     try {
                        if (request.getPushPopSubject()) {
                           SecurityServiceManager.pushSubject(KERNEL_ID, (AuthenticatedSubject)request.getAuthenticatedSubject());
                        }

                        if (serverInfo == null) {
                           serverInfo = session.getConnection().getFrontEnd().getService().findOrCreateServerInfo(pathServiceJndiName, key);
                           request.setServerInfo(serverInfo);
                        }

                        cache = (BEUOOMember)serverInfo.cachedGet(key, 576);
                     } catch (NamingException var34) {
                        if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                           PathHelper.PathSvcVerbose.debug("FEProd cache unavailable Key:" + key, var34);
                        }

                        throw new JMSOrderException(var34.getMessage(), var34);
                     } catch (Throwable var35) {
                        if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                           PathHelper.PathSvc.debug("FEProd get Key:" + key, var35);
                        }

                        throw JMSUtilities.throwJMSOrRuntimeException(PathHelper.wrapExtensionImpl(var35));
                     } finally {
                        if (request.getPushPopSubject()) {
                           SecurityServiceManager.popSubject(KERNEL_ID);
                        }

                     }

                     if (cache != this.cacheUOOMember) {
                        if (cache instanceof CacheUOOMember) {
                           if (this.cacheUOOMember != null && this.cacheUOOMember.isCancelled()) {
                              this.cacheUOOMember = null;
                           }

                           if (this.cacheUOOMember == null) {
                              this.cacheUOOMember = (CacheUOOMember)cache;
                           } else {
                              this.cacheUOOMember.merge(cache);
                           }
                        } else if (this.cacheUOOMember == null) {
                           this.cacheUOOMember = new CacheUOOMember(key, cache, System.currentTimeMillis() + (long)TTL_GUESS);
                        } else if (cache != null) {
                           this.cacheUOOMember.merge(cache);
                        }
                     }

                     if (this.cacheUOOMember != null) {
                        if (!UOOHelper.cacheUpToDate(feDDHandler, (String)this.cacheUOOMember.getMemberId(), this.cacheUOOMember.getLastHasConsumers(), request.getMessage())) {
                           this.cacheUOOMember = null;
                        } else {
                           this.cacheUOOMember.setLastHasConsumers(UOOHelper.hasConsumers(feDDHandler, (String)this.cacheUOOMember.getMemberId()));
                        }
                     }

                     DistributedDestinationImpl guessDestination;
                     if (this.cacheUOOMember == null) {
                        guessDestination = (DistributedDestinationImpl)this.computeTypicalLoadBalance(feDDHandler, request, session);
                        if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
                           PathHelper.PathSvcVerbose.debug("FEProd cache miss Key:" + key + ", balance:" + request.getDestination().getName());
                        }

                        this.cacheUOOMember = new CacheUOOMember(key, guessDestination.getMemberName(), guessDestination.getServerName(), guessDestination.getNonSystemSubscriberConsumers() != 0, (long)TTL_GUESS);
                     } else {
                        guessDestination = null;
                     }

                     if (PathHelper.retired && PathHelper.PathSvcVerbose.isDebugEnabled()) {
                        PathHelper.PathSvcVerbose.debug("FEProd cache hit Key:" + key + ", got:" + this.cacheUOOMember);
                     }

                     String ddMemberName = this.cacheUOOMember.getStringId();
                     request.setDestination(DDManager.findDDImplByMemberName(ddMemberName));
                     if (request.getDestination() == null) {
                        throw new JMSOrderException("unable to reach member " + ddMemberName + " of " + feDDHandler.getName() + " from keys " + ddHandler.debugKeys() + (guessDestination == null ? " cached " : " typicalLoadBalance ") + this.cacheUOOMember.getStringId());
                     } else if (cache instanceof CacheUOOMember) {
                        if (cache != this.cacheUOOMember) {
                           ((CacheUOOMember)cache).copyValues(this.cacheUOOMember, this.cacheUOOMember.getExpireTime());
                        }

                     } else {
                        try {
                           if (request.getPushPopSubject()) {
                              SecurityServiceManager.pushSubject(KERNEL_ID, (AuthenticatedSubject)request.getAuthenticatedSubject());
                           }

                           CompletionRequest completionRequest = new CompletionRequest();
                           serverInfo.cachedPutIfAbsent(this.cacheUOOMember.getKey(), this.cacheUOOMember, 512, completionRequest);
                           completionRequest.getResult();
                        } catch (NamingException var31) {
                           PathHelper.PathSvcVerbose.debug("FEProd cache unavailable Key:" + key, var31);
                           throw new JMSOrderException(var31.getMessage(), var31);
                        } catch (Throwable var32) {
                           PathHelper.PathSvc.debug("FEProd get Key:" + key, var32);
                           throw JMSUtilities.throwJMSOrRuntimeException(PathHelper.wrapExtensionImpl(var32));
                        } finally {
                           if (request.getPushPopSubject()) {
                              SecurityServiceManager.popSubject(KERNEL_ID);
                           }

                        }

                     }
                  }
               }
            }
         } else {
            throw new JMSOrderException("could not find distributed destination " + request.getDestination().getName());
         }
      }
   }

   private DestinationImpl computeTypicalLoadBalance(FEDDHandler feDDHandler, FEProducerSendRequest request, FESession session) throws JMSException {
      if (feDDHandler == null) {
         assert request.getDestination() != null;

         feDDHandler = DDManager.findFEDDHandlerByDDName(request.getDestination().getName());
      }

      if (feDDHandler == null) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("DD named: " + request.getDestination().getName() + " not found");
         }

         return request.getDestination();
      } else {
         if (feDDHandler.getLoadBalancingPolicy() == 2 && this.stickyDests != null) {
            DestinationImpl retDest = (DestinationImpl)this.stickyDests.get(request.getDestination().getName());
            if (retDest != null) {
               return retDest;
            }
         }

         boolean isPersistent = request.getMessage().getAdjustedDeliveryMode() == 2;

         try {
            DestinationImpl retDest = feDDHandler.producerLoadBalance(isPersistent, session, request.getDestination() != null ? request.getDestination().getName() : null);
            return retDest;
         } catch (JMSException var6) {
            if (JMSDebug.JMSMessagePath.isDebugEnabled() || JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("Load Balancer can't find a candidate for load balancing for DD: " + request.getDestination().getName());
            }

            throw new weblogic.jms.common.JMSException("Distributed Destination " + request.getDestination().getName() + " does not have any member destinations which are active");
         }
      }
   }

   private JMSOrderException findOrderExceptionCause(Throwable cause) {
      while(cause != null) {
         if (cause instanceof JMSOrderException) {
            return (JMSOrderException)cause;
         }

         cause = cause.getCause();
      }

      return null;
   }

   private void processUOOCache(FEProducerSendRequest request, Object response, JMSOrderException jmsOrderException) throws JMSOrderException {
      if (JMSDebug.JMSMessagePath.isDebugEnabled() || JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         messageOrFrontEndDebug("FEProducer DD UOO Failover recalculation");
      }

      BEUOOMember member;
      if (jmsOrderException != null) {
         JMSOrderException current = jmsOrderException;

         while(true) {
            member = (BEUOOMember)current.getMember();
            if (member != null) {
               current.setMember((Serializable)null);
               request.setDestination(DDManager.findDDImplByMemberName((String)member.getMemberId()));
               if (request.getDestination() == null) {
                  throw new JMSOrderException("unable to contact member " + member.getMemberId() + ", keys are " + DDManager.debugKeys(), jmsOrderException);
               }
               break;
            }

            current = this.findOrderExceptionCause(current.getCause());
            if (current == null) {
               throw jmsOrderException;
            }
         }
      } else {
         JMSProducerSendResponse resp = (JMSProducerSendResponse)response;
         member = (BEUOOMember)((BEUOOMember)resp.getUOOInfo());
         resp.setUOOInfo((Serializable)null);
      }

      request.setNumberOfRetries(request.getNumberOfRetries() + 1);
      if (request.getMessage().getControlOpcode() == 196608) {
         synchronized(cacheUOOLock) {
            if (this.cacheUOOMember != null) {
               this.cacheUOOMember.setCancelled(true);
               PathHelper.ServerInfo serverInfo = (PathHelper.ServerInfo)request.getServerInfo();

               try {
                  if (request.getPushPopSubject()) {
                     SecurityServiceManager.pushSubject(KERNEL_ID, (AuthenticatedSubject)request.getAuthenticatedSubject());
                  }

                  if (serverInfo == null) {
                     serverInfo = this.session.getConnection().getFrontEnd().getService().findOrCreateServerInfo(request.getPathServiceJndiName(), this.cacheUOOMember.getKey());
                     request.setServerInfo(serverInfo);
                  }

                  serverInfo.cachedRemove(this.cacheUOOMember.getKey(), this.cacheUOOMember, 512, new CompletionRequest());
               } catch (NamingException var19) {
                  throw new JMSOrderException(var19.getMessage(), var19);
               } finally {
                  this.cacheUOOMember = null;
                  if (request.getPushPopSubject()) {
                     SecurityServiceManager.popSubject(KERNEL_ID);
                  }

               }

            }
         }
      } else if (member == null) {
         if (request.getUOOKey() != null) {
            synchronized(cacheUOOLock) {
               if (this.cacheUOOMember != null) {
                  this.cacheUOOMember.setExpireTime(TTL_CONFIRMED);
               }
            }
         }

      } else {
         if (PathHelper.PathSvcVerbose.isDebugEnabled()) {
            PathHelper.PathSvcVerbose.debug("FE Caching update " + member + " for key " + request.getUOOKey());
         }

         synchronized(cacheUOOLock) {
            assert this.cacheUOOMember != null;

            this.cacheUOOMember.copyValues(member, System.currentTimeMillis() + (long)TTL_CONFIRMED);
         }
      }
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Producer");
      xsw.writeAttribute("id", this.producerId != null ? this.producerId.toString() : "");
      xsw.writeAttribute("messagesSentCount", String.valueOf(this.messagesSentCount));
      xsw.writeAttribute("messagesPendingCount", String.valueOf(this.messagesPendingCount));
      xsw.writeAttribute("bytesSentCount", String.valueOf(this.bytesSentCount));
      xsw.writeAttribute("bytesPendingCount", String.valueOf(this.bytesPendingCount));
      xsw.writeAttribute("isPinned", String.valueOf(this.pinned));
      if (this.cacheUOOMember != null) {
         xsw.writeAttribute("uooMemberKey", this.cacheUOOMember.getKey().toString());
      }

      if (this.producerDestination != null) {
         xsw.writeStartElement("Destination");
         JMSDiagnosticImageSource.dumpDestinationImpl(xsw, this.producerDestination);
         xsw.writeEndElement();
      }

      if (this.pinnedDests != null) {
         HashMap tempPinnedDests = (HashMap)this.pinnedDests.clone();
         xsw.writeStartElement("PinnedDestinations");
         xsw.writeAttribute("currentCount", String.valueOf(tempPinnedDests.size()));
         Iterator it = tempPinnedDests.values().iterator();

         while(it.hasNext()) {
            DestinationImpl dest = (DestinationImpl)it.next();
            xsw.writeStartElement("Destination");
            JMSDiagnosticImageSource.dumpDestinationImpl(xsw, dest);
            xsw.writeEndElement();
         }

         xsw.writeEndElement();
      }

      FEProducerSendRequest tempRequest = this.currentRequest;
      if (tempRequest != null) {
         xsw.writeStartElement("CurrentSendRequest");
         xsw.writeAttribute("jmsMessageID", tempRequest.getMessage().getJMSMessageID().toString());
         xsw.writeAttribute("sendTimeout", String.valueOf(tempRequest.getSendTimeout()));
         xsw.writeEndElement();
      }

      boolean tempInJMSAsyncSend = false;
      synchronized(this.closeProducerLock) {
         tempInJMSAsyncSend = this.inJMSAsyncSend;
      }

      xsw.writeStartElement("inJMSAsyncSend");
      xsw.writeAttribute("inJMSAsyncSend", String.valueOf(tempInJMSAsyncSend));
      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   public void removeDispatcher() {
      if (this.producerDispatcher != null) {
         this.producerDispatcher.removeDispatcherPeerGoneListener(new DestinationPeerGoneAdapter(this.producerDestination, (FEConnection)null));
      }

   }

   private class CacheUOOMember extends BEUOOMember implements ExtendedBEUOOMember {
      static final long serialVersionUID = 1771787601821860231L;
      private transient KeyString keyString;
      private transient boolean cancelled;
      private transient boolean lastHasConsumers;
      private transient long expireTime;

      CacheUOOMember(KeyString key, BEUOOMember member, long expireArg) {
         super(member.getStringId(), member.getWLServerName(), member.getDynamic());
         this.keyString = key;
         this.lastHasConsumers = false;
         this.copyValues(member, expireArg);
      }

      CacheUOOMember(KeyString key, String memberName, String server, boolean hasConsumers, long expireArg) {
         super(memberName, server, true);
         this.keyString = key;
         this.lastHasConsumers = hasConsumers;
         this.expireTime = expireArg;
      }

      public CacheUOOMember() {
      }

      private void copyValues(BEUOOMember other, long expireArg) {
         this.serverName = other.getWLServerName();
         this.generation = other.getGeneration();
         this.timestamp = other.getTimeStamp();
         this.id = other.getStringId();
         this.dynamic = other.getDynamic();
         this.expireTime = expireArg;
         if (other instanceof CacheUOOMember) {
            this.keyString = ((CacheUOOMember)other).keyString;
            this.lastHasConsumers = ((CacheUOOMember)other).lastHasConsumers;
         }

      }

      private void merge(BEUOOMember other) {
         long computeExpireTime;
         if (other instanceof CacheUOOMember) {
            if (this.expireTime > ((CacheUOOMember)other).expireTime) {
               return;
            }

            computeExpireTime = ((CacheUOOMember)other).expireTime;
         } else {
            computeExpireTime = System.currentTimeMillis() + (long)FEProducer.TTL_GUESS;
            this.lastHasConsumers = false;
         }

         this.copyValues(other, computeExpireTime);
      }

      private KeyString getKey() {
         return this.keyString;
      }

      private void setLastHasConsumers(boolean lastHasConsumers) {
         this.lastHasConsumers = lastHasConsumers;
      }

      private boolean getLastHasConsumers() {
         return this.lastHasConsumers;
      }

      private long setExpireTime(int ttl) {
         this.expireTime = System.currentTimeMillis() + (long)ttl;
         return this.expireTime;
      }

      private long getExpireTime() {
         return this.expireTime;
      }

      private boolean isCancelled() {
         return this.cancelled;
      }

      private void setCancelled(boolean arg) {
         this.cancelled = arg;
      }
   }

   public interface ExtendedBEUOOMember extends Member {
      boolean getDynamic();

      String getStringId();

      void setTimestamp(long var1);

      void setGeneration(int var1);
   }
}
