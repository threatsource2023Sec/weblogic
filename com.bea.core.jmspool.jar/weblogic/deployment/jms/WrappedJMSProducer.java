package weblogic.deployment.jms;

import java.io.Serializable;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import weblogic.utils.wrapper.Wrapper;

public class WrappedJMSProducer implements Wrapper {
   protected Object vendorObj;
   protected WrappedSecondaryContext parent;
   protected JMSProducer jmsProducer;
   private boolean closed;

   protected void init(WrappedSecondaryContext parent, JMSProducer producer) {
      this.parent = parent;
      this.vendorObj = producer;
      this.jmsProducer = producer;
   }

   private synchronized void checkClosed() {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   public void setVendorObj(Object o) {
      this.vendorObj = o;
      this.jmsProducer = (JMSProducer)o;
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

      if (this.parent.getWrapStyle() != 0 && methodName.equals("setAsync")) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws JMSException {
      switch (methodName) {
         case "clearProperties":
         case "setAsync":
         case "setDeliveryDelay":
         case "setDeliveryMode":
         case "setDisableMessageID":
         case "setDisableMessageTimestamp":
         case "setJMSCorrelationID":
         case "setJMSCorrelationIDAsBytes":
         case "setJMSReplyTo":
         case "setJMSType":
         case "setPriority":
         case "setProperty":
         case "setTimeToLive":
         case "forward":
         case "setCompressionThreshold":
         case "setRedeliveryLimit":
         case "setUnitOfOrder":
         case "setSendTimeout":
            return this;
         default:
            return ret;
      }
   }

   public JMSProducer send(Destination destination, byte[] body) {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         this.jmsProducer.send(destination, body);
      } catch (JMSRuntimeException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
      return this.jmsProducer;
   }

   public JMSProducer send(Destination destination, Map body) {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         this.jmsProducer.send(destination, body);
      } catch (JMSRuntimeException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
      return this.jmsProducer;
   }

   public JMSProducer send(Destination destination, Message message) {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         this.jmsProducer.send(destination, message);
      } catch (JMSRuntimeException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
      return this.jmsProducer;
   }

   public JMSProducer send(Destination destination, Serializable body) {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         this.jmsProducer.send(destination, body);
      } catch (JMSRuntimeException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
      return this.jmsProducer;
   }

   public JMSProducer send(Destination destination, String body) {
      this.checkClosed();
      this.parent.enlistInTransaction(true);
      this.parent.pushSubject();

      try {
         this.jmsProducer.send(destination, body);
      } catch (JMSRuntimeException var10) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var9) {
         }

         throw var10;
      } finally {
         this.parent.popSubject();
      }

      this.parent.delistFromTransaction(true);
      return this.jmsProducer;
   }

   protected synchronized void setClosed() {
      this.closed = true;
   }
}
