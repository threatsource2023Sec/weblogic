package weblogic.messaging.saf.store;

import java.util.HashMap;
import weblogic.messaging.kernel.util.Util;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.internal.Agent;
import weblogic.messaging.saf.internal.ReceivingAgent;
import weblogic.messaging.saf.internal.ReceivingAgentImpl;
import weblogic.messaging.saf.internal.SendingAgent;
import weblogic.store.PersistentHandle;

public final class SAFStoreAdvanced extends SAFStore {
   private String alternativeAgentName;
   private String agentInstanceName;
   SAFStore additionalStore = null;
   private static final boolean upgradeOldConnectionsOnly;
   private static final boolean upgradeIgnoreOldConnections;
   private static final boolean forceOldConnections;

   SAFStoreAdvanced(String name, String agentInstanceName, String agentName, boolean isRA, String alternativeAgentName) throws SAFException {
      super(name, agentName);
      this.agentInstanceName = agentInstanceName;
      this.alternativeAgentName = alternativeAgentName;
      this.open(isRA);
      if (SAFDebug.SAFStore.isDebugEnabled()) {
         SAFDebug.SAFStore.debug(" == SAFStore Created for Agent " + agentName);
      }

   }

   private void open(boolean isRA) throws SAFStoreException {
      boolean useAlternativeAgentName = false;
      String effectiveName = this.getAgentName();
      if (this.alternativeAgentName != null) {
         try {
            if (this.getStoreName() != null) {
               this.additionalStore = new SAFStore((String)null, this.getAgentName(), isRA);
            }
         } catch (SAFException var6) {
            throw new SAFStoreException(this, var6);
         }

         if (this.getConnection(this.alternativeAgentName) != null) {
            if (SAFDebug.SAFStore.isDebugEnabled()) {
               SAFDebug.SAFStore.debug("Opening persistent store connections: found pre-12.2.1.0.0 store connections");
            }

            if (this.getConnection(this.getAgentName()) != null || this.additionalStore != null && this.additionalStore.getConnection(this.getAgentName()) != null) {
               if (SAFDebug.SAFStore.isDebugEnabled()) {
                  SAFDebug.SAFStore.debug("Opening persistent store connections: found post-12.2.1.0.0 store connections");
               }

               if (!upgradeIgnoreOldConnections && this.hasConversations(this.alternativeAgentName)) {
                  if (upgradeOldConnectionsOnly) {
                     effectiveName = this.alternativeAgentName;
                     useAlternativeAgentName = true;
                     if (SAFDebug.SAFStore.isDebugEnabled()) {
                        SAFDebug.SAFStore.debug("Opening persistent store connections: forced to use the old store connections");
                     }
                  } else {
                     Util.logSAFUpgradeWarning(this.agentInstanceName);
                  }
               }
            } else {
               if (SAFDebug.SAFStore.isDebugEnabled()) {
                  SAFDebug.SAFStore.debug("Opening persistent store connections: did not find post-12.2.1.0.0 store connections");
               }

               effectiveName = this.alternativeAgentName;
               useAlternativeAgentName = true;
            }

            if (forceOldConnections) {
               effectiveName = this.alternativeAgentName;
               useAlternativeAgentName = true;
               if (SAFDebug.SAFStore.isDebugEnabled()) {
                  SAFDebug.SAFStore.debug("Opening persistent store connections:  forced to use the alternativeName  = " + effectiveName);
               }
            }
         }
      }

      this.open(isRA, effectiveName);
      if (!upgradeOldConnectionsOnly && this.additionalStore != null && this.additionalStore.hasConversations()) {
         if (SAFDebug.SAFStore.isDebugEnabled()) {
            SAFDebug.SAFStore.debug("Found conversations in the default store connections; need to consolidate.");
         }

         this.consolidateAdditionalStoreData(this.additionalStore);
         if (SAFDebug.SAFVerbose.isDebugEnabled()) {
            int numConsSA = this.sendingAgent == null ? 0 : this.sendingAgent.getConversationInfosFromStore().size();
            int numConsRA = this.receivingAgent == null ? 0 : ((ReceivingAgentImpl)this.receivingAgent).getConversationInfosFromStore().size();
            SAFDebug.SAFStore.debug("After consolidation: sendingAgent = " + this.sendingAgent + " has " + numConsSA + " conversations. receivingAgent = " + this.receivingAgent + " has " + numConsRA + " conversations.");
         }
      } else if (this.additionalStore != null) {
         this.additionalStore.close();
         this.additionalStore = null;
      }

   }

   public void close() {
      super.close();
      if (this.additionalStore != null) {
         this.additionalStore.close();
      }

   }

   public void clean() throws SAFStoreException {
      super.clean();
      if (this.additionalStore != null) {
         this.additionalStore.clean();
      }

   }

   public PersistentHandle addConversationInfo(SAFConversationInfo conversationInfo) throws SAFStoreException {
      PersistentHandle handle = super.addConversationInfo(conversationInfo);
      if (handle == null && this.additionalStore != null) {
         synchronized(this.additionalStore.getConversationStoreHandles()) {
            handle = (PersistentHandle)this.additionalStore.getConversationStoreHandles().get(conversationInfo.getConversationName());
            if (handle != null) {
               this.additionalStore.updateSync(handle, conversationInfo);
            }
         }
      }

      return handle;
   }

   public PersistentHandle removeConversationInfo(SAFConversationInfo conversationInfo) throws SAFStoreException {
      PersistentHandle handle = super.removeConversationInfo(conversationInfo);
      if (handle == null && this.additionalStore != null) {
         synchronized(this.additionalStore.getConversationStoreHandles()) {
            handle = (PersistentHandle)this.additionalStore.getConversationStoreHandles().get(conversationInfo.getConversationName());
            if (handle != null) {
               this.additionalStore.delete(handle);
            }
         }
      }

      return handle;
   }

   private void consolidateAdditionalStoreData(SAFStore additionalStore) {
      this.sendingAgent = (SendingAgent)this.consolidateAgent(this.sendingAgent, additionalStore.getSendingAgent());
      this.receivingAgent = (ReceivingAgent)this.consolidateAgent(this.receivingAgent, additionalStore.getReceivingAgent());
      if (additionalStore.getConversationInfos().size() != 0) {
         this.getConversationInfos().putAll(additionalStore.getConversationInfos());
      }

   }

   private Agent consolidateAgent(Agent agent, Agent additionalAgent) {
      if (SAFDebug.SAFVerbose.isDebugEnabled()) {
         int numCons = additionalAgent != null && additionalAgent.getConversationInfosFromStore() != null ? additionalAgent.getConversationInfosFromStore().size() : 0;
         SAFDebug.SAFStore.debug("Before consolidation: additionaStore has Agent = " + additionalAgent + " has " + numCons + " conversations.");
      }

      if (additionalAgent != null) {
         if (agent == null) {
            agent = additionalAgent.makeNewAgent();
         }

         if (additionalAgent.getConversationInfosFromStore().size() != 0) {
            HashMap agentMap = ((Agent)agent).getConversationInfosFromStore();
            agentMap.putAll(additionalAgent.getConversationInfosFromStore());
         }
      }

      return (Agent)agent;
   }

   static {
      String upgradeMode = System.getProperty("weblogic.saf.StoreUpgradeMode");
      upgradeOldConnectionsOnly = upgradeMode != null && "OldOnly".equalsIgnoreCase(upgradeMode);
      upgradeIgnoreOldConnections = upgradeMode != null && "IgnoreOld".equalsIgnoreCase(upgradeMode);
      forceOldConnections = upgradeMode != null && "ForceOld".equalsIgnoreCase(upgradeMode);
   }
}
