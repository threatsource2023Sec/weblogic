package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.exps.AbstractExpressionVisitor;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.Constant;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.kernel.exps.InMemoryExpressionFactory;
import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Resolver;
import org.apache.openjpa.kernel.exps.StringContains;
import org.apache.openjpa.kernel.exps.Val;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.kernel.exps.WildcardMatch;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.RangeResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class ExpressionStoreQuery extends AbstractStoreQuery {
   private static final Localizer _loc = Localizer.forPackage(ExpressionStoreQuery.class);
   private static final FilterListener[] _listeners = new FilterListener[]{new StringContains(), new WildcardMatch()};
   private final ExpressionParser _parser;
   private transient Object _parsed;

   public ExpressionStoreQuery(ExpressionParser parser) {
      this._parser = parser;
   }

   public Resolver getResolver() {
      return new Resolver() {
         public Class classForName(String name, String[] imports) {
            return ExpressionStoreQuery.this.ctx.classForName(name, imports);
         }

         public FilterListener getFilterListener(String tag) {
            return ExpressionStoreQuery.this.ctx.getFilterListener(tag);
         }

         public AggregateListener getAggregateListener(String tag) {
            return ExpressionStoreQuery.this.ctx.getAggregateListener(tag);
         }

         public OpenJPAConfiguration getConfiguration() {
            return ExpressionStoreQuery.this.ctx.getStoreContext().getConfiguration();
         }

         public QueryContext getQueryContext() {
            return ExpressionStoreQuery.this.ctx;
         }
      };
   }

   public boolean setQuery(Object query) {
      this._parsed = query;
      return true;
   }

   public FilterListener getFilterListener(String tag) {
      for(int i = 0; i < _listeners.length; ++i) {
         if (_listeners[i].getTag().equals(tag)) {
            return _listeners[i];
         }
      }

      return null;
   }

   public Object newCompilation() {
      return this._parsed != null ? this._parsed : this._parser.parse(this.ctx.getQueryString(), this);
   }

   public void populateFromCompilation(Object comp) {
      this._parser.populate(comp, this);
   }

   public void invalidateCompilation() {
      this._parsed = null;
   }

   public boolean supportsInMemoryExecution() {
      return true;
   }

   public StoreQuery.Executor newInMemoryExecutor(ClassMetaData meta, boolean subs) {
      return new InMemoryExecutor(this, meta, subs, this._parser, this.ctx.getCompilation());
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subs) {
      return new DataStoreExecutor(this, meta, subs, this._parser, this.ctx.getCompilation());
   }

   protected ResultObjectProvider executeQuery(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params, StoreQuery.Range range) {
      throw new UnsupportedException();
   }

   protected Number executeDelete(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params) {
      return null;
   }

   protected Number executeUpdate(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params) {
      return null;
   }

   protected String[] getDataStoreActions(ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params, StoreQuery.Range range) {
      return StoreQuery.EMPTY_STRINGS;
   }

   protected ClassMetaData[] getIndependentExpressionCandidates(ClassMetaData type, boolean subclasses) {
      return new ClassMetaData[]{type};
   }

   protected ExpressionFactory getExpressionFactory(ClassMetaData type) {
      throw new UnsupportedException();
   }

   public static class DataStoreExecutor extends AbstractExpressionExecutor implements StoreQuery.Executor, Serializable {
      private ClassMetaData _meta;
      private ClassMetaData[] _metas;
      private boolean _subs;
      private ExpressionParser _parser;
      private ExpressionFactory[] _facts;
      private QueryExpressions[] _exps;
      private Class[] _projTypes;
      private Value[] _inMemOrdering;

      public DataStoreExecutor(ExpressionStoreQuery q, ClassMetaData meta, boolean subclasses, ExpressionParser parser, Object parsed) {
         this._metas = q.getIndependentExpressionCandidates(meta, subclasses);
         if (this._metas.length == 0) {
            throw new UserException(ExpressionStoreQuery._loc.get("query-unmapped", (Object)meta));
         } else {
            this._meta = meta;
            this._subs = subclasses;
            this._parser = parser;
            this._facts = new ExpressionFactory[this._metas.length];

            int i;
            for(i = 0; i < this._facts.length; ++i) {
               this._facts[i] = q.getExpressionFactory(this._metas[i]);
            }

            this._exps = new QueryExpressions[this._metas.length];

            for(i = 0; i < this._exps.length; ++i) {
               this._exps[i] = parser.eval(parsed, q, this._facts[i], this._metas[i]);
            }

            if (this._exps[0].projections.length == 0) {
               this._projTypes = StoreQuery.EMPTY_CLASSES;
            } else {
               this._projTypes = new Class[this._exps[0].projections.length];

               for(i = 0; i < this._exps[0].projections.length; ++i) {
                  this.assertNotContainer(this._exps[0].projections[i], q);
                  this._projTypes[i] = this._exps[0].projections[i].getType();
               }
            }

         }
      }

      protected QueryExpressions[] getQueryExpressions() {
         return this._exps;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         range.lrs &= !this.isAggregate(q) && !this.hasGrouping(q);
         return ((ExpressionStoreQuery)q).executeQuery(this, this._meta, this._metas, this._subs, this._facts, this._exps, params, range);
      }

      public Number executeDelete(StoreQuery q, Object[] params) {
         Number num = ((ExpressionStoreQuery)q).executeDelete(this, this._meta, this._metas, this._subs, this._facts, this._exps, params);
         return num == null ? q.getContext().deleteInMemory(q, this, params) : num;
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         Number num = ((ExpressionStoreQuery)q).executeUpdate(this, this._meta, this._metas, this._subs, this._facts, this._exps, params);
         return num == null ? q.getContext().updateInMemory(q, this, params) : num;
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         return ((ExpressionStoreQuery)q).getDataStoreActions(this._meta, this._metas, this._subs, this._facts, this._exps, params, range);
      }

      public Object getOrderingValue(StoreQuery q, Object[] params, Object resultObject, int orderIndex) {
         if (this._exps[0].projections.length > 0) {
            String ordering = this._exps[0].orderingClauses[orderIndex];

            for(int i = 0; i < this._exps[0].projectionClauses.length; ++i) {
               if (ordering.equals(this._exps[0].projectionClauses[i])) {
                  return ((Object[])((Object[])resultObject))[i];
               }
            }

            throw new InvalidStateException(ExpressionStoreQuery._loc.get("merged-order-with-result", q.getContext().getLanguage(), q.getContext().getQueryString(), ordering));
         } else {
            synchronized(this) {
               if (this._inMemOrdering == null) {
                  ExpressionFactory factory = new InMemoryExpressionFactory();
                  this._inMemOrdering = this._parser.eval((String[])this._exps[0].orderingClauses, (ExpressionStoreQuery)q, factory, this._meta);
               }

               if (this._inMemOrdering == null) {
                  this._inMemOrdering = this._exps[0].ordering;
               }
            }

            Val val = (Val)this._inMemOrdering[orderIndex];
            return val.evaluate(resultObject, resultObject, q.getContext().getStoreContext(), params);
         }
      }

      public Class[] getProjectionTypes(StoreQuery q) {
         return this._projTypes;
      }
   }

   private static class InMemoryExecutor extends AbstractExpressionExecutor implements StoreQuery.Executor, Serializable {
      private final ClassMetaData _meta;
      private final boolean _subs;
      private final InMemoryExpressionFactory _factory;
      private final QueryExpressions[] _exps;
      private final Class[] _projTypes;

      public InMemoryExecutor(ExpressionStoreQuery q, ClassMetaData candidate, boolean subclasses, ExpressionParser parser, Object parsed) {
         this._meta = candidate;
         this._subs = subclasses;
         this._factory = new InMemoryExpressionFactory();
         this._exps = new QueryExpressions[]{parser.eval((Object)parsed, q, this._factory, this._meta)};
         if (this._exps[0].projections.length == 0) {
            this._projTypes = StoreQuery.EMPTY_CLASSES;
         } else {
            AssertNoVariablesExpressionVisitor novars = new AssertNoVariablesExpressionVisitor(q.getContext());
            this._projTypes = new Class[this._exps[0].projections.length];

            int i;
            for(i = 0; i < this._exps[0].projections.length; ++i) {
               this._projTypes[i] = this._exps[0].projections[i].getType();
               this.assertNotContainer(this._exps[0].projections[i], q);
               this._exps[0].projections[i].acceptVisit(novars);
            }

            for(i = 0; i < this._exps[0].grouping.length; ++i) {
               this._exps[0].grouping[i].acceptVisit(novars);
            }
         }

      }

      protected QueryExpressions[] getQueryExpressions() {
         return this._exps;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         Collection coll = q.getContext().getCandidateCollection();
         Iterator itr;
         if (coll != null) {
            itr = coll.iterator();
         } else {
            itr = q.getContext().getStoreContext().extentIterator(this._meta.getDescribedType(), this._subs, q.getContext().getFetchConfiguration(), q.getContext().getIgnoreChanges());
         }

         List results = new ArrayList();
         StoreContext ctx = q.getContext().getStoreContext();

         Object rop;
         try {
            while(itr.hasNext()) {
               rop = itr.next();
               if (this._factory.matches(this._exps[0], this._meta, this._subs, rop, ctx, params)) {
                  results.add(rop);
               }
            }
         } finally {
            ImplHelper.close(itr);
         }

         List results = this._factory.group(this._exps[0], results, ctx, params);
         if (this._exps[0].having != null) {
            List matches = new ArrayList(((List)results).size());
            itr = ((List)results).iterator();

            while(itr.hasNext()) {
               Collection c = (Collection)itr.next();
               if (this._factory.matches(this._exps[0], c, ctx, params)) {
                  matches.add(c);
               }
            }

            results = matches;
         }

         List results = this._factory.project(this._exps[0], (List)results, ctx, params);
         results = this._factory.order(this._exps[0], results, ctx, params);
         results = this._factory.distinct(this._exps[0], coll == null, results);
         rop = new ListResultObjectProvider(results);
         if (range.start != 0L || range.end != Long.MAX_VALUE) {
            rop = new RangeResultObjectProvider((ResultObjectProvider)rop, range.start, range.end);
         }

         return (ResultObjectProvider)rop;
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         return StoreQuery.EMPTY_STRINGS;
      }

      public Object getOrderingValue(StoreQuery q, Object[] params, Object resultObject, int orderIndex) {
         if (this._exps[0].projections.length > 0) {
            String ordering = this._exps[0].orderingClauses[orderIndex];

            for(int i = 0; i < this._exps[0].projectionClauses.length; ++i) {
               if (ordering.equals(this._exps[0].projectionClauses[i])) {
                  return ((Object[])((Object[])resultObject))[i];
               }
            }

            throw new InvalidStateException(ExpressionStoreQuery._loc.get("merged-order-with-result", q.getContext().getLanguage(), q.getContext().getQueryString(), ordering));
         } else {
            Val val = (Val)this._exps[0].ordering[orderIndex];
            return val.evaluate(resultObject, resultObject, q.getContext().getStoreContext(), params);
         }
      }

      public Class[] getProjectionTypes(StoreQuery q) {
         return this._projTypes;
      }

      private static class AssertNoVariablesExpressionVisitor extends AbstractExpressionVisitor {
         private final QueryContext _ctx;

         public AssertNoVariablesExpressionVisitor(QueryContext ctx) {
            this._ctx = ctx;
         }

         public void enter(Value val) {
            if (val.isVariable()) {
               throw new UnsupportedException(ExpressionStoreQuery._loc.get("inmem-agg-proj-var", this._ctx.getCandidateType(), this._ctx.getQueryString()));
            }
         }
      }
   }

   public abstract static class AbstractExpressionExecutor extends AbstractStoreQuery.AbstractExecutor implements StoreQuery.Executor {
      protected abstract QueryExpressions[] getQueryExpressions();

      private QueryExpressions assertQueryExpression() {
         QueryExpressions[] exp = this.getQueryExpressions();
         if (exp != null && exp.length >= 1) {
            return exp[0];
         } else {
            throw new InvalidStateException(ExpressionStoreQuery._loc.get("no-expressions"));
         }
      }

      protected void assertNotContainer(Value var1, StoreQuery var2) {
         // $FF: Couldn't be decompiled
      }

      public final void validate(StoreQuery q) {
         QueryExpressions exps = this.assertQueryExpression();
         ExpressionStoreQuery.AbstractExpressionExecutor.ValidateGroupingExpressionVisitor.validate(q.getContext(), exps);
      }

      public void getRange(StoreQuery q, Object[] params, StoreQuery.Range range) {
         QueryExpressions exps = this.assertQueryExpression();
         if (exps.range.length != 0) {
            if (exps.range.length == 2 && exps.range[0] instanceof Constant && exps.range[1] instanceof Constant) {
               try {
                  range.start = ((Number)((Constant)exps.range[0]).getValue(params)).longValue();
                  range.end = ((Number)((Constant)exps.range[1]).getValue(params)).longValue();
                  return;
               } catch (ClassCastException var6) {
               } catch (NullPointerException var7) {
               }
            }

            throw new UserException(ExpressionStoreQuery._loc.get("only-range-constants", (Object)q.getContext().getQueryString()));
         }
      }

      public final Class getResultClass(StoreQuery q) {
         return this.assertQueryExpression().resultClass;
      }

      public final boolean[] getAscending(StoreQuery q) {
         return this.assertQueryExpression().ascending;
      }

      public final String getAlias(StoreQuery q) {
         return this.assertQueryExpression().alias;
      }

      public final String[] getProjectionAliases(StoreQuery q) {
         return this.assertQueryExpression().projectionAliases;
      }

      public final int getOperation(StoreQuery q) {
         return this.assertQueryExpression().operation;
      }

      public final boolean isAggregate(StoreQuery q) {
         return this.assertQueryExpression().isAggregate();
      }

      public final boolean hasGrouping(StoreQuery q) {
         return this.assertQueryExpression().grouping.length > 0;
      }

      public final LinkedMap getParameterTypes(StoreQuery q) {
         return this.assertQueryExpression().parameterTypes;
      }

      public final Map getUpdates(StoreQuery q) {
         return this.assertQueryExpression().updates;
      }

      public final ClassMetaData[] getAccessPathMetaDatas(StoreQuery q) {
         QueryExpressions[] exps = this.getQueryExpressions();
         if (exps.length == 1) {
            return exps[0].accessPath;
         } else {
            List metas = null;

            for(int i = 0; i < exps.length; ++i) {
               metas = Filters.addAccessPathMetaDatas(metas, exps[i].accessPath);
            }

            return metas == null ? StoreQuery.EMPTY_METAS : (ClassMetaData[])((ClassMetaData[])metas.toArray(new ClassMetaData[metas.size()]));
         }
      }

      public boolean isPacking(StoreQuery q) {
         return false;
      }

      private static class ValidateGroupingExpressionVisitor extends AbstractExpressionVisitor {
         private final QueryContext _ctx;
         private boolean _grouping = false;
         private Set _grouped = null;
         private Value _agg = null;

         public static void validate(QueryContext ctx, QueryExpressions exps) {
            if (exps.grouping.length != 0) {
               ValidateGroupingExpressionVisitor visitor = new ValidateGroupingExpressionVisitor(ctx);
               visitor._grouping = true;

               int i;
               for(i = 0; i < exps.grouping.length; ++i) {
                  exps.grouping[i].acceptVisit(visitor);
               }

               visitor._grouping = false;
               if (exps.having != null) {
                  exps.having.acceptVisit(visitor);
               }

               for(i = 0; i < exps.projections.length; ++i) {
                  exps.projections[i].acceptVisit(visitor);
               }

            }
         }

         public ValidateGroupingExpressionVisitor(QueryContext ctx) {
            this._ctx = ctx;
         }

         public void enter(Value val) {
            if (this._grouping) {
               if (val instanceof Path) {
                  if (this._grouped == null) {
                     this._grouped = new HashSet();
                  }

                  this._grouped.add(val);
               }
            } else if (this._agg == null) {
               if (val.isAggregate()) {
                  this._agg = val;
               } else if (val instanceof Path && (this._grouped == null || !this._grouped.contains(val))) {
                  throw new UserException(ExpressionStoreQuery._loc.get("bad-grouping", this._ctx.getCandidateType(), this._ctx.getQueryString()));
               }
            }

         }

         public void exit(Value val) {
            if (val == this._agg) {
               this._agg = null;
            }

         }
      }
   }
}
