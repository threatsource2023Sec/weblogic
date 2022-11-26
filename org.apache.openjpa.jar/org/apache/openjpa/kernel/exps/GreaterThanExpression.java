package org.apache.openjpa.kernel.exps;

class GreaterThanExpression extends CompareExpression {
   public GreaterThanExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   protected boolean compare(Object o1, Object o2) {
      if (!(o1 instanceof Comparable)) {
         return false;
      } else {
         return ((Comparable)o1).compareTo(o2) > 0;
      }
   }
}
