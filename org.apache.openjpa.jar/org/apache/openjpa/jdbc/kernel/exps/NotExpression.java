package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class NotExpression implements Exp {
   private final Exp _exp;

   public NotExpression(Exp exp) {
      this._exp = exp;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState state = this._exp.initialize(sel, ctx, contains);
      return new NotExpState(sel.or(state.joins, (Joins)null), state);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      buf.append("NOT (");
      this._exp.appendTo(sel, ctx, ((NotExpState)state).state, buf);
      buf.append(")");
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this._exp.selectColumns(sel, ctx, ((NotExpState)state).state, pks);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._exp.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }

   private static class NotExpState extends ExpState {
      public final ExpState state;

      public NotExpState(Joins joins, ExpState state) {
         super(joins);
         this.state = state;
      }
   }
}
