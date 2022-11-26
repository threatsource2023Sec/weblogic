package weblogic.diagnostics.snmp.server;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPCounterMonitorMBean;
import weblogic.management.configuration.ServerMBean;

public class CounterMonitorLifecycle extends JMXMonitorLifecycle {
   public CounterMonitorLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      super(adminServer, serverName, snmpAgent, mbeanServerConnection);
   }

   void initializeMonitorListenerList(SNMPAgentMBean snmpAgentMBean) throws Exception {
      this.initializeCounterMonitors(snmpAgentMBean.getSNMPCounterMonitors());
   }

   private void initializeCounterMonitors(SNMPCounterMonitorMBean[] counterMonitors) throws Exception {
      if (counterMonitors != null) {
         for(int i = 0; i < counterMonitors.length; ++i) {
            SNMPCounterMonitorMBean cm = counterMonitors[i];
            CounterMonitorListener counter = null;
            ServerMBean[] servers = cm.getEnabledServers();
            int serverCount = servers == null ? 0 : servers.length;
            boolean runtime = cm.getMonitoredMBeanType().endsWith("Runtime");
            if (runtime && this.adminServer && serverCount > 0) {
               for(int j = 0; j < serverCount; ++j) {
                  counter = new CounterMonitorListener(this, this.snmpAgent, cm.getMonitoredMBeanName(), cm.getMonitoredMBeanType(), this.serverName, servers[j].getName(), cm.getMonitoredAttributeName(), cm.getThreshold(), cm.getOffset(), cm.getModulus());
                  this.monitorListenerList.add(counter);
                  counter.setName(cm.getName());
                  counter.setPollingIntervalSeconds(cm.getPollingInterval());
               }
            } else {
               counter = new CounterMonitorListener(this, this.snmpAgent, cm.getMonitoredMBeanName(), cm.getMonitoredMBeanType(), this.serverName, (String)null, cm.getMonitoredAttributeName(), cm.getThreshold(), cm.getOffset(), cm.getModulus());
               this.monitorListenerList.add(counter);
               counter.setName(cm.getName());
               counter.setPollingIntervalSeconds(cm.getPollingInterval());
            }
         }

      }
   }

   void registerMonitor(ObjectName objectName, JMXMonitorListener jmxMonitorListener) {
      CounterMonitorListener cm = (CounterMonitorListener)jmxMonitorListener;

      try {
         ObjectName monitor = this.getMonitorObjectName(objectName, cm, "CounterMonitor");
         if (!this.mbeanServerConnection.isRegistered(monitor)) {
            ObjectInstance oi = this.mbeanServerConnection.createMBean("javax.management.monitor.CounterMonitor", monitor);
            monitor = oi.getObjectName();
         } else {
            this.mbeanServerConnection.invoke(monitor, "stop", new Object[0], new String[0]);
         }

         this.mbeanServerConnection.setAttribute(monitor, new Attribute("GranularityPeriod", new Long((long)(cm.getPollingIntervalSeconds() * 1000))));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("ObservedAttribute", cm.getAttributeName()));
         this.mbeanServerConnection.invoke(monitor, "addObservedObject", new Object[]{objectName}, new String[]{"javax.management.ObjectName"});
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("Notify", Boolean.TRUE));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("DifferenceMode", Boolean.FALSE));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("InitThreshold", this.getNumber(cm.getTypeName(), cm.getAttributeName(), (double)cm.getThreshold())));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("Offset", this.getNumber(cm.getTypeName(), cm.getAttributeName(), (double)cm.getOffset())));
         this.mbeanServerConnection.setAttribute(monitor, new Attribute("Modulus", this.getNumber(cm.getTypeName(), cm.getAttributeName(), (double)cm.getModulus())));
         this.registerMonitorListener(monitor, cm, (Object)null);
         this.mbeanServerConnection.invoke(monitor, "start", new Object[0], new String[0]);
         cm.setMonitor(monitor);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Registered " + monitor + " to observe " + objectName + ":" + cm.getAttributeName());
         }
      } catch (Throwable var6) {
         SNMPLogger.logMonitorCreationError(cm.getName(), "SNMPCounterMonitorMBean", objectName.toString(), var6);
      }

   }
}
