package weblogic.messaging.saf.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import weblogic.messaging.saf.common.SAFDebug;

public final class SAFAgentFactoryInternal {
   private final List agentList = new ArrayList();
   private final Random random = new Random();
   private int agentIndx = -2;

   public synchronized void addAgent(AgentImpl agentImpl) {
      if (!this.agentList.contains(agentImpl)) {
         this.agentList.add(agentImpl);
      }

   }

   public synchronized void removeAgent(AgentImpl agentImpl) {
      int index = this.agentList.indexOf(agentImpl);
      if (index != -1) {
         this.agentList.remove(agentImpl);
         if (this.agentIndx >= index) {
            --this.agentIndx;
         }

      }
   }

   public AgentImpl getAgentImpl(String desiredStore) {
      AgentImpl agent = null;
      synchronized(this) {
         Object agentListUse;
         if (desiredStore != null) {
            agentListUse = new ArrayList();
            Iterator var5 = this.agentList.iterator();

            while(var5.hasNext()) {
               Object obj = var5.next();
               AgentImpl agent2 = (AgentImpl)obj;
               if (agent2.getStoreName().equals(desiredStore)) {
                  ((List)agentListUse).add(agent2);
               }
            }
         } else {
            agentListUse = this.agentList;
         }

         int numAgents = ((List)agentListUse).size();
         if (numAgents == 0) {
            return null;
         }

         if (this.agentIndx == -2) {
            this.agentIndx = this.random.nextInt(numAgents);
         } else {
            ++this.agentIndx;
         }

         this.agentIndx %= numAgents;
         if (this.agentIndx > numAgents - 1) {
            this.agentIndx = numAgents - 1;
         }

         agent = (AgentImpl)((List)agentListUse).get(this.agentIndx);
      }

      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && SAFDebug.SAFReceivingAgent.isDebugEnabled()) {
         SAFDebug.SAFSendingAgent.debug("SAFAgentFactory.getSAFAgent = " + agent);
      }

      return agent;
   }

   synchronized boolean haveSendingAgentAvailable() {
      return this.agentList.size() != 0;
   }
}
