package jnr.ffi.provider.jffi;

import java.util.Map;

public abstract class LibraryLoader {
   abstract Object loadLibrary(NativeLibrary var1, Class var2, Map var3);
}
