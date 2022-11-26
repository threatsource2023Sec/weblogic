package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import java.util.Map;
import serp.util.Numbers;

class Size extends UnaryMathVal {
   public Size(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return Integer.TYPE;
   }

   protected Object operate(Object o, Class c) {
      if (o instanceof Collection) {
         return Numbers.valueOf(((Collection)o).size());
      } else if (o instanceof Map) {
         return Numbers.valueOf(((Map)o).size());
      } else {
         return o == null ? Numbers.valueOf(0) : Numbers.valueOf(1);
      }
   }
}
