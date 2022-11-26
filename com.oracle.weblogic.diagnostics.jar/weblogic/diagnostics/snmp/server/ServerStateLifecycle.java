package weblogic.diagnostics.snmp.server;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;

public class ServerStateLifecycle extends JMXMonitorLifecycle {
   private LogFilterLifecycle logFilterLifecycle;

   public ServerStateLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      super(adminServer, serverName, snmpAgent, mbeanServerConnection);
      this.deregisterMonitorListener = true;
   }

   void initializeMonitorListenerList(SNMPAgentMBean snmpAgentMBean) throws Exception {
      if (this.adminServer) {
         this.initializeAdminServerLifecycleListeners();
      } else {
         this.initializeManagedServerRuntimeListener();
      }

   }

   void registerMonitor(ObjectName objectName, JMXMonitorListener jmxMonitorListener) {
   }

   private void initializeAdminServerLifecycleListeners() throws Exception {
      ObjectName domainRuntime = (ObjectName)this.mbeanServerConnection.getAttribute(new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME), "DomainRuntime");
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Found " + domainRuntime);
      }

      ObjectName[] serverLifecycles = (ObjectName[])((ObjectName[])this.mbeanServerConnection.getAttribute(domainRuntime, "ServerLifeCycleRuntimes"));

      for(int i = 0; i < serverLifecycles.length; ++i) {
         ObjectName serverLifecycle = serverLifecycles[i];
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Found " + serverLifecycle);
         }

         String name = (String)this.mbeanServerConnection.getAttribute(serverLifecycle, "Name");
         ServerStateListener listener = new ServerStateListener(this, name, this.snmpAgent);
         this.monitorListenerList.add(listener);
         this.registerMonitorListener(serverLifecycle, listener, (Object)null);
      }

   }

   private void initializeManagedServerRuntimeListener() throws Exception {
      ObjectName serverRuntime = (ObjectName)this.mbeanServerConnection.getAttribute(new ObjectName(RuntimeServiceMBean.OBJECT_NAME), "ServerRuntime");
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Found " + serverRuntime);
      }

      String name = (String)this.mbeanServerConnection.getAttribute(serverRuntime, "Name");
      ServerStateListener listener = new ServerStateListener(this, name, this.snmpAgent);
      this.monitorListenerList.add(listener);
      this.registerMonitorListener(serverRuntime, listener, (Object)null);
   }

   public void setLogFilterLifecycle(LogFilterLifecycle logFilterLifecycle) {
      this.logFilterLifecycle = logFilterLifecycle;
   }

   void serverStarted(String serverName) {
      if (this.logFilterLifecycle != null) {
         this.logFilterLifecycle.serverStarted(serverName);
      }

   }

   void serverStopped(String serverName) {
      if (this.logFilterLifecycle != null) {
         this.logFilterLifecycle.serverStopped(serverName);
      }

   }
}
