package weblogic.diagnostics.snmp.server;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPGaugeMonitorMBean;
import weblogic.management.configuration.ServerMBean;

public class GaugeMonitorLifecycle extends JMXMonitorLifecycle {
   public GaugeMonitorLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      super(adminServer, serverName, snmpAgent, mbeanServerConnection);
   }

   void initializeMonitorListenerList(SNMPAgentMBean snmpAgentMBean) throws Exception {
      this.initializeGaugeMonitors(snmpAgentMBean.getSNMPGaugeMonitors());
   }

   private void initializeGaugeMonitors(SNMPGaugeMonitorMBean[] gaugeMonitors) throws Exception {
      if (gaugeMonitors != null) {
         for(int i = 0; i < gaugeMonitors.length; ++i) {
            SNMPGaugeMonitorMBean gm = gaugeMonitors[i];
            GaugeMonitorListener gauge = null;
            ServerMBean[] servers = gm.getEnabledServers();
            int serverCount = servers == null ? 0 : servers.length;
            boolean runtime = gm.getMonitoredMBeanType().endsWith("Runtime");
            if (runtime && this.adminServer && serverCount > 0) {
               for(int j = 0; j < serverCount; ++j) {
                  gauge = new GaugeMonitorListener(this, this.snmpAgent, gm.getMonitoredMBeanName(), gm.getMonitoredMBeanType(), this.serverName, servers[j].getName(), gm.getMonitoredAttributeName(), gm.getThresholdLow(), gm.getThresholdHigh());
                  this.monitorListenerList.add(gauge);
                  gauge.setName(gm.getName());
                  gauge.setPollingIntervalSeconds(gm.getPollingInterval());
               }
            } else {
               gauge = new GaugeMonitorListener(this, this.snmpAgent, gm.getMonitoredMBeanName(), gm.getMonitoredMBeanType(), this.serverName, (String)null, gm.getMonitoredAttributeName(), gm.getThresholdLow(), gm.getThresholdHigh());
               this.monitorListenerList.add(gauge);
               gauge.setName(gm.getName());
               gauge.setPollingIntervalSeconds(gm.getPollingInterval());
            }
         }

      }
   }

   void registerMonitor(ObjectName objectName, JMXMonitorListener jmxMonitorListener) {
      GaugeMonitorListener gm = (GaugeMonitorListener)jmxMonitorListener;

      try {
         ObjectName monitor = this.getMonitorObjectName(objectName, gm, "GaugeMonitor");
         if (!this.mbeanServerConnection.isRegistered(monitor)) {
            ObjectInstance oi = this.mbeanServerConnection.createMBean("javax.management.monitor.GaugeMonitor", monitor);
            monitor = oi.getObjectName();
         } else {
            this.mbeanServerConnection.invoke(monitor, "stop", new Object[0], new String[0]);
         }

         this.mbeanServerConnection.setAttribute(monitor, new Attribute("GranularityPeriod", new Long((long)(gm.getPollingIntervalSeconds() * 1000))));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("ObservedAttribute", gm.getAttributeName()));
         this.mbeanServerConnection.invoke(monitor, "addObservedObject", new Object[]{objectName}, new String[]{"javax.management.ObjectName"});
         Number high = this.getNumber(gm.getTypeName(), gm.getAttributeName(), gm.getHighThreshold());
         Number low = this.getNumber(gm.getTypeName(), gm.getAttributeName(), gm.getLowThreshold());
         this.mbeanServerConnection.invoke(monitor, "setThresholds", new Object[]{high, low}, new String[]{"java.lang.Number", "java.lang.Number"});
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("NotifyHigh", Boolean.TRUE));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("NotifyLow", Boolean.TRUE));
         this.registerMonitorListener(monitor, gm, (Object)null);
         this.mbeanServerConnection.invoke(monitor, "start", new Object[0], new String[0]);
         gm.setMonitor(monitor);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Registered " + monitor + " to observe " + objectName + ":" + gm.getAttributeName());
         }
      } catch (Throwable var7) {
         SNMPLogger.logMonitorCreationError(gm.getName(), "SNMPGaugeMonitorMBean", objectName.toString(), var7);
      }

   }
}
