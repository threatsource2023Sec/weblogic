package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.JMXNotificationProducerMBean;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.DiagnosticRuntimeException;
import weblogic.management.ManagementException;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFWatchNotificationSourceRuntimeMBean;

public class JMXNotificationSource extends RuntimeMBeanDelegate implements WLDFWatchNotificationSourceRuntimeMBean, JMXNotificationProducerMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final String WATCH_JMX_NOTIFICATION_SOURCE_NAME = "WatchJMXNotificationSource";
   private NotificationGenerator notificationGenerator;
   private static int sequenceNumber = 0;

   public JMXNotificationSource(RuntimeMBean parent) throws ManagementException {
      super("WatchJMXNotificationSource", parent);
   }

   NotificationGenerator getNotificationGenerator() {
      return this.notificationGenerator;
   }

   public void setNotificationGenerator(NotificationGenerator ng) {
      this.notificationGenerator = ng;
   }

   public long generateSequenceNumber() {
      return (long)(sequenceNumber++);
   }

   public void sendNotification(Notification n) {
      if (this.isSubscribed()) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("WatchJMXNotificationSource sending JMX notification: " + n.toString());
            }

            this.notificationGenerator.sendNotification(n);
         } catch (Exception var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("JMX send notification failed with exception ", var3);
            }

            DiagnosticsLogger.logErrorInNotification(var3);
            throw new DiagnosticRuntimeException(var3);
         }
      }

   }

   public boolean isSubscribed() {
      return this.notificationGenerator != null && this.notificationGenerator.isSubscribed();
   }
}
