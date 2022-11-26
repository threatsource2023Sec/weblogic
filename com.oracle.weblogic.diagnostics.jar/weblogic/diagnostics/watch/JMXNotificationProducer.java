package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.JMXNotificationProducerMBean;
import javax.management.Notification;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.DiagnosticRuntimeException;
import weblogic.management.ManagementException;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFWatchJMXNotificationRuntimeMBean;

/** @deprecated */
@Deprecated
public final class JMXNotificationProducer extends RuntimeMBeanDelegate implements WLDFWatchJMXNotificationRuntimeMBean, JMXNotificationProducerMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static long sequenceNumber = 0L;
   private NotificationGenerator notificationGenerator;

   public JMXNotificationProducer(WatchNotificationRuntimeMBeanImpl parent) throws ManagementException, InvalidNotificationException {
      super("DiagnosticsJMXNotificationSource", parent);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created JMX notification producer " + this);
      }

   }

   NotificationGenerator getNotificationGenerator() {
      return this.notificationGenerator;
   }

   public void setNotificationGenerator(NotificationGenerator ng) {
      this.notificationGenerator = ng;
   }

   public String toString() {
      return "JMXNotificationProducer - notification runtime mbean name: " + this.getName();
   }

   public void sendNotification(Notification n) {
      if (this.isSubscribed()) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("DiagnosticsJMXNotificationSource sending JMX notification: " + n.toString());
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

   public long generateSequenceNumber() {
      return (long)(sequenceNumber++);
   }

   public boolean isSubscribed() {
      return this.notificationGenerator != null && this.notificationGenerator.isSubscribed();
   }
}
