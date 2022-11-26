package jnr.ffi.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jnr.ffi.Library;

public interface FunctionMapper {
   FunctionMapper IDENTITY = new FunctionMapper() {
      public String mapFunctionName(String functionName, Context context) {
         return functionName;
      }
   };

   String mapFunctionName(String var1, Context var2);

   public static final class Builder {
      private final Map functionNameMap = Collections.synchronizedMap(new HashMap());

      public Builder map(String javaName, String nativeFunction) {
         this.functionNameMap.put(javaName, nativeFunction);
         return this;
      }

      public FunctionMapper build() {
         return new SimpleFunctionMapper(this.functionNameMap);
      }
   }

   public interface Context {
      /** @deprecated */
      @Deprecated
      Library getLibrary();

      boolean isSymbolPresent(String var1);

      Collection getAnnotations();
   }
}
