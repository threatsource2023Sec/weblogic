package weblogic.deployment.jms;

import javax.jms.CompletionListener;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import weblogic.utils.wrapper.Wrapper;

public class WrappedMessageProducer implements Wrapper {
   protected Object vendorObj;
   protected WrappedSession parent;
   protected QueueSender qSender;
   protected TopicPublisher tPublisher;
   protected MessageProducer mProducer;
   private boolean closed;
   private boolean isDestOverride;
   private Destination destOrig;
   private int wrapType;

   protected void init(WrappedSession parent, MessageProducer producer, Destination destOrig, int wrapType) throws JMSException {
      this.parent = parent;
      this.vendorObj = producer;
      this.destOrig = destOrig;
      this.wrapType = wrapType;
      if (producer instanceof QueueSender) {
         this.qSender = (QueueSender)producer;
      }

      if (producer instanceof TopicPublisher) {
         this.tPublisher = (TopicPublisher)producer;
      }

      this.mProducer = producer;
      this.isDestOverride = destOrig != null && JMSSessionHolder.getProducerDest(producer, wrapType) == null;
   }

   int getWrapType() {
      return this.wrapType;
   }

   protected void closeProviderProducer() throws JMSException {
      ((MessageProducer)this.vendorObj).close();
   }

   private synchronized void checkClosed() throws JMSException {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   public void setVendorObj(Object o) {
      this.vendorObj = o;
      if (o instanceof QueueSender) {
         this.qSender = (QueueSender)o;
      }

      if (o instanceof TopicPublisher) {
         this.tPublisher = (TopicPublisher)o;
      }

      if (o instanceof MessageProducer) {
         this.mProducer = (MessageProducer)o;
      }

   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws JMSException {
      if (!methodName.equals("close")) {
         this.checkClosed();
      }

      if (methodName.equals("send") || methodName.equals("publish")) {
         this.parent.enlistInTransaction(true);
      }

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws JMSException {
      if (methodName.equals("send") || methodName.equals("publish")) {
         this.parent.delistFromTransaction(true);
      }

      return ret;
   }

   public void send(Message message) throws JMSException {
      if (this.isDestOverride) {
         this.send(this.destOrig, message);
      } else {
         this.checkClosed();
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            if (this.qSender != null) {
               this.qSender.send(message);
            } else if (this.mProducer != null) {
               this.mProducer.send(message);
            } else {
               ((MessageProducer)this.vendorObj).send(message);
            }
         } catch (JMSException var9) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var8) {
            }

            throw var9;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.isDestOverride) {
         this.send(this.destOrig, message, deliveryMode, priority, timeToLive);
      } else {
         this.checkClosed();
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            if (this.qSender != null) {
               this.qSender.send(message, deliveryMode, priority, timeToLive);
            } else if (this.mProducer != null) {
               this.mProducer.send(message, deliveryMode, priority, timeToLive);
            } else {
               ((MessageProducer)this.vendorObj).send(message, deliveryMode, priority, timeToLive);
            }
         } catch (JMSException var13) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var12) {
            }

            throw var13;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void send(Destination destination, Message message) throws JMSException {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         if (this.qSender != null) {
            this.qSender.send((Queue)destination, message);
         } else if (this.mProducer != null) {
            this.mProducer.send(destination, message);
         } else {
            ((MessageProducer)this.vendorObj).send(destination, message);
         }
      } catch (JMSException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
   }

   public void send(Queue queue, Message message) throws JMSException {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         if (this.qSender == null) {
            throw new JMSException("MessageProducer is null, can't send message");
         }

         this.qSender.send(queue, message);
      } catch (JMSException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
   }

   public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         if (this.qSender != null) {
            this.qSender.send((Queue)destination, message, deliveryMode, priority, timeToLive);
         } else if (this.mProducer != null) {
            this.mProducer.send(destination, message, deliveryMode, priority, timeToLive);
         } else {
            ((MessageProducer)this.vendorObj).send(destination, message, deliveryMode, priority, timeToLive);
         }
      } catch (JMSException var14) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var13) {
         }

         throw var14;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
   }

   public void send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         if (this.qSender == null) {
            throw new JMSException("MessageProducer is null, can't send message");
         }

         this.qSender.send(queue, message, deliveryMode, priority, timeToLive);
      } catch (JMSException var14) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var13) {
         }

         throw var14;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
   }

   public void publish(Message message) throws JMSException {
      if (this.isDestOverride) {
         this.publish((Topic)this.destOrig, message);
      } else {
         this.checkClosed();
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            if (this.tPublisher == null) {
               throw new JMSException("TopicPublisher is null, can't publish message");
            }

            this.tPublisher.publish(message);
         } catch (JMSException var9) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var8) {
            }

            throw var9;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void publish(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      if (this.isDestOverride) {
         this.publish((Topic)this.destOrig, message, deliveryMode, priority, timeToLive);
      } else {
         this.checkClosed();
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            if (this.tPublisher == null) {
               throw new JMSException("TopicPublisher is null, can't publish message");
            }

            this.tPublisher.publish(message, deliveryMode, priority, timeToLive);
         } catch (JMSException var13) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var12) {
            }

            throw var13;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void publish(Topic topic, Message message) throws JMSException {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         if (this.tPublisher == null) {
            throw new JMSException("TopicPublisher is null, can't publish message");
         }

         this.tPublisher.publish(topic, message);
      } catch (JMSException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
   }

   public void publish(Topic topic, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         if (this.tPublisher == null) {
            throw new JMSException("TopicPublisher is null, can't publish message");
         }

         this.tPublisher.publish(topic, message, deliveryMode, priority, timeToLive);
      } catch (JMSException var14) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var13) {
         }

         throw var14;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
   }

   public void send(Message message, CompletionListener completionListener) throws JMSException {
      this.checkClosed();
      if (this.parent.getWrapStyle() != 0) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("send(Message, CompletionListener)"));
      } else {
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            ((MessageProducer)this.vendorObj).send(message, completionListener);
         } catch (JMSException var10) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var9) {
            }

            throw var10;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void send(Destination destination, Message message, CompletionListener completionListener) throws JMSException {
      this.checkClosed();
      if (this.parent.getWrapStyle() != 0) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("send(Destination, Message, CompletionListener)"));
      } else {
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            ((MessageProducer)this.vendorObj).send(destination, message, completionListener);
         } catch (JMSException var11) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var10) {
            }

            throw var11;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void send(Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
      this.checkClosed();
      if (this.parent.getWrapStyle() != 0) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("send(Message, deliveryMode, priority, timeToLive, CompletionListener)"));
      } else {
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            ((MessageProducer)this.vendorObj).send(message, deliveryMode, priority, timeToLive, completionListener);
         } catch (JMSException var14) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var13) {
            }

            throw var14;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
      this.checkClosed();
      if (this.parent.getWrapStyle() != 0) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("send(Destination, Message, deliveryMode, priority, timeToLive, CompletionListener)"));
      } else {
         this.parent.enlistInTransaction(true);
         this.parent.pushSubject();

         try {
            ((MessageProducer)this.vendorObj).send(destination, message, deliveryMode, priority, timeToLive, completionListener);
         } catch (JMSException var15) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var14) {
            }

            throw var15;
         } finally {
            this.parent.popSubject();
         }

         this.parent.delistFromTransaction(true);
      }
   }

   public Queue getQueue() throws JMSException {
      this.checkClosed();
      if (this.isDestOverride) {
         return (Queue)this.destOrig;
      } else if (this.qSender != null) {
         return this.qSender.getQueue();
      } else {
         throw new JMSException("getQueue supported only on QueueSender");
      }
   }

   public Topic getTopic() throws JMSException {
      this.checkClosed();
      if (this.isDestOverride) {
         return (Topic)this.destOrig;
      } else if (this.tPublisher != null) {
         return this.tPublisher.getTopic();
      } else {
         throw new JMSException("getTopic supported only on TopicPublisher");
      }
   }

   public Destination getDestination() throws JMSException {
      this.checkClosed();
      return this.isDestOverride ? this.destOrig : this.mProducer.getDestination();
   }

   public void setDeliveryDelay(long deliveryDelay) throws JMSException {
      try {
         ((MessageProducer)this.vendorObj).setDeliveryDelay(deliveryDelay);
      } catch (AbstractMethodError var4) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("setDeliveryDelay(long deliveryDelay)", "javax.jms.MessageProducer"), new Exception(var4));
      }
   }

   public long getDeliveryDelay() throws JMSException {
      try {
         return ((MessageProducer)this.vendorObj).getDeliveryDelay();
      } catch (AbstractMethodError var2) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("getDeliveryDelay()", "javax.jms.MessageProducer"), new Exception(var2));
      }
   }

   public void close() throws JMSException {
      this.setClosed();
      this.parent.producerClosed(this);
   }

   protected synchronized void setClosed() {
      this.closed = true;
   }
}
