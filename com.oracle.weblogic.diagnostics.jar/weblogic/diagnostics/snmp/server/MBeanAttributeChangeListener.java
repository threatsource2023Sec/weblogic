package weblogic.diagnostics.snmp.server;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.management.AttributeChangeNotification;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;

public class MBeanAttributeChangeListener extends JMXMonitorListener {
   private static final String ATTR_CHANGE_TRAP = "wlsAttributeChange";
   private static final String TRAP_ATTR_TYPE = "trapAttributeType";
   private static final String TRAP_ATTR_CHANGE_TYPE = "trapAttributeChangeType";
   private static final String TRAP_ATTR_OLD_VALUE = "trapAttributeOldVal";
   private static final String TRAP_ATTR_NEW_VALUE = "trapAttributeNewVal";

   public MBeanAttributeChangeListener(JMXMonitorLifecycle lifecycle, SNMPAgent agent, String name, String type, String server, String location, String attribute) throws MalformedObjectNameException {
      super(lifecycle, agent, name, type, server, location, attribute);
   }

   public void handleNotification(Notification arg0, Object arg1) {
      AttributeChangeNotification acn = (AttributeChangeNotification)arg0;
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Got attribute change notification " + acn + " from " + acn.getSource());
      }

      Object oldValue = acn.getOldValue();
      Object newValue = acn.getNewValue();
      SNMPNotificationManager nm = this.snmpAgent.getSNMPAgentToolkit().getSNMPNotificationManager();
      String notifyGroup = this.snmpAgent.getNotifyGroup();
      List varBindList = new LinkedList();
      String attributeType = acn.getAttributeType();
      String attributeChangeType = "UPDATE";
      boolean array = false;
      if (acn.getNewValue() != null) {
         array = acn.getNewValue().getClass().isArray();
      }

      if (acn.getOldValue() != null) {
         array = array || acn.getOldValue().getClass().isArray();
      }

      if (array) {
         Object[] oldArrayValue = (Object[])((Object[])acn.getOldValue());
         Object[] newArrayValue = (Object[])((Object[])acn.getNewValue());
         int oldArrayLength = oldArrayValue == null ? 0 : oldArrayValue.length;
         int newArrayLength = newArrayValue == null ? 0 : newArrayValue.length;
         if (oldArrayLength > newArrayLength) {
            attributeChangeType = "REMOVE";
         } else if (oldArrayLength < newArrayLength) {
            attributeChangeType = "ADD";
         }

         oldValue = this.toString(oldArrayValue);
         newValue = this.toString(newArrayValue);
      }

      Object src = acn.getSource();
      if (src == null) {
         src = "";
      }

      if (src instanceof ObjectName) {
         src = ((ObjectName)src).getCanonicalName();
      }

      varBindList.add(new Object[]{"trapTime", (new Date()).toString()});
      varBindList.add(new Object[]{"trapServerName", this.serverName});
      varBindList.add(new Object[]{"trapMBeanName", src.toString()});
      varBindList.add(new Object[]{"trapMBeanType", this.typeName});
      varBindList.add(new Object[]{"trapAttributeName", this.attributeName});
      varBindList.add(new Object[]{"trapAttributeType", attributeType});
      varBindList.add(new Object[]{"trapAttributeChangeType", attributeChangeType});
      varBindList.add(new Object[]{"trapAttributeOldVal", "" + oldValue});
      varBindList.add(new Object[]{"trapAttributeNewVal", "" + newValue});
      varBindList.add(new Object[]{"trapConfigName", this.name});

      try {
         nm.sendNotification(notifyGroup, "wlsAttributeChange", varBindList);
         this.updateMonitorTrapCount();
      } catch (SNMPAgentToolkitException var16) {
         SNMPLogger.logMonitorNotificationError(this.serverName, this.typeName, this.mbeanName, var16);
      }

   }

   public boolean isNotificationEnabled(Notification arg0) {
      if (arg0 instanceof AttributeChangeNotification) {
         AttributeChangeNotification acn = (AttributeChangeNotification)arg0;
         if (acn.getAttributeName().equals(this.attributeName)) {
            return true;
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Filtering notification " + arg0);
      }

      return false;
   }

   void updateMonitorTrapCount() {
      if (this.snmpStats != null) {
         this.snmpStats.incrementAttributeChangeTrapCount();
      }

   }

   private String toString(Object[] array) {
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      if (array != null) {
         Object[] var3 = array;
         int var4 = array.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object obj = var3[var5];
            sb.append("[");
            sb.append(obj != null ? obj.toString() : "");
            sb.append("]");
         }
      }

      sb.append("}");
      return sb.toString();
   }
}
