package jnr.ffi.provider;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class IntPointer extends InAccessibleMemoryIO {
   public IntPointer(Runtime runtime, long address) {
      super(runtime, address, true);
   }

   public IntPointer(Runtime runtime, int address) {
      super(runtime, (long)address & 4294967295L, true);
   }

   public long size() {
      return 0L;
   }

   public int hashCode() {
      return (int)this.address();
   }

   public boolean equals(Object obj) {
      return obj instanceof Pointer && ((Pointer)obj).address() == this.address();
   }
}
