package jnr.ffi.mapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SimpleFunctionMapper implements FunctionMapper {
   private final Map functionNameMap;

   SimpleFunctionMapper(Map map) {
      this.functionNameMap = Collections.unmodifiableMap(new HashMap(map));
   }

   public String mapFunctionName(String functionName, FunctionMapper.Context context) {
      String nativeFunction = (String)this.functionNameMap.get(functionName);
      return nativeFunction != null ? nativeFunction : functionName;
   }
}
