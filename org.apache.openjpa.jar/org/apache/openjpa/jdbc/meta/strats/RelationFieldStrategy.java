package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Embeddable;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.Joinable;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Numbers;

public class RelationFieldStrategy extends AbstractFieldStrategy implements Joinable, Embeddable {
   private static final Localizer _loc = Localizer.forPackage(RelationFieldStrategy.class);
   private Boolean _fkOid = null;

   public void map(boolean adapt) {
      if (this.field.getTypeCode() == 15 && !this.field.isEmbeddedPC()) {
         this.field.getKeyMapping().getValueInfo().assertNoSchemaComponents(this.field.getKey(), !adapt);
         this.field.getElementMapping().getValueInfo().assertNoSchemaComponents(this.field.getElement(), !adapt);
         boolean criteria = this.field.getValueInfo().getUseClassCriteria();
         FieldMapping mapped = this.field.getMappedByMapping();
         FieldMapping var10001;
         if (mapped != null) {
            this.field.getMappingInfo().assertNoSchemaComponents(this.field, !adapt);
            this.field.getValueInfo().assertNoSchemaComponents(this.field, !adapt);
            mapped.resolve(1 | 2);
            if (mapped.isMapped() && !mapped.isSerialized()) {
               if (mapped.getTypeCode() == 15) {
                  if (mapped.getJoinDirection() == 0) {
                     var10001 = this.field;
                     this.field.setJoinDirection(1);
                     this.field.setColumns(mapped.getDefiningMapping().getPrimaryKeyColumns());
                  } else if (this.isTypeUnjoinedSubclass(mapped)) {
                     throw new MetaDataException(_loc.get("mapped-inverse-unjoined", this.field.getName(), this.field.getDefiningMapping(), mapped));
                  }

                  this.field.setForeignKey(mapped.getForeignKey(this.field.getDefiningMapping()));
               } else {
                  if (mapped.getElement().getTypeCode() != 15) {
                     throw new MetaDataException(_loc.get("not-inv-relation", this.field, mapped));
                  }

                  if (this.isTypeUnjoinedSubclass(mapped.getElementMapping())) {
                     throw new MetaDataException(_loc.get("mapped-inverse-unjoined", this.field.getName(), this.field.getDefiningMapping(), mapped));
                  }

                  Log log = this.field.getRepository().getLog();
                  if (log.isInfoEnabled()) {
                     log.info(_loc.get("coll-owner", this.field, mapped));
                  }

                  this.field.setForeignKey(mapped.getElementMapping().getForeignKey());
               }

               this.field.setUseClassCriteria(criteria);
            } else {
               throw new MetaDataException(_loc.get("mapped-by-unmapped", this.field, mapped));
            }
         } else {
            String tableName = this.field.getMappingInfo().getTableName();
            Table table = this.field.getTypeMapping().getTable();
            ValueMappingInfo vinfo = this.field.getValueInfo();
            if (tableName != null && table != null && (tableName.equalsIgnoreCase(table.getName()) || tableName.equalsIgnoreCase(table.getFullName()))) {
               vinfo.setJoinDirection(2);
               vinfo.setColumns(this.field.getMappingInfo().getColumns());
               this.field.getMappingInfo().setTableName((String)null);
               this.field.getMappingInfo().setColumns((List)null);
            }

            this.field.mapJoin(adapt, false);
            if (this.field.getTypeMapping().isMapped()) {
               ForeignKey fk = vinfo.getTypeJoin(this.field, this.field.getName(), true, adapt);
               this.field.setForeignKey(fk);
               this.field.setColumnIO(vinfo.getColumnIO());
               if (vinfo.getJoinDirection() == 2) {
                  var10001 = this.field;
                  this.field.setJoinDirection(1);
               }
            } else {
               RelationStrategies.mapRelationToUnmappedPC(this.field, this.field.getName(), adapt);
            }

            this.field.setUseClassCriteria(criteria);
            this.field.mapPrimaryKey(adapt);
            PrimaryKey pk = this.field.getTable().getPrimaryKey();
            if (this.field.isPrimaryKey()) {
               Column[] cols = this.field.getColumns();
               int i;
               if (pk != null && (adapt || pk.isLogical())) {
                  for(i = 0; i < cols.length; ++i) {
                     pk.addColumn(cols[i]);
                  }
               }

               for(i = 0; i < cols.length; ++i) {
                  this.field.getDefiningMapping().setJoinable(cols[i], this);
               }
            }

            this.field.mapConstraints(this.field.getName(), adapt);
         }
      } else {
         throw new MetaDataException(_loc.get("not-relation", (Object)this.field));
      }
   }

   private boolean isTypeUnjoinedSubclass(ValueMapping mapped) {
      for(ClassMapping def = this.field.getDefiningMapping(); def != null; def = def.getJoinablePCSuperclassMapping()) {
         if (def == mapped.getTypeMapping()) {
            return false;
         }
      }

      return true;
   }

   public void initialize() {
      this.field.setUsesIntermediate(true);
      ForeignKey fk = this.field.getForeignKey();
      if (fk == null) {
         this._fkOid = Boolean.TRUE;
      } else if (this.field.getJoinDirection() != 1) {
         this._fkOid = this.field.getTypeMapping().isForeignKeyObjectId(fk);
      }

   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null) {
         OpenJPAStateManager rel = RelationStrategies.getStateManager(sm.fetchObjectField(this.field.getIndex()), store.getContext());
         int var10000 = this.field.getJoinDirection();
         FieldMapping var10001 = this.field;
         if (var10000 == 1) {
            this.updateInverse(sm, rel, store, rm);
         } else {
            Row row = this.field.getRow(sm, store, rm, 1);
            if (row != null) {
               this.field.setForeignKey(row, rel);
            }
         }

      }
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null) {
         OpenJPAStateManager rel = RelationStrategies.getStateManager(sm.fetchObjectField(this.field.getIndex()), store.getContext());
         int var10000 = this.field.getJoinDirection();
         FieldMapping var10001 = this.field;
         if (var10000 == 1) {
            this.nullInverse(sm, rm);
            this.updateInverse(sm, rel, store, rm);
         } else {
            Row row = this.field.getRow(sm, store, rm, 0);
            if (row != null) {
               this.field.setForeignKey(row, rel);
            }
         }

      }
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this.field.getMappedBy() == null) {
         int var10000 = this.field.getJoinDirection();
         FieldMapping var10001 = this.field;
         OpenJPAStateManager rel;
         if (var10000 == 1) {
            if (sm.getLoaded().get(this.field.getIndex())) {
               rel = RelationStrategies.getStateManager(sm.fetchObjectField(this.field.getIndex()), store.getContext());
               this.updateInverse(sm, rel, store, rm);
            } else {
               this.nullInverse(sm, rm);
            }
         } else {
            this.field.deleteRow(sm, store, rm);
            rel = RelationStrategies.getStateManager(sm.fetchObjectField(this.field.getIndex()), store.getContext());
            if (rel != null) {
               ForeignKey fk = this.field.getForeignKey((ClassMapping)rel.getMetaData());
               if (fk.getDeleteAction() == 2 || fk.getDeleteAction() == 3) {
                  Row row = this.field.getRow(sm, store, rm, 2);
                  row.setForeignKey(fk, (ColumnIO)null, rel);
               }
            }
         }

      }
   }

   private void nullInverse(OpenJPAStateManager sm, RowManager rm) throws SQLException {
      if (!this.field.getUseClassCriteria()) {
         ForeignKey fk = this.field.getForeignKey();
         ColumnIO io = this.field.getColumnIO();
         if (io.isAnyUpdatable(fk, true)) {
            if (this.field.getIndependentTypeMappings().length != 1) {
               throw RelationStrategies.uninversable(this.field);
            } else {
               Row row = rm.getAllRows(fk.getTable(), 0);
               row.setForeignKey(fk, io, (OpenJPAStateManager)null);
               row.whereForeignKey(fk, sm);
               rm.flushAllRows(row);
            }
         }
      }
   }

   private void updateInverse(OpenJPAStateManager sm, OpenJPAStateManager rel, JDBCStore store, RowManager rm) throws SQLException {
      if (rel != null) {
         ForeignKey fk = this.field.getForeignKey();
         ColumnIO io = this.field.getColumnIO();
         byte action;
         if (rel.isNew() && !rel.isFlushed()) {
            if (sm.isDeleted() || !io.isAnyInsertable(fk, false)) {
               return;
            }

            action = 1;
         } else if (rel.isDeleted()) {
            if (rel.isFlushed() || !sm.isDeleted()) {
               return;
            }

            action = 2;
         } else {
            if (sm.isDeleted()) {
               sm = null;
            }

            if (!io.isAnyUpdatable(fk, sm == null)) {
               return;
            }

            action = 0;
         }

         if (this.field.getIndependentTypeMappings().length != 1) {
            throw RelationStrategies.uninversable(this.field);
         } else {
            Row row = null;
            FieldMapping[] invs = this.field.getInverseMappings();

            for(int i = 0; i < invs.length; ++i) {
               if (invs[i].getMappedByMetaData() == this.field && invs[i].getTypeCode() == 15) {
                  row = invs[i].getRow(rel, store, rm, action);
                  break;
               }
            }

            ClassMapping relMapping = this.field.getTypeMapping();
            if (row == null) {
               row = rm.getRow(relMapping.getTable(), action, rel, true);
            }

            if (action == 0 && row.getTable() == relMapping.getTable()) {
               row.wherePrimaryKey(rel);
            }

            row.setForeignKey(fk, io, sm);
         }
      }
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      if (type == 3) {
         int var10000 = this.field.getJoinDirection();
         FieldMapping var10001 = this.field;
         return var10000 != 1 && sel.isSelected(this.field.getTable()) ? 1 : 0;
      } else if (type == 4) {
         return 1;
      } else {
         if (sm != null) {
            Object oid = sm.getIntermediate(this.field.getIndex());
            if (store.getContext().findCached(oid, (FindCallbacks)null) != null) {
               return 0;
            }
         }

         ClassMapping[] clss = this.field.getIndependentTypeMappings();
         switch (type) {
            case 0:
               return clss.length == 1 ? 1 : 0;
            case 1:
               return clss.length == 1 && store.getDBDictionary().canOuterJoin(sel.getJoinSyntax(), this.field.getForeignKey(clss[0])) ? 1 : 0;
            case 2:
               return clss.length;
            default:
               return 0;
         }
      }
   }

   public void selectEagerParallel(SelectExecutor sel, OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch, final int eagerMode) {
      final ClassMapping[] clss = this.field.getIndependentTypeMappings();
      if (!(sel instanceof Union)) {
         this.selectEagerParallel((Select)sel, clss[0], store, fetch, eagerMode);
      } else {
         Union union = (Union)sel;
         if (fetch.getSubclassFetchMode(this.field.getTypeMapping()) != 1) {
            union.abortUnion();
         }

         union.select(new Union.Selector() {
            public void select(Select sel, int idx) {
               RelationFieldStrategy.this.selectEagerParallel(sel, clss[idx], store, fetch, eagerMode);
            }
         });
      }

   }

   private void selectEagerParallel(Select sel, ClassMapping cls, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      sel.selectPrimaryKey(this.field.getDefiningMapping());
      Joins joins = sel.newJoins().setVariable("*");
      this.eagerJoin(joins, cls, true);
      sel.select(cls, this.field.getSelectSubclasses(), store, fetch, eagerMode, joins);
   }

   public void selectEagerJoin(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      ClassMapping cls = this.field.getIndependentTypeMappings()[0];
      boolean forceInner = fetch.hasFetchInnerJoin(this.field.getFullName(false));
      sel.select(cls, this.field.getSelectSubclasses(), store, fetch, 1, this.eagerJoin(sel.newJoins(), cls, forceInner));
   }

   private Joins eagerJoin(Joins joins, ClassMapping cls, boolean forceInner) {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      boolean inverse = var10000 == 1;
      if (!inverse) {
         joins = this.join(joins, false);
         joins = this.setEmbeddedVariable(joins);
      }

      ForeignKey fk = this.field.getForeignKey(cls);
      return !forceInner && this.field.getNullValue() != 2 ? joins.outerJoinRelation(this.field.getName(), fk, this.field.getTypeMapping(), this.field.getSelectSubclasses(), inverse, false) : joins.joinRelation(this.field.getName(), fk, this.field.getTypeMapping(), this.field.getSelectSubclasses(), inverse, false);
   }

   private Joins setEmbeddedVariable(Joins joins) {
      return this.field.getDefiningMetaData().getEmbeddingMetaData() == null ? joins : joins.setVariable(this.field.getDefiningMetaData().getEmbeddingMetaData().getFieldMetaData().getName());
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      if (var10000 == 1) {
         return -1;
      } else if (sm != null && sm.getIntermediate(this.field.getIndex()) != null) {
         return -1;
      } else if (!Boolean.TRUE.equals(this._fkOid)) {
         return -1;
      } else {
         sel.select(this.field.getColumns(), this.field.join(sel));
         return 0;
      }
   }

   public Object loadEagerParallel(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object res) throws SQLException {
      Map rels;
      if (res instanceof Result) {
         rels = this.processEagerParallelResult(sm, store, fetch, (Result)res);
      } else {
         rels = (Map)res;
      }

      sm.storeObject(this.field.getIndex(), rels.remove(sm.getObjectId()));
      return rels;
   }

   private Map processEagerParallelResult(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      ClassMapping[] clss = this.field.getIndependentTypeMappings();
      Joins joins = res.newJoins().setVariable("*");
      this.eagerJoin(joins, clss[0], true);
      Map rels = new HashMap();
      ClassMapping owner = this.field.getDefiningMapping();

      while(res.next()) {
         ClassMapping cls = res.getBaseMapping();
         if (cls == null) {
            cls = clss[0];
         }

         Object oid = owner.getObjectId(store, res, (ForeignKey)null, true, (Joins)null);
         rels.put(oid, res.load(cls, store, fetch, joins));
      }

      res.close();
      return rels;
   }

   public void loadEagerJoin(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      ClassMapping cls = this.field.getIndependentTypeMappings()[0];
      sm.storeObject(this.field.getIndex(), res.load(cls, store, fetch, this.eagerJoin(res.newJoins(), cls, false)));
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      if (var10000 != 1) {
         if (sm == null || sm.getIntermediate(this.field.getIndex()) == null) {
            if (Boolean.TRUE.equals(this._fkOid)) {
               if (res.containsAll(this.field.getColumns())) {
                  ClassMapping relMapping = this.field.getTypeMapping();
                  Object oid = null;
                  if (relMapping.isMapped()) {
                     oid = relMapping.getObjectId(store, res, this.field.getForeignKey(), this.field.getPolymorphic() != 1, (Joins)null);
                  } else {
                     Column[] cols = this.field.getColumns();
                     if (relMapping.getIdentityType() == 1) {
                        long id = res.getLong(cols[0]);
                        if (!res.wasNull()) {
                           oid = store.newDataStoreId(id, relMapping, true);
                        }
                     } else if (cols.length == 1) {
                        Object val = res.getObject(cols[0], (Object)null, (Joins)null);
                        if (val != null) {
                           oid = ApplicationIds.fromPKValues(new Object[]{val}, relMapping);
                        }
                     } else {
                        Object[] vals = new Object[cols.length];

                        for(int i = 0; i < cols.length; ++i) {
                           vals[i] = res.getObject(cols[i], (Object)null, (Joins)null);
                           if (vals[i] == null) {
                              break;
                           }

                           if (i == cols.length - 1) {
                              oid = ApplicationIds.fromPKValues(vals, relMapping);
                           }
                        }
                     }
                  }

                  if (oid == null) {
                     sm.storeObject(this.field.getIndex(), (Object)null);
                  } else {
                     sm.setIntermediate(this.field.getIndex(), oid);
                  }

               }
            }
         }
      }
   }

   public void load(final OpenJPAStateManager sm, final JDBCStore store, final JDBCFetchConfiguration fetch) throws SQLException {
      if (Boolean.TRUE.equals(this._fkOid)) {
         Object oid = sm.getIntermediate(this.field.getIndex());
         if (oid != null) {
            Object val = store.find(oid, this.field, fetch);
            sm.storeObject(this.field.getIndex(), val);
            return;
         }
      }

      final ClassMapping[] rels = this.field.getIndependentTypeMappings();
      final int subs = this.field.getSelectSubclasses();
      final Joins[] resJoins = new Joins[rels.length];
      Union union = store.getSQLFactory().newUnion(rels.length);
      union.setExpectedResultCount(1, false);
      if (fetch.getSubclassFetchMode(this.field.getTypeMapping()) != 1) {
         union.abortUnion();
      }

      union.select(new Union.Selector() {
         public void select(Select sel, int idx) {
            int var10000 = RelationFieldStrategy.this.field.getJoinDirection();
            FieldMapping var10001 = RelationFieldStrategy.this.field;
            if (var10000 == 1) {
               sel.whereForeignKey(RelationFieldStrategy.this.field.getForeignKey(rels[idx]), sm.getObjectId(), RelationFieldStrategy.this.field.getDefiningMapping(), store);
            } else {
               resJoins[idx] = sel.newJoins().joinRelation(RelationFieldStrategy.this.field.getName(), RelationFieldStrategy.this.field.getForeignKey(rels[idx]), rels[idx], RelationFieldStrategy.this.field.getSelectSubclasses(), false, false);
               RelationFieldStrategy.this.field.wherePrimaryKey(sel, sm, store);
            }

            ClassMapping var3 = rels[idx];
            JDBCFetchConfiguration var10005 = fetch;
            sel.select(var3, subs, store, fetch, 1, resJoins[idx]);
         }
      });
      Result res = union.execute(store, fetch);

      try {
         Object val = null;
         if (res.next()) {
            val = res.load(rels[res.indexOf()], store, fetch, resJoins[res.indexOf()]);
         }

         sm.storeObject(this.field.getIndex(), val);
      } finally {
         res.close();
      }

   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return RelationStrategies.toDataStoreValue(this.field, val, store);
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      if (var10000 != 1) {
         joins = this.join(joins, false);
         Column[] cols = this.field.getColumns();
         if (cols.length == 0) {
            sql.append("1 <> 1");
         } else {
            sql.append(sel.getColumnAlias(cols[0], joins)).append(" IS ").appendValue((Object)null, cols[0]);
         }
      } else {
         this.testInverseNull(sql, sel, joins, true);
      }

   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      if (var10000 != 1) {
         joins = this.join(joins, false);
         Column[] cols = this.field.getColumns();
         if (cols.length == 0) {
            sql.append("1 = 1");
         } else {
            sql.append(sel.getColumnAlias(cols[0], joins)).append(" IS NOT ").appendValue((Object)null, cols[0]);
         }
      } else {
         this.testInverseNull(sql, sel, joins, false);
      }

   }

   private void testInverseNull(SQLBuffer sql, Select sel, Joins joins, boolean empty) {
      DBDictionary dict = this.field.getMappingRepository().getDBDictionary();
      dict.assertSupport(dict.supportsSubselect, "SupportsSubselect");
      if (this.field.getIndependentTypeMappings().length != 1) {
         throw RelationStrategies.uninversable(this.field);
      } else {
         if (empty) {
            sql.append("0 = ");
         } else {
            sql.append("0 < ");
         }

         ForeignKey fk = this.field.getForeignKey();
         ContainerFieldStrategy.appendJoinCount(sql, sel, joins, dict, this.field, fk);
      }
   }

   public Joins join(Joins joins, boolean forceOuter) {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      if (var10000 != 1) {
         return this.field.join(joins, forceOuter, false);
      } else {
         ClassMapping[] clss = this.field.getIndependentTypeMappings();
         if (clss.length != 1) {
            throw RelationStrategies.uninversable(this.field);
         } else {
            return forceOuter ? joins.outerJoinRelation(this.field.getName(), this.field.getForeignKey(), clss[0], this.field.getSelectSubclasses(), true, false) : joins.joinRelation(this.field.getName(), this.field.getForeignKey(), clss[0], this.field.getSelectSubclasses(), true, false);
         }
      }
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      int var10000 = this.field.getJoinDirection();
      FieldMapping var10001 = this.field;
      if (var10000 == 1) {
         return joins;
      } else {
         ClassMapping[] clss = this.field.getIndependentTypeMappings();
         if (clss.length != 1) {
            if (traverse) {
               throw RelationStrategies.unjoinable(this.field);
            } else {
               return joins;
            }
         } else {
            joins = this.setEmbeddedVariable(joins);
            return forceOuter ? joins.outerJoinRelation(this.field.getName(), this.field.getForeignKey(clss[0]), clss[0], this.field.getSelectSubclasses(), false, false) : joins.joinRelation(this.field.getName(), this.field.getForeignKey(clss[0]), clss[0], this.field.getSelectSubclasses(), false, false);
         }
      }
   }

   public int getFieldIndex() {
      return this.field.getIndex();
   }

   public Object getPrimaryKeyValue(Result res, Column[] cols, ForeignKey fk, JDBCStore store, Joins joins) throws SQLException {
      ClassMapping relmapping = this.field.getTypeMapping();
      if (relmapping.getIdentityType() == 1) {
         Column col = cols[0];
         if (fk != null) {
            col = fk.getColumn(col);
         }

         long id = res.getLong(col, joins);
         return this.field.getObjectIdFieldTypeCode() == 6 ? Numbers.valueOf(id) : store.newDataStoreId(id, relmapping, this.field.getPolymorphic() != 1);
      } else if (relmapping.isOpenJPAIdentity()) {
         return ((Joinable)relmapping.getPrimaryKeyFieldMappings()[0].getStrategy()).getPrimaryKeyValue(res, cols, fk, store, joins);
      } else {
         if (cols == this.getColumns() && fk == null) {
            fk = this.field.getForeignKey();
         } else {
            fk = this.createTranslatingForeignKey(relmapping, cols, fk);
         }

         return relmapping.getObjectId(store, res, fk, this.field.getPolymorphic() != 1, joins);
      }
   }

   private ForeignKey createTranslatingForeignKey(ClassMapping relmapping, Column[] gcols, ForeignKey gfk) {
      ForeignKey fk = this.field.getForeignKey();
      Column[] cols = fk.getColumns();
      ForeignKey tfk = null;

      for(int i = 0; i < gcols.length; ++i) {
         Column tcol = gcols[i];
         if (gfk != null) {
            tcol = gfk.getColumn(tcol);
         }

         if (tfk == null) {
            tfk = new ForeignKey((String)null, tcol.getTable());
         }

         tfk.join(tcol, fk.getPrimaryKeyColumn(cols[i]));
      }

      return tfk;
   }

   public Object getJoinValue(Object fieldVal, Column col, JDBCStore store) {
      Object o = this.field.getForeignKey().getConstant(col);
      if (o != null) {
         return o;
      } else {
         col = this.field.getForeignKey().getPrimaryKeyColumn(col);
         if (col == null) {
            throw new InternalException();
         } else {
            ClassMapping relmapping = this.field.getTypeMapping();
            Joinable j = this.field.getTypeMapping().assertJoinable(col);
            if (ImplHelper.isManageable(fieldVal)) {
               fieldVal = store.getContext().getObjectId(fieldVal);
            }

            if (fieldVal instanceof OpenJPAId) {
               fieldVal = ((OpenJPAId)fieldVal).getIdObject();
            } else if (relmapping.getObjectIdType() != null && relmapping.getObjectIdType().isInstance(fieldVal)) {
               Object[] pks = ApplicationIds.toPKValues(fieldVal, relmapping);
               fieldVal = pks[relmapping.getField(j.getFieldIndex()).getPrimaryKeyIndex()];
            }

            return j.getJoinValue(fieldVal, col, store);
         }
      }
   }

   public Object getJoinValue(OpenJPAStateManager sm, Column col, JDBCStore store) {
      return this.getJoinValue(sm.fetch(this.field.getIndex()), col, store);
   }

   public void setAutoAssignedValue(OpenJPAStateManager sm, JDBCStore store, Column col, Object autoInc) {
      throw new UnsupportedException();
   }

   public Column[] getColumns() {
      return this.field.getColumns();
   }

   public ColumnIO getColumnIO() {
      return this.field.getColumnIO();
   }

   public Object[] getResultArguments() {
      return null;
   }

   public Object toEmbeddedDataStoreValue(Object val, JDBCStore store) {
      return this.toDataStoreValue(val, store);
   }

   public Object toEmbeddedObjectValue(Object val) {
      return UNSUPPORTED;
   }

   public void loadEmbedded(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object val) throws SQLException {
      ClassMapping relMapping = this.field.getTypeMapping();
      Object oid;
      if (val == null) {
         oid = null;
      } else if (relMapping.getIdentityType() == 1) {
         oid = store.newDataStoreId(((Number)val).longValue(), relMapping, this.field.getPolymorphic() != 1);
      } else {
         Object[] pks = this.getColumns().length == 1 ? new Object[]{val} : (Object[])((Object[])val);
         boolean nulls = true;

         for(int i = 0; nulls && i < pks.length; ++i) {
            nulls = pks[i] == null;
         }

         if (nulls) {
            oid = null;
         } else {
            oid = ApplicationIds.fromPKValues(pks, relMapping);
            if (this.field.getPolymorphic() == 1 && oid instanceof OpenJPAId) {
               ((OpenJPAId)oid).setManagedInstanceType(relMapping.getDescribedType());
            }
         }
      }

      if (oid == null) {
         sm.storeObject(this.field.getIndex(), (Object)null);
      } else {
         sm.setIntermediate(this.field.getIndex(), oid);
      }

   }
}
