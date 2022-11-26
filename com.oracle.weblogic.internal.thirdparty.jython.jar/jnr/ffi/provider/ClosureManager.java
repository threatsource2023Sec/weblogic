package jnr.ffi.provider;

import jnr.ffi.Pointer;

public interface ClosureManager {
   Object newClosure(Class var1, Object var2);

   Pointer getClosurePointer(Class var1, Object var2);
}
