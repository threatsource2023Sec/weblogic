package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.utils.DebugPoint;

public final class AsyncReadQueueRecord extends AsyncQueueRecord {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(AsyncReadQueueRecord.class, 2);
   protected Interceptor interceptor;
   private final RecordReadResult readResult = new RecordReadResult();

   public static AsyncReadQueueRecord create(Connection connection, Buffer message, CompletionHandler completionHandler, Interceptor interceptor) {
      AsyncReadQueueRecord asyncReadQueueRecord = (AsyncReadQueueRecord)ThreadCache.takeFromCache(CACHE_IDX);
      if (asyncReadQueueRecord != null) {
         asyncReadQueueRecord.isRecycled = false;
         asyncReadQueueRecord.set(connection, message, completionHandler, interceptor);
         return asyncReadQueueRecord;
      } else {
         return new AsyncReadQueueRecord(connection, message, completionHandler, interceptor);
      }
   }

   private AsyncReadQueueRecord(Connection connection, Buffer message, CompletionHandler completionHandler, Interceptor interceptor) {
      this.set(connection, message, completionHandler, interceptor);
   }

   public final Interceptor getInterceptor() {
      this.checkRecycled();
      return this.interceptor;
   }

   public final void notifyComplete() {
      if (this.completionHandler != null) {
         this.completionHandler.completed(this.readResult);
      }

   }

   public boolean isFinished() {
      return this.readResult.getReadSize() > 0 || !((Buffer)this.message).hasRemaining();
   }

   public ReadResult getCurrentResult() {
      return this.readResult;
   }

   protected final void set(Connection connection, Object message, CompletionHandler completionHandler, Interceptor interceptor) {
      this.set(connection, message, completionHandler);
      this.interceptor = interceptor;
      this.readResult.set(connection, message, (Object)null, 0);
   }

   protected final void reset() {
      this.set((Connection)null, (Object)null, (CompletionHandler)null);
      this.readResult.recycle();
      this.interceptor = null;
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
