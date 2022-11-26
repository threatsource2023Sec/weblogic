package weblogic.deployment.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import weblogic.utils.wrapper.Wrapper;

public abstract class WrappedMessage implements Wrapper {
   protected Message vendorMessage;
   protected Object vendorObj;
   protected WrappedSession parentSession = null;
   protected WrappedSecondaryContext parentContext = null;

   void init(Message vendorMessage, WrappedSession session) {
      this.vendorMessage = vendorMessage;
      this.vendorObj = vendorMessage;
      this.parentSession = session;
   }

   void init(Message vendorMessage, WrappedSecondaryContext context) {
      this.vendorMessage = vendorMessage;
      this.vendorObj = vendorMessage;
      this.parentContext = context;
   }

   public void acknowledge() throws JMSException {
      if (this.parentSession != null) {
         this.parentSession.checkClosed();
      } else {
         this.parentContext.checkClosed();
      }

      this.vendorMessage.acknowledge();
   }

   public Object getBody(Class c) throws JMSException {
      try {
         return ((Message)this.vendorObj).getBody(c);
      } catch (NoSuchMethodError | UnsupportedOperationException | AbstractMethodError var3) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("getBody(Class<T> c))", "javax.jms.Message"), new Exception(var3));
      }
   }

   public long getJMSDeliveryTime() throws JMSException {
      try {
         return ((Message)this.vendorObj).getJMSDeliveryTime();
      } catch (NoSuchMethodError | UnsupportedOperationException | AbstractMethodError var2) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("getJMSDeliveryTime()", "javax.jms.Message"), new Exception(var2));
      }
   }

   public void setJMSDeliveryTime(long deliveryTime) throws JMSException {
      try {
         ((Message)this.vendorObj).setJMSDeliveryTime(deliveryTime);
      } catch (NoSuchMethodError | UnsupportedOperationException | AbstractMethodError var4) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("setJMSDeliveryTime(long deliveryTime)", "javax.jms.Message"), new Exception(var4));
      }
   }

   public void setVendorObj(Object o) {
      this.vendorMessage = (Message)o;
      this.vendorObj = o;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws JMSException {
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }
}
