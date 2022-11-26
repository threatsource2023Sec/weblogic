package weblogic.ejb.container.internal;

import java.security.PrivilegedExceptionAction;
import javax.jms.Destination;
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
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManagerHelper;

final class TokenBasedJMSMessagePoller implements Runnable, TimerListener {
   private static final DebugLogger debugLogger;
   private static final int MESSAGE_RECEIVE_TIMEOUT_SECONDS = 2;
   static final int LAST_SESSION_CLOSE_IDLE_THRESHOLD_MILLIS = 5000;
   private static final String TRANSACTION_NAME_PREFIX = "TokenBasedJMSMessagePoller.";
   private final int id;
   private final String mdbName;
   private final JMSConnectionPoller connectionPoller;
   private volatile MessageConsumer consumer;
   private final TransactionManager txManager;
   private final MDListener listener;
   private final String txName;
   private final JMSPollerManager pm;
   private final boolean reCreateMC;
   private final Destination dest;
   private final boolean dynamicSessionClose;
   private volatile boolean keepRunning;
   private ErrorMessageLoggingSuppressor emsgLoggingSuppressor = new ErrorMessageLoggingSuppressor();

   TokenBasedJMSMessagePoller(int id, String mdbName, JMSConnectionPoller cp, JMSPollerManager pm, MessageConsumer mc, MDListener mdl, boolean reCreateMC, Destination dest, boolean dynamicSessionClose) {
      this.mdbName = mdbName;
      this.connectionPoller = cp;
      this.pm = pm;
      this.listener = mdl;
      this.consumer = mc;
      this.txManager = TransactionService.getWeblogicTransactionManager();
      this.txName = "TokenBasedJMSMessagePoller." + mdbName;
      this.id = id;
      this.reCreateMC = reCreateMC;
      this.dest = dest;
      this.dynamicSessionClose = dynamicSessionClose;
   }

   public void stop() {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Stopping : TokenBasedJMSMessagePoller@" + this.hashCode() + "[" + this + "]");
      }

      this.keepRunning = false;
   }

   public void start() {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Starting : TokenBasedJMSMessagePoller@" + this.hashCode() + "[" + this + "]");
      }

      this.keepRunning = true;
   }

   public void run() {
      ManagedInvocationContext mic = this.connectionPoller.setCIC();
      Throwable var2 = null;

      try {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Message polling started for MDB " + this.mdbName + " poller " + this.id);
         }

         boolean wasError = false;
         boolean isListenerExecuteException = false;
         boolean isJmsProviderReceiveRuntimeException = false;
         Throwable exceptionForParent = null;
         int errorCount = 0;
         int jmsErrorCount = 0;
         int MY_DESTINATION_POLL_INTERVAL_MILLIS = this.connectionPoller.info.getMdbDestinationPollIntervalMillis();
         boolean receiveNoWait = this.connectionPoller.isAQJMS() && this.connectionPoller.info.isReceiveNoWaitAQ();
         if (receiveNoWait && this.connectionPoller.info.getMdbDestinationPollIntervalMillis() == 0) {
            MY_DESTINATION_POLL_INTERVAL_MILLIS = 10;
         }

         while(this.keepRunning) {
            wasError = false;
            isListenerExecuteException = false;
            isJmsProviderReceiveRuntimeException = false;
            exceptionForParent = null;
            int batchSize = this.pm.getBatchSize(this.connectionPoller.getMaxMessagesInTransaction());

            try {
               if (this.consumer == null) {
                  assert this.listener.isDetached() : "The MDListener should be deteched when consumer is null";

                  JMSConnectionPoller.CreateSessionResult result = this.connectionPoller.dynamicCreateSession(this.id);
                  this.consumer = result.consumer;
                  this.listener.attach(result.session, result.wrappedSession);
               }

               if (this.processOneMessage(batchSize, 2, receiveNoWait)) {
                  if (this.listener.getExecuteException() != null) {
                     isListenerExecuteException = true;
                     throw this.listener.getExecuteException();
                  }

                  if (this.listener.getRolledBack()) {
                     wasError = true;
                  }

                  this.emsgLoggingSuppressor.clear();
                  if (this.keepRunning && this.pm.scheduleIfBusy(this)) {
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("Got scheduled as server is busy, poller : " + this);
                     }

                     return;
                  }
               } else {
                  if (!this.pm.holdsToken(this.id) && !this.pm.acquireToken(this.id)) {
                     if (!this.keepRunning) {
                        break;
                     }

                     if (this.dynamicSessionClose) {
                        this.listener.detach();

                        try {
                           this.connectionPoller.dynamicCloseSession(this.id);
                        } catch (Throwable var35) {
                           if (debugLogger.isDebugEnabled()) {
                              this.debug("Failed to dynamic close session:" + var35);
                           }
                        }

                        this.consumer = null;
                        break;
                     }

                     if (this.reCreateMC) {
                        this.consumer = this.connectionPoller.reCreateMessageConsumer(this.dest, this.id);
                     }
                     break;
                  }

                  if (MY_DESTINATION_POLL_INTERVAL_MILLIS != 0) {
                     if (MY_DESTINATION_POLL_INTERVAL_MILLIS > 5000 && this.dynamicSessionClose) {
                        this.listener.detach();

                        try {
                           this.connectionPoller.dynamicCloseSession(this.id);
                        } catch (Throwable var34) {
                           if (debugLogger.isDebugEnabled()) {
                              this.debug("Failed to dynamic close session:" + var34);
                           }
                        }

                        this.consumer = null;
                     }

                     this.pm.scheduleTimer(this, (long)MY_DESTINATION_POLL_INTERVAL_MILLIS);
                     return;
                  }

                  if (this.pm.scheduleIfBusy(this)) {
                     return;
                  }
               }
            } catch (JMSProviderReceiveRuntimeException var36) {
               this.emsgLoggingSuppressor.clear();
               EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var36), StackTraceUtilsClient.throwable2StackTrace(var36));
               isJmsProviderReceiveRuntimeException = true;
               exceptionForParent = var36.getCause() == null ? var36 : var36.getCause();
               wasError = true;
            } catch (JMSException var37) {
               this.emsgLoggingSuppressor.clear();
               EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var37), StackTraceUtilsClient.throwable2StackTrace(var37));
               ++jmsErrorCount;
               exceptionForParent = var37;
               wasError = true;
            } catch (SystemException var38) {
               this.emsgLoggingSuppressor.clear();
               EJBLogger.logJMSExceptionReceivingForMDB(JMSConnectionPoller.getAllExceptionText(var38), StackTraceUtilsClient.throwable2StackTrace(var38));
               ++jmsErrorCount;
               exceptionForParent = EJBRuntimeUtils.asJMSException(var38.toString(), var38);
               wasError = true;
            } catch (Throwable var39) {
               ErrorMessageSuppressCounter ec = this.emsgLoggingSuppressor.checkShouldLog(var39);
               if (ec != null) {
                  long suppresscnt = ec.getSuppressedCount();
                  if (suppresscnt > 0L) {
                     EJBLogger.logJMSExceptionProcessingMDBWithSuppressCount(JMSConnectionPoller.getAllExceptionText(var39), StackTraceUtilsClient.throwable2StackTrace(var39), suppresscnt);
                  } else {
                     EJBLogger.logJMSExceptionProcessingMDB(JMSConnectionPoller.getAllExceptionText(var39), StackTraceUtilsClient.throwable2StackTrace(var39));
                  }
               }

               if (!isListenerExecuteException) {
                  exceptionForParent = var39;
               }

               wasError = true;
            }

            if (wasError) {
               ++errorCount;
               WorkManagerHelper.currentThreadMakingProgress();
               this.pm.notifyError(batchSize);
               if (isJmsProviderReceiveRuntimeException) {
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("JMS provider threw runtime exception on receive call, attempting re-connect");
                  }

                  this.connectionPoller.processOnException((Throwable)exceptionForParent);
                  errorCount = 0;
                  jmsErrorCount = 0;
               } else if (jmsErrorCount >= 3 && exceptionForParent != null) {
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("Too many JMS related errors, attempting re-connect");
                  }

                  this.connectionPoller.processOnException((Throwable)exceptionForParent);
                  jmsErrorCount = 0;
               } else if (JMSConnectionPoller.MAX_ERROR_COUNT > 0 && errorCount >= JMSConnectionPoller.MAX_ERROR_COUNT) {
                  long moreErrorsThanMaxErrorCount = (long)JMSConnectionPoller.MAX_ERROR_COUNT * 2L;
                  if (moreErrorsThanMaxErrorCount > 2147483647L) {
                     moreErrorsThanMaxErrorCount = 2147483647L;
                  }

                  if ((long)errorCount >= moreErrorsThanMaxErrorCount && exceptionForParent != null) {
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("Too many errors, attempting re-connect");
                     }

                     this.connectionPoller.processOnException((Throwable)exceptionForParent);
                     errorCount = 0;
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        this.debug("Sleeping after error in MDB poller thread");
                     }

                     try {
                        Thread.sleep((long)JMSConnectionPoller.ERROR_SLEEP_TIME);
                     } catch (InterruptedException var33) {
                     }

                     if (exceptionForParent == null) {
                        errorCount = 0;
                     }

                     WorkManagerHelper.currentThreadMakingProgress();
                  }
               }
            } else {
               jmsErrorCount = 0;
               errorCount = 0;
            }
         }

         this.pm.releaseToken(this.id);
         this.pm.returnToPool(this);
         if (debugLogger.isDebugEnabled()) {
            this.debug("Message polling ended for MDB " + this.mdbName + " poller " + this.id);
         }

      } catch (Throwable var40) {
         var2 = var40;
         throw var40;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var32) {
                  var2.addSuppressed(var32);
               }
            } else {
               mic.close();
            }
         }

      }
   }

   public void timerExpired(Timer timer) {
      this.run();
   }

   private boolean processOneMessage(int numMsgsInTx, final int waitTimeSeconds, final boolean receiveNoWait) throws JMSException, NotSupportedException, SystemException, JMSProviderReceiveRuntimeException {
      Message jmsMsg = null;
      Transaction tx = null;
      int i;
      if (this.listener.isTransacted()) {
         i = this.listener.getTransactionTimeoutSeconds() + waitTimeSeconds;
         tx = this.connectionPoller.beginTransaction(this.txManager, this.txName, i);
      }

      try {
         for(i = 0; i < numMsgsInTx; ++i) {
            final int lcv_final = i;

            try {
               jmsMsg = (Message)this.connectionPoller.doPrivilegedJMSAction(new PrivilegedExceptionAction() {
                  public Message run() throws JMSException {
                     return lcv_final == 0 && !receiveNoWait ? TokenBasedJMSMessagePoller.this.consumer.receive((long)waitTimeSeconds * 1000L) : TokenBasedJMSMessagePoller.this.consumer.receiveNoWait();
                  }
               });
            } catch (JMSException var13) {
               throw var13;
            } catch (Throwable var14) {
               throw new JMSProviderReceiveRuntimeException(var14.getMessage(), var14);
            }

            if (jmsMsg == null) {
               if (i > 0) {
                  this.listener.transactionalOnMessage((Message)null, true);
                  tx = null;
               }
               break;
            }

            if (debugLogger.isDebugEnabled()) {
               this.debug("Got message " + jmsMsg.getJMSMessageID() + " #" + i + " for processing by MDB " + this.mdbName);
            }

            if (this.pm.holdsToken(this.id)) {
               this.pm.wakeUpPoller(this, (TokenBasedJMSMessagePoller)null);
            }

            if (i >= numMsgsInTx - 1) {
               this.listener.transactionalOnMessage(jmsMsg, true);
               tx = null;
            } else if (!this.listener.transactionalOnMessage(jmsMsg, false)) {
               tx = null;
               break;
            }
         }
      } finally {
         if (tx != null) {
            tx.rollback();
         }

      }

      return jmsMsg != null;
   }

   int getId() {
      return this.id;
   }

   public String toString() {
      return "[Poller for " + this.mdbName + " with id :" + this.id + ". ]";
   }

   private void debug(String s) {
      debugLogger.debug("[TokenBasedJMSMessagePoller] " + s);
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
   }
}
