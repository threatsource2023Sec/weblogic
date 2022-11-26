package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
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
import org.apache.openjpa.util.AbstractLRSProxyCollection;
import org.apache.openjpa.util.InvalidStateException;

public class LRSProxyCollection extends AbstractLRSProxyCollection {
   private static final Localizer _loc = Localizer.forPackage(LRSProxyCollection.class);
   private final LRSCollectionFieldStrategy _strat;

   public LRSProxyCollection(LRSCollectionFieldStrategy strat) {
      super(strat.getFieldMapping().getElement().getDeclaredType(), strat.getFieldMapping().getOrderColumn() != null);
      this._strat = strat;
   }

   protected int count() {
      final ClassMapping[] elems = this._strat.getIndependentElementMappings(false);
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      Union union = store.getSQLFactory().newUnion(Math.max(1, elems.length));
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            ClassMapping elem = elems.length == 0 ? null : elems[idx];
            sel.whereForeignKey(LRSProxyCollection.this._strat.getJoinForeignKey(elem), sm.getObjectId(), LRSProxyCollection.this._strat.getFieldMapping().getDefiningMapping(), store);
         }
      });

      try {
         return union.getCount(store);
      } catch (SQLException var6) {
         throw SQLExceptions.getStore(var6, store.getDBDictionary());
      }
   }

   protected boolean has(final Object obj) {
      final ClassMapping[] elems = this._strat.getIndependentElementMappings(false);
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      Union union = store.getSQLFactory().newUnion(Math.max(1, elems.length));
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            ClassMapping elem = elems.length == 0 ? null : elems[idx];
            sel.whereForeignKey(LRSProxyCollection.this._strat.getJoinForeignKey(elem), sm.getObjectId(), LRSProxyCollection.this._strat.getFieldMapping().getDefiningMapping(), store);
            Object val = LRSProxyCollection.this._strat.toDataStoreValue(obj, store);
            Column[] cols = LRSProxyCollection.this._strat.getElementColumns(elem);
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
         }
      });

      try {
         return union.getCount(store) > 0;
      } catch (SQLException var7) {
         throw SQLExceptions.getStore(var7, store.getDBDictionary());
      }
   }

   protected Iterator itr() {
      final ClassMapping[] elems = this._strat.getIndependentElementMappings(true);
      final OpenJPAStateManager sm = this.assertOwner();
      final JDBCStore store = this.getStore();
      final JDBCFetchConfiguration fetch = store.getFetchConfiguration();
      final Joins[] resJoins = new Joins[Math.max(1, elems.length)];
      final FieldMapping fm = this._strat.getFieldMapping();
      Union union = store.getSQLFactory().newUnion(Math.max(1, elems.length));
      if (fetch.getSubclassFetchMode(fm.getElementMapping().getTypeMapping()) != 1) {
         union.abortUnion();
      }

      union.setLRS(true);
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            ClassMapping elem = elems.length == 0 ? null : elems[idx];
            sel.whereForeignKey(LRSProxyCollection.this._strat.getJoinForeignKey(elem), sm.getObjectId(), fm.getDefiningMapping(), store);
            fm.orderLocal(sel, elem, (Joins)null);
            resJoins[idx] = LRSProxyCollection.this._strat.joinElementRelation(sel.newJoins(), elem);
            fm.orderRelation(sel, elem, resJoins[idx]);
            LRSCollectionFieldStrategy var10000 = LRSProxyCollection.this._strat;
            JDBCFetchConfiguration var10005 = fetch;
            var10000.selectElement(sel, elem, store, fetch, 1, resJoins[idx]);
         }
      });

      try {
         Result res = union.execute(store, fetch);
         return new ResultIterator(sm, store, fetch, res, resJoins);
      } catch (SQLException var9) {
         throw SQLExceptions.getStore(var9, store.getDBDictionary());
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

   private class ResultIterator implements Iterator, Closeable {
      private final OpenJPAStateManager _sm;
      private final JDBCStore _store;
      private final JDBCFetchConfiguration _fetch;
      private final Result _res;
      private final Joins[] _joins;
      private Boolean _next = null;

      public ResultIterator(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins[] joins) {
         this._sm = sm;
         this._store = store;
         this._fetch = fetch;
         this._res = res;
         this._joins = joins;
      }

      public boolean hasNext() {
         if (this._next == null) {
            try {
               this._next = this._res.next() ? Boolean.TRUE : Boolean.FALSE;
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
            try {
               this._next = null;
               return LRSProxyCollection.this._strat.loadElement(this._sm, this._store, this._fetch, this._res, this._joins[this._res.indexOf()]);
            } catch (SQLException var2) {
               throw SQLExceptions.getStore(var2, this._store.getDBDictionary());
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void close() {
         this._next = Boolean.FALSE;
         this._res.close();
      }
   }
}
