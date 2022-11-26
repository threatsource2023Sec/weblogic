package weblogic.diagnostics.snmp.agent;

import java.util.List;

public interface SNMPNotificationManager {
   String COLD_START_TRAP = "coldStart";

   void addTrapGroup(String var1, String var2) throws SNMPAgentToolkitException;

   void addInformGroup(String var1, String var2) throws SNMPAgentToolkitException;

   void sendNotification(String var1, String var2, List var3) throws SNMPAgentToolkitException;
}
