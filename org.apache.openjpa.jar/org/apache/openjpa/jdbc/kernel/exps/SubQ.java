package org.apache.openjpa.jdbc.kernel.exps;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Subquery;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.meta.ClassMetaData;

class SubQ extends AbstractVal implements Subquery {
   private final ClassMapping _candidate;
   private final boolean _subs;
   private final String _alias;
   private final SelectConstructor _cons = new SelectConstructor();
   private Class _type = null;
   private ClassMetaData _meta = null;
   private QueryExpressions _exps = null;

   public SubQ(ClassMapping candidate, boolean subs, String alias) {
      this._candidate = candidate;
      this._subs = subs;
      this._alias = alias;
   }

   public ClassMapping getCandidate() {
      return this._candidate;
   }

   public Class getType() {
      if (this._exps != null) {
         if (this._exps.projections.length == 0) {
            return this._candidate.getDescribedType();
         }

         if (this._exps.projections.length == 1) {
            return this._exps.projections[0].getType();
         }
      }

      return this._type;
   }

   public void setImplicitType(Class type) {
      if (this._exps != null && this._exps.projections.length == 1) {
         this._exps.projections[0].setImplicitType(type);
      }

      this._type = type;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public String getCandidateAlias() {
      return this._alias;
   }

   public void setQueryExpressions(QueryExpressions query) {
      this._exps = query;
   }

   public ExpState initialize(Select sel, ExpContext ctx, int flags) {
      return this._exps.projections.length == 1 ? ((Val)this._exps.projections[0]).initialize(sel, ctx, flags) : ExpState.NULL;
   }

   public Object toDataStoreValue(Select sel, ExpContext ctx, ExpState state, Object val) {
      if (this._exps.projections.length == 0) {
         return this._candidate.toDataStoreValue(val, this._candidate.getPrimaryKeyColumns(), ctx.store);
      } else {
         return this._exps.projections.length == 1 ? ((Val)this._exps.projections[0]).toDataStoreValue(sel, ctx, state, val) : val;
      }
   }

   public void select(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      this.selectColumns(sel, ctx, state, pks);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      sel.select((SQLBuffer)this.newSQLBuffer(sel, ctx, state), (Object)this);
   }

   public void groupBy(Select sel, ExpContext ctx, ExpState state) {
      sel.groupBy(this.newSQLBuffer(sel, ctx, state));
   }

   public void orderBy(Select sel, ExpContext ctx, ExpState state, boolean asc) {
      sel.orderBy(this.newSQLBuffer(sel, ctx, state), asc, false);
   }

   private SQLBuffer newSQLBuffer(Select sel, ExpContext ctx, ExpState state) {
      SQLBuffer buf = new SQLBuffer(ctx.store.getDBDictionary());
      this.appendTo(sel, ctx, state, buf, 0);
      return buf;
   }

   public Object load(ExpContext ctx, ExpState state, Result res) throws SQLException {
      return Filters.convert(res.getObject(this, 1012, (Object)null), this.getType());
   }

   public void calculateValue(Select sel, ExpContext ctx, ExpState state, Val other, ExpState otherState) {
   }

   public int length(Select sel, ExpContext ctx, ExpState state) {
      return 1;
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index) {
      this.appendTo(sel, ctx, state, sql, index, false);
   }

   private void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql, int index, boolean size) {
      QueryExpressionsState substate = new QueryExpressionsState();
      Select sub = this._cons.evaluate(ctx, sel, this._alias, this._exps, substate);
      this._cons.select(sub, ctx, this._candidate, this._subs, this._exps, substate, 0);
      if (size) {
         sql.appendCount(sub, ctx.fetch);
      } else {
         sql.append(sub, ctx.fetch);
      }

   }

   public void appendIsEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      sql.append("NOT EXISTS ");
      this.appendTo(sel, ctx, state, sql, 0);
   }

   public void appendIsNotEmpty(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      sql.append("EXISTS ");
      this.appendTo(sel, ctx, state, sql, 0);
   }

   public void appendSize(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      this.appendTo(sel, ctx, state, sql, 0, true);
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);

      int i;
      for(i = 0; i < this._exps.projections.length; ++i) {
         this._exps.projections[i].acceptVisit(visitor);
      }

      if (this._exps.filter != null) {
         this._exps.filter.acceptVisit(visitor);
      }

      for(i = 0; i < this._exps.grouping.length; ++i) {
         this._exps.grouping[i].acceptVisit(visitor);
      }

      if (this._exps.having != null) {
         this._exps.having.acceptVisit(visitor);
      }

      for(i = 0; i < this._exps.ordering.length; ++i) {
         this._exps.ordering[i].acceptVisit(visitor);
      }

      visitor.exit((Value)this);
   }
}
