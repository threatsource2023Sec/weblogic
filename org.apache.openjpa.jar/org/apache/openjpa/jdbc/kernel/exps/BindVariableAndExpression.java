package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class BindVariableAndExpression implements Exp {
   private final BindVariableExpression _bind;
   private final Exp _exp;

   public BindVariableAndExpression(BindVariableExpression bind, Exp exp) {
      this._bind = bind;
      this._exp = exp;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState s1 = this._bind.initialize(sel, ctx, contains);
      ExpState s2 = this._exp.initialize(sel, ctx, contains);
      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      boolean or = this._exp instanceof OrExpression;
      if (or) {
         buf.append("(");
      }

      this._exp.appendTo(sel, ctx, ((BinaryOpExpState)state).state2, buf);
      if (or) {
         buf.append(")");
      }

   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this._exp.selectColumns(sel, ctx, ((BinaryOpExpState)state).state2, pks);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._bind.acceptVisit(visitor);
      this._exp.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}
