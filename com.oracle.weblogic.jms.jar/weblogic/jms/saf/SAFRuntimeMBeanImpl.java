package weblogic.jms.saf;

import java.util.ArrayList;
import java.util.HashMap;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFAgentRuntimeMBean;
import weblogic.management.runtime.SAFRuntimeMBean;

public class SAFRuntimeMBeanImpl extends RuntimeMBeanDelegate implements SAFRuntimeMBean {
   private final HashMap agents = new HashMap();

   SAFRuntimeMBeanImpl(String name) throws ManagementException {
      super(name, true);
   }

   public synchronized void addAgent(SAFAgentRuntimeMBeanAggregator agent) {
      this.agents.put(agent.getDecoratedName(), agent);
   }

   public synchronized void removeAgent(SAFAgentRuntimeMBeanAggregator agent) {
      this.agents.remove(agent.getDecoratedName());
   }

   public synchronized SAFAgentRuntimeMBeanAggregator getAgent(String name) {
      return (SAFAgentRuntimeMBeanAggregator)this.agents.get(name);
   }

   public synchronized HealthState getHealthState() {
      int health = 0;
      ArrayList symptoms = new ArrayList();
      SAFAgentRuntimeMBean[] agentArray = this.getAgents();

      for(int i = 0; i < agentArray.length; ++i) {
         HealthState healthState = agentArray[i].getHealthState();
         health = Math.max(healthState.getState(), health);
         String[] codes = healthState.getReasonCode();
         Symptom[] var7 = healthState.getSymptoms();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Symptom symptom = var7[var9];
            symptoms.add(symptom);
         }
      }

      return new HealthState(health, (Symptom[])symptoms.toArray(new Symptom[symptoms.size()]));
   }

   public synchronized SAFAgentRuntimeMBean[] getAgents() {
      SAFAgentRuntimeMBean[] ret = new SAFAgentRuntimeMBean[this.agents.size()];
      return (SAFAgentRuntimeMBean[])((SAFAgentRuntimeMBean[])this.agents.values().toArray(ret));
   }
}
