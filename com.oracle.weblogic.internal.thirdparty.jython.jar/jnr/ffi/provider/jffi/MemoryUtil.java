package jnr.ffi.provider.jffi;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.provider.BoundedMemoryIO;

public final class MemoryUtil {
   private MemoryUtil() {
   }

   static Pointer newPointer(Runtime runtime, long ptr) {
      return ptr != 0L ? new DirectMemoryIO(runtime, ptr) : null;
   }

   static Pointer newPointer(Runtime runtime, int ptr) {
      return ptr != 0 ? new DirectMemoryIO(runtime, ptr) : null;
   }

   static Pointer newPointer(Runtime runtime, long ptr, long size) {
      return ptr != 0L ? new BoundedMemoryIO(new DirectMemoryIO(runtime, ptr), 0L, size) : null;
   }
}
