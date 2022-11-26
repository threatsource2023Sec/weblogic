package com.bea.diagnostics.notifications;

import java.util.Date;
import weblogic.diagnostics.notifications.i18n.NotificationsTextTextFormatter;

public final class JMXNotificationService extends NotificationServiceAdapter implements NotificationConstants {
   private static final String PRODUCER_MBEAN_NOT_SET_TEXT = NotificationsTextTextFormatter.getInstance().getJMXProducerMBeanNotSetText();
   private String notificationType;
   private JMXNotificationProducerMBean notificationProducer = null;
   private JMXNotificationCustomizer customizer;
   private String message;

   JMXNotificationService(String serviceName, JMXNotificationProducerMBean notificationProducer, String jmxNotificationType, JMXNotificationCustomizer customizer) throws NotificationCreateException {
      super(serviceName);
      this.customizer = customizer;
      if (notificationProducer == null) {
         throw new InvalidNotificationException(PRODUCER_MBEAN_NOT_SET_TEXT);
      } else {
         this.notificationProducer = notificationProducer;
         this.notificationType = jmxNotificationType;
         if (this.notificationType == null) {
            this.notificationType = "weblogic.diagnostics.notifications.defaultNotificationType";
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created JMX notification " + this + "Type: " + this.notificationType + ", Customizer: " + customizer.getClass().getName());
         }

      }
   }

   public void send(Notification notif) throws NotificationPropagationException {
      if (this.isEnabled()) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Handle JMX notification for " + this);
            }

            if (this.notificationProducer != null && this.notificationProducer.isSubscribed()) {
               long sequenceNumber = this.notificationProducer.generateSequenceNumber();
               javax.management.Notification jmxNotif = null;
               if (this.customizer != null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Using customizer to create JMX notification");
                  }

                  jmxNotif = this.customizer.createJMXNotification(notif);
                  if (jmxNotif != null) {
                     jmxNotif.setSequenceNumber(sequenceNumber);
                  }
               }

               if (jmxNotif == null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("creating default JMX notification");
                  }

                  String nmsg = null;
                  if (this.message == null) {
                     nmsg = NotificationsTextTextFormatter.getInstance().getDefaultJMXNotificationMessage(this.getName(), sequenceNumber);
                  } else {
                     nmsg = this.message;
                  }

                  String notifType = this.getNotificationType();
                  if (notifType == null) {
                     notifType = "weblogic.diagnostics.notifications.defaultNotificationType";
                  }

                  jmxNotif = new javax.management.Notification(notifType, this.getName(), sequenceNumber, nmsg);
                  jmxNotif.setUserData(notif);
               }

               jmxNotif.setTimeStamp((new Date()).getTime());
               this.notificationProducer.sendNotification(jmxNotif);
            }

         } catch (Throwable var7) {
            throw new NotificationPropagationException(var7);
         }
      }
   }

   public void setNotificationType(String notificationType) {
      this.notificationType = notificationType;
   }

   public String getNotificationType() {
      return this.notificationType;
   }

   public String getType() {
      return "JMX";
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public void destroy() {
      super.destroy();
      this.message = null;
      this.notificationProducer = null;
      this.notificationType = null;
      this.customizer = null;
   }
}
