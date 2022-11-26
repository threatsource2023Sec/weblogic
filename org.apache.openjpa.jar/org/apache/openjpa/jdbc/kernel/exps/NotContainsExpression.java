package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class NotContainsExpression implements Exp {
   private final Exp _exp;

   public NotContainsExpression(Exp exp) {
      this._exp = exp;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      return new NotContainsExpState(contains);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer buf) {
      DBDictionary dict = ctx.store.getDBDictionary();
      dict.assertSupport(dict.supportsSubselect, "SupportsSubselect");
      Select sub = ctx.store.getSQLFactory().newSelect();
      sub.setParent(sel, (String)null);
      ExpState estate = this._exp.initialize(sub, ctx, ((NotContainsExpState)state).contains);
      sub.where(sub.and((Joins)null, estate.joins));
      SQLBuffer where = (new SQLBuffer(dict)).append("(");
      this._exp.appendTo(sub, ctx, estate, where);
      if (where.getSQL().length() > 1) {
         sub.where(where.append(")"));
      }

      buf.append("0 = ");
      buf.appendCount(sub, ctx.fetch);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      ExpState estate = this._exp.initialize(sel, ctx, ((NotContainsExpState)state).contains);
      this._exp.selectColumns(sel, ctx, estate, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._exp.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }

   private static class NotContainsExpState extends ExpState {
      public final Map contains;

      public NotContainsExpState(Map contains) {
         this.contains = contains;
      }
   }
}
