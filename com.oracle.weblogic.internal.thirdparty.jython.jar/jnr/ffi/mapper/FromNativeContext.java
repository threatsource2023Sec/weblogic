package jnr.ffi.mapper;

import java.util.Collection;
import jnr.ffi.Runtime;

public interface FromNativeContext {
   Collection getAnnotations();

   Runtime getRuntime();
}
