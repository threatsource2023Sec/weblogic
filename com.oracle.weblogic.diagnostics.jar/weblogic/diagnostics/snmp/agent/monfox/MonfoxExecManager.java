package weblogic.diagnostics.snmp.agent.monfox;

import monfox.toolkit.snmp.util.ExecManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class MonfoxExecManager implements ExecManager {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private WorkManager snmpAgentWorkManager = WorkManagerFactory.getInstance().find("SnmpWorkManager");

   MonfoxExecManager() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("MonFoxExecManager using SNMP WorkManager instance " + this.snmpAgentWorkManager.getName());
      }

   }

   public void schedule(Runnable work) {
      this.snmpAgentWorkManager.schedule(work);
   }
}
