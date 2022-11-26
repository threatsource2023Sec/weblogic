package weblogic.ejb.container.internal;

import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.LinkedList;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerHelper;

public class JMSMessagePoller implements Runnable {
   private static final DebugLogger debugLogger;
   private static final int DEFAULT_EMPTY_QUEUE_WAIT_SECS = 2;
   private static final int SHORT_EMPTY_QUEUE_WAIT = 250;
   private static final String MESSAGE_WAIT_TIME_PROPERTY = "weblogic.ejb.container.MDBMessageWaitTime";
   private static final String TRANSACTION_NAME_PREFIX = "JMSMessagePoller.";
   private static final float MESSAGE_WAIT_TIME_PROPERTY_VALUE;
   private static final int MESSAGE_WAIT_TIME_SECONDS;
   private static final int MESSAGE_WAIT_TIME_ACTUAL;
   private final String mdbName;
   protected final JMSConnectionPoller connectionPoller;
   private MessageConsumer consumer;
   private final TransactionManager tranManager;
   private final MDListener listener;
   private final WorkManager wm;
   private final String transactionName;
   private LinkedList availableChildren;
   private LinkedList allChildren;
   private final JMSMessagePoller parentPoller;
   private volatile boolean keepRunning;
   private boolean isRunning;
   private int errorCount;
   private int jmsErrorCount;
   private int childCount = 0;
   private int childNo = 0;
   private final int id;
   private final boolean dynamicSessionClose;
   private ErrorMessageLoggingSuppressor emsgLoggingSuppressor = new ErrorMessageLoggingSuppressor();

   JMSMessagePoller(String mdbName, JMSConnectionPoller cp, JMSMessagePoller mp, MessageConsumer consumer, MDListener listener, WorkManager wm, int id, boolean dynamicSessionClose) {
      this.mdbName = mdbName;
      this.connectionPoller = cp;
      this.parentPoller = mp;
      this.listener = listener;
      this.consumer = consumer;
      this.tranManager = TransactionService.getWeblogicTransactionManager();
      this.wm = wm;
      this.transactionName = "JMSMessagePoller." + mdbName;
      this.id = id;
      this.dynamicSessionClose = dynamicSessionClose;
   }

   public synchronized void addChild(JMSMessagePoller child) {
      if (this.allChildren == null) {
         this.allChildren = new LinkedList();
      }

      this.allChildren.add(child);
      child.childNo = ++this.childCount;
      if (this.availableChildren == null) {
         this.availableChildren = new LinkedList();
      }

      this.availableChildren.add(child);
   }

   public synchronized void stop() {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Stopping :" + this);
      }

      if (this.keepRunning) {
         this.keepRunning = false;
         if (this.allChildren != null) {
            Iterator var1 = this.allChildren.iterator();

            while(var1.hasNext()) {
               JMSMessagePoller child = (JMSMessagePoller)var1.next();
               child.stop();
            }
         }

      }
   }

   public synchronized void start() {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Starting :" + this);
      }

      this.keepRunning = true;
      if (this.allChildren != null) {
         Iterator var1 = this.allChildren.iterator();

         while(var1.hasNext()) {
            JMSMessagePoller child = (JMSMessagePoller)var1.next();
            child.start();
         }
      }

   }

   public synchronized boolean getRunning() {
      return this.isRunning;
   }

   private synchronized void wakeUpChildPoller() {
      JMSMessagePoller child = this.getChildFromPool();
      if (child != null) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Scheduling work on :" + this.wm + " for child :" + child);
         }

         this.wm.schedule(child);
      }

   }

   synchronized JMSMessagePoller getChildFromPool() {
      return this.availableChildren != null && this.availableChildren.size() > 0 ? (JMSMessagePoller)this.availableChildren.removeFirst() : null;
   }

   private synchronized void returnChildToPool(JMSMessagePoller child) {
      if (this.availableChildren != null) {
         this.availableChildren.addFirst(child);
      }

   }

   boolean processOneMessage(final boolean block, int numMsgsInTran) throws JMSException, NotSupportedException, SystemException, JMSProviderReceiveRuntimeException {
      if (this.consumer == null) {
         assert this.listener.isDetached() : "The MDListener should be deteched when consumer is null";

         JMSConnectionPoller.CreateSessionResult result = this.connectionPoller.dynamicCreateSession(this.id);
         this.consumer = result.consumer;
         this.listener.attach(result.session, result.wrappedSession);
      }

      Message jmsMsg = null;
      Transaction tran = null;
      int lcv;
      if (this.listener.isTransacted()) {
         lcv = this.listener.getTransactionTimeoutSeconds();
         if (block) {
            lcv += MESSAGE_WAIT_TIME_SECONDS;
         }

         tran = this.connectionPoller.beginTransaction(this.tranManager, this.transactionName, lcv);
      }

      try {
         for(lcv = 0; lcv < numMsgsInTran; ++lcv) {
            final int lcv_final = lcv;

            try {
               jmsMsg = (Message)this.connectionPoller.doPrivilegedJMSAction(new PrivilegedExceptionAction() {
                  public Message run() throws JMSException {
                     if (lcv_final == 0) {
                        return block ? JMSMessagePoller.this.consumer.receive((long)JMSMessagePoller.MESSAGE_WAIT_TIME_ACTUAL) : JMSMessagePoller.this.consumer.receive(250L);
                     } else {
                        return JMSMessagePoller.this.consumer.receiveNoWait();
                     }
                  }
               });
            } catch (JMSException var12) {
               throw var12;
            } catch (Throwable var13) {
               throw new JMSProviderReceiveRuntimeException(var13.getMessage(), var13);
            }

            if (jmsMsg == null) {
               if (lcv > 0) {
                  this.listener.transactionalOnMessage((Message)null, true);
                  tran = null;
               }
               break;
            }

            if (debugLogger.isDebugEnabled()) {
               this.debug("Got message " + jmsMsg.getJMSMessageID() + " #" + lcv + " for processing by MDB " + this.mdbName);
            }

            if (block) {
               this.wakeUpChildPoller();
            }

            if (lcv >= numMsgsInTran - 1) {
               this.listener.transactionalOnMessage(jmsMsg, true);
               tran = null;
            } else if (!this.listener.transactionalOnMessage(jmsMsg, false)) {
               tran = null;
               break;
            }
         }
      } finally {
         if (tran != null) {
            tran.rollback();
         }

      }

      return jmsMsg != null;
   }

   public void pollContinuously() {
      if (debugLogger.isDebugEnabled()) {
         this.debug(this + " started polling.");
      }

      boolean isListenerExecuteException = false;
      boolean isJmsProviderReceiveRuntimeException = false;
      int errorsRemaining = 0;
      synchronized(this) {
         this.isRunning = true;
      }

      while(true) {
         while(true) {
            while(this.keepRunning) {
               boolean wasError = false;
               isListenerExecuteException = false;
               isJmsProviderReceiveRuntimeException = false;
               Throwable exceptionForParent = null;
               int batchSize;
               if (errorsRemaining > 0) {
                  batchSize = 1;
                  --errorsRemaining;
               } else {
                  batchSize = this.connectionPoller.getMaxMessagesInTransaction();
               }

               try {
                  if (this.processOneMessage(true, batchSize)) {
                     if (this.listener.getExecuteException() != null) {
                        isListenerExecuteException = true;
                        throw this.listener.getExecuteException();
                     }

                     if (this.listener.getRolledBack()) {
                        wasError = true;
                     }

                     this.emsgLoggingSuppressor.clear();
                  }
               } catch (JMSProviderReceiveRuntimeException var15) {
                  this.emsgLoggingSuppressor.clear();
                  EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var15), StackTraceUtilsClient.throwable2StackTrace(var15));
                  isJmsProviderReceiveRuntimeException = true;
                  exceptionForParent = var15.getCause() == null ? var15 : var15.getCause();
                  wasError = true;
               } catch (JMSException var16) {
                  this.emsgLoggingSuppressor.clear();
                  EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var16), StackTraceUtilsClient.throwable2StackTrace(var16));
                  ++this.jmsErrorCount;
                  exceptionForParent = var16;
                  wasError = true;
               } catch (SystemException var17) {
                  this.emsgLoggingSuppressor.clear();
                  EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var17), StackTraceUtilsClient.throwable2StackTrace(var17));
                  ++this.jmsErrorCount;
                  exceptionForParent = EJBRuntimeUtils.asJMSException(var17.toString(), var17);
                  wasError = true;
               } catch (Throwable var18) {
                  ErrorMessageSuppressCounter ec = this.emsgLoggingSuppressor.checkShouldLog(var18);
                  if (ec != null) {
                     long suppresscnt = ec.getSuppressedCount();
                     if (suppresscnt > 0L) {
                        EJBLogger.logJMSExceptionProcessingMDBWithSuppressCount(JMSConnectionPoller.getAllExceptionText(var18), StackTraceUtilsClient.throwable2StackTrace(var18), suppresscnt);
                     } else {
                        EJBLogger.logJMSExceptionProcessingMDB(JMSConnectionPoller.getAllExceptionText(var18), StackTraceUtilsClient.throwable2StackTrace(var18));
                     }
                  }

                  if (!isListenerExecuteException) {
                     exceptionForParent = var18;
                  }

                  wasError = true;
               }

               if (wasError) {
                  ++this.errorCount;
                  WorkManagerHelper.currentThreadMakingProgress();
                  if (batchSize > 1) {
                     errorsRemaining = batchSize;
                  }

                  if (isJmsProviderReceiveRuntimeException) {
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("JMS provider threw runtime exception on receive call, attempting re-connect");
                     }

                     this.connectionPoller.processOnException((Throwable)exceptionForParent);
                     this.jmsErrorCount = this.errorCount = 0;
                  } else if (this.jmsErrorCount >= 3 && exceptionForParent != null) {
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("Too many JMS related errors, attempting re-connect");
                     }

                     this.connectionPoller.processOnException((Throwable)exceptionForParent);
                     this.jmsErrorCount = 0;
                  } else if (JMSConnectionPoller.MAX_ERROR_COUNT > 0 && this.errorCount >= JMSConnectionPoller.MAX_ERROR_COUNT) {
                     long moreErrorsThanMaxErrorCount = (long)JMSConnectionPoller.MAX_ERROR_COUNT * 2L;
                     if (moreErrorsThanMaxErrorCount > 2147483647L) {
                        moreErrorsThanMaxErrorCount = 2147483647L;
                     }

                     if ((long)this.errorCount >= moreErrorsThanMaxErrorCount && exceptionForParent != null) {
                        if (debugLogger.isDebugEnabled()) {
                           this.debug("Too many errors, attempting re-connect");
                        }

                        this.connectionPoller.processOnException((Throwable)exceptionForParent);
                        this.errorCount = 0;
                     } else {
                        if (debugLogger.isDebugEnabled()) {
                           this.debug("Sleeping after error in MDB poller thread");
                        }

                        try {
                           Thread.sleep((long)JMSConnectionPoller.ERROR_SLEEP_TIME);
                        } catch (InterruptedException var13) {
                        }

                        if (exceptionForParent == null) {
                           this.errorCount = 0;
                        }

                        WorkManagerHelper.currentThreadMakingProgress();
                     }
                  }
               } else {
                  this.errorCount = this.jmsErrorCount = 0;
               }
            }

            if (debugLogger.isDebugEnabled()) {
               this.debug(this + " has stopped");
            }

            synchronized(this) {
               this.isRunning = false;
               this.notifyAll();
               return;
            }
         }
      }
   }

   private void pollForAWhile() {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Child message polling loop started for MDB " + this.mdbName);
      }

      try {
         while(this.keepRunning) {
            if (!this.processOneMessage(false, this.connectionPoller.getMaxMessagesInTransaction())) {
               if (this.dynamicSessionClose) {
                  this.consumer = null;
                  this.listener.detach();
                  this.connectionPoller.dynamicCloseSession(this.id);
               }
               break;
            }

            if (this.listener.getExecuteException() != null) {
               throw this.listener.getExecuteException();
            }

            this.emsgLoggingSuppressor.clear();
            if (this.listener.getRolledBack()) {
               break;
            }

            if (this.wm.scheduleIfBusy(this)) {
               return;
            }
         }
      } catch (JMSException var5) {
         this.emsgLoggingSuppressor.clear();
         EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var5), StackTraceUtilsClient.throwable2StackTrace(var5));
      } catch (Throwable var6) {
         ErrorMessageSuppressCounter ec = this.emsgLoggingSuppressor.checkShouldLog(var6);
         if (ec != null) {
            long suppresscnt = ec.getSuppressedCount();
            if (suppresscnt > 0L) {
               EJBLogger.logJMSExceptionProcessingMDBWithSuppressCount(JMSConnectionPoller.getAllExceptionText(var6), StackTraceUtilsClient.throwable2StackTrace(var6), suppresscnt);
            } else {
               EJBLogger.logJMSExceptionProcessingMDB(JMSConnectionPoller.getAllExceptionText(var6), StackTraceUtilsClient.throwable2StackTrace(var6));
            }
         }
      }

      this.parentPoller.returnChildToPool(this);
      if (debugLogger.isDebugEnabled()) {
         this.debug("Child message polling loop ended for MDB " + this.mdbName);
      }

   }

   void pollForChild() {
      this.pollForAWhile();
   }

   void pollForParent() {
      this.pollContinuously();
   }

   public void run() {
      ManagedInvocationContext mic = this.connectionPoller.setCIC();
      Throwable var2 = null;

      try {
         if (this.parentPoller != null) {
            this.pollForChild();
         } else {
            this.pollForParent();
         }
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   String getMDBName() {
      return this.mdbName;
   }

   MessageConsumer getConsumer() {
      return this.consumer;
   }

   MDListener getMDListener() {
      return this.listener;
   }

   int getMessageWaitTimeSeconds() {
      return MESSAGE_WAIT_TIME_SECONDS;
   }

   int getMessageWaitTimeActual() {
      return MESSAGE_WAIT_TIME_ACTUAL;
   }

   DebugLogger getDebugLogger() {
      return debugLogger;
   }

   int getChildCount() {
      return this.childCount;
   }

   boolean getKeepRunning() {
      return this.keepRunning;
   }

   boolean isParent() {
      return this.parentPoller == null;
   }

   JMSMessagePoller getParentPoller() {
      return this.parentPoller;
   }

   private static float getMessageWaitTimePropertyValue(String propertyName, int defaultValue) {
      String messageWaitTimeStr = System.getProperty(propertyName, Integer.toString(defaultValue));
      float messageWaitTime = (float)defaultValue;

      try {
         messageWaitTime = new Float(messageWaitTimeStr);
      } catch (NumberFormatException var5) {
      }

      return messageWaitTime;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.parentPoller == null) {
         sb.append("[parent poller for " + this.mdbName + " with " + this.childCount + " children. ] ");
      } else {
         sb.append("[child poller for " + this.mdbName + " with childNo :" + this.childNo + ". ]");
      }

      return sb.toString();
   }

   private void debug(String s) {
      debugLogger.debug("[JMSMessagePoller] " + s);
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
      MESSAGE_WAIT_TIME_PROPERTY_VALUE = getMessageWaitTimePropertyValue("weblogic.ejb.container.MDBMessageWaitTime", 2);
      MESSAGE_WAIT_TIME_SECONDS = (int)Math.ceil((double)MESSAGE_WAIT_TIME_PROPERTY_VALUE);
      MESSAGE_WAIT_TIME_ACTUAL = (int)(MESSAGE_WAIT_TIME_PROPERTY_VALUE * 1000.0F);
   }
}
