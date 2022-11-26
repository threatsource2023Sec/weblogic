package org.glassfish.grizzly.memory;

import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class AbstractBufferArray {
   protected final Class clazz;
   private Object[] byteBufferArray;
   private PosLim[] initStateArray;
   private int size;

   protected abstract void setPositionLimit(Object var1, int var2, int var3);

   protected abstract int getPosition(Object var1);

   protected abstract int getLimit(Object var1);

   protected AbstractBufferArray(Class clazz) {
      this.clazz = clazz;
      this.byteBufferArray = (Object[])((Object[])Array.newInstance(clazz, 4));
      this.initStateArray = new PosLim[4];
   }

   public void add(Object byteBuffer) {
      this.add(byteBuffer, this.getPosition(byteBuffer), this.getLimit(byteBuffer));
   }

   public void add(Object byteBuffer, int restorePosition, int restoreLimit) {
      this.ensureCapacity(1);
      this.byteBufferArray[this.size] = byteBuffer;
      PosLim poslim = this.initStateArray[this.size];
      if (poslim == null) {
         poslim = new PosLim();
         this.initStateArray[this.size] = poslim;
      }

      poslim.initialPosition = this.getPosition(byteBuffer);
      poslim.initialLimit = this.getLimit(byteBuffer);
      poslim.restorePosition = restorePosition;
      poslim.restoreLimit = restoreLimit;
      ++this.size;
   }

   public Object[] getArray() {
      return this.byteBufferArray;
   }

   public void restore() {
      for(int i = 0; i < this.size; ++i) {
         PosLim poslim = this.initStateArray[i];
         this.setPositionLimit(this.byteBufferArray[i], poslim.restorePosition, poslim.restoreLimit);
      }

   }

   public final int getInitialPosition(int idx) {
      return this.initStateArray[idx].initialPosition;
   }

   public int getInitialLimit(int idx) {
      return this.initStateArray[idx].initialLimit;
   }

   public final int getInitialBufferSize(int idx) {
      return this.getInitialLimit(idx) - this.getInitialPosition(idx);
   }

   public int size() {
      return this.size;
   }

   private void ensureCapacity(int grow) {
      int diff = this.byteBufferArray.length - this.size;
      if (diff < grow) {
         int newSize = Math.max(diff + this.size, this.byteBufferArray.length * 3 / 2 + 1);
         this.byteBufferArray = Arrays.copyOf(this.byteBufferArray, newSize);
         this.initStateArray = (PosLim[])Arrays.copyOf(this.initStateArray, newSize);
      }
   }

   public void reset() {
      Arrays.fill(this.byteBufferArray, 0, this.size, (Object)null);
      this.size = 0;
   }

   public void recycle() {
      this.reset();
   }

   private static final class PosLim {
      int initialPosition;
      int initialLimit;
      int restorePosition;
      int restoreLimit;

      private PosLim() {
      }

      // $FF: synthetic method
      PosLim(Object x0) {
         this();
      }
   }
}
