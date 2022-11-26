package jnr.ffi.provider;

import java.util.Collection;
import java.util.Map;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;

final class InvalidProvider extends FFIProvider {
   private final String message;
   private final Throwable cause;
   private final Runtime runtime;

   InvalidProvider(String message, Throwable cause) {
      this.message = message;
      this.cause = cause;
      this.runtime = new InvalidRuntime(message, cause);
   }

   public Runtime getRuntime() {
      return this.runtime;
   }

   public LibraryLoader createLibraryLoader(Class interfaceClass) {
      return new LibraryLoader(interfaceClass) {
         protected Object loadLibrary(Class interfaceClass, Collection libraryNames, Collection searchPaths, Map options) {
            UnsatisfiedLinkError error = new UnsatisfiedLinkError(InvalidProvider.this.message);
            error.initCause(InvalidProvider.this.cause);
            throw error;
         }
      };
   }
}
