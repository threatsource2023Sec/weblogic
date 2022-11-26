package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class AndExpression implements Exp {
   private final Exp _exp1;
   private final Exp _exp2;

   public AndExpression(Exp exp1, Exp exp2) {
      this._exp1 = exp1;
      this._exp2 = exp2;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState s1 = this._exp1.initialize(sel, ctx, contains);
      ExpState s2 = this._exp2.initialize(sel, ctx, contains);
      return new BinaryOpExpState(sel.and(s1.joins, s2.joins), s1, s2);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      boolean paren1 = this._exp1 instanceof OrExpression;
      boolean paren2 = this._exp2 instanceof OrExpression;
      if (paren1) {
         buf.append("(");
      }

      this._exp1.appendTo(sel, ctx, bstate.state1, buf);
      if (paren1) {
         buf.append(")");
      }

      buf.append(" AND ");
      if (paren2) {
         buf.append("(");
      }

      this._exp2.appendTo(sel, ctx, bstate.state2, buf);
      if (paren2) {
         buf.append(")");
      }

      sel.append(buf, state.joins);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      this._exp1.selectColumns(sel, ctx, bstate.state1, pks);
      this._exp2.selectColumns(sel, ctx, bstate.state2, pks);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._exp1.acceptVisit(visitor);
      this._exp2.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}
