package org.apache.openjpa.jdbc.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.strats.EnumValueHandler;
import org.apache.openjpa.jdbc.meta.strats.UntypedPCValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import serp.util.Strings;

public class MappingDefaultsImpl implements MappingDefaults, Configurable {
   protected transient DBDictionary dict = null;
   private String _baseClassStrategy = null;
   private String _subclassStrategy = null;
   private String _versionStrategy = null;
   private String _discStrategy = null;
   private final Map _fieldMap = new HashMap();
   private boolean _defMissing = false;
   private boolean _classCriteria = false;
   private int _joinFKAction = 1;
   private int _fkAction = 1;
   private boolean _defer = false;
   private boolean _indexFK = true;
   private boolean _indexDisc = true;
   private boolean _indexVers = false;
   private boolean _orderLists = true;
   private boolean _addNullInd = false;
   private boolean _ordinalEnum = false;
   private boolean _stringifyUnmapped = false;
   private String _dsIdName = null;
   private String _versName = null;
   private String _discName = null;
   private String _orderName = null;
   private String _nullIndName = null;
   private boolean _removeHungarianNotation = false;

   public boolean isRemoveHungarianNotation() {
      return this._removeHungarianNotation;
   }

   public void setRemoveHungarianNotation(boolean removeHungarianNotation) {
      this._removeHungarianNotation = removeHungarianNotation;
   }

   public String getBaseClassStrategy() {
      return this._baseClassStrategy;
   }

   public void setBaseClassStrategy(String baseClassStrategy) {
      this._baseClassStrategy = baseClassStrategy;
   }

   public String getSubclassStrategy() {
      return this._subclassStrategy;
   }

   public void setSubclassStrategy(String subclassStrategy) {
      this._subclassStrategy = subclassStrategy;
   }

   public String getVersionStrategy() {
      return this._versionStrategy;
   }

   public void setVersionStrategy(String versionStrategy) {
      this._versionStrategy = versionStrategy;
   }

   public String getDiscriminatorStrategy() {
      return this._discStrategy;
   }

   public void setDiscriminatorStrategy(String discStrategy) {
      this._discStrategy = discStrategy;
   }

   public void setFieldStrategies(String fieldMapString) {
      Properties props = Configurations.parseProperties(fieldMapString);
      if (props != null) {
         this._fieldMap.putAll(props);
      }

   }

   public void setFieldStrategy(String valueType, String handlerType) {
      if (handlerType == null) {
         this._fieldMap.remove(valueType);
      } else {
         this._fieldMap.put(valueType, handlerType);
      }

   }

   public String getFieldStrategy(String valueType) {
      return (String)this._fieldMap.get(valueType);
   }

   public boolean getStoreEnumOrdinal() {
      return this._ordinalEnum;
   }

   public void setStoreEnumOrdinal(boolean ordinal) {
      this._ordinalEnum = ordinal;
   }

   public boolean getStoreUnmappedObjectIdString() {
      return this._stringifyUnmapped;
   }

   public void setStoreUnmappedObjectIdString(boolean stringify) {
      this._stringifyUnmapped = stringify;
   }

   public int getJoinForeignKeyDeleteAction() {
      return this._joinFKAction;
   }

   public void setJoinForeignKeyDeleteAction(int joinFKAction) {
      this._joinFKAction = joinFKAction;
   }

   public void setJoinForeignKeyDeleteAction(String joinFKAction) {
      this._joinFKAction = ForeignKey.getAction(joinFKAction);
   }

   public int getForeignKeyDeleteAction() {
      return this._fkAction;
   }

   public void setForeignKeyDeleteAction(int fkAction) {
      this._fkAction = fkAction;
   }

   public void setForeignKeyDeleteAction(String fkAction) {
      this._fkAction = ForeignKey.getAction(fkAction);
   }

   public boolean getIndexLogicalForeignKeys() {
      return this._indexFK;
   }

   public void setIndexLogicalForeignKeys(boolean indexFK) {
      this._indexFK = indexFK;
   }

   public boolean getIndexDiscriminator() {
      return this._indexDisc;
   }

   public void setIndexDiscriminator(boolean indexDisc) {
      this._indexDisc = indexDisc;
   }

   public boolean getIndexVersion() {
      return this._indexVers;
   }

   public void setIndexVersion(boolean indexVers) {
      this._indexVers = indexVers;
   }

   public boolean getOrderLists() {
      return this._orderLists;
   }

   public void setOrderLists(boolean orderLists) {
      this._orderLists = orderLists;
   }

   public boolean getAddNullIndicator() {
      return this._addNullInd;
   }

   public void setAddNullIndicator(boolean addNullInd) {
      this._addNullInd = addNullInd;
   }

   public boolean getDeferConstraints() {
      return this._defer;
   }

   public void setDeferConstraints(boolean defer) {
      this._defer = defer;
   }

   public String getDataStoreIdColumnName() {
      return this._dsIdName;
   }

   public void setDataStoreIdColumnName(String dsIdName) {
      this._dsIdName = dsIdName;
   }

   public String getVersionColumnName() {
      return this._versName;
   }

   public void setVersionColumnName(String versName) {
      this._versName = versName;
   }

   public String getDiscriminatorColumnName() {
      return this._discName;
   }

   public void setDiscriminatorColumnName(String discName) {
      this._discName = discName;
   }

   public String getOrderColumnName() {
      return this._orderName;
   }

   public void setOrderColumnName(String orderName) {
      this._orderName = orderName;
   }

   public String getNullIndicatorColumnName() {
      return this._nullIndName;
   }

   public void setNullIndicatorColumnName(String nullIndName) {
      this._nullIndName = nullIndName;
   }

   public boolean defaultMissingInfo() {
      return this._defMissing;
   }

   public void setDefaultMissingInfo(boolean defMissing) {
      this._defMissing = defMissing;
   }

   public boolean useClassCriteria() {
      return this._classCriteria;
   }

   public void setUseClassCriteria(boolean classCriteria) {
      this._classCriteria = classCriteria;
   }

   public Object getStrategy(ClassMapping cls, boolean adapt) {
      if (!adapt && !this.defaultMissingInfo()) {
         return null;
      } else {
         return cls.getMappedPCSuperclassMapping() == null ? this._baseClassStrategy : this._subclassStrategy;
      }
   }

   public Object getStrategy(Version vers, boolean adapt) {
      ClassMapping cls = vers.getClassMapping();
      return (adapt || this.defaultMissingInfo()) && cls.getJoinablePCSuperclassMapping() == null && cls.getVersionField() == null ? this._versionStrategy : null;
   }

   public Object getStrategy(Discriminator disc, boolean adapt) {
      ClassMapping cls = disc.getClassMapping();
      return (adapt || this.defaultMissingInfo()) && cls.getJoinablePCSuperclassMapping() == null && disc.getMappingInfo().getValue() == null ? this._discStrategy : null;
   }

   public Object getStrategy(ValueMapping vm, Class type, boolean adapt) {
      Object ret = this._fieldMap.get(type.getName());
      if (ret != null) {
         return ret;
      } else if (this._stringifyUnmapped && vm.getTypeMapping() != null && !vm.getTypeMapping().isMapped()) {
         return UntypedPCValueHandler.getInstance();
      } else if (type.isEnum() && !vm.isSerialized()) {
         EnumValueHandler enumHandler = new EnumValueHandler();
         enumHandler.setStoreOrdinal(this._ordinalEnum);
         return enumHandler;
      } else {
         return null;
      }
   }

   public Object getDiscriminatorValue(Discriminator disc, boolean adapt) {
      if (!adapt && !this.defaultMissingInfo()) {
         return null;
      } else {
         String alias = Strings.getClassName(disc.getClassMapping().getTypeAlias());
         switch (disc.getJavaType()) {
            case 2:
               return new Character(alias.charAt(0));
            case 5:
               return new Integer(alias.hashCode());
            case 9:
            default:
               return alias;
         }
      }
   }

   public String getTableName(ClassMapping cls, Schema schema) {
      String name = Strings.getClassName(cls.getDescribedType()).replace('$', '_');
      if (!this._defMissing) {
         name = this.dict.getValidTableName(name, schema);
      }

      return name;
   }

   public String getTableName(FieldMapping fm, Schema schema) {
      String name = fm.getName();
      Table table = fm.getDefiningMapping().getTable();
      if (table != null) {
         String tableName = table.getName();
         if (tableName.length() > 5) {
            tableName = tableName.substring(0, 5);
         }

         name = tableName + "_" + name;
      }

      if (!this._defMissing) {
         name = this.dict.getValidTableName(name, schema);
      }

      return name;
   }

   public void populateDataStoreIdColumns(ClassMapping cls, Table table, Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         if (this._dsIdName != null && cols.length == 1) {
            cols[i].setName(this._dsIdName);
         } else if (this._dsIdName != null) {
            cols[i].setName(this._dsIdName + i);
         }

         this.correctName(table, cols[i]);
         table.addSubColumn(cols[i].getName());
      }

      table.resetSubColumns();
   }

   protected void correctName(Table table, Column col) {
      if (!this._defMissing || this._removeHungarianNotation) {
         String name = col.getName();
         if (this._removeHungarianNotation) {
            name = this.removeHungarianNotation(name);
         }

         col.setName(this.dict.getValidColumnName(name, table));
      }

   }

   protected String removeHungarianNotation(String columnName) {
      char[] name = columnName.toCharArray();
      int newStart = 0;

      for(int i = 0; i < name.length; ++i) {
         if (Character.isUpperCase(name[i])) {
            newStart = i;
            break;
         }
      }

      return columnName.substring(newStart);
   }

   public void populateColumns(Version vers, Table table, Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         if (this._versName != null && cols.length == 1) {
            cols[i].setName(this._versName);
         } else if (this._versName != null) {
            if (i == 0) {
               cols[i].setName(this._versName);
            } else {
               cols[i].setName(this._versName + "_" + i);
            }
         } else if (this._versName != null) {
            cols[i].setName(this._versName + i);
         }

         this.correctName(table, cols[i]);
         table.addSubColumn(cols[i].getName());
      }

      table.resetSubColumns();
   }

   public void populateColumns(Discriminator disc, Table table, Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         if (this._discName != null && cols.length == 1) {
            cols[i].setName(this._discName);
         } else if (this._discName != null) {
            cols[i].setName(this._discName + i);
         }

         this.correctName(table, cols[i]);
         table.addSubColumn(cols[i].getName());
      }

      table.resetSubColumns();
   }

   public void populateJoinColumn(ClassMapping cm, Table local, Table foreign, Column col, Object target, int pos, int cols) {
      this.correctName(local, col);
   }

   public void populateJoinColumn(FieldMapping fm, Table local, Table foreign, Column col, Object target, int pos, int cols) {
      this.correctName(local, col);
   }

   public void populateForeignKeyColumn(ValueMapping vm, String name, Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
      if (cols == 1) {
         col.setName(name);
      } else if (target instanceof Column) {
         col.setName(name + "_" + ((Column)target).getName());
      }

      this.correctName(local, col);
   }

   public void populateColumns(ValueMapping vm, String name, Table table, Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         this.correctName(table, cols[i]);
         table.addSubColumn(cols[i].getName());
      }

      table.resetSubColumns();
   }

   public boolean populateOrderColumns(FieldMapping fm, Table table, Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         if (this._orderName != null && cols.length == 1) {
            cols[i].setName(this._orderName);
         } else if (this._orderName != null) {
            cols[i].setName(this._orderName + i);
         }

         this.correctName(table, cols[i]);
         table.addSubColumn(cols[i].getName());
      }

      table.resetSubColumns();
      return this._orderLists && (11 == fm.getTypeCode() || List.class.isAssignableFrom(fm.getType()));
   }

   public boolean populateNullIndicatorColumns(ValueMapping vm, String name, Table table, Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         if (this._nullIndName != null && cols.length == 1) {
            cols[i].setName(this._nullIndName);
         } else if (this._nullIndName != null) {
            cols[i].setName(this._nullIndName + i);
         }

         this.correctName(table, cols[i]);
         table.addSubColumn(cols[i].getName());
      }

      table.resetSubColumns();
      return this._addNullInd;
   }

   public ForeignKey getJoinForeignKey(ClassMapping cls, Table local, Table foreign) {
      if (this._joinFKAction == 1) {
         return null;
      } else {
         ForeignKey fk = new ForeignKey();
         fk.setDeleteAction(this._joinFKAction);
         fk.setDeferred(this._defer);
         return fk;
      }
   }

   public ForeignKey getJoinForeignKey(FieldMapping fm, Table local, Table foreign) {
      if (this._joinFKAction == 1) {
         return null;
      } else {
         ForeignKey fk = new ForeignKey();
         fk.setDeleteAction(this._joinFKAction);
         fk.setDeferred(this._defer);
         return fk;
      }
   }

   public ForeignKey getForeignKey(ValueMapping vm, String name, Table local, Table foreign, boolean inverse) {
      if (this._fkAction == 1) {
         return null;
      } else {
         ForeignKey fk = new ForeignKey();
         fk.setDeleteAction(this._fkAction);
         fk.setDeferred(this._defer);
         return fk;
      }
   }

   public Index getJoinIndex(FieldMapping fm, Table table, Column[] cols) {
      if (this._indexFK && fm.getJoinForeignKey() != null && fm.getJoinForeignKey().isLogical()) {
         if (this.areAllPrimaryKeyColumns(cols)) {
            return null;
         } else {
            Index idx = new Index();
            idx.setName(this.getIndexName((String)null, table, cols));
            return idx;
         }
      } else {
         return null;
      }
   }

   protected boolean areAllPrimaryKeyColumns(Column[] cols) {
      for(int i = 0; i < cols.length; ++i) {
         if (!cols[i].isPrimaryKey()) {
            return false;
         }
      }

      return true;
   }

   protected String getIndexName(String name, Table table, Column[] cols) {
      if (name == null) {
         name = cols[0].getName();
      }

      if (this._removeHungarianNotation) {
         name = this.removeHungarianNotation(name);
      }

      return this.dict.getValidIndexName(name, table);
   }

   public Index getIndex(ValueMapping vm, String name, Table table, Column[] cols) {
      if (this._indexFK && vm.getForeignKey() != null && vm.getForeignKey().isLogical()) {
         if (this.areAllPrimaryKeyColumns(cols)) {
            return null;
         } else {
            Index idx = new Index();
            idx.setName(this.getIndexName(name, table, cols));
            return idx;
         }
      } else {
         return null;
      }
   }

   public Index getIndex(Version vers, Table table, Column[] cols) {
      if (!this._indexVers) {
         return null;
      } else {
         Index idx = new Index();
         idx.setName(this.getIndexName(this._versName, table, cols));
         return idx;
      }
   }

   public Index getIndex(Discriminator disc, Table table, Column[] cols) {
      if (!this._indexDisc) {
         return null;
      } else {
         Index idx = new Index();
         idx.setName(this.getIndexName(this._discName, table, cols));
         return idx;
      }
   }

   public Unique getJoinUnique(FieldMapping fm, Table table, Column[] cols) {
      return null;
   }

   public Unique getUnique(ValueMapping vm, String name, Table table, Column[] cols) {
      return null;
   }

   public String getPrimaryKeyName(ClassMapping cm, Table table) {
      return null;
   }

   public void installPrimaryKey(FieldMapping fm, Table table) {
   }

   public void setConfiguration(Configuration conf) {
      this.dict = ((JDBCConfiguration)conf).getDBDictionaryInstance();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}
