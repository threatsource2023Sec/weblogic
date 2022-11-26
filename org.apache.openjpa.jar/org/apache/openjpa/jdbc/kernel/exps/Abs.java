package org.apache.openjpa.jdbc.kernel.exps;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.openjpa.kernel.Filters;

class Abs extends UnaryOp {
   public Abs(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      Class wrap = Filters.wrap(c);
      return wrap != Integer.class && wrap != Float.class && wrap != Double.class && wrap != Long.class && wrap != BigDecimal.class && wrap != BigInteger.class ? Integer.TYPE : Filters.unwrap(c);
   }

   protected String getOperator() {
      return "ABS";
   }
}
