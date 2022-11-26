package weblogic.jms.backend;

import java.util.Iterator;
import java.util.Map;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import weblogic.jms.JMSService;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.JMSBrowserCreateResponse;
import weblogic.jms.common.JMSConnectionConsumerCreateResponse;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class BEManager implements Invocable {
   private final InvocableMonitor invocableMonitor;
   private final JMSService jmsService;

   public BEManager(InvocableMonitor invocableMonitor, JMSService jmsservice) {
      this.invocableMonitor = invocableMonitor;
      this.jmsService = jmsservice;
   }

   public BEConnection connectionFindOrCreate(JMSID connectionId, JMSDispatcher dispatcher, boolean stopped, long sequenceNumber, String address) {
      BEConnection connection;
      do {
         try {
            connection = (BEConnection)this.jmsService.getInvocableManagerDelegate().invocableFind(15, connectionId);
            if (connection.getDispatcher() != dispatcher && connection.getDispatcher() != null) {
               connection.setDispatcher(dispatcher);
            }

            if (sequenceNumber > connection.getStartStopSequenceNumber()) {
               if (stopped) {
                  connection.stop(sequenceNumber, false);
               } else {
                  connection.start(sequenceNumber);
               }
            }
         } catch (JMSException var11) {
            connection = new BEConnection(dispatcher, connectionId, stopped, address, this.jmsService);
            connection.setStartStopSequenceNumber(sequenceNumber);

            try {
               this.jmsService.getInvocableManagerDelegate().invocableAdd(15, connection);
            } catch (JMSException var10) {
               connection = null;
            }
         }
      } while(connection == null);

      return connection;
   }

   private int browserCreate(Request invocableRequest) throws JMSException {
      BEBrowserCreateRequest request = (BEBrowserCreateRequest)invocableRequest;
      BEDestinationImpl destination = (BEDestinationImpl)this.jmsService.getInvocableManagerDelegate().invocableFind(20, request.getDestinationId());
      destination.checkShutdownOrSuspendedNeedLock("create browser");
      BEBrowser browser = destination.createBrowser((BESession)null, request.getSelector());
      this.jmsService.getInvocableManagerDelegate().invocableAdd(18, browser);
      request.setResult(new JMSBrowserCreateResponse(browser.getJMSID()));
      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   private void sessionCreate(Request invocableRequest) throws JMSException {
      BESessionCreateRequest request = (BESessionCreateRequest)invocableRequest;
      JMSID sessionId = request.getSessionId();
      JMSDispatcher dispatcher = null;

      try {
         if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("BEManager.sessionCreate: dispatcherFindOrCreate: sessionId:").append(sessionId);
            sb.append(" jmsService.partitionName: ").append(this.jmsService.getPartitionName());
            sb.append(" request.FEDispatcherId: ").append(request.getFEDispatcherId());
            sb.append(" startStopSequenceNumber: ").append(request.getStartStopSequenceNumber());
            JMSDebug.JMSDispatcherVerbose.debug(sb.toString());
         }

         DispatcherWrapper feDispatcherWrapper = request.getFEDispatcherWrapper();
         if (feDispatcherWrapper != null) {
            try {
               dispatcher = this.jmsService.registerDispatcher(feDispatcherWrapper);
            } catch (DispatcherException var9) {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  StringBuilder sb = new StringBuilder();
                  sb.append("BEManager.sessionCreate: exception caught: registering DispatcherWrapper: sessionId: ").append(sessionId);
                  sb.append(" jmsService.partitionName: ").append(this.jmsService.getPartitionName());
                  sb.append(" request.FEDispatcherId: ").append(request.getFEDispatcherId());
                  sb.append(" feDispatchWrapper.id: ").append(feDispatcherWrapper.getId());
                  sb.append(" feDispatchWrapper.partition: ").append(feDispatcherWrapper.getPartitionName());
                  JMSDebug.JMSDispatcherVerbose.debug(sb.toString());
               }
            }
         }

         if (dispatcher == null) {
            dispatcher = this.jmsService.dispatcherFindOrCreate(request.getFEDispatcherId());
         }
      } catch (DispatcherException var10) {
         if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("BEManager.sessionCreate: exception caught: : sessionId:").append(sessionId);
            sb.append(" jmsService.partitionName: ").append(this.jmsService.getPartitionName());
            sb.append(" request.FEDispatcherId: ").append(request.getFEDispatcherId());
            sb.append(" startStopSequenceNumber: ").append(request.getStartStopSequenceNumber());
            JMSDebug.JMSDispatcherVerbose.debug(sb.toString());
         }

         throw new weblogic.jms.common.JMSException("BEManager.sessionCreate: exception caught: : sessionId:" + sessionId + ": " + var10.getMessage(), var10);
      }

      BEConnection connection = this.connectionFindOrCreate(request.getConnectionId(), dispatcher, request.getIsStopped(), request.getStartStopSequenceNumber(), request.getConnectionAddress());
      connection.checkShutdownOrSuspendedNeedLock("create session");

      try {
         BESession var10000 = (BESession)this.jmsService.getInvocableManagerDelegate().invocableFind(16, sessionId);
      } catch (JMSException var8) {
         BESession session = new BESessionImpl(connection, this.jmsService, sessionId, request.getSequencerId(), request.getTransacted(), request.getXASession(), request.getAcknowledgeMode(), request.getClientVersion(), request.getPushWorkManager());
         connection.sessionAdd(session);
      }

   }

   private int connectionConsumerCreate(BEConnectionConsumerCreateRequest request) throws JMSException {
      BackEnd backEnd = this.jmsService.getBEDeployer().findBackEnd(request.getBackEndId());
      backEnd.checkShutdownNeedLock("create connection consumer");
      BEDestinationImpl dest = (BEDestinationImpl)this.jmsService.getInvocableManagerDelegate().invocableFind(20, request.getDestinationId());
      JMSService var10000 = this.jmsService;
      JMSID consumerId = JMSService.getNextId();
      boolean started = true;
      if (request.getConnection() != null && request.getConnection().isStopped()) {
         started = false;
      }

      BEConnectionConsumerImpl connectionConsumer = dest.createConnectionConsumer(consumerId, request.getServerSessionPool(), (String)null, (String)null, request.getMessageSelector(), false, request.getMessagesMaximum(), -1L, request.isDurable(), started);
      BEConnection connection = this.connectionFindOrCreate(request.getConnectionId(), this.jmsService.localDispatcherFind(), request.isStopped(), request.getStartStopSequenceNumber(), (String)null);
      if (request.isDurable()) {
         DurableSubscription durSub = backEnd.getDurableSubscription(connectionConsumer.getName());
         durSub.addSubscriber(connectionConsumer);
         backEnd.addDurableSubscription(connectionConsumer.getName(), durSub);
      }

      connection.connectionConsumerAdd(connectionConsumer);
      request.setResult(new JMSConnectionConsumerCreateResponse(connectionConsumer));
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   private int removeSubscription(Request invocableRequest) throws JMSException {
      BERemoveSubscriptionRequest request = (BERemoveSubscriptionRequest)invocableRequest;
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("BEManager removes Subscription: " + request.getName() + " client id = " + request.getClientId());
      }

      BackEnd backEnd = this.jmsService.getBEDeployer().findBackEnd(request.getBackEndId());
      if (backEnd == null) {
         throw new InvalidDestinationException("JMS Destination referenced by the request is not found");
      } else {
         backEnd.checkShutdownOrSuspendedNeedLock("remove subscription");
         String mapName = BEConsumerImpl.clientIdPlusName(request.getClientId(), request.getName(), request.getClientIdPolicy(), request.getDestinationName(), backEnd.getName());
         DurableSubscription durSub = backEnd.getDurableSubscription(mapName);
         if (durSub == null) {
            throw new InvalidDestinationException("Subscription " + mapName + " not found");
         } else {
            durSub.getConsumer().delete(false, true, false);
            request.setResult(new VoidResponse());
            request.setState(Integer.MAX_VALUE);
            return request.getState();
         }
      }
   }

   private static int completeUpdateParentRequest(Request r) {
      BEOrderUpdateParentRequest request = (BEOrderUpdateParentRequest)r;

      try {
         request.setResult(request.getOrderUpdate().getResult());
         request.setState(Integer.MAX_VALUE);
      } catch (Throwable var3) {
         request.getCompletionRequest().setResult(var3);
         return Integer.MAX_VALUE;
      }

      request.getCompletionRequest().setResult(Boolean.TRUE);
      return Integer.MAX_VALUE;
   }

   public JMSID getJMSID() {
      return null;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.jmsService.getDispatcherPartitionContext();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.jmsService, "BEManager");
      switch (request.getMethodId()) {
         case 8450:
            return this.browserCreate(request);
         case 9218:
            return this.connectionConsumerCreate((BEConnectionConsumerCreateRequest)request);
         case 13570:
            this.sessionCreate(request);
            request.setResult(new VoidResponse());
            request.setState(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
         case 14850:
            return this.removeSubscription(request);
         case 18178:
            return completeUpdateParentRequest(request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + request.getMethodId());
      }
   }

   public BEConnection[] getBEConnections() {
      Map invocableMap = this.jmsService.getInvocableManagerDelegate().getInvocableMap(15);
      synchronized(invocableMap) {
         BEConnection[] connections = new BEConnection[invocableMap.size()];
         Iterator it = invocableMap.values().iterator();

         for(int i = 0; it.hasNext(); connections[i++] = (BEConnection)it.next()) {
         }

         return connections;
      }
   }
}
