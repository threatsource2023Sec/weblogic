package jnr.ffi;

import java.nio.ByteOrder;
import jnr.ffi.provider.ClosureManager;
import jnr.ffi.provider.FFIProvider;
import jnr.ffi.provider.LoadedLibrary;
import jnr.ffi.provider.MemoryManager;

public abstract class Runtime {
   public static Runtime getSystemRuntime() {
      return Runtime.SingletonHolder.SYSTEM_RUNTIME;
   }

   public static Runtime getRuntime(Object library) {
      return ((LoadedLibrary)library).getRuntime();
   }

   public abstract Type findType(NativeType var1);

   public abstract Type findType(TypeAlias var1);

   public abstract MemoryManager getMemoryManager();

   public abstract ClosureManager getClosureManager();

   public abstract ObjectReferenceManager newObjectReferenceManager();

   public abstract int getLastError();

   public abstract void setLastError(int var1);

   public abstract long addressMask();

   public abstract int addressSize();

   public abstract int longSize();

   public abstract ByteOrder byteOrder();

   public abstract boolean isCompatible(Runtime var1);

   private static final class SingletonHolder {
      public static final Runtime SYSTEM_RUNTIME = FFIProvider.getSystemProvider().getRuntime();
   }
}
