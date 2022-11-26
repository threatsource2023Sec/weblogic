package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

class NotEqualExpression extends CompareEqualExpression {
   public NotEqualExpression(Val val1, Val val2) {
      super(val1, val2);
   }

   public void appendTo(Select sel, ExpContext ctx, BinaryOpExpState bstate, SQLBuffer buf, boolean val1Null, boolean val2Null) {
      if (val1Null && val2Null) {
         buf.append("1 <> 1");
      } else {
         Val val;
         int len;
         int i;
         if (!val1Null && !val2Null) {
            val = this.getValue1();
            Val val2 = this.getValue2();
            if (val.length(sel, ctx, bstate.state1) == 1 && val2.length(sel, ctx, bstate.state2) == 1) {
               ctx.store.getDBDictionary().comparison(buf, "<>", new FilterValueImpl(sel, ctx, bstate.state1, val), new FilterValueImpl(sel, ctx, bstate.state2, val2));
            } else {
               len = java.lang.Math.max(val.length(sel, ctx, bstate.state1), val2.length(sel, ctx, bstate.state2));
               buf.append("(");

               for(i = 0; i < len; ++i) {
                  if (i > 0) {
                     buf.append(" OR ");
                  }

                  val.appendTo(sel, ctx, bstate.state1, buf, i);
                  buf.append(" <> ");
                  val2.appendTo(sel, ctx, bstate.state2, buf, i);
               }

               buf.append(")");
            }
         } else {
            val = val1Null ? this.getValue2() : this.getValue1();
            ExpState state = val1Null ? bstate.state2 : bstate.state1;
            if (!this.isDirectComparison()) {
               len = val.length(sel, ctx, state);

               for(i = 0; i < len; ++i) {
                  if (i > 0) {
                     buf.append(" AND ");
                  }

                  val.appendTo(sel, ctx, state, buf, i);
                  buf.append(" IS NOT ").appendValue((Object)null);
               }
            } else {
               val.appendIsNotNull(sel, ctx, state, buf);
            }
         }
      }

   }
}
