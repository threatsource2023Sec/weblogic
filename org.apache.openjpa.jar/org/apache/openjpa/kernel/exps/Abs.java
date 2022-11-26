package org.apache.openjpa.kernel.exps;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.openjpa.kernel.Filters;
import serp.util.Numbers;

class Abs extends UnaryMathVal {
   public Abs(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      Class wrap = Filters.wrap(c);
      return wrap != Integer.class && wrap != Float.class && wrap != Double.class && wrap != Long.class && wrap != BigDecimal.class && wrap != BigInteger.class ? Integer.TYPE : Filters.unwrap(c);
   }

   protected Object operate(Object o, Class c) {
      c = Filters.wrap(c);
      if (c == Integer.class) {
         return Numbers.valueOf(Math.abs(((Number)o).intValue()));
      } else if (c == Float.class) {
         return new Float(Math.abs(((Number)o).floatValue()));
      } else if (c == Double.class) {
         return new Double(Math.abs(((Number)o).doubleValue()));
      } else if (c == Long.class) {
         return Numbers.valueOf(Math.abs(((Number)o).longValue()));
      } else if (c == BigDecimal.class) {
         return ((BigDecimal)o).abs();
      } else {
         return c == BigInteger.class ? ((BigInteger)o).abs() : Numbers.valueOf(Math.abs(((Number)o).intValue()));
      }
   }
}
