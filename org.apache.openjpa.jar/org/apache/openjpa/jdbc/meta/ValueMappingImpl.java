package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schemas;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ValueMetaDataImpl;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;

public class ValueMappingImpl extends ValueMetaDataImpl implements ValueMapping {
   private static final Localizer _loc = Localizer.forPackage(ValueMappingImpl.class);
   private ValueMappingInfo _info;
   private ValueHandler _handler = null;
   private ClassMapping[] _typeArr = null;
   private Column[] _cols;
   private ColumnIO _io;
   private ForeignKey _fk;
   private Map _targetFKs;
   private Index _idx;
   private Unique _unq;
   private int _join;
   private boolean _criteria;
   private int _poly;

   public ValueMappingImpl(FieldMapping owner) {
      super(owner);
      this._cols = Schemas.EMPTY_COLUMNS;
      this._io = null;
      this._fk = null;
      this._targetFKs = null;
      this._idx = null;
      this._unq = null;
      this._join = 0;
      this._criteria = false;
      this._poly = 0;
      this._info = owner.getMappingRepository().newMappingInfo((ValueMapping)this);
      this._info.setUseClassCriteria(owner.getMappingRepository().getMappingDefaults().useClassCriteria());
   }

   protected ValueMappingImpl() {
      this._cols = Schemas.EMPTY_COLUMNS;
      this._io = null;
      this._fk = null;
      this._targetFKs = null;
      this._idx = null;
      this._unq = null;
      this._join = 0;
      this._criteria = false;
      this._poly = 0;
   }

   public ValueMappingInfo getValueInfo() {
      return this._info;
   }

   public ValueHandler getHandler() {
      return this._handler;
   }

   public void setHandler(ValueHandler handler) {
      this._handler = handler;
   }

   public MappingRepository getMappingRepository() {
      return (MappingRepository)this.getRepository();
   }

   public FieldMapping getFieldMapping() {
      return (FieldMapping)this.getFieldMetaData();
   }

   public ClassMapping getTypeMapping() {
      return (ClassMapping)this.getTypeMetaData();
   }

   public ClassMapping getDeclaredTypeMapping() {
      return (ClassMapping)this.getDeclaredTypeMetaData();
   }

   public ClassMapping getEmbeddedMapping() {
      return (ClassMapping)this.getEmbeddedMetaData();
   }

   public FieldMapping getValueMappedByMapping() {
      return (FieldMapping)this.getValueMappedByMetaData();
   }

   public Column[] getColumns() {
      if (this._cols.length != 0) {
         return this._cols;
      } else if (this._fk != null) {
         return this._fk.getColumns();
      } else {
         return this.getValueMappedBy() != null ? this.getValueMappedByMapping().getColumns() : this._cols;
      }
   }

   public void setColumns(Column[] cols) {
      if (cols == null) {
         cols = Schemas.EMPTY_COLUMNS;
      }

      this._cols = cols;
   }

   public ColumnIO getColumnIO() {
      if (this._cols.length == 0 && this._fk == null && this.getValueMappedBy() != null) {
         return this.getValueMappedByMapping().getColumnIO();
      } else {
         return this._io == null ? ColumnIO.UNRESTRICTED : this._io;
      }
   }

   public void setColumnIO(ColumnIO io) {
      this._io = io;
   }

   public ForeignKey getForeignKey() {
      return this._fk == null && this.getValueMappedBy() != null ? this.getValueMappedByMapping().getForeignKey() : this._fk;
   }

   public void setForeignKey(ForeignKey fk) {
      this._fk = fk;
      if (fk == null) {
         this._join = 0;
      }

   }

   public ForeignKey getForeignKey(ClassMapping target) {
      if (this._fk == null && this.getValueMappedBy() != null) {
         return this.getValueMappedByMapping().getForeignKey(target);
      } else if (target == null) {
         return this._fk;
      } else if (this._fk == null && this._cols.length == 0) {
         return null;
      } else {
         for(ClassMapping sup = target; sup != null; sup = sup.getJoinablePCSuperclassMapping()) {
            if (sup == this.getTypeMetaData()) {
               return this._fk;
            }

            target = sup;
         }

         synchronized(this) {
            if (this._targetFKs != null) {
               Object cachedFK = this._targetFKs.get(target);
               if (cachedFK != null) {
                  return (ForeignKey)cachedFK;
               }
            } else {
               this._targetFKs = new HashMap();
            }

            ForeignKey newfk = this._join == 0 ? this.newForwardForeignKey(target) : this.newInverseForeignKey(target);
            this._targetFKs.put(target, newfk);
            return newfk;
         }
      }
   }

   private ForeignKey newForwardForeignKey(ClassMapping target) {
      Table table;
      Column[] cols;
      if (this._fk == null) {
         table = this._cols[0].getTable();
         cols = this._cols;
      } else {
         table = this._fk.getTable();
         cols = this._fk.getColumns();
      }

      Column[] tcols = new Column[cols.length];

      for(int i = 0; i < cols.length; ++i) {
         if (cols[i].getTargetField() != null) {
            tcols[i] = this.getEquivalentColumn(cols[i], target, cols[i].getTargetField());
         } else if (this._fk != null) {
            tcols[i] = this.getEquivalentColumn(this._fk.getPrimaryKeyColumn(cols[i]).getName(), target, true);
         } else if (cols[i].getTarget() != null) {
            tcols[i] = this.getEquivalentColumn(cols[i].getTarget(), target, true);
         } else {
            tcols[i] = this.getEquivalentColumn(cols[i].getName(), target, false);
         }
      }

      ForeignKey newfk = table.addForeignKey();
      newfk.setJoins(cols, tcols);
      if (this._fk != null) {
         cols = this._fk.getConstantColumns();

         int i;
         for(i = 0; i < cols.length; ++i) {
            newfk.joinConstant(cols[i], this._fk.getConstant(cols[i]));
         }

         cols = this._fk.getConstantPrimaryKeyColumns();

         for(i = 0; i < cols.length; ++i) {
            newfk.joinConstant(this._fk.getPrimaryKeyConstant(cols[i]), this.getEquivalentColumn(cols[i].getName(), target, true));
         }
      }

      return newfk;
   }

   private Column getEquivalentColumn(Column col, ClassMapping target, String fieldName) {
      fieldName = fieldName.substring(fieldName.indexOf(46) + 1);
      FieldMapping field = target.getFieldMapping(fieldName);
      if (field == null) {
         throw new MetaDataException(_loc.get("no-equiv-field", new Object[]{this, target, fieldName, col}));
      } else {
         Column[] cols = field.getColumns();
         if (cols.length != 1) {
            throw new MetaDataException(_loc.get("bad-equiv-field", new Object[]{this, target, fieldName, col}));
         } else {
            return cols[0];
         }
      }
   }

   private Column getEquivalentColumn(String colName, ClassMapping target, boolean explicit) {
      if (!explicit) {
         for(ClassMapping cls = target; cls != null; cls = cls.getJoinablePCSuperclassMapping()) {
            if (cls.getTable() != null) {
               if (cls.getPrimaryKeyColumns().length == 1) {
                  return cls.getPrimaryKeyColumns()[0];
               }
               break;
            }
         }
      }

      for(ClassMapping cls = target; cls != null; cls = cls.getJoinablePCSuperclassMapping()) {
         if (cls.getTable() != null) {
            Column ret = cls.getTable().getColumn(colName);
            if (ret != null) {
               return ret;
            }
         }
      }

      throw new MetaDataException(_loc.get("no-equiv-col", this, target, colName));
   }

   private ForeignKey newInverseForeignKey(ClassMapping target) {
      FieldMapping field = this.getFieldMapping();
      FieldMapping mapped = field.getMappedByMapping();
      if (mapped == null) {
         throw new MetaDataException(_loc.get("cant-inverse", (Object)this));
      } else {
         mapped = target.getFieldMapping(mapped.getIndex());
         if (mapped != null && mapped.getTypeCode() == 15) {
            return mapped.getForeignKey();
         } else {
            throw new MetaDataException(_loc.get("no-equiv-mapped-by", this, target, field.getMappedBy()));
         }
      }
   }

   public int getJoinDirection() {
      return this._fk == null && this.getValueMappedBy() != null ? this.getValueMappedByMapping().getJoinDirection() : this._join;
   }

   public void setJoinDirection(int direction) {
      this._join = direction;
   }

   public void setForeignKey(Row row, OpenJPAStateManager rel) throws SQLException {
      if (rel != null) {
         row.setForeignKey(this.getForeignKey((ClassMapping)rel.getMetaData()), this._io, rel);
      } else if (this._fk != null) {
         row.setForeignKey(this._fk, this._io, (OpenJPAStateManager)null);
      } else {
         for(int i = 0; i < this._cols.length; ++i) {
            if (this._io == null || row.getAction() == 1 && this._io.isInsertable(i, true) || row.getAction() != 1 && this._io.isUpdatable(i, true)) {
               row.setNull(this._cols[i]);
            }
         }
      }

   }

   public void whereForeignKey(Row row, OpenJPAStateManager rel) throws SQLException {
      if (rel != null) {
         row.whereForeignKey(this.getForeignKey((ClassMapping)rel.getMetaData()), rel);
      } else if (this._fk != null) {
         row.whereForeignKey(this._fk, (OpenJPAStateManager)null);
      } else {
         for(int i = 0; i < this._cols.length; ++i) {
            row.whereNull(this._cols[i]);
         }
      }

   }

   public ClassMapping[] getIndependentTypeMappings() {
      ClassMapping rel = this.getTypeMapping();
      if (rel == null) {
         return ClassMapping.EMPTY_MAPPINGS;
      } else if (this._poly != 0) {
         if (!rel.isMapped()) {
            return ClassMapping.EMPTY_MAPPINGS;
         } else {
            if (this._typeArr == null) {
               this._typeArr = new ClassMapping[]{rel};
            }

            return this._typeArr;
         }
      } else {
         return rel.getIndependentAssignableMappings();
      }
   }

   public int getSelectSubclasses() {
      ClassMapping rel = this.getTypeMapping();
      if (rel != null && rel.isMapped()) {
         switch (this._poly) {
            case 0:
               ClassMapping[] assign = rel.getIndependentAssignableMappings();
               if (assign.length == 1 && assign[0] == rel) {
                  break;
               }

               return -1;
            case 1:
               return this._criteria ? 2 : 4;
            case 2:
               break;
            default:
               throw new InternalException();
         }

         return this._criteria ? 1 : 3;
      } else {
         return -1;
      }
   }

   public Unique getValueUnique() {
      return this._unq;
   }

   public void setValueUnique(Unique unq) {
      this._unq = unq;
   }

   public Index getValueIndex() {
      return this._idx;
   }

   public void setValueIndex(Index idx) {
      this._idx = idx;
   }

   public boolean getUseClassCriteria() {
      return this._fk == null && this.getValueMappedBy() != null ? this.getValueMappedByMapping().getUseClassCriteria() : this._criteria;
   }

   public void setUseClassCriteria(boolean criteria) {
      this._criteria = criteria;
   }

   public int getPolymorphic() {
      return this._poly;
   }

   public void setPolymorphic(int poly) {
      this._poly = poly;
   }

   public void refSchemaComponents() {
      for(int i = 0; i < this._cols.length; ++i) {
         this._cols[i].ref();
      }

      if (this._fk != null) {
         this._fk.ref();
         this._fk.refColumns();
      }

      ClassMapping embed = this.getEmbeddedMapping();
      if (embed != null) {
         embed.refSchemaComponents();
      }

   }

   public void mapConstraints(String name, boolean adapt) {
      this._unq = this._info.getUnique(this, name, adapt);
      this._idx = this._info.getIndex(this, name, adapt);
   }

   public void clearMapping() {
      this._handler = null;
      this._cols = Schemas.EMPTY_COLUMNS;
      this._unq = null;
      this._idx = null;
      this._fk = null;
      this._join = 0;
      this._info.clear();
      this.setResolve(10, false);
   }

   public void syncMappingInfo() {
      if (this.getValueMappedBy() != null) {
         this._info.clear();
      } else {
         this._info.syncWith(this);
         ClassMapping embed = this.getEmbeddedMapping();
         if (embed != null) {
            embed.syncMappingInfo();
         }
      }

   }

   public void copyMappingInfo(ValueMapping vm) {
      this.setValueMappedBy(vm.getValueMappedBy());
      this.setPolymorphic(vm.getPolymorphic());
      this._info.copy(vm.getValueInfo());
      ClassMapping embed = vm.getEmbeddedMapping();
      if (embed != null && this.getEmbeddedMapping() != null) {
         FieldMapping[] tmplates = embed.getFieldMappings();
         FieldMapping[] fms = this.getEmbeddedMapping().getFieldMappings();
         if (tmplates.length == fms.length) {
            for(int i = 0; i < fms.length; ++i) {
               fms[i].copyMappingInfo(tmplates[i]);
            }
         }
      }

   }

   public boolean resolve(int mode) {
      int cur = this.getResolve();
      if (super.resolve(mode)) {
         return true;
      } else {
         ClassMapping embed = this.getEmbeddedMapping();
         if (embed != null) {
            embed.resolve(mode);
         }

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
      Column[] cols;
      byte insertFlag;
      if (this._fk != null) {
         cols = this._fk.getColumns();
         insertFlag = 32;
      } else {
         cols = this.getColumns();
         insertFlag = 8;
      }

      ColumnIO io = this.getColumnIO();

      for(int i = 0; i < cols.length; ++i) {
         if (io.isInsertable(i, false)) {
            cols[i].setFlag(insertFlag, true);
         }

         if (io.isUpdatable(i, false)) {
            cols[i].setFlag(insertFlag, true);
         }
      }

   }

   private void initializeMapping() {
      if (this._fk != null) {
         Column[] cols = this._fk.getColumns();

         int len;
         for(len = 0; len < cols.length; ++len) {
            if (cols[len].getFlag(8)) {
               this.newIO().setNullInsertable(len, false);
            }

            if (cols[len].getFlag(16)) {
               this.newIO().setNullUpdatable(len, false);
            }
         }

         len = cols.length;
         cols = this._fk.getConstantColumns();

         for(int i = 0; i < cols.length; ++i) {
            if (cols[i].getFlag(8) || cols[i].getFlag(32)) {
               this.newIO().setInsertable(len + i, false);
            }

            if (cols[i].getFlag(16) || cols[i].getFlag(64)) {
               this.newIO().setUpdatable(len + i, false);
            }
         }

      }
   }

   private ColumnIO newIO() {
      if (this._io == null) {
         this._io = new ColumnIO();
      }

      return this._io;
   }
}
