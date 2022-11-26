package org.apache.openjpa.jdbc.kernel.exps;

class Sqrt extends UnaryOp {
   public Sqrt(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return Double.TYPE;
   }

   protected String getOperator() {
      return "SQRT";
   }
}
