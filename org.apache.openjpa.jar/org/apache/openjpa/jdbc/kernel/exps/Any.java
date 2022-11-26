package org.apache.openjpa.jdbc.kernel.exps;

class Any extends UnaryOp {
   public Any(Val val) {
      super(val);
   }

   protected String getOperator() {
      return "ANY";
   }
}
