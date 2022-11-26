package weblogic.messaging.saf.internal;

import java.io.Externalizable;
import java.util.HashMap;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFEndpoint;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFManager;
import weblogic.messaging.saf.SAFServiceNotAvailException;
import weblogic.messaging.saf.common.AgentDeliverRequest;
import weblogic.messaging.saf.common.AgentDeliverResponse;
import weblogic.messaging.saf.common.SAFConversationInfoImpl;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.store.SAFStore;

public final class ReceivingAgentImpl extends AgentImpl implements ReceivingAgent, Externalizable {
   static final long serialVersionUID = -5246833642083492445L;
   private final HashMap conversations = new HashMap();
   private int windowSize;
   private long defaultTimeToLive = Long.MAX_VALUE;
   private long conversationIdleTimeMaximum = Long.MAX_VALUE;
   private boolean started;
   private boolean isPausedForReceiving;
   private long ackInterval;
   private static final SAFAgentFactoryInternal agentFactoryInternal = new SAFAgentFactoryInternal();
   private static final SAFManager manager = SAFManagerImpl.getManager();

   public ReceivingAgentImpl() {
   }

   private ReceivingAgentImpl(String name) {
      this.name = name;
   }

   ReceivingAgentImpl(String name, SAFAgentAdmin agentAdmin, SAFStore store) throws NamingException, SAFException {
      super(name, agentAdmin, store, 2);
      if (SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Receiving Agent '" + name + "': TimeToLive=" + this.defaultTimeToLive + ", ConversationIdleTimeMaximum=" + this.conversationIdleTimeMaximum + ", WindowSize=" + this.windowSize);
      }

   }

   protected void startInitialize(SAFAgentMBean mbean) {
      this.defaultTimeToLive = mbean.getDefaultTimeToLive();
      this.conversationIdleTimeMaximum = mbean.getConversationIdleTimeMaximum();
      if (this.defaultTimeToLive == 0L) {
         this.defaultTimeToLive = Long.MAX_VALUE;
      }

      if (this.conversationIdleTimeMaximum == 0L) {
         this.conversationIdleTimeMaximum = Long.MAX_VALUE;
      }

      this.windowSize = mbean.getWindowSize();
      this.ackInterval = mbean.getAcknowledgeInterval();
      this.isPaused = this.isPausedForReceiving = mbean.isReceivingPausedAtStartup();
   }

   protected void addToAgentFactory() {
      safManager.addLocalReceivingAgent(this);
      agentFactoryInternal.addAgent(this);
   }

   protected void removeFromAgentFactory() {
      agentFactoryInternal.removeAgent(this);
      safManager.removeLocalReceivingAgent(this);
   }

   static SAFAgentFactoryInternal getAgentFactory() {
      return agentFactoryInternal;
   }

   protected void start() throws SAFException {
      synchronized(this) {
         if (this.started) {
            return;
         }
      }

      Iterator itr;
      synchronized(this.conversationInfosFromStore) {
         itr = ((HashMap)this.conversationInfosFromStore.clone()).values().iterator();
      }

      if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Receiving anget '" + this.getName() + "' recovered " + this.conversationInfosFromStore.size() + " conversations from the store");
      }

      while(itr.hasNext()) {
         SAFConversationInfo info = (SAFConversationInfo)itr.next();

         try {
            this.createConversation(info);
         } catch (SAFException var7) {
            Symptom symptom = new Symptom(SymptomType.SAF_RECEIVE_ERROR, Severity.HIGH, this.name, var7.getMessage());
            this.healthState = updateHealthState(this.healthState, 3, symptom);
            throw var7;
         }
      }

      synchronized(this) {
         this.started = true;
      }
   }

   int getWindowSize() {
      return this.windowSize;
   }

   long getAckInterval() {
      return this.ackInterval;
   }

   public AgentDeliverResponse deliver(AgentDeliverRequest arequest) throws SAFException {
      if (this.isPausedForReceiving()) {
         if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
            SAFDebug.SAFReceivingAgent.debug(" [ReceivingAgent.deliver()]: the agent is paused.");
         }

         throw new SAFServiceNotAvailException("ReceivingAgent " + this + " is paused.");
      } else {
         return arequest.finishDeliver(this);
      }
   }

   public void suspend(boolean force) {
      this.close(false);
   }

   public void resume() throws SAFException {
      this.start();
   }

   public synchronized void close(boolean destroy) {
      this.closeInternal(destroy);
   }

   private void closeInternal(boolean destroy) {
      this.unadvertise();
      Iterator itr = this.conversations.values().iterator();

      while(itr.hasNext()) {
         try {
            ((ConversationReassembler)itr.next()).close(destroy);
         } catch (SAFException var4) {
            if (SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
               var4.printStackTrace();
            }
         }
      }

      this.conversations.clear();
      this.store.close();
   }

   public long getDefaultTimeToLive() {
      return this.defaultTimeToLive;
   }

   private void createConversation(SAFConversationInfo info) throws SAFException {
      this.createConversation(info, false);
   }

   ConversationReassembler createConversation(SAFConversationInfo info, boolean establishSASAFConnection) throws SAFException {
      if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFReceivingAgent.debug("Receiving Agent '" + this.name + "': about to create conversation " + info);
      }

      if (info == null) {
         return null;
      } else {
         boolean needRegisterOffer = false;
         SAFConversationInfo offer = null;
         ConversationReassembler con;
         synchronized(this) {
            con = this.getConversation(info);
            if (con == null) {
               SAFEndpoint destination = safManager.getEndpointManager(info.getDestinationType()).getEndpoint(info.getDestinationURL());
               ((SAFConversationInfoImpl)info).setTimestamp(System.currentTimeMillis());
               safManager.addConversationInfoOnReceivingSide(info);
               con = new ConversationReassembler(this, info.getTransportType(), destination, info, this.store, establishSASAFConnection);
               if (establishSASAFConnection && (offer = info.getConversationOffer()) != null) {
                  needRegisterOffer = true;
               }

               this.conversations.put(info, con);
               this.storeConversationInfo(info);
               if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && SAFDebug.SAFVerbose.isDebugEnabled()) {
                  SAFDebug.SAFReceivingAgent.debug("Receiving Agent '" + this.name + "' after created conversation = " + con);
               }
            }
         }

         if (needRegisterOffer) {
            manager.registerConversationOnSendingSide(offer, (SAFManager.ConversationNameRefinementCallback)null);
         }

         return con;
      }
   }

   public synchronized ConversationReassembler getConversation(SAFConversationInfo info) {
      return (ConversationReassembler)this.conversations.get(info);
   }

   public void removeConversation(SAFConversationInfo info) throws SAFException {
      ConversationReassembler conversationReAssembler;
      synchronized(this) {
         conversationReAssembler = this.getConversation(info);
      }

      if (conversationReAssembler != null) {
         conversationReAssembler.finishConversation();
      }

      synchronized(this) {
         this.conversations.remove(info);
         this.conversationInfosFromStore.remove(info.getConversationName());
      }

      if (conversationReAssembler == null) {
         throw new SAFException("ConversationReassembler Not Found for conversation: " + info.getConversationName());
      }
   }

   public synchronized void pauseReceiving() {
      if (!this.isPausedForReceiving) {
         this.isPaused = this.isPausedForReceiving = true;
         this.unadvertise();
      }
   }

   public synchronized void resumeReceiving() {
      if (this.isPausedForReceiving) {
         this.isPaused = this.isPausedForReceiving = false;
         this.advertise();
      }
   }

   public synchronized boolean isPausedForReceiving() {
      return this.isPausedForReceiving;
   }

   void setWindowSize(int newVal) {
      this.windowSize = newVal;
   }

   synchronized void setDefaultTimeToLive(long newVal) {
      if (this.defaultTimeToLive == 0L) {
         this.defaultTimeToLive = Long.MAX_VALUE;
      } else {
         this.defaultTimeToLive = newVal;
      }

   }

   synchronized void setAcknowledgementInterval(long newVal) {
      this.ackInterval = newVal;
   }

   synchronized void setConversationIdleTimeMaximum(long newVal) {
      if (this.conversationIdleTimeMaximum == 0L) {
         this.conversationIdleTimeMaximum = Long.MAX_VALUE;
      } else {
         this.conversationIdleTimeMaximum = newVal;
      }

   }

   synchronized long getConversationIdleTimeMaximum() {
      return this.conversationIdleTimeMaximum;
   }

   HealthState getHealthState() {
      return this.healthState;
   }

   public AgentImpl makeNewAgent() {
      return new ReceivingAgentImpl(this.getName());
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("ReceivingAgent");
      super.dump(imageSource, xsw);
      xsw.writeAttribute("windowSize", String.valueOf(this.windowSize));
      xsw.writeAttribute("defaultTimeToLive", String.valueOf(this.defaultTimeToLive));
      xsw.writeAttribute("conversationIdleTimeMaximum", String.valueOf(this.conversationIdleTimeMaximum));
      xsw.writeAttribute("started", String.valueOf(this.started));
      xsw.writeAttribute("isPausedForReceiving", String.valueOf(this.isPausedForReceiving));
      xsw.writeAttribute("ackInterval", String.valueOf(this.ackInterval));
      xsw.writeAttribute("idleTimeMaximum", String.valueOf(this.conversationIdleTimeMaximum));
      SAFDiagnosticImageSource.dumpHealthStateElement(xsw, this.getAgentAdmin().getHealthState());
      xsw.writeStartElement("ConversationReassemblers");
      Object[] reassemblers = this.conversations.values().toArray();
      xsw.writeAttribute("count", String.valueOf(reassemblers.length));

      for(int i = 0; i < reassemblers.length; ++i) {
         ConversationReassembler cr = (ConversationReassembler)reassemblers[i];
         cr.dump(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }
}
