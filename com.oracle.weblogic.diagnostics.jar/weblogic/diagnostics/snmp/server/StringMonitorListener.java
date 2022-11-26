package weblogic.diagnostics.snmp.server;

import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;

public class StringMonitorListener extends JMXMonitorListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private String stringToCompare;
   private boolean notifyDiffer;
   private boolean notifyMatch;

   public StringMonitorListener(JMXMonitorLifecycle lifecycle, SNMPAgent agent, String name, String type, String server, String location, String attribute, String stringToCompare, boolean notifyDiffer, boolean notifyMatch) throws MalformedObjectNameException {
      super(lifecycle, agent, name, type, server, location, attribute);
      this.stringToCompare = stringToCompare;
      this.notifyDiffer = notifyDiffer;
      this.notifyMatch = notifyMatch;
   }

   public boolean isNotificationEnabled(Notification arg0) {
      return true;
   }

   boolean isNotifyDiffer() {
      return this.notifyDiffer;
   }

   boolean isNotifyMatch() {
      return this.notifyMatch;
   }

   String getStringToCompare() {
      return this.stringToCompare;
   }

   void updateMonitorTrapCount() {
      if (this.snmpStats != null) {
         this.snmpStats.incrementStringMonitorTrapCount();
      }

   }
}
