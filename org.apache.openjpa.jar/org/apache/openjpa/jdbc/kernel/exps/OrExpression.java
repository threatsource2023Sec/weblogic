package org.apache.openjpa.jdbc.kernel.exps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class OrExpression implements Exp {
   private final Exp _exp1;
   private final Exp _exp2;

   public OrExpression(Exp exp1, Exp exp2) {
      this._exp1 = exp1;
      this._exp2 = exp2;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      Map contains2 = null;
      if (contains != null) {
         contains2 = new HashMap(contains);
      }

      ExpState s1 = this._exp1.initialize(sel, ctx, contains);
      ExpState s2 = this._exp2.initialize(sel, ctx, contains2);
      ExpState ret = new BinaryOpExpState(sel.or(s1.joins, s2.joins), s1, s2);
      if (contains == null) {
         return ret;
      } else {
         Iterator itr = contains2.entrySet().iterator();

         while(true) {
            Map.Entry entry;
            Integer val1;
            Integer val2;
            do {
               if (!itr.hasNext()) {
                  return ret;
               }

               entry = (Map.Entry)itr.next();
               val2 = (Integer)entry.getValue();
               val1 = (Integer)contains.get(entry.getKey());
            } while(val1 != null && val2 <= val1);

            contains.put(entry.getKey(), val2);
         }
      }
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      BinaryOpExpState bstate = (BinaryOpExpState)state;
      boolean paren = bstate.joins != null && !bstate.joins.isEmpty();
      if (paren) {
         buf.append("(");
      }

      this._exp1.appendTo(sel, ctx, bstate.state1, buf);
      buf.append(" OR ");
      this._exp2.appendTo(sel, ctx, bstate.state2, buf);
      if (paren) {
         buf.append(")");
      }

      sel.append(buf, bstate.joins);
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
