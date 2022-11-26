package weblogic.diagnostics.snmp.agent.monfox;

import monfox.toolkit.snmp.agent.target.SnmpTarget;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPTargetManager;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;

public class TargetManager implements SNMPTargetManager {
   private static final int TIMEOUT_FACTOR = 10;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private SnmpTarget snmpTarget;

   public TargetManager(SnmpTarget target) {
      this.snmpTarget = target;
   }

   public void addV1TrapDestination(String name, String host, int port, int timeout, int retryCount, String tagList, String community) throws SNMPAgentToolkitException {
      try {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Adding V1 TrapDestination " + name + " @ " + host + ":" + port);
         }

         this.snmpTarget.addV1(name, host, port, timeout / 10, retryCount, tagList, community);
      } catch (Exception var9) {
         throw new SNMPAgentToolkitException(var9);
      }
   }

   public void addV2TrapDestination(String name, String host, int port, int timeout, int retryCount, String tagList, String community) throws SNMPAgentToolkitException {
      try {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Adding V2 TrapDestination " + name + " @ " + host + ":" + port);
         }

         this.snmpTarget.addV2(name, host, port, timeout / 10, retryCount, tagList, community);
      } catch (Exception var9) {
         throw new SNMPAgentToolkitException(var9);
      }
   }

   public void addV3TrapDestination(String name, String host, int port, int timeout, int retryCount, String tagList, String securityName, int securityLevel) throws SNMPAgentToolkitException {
      if (securityName != null && securityName.length() != 0) {
         try {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Adding V3 TrapDestination " + name + " @ " + host + ":" + port);
            }

            this.snmpTarget.addV3(name, host, port, timeout / 10, retryCount, tagList, securityName, securityLevel);
         } catch (Exception var10) {
            throw new SNMPAgentToolkitException(var10);
         }
      } else {
         String s = SNMPLogger.logSecurityNameNotSpecifiedForV3TrapDestinationLoggable(name).getMessageBody();
         throw new SNMPAgentToolkitException(s);
      }
   }

   public void cleanup() {
      this.snmpTarget.getAddrTable().removeAllRows();
      this.snmpTarget.getParamsTable().removeAllRows();
   }
}
