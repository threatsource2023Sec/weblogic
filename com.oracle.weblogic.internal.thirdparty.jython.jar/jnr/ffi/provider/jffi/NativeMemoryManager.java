package jnr.ffi.provider.jffi;

import java.nio.ByteBuffer;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.provider.BoundedMemoryIO;
import jnr.ffi.provider.IntPointer;
import jnr.ffi.provider.MemoryManager;

public class NativeMemoryManager implements MemoryManager {
   private final Runtime runtime;
   private final long addressMask;

   public NativeMemoryManager(NativeRuntime runtime) {
      this.runtime = runtime;
      this.addressMask = runtime.addressMask();
   }

   public Pointer allocate(int size) {
      return new ArrayMemoryIO(this.runtime, size);
   }

   public Pointer allocateDirect(int size) {
      return new BoundedMemoryIO(TransientNativeMemory.allocate(this.runtime, size, 8, true), 0L, (long)size);
   }

   public Pointer allocateDirect(int size, boolean clear) {
      return new BoundedMemoryIO(TransientNativeMemory.allocate(this.runtime, size, 8, clear), 0L, (long)size);
   }

   public Pointer allocateTemporary(int size) {
      return new BoundedMemoryIO(TransientNativeMemory.allocate(this.runtime, size, 8, true), 0L, (long)size);
   }

   public Pointer allocateTemporary(int size, boolean clear) {
      return new BoundedMemoryIO(TransientNativeMemory.allocate(this.runtime, size, 8, clear), 0L, (long)size);
   }

   public Pointer newPointer(ByteBuffer buffer) {
      return new ByteBufferMemoryIO(this.runtime, buffer);
   }

   public Pointer newPointer(long address) {
      return new DirectMemoryIO(this.runtime, address & this.addressMask);
   }

   public Pointer newPointer(long address, long size) {
      return new BoundedMemoryIO(new DirectMemoryIO(this.runtime, address & this.addressMask), 0L, size);
   }

   public Pointer newOpaquePointer(long address) {
      return new IntPointer(this.runtime, address);
   }
}
