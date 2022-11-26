package org.python.modules.jffi;

import org.python.core.Py;

class AllocatedNativeMemory extends BoundedNativeMemory implements AllocatedDirectMemory {
   private volatile boolean released = false;
   private volatile boolean autorelease = true;
   private final long storage;

   static final AllocatedNativeMemory allocate(int size, boolean clear) {
      return allocateAligned(size, 1, clear);
   }

   static final AllocatedNativeMemory allocateAligned(int size, int align, boolean clear) {
      long memory = IO.allocateMemory((long)(size + align - 1), clear);
      if (memory == 0L) {
         throw Py.RuntimeError("failed to allocate " + size + " bytes");
      } else {
         return new AllocatedNativeMemory(memory, size, align);
      }
   }

   private AllocatedNativeMemory(long address, int size, int align) {
      super((address - 1L & (long)(~(align - 1))) + (long)align, size);
      this.storage = address;
   }

   public void free() {
      if (!this.released) {
         IO.freeMemory(this.storage);
         this.released = true;
      }

   }

   public void setAutoRelease(boolean release) {
      this.autorelease = release;
   }

   protected void finalize() throws Throwable {
      try {
         if (!this.released && this.autorelease) {
            IO.freeMemory(this.storage);
            this.released = true;
         }
      } finally {
         super.finalize();
      }

   }
}
