package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.JMSNotificationCustomizer;
import com.bea.diagnostics.notifications.JMSNotificationService;
import com.bea.diagnostics.notifications.Notification;
import com.bea.diagnostics.notifications.NotificationPropagationException;
import com.bea.diagnostics.notifications.NotificationServiceFactory;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFJMSNotificationBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;

final class JMSNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener, JMSNotificationCustomizer {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final String JMS_NOTIFICATION_NAME = "JMSNotificationName";
   private String destinationJNDIName;
   private String connectionFactoryJNDIName;
   private JMSNotificationService jmsService;

   public JMSNotificationListener(WLDFJMSNotificationBean configBean, WatchManagerStatisticsImpl statistics) throws NotificationCreateException, InvalidNotificationException {
      super(configBean, statistics);
      this.destinationJNDIName = configBean.getDestinationJNDIName();
      if (this.destinationJNDIName == null) {
         throw new NotificationCreateException("JNDI name for JMS Destination must be set and cannot be null");
      } else {
         if (configBean.getConnectionFactoryJNDIName() == null) {
            this.connectionFactoryJNDIName = "weblogic.jms.ConnectionFactory";
         } else {
            this.connectionFactoryJNDIName = configBean.getConnectionFactoryJNDIName();
         }

         try {
            this.jmsService = NotificationServiceFactory.getInstance().createJMSNotificationService(this.getNotificationName(), this.destinationJNDIName, this.connectionFactoryJNDIName, this);
         } catch (com.bea.diagnostics.notifications.NotificationCreateException var4) {
            throw new NotificationCreateException(var4);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created JMS notification " + this);
         }

      }
   }

   String getDestinationJNDIName() {
      return this.destinationJNDIName;
   }

   String getConnectionFactoryJNDIName() {
      return this.connectionFactoryJNDIName;
   }

   public synchronized void processWatchNotification(Notification wn) {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Processing watch notification for " + wn);
         }

         if (this.jmsService != null) {
            this.jmsService.send(wn);
            this.getStatistics().incrementTotalJMSNotificationsPerformed();
         }
      } catch (Throwable var3) {
         DiagnosticsLogger.logJMSNotificationSendMsgException(var3);
         this.getStatistics().incrementTotalFailedJMSNotifications();
      }

   }

   public String toString() {
      return "JMSNotificationListener " + this.getNotificationName() + " - " + (this.jmsService != null ? this.jmsService.toString() : "");
   }

   public Message createMessage(Session session, Notification notif) throws NotificationPropagationException {
      try {
         MapMessage msg = session.createMapMessage();
         WatchNotificationInternal wn = (WatchNotificationInternal)notif;
         msg.setString("WatchTime", wn.getWatchTime());
         msg.setString("WatchDomainName", wn.getWatchDomainName());
         msg.setString("WatchServerName", wn.getWatchServerName());
         msg.setString("WatchSeverityLevel", wn.getWatchSeverityLevel());
         msg.setString("WatchName", wn.getWatchName());
         msg.setString("WatchModule", wn.getModuleName());
         msg.setString("WatchRuleType", wn.getWatchRuleType());
         msg.setString("WatchRule", wn.getWatchRule());
         msg.setString("WatchAlarmType", wn.getWatchAlarmType());
         msg.setString("WatchAlarmResetPeriod", wn.getWatchAlarmResetPeriod());
         msg.setString("JMSNotificationName", this.getNotificationName());
         msg.setString("WatchData", wn.getWatchDataToString());
         return msg;
      } catch (Exception var5) {
         DiagnosticsLogger.logJMSNotificationCreateMsgException(var5);
         throw new NotificationPropagationException(var5);
      }
   }
}
