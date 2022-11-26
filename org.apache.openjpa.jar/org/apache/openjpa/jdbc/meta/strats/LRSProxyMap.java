package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.AbstractLRSProxyMap;
import org.apache.openjpa.util.InvalidStateException;

class LRSProxyMap extends AbstractLRSProxyMap {
   private static final Localizer _loc = Localizer.forPackage(LRSProxyMap.class);
   private final LRSMapFieldStrategy _strat;

   public LRSProxyMap(LRSMapFieldStrategy strat) {
      super(strat.getFieldMapping().getKey().getDeclaredType(), strat.getFieldMapping().getElement().getDeclaredType());
      this._strat = strat;
   }

   protected synchronized int count() {
      boolean derivedVal = this._strat.getFieldMapping().getElement().getValueMappedBy() != null;
      final ClassMapping[] clss = derivedVal ? this._strat.getIndependentKeyMappings(false) : this._strat.getIndependentValueMappings(false);
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      Union union = store.getSQLFactory().newUnion(Math.max(1, clss.length));
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            ClassMapping cls = clss.length == 0 ? null : clss[idx];
            sel.whereForeignKey(LRSProxyMap.this._strat.getJoinForeignKey(cls), sm.getObjectId(), LRSProxyMap.this._strat.getFieldMapping().getDefiningMapping(), store);
         }
      });

      try {
         return union.getCount(store);
      } catch (SQLException var7) {
         throw SQLExceptions.getStore(var7, store.getDBDictionary());
      }
   }

   protected boolean hasKey(Object key) {
      return this.has(key, true);
   }

   protected boolean hasValue(Object value) {
      return this.has(value, false);
   }

   private boolean has(final Object obj, final boolean key) {
      final boolean derivedKey = key && this._strat.getFieldMapping().getKey().getValueMappedBy() != null;
      final boolean derivedVal = !key && this._strat.getFieldMapping().getElement().getValueMappedBy() != null;
      final ClassMapping[] clss = (!key || derivedKey) && !derivedVal ? this._strat.getIndependentValueMappings(derivedKey) : this._strat.getIndependentKeyMappings(derivedVal);
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      Union union = store.getSQLFactory().newUnion(Math.max(1, clss.length));
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            ClassMapping cls = clss.length == 0 ? null : clss[idx];
            sel.whereForeignKey(LRSProxyMap.this._strat.getJoinForeignKey(cls), sm.getObjectId(), LRSProxyMap.this._strat.getFieldMapping().getDefiningMapping(), store);
            Joins joins = null;
            Column[] cols;
            Object val;
            if (key) {
               if (derivedKey) {
                  joins = LRSProxyMap.this._strat.joinValueRelation(sel.newJoins(), cls);
               }

               val = LRSProxyMap.this._strat.toKeyDataStoreValue(obj, store);
               cols = LRSProxyMap.this._strat.getKeyColumns(cls);
            } else {
               if (derivedVal) {
                  joins = LRSProxyMap.this._strat.joinKeyRelation(sel.newJoins(), cls);
               }

               val = LRSProxyMap.this._strat.toDataStoreValue(obj, store);
               cols = LRSProxyMap.this._strat.getValueColumns(cls);
            }

            Object[] vals = cols.length == 1 ? null : (Object[])((Object[])val);
            SQLBuffer sql = new SQLBuffer(store.getDBDictionary());

            for(int i = 0; i < cols.length; ++i) {
               if (i > 0) {
                  sql.append(" AND ");
               }

               sql.append(sel.getColumnAlias(cols[i], joins));
               if (vals == null) {
                  sql.append(val == null ? " IS " : " = ").appendValue(val, cols[i]);
               } else {
                  sql.append(vals[i] == null ? " IS " : " = ").appendValue(vals[i], cols[i]);
               }
            }

            sel.where(sql, joins);
         }
      });

      try {
         return union.getCount(store) > 0;
      } catch (SQLException var10) {
         throw SQLExceptions.getStore(var10, store.getDBDictionary());
      }
   }

   protected Collection keys(final Object obj) {
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      if (this._strat.getFieldMapping().getKey().getValueMappedBy() != null) {
         Object key = this._strat.deriveKey(store, obj);
         return (Collection)(this.hasKey(key) ? Collections.singleton(key) : Collections.EMPTY_LIST);
      } else {
         final ClassMapping[] clss = this._strat.getIndependentKeyMappings(true);
         final JDBCFetchConfiguration fetch = store.getFetchConfiguration();
         final Joins[] resJoins = new Joins[Math.max(1, clss.length)];
         Union union = store.getSQLFactory().newUnion(Math.max(1, clss.length));
         if (fetch.getSubclassFetchMode(this._strat.getFieldMapping().getKeyMapping().getTypeMapping()) != 1) {
            union.abortUnion();
         }

         union.select(new Union.Selector() {
            public void select(Select sel, int idx) {
               ClassMapping cls = clss.length == 0 ? null : clss[idx];
               sel.whereForeignKey(LRSProxyMap.this._strat.getJoinForeignKey(cls), sm.getObjectId(), LRSProxyMap.this._strat.getFieldMapping().getDefiningMapping(), store);
               if (LRSProxyMap.this._strat.getFieldMapping().getElement().getValueMappedBy() != null) {
                  resJoins[idx] = LRSProxyMap.this._strat.joinKeyRelation(sel.newJoins(), cls);
               }

               Object val = LRSProxyMap.this._strat.toDataStoreValue(obj, store);
               Column[] cols = LRSProxyMap.this._strat.getValueColumns(cls);
               Object[] vals = cols.length == 1 ? null : (Object[])((Object[])val);
               SQLBuffer sql = new SQLBuffer(store.getDBDictionary());

               for(int i = 0; i < cols.length; ++i) {
                  if (i > 0) {
                     sql.append(" AND ");
                  }

                  sql.append(sel.getColumnAlias(cols[i]));
                  if (vals == null) {
                     sql.append(val == null ? " IS " : " = ").appendValue(val, cols[i]);
                  } else {
                     sql.append(vals[i] == null ? " IS " : " = ").appendValue(vals[i], cols[i]);
                  }
               }

               sel.where(sql);
               if (resJoins[idx] == null) {
                  resJoins[idx] = LRSProxyMap.this._strat.joinKeyRelation(sel.newJoins(), cls);
               }

               LRSProxyMap.this._strat.selectKey(sel, cls, sm, store, fetch, resJoins[idx]);
            }
         });
         Result res = null;
         Collection keys = new ArrayList(3);

         try {
            res = union.execute(store, fetch);

            while(res.next()) {
               keys.add(this._strat.loadKey(sm, store, fetch, res, resJoins[res.indexOf()]));
            }

            ArrayList var10 = keys;
            return var10;
         } catch (SQLException var14) {
            throw SQLExceptions.getStore(var14, store.getDBDictionary());
         } finally {
            if (res != null) {
               res.close();
            }

         }
      }
   }

   protected Object value(final Object obj) {
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      if (this._strat.getFieldMapping().getElement().getValueMappedBy() != null) {
         Object val = this._strat.deriveValue(store, obj);
         return this.hasValue(val) ? val : null;
      } else {
         final JDBCFetchConfiguration fetch = store.getFetchConfiguration();
         final ClassMapping[] clss = this._strat.getIndependentValueMappings(true);
         final Joins[] resJoins = new Joins[Math.max(1, clss.length)];
         Union union = store.getSQLFactory().newUnion(Math.max(1, clss.length));
         union.setExpectedResultCount(1, false);
         if (fetch.getSubclassFetchMode(this._strat.getFieldMapping().getElementMapping().getTypeMapping()) != 1) {
            union.abortUnion();
         }

         union.select(new Union.Selector() {
            public void select(Select sel, int idx) {
               ClassMapping cls = clss.length == 0 ? null : clss[idx];
               sel.whereForeignKey(LRSProxyMap.this._strat.getJoinForeignKey(cls), sm.getObjectId(), LRSProxyMap.this._strat.getFieldMapping().getDefiningMapping(), store);
               if (LRSProxyMap.this._strat.getFieldMapping().getKey().getValueMappedBy() != null) {
                  resJoins[idx] = LRSProxyMap.this._strat.joinValueRelation(sel.newJoins(), cls);
               }

               Object key = LRSProxyMap.this._strat.toKeyDataStoreValue(obj, store);
               Column[] cols = LRSProxyMap.this._strat.getKeyColumns(cls);
               Object[] vals = cols.length == 1 ? null : (Object[])((Object[])key);
               SQLBuffer sql = new SQLBuffer(store.getDBDictionary());

               for(int i = 0; i < cols.length; ++i) {
                  if (i > 0) {
                     sql.append(" AND ");
                  }

                  sql.append(sel.getColumnAlias(cols[i], resJoins[idx]));
                  if (vals == null) {
                     sql.append(key == null ? " IS " : " = ").appendValue(key, cols[i]);
                  } else {
                     sql.append(vals[i] == null ? " IS " : " = ").appendValue(vals[i], cols[i]);
                  }
               }

               sel.where(sql, resJoins[idx]);
               if (resJoins[idx] == null) {
                  resJoins[idx] = LRSProxyMap.this._strat.joinValueRelation(sel.newJoins(), cls);
               }

               LRSProxyMap.this._strat.selectValue(sel, cls, sm, store, fetch, resJoins[idx]);
            }
         });
         Result res = null;

         Object var9;
         try {
            res = union.execute(store, fetch);
            if (res.next()) {
               var9 = this._strat.loadValue(sm, store, fetch, res, resJoins[res.indexOf()]);
               return var9;
            }

            var9 = null;
         } catch (SQLException var13) {
            throw SQLExceptions.getStore(var13, store.getDBDictionary());
         } finally {
            if (res != null) {
               res.close();
            }

         }

         return var9;
      }
   }

   protected Iterator itr() {
      OpenJPAStateManager sm = this.assertOwner();
      JDBCStore store = this.getStore();
      JDBCFetchConfiguration fetch = store.getFetchConfiguration();

      try {
         Joins[] joins = new Joins[2];
         Result[] res = this._strat.getResults(sm, store, fetch, 1, joins, true);
         return new ResultIterator(sm, store, fetch, res, joins);
      } catch (SQLException var6) {
         throw SQLExceptions.getStore(var6, store.getDBDictionary());
      }
   }

   private OpenJPAStateManager assertOwner() {
      OpenJPAStateManager sm = this.getOwner();
      if (sm == null) {
         throw new InvalidStateException(_loc.get("lrs-no-owner", (Object)this._strat.getFieldMapping()));
      } else {
         return sm;
      }
   }

   private JDBCStore getStore() {
      return (JDBCStore)this.getOwner().getContext().getStoreManager().getInnermostDelegate();
   }

   private static class Entry implements Map.Entry {
      public Object key;
      public Object val;

      private Entry() {
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.val;
      }

      public Object setValue(Object val) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      Entry(Object x0) {
         this();
      }
   }

   private class ResultIterator implements Iterator, Closeable {
      private final OpenJPAStateManager _sm;
      private final JDBCStore _store;
      private final JDBCFetchConfiguration _fetch;
      private final Result[] _res;
      private final Joins[] _joins;
      private Boolean _next = null;

      public ResultIterator(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result[] res, Joins[] joins) {
         this._sm = sm;
         this._store = store;
         this._fetch = fetch;
         this._res = res;
         this._joins = joins;
      }

      public boolean hasNext() {
         if (this._next == null) {
            try {
               this._next = this._res[0].next() ? Boolean.TRUE : Boolean.FALSE;
               if (this._next && this._res[1] != this._res[0]) {
                  this._res[1].next();
               }
            } catch (SQLException var2) {
               throw SQLExceptions.getStore(var2, this._store.getDBDictionary());
            }
         }

         return this._next;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this._next = null;
            boolean keyDerived = LRSProxyMap.this._strat.getFieldMapping().getKey().getValueMappedBy() != null;
            boolean valDerived = LRSProxyMap.this._strat.getFieldMapping().getElement().getValueMappedBy() != null;
            Entry entry = new Entry();

            try {
               if (!keyDerived) {
                  entry.key = LRSProxyMap.this._strat.loadKey(this._sm, this._store, this._fetch, this._res[0], this._joins[0]);
               }

               if (!valDerived) {
                  entry.val = LRSProxyMap.this._strat.loadValue(this._sm, this._store, this._fetch, this._res[1], this._joins[1]);
               }

               if (keyDerived) {
                  entry.key = LRSProxyMap.this._strat.deriveKey(this._store, entry.val);
               }

               if (valDerived) {
                  entry.val = LRSProxyMap.this._strat.deriveValue(this._store, entry.key);
               }

               return entry;
            } catch (SQLException var5) {
               throw SQLExceptions.getStore(var5, this._store.getDBDictionary());
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void close() {
         this._next = Boolean.FALSE;
         this._res[0].close();
         if (this._res[1] != this._res[0]) {
            this._res[1].close();
         }

      }
   }
}
