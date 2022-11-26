package weblogic.diagnostics.snmp.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;

public class ServerStateTrapUtil {
   static final String WLS_START_TRAP = "wlsServerStart";
   static final String WLS_SHUTDOWN_TRAP = "wlsServerShutDown";

   static void sendServerLifecycleNotification(SNMPAgent snmpAgent, String serverName, String trapType) throws SNMPAgentToolkitException {
      SNMPNotificationManager nm = snmpAgent.getSNMPAgentToolkit().getSNMPNotificationManager();
      List varBindList = new LinkedList();
      String trapTime = (new Date()).toString();
      varBindList.add(new Object[]{"trapTime", trapTime});
      varBindList.add(new Object[]{"trapServerName", serverName});
      nm.sendNotification(snmpAgent.getNotifyGroup(), trapType, varBindList);
   }
}
