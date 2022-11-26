package org.apache.openjpa.jdbc.kernel.exps;

class All extends UnaryOp {
   public All(Val val) {
      super(val);
   }

   protected String getOperator() {
      return "ALL";
   }
}
