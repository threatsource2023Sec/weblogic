package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class InSubQExpression implements Exp {
   private final Val _val;
   private final SubQ _sub;

   public InSubQExpression(Val val, SubQ sub) {
      this._val = val;
      this._sub = sub;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState subqState = this._sub.initialize(sel, ctx, 0);
      ExpState valueState = this._val.initialize(sel, ctx, 0);
      return new InSubQExpState(valueState.joins, subqState, valueState);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      InSubQExpState istate = (InSubQExpState)state;
      this._sub.calculateValue(sel, ctx, istate.subqState, (Val)null, (ExpState)null);
      this._val.calculateValue(sel, ctx, istate.valueState, (Val)null, (ExpState)null);
      this._val.appendTo(sel, ctx, istate.valueState, buf, 0);
      buf.append(" IN ");
      this._sub.appendTo(sel, ctx, istate.valueState, buf, 0);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      InSubQExpState istate = (InSubQExpState)state;
      this._sub.selectColumns(sel, ctx, istate.subqState, pks);
      this._val.selectColumns(sel, ctx, istate.valueState, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      this._sub.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }

   private static class InSubQExpState extends ExpState {
      public final ExpState subqState;
      public final ExpState valueState;

      public InSubQExpState(Joins joins, ExpState subqState, ExpState valueState) {
         super(joins);
         this.subqState = subqState;
         this.valueState = valueState;
      }
   }
}
