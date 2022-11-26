package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.JMXNotificationCustomizer;
import com.bea.diagnostics.notifications.JMXNotificationProducerMBean;
import com.bea.diagnostics.notifications.JMXNotificationService;
import com.bea.diagnostics.notifications.Notification;
import com.bea.diagnostics.notifications.NotificationRuntimeException;
import com.bea.diagnostics.notifications.NotificationServiceFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFJMXNotificationBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.WLDFWatchJMXNotificationRuntimeMBean;
import weblogic.management.runtime.WLDFWatchNotificationSourceRuntimeMBean;

public final class JMXNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private String notificationType;
   private JMXNotificationService jmxService;
   private JMXNotificationService deprecatedJMXService;

   JMXNotificationListener(WLDFJMXNotificationBean configBean, WatchManagerStatisticsImpl stats) throws ManagementException, InvalidNotificationException, NotificationCreateException {
      super(configBean, stats);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created JMX notification " + this);
      }

      this.setNotificationType(configBean.getNotificationType());
   }

   void activate(WLDFWatchJMXNotificationRuntimeMBean wldfWatchJMXNotificationRuntimeMBean, WLDFWatchNotificationSourceRuntimeMBean wldfWatchNotificationSourceRuntimeMBean) {
      try {
         if (wldfWatchJMXNotificationRuntimeMBean != null) {
            this.deprecatedJMXService = NotificationServiceFactory.getInstance().createJMXNotificationService(this.getNotificationName(), (JMXNotificationProducerMBean)wldfWatchJMXNotificationRuntimeMBean, this.getNotificationType(), new DeprecatedNotificationCustomizer());
         }

         this.jmxService = NotificationServiceFactory.getInstance().createJMXNotificationService(this.getNotificationName(), (JMXNotificationProducerMBean)wldfWatchNotificationSourceRuntimeMBean, this.getNotificationType(), new MapNotificationCustomizer());
         this.jmxService.setNotificationType(this.notificationType);
      } catch (com.bea.diagnostics.notifications.NotificationCreateException var4) {
         throw new NotificationRuntimeException(var4);
      }
   }

   public void processWatchNotification(Notification wn) {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Handle JMX notification for " + this);
            debugLogger.debug("Watch notification: " + wn);
         }

         if (this.jmxService != null) {
            this.jmxService.send(wn);
         }

         if (this.deprecatedJMXService != null) {
            this.deprecatedJMXService.send(wn);
         }

         this.getStatistics().incrementTotalJMXNotificationsPerformed();
      } catch (Throwable var3) {
         this.getStatistics().incrementTotalFailedJMXNotifications();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("JMX send notification failed with exception ", var3);
         }

         DiagnosticsLogger.logErrorInNotification(var3);
      }

   }

   public String toString() {
      return "JMXNotificationListener - notification runtime mbean name: " + this.getNotificationName();
   }

   public void setNotificationType(String notificationType) {
      this.notificationType = notificationType;
   }

   public String getNotificationType() {
      return this.notificationType;
   }

   private class MapNotificationCustomizer implements JMXNotificationCustomizer {
      private MapNotificationCustomizer() {
      }

      public javax.management.Notification createJMXNotification(Notification notif) {
         javax.management.Notification mapNotif = null;
         if (notif instanceof WatchNotificationInternal) {
            WatchNotificationInternal wni = (WatchNotificationInternal)notif;
            mapNotif = new javax.management.Notification(JMXNotificationListener.this.getNotificationType(), wni.getWatchName(), 0L, wni.getWatchTimeMillis(), wni.getMessage());
            Map payload = new HashMap();
            payload.put("WatchName", wni.getWatchName());
            payload.put("WatchModule", wni.getModuleName());
            payload.put("WatchRuleType", wni.getWatchRuleType());
            payload.put("WatchRule", wni.getWatchRule());
            payload.put("WatchTime", wni.getWatchTime());
            payload.put("WatchSeverityLevel", wni.getWatchSeverityLevel());
            payload.put("WatchAlarmType", wni.getWatchAlarmType());
            payload.put("WatchAlarmResetPeriod", wni.getWatchAlarmResetPeriod());
            payload.put("WatchDomainName", wni.getWatchDomainName());
            payload.put("WatchServerName", wni.getWatchServerName());
            payload.put("WatchData", wni.getWatchDataToString());
            mapNotif.setUserData(payload);
         }

         return mapNotif;
      }

      // $FF: synthetic method
      MapNotificationCustomizer(Object x1) {
         this();
      }
   }

   private class DeprecatedNotificationCustomizer implements JMXNotificationCustomizer {
      private DeprecatedNotificationCustomizer() {
      }

      public javax.management.Notification createJMXNotification(Notification notif) {
         JMXWatchNotification jmxNotify = null;
         if (notif instanceof WatchNotificationInternal) {
            WatchNotificationInternal wni = (WatchNotificationInternal)notif;

            try {
               WatchNotification wn = wni.createWatchNotificationExternal();
               jmxNotify = new JMXWatchNotification(JMXNotificationListener.this.getNotificationType(), wn.getWatchName(), 0L, (new Date()).getTime(), wn.getMessage(), wn);
            } catch (Exception var5) {
               throw new NotificationRuntimeException(var5);
            }
         }

         return jmxNotify;
      }

      // $FF: synthetic method
      DeprecatedNotificationCustomizer(Object x1) {
         this();
      }
   }
}
