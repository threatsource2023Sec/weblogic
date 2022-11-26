package weblogic.deployment.jms;

import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import weblogic.utils.wrapper.Wrapper;

public class WrappedJMSConsumer implements Wrapper {
   protected JMSConsumer vendorConsumer;
   protected Object vendorObj;
   protected WrappedSecondaryContext parent;
   private WrappedClassManager wrapperManager;
   private boolean closed;
   private boolean started;

   protected void init(WrappedSecondaryContext parent, JMSConsumer consumer, WrappedClassManager wrapperManager) {
      this.parent = parent;
      this.wrapperManager = wrapperManager;
      this.vendorConsumer = consumer;
      this.vendorObj = consumer;
   }

   protected void closeProviderConsumer() {
      this.vendorConsumer.close();
   }

   private synchronized void checkClosed() {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   protected synchronized void setSecondaryContextStarted(boolean started) {
      this.started = started;
      if (started) {
         this.notifyAll();
      }

   }

   public void setVendorObj(Object o) {
      this.vendorConsumer = (JMSConsumer)o;
      this.vendorObj = o;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) {
      if (!methodName.equals("close")) {
         this.checkClosed();
      }

      if (this.parent.getWrapStyle() != 0 && (methodName.equals("getMessageListener") || methodName.equals("setMessageListener"))) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      return ret;
   }

   public Message receive() {
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
      } catch (JMSRuntimeException var5) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var4) {
         }

         throw var5;
      }

      this.parent.delistFromTransaction(true);
      return this.wrapMessage(msg);
   }

   public Message receive(long timeout) {
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
         } catch (JMSRuntimeException var11) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSRuntimeException var10) {
            }

            throw var11;
         }

         this.parent.delistFromTransaction(true);
         return this.wrapMessage(msg);
      }
   }

   public Message receiveNoWait() {
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
      } catch (JMSRuntimeException var5) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var4) {
         }

         throw var5;
      }

      this.parent.delistFromTransaction(true);
      return this.wrapMessage(msg);
   }

   public Object receiveBody(Class c) {
      this.checkClosed();
      synchronized(this) {
         while(!this.started && !this.closed) {
            try {
               this.wait();
            } catch (InterruptedException var7) {
            }
         }

         if (this.closed) {
            return null;
         }
      }

      this.parent.enlistInTransaction(false);

      Object body;
      try {
         body = this.vendorConsumer.receiveBody(c);
      } catch (JMSRuntimeException var6) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var5) {
         }

         throw var6;
      }

      this.parent.delistFromTransaction(true);
      return body;
   }

   public Object receiveBody(Class c, long timeout) {
      if (timeout <= 0L) {
         return this.receiveBody(c);
      } else {
         this.checkClosed();
         long waitLeft = timeout;
         long expirationTime = System.currentTimeMillis() + timeout;
         synchronized(this) {
            for(; !this.started && !this.closed && waitLeft > 0L; waitLeft = expirationTime - System.currentTimeMillis()) {
               try {
                  this.wait(waitLeft);
               } catch (InterruptedException var13) {
               }
            }

            if (this.closed || waitLeft <= 0L) {
               return null;
            }
         }

         this.parent.enlistInTransaction(false);

         Object body;
         try {
            body = this.vendorConsumer.receiveBody(c, waitLeft);
         } catch (JMSRuntimeException var12) {
            try {
               this.parent.delistFromTransaction(false);
            } catch (JMSRuntimeException var11) {
            }

            throw var12;
         }

         this.parent.delistFromTransaction(true);
         return body;
      }
   }

   public Object receiveBodyNoWait(Class c) {
      this.checkClosed();
      synchronized(this) {
         if (!this.started) {
            return null;
         }
      }

      this.parent.enlistInTransaction(false);

      Object body;
      try {
         body = this.vendorConsumer.receiveBodyNoWait(c);
      } catch (JMSRuntimeException var6) {
         try {
            this.parent.delistFromTransaction(false);
         } catch (JMSRuntimeException var5) {
         }

         throw var6;
      }

      this.parent.delistFromTransaction(true);
      return body;
   }

   public void close() {
      this.setClosed();
      this.parent.consumerClosed(this);
   }

   protected synchronized void setClosed() {
      this.closed = true;
      this.notifyAll();
   }

   private Message wrapMessage(Message vendorMsg) {
      if (vendorMsg == null) {
         return null;
      } else {
         WrappedMessage wrappedMsg;
         try {
            wrappedMsg = (WrappedMessage)this.wrapperManager.getWrappedInstance(23, vendorMsg);
         } catch (JMSException var4) {
            throw new JMSRuntimeException(var4.getMessage(), var4.getErrorCode(), var4);
         }

         wrappedMsg.init(vendorMsg, this.parent);
         return (Message)wrappedMsg;
      }
   }
}
