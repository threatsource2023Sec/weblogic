package com.bea.core.repackaged.springframework.core.io.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class LimitedDataBufferList extends ArrayList {
   private final int maxByteCount;
   private int byteCount;

   public LimitedDataBufferList(int maxByteCount) {
      this.maxByteCount = maxByteCount;
   }

   public boolean add(DataBuffer buffer) {
      boolean result = super.add(buffer);
      if (result) {
         this.updateCount(buffer.readableByteCount());
      }

      return result;
   }

   public void add(int index, DataBuffer buffer) {
      super.add(index, buffer);
      this.updateCount(buffer.readableByteCount());
   }

   public boolean addAll(Collection collection) {
      boolean result = super.addAll(collection);
      collection.forEach((buffer) -> {
         this.updateCount(buffer.readableByteCount());
      });
      return result;
   }

   public boolean addAll(int index, Collection collection) {
      boolean result = super.addAll(index, collection);
      collection.forEach((buffer) -> {
         this.updateCount(buffer.readableByteCount());
      });
      return result;
   }

   private void updateCount(int bytesToAdd) {
      if (this.maxByteCount >= 0) {
         if (bytesToAdd > Integer.MAX_VALUE - this.byteCount) {
            this.raiseLimitException();
         } else {
            this.byteCount += bytesToAdd;
            if (this.byteCount > this.maxByteCount) {
               this.raiseLimitException();
            }
         }

      }
   }

   private void raiseLimitException() {
      throw new DataBufferLimitException("Exceeded limit on max bytes to buffer : " + this.maxByteCount);
   }

   public DataBuffer remove(int index) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   protected void removeRange(int fromIndex, int toIndex) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeIf(Predicate filter) {
      throw new UnsupportedOperationException();
   }

   public DataBuffer set(int index, DataBuffer element) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      this.byteCount = 0;
      super.clear();
   }

   public void releaseAndClear() {
      this.forEach((buf) -> {
         try {
            DataBufferUtils.release(buf);
         } catch (Throwable var2) {
         }

      });
      this.clear();
   }
}
