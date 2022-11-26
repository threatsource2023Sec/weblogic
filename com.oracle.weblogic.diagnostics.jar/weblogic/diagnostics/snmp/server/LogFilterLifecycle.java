package weblogic.diagnostics.snmp.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPLogFilterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;

public class LogFilterLifecycle extends JMXMonitorLifecycle {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private Map logFilters = new HashMap();

   public LogFilterLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      super(adminServer, serverName, snmpAgent, mbeanServerConnection);
   }

   void initializeMonitorListenerList(SNMPAgentMBean snmpAgentMBean) throws Exception {
      this.initializeLogFilterListeners(snmpAgentMBean.getSNMPLogFilters());
   }

   void registerMonitor(ObjectName objectName, JMXMonitorListener jmxMonitorListener) {
   }

   void serverStarted(String server) {
      synchronized(this.logFilters) {
         List lfList = (List)this.logFilters.get(server);
         if (lfList != null) {
            Iterator i = lfList.iterator();

            while(i.hasNext()) {
               SNMPLogFilterMBean lf = (SNMPLogFilterMBean)i.next();
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Adding filter " + lf.getName() + " for server " + server);
               }

               try {
                  this.addLogBroadcasterRuntimeListener(server, lf);
               } catch (Throwable var8) {
                  SNMPLogger.logTrapLogAddNotifError(lf.getName(), var8);
               }
            }

         }
      }
   }

   private void initializeLogFilterListeners(SNMPLogFilterMBean[] logFilters) throws Exception {
      if (logFilters != null) {
         for(int i = 0; i < logFilters.length; ++i) {
            SNMPLogFilterMBean lf = logFilters[i];
            if (this.adminServer) {
               this.initializeAdminServerLogFilterListeners(lf);
            } else {
               this.initializeManagedServerLogFilterListeners(lf);
            }
         }

      }
   }

   private void initializeManagedServerLogFilterListeners(SNMPLogFilterMBean lf) throws Exception {
      ObjectName serverRuntime = (ObjectName)this.mbeanServerConnection.getAttribute(new ObjectName(RuntimeServiceMBean.OBJECT_NAME), "ServerRuntime");
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Found " + serverRuntime);
      }

      String name = (String)this.mbeanServerConnection.getAttribute(serverRuntime, "Name");
      this.addLogFilter(name, lf);
      this.addLogBroadcasterRuntimeListener(serverRuntime, lf);
   }

   private void initializeAdminServerLogFilterListeners(SNMPLogFilterMBean lf) throws Exception {
      ServerMBean[] servers = lf.getEnabledServers();
      if (servers == null || servers.length == 0) {
         SNMPAgentMBean snmpAgent = (SNMPAgentMBean)lf.getParent();
         if (snmpAgent != null) {
            DomainMBean domain = null;
            domain = (DomainMBean)snmpAgent.getParent();
            if (domain != null) {
               servers = domain.getServers();
            }
         }
      }

      if (servers != null) {
         for(int i = 0; i < servers.length; ++i) {
            ServerMBean server = servers[i];
            String s = server.getName();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Looking up ServerRuntime for " + s);
            }

            this.addLogFilter(s, lf);
            this.addLogBroadcasterRuntimeListener(s, lf);
         }

      }
   }

   private void addLogFilter(String name, SNMPLogFilterMBean lf) {
      synchronized(this.logFilters) {
         List logFilterList = (List)this.logFilters.get(name);
         if (logFilterList == null) {
            logFilterList = new LinkedList();
            this.logFilters.put(name, logFilterList);
         }

         ((List)logFilterList).add(lf);
      }
   }

   private void addLogBroadcasterRuntimeListener(String name, SNMPLogFilterMBean lf) throws Exception {
      ObjectName serverRuntime = null;
      if (this.adminServer) {
         serverRuntime = (ObjectName)this.mbeanServerConnection.invoke(new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME), "lookupServerRuntime", new Object[]{name}, new String[]{"java.lang.String"});
      } else {
         serverRuntime = (ObjectName)this.mbeanServerConnection.getAttribute(new ObjectName(RuntimeServiceMBean.OBJECT_NAME), "ServerRuntime");
      }

      if (serverRuntime != null) {
         this.addLogBroadcasterRuntimeListener(serverRuntime, lf);
      }

   }

   private void addLogBroadcasterRuntimeListener(ObjectName serverRuntime, SNMPLogFilterMBean lf) throws Exception {
      ObjectName logBroadcasterRuntime = (ObjectName)this.mbeanServerConnection.getAttribute(serverRuntime, "LogBroadcasterRuntime");
      if (this.monitorListenerRegistry.containsKey(logBroadcasterRuntime)) {
         List listeners = (List)this.monitorListenerRegistry.get(logBroadcasterRuntime);
         Iterator var5 = listeners.iterator();

         while(var5.hasNext()) {
            Object item = var5.next();
            if (item != null && item instanceof LogFilterListener) {
               LogFilterListener l = (LogFilterListener)item;
               if (l.getName().equals(lf.getName())) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Not adding log broadcaster listener as it is already registered for filter " + lf.getName());
                  }

                  return;
               }
            }
         }
      }

      LogFilterListener listener = new LogFilterListener(this, this.snmpAgent, lf.getName(), lf.getSeverityLevel(), lf.getSubsystemNames(), lf.getUserIds(), lf.getMessageIds(), lf.getMessageSubstring());
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Registering log filter listener " + lf.getName() + " for " + logBroadcasterRuntime);
      }

      this.monitorListenerList.add(listener);
      this.registerMonitorListener(logBroadcasterRuntime, listener, (Object)null);
   }

   protected void deregisterMonitorListener(ObjectName name, JMXMonitorListener listener) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Deregistering log filter listener " + listener);
      }

      try {
         this.mbeanServerConnection.removeNotificationListener(name, listener);
      } catch (Throwable var4) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Exception deregistering listener " + listener + " from " + name);
         }
      }

   }
}
