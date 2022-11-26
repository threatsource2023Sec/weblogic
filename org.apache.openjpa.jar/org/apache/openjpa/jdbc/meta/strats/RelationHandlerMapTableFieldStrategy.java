package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxies;
import org.apache.openjpa.util.Proxy;

public class RelationHandlerMapTableFieldStrategy extends MapTableFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(RelationHandlerMapTableFieldStrategy.class);
   private Column[] _vcols = null;
   private ColumnIO _vio = null;
   private boolean _vload = false;

   public Column[] getKeyColumns(ClassMapping cls) {
      return this.field.getKeyMapping().getColumns();
   }

   public Column[] getValueColumns(ClassMapping cls) {
      return this._vcols;
   }

   public void selectKey(Select sel, ClassMapping key, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(key, this.field.getKeyMapping().getSelectSubclasses(), store, fetch, 0, joins);
   }

   public void selectValue(Select sel, ClassMapping val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(this._vcols, joins);
   }

   public Result[] getResults(final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch, final int eagerMode, final Joins[] resJoins, boolean lrs) throws SQLException {
      ValueMapping key = this.field.getKeyMapping();
      final ClassMapping[] keys = key.getIndependentTypeMappings();
      Union union = store.getSQLFactory().newUnion(keys.length);
      if (fetch.getSubclassFetchMode(key.getTypeMapping()) != 1) {
         union.abortUnion();
      }

      union.setLRS(lrs);
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            sel.select(RelationHandlerMapTableFieldStrategy.this._vcols);
            sel.whereForeignKey(RelationHandlerMapTableFieldStrategy.this.field.getJoinForeignKey(), sm.getObjectId(), RelationHandlerMapTableFieldStrategy.this.field.getDefiningMapping(), store);
            Joins joins = RelationHandlerMapTableFieldStrategy.this.joinKeyRelation(sel.newJoins(), keys[idx]);
            sel.select(keys[idx], RelationHandlerMapTableFieldStrategy.this.field.getKeyMapping().getSelectSubclasses(), store, fetch, eagerMode, joins);
            if (idx == 0) {
               resJoins[0] = joins;
            }

         }
      });
      Result res = union.execute(store, fetch);
      return new Result[]{res, res};
   }

   public Object loadKey(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      ClassMapping key = res.getBaseMapping();
      if (key == null) {
         key = this.field.getKeyMapping().getIndependentTypeMappings()[0];
      }

      return res.load(key, store, fetch, joins);
   }

   public Object loadValue(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return HandlerStrategies.loadObject(this.field.getElementMapping(), sm, store, fetch, res, joins, this._vcols, this._vload);
   }

   public Joins joinKeyRelation(Joins joins, ClassMapping key) {
      ValueMapping vm = this.field.getKeyMapping();
      return joins.joinRelation(this.field.getName(), vm.getForeignKey(key), key, vm.getSelectSubclasses(), false, false);
   }

   public void map(boolean adapt) {
      super.map(adapt);
      ValueMapping key = this.field.getKeyMapping();
      if (key.getTypeCode() == 15 && !key.isEmbeddedPC()) {
         ValueMapping val = this.field.getElementMapping();
         if (val.getHandler() == null) {
            throw new MetaDataException(_loc.get("no-handler", (Object)val));
         } else {
            this.assertNotMappedBy();
            this.field.mapJoin(adapt, true);
            this._vio = new ColumnIO();
            this._vcols = HandlerStrategies.map(val, "value", this._vio, adapt);
            if (key.getTypeMapping().isMapped()) {
               ValueMappingInfo vinfo = key.getValueInfo();
               ForeignKey fk = vinfo.getTypeJoin(key, "key", false, adapt);
               key.setForeignKey(fk);
               key.setColumnIO(vinfo.getColumnIO());
            } else {
               RelationStrategies.mapRelationToUnmappedPC(key, "key", adapt);
            }

            key.mapConstraints("key", adapt);
            this.field.mapPrimaryKey(adapt);
         }
      } else {
         throw new MetaDataException(_loc.get("not-relation", (Object)key));
      }
   }

   public void initialize() {
      this._vload = this.field.getElementMapping().getHandler().objectValueRequiresLoad(this.field.getElementMapping());
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.insert(sm, store, rm, (Map)sm.fetchObject(this.field.getIndex()));
   }

   private void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Map map) throws SQLException {
      if (map != null && !map.isEmpty()) {
         Row row = rm.getSecondaryRow(this.field.getTable(), 1);
         row.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
         ValueMapping val = this.field.getElementMapping();
         ValueMapping key = this.field.getKeyMapping();
         StoreContext ctx = store.getContext();
         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            OpenJPAStateManager keysm = RelationStrategies.getStateManager(entry.getKey(), ctx);
            key.setForeignKey(row, keysm);
            HandlerStrategies.set(val, entry.getValue(), store, row, this._vcols, this._vio, true);
            rm.flushSecondaryRow(row);
         }

      }
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Map map = (Map)sm.fetchObject(this.field.getIndex());
      ChangeTracker ct = null;
      if (map instanceof Proxy) {
         Proxy proxy = (Proxy)map;
         if (Proxies.isOwner(proxy, sm, this.field.getIndex())) {
            ct = proxy.getChangeTracker();
         }
      }

      if (ct != null && ct.isTracking()) {
         ValueMapping key = this.field.getKeyMapping();
         StoreContext ctx = store.getContext();
         Collection rem = ct.getRemoved();
         OpenJPAStateManager keysm;
         if (!rem.isEmpty()) {
            Row delRow = rm.getSecondaryRow(this.field.getTable(), 2);
            delRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = rem.iterator();

            while(itr.hasNext()) {
               keysm = RelationStrategies.getStateManager(itr.next(), ctx);
               key.whereForeignKey(delRow, keysm);
               rm.flushSecondaryRow(delRow);
            }
         }

         ValueMapping val = this.field.getElementMapping();
         Collection add = ct.getAdded();
         Object mkey;
         if (!add.isEmpty()) {
            Row addRow = rm.getSecondaryRow(this.field.getTable(), 1);
            addRow.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
            Iterator itr = add.iterator();

            while(itr.hasNext()) {
               mkey = itr.next();
               keysm = RelationStrategies.getStateManager(mkey, ctx);
               key.setForeignKey(addRow, keysm);
               HandlerStrategies.set(val, map.get(mkey), store, addRow, this._vcols, this._vio, true);
               rm.flushSecondaryRow(addRow);
            }
         }

         Collection change = ct.getChanged();
         if (!change.isEmpty()) {
            Row changeRow = rm.getSecondaryRow(this.field.getTable(), 0);
            changeRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = change.iterator();

            while(itr.hasNext()) {
               mkey = itr.next();
               keysm = RelationStrategies.getStateManager(mkey, ctx);
               key.whereForeignKey(changeRow, keysm);
               HandlerStrategies.set(val, map.get(mkey), store, changeRow, this._vcols, this._vio, true);
               rm.flushSecondaryRow(changeRow);
            }
         }

      } else {
         this.delete(sm, store, rm);
         this.insert(sm, store, rm, map);
      }
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      if (traverse) {
         HandlerStrategies.assertJoinable(this.field.getElementMapping());
      }

      return joins;
   }

   public Joins joinKeyRelation(Joins joins, boolean forceOuter, boolean traverse) {
      ValueMapping key = this.field.getKeyMapping();
      ClassMapping[] clss = key.getIndependentTypeMappings();
      if (clss.length != 1) {
         if (traverse) {
            throw RelationStrategies.unjoinable(this.field.getKeyMapping());
         } else {
            return joins;
         }
      } else {
         return forceOuter ? joins.outerJoinRelation(this.field.getName(), key.getForeignKey(clss[0]), clss[0], key.getSelectSubclasses(), false, false) : joins.joinRelation(this.field.getName(), key.getForeignKey(clss[0]), clss[0], key.getSelectSubclasses(), false, false);
      }
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return HandlerStrategies.toDataStoreValue(this.field.getElementMapping(), val, this._vcols, store);
   }

   public Object toKeyDataStoreValue(Object val, JDBCStore store) {
      return RelationStrategies.toDataStoreValue(this.field.getKeyMapping(), val, store);
   }
}
