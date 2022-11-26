package org.apache.openjpa.jdbc.kernel.exps;

class Avg extends UnaryOp {
   public Avg(Val val) {
      super(val);
   }

   protected String getOperator() {
      return "AVG";
   }

   public boolean isAggregate() {
      return true;
   }
}
