package org.glassfish.grizzly;

import org.glassfish.grizzly.utils.Holder;

public class ReadResult implements Result, Cacheable {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(ReadResult.class, 4);
   private boolean isRecycled;
   private Connection connection;
   private Object message;
   private Holder srcAddressHolder;
   private int readSize;

   public static ReadResult create(Connection connection) {
      ReadResult readResult = takeFromCache();
      if (readResult != null) {
         readResult.connection = connection;
         readResult.isRecycled = false;
         return readResult;
      } else {
         return new ReadResult(connection);
      }
   }

   public static ReadResult create(Connection connection, Object message, Object srcAddress, int readSize) {
      ReadResult readResult = takeFromCache();
      if (readResult != null) {
         readResult.connection = connection;
         readResult.message = message;
         readResult.srcAddressHolder = Holder.staticHolder(srcAddress);
         readResult.readSize = readSize;
         readResult.isRecycled = false;
         return readResult;
      } else {
         return new ReadResult(connection, message, srcAddress, readSize);
      }
   }

   private static ReadResult takeFromCache() {
      return (ReadResult)ThreadCache.takeFromCache(CACHE_IDX);
   }

   protected ReadResult() {
      this.isRecycled = false;
   }

   protected ReadResult(Connection connection) {
      this(connection, (Object)null, (Object)null, 0);
   }

   protected ReadResult(Connection connection, Object message, Object srcAddress, int readSize) {
      this.isRecycled = false;
      this.connection = connection;
      this.message = message;
      this.srcAddressHolder = Holder.staticHolder(srcAddress);
      this.readSize = readSize;
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

   public final Object getSrcAddress() {
      this.checkRecycled();
      return this.srcAddressHolder != null ? this.srcAddressHolder.get() : null;
   }

   public final Holder getSrcAddressHolder() {
      this.checkRecycled();
      return this.srcAddressHolder;
   }

   public final void setSrcAddress(Object srcAddress) {
      this.checkRecycled();
      this.srcAddressHolder = Holder.staticHolder(srcAddress);
   }

   public final void setSrcAddressHolder(Holder srcAddressHolder) {
      this.checkRecycled();
      this.srcAddressHolder = srcAddressHolder;
   }

   public final int getReadSize() {
      this.checkRecycled();
      return this.readSize;
   }

   public final void setReadSize(int readSize) {
      this.checkRecycled();
      this.readSize = readSize;
   }

   protected void set(Connection connection, Object message, Object srcAddress, int readSize) {
      this.connection = connection;
      this.message = message;
      this.srcAddressHolder = Holder.staticHolder(srcAddress);
      this.readSize = readSize;
   }

   protected void reset() {
      this.connection = null;
      this.message = null;
      this.srcAddressHolder = null;
      this.readSize = 0;
   }

   private void checkRecycled() {
      if (Grizzly.isTrackingThreadCache() && this.isRecycled) {
         throw new IllegalStateException("ReadResult has been recycled!");
      }
   }

   public void recycle() {
      this.reset();
      this.isRecycled = true;
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public Object copy() {
      return create(this.getConnection(), this.getMessage(), this.getSrcAddress(), this.getReadSize());
   }
}
