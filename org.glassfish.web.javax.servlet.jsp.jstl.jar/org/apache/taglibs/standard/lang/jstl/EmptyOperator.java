package org.apache.taglibs.standard.lang.jstl;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class EmptyOperator extends UnaryOperator {
   public static final EmptyOperator SINGLETON = new EmptyOperator();

   public String getOperatorSymbol() {
      return "empty";
   }

   public Object apply(Object pValue, Object pContext, Logger pLogger) throws ELException {
      if (pValue == null) {
         return PrimitiveObjects.getBoolean(true);
      } else if ("".equals(pValue)) {
         return PrimitiveObjects.getBoolean(true);
      } else if (pValue.getClass().isArray() && Array.getLength(pValue) == 0) {
         return PrimitiveObjects.getBoolean(true);
      } else if (pValue instanceof List && ((List)pValue).isEmpty()) {
         return PrimitiveObjects.getBoolean(true);
      } else {
         return pValue instanceof Map && ((Map)pValue).isEmpty() ? PrimitiveObjects.getBoolean(true) : PrimitiveObjects.getBoolean(false);
      }
   }
}
