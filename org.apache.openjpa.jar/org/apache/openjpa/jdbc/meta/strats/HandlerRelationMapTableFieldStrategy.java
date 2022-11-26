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
import org.apache.openjpa.jdbc.sql.DBDictionary;
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

public class HandlerRelationMapTableFieldStrategy extends MapTableFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(HandlerRelationMapTableFieldStrategy.class);
   private Column[] _kcols = null;
   private ColumnIO _kio = null;
   private boolean _kload = false;

   public Column[] getKeyColumns(ClassMapping cls) {
      return this._kcols;
   }

   public Column[] getValueColumns(ClassMapping cls) {
      return this.field.getElementMapping().getColumns();
   }

   public void selectKey(Select sel, ClassMapping key, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(this._kcols, joins);
   }

   public void selectValue(Select sel, ClassMapping val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(val, this.field.getElementMapping().getSelectSubclasses(), store, fetch, 0, joins);
   }

   public Result[] getResults(final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch, final int eagerMode, final Joins[] resJoins, boolean lrs) throws SQLException {
      ValueMapping elem = this.field.getElementMapping();
      final ClassMapping[] vals = elem.getIndependentTypeMappings();
      Union union = store.getSQLFactory().newUnion(vals.length);
      if (fetch.getSubclassFetchMode(elem.getTypeMapping()) != 1) {
         union.abortUnion();
      }

      union.setLRS(lrs);
      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            sel.select(HandlerRelationMapTableFieldStrategy.this._kcols);
            sel.whereForeignKey(HandlerRelationMapTableFieldStrategy.this.field.getJoinForeignKey(), sm.getObjectId(), HandlerRelationMapTableFieldStrategy.this.field.getDefiningMapping(), store);
            Joins joins = HandlerRelationMapTableFieldStrategy.this.joinValueRelation(sel.newJoins(), vals[idx]);
            sel.select(vals[idx], HandlerRelationMapTableFieldStrategy.this.field.getElementMapping().getSelectSubclasses(), store, fetch, eagerMode, joins);
            if (idx == 0) {
               resJoins[1] = joins;
            }

         }
      });
      Result res = union.execute(store, fetch);
      return new Result[]{res, res};
   }

   public Object loadKey(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return HandlerStrategies.loadObject(this.field.getKeyMapping(), sm, store, fetch, res, joins, this._kcols, this._kload);
   }

   public Object loadValue(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      ClassMapping val = res.getBaseMapping();
      if (val == null) {
         val = this.field.getElementMapping().getIndependentTypeMappings()[0];
      }

      return res.load(val, store, fetch, joins);
   }

   public Joins joinValueRelation(Joins joins, ClassMapping val) {
      ValueMapping vm = this.field.getElementMapping();
      return joins.joinRelation(this.field.getName(), vm.getForeignKey(val), val, vm.getSelectSubclasses(), false, false);
   }

   public void map(boolean adapt) {
      super.map(adapt);
      ValueMapping key = this.field.getKeyMapping();
      if (key.getHandler() == null) {
         throw new MetaDataException(_loc.get("no-handler", (Object)key));
      } else {
         ValueMapping val = this.field.getElementMapping();
         if (val.getTypeCode() == 15 && !val.isEmbeddedPC()) {
            this.assertNotMappedBy();
            this.field.mapJoin(adapt, true);
            this._kio = new ColumnIO();
            DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
            this._kcols = HandlerStrategies.map(key, dict.getValidColumnName("key", this.field.getTable()), this._kio, adapt);
            if (val.getTypeMapping().isMapped()) {
               ValueMappingInfo vinfo = val.getValueInfo();
               ForeignKey fk = vinfo.getTypeJoin(val, "value", false, adapt);
               val.setForeignKey(fk);
               val.setColumnIO(vinfo.getColumnIO());
            } else {
               RelationStrategies.mapRelationToUnmappedPC(val, "value", adapt);
            }

            val.mapConstraints("value", adapt);
            this.field.mapPrimaryKey(adapt);
         } else {
            throw new MetaDataException(_loc.get("not-relation", (Object)val));
         }
      }
   }

   public void initialize() {
      this._kload = this.field.getKeyMapping().getHandler().objectValueRequiresLoad(this.field.getKeyMapping());
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.insert(sm, store, rm, (Map)sm.fetchObject(this.field.getIndex()));
   }

   private void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Map map) throws SQLException {
      if (map != null && !map.isEmpty()) {
         Row row = rm.getSecondaryRow(this.field.getTable(), 1);
         row.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
         ValueMapping key = this.field.getKeyMapping();
         ValueMapping val = this.field.getElementMapping();
         StoreContext ctx = store.getContext();
         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            HandlerStrategies.set(key, entry.getKey(), store, row, this._kcols, this._kio, true);
            OpenJPAStateManager valsm = RelationStrategies.getStateManager(entry.getValue(), ctx);
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
         OpenJPAStateManager valsm;
         Object mkey;
         if (canChange && !change.isEmpty()) {
            Row changeRow = rm.getSecondaryRow(this.field.getTable(), 0);
            changeRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = change.iterator();

            while(itr.hasNext()) {
               mkey = itr.next();
               HandlerStrategies.where(key, mkey, store, changeRow, this._kcols);
               valsm = RelationStrategies.getStateManager(map.get(mkey), ctx);
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
               HandlerStrategies.where(key, itr.next(), store, delRow, this._kcols);
               rm.flushSecondaryRow(delRow);
            }

            if (!canChange && !change.isEmpty()) {
               itr = change.iterator();

               while(itr.hasNext()) {
                  HandlerStrategies.where(key, itr.next(), store, delRow, this._kcols);
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
               HandlerStrategies.set(key, mkey, store, addRow, this._kcols, this._kio, true);
               valsm = RelationStrategies.getStateManager(map.get(mkey), ctx);
               val.setForeignKey(addRow, valsm);
               rm.flushSecondaryRow(addRow);
            }

            if (!canChange && !change.isEmpty()) {
               itr = change.iterator();

               while(itr.hasNext()) {
                  mkey = itr.next();
                  HandlerStrategies.set(key, mkey, store, addRow, this._kcols, this._kio, true);
                  valsm = RelationStrategies.getStateManager(map.get(mkey), ctx);
                  val.setForeignKey(addRow, valsm);
                  rm.flushSecondaryRow(addRow);
               }
            }
         }

      } else {
         this.delete(sm, store, rm);
         this.insert(sm, store, rm, map);
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
      if (traverse) {
         HandlerStrategies.assertJoinable(this.field.getKeyMapping());
      }

      return joins;
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return RelationStrategies.toDataStoreValue(this.field.getElementMapping(), val, store);
   }

   public Object toKeyDataStoreValue(Object val, JDBCStore store) {
      return HandlerStrategies.toDataStoreValue(this.field.getKeyMapping(), val, this._kcols, store);
   }
}
