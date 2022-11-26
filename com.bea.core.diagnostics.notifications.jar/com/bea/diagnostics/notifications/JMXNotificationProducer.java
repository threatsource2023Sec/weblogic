package com.bea.diagnostics.notifications;

import java.util.concurrent.atomic.AtomicLong;
import javax.management.NotificationBroadcasterSupport;
import weblogic.diagnostics.debug.DebugLogger;

public final class JMXNotificationProducer extends NotificationBroadcasterSupport implements JMXNotificationProducerMBean {
   public static final String DEFAULT_JMX_NOTIFICATION_PRODUCER_NAME = "DiagnosticsJMXNotificationSource";
   public static final String DEFAULT_OBJECT_NAME = "com.bea:Name=DiagnosticsJMXNotificationSource,Type=JMXNotificationProducer";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticNotifications");
   private AtomicLong sequenceNumber = new AtomicLong(0L);
   private static JMXNotificationProducer singleton = null;

   public long generateSequenceNumber() {
      return this.sequenceNumber.getAndIncrement();
   }

   public long nextSequenceNumber() {
      return this.sequenceNumber.get();
   }

   public static synchronized JMXNotificationProducer getInstance() {
      if (singleton == null) {
         try {
            singleton = new JMXNotificationProducer("DiagnosticsJMXNotificationSource");
         } catch (Throwable var1) {
            throw new NotificationRuntimeException(var1);
         }
      }

      return singleton;
   }

   private JMXNotificationProducer(String name) throws InvalidNotificationException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created JMX notification producer " + this);
      }

   }

   public boolean isSubscribed() {
      return true;
   }
}
