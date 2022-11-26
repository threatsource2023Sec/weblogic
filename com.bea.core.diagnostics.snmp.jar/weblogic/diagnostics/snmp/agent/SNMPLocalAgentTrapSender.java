package weblogic.diagnostics.snmp.agent;

import java.util.List;

public class SNMPLocalAgentTrapSender implements SNMPTrapSender {
   private SNMPAgent snmpAgent;

   public SNMPLocalAgentTrapSender(SNMPAgent snmpAgent) {
      this.snmpAgent = snmpAgent;
   }

   public void sendTrap(String trapName, List varBindings) throws SNMPTrapException {
      SNMPNotificationManager notifier = this.snmpAgent.getSNMPAgentToolkit().getSNMPNotificationManager();
      String notifyGroup = this.snmpAgent.getNotifyGroup();

      try {
         notifier.sendNotification(notifyGroup, trapName, varBindings);
      } catch (SNMPAgentToolkitException var6) {
         throw new SNMPTrapException(var6);
      }
   }
}
