package weblogic.messaging.kernel.internal;

import weblogic.messaging.kernel.KernelException;
import weblogic.store.gxa.GXATransaction;

public final class DupEliminationSequenceImpl extends SequenceImpl {
   public DupEliminationSequenceImpl() {
   }

   protected DupEliminationSequenceImpl(String name, int sequenceMode, long id, QueueImpl queue) {
      super(name, sequenceMode, id, queue);

      assert (sequenceMode & 2) != 0;

   }

   synchronized boolean sendMessage(GXATransaction transaction, MessageReference ref, long sequenceNum) throws KernelException {
      assert transaction != null;

      if (logger.isDebugEnabled()) {
         logger.debug("Checking sequence number " + sequenceNum + " against " + this.lastValue + " sequence name " + this.name + " on message " + ref.getMessageHandle().getMessage() + ", group=" + ref.getMessageHandle().getGroupName() + " isOverride " + this.isOverride() + " isPoisoned " + this.isPoisoned());
      }

      if (sequenceNum < 0L) {
         throw new KernelException("Invalid sequence number");
      } else if (this.isPoisoned()) {
         throw new KernelException("Sequence number resource unavailable");
      } else if (sequenceNum <= this.lastValue && !this.passthru) {
         ref.setState(256);
         if (logger.isDebugEnabled()) {
            logger.debug("Sequence number " + sequenceNum + " is a duplicate compared to " + this.lastValue + " for sequence " + this.name + " on message " + ref.getMessageHandle().getMessage());
         }

         return true;
      } else {
         boolean needsUnlock = true;

         try {
            this.enlistUpdateOperation(transaction);
            needsUnlock = false;
            this.numberRecord.setNewValue(sequenceNum);
            if (!this.isOverride()) {
               this.addSequenceReference(ref, sequenceNum);
               this.addMessage();
            }
         } finally {
            if (needsUnlock) {
               this.unlock(transaction);
            }

         }

         this.lastValue = sequenceNum;
         if (logger.isDebugEnabled()) {
            logger.debug("Updating sequence lastValue " + this.lastValue + " for sequence " + this.name);
         }

         return false;
      }
   }
}
