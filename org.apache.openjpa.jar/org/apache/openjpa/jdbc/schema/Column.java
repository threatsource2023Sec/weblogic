package org.apache.openjpa.jdbc.schema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.meta.VersionStrategy;
import serp.util.Numbers;

public class Column extends ReferenceCounter {
   public static final int FLAG_UNINSERTABLE = 2;
   public static final int FLAG_UNUPDATABLE = 4;
   public static final int FLAG_DIRECT_INSERT = 8;
   public static final int FLAG_DIRECT_UPDATE = 16;
   public static final int FLAG_FK_INSERT = 32;
   public static final int FLAG_FK_UPDATE = 64;
   public static final int FLAG_PK_JOIN = 128;
   private String _name = null;
   private String _fullName = null;
   private Table _table = null;
   private String _tableName = null;
   private String _schemaName = null;
   private int _type = 1111;
   private String _typeName = null;
   private int _javaType = 8;
   private int _size = 0;
   private int _decimals = 0;
   private String _defaultStr = null;
   private Object _default = null;
   private Boolean _notNull = null;
   private boolean _autoAssign = false;
   private boolean _rel = false;
   private String _target = null;
   private String _targetField = null;
   private int _flags = 0;
   private int _index = 0;
   private boolean _pk = false;
   private VersionStrategy _versionStrategy = null;
   private String _comment = null;

   public Column() {
   }

   public Column(String name, Table table) {
      this.setName(name);
      if (table != null) {
         this.setTableName(table.getName());
         this.setSchemaName(table.getSchemaName());
      }

      this._table = table;
   }

   void remove() {
      Table table = this.getTable();
      if (table != null) {
         Schema schema = table.getSchema();
         if (schema != null && schema.getSchemaGroup() != null) {
            Schema[] schemas = schema.getSchemaGroup().getSchemas();

            for(int i = 0; i < schemas.length; ++i) {
               Table[] tabs = schemas[i].getTables();

               for(int j = 0; j < tabs.length; ++j) {
                  ForeignKey[] fks = tabs[j].getForeignKeys();

                  for(int k = 0; k < fks.length; ++k) {
                     Column[] cols = fks[k].getColumns();
                     Column[] pks = fks[k].getPrimaryKeyColumns();

                     int l;
                     for(l = 0; l < cols.length; ++l) {
                        if (this.equals(cols[l]) || this.equals(pks[l])) {
                           fks[k].removeJoin(cols[l]);
                        }
                     }

                     cols = fks[k].getConstantColumns();

                     for(l = 0; l < cols.length; ++l) {
                        if (this.equals(cols[l])) {
                           fks[k].removeJoin(cols[l]);
                        }
                     }

                     pks = fks[k].getConstantPrimaryKeyColumns();

                     for(l = 0; l < pks.length; ++l) {
                        if (this.equals(pks[l])) {
                           fks[k].removeJoin(pks[l]);
                        }
                     }

                     if (fks[k].getColumns().length == 0 && fks[k].getConstantColumns().length == 0) {
                        tabs[j].removeForeignKey(fks[k]);
                     }
                  }
               }
            }
         }

         Index[] idxs = table.getIndexes();

         for(int i = 0; i < idxs.length; ++i) {
            if (idxs[i].removeColumn(this) && idxs[i].getColumns().length == 0) {
               table.removeIndex(idxs[i]);
            }
         }

         Unique[] unqs = table.getUniques();

         for(int i = 0; i < unqs.length; ++i) {
            if (unqs[i].removeColumn(this) && unqs[i].getColumns().length == 0) {
               table.removeUnique(unqs[i]);
            }
         }

         PrimaryKey pk = table.getPrimaryKey();
         if (pk != null && pk.removeColumn(this) && pk.getColumns().length == 0) {
            table.removePrimaryKey();
         }

         this._table = null;
      }
   }

   public Table getTable() {
      return this._table;
   }

   public String getTableName() {
      return this._tableName;
   }

   public void setTableName(String name) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._tableName = name;
         this._fullName = null;
      }
   }

   public void resetTableName(String name) {
      this._tableName = name;
   }

   public String getSchemaName() {
      return this._schemaName;
   }

   public void setSchemaName(String name) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._schemaName = name;
      }
   }

   public String getName() {
      return this._name;
   }

   public void setName(String name) {
      if (this.getTable() != null) {
         throw new IllegalStateException();
      } else {
         this._name = name;
         this._fullName = null;
      }
   }

   public String getFullName() {
      if (this._fullName == null) {
         String name = this.getName();
         if (name == null) {
            return null;
         }

         String tname = this.getTableName();
         if (tname == null) {
            return name;
         }

         this._fullName = tname + "." + name;
      }

      return this._fullName;
   }

   public int getType() {
      return this._type;
   }

   public void setType(int sqlType) {
      this._type = sqlType;
   }

   public String getTypeName() {
      return this._typeName;
   }

   public void setTypeName(String typeName) {
      this._typeName = typeName;
   }

   public int getJavaType() {
      return this._javaType;
   }

   public void setJavaType(int type) {
      this._javaType = type;
   }

   public int getSize() {
      return this._size;
   }

   public void setSize(int size) {
      this._size = size;
   }

   public int getDecimalDigits() {
      return this._decimals;
   }

   public void setDecimalDigits(int digits) {
      this._decimals = digits;
   }

   public String getDefaultString() {
      return this._defaultStr;
   }

   public void setDefaultString(String def) {
      this._defaultStr = def;
      this._default = null;
   }

   public Object getDefault() {
      if (this._default != null) {
         return this._default;
      } else if (this._defaultStr == null) {
         return null;
      } else {
         switch (this._javaType) {
            case 0:
            case 16:
               this._default = "true".equals(this._defaultStr) ? Boolean.TRUE : Boolean.FALSE;
               break;
            case 1:
            case 17:
               this._default = new Byte(this._defaultStr);
               break;
            case 2:
            case 18:
               this._default = new Character(this._defaultStr.charAt(0));
               break;
            case 3:
            case 19:
               this._default = new Double(this._defaultStr);
               break;
            case 4:
            case 20:
               this._default = new Float(this._defaultStr);
               break;
            case 5:
            case 21:
               this._default = Numbers.valueOf(Integer.parseInt(this._defaultStr));
               break;
            case 6:
            case 22:
               this._default = Numbers.valueOf(Long.parseLong(this._defaultStr));
               break;
            case 7:
            case 23:
               this._default = new Short(this._defaultStr);
               break;
            case 10:
            case 24:
               this._default = new BigDecimal(this._defaultStr);
               break;
            case 14:
               this._default = new Date(this._defaultStr);
               break;
            case 25:
               this._default = new BigInteger(this._defaultStr);
               break;
            case 1007:
               this._default = java.sql.Date.valueOf(this._defaultStr);
               break;
            case 1010:
               this._default = Time.valueOf(this._defaultStr);
               break;
            case 1011:
               this._default = Timestamp.valueOf(this._defaultStr);
               break;
            default:
               this._default = this._defaultStr;
         }

         return this._default;
      }
   }

   public void setDefault(Object def) {
      this._default = def;
      this._defaultStr = def == null ? null : def.toString();
   }

   public boolean isNotNull() {
      return this._notNull == Boolean.TRUE;
   }

   public void setNotNull(boolean notNull) {
      this._notNull = notNull ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean isNotNullExplicit() {
      return this._notNull != null;
   }

   public boolean isAutoAssigned() {
      return this._autoAssign;
   }

   public void setAutoAssigned(boolean autoAssign) {
      if (autoAssign != this._autoAssign && this.getTable() != null) {
         this.getTable().changeAutoAssigned(this);
      }

      this._autoAssign = autoAssign;
   }

   public boolean isRelationId() {
      return this._rel;
   }

   public void setRelationId(boolean rel) {
      if (rel != this._rel && this.getTable() != null) {
         this.getTable().changeRelationId(this);
      }

      this._rel = rel;
   }

   public String getTarget() {
      return this._target;
   }

   public void setTarget(String target) {
      this._target = StringUtils.trimToNull(target);
   }

   public String getTargetField() {
      return this._targetField;
   }

   public void setTargetField(String target) {
      if (target != null && target.length() == 0) {
         target = null;
      }

      this._targetField = target;
   }

   public boolean getFlag(int flag) {
      return (this._flags & flag) != 0;
   }

   public void setFlag(int flag, boolean on) {
      if (on) {
         this._flags |= flag;
      } else {
         this._flags &= ~flag;
      }

   }

   public boolean isPrimaryKey() {
      return this._pk;
   }

   void setPrimaryKey(boolean pk) {
      this._pk = pk;
   }

   public int getIndex() {
      if (this.getTable() != null) {
         this.getTable().indexColumns();
      }

      return this._index;
   }

   void setIndex(int index) {
      this._index = index;
   }

   public boolean isLob() {
      switch (this._type) {
         case -4:
         case -3:
         case -2:
         case 2004:
         case 2005:
            return true;
         default:
            return false;
      }
   }

   public boolean isCompatible(int type, String typeName, int size, int decimals) {
      if (type != 1111 && this.getType() != 1111) {
         switch (this.getType()) {
            case -7:
            case -6:
            case -5:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
               switch (type) {
                  case -7:
                  case -6:
                  case -5:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                  case 8:
                     return true;
                  case -4:
                  case -3:
                  case -2:
                  case -1:
                  case 0:
                  case 1:
                  default:
                     return false;
               }
            case -4:
            case -3:
            case -2:
            case 1111:
            case 2004:
               switch (type) {
                  case -4:
                  case -3:
                  case -2:
                  case 1111:
                  case 2004:
                     return true;
                  default:
                     return false;
               }
            case -1:
            case 1:
            case 12:
            case 2005:
               switch (type) {
                  case -1:
                  case 1:
                  case 12:
                  case 91:
                  case 92:
                  case 93:
                  case 2005:
                     return true;
                  default:
                     return false;
               }
            case 91:
            case 92:
            case 93:
               switch (type) {
                  case -1:
                  case 12:
                  case 91:
                  case 92:
                  case 93:
                  case 2005:
                     return true;
                  default:
                     return false;
               }
            default:
               return type == this.getType();
         }
      } else {
         return true;
      }
   }

   public String toString() {
      return this.getName();
   }

   public String getDescription() {
      StringBuffer buf = new StringBuffer();
      buf.append("Full Name: ").append(this.getFullName()).append("\n");
      buf.append("Type: ").append(Schemas.getJDBCName(this.getType())).append("\n");
      buf.append("Size: ").append(this.getSize()).append("\n");
      buf.append("Default: ").append(this.getDefaultString()).append("\n");
      buf.append("Not Null: ").append(this.isNotNull()).append("\n");
      return buf.toString();
   }

   public boolean equalsColumn(Column col) {
      if (col == this) {
         return true;
      } else if (col == null) {
         return false;
      } else if (!this.getFullName().equalsIgnoreCase(col.getFullName())) {
         return false;
      } else if (!this.isCompatible(col.getType(), col.getTypeName(), col.getSize(), col.getDecimalDigits())) {
         return false;
      } else {
         return this.getType() != 12 || this.getSize() <= 0 || col.getSize() <= 0 || this.getSize() == col.getSize();
      }
   }

   public void copy(Column from) {
      if (from != null) {
         if (this.getName() == null) {
            this.setName(from.getName());
         }

         if (this.getType() == 1111) {
            this.setType(from.getType());
         }

         if (this.getTypeName() == null) {
            this.setTypeName(from.getTypeName());
         }

         if (this.getJavaType() == 8) {
            this.setJavaType(from.getJavaType());
         }

         if (this.getSize() == 0) {
            this.setSize(from.getSize());
         }

         if (this.getDecimalDigits() == 0) {
            this.setDecimalDigits(from.getDecimalDigits());
         }

         if (this.getDefaultString() == null) {
            this.setDefaultString(from.getDefaultString());
         }

         if (!this.isNotNullExplicit() && from.isNotNullExplicit()) {
            this.setNotNull(from.isNotNull());
         }

         if (!this.isAutoAssigned()) {
            this.setAutoAssigned(from.isAutoAssigned());
         }

         if (!this.isRelationId()) {
            this.setRelationId(from.isRelationId());
         }

         if (this.getTarget() == null) {
            this.setTarget(from.getTarget());
         }

         if (this.getTargetField() == null) {
            this.setTargetField(from.getTargetField());
         }

         if (this._flags == 0) {
            this._flags = from._flags;
         }

      }
   }

   public boolean isXML() {
      return this._typeName != null && this._typeName.startsWith("XML");
   }

   public VersionStrategy getVersionStrategy() {
      return this._versionStrategy;
   }

   public void setVersionStrategy(VersionStrategy strategy) {
      this._versionStrategy = strategy;
   }

   public boolean hasComment() {
      return this._comment != null && !this._comment.equalsIgnoreCase(this._name);
   }

   public String getComment() {
      return this._comment;
   }

   public void setComment(String comment) {
      this._comment = comment;
   }
}
