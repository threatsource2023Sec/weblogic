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

public class TableJDORMappingFactoryBeanImpl extends MappingFactoryBeanImpl implements TableJDORMappingFactoryBean, Serializable {
   private boolean _ConstraintNames;
   private String _MappingColumn;
   private String _NameColumn;
   private int _StoreMode;
   private boolean _Strict;
   private String _Table;
   private String _TypeColumn;
   private String _Types;
   private boolean _UseSchemaValidation;
   private static SchemaHelper2 _schemaHelper;

   public TableJDORMappingFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public TableJDORMappingFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TableJDORMappingFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getUseSchemaValidation() {
      return this._UseSchemaValidation;
   }

   public boolean isUseSchemaValidationInherited() {
      return false;
   }

   public boolean isUseSchemaValidationSet() {
      return this._isSet(0);
   }

   public void setUseSchemaValidation(boolean param0) {
      boolean _oldVal = this._UseSchemaValidation;
      this._UseSchemaValidation = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getTypeColumn() {
      return this._TypeColumn;
   }

   public boolean isTypeColumnInherited() {
      return false;
   }

   public boolean isTypeColumnSet() {
      return this._isSet(1);
   }

   public void setTypeColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TypeColumn;
      this._TypeColumn = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean getConstraintNames() {
      return this._ConstraintNames;
   }

   public boolean isConstraintNamesInherited() {
      return false;
   }

   public boolean isConstraintNamesSet() {
      return this._isSet(2);
   }

   public void setConstraintNames(boolean param0) {
      boolean _oldVal = this._ConstraintNames;
      this._ConstraintNames = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getTable() {
      return this._Table;
   }

   public boolean isTableInherited() {
      return false;
   }

   public boolean isTableSet() {
      return this._isSet(3);
   }

   public void setTable(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Table;
      this._Table = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getTypes() {
      return this._Types;
   }

   public boolean isTypesInherited() {
      return false;
   }

   public boolean isTypesSet() {
      return this._isSet(4);
   }

   public void setTypes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Types;
      this._Types = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getStoreMode() {
      return this._StoreMode;
   }

   public boolean isStoreModeInherited() {
      return false;
   }

   public boolean isStoreModeSet() {
      return this._isSet(5);
   }

   public void setStoreMode(int param0) {
      int _oldVal = this._StoreMode;
      this._StoreMode = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getMappingColumn() {
      return this._MappingColumn;
   }

   public boolean isMappingColumnInherited() {
      return false;
   }

   public boolean isMappingColumnSet() {
      return this._isSet(6);
   }

   public void setMappingColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappingColumn;
      this._MappingColumn = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean getStrict() {
      return this._Strict;
   }

   public boolean isStrictInherited() {
      return false;
   }

   public boolean isStrictSet() {
      return this._isSet(7);
   }

   public void setStrict(boolean param0) {
      boolean _oldVal = this._Strict;
      this._Strict = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getNameColumn() {
      return this._NameColumn;
   }

   public boolean isNameColumnInherited() {
      return false;
   }

   public boolean isNameColumnSet() {
      return this._isSet(8);
   }

   public void setNameColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NameColumn;
      this._NameColumn = param0;
      this._postSet(8, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ConstraintNames = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._MappingColumn = "MAPPING_DEF";
               if (initOne) {
                  break;
               }
            case 8:
               this._NameColumn = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._StoreMode = 0;
               if (initOne) {
                  break;
               }
            case 7:
               this._Strict = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._Table = "KODO_JDO_MAPPINGS";
               if (initOne) {
                  break;
               }
            case 1:
               this._TypeColumn = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Types = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._UseSchemaValidation = false;
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

   public static class SchemaHelper2 extends MappingFactoryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("table")) {
                  return 3;
               }

               if (s.equals("types")) {
                  return 4;
               }
               break;
            case 6:
               if (s.equals("strict")) {
                  return 7;
               }
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            default:
               break;
            case 10:
               if (s.equals("store-mode")) {
                  return 5;
               }
               break;
            case 11:
               if (s.equals("name-column")) {
                  return 8;
               }

               if (s.equals("type-column")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("mapping-column")) {
                  return 6;
               }
               break;
            case 16:
               if (s.equals("constraint-names")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("use-schema-validation")) {
                  return 0;
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
               return "use-schema-validation";
            case 1:
               return "type-column";
            case 2:
               return "constraint-names";
            case 3:
               return "table";
            case 4:
               return "types";
            case 5:
               return "store-mode";
            case 6:
               return "mapping-column";
            case 7:
               return "strict";
            case 8:
               return "name-column";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends MappingFactoryBeanImpl.Helper {
      private TableJDORMappingFactoryBeanImpl bean;

      protected Helper(TableJDORMappingFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "UseSchemaValidation";
            case 1:
               return "TypeColumn";
            case 2:
               return "ConstraintNames";
            case 3:
               return "Table";
            case 4:
               return "Types";
            case 5:
               return "StoreMode";
            case 6:
               return "MappingColumn";
            case 7:
               return "Strict";
            case 8:
               return "NameColumn";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConstraintNames")) {
            return 2;
         } else if (propName.equals("MappingColumn")) {
            return 6;
         } else if (propName.equals("NameColumn")) {
            return 8;
         } else if (propName.equals("StoreMode")) {
            return 5;
         } else if (propName.equals("Strict")) {
            return 7;
         } else if (propName.equals("Table")) {
            return 3;
         } else if (propName.equals("TypeColumn")) {
            return 1;
         } else if (propName.equals("Types")) {
            return 4;
         } else {
            return propName.equals("UseSchemaValidation") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isConstraintNamesSet()) {
               buf.append("ConstraintNames");
               buf.append(String.valueOf(this.bean.getConstraintNames()));
            }

            if (this.bean.isMappingColumnSet()) {
               buf.append("MappingColumn");
               buf.append(String.valueOf(this.bean.getMappingColumn()));
            }

            if (this.bean.isNameColumnSet()) {
               buf.append("NameColumn");
               buf.append(String.valueOf(this.bean.getNameColumn()));
            }

            if (this.bean.isStoreModeSet()) {
               buf.append("StoreMode");
               buf.append(String.valueOf(this.bean.getStoreMode()));
            }

            if (this.bean.isStrictSet()) {
               buf.append("Strict");
               buf.append(String.valueOf(this.bean.getStrict()));
            }

            if (this.bean.isTableSet()) {
               buf.append("Table");
               buf.append(String.valueOf(this.bean.getTable()));
            }

            if (this.bean.isTypeColumnSet()) {
               buf.append("TypeColumn");
               buf.append(String.valueOf(this.bean.getTypeColumn()));
            }

            if (this.bean.isTypesSet()) {
               buf.append("Types");
               buf.append(String.valueOf(this.bean.getTypes()));
            }

            if (this.bean.isUseSchemaValidationSet()) {
               buf.append("UseSchemaValidation");
               buf.append(String.valueOf(this.bean.getUseSchemaValidation()));
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
            TableJDORMappingFactoryBeanImpl otherTyped = (TableJDORMappingFactoryBeanImpl)other;
            this.computeDiff("ConstraintNames", this.bean.getConstraintNames(), otherTyped.getConstraintNames(), false);
            this.computeDiff("MappingColumn", this.bean.getMappingColumn(), otherTyped.getMappingColumn(), false);
            this.computeDiff("NameColumn", this.bean.getNameColumn(), otherTyped.getNameColumn(), false);
            this.computeDiff("StoreMode", this.bean.getStoreMode(), otherTyped.getStoreMode(), false);
            this.computeDiff("Strict", this.bean.getStrict(), otherTyped.getStrict(), false);
            this.computeDiff("Table", this.bean.getTable(), otherTyped.getTable(), false);
            this.computeDiff("TypeColumn", this.bean.getTypeColumn(), otherTyped.getTypeColumn(), false);
            this.computeDiff("Types", this.bean.getTypes(), otherTyped.getTypes(), false);
            this.computeDiff("UseSchemaValidation", this.bean.getUseSchemaValidation(), otherTyped.getUseSchemaValidation(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TableJDORMappingFactoryBeanImpl original = (TableJDORMappingFactoryBeanImpl)event.getSourceBean();
            TableJDORMappingFactoryBeanImpl proposed = (TableJDORMappingFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConstraintNames")) {
                  original.setConstraintNames(proposed.getConstraintNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MappingColumn")) {
                  original.setMappingColumn(proposed.getMappingColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("NameColumn")) {
                  original.setNameColumn(proposed.getNameColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("StoreMode")) {
                  original.setStoreMode(proposed.getStoreMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Strict")) {
                  original.setStrict(proposed.getStrict());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Table")) {
                  original.setTable(proposed.getTable());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TypeColumn")) {
                  original.setTypeColumn(proposed.getTypeColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Types")) {
                  original.setTypes(proposed.getTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("UseSchemaValidation")) {
                  original.setUseSchemaValidation(proposed.getUseSchemaValidation());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            TableJDORMappingFactoryBeanImpl copy = (TableJDORMappingFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConstraintNames")) && this.bean.isConstraintNamesSet()) {
               copy.setConstraintNames(this.bean.getConstraintNames());
            }

            if ((excludeProps == null || !excludeProps.contains("MappingColumn")) && this.bean.isMappingColumnSet()) {
               copy.setMappingColumn(this.bean.getMappingColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("NameColumn")) && this.bean.isNameColumnSet()) {
               copy.setNameColumn(this.bean.getNameColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreMode")) && this.bean.isStoreModeSet()) {
               copy.setStoreMode(this.bean.getStoreMode());
            }

            if ((excludeProps == null || !excludeProps.contains("Strict")) && this.bean.isStrictSet()) {
               copy.setStrict(this.bean.getStrict());
            }

            if ((excludeProps == null || !excludeProps.contains("Table")) && this.bean.isTableSet()) {
               copy.setTable(this.bean.getTable());
            }

            if ((excludeProps == null || !excludeProps.contains("TypeColumn")) && this.bean.isTypeColumnSet()) {
               copy.setTypeColumn(this.bean.getTypeColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("Types")) && this.bean.isTypesSet()) {
               copy.setTypes(this.bean.getTypes());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSchemaValidation")) && this.bean.isUseSchemaValidationSet()) {
               copy.setUseSchemaValidation(this.bean.getUseSchemaValidation());
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
