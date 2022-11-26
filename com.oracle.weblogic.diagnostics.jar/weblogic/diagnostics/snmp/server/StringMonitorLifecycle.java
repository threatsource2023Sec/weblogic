package weblogic.diagnostics.snmp.server;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPStringMonitorMBean;
import weblogic.management.configuration.ServerMBean;

public class StringMonitorLifecycle extends JMXMonitorLifecycle {
   public StringMonitorLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      super(adminServer, serverName, snmpAgent, mbeanServerConnection);
   }

   void initializeMonitorListenerList(SNMPAgentMBean snmpAgentMBean) throws Exception {
      this.initializeStringMonitors(snmpAgentMBean.getSNMPStringMonitors());
   }

   private void initializeStringMonitors(SNMPStringMonitorMBean[] stringMonitors) throws Exception {
      if (stringMonitors != null) {
         for(int i = 0; i < stringMonitors.length; ++i) {
            SNMPStringMonitorMBean sm = stringMonitors[i];
            StringMonitorListener stringMonitor = null;
            ServerMBean[] servers = sm.getEnabledServers();
            int serverCount = servers == null ? 0 : servers.length;
            boolean runtime = sm.getMonitoredMBeanType().endsWith("Runtime");
            if (runtime && this.adminServer && serverCount > 0) {
               for(int j = 0; j < serverCount; ++j) {
                  stringMonitor = new StringMonitorListener(this, this.snmpAgent, sm.getMonitoredMBeanName(), sm.getMonitoredMBeanType(), this.serverName, servers[j].getName(), sm.getMonitoredAttributeName(), sm.getStringToCompare(), sm.isNotifyDiffer(), sm.isNotifyMatch());
                  this.monitorListenerList.add(stringMonitor);
                  stringMonitor.setName(sm.getName());
                  stringMonitor.setPollingIntervalSeconds(sm.getPollingInterval());
               }
            } else {
               stringMonitor = new StringMonitorListener(this, this.snmpAgent, sm.getMonitoredMBeanName(), sm.getMonitoredMBeanType(), this.serverName, (String)null, sm.getMonitoredAttributeName(), sm.getStringToCompare(), sm.isNotifyDiffer(), sm.isNotifyMatch());
               this.monitorListenerList.add(stringMonitor);
               stringMonitor.setName(sm.getName());
               stringMonitor.setPollingIntervalSeconds(sm.getPollingInterval());
            }
         }

      }
   }

   void registerMonitor(ObjectName objectName, JMXMonitorListener jmxMonitorListener) {
      StringMonitorListener sm = (StringMonitorListener)jmxMonitorListener;

      try {
         ObjectName monitor = this.getMonitorObjectName(objectName, sm, "StringMonitor");
         if (!this.mbeanServerConnection.isRegistered(monitor)) {
            ObjectInstance oi = this.mbeanServerConnection.createMBean("javax.management.monitor.StringMonitor", monitor);
            monitor = oi.getObjectName();
         } else {
            this.mbeanServerConnection.invoke(monitor, "stop", new Object[0], new String[0]);
         }

         this.mbeanServerConnection.setAttribute(monitor, new Attribute("GranularityPeriod", new Long((long)(sm.getPollingIntervalSeconds() * 1000))));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("ObservedAttribute", sm.getAttributeName()));
         this.mbeanServerConnection.invoke(monitor, "addObservedObject", new Object[]{objectName}, new String[]{"javax.management.ObjectName"});
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("StringToCompare", sm.getStringToCompare()));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("NotifyDiffer", new Boolean(sm.isNotifyDiffer())));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("NotifyMatch", new Boolean(sm.isNotifyMatch())));
         this.registerMonitorListener(monitor, sm, (Object)null);
         this.mbeanServerConnection.invoke(monitor, "start", new Object[0], new String[0]);
         sm.setMonitor(monitor);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Registered " + monitor + " to observe " + objectName + ":" + sm.getAttributeName());
         }
      } catch (Throwable var6) {
         SNMPLogger.logMonitorCreationError(sm.getName(), "SNMPStringMonitorMBean", objectName.toString(), var6);
      }

   }
}
