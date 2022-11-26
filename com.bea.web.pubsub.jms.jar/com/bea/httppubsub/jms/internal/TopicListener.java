package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Channel;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.internal.ChannelId;
import com.bea.httppubsub.internal.ChannelManager;
import com.bea.httppubsub.jms.PubSubJmsLogger;
import com.bea.httppubsub.jms.utils.Encoder;
import com.bea.httppubsub.jms.utils.JMSHelper;
import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import weblogic.deployment.jms.WrappedMessageConsumer;

public class TopicListener implements MessageListener {
   private ChannelManager channelManager;
   private String channelPattern;
   private Connection connection;
   private Session session;
   private MessageConsumer consumer;

   public TopicListener(String channelPattern, ChannelManager channelManager) {
      this.channelPattern = channelPattern;
      this.channelManager = channelManager;
   }

   public void initialize(ConnectionFactory connectionFactory, Topic topic) {
      try {
         this.connection = connectionFactory.createConnection();
         this.session = this.connection.createSession(false, 1);
         this.consumer = this.session.createConsumer(topic, Encoder.encode(this.channelPattern) + " IS NOT NULL", true);
         if (this.consumer instanceof WrappedMessageConsumer) {
            ((MessageConsumer)((WrappedMessageConsumer)this.consumer).getVendorObj()).setMessageListener(this);
         } else {
            this.consumer.setMessageListener(this);
         }

         this.connection.start();
      } catch (JMSException var4) {
         PubSubJmsLogger.logCannotInitializeJmstopicListener(var4);
         throw new RuntimeException(PubSubJmsLogger.logCannotInitializeJmstopicListenerLoggable(var4).getMessage(), var4);
      }
   }

   public void destroy() {
      JMSHelper.closeJmsMessageConsumerIfNeccessary(this.consumer);
      JMSHelper.closeJmsSessionIfNecessary(this.session);
      JMSHelper.closeJmsConnectionIfNecessary(this.connection);
   }

   public void onMessage(Message message) {
      BayeuxMessage bayeuxMessage = this.fetchBayeuxMessageFromJmsMessage(message);
      if (bayeuxMessage != null) {
         String url = bayeuxMessage.getChannel();
         Channel channel = this.channelManager.findChannel(url);
         if (channel != null) {
            ChannelId cid = ChannelId.newInstance(url);
            Channel.ChannelPattern pattern = cid.getChannelPattern();

            try {
               channel.publish(bayeuxMessage, pattern);
            } catch (PubSubSecurityException var8) {
               PubSubJmsLogger.logNoPermissionPublish(channel.getName(), var8);
            }
         }
      }

   }

   private BayeuxMessage fetchBayeuxMessageFromJmsMessage(Message jmsMessage) {
      BayeuxMessage bayeuxMessage = null;
      if (jmsMessage instanceof ObjectMessage) {
         ObjectMessage objectMessage = (ObjectMessage)jmsMessage;

         Serializable object;
         try {
            object = objectMessage.getObject();
         } catch (JMSException var6) {
            PubSubJmsLogger.logErrorRetrieveMessageFromJmsTopic(var6);
            return null;
         }

         if (object instanceof BayeuxMessage) {
            bayeuxMessage = (BayeuxMessage)object;
         }
      }

      return bayeuxMessage;
   }

   public boolean equals(Object o) {
      if (!(o instanceof TopicListener)) {
         return false;
      } else {
         TopicListener listener = (TopicListener)o;
         return this.channelPattern.equals(listener.channelPattern);
      }
   }

   public int hashCode() {
      return this.channelPattern.hashCode();
   }
}
