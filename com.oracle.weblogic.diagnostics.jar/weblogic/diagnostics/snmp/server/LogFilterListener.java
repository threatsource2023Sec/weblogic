package weblogic.diagnostics.snmp.server;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.management.Notification;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;
import weblogic.i18n.logging.SeverityI18N;
import weblogic.management.logging.WebLogicLogNotification;

public class LogFilterListener extends JMXMonitorListener {
   private String name;
   private int severity;
   private Set allowedSubsystems = new HashSet();
   private Set allowedUserIds = new HashSet();
   private Set allowedMsgIds = new HashSet();
   private String messageSubString = "";

   public LogFilterListener(JMXMonitorLifecycle lifecycle, SNMPAgent agent, String name, String severityLevel, String[] subsystems, String[] userIds, String[] msgIds, String searchText) {
      super(lifecycle, agent);
      this.name = name;
      this.severity = SeverityI18N.severityStringToNum(severityLevel);
      if (subsystems != null) {
         Collections.addAll(this.allowedSubsystems, subsystems);
      }

      if (userIds != null) {
         Collections.addAll(this.allowedUserIds, userIds);
      }

      if (msgIds != null) {
         Collections.addAll(this.allowedMsgIds, msgIds);
      }

      if (searchText != null) {
         this.messageSubString = searchText;
      }

   }

   public boolean isNotificationEnabled(Notification arg0) {
      if (arg0 instanceof WebLogicLogNotification) {
         WebLogicLogNotification logNotif = (WebLogicLogNotification)arg0;
         if (logNotif.getSeverity() > this.severity) {
            return false;
         } else {
            String message;
            if (!this.allowedSubsystems.isEmpty()) {
               message = logNotif.getSubsystem();
               if (!this.allowedSubsystems.contains(message)) {
                  return false;
               }
            }

            if (!this.allowedUserIds.isEmpty()) {
               message = logNotif.getUserId();
               if (!this.allowedUserIds.contains(message)) {
                  return false;
               }
            }

            if (!this.allowedMsgIds.isEmpty()) {
               message = logNotif.getMessageIdString();
               if (!this.allowedMsgIds.contains(message)) {
                  return false;
               }
            }

            if (this.messageSubString != null && this.messageSubString.length() > 0) {
               message = logNotif.getMessage();
               if (!message.contains(this.messageSubString)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public void handleNotification(Notification arg0, Object arg1) {
      WebLogicLogNotification logNotif = (WebLogicLogNotification)arg0;
      SNMPNotificationManager nm = this.snmpAgent.getSNMPAgentToolkit().getSNMPNotificationManager();
      if (nm == null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Null notification manager, agent deactivated.");
         }

      } else {
         List varBindList = new LinkedList();
         String trapTime = (new Date()).toString();
         varBindList.add(new Object[]{"trapTime", trapTime});
         varBindList.add(new Object[]{"trapServerName", logNotif.getServername()});
         varBindList.add(new Object[]{"trapMachineName", logNotif.getMachineName()});
         varBindList.add(new Object[]{"trapLogThreadId", logNotif.getThreadId()});
         varBindList.add(new Object[]{"trapLogTransactionId", logNotif.getTransactionId()});
         varBindList.add(new Object[]{"trapLogUserId", logNotif.getUserId()});
         varBindList.add(new Object[]{"trapLogSubsystem", logNotif.getSubsystem()});
         varBindList.add(new Object[]{"trapLogMsgId", logNotif.getId()});
         String severityStr = SeverityI18N.severityNumToString(logNotif.getSeverity());
         varBindList.add(new Object[]{"trapLogSeverity", severityStr});
         varBindList.add(new Object[]{"trapLogMessage", logNotif.getMessage()});

         try {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Sending log message trap:" + toString(varBindList));
            }

            nm.sendNotification(this.snmpAgent.getNotifyGroup(), "wlsLogNotification", varBindList);
            this.updateMonitorTrapCount();
         } catch (Exception var9) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Exception sending log message trap", var9);
            }
         }

      }
   }

   void updateMonitorTrapCount() {
      if (this.snmpStats != null) {
         this.snmpStats.incrementLogMessageTrapCount();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Updated log message count to " + this.snmpStats.getLogMessageTrapCount());
         }
      }

   }

   String getName() {
      return this.name;
   }
}
