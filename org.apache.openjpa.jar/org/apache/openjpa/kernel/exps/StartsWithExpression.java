package org.apache.openjpa.kernel.exps;

class StartsWithExpression extends CompareExpression {
   public StartsWithExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   protected boolean compare(Object o1, Object o2) {
      return o1 != null && o2 != null ? o1.toString().startsWith(o2.toString()) : false;
   }
}
