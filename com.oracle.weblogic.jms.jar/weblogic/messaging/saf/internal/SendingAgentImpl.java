package weblogic.messaging.saf.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Externalizable;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.jms.saf.SAFService;
import weblogic.management.ManagementException;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.SAFConversationRuntimeMBean;
import weblogic.messaging.common.SQLFilter;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaException;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.kernel.internal.KernelImpl;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationHandle;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFConversationNotAvailException;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFServiceNotAvailException;
import weblogic.messaging.saf.common.SAFConversationInfoImpl;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.common.SAFRequestImpl;
import weblogic.messaging.saf.store.SAFStore;
import weblogic.messaging.saf.store.SAFStoreException;
import weblogic.messaging.saf.utils.Util;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.T3Srvr;

public final class SendingAgentImpl extends AgentImpl implements SendingAgent, Externalizable {
   static final long serialVersionUID = -2776562935951109789L;
   private HashMap conversations;
   private long conversationsCurrentCount;
   private long conversationsHighCount;
   private long conversationsTotalCount;
   private HashMap dynamicNameToName;
   private HashMap conversationsByCreateConvMsgID;
   private long failedMessagesTotal;
   private HashMap kernelTopics;
   private double retryDelayMultiplier;
   private long retryDelayBase;
   private long retryDelayMaximum;
   private long timeToLiveDefault;
   private long conversationIdleTimeMaximum;
   private boolean isLoggingEnabled;
   private int windowSize;
   private boolean isPausedForIncoming;
   private boolean isPausedForForwarding;
   private Kernel kernel;
   private Quota kernelQuota;
   private static final SAFAgentFactoryInternal agentFactoryInternal = new SAFAgentFactoryInternal();
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static RuntimeAccess runtimeAccess;
   private ServerStateChangeListener stateChangeListener;

   public SendingAgentImpl() {
   }

   private SendingAgentImpl(String name) {
      this.name = name;
   }

   SendingAgentImpl(String name, SAFAgentAdmin agentAdmin, SAFStore store) throws NamingException, SAFException {
      super(name, agentAdmin, store, 1);
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + name + "': RetryDelayBase=" + this.retryDelayBase + ", RetryDelayMaximum=" + this.retryDelayMaximum + ", RetryDelayMultiplier=" + this.retryDelayMultiplier + ", ConversationIdleTimeMaximum=" + this.conversationIdleTimeMaximum + ", TimeToLive=" + this.timeToLiveDefault + ", WindowSize=" + this.windowSize);
      }

   }

   public String toString() {
      return "<SendingAgentImpl> :  SAFAgentInternalName = " + this.name + " " + this.store;
   }

   static SAFAgentFactoryInternal getAgentFactory() {
      return agentFactoryInternal;
   }

   protected synchronized void addToAgentFactory() {
      agentFactoryInternal.addAgent(this);
      safManager.addLocalSendingAgent(this);
   }

   protected void removeFromAgentFactory() {
      agentFactoryInternal.removeAgent(this);
      safManager.removeLocalSendingAgent(this);
   }

   public void startInitialize(SAFAgentMBean mbean) throws SAFException {
      this.state = 1;
      this.retryDelayBase = mbean.getDefaultRetryDelayBase();
      this.retryDelayMultiplier = mbean.getDefaultRetryDelayMultiplier();
      this.retryDelayMaximum = mbean.getDefaultRetryDelayMaximum();
      this.timeToLiveDefault = mbean.getDefaultTimeToLive();
      this.conversationIdleTimeMaximum = mbean.getConversationIdleTimeMaximum();
      this.windowSize = mbean.getWindowSize();
      this.isPaused = this.isPausedForIncoming = mbean.isIncomingPausedAtStartup();
      this.isPausedForForwarding = mbean.isForwardingPausedAtStartup();
      this.isLoggingEnabled = mbean.isLoggingEnabled();
      this.kernel = SAFService.getSAFService().getDeployer().getAgent(this.name).getBackEnd().getKernel();
      this.kernelQuota = SAFService.getSAFService().getDeployer().getAgent(this.name).getBackEnd().getQuota();
      this.initializeMaps();
   }

   private void initializeMaps() {
      this.conversations = new HashMap();
      this.dynamicNameToName = new HashMap();
      this.kernelTopics = new HashMap();
      this.conversationsByCreateConvMsgID = new HashMap();
      if (this.conversationInfosFromStore == null) {
         this.conversationInfosFromStore = new HashMap();
      }

   }

   protected void start() throws SAFException {
      synchronized(this) {
         if ((this.state & 6) != 0) {
            return;
         }

         this.waitForState(1);
         this.state = 2;
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' has recovered " + this.conversationInfosFromStore.size() + " conversation infos");
      }

      Collection topics = this.kernel.getTopics();
      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' is starting: found " + topics.size() + " topics");
      }

      Iterator i = topics.iterator();

      while(i.hasNext()) {
         Topic topic = (Topic)i.next();
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Topic: " + topic.getName());
         }

         if (!isNotWSRM(topic.getName())) {
            try {
               topic.setProperty("Quota", this.kernelQuota);
               topic.setFilter(new SQLFilter(this.kernel, SAFVariableBinder.THE_ONE));
               this.kernelTopics.put(topic.getName(), topic);
            } catch (KernelException var12) {
               if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  var12.printStackTrace();
               }

               throw new SAFException(var12.getMessage(), var12);
            }

            if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': recovered topic = " + topic.getName());
            }
         }
      }

      int numQueues = this.convertQueuesToTopics();

      try {
         Iterator itr = this.conversationInfosFromStore.values().iterator();

         while(itr.hasNext()) {
            SAFConversationInfo info = (SAFConversationInfo)itr.next();
            this.findOrCreateConversation(info);
            if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': recovered conversation = " + info);
            }
         }
      } catch (SAFException var13) {
         Symptom symptom = new Symptom(SymptomType.SAF_SEND_ERROR, Severity.HIGH, this.name, var13.getMessage());
         this.healthState = updateHealthState(this.healthState, 3, symptom);
         this.close();
         synchronized(this) {
            this.state = 1;
         }

         throw var13;
      }

      try {
         this.activateAllKernelTopics();
      } catch (KernelException var11) {
         throw new SAFException(var11.getMessage());
      }

      if (numQueues > 0) {
         this.moveQueueMessagesToTopic();
      }

      synchronized(this) {
         this.state = 4;
         if (this.waitersCount > 0) {
            this.notifyAll();
         }

      }
   }

   private int convertQueuesToTopics() throws SAFException {
      Collection queues = this.kernel.getQueues();
      int count = 0;
      Iterator i = queues.iterator();

      while(i.hasNext()) {
         Queue queue = (Queue)i.next();
         if (!isNotWSRM(queue.getName())) {
            ++count;
            this.kernelTopics.put(queue.getName(), this.createKernelTopic(queue.getName(), queue.getProperties()));
            if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': converted queue = " + queue.getName() + " to a topic");
            }
         }
      }

      return count;
   }

   private void moveQueueMessagesToTopic() throws SAFException {
      Collection queues = this.kernel.getQueues();
      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' is starting: move messages from pre9.0.1 saf repository to post 9.0 one ");
      }

      Iterator i = queues.iterator();

      while(i.hasNext()) {
         Queue queue = (Queue)i.next();
         if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Queue: " + queue.getName());
         }

         if (!isNotWSRM(queue.getName())) {
            try {
               Cursor cursor = queue.createCursor(true, (Expression)null, -1);
               KernelRequest kernelRequest = new KernelRequest();

               while(true) {
                  MessageElement element;
                  if ((element = cursor.next()) == null) {
                     cursor.close();
                     kernelRequest.getResult();
                     kernelRequest.reset();
                     queue.delete(kernelRequest);
                     kernelRequest.getResult();
                     break;
                  }

                  SAFRequest request = (SAFRequest)element.getMessage();
                  String conversationName = request.getConversationName();
                  Queue subQueue = this.kernel.findQueue(conversationName);
                  Sequence sequence = subQueue.findSequence(conversationName);
                  sequence.setPassthru(true);
                  SendOptions options = new SendOptions();
                  options.setPersistent(request.getDeliveryMode() == 2);
                  options.setTimeout(0L);
                  options.setSequence(sequence);
                  options.setSequenceNum(element.getSequenceNum());
                  kernelRequest = subQueue.send((SAFRequestImpl)request, options);
               }
            } catch (KernelException var12) {
               if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  var12.printStackTrace();
               }

               throw new SAFException(var12.getMessage(), var12);
            }

            if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': recovered messages from queue = " + queue.getName());
            }
         }
      }

   }

   private void activateAllKernelTopics() throws KernelException {
      Iterator itr;
      Iterator itr2;
      synchronized(this) {
         itr = this.kernelTopics.values().iterator();
         itr2 = this.conversations.values().iterator();
      }

      while(itr2.hasNext()) {
         String conversationName = ((ConversationAssembler)itr2.next()).getConversationName();
         Queue queue = this.kernel.findQueue(conversationName);
         if (queue != null) {
            Sequence sequence = queue.findSequence(conversationName);
            if (sequence != null && sequence.isPassthru()) {
               sequence.setPassthru(false);
            }
         }
      }

      while(itr.hasNext()) {
         Topic topic = (Topic)itr.next();
         topic.resume(16384);
      }

   }

   void send(SAFRequest request) throws SAFException {
      if (this.isPausedForIncoming()) {
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' == send(): agent is paused for incoming");
         }

         throw new SAFServiceNotAvailException("The agent is paused for incoming messages");
      } else {
         this.sendInternal(request);
      }
   }

   private ConversationAssembler prepareSAFRequest(SAFRequest request) throws SAFException {
      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' prepareSAFRequest(): timestamp =" + request.getTimestamp() + " conversationName = " + request.getConversationName());
      }

      if (request.getTimestamp() == -1L || request.getTimestamp() == 0L) {
         request.setTimestamp(System.currentTimeMillis());
      }

      if (request.getMessageId() == null) {
         request.setMessageId(Util.generateID().toString());
      }

      ConversationAssembler conversation = this.getConversation(request.getConversationName());
      if (conversation == null) {
         throw new SAFConversationNotAvailException("Failed to send a message: conversation " + request.getConversationName() + " has never been registered.");
      } else if (conversation.isNotAvailAndClosed()) {
         throw new SAFConversationNotAvailException("Failed to send a message: conversation " + request.getConversationName() + " has expired or terminated, or has been destroyed administratively");
      } else if (this.getRemoteEndpoint(conversation.getInfo().getDestinationURL()).isPausedForIncoming()) {
         throw new SAFException("The endpoint " + conversation.getInfo().getDestinationURL() + " is paused for incoming messages");
      } else {
         if (request.getTimeToLive() == -1L) {
            request.setTimeToLive(this.timeToLiveDefault);
         }

         return conversation;
      }
   }

   private void sendInternal(SAFRequest request) throws SAFException {
      synchronized(this) {
         this.waitForState(4);
      }

      ConversationAssembler conversation = this.prepareSAFRequest(request);
      Topic topic = this.findKernelTopic(getKernelTopicName(conversation.getInfo()));
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.getName() + "': message writer about to put message " + request.getMessageId() + " to the SAF repository, persistent = " + (request.getDeliveryMode() == 2));
      }

      try {
         SendOptions options = new SendOptions();
         options.setPersistent(request.getDeliveryMode() == 2);
         options.setTimeout(0L);
         KernelRequest kr = topic.send((SAFRequestImpl)request, options);
         if (kr != null) {
            kr.getResult();
         }
      } catch (QuotaException var7) {
         if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            var7.printStackTrace();
         }

         throw new SAFException("Failed to save a message to SAF repository because  quota has been exceeded", var7);
      } catch (KernelException var8) {
         throw new SAFException("Failed to store the request to messaging kernel", var8);
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' == send(): Message stored: " + request.getMessageId());
      }

   }

   private void destroyConversation(String conversationName) {
      ConversationAssembler con = this.getConversation(conversationName);
      con.destroy();
   }

   private void closeConversation(String conversationName) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': closing conversation " + conversationName);
      }

      ConversationAssembler con = this.getConversation(conversationName);
      if (con != null && !con.isNotAvailAndClosed()) {
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Anget " + this.name + " closeConversation():  send a fake request with endOfConversation flag");
         }

         SAFRequest request = new SAFRequestImpl();
         request.setConversationName(conversationName);
         request.setSequenceNumber(-1L);
         request.setEndOfConversation(true);
         request.setPayload((Externalizable)null);
         long timeLeft = con.getTimeLeft();
         if (timeLeft == 0L) {
            request.setTimeToLive(100L);
         } else {
            request.setTimeToLive(timeLeft);
         }

         request.setTimestamp(System.currentTimeMillis());
         int index = conversationName.indexOf("NonPersistent");
         if (index != -1 && index + 13 == conversationName.length()) {
            request.setDeliveryMode(1);
         } else {
            request.setDeliveryMode(2);
         }

         try {
            this.sendInternal(request);
         } catch (SAFException var8) {
            if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
               var8.printStackTrace();
            }
         }

         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' == closeConversation():  after sending a fake request with endOfConversation flag");
         }

      }
   }

   void closeConversation(String conversationName, boolean destroy) {
      if (destroy) {
         this.destroyConversation(conversationName);
      } else {
         this.closeConversation(conversationName);
      }
   }

   RemoteEndpointRuntimeDelegate getRemoteEndpoint(String destinationURL) {
      return this.agentAdmin.getRemoteEndpoint(destinationURL);
   }

   synchronized Topic getKernelTopic(SAFConversationInfo info) {
      return this.kernel.findTopic(getKernelTopicName(info));
   }

   private void findOrCreateKernelTopic(SAFConversationInfo info) throws SAFException {
      String qname = getKernelTopicName(info);
      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' == findOrCreateKernelTopic(): " + qname);
      }

      boolean created = false;
      Topic topic;
      synchronized(this.kernel) {
         topic = this.findKernelTopic(qname);
         if (topic == null) {
            created = true;
            topic = this.createKernelTopic(qname, (Map)null);
         } else if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' found a topic : " + qname);
         }
      }

      if (created) {
         try {
            topic.resume(16384);
         } catch (KernelException var7) {
            throw new SAFException("Cannot create reader on the topic", var7);
         }
      }

   }

   private Topic findKernelTopic(String name) {
      return this.kernel.findTopic(name);
   }

   private Topic createKernelTopic(String qname, Map properties) throws SAFException {
      if (properties == null) {
         properties = new HashMap();
         ((Map)properties).put("Durable", new Boolean(true));
         ((Map)properties).put("Quota", this.kernelQuota);
      }

      try {
         Topic topic = this.kernel.createTopic(qname, (Map)properties);
         topic.setFilter(new SQLFilter(this.kernel, SAFVariableBinder.THE_ONE));
         return topic;
      } catch (KernelException var4) {
         throw new SAFException("Cannot create kernel topic", var4);
      }
   }

   public void suspend(boolean force) {
      try {
         this.close();
      } catch (SAFException var3) {
      }

   }

   public void resume() throws SAFException {
      this.start();
   }

   private void close() throws SAFException {
      this.close(false);
   }

   void close(boolean destroy) throws SAFException {
      synchronized(this) {
         if ((this.state & 9) != 0) {
            return;
         }

         this.waitForState(6);
         this.state = 8;
         if (this.stateChangeListener != null) {
            runtimeAccess.getServerRuntime().removePropertyChangeListener(this.stateChangeListener);
         }
      }

      boolean var15 = false;

      try {
         var15 = true;
         this.unadvertise();
         Iterator itr;
         synchronized(this) {
            this.conversationsCurrentCount = 0L;
            HashMap cloneMap = (HashMap)this.conversations.clone();
            itr = cloneMap.keySet().iterator();
            this.dynamicNameToName.clear();
         }

         while(itr.hasNext()) {
            String conversationName = (String)itr.next();
            ConversationAssembler con = (ConversationAssembler)this.conversations.get(conversationName);
            if (con != null) {
               if (destroy) {
                  con.delete();
               } else {
                  this.removeConversation(conversationName, false);
                  con.close();
               }
            }
         }

         this.store.close();
         var15 = false;
      } finally {
         if (var15) {
            synchronized(this) {
               this.conversations.clear();
               this.conversationsByCreateConvMsgID.clear();
               this.state = 1;
               if (this.waitersCount > 0) {
                  this.notifyAll();
               }

            }
         }
      }

      synchronized(this) {
         this.conversations.clear();
         this.conversationsByCreateConvMsgID.clear();
         this.state = 1;
         if (this.waitersCount > 0) {
            this.notifyAll();
         }
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending agent '" + this.name + "' has been closed");
      }

   }

   private SAFConversationInfo normalizeSAFConversationInfo(SAFConversationInfo info) {
      synchronized(this.conversationInfosFromStore) {
         return (SAFConversationInfo)this.conversationInfosFromStore.get(info.getConversationName());
      }
   }

   private boolean findOrCreateConversation(SAFConversationInfo info) throws SAFException {
      if (info == null) {
         throw new IllegalArgumentException("Cannot create a conversation with null conversation info");
      } else {
         boolean needToStoreInfo = false;
         this.findOrCreateKernelTopic(info);
         ConversationAssembler con;
         SAFConversationInfo realInfo;
         synchronized(this) {
            con = this.getConversation(info.getConversationName());
            SAFConversationInfo offer;
            if (con != null) {
               if (con.isNotAvailAndClosed()) {
                  throw new SAFConversationNotAvailException("Conversation " + info.getConversationName() + " has expired or has been terminated or destroyed");
               }

               offer = con.getInfo().getConversationOffer();
               if (offer != info.getConversationOffer()) {
                  throw new SAFException(" Illegal usage of SAFConversation. Offer = " + offer + " in SAFConversation already registered with the Sending Agent does not match the current offer = " + info.getConversationOffer());
               }

               return false;
            }

            offer = this.normalizeSAFConversationInfo(info);
            if (offer == null) {
               if (((SAFConversationInfoImpl)info).getTimestamp() == 0L) {
                  ((SAFConversationInfoImpl)info).setTimestamp(System.currentTimeMillis());
               }

               needToStoreInfo = true;
            }

            realInfo = offer != null ? offer : info;
            String conversationName = realInfo.getConversationName();

            try {
               con = new ConversationAssembler(this, realInfo, this.store, this.isLoggingEnabled, this.windowSize);
               this.addConversation(con);
            } catch (ManagementException var12) {
               if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
                  var12.printStackTrace();
                  SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': failed to create a new conversation runtime MBean " + conversationName);
               }

               throw new SAFException("Sending Agent '" + this.name + " Failed to create runtime MBEan for conversation " + conversationName);
            }
         }

         con.setupSubscriptionQueue();
         safManager.addLocalSendingAgentId(info, this);
         safManager.addConversationInfoOnSendingSide(info);
         if (realInfo.getDynamicConversationName() != null && !realInfo.getDynamicConversationName().equals(realInfo.getConversationName())) {
            con.createAndRecordDynamicConversation(realInfo.getConversationName(), realInfo.getDynamicConversationName());
         }

         if (T3Srvr.getT3Srvr().getRunState() == 2) {
            con.startFromADiffThread();
         } else {
            synchronized(this) {
               if (this.stateChangeListener == null) {
                  this.stateChangeListener = new ServerStateChangeListener();
                  runtimeAccess.getServerRuntime().addPropertyChangeListener(this.stateChangeListener);
               }
            }
         }

         return needToStoreInfo;
      }
   }

   RemoteEndpointRuntimeDelegate findOrCreateRemoteEndpointRuntime(String url, int type, Topic topicFinal) throws ManagementException {
      return this.agentAdmin.findOrCreateRemoteEndpointRuntime(url, type, topicFinal);
   }

   synchronized void addDynamicName(String name, String dynamicName) {
      this.dynamicNameToName.put(dynamicName, name);
      this.addConversation(dynamicName, this.getConversation(name));
   }

   private synchronized String getOrginalName(String dynamicName) {
      return (String)this.dynamicNameToName.get(dynamicName);
   }

   private void addConversation(ConversationAssembler con) {
      if (this.conversations.get(con.getName()) == null) {
         ++this.conversationsCurrentCount;
         ++this.conversationsTotalCount;
         if (this.conversationsCurrentCount > this.conversationsHighCount) {
            this.conversationsHighCount = this.conversationsCurrentCount;
         }

         this.conversations.put(con.getName(), con);
         if (con.getInfo().isDynamic()) {
            this.conversationsByCreateConvMsgID.put(con.getInfo().getCreateConversationMessageID(), con);
         }
      }

   }

   private void addConversation(String dynamicName, ConversationAssembler con) {
      this.conversations.put(dynamicName, con);
   }

   public synchronized ConversationAssembler getConversation(String conversationName) {
      return (ConversationAssembler)this.conversations.get(conversationName);
   }

   ConversationAssembler removeConversation(String conversationName, boolean destroy) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "' : removing conversation " + conversationName + " destroy = " + destroy);
      }

      SAFConversationInfo info = this.getConversationInfo(conversationName);
      if (info == null) {
         return null;
      } else {
         if (destroy && this.store != null) {
            try {
               this.store.removeConversationInfo(info);
            } catch (SAFStoreException var9) {
            }
         }

         RemoteEndpointRuntimeDelegate remoteEndpoint = this.getRemoteEndpoint(info.getDestinationURL());
         if (remoteEndpoint != null) {
            remoteEndpoint.removeConversation(conversationName);
         }

         safManager.removeLocalSendingAgentId(info);
         synchronized(this) {
            if (this.conversations.get(conversationName) != null) {
               --this.conversationsCurrentCount;
               ConversationAssembler conv = (ConversationAssembler)this.conversations.remove(conversationName);
               if (info.getDynamicConversationName() != null) {
                  this.conversations.remove(info.getDynamicConversationName());
                  this.dynamicNameToName.remove(info.getDynamicConversationName());
               }

               if (info.getCreateConversationMessageID() != null) {
                  this.conversations.remove(info.getCreateConversationMessageID());
                  this.removeConversationByCreateConvMsgID(info.getCreateConversationMessageID());
               }

               this.conversationInfosFromStore.remove(conversationName);
               return conv;
            } else {
               return null;
            }
         }
      }
   }

   private synchronized ConversationAssembler removeConversationByCreateConvMsgID(String createConvMsgId) {
      return (ConversationAssembler)this.conversationsByCreateConvMsgID.remove(createConvMsgId);
   }

   void registerConversationInfo(SAFConversationInfo info) throws SAFException {
      if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': registering conversation info: " + info);
      }

      if (this.findOrCreateConversation(info)) {
         this.storeConversationInfo(info);
      }

   }

   private synchronized SAFConversationInfo getConversationInfo(String conversationNameOrCreateConvMsgID) {
      ConversationAssembler con = (ConversationAssembler)this.conversations.get(conversationNameOrCreateConvMsgID);
      if (con == null) {
         con = (ConversationAssembler)this.conversationsByCreateConvMsgID.get(conversationNameOrCreateConvMsgID);
      }

      return con == null ? null : con.getInfo();
   }

   synchronized double getDefaultRetryDelayMultiplier() {
      return this.retryDelayMultiplier;
   }

   synchronized long getDefaultRetryDelayBase() {
      return this.retryDelayBase;
   }

   synchronized long getDefaultRetryDelayMaximum() {
      return this.retryDelayMaximum;
   }

   synchronized long getDefaultTimeToLive() {
      return this.timeToLiveDefault;
   }

   synchronized long getDefaultMaximumIdleTime() {
      return this.conversationIdleTimeMaximum;
   }

   void acknowledge(String conversationName, long sequenceNumberLow, long sequenceNumberHigh) {
      ConversationAssembler conversation = this.getConversation(conversationName);
      if (conversation == null) {
         String name = this.getOrginalName(conversationName);
         if (name != null) {
            conversation = this.getConversation(name);
         }
      }

      if (conversation != null) {
         try {
            conversation.acknowledge(sequenceNumberLow, sequenceNumberHigh);
         } catch (SAFException var8) {
            if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': failed to acknowledge messages " + sequenceNumberLow + ":" + sequenceNumberHigh);
               var8.printStackTrace();
            }
         }

      }
   }

   void handleAsyncFault(String conversationName, String messageId, Exception ex) {
      ConversationAssembler conversation = this.getConversation(conversationName);
      if (conversation == null) {
         String name = this.getOrginalName(conversationName);
         if (name != null) {
            conversation = this.getConversation(name);
         }
      }

      if (conversation != null) {
         try {
            conversation.handleAsyncFault(messageId, ex);
         } catch (SAFException var6) {
            if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': failed to handle Fault related to " + messageId);
               var6.printStackTrace();
            }
         }

      }
   }

   void onCreateConversationSucceed(SAFConversationHandle handle) {
      ConversationAssembler con;
      if (handle.getCreateConversationMessageID() != null) {
         con = this.removeConversationByCreateConvMsgID(handle.getCreateConversationMessageID());
      } else {
         con = this.getConversation(handle.getConversationName());
      }

      if (con != null) {
         synchronized(this) {
            con.onCreateConversationSucceed(handle);
         }
      }

   }

   synchronized void increaseFailedMessagesCount() {
      ++this.failedMessagesTotal;
   }

   SAFConversationRuntimeMBean[] getConversationRuntimeDelegates() {
      Iterator itr;
      int size;
      synchronized(this) {
         size = this.conversations.size();
         itr = ((HashMap)this.conversations.clone()).values().iterator();
      }

      int i = 0;
      SAFConversationRuntimeMBean[] cons = new SAFConversationRuntimeMBean[size];

      while(itr.hasNext()) {
         ConversationAssembler con = (ConversationAssembler)itr.next();
         if (this.getConversation(con.getName()) != null) {
            cons[i++] = con.getRuntimeDelegate();
         }
      }

      return cons;
   }

   public synchronized long getConversationsCurrentCount() {
      return this.conversationsCurrentCount;
   }

   public synchronized long getConversationsHighCount() {
      return this.conversationsHighCount;
   }

   public synchronized long getConversationsTotalCount() {
      return this.conversationsTotalCount;
   }

   public synchronized long getFailedMessagesTotal() {
      return this.failedMessagesTotal;
   }

   public synchronized void pauseIncoming() {
      if (!this.isPausedForIncoming) {
         this.isPaused = this.isPausedForIncoming = true;
         this.unadvertise();
      }
   }

   public synchronized void resumeIncoming() {
      if (this.isPausedForIncoming) {
         this.isPaused = this.isPausedForIncoming = false;
         this.advertise();
      }
   }

   public synchronized boolean isPausedForIncoming() {
      return this.isPausedForIncoming;
   }

   public void pauseForwarding() throws SAFException {
      Iterator itr;
      synchronized(this) {
         if (this.isPausedForForwarding) {
            return;
         }

         this.isPausedForForwarding = true;
         itr = ((HashMap)this.conversations.clone()).values().iterator();
      }

      while(itr.hasNext()) {
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': about to pause one conversation");
         }

         try {
            ((ConversationAssembler)itr.next()).pauseReader();
         } catch (KernelException var4) {
            throw new SAFException("SAF Agent '" + this.name + "': failed to resume forwarding", var4);
         }
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("SAF Agent " + this.getName() + " is paused for forwarding");
      }

   }

   public void resumeForwarding() throws SAFException {
      Iterator itr;
      synchronized(this) {
         if (!this.isPausedForForwarding) {
            return;
         }

         itr = ((HashMap)this.conversations.clone()).values().iterator();
         this.isPausedForForwarding = false;
      }

      while(itr.hasNext()) {
         if (SAFDebug.SAFVerbose.isDebugEnabled() && SAFDebug.SAFSendingAgent.isDebugEnabled()) {
            SAFDebug.SAFSendingAgent.debug("Sending Agent '" + this.name + "': about to resume one reader");
         }

         try {
            ((ConversationAssembler)itr.next()).resumeReader();
         } catch (KernelException var4) {
            throw new SAFException("SAF Agent '" + this.name + "': Failed to resume forwarding", var4);
         }
      }

   }

   public synchronized boolean isPausedForForwarding() {
      return this.isPausedForForwarding;
   }

   synchronized void setDefaultRetryDelayBase(long newValue) {
      this.retryDelayBase = newValue;
   }

   synchronized void setDefaultRetryDelayMaximum(long newValue) {
      this.retryDelayMaximum = newValue;
   }

   synchronized void setDefaultRetryDelayMultiplier(double newValue) {
      this.retryDelayMultiplier = newValue;
   }

   synchronized void setDefaultTimeToLive(long newValue) {
      this.timeToLiveDefault = newValue;
   }

   synchronized void setConversationIdleTimeMaximum(long newVal) {
      this.conversationIdleTimeMaximum = newVal;
   }

   synchronized void setLoggingEnabled(boolean newVal) {
      this.isLoggingEnabled = newVal;
   }

   void setWindowSize(int newVal) {
      this.windowSize = newVal;
   }

   HealthState getHealthState() {
      return this.healthState;
   }

   public AgentImpl makeNewAgent() {
      return new SendingAgentImpl(this.getName());
   }

   private static boolean isNotWSRM(String kernelTopicName) {
      return !kernelTopicName.substring(0, 3).equals("WS:");
   }

   private static String getKernelTopicName(SAFConversationInfo info) {
      int type = info.getDestinationType();
      String url = info.getDestinationURL();
      if (type == 2) {
         return "WS:" + url;
      } else {
         return type == 3 ? "WS_JAXWS:" + url : url;
      }
   }

   Kernel getKernel() {
      return this.kernel;
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("SendingAgent");
      super.dump(imageSource, xsw);
      xsw.writeAttribute("failedMessagesTotal", String.valueOf(this.failedMessagesTotal));
      xsw.writeAttribute("retryDelayMultiplier", String.valueOf(this.retryDelayMultiplier));
      xsw.writeAttribute("retryDelayBase", String.valueOf(this.retryDelayBase));
      xsw.writeAttribute("retryDelayMaximum", String.valueOf(this.retryDelayMaximum));
      xsw.writeAttribute("timeToLiveDefault", String.valueOf(this.timeToLiveDefault));
      xsw.writeAttribute("conversationIdleTimeMaximum", String.valueOf(this.conversationIdleTimeMaximum));
      xsw.writeAttribute("isLoggingEnabled", String.valueOf(this.isLoggingEnabled));
      xsw.writeAttribute("windowSize", String.valueOf(this.windowSize));
      xsw.writeAttribute("isPausedForIncoming", String.valueOf(this.isPausedForIncoming));
      xsw.writeAttribute("isPausedForForwarding", String.valueOf(this.isPausedForForwarding));
      SAFDiagnosticImageSource.dumpHealthStateElement(xsw, this.getAgentAdmin().getHealthState());
      xsw.writeStartElement("DynamicNames");
      HashMap namesCopy = (HashMap)this.dynamicNameToName.clone();
      xsw.writeAttribute("count", String.valueOf(namesCopy.size()));
      Iterator it = namesCopy.keySet().iterator();

      while(it.hasNext()) {
         String dynamicName = (String)it.next();
         xsw.writeStartElement("DynamicNameToName");
         xsw.writeAttribute("dynamicName", dynamicName);
         xsw.writeAttribute("name", (String)namesCopy.get(dynamicName));
         xsw.writeEndElement();
      }

      xsw.writeEndElement();
      xsw.writeStartElement("ConversationAssemblers");
      Object[] assemblers = this.conversations.values().toArray();
      xsw.writeAttribute("count", String.valueOf(assemblers.length));

      for(int i = 0; i < assemblers.length; ++i) {
         ConversationAssembler ca = (ConversationAssembler)assemblers[i];
         ca.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      ((KernelImpl)this.kernel).dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
   }

   class ServerStateChangeListener implements PropertyChangeListener {
      public void propertyChange(PropertyChangeEvent evt) {
         if ("State".equals(evt.getPropertyName()) && "RUNNING".equals(evt.getNewValue())) {
            synchronized(SendingAgentImpl.this) {
               Iterator itr = SendingAgentImpl.this.conversations.values().iterator();

               while(itr.hasNext()) {
                  ConversationAssembler con = (ConversationAssembler)itr.next();
                  con.startFromADiffThread();
               }
            }
         }

      }
   }
}
