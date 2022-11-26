package weblogic.deployment.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import weblogic.utils.wrapper.Wrapper;

public class WrappedMessageConsumer implements Wrapper {
   protected MessageConsumer vendorConsumer;
   protected Object vendorObj;
   protected WrappedSession parent;
   private WrappedClassManager wrapperManager;
   private boolean closed;
   private boolean started;

   protected void init(WrappedSession parent, MessageConsumer consumer, WrappedClassManager wrapperManager) throws JMSException {
      this.parent = parent;
      this.wrapperManager = wrapperManager;
      this.vendorConsumer = consumer;
      this.vendorObj = consumer;
   }

   protected void closeProviderConsumer() throws JMSException {
      this.vendorConsumer.close();
   }

   private synchronized void checkClosed() throws JMSException {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   protected synchronized void setConnectionStarted(boolean started) {
      this.started = started;
      if (started) {
         this.notifyAll();
      }

   }

   public void setVendorObj(Object o) {
      this.vendorConsumer = (MessageConsumer)o;
      this.vendorObj = o;
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

      if (this.parent.getWrapStyle() != 0 && (methodName.equals("getMessageListener") || methodName.equals("setMessageListener"))) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }

   public Message receive() throws JMSException {
      this.checkClosed();
      synchronized(this) {
         while(!this.started && !this.closed) {
            try {
               this.wait();
            } catch (InterruptedException var6) {
            }
         }

         if (this.closed) {
            return null;
         }
      }

      this.parent.enlistInTransaction(false);

      Message msg;
      try {
         msg = this.vendorConsumer.receive();
      } catch (JMSException var5) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var4) {
         }

         throw var5;
      }

      this.parent.delistFromTransaction(true);
      return this.wrapMessage(msg);
   }

   public Message receive(long timeout) throws JMSException {
      if (timeout <= 0L) {
         return this.receive();
      } else {
         this.checkClosed();
         long waitLeft = timeout;
         long expirationTime = System.currentTimeMillis() + timeout;
         synchronized(this) {
            for(; !this.started && !this.closed && waitLeft > 0L; waitLeft = expirationTime - System.currentTimeMillis()) {
               try {
                  this.wait(waitLeft);
               } catch (InterruptedException var12) {
               }
            }

            if (this.closed || waitLeft <= 0L) {
               return null;
            }
         }

         this.parent.enlistInTransaction(false);

         Message msg;
         try {
            msg = this.vendorConsumer.receive(waitLeft);
         } catch (JMSException var11) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSException var10) {
            }

            throw var11;
         }

         this.parent.delistFromTransaction(true);
         return this.wrapMessage(msg);
      }
   }

   public Message receiveNoWait() throws JMSException {
      this.checkClosed();
      synchronized(this) {
         if (!this.started) {
            return null;
         }
      }

      this.parent.enlistInTransaction(false);

      Message msg;
      try {
         msg = this.vendorConsumer.receiveNoWait();
      } catch (JMSException var5) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSException var4) {
         }

         throw var5;
      }

      this.parent.delistFromTransaction(true);
      return this.wrapMessage(msg);
   }

   public void close() throws JMSException {
      this.setClosed();
      this.parent.consumerClosed(this);
   }

   protected synchronized void setClosed() {
      this.closed = true;
      this.notifyAll();
   }

   private Message wrapMessage(Message vendorMsg) throws JMSException {
      if (vendorMsg == null) {
         return null;
      } else {
         WrappedMessage wrappedMsg = (WrappedMessage)this.wrapperManager.getWrappedInstance(23, vendorMsg);
         wrappedMsg.init(vendorMsg, this.parent);
         return (Message)wrappedMsg;
      }
   }
}
