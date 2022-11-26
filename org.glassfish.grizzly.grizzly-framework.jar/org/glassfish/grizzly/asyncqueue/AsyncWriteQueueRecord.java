package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.utils.DebugPoint;

public class AsyncWriteQueueRecord extends AsyncQueueRecord {
   public static final int UNCOUNTABLE_RECORD_SPACE_VALUE = 1;
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(AsyncWriteQueueRecord.class, Writer.Reentrant.getMaxReentrants());
   private long initialMessageSize;
   private boolean isUncountable;
   private Object dstAddress;
   private PushBackHandler pushBackHandler;
   private final RecordWriteResult writeResult = new RecordWriteResult();

   public static AsyncWriteQueueRecord create(Connection connection, WritableMessage message, CompletionHandler completionHandler, Object dstAddress, PushBackHandler pushbackHandler, boolean isUncountable) {
      AsyncWriteQueueRecord asyncWriteQueueRecord = (AsyncWriteQueueRecord)ThreadCache.takeFromCache(CACHE_IDX);
      if (asyncWriteQueueRecord != null) {
         asyncWriteQueueRecord.isRecycled = false;
         asyncWriteQueueRecord.set(connection, message, completionHandler, dstAddress, pushbackHandler, isUncountable);
         return asyncWriteQueueRecord;
      } else {
         return new AsyncWriteQueueRecord(connection, message, completionHandler, dstAddress, pushbackHandler, isUncountable);
      }
   }

   protected AsyncWriteQueueRecord(Connection connection, WritableMessage message, CompletionHandler completionHandler, Object dstAddress, PushBackHandler pushBackHandler, boolean isUncountable) {
      this.set(connection, message, completionHandler, dstAddress, pushBackHandler, isUncountable);
   }

   protected void set(Connection connection, WritableMessage message, CompletionHandler completionHandler, Object dstAddress, PushBackHandler pushBackHandler, boolean isUncountable) {
      super.set(connection, message, completionHandler);
      this.dstAddress = dstAddress;
      this.isUncountable = isUncountable;
      this.initialMessageSize = message != null ? (long)message.remaining() : 0L;
      this.pushBackHandler = pushBackHandler;
      this.writeResult.set(connection, message, dstAddress, 0L);
   }

   public final Object getDstAddress() {
      this.checkRecycled();
      return this.dstAddress;
   }

   public final WritableMessage getWritableMessage() {
      return (WritableMessage)this.message;
   }

   public boolean isUncountable() {
      return this.isUncountable;
   }

   public void setUncountable(boolean isUncountable) {
      this.isUncountable = isUncountable;
   }

   public long getBytesToReserve() {
      return this.isUncountable ? 1L : this.initialMessageSize;
   }

   public long getInitialMessageSize() {
      return this.initialMessageSize;
   }

   public long remaining() {
      return (long)this.getWritableMessage().remaining();
   }

   public RecordWriteResult getCurrentResult() {
      return this.writeResult;
   }

   /** @deprecated */
   @Deprecated
   public PushBackHandler getPushBackHandler() {
      return this.pushBackHandler;
   }

   public boolean canBeAggregated() {
      return !this.getWritableMessage().isExternal();
   }

   public void notifyCompleteAndRecycle() {
      CompletionHandler completionHandlerLocal = this.completionHandler;
      WritableMessage messageLocal = this.getWritableMessage();
      if (completionHandlerLocal != null) {
         completionHandlerLocal.completed(this.writeResult);
      }

      this.recycle();
      messageLocal.release();
   }

   public boolean isFinished() {
      return !this.getWritableMessage().hasRemaining();
   }

   protected final void reset() {
      this.set((Connection)null, (WritableMessage)null, (CompletionHandler)null, (Object)null, (PushBackHandler)null, false);
      this.writeResult.recycle();
   }

   public void recycle() {
      this.checkRecycled();
      this.reset();
      this.isRecycled = true;
      if (Grizzly.isTrackingThreadCache()) {
         this.recycleTrack = new DebugPoint(new Exception(), Thread.currentThread().getName());
      }

      ThreadCache.putToCache(CACHE_IDX, this);
   }
}
