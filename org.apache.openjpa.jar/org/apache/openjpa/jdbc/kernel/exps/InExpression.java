package org.apache.openjpa.jdbc.kernel.exps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class InExpression implements Exp {
   private final Val _val;
   private final Const _const;

   public InExpression(Val val, Const constant) {
      this._val = val;
      this._const = constant;
   }

   public Const getConstant() {
      return this._const;
   }

   public Val getValue() {
      return this._val;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState valueState = this._val.initialize(sel, ctx, 0);
      ExpState constantState = this._const.initialize(sel, ctx, 0);
      return new InExpState(valueState.joins, constantState, valueState);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      InExpState istate = (InExpState)state;
      this._const.calculateValue(sel, ctx, istate.constantState, (Val)null, (ExpState)null);
      this._val.calculateValue(sel, ctx, istate.valueState, (Val)null, (ExpState)null);
      List list = null;
      Collection coll = this.getCollection(ctx, istate.constantState);
      if (coll != null) {
         list = new ArrayList(coll.size());
         Iterator itr = coll.iterator();

         while(itr.hasNext()) {
            list.add(this._val.toDataStoreValue(sel, ctx, istate.valueState, itr.next()));
         }
      }

      Column[] cols = null;
      if (this._val instanceof PCPath) {
         cols = ((PCPath)this._val).getColumns(istate.valueState);
      } else if (this._val instanceof GetObjectId) {
         cols = ((GetObjectId)this._val).getColumns(istate.valueState);
      }

      if (list != null && !list.isEmpty()) {
         if (this._val.length(sel, ctx, istate.valueState) == 1) {
            this.createInContains(sel, ctx, istate.valueState, buf, list, cols);
         } else {
            this.orContains(sel, ctx, istate.valueState, buf, list, cols);
         }
      } else {
         buf.append("1 <> 1");
      }

      sel.append(buf, state.joins);
   }

   private void createInContains(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf, List list, Column[] cols) {
      int inClauseLimit = ctx.store.getDBDictionary().inClauseLimit;
      if (inClauseLimit > 0 && list.size() > inClauseLimit) {
         buf.append("(");

         int high;
         for(int low = 0; low < list.size(); low = high) {
            if (low > 0) {
               buf.append(" OR ");
            }

            high = java.lang.Math.min(low + inClauseLimit, list.size());
            this.inContains(sel, ctx, state, buf, list.subList(low, high), cols);
         }

         buf.append(")");
      } else {
         this.inContains(sel, ctx, state, buf, list, cols);
      }

   }

   private void inContains(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf, Collection coll, Column[] cols) {
      this._val.appendTo(sel, ctx, state, buf, 0);
      buf.append(" IN (");
      Column col = cols != null && cols.length == 1 ? cols[0] : null;
      Iterator itr = coll.iterator();

      while(itr.hasNext()) {
         buf.appendValue(itr.next(), col);
         if (itr.hasNext()) {
            buf.append(", ");
         }
      }

      buf.append(")");
   }

   private void orContains(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf, Collection coll, Column[] cols) {
      if (coll.size() > 1) {
         buf.append("(");
      }

      Iterator itr = coll.iterator();

      while(itr.hasNext()) {
         Object[] vals = (Object[])((Object[])itr.next());
         buf.append("(");

         for(int i = 0; i < vals.length; ++i) {
            Column col = cols != null && cols.length == vals.length ? cols[i] : null;
            if (i > 0) {
               buf.append(" AND ");
            }

            this._val.appendTo(sel, ctx, state, buf, i);
            if (vals[i] == null) {
               buf.append(" IS ");
            } else {
               buf.append(" = ");
            }

            buf.appendValue(vals[i], col);
         }

         buf.append(")");
         if (itr.hasNext()) {
            buf.append(" OR ");
         }
      }

      if (coll.size() > 1) {
         buf.append(")");
      }

   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      InExpState istate = (InExpState)state;
      this._const.selectColumns(sel, ctx, istate.constantState, true);
      this._val.selectColumns(sel, ctx, istate.valueState, true);
   }

   protected Collection getCollection(ExpContext ctx, ExpState state) {
      Object val = this._const.getValue(ctx, state);
      if (!(val instanceof Collection)) {
         val = Collections.singleton(val);
      }

      return (Collection)val;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._val.acceptVisit(visitor);
      this._const.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }

   private static class InExpState extends ExpState {
      public final ExpState constantState;
      public final ExpState valueState;

      public InExpState(Joins joins, ExpState constantState, ExpState valueState) {
         super(joins);
         this.constantState = constantState;
         this.valueState = valueState;
      }
   }
}
