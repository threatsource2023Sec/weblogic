package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import weblogic.messaging.Message;
import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.messaging.kernel.internal.events.MessageExpirationEventImpl;
import weblogic.messaging.kernel.internal.events.MessageReceiveEventImpl;
import weblogic.messaging.kernel.internal.events.MessageRedeliveryLimitEventImpl;
import weblogic.messaging.kernel.internal.events.MessageRemoveEventImpl;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXATransaction;

final class ReceiveOperation extends AbstractOperation {
   private RedeliveryParameters redeliveryParams;
   private MultiPersistenceHandle lockedHandle;
   private boolean isDeletedByLimit;
   private long millisSinceBeginIfTimedOut;

   ReceiveOperation(int type, QueueImpl queue, MessageReference element, String consumerID, RedeliveryParameters redeliveryParams, KernelImpl kernel, boolean localTran, boolean isDeletedByLimit) {
      super(type, "Kernel ReceiveOperation", kernel, queue, element, SecurityHelper.getCurrentSubject(), localTran);

      assert type == 2 || type == 3 || type == 4 || type == 5;

      this.userID = consumerID;
      this.redeliveryParams = redeliveryParams;
      this.isDeletedByLimit = isDeletedByLimit;
   }

   ReceiveOperation(int type, QueueImpl queue, MessageReference element, String consumerID, RedeliveryParameters redeliveryParams, KernelImpl kernel, String subjectName, boolean localTran) {
      super(type, "Kernel ReceiveOperation", kernel, queue, element, subjectName, localTran);

      assert type == 2 || type == 3 || type == 4 || type == 5;

      this.userID = consumerID;
   }

   private void lockHandles() {
      GXAOperation[] gxaops = this.gxaTransaction.getGXAOperations();
      int numops = gxaops.length;
      List rops = new ArrayList();

      for(int i = 0; i < numops; ++i) {
         if (gxaops[i] instanceof ReceiveOperation) {
            MessageReference element = ((ReceiveOperation)gxaops[i]).element;
            if (element != null & element.isPersistent() && element instanceof MultiMessageReference) {
               rops.add((ReceiveOperation)gxaops[i]);
            }
         }
      }

      Collections.sort(rops, new PersistenceHandleComparator());
      Iterator iter = rops.iterator();

      while(iter.hasNext()) {
         ReceiveOperation rop = (ReceiveOperation)iter.next();
         synchronized(rop) {
            if (rop.element.isPersistent() && rop.element instanceof MultiMessageReference && (MultiMessageReference)rop.element != null) {
               rop.lockedHandle = ((MultiMessageReference)rop.element).getPersistenceHandle();
               rop.lockedHandle.lock(this.gxaTransaction);
            }
         }
      }

   }

   public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
      super.onInitialize(traceLogger, gxaTransaction, operationWrapper);
      if (this.type == 2) {
         this.element.setState(2);
      } else {
         this.element.setState(536870912);
      }

      this.element.setTransaction(gxaTransaction);
      if (gxaTransaction.isRecovered()) {
         try {
            this.queue.moveToPendingList(this.element);
         } catch (KernelException var7) {
            if (logger.isDebugEnabled()) {
               logger.debug("Error making message pending: " + var7, var7);
            }
         }
      }

      if ((this.queue.getLogMask() & 2) != 0) {
         Message message = null;

         try {
            message = this.element.getMessage(this.kernel);
         } catch (KernelException var6) {
         }

         switch (this.type) {
            case 2:
               this.addEvent(new MessageReceiveEventImpl(this.getSubjectName(), this.queue, message, this.getNonLocalTranForLogging(), this.userID, this.element.getDeliveryCount()));
               break;
            case 3:
               this.addEvent(new MessageExpirationEventImpl(this.getSubjectName(), this.queue, message, this.getNonLocalTranForLogging(), this.element.getDeliveryCount(), this.element.getMessageHandle().getExpirationTime()));
               break;
            case 4:
               this.addEvent(new MessageRedeliveryLimitEventImpl(this.getSubjectName(), this.queue, message, this.getNonLocalTranForLogging(), this.element.getMessageHandle().getRedeliveryLimit(), this.element.getDeliveryCount()));
               break;
            case 5:
               this.addEvent(new MessageRemoveEventImpl(this.getSubjectName(), this.queue, message, this.getNonLocalTranForLogging(), this.element.getDeliveryCount()));
               break;
            default:
               throw new AssertionError("Invalid operation type");
         }
      }

   }

   public boolean onPrepare(int pass, boolean isOnePhase) {
      boolean isActive;
      boolean isDeleted;
      synchronized(this.queue) {
         isActive = this.queue.isActive();
         isDeleted = this.queue.isDeleted();
      }

      if (!isActive) {
         if (isDeleted) {
            return true;
         } else {
            MessagingLogger.logReceiveCommitAfterDeactivate(this.kernel.getName());
            return false;
         }
      } else {
         if (pass == 1 && !isOnePhase && this.element.isPersistent()) {
            if (logger.isDebugEnabled()) {
               logger.debug("ReceiveOperation prepare called for " + this.element);
            }

            this.assignID();
            PersistentStoreTransaction tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().create2PCRecord(tran, this);
         }

         return true;
      }
   }

   public void onCommit(int pass) {
      boolean isActive;
      boolean isDeleted;
      synchronized(this.queue) {
         isActive = this.queue.isActive();
         isDeleted = this.queue.isDeleted();
      }

      PersistentStoreTransaction tran;
      if (!isActive) {
         if (isDeleted && pass == 1 && this.element.isPersistent() && this.persistentHandle != null) {
            tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().delete2PCRecord(tran, this);
         }

         synchronized(this) {
            if (this.lockedHandle != null) {
               this.lockedHandle.unlock(this.gxaTransaction);
               this.lockedHandle = null;
            }
         }

         if (!isDeleted) {
            throw new RuntimeException(MessagingLogger.logReceiveCommitAfterDeactivateLoggable(this.kernel.getName()).getMessage());
         }
      } else {
         if (pass == 1 && this.element.isPersistent()) {
            if (logger.isDebugEnabled()) {
               logger.debug("ReceiveOperation commit called. Deleting " + this.element);
            }

            if (this.element instanceof MultiMessageReference) {
               synchronized(this) {
                  if (this.lockedHandle == null) {
                     this.lockHandles();
                  }
               }
            }

            tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().deleteMessage(tran, this.element);
            if (this.persistentHandle != null) {
               this.kernel.getPersistence().delete2PCRecord(tran, this);
            }
         } else if (pass != 21 && pass != 32) {
            if (pass == 3) {
               synchronized(this) {
                  if (this.lockedHandle != null) {
                     this.lockedHandle.unlock(this.gxaTransaction);
                  }

                  this.lockedHandle = null;
               }

               if (logger.isDebugEnabled()) {
                  logger.debug("ReceiveOperation commit called. Removing " + this.element);
               }

               synchronized(this.queue) {
                  this.element.setTransaction((GXATransaction)null);
                  this.queue.remove(this.element);
                  if (this.isDeletedByLimit) {
                     this.queue.decreaseDeletingSize(true);
                  }
               }

               if ((this.queue.getLogMask() & 2) != 0) {
                  this.logEvents(this.queue);
               }
            }
         } else {
            synchronized(this) {
               if (this.lockedHandle != null) {
                  this.lockedHandle.unlock(this.gxaTransaction);
               }

               this.lockedHandle = null;
            }
         }

      }
   }

   public void onRollback(int pass) {
      boolean isActive;
      boolean isDeleted;
      synchronized(this.queue) {
         isActive = this.queue.isActive();
         isDeleted = this.queue.isDeleted();
      }

      PersistentStoreTransaction tran;
      if (!isActive) {
         if (isDeleted && pass == 1 && this.element.isPersistent() && this.persistentHandle != null) {
            tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().delete2PCRecord(tran, this);
         }

      } else {
         if (pass == 1) {
            this.millisSinceBeginIfTimedOut = this.gxaTransaction.getMillisSinceBeginIffTimedOut();
            if (this.persistentHandle != null) {
               if (logger.isDebugEnabled()) {
                  logger.debug("ReceiveOperation rollback called. Deleting 2PC record for " + this.element);
               }

               tran = this.gxaTransaction.getStoreTransaction();
               this.kernel.getPersistence().delete2PCRecord(tran, this);
            }
         } else if (pass == 3) {
            synchronized(this) {
               if (this.lockedHandle != null) {
                  this.lockedHandle.unlock(this.gxaTransaction);
                  this.lockedHandle = null;
               }
            }

            if (logger.isDebugEnabled()) {
               logger.debug("ReceiveOperation rollback called. NACKing " + this.element);
            }

            long delay = 0L;
            if (this.redeliveryParams != null) {
               delay = this.redeliveryParams.getRedeliveryDelay();
            }

            if (delay != 0L) {
               delay = Math.max(delay - this.millisSinceBeginIfTimedOut, 0L);
            }

            synchronized(this.queue) {
               this.element.setTransaction((GXATransaction)null);
               if (this.isDeletedByLimit) {
                  this.queue.decreaseDeletingSize(false);
               }

               try {
                  boolean incrementRedelivered = this.type == 2;
                  this.queue.negativeAcknowledgeInternal(this.element, delay, incrementRedelivered, (String)null);
               } catch (KernelException var10) {
                  if (logger.isDebugEnabled()) {
                     logger.debug("Error rolling back received message: " + var10, var10);
                  }
               }
            }
         }

      }
   }

   public String toString() {
      return "ReceiveOperation(" + this.gxaTransaction + "," + this.element + "," + this.queue + ")";
   }

   static final class PersistenceHandleComparator implements Comparator {
      public int compare(Object o1, Object o2) {
         ReceiveOperation r1 = (ReceiveOperation)o1;
         ReceiveOperation r2 = (ReceiveOperation)o2;
         MultiPersistenceHandle mphandle1 = null;
         MultiPersistenceHandle mphandle2 = null;
         mphandle1 = ((MultiMessageReference)r1.element).getPersistenceHandle();
         mphandle2 = ((MultiMessageReference)r2.element).getPersistenceHandle();
         int hashofFirst = System.identityHashCode(mphandle1);
         int hashofSecond = System.identityHashCode(mphandle2);
         if (hashofFirst < hashofSecond) {
            return -1;
         } else {
            return hashofFirst > hashofSecond ? 1 : 0;
         }
      }
   }
}
