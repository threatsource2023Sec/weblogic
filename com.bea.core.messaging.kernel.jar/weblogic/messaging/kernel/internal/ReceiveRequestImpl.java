package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.ReceiveRequest;
import weblogic.security.subject.AbstractSubject;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.transaction.TransactionHelper;

public final class ReceiveRequestImpl extends ReceiveRequest implements Reader, NakedTimerListener, Synchronization {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private QueueImpl queue;
   private Expression expression;
   private Object owner;
   private boolean listening;
   private boolean acknowledge;
   private int count;
   private Timer timer;
   private AbstractSubject subject;
   private String consumerID;

   ReceiveRequestImpl(QueueImpl queue, Expression expression, int count, boolean acknowledge, Object owner, boolean started, long timeout, TimerManager timerManager, String consumerID) {
      assert queue != null;

      this.queue = queue;
      this.count = count;
      this.owner = owner;
      this.expression = expression;
      this.acknowledge = acknowledge;
      this.subject = SecurityHelper.getCurrentSubject();
      this.consumerID = consumerID;

      try {
         if (timeout == 0L) {
            if (started) {
               List result;
               synchronized(queue) {
                  result = queue.get(expression, count, owner, this);
               }

               this.setResult(result);
            } else {
               this.setResult((Object)null);
            }

            return;
         }

         if (started) {
            this.start();
         }

         if (!this.hasResult()) {
            if (timeout < Long.MAX_VALUE) {
               this.timer = timerManager.schedule(this, timeout);
               logger.debug("Timer initiated for blocking receive <" + timeout + ">ms for " + this);
            }

            Transaction tran = TransactionHelper.getTransactionHelper().getTransaction();
            if (tran != null) {
               try {
                  tran.registerSynchronization(this);
               } catch (SystemException var15) {
                  this.deliverNullResult();
               } catch (RollbackException var16) {
                  this.deliverNullResult();
               }
            }
         }
      } catch (KernelException var17) {
         this.setResult(var17);
      }

   }

   public Expression getExpression() {
      return this.expression;
   }

   public synchronized int getCount() {
      return this.count;
   }

   public void incrementReserveCount(int reservation) {
   }

   public Object getOwner() {
      return this.owner;
   }

   public String getConsumerID() {
      return this.consumerID;
   }

   public boolean acknowledge() {
      return this.acknowledge;
   }

   public String getSubjectName() {
      return SecurityHelper.getSubjectName(this.subject);
   }

   public void stop() {
      synchronized(this) {
         if (!this.listening) {
            return;
         }

         this.listening = false;
      }

      this.queue.removeReader(this);
   }

   public void start() throws KernelException {
      synchronized(this) {
         if (this.listening || this.hasResult()) {
            return;
         }

         this.listening = true;
      }

      this.queue.addReader(this);
   }

   private void deliverNullResult() {
      synchronized(this.queue) {
         synchronized(this) {
            if (this.listening) {
               this.queue.removeReader(this);
            }

            this.listening = false;
            if (!this.hasResult()) {
               this.setResult((Object)null);
            }

            this.count = 0;
            if (this.timer != null) {
               this.timer.cancel();
            }
         }

      }
   }

   public void cancel() {
      this.deliverNullResult();
   }

   public int deliver(MessageElement element) {
      ArrayList messages = new ArrayList(1);
      messages.add(element);
      return this.deliver((List)messages);
   }

   public int deliver(List messages) {
      assert Thread.holdsLock(this.queue);

      synchronized(this) {
         this.count = 0;
         if (this.timer != null) {
            this.timer.cancel();
         }

         this.setResult(messages);
         return 0;
      }
   }

   public void timerExpired(Timer t) {
      logger.debug("Timer expired on blocking receive " + this);
      this.deliverNullResult();
   }

   public void afterCompletion(int status) {
      if (status == 4) {
         logger.debug("Transaction rolled back on blocking receive");
         this.startRollback();
      }

   }

   private void startRollback() {
      synchronized(this.queue) {
         synchronized(this) {
            if (this.listening) {
               this.queue.removeReader(this);
            }

            this.listening = false;
            this.count = 0;
            if (this.timer != null) {
               this.timer.cancel();
            }
         }
      }

      synchronized(this) {
         if (!this.hasResult()) {
            KernelException ke = new KernelException("Transaction rolled back on blocking receive");
            this.setResult(ke, false);
         }

      }
   }

   public void beforeCompletion() {
   }
}
