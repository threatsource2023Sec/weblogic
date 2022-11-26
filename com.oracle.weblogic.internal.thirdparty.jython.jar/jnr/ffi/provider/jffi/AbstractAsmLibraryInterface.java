package jnr.ffi.provider.jffi;

import com.kenai.jffi.Invoker;
import jnr.ffi.Runtime;
import jnr.ffi.provider.LoadedLibrary;

public abstract class AbstractAsmLibraryInterface implements LoadedLibrary {
   public static final Invoker ffi = Invoker.getInstance();
   protected final Runtime runtime;
   protected final NativeLibrary library;

   public AbstractAsmLibraryInterface(Runtime runtime, NativeLibrary library) {
      this.runtime = runtime;
      this.library = library;
   }

   public final Runtime getRuntime() {
      return this.runtime;
   }

   final NativeLibrary getLibrary() {
      return this.library;
   }
}
