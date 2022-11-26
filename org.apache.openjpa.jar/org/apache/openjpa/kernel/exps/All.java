package org.apache.openjpa.kernel.exps;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UnsupportedException;

class All extends UnaryMathVal {
   private static final Localizer _loc = Localizer.forPackage(All.class);

   public All(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      Class wrap = Filters.wrap(c);
      return wrap != Integer.class && wrap != Float.class && wrap != Double.class && wrap != Long.class && wrap != BigDecimal.class && wrap != BigInteger.class ? Integer.TYPE : c;
   }

   protected Object operate(Object o, Class c) {
      throw new UnsupportedException(_loc.get("in-mem-subquery"));
   }
}
