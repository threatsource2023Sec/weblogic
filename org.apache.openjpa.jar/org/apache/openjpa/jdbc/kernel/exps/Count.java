package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.Select;

class Count extends UnaryOp {
   public Count(Val val) {
      super(val);
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this.initializeValue(sel, ctx, 4);
   }

   protected Class getType(Class c) {
      return Long.TYPE;
   }

   protected String getOperator() {
      return "COUNT";
   }

   public boolean isAggregate() {
      return true;
   }
}
