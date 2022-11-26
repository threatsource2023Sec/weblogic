package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.kernel.Filters;

class Sum extends UnaryOp {
   public Sum(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      Class wrap = Filters.wrap(c);
      return wrap != Integer.class && wrap != Short.class && wrap != Byte.class ? c : Long.TYPE;
   }

   protected String getOperator() {
      return "SUM";
   }

   public boolean isAggregate() {
      return true;
   }
}
