package com.bea.httppubsub.jms.utils;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

public final class JMSHelper {
   private JMSHelper() {
   }

   public static void closeJmsSessionIfNecessary(Session session) {
      if (session != null) {
         try {
            session.close();
         } catch (JMSException var2) {
         }
      }

   }

   public static void closeJmsConnectionIfNecessary(Connection connection) {
      if (connection != null) {
         try {
            connection.close();
         } catch (JMSException var2) {
         }
      }

   }

   public static void closeJmsMessageProducerIfNecessary(MessageProducer producer) {
      if (producer != null) {
         try {
            producer.close();
         } catch (JMSException var2) {
         }
      }

   }

   public static void closeJmsMessageConsumerIfNeccessary(MessageConsumer consumer) {
      if (consumer != null) {
         try {
            consumer.close();
         } catch (JMSException var2) {
         }
      }

   }
}
