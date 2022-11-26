package jnr.ffi.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class CompositeFunctionMapper implements FunctionMapper {
   private final Collection functionMappers;

   public CompositeFunctionMapper(Collection functionMappers) {
      this.functionMappers = Collections.unmodifiableList(new ArrayList(functionMappers));
   }

   public String mapFunctionName(String functionName, FunctionMapper.Context context) {
      Iterator var3 = this.functionMappers.iterator();

      String mappedName;
      do {
         if (!var3.hasNext()) {
            return functionName;
         }

         FunctionMapper functionMapper = (FunctionMapper)var3.next();
         mappedName = functionMapper.mapFunctionName(functionName, context);
      } while(mappedName == functionName);

      return mappedName;
   }
}
