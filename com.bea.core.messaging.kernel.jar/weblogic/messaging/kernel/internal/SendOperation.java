package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import weblogic.common.CompletionRequest;
import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.internal.events.MessageSendEventImpl;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXATransaction;

final class SendOperation extends AbstractOperation {
   private SequenceImpl assigningSequence;
   private boolean sequenceLocked;

   SendOperation(int type, QueueImpl queue, MessageReference element, KernelImpl kernel, boolean localTran) {
      super(type, "Kernel SendOperation", kernel, queue, element, SecurityHelper.getCurrentSubject(), localTran);

      assert type == 1 || type == 6;

   }

   SendOperation(int type, QueueImpl queue, MessageReference element, KernelImpl kernel, String subjectName, boolean localTran) {
      super(type, "Kernel SendOperation", kernel, queue, element, subjectName, localTran);

      assert type == 1 || type == 6;

   }

   private void lockSequences() {
      GXAOperation[] gxaops = this.gxaTransaction.getGXAOperations();
      int numops = gxaops.length;
      List sops = new ArrayList();

      for(int i = 0; i < numops; ++i) {
         if (gxaops[i] instanceof SendOperation && ((SendOperation)gxaops[i]).element.getSequenceRef() != null) {
            sops.add((SendOperation)gxaops[i]);
         }
      }

      Collections.sort(sops, new SequenceIdentityComparator());
      Iterator var10 = sops.iterator();

      while(var10.hasNext()) {
         SendOperation sop = (SendOperation)var10.next();
         synchronized(sop) {
            SequenceImpl sequence = null;
            if (sop.element.getSequenceRef() != null) {
               sequence = sop.element.getSequenceRef().getSequence();
            }

            if (sequence.requiresUpdate()) {
               sequence.lock(this.gxaTransaction);
               sop.sequenceLocked = true;
            }
         }
      }

   }

   public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
      super.onInitialize(traceLogger, gxaTransaction, operationWrapper);
      this.element.setState(1);
      this.element.setTransaction(gxaTransaction);
      if ((this.queue.getLogMask() & 1) != 0) {
         try {
            this.addEvent(new MessageSendEventImpl(this.getSubjectName(), this.queue, this.element.getMessage(this.kernel), this.getNonLocalTranForLogging(), this.element.getDeliveryCount()));
         } catch (KernelException var5) {
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
            MessagingLogger.logSendCommitAfterDeactivate(this.kernel.getName());
            return false;
         }
      } else {
         this.isTwoPhase = !isOnePhase;
         if (pass == 1) {
            SequenceImpl sequence = null;
            if (this.element.getSequenceRef() != null) {
               sequence = this.element.getSequenceRef().getSequence();
            }

            if (logger.isDebugEnabled()) {
               logger.debug("SendOperation prepare called for " + this.element + ", " + this.element.getMessageHandle().getMessage());
            }

            try {
               this.assignID();
               PersistenceImpl pers = null;
               PersistentStoreTransaction tran = null;
               if (this.queue.isDurable()) {
                  pers = this.kernel.getPersistence();
                  tran = this.gxaTransaction.getStoreTransaction();
               }

               if ((isOnePhase || this.queue.isSAFImportedDestination() && this.element.getMessageHandle().getGroupName() != null) && sequence != null && sequence.requiresUpdate()) {
                  synchronized(this) {
                     this.assigningSequence = sequence;
                     if (!this.sequenceLocked) {
                        this.lockSequences();
                     }

                     this.queue.updateSequence(tran, this.element, false, 5);
                     if (logger.isDebugEnabled()) {
                        logger.debug("SendOperation.onPrepare() updateSequence isSAF " + this.queue.isSAFImportedDestination() + " group " + this.element.getMessageHandle().getGroupName() + " msg " + this.element.getMessageHandle().getMessage());
                     }
                  }
               }

               if (this.queue.isDurable() && this.element.isPersistent()) {
                  if (this.type == 1) {
                     pers.createQueueMessage(tran, (QueueMessageReference)this.element);
                  } else {
                     MultiMessageReference mRef = (MultiMessageReference)this.element;
                     MultiPersistenceHandle persHandle = mRef.getPersistenceHandle();
                     if (persHandle.getPersistentHandle() == null) {
                        pers.createMultiMessage(tran, persHandle.getMessageHandle(), persHandle);
                     }
                  }

                  if (!isOnePhase) {
                     this.kernel.getPersistence().create2PCRecord(tran, this);
                  }
               }
            } catch (KernelException var12) {
               MessagingLogger.logSendPrepareError(this.kernel.getName(), var12);
               return false;
            }
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

      SequenceImpl sequence;
      if (!isActive) {
         if (isDeleted && pass == 1 && this.queue.isDurable() && this.element.isPersistent() && this.persistentHandle != null) {
            PersistentStoreTransaction tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().delete2PCRecord(tran, this);
         }

         synchronized(this) {
            if (this.sequenceLocked) {
               if (this.element.getSequenceRef() != null) {
                  sequence = this.element.getSequenceRef().getSequence();
                  if (sequence != null) {
                     sequence.unlock(this.gxaTransaction);
                  }
               }

               this.sequenceLocked = false;
            }
         }

         if (!isDeleted && pass != 32) {
            throw new RuntimeException(MessagingLogger.logSendCommitAfterDeactivateLoggable(this.kernel.getName()).getMessage());
         }
      } else {
         boolean updateSequence = true;
         sequence = null;
         if (this.element.getSequenceRef() != null) {
            sequence = this.element.getSequenceRef().getSequence();
            if (this.queue.isSAFImportedDestination() && this.element.getSequenceRef().getSequenceNum() > 0L && this.element.getMessageHandle().getGroupName() != null) {
               if (logger.isDebugEnabled()) {
                  logger.debug("SendOperation.onCommit() updateSequence false");
               }

               updateSequence = false;
            }
         }

         PersistentStoreTransaction tran;
         if (pass == 1 && this.isTwoPhase) {
            tran = null;
            if (this.queue.isDurable()) {
               tran = this.gxaTransaction.getStoreTransaction();
            }

            if (updateSequence && sequence != null && sequence.requiresUpdate()) {
               synchronized(this) {
                  this.assigningSequence = sequence;
                  if (!this.sequenceLocked) {
                     this.lockSequences();
                  }

                  this.queue.updateSequence(tran, this.element, true, 5);
               }
            }

            if (this.queue.isDurable() && this.element.isPersistent()) {
               if (logger.isDebugEnabled()) {
                  logger.debug("SendOperation commit called. Deleting 2PC record for " + this.element);
               }

               this.kernel.getPersistence().delete2PCRecord(tran, this);
            }
         } else if (pass != 21 && pass != 32) {
            if (pass == 3) {
               synchronized(this) {
                  if (this.sequenceLocked && this.assigningSequence != null) {
                     this.assigningSequence.unlock(this.gxaTransaction);
                  }

                  this.sequenceLocked = false;
               }

               if (logger.isDebugEnabled()) {
                  logger.debug("SendOperation commit called. Making message visible " + this.element + " : " + this.element.getMessageHandle().getMessage());
               }

               if (updateSequence && sequence != null && sequence.requiresUpdate() && (this.element.getState() & -38) == 0) {
                  tran = null;
                  if (this.queue.isDurable()) {
                     tran = this.kernel.getPersistence().startStoreTransaction();
                  }

                  synchronized(this) {
                     this.assigningSequence = this.element.getSequenceRef().getSequence();
                     this.assigningSequence.lock(tran);

                     try {
                        this.queue.updateSequence(tran, this.element, true, 5);
                        if (tran != null) {
                           CompletionRequest storeReq = new CompletionRequest();
                           tran.commit(storeReq);
                        }
                     } finally {
                        this.assigningSequence.unlock(tran);
                     }
                  }
               }

               synchronized(this.queue) {
                  this.element.setTransaction((GXATransaction)null);
                  this.element.clearState(1);
                  if (sequence != null && (sequence.getMode() & 8) != 0) {
                     sequence.updateVisibleMessage(this.element, (KernelImpl)null, (PersistentStoreTransaction)null);
                  }

                  synchronized(this) {
                     if ((this.queue.getLogMask() & 1) != 0) {
                        this.logEvents(this.queue);
                     }
                  }

                  try {
                     if (this.assigningSequence != null) {
                        List assignedList = this.assigningSequence.getAssignedMessages(17);
                        if (assignedList != null) {
                           Iterator i = assignedList.iterator();

                           while(i.hasNext()) {
                              this.queue.messageSendComplete((MessageReference)i.next());
                           }
                        }
                     } else {
                        this.queue.messageSendComplete(this.element);
                     }
                  } catch (KernelException var29) {
                     if (logger.isDebugEnabled()) {
                        logger.debug("Error making message available after commit: " + var29, var29);
                     }
                  }
               }
            }
         } else {
            synchronized(this) {
               if (this.sequenceLocked && this.assigningSequence != null) {
                  this.assigningSequence.unlock(this.gxaTransaction);
               }

               this.sequenceLocked = false;
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
         if (isDeleted && pass == 1 && this.queue.isDurable() && this.element.isPersistent() && this.persistentHandle != null) {
            tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().delete2PCRecord(tran, this);
         }

      } else {
         if (pass == 1 && this.isTwoPhase && this.persistentHandle != null) {
            if (logger.isDebugEnabled()) {
               logger.debug("SendOperation rollback called. Deleting " + this.element);
            }

            tran = this.gxaTransaction.getStoreTransaction();
            this.kernel.getPersistence().deleteMessageForRollback(tran, this.element);
            this.kernel.getPersistence().delete2PCRecord(tran, this);
         } else if (pass == 3) {
            if (logger.isDebugEnabled()) {
               logger.debug("SendOperation rollback called. Removing " + this.element);
            }

            synchronized(this.queue) {
               this.element.setTransaction((GXATransaction)null);
               this.queue.remove(this.element);
            }

            synchronized(this) {
               if (this.sequenceLocked) {
                  this.element.getSequenceRef().getSequence().unlock(this.gxaTransaction);
                  this.sequenceLocked = false;
               }
            }
         }

      }
   }

   public String toString() {
      return "SendOperation(" + this.gxaTransaction + "," + this.element + ", " + this.element.getMessageHandle().getMessage() + "," + this.queue.getName() + ")";
   }

   static final class SequenceIdentityComparator implements Comparator {
      public int compare(Object o1, Object o2) {
         SendOperation s1 = (SendOperation)o1;
         SendOperation s2 = (SendOperation)o2;
         SequenceImpl seq1 = s1.element.getSequenceRef().getSequence();
         SequenceImpl seq2 = s2.element.getSequenceRef().getSequence();
         int hashofFirst = System.identityHashCode(seq1);
         int hashofSecond = System.identityHashCode(seq2);
         if (hashofFirst < hashofSecond) {
            return -1;
         } else {
            return hashofFirst > hashofSecond ? 1 : 0;
         }
      }
   }
}
