package org.apache.openjpa.jdbc.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class LogicalUnion implements Union {
   private static final Localizer _loc = Localizer.forPackage(LogicalUnion.class);
   protected final UnionSelect[] sels;
   protected final DBDictionary dict;
   protected final ClassMapping[] mappings;
   protected final BitSet desc;
   private boolean _distinct;

   public LogicalUnion(JDBCConfiguration conf, int sels) {
      this(conf, sels, (Select[])null);
   }

   public LogicalUnion(JDBCConfiguration conf, Select[] seeds) {
      this(conf, seeds.length, seeds);
   }

   protected LogicalUnion(JDBCConfiguration conf, int sels, Select[] seeds) {
      this.desc = new BitSet();
      this._distinct = true;
      if (sels == 0) {
         throw new InternalException("sels == 0");
      } else {
         this.dict = conf.getDBDictionaryInstance();
         this.mappings = new ClassMapping[sels];
         this.sels = new UnionSelect[sels];

         for(int i = 0; i < sels; ++i) {
            SelectImpl seed = seeds == null ? (SelectImpl)conf.getSQLFactoryInstance().newSelect() : (SelectImpl)seeds[i];
            this.sels[i] = this.newUnionSelect(seed, i);
         }

      }
   }

   protected UnionSelect newUnionSelect(SelectImpl seed, int pos) {
      return new UnionSelect(seed, pos);
   }

   public Select[] getSelects() {
      return this.sels;
   }

   public boolean isUnion() {
      return false;
   }

   public void abortUnion() {
   }

   public String getOrdering() {
      return null;
   }

   public JDBCConfiguration getConfiguration() {
      return this.sels[0].getConfiguration();
   }

   public DBDictionary getDBDictionary() {
      return this.dict;
   }

   public SQLBuffer toSelect(boolean forUpdate, JDBCFetchConfiguration fetch) {
      return this.dict.toSelect(this.sels[0], forUpdate, fetch);
   }

   public SQLBuffer toSelectCount() {
      return this.dict.toSelectCount(this.sels[0]);
   }

   public boolean getAutoDistinct() {
      return this.sels[0].getAutoDistinct();
   }

   public void setAutoDistinct(boolean distinct) {
      for(int i = 0; i < this.sels.length; ++i) {
         this.sels[i].setAutoDistinct(distinct);
      }

   }

   public boolean isDistinct() {
      return this._distinct;
   }

   public void setDistinct(boolean distinct) {
      this._distinct = distinct;
   }

   public boolean isLRS() {
      return this.sels[0].isLRS();
   }

   public void setLRS(boolean lrs) {
      for(int i = 0; i < this.sels.length; ++i) {
         this.sels[i].setLRS(lrs);
      }

   }

   public int getExpectedResultCount() {
      return this.sels[0].getExpectedResultCount();
   }

   public void setExpectedResultCount(int expectedResultCount, boolean force) {
      for(int i = 0; i < this.sels.length; ++i) {
         this.sels[i].setExpectedResultCount(expectedResultCount, force);
      }

   }

   public int getJoinSyntax() {
      return this.sels[0].getJoinSyntax();
   }

   public void setJoinSyntax(int syntax) {
      for(int i = 0; i < this.sels.length; ++i) {
         this.sels[i].setJoinSyntax(syntax);
      }

   }

   public boolean supportsRandomAccess(boolean forUpdate) {
      return this.sels.length == 1 ? this.sels[0].supportsRandomAccess(forUpdate) : false;
   }

   public boolean supportsLocking() {
      if (this.sels.length == 1) {
         return this.sels[0].supportsLocking();
      } else {
         for(int i = 0; i < this.sels.length; ++i) {
            if (!this.sels[i].supportsLocking()) {
               return false;
            }
         }

         return true;
      }
   }

   public int getCount(JDBCStore store) throws SQLException {
      int count = 0;

      for(int i = 0; i < this.sels.length; ++i) {
         count += this.sels[i].getCount(store);
      }

      return count;
   }

   public Result execute(JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      if (fetch == null) {
         fetch = store.getFetchConfiguration();
      }

      return this.execute(store, fetch, fetch.getReadLockLevel());
   }

   public Result execute(JDBCStore store, JDBCFetchConfiguration fetch, int lockLevel) throws SQLException {
      if (fetch == null) {
         fetch = store.getFetchConfiguration();
      }

      if (this.sels.length == 1) {
         Result res = this.sels[0].execute(store, fetch, lockLevel);
         ((AbstractResult)res).setBaseMapping(this.mappings[0]);
         return res;
      } else {
         if (this.getExpectedResultCount() == 1) {
            for(int i = 0; i < this.sels.length; ++i) {
               AbstractResult res = (AbstractResult)this.sels[i].execute(store, fetch, lockLevel);
               res.setBaseMapping(this.mappings[i]);
               res.setIndexOf(i);
               if (i == this.sels.length - 1) {
                  return res;
               }

               try {
                  if (res.next()) {
                     res.pushBack();
                     return res;
                  }

                  res.close();
               } catch (SQLException var9) {
                  res.close();
                  throw var9;
               }
            }
         }

         AbstractResult[] res = new AbstractResult[this.sels.length];
         List[] orderIdxs = null;

         int i;
         try {
            for(i = 0; i < res.length; ++i) {
               res[i] = (AbstractResult)this.sels[i].execute(store, fetch, lockLevel);
               res[i].setBaseMapping(this.mappings[i]);
               res[i].setIndexOf(i);
               List l = this.sels[i].getSelectedOrderIndexes();
               if (l != null) {
                  if (orderIdxs == null) {
                     orderIdxs = new List[this.sels.length];
                  }

                  orderIdxs[i] = l;
               }
            }
         } catch (SQLException var8) {
            for(i = 0; res[i] != null; ++i) {
               res[i].close();
            }

            throw var8;
         }

         ResultComparator comp = null;
         if (orderIdxs != null) {
            comp = new ResultComparator(orderIdxs, this.desc, this.dict);
         }

         return new MergedResult(res, comp);
      }
   }

   public void select(Union.Selector selector) {
      for(int i = 0; i < this.sels.length; ++i) {
         selector.select(this.sels[i], i);
      }

   }

   public String toString() {
      return this.toSelect(false, (JDBCFetchConfiguration)null).getSQL();
   }

   private static class ResultComparator implements MergedResult.ResultComparator {
      private final List[] _orders;
      private final BitSet _desc;
      private final DBDictionary _dict;

      public ResultComparator(List[] orders, BitSet desc, DBDictionary dict) {
         this._orders = orders;
         this._desc = desc;
         this._dict = dict;
      }

      public Object getOrderingValue(Result res, int idx) {
         ResultSet rs = ((ResultSetResult)res).getResultSet();
         if (this._orders[idx].size() == 1) {
            return this.getOrderingValue(rs, this._orders[idx].get(0));
         } else {
            Object[] vals = new Object[this._orders[idx].size()];

            for(int i = 0; i < vals.length; ++i) {
               vals[i] = this.getOrderingValue(rs, this._orders[idx].get(i));
            }

            return vals;
         }
      }

      private Object getOrderingValue(ResultSet rs, Object i) {
         try {
            return this._dict.getObject(rs, (Integer)i + 1, (Map)null);
         } catch (SQLException var4) {
            throw SQLExceptions.getStore(var4, this._dict);
         }
      }

      public int compare(Object o1, Object o2) {
         if (o1 == o2) {
            return 0;
         } else if (o1 == null) {
            return this._desc.get(0) ? -1 : 1;
         } else if (o2 == null) {
            return this._desc.get(0) ? 1 : -1;
         } else {
            int cmp;
            if (!(o1 instanceof Object[])) {
               if (!(o2 instanceof Object[])) {
                  cmp = ((Comparable)o1).compareTo(o2);
                  return this._desc.get(0) ? -cmp : cmp;
               } else {
                  cmp = ((Comparable)o1).compareTo(((Object[])((Object[])o2))[0]);
                  if (cmp != 0) {
                     return this._desc.get(0) ? -cmp : cmp;
                  } else {
                     return -1;
                  }
               }
            } else if (!(o2 instanceof Object[])) {
               cmp = ((Comparable)((Object[])((Object[])o1))[0]).compareTo(o2);
               if (cmp != 0) {
                  return this._desc.get(0) ? -cmp : cmp;
               } else {
                  return 1;
               }
            } else {
               Object[] a1 = (Object[])((Object[])o1);
               Object[] a2 = (Object[])((Object[])o2);

               for(int i = 0; i < a1.length; ++i) {
                  cmp = ((Comparable)a1[i]).compareTo(a2[i]);
                  if (cmp != 0) {
                     return this._desc.get(i) ? -cmp : cmp;
                  }
               }

               return a1.length - a2.length;
            }
         }
      }
   }

   protected class UnionSelect implements Select {
      protected final SelectImpl sel;
      protected final int pos;
      protected int orders = 0;
      protected List orderIdxs = null;

      public UnionSelect(SelectImpl sel, int pos) {
         this.sel = sel;
         this.pos = pos;
         sel.setRecordOrderedIndexes(true);
      }

      public SelectImpl getDelegate() {
         return this.sel;
      }

      public List getSelectedOrderIndexes() {
         if (this.orderIdxs == null) {
            this.orderIdxs = this.sel.getOrderedIndexes();
         }

         return this.orderIdxs;
      }

      public JDBCConfiguration getConfiguration() {
         return this.sel.getConfiguration();
      }

      public int indexOf() {
         return this.pos;
      }

      public SQLBuffer toSelect(boolean forUpdate, JDBCFetchConfiguration fetch) {
         return this.sel.toSelect(forUpdate, fetch);
      }

      public SQLBuffer toSelectCount() {
         return this.sel.toSelectCount();
      }

      public boolean getAutoDistinct() {
         return this.sel.getAutoDistinct();
      }

      public void setAutoDistinct(boolean distinct) {
         this.sel.setAutoDistinct(distinct);
      }

      public boolean isDistinct() {
         return this.sel.isDistinct();
      }

      public void setDistinct(boolean distinct) {
         this.sel.setDistinct(distinct);
      }

      public boolean isLRS() {
         return this.sel.isLRS();
      }

      public void setLRS(boolean lrs) {
         this.sel.setLRS(lrs);
      }

      public int getJoinSyntax() {
         return this.sel.getJoinSyntax();
      }

      public void setJoinSyntax(int joinSyntax) {
         this.sel.setJoinSyntax(joinSyntax);
      }

      public boolean supportsRandomAccess(boolean forUpdate) {
         return this.sel.supportsRandomAccess(forUpdate);
      }

      public boolean supportsLocking() {
         return this.sel.supportsLocking();
      }

      public int getCount(JDBCStore store) throws SQLException {
         return this.sel.getCount(store);
      }

      public Result execute(JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
         return this.sel.execute(store, fetch);
      }

      public Result execute(JDBCStore store, JDBCFetchConfiguration fetch, int lockLevel) throws SQLException {
         return this.sel.execute(store, fetch, lockLevel);
      }

      public List getSubselects() {
         return Collections.EMPTY_LIST;
      }

      public Select getParent() {
         return null;
      }

      public String getSubselectPath() {
         return null;
      }

      public void setParent(Select parent, String path) {
         throw new UnsupportedException(LogicalUnion._loc.get("union-element"));
      }

      public Select getFromSelect() {
         return null;
      }

      public void setFromSelect(Select sel) {
         throw new UnsupportedException(LogicalUnion._loc.get("union-element"));
      }

      public boolean hasEagerJoin(boolean toMany) {
         return this.sel.hasEagerJoin(toMany);
      }

      public boolean hasJoin(boolean toMany) {
         return this.sel.hasJoin(toMany);
      }

      public boolean isSelected(Table table) {
         return this.sel.isSelected(table);
      }

      public Collection getTableAliases() {
         return this.sel.getTableAliases();
      }

      public List getSelects() {
         return this.sel.getSelects();
      }

      public List getSelectAliases() {
         return this.sel.getSelectAliases();
      }

      public List getIdentifierAliases() {
         return this.sel.getIdentifierAliases();
      }

      public SQLBuffer getOrdering() {
         return this.sel.getOrdering();
      }

      public SQLBuffer getGrouping() {
         return this.sel.getGrouping();
      }

      public SQLBuffer getWhere() {
         return this.sel.getWhere();
      }

      public SQLBuffer getHaving() {
         return this.sel.getHaving();
      }

      public void addJoinClassConditions() {
         this.sel.addJoinClassConditions();
      }

      public Joins getJoins() {
         return this.sel.getJoins();
      }

      public Iterator getJoinIterator() {
         return this.sel.getJoinIterator();
      }

      public long getStartIndex() {
         return this.sel.getStartIndex();
      }

      public long getEndIndex() {
         return this.sel.getEndIndex();
      }

      public void setRange(long start, long end) {
         this.sel.setRange(start, end);
      }

      public String getColumnAlias(Column col) {
         return this.sel.getColumnAlias(col);
      }

      public String getColumnAlias(Column col, Joins joins) {
         return this.sel.getColumnAlias(col, joins);
      }

      public String getColumnAlias(String col, Table table) {
         return this.sel.getColumnAlias(col, table);
      }

      public String getColumnAlias(String col, Table table, Joins joins) {
         return this.sel.getColumnAlias(col, table, joins);
      }

      public boolean isAggregate() {
         return this.sel.isAggregate();
      }

      public void setAggregate(boolean agg) {
         this.sel.setAggregate(agg);
      }

      public boolean isLob() {
         return this.sel.isLob();
      }

      public void setLob(boolean lob) {
         this.sel.setLob(lob);
      }

      public void selectPlaceholder(String sql) {
         this.sel.selectPlaceholder(sql);
      }

      public void clearSelects() {
         this.sel.clearSelects();
      }

      public boolean select(SQLBuffer sql, Object id) {
         return this.sel.select(sql, id);
      }

      public boolean select(SQLBuffer sql, Object id, Joins joins) {
         return this.sel.select(sql, id, joins);
      }

      public boolean select(String sql, Object id) {
         return this.sel.select(sql, id);
      }

      public boolean select(String sql, Object id, Joins joins) {
         return this.sel.select(sql, id, joins);
      }

      public boolean select(Column col) {
         return this.sel.select(col);
      }

      public boolean select(Column col, Joins joins) {
         return this.sel.select(col, joins);
      }

      public int select(Column[] cols) {
         return this.sel.select(cols);
      }

      public int select(Column[] cols, Joins joins) {
         return this.sel.select(cols, joins);
      }

      public void select(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager) {
         this.select(mapping, subclasses, store, fetch, eager, (Joins)null, false);
      }

      public void select(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager, Joins joins) {
         this.select(mapping, subclasses, store, fetch, eager, joins, false);
      }

      private void select(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager, Joins joins, boolean identifier) {
         if (LogicalUnion.this.mappings[this.pos] == null) {
            LogicalUnion.this.mappings[this.pos] = mapping;
         }

         this.sel.select(this, mapping, subclasses, store, fetch, eager, joins, identifier);
      }

      public boolean selectIdentifier(Column col) {
         return this.sel.selectIdentifier(col);
      }

      public boolean selectIdentifier(Column col, Joins joins) {
         return this.sel.selectIdentifier(col, joins);
      }

      public int selectIdentifier(Column[] cols) {
         return this.sel.selectIdentifier(cols);
      }

      public int selectIdentifier(Column[] cols, Joins joins) {
         return this.sel.selectIdentifier(cols, joins);
      }

      public void selectIdentifier(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager) {
         this.select(mapping, subclasses, store, fetch, eager, (Joins)null, true);
      }

      public void selectIdentifier(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, int eager, Joins joins) {
         this.select(mapping, subclasses, store, fetch, eager, joins, true);
      }

      public int selectPrimaryKey(ClassMapping mapping) {
         return this.sel.selectPrimaryKey(mapping);
      }

      public int selectPrimaryKey(ClassMapping mapping, Joins joins) {
         return this.sel.selectPrimaryKey(mapping, joins);
      }

      public int orderByPrimaryKey(ClassMapping mapping, boolean asc, boolean select) {
         return this.orderByPrimaryKey(mapping, asc, (Joins)null, select);
      }

      public int orderByPrimaryKey(ClassMapping mapping, boolean asc, Joins joins, boolean select) {
         ClassMapping pks;
         for(pks = mapping; !pks.isPrimaryKeyObjectId(true); pks = pks.getJoinablePCSuperclassMapping()) {
         }

         Column[] cols = pks.getPrimaryKeyColumns();
         this.recordOrderColumns(cols, asc);
         return this.sel.orderByPrimaryKey(mapping, asc, joins, select, LogicalUnion.this.isUnion());
      }

      protected void recordOrder(Object ord, boolean asc) {
         if (ord != null) {
            this.orderIdxs = null;
            int idx = this.orders++;
            if (LogicalUnion.this.desc.get(idx) && asc) {
               throw new UserException(LogicalUnion._loc.get("incompat-ordering"));
            } else {
               if (!asc) {
                  LogicalUnion.this.desc.set(idx);
               }

            }
         }
      }

      protected void recordOrderColumns(Column[] cols, boolean asc) {
         for(int i = 0; i < cols.length; ++i) {
            this.recordOrder(cols[i], asc);
         }

      }

      public boolean orderBy(Column col, boolean asc, boolean select) {
         return this.orderBy((Column)col, asc, (Joins)null, select);
      }

      public boolean orderBy(Column col, boolean asc, Joins joins, boolean select) {
         this.recordOrder(col, asc);
         return this.sel.orderBy(col, asc, joins, select, LogicalUnion.this.isUnion());
      }

      public int orderBy(Column[] cols, boolean asc, boolean select) {
         return this.orderBy((Column[])cols, asc, (Joins)null, select);
      }

      public int orderBy(Column[] cols, boolean asc, Joins joins, boolean select) {
         this.recordOrderColumns(cols, asc);
         return this.sel.orderBy(cols, asc, joins, select, LogicalUnion.this.isUnion());
      }

      public boolean orderBy(SQLBuffer sql, boolean asc, boolean select) {
         return this.orderBy((SQLBuffer)sql, asc, (Joins)null, select);
      }

      public boolean orderBy(SQLBuffer sql, boolean asc, Joins joins, boolean select) {
         this.recordOrder(sql.getSQL(false), asc);
         return this.sel.orderBy(sql, asc, joins, select, LogicalUnion.this.isUnion());
      }

      public boolean orderBy(String sql, boolean asc, boolean select) {
         return this.orderBy((String)sql, asc, (Joins)null, select);
      }

      public boolean orderBy(String sql, boolean asc, Joins joins, boolean select) {
         this.recordOrder(sql, asc);
         return this.sel.orderBy(sql, asc, joins, select, LogicalUnion.this.isUnion());
      }

      public void clearOrdering() {
         this.sel.clearOrdering();
      }

      public void wherePrimaryKey(Object oid, ClassMapping mapping, JDBCStore store) {
         this.sel.wherePrimaryKey(oid, mapping, store);
      }

      public void whereForeignKey(ForeignKey fk, Object oid, ClassMapping mapping, JDBCStore store) {
         this.sel.whereForeignKey(fk, oid, mapping, store);
      }

      public void where(Joins joins) {
         this.sel.where(joins);
      }

      public void where(SQLBuffer sql) {
         this.sel.where(sql);
      }

      public void where(SQLBuffer sql, Joins joins) {
         this.sel.where(sql, joins);
      }

      public void where(String sql) {
         this.sel.where(sql);
      }

      public void where(String sql, Joins joins) {
         this.sel.where(sql, joins);
      }

      public void having(SQLBuffer sql) {
         this.sel.having(sql);
      }

      public void having(SQLBuffer sql, Joins joins) {
         this.sel.having(sql, joins);
      }

      public void having(String sql) {
         this.sel.having(sql);
      }

      public void having(String sql, Joins joins) {
         this.sel.having(sql, joins);
      }

      public void groupBy(SQLBuffer sql) {
         this.sel.groupBy(sql);
      }

      public void groupBy(SQLBuffer sql, Joins joins) {
         this.sel.groupBy(sql, joins);
      }

      public void groupBy(String sql) {
         this.sel.groupBy(sql);
      }

      public void groupBy(String sql, Joins joins) {
         this.sel.groupBy(sql, joins);
      }

      public void groupBy(Column col) {
         this.sel.groupBy(col);
      }

      public void groupBy(Column col, Joins joins) {
         this.sel.groupBy(col, joins);
      }

      public void groupBy(Column[] cols) {
         this.sel.groupBy(cols);
      }

      public void groupBy(Column[] cols, Joins joins) {
         this.sel.groupBy(cols, joins);
      }

      public void groupBy(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch) {
         this.sel.groupBy(mapping, subclasses, store, fetch);
      }

      public void groupBy(ClassMapping mapping, int subclasses, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
         this.sel.groupBy(mapping, subclasses, store, fetch, joins);
      }

      public SelectExecutor whereClone(int sels) {
         return this.sel.whereClone(sels);
      }

      public SelectExecutor fullClone(int sels) {
         return this.sel.fullClone(sels);
      }

      public SelectExecutor eagerClone(FieldMapping key, int eagerType, boolean toMany, int sels) {
         SelectExecutor ex = this.sel.eagerClone(key, eagerType, toMany, sels);
         return (SelectExecutor)(ex == this.sel ? this : ex);
      }

      public SelectExecutor getEager(FieldMapping key) {
         SelectExecutor ex = this.sel.getEager(key);
         return (SelectExecutor)(ex == this.sel ? this : ex);
      }

      public Joins newJoins() {
         return this.sel.newJoins();
      }

      public Joins newOuterJoins() {
         return this.sel.newOuterJoins();
      }

      public void append(SQLBuffer buf, Joins joins) {
         this.sel.append(buf, joins);
      }

      public Joins and(Joins joins1, Joins joins2) {
         return this.sel.and(joins1, joins2);
      }

      public Joins or(Joins joins1, Joins joins2) {
         return this.sel.or(joins1, joins2);
      }

      public Joins outer(Joins joins) {
         return this.sel.outer(joins);
      }

      public String toString() {
         return this.sel.toString();
      }

      public int getExpectedResultCount() {
         return this.sel.getExpectedResultCount();
      }

      public void setExpectedResultCount(int expectedResultCount, boolean force) {
         this.sel.setExpectedResultCount(expectedResultCount, force);
      }
   }

   public interface Selector {
      void select(Select var1, int var2);
   }
}
