package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.util.InternalException;

class Size extends UnaryOp {
   public Size(Val val) {
      super(val);
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this.initializeValue(sel, ctx, 2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      this.getValue().calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      this.getValue().appendSize(sel, ctx, state, sql);
      sel.append(sql, state.joins);
   }

   protected Class getType(Class c) {
      return Long.TYPE;
   }

   protected String getOperator() {
      throw new InternalException();
   }
}
