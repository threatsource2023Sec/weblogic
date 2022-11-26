package com.bea.diagnostics.notifications;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import weblogic.diagnostics.notifications.i18n.NotificationsTextTextFormatter;

public final class JMSNotificationService extends NotificationServiceAdapter implements NotificationConstants {
   private static final String INVALID_JMSDESTINATION_TEXT = NotificationsTextTextFormatter.getInstance().getInvalidJMSDestinationText();
   public String DEFAULT_JMS_CONNECTION_FACTORY_JNDI_NAME;
   private JMSNotificationCustomizer customizer;
   private String destinationJNDIName;
   private String connectionFactoryJNDIName;
   private ConnectionFactory connectionFactory;
   private Connection connection;
   private Destination destination;
   private MessageProducer messageProducer;
   private Session session;
   private Properties jndiProperties;

   JMSNotificationService(String serviceName, String destinationJNDIName, Properties jndiContextProps, JMSNotificationCustomizer customizer) throws NotificationCreateException {
      this(serviceName, destinationJNDIName, (String)null, jndiContextProps, customizer);
   }

   JMSNotificationService(String serviceName, String destinationJNDIName, String connectionFactoryJNDIName, Properties jndiContextProps, JMSNotificationCustomizer customizer) throws NotificationCreateException {
      super(serviceName);
      this.DEFAULT_JMS_CONNECTION_FACTORY_JNDI_NAME = "weblogic.jms.ConnectionFactory";
      this.customizer = customizer;
      this.jndiProperties = jndiContextProps;
      this.destinationJNDIName = destinationJNDIName;
      if (this.destinationJNDIName == null) {
         throw new NotificationCreateException(INVALID_JMSDESTINATION_TEXT);
      } else {
         if (connectionFactoryJNDIName == null) {
            this.connectionFactoryJNDIName = this.DEFAULT_JMS_CONNECTION_FACTORY_JNDI_NAME;
         } else {
            this.connectionFactoryJNDIName = connectionFactoryJNDIName;
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created JMS notification " + this);
         }

      }
   }

   public String getDestinationJNDIName() {
      return this.destinationJNDIName;
   }

   public String getConnectionFactoryJNDIName() {
      return this.connectionFactoryJNDIName;
   }

   public void send(Notification n) throws NotificationPropagationException {
      if (this.isEnabled()) {
         Message msg = null;

         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("JMS Notification service: Handle jms notification for " + this.getName() + ", notif=" + n.toString());
            }

            this.initConnection();
            if (this.customizer != null) {
               msg = this.customizer.createMessage(this.session, n);
            } else {
               msg = this.createObjectMessage(n);
            }

            try {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("JMS Notification service[" + this.getName() + "]: SENDING msg");
               }

               this.messageProducer.send(msg);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("JMS Notification service[" + this.getName() + "]: Message SENT");
               }
            } finally {
               this.tearDownConnection();
            }

         } catch (Throwable var7) {
            throw new NotificationPropagationException(var7);
         }
      }
   }

   private Message createObjectMessage(Notification n) throws Exception {
      ObjectMessage omsg = this.session.createObjectMessage();
      omsg.setObject(n);
      return omsg;
   }

   private void initConnection() {
      try {
         InitialContext ctx = new InitialContext(this.jndiProperties);
         String factoryName = this.connectionFactoryJNDIName;
         this.connectionFactory = (ConnectionFactory)ctx.lookup(factoryName);
         this.connection = this.connectionFactory.createConnection();
         this.session = this.connection.createSession(false, 1);
         this.destination = (Destination)ctx.lookup(this.destinationJNDIName);
         this.messageProducer = this.session.createProducer(this.destination);
         this.connection.start();
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("JMS initialization failed with exception ", var3);
         }

         throw new NotificationRuntimeException(var3);
      }
   }

   public void initialize() {
   }

   public void destroy() {
      super.destroy();
      this.tearDownConnection();
   }

   private void tearDownConnection() {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Destroying JMS Notification service: " + this.getName());
         }

         if (this.connection != null) {
            this.connection.close();
            this.connection = null;
         }

         if (this.messageProducer != null) {
            this.messageProducer.close();
            this.messageProducer = null;
         }

         if (this.session != null) {
            this.session.close();
            this.session = null;
         }

      } catch (Exception var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("JMS notification service " + this.getName() + "destroy() failed with exception ", var2);
         }

         throw new NotificationRuntimeException(var2);
      }
   }

   public String toString() {
      return "JMSNotificationListener[" + this.getName() + " ]: destination JNDI name: " + this.destinationJNDIName + " connectionFactory JNDI name: " + this.connectionFactoryJNDIName + " connectionFactory: " + this.connectionFactory + " connection: " + this.connection + " destination: " + this.destination + " message producer: " + this.messageProducer + " session: " + this.session;
   }

   public String getType() {
      return "JMS";
   }

   protected void finalize() throws Throwable {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("JMSNotificationService.finalize(): cleaning up resources");
      }

      super.finalize();
      this.destroy();
   }
}
