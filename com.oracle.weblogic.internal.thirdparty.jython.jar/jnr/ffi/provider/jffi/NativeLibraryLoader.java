package jnr.ffi.provider.jffi;

import java.util.Collection;
import java.util.Map;

class NativeLibraryLoader extends jnr.ffi.LibraryLoader {
   static final boolean ASM_ENABLED = Util.getBooleanProperty("jnr.ffi.asm.enabled", true);

   NativeLibraryLoader(Class interfaceClass) {
      super(interfaceClass);
   }

   public Object loadLibrary(Class interfaceClass, Collection libraryNames, Collection searchPaths, Map options) {
      NativeLibrary nativeLibrary = new NativeLibrary(libraryNames, searchPaths);

      try {
         return ASM_ENABLED ? (new AsmLibraryLoader()).loadLibrary(nativeLibrary, interfaceClass, options) : (new ReflectionLibraryLoader()).loadLibrary(nativeLibrary, interfaceClass, options);
      } catch (RuntimeException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }
   }
}
