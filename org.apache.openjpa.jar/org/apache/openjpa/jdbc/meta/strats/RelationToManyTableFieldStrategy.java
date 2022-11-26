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
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxies;
import org.apache.openjpa.util.Proxy;

public abstract class RelationToManyTableFieldStrategy extends StoreCollectionFieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(RelationToManyTableFieldStrategy.class);

   protected ClassMapping[] getIndependentElementMappings(boolean traverse) {
      return traverse ? this.field.getElementMapping().getIndependentTypeMappings() : ClassMapping.EMPTY_MAPPINGS;
   }

   protected ForeignKey getJoinForeignKey(ClassMapping elem) {
      return this.field.getJoinForeignKey();
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
      return this.join(joins, false);
   }

   protected Joins joinElementRelation(Joins joins, ClassMapping elem) {
      ValueMapping vm = this.field.getElementMapping();
      return joins.joinRelation(this.field.getName(), vm.getForeignKey(elem), elem, vm.getSelectSubclasses(), false, false);
   }

   public void map(boolean adapt) {
      this.field.getValueInfo().assertNoSchemaComponents(this.field, !adapt);
      this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
      ValueMapping elem = this.field.getElementMapping();
      if (elem.getTypeCode() == 15 && !elem.isEmbeddedPC()) {
         FieldMapping mapped = this.field.getMappedByMapping();
         ValueMappingInfo vinfo = elem.getValueInfo();
         boolean criteria = vinfo.getUseClassCriteria();
         if (mapped != null) {
            if (mapped.getElement().getTypeCode() != 15) {
               throw new MetaDataException(_loc.get("not-inv-relation-coll", this.field, mapped));
            } else {
               this.field.getMappingInfo().assertNoSchemaComponents(this.field, !adapt);
               vinfo.assertNoSchemaComponents(elem, !adapt);
               mapped.resolve(1 | 2);
               if (mapped.isMapped() && !mapped.isSerialized()) {
                  this.field.setJoinForeignKey(mapped.getElementMapping().getForeignKey(this.field.getDefiningMapping()));
                  elem.setForeignKey(mapped.getJoinForeignKey());
                  elem.setUseClassCriteria(criteria);
                  this.field.setOrderColumn(mapped.getOrderColumn());
               } else {
                  throw new MetaDataException(_loc.get("mapped-by-unmapped", this.field, mapped));
               }
            }
         } else {
            this.field.mapJoin(adapt, true);
            if (elem.getTypeMapping().isMapped()) {
               ForeignKey fk = vinfo.getTypeJoin(elem, "element", false, adapt);
               elem.setForeignKey(fk);
               elem.setColumnIO(vinfo.getColumnIO());
            } else {
               RelationStrategies.mapRelationToUnmappedPC(elem, "element", adapt);
            }

            elem.setUseClassCriteria(criteria);
            elem.mapConstraints("element", adapt);
            FieldMappingInfo finfo = this.field.getMappingInfo();
            Column orderCol = finfo.getOrderColumn(this.field, this.field.getTable(), adapt);
            this.field.setOrderColumn(orderCol);
            this.field.setOrderColumnIO(finfo.getColumnIO());
            this.field.mapPrimaryKey(adapt);
         }
      } else {
         throw new MetaDataException(_loc.get("not-elem-relation", (Object)this.field));
      }
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null) {
         this.insert(sm, rm, sm.fetchObject(this.field.getIndex()));
      }

   }

   private void insert(OpenJPAStateManager sm, RowManager rm, Object vals) throws SQLException {
      Collection coll = this.toCollection(vals);
      if (coll != null && !coll.isEmpty()) {
         Row row = rm.getSecondaryRow(this.field.getTable(), 1);
         row.setForeignKey(this.field.getJoinForeignKey(), this.field.getJoinColumnIO(), sm);
         ValueMapping elem = this.field.getElementMapping();
         StoreContext ctx = sm.getContext();
         Column order = this.field.getOrderColumn();
         boolean setOrder = this.field.getOrderColumnIO().isInsertable(order, false);
         int idx = 0;

         for(Iterator itr = coll.iterator(); itr.hasNext(); ++idx) {
            OpenJPAStateManager esm = RelationStrategies.getStateManager(itr.next(), ctx);
            elem.setForeignKey(row, esm);
            if (setOrder) {
               row.setInt(order, idx);
            }

            rm.flushSecondaryRow(row);
         }

      }
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null) {
         Object obj = sm.fetchObject(this.field.getIndex());
         ChangeTracker ct = null;
         if (obj instanceof Proxy) {
            Proxy proxy = (Proxy)obj;
            if (Proxies.isOwner(proxy, sm, this.field.getIndex())) {
               ct = proxy.getChangeTracker();
            }
         }

         if (ct != null && ct.isTracking()) {
            StoreContext ctx = store.getContext();
            ValueMapping elem = this.field.getElementMapping();
            Collection rem = ct.getRemoved();
            OpenJPAStateManager esm;
            if (!rem.isEmpty()) {
               Row delRow = rm.getSecondaryRow(this.field.getTable(), 2);
               delRow.whereForeignKey(this.field.getJoinForeignKey(), sm);
               Iterator itr = rem.iterator();

               while(itr.hasNext()) {
                  esm = RelationStrategies.getStateManager(itr.next(), ctx);
                  elem.whereForeignKey(delRow, esm);
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
                  esm = RelationStrategies.getStateManager(itr.next(), ctx);
                  elem.setForeignKey(addRow, esm);
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
            this.insert(sm, rm, obj);
         }
      }
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getAllRows(this.field.getTable(), 2);
      row.whereForeignKey(this.field.getJoinForeignKey(), sm);
      rm.flushAllRows(row);
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return RelationStrategies.toDataStoreValue(this.field.getElementMapping(), val, store);
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.field.join(joins, forceOuter, true);
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      ValueMapping elem = this.field.getElementMapping();
      ClassMapping[] clss = elem.getIndependentTypeMappings();
      if (clss.length != 1) {
         if (traverse) {
            throw RelationStrategies.unjoinable(elem);
         } else {
            return joins;
         }
      } else {
         return forceOuter ? joins.outerJoinRelation(this.field.getName(), elem.getForeignKey(clss[0]), clss[0], elem.getSelectSubclasses(), false, false) : joins.joinRelation(this.field.getName(), elem.getForeignKey(clss[0]), clss[0], elem.getSelectSubclasses(), false, false);
      }
   }
}
