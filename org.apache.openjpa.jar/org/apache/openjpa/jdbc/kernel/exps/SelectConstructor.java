package org.apache.openjpa.jdbc.kernel.exps;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.AbstractExpressionVisitor;
import org.apache.openjpa.kernel.exps.Constant;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Value;

public class SelectConstructor implements Serializable {
   private boolean _extent = false;

   public boolean isExtent() {
      return this._extent;
   }

   public Select evaluate(ExpContext ctx, Select parent, String alias, QueryExpressions exps, QueryExpressionsState state) {
      Select sel;
      if (this._extent) {
         sel = ctx.store.getSQLFactory().newSelect();
         sel.setAutoDistinct((exps.distinct & 2) != 0);
         return sel;
      } else {
         sel = this.newSelect(ctx, parent, alias, exps, state);
         Select inner = sel.getFromSelect();
         SQLBuffer where = this.buildWhere(inner != null ? inner : sel, ctx, state.filter, exps.filter);
         if (where != null || exps.projections.length != 0 || exps.ordering.length != 0 || sel.getJoins() != null && !sel.getJoins().isEmpty()) {
            if (inner != null) {
               inner.where(where);
            } else {
               sel.where(where);
            }

            if (exps.having != null) {
               Exp havingExp = (Exp)exps.having;
               SQLBuffer buf = new SQLBuffer(ctx.store.getDBDictionary());
               havingExp.appendTo(sel, ctx, state.having, buf);
               sel.having(buf);
            }

            for(int i = 0; i < exps.grouping.length; ++i) {
               ((Val)exps.grouping[i]).groupBy(sel, ctx, state.grouping[i]);
            }

            return sel;
         } else {
            this._extent = true;
            sel.setAutoDistinct((exps.distinct & 2) != 0);
            return sel;
         }
      }
   }

   private Select newSelect(ExpContext ctx, Select parent, String alias, QueryExpressions exps, QueryExpressionsState state) {
      Select sel = ctx.store.getSQLFactory().newSelect();
      sel.setAutoDistinct((exps.distinct & 2) != 0);
      sel.setJoinSyntax(ctx.fetch.getJoinSyntax());
      sel.setParent(parent, alias);
      this.initialize(sel, ctx, exps, state);
      if (!sel.getAutoDistinct()) {
         if ((exps.distinct & 4) != 0) {
            sel.setDistinct(true);
         } else if ((exps.distinct & 8) != 0) {
            sel.setDistinct(false);
         }
      } else if (exps.projections.length > 0) {
         if (!sel.isDistinct() && (exps.distinct & 4) != 0) {
            sel.setDistinct(true);
         } else if (sel.isDistinct()) {
            boolean agg = exps.isAggregate();
            boolean candidate = SelectConstructor.ProjectionExpressionVisitor.hasCandidateProjections(exps.projections);
            if (!agg && (!candidate || (exps.distinct & 4) != 0)) {
               if (!candidate && (exps.distinct & 4) == 0) {
                  sel.setDistinct(false);
               }
            } else {
               DBDictionary dict = ctx.store.getDBDictionary();
               dict.assertSupport(dict.supportsSubselect, "SupportsSubselect");
               Select inner = sel;
               sel = ctx.store.getSQLFactory().newSelect();
               sel.setParent(parent, alias);
               sel.setDistinct(agg && (exps.distinct & 4) != 0);
               sel.setFromSelect(inner);
            }
         }
      }

      return sel;
   }

   private void initialize(Select sel, ExpContext ctx, QueryExpressions exps, QueryExpressionsState state) {
      Map contains = null;
      if (HasContainsExpressionVisitor.hasContains(exps.filter) || HasContainsExpressionVisitor.hasContains(exps.having)) {
         contains = new HashMap(7);
      }

      Exp filterExp = (Exp)exps.filter;
      state.filter = filterExp.initialize(sel, ctx, contains);
      Exp havingExp = (Exp)exps.having;
      if (havingExp != null) {
         state.having = havingExp.initialize(sel, ctx, contains);
      }

      Joins filterJoins = state.filter.joins;
      Joins havingJoins = state.having == null ? null : state.having.joins;
      Joins joins = sel.and(filterJoins, havingJoins);
      Val orderVal;
      int i;
      if (exps.projections.length > 0) {
         state.projections = new ExpState[exps.projections.length];

         for(i = 0; i < exps.projections.length; ++i) {
            orderVal = (Val)exps.projections[i];
            state.projections[i] = orderVal.initialize(sel, ctx, 12);
            joins = sel.and(joins, state.projections[i].joins);
         }
      }

      if (exps.grouping.length > 0) {
         state.grouping = new ExpState[exps.grouping.length];

         for(i = 0; i < exps.grouping.length; ++i) {
            orderVal = (Val)exps.grouping[i];
            state.grouping[i] = orderVal.initialize(sel, ctx, 4);
            joins = sel.and(joins, state.grouping[i].joins);
         }
      }

      if (exps.ordering.length > 0) {
         state.ordering = new ExpState[exps.ordering.length];

         for(i = 0; i < exps.ordering.length; ++i) {
            orderVal = (Val)exps.ordering[i];
            state.ordering[i] = orderVal.initialize(sel, ctx, 0);
            joins = sel.and(joins, state.ordering[i].joins);
         }
      }

      sel.where(joins);
   }

   private SQLBuffer buildWhere(Select sel, ExpContext ctx, ExpState state, Expression filter) {
      SQLBuffer where = new SQLBuffer(ctx.store.getDBDictionary());
      where.append("(");
      Exp filterExp = (Exp)filter;
      filterExp.appendTo(sel, ctx, state, where);
      return !where.sqlEquals("(") && !where.sqlEquals("(1 = 1") ? where.append(")") : null;
   }

   public void select(Select sel, ExpContext ctx, ClassMapping mapping, boolean subclasses, QueryExpressions exps, QueryExpressionsState state, int eager) {
      Select inner = sel.getFromSelect();
      Joins joins = null;
      if (sel.getSubselectPath() != null) {
         joins = sel.newJoins().setSubselect(sel.getSubselectPath());
      }

      int i;
      for(i = 0; i < exps.ordering.length; ++i) {
         ((Val)exps.ordering[i]).orderBy(sel, ctx, state.ordering[i], exps.ascending[i]);
      }

      Val val;
      if (exps.projections.length == 0 && sel.getParent() == null) {
         i = subclasses ? 1 : 2;
         sel.selectIdentifier(mapping, i, ctx.store, ctx.fetch, eager);
      } else if (exps.projections.length == 0) {
         sel.select(mapping.getPrimaryKeyColumns(), joins);
      } else {
         if (inner != null) {
            inner.select(mapping.getPrimaryKeyColumns(), joins);
         }

         boolean pks = sel.getParent() != null;

         for(int i = 0; i < exps.projections.length; ++i) {
            val = (Val)exps.projections[i];
            if (inner != null) {
               val.selectColumns(inner, ctx, state.projections[i], pks);
            }

            val.select(sel, ctx, state.projections[i], pks);
         }

         if (exps.having != null && inner != null) {
            ((Exp)exps.having).selectColumns(inner, ctx, state.having, true);
         }
      }

      for(i = 0; i < exps.ordering.length; ++i) {
         val = (Val)exps.ordering[i];
         if (inner != null) {
            val.selectColumns(inner, ctx, state.ordering[i], true);
         }

         val.select(sel, ctx, state.ordering[i], true);
      }

      if (exps.projections.length > 0 || sel.getParent() != null) {
         ctx.store.loadSubclasses(mapping);
         mapping.getDiscriminator().addClassConditions(inner != null ? inner : sel, subclasses, joins);
      }

   }

   private static class ProjectionExpressionVisitor extends AbstractExpressionVisitor {
      private boolean _candidate = false;
      private int _level = 0;

      public static boolean hasCandidateProjections(Value[] projs) {
         ProjectionExpressionVisitor v = new ProjectionExpressionVisitor();

         for(int i = 0; i < projs.length; ++i) {
            projs[i].acceptVisit(v);
            if (v._candidate) {
               return true;
            }
         }

         return false;
      }

      public void enter(Value val) {
         if (!this._candidate) {
            this._candidate = this._level == 0 && val instanceof Constant || val instanceof PCPath && !((PCPath)val).isVariablePath();
         }

         ++this._level;
      }

      public void exit(Value val) {
         --this._level;
      }
   }
}
