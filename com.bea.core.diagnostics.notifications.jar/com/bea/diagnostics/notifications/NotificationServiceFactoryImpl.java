package com.bea.diagnostics.notifications;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import weblogic.diagnostics.snmp.agent.SNMPAgent;

class NotificationServiceFactoryImpl extends NotificationServiceFactory {
   private JMXNotificationProducerMBean defaultNotificationProducer;

   public JMXNotificationService createJMXNotificationService(String serviceName, JMXNotificationProducerMBean notificationProducer, String jmxNotificationType, JMXNotificationCustomizer customizer) throws NotificationCreateException {
      JMXNotificationService notificationService = new JMXNotificationService(serviceName, notificationProducer, jmxNotificationType, customizer);
      notificationService.initialize();
      return notificationService;
   }

   public JMXNotificationService createJMXNotificationService(String serviceName, String notifierObjectName, String jmxNotificationType, MBeanServer mbeanServer, String customizerClass) throws NotificationCreateException {
      try {
         JMXNotificationCustomizer customizer = (JMXNotificationCustomizer)createInstance(customizerClass);
         JMXNotificationProducerMBean notificationProducer = this.getNotifierReference(notifierObjectName, mbeanServer);
         return this.createJMXNotificationService(serviceName, notificationProducer, jmxNotificationType, customizer);
      } catch (NotificationCreateException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new NotificationCreateException(var9);
      }
   }

   private JMXNotificationProducerMBean getNotifierReference(String notifierObjectName, MBeanServer mbeanServer) throws MalformedObjectNameException, NotificationCreateException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      JMXNotificationProducerMBean notificationProducer = this.getDefaultNotifier();
      MBeanServer mbs = mbeanServer;
      if (mbeanServer == null) {
         mbs = ManagementFactory.getPlatformMBeanServer();
      }

      ObjectName objectName = null;
      if (notifierObjectName != null) {
         objectName = new ObjectName(notifierObjectName);
      } else {
         objectName = new ObjectName("com.bea:Name=DiagnosticsJMXNotificationSource,Type=JMXNotificationProducer");
      }

      if (!mbs.isRegistered(objectName)) {
         mbs.registerMBean(notificationProducer, objectName);
      } else {
         notificationProducer = (JMXNotificationProducerMBean)MBeanServerInvocationHandler.newProxyInstance(mbs, objectName, JMXNotificationProducerMBean.class, true);
      }

      return notificationProducer;
   }

   public JMSNotificationService createJMSNotificationService(String serviceName, String destinationJNDIName, String connectionFactoryJNDIName, JMSNotificationCustomizer customizer) throws NotificationCreateException {
      return this.createJMSNotificationService(serviceName, destinationJNDIName, connectionFactoryJNDIName, (Properties)null, (JMSNotificationCustomizer)customizer);
   }

   public JMSNotificationService createJMSNotificationService(String serviceName, String destinationJNDIName, String connectionFactoryJNDIName, Properties jndiProperties, String customizerClass) throws NotificationCreateException {
      try {
         JMSNotificationCustomizer customizer = (JMSNotificationCustomizer)createInstance(customizerClass);
         return this.createJMSNotificationService(serviceName, destinationJNDIName, connectionFactoryJNDIName, jndiProperties, customizer);
      } catch (Exception var7) {
         throw new NotificationCreateException(var7);
      }
   }

   public JMSNotificationService createJMSNotificationService(String serviceName, String destinationJNDIName, String connectionFactoryJNDIName, Properties jndiProperties, JMSNotificationCustomizer customizer) throws NotificationCreateException {
      JMSNotificationService notificationService = new JMSNotificationService(serviceName, destinationJNDIName, connectionFactoryJNDIName, jndiProperties, customizer);
      notificationService.initialize();
      return notificationService;
   }

   public SMTPNotificationService createSMTPNotificationService(String serviceName, Session mailSession, String recipientList, String subject, String body, SMTPNotificationCustomizer customizer) throws NotificationCreateException {
      SMTPNotificationService notificationService = new SMTPNotificationService(serviceName, mailSession, recipientList, subject, body, customizer);
      notificationService.initialize();
      return notificationService;
   }

   public SMTPNotificationService createSMTPNotificationService(String serviceName, Properties mailSessionProps, String recipientList, String subject, String body, String customizerClass, String authenticatorClass) throws NotificationCreateException {
      try {
         Session mailSession = null;
         if (authenticatorClass != null) {
            Authenticator auth = (Authenticator)createInstance(authenticatorClass);
            mailSession = Session.getInstance(mailSessionProps, auth);
         } else {
            mailSession = Session.getInstance(mailSessionProps);
         }

         SMTPNotificationCustomizer customizer = (SMTPNotificationCustomizer)createInstance(customizerClass);
         return this.createSMTPNotificationService(serviceName, mailSession, recipientList, subject, body, customizer);
      } catch (Exception var10) {
         throw new NotificationCreateException(var10);
      }
   }

   public SNMPNotificationService createSNMPotificationService(String serviceName, String trapName, SNMPAgent agent, Map varToKeyMap, SNMPNotificationCustomizer customizer) throws NotificationCreateException {
      return new SNMPNotificationService(serviceName, trapName, agent, varToKeyMap, customizer);
   }

   public SNMPNotificationService createSNMPNotificationService(String serviceName, String trapName, String snmpAgent, Map varToKeyMap, String customizerClass) throws NotificationCreateException {
      try {
         SNMPNotificationCustomizer customizer = (SNMPNotificationCustomizer)createInstance(customizerClass);
         return new SNMPNotificationService(serviceName, trapName, snmpAgent, varToKeyMap, customizer);
      } catch (Exception var7) {
         throw new NotificationCreateException(var7);
      }
   }

   public void destroyNotificationService(NotificationService service) {
      if (service != null) {
         service.destroy();
      }

   }

   synchronized JMXNotificationProducerMBean getDefaultNotifier() {
      if (this.defaultNotificationProducer == null) {
         this.defaultNotificationProducer = JMXNotificationProducer.getInstance();
      }

      return this.defaultNotificationProducer;
   }

   public void prepare() {
   }

   public void activate() {
   }

   public void deactivate() {
   }

   static Object createInstance(String className) throws Exception {
      if (className != null) {
         Class c = Class.forName(className);
         Object instance = c.newInstance();
         return instance;
      } else {
         return null;
      }
   }
}
