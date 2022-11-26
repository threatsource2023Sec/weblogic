package jnr.posix;

import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public abstract class NativeGroup implements Group {
   protected final Runtime runtime;
   protected final StructLayout structLayout;

   protected NativeGroup(Runtime runtime, StructLayout structLayout) {
      this.runtime = runtime;
      this.structLayout = structLayout;
   }
}
