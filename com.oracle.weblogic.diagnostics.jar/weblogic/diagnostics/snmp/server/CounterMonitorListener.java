package weblogic.diagnostics.snmp.server;

import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;

public class CounterMonitorListener extends JMXMonitorListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private long threshold;
   private long offset;
   private long modulus;

   public CounterMonitorListener(JMXMonitorLifecycle lifecycle, SNMPAgent agent, String name, String type, String server, String location, String attribute, long threshold, long offset, long modulus) throws MalformedObjectNameException {
      super(lifecycle, agent, name, type, server, location, attribute);
      this.threshold = threshold;
      this.offset = offset;
      this.modulus = modulus;
   }

   public boolean isNotificationEnabled(Notification arg0) {
      return true;
   }

   long getModulus() {
      return this.modulus;
   }

   long getOffset() {
      return this.offset;
   }

   long getThreshold() {
      return this.threshold;
   }

   void updateMonitorTrapCount() {
      if (this.snmpStats != null) {
         this.snmpStats.incrementCounterMonitorTrapCount();
      }

   }
}
