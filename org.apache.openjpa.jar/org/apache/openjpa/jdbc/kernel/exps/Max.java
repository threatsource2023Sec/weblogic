package org.apache.openjpa.jdbc.kernel.exps;

class Max extends UnaryOp {
   public Max(Val val) {
      super(val);
   }

   protected String getOperator() {
      return "MAX";
   }

   public boolean isAggregate() {
      return true;
   }
}
