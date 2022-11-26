package weblogic.messaging.saf.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.store.SAFStore;
import weblogic.messaging.saf.store.SAFStoreException;
import weblogic.messaging.saf.utils.Util;
import weblogic.store.common.PartitionNameUtils;
import weblogic.work.WorkManager;

public abstract class AgentImpl implements Agent, Externalizable {
   static final long serialVersionUID = -8897468130390722827L;
   private static final String AGENT_WM_NAME_PREFIX = "weblogic.saf.agent.";
   protected String name;
   protected SAFStore store;
   protected SAFAgentAdmin agentAdmin;
   private int agentType;
   private static final byte EXTVERSION1 = 1;
   protected static final int _SENDINGAGENTTYPE = 1;
   protected static final int _RECEIVINGAGENTTYPE = 2;
   protected static final int STATE_CLOSED = 1;
   protected static final int STATE_STARTING = 2;
   protected static final int STATE_STARTED = 4;
   protected static final int STATE_CLOSING = 8;
   protected static final SAFManagerImpl safManager = (SAFManagerImpl)SAFManagerImpl.getManager();
   protected HashMap conversationInfosFromStore = new HashMap();
   protected int state;
   protected int waitersCount;
   protected boolean isPaused;
   private WorkManager workManager;
   protected HealthState healthState = new HealthState(0);
   private static final int _VERSIONMASK = 255;
   private static final int _AGENTTYPESHIFT = 8;
   private static final int _AGENTTYPEMASK = 65280;

   public AgentImpl() {
   }

   AgentImpl(String name, SAFAgentAdmin agentAdmin, SAFStore store, int agentType) throws SAFException {
      this.agentType = agentType;
      this.init(name, agentAdmin, store);
      this.addAgentToStore(store);
   }

   void init(String name, SAFAgentAdmin agentAdmin, SAFStore store) throws SAFException {
      this.name = name;
      this.agentAdmin = agentAdmin;
      this.store = store;
      this.init(agentAdmin.getMBean());
   }

   public void init(SAFAgentMBean mbean) throws SAFException {
      this.startInitialize(mbean);
      this.workManager = Util.findOrCreateWorkManager("weblogic.saf.agent." + PartitionNameUtils.stripDecoratedPartitionName(mbean.getName()) + (this.agentType == 1 ? "Sender" : "Receiver"), -1, 1, -1);

      try {
         this.start();
         this.advertise();
      } catch (SAFException var3) {
         this.printDebug(var3.getMessage());
         throw var3;
      }
   }

   protected abstract void addToAgentFactory();

   protected abstract void removeFromAgentFactory();

   protected final void advertise() {
      if (!this.isPaused) {
         this.addToAgentFactory();
      }
   }

   protected final void unadvertise() {
      this.removeFromAgentFactory();
   }

   private void addAgentToStore(SAFStore store) throws SAFStoreException {
      store.addAgent(this);
      this.printDebug(" Agent = " + this.name + " has been added to SAFStore " + store);
   }

   public final String getName() {
      return this.name;
   }

   public final String getStoreName() {
      return this.store.getEffectiveStoreName();
   }

   public final SAFAgentAdmin getAgentAdmin() {
      return this.agentAdmin;
   }

   protected static final SAFManagerImpl getSAFManager() {
      return safManager;
   }

   protected abstract void start() throws SAFException;

   protected abstract void startInitialize(SAFAgentMBean var1) throws SAFException;

   protected void waitForState(int statesWaitOn) throws SAFException {
      while((this.state & statesWaitOn) == 0) {
         ++this.waitersCount;

         try {
            this.wait();
         } catch (InterruptedException var6) {
            throw new SAFException(var6);
         } finally {
            --this.waitersCount;
         }
      }

   }

   private void printDebug(String str) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && this.agentType == 1) {
         SAFDebug.SAFSendingAgent.debug(str);
      }

      if (SAFDebug.SAFReceivingAgent.isDebugEnabled() && this.agentType == 2) {
         SAFDebug.SAFReceivingAgent.debug(str);
      }

   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 0;
      int peerVersion = 1;
      flags |= peerVersion;
      flags |= this.agentType << 8;
      out.writeInt(flags);
      out.writeUTF(this.name);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      this.agentType = (flags & '\uff00') >>> 8;
      byte vrsn = (byte)(flags & 255);
      if (vrsn != 1) {
         throw new IOException(" unknown version of the object");
      } else {
         this.name = in.readUTF();
      }
   }

   void storeConversationInfo(SAFConversationInfo conversationInfo) throws SAFStoreException {
      this.store.addConversationInfo(conversationInfo);
      synchronized(this.conversationInfosFromStore) {
         this.conversationInfosFromStore.put(conversationInfo.getConversationName(), conversationInfo);
      }
   }

   public void setConversationInfosFromStore(HashMap conversationInfosFromStore) {
      this.conversationInfosFromStore = conversationInfosFromStore;
   }

   public HashMap getConversationInfosFromStore() {
      return this.conversationInfosFromStore;
   }

   static HealthState updateHealthState(HealthState oldState, int state, Symptom symptom) {
      HealthState healthState;
      if (oldState.getState() == state) {
         Symptom[] oldSymptoms = oldState.getSymptoms();
         Symptom[] newSymptoms = new Symptom[oldSymptoms.length + 1];
         System.arraycopy(oldSymptoms, 0, newSymptoms, 0, oldSymptoms.length);
         newSymptoms[oldSymptoms.length] = symptom;
         healthState = new HealthState(state, newSymptoms);
      } else {
         healthState = new HealthState(state, symptom);
      }

      return healthState;
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeAttribute("name", this.name);
      String storeName = this.store.getStoreName();
      xsw.writeAttribute("storeName", storeName != null ? storeName : "");
      xsw.writeAttribute("conversationsCurrentCount", String.valueOf(this.agentAdmin.getConversationsCurrentCount()));
      xsw.writeAttribute("conversationsHighCount", String.valueOf(this.agentAdmin.getConversationsHighCount()));
      xsw.writeAttribute("conversationsTotalCount", String.valueOf(this.agentAdmin.getConversationsTotalCount()));
   }

   public AgentImpl makeNewAgent() {
      return null;
   }
}
