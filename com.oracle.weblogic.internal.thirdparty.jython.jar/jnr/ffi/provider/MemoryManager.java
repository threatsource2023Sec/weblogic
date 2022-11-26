package jnr.ffi.provider;

import java.nio.ByteBuffer;
import jnr.ffi.Pointer;

public interface MemoryManager {
   Pointer allocate(int var1);

   Pointer allocateDirect(int var1);

   Pointer allocateDirect(int var1, boolean var2);

   Pointer allocateTemporary(int var1, boolean var2);

   Pointer newPointer(ByteBuffer var1);

   Pointer newPointer(long var1);

   Pointer newPointer(long var1, long var3);

   Pointer newOpaquePointer(long var1);
}
