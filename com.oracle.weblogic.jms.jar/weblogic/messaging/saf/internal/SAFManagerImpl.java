package weblogic.messaging.saf.internal;

import java.io.Externalizable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.health.HealthState;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.saf.SAFService;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationHandle;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFConversationNotAvailException;
import weblogic.messaging.saf.SAFEndpointManager;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFManager;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFServiceNotAvailException;
import weblogic.messaging.saf.SAFTransport;
import weblogic.messaging.saf.common.AgentDeliverRequest;
import weblogic.messaging.saf.common.AgentDeliverResponse;
import weblogic.messaging.saf.common.SAFConversationHandleImpl;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.common.SAFRemoteContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.transaction.TransactionHelper;

public final class SAFManagerImpl implements SAFManager {
   private static final SAFManagerImpl manager = new SAFManagerImpl();
   private final Map endpointManagers = Collections.synchronizedMap(new HashMap());
   private final Map transports = Collections.synchronizedMap(new HashMap());
   private final HashMap conversationInfosOnSendingSide = new HashMap();
   private final HashMap conversationInfosOnReceivingSide = new HashMap();
   private final List localSendingAgents = Collections.synchronizedList(new ArrayList());
   private final Map localSendingAgentIdsByConversationName = Collections.synchronizedMap(new HashMap());
   private final List localReceivingAgents = Collections.synchronizedList(new ArrayList());
   private List conversationLifecycleListeners = Collections.synchronizedList(new ArrayList());

   private SAFManagerImpl() {
   }

   public static synchronized SAFManager getManager() {
      return manager;
   }

   private static void checkPartition() throws SAFException {
      String partitionName = PartitionUtils.getPartitionName();
      if (!PartitionUtils.isDomain(partitionName)) {
         throw new SAFServiceNotAvailException("WebService Reliable Messaging is not available in a partition");
      }
   }

   public void send(SAFRequest request) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("send(): conversationName= " + request.getConversationName() + " MessageId = " + request.getMessageId() + " transaction = " + TransactionHelper.getTransactionHelper().getTransaction());
      }

      SAFConversationInfo info = this.getCachedConversationInfoOnSendingSide(request.getConversationName());
      if (info == null) {
         throw new SAFConversationNotAvailException("Cannot send a message to an unknow conversation");
      } else {
         SendingAgentImpl agent = this.getSendingAgent(info, (SAFManager.ConversationNameRefinementCallback)null);
         agent.send(request);
      }
   }

   public void deliver(SAFConversationInfo conversationInfo, SAFRequest request) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("deliver(): " + request.getSequenceNumber());
      }

      AgentDeliverRequest deliverRequest = new AgentDeliverRequest(conversationInfo, request, false);
      this.deliverInternal(deliverRequest);
   }

   public SAFResult deliverSync(SAFConversationInfo conversationInfo, SAFRequest request) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("deliverSync(): " + request.getSequenceNumber());
      }

      AgentDeliverRequest deliverRequest = new AgentDeliverRequest(conversationInfo, request, true);
      AgentDeliverResponse response = this.deliverInternal(deliverRequest);
      return response.getResult();
   }

   private AgentDeliverResponse deliverInternal(AgentDeliverRequest arequest) throws SAFException {
      SAFConversationInfo conversationInfo = arequest.getConversationInfo();

      assert conversationInfo != null;

      ReceivingAgentImpl agent = this.getReceivingAgent(conversationInfo);

      assert agent != null;

      if (SAFDebug.SAFManager.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("deliverIntenal(): conversation =  " + conversationInfo.toString() + " receiving agent = " + agent);
      }

      return agent.deliver(arequest);
   }

   public void registerTransport(SAFTransport transport) {
      if (this.transports.get(new Integer(transport.getType())) == null) {
         this.transports.put(new Integer(transport.getType()), transport);
      }

   }

   public SAFTransport getTransport(int type) {
      return (SAFTransport)this.transports.get(new Integer(type));
   }

   public void registerEndpointManager(int type, SAFEndpointManager manager) {
      this.endpointManagers.put(new Integer(type), manager);
   }

   public SAFEndpointManager getEndpointManager(int type) {
      return (SAFEndpointManager)this.endpointManagers.get(new Integer(type));
   }

   public String registerConversationOnSendingSide(SAFConversationInfo info) throws SAFException {
      return this.registerConversationOnSendingSide(info, (SAFManager.ConversationNameRefinementCallback)null);
   }

   public String registerConversationOnSendingSide(SAFConversationInfo info, SAFManager.ConversationNameRefinementCallback callback) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("registerConversationOnSendingSide(): name = " + info.getConversationName());
      }

      if (info == null) {
         throw new IllegalArgumentException("Cannot register a conversation without a conversation info");
      } else {
         if (info.getRemoteContext() == null) {
            if (SAFDebug.SAFManager.isDebugEnabled()) {
               SAFDebug.SAFManager.debug("registerConversationOnSendingSide(): null remote context, create one");
            }

            info.setRemoteContext(new SAFRemoteContext());
         }

         SAFConversationInfo cachedInfo = this.getCachedConversationInfoOnSendingSide(info.getConversationName());
         if (cachedInfo != null) {
            return cachedInfo.getConversationName();
         } else {
            SendingAgentImpl agent = this.getSendingAgent(info, callback);
            agent.registerConversationInfo(info);
            return info.getConversationName();
         }
      }
   }

   public void closeConversationOnSendingSide(String conversationName, boolean destroy) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("closeConversationOnSendingSide(): conversationName= " + conversationName + " destroy? " + destroy);
      }

      SAFConversationInfo info = this.getCachedConversationInfoOnSendingSide(conversationName);
      if (info == null) {
         throw new SAFConversationNotAvailException("Cannot close a conversation that does not exist");
      } else {
         SendingAgentImpl agent = this.getSendingAgent(info, (SAFManager.ConversationNameRefinementCallback)null);
         agent.closeConversation(conversationName, destroy);
      }
   }

   final void addConversationInfoOnSendingSide(SAFConversationInfo info) {
      synchronized(this.conversationInfosOnSendingSide) {
         if (this.conversationInfosOnSendingSide.get(info.getConversationName()) == null) {
            this.conversationInfosOnSendingSide.put(info.getConversationName(), info);
            this.notifyAddConverationToCache(true, "ConversationName", info.getConversationName(), info);
            if (info.getCreateConversationMessageID() != null) {
               this.conversationInfosOnSendingSide.put(info.getCreateConversationMessageID(), info);
               this.notifyAddConverationToCache(true, "CreateConversationMessageD", info.getCreateConversationMessageID(), info);
            }
         }

      }
   }

   private SendingAgentImpl getSendingAgent(String conversationNameOrCreateConvMsgID) throws SAFException {
      SendingAgentImpl agentImpl = this.getLocalSendingAgent(conversationNameOrCreateConvMsgID);
      if (agentImpl == null) {
         agentImpl = this.findLocalSendingAgent(conversationNameOrCreateConvMsgID);
      }

      if (agentImpl != null) {
         return agentImpl;
      } else {
         throw new SAFServiceNotAvailException("The SAF agent that handles conversation " + conversationNameOrCreateConvMsgID + " is not available at the moment.");
      }
   }

   private SendingAgentImpl getCachedSendingAgentId(SAFConversationInfo info) {
      String createConvMsgID = info.getCreateConversationMessageID();
      SendingAgentImpl agent = this.getLocalSendingAgentId(info.getConversationName());
      if (agent == null && createConvMsgID != null) {
         agent = this.getLocalSendingAgentId(createConvMsgID);
      }

      return agent;
   }

   private SendingAgentImpl getSendingAgent(SAFConversationInfo info, SAFManager.ConversationNameRefinementCallback callback) throws SAFException {
      SendingAgentImpl agent = this.getCachedSendingAgentId(info);
      if (agent != null) {
         return agent;
      } else {
         agent = (SendingAgentImpl)getNextLocalAgent(SendingAgentImpl.getAgentFactory(), (String)null);
         if (SAFDebug.SAFManager.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFManager.debug("getSendingAgent(): candidated agent = " + agent);
         }

         if (agent != null) {
            if (callback != null) {
               SAFManager.LocationInfo locationInfo = new SAFManager.LocationInfo(agent.getStoreName());
               callback.conversationPreStore(info, locationInfo);
            }

            this.addLocalSendingAgentId(info, agent);
         }

         if (agent == null) {
            if (SendingAgentImpl.getAgentFactory().haveSendingAgentAvailable()) {
               throw new SAFServiceNotAvailException("Conversation '" + info.getConversationName() + "' does not exist, has timed out, or has been administratively destroyed");
            } else {
               throw new SAFServiceNotAvailException("There is no active SAF sending agent available on server '" + ManagementService.getRuntimeAccess((AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction())).getServerName() + "'.");
            }
         } else {
            return agent;
         }
      }
   }

   private ReceivingAgentImpl getReceivingAgent(SAFConversationInfo conversationInfo) throws SAFException {
      ReceivingAgentImpl agent = this.findLocalReceivingAgent(conversationInfo);
      if (agent == null) {
         if (SAFDebug.SAFManager.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFManager.debug("getReceivingAgent(): The SAF agent that handles conversation " + conversationInfo.getConversationName() + " is not available at the moment.");
         }

         throw new SAFException("The SAF receiving agent that handles conversation:" + conversationInfo.getConversationName() + " is not available at the moment.");
      } else {
         return agent;
      }
   }

   private static AgentImpl getNextLocalAgent(SAFAgentFactoryInternal agentFactory, String desiredStore) {
      return agentFactory.getAgentImpl(desiredStore);
   }

   void addLocalReceivingAgent(ReceivingAgentImpl receivingAgent) {
      this.localReceivingAgents.add(receivingAgent);
   }

   private SendingAgentImpl findLocalSendingAgent(String conversationName) {
      Iterator itr;
      synchronized(this.localSendingAgents) {
         itr = this.localSendingAgents.iterator();
      }

      SendingAgentImpl agent;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         agent = (SendingAgentImpl)itr.next();
      } while(agent.getConversation(conversationName) == null);

      return agent;
   }

   private ReceivingAgentImpl findLocalReceivingAgent(SAFConversationInfo info) {
      Iterator itr;
      synchronized(this.localReceivingAgents) {
         itr = this.localReceivingAgents.iterator();
      }

      ReceivingAgentImpl agent;
      do {
         if (!itr.hasNext()) {
            return null;
         }

         agent = (ReceivingAgentImpl)itr.next();
      } while(agent.getConversation(info) == null);

      return agent;
   }

   void removeLocalReceivingAgent(ReceivingAgentImpl receivingAgent) {
      this.localReceivingAgents.remove(receivingAgent);
   }

   void addLocalSendingAgent(SendingAgentImpl sendingAgent) {
      this.localSendingAgents.add(sendingAgent);
   }

   void removeLocalSendingAgent(SendingAgentImpl sendingAgent) {
      this.localSendingAgents.remove(sendingAgent);
   }

   void recordDynamicName(String cName, String dynamicCName) {
      if (!cName.equals(dynamicCName)) {
         this.localSendingAgentIdsByConversationName.put(dynamicCName, this.getLocalSendingAgentId(cName));

         try {
            this.addConversationInfoOnSendingSide(dynamicCName, this.getCachedConversationInfoOnSendingSide(cName));
         } catch (SAFException var4) {
         }

      }
   }

   private void addConversationInfoOnSendingSide(String dynamicName, SAFConversationInfo info) {
      synchronized(this.conversationInfosOnSendingSide) {
         if (this.conversationInfosOnSendingSide.get(dynamicName) == null) {
            this.conversationInfosOnSendingSide.put(dynamicName, info);
            this.notifyAddConverationToCache(true, "DynamicName", dynamicName, info);
         }

      }
   }

   void addLocalSendingAgentId(SAFConversationInfo info, SendingAgentImpl agent) {
      this.localSendingAgentIdsByConversationName.put(info.getConversationName(), agent);
      if (info.getCreateConversationMessageID() != null) {
         this.localSendingAgentIdsByConversationName.put(info.getCreateConversationMessageID(), agent);
      }

   }

   private SendingAgentImpl getLocalSendingAgentId(String conversationNameOrCreateConvMsgID) {
      return (SendingAgentImpl)this.localSendingAgentIdsByConversationName.get(conversationNameOrCreateConvMsgID);
   }

   private SendingAgentImpl getLocalSendingAgent(String conversationNameOrCreateConvMsgID) {
      return (SendingAgentImpl)this.localSendingAgentIdsByConversationName.get(conversationNameOrCreateConvMsgID);
   }

   void removeLocalSendingAgentId(SAFConversationInfo info) {
      this.localSendingAgentIdsByConversationName.remove(info.getConversationName());
      if (info.getDynamicConversationName() != null) {
         this.localSendingAgentIdsByConversationName.remove(info.getDynamicConversationName());
      }

      if (info.getCreateConversationMessageID() != null) {
         this.localSendingAgentIdsByConversationName.remove(info.getCreateConversationMessageID());
      }

      this.cleanUpConversationInfoOnSendingSide(info.getConversationName());
   }

   public SAFConversationHandle registerConversationOnReceivingSide(SAFConversationInfo info) throws SAFException {
      return this.registerConversationOnReceivingSide(info, (SAFManager.ConversationNameRefinementCallback)null, (String)null);
   }

   public SAFConversationHandle registerConversationOnReceivingSide(SAFConversationInfo info, SAFManager.ConversationNameRefinementCallback callback, String desiredStore) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("Registering conversation:" + info.getConversationName() + " on the receiving side");
      }

      ReceivingAgentImpl agent = this.findLocalReceivingAgent(info);
      if (agent == null) {
         agent = (ReceivingAgentImpl)getNextLocalAgent(ReceivingAgentImpl.getAgentFactory(), desiredStore);
      }

      if (agent == null) {
         throw new SAFServiceNotAvailException("There is no active SAF receiving agent available on the server" + (desiredStore != null ? " that is configured to use store '" + desiredStore + "'" : ""));
      } else {
         if (callback != null) {
            SAFManager.LocationInfo locationInfo = new SAFManager.LocationInfo(agent.getStoreName());
            callback.conversationPreStore(info, locationInfo);
         }

         this.addConversationInfoOnReceivingSide(info);
         ConversationReassembler conversationReassembler = agent.getConversation(info);
         if (conversationReassembler == null) {
            conversationReassembler = agent.createConversation(info, true);
         }

         if (conversationReassembler == null) {
            SAFException se = new SAFException("Cannot send messages to a conversation that was never registered, timed out, or destroyed");
            throw se;
         } else {
            conversationReassembler.setAgentConnectionEstablished();
            return new SAFConversationHandleImpl(info.getConversationName(), info.getConversationTimeout(), info.getMaximumIdleTime(), info.getConversationOffer(), info.getCreateConversationMessageID(), info.getContext());
         }
      }
   }

   public SAFManager.LocationInfo getLocationInfoForConversationOnSendingSide(String convId) {
      SendingAgentImpl agent = this.findLocalSendingAgent(convId);
      return agent == null ? null : new SAFManager.LocationInfo(agent.getStoreName());
   }

   public SAFManager.LocationInfo getLocationInfoForConversationOnReceivingSide(SAFConversationInfo info) {
      ReceivingAgentImpl agent = this.findLocalReceivingAgent(info);
      return agent == null ? null : new SAFManager.LocationInfo(agent.getStoreName());
   }

   public long getLastAcknowledged(SAFConversationInfo conversationInfo) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("getLastAcknowledged():" + conversationInfo);
      }

      ReceivingAgentImpl agent = this.findLocalReceivingAgent(conversationInfo);
      ConversationReassembler conversationReassembler = agent.getConversation(conversationInfo);
      return conversationReassembler.getLastAcked();
   }

   public SAFConversationInfo getCachedConversationInfoOnReceivingSide(String conversationName) throws SAFException {
      checkShutdownAndPartition();
      synchronized(this.conversationInfosOnReceivingSide) {
         return (SAFConversationInfo)this.conversationInfosOnReceivingSide.get(conversationName);
      }
   }

   public SAFConversationInfo getConversationInfoOnReceivingSide(String conversationNameOrCreateConvMsgID) throws SAFException {
      checkShutdownAndPartition();
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("getConversationInfoOnReceivingSide():" + conversationNameOrCreateConvMsgID);
      }

      return this.getCachedConversationInfoOnReceivingSide(conversationNameOrCreateConvMsgID);
   }

   public SAFConversationInfo getCachedConversationInfoOnSendingSide(String cid) throws SAFException {
      checkShutdownAndPartition();
      synchronized(this.conversationInfosOnSendingSide) {
         return (SAFConversationInfo)this.conversationInfosOnSendingSide.get(cid);
      }
   }

   public SAFConversationInfo getConversationInfoOnSendingSide(String conversationNameOrCreateConvMsgID) throws SAFException {
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("getConversationInfoOnSendingSide():" + conversationNameOrCreateConvMsgID);
      }

      return this.getCachedConversationInfoOnSendingSide(conversationNameOrCreateConvMsgID);
   }

   public boolean checkForConversationClosedOnReceivingSide(String convId) throws SAFException {
      return this.getConversationInfoOnReceivingSide(convId) == null;
   }

   public boolean checkForConversationClosedOnSendingSide(String convId) throws SAFException {
      return this.getConversationInfoOnSendingSide(convId) == null;
   }

   private void cleanUpConversationInfoOnSendingSide(String convId) {
      try {
         SAFConversationInfo info = this.getConversationInfoOnSendingSide(convId);
         if (info != null) {
            synchronized(this.conversationInfosOnSendingSide) {
               this.conversationInfosOnSendingSide.remove(convId);
               this.notifyRemoveConverationFromCache(true, "ConversationName", convId, info);
               if (info.isDynamic() && info.getDynamicConversationName() != null) {
                  this.conversationInfosOnSendingSide.remove(info.getDynamicConversationName());
                  this.notifyRemoveConverationFromCache(true, "DynamicName", info.getDynamicConversationName(), info);
               }

               if (info.getCreateConversationMessageID() != null) {
                  this.conversationInfosOnSendingSide.remove(info.getCreateConversationMessageID());
                  this.notifyRemoveConverationFromCache(true, "CreateConversationMessageD", info.getCreateConversationMessageID(), info);
               }
            }
         }
      } catch (Throwable var6) {
         var6.printStackTrace();
      }

   }

   public void closeConversationOnReceivingSide(SAFConversationInfo conversationInfo) throws SAFException {
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("closeConversationOnReceivingSide():" + conversationInfo);
      }

      ReceivingAgentImpl agent = this.findLocalReceivingAgent(conversationInfo);
      agent.removeConversation(conversationInfo);
   }

   public void closeRAConversation(SAFConversationInfo conversationInfo) {
      synchronized(this.conversationInfosOnReceivingSide) {
         this.conversationInfosOnReceivingSide.remove(conversationInfo.getConversationName());
         this.notifyRemoveConverationFromCache(false, "ConversationName", conversationInfo.getConversationName(), conversationInfo);
         if (conversationInfo.getCreateConversationMessageID() != null) {
            this.conversationInfosOnReceivingSide.remove(conversationInfo.getCreateConversationMessageID());
            this.notifyRemoveConverationFromCache(false, "CreateConversationMessageID", conversationInfo.getCreateConversationMessageID(), conversationInfo);
         }

      }
   }

   void addConversationInfoOnReceivingSide(SAFConversationInfo conversationInfo) {
      synchronized(this.conversationInfosOnReceivingSide) {
         this.conversationInfosOnReceivingSide.put(conversationInfo.getConversationName(), conversationInfo);
         this.notifyAddConverationToCache(false, "ConversationName", conversationInfo.getConversationName(), conversationInfo);
         if (conversationInfo.getCreateConversationMessageID() != null) {
            this.conversationInfosOnReceivingSide.put(conversationInfo.getCreateConversationMessageID(), conversationInfo);
            this.notifyAddConverationToCache(false, "CreateConversationMessageID", conversationInfo.getCreateConversationMessageID(), conversationInfo);
         }

      }
   }

   public void acknowledge(String conversationName, long sequenceNumberLow, long sequenceNumberHigh) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("acknowledge(): agent =  " + agent + " conversation = " + conversationName + " sequence number low = " + sequenceNumberLow + " sequence number high = " + sequenceNumberHigh);
      }

      agent.acknowledge(conversationName, sequenceNumberLow, sequenceNumberHigh);
   }

   public void handleAsyncFault(String conversationName, String messageId, Exception ex) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("handleFault(): agent =  " + agent + " conversation = " + conversationName + " message ID  = " + messageId + " Exception = " + ex.getMessage());
      }

      agent.handleAsyncFault(conversationName, messageId, ex);
   }

   public void createConversationSucceeded(SAFConversationHandle handle) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent;
      if (handle.getCreateConversationMessageID() != null) {
         agent = this.getSendingAgent(handle.getCreateConversationMessageID());
      } else {
         agent = this.getSendingAgent(handle.getConversationName());
      }

      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("onCreateSequenceSucceed(): found agent: " + agent + " for conversation " + handle.getConversationName() + " and createMsgID " + handle.getCreateConversationMessageID());
      }

      agent.onCreateConversationSucceed(handle);
   }

   public void storeConversationContextOnReceivingSide(String conversationName, Externalizable context) throws SAFException {
      checkShutdownAndPartition();
      SAFConversationInfo info = this.getCachedConversationInfoOnReceivingSide(conversationName);
      if (info == null) {
         throw new SAFException("Unknown conversation: " + conversationName);
      } else {
         info.setContext(context);
         ReceivingAgentImpl agent = this.getReceivingAgent(info);
         if (SAFDebug.SAFManager.isDebugEnabled()) {
            SAFDebug.SAFManager.debug("storeConversationContextOnReceivingSide(): found agent: " + agent + " for conversation " + conversationName);
         }

         agent.storeConversationInfo(info);
      }
   }

   public void storeConversationContextOnSendingSide(String conversationName, Externalizable context) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("storeConversationContextOnSendingSide(): found agent: " + agent + " for conversation " + conversationName);
      }

      SAFConversationInfo info = this.getCachedConversationInfoOnSendingSide(conversationName);
      if (info == null) {
         throw new SAFException("Unknown conversation: " + conversationName);
      } else {
         info.setContext(context);
         agent.storeConversationInfo(info);
      }
   }

   public List getAllSequenceNumberRangesOnReceivingSide(String conversationName) throws SAFException {
      SAFConversationInfo info = this.getCachedConversationInfoOnReceivingSide(conversationName);
      if (info == null) {
         throw new SAFException("Unknown conversation: " + conversationName);
      } else {
         ReceivingAgentImpl agent = this.getReceivingAgent(info);
         if (SAFDebug.SAFManager.isDebugEnabled()) {
            SAFDebug.SAFManager.debug("getAllSequenceNumberRangesOnReceivingSide(): found agent: " + agent + " for conversation " + conversationName);
         }

         ConversationReassembler conv = agent.getConversation(info);
         return conv.getAllSequenceNumberRanges();
      }
   }

   public long getLastAssignedSequenceValueOnSendingSide(String conversationName) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("getLastAssignedSequenceValueOnSendingSide(): found agent: " + agent + " for conversation " + conversationName);
      }

      ConversationAssembler conversation = agent.getConversation(conversationName);
      return conversation.getLastAssignedSequenceValue();
   }

   public List getAllSequenceNumberRangesOnSendingSide(String conversationName) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("getAllSequenceNumberRangesOnSendingSide(): found agent: " + agent + " for conversation " + conversationName);
      }

      ConversationAssembler conversation = agent.getConversation(conversationName);
      return conversation.getAllSequenceNumberRanges();
   }

   public boolean hasSentLastMessageOnSendingSide(String conversationName) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("hasSentLastMessageOnSendingSide(): found agent: " + agent + " for conversation " + conversationName);
      }

      ConversationAssembler conversation = agent.getConversation(conversationName);
      return conversation.hasSeenLastMsg();
   }

   public void setSentLastMessageOnSendingSide(String conversationName, long seqNum) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("setSentLastMessageOnSendingSide(): found agent: " + agent + " for conversation " + conversationName);
      }

      ConversationAssembler conversation = agent.getConversation(conversationName);
      conversation.setSeenLastMsg(true);
      conversation.setLastMsgSequenceNumber(seqNum);
      conversation.checkCompleted();
   }

   public boolean hasReceivedLastMessageOnReceivingSide(String conversationName) throws SAFException {
      SAFConversationInfo info = this.getCachedConversationInfoOnReceivingSide(conversationName);
      if (info == null) {
         throw new SAFException("Unknown conversation: " + conversationName);
      } else {
         ReceivingAgentImpl agent = this.getReceivingAgent(info);
         if (SAFDebug.SAFManager.isDebugEnabled()) {
            SAFDebug.SAFManager.debug("hasReceivedLastMessageOnReceivingSide(): found agent: " + agent + " for conversation " + conversationName);
         }

         ConversationReassembler conv = agent.getConversation(info);
         return conv.hasSeenLastMsg();
      }
   }

   public long getLastMessageSequenceNumberOnReceivingSide(String conversationName) throws SAFException {
      SAFConversationInfo info = this.getCachedConversationInfoOnReceivingSide(conversationName);
      if (info == null) {
         throw new SAFException("Unknown conversation: " + conversationName);
      } else {
         ReceivingAgentImpl agent = this.getReceivingAgent(info);
         if (SAFDebug.SAFManager.isDebugEnabled()) {
            SAFDebug.SAFManager.debug("getLastMessageSequenceNumberOnReceivingSide(): found agent: " + agent + " for conversation " + conversationName);
         }

         ConversationReassembler conv = agent.getConversation(info);
         return conv.getLastMsgSequenceNumber();
      }
   }

   public long getLastMessageSequenceNumberOnSendingSide(String conversationName) throws SAFException {
      checkShutdownAndPartition();
      SendingAgentImpl agent = this.getSendingAgent(conversationName);
      if (SAFDebug.SAFManager.isDebugEnabled()) {
         SAFDebug.SAFManager.debug("getLastMessageSequenceNumberOnSendingSide(): found agent: " + agent + " for conversation " + conversationName);
      }

      ConversationAssembler conversation = agent.getConversation(conversationName);
      return conversation.getLastMsgSequenceNumber();
   }

   public void addConversationLifecycleListener(SAFManager.ConversationLifecycleListener listener) {
      this.conversationLifecycleListeners.add(listener);
   }

   public void removeConversationLifecycleListener(SAFManager.ConversationLifecycleListener listener) {
      this.conversationLifecycleListeners.remove(listener);
   }

   void notifyAddConverationToCache(boolean sending, String description, String key, SAFConversationInfo info) {
      int size;
      if (sending) {
         synchronized(this.conversationInfosOnSendingSide) {
            size = this.conversationInfosOnSendingSide.size();
         }
      } else {
         synchronized(this.conversationInfosOnReceivingSide) {
            size = this.conversationInfosOnReceivingSide.size();
         }
      }

      SAFManager.ConversationLifecycleListener[] listeners = (SAFManager.ConversationLifecycleListener[])((SAFManager.ConversationLifecycleListener[])this.conversationLifecycleListeners.toArray(new SAFManager.ConversationLifecycleListener[0]));

      for(int i = 0; i < listeners.length; ++i) {
         listeners[i].addToCache(sending, description, key, info, size);
      }

   }

   void notifyRemoveConverationFromCache(boolean sending, String description, String key, SAFConversationInfo info) {
      int size;
      if (sending) {
         synchronized(this.conversationInfosOnSendingSide) {
            size = this.conversationInfosOnSendingSide.size();
         }
      } else {
         synchronized(this.conversationInfosOnReceivingSide) {
            size = this.conversationInfosOnReceivingSide.size();
         }
      }

      SAFManager.ConversationLifecycleListener[] listeners = (SAFManager.ConversationLifecycleListener[])this.conversationLifecycleListeners.toArray(new SAFManager.ConversationLifecycleListener[0]);

      for(int i = 0; i < listeners.length; ++i) {
         listeners[i].removeFromCache(sending, description, key, info, size);
      }

   }

   void notifyPreConversationClose(boolean sending, boolean destroy, SAFConversationInfo info) {
      SAFManager.ConversationLifecycleListener[] listeners = (SAFManager.ConversationLifecycleListener[])this.conversationLifecycleListeners.toArray(new SAFManager.ConversationLifecycleListener[0]);

      for(int i = 0; i < listeners.length; ++i) {
         listeners[i].preClose(sending, destroy, info);
      }

   }

   void notifyAckConversation(SAFConversationInfo info, long low, long high) {
      SAFManager.ConversationLifecycleListener[] listeners = (SAFManager.ConversationLifecycleListener[])((SAFManager.ConversationLifecycleListener[])this.conversationLifecycleListeners.toArray(new SAFManager.ConversationLifecycleListener[0]));

      for(int i = 0; i < listeners.length; ++i) {
         listeners[i].ack(info, low, high);
      }

   }

   public Set getConversationNamesOnSendingSide() {
      synchronized(this.conversationInfosOnSendingSide) {
         return new HashSet(this.conversationInfosOnSendingSide.keySet());
      }
   }

   public Set getConversationNamesOnReceivingSide() {
      synchronized(this.conversationInfosOnReceivingSide) {
         return new HashSet(this.conversationInfosOnReceivingSide.keySet());
      }
   }

   private static void checkShutdownAndPartition() throws SAFException {
      checkPartition();

      try {
         SAFServerService safServerService = SAFServerService.getService();
         if (safServerService == null) {
            throw new SAFServiceNotAvailException("Reliable Messaging cannot be invoked from a java client");
         } else {
            safServerService.checkShutdown();
         }
      } catch (ServiceFailureException var1) {
         throw new SAFServiceNotAvailException("SAF service is shutting down");
      }
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("SAF");
      HealthState health = SAFService.getSAFService().getRuntimeMBean().getHealthState();
      SAFDiagnosticImageSource.dumpHealthStateElement(xsw, health);
      xsw.writeStartElement("EndpointManagers");
      Object[] ems = this.endpointManagers.values().toArray();
      xsw.writeAttribute("count", String.valueOf(ems.length));

      for(int i = 0; i < ems.length; ++i) {
         xsw.writeStartElement("EndpointManager");
         xsw.writeCharacters(ems[i].toString());
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeStartElement("Transports");
      Object[] t = this.transports.values().toArray();
      xsw.writeAttribute("count", String.valueOf(t.length));

      for(int i = 0; i < t.length; ++i) {
         SAFTransport transport = (SAFTransport)t[i];
         xsw.writeStartElement("Transport");
         xsw.writeAttribute("type", String.valueOf(transport.getType()));
         xsw.writeAttribute("isGapsAllowed", String.valueOf(transport.isGapsAllowed()));
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeStartElement("SendingAgents");
      Object[] sa = this.localSendingAgents.toArray();
      xsw.writeAttribute("count", String.valueOf(sa.length));

      for(int i = 0; i < sa.length; ++i) {
         SendingAgentImpl sai = (SendingAgentImpl)sa[i];
         sai.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeStartElement("ReceivingAgents");
      Object[] ra = this.localReceivingAgents.toArray();
      xsw.writeAttribute("count", String.valueOf(ra.length));

      for(int i = 0; i < ra.length; ++i) {
         ReceivingAgentImpl rai = (ReceivingAgentImpl)ra[i];
         rai.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }
}
