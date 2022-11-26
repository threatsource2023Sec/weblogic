package jnr.ffi.mapper;

import java.util.Collection;
import jnr.ffi.Runtime;

public interface ToNativeContext {
   Collection getAnnotations();

   Runtime getRuntime();
}
