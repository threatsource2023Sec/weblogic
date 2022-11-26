package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.strats.NoneFieldStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;

public class FieldMapping extends FieldMetaData implements ValueMapping, FieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(FieldMapping.class);
   private final ValueMapping _val;
   private final ValueMapping _key;
   private final ValueMapping _elem;
   private final FieldMappingInfo _info;
   private final JDBCColumnOrder _orderCol = new JDBCColumnOrder();
   private FieldStrategy _strategy = null;
   private ForeignKey _fk = null;
   private ColumnIO _io = null;
   private Unique _unq = null;
   private Index _idx = null;
   private boolean _outer = false;
   private int _fetchMode = Integer.MAX_VALUE;

   public FieldMapping(String name, Class type, ClassMapping owner) {
      super(name, type, owner);
      this._info = owner.getMappingRepository().newMappingInfo(this);
      this._val = (ValueMapping)this.getValue();
      this._key = (ValueMapping)this.getKey();
      this._elem = (ValueMapping)this.getElement();
      this.setUsesIntermediate(false);
      this.setUsesImplData(Boolean.FALSE);
   }

   public FieldMappingInfo getMappingInfo() {
      return this._info;
   }

   public FieldStrategy getStrategy() {
      return this._strategy;
   }

   public void setStrategy(FieldStrategy strategy, Boolean adapt) {
      FieldStrategy orig = this._strategy;
      this._strategy = strategy;
      if (strategy != null) {
         try {
            strategy.setFieldMapping(this);
            if (adapt != null) {
               strategy.map(adapt);
            }
         } catch (RuntimeException var5) {
            this._strategy = orig;
            throw var5;
         }

         if (!this.isMapped()) {
            this.getDefiningMapping().clearDefinedFieldCache();
         }
      }

   }

   public Table getTable() {
      if (this._fk != null) {
         return this._fk.getTable();
      } else {
         return this._val.getForeignKey() != null ? this._val.getForeignKey().getTable() : this.getDefiningMapping().getTable();
      }
   }

   public ColumnIO getJoinColumnIO() {
      return this._io == null ? ColumnIO.UNRESTRICTED : this._io;
   }

   public void setJoinColumnIO(ColumnIO io) {
      this._io = io;
   }

   public ForeignKey getJoinForeignKey() {
      return this._fk;
   }

   public void setJoinForeignKey(ForeignKey fk) {
      this._fk = fk;
   }

   public Unique getJoinUnique() {
      return this._unq;
   }

   public void setJoinUnique(Unique unq) {
      this._unq = unq;
   }

   public Index getJoinIndex() {
      return this._idx;
   }

   public void setJoinIndex(Index idx) {
      this._idx = idx;
   }

   public boolean isJoinOuter() {
      return this._outer;
   }

   public void setJoinOuter(boolean outer) {
      this._outer = outer;
   }

   public Column getOrderColumn() {
      return this._orderCol.getColumn();
   }

   public void setOrderColumn(Column order) {
      this._orderCol.setColumn(order);
   }

   public ColumnIO getOrderColumnIO() {
      return this._orderCol.getColumnIO();
   }

   public void setOrderColumnIO(ColumnIO io) {
      this._orderCol.setColumnIO(io);
   }

   public void refSchemaComponents() {
      if (this._fk != null) {
         this._fk.ref();
         this._fk.refColumns();
      }

      if (this._orderCol.getColumn() != null) {
         this._orderCol.getColumn().ref();
      }

      this._val.refSchemaComponents();
      this._key.refSchemaComponents();
      this._elem.refSchemaComponents();
   }

   public void clearMapping() {
      this._strategy = null;
      this._fk = null;
      this._unq = null;
      this._idx = null;
      this._outer = false;
      this._orderCol.setColumn((Column)null);
      this._val.clearMapping();
      this._key.clearMapping();
      this._elem.clearMapping();
      this._info.clear();
      this.setResolve(2, false);
   }

   public void syncMappingInfo() {
      if (!this.isVersion()) {
         if (this.getMappedByMapping() != null) {
            this._info.clear();
            this._val.getValueInfo().clear();
            this._key.getValueInfo().clear();
            this._elem.getValueInfo().clear();
            FieldMapping mapped = this.getMappedByMapping();
            this._info.syncStrategy(this);
            if (this._orderCol.getColumn() != null && mapped.getOrderColumn() == null) {
               this._info.syncOrderColumn(this);
            }

            this._val.getValueInfo().setUseClassCriteria(this._val.getUseClassCriteria());
            this._key.getValueInfo().setUseClassCriteria(this._key.getUseClassCriteria());
            this._elem.getValueInfo().setUseClassCriteria(this._elem.getUseClassCriteria());
         } else {
            this._info.syncWith(this);
            this._val.syncMappingInfo();
            this._key.syncMappingInfo();
            this._elem.syncMappingInfo();
         }
      }

   }

   public boolean isMapped() {
      return this._strategy != NoneFieldStrategy.getInstance();
   }

   public int getEagerFetchMode() {
      if (this._fetchMode == Integer.MAX_VALUE) {
         this._fetchMode = -99;
      }

      return this._fetchMode;
   }

   public void setEagerFetchMode(int mode) {
      this._fetchMode = mode;
   }

   public MappingRepository getMappingRepository() {
      return (MappingRepository)this.getRepository();
   }

   public ClassMapping getDefiningMapping() {
      return (ClassMapping)this.getDefiningMetaData();
   }

   public ClassMapping getDeclaringMapping() {
      return (ClassMapping)this.getDeclaringMetaData();
   }

   public ValueMapping getKeyMapping() {
      return this._key;
   }

   public ValueMapping getElementMapping() {
      return this._elem;
   }

   public ValueMapping getValueMapping() {
      return (ValueMapping)this.getValue();
   }

   public FieldMapping getMappedByMapping() {
      return (FieldMapping)this.getMappedByMetaData();
   }

   public FieldMapping[] getInverseMappings() {
      return (FieldMapping[])((FieldMapping[])this.getInverseMetaDatas());
   }

   public boolean resolve(int mode) {
      int cur = this.getResolve();
      if (super.resolve(mode)) {
         return true;
      } else {
         if ((mode & 2) != 0 && (cur & 2) == 0) {
            this.resolveMapping();
         }

         if ((mode & 8) != 0 && (cur & 8) == 0) {
            this.initializeMapping();
         }

         return false;
      }
   }

   private void resolveMapping() {
      MappingRepository repos = this.getMappingRepository();
      if (repos.getMappingDefaults().defaultMissingInfo()) {
         ClassMapping cls = this.getDefiningMapping();
         if (cls.getEmbeddingMapping() != null) {
            ClassMapping orig = repos.getMapping(cls.getDescribedType(), cls.getEnvClassLoader(), true);
            FieldMapping tmplate = orig.getFieldMapping(this.getName());
            if (tmplate != null) {
               this.copyMappingInfo(tmplate);
            }
         } else if (cls.isMapped() && cls.getPCSuperclass() != null && cls.getDescribedType() != this.getDeclaringType()) {
            FieldMapping sup = cls.getPCSuperclassMapping().getFieldMapping(this.getName());
            if (sup != null) {
               this.copyMappingInfo(sup);
            }
         }
      }

      if (this._strategy == null) {
         if (this.isVersion()) {
            this._strategy = NoneFieldStrategy.getInstance();
         } else {
            repos.getStrategyInstaller().installStrategy(this);
         }
      }

      Log log = this.getRepository().getLog();
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("field-strategy", this.getName(), this._strategy.getAlias()));
      }

      if (this._orderCol.getColumn() != null) {
         if (this.getOrderColumnIO().isInsertable(0, false)) {
            this._orderCol.getColumn().setFlag(8, true);
         }

         if (this.getOrderColumnIO().isUpdatable(0, false)) {
            this._orderCol.getColumn().setFlag(16, true);
         }
      }

      if (this._fk != null) {
         Column[] cols = this._fk.getColumns();
         ColumnIO io = this.getJoinColumnIO();

         for(int i = 0; i < cols.length; ++i) {
            if (io.isInsertable(i, false)) {
               cols[i].setFlag(32, true);
            }

            if (io.isUpdatable(i, false)) {
               cols[i].setFlag(64, true);
            }
         }
      }

      this._val.resolve(2);
      this._key.resolve(2);
      this._elem.resolve(2);
   }

   public void copyMappingInfo(FieldMapping fm) {
      this.setMappedBy(fm.getMappedBy());
      this._info.copy(fm.getMappingInfo());
      this._val.copyMappingInfo(fm.getValueMapping());
      this._key.copyMappingInfo(fm.getKeyMapping());
      this._elem.copyMappingInfo(fm.getElementMapping());
   }

   private void initializeMapping() {
      this._val.resolve(8);
      this._key.resolve(8);
      this._val.resolve(8);
      if (this._strategy != null) {
         this._strategy.initialize();
      }

   }

   public void copy(FieldMetaData fmd) {
      super.copy(fmd);
      if (this._fetchMode == Integer.MAX_VALUE) {
         this._fetchMode = ((FieldMapping)fmd).getEagerFetchMode();
      }

   }

   protected boolean validateDataStoreExtensionPrefix(String prefix) {
      return "jdbc-".equals(prefix);
   }

   public String getAlias() {
      return this.assertStrategy().getAlias();
   }

   public void map(boolean adapt) {
      this.assertStrategy().map(adapt);
   }

   public void mapJoin(boolean adapt, boolean joinRequired) {
      Table table = this._info.getTable(this, joinRequired, adapt);
      if (table != null && table.equals(this.getDefiningMapping().getTable())) {
         table = null;
      }

      ForeignKey join = null;
      if (table != null) {
         join = this._info.getJoin(this, table, adapt);
      }

      if (join == null && joinRequired) {
         throw new MetaDataException(_loc.get("join-required", (Object)this));
      } else {
         if (join == null) {
            this._info.assertNoJoin(this, true);
            this._info.assertNoForeignKey(this, !adapt);
            this._info.assertNoUnique(this, !adapt);
            this._info.assertNoIndex(this, !adapt);
         } else {
            this._fk = join;
            this._io = this._info.getColumnIO();
            this._outer = this._info.isJoinOuter();
            this._unq = this._info.getJoinUnique(this, false, adapt);
            this._idx = this._info.getJoinIndex(this, adapt);
         }

      }
   }

   public void mapPrimaryKey(boolean adapt) {
      if (adapt && this._fk != null && this._fk.getTable().getPrimaryKey() == null) {
         this.getMappingRepository().getMappingDefaults().installPrimaryKey(this, this._fk.getTable());
      }

   }

   public void initialize() {
      this.assertStrategy().initialize();
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.assertStrategy().insert(sm, store, rm);
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.assertStrategy().update(sm, store, rm);
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      this.assertStrategy().delete(sm, store, rm);
   }

   public void deleteRow(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      if (this._fk != null) {
         Row row = rm.getRow(this.getTable(), 2, sm, true);
         row.whereForeignKey(this._fk, sm);
      }

   }

   public Row getRow(OpenJPAStateManager sm, JDBCStore store, RowManager rm, int action) throws SQLException {
      Row row = null;
      boolean newOuterRow = false;
      if (this._fk != null && this._outer && action != 2) {
         if (action == 0) {
            row = rm.getRow(this.getTable(), 0, sm, false);
            if (row == null) {
               Row del = rm.getRow(this.getTable(), 2, sm, true);
               del.whereForeignKey(this._fk, sm);
            }
         } else {
            row = rm.getRow(this.getTable(), 1, sm, false);
         }

         if (row == null && !this.isNullValue(sm)) {
            row = rm.getRow(this.getTable(), 1, sm, true);
            newOuterRow = true;
         }
      } else {
         row = rm.getRow(this.getTable(), action, sm, true);
      }

      if (row != null && this._fk != null) {
         if (row.getAction() == 1) {
            row.setForeignKey(this._fk, this._io, sm);
         } else {
            row.whereForeignKey(this._fk, sm);
         }

         if (newOuterRow) {
            row.setValid(false);
         }
      }

      return row;
   }

   private boolean isNullValue(OpenJPAStateManager sm) {
      switch (this.getTypeCode()) {
         case 0:
            return !sm.fetchBoolean(this.getIndex());
         case 1:
            return sm.fetchByte(this.getIndex()) == 0;
         case 2:
            return sm.fetchChar(this.getIndex()) == 0;
         case 3:
            return sm.fetchDouble(this.getIndex()) == 0.0;
         case 4:
            return sm.fetchFloat(this.getIndex()) == 0.0F;
         case 5:
            return sm.fetchInt(this.getIndex()) == 0;
         case 6:
            return sm.fetchLong(this.getIndex()) == 0L;
         case 7:
            return sm.fetchShort(this.getIndex()) == 0;
         case 8:
         default:
            return sm.fetchObject(this.getIndex()) == null;
         case 9:
            return sm.fetchString(this.getIndex()) == null;
      }
   }

   public Boolean isCustomInsert(OpenJPAStateManager sm, JDBCStore store) {
      return this.assertStrategy().isCustomInsert(sm, store);
   }

   public Boolean isCustomUpdate(OpenJPAStateManager sm, JDBCStore store) {
      return this.assertStrategy().isCustomUpdate(sm, store);
   }

   public Boolean isCustomDelete(OpenJPAStateManager sm, JDBCStore store) {
      return this.assertStrategy().isCustomDelete(sm, store);
   }

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.assertStrategy().customInsert(sm, store);
   }

   public void customUpdate(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.assertStrategy().customUpdate(sm, store);
   }

   public void customDelete(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.assertStrategy().customDelete(sm, store);
   }

   public void setFieldMapping(FieldMapping owner) {
      this.assertStrategy().setFieldMapping(owner);
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return this.assertStrategy().supportsSelect(sel, type, sm, store, fetch);
   }

   public void selectEagerParallel(SelectExecutor sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      this.assertStrategy().selectEagerParallel(sel, sm, store, fetch, eagerMode);
   }

   public void selectEagerJoin(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      this.assertStrategy().selectEagerJoin(sel, sm, store, fetch, eagerMode);
   }

   public boolean isEagerSelectToMany() {
      return this.assertStrategy().isEagerSelectToMany();
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      return this.assertStrategy().select(sel, sm, store, fetch, eagerMode);
   }

   public Joins join(Select sel) {
      if (this._fk == null) {
         return null;
      } else {
         Joins joins = sel.newJoins();
         return this._outer ? joins.outerJoin(this._fk, true, false) : joins.join(this._fk, true, false);
      }
   }

   public void wherePrimaryKey(Select sel, OpenJPAStateManager sm, JDBCStore store) {
      if (this._fk != null) {
         sel.whereForeignKey(this._fk, sm.getObjectId(), this.getDefiningMapping(), store);
      } else {
         sel.wherePrimaryKey(sm.getObjectId(), this.getDefiningMapping(), store);
      }

   }

   public void orderLocal(Select sel, ClassMapping elem, Joins joins) {
      this._orderCol.order(sel, elem, joins);
      JDBCOrder[] orders = (JDBCOrder[])((JDBCOrder[])this.getOrders());

      for(int i = 0; i < orders.length; ++i) {
         if (!orders[i].isInRelation()) {
            orders[i].order(sel, elem, joins);
         }
      }

   }

   public void orderRelation(Select sel, ClassMapping elem, Joins joins) {
      JDBCOrder[] orders = (JDBCOrder[])((JDBCOrder[])this.getOrders());

      for(int i = 0; i < orders.length; ++i) {
         if (orders[i].isInRelation()) {
            orders[i].order(sel, elem, joins);
         }
      }

   }

   public Object loadEagerParallel(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object res) throws SQLException {
      return this.assertStrategy().loadEagerParallel(sm, store, fetch, res);
   }

   public void loadEagerJoin(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      this.assertStrategy().loadEagerJoin(sm, store, fetch, res);
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
      this.assertStrategy().load(sm, store, fetch, res);
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
      this.assertStrategy().load(sm, store, fetch);
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return this.assertStrategy().toDataStoreValue(val, store);
   }

   public Object toKeyDataStoreValue(Object val, JDBCStore store) {
      return this.assertStrategy().toKeyDataStoreValue(val, store);
   }

   public void appendIsEmpty(SQLBuffer sql, Select sel, Joins joins) {
      this.assertStrategy().appendIsEmpty(sql, sel, joins);
   }

   public void appendIsNotEmpty(SQLBuffer sql, Select sel, Joins joins) {
      this.assertStrategy().appendIsNotEmpty(sql, sel, joins);
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      this.assertStrategy().appendIsNull(sql, sel, joins);
   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      this.assertStrategy().appendIsNotNull(sql, sel, joins);
   }

   public void appendSize(SQLBuffer sql, Select sel, Joins joins) {
      this.assertStrategy().appendSize(sql, sel, joins);
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return this.assertStrategy().join(joins, forceOuter);
   }

   public Joins joinKey(Joins joins, boolean forceOuter) {
      return this.assertStrategy().joinKey(joins, forceOuter);
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      return this.assertStrategy().joinRelation(joins, forceOuter, traverse);
   }

   public Joins joinKeyRelation(Joins joins, boolean forceOuter, boolean traverse) {
      return this.assertStrategy().joinKeyRelation(joins, forceOuter, traverse);
   }

   public Joins join(Joins joins, boolean forceOuter, boolean toMany) {
      if (this._fk == null) {
         return joins;
      } else {
         return !this._outer && !forceOuter ? joins.join(this._fk, true, toMany) : joins.outerJoin(this._fk, true, toMany);
      }
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return this.assertStrategy().loadProjection(store, fetch, res, joins);
   }

   public Object loadKeyProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return this.assertStrategy().loadKeyProjection(store, fetch, res, joins);
   }

   public boolean isVersionable() {
      return this.assertStrategy().isVersionable();
   }

   public void where(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object prevValue) throws SQLException {
      this.assertStrategy().where(sm, store, rm, prevValue);
   }

   private FieldStrategy assertStrategy() {
      if (this._strategy == null) {
         throw new InternalException();
      } else {
         return this._strategy;
      }
   }

   public ValueMappingInfo getValueInfo() {
      return this._val.getValueInfo();
   }

   public ValueHandler getHandler() {
      return this._val.getHandler();
   }

   public void setHandler(ValueHandler handler) {
      this._val.setHandler(handler);
   }

   public FieldMapping getFieldMapping() {
      return this;
   }

   public ClassMapping getTypeMapping() {
      return this._val.getTypeMapping();
   }

   public ClassMapping getDeclaredTypeMapping() {
      return this._val.getDeclaredTypeMapping();
   }

   public ClassMapping getEmbeddedMapping() {
      return this._val.getEmbeddedMapping();
   }

   public FieldMapping getValueMappedByMapping() {
      return this._val.getValueMappedByMapping();
   }

   public Column[] getColumns() {
      return this.isVersion() ? this.getDeclaringMapping().getVersion().getColumns() : this._val.getColumns();
   }

   public void setColumns(Column[] cols) {
      this._val.setColumns(cols);
   }

   public ColumnIO getColumnIO() {
      return this._val.getColumnIO();
   }

   public void setColumnIO(ColumnIO io) {
      this._val.setColumnIO(io);
   }

   public ForeignKey getForeignKey() {
      return this._val.getForeignKey();
   }

   public ForeignKey getForeignKey(ClassMapping target) {
      return this._val.getForeignKey(target);
   }

   public void setForeignKey(ForeignKey fk) {
      this._val.setForeignKey(fk);
   }

   public int getJoinDirection() {
      return this._val.getJoinDirection();
   }

   public void setJoinDirection(int direction) {
      this._val.setJoinDirection(direction);
   }

   public void setForeignKey(Row row, OpenJPAStateManager sm) throws SQLException {
      this._val.setForeignKey(row, sm);
   }

   public void whereForeignKey(Row row, OpenJPAStateManager sm) throws SQLException {
      this._val.whereForeignKey(row, sm);
   }

   public ClassMapping[] getIndependentTypeMappings() {
      return this._val.getIndependentTypeMappings();
   }

   public int getSelectSubclasses() {
      return this._val.getSelectSubclasses();
   }

   public Unique getValueUnique() {
      return this._val.getValueUnique();
   }

   public void setValueUnique(Unique unq) {
      this._val.setValueUnique(unq);
   }

   public Index getValueIndex() {
      return this._val.getValueIndex();
   }

   public void setValueIndex(Index idx) {
      this._val.setValueIndex(idx);
   }

   public boolean getUseClassCriteria() {
      return this._val.getUseClassCriteria();
   }

   public void setUseClassCriteria(boolean criteria) {
      this._val.setUseClassCriteria(criteria);
   }

   public int getPolymorphic() {
      return this._val.getPolymorphic();
   }

   public void setPolymorphic(int poly) {
      this._val.setPolymorphic(poly);
   }

   public void mapConstraints(String name, boolean adapt) {
      this._val.mapConstraints(name, adapt);
   }

   public void copyMappingInfo(ValueMapping vm) {
      this._val.copyMappingInfo(vm);
   }
}
