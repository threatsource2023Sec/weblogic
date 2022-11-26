package weblogic.messaging.kernel.internal;

import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.MultiListener;
import weblogic.messaging.kernel.Queue;
import weblogic.security.subject.AbstractSubject;
import weblogic.utils.collections.AbstractEmbeddedListElement;
import weblogic.work.WorkManager;

public final class ListenRequestImpl extends AbstractEmbeddedListElement implements Reader, ListenRequest {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private QueueImpl queue;
   private int count;
   private int reservedCount;
   private Expression expression;
   private boolean acknowledge;
   private Object owner;
   private Listener listener;
   private MultiListener multiListener;
   private AbstractSubject subject;
   private String consumerID;
   private WorkManager workManager;
   private boolean stopped;

   ListenRequestImpl(QueueImpl queue, Expression expression, int count, boolean acknowledge, Object owner, Listener listener, MultiListener multiListener, String consumerID, WorkManager workManager) throws KernelException {
      assert queue != null;

      assert listener != null;

      this.queue = queue;
      this.listener = listener;
      this.multiListener = multiListener;
      this.expression = expression;
      this.acknowledge = acknowledge;
      this.owner = owner;
      this.subject = SecurityHelper.getCurrentSubject();
      this.consumerID = consumerID;
      this.workManager = workManager;
      this.incrementCount(count);
   }

   public Expression getExpression() {
      return this.expression;
   }

   public Object getOwner() {
      return this.owner;
   }

   public String getConsumerID() {
      return this.consumerID;
   }

   public synchronized int getCount() {
      return this.count;
   }

   public boolean acknowledge() {
      return this.acknowledge;
   }

   public String getSubjectName() {
      return SecurityHelper.getSubjectName(this.subject);
   }

   public Queue getQueue() {
      return this.queue;
   }

   public synchronized void incrementReserveCount(int reservation) {
      assert reservation <= 0 || this.count > 0;

      this.reservedCount += reservation;

      assert this.reservedCount >= 0;

      if (this.reservedCount == 0) {
         this.notifyAll();
      }

   }

   public void incrementCount(int increment) throws KernelException {
      assert increment > 0;

      synchronized(this) {
         if (this.stopped) {
            return;
         }

         if ((this.count += increment) > increment) {
            return;
         }
      }

      this.queue.addReader(this);
   }

   public void stop() {
      this.stopInternal(false);
   }

   public void stopAndWait() {
      this.stopInternal(true);
   }

   public void stopInternal(boolean shouldWait) {
      boolean remove;
      synchronized(this) {
         this.stopped = true;
         remove = this.count > 0;
         this.count = 0;
         if (shouldWait) {
            while(this.reservedCount > 0) {
               try {
                  this.wait();
               } catch (InterruptedException var6) {
               }
            }
         }
      }

      if (remove) {
         this.queue.removeReader(this);
      }

   }

   private boolean multiDeliver(MessageElement ref) {
      if (this.multiListener == null) {
         return false;
      } else {
         MultiSender multiSender = ((MessageElementImpl)ref).getMessageReference().getMessageHandle().getMultiSender();
         if (multiSender == null) {
            return false;
         } else {
            multiSender.add(this.multiListener, ref, this.listener);
            return true;
         }
      }
   }

   public int deliver(MessageElement ref) {
      if (logger.isDebugEnabled()) {
         logger.debug("ListenRequestImpl listener " + this.listener + " ref = " + ref);
      }

      this.queue.updateLastMessagesReceivedTime();
      int count;
      Runnable runnable;
      synchronized(this) {
         count = --this.count;
         boolean result = this.multiDeliver(ref);
         if (logger.isDebugEnabled()) {
            logger.debug("ListenRequestImpl  multiDeliver = " + result);
         }

         if (result) {
            return count;
         }

         runnable = this.listener.deliver(this, (MessageElement)ref);
         if (logger.isDebugEnabled()) {
            logger.debug("ListenRequestImpl runnable " + runnable);
         }

         this.incrementReserveCount(-1);
      }

      if (runnable != null) {
         this.workManager.schedule(runnable);
      }

      return count;
   }

   public int deliver(List list) {
      int count;
      Runnable runnable;
      synchronized(this) {
         int numMessages = list.size();
         count = this.count -= numMessages;
         runnable = this.listener.deliver(this, (List)list);
         this.incrementReserveCount(-numMessages);
      }

      this.queue.updateLastMessagesReceivedTime();
      if (runnable != null) {
         this.workManager.schedule(runnable);
      }

      return count;
   }
}
