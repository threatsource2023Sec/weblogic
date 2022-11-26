package weblogic.diagnostics.snmp.server;

import java.util.LinkedList;
import java.util.List;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPAttributeChangeMBean;
import weblogic.management.configuration.ServerMBean;

public class MBeanAttributeChangeLifecycle extends JMXMonitorLifecycle {
   public MBeanAttributeChangeLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      super(adminServer, serverName, snmpAgent, mbeanServerConnection);
      this.deregisterMonitorListener = true;
   }

   void initializeMonitorListenerList(SNMPAgentMBean snmpAgentMBean) throws Exception {
      this.initializeMBeanAttributeChangeListeners(snmpAgentMBean.getSNMPAttributeChanges());
   }

   private void initializeMBeanAttributeChangeListeners(SNMPAttributeChangeMBean[] attrChangeListeners) throws Exception {
      if (attrChangeListeners != null) {
         for(int i = 0; i < attrChangeListeners.length; ++i) {
            SNMPAttributeChangeMBean acl = attrChangeListeners[i];
            MBeanAttributeChangeListener listener = null;
            ServerMBean[] servers = acl.getEnabledServers();
            int serverCount = servers == null ? 0 : servers.length;
            boolean runtime = acl.getAttributeMBeanType().endsWith("Runtime");
            if (runtime & this.adminServer && serverCount > 0) {
               for(int j = 0; j < serverCount; ++j) {
                  listener = new MBeanAttributeChangeListener(this, this.snmpAgent, acl.getAttributeMBeanName(), acl.getAttributeMBeanType(), this.serverName, servers[j].getName(), acl.getAttributeName());
                  this.monitorListenerList.add(listener);
                  listener.setName(acl.getName());
               }
            } else {
               listener = new MBeanAttributeChangeListener(this, this.snmpAgent, acl.getAttributeMBeanName(), acl.getAttributeMBeanType(), this.serverName, this.adminServer ? this.serverName : null, acl.getAttributeName());
               this.monitorListenerList.add(listener);
               listener.setName(acl.getName());
            }
         }

      }
   }

   void registerMonitor(ObjectName objectName, JMXMonitorListener jmxMonitorListener) {
      MBeanAttributeChangeListener acl = (MBeanAttributeChangeListener)jmxMonitorListener;
      List attrChangeListeners = null;
      synchronized(this.monitorListenerRegistry) {
         attrChangeListeners = (List)this.monitorListenerRegistry.get(objectName);
         if (attrChangeListeners == null) {
            attrChangeListeners = new LinkedList();
            this.monitorListenerRegistry.put(objectName, attrChangeListeners);
         }
      }

      try {
         this.mbeanServerConnection.addNotificationListener(objectName, acl, acl, (Object)null);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Registered attribute change listener " + acl + " for " + objectName + ":" + acl.getAttributeName());
         }

         synchronized(attrChangeListeners) {
            ((List)attrChangeListeners).add(acl);
         }
      } catch (Throwable var9) {
         SNMPLogger.logAttrChangeCreationError(acl.getName(), acl.getTypeName(), acl.getServerName(), var9);
      }

   }
}
