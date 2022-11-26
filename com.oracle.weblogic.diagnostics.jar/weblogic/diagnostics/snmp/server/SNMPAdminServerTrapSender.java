package weblogic.diagnostics.snmp.server;

import java.rmi.RemoteException;
import java.util.List;
import weblogic.diagnostics.snmp.agent.SNMPTrapException;
import weblogic.diagnostics.snmp.agent.SNMPTrapSender;
import weblogic.logging.DomainLogBroadcasterClient;

public class SNMPAdminServerTrapSender implements SNMPTrapSender {
   public void sendTrap(String trapName, List varBindings) throws SNMPTrapException {
      DomainLogBroadcasterClient client = DomainLogBroadcasterClient.getInstance();

      try {
         client.sendTrap(trapName, varBindings);
      } catch (RemoteException var5) {
         throw new SNMPTrapException(var5);
      }
   }
}
