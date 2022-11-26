package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.Filters;

class Subtract extends MathVal {
   public Subtract(Val val1, Val val2) {
      super(val1, val2);
   }

   protected Object operate(Object o1, Class c1, Object o2, Class c2) {
      return Filters.subtract(o1, c1, o2, c2);
   }
}
