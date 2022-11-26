package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.jdbc.kernel.exps.ExpContext;
import org.apache.openjpa.jdbc.kernel.exps.GetColumn;
import org.apache.openjpa.jdbc.kernel.exps.JDBCExpressionFactory;
import org.apache.openjpa.jdbc.kernel.exps.JDBCStringContains;
import org.apache.openjpa.jdbc.kernel.exps.JDBCWildcardMatch;
import org.apache.openjpa.jdbc.kernel.exps.QueryExpressionsState;
import org.apache.openjpa.jdbc.kernel.exps.SQLEmbed;
import org.apache.openjpa.jdbc.kernel.exps.SQLExpression;
import org.apache.openjpa.jdbc.kernel.exps.SQLValue;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.strats.VerticalClassStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.OrderingMergedResultObjectProvider;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.RangeResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;

public class JDBCStoreQuery extends ExpressionStoreQuery {
   private static final Table INVALID = new Table();
   private static final Map _listeners = new HashMap();
   private final transient JDBCStore _store;

   public JDBCStoreQuery(JDBCStore store, ExpressionParser parser) {
      super(parser);
      this._store = store;
   }

   public JDBCStore getStore() {
      return this._store;
   }

   public FilterListener getFilterListener(String tag) {
      return (FilterListener)_listeners.get(tag);
   }

   public Object newCompilationKey() {
      JDBCFetchConfiguration fetch = (JDBCFetchConfiguration)this.ctx.getFetchConfiguration();
      return Numbers.valueOf(fetch.getJoinSyntax());
   }

   public boolean supportsDataStoreExecution() {
      return true;
   }

   protected ClassMetaData[] getIndependentExpressionCandidates(ClassMetaData meta, boolean subclasses) {
      return !subclasses ? new ClassMapping[]{(ClassMapping)meta} : ((ClassMapping)meta).getIndependentAssignableMappings();
   }

   protected ExpressionFactory getExpressionFactory(ClassMetaData meta) {
      return new JDBCExpressionFactory((ClassMapping)meta);
   }

   protected ResultObjectProvider executeQuery(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] metas, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] exps, Object[] params, StoreQuery.Range range) {
      if (metas.length > 1 && exps[0].isAggregate()) {
         throw new UserException(Localizer.forPackage(JDBCStoreQuery.class).get("mult-mapping-aggregate", (Object)Arrays.asList(metas)));
      } else {
         ClassMapping[] mappings = (ClassMapping[])((ClassMapping[])metas);
         JDBCFetchConfiguration fetch = (JDBCFetchConfiguration)this.ctx.getFetchConfiguration();
         if (exps[0].fetchPaths != null) {
            fetch.addFields(Arrays.asList(exps[0].fetchPaths));
            fetch.addJoins(Arrays.asList(exps[0].fetchPaths));
         }

         if (exps[0].fetchInnerPaths != null) {
            fetch.addFetchInnerJoins(Arrays.asList(exps[0].fetchInnerPaths));
         }

         int eager = this.calculateEagerMode(exps[0], range.start, range.end);
         int subclassMode = fetch.getSubclassFetchMode((ClassMapping)base);
         DBDictionary dict = this._store.getDBDictionary();
         long start = mappings.length == 1 && dict.supportsSelectStartIndex ? range.start : 0L;
         long end = dict.supportsSelectEndIndex ? range.end : Long.MAX_VALUE;
         QueryExpressionsState[] states = new QueryExpressionsState[exps.length];

         for(int i = 0; i < states.length; ++i) {
            states[i] = new QueryExpressionsState();
         }

         ExpContext ctx = new ExpContext(this._store, params, fetch);
         List sels = new ArrayList(mappings.length);
         List selMappings = new ArrayList(mappings.length);
         BitSet subclassBits = new BitSet();
         BitSet nextBits = new BitSet();
         boolean unionable = this.createWhereSelects(sels, mappings, selMappings, subclasses, subclassBits, nextBits, facts, exps, states, ctx, subclassMode) && subclassMode == 1 && start == 0L && end == Long.MAX_VALUE;
         if (sels.size() > 1) {
            start = 0L;
         }

         boolean lrs = range.lrs || fetch.getFetchBatchSize() >= 0 && (start != range.start || end != range.end);
         ResultObjectProvider[] rops = null;
         ResultObjectProvider rop = null;
         if (unionable) {
            Union union = this._store.getSQLFactory().newUnion((Select[])((Select[])sels.toArray(new Select[sels.size()])));
            BitSet[] paged = this.populateUnion(union, mappings, subclasses, facts, exps, states, ctx, lrs, eager, start, end);
            union.setLRS(lrs);
            rop = this.executeUnion(union, mappings, exps, states, ctx, paged);
         } else {
            if (sels.size() > 1) {
               rops = new ResultObjectProvider[sels.size()];
            }

            int i = 0;

            for(int idx = 0; i < sels.size(); ++i) {
               Select sel = (Select)sels.get(i);
               BitSet paged = this.populateSelect(sel, (ClassMapping)selMappings.get(i), subclassBits.get(i), (JDBCExpressionFactory)facts[idx], exps[idx], states[idx], ctx, lrs, eager, start, end);
               rop = this.executeSelect(sel, (ClassMapping)selMappings.get(i), exps[idx], states[idx], ctx, paged, start, end);
               if (rops != null) {
                  rops[i] = (ResultObjectProvider)rop;
               }

               if (nextBits.get(i)) {
                  ++idx;
               }
            }
         }

         if (rops != null) {
            if (exps[0].ascending.length == 0) {
               rop = new MergedResultObjectProvider(rops);
            } else {
               rop = new OrderingMergedResultObjectProvider(rops, exps[0].ascending, ex, this, params);
            }
         }

         if (rops != null && range.end != Long.MAX_VALUE || start != range.start || end != range.end) {
            rop = new RangeResultObjectProvider((ResultObjectProvider)rop, range.start, range.end);
         }

         return (ResultObjectProvider)rop;
      }
   }

   private BitSet[] populateUnion(Union union, final ClassMapping[] mappings, final boolean subclasses, final ExpressionFactory[] facts, final QueryExpressions[] exps, final QueryExpressionsState[] states, final ExpContext ctx, final boolean lrs, final int eager, final long start, final long end) {
      final BitSet[] paged = exps[0].projections.length > 0 ? null : new BitSet[mappings.length];
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            BitSet bits = JDBCStoreQuery.this.populateSelect(sel, mappings[idx], subclasses, (JDBCExpressionFactory)facts[idx], exps[idx], states[idx], ctx, lrs, eager, start, end);
            if (paged != null) {
               paged[idx] = bits;
            }

         }
      });
      return paged;
   }

   private BitSet populateSelect(Select sel, ClassMapping mapping, boolean subclasses, JDBCExpressionFactory fact, QueryExpressions exps, QueryExpressionsState state, ExpContext ctx, boolean lrs, int eager, long start, long end) {
      sel.setLRS(lrs);
      sel.setRange(start, end);
      BitSet paged = null;
      if (exps.projections.length == 0) {
         paged = PagingResultObjectProvider.getPagedFields(sel, mapping, this._store, ctx.fetch, eager, end - start);
         if (paged != null) {
            eager = 1;
         }
      }

      fact.getSelectConstructor().select(sel, ctx, mapping, subclasses, exps, state, eager);
      return paged;
   }

   private ResultObjectProvider executeUnion(Union union, ClassMapping[] mappings, QueryExpressions[] exps, QueryExpressionsState[] states, ExpContext ctx, BitSet[] paged) {
      if (exps[0].projections.length > 0) {
         return new ProjectionResultObjectProvider(union, exps, states, ctx);
      } else {
         if (paged != null) {
            for(int i = 0; i < paged.length; ++i) {
               if (paged[i] != null) {
                  return new PagingResultObjectProvider(union, mappings, this._store, ctx.fetch, paged, Long.MAX_VALUE);
               }
            }
         }

         return new InstanceResultObjectProvider(union, mappings[0], this._store, ctx.fetch);
      }
   }

   private ResultObjectProvider executeSelect(Select sel, ClassMapping mapping, QueryExpressions exps, QueryExpressionsState state, ExpContext ctx, BitSet paged, long start, long end) {
      if (exps.projections.length > 0) {
         return new ProjectionResultObjectProvider(sel, exps, state, ctx);
      } else {
         return (ResultObjectProvider)(paged != null ? new PagingResultObjectProvider(sel, mapping, this._store, ctx.fetch, paged, end - start) : new InstanceResultObjectProvider(sel, mapping, this._store, ctx.fetch));
      }
   }

   private boolean createWhereSelects(List sels, ClassMapping[] mappings, List selMappings, boolean subclasses, BitSet subclassBits, BitSet nextBits, ExpressionFactory[] facts, QueryExpressions[] exps, QueryExpressionsState[] states, ExpContext ctx, int subclassMode) {
      Number optHint = (Number)ctx.fetch.getHint("openjpa.hint.OptimizeResultCount");
      boolean unionable = true;

      for(int i = 0; i < mappings.length; ++i) {
         ClassMapping[] verts = this.getVerticalMappings(mappings[i], subclasses, exps[i], subclassMode);
         if (verts.length == 1 && subclasses) {
            subclassBits.set(sels.size());
         }

         Select sel = ((JDBCExpressionFactory)facts[i]).getSelectConstructor().evaluate(ctx, (Select)null, (String)null, exps[i], states[i]);
         if (optHint != null) {
            sel.setExpectedResultCount(optHint.intValue(), true);
         } else if (this.ctx.isUnique()) {
            sel.setExpectedResultCount(1, false);
         }

         for(int j = 0; j < verts.length; ++j) {
            selMappings.add(verts[j]);
            if (j == verts.length - 1) {
               nextBits.set(sels.size());
               sels.add(sel);
            } else {
               sels.add(sel.fullClone(1));
            }
         }

         if (verts.length > 1 || sel.getFromSelect() != null) {
            unionable = false;
         }
      }

      return unionable;
   }

   private ClassMapping[] getVerticalMappings(ClassMapping mapping, boolean subclasses, QueryExpressions exps, int subclassMode) {
      if (subclasses && exps.projections.length <= 0) {
         if (subclassMode == 2 && hasVerticalSubclasses(mapping)) {
            List subs = new ArrayList(4);
            this.addSubclasses(mapping, subs);
            return (ClassMapping[])((ClassMapping[])subs.toArray(new ClassMapping[subs.size()]));
         } else {
            return new ClassMapping[]{mapping};
         }
      } else {
         return new ClassMapping[]{mapping};
      }
   }

   private void addSubclasses(ClassMapping mapping, Collection subs) {
      subs.add(mapping);
      if (hasVerticalSubclasses(mapping)) {
         ClassMapping[] subMappings = mapping.getJoinablePCSubclassMappings();

         for(int i = 0; i < subMappings.length; ++i) {
            if (subMappings[i].getJoinablePCSuperclassMapping() == mapping) {
               this.addSubclasses(subMappings[i], subs);
            }
         }

      }
   }

   private static boolean hasVerticalSubclasses(ClassMapping mapping) {
      ClassMapping[] subs = mapping.getJoinablePCSubclassMappings();

      for(int i = 0; i < subs.length; ++i) {
         if (subs[i].getStrategy() instanceof VerticalClassStrategy) {
            return true;
         }
      }

      return false;
   }

   private int calculateEagerMode(QueryExpressions exps, long start, long end) {
      if (exps.projections.length <= 0 && start < end) {
         return end - start != 1L && !this.ctx.isUnique() ? 2 : 1;
      } else {
         return 0;
      }
   }

   protected Number executeDelete(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] metas, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] exps, Object[] params) {
      return this.executeBulkOperation(metas, subclasses, facts, exps, params, (Map)null);
   }

   protected Number executeUpdate(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] metas, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] exps, Object[] params) {
      return this.executeBulkOperation(metas, subclasses, facts, exps, params, exps[0].updates);
   }

   private Number executeBulkOperation(ClassMetaData[] metas, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] exps, Object[] params, Map updates) {
      ClassMapping[] mappings = (ClassMapping[])((ClassMapping[])metas);
      boolean isUpdate = updates != null && updates.size() > 0;

      for(int i = 0; i < mappings.length; ++i) {
         if (!this.isSingleTableMapping(mappings[i], subclasses) && !isUpdate) {
            return null;
         }

         if (!isUpdate) {
            LifecycleEventManager mgr = this.ctx.getStoreContext().getBroker().getLifecycleEventManager();
            if (mgr.hasDeleteListeners((Object)null, mappings[i])) {
               return null;
            }
         }
      }

      JDBCFetchConfiguration fetch = (JDBCFetchConfiguration)this.ctx.getFetchConfiguration();
      ExpContext ctx = new ExpContext(this._store, params, fetch);
      DBDictionary dict = this._store.getDBDictionary();
      QueryExpressionsState[] state = new QueryExpressionsState[exps.length];

      for(int i = 0; i < state.length; ++i) {
         state[i] = new QueryExpressionsState();
      }

      SQLBuffer[] sql = new SQLBuffer[mappings.length];

      for(int i = 0; i < mappings.length; ++i) {
         JDBCExpressionFactory jdbcFactory = (JDBCExpressionFactory)facts[i];
         Select sel = jdbcFactory.getSelectConstructor().evaluate(ctx, (Select)null, (String)null, exps[i], state[i]);
         jdbcFactory.getSelectConstructor().select(sel, ctx, mappings[i], subclasses, exps[i], state[i], 0);
         if (!isUpdate) {
            sql[i] = dict.toDelete(mappings[i], sel, params);
         } else {
            sql[i] = dict.toUpdate(mappings[i], sel, this._store, params, updates);
         }

         if (sql[i] == null) {
            return null;
         }
      }

      this._store.getContext().beginStore();
      Connection conn = this._store.getConnection();
      long count = 0L;

      try {
         for(int i = 0; i < sql.length; ++i) {
            PreparedStatement stmnt = null;

            try {
               stmnt = this.prepareStatement(conn, sql[i]);
               count += (long)this.executeUpdate(conn, stmnt, sql[i], isUpdate);
            } catch (SQLException var41) {
               throw SQLExceptions.getStore((SQLException)var41, (Object)sql[i].getSQL(), this._store.getDBDictionary());
            } finally {
               if (stmnt != null) {
                  try {
                     stmnt.close();
                  } catch (SQLException var40) {
                  }
               }

            }
         }
      } finally {
         try {
            conn.close();
         } catch (SQLException var39) {
         }

      }

      return Numbers.valueOf(count);
   }

   private boolean isSingleTableMapping(ClassMapping mapping, boolean subclasses) {
      ClassMapping root;
      for(root = mapping; root.getJoinablePCSuperclassMapping() != null; root = root.getJoinablePCSuperclassMapping()) {
      }

      if (hasVerticalSubclasses(root)) {
         return false;
      } else {
         Table table = this.getTable((FieldMapping[])mapping.getFieldMappings(), (Table)null);
         if (table == INVALID) {
            return false;
         } else {
            if (subclasses) {
               ClassMapping[] subs = mapping.getJoinablePCSubclassMappings();

               for(int i = 0; subs != null && i < subs.length; ++i) {
                  table = this.getTable(subs[i].getDefinedFieldMappings(), table);
                  if (table == INVALID) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   private Table getTable(FieldMapping[] fields, Table table) {
      for(int i = 0; i < fields.length; ++i) {
         table = this.getTable(fields[i], table);
         if (table == INVALID) {
            break;
         }
      }

      return table;
   }

   private Table getTable(FieldMapping fm, Table table) {
      if (fm.getCascadeDelete() != 0 && !fm.isEmbeddedPC()) {
         return INVALID;
      } else {
         Column[] columns = fm.getColumns();

         for(int i = 0; columns != null && i < columns.length; ++i) {
            if (table == null) {
               table = columns[i].getTable();
            } else if (table != columns[i].getTable()) {
               return INVALID;
            }
         }

         return table;
      }
   }

   protected Number executeUpdate(ClassMetaData base, ClassMetaData[] metas, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params) {
      return null;
   }

   protected String[] getDataStoreActions(ClassMetaData base, ClassMetaData[] metas, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] exps, Object[] params, StoreQuery.Range range) {
      ClassMapping[] mappings = (ClassMapping[])((ClassMapping[])metas);
      JDBCFetchConfiguration fetch = (JDBCFetchConfiguration)this.ctx.getFetchConfiguration();
      if (exps[0].fetchPaths != null) {
         fetch.addFields(Arrays.asList(exps[0].fetchPaths));
         fetch.addJoins(Arrays.asList(exps[0].fetchPaths));
      }

      if (exps[0].fetchInnerPaths != null) {
         fetch.addFetchInnerJoins(Arrays.asList(exps[0].fetchInnerPaths));
      }

      int eager = this.calculateEagerMode(exps[0], range.start, range.end);
      eager = Math.min(eager, 1);
      int subclassMode = fetch.getSubclassFetchMode((ClassMapping)base);
      DBDictionary dict = this._store.getDBDictionary();
      long start = mappings.length == 1 && dict.supportsSelectStartIndex ? range.start : 0L;
      long end = dict.supportsSelectEndIndex ? range.end : Long.MAX_VALUE;
      QueryExpressionsState[] states = new QueryExpressionsState[exps.length];

      for(int i = 0; i < states.length; ++i) {
         states[i] = new QueryExpressionsState();
      }

      ExpContext ctx = new ExpContext(this._store, params, fetch);
      List sels = new ArrayList(mappings.length);
      List selMappings = new ArrayList(mappings.length);
      BitSet subclassBits = new BitSet();
      BitSet nextBits = new BitSet();
      boolean unionable = this.createWhereSelects((List)sels, mappings, selMappings, subclasses, subclassBits, nextBits, facts, exps, states, ctx, subclassMode) && subclassMode == 1;
      if (((List)sels).size() > 1) {
         start = 0L;
      }

      int i;
      if (unionable) {
         Union union = this._store.getSQLFactory().newUnion((Select[])((Select[])((List)sels).toArray(new Select[((List)sels).size()])));
         this.populateUnion(union, mappings, subclasses, facts, exps, states, ctx, false, eager, start, end);
         if (union.isUnion()) {
            return new String[]{union.toSelect(false, fetch).getSQL(true)};
         }

         sels = Arrays.asList(union.getSelects());
      } else {
         i = 0;

         for(int idx = 0; i < ((List)sels).size(); ++i) {
            Select sel = (Select)((List)sels).get(i);
            this.populateSelect(sel, (ClassMapping)selMappings.get(i), subclassBits.get(i), (JDBCExpressionFactory)facts[idx], exps[idx], states[idx], ctx, false, eager, start, end);
            if (nextBits.get(i)) {
               ++idx;
            }
         }
      }

      String[] sql = new String[((List)sels).size()];

      for(i = 0; i < ((List)sels).size(); ++i) {
         sql[i] = ((Select)((List)sels).get(i)).toSelect(false, fetch).getSQL(true);
      }

      return sql;
   }

   protected int executeUpdate(Connection conn, PreparedStatement stmnt, SQLBuffer sqlBuf, boolean isUpdate) throws SQLException {
      return stmnt.executeUpdate();
   }

   protected PreparedStatement prepareStatement(Connection conn, SQLBuffer sql) throws SQLException {
      return sql.prepareStatement(conn);
   }

   static {
      _listeners.put("stringContains", new JDBCStringContains());
      _listeners.put("wildcardMatch", new JDBCWildcardMatch());
      _listeners.put("sqlExp", new SQLExpression());
      _listeners.put("sqlVal", new SQLValue());
      _listeners.put("getColumn", new GetColumn());
      _listeners.put(SQLEmbed.TAG, new SQLEmbed());
   }
}
