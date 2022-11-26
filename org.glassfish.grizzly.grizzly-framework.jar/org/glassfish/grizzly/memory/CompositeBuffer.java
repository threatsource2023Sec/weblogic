package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;

public abstract class CompositeBuffer implements Buffer {
   protected DisposeOrder disposeOrder;

   public CompositeBuffer() {
      this.disposeOrder = CompositeBuffer.DisposeOrder.LAST_TO_FIRST;
   }

   public static CompositeBuffer newBuffer() {
      return BuffersBuffer.create();
   }

   public static CompositeBuffer newBuffer(MemoryManager memoryManager) {
      return BuffersBuffer.create(memoryManager);
   }

   public static CompositeBuffer newBuffer(MemoryManager memoryManager, Buffer... buffers) {
      return BuffersBuffer.create(memoryManager, buffers);
   }

   public static CompositeBuffer newBuffer(MemoryManager memoryManager, Buffer[] buffers, boolean isReadOnly) {
      return BuffersBuffer.create(memoryManager, buffers, isReadOnly);
   }

   public DisposeOrder disposeOrder() {
      return this.disposeOrder;
   }

   public CompositeBuffer disposeOrder(DisposeOrder disposeOrder) {
      this.disposeOrder = disposeOrder;
      return this;
   }

   public abstract CompositeBuffer append(Buffer var1);

   public abstract CompositeBuffer prepend(Buffer var1);

   public abstract Object[] underlying();

   public abstract void removeAll();

   public abstract void allowInternalBuffersDispose(boolean var1);

   public abstract boolean allowInternalBuffersDispose();

   public abstract int bulk(BulkOperation var1);

   public abstract int bulk(BulkOperation var1, int var2, int var3);

   public abstract boolean replace(Buffer var1, Buffer var2);

   public interface Setter {
      void set(byte var1);
   }

   public interface BulkOperation {
      boolean processByte(byte var1, Setter var2);
   }

   public static enum DisposeOrder {
      LAST_TO_FIRST,
      FIRST_TO_LAST;
   }
}
