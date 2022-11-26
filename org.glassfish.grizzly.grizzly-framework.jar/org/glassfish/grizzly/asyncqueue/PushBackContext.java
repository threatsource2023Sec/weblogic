package org.glassfish.grizzly.asyncqueue;

/** @deprecated */
public abstract class PushBackContext {
   protected final AsyncWriteQueueRecord queueRecord;

   public PushBackContext(AsyncWriteQueueRecord queueRecord) {
      this.queueRecord = queueRecord;
   }

   public PushBackHandler getPushBackHandler() {
      return this.queueRecord.getPushBackHandler();
   }

   public final long size() {
      return this.queueRecord.remaining();
   }

   public abstract void retryWhenPossible();

   public abstract void retryNow();

   public abstract void cancel();
}
