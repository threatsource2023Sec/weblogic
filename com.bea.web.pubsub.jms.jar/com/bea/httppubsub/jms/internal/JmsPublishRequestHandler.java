package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.handlers.PublishRequestHandler;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.bayeux.messages.EventMessageImpl;
import com.bea.httppubsub.jms.PubSubJmsLogger;
import com.bea.httppubsub.jms.utils.Encoder;
import com.bea.httppubsub.jms.utils.JMSHelper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import weblogic.deployment.jms.PooledConnection;
import weblogic.work.WorkManagerImpl;

public class JmsPublishRequestHandler extends PublishRequestHandler {
   private ConnectionFactory connectionFactory;
   private Topic topic;
   private String channelPattern;
   private JmsMessageDeliver jmsMessageDeliver;
   private LinkedBlockingQueue messageQueue;

   public void initialize() {
      super.initialize();
      this.messageQueue = new LinkedBlockingQueue();

      try {
         this.jmsMessageDeliver = new JmsMessageDeliver();
         String topicName = this.topic.getTopicName();
         WorkManagerImpl.executeDaemonTask("JMS publisher [" + topicName + "] for PubSub server [" + this.getPubSubServer().getName() + "]", 5, this.jmsMessageDeliver);
      } catch (JMSException var2) {
         PubSubJmsLogger.logCannotInitializeJmsPublishHandler(var2);
         throw new RuntimeException(PubSubJmsLogger.logCannotInitializeJmsPublishHandlerLoggable(var2).getMessage(), var2);
      }
   }

   protected void publish(Channel channel, EventMessageImpl message, Transport transport) throws PubSubSecurityException {
      DeliverEventMessage messageToPublish = new DeliverEventMessage(message);
      message.setSuccessful(true);

      try {
         this.messageQueue.put(messageToPublish);
      } catch (InterruptedException var6) {
         message.setSuccessful(false);
      }

   }

   public void destroy() {
      super.destroy();
      this.jmsMessageDeliver.stop();
      this.messageQueue.clear();
   }

   public void setConnectionFactory(ConnectionFactory connectionFactory) {
      this.connectionFactory = connectionFactory;
   }

   public void setTopic(Topic topic) {
      this.topic = topic;
   }

   public void setChannelPattern(String channelPattern) {
      this.channelPattern = channelPattern;
   }

   class JmsMessageDeliver implements Runnable {
      private int TIMEOUT_SECONDS = 60;
      private Connection connection;
      private Session session;
      private MessageProducer producer;
      private boolean done;

      JmsMessageDeliver() throws JMSException {
         this.connection = JmsPublishRequestHandler.this.connectionFactory.createConnection();
         if (!(this.connection instanceof PooledConnection)) {
            this.connection.setExceptionListener(new ExceptionListener() {
               public void onException(JMSException jmsException) {
                  try {
                     JmsMessageDeliver.this.producer.close();
                     JmsMessageDeliver.this.session.close();
                     JmsMessageDeliver.this.connection.close();
                     JmsMessageDeliver.this.connection = JmsPublishRequestHandler.this.connectionFactory.createConnection();
                     JmsMessageDeliver.this.session = JmsMessageDeliver.this.connection.createSession(false, 1);
                     JmsMessageDeliver.this.producer = JmsMessageDeliver.this.session.createProducer(JmsPublishRequestHandler.this.topic);
                  } catch (JMSException var3) {
                     throw new RuntimeException(var3);
                  }
               }
            });
         }

         this.session = this.connection.createSession(false, 1);
         this.producer = this.session.createProducer(JmsPublishRequestHandler.this.topic);
      }

      public void run() {
         try {
            while(!this.done) {
               try {
                  DeliverEventMessage message = (DeliverEventMessage)JmsPublishRequestHandler.this.messageQueue.poll((long)this.TIMEOUT_SECONDS, TimeUnit.SECONDS);
                  if (message != null) {
                     ObjectMessage jmsMessage = this.session.createObjectMessage();
                     jmsMessage.setObject(message);
                     jmsMessage.setBooleanProperty(Encoder.encode(JmsPublishRequestHandler.this.channelPattern), true);
                     this.producer.send(jmsMessage);
                  }
               } catch (InterruptedException var7) {
               } catch (JMSException var8) {
                  PubSubJmsLogger.logCannotPublishMessageToTopic(var8);
               }
            }
         } finally {
            JMSHelper.closeJmsMessageProducerIfNecessary(this.producer);
            JMSHelper.closeJmsSessionIfNecessary(this.session);
            JMSHelper.closeJmsConnectionIfNecessary(this.connection);
         }

      }

      public void stop() {
         this.done = true;
      }
   }
}
