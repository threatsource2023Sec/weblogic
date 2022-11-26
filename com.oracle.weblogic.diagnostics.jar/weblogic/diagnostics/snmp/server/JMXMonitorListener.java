package weblogic.diagnostics.snmp.server;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.monitor.MonitorNotification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.utils.ArrayUtils;

public abstract class JMXMonitorListener implements NotificationFilter, NotificationListener {
   private static final String LOCATION_KEY = "Location";
   protected static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   protected static final String TRAP_TIME = "trapTime";
   protected static final String TRAP_SERVER_NAME = "trapServerName";
   protected static final String TRAP_MONITOR_TYPE = "trapMonitorType";
   protected static final String TRAP_MBEAN_NAME = "trapMBeanName";
   protected static final String TRAP_MBEAN_TYPE = "trapMBeanType";
   protected static final String TRAP_MONITOR_THRESHOLD = "trapMonitorThreshold";
   protected static final String TRAP_ATTR_NAME = "trapAttributeName";
   protected static final String TRAP_CONFIG_NAME = "trapConfigName";
   protected static final String TRAP_MONITOR_VALUE = "trapMonitorValue";
   protected static final String WLS_MONITOR_NOTIFICATION = "wlsMonitorNotification";
   protected transient SNMPAgent snmpAgent;
   protected transient ObjectName monitor;
   protected transient JMXMonitorLifecycle monitorLifecycle;
   protected transient SNMPRuntimeStats snmpStats;
   protected String serverName;
   protected String mbeanName;
   protected String typeName;
   protected ObjectName queryExpression;
   protected String attributeName;
   protected int pollingIntervalSeconds;
   protected String name;

   public JMXMonitorListener(JMXMonitorLifecycle lc, SNMPAgent agent) {
      this.monitor = null;
      this.monitorLifecycle = lc;
      this.snmpAgent = agent;
   }

   public JMXMonitorListener(JMXMonitorLifecycle lifecycle, SNMPAgent agent, String name, String type, String server, String location, String attribute) throws MalformedObjectNameException {
      this(lifecycle, agent);
      this.serverName = server;
      this.mbeanName = name;
      this.typeName = type;
      this.attributeName = attribute;
      String queryString = "com.bea:Type=" + this.typeName;
      if (this.mbeanName != null && this.mbeanName.length() > 0) {
         queryString = queryString + ",Name=" + this.mbeanName;
      }

      if (location != null && location.length() > 0) {
         queryString = queryString + ",Location=" + location;
      }

      queryString = queryString + ",*";
      this.queryExpression = new ObjectName(queryString);
   }

   public ObjectName getQueryExpression() {
      return this.queryExpression;
   }

   int getPollingIntervalSeconds() {
      return this.pollingIntervalSeconds;
   }

   void setPollingIntervalSeconds(int pollingIntervalSeconds) {
      this.pollingIntervalSeconds = pollingIntervalSeconds;
   }

   String getAttributeName() {
      return this.attributeName;
   }

   String getName() {
      return this.name;
   }

   void setName(String name) {
      this.name = name;
   }

   ObjectName getMonitor() {
      return this.monitor;
   }

   void setMonitor(ObjectName monitorMBean) {
      this.monitor = monitorMBean;
   }

   public void handleNotification(Notification arg0, Object arg1) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Got notification from MonitorMBean " + arg0);
      }

      if (arg0 instanceof MonitorNotification) {
         MonitorNotification mn = (MonitorNotification)arg0;
         Object observedObject = mn.getObservedObject();
         String trapServerName = this.serverName;
         String notifyGroup;
         if (observedObject instanceof ObjectName) {
            ObjectName sourceObjName = (ObjectName)observedObject;
            if (this.mbeanName == null || this.mbeanName.trim().length() == 0) {
               this.mbeanName = sourceObjName.toString();
            }

            notifyGroup = sourceObjName.getKeyProperty("Location");
            if (notifyGroup != null && notifyGroup.length() > 0) {
               trapServerName = notifyGroup;
            }
         }

         SNMPNotificationManager nm = this.snmpAgent.getSNMPAgentToolkit().getSNMPNotificationManager();
         notifyGroup = this.snmpAgent.getNotifyGroup();
         List varBindList = new LinkedList();
         varBindList.add(new Object[]{"trapTime", (new Date()).toString()});
         varBindList.add(new Object[]{"trapServerName", trapServerName});
         varBindList.add(new Object[]{"trapMonitorType", mn.getType()});
         if (null != mn.getTrigger()) {
            varBindList.add(new Object[]{"trapMonitorThreshold", mn.getTrigger().toString()});
         } else {
            varBindList.add(new Object[]{"trapMonitorThreshold", "null"});
         }

         if (null != mn.getDerivedGauge()) {
            varBindList.add(new Object[]{"trapMonitorValue", mn.getDerivedGauge().toString()});
         } else {
            varBindList.add(new Object[]{"trapMonitorValue", "null"});
         }

         varBindList.add(new Object[]{"trapMBeanName", observedObject != null ? observedObject.toString() : this.mbeanName});
         varBindList.add(new Object[]{"trapMBeanType", this.typeName});
         varBindList.add(new Object[]{"trapAttributeName", this.attributeName});
         varBindList.add(new Object[]{"trapConfigName", this.name});

         try {
            nm.sendNotification(notifyGroup, "wlsMonitorNotification", varBindList);
            this.updateMonitorTrapCount();
         } catch (SNMPAgentToolkitException var10) {
            SNMPLogger.logMonitorNotificationError(this.serverName, this.typeName, this.mbeanName, var10);
         }
      }

   }

   abstract void updateMonitorTrapCount();

   JMXMonitorLifecycle getMonitorLifecycle() {
      return this.monitorLifecycle;
   }

   SNMPRuntimeStats getSNMPRuntimeStats() {
      return this.snmpStats;
   }

   void setSNMPRuntimeStats(SNMPRuntimeStats stats) {
      this.snmpStats = stats;
   }

   String getTypeName() {
      return this.typeName;
   }

   String getServerName() {
      return this.serverName;
   }

   static String toString(List varBindList) {
      StringBuffer buf = new StringBuffer();
      buf.append('[');
      Iterator var2 = varBindList.iterator();

      while(var2.hasNext()) {
         Object[] varBind = (Object[])var2.next();
         buf.append(ArrayUtils.toString(varBind));
      }

      buf.append(']');
      return buf.toString();
   }
}
