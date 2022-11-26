package org.apache.openjpa.jdbc.kernel.exps;

class Min extends UnaryOp {
   public Min(Val val) {
      super(val);
   }

   protected String getOperator() {
      return "MIN";
   }

   public boolean isAggregate() {
      return true;
   }
}
