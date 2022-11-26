package org.glassfish.grizzly;

import org.glassfish.grizzly.utils.Holder;

public class WriteResult implements Result, Cacheable {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(WriteResult.class, 4);
   private boolean isRecycled;
   private Connection connection;
   private Object message;
   private Holder dstAddressHolder;
   private long writtenSize;

   public static WriteResult create(Connection connection) {
      WriteResult writeResult = takeFromCache();
      if (writeResult != null) {
         writeResult.connection = connection;
         writeResult.isRecycled = false;
         return writeResult;
      } else {
         return new WriteResult(connection);
      }
   }

   public static WriteResult create(Connection connection, Object message, Object dstAddress, long writeSize) {
      WriteResult writeResult = takeFromCache();
      if (writeResult != null) {
         writeResult.set(connection, message, dstAddress, writeSize);
         writeResult.isRecycled = false;
         return writeResult;
      } else {
         return new WriteResult(connection, message, dstAddress, writeSize);
      }
   }

   private static WriteResult takeFromCache() {
      return (WriteResult)ThreadCache.takeFromCache(CACHE_IDX);
   }

   protected WriteResult() {
      this.isRecycled = false;
   }

   private WriteResult(Connection connection) {
      this(connection, (Object)null, (Object)null, 0L);
   }

   private WriteResult(Connection connection, Object message, Object dstAddress, long writeSize) {
      this.isRecycled = false;
      this.set(connection, message, dstAddress, writeSize);
   }

   public final Connection getConnection() {
      this.checkRecycled();
      return this.connection;
   }

   public final Object getMessage() {
      this.checkRecycled();
      return this.message;
   }

   public final void setMessage(Object message) {
      this.checkRecycled();
      this.message = message;
   }

   public final Object getDstAddress() {
      this.checkRecycled();
      return this.dstAddressHolder != null ? this.dstAddressHolder.get() : null;
   }

   public final Holder getDstAddressHolder() {
      this.checkRecycled();
      return this.dstAddressHolder;
   }

   public final void setDstAddress(Object dstAddress) {
      this.checkRecycled();
      this.dstAddressHolder = this.createAddrHolder(dstAddress);
   }

   public final void setDstAddressHolder(Holder dstAddressHolder) {
      this.checkRecycled();
      this.dstAddressHolder = dstAddressHolder;
   }

   public final long getWrittenSize() {
      this.checkRecycled();
      return this.writtenSize;
   }

   public final void setWrittenSize(long writeSize) {
      this.checkRecycled();
      this.writtenSize = writeSize;
   }

   private void checkRecycled() {
      if (Grizzly.isTrackingThreadCache() && this.isRecycled) {
         throw new IllegalStateException("ReadResult has been recycled!");
      }
   }

   protected void set(Connection connection, Object message, Object dstAddress, long writtenSize) {
      this.connection = connection;
      this.message = message;
      this.dstAddressHolder = this.createAddrHolder(dstAddress);
      this.writtenSize = writtenSize;
   }

   protected Holder createAddrHolder(Object dstAddress) {
      return Holder.staticHolder(dstAddress);
   }

   protected void reset() {
      this.connection = null;
      this.message = null;
      this.dstAddressHolder = null;
      this.writtenSize = 0L;
   }

   public void recycle() {
      this.reset();
      this.isRecycled = true;
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public Object copy() {
      return create(this.getConnection(), this.getMessage(), this.getDstAddress(), this.getWrittenSize());
   }
}
