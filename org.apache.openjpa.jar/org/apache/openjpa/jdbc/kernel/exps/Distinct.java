package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.Select;

class Distinct extends UnaryOp {
   public Distinct(Val val) {
      super(val);
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this.initializeValue(sel, ctx, 4);
   }

   protected String getOperator() {
      return "DISTINCT";
   }
}
