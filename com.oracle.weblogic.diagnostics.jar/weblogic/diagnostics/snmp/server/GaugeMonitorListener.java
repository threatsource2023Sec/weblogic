package weblogic.diagnostics.snmp.server;

import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;

public class GaugeMonitorListener extends JMXMonitorListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private double lowThreshold;
   private double highThreshold;

   public GaugeMonitorListener(JMXMonitorLifecycle lifecycle, SNMPAgent agent, String name, String type, String server, String location, String attribute, double lowThreshold, double highThreshold) throws MalformedObjectNameException {
      super(lifecycle, agent, name, type, server, location, attribute);
      this.lowThreshold = lowThreshold;
      this.highThreshold = highThreshold;
   }

   public boolean isNotificationEnabled(Notification arg0) {
      return true;
   }

   double getHighThreshold() {
      return this.highThreshold;
   }

   double getLowThreshold() {
      return this.lowThreshold;
   }

   void updateMonitorTrapCount() {
      if (this.snmpStats != null) {
         this.snmpStats.incrementGaugeMonitorTrapCount();
      }

   }
}
