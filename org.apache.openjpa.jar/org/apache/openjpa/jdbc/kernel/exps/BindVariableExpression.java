package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class BindVariableExpression extends EmptyExpression {
   private final Variable _var;

   public BindVariableExpression(Variable var, PCPath val, boolean key) {
      if (key) {
         val.getKey();
      }

      var.setPCPath(val);
      this._var = var;
   }

   public Variable getVariable() {
      return this._var;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      return this._var.initialize(sel, ctx, 0);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      buf.append("1 = 1");
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._var.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }
}
