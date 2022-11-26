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
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxies;
import org.apache.openjpa.util.Proxy;

public abstract class RelationToManyInverseKeyFieldStrategy extends StoreCollectionFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(RelationToManyInverseKeyFieldStrategy.class);
   private boolean _orderInsert = false;
   private boolean _orderUpdate = false;

   protected ClassMapping[] getIndependentElementMappings(boolean traverse) {
      return this.field.getElementMapping().getIndependentTypeMappings();
   }

   protected ForeignKey getJoinForeignKey(ClassMapping elem) {
      return this.field.getElementMapping().getForeignKey(elem);
   }

   protected void selectElement(Select sel, ClassMapping elem, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode, Joins joins) {
      sel.select(elem, this.field.getElementMapping().getSelectSubclasses(), store, fetch, eagerMode, joins);
   }

   protected Object loadElement(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      ClassMapping elem = res.getBaseMapping();
      if (elem == null) {
         elem = this.field.getElementMapping().getIndependentTypeMappings()[0];
      }

      return res.load(elem, store, fetch, joins);
   }

   protected Joins join(Joins joins, ClassMapping elem) {
      ValueMapping vm = this.field.getElementMapping();
      ForeignKey fk = vm.getForeignKey(elem);
      ClassMapping owner = this.field.getDefiningMapping();

      do {
         if (fk.getPrimaryKeyTable() == owner.getTable()) {
            return joins.joinRelation(this.field.getName(), fk, elem, vm.getSelectSubclasses(), true, true);
         }

         joins = owner.joinSuperclass(joins, false);
         owner = owner.getJoinablePCSuperclassMapping();
      } while(owner != null);

      throw new InternalException();
   }

   protected Joins joinElementRelation(Joins joins, ClassMapping elem) {
      return this.joinRelation(joins, false, false);
   }

   public void map(boolean adapt) {
      this.field.getValueInfo().assertNoSchemaComponents(this.field, !adapt);
      this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
      ValueMapping elem = this.field.getElementMapping();
      if (elem.getTypeCode() == 15 && !elem.isEmbeddedPC() && elem.getTypeMapping().isMapped()) {
         FieldMapping mapped = this.field.getMappedByMapping();
         FieldMappingInfo finfo = this.field.getMappingInfo();
         ValueMappingInfo vinfo = elem.getValueInfo();
         boolean criteria = vinfo.getUseClassCriteria();
         if (mapped != null) {
            mapped.resolve(1 | 2);
            if (!(mapped.getStrategy() instanceof RelationFieldStrategy)) {
               throw new MetaDataException(_loc.get("not-inv-relation", this.field, mapped));
            } else {
               vinfo.assertNoSchemaComponents(elem, !adapt);
               elem.setForeignKey(mapped.getForeignKey(this.field.getDefiningMapping()));
               elem.setColumns(mapped.getDefiningMapping().getPrimaryKeyColumns());
               elem.setJoinDirection(2);
               elem.setUseClassCriteria(criteria);
               this.field.setOrderColumn(finfo.getOrderColumn(this.field, mapped.getForeignKey().getTable(), adapt));
               this.field.setOrderColumnIO(finfo.getColumnIO());
            }
         } else {
            ForeignKey fk = vinfo.getInverseTypeJoin(elem, this.field.getName(), adapt);
            elem.setForeignKey(fk);
            elem.setColumnIO(vinfo.getColumnIO());
            elem.setColumns(elem.getTypeMapping().getPrimaryKeyColumns());
            elem.setJoinDirection(2);
            elem.setUseClassCriteria(criteria);
            elem.mapConstraints(this.field.getName(), adapt);
            this.field.setOrderColumn(finfo.getOrderColumn(this.field, fk.getTable(), adapt));
            this.field.setOrderColumnIO(finfo.getColumnIO());
         }
      } else {
         throw new MetaDataException(_loc.get("not-elem-relation", (Object)this.field));
      }
   }

   public void initialize() {
      Column order = this.field.getOrderColumn();
      this._orderInsert = this.field.getOrderColumnIO().isInsertable(order, false);
      this._orderUpdate = this.field.getOrderColumnIO().isUpdatable(order, false);
      ValueMapping elem = this.field.getElementMapping();
      Log log = this.field.getRepository().getLog();
      if (this.field.getMappedBy() == null && elem.getUseClassCriteria() && log.isWarnEnabled()) {
         ForeignKey fk = elem.getForeignKey();
         if (elem.getColumnIO().isAnyUpdatable(fk, false)) {
            log.warn(_loc.get("class-crit-owner", (Object)this.field));
         }
      }

   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null || this._orderInsert || this._orderUpdate) {
         this.insert(sm, rm, sm.fetchObject(this.field.getIndex()));
      }

   }

   private void insert(OpenJPAStateManager sm, RowManager rm, Object vals) throws SQLException {
      if (this.field.getMappedBy() == null || this._orderInsert || this._orderUpdate) {
         Collection coll = this.toCollection(vals);
         if (coll != null && !coll.isEmpty()) {
            ClassMapping rel = this.field.getElementMapping().getTypeMapping();
            int idx = 0;

            for(Iterator itr = coll.iterator(); itr.hasNext(); ++idx) {
               this.updateInverse(sm.getContext(), itr.next(), rel, rm, sm, idx);
            }

         }
      }
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null || this._orderInsert || this._orderUpdate) {
         Object obj = sm.fetchObject(this.field.getIndex());
         ChangeTracker ct = null;
         if (obj instanceof Proxy) {
            Proxy proxy = (Proxy)obj;
            if (Proxies.isOwner(proxy, sm, this.field.getIndex())) {
               ct = proxy.getChangeTracker();
            }
         }

         if (ct != null && ct.isTracking()) {
            ClassMapping rel = this.field.getElementMapping().getTypeMapping();
            StoreContext ctx = store.getContext();
            Collection add;
            if (this.field.getMappedBy() == null) {
               add = ct.getRemoved();
               Iterator itr = add.iterator();

               while(itr.hasNext()) {
                  this.updateInverse(ctx, itr.next(), rel, rm, (OpenJPAStateManager)null, 0);
               }
            }

            add = ct.getAdded();
            int seq = ct.getNextSequence();

            for(Iterator itr = add.iterator(); itr.hasNext(); ++seq) {
               this.updateInverse(ctx, itr.next(), rel, rm, sm, seq);
            }

            if (this.field.getOrderColumn() != null) {
               ct.setNextSequence(seq);
            }

         } else {
            this.delete(sm, store, rm);
            this.insert(sm, rm, obj);
         }
      }
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null) {
         ValueMapping elem = this.field.getElementMapping();
         ColumnIO io = elem.getColumnIO();
         ForeignKey fk = elem.getForeignKey();
         if (!elem.getUseClassCriteria() && io.isAnyUpdatable(fk, true)) {
            this.assertInversable();
            Row row = rm.getAllRows(fk.getTable(), 0);
            row.setForeignKey(fk, io, (OpenJPAStateManager)null);
            row.whereForeignKey(fk, sm);
            rm.flushAllRows(row);
         } else if (sm.getLoaded().get(this.field.getIndex())) {
            ClassMapping rel = this.field.getElementMapping().getTypeMapping();
            StoreContext ctx = store.getContext();
            Collection objs = this.toCollection(sm.fetchObject(this.field.getIndex()));
            if (objs != null && !objs.isEmpty()) {
               Iterator itr = objs.iterator();

               while(itr.hasNext()) {
                  this.updateInverse(ctx, itr.next(), rel, rm, sm, 0);
               }
            }

         }
      }
   }

   private void updateInverse(StoreContext ctx, Object inverse, ClassMapping rel, RowManager rm, OpenJPAStateManager sm, int idx) throws SQLException {
      OpenJPAStateManager invsm = RelationStrategies.getStateManager(inverse, ctx);
      if (invsm != null) {
         ValueMapping elem = this.field.getElementMapping();
         ForeignKey fk = elem.getForeignKey();
         ColumnIO io = elem.getColumnIO();
         Column order = this.field.getOrderColumn();
         byte action;
         boolean writeable;
         boolean orderWriteable;
         if (invsm.isNew() && !invsm.isFlushed()) {
            if (sm == null || sm.isDeleted()) {
               return;
            }

            writeable = io.isAnyInsertable(fk, false);
            orderWriteable = this._orderInsert;
            action = 1;
         } else if (invsm.isDeleted()) {
            if (invsm.isFlushed() || sm == null || !sm.isDeleted()) {
               return;
            }

            writeable = true;
            orderWriteable = false;
            action = 2;
         } else {
            if (sm != null && sm.isDeleted()) {
               sm = null;
            }

            writeable = io.isAnyUpdatable(fk, sm == null);
            orderWriteable = this.field.getOrderColumnIO().isUpdatable(order, sm == null);
            action = 0;
         }

         if (writeable || orderWriteable) {
            this.assertInversable();
            Row row = rm.getRow(fk.getTable(), action, invsm, true);
            if (action == 0) {
               row.wherePrimaryKey(invsm);
            }

            if (writeable) {
               row.setForeignKey(fk, io, sm);
            }

            if (orderWriteable) {
               row.setInt(order, idx);
            }

         }
      }
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      ClassMapping cm = this.field.getElementMapping().getTypeMapping();
      return cm.toDataStoreValue(val, cm.getPrimaryKeyColumns(), store);
   }

   public Joins join(Joins joins, boolean forceOuter) {
      ValueMapping elem = this.field.getElementMapping();
      ClassMapping[] clss = elem.getIndependentTypeMappings();
      if (clss.length != 1) {
         throw RelationStrategies.unjoinable(elem);
      } else {
         return forceOuter ? joins.outerJoinRelation(this.field.getName(), elem.getForeignKey(clss[0]), clss[0], elem.getSelectSubclasses(), true, true) : joins.joinRelation(this.field.getName(), elem.getForeignKey(clss[0]), clss[0], elem.getSelectSubclasses(), true, true);
      }
   }

   private void assertInversable() {
      ValueMapping elem = this.field.getElementMapping();
      if (elem.getIndependentTypeMappings().length != 1) {
         throw RelationStrategies.uninversable(elem);
      }
   }
}
