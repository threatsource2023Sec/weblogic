package com.bea.diagnostics.notifications;

import java.util.Map;
import java.util.Properties;
import javax.mail.Session;
import javax.management.MBeanServer;
import weblogic.diagnostics.snmp.agent.SNMPAgent;

public abstract class NotificationServiceFactory {
   private static final NotificationServiceFactory singleton = new NotificationServiceFactoryImpl();

   public static NotificationServiceFactory getInstance() {
      return singleton;
   }

   public abstract JMXNotificationService createJMXNotificationService(String var1, JMXNotificationProducerMBean var2, String var3, JMXNotificationCustomizer var4) throws NotificationCreateException;

   public abstract JMXNotificationService createJMXNotificationService(String var1, String var2, String var3, MBeanServer var4, String var5) throws NotificationCreateException;

   public abstract JMSNotificationService createJMSNotificationService(String var1, String var2, String var3, JMSNotificationCustomizer var4) throws NotificationCreateException;

   public abstract JMSNotificationService createJMSNotificationService(String var1, String var2, String var3, Properties var4, JMSNotificationCustomizer var5) throws NotificationCreateException;

   public abstract JMSNotificationService createJMSNotificationService(String var1, String var2, String var3, Properties var4, String var5) throws NotificationCreateException;

   public abstract SMTPNotificationService createSMTPNotificationService(String var1, Session var2, String var3, String var4, String var5, SMTPNotificationCustomizer var6) throws NotificationCreateException;

   public abstract SMTPNotificationService createSMTPNotificationService(String var1, Properties var2, String var3, String var4, String var5, String var6, String var7) throws NotificationCreateException;

   public abstract SNMPNotificationService createSNMPotificationService(String var1, String var2, SNMPAgent var3, Map var4, SNMPNotificationCustomizer var5) throws NotificationCreateException;

   public abstract SNMPNotificationService createSNMPNotificationService(String var1, String var2, String var3, Map var4, String var5) throws NotificationCreateException;

   public abstract void destroyNotificationService(NotificationService var1);

   public abstract void prepare();

   public abstract void activate();

   public abstract void deactivate();
}
