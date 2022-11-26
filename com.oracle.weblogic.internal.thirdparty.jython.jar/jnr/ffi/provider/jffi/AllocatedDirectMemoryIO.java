package jnr.ffi.provider.jffi;

import java.util.concurrent.atomic.AtomicBoolean;
import jnr.ffi.Runtime;

class AllocatedDirectMemoryIO extends DirectMemoryIO {
   private final AtomicBoolean allocated = new AtomicBoolean(true);
   private final int size;

   public AllocatedDirectMemoryIO(Runtime runtime, int size, boolean clear) {
      super(runtime, IO.allocateMemory((long)size, clear));
      this.size = size;
      if (this.address() == 0L) {
         throw new OutOfMemoryError("Failed to allocate " + size + " bytes");
      }
   }

   public long size() {
      return (long)this.size;
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AllocatedDirectMemoryIO)) {
         return super.equals(obj);
      } else {
         AllocatedDirectMemoryIO mem = (AllocatedDirectMemoryIO)obj;
         return mem.size == this.size && mem.address() == this.address();
      }
   }

   public final void dispose() {
      if (this.allocated.getAndSet(false)) {
         IO.freeMemory(this.address());
      }

   }

   protected void finalize() throws Throwable {
      try {
         if (this.allocated.getAndSet(false)) {
            IO.freeMemory(this.address());
         }
      } finally {
         super.finalize();
      }

   }
}
