package weblogic.diagnostics.snmp.agent.monfox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import monfox.toolkit.snmp.SnmpCounter;
import monfox.toolkit.snmp.SnmpCounter64;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.notify.SnmpNotifier;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpNotificationInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPConstants;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class NotificationManager implements SNMPNotificationManager, SNMPConstants {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private SnmpMetadata snmpMetada;
   private SnmpNotifier snmpNotifier;
   private WorkManager snmpWorkManager;

   public NotificationManager(SnmpMetadata snmpMetada, SnmpNotifier snmpNotifier) {
      this.snmpMetada = snmpMetada;
      this.snmpNotifier = snmpNotifier;
      this.snmpWorkManager = WorkManagerFactory.getInstance().findOrCreate("SnmpWorkManager", 2, -1);
   }

   public void addTrapGroup(String notifyGroup, String tagName) throws SNMPAgentToolkitException {
      try {
         this.snmpNotifier.getNotifyTable().addTrapGroup(notifyGroup, tagName);
      } catch (Exception var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   public void addInformGroup(String notifyGroup, String tagName) throws SNMPAgentToolkitException {
      try {
         this.snmpNotifier.getNotifyTable().addInformGroup(notifyGroup, tagName);
      } catch (Exception var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   public void sendNotification(String notifyGroup, String notifName, List varBindings) throws SNMPAgentToolkitException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Sending notification to " + notifyGroup + " with the following bindings " + varBindings);
      }

      SnmpNotificationInfo notifInfo;
      try {
         notifInfo = this.snmpMetada.getNotification(notifName);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Got NotificationInfo " + notifInfo);
         }
      } catch (SnmpValueException var16) {
         throw new SNMPAgentToolkitException(var16);
      }

      Map objectInfos = this.getObjectInfos(notifInfo);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Got ObjectInfos " + objectInfos);
      }

      SnmpVarBindList varBindList = new SnmpVarBindList();
      Iterator i = varBindings.iterator();

      while(i.hasNext()) {
         Object[] tuple = (Object[])((Object[])i.next());
         String variableName = (String)tuple[0];
         SnmpObjectInfo objectInfo = (SnmpObjectInfo)objectInfos.get(variableName);
         if (objectInfo == null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Got null objectInfo for variable " + variableName);
            }
         } else {
            SnmpOid variableOid = objectInfo.getOid();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Got Variable OID " + variableOid + " for variable " + variableName);
            }

            Object val = tuple[1];
            if (val == null) {
               val = "";
            }

            SnmpValue snmpVal = null;
            if (val instanceof Integer) {
               switch (objectInfo.getType()) {
                  case 65:
                     snmpVal = new SnmpCounter((Integer)val);
                     break;
                  default:
                     snmpVal = new SnmpInt((Integer)val);
               }
            } else if (val instanceof Long) {
               snmpVal = new SnmpCounter64((Long)val);
            } else {
               try {
                  snmpVal = new SnmpString(val.toString());
               } catch (SnmpValueException var15) {
                  throw new SNMPAgentToolkitException(var15);
               }
            }

            varBindList.add(variableOid, (SnmpValue)snmpVal);
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Sending notification to " + notifyGroup + " with OID " + notifInfo.getOid() + " and SNMP Variable bindings " + varBindList);
      }

      SnmpNotifier.NotifyResult result = this.snmpNotifier.send(notifyGroup, notifInfo.getOid(), varBindList);
      this.snmpWorkManager.schedule(new NotifyResultLogger(result, notifInfo));
   }

   public void cleanup() {
      this.snmpNotifier.getNotifyTable().removeAllRows();
   }

   private Map getObjectInfos(SnmpNotificationInfo notifInfo) {
      Map result = new HashMap();
      SnmpObjectInfo[] objects = notifInfo.getObjects();

      for(int i = 0; i < objects.length; ++i) {
         result.put(objects[i].getName(), objects[i]);
      }

      return result;
   }

   private static class NotifyResultLogger implements Runnable {
      private SnmpNotifier.NotifyResult result;
      private SnmpNotificationInfo notifInfo;

      NotifyResultLogger(SnmpNotifier.NotifyResult result, SnmpNotificationInfo notifInfo) {
         this.result = result;
         this.notifInfo = notifInfo;
      }

      public void run() {
         if (NotificationManager.DEBUG.isDebugEnabled()) {
            NotificationManager.DEBUG.debug("Before awaitCompletion:" + this.result);
         }

         this.result.awaitCompletion();
         int errCount = this.result.getErrorCount();
         int sentCount = this.result.getSentCount();
         if (NotificationManager.DEBUG.isDebugEnabled()) {
            NotificationManager.DEBUG.debug("After awaitCompletion:" + this.result + " errCount=" + errCount);
         }

         if (errCount > 0 && errCount == sentCount) {
            SNMPLogger.logErrorSendingSNMPNotification(sentCount, errCount, this.notifInfo.getOid().toString(), this.result.toString());
         } else if (NotificationManager.DEBUG.isDebugEnabled()) {
            NotificationManager.DEBUG.debug("At least one notification " + this.notifInfo.getOid() + " sent successfully.");
         }

      }
   }
}
