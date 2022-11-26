package jnr.posix;

import jnr.ffi.Pointer;

public abstract class NativePasswd implements Passwd {
   protected final Pointer memory;

   NativePasswd(Pointer pointer) {
      this.memory = pointer;
   }
}
