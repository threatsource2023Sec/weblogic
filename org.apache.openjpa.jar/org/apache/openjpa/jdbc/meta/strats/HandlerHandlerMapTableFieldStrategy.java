package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxies;
import org.apache.openjpa.util.Proxy;

public class HandlerHandlerMapTableFieldStrategy extends MapTableFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(HandlerHandlerMapTableFieldStrategy.class);
   private Column[] _kcols = null;
   private ColumnIO _kio = null;
   private boolean _kload = false;
   private Column[] _vcols = null;
   private ColumnIO _vio = null;
   private boolean _vload = false;

   public Column[] getKeyColumns(ClassMapping cls) {
      return this._kcols;
   }

   public Column[] getValueColumns(ClassMapping cls) {
      return this._vcols;
   }

   public void selectKey(Select sel, ClassMapping cls, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(this._kcols, joins);
   }

   public void selectValue(Select sel, ClassMapping cls, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
      sel.select(this._vcols, joins);
   }

   public Result[] getResults(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode, Joins[] joins, boolean lrs) throws SQLException {
      Select sel = store.getSQLFactory().newSelect();
      sel.setLRS(lrs);
      sel.select(this._kcols);
      sel.select(this._vcols);
      sel.whereForeignKey(this.field.getJoinForeignKey(), sm.getObjectId(), this.field.getDefiningMapping(), store);
      Result res = sel.execute(store, fetch);
      return new Result[]{res, res};
   }

   public Object loadKey(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return HandlerStrategies.loadObject(this.field.getKeyMapping(), sm, store, fetch, res, joins, this._kcols, this._kload);
   }

   public Object loadValue(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return HandlerStrategies.loadObject(this.field.getElementMapping(), sm, store, fetch, res, joins, this._vcols, this._vload);
   }

   public void map(boolean adapt) {
      super.map(adapt);
      ValueMapping key = this.field.getKeyMapping();
      if (key.getHandler() == null) {
         throw new MetaDataException(_loc.get("no-handler", (Object)key));
      } else {
         ValueMapping val = this.field.getElementMapping();
         if (val.getHandler() == null) {
            throw new MetaDataException(_loc.get("no-handler", (Object)val));
         } else {
            this.assertNotMappedBy();
            this.field.mapJoin(adapt, true);
            this._kio = new ColumnIO();
            DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
            this._kcols = HandlerStrategies.map(key, dict.getValidColumnName("key", this.field.getTable()), this._kio, adapt);
            this._vio = new ColumnIO();
            this._vcols = HandlerStrategies.map(val, "value", this._vio, adapt);
            this.field.mapPrimaryKey(adapt);
         }
      }
   }

   public void initialize() {
      this._kload = this.field.getKeyMapping().getHandler().objectValueRequiresLoad(this.field.getKeyMapping());
      this._vload = this.field.getElementMapping().getHandler().objectValueRequiresLoad(this.field.getElementMapping());
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
         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            HandlerStrategies.set(key, entry.getKey(), store, row, this._kcols, this._kio, true);
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
         Collection rem = ct.getRemoved();
         if (!rem.isEmpty()) {
            Row delRow = rm.getSecondaryRow(this.field.getTable(), 2);
            delRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = rem.iterator();

            while(itr.hasNext()) {
               HandlerStrategies.where(key, itr.next(), store, delRow, this._kcols);
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
               HandlerStrategies.set(key, mkey, store, addRow, this._kcols, this._kio, true);
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
               HandlerStrategies.where(key, mkey, store, changeRow, this._kcols);
               HandlerStrategies.set(val, map.get(mkey), store, changeRow, this._vcols, this._vio, true);
               rm.flushSecondaryRow(changeRow);
            }
         }

      } else {
         this.delete(sm, store, rm);
         this.insert(sm, store, rm, map);
      }
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return HandlerStrategies.toDataStoreValue(this.field.getElementMapping(), val, this._vcols, store);
   }

   public Object toKeyDataStoreValue(Object val, JDBCStore store) {
      return HandlerStrategies.toDataStoreValue(this.field.getKeyMapping(), val, this._kcols, store);
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      if (traverse) {
         HandlerStrategies.assertJoinable(this.field.getElementMapping());
      }

      return joins;
   }

   public Joins joinKeyRelation(Joins joins, boolean forceOuter, boolean traverse) {
      if (traverse) {
         HandlerStrategies.assertJoinable(this.field.getKeyMapping());
      }

      return joins;
   }
}
