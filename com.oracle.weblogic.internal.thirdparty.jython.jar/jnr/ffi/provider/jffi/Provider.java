package jnr.ffi.provider.jffi;

import jnr.ffi.Runtime;
import jnr.ffi.provider.FFIProvider;

public final class Provider extends FFIProvider {
   private final NativeRuntime runtime = NativeRuntime.getInstance();

   public final Runtime getRuntime() {
      return this.runtime;
   }

   public jnr.ffi.LibraryLoader createLibraryLoader(Class interfaceClass) {
      return new NativeLibraryLoader(interfaceClass);
   }
}
