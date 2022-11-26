package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

class CompareExpression implements Exp {
   public static final String LESS = "<";
   public static final String GREATER = ">";
   public static final String LESS_EQUAL = "<=";
   public static final String GREATER_EQUAL = ">=";
   private static final Localizer _loc = Localizer.forPackage(CompareExpression.class);
   private final Val _val1;
   private final Val _val2;
   private final String _op;

   public CompareExpression(Val val1, Val val2, String op) {
      this._val1 = val1;
      this._val2 = val2;
      this._op = op;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState s1 = this._val1.initialize(sel, ctx, 0);
      ExpState s2 = this._val2.initialize(sel, ctx, 0);
      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.calculateValue(sel, ctx, bstate.state1, this._val2, bstate.state2);
      this._val2.calculateValue(sel, ctx, bstate.state2, this._val1, bstate.state1);
      if (!Filters.canConvert(this._val1.getType(), this._val2.getType(), false) && !Filters.canConvert(this._val2.getType(), this._val1.getType(), false)) {
         throw new UserException(_loc.get("cant-convert", this._val1.getType(), this._val2.getType()));
      } else {
         ctx.store.getDBDictionary().comparison(buf, this._op, new FilterValueImpl(sel, ctx, bstate.state1, this._val1), new FilterValueImpl(sel, ctx, bstate.state2, this._val2));
         sel.append(buf, state.joins);
      }
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._val1.selectColumns(sel, ctx, bstate.state1, true);
      this._val2.selectColumns(sel, ctx, bstate.state2, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val1.acceptVisit(visitor);
      this._val2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}
