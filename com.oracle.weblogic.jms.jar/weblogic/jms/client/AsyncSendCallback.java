package weblogic.jms.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javax.jms.Message;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.frontend.FEProducerSendRequest;
import weblogic.messaging.dispatcher.CompletionListener;

public class AsyncSendCallback {
   private DestinationImpl mydestination;
   private Message messageState;
   private int deliveryModeState;
   private long timeToDeliverState;
   private int priorityState;
   private long timeToLiveState;
   private boolean forwardingState;
   private Object appListener;
   private boolean jmsAsyncOff = false;
   private JMSProducer jmsProducer;
   private boolean sendCallSuccessReturned = false;
   private boolean sendCallReturned = false;
   private boolean completed = false;
   private Throwable exception = null;
   private boolean completing = false;
   private boolean callbackCalled = false;
   private long timeoutTime = 0L;
   private long messageImplSize = 0L;
   private long messageSize = 0L;
   private JMSContext userContext;
   private static volatile Set asyncSendForeignMessages = null;

   public AsyncSendCallback(DestinationImpl destination, Message message, int deliveryMode, long timeToDeliver, int priority, long timeToLive, boolean forwarding, Object appListener, boolean jmsAsyncOff, JMSProducer jmsProducer) {
      this.mydestination = destination;
      this.messageState = message;
      this.deliveryModeState = deliveryMode;
      this.timeToDeliverState = timeToDeliver;
      this.priorityState = priority;
      this.timeToLiveState = timeToLive;
      this.forwardingState = forwarding;
      this.appListener = appListener;
      this.jmsAsyncOff = jmsAsyncOff;
      this.jmsProducer = jmsProducer;
      if (this.appListener instanceof CompletionListener) {
         jmsProducer.incrementWLAsyncSendCount();
      }

      this.userContext = new JMSContext();
   }

   public boolean isJMSAsyncSend() {
      return this.appListener instanceof javax.jms.CompletionListener;
   }

   void startTimeoutTimer() {
      if (this.timeoutTime == 0L) {
         this.timeoutTime = 0L;
      }
   }

   boolean isTimedout() {
      synchronized(this) {
         if (this.completed || this.exception != null) {
            return false;
         }
      }

      if (this.timeoutTime == 0L) {
         return false;
      } else {
         return System.currentTimeMillis() >= this.timeoutTime;
      }
   }

   void setMessageSize(long size, long implSize) {
      synchronized(this) {
         if (implSize > 0L) {
            this.messageImplSize = implSize;
         }

         if (size > 0L) {
            this.messageSize = size;
         }

         if (this.messageSize == 0L && this.messageImplSize > 0L) {
            this.messageSize = this.messageImplSize;
         }
      }

      if (this.appListener instanceof javax.jms.CompletionListener) {
         this.jmsProducer.getSession().incrementAsyncSendPendingSize(this);
      }

   }

   synchronized long getMessageSize() {
      return this.messageSize;
   }

   void onSendCallSuccessReturn() {
      synchronized(this) {
         this.sendCallSuccessReturned = true;
         this.sendCallReturned = true;
         if (this.jmsAsyncOff) {
            this.completed = true;
         }
      }

      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug(this.toDebugString() + ", onSendCallSuccessReturn() called");
      }

      if (this.appListener instanceof javax.jms.CompletionListener) {
         this.jmsProducer.getSession().wakeupAsyncSendCallback();
      }

   }

   void onSendCallReturn() {
      boolean remove = false;
      synchronized(this) {
         if (!this.sendCallSuccessReturned) {
            remove = true;
         }
      }

      if (remove) {
         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug(this.toDebugString() + ", onSendCallReturn() called, send call unsuccessful");
         }

         this.markAsyncSendEnd();
         if (this.appListener instanceof javax.jms.CompletionListener) {
            this.jmsProducer.getSession().dequeueAsyncSendCallback(this);
         }
      }

      synchronized(this) {
         this.sendCallReturned = true;
      }

      if (!remove && this.appListener instanceof javax.jms.CompletionListener) {
         this.jmsProducer.getSession().wakeupAsyncSendCallback();
      }

   }

   public void processCompletion(Object response, FEProducerSendRequest request, DestinationImpl destination, MessageImpl messageImpl) {
      if (this.jmsAsyncOff) {
         JMSClientExceptionLogger.logStackTrace(new RuntimeException(this.toDebugString() + ", processCompletion() unexpected call"));
      } else {
         boolean mycompleting = false;
         synchronized(this) {
            if (!this.completed && this.exception == null && !this.completing) {
               this.completing = true;
               mycompleting = true;
            }
         }

         if (mycompleting) {
            boolean var28 = false;

            try {
               var28 = true;
               Object result = null;

               try {
                  result = this.jmsProducer.completeAsyncSend(response, request, destination, this.messageState, messageImpl, this.deliveryModeState, this.timeToDeliverState, this.priorityState, this.timeToLiveState, this.forwardingState);
               } catch (Throwable var44) {
                  this.processException(var44);
               }

               boolean mycompleted = false;
               synchronized(this) {
                  if (this.exception == null) {
                     this.completed = true;
                     mycompleted = this.completed;
                  }
               }

               if (mycompleted) {
                  if (this.appListener instanceof CompletionListener) {
                     this.jmsProducer.decrementWLAsyncSendCount();

                     try {
                        AutoCloseable cached = this.userContext.pushAll();
                        Throwable var9 = null;

                        try {
                           ((CompletionListener)this.appListener).onCompletion(result);
                        } catch (Throwable var43) {
                           var9 = var43;
                           throw var43;
                        } finally {
                           if (cached != null) {
                              if (var9 != null) {
                                 try {
                                    cached.close();
                                 } catch (Throwable var42) {
                                    var9.addSuppressed(var42);
                                 }
                              } else {
                                 cached.close();
                              }
                           }

                        }

                        var28 = false;
                     } catch (Throwable var46) {
                        JMSClientExceptionLogger.logStackTrace(var46);
                        var28 = false;
                     }
                  } else {
                     this.jmsProducer.getSession().wakeupAsyncSendCallback();
                     var28 = false;
                  }
               } else {
                  var28 = false;
               }
            } finally {
               if (var28) {
                  synchronized(this) {
                     this.completing = false;
                  }
               }
            }

            synchronized(this) {
               this.completing = false;
            }
         }

         if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
            JMSDebug.JMSMessagePath.debug(this.toDebugString() + ", processCompletion() called, mycompleting=" + mycompleting + ", messageImpl.size=" + messageImpl.size());
         }

      }
   }

   public void processException(Throwable ex) {
      Throwable myexception = null;
      synchronized(this) {
         if (!this.completed && this.exception == null) {
            this.exception = ex;
            myexception = this.exception;
         }
      }

      if (myexception != null) {
         if (this.appListener instanceof CompletionListener) {
            this.jmsProducer.decrementWLAsyncSendCount();

            try {
               AutoCloseable cached = this.userContext.pushAll();
               Throwable var4 = null;

               try {
                  ((CompletionListener)this.appListener).onException(myexception);
               } catch (Throwable var15) {
                  var4 = var15;
                  throw var15;
               } finally {
                  if (cached != null) {
                     if (var4 != null) {
                        try {
                           cached.close();
                        } catch (Throwable var14) {
                           var4.addSuppressed(var14);
                        }
                     } else {
                        cached.close();
                     }
                  }

               }
            } catch (Throwable var17) {
               JMSClientExceptionLogger.logStackTrace(var17);
            }
         } else {
            this.jmsProducer.getSession().wakeupAsyncSendCallback();
         }
      }

      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug(this.toDebugString() + ", processException(" + ex + ") called, myexception=" + myexception);
      }

   }

   void callCompletionListener() {
      boolean mycompleted;
      Throwable myexception;
      synchronized(this) {
         if (this.callbackCalled) {
            return;
         }

         if (!this.completed && this.exception == null && this.isTimedout()) {
            this.exception = new TimeoutException("Async send completion wait timed out. " + this.toDebugString());
            this.exception.fillInStackTrace();
         }

         this.callbackCalled = true;
         mycompleted = this.completed;
         myexception = this.exception;
      }

      if (JMSDebug.JMSMessagePath.isDebugEnabled()) {
         JMSDebug.JMSMessagePath.debug(this.toDebugString() + ", callCompletionListener() called");
      }

      if (this.appListener instanceof javax.jms.CompletionListener) {
         assert mycompleted || myexception != null;

         try {
            this.markAsyncSendEnd();
            if (myexception != null) {
               Exception ex;
               if (myexception instanceof Exception) {
                  ex = (Exception)myexception;
               } else {
                  ex = new Exception(myexception.getMessage(), myexception);
               }

               try {
                  AutoCloseable cached = this.userContext.pushAll();
                  Throwable var5 = null;

                  try {
                     ((javax.jms.CompletionListener)this.appListener).onException(this.messageState, ex);
                  } catch (Throwable var50) {
                     var5 = var50;
                     throw var50;
                  } finally {
                     if (cached != null) {
                        if (var5 != null) {
                           try {
                              cached.close();
                           } catch (Throwable var48) {
                              var5.addSuppressed(var48);
                           }
                        } else {
                           cached.close();
                        }
                     }

                  }
               } catch (Throwable var54) {
                  JMSClientExceptionLogger.logStackTrace(var54);
               }
            } else if (mycompleted) {
               try {
                  AutoCloseable cached = this.userContext.pushAll();
                  Throwable var58 = null;

                  try {
                     ((javax.jms.CompletionListener)this.appListener).onCompletion(this.messageState);
                  } catch (Throwable var49) {
                     var58 = var49;
                     throw var49;
                  } finally {
                     if (cached != null) {
                        if (var58 != null) {
                           try {
                              cached.close();
                           } catch (Throwable var47) {
                              var58.addSuppressed(var47);
                           }
                        } else {
                           cached.close();
                        }
                     }

                  }
               } catch (Throwable var52) {
                  JMSClientExceptionLogger.logStackTrace(var52);
               }
            }
         } finally {
            this.jmsProducer.getSession().dequeueAsyncSendCallback(this);
         }

      }
   }

   void enqueued() {
      this.jmsProducer.incrementJMSAsyncSendCount();
   }

   void dequeued() {
      this.jmsProducer.decrementJMSAsyncSendCount();
   }

   synchronized boolean isCallbackPending() {
      return (this.completed || this.exception != null) && this.sendCallReturned;
   }

   void markAsyncSendStart() {
      if (this.messageState instanceof MessageImpl) {
         ((MessageImpl)this.messageState).setInAsyncSend(true);
      } else {
         if (asyncSendForeignMessages == null) {
            Class var1 = AsyncSendCallback.class;
            synchronized(AsyncSendCallback.class) {
               if (asyncSendForeignMessages == null) {
                  asyncSendForeignMessages = Collections.synchronizedSet(new HashSet());
               }
            }
         }

         asyncSendForeignMessages.add(this.messageState);
      }

   }

   private void markAsyncSendEnd() {
      if (this.messageState instanceof MessageImpl) {
         ((MessageImpl)this.messageState).setInAsyncSend(false);
      } else if (asyncSendForeignMessages != null) {
         asyncSendForeignMessages.remove(this.messageState);
      }

   }

   static boolean isMessageInAsyncSend(Message m) {
      if (m instanceof MessageImpl) {
         return ((MessageImpl)m).isInAsyncSend();
      } else {
         return asyncSendForeignMessages == null ? false : asyncSendForeignMessages.contains(new AsyncSendForeignMessage(m));
      }
   }

   String toDebugString() {
      boolean mycompleted;
      Throwable myexception;
      synchronized(this) {
         mycompleted = this.completed;
         myexception = this.exception;
      }

      StringBuffer buf = new StringBuffer();
      buf.append("AsyncSendCallback[producer@" + this.jmsProducer.hashCode() + "[" + this.jmsProducer.getJMSID() + ", " + this.jmsProducer + "], destination=" + this.mydestination);
      if (mycompleted) {
         try {
            buf.append(", " + this.messageState.getJMSMessageID());
         } catch (Exception var5) {
            buf.append(", [message@" + this.messageState.hashCode() + ":" + var5.toString() + "]");
         }
      } else {
         buf.append(", [message@" + this.messageState.hashCode() + "]");
      }

      buf.append(", completed=" + mycompleted + ", exception=" + myexception + ", sendCallSuccessReturn=" + this.sendCallSuccessReturned + ", sendCallReturned=" + this.sendCallReturned + ", jmsAsyncOff=" + this.jmsAsyncOff + ", messageSize=" + this.messageSize + ", messageImplSize=" + this.messageImplSize + "]");
      return buf.toString();
   }

   private static final class AsyncSendForeignMessage {
      Message msg;

      public AsyncSendForeignMessage(Message m) {
         this.msg = m;
      }

      public int hashCode() {
         return this.msg.hashCode();
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            return o == this.msg;
         }
      }
   }
}
