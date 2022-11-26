package weblogic.diagnostics.snmp.server;

import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SNMPAgentRuntimeMBean;

public class SNMPAgentRuntime extends RuntimeMBeanDelegate implements SNMPAgentRuntimeMBean {
   private SNMPRuntimeStats snmpRuntimeStats;
   private String snmpAgentName;

   public SNMPAgentRuntime(RuntimeMBean parent, SNMPRuntimeStats stats) throws ManagementException {
      super(parent.getName(), parent);
      this.snmpRuntimeStats = stats;
   }

   public long getAttributeChangeTrapCount() {
      return this.snmpRuntimeStats.getAttributeChangeTrapCount();
   }

   public long getCounterMonitorTrapCount() {
      return this.snmpRuntimeStats.getCounterMonitorTrapCount();
   }

   public long getGaugeMonitorTrapCount() {
      return this.snmpRuntimeStats.getGaugeMonitorTrapCount();
   }

   public long getLogMessageTrapCount() {
      return this.snmpRuntimeStats.getLogMessageTrapCount();
   }

   public long getMonitorTrapCount() {
      return this.snmpRuntimeStats.getMonitorTrapCount();
   }

   public boolean isRunning() {
      return this.snmpRuntimeStats.isRunning();
   }

   public long getStringMonitorTrapCount() {
      return this.snmpRuntimeStats.getStringMonitorTrapCount();
   }

   public long getServerStartTrapCount() {
      return this.snmpRuntimeStats.getServerStartTrapCount();
   }

   public long getServerStopTrapCount() {
      return this.snmpRuntimeStats.getServerStopTrapCount();
   }

   public String getSNMPAgentListenAddress() {
      return this.snmpRuntimeStats.getSNMPAgentListenAddress();
   }

   public int getUDPListenPort() {
      return this.snmpRuntimeStats.getSNMPAgentUDPPort();
   }

   public String getMasterAgentXListenAddress() {
      return this.snmpRuntimeStats.getMasterAgentXListenAddress();
   }

   public int getMasterAgentXPort() {
      return this.snmpRuntimeStats.getMasterAgentXPort();
   }

   public String outputCustomMBeansMIBModule() throws ManagementException {
      try {
         return this.snmpRuntimeStats.getCustomMBeansSubAgentMIB();
      } catch (SNMPAgentToolkitException var3) {
         ManagementException me = new ManagementException(var3.getMessage());
         me.setStackTrace(var3.getStackTrace());
         throw me;
      }
   }

   public int getFailedAuthenticationCount() {
      return this.snmpRuntimeStats.getFailedAuthenticationCount();
   }

   public int getFailedAuthorizationCount() {
      return this.snmpRuntimeStats.getFailedAuthorizationCount();
   }

   public int getFailedEncryptionCount() {
      return this.snmpRuntimeStats.getFailedEncryptionCount();
   }

   public void invalidateLocalizedKeyCache(String username) {
      this.snmpRuntimeStats.invalidateLocalizedKeyCache(username);
   }

   SNMPRuntimeStats getSNMPRuntimeStats() {
      return this.snmpRuntimeStats;
   }

   void setSNMPRuntimeStats(SNMPRuntimeStats snmpRuntimeStats) {
      this.snmpRuntimeStats = snmpRuntimeStats;
   }

   public String getSNMPAgentName() {
      return this.snmpRuntimeStats.getSNMPAgentName();
   }
}
