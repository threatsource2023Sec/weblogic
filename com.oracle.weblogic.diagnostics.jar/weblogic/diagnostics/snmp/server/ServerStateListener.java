package weblogic.diagnostics.snmp.server;

import java.util.HashSet;
import java.util.Set;
import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;

class ServerStateListener extends JMXMonitorListener {
   private static final String STATE_ATTRIBUTE = "State";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static Set shutDownStates = new HashSet();
   private String serverName;
   private boolean serverStarted;
   private boolean serverShutdown;

   private static void ensureShutdownStatesInitialized() {
      if (shutDownStates.isEmpty()) {
         shutDownStates.add("SHUTTING_DOWN");
         shutDownStates.add("FORCE_SHUTTING_DOWN");
         shutDownStates.add("SHUTDOWN");
         shutDownStates.add("UNKNOWN");
      }

   }

   public ServerStateListener(JMXMonitorLifecycle lifecycle, String serverName, SNMPAgent snmpAgent) {
      super(lifecycle, snmpAgent);
      this.serverName = serverName;
      ensureShutdownStatesInitialized();
   }

   public boolean isNotificationEnabled(Notification arg0) {
      if (arg0 instanceof AttributeChangeNotification) {
         AttributeChangeNotification acn = (AttributeChangeNotification)arg0;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Got AttributeChangeNotification from " + acn.getSource());
         }

         String attr = acn.getAttributeName();
         if (attr.equals("State")) {
            return true;
         }
      }

      return false;
   }

   public void handleNotification(Notification arg0, Object arg1) {
      if (arg0 instanceof AttributeChangeNotification) {
         AttributeChangeNotification acn = (AttributeChangeNotification)arg0;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Got AttributeChangeNotification from " + acn.getSource());
         }

         Object newValue = acn.getNewValue();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Server state = " + newValue + " for " + this.serverName);
         }

         if (newValue.equals("RUNNING")) {
            try {
               this.monitorLifecycle.serverStarted(this.serverName);
               if (this.snmpAgent.isAutomaticTrapsEnabled()) {
                  this.sendServerStartNotification();
               }
            } catch (Exception var7) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Exception sending server start trap", var7);
               }
            }
         } else if (this.isShutdownState(newValue)) {
            try {
               this.monitorLifecycle.serverStopped(this.serverName);
               if (this.snmpAgent.isAutomaticTrapsEnabled()) {
                  this.serverServerShutdownNotification();
               }
            } catch (Throwable var6) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Exception sending server start trap", var6);
               }
            }
         }
      }

   }

   void updateMonitorTrapCount() {
   }

   private boolean isShutdownState(Object newValue) {
      return shutDownStates.contains(newValue);
   }

   private void sendServerStartNotification() throws SNMPAgentToolkitException {
      if (!this.serverStarted) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Sending serverStart trap for " + this.serverName);
         }

         SNMPRuntimeStats rt = this.getSNMPRuntimeStats();
         if (rt != null) {
            rt.incrementServerStartTrapCount();
         }

         ServerStateTrapUtil.sendServerLifecycleNotification(this.snmpAgent, this.serverName, "wlsServerStart");
         this.serverStarted = true;
         this.serverShutdown = false;
      }

   }

   private void serverServerShutdownNotification() throws SNMPAgentToolkitException {
      if (!this.serverShutdown) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Sending serverStop trap for " + this.serverName);
         }

         SNMPRuntimeStats rt = this.getSNMPRuntimeStats();
         if (rt != null) {
            rt.incrementServerStopTrapCount();
         }

         ServerStateTrapUtil.sendServerLifecycleNotification(this.snmpAgent, this.serverName, "wlsServerShutDown");
         this.serverShutdown = true;
         this.serverStarted = false;
      }

   }
}
