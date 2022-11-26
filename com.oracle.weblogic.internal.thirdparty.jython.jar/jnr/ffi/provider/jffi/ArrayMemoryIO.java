package jnr.ffi.provider.jffi;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.provider.AbstractArrayMemoryIO;

public final class ArrayMemoryIO extends AbstractArrayMemoryIO {
   public ArrayMemoryIO(Runtime runtime, int size) {
      super(runtime, size);
   }

   public ArrayMemoryIO(Runtime runtime, byte[] bytes, int off, int len) {
      super(runtime, bytes, off, len);
   }

   public Pointer getPointer(long offset) {
      return MemoryUtil.newPointer(this.getRuntime(), this.getAddress(offset));
   }

   public Pointer getPointer(long offset, long size) {
      return MemoryUtil.newPointer(this.getRuntime(), this.getAddress(offset), size);
   }

   public void putPointer(long offset, Pointer value) {
      this.putAddress(offset, value != null ? value.address() : 0L);
   }
}
