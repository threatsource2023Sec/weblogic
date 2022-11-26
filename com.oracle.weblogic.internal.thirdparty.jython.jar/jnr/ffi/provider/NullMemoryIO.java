package jnr.ffi.provider;

import jnr.ffi.Runtime;

public final class NullMemoryIO extends InAccessibleMemoryIO {
   private static final String msg = "attempted access to a NULL memory address";

   public NullMemoryIO(Runtime runtime) {
      super(runtime, 0L, true);
   }

   protected final NullPointerException error() {
      return new NullPointerException("attempted access to a NULL memory address");
   }

   public long size() {
      return Long.MAX_VALUE;
   }
}
