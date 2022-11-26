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

public class RelationRelationMapTableFieldStrategy extends MapTableFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(RelationRelationMapTableFieldStrategy.class);
   private String _keyRelationName = null;

   public Column[] getKeyColumns(ClassMapping cls) {
      return this.field.getKeyMapping().getColumns();
   }

   public Column[] getValueColumns(ClassMapping cls) {
      return this.field.getElementMapping().getColumns();
   }

   public void selectKey(Select sel, ClassMapping key, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(key, this.field.getKeyMapping().getSelectSubclasses(), store, fetch, 0, joins);
   }

   public void selectValue(Select sel, ClassMapping val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(val, this.field.getElementMapping().getSelectSubclasses(), store, fetch, 0, joins);
   }

   public Result[] getResults(final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch, final int eagerMode, final Joins[] resJoins, boolean lrs) throws SQLException {
      ValueMapping key = this.field.getKeyMapping();
      final ClassMapping[] keys = key.getIndependentTypeMappings();
      Union kunion = store.getSQLFactory().newUnion(keys.length);
      if (fetch.getSubclassFetchMode(key.getTypeMapping()) != 1) {
         kunion.abortUnion();
      }

      kunion.setLRS(lrs);
      kunion.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            sel.whereForeignKey(RelationRelationMapTableFieldStrategy.this.field.getJoinForeignKey(), sm.getObjectId(), RelationRelationMapTableFieldStrategy.this.field.getDefiningMapping(), store);
            Joins joins = RelationRelationMapTableFieldStrategy.this.joinKeyRelation(sel.newJoins(), keys[idx]);
            sel.orderBy(RelationRelationMapTableFieldStrategy.this.field.getKeyMapping().getColumns(), true, true);
            sel.select(keys[idx], RelationRelationMapTableFieldStrategy.this.field.getKeyMapping().getSelectSubclasses(), store, fetch, eagerMode, joins);
            if (idx == 0) {
               resJoins[0] = joins;
            }

         }
      });
      ValueMapping val = this.field.getElementMapping();
      final ClassMapping[] vals = val.getIndependentTypeMappings();
      Union vunion = store.getSQLFactory().newUnion(vals.length);
      if (fetch.getSubclassFetchMode(val.getTypeMapping()) != 1) {
         vunion.abortUnion();
      }

      vunion.setLRS(lrs);
      vunion.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            sel.whereForeignKey(RelationRelationMapTableFieldStrategy.this.field.getJoinForeignKey(), sm.getObjectId(), RelationRelationMapTableFieldStrategy.this.field.getDefiningMapping(), store);
            Joins joins = RelationRelationMapTableFieldStrategy.this.joinValueRelation(sel.newJoins(), vals[idx]);
            sel.orderBy(RelationRelationMapTableFieldStrategy.this.field.getKeyMapping().getColumns(), true, true);
            sel.select(vals[idx], RelationRelationMapTableFieldStrategy.this.field.getElementMapping().getSelectSubclasses(), store, fetch, eagerMode, joins);
            if (idx == 0) {
               resJoins[1] = joins;
            }

         }
      });
      Result kres = null;
      Result vres = null;

      try {
         kres = kunion.execute(store, fetch);
         vres = vunion.execute(store, fetch);
         return new Result[]{kres, vres};
      } catch (SQLException var16) {
         if (kres != null) {
            kres.close();
         }

         if (vres != null) {
            vres.close();
         }

         throw var16;
      }
   }

   public Object loadKey(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      ClassMapping key = res.getBaseMapping();
      if (key == null) {
         key = this.field.getKeyMapping().getIndependentTypeMappings()[0];
      }

      return res.load(key, store, fetch, joins);
   }

   public Object loadValue(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      ClassMapping val = res.getBaseMapping();
      if (val == null) {
         val = this.field.getElementMapping().getIndependentTypeMappings()[0];
      }

      return res.load(val, store, fetch, joins);
   }

   public Joins joinKeyRelation(Joins joins, ClassMapping key) {
      ValueMapping vm = this.field.getKeyMapping();
      return joins.joinRelation(this._keyRelationName, vm.getForeignKey(key), key, vm.getSelectSubclasses(), false, false);
   }

   public Joins joinValueRelation(Joins joins, ClassMapping val) {
      ValueMapping vm = this.field.getElementMapping();
      return joins.joinRelation(this.field.getName(), vm.getForeignKey(val), val, vm.getSelectSubclasses(), false, false);
   }

   public void map(boolean adapt) {
      super.map(adapt);
      ValueMapping key = this.field.getKeyMapping();
      if (key.getTypeCode() == 15 && !key.isEmbeddedPC()) {
         ValueMapping val = this.field.getElementMapping();
         if (val.getTypeCode() == 15 && !val.isEmbeddedPC()) {
            this.assertNotMappedBy();
            this.field.mapJoin(adapt, true);
            this.mapTypeJoin(key, "key", adapt);
            this.mapTypeJoin(val, "value", adapt);
            this.field.mapPrimaryKey(adapt);
         } else {
            throw new MetaDataException(_loc.get("not-relation", (Object)val));
         }
      } else {
         throw new MetaDataException(_loc.get("not-relation", (Object)key));
      }
   }

   private void mapTypeJoin(ValueMapping vm, String name, boolean adapt) {
      if (vm.getTypeMapping().isMapped()) {
         ValueMappingInfo vinfo = vm.getValueInfo();
         ForeignKey fk = vinfo.getTypeJoin(vm, name, false, adapt);
         vm.setForeignKey(fk);
         vm.setColumnIO(vinfo.getColumnIO());
      } else {
         RelationStrategies.mapRelationToUnmappedPC(vm, name, adapt);
      }

      vm.mapConstraints(name, adapt);
   }

   public void initialize() {
      this._keyRelationName = this.field.getName() + ":key";
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.insert(sm, rm, (Map)sm.fetchObject(this.field.getIndex()));
   }

   private void insert(OpenJPAStateManager sm, RowManager rm, Map map) throws SQLException {
      if (map != null && !map.isEmpty()) {
         Row row = rm.getSecondaryRow(this.field.getTable(), 1);
         row.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
         ValueMapping key = this.field.getKeyMapping();
         ValueMapping val = this.field.getElementMapping();
         StoreContext ctx = sm.getContext();
         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            OpenJPAStateManager keysm = RelationStrategies.getStateManager(entry.getKey(), ctx);
            OpenJPAStateManager valsm = RelationStrategies.getStateManager(entry.getValue(), ctx);
            key.setForeignKey(row, keysm);
            val.setForeignKey(row, valsm);
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
         ValueMapping val = this.field.getElementMapping();
         StoreContext ctx = store.getContext();
         Collection change = ct.getChanged();
         boolean canChange = val.getForeignKey().isLogical();
         OpenJPAStateManager keysm;
         OpenJPAStateManager valsm;
         Object mkey;
         if (canChange && !change.isEmpty()) {
            Row changeRow = rm.getSecondaryRow(this.field.getTable(), 0);
            changeRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = change.iterator();

            while(itr.hasNext()) {
               mkey = itr.next();
               keysm = RelationStrategies.getStateManager(mkey, ctx);
               valsm = RelationStrategies.getStateManager(map.get(mkey), ctx);
               key.whereForeignKey(changeRow, keysm);
               val.setForeignKey(changeRow, valsm);
               rm.flushSecondaryRow(changeRow);
            }
         }

         Collection rem = ct.getRemoved();
         if (!rem.isEmpty() || !canChange && !change.isEmpty()) {
            Row delRow = rm.getSecondaryRow(this.field.getTable(), 2);
            delRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = rem.iterator();

            while(itr.hasNext()) {
               keysm = RelationStrategies.getStateManager(itr.next(), ctx);
               key.whereForeignKey(delRow, keysm);
               rm.flushSecondaryRow(delRow);
            }

            if (!canChange && !change.isEmpty()) {
               itr = change.iterator();

               while(itr.hasNext()) {
                  keysm = RelationStrategies.getStateManager(itr.next(), ctx);
                  key.whereForeignKey(delRow, keysm);
                  rm.flushSecondaryRow(delRow);
               }
            }
         }

         Collection add = ct.getAdded();
         if (!add.isEmpty() || !canChange && !change.isEmpty()) {
            Row addRow = rm.getSecondaryRow(this.field.getTable(), 1);
            addRow.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
            Iterator itr = add.iterator();

            while(itr.hasNext()) {
               mkey = itr.next();
               keysm = RelationStrategies.getStateManager(mkey, ctx);
               valsm = RelationStrategies.getStateManager(map.get(mkey), ctx);
               key.setForeignKey(addRow, keysm);
               val.setForeignKey(addRow, valsm);
               rm.flushSecondaryRow(addRow);
            }

            if (!canChange && !change.isEmpty()) {
               itr = change.iterator();

               while(itr.hasNext()) {
                  mkey = itr.next();
                  keysm = RelationStrategies.getStateManager(mkey, ctx);
                  valsm = RelationStrategies.getStateManager(map.get(mkey), ctx);
                  key.setForeignKey(addRow, keysm);
                  val.setForeignKey(addRow, valsm);
                  rm.flushSecondaryRow(addRow);
               }
            }
         }

      } else {
         this.delete(sm, store, rm);
         this.insert(sm, rm, map);
      }
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      ValueMapping val = this.field.getElementMapping();
      ClassMapping[] clss = val.getIndependentTypeMappings();
      if (clss.length != 1) {
         if (traverse) {
            throw RelationStrategies.unjoinable(val);
         } else {
            return joins;
         }
      } else {
         return forceOuter ? joins.outerJoinRelation(this.field.getName(), val.getForeignKey(clss[0]), clss[0], val.getSelectSubclasses(), false, false) : joins.joinRelation(this.field.getName(), val.getForeignKey(clss[0]), clss[0], val.getSelectSubclasses(), false, false);
      }
   }

   public Joins joinKeyRelation(Joins joins, boolean forceOuter, boolean traverse) {
      ValueMapping key = this.field.getKeyMapping();
      ClassMapping[] clss = key.getIndependentTypeMappings();
      if (clss.length != 1) {
         if (traverse) {
            throw RelationStrategies.unjoinable(key);
         } else {
            return joins;
         }
      } else {
         return forceOuter ? joins.outerJoinRelation(this.field.getName(), key.getForeignKey(clss[0]), clss[0], key.getSelectSubclasses(), false, false) : joins.joinRelation(this._keyRelationName, key.getForeignKey(clss[0]), clss[0], key.getSelectSubclasses(), false, false);
      }
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return RelationStrategies.toDataStoreValue(this.field.getElementMapping(), val, store);
   }

   public Object toKeyDataStoreValue(Object val, JDBCStore store) {
      return RelationStrategies.toDataStoreValue(this.field.getKeyMapping(), val, store);
   }
}
