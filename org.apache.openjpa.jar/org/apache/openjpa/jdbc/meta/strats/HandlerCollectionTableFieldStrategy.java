package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.FieldMappingInfo;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxies;
import org.apache.openjpa.util.Proxy;

public class HandlerCollectionTableFieldStrategy extends StoreCollectionFieldStrategy implements LRSCollectionFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(HandlerCollectionTableFieldStrategy.class);
   private Column[] _cols = null;
   private ColumnIO _io = null;
   private boolean _load = false;
   private boolean _lob = false;
   private boolean _embed = false;

   public FieldMapping getFieldMapping() {
      return this.field;
   }

   public ClassMapping[] getIndependentElementMappings(boolean traverse) {
      return ClassMapping.EMPTY_MAPPINGS;
   }

   public Column[] getElementColumns(ClassMapping elem) {
      return this._cols;
   }

   public ForeignKey getJoinForeignKey(ClassMapping elem) {
      return this.field.getJoinForeignKey();
   }

   public void selectElement(Select sel, ClassMapping elem, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode, Joins joins) {
      sel.select(this._cols, joins);
   }

   public Object loadElement(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return HandlerStrategies.loadObject(this.field.getElementMapping(), sm, store, fetch, res, joins, this._cols, this._load);
   }

   protected Joins join(Joins joins, ClassMapping elem) {
      return this.join(joins, false);
   }

   public Joins joinElementRelation(Joins joins, ClassMapping elem) {
      return this.joinRelation(joins, false, false);
   }

   protected Proxy newLRSProxy() {
      return new LRSProxyCollection(this);
   }

   public void map(boolean adapt) {
      if (this.field.getTypeCode() != 12 && this.field.getTypeCode() != 11) {
         throw new MetaDataException(_loc.get("not-coll", (Object)this.field));
      } else {
         this.assertNotMappedBy();
         this.field.getValueInfo().assertNoSchemaComponents(this.field, !adapt);
         this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
         ValueMapping elem = this.field.getElementMapping();
         if (elem.getHandler() == null) {
            throw new MetaDataException(_loc.get("no-handler", (Object)elem));
         } else {
            this.field.mapJoin(adapt, true);
            this._io = new ColumnIO();
            this._cols = HandlerStrategies.map(elem, "element", this._io, adapt);
            FieldMappingInfo finfo = this.field.getMappingInfo();
            Column orderCol = finfo.getOrderColumn(this.field, this.field.getTable(), adapt);
            this.field.setOrderColumn(orderCol);
            this.field.setOrderColumnIO(finfo.getColumnIO());
            this.field.mapPrimaryKey(adapt);
         }
      }
   }

   public void initialize() {
      for(int i = 0; !this._lob && i < this._cols.length; ++i) {
         this._lob = this._cols[i].isLob();
      }

      ValueMapping elem = this.field.getElementMapping();
      this._embed = elem.getEmbeddedMetaData() != null;
      this._load = elem.getHandler().objectValueRequiresLoad(elem);
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.insert(sm, store, rm, sm.fetchObject(this.field.getIndex()));
   }

   private void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object vals) throws SQLException {
      Object coll;
      if (this.field.getTypeCode() == 11) {
         coll = JavaTypes.toList(vals, this.field.getElement().getType(), false);
      } else {
         coll = (Collection)vals;
      }

      if (coll != null && !((Collection)coll).isEmpty()) {
         Row row = rm.getSecondaryRow(this.field.getTable(), 1);
         row.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
         ValueMapping elem = this.field.getElementMapping();
         Column order = this.field.getOrderColumn();
         boolean setOrder = this.field.getOrderColumnIO().isInsertable(order, false);
         int idx = 0;

         for(Iterator itr = ((Collection)coll).iterator(); itr.hasNext(); ++idx) {
            HandlerStrategies.set(elem, itr.next(), store, row, this._cols, this._io, true);
            if (setOrder) {
               row.setInt(order, idx);
            }

            rm.flushSecondaryRow(row);
         }

      }
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Object obj = sm.fetchObject(this.field.getIndex());
      ChangeTracker ct = null;
      if (obj instanceof Proxy) {
         Proxy proxy = (Proxy)obj;
         if (Proxies.isOwner(proxy, sm, this.field.getIndex())) {
            ct = proxy.getChangeTracker();
         }
      }

      if (ct != null && ct.isTracking()) {
         ValueMapping elem = this.field.getElementMapping();
         Collection rem = ct.getRemoved();
         if (!rem.isEmpty()) {
            Row delRow = rm.getSecondaryRow(this.field.getTable(), 2);
            delRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
            Iterator itr = rem.iterator();

            while(itr.hasNext()) {
               HandlerStrategies.where(elem, itr.next(), store, delRow, this._cols);
               rm.flushSecondaryRow(delRow);
            }
         }

         Collection add = ct.getAdded();
         if (!add.isEmpty()) {
            Row addRow = rm.getSecondaryRow(this.field.getTable(), 1);
            addRow.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
            int seq = ct.getNextSequence();
            Column order = this.field.getOrderColumn();
            boolean setOrder = this.field.getOrderColumnIO().isInsertable(order, false);

            for(Iterator itr = add.iterator(); itr.hasNext(); ++seq) {
               HandlerStrategies.set(elem, itr.next(), store, addRow, this._cols, this._io, true);
               if (setOrder) {
                  addRow.setInt(order, seq);
               }

               rm.flushSecondaryRow(addRow);
            }

            if (order != null) {
               ct.setNextSequence(seq);
            }
         }

      } else {
         this.delete(sm, store, rm);
         this.insert(sm, store, rm, obj);
      }
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getAllRows(this.field.getTable(), 2);
      row.whereForeignKey(this.field.getJoinForeignKey(), sm);
      rm.flushAllRows(row);
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return !this._lob && (!this._embed || type != 2) ? super.supportsSelect(sel, type, sm, store, fetch) : 0;
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return HandlerStrategies.toDataStoreValue(this.field.getElementMapping(), val, this._cols, store);
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, true);
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      if (traverse) {
         HandlerStrategies.assertJoinable(this.field.getElementMapping());
      }

      return joins;
   }
}
