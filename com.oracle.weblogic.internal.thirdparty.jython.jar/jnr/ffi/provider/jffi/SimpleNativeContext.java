package jnr.ffi.provider.jffi;

import java.util.Collection;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.ToNativeContext;

public class SimpleNativeContext implements ToNativeContext, FromNativeContext {
   private final Runtime runtime;
   private final Collection annotations;

   SimpleNativeContext(Runtime runtime, Collection annotations) {
      this.runtime = runtime;
      this.annotations = annotations;
   }

   public Collection getAnnotations() {
      return this.annotations;
   }

   public final Runtime getRuntime() {
      return this.runtime;
   }
}
