package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface SAFRuntimeMBean extends RuntimeMBean, HealthFeedback {
   HealthState getHealthState();

   SAFAgentRuntimeMBean[] getAgents();
}
