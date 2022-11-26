package org.apache.openjpa.kernel.exps;

class NotEqualExpression extends CompareExpression {
   public NotEqualExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   protected boolean compare(Object o1, Object o2) {
      return o1 == null && o2 != null || o1 != null && !o1.equals(o2);
   }
}
