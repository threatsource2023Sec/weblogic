package weblogic.jms.client;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.messaging.dispatcher.CompletionListener;

public class WLProducerImpl extends ReconnectController implements ProducerInternal {
   private WLSessionImpl parent;

   public WLProducerImpl(JMSProducer producer, WLSessionImpl recSession) {
      super(recSession, producer);
      this.parent = recSession;
   }

   protected ReconnectController getParent() {
      return this.parent;
   }

   Object getConnectionStateLock() {
      return this.parent.getConnectionStateLock();
   }

   protected WLConnectionImpl getWLConnectionImpl() {
      return this.parent.getWLConnectionImpl();
   }

   protected JMSConnection getPhysicalJMSConnection() {
      return this.parent.getPhysicalJMSConnection();
   }

   private JMSProducer getPhysicalJMSProducer() {
      return (JMSProducer)this.getPhysical();
   }

   private JMSProducer checkClosedReconnectGetProducer(long startTime, JMSProducer oldProd) throws JMSException {
      return (JMSProducer)this.checkClosedReconnect(startTime, oldProd);
   }

   private JMSProducer checkClosedReconnectGetProducer() throws JMSException {
      return this.checkClosedReconnectGetProducer(System.currentTimeMillis(), (JMSProducer)this.getPhysical());
   }

   public int getCompressionThreshold() throws JMSException {
      return ((JMSProducer)this.getPhysicalWaitForState()).getCompressionThreshold();
   }

   public String getWLSServerName() {
      return ((JMSProducer)this.getPhysicalWaitForState()).getWLSServerName();
   }

   public String getRuntimeMBeanName() {
      return ((JMSProducer)this.getPhysicalWaitForState()).getRuntimeMBeanName();
   }

   public ClientRuntimeInfo getParentInfo() {
      return ((JMSProducer)this.getPhysicalWaitForState()).getParentInfo();
   }

   public Queue getQueue() throws JMSException {
      return (Queue)this.getDestination();
   }

   public void send(Queue queue, Message message) throws JMSException {
      this.send((Destination)queue, (Message)message);
   }

   public void send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.send((Destination)queue, message, deliveryMode, priority, timeToLive);
   }

   public Topic getTopic() throws JMSException {
      return (Topic)this.getDestination();
   }

   public void publish(Message message) throws JMSException {
      this.send(message);
   }

   public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.send(message, deliveryMode, priority, timeToLive);
   }

   public void publish(Topic topic, Message message) throws JMSException {
      this.send((Destination)topic, (Message)message);
   }

   public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.send((Destination)topic, message, deliveryMode, priority, timeToLive);
   }

   public void setSequence(String name) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setSequence(name);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setSequence(name);
      }

   }

   public String getSequence() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getSequence();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getSequence();
      }
   }

   public void reserveUnitOfOrderWithSequence() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.reserveUnitOfOrderWithSequence();
      } catch (weblogic.jms.common.JMSException var5) {
         this.nonIdempotentJMSProducer(startTime, physical, var5).reserveUnitOfOrderWithSequence();
      }

   }

   public void reserveSequence(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.reserveSequence(destination, message, deliveryMode, priority, ttlArg);
      } catch (weblogic.jms.common.JMSException var16) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var16);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.reserveSequence(destination, message, deliveryMode, priority, timeToLive);
      }

   }

   public void releaseSequenceAndUnitOfOrder(boolean fanout) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.releaseSequenceAndUnitOfOrder(fanout);
      } catch (weblogic.jms.common.JMSException var6) {
         this.nonIdempotentJMSProducer(startTime, physical, var6).releaseSequenceAndUnitOfOrder(fanout);
      }

   }

   public void releaseSequenceAndUnitOfOrder(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, boolean fanout) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.releaseSequenceAndUnitOfOrder(destination, message, deliveryMode, priority, ttlArg, fanout);
      } catch (weblogic.jms.common.JMSException var17) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var17);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.releaseSequenceAndUnitOfOrder(destination, message, deliveryMode, priority, timeToLive, fanout);
      }

   }

   public long getTimeToDeliver() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getTimeToDeliver();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getTimeToDeliver();
      }
   }

   public void setTimeToDeliver(long timeToDeliver) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setTimeToDeliver(timeToDeliver);
      } catch (weblogic.jms.common.JMSException var7) {
         this.idempotentJMSProducer(startTime, physical, var7).setTimeToDeliver(timeToDeliver);
      }

   }

   public int getRedeliveryLimit() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getRedeliveryLimit();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getRedeliveryLimit();
      }
   }

   public void setRedeliveryLimit(int redeliveryLimit) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setRedeliveryLimit(redeliveryLimit);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setRedeliveryLimit(redeliveryLimit);
      }

   }

   public long getSendTimeout() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getSendTimeout();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getSendTimeout();
      }
   }

   public void setSendTimeout(long sendTimeout) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setSendTimeout(sendTimeout);
      } catch (weblogic.jms.common.JMSException var7) {
         this.idempotentJMSProducer(startTime, physical, var7).setSendTimeout(sendTimeout);
      }

   }

   public String getUnitOfOrder() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getUnitOfOrder();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getUnitOfOrder();
      }
   }

   public void setUnitOfOrder(String name) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setUnitOfOrder(name);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setUnitOfOrder(name);
      }

   }

   public void setUnitOfOrder() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setUnitOfOrder();
      } catch (weblogic.jms.common.JMSException var5) {
         this.idempotentJMSProducer(startTime, physical, var5).setUnitOfOrder();
      }

   }

   public void forward(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.forward(message, deliveryMode, priority, ttlArg);
      } catch (weblogic.jms.common.JMSException var15) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var15);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.forward(message, deliveryMode, priority, timeToLive);
      }

   }

   public void forward(Message message) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.forward(message);
      } catch (weblogic.jms.common.JMSException var6) {
         this.nonIdempotentJMSProducer(startTime, physical, var6).forward(message);
      }

   }

   public void forward(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.forward(destination, message, deliveryMode, priority, ttlArg);
      } catch (weblogic.jms.common.JMSException var16) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var16);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.forward(destination, message, deliveryMode, priority, timeToLive);
      }

   }

   public void forward(Destination destination, Message message) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.forward(destination, message);
      } catch (weblogic.jms.common.JMSException var7) {
         this.nonIdempotentJMSProducer(startTime, physical, var7).forward(destination, message);
      }

   }

   public void setCompressionThreshold(int limit) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setCompressionThreshold(limit);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setCompressionThreshold(limit);
      }

   }

   public void setDisableMessageID(boolean b) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setDisableMessageID(b);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setDisableMessageID(b);
      }

   }

   public boolean getDisableMessageID() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getDisableMessageID();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getDisableMessageID();
      }
   }

   public void setDisableMessageTimestamp(boolean b) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setDisableMessageTimestamp(b);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setDisableMessageTimestamp(b);
      }

   }

   public boolean getDisableMessageTimestamp() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getDisableMessageTimestamp();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getDisableMessageTimestamp();
      }
   }

   public void setDeliveryMode(int i) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setDeliveryMode(i);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setDeliveryMode(i);
      }

   }

   public int getDeliveryMode() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getDeliveryMode();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getDeliveryMode();
      }
   }

   public void setPriority(int i) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setPriority(i);
      } catch (weblogic.jms.common.JMSException var6) {
         this.idempotentJMSProducer(startTime, physical, var6).setPriority(i);
      }

   }

   public int getPriority() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getPriority();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getPriority();
      }
   }

   public void setTimeToLive(long l) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.setTimeToLive(l);
      } catch (weblogic.jms.common.JMSException var7) {
         this.idempotentJMSProducer(startTime, physical, var7).setTimeToLive(l);
      }

   }

   public long getTimeToLive() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getTimeToLive();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getTimeToLive();
      }
   }

   public Destination getDestination() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         return physical.getDestination();
      } catch (weblogic.jms.common.JMSException var5) {
         return this.idempotentJMSProducer(startTime, physical, var5).getDestination();
      }
   }

   public void send(Message message) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.send(message);
      } catch (weblogic.jms.common.JMSException var6) {
         this.nonIdempotentJMSProducer(startTime, physical, var6).send(message);
      }

   }

   public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.send(message, deliveryMode, priority, ttlArg);
      } catch (weblogic.jms.common.JMSException var15) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var15);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.send(message, deliveryMode, priority, timeToLive);
      }

   }

   public void send(Destination destination, Message message) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.send(destination, message);
      } catch (weblogic.jms.common.JMSException var7) {
         this.nonIdempotentJMSProducer(startTime, physical, var7).send(destination, message);
      }

   }

   public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.send(destination, message, deliveryMode, priority, ttlArg);
      } catch (weblogic.jms.common.JMSException var16) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var16);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.send(destination, message, deliveryMode, priority, timeToLive);
      }

   }

   public void sendAsync(Message message, CompletionListener listener) {
      this.getPhysicalJMSProducer().sendAsync(message, listener);
   }

   public void sendAsync(Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      this.getPhysicalJMSProducer().sendAsync(message, deliveryMode, priority, timeToLive, listener);
   }

   public void sendAsync(Destination destination, Message message, CompletionListener listener) {
      this.getPhysicalJMSProducer().sendAsync(destination, message, listener);
   }

   public void sendAsync(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener listener) {
      this.getPhysicalJMSProducer().sendAsync(destination, message, deliveryMode, priority, timeToLive, listener);
   }

   public void send(Message message, javax.jms.CompletionListener jmsCompletionListener) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.send(message, jmsCompletionListener);
      } catch (weblogic.jms.common.JMSException var7) {
         this.nonIdempotentJMSProducer(startTime, physical, var7).send(message, jmsCompletionListener);
      }

   }

   public void send(Destination destination, Message message, javax.jms.CompletionListener jmsCompletionListener) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSProducer physical = this.checkClosedReconnectGetProducer();

      try {
         physical.send(destination, message, jmsCompletionListener);
      } catch (weblogic.jms.common.JMSException var8) {
         this.nonIdempotentJMSProducer(startTime, physical, var8).send(destination, message, jmsCompletionListener);
      }

   }

   public void send(Message message, int deliveryMode, int priority, long timeToLive, javax.jms.CompletionListener jmsCompletionListener) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.send(message, deliveryMode, priority, ttlArg, jmsCompletionListener);
      } catch (weblogic.jms.common.JMSException var16) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var16);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.send(message, deliveryMode, priority, timeToLive, jmsCompletionListener);
      }

   }

   public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, javax.jms.CompletionListener jmsCompletionListener) throws JMSException {
      long start = System.currentTimeMillis();
      JMSProducer firstProd = this.getPhysicalJMSProducer();
      JMSProducer reconProd = this.checkClosedReconnectGetProducer(start, firstProd);
      long ttlArg = timeToLive;
      if (firstProd != reconProd && timeToLive > 0L) {
         long elapsed = System.currentTimeMillis() - start;
         if (elapsed < timeToLive) {
            ttlArg = timeToLive - elapsed;
         }
      }

      try {
         reconProd.send(destination, message, deliveryMode, priority, ttlArg, jmsCompletionListener);
      } catch (weblogic.jms.common.JMSException var17) {
         reconProd = this.nonIdempotentJMSProducer(start, reconProd, var17);
         if (firstProd != reconProd && timeToLive > 0L) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed < timeToLive) {
               timeToLive -= elapsed;
            }
         }

         reconProd.send(destination, message, deliveryMode, priority, timeToLive);
      }

   }

   public long getDeliveryDelay() throws JMSException {
      return this.getTimeToDeliver();
   }

   public void setDeliveryDelay(long delay) throws JMSException {
      if (delay < 0L) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidJMSDeliveryDelayLoggable().getMessage());
      } else {
         this.setTimeToDeliver(delay);
      }
   }

   public String getPartitionName() {
      return this.getPhysicalJMSConnection().getPartitionName();
   }
}
