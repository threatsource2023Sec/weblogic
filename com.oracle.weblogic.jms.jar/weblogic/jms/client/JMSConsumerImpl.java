package weblogic.jms.client;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.WLIllegalStateRuntimeException;
import weblogic.jms.common.WLJMSRuntimeException;

public class JMSConsumerImpl implements javax.jms.JMSConsumer {
   JMSContextImpl context;
   WLConsumerImpl messageConsumer;
   boolean closed = false;

   protected void initialiseConsumer(JMSContextImpl context, Destination destination) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createConsumer(destination);
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseConsumer(JMSContextImpl context, Destination destination, String messageSelector) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createConsumer(destination, messageSelector);
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseConsumer(JMSContextImpl context, Destination destination, String messageSelector, boolean noLocal) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createConsumer(destination, messageSelector, noLocal);
      } catch (JMSException var6) {
         throw WLJMSRuntimeException.convertJMSException(var6);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseDurableConsumer(JMSContextImpl context, Topic topic, String name) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createDurableConsumer(topic, name);
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseDurableConsumer(JMSContextImpl context, Topic topic, String name, String messageSelector, boolean noLocal) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createDurableConsumer(topic, name, messageSelector, noLocal);
      } catch (JMSException var7) {
         throw WLJMSRuntimeException.convertJMSException(var7);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseSharedDurableConsumer(JMSContextImpl context, Topic topic, String name) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createSharedDurableConsumer(topic, name);
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseSharedDurableConsumer(JMSContextImpl context, Topic topic, String name, String messageSelector) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createSharedDurableConsumer(topic, name, messageSelector);
      } catch (JMSException var6) {
         throw WLJMSRuntimeException.convertJMSException(var6);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseSharedConsumer(JMSContextImpl context, Topic topic, String sharedSubscriptionName) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createSharedConsumer(topic, sharedSubscriptionName);
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   protected void initialiseSharedConsumer(JMSContextImpl context, Topic topic, String sharedSubscriptionName, String messageSelector) {
      context.checkNotClosed();
      this.context = context;

      try {
         this.messageConsumer = (WLConsumerImpl)context._getSession().createSharedConsumer(topic, sharedSubscriptionName, messageSelector);
      } catch (JMSException var6) {
         throw WLJMSRuntimeException.convertJMSException(var6);
      }

      if (context.getAutoStart()) {
         context.start();
      }

   }

   public String getMessageSelector() {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.getMessageSelector();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public MessageListener getMessageListener() throws JMSRuntimeException {
      if (this.context.getContainerType() == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.checkNotClosed();
         this.context.checkNotClosed();

         try {
            return this.messageConsumer.getMessageListener();
         } catch (JMSException var2) {
            throw WLJMSRuntimeException.convertJMSException(var2);
         }
      }
   }

   public void setMessageListener(MessageListener listener) throws JMSRuntimeException {
      if (this.context.getContainerType() == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.checkNotClosed();
         this.context.checkNotClosed();

         try {
            this.messageConsumer.setMessageListener(listener);
         } catch (JMSException var3) {
            throw WLJMSRuntimeException.convertJMSException(var3);
         }
      }
   }

   public Message receive() {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.receive();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public Message receive(long timeout) {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.receive(timeout);
      } catch (JMSException var4) {
         throw WLJMSRuntimeException.convertJMSException(var4);
      }
   }

   public Message receiveNoWait() {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.receiveNoWait();
      } catch (JMSException var2) {
         throw WLJMSRuntimeException.convertJMSException(var2);
      }
   }

   public Object receiveBody(Class c) {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.receiveBody(c);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public Object receiveBody(Class c, long timeout) {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.receiveBody(c, timeout);
      } catch (JMSException var5) {
         throw WLJMSRuntimeException.convertJMSException(var5);
      }
   }

   public Object receiveBodyNoWait(Class c) {
      this.checkNotClosed();
      this.context.checkNotClosed();

      try {
         return this.messageConsumer.receiveBodyNoWait(c);
      } catch (JMSException var3) {
         throw WLJMSRuntimeException.convertJMSException(var3);
      }
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         this.context.removeConsumer(this);

         try {
            this.messageConsumer.close();
         } catch (JMSException var2) {
            throw WLJMSRuntimeException.convertJMSException(var2);
         }
      }
   }

   protected void checkNotClosed() {
      if (this.closed) {
         throw new WLIllegalStateRuntimeException(JMSClientExceptionLogger.logJMSConsumerIsClosedLoggable());
      }
   }
}
