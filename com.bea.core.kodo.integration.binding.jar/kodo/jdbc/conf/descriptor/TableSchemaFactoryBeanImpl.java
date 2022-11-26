package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class TableSchemaFactoryBeanImpl extends SchemaFactoryBeanImpl implements TableSchemaFactoryBean, Serializable {
   private String _PrimaryKeyColumn;
   private String _SchemaColumn;
   private String _Table;
   private String _TableName;
   private static SchemaHelper2 _schemaHelper;

   public TableSchemaFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public TableSchemaFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TableSchemaFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSchemaColumn() {
      return this._SchemaColumn;
   }

   public boolean isSchemaColumnInherited() {
      return false;
   }

   public boolean isSchemaColumnSet() {
      return this._isSet(0);
   }

   public void setSchemaColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SchemaColumn;
      this._SchemaColumn = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(1);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTable() {
      return this._Table;
   }

   public boolean isTableInherited() {
      return false;
   }

   public boolean isTableSet() {
      return this._isSet(2);
   }

   public void setTable(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Table;
      this._Table = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getPrimaryKeyColumn() {
      return this._PrimaryKeyColumn;
   }

   public boolean isPrimaryKeyColumnInherited() {
      return false;
   }

   public boolean isPrimaryKeyColumnSet() {
      return this._isSet(3);
   }

   public void setPrimaryKeyColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrimaryKeyColumn;
      this._PrimaryKeyColumn = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._PrimaryKeyColumn = "ID";
               if (initOne) {
                  break;
               }
            case 0:
               this._SchemaColumn = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Table = "OPENJPA_SCHEMA";
               if (initOne) {
                  break;
               }
            case 1:
               this._TableName = null;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SchemaFactoryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("table")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("table-name")) {
                  return 1;
               }
               break;
            case 13:
               if (s.equals("schema-column")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("primary-key-column")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "schema-column";
            case 1:
               return "table-name";
            case 2:
               return "table";
            case 3:
               return "primary-key-column";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SchemaFactoryBeanImpl.Helper {
      private TableSchemaFactoryBeanImpl bean;

      protected Helper(TableSchemaFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SchemaColumn";
            case 1:
               return "TableName";
            case 2:
               return "Table";
            case 3:
               return "PrimaryKeyColumn";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PrimaryKeyColumn")) {
            return 3;
         } else if (propName.equals("SchemaColumn")) {
            return 0;
         } else if (propName.equals("Table")) {
            return 2;
         } else {
            return propName.equals("TableName") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isPrimaryKeyColumnSet()) {
               buf.append("PrimaryKeyColumn");
               buf.append(String.valueOf(this.bean.getPrimaryKeyColumn()));
            }

            if (this.bean.isSchemaColumnSet()) {
               buf.append("SchemaColumn");
               buf.append(String.valueOf(this.bean.getSchemaColumn()));
            }

            if (this.bean.isTableSet()) {
               buf.append("Table");
               buf.append(String.valueOf(this.bean.getTable()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            TableSchemaFactoryBeanImpl otherTyped = (TableSchemaFactoryBeanImpl)other;
            this.computeDiff("PrimaryKeyColumn", this.bean.getPrimaryKeyColumn(), otherTyped.getPrimaryKeyColumn(), false);
            this.computeDiff("SchemaColumn", this.bean.getSchemaColumn(), otherTyped.getSchemaColumn(), false);
            this.computeDiff("Table", this.bean.getTable(), otherTyped.getTable(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TableSchemaFactoryBeanImpl original = (TableSchemaFactoryBeanImpl)event.getSourceBean();
            TableSchemaFactoryBeanImpl proposed = (TableSchemaFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PrimaryKeyColumn")) {
                  original.setPrimaryKeyColumn(proposed.getPrimaryKeyColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SchemaColumn")) {
                  original.setSchemaColumn(proposed.getSchemaColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Table")) {
                  original.setTable(proposed.getTable());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            TableSchemaFactoryBeanImpl copy = (TableSchemaFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PrimaryKeyColumn")) && this.bean.isPrimaryKeyColumnSet()) {
               copy.setPrimaryKeyColumn(this.bean.getPrimaryKeyColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("SchemaColumn")) && this.bean.isSchemaColumnSet()) {
               copy.setSchemaColumn(this.bean.getSchemaColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("Table")) && this.bean.isTableSet()) {
               copy.setTable(this.bean.getTable());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
