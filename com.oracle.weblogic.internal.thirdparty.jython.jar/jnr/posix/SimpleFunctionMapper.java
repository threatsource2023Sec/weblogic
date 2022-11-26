package jnr.posix;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jnr.ffi.mapper.FunctionMapper;

public class SimpleFunctionMapper implements FunctionMapper {
   private final Map functionNameMap;

   private SimpleFunctionMapper(Map map) {
      this.functionNameMap = Collections.unmodifiableMap(new HashMap(map));
   }

   public String mapFunctionName(String functionName, FunctionMapper.Context context) {
      String nativeFunction = (String)this.functionNameMap.get(functionName);
      return nativeFunction != null ? nativeFunction : functionName;
   }

   // $FF: synthetic method
   SimpleFunctionMapper(Map x0, Object x1) {
      this(x0);
   }

   public static class Builder {
      private final Map functionNameMap = Collections.synchronizedMap(new HashMap());

      public Builder map(String posixName, String nativeFunction) {
         this.functionNameMap.put(posixName, nativeFunction);
         return this;
      }

      public SimpleFunctionMapper build() {
         return new SimpleFunctionMapper(this.functionNameMap);
      }
   }
}
