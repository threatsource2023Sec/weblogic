package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class ConstInstanceofExpression implements Exp {
   private final Const _const;
   private final Class _cls;

   public ConstInstanceofExpression(Const val, Class cls) {
      this._const = val;
      this._cls = Filters.wrap(cls);
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      return this._const.initialize(sel, ctx, 0);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      this._const.calculateValue(sel, ctx, state, (Val)null, (ExpState)null);
      if (this._cls.isInstance(this._const.getValue(ctx, state))) {
         buf.append("1 = 1");
      } else {
         buf.append("1 <> 1");
      }

   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this._const.selectColumns(sel, ctx, state, pks);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._const.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}
