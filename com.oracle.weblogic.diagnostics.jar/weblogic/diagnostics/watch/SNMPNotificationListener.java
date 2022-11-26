package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.Notification;
import com.bea.diagnostics.notifications.NotificationServiceFactory;
import com.bea.diagnostics.notifications.SNMPNotificationCustomizer;
import com.bea.diagnostics.notifications.SNMPNotificationService;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFSNMPNotificationBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPTrapException;
import weblogic.diagnostics.snmp.agent.SNMPTrapSender;
import weblogic.diagnostics.snmp.agent.SNMPTrapUtil;

final class SNMPNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener, SNMPNotificationCustomizer {
   private static final String WATCH_NOTIFICATION_TRAP_NAME = "wlsWatchNotification";
   private SNMPNotificationService snmpService;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");

   SNMPNotificationListener(WLDFSNMPNotificationBean configBean, WatchManagerStatisticsImpl stats) throws InvalidNotificationException, NotificationCreateException {
      super(configBean, stats);

      try {
         this.snmpService = NotificationServiceFactory.getInstance().createSNMPotificationService(this.getNotificationName(), "wlsWatchNotification", (SNMPAgent)null, (Map)null, this);
      } catch (com.bea.diagnostics.notifications.NotificationCreateException var4) {
         throw new NotificationCreateException(var4);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created snmp notification " + this);
      }

   }

   public void processWatchNotification(Notification wn) {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Handle snmp notification for " + this);
            debugLogger.debug("Watch notification: " + wn);
         }

         if (this.snmpService != null) {
            this.snmpService.send(wn);
            this.getStatistics().incrementTotalSNMPNotificationsPerformed();
         }
      } catch (Throwable var3) {
         this.getStatistics().incrementTotalFailedSNMPNotifications();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("SNMP trap send failed with exception ", var3);
         }

         DiagnosticsLogger.logErrorInSNMPNotification(var3);
      }

   }

   public List processNotification(Notification notif) {
      if (notif instanceof WatchNotificationInternal) {
         WatchNotificationInternal wn = (WatchNotificationInternal)notif;
         List varBindings = new LinkedList();
         varBindings.add(new Object[]{"trapTime", wn.getWatchTime()});
         varBindings.add(new Object[]{"trapDomainName", wn.getWatchDomainName()});
         varBindings.add(new Object[]{"trapServerName", wn.getWatchServerName()});
         varBindings.add(new Object[]{"trapWatchModule", wn.getModuleName()});
         varBindings.add(new Object[]{"trapWatchSeverity", wn.getWatchSeverityLevel()});
         varBindings.add(new Object[]{"trapWatchName", wn.getWatchName()});
         varBindings.add(new Object[]{"trapWatchRuleType", wn.getWatchRuleType()});
         varBindings.add(new Object[]{"trapWatchRule", wn.getWatchRule()});
         varBindings.add(new Object[]{"trapWatchData", wn.getWatchDataToString()});
         varBindings.add(new Object[]{"trapWatchAlarmType", wn.getWatchAlarmType()});
         varBindings.add(new Object[]{"trapWatchAlarmResetPeriod", wn.getWatchAlarmResetPeriod()});
         varBindings.add(new Object[]{"trapWatchSNMPNotificationName", this.getNotificationName()});
         SNMPTrapSender ts = SNMPTrapUtil.getInstance().getSNMPTrapSender();
         if (ts != null) {
            try {
               ts.sendTrap("wlsWatchNotification", varBindings);
            } catch (SNMPTrapException var6) {
               DiagnosticsLogger.logErrorInSNMPNotification(var6);
            }
         }
      }

      return null;
   }
}
