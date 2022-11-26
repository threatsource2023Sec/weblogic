package weblogic.diagnostics.snmp.agent.monfox;

import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.proxy.SnmpProxySubtreeTable;
import monfox.toolkit.snmp.agent.target.SnmpTargetAddrTable;
import monfox.toolkit.snmp.agent.target.SnmpTargetParamsTable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPProxyManager;

public class ProxyManager implements SNMPProxyManager {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private SnmpAgent snmpAgent;

   public ProxyManager(SnmpAgent agent) {
      this.snmpAgent = agent;
      this.snmpAgent.getProxyForwarder().isSubtreeProxyBypassModeEnabled(true);
   }

   public void addProxyAgent(String proxyName, String host, int port, String oidRoot, String community, String secName, int secLevel, long timeoutMillis) throws SNMPAgentToolkitException {
      try {
         community = community == null ? "" : community;
         secName = secName == null ? "" : secName;
         SnmpProxySubtreeTable subtree_table = this.snmpAgent.getProxyForwarder().getProxySubtreeTable();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Adding entry to proxy subtree table " + oidRoot);
         }

         subtree_table.add(proxyName, oidRoot, proxyName);
         SnmpTargetAddrTable addrTable = this.snmpAgent.getTarget().getAddrTable();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Adding entry to address table " + host + ":" + port);
         }

         addrTable.add(proxyName, host, port, (int)(timeoutMillis / 10L), 1, "", proxyName);
         SnmpTargetParamsTable paramsTable = this.snmpAgent.getTarget().getParamsTable();
         if (secName != null && secName.length() > 0) {
            int monfoxSecurityLevel = SecurityUtil.convertSNMPAgentToolkitSecurityLevel(secLevel);
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Adding v3 entry to params table secName:" + secName + ", secLevel: " + secLevel);
            }

            paramsTable.addV3(proxyName, secName, monfoxSecurityLevel);
         } else if (community.length() > 0) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Adding v1 entry to params table community:" + community);
            }

            paramsTable.addV1(proxyName, community);
         }

      } catch (Throwable var14) {
         throw new SNMPAgentToolkitException(var14);
      }
   }

   public void removeProxyAgent(String proxyName) throws SNMPAgentToolkitException {
      try {
         SnmpProxySubtreeTable subtree_table = this.snmpAgent.getProxyForwarder().getProxySubtreeTable();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Removing entry from proxy subtree table " + proxyName);
         }

         subtree_table.remove(proxyName);
         SnmpTargetAddrTable addrTable = this.snmpAgent.getTarget().getAddrTable();
         addrTable.remove(proxyName);
         SnmpTargetParamsTable paramsTable = this.snmpAgent.getTarget().getParamsTable();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Removing entry from params table " + proxyName);
         }

         paramsTable.remove(proxyName);
      } catch (Throwable var5) {
         throw new SNMPAgentToolkitException(var5);
      }
   }

   public void cleanup() throws SNMPAgentToolkitException {
      try {
         SnmpProxySubtreeTable subtree_table = this.snmpAgent.getProxyForwarder().getProxySubtreeTable();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Removing entries from proxy subtree table");
         }

         subtree_table.removeAllRows();
         SnmpTargetParamsTable paramsTable = this.snmpAgent.getTarget().getParamsTable();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Removing entries from params table");
         }

         paramsTable.removeAllRows();
      } catch (Throwable var3) {
         throw new SNMPAgentToolkitException(var3);
      }
   }
}
