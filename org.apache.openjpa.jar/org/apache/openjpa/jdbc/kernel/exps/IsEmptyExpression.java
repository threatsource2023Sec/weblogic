package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class IsEmptyExpression implements Exp {
   private final Val _val;

   public IsEmptyExpression(Val val) {
      this._val = val;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      return this._val.initialize(sel, ctx, 2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      this._val.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      this._val.appendIsEmpty(sel, ctx, state, buf);
      sel.append(buf, state.joins);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this._val.selectColumns(sel, ctx, state, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}
