package jnr.ffi.provider.jffi;

import java.util.Collection;
import jnr.ffi.Library;
import jnr.ffi.mapper.FunctionMapper;

public final class NativeFunctionMapperContext implements FunctionMapper.Context {
   private final NativeLibrary library;
   private final Collection annotations;

   public NativeFunctionMapperContext(NativeLibrary library, Collection annotations) {
      this.library = library;
      this.annotations = annotations;
   }

   public Library getLibrary() {
      return null;
   }

   public boolean isSymbolPresent(String name) {
      return this.library.getSymbolAddress(name) != 0L;
   }

   public Collection getAnnotations() {
      return this.annotations;
   }
}
