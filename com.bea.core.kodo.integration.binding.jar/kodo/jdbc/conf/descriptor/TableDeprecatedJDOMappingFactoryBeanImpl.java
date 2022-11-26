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

public class TableDeprecatedJDOMappingFactoryBeanImpl extends MappingFactoryBeanImpl implements TableDeprecatedJDOMappingFactoryBean, Serializable {
   private String _ClasspathScan;
   private String _Files;
   private String _MappingColumn;
   private String _NameColumn;
   private String _Resources;
   private boolean _ScanTopDown;
   private boolean _SingleRow;
   private int _StoreMode;
   private boolean _Strict;
   private String _TableName;
   private String _Types;
   private String _URLs;
   private boolean _UseSchemaValidation;
   private static SchemaHelper2 _schemaHelper;

   public TableDeprecatedJDOMappingFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public TableDeprecatedJDOMappingFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TableDeprecatedJDOMappingFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(0);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getURLs() {
      return this._URLs;
   }

   public boolean isURLsInherited() {
      return false;
   }

   public boolean isURLsSet() {
      return this._isSet(1);
   }

   public void setURLs(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._URLs;
      this._URLs = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getClasspathScan() {
      return this._ClasspathScan;
   }

   public boolean isClasspathScanInherited() {
      return false;
   }

   public boolean isClasspathScanSet() {
      return this._isSet(2);
   }

   public void setClasspathScan(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClasspathScan;
      this._ClasspathScan = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getTypes() {
      return this._Types;
   }

   public boolean isTypesInherited() {
      return false;
   }

   public boolean isTypesSet() {
      return this._isSet(3);
   }

   public void setTypes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Types;
      this._Types = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getMappingColumn() {
      return this._MappingColumn;
   }

   public boolean isMappingColumnInherited() {
      return false;
   }

   public boolean isMappingColumnSet() {
      return this._isSet(4);
   }

   public void setMappingColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappingColumn;
      this._MappingColumn = param0;
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

   public boolean getStrict() {
      return this._Strict;
   }

   public boolean isStrictInherited() {
      return false;
   }

   public boolean isStrictSet() {
      return this._isSet(6);
   }

   public void setStrict(boolean param0) {
      boolean _oldVal = this._Strict;
      this._Strict = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getNameColumn() {
      return this._NameColumn;
   }

   public boolean isNameColumnInherited() {
      return false;
   }

   public boolean isNameColumnSet() {
      return this._isSet(7);
   }

   public void setNameColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NameColumn;
      this._NameColumn = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean getUseSchemaValidation() {
      return this._UseSchemaValidation;
   }

   public boolean isUseSchemaValidationInherited() {
      return false;
   }

   public boolean isUseSchemaValidationSet() {
      return this._isSet(8);
   }

   public void setUseSchemaValidation(boolean param0) {
      boolean _oldVal = this._UseSchemaValidation;
      this._UseSchemaValidation = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean getSingleRow() {
      return this._SingleRow;
   }

   public boolean isSingleRowInherited() {
      return false;
   }

   public boolean isSingleRowSet() {
      return this._isSet(9);
   }

   public void setSingleRow(boolean param0) {
      boolean _oldVal = this._SingleRow;
      this._SingleRow = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getFiles() {
      return this._Files;
   }

   public boolean isFilesInherited() {
      return false;
   }

   public boolean isFilesSet() {
      return this._isSet(10);
   }

   public void setFiles(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Files;
      this._Files = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean getScanTopDown() {
      return this._ScanTopDown;
   }

   public boolean isScanTopDownInherited() {
      return false;
   }

   public boolean isScanTopDownSet() {
      return this._isSet(11);
   }

   public void setScanTopDown(boolean param0) {
      boolean _oldVal = this._ScanTopDown;
      this._ScanTopDown = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getResources() {
      return this._Resources;
   }

   public boolean isResourcesInherited() {
      return false;
   }

   public boolean isResourcesSet() {
      return this._isSet(12);
   }

   public void setResources(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Resources;
      this._Resources = param0;
      this._postSet(12, _oldVal, param0);
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
               this._ClasspathScan = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Files = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MappingColumn = "MAPPING_DEF";
               if (initOne) {
                  break;
               }
            case 7:
               this._NameColumn = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Resources = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ScanTopDown = false;
               if (initOne) {
                  break;
               }
            case 9:
               this._SingleRow = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._StoreMode = 0;
               if (initOne) {
                  break;
               }
            case 6:
               this._Strict = true;
               if (initOne) {
                  break;
               }
            case 0:
               this._TableName = "JDO_MAPPING";
               if (initOne) {
                  break;
               }
            case 3:
               this._Types = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._URLs = null;
               if (initOne) {
                  break;
               }
            case 8:
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
            case 4:
               if (s.equals("urls")) {
                  return 1;
               }
               break;
            case 5:
               if (s.equals("files")) {
                  return 10;
               }

               if (s.equals("types")) {
                  return 3;
               }
               break;
            case 6:
               if (s.equals("strict")) {
                  return 6;
               }
            case 7:
            case 8:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            default:
               break;
            case 9:
               if (s.equals("resources")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("single-row")) {
                  return 9;
               }

               if (s.equals("store-mode")) {
                  return 5;
               }

               if (s.equals("table-name")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("name-column")) {
                  return 7;
               }
               break;
            case 13:
               if (s.equals("scan-top-down")) {
                  return 11;
               }
               break;
            case 14:
               if (s.equals("classpath-scan")) {
                  return 2;
               }

               if (s.equals("mapping-column")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("use-schema-validation")) {
                  return 8;
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
               return "table-name";
            case 1:
               return "urls";
            case 2:
               return "classpath-scan";
            case 3:
               return "types";
            case 4:
               return "mapping-column";
            case 5:
               return "store-mode";
            case 6:
               return "strict";
            case 7:
               return "name-column";
            case 8:
               return "use-schema-validation";
            case 9:
               return "single-row";
            case 10:
               return "files";
            case 11:
               return "scan-top-down";
            case 12:
               return "resources";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends MappingFactoryBeanImpl.Helper {
      private TableDeprecatedJDOMappingFactoryBeanImpl bean;

      protected Helper(TableDeprecatedJDOMappingFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TableName";
            case 1:
               return "URLs";
            case 2:
               return "ClasspathScan";
            case 3:
               return "Types";
            case 4:
               return "MappingColumn";
            case 5:
               return "StoreMode";
            case 6:
               return "Strict";
            case 7:
               return "NameColumn";
            case 8:
               return "UseSchemaValidation";
            case 9:
               return "SingleRow";
            case 10:
               return "Files";
            case 11:
               return "ScanTopDown";
            case 12:
               return "Resources";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClasspathScan")) {
            return 2;
         } else if (propName.equals("Files")) {
            return 10;
         } else if (propName.equals("MappingColumn")) {
            return 4;
         } else if (propName.equals("NameColumn")) {
            return 7;
         } else if (propName.equals("Resources")) {
            return 12;
         } else if (propName.equals("ScanTopDown")) {
            return 11;
         } else if (propName.equals("SingleRow")) {
            return 9;
         } else if (propName.equals("StoreMode")) {
            return 5;
         } else if (propName.equals("Strict")) {
            return 6;
         } else if (propName.equals("TableName")) {
            return 0;
         } else if (propName.equals("Types")) {
            return 3;
         } else if (propName.equals("URLs")) {
            return 1;
         } else {
            return propName.equals("UseSchemaValidation") ? 8 : super.getPropertyIndex(propName);
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
            if (this.bean.isClasspathScanSet()) {
               buf.append("ClasspathScan");
               buf.append(String.valueOf(this.bean.getClasspathScan()));
            }

            if (this.bean.isFilesSet()) {
               buf.append("Files");
               buf.append(String.valueOf(this.bean.getFiles()));
            }

            if (this.bean.isMappingColumnSet()) {
               buf.append("MappingColumn");
               buf.append(String.valueOf(this.bean.getMappingColumn()));
            }

            if (this.bean.isNameColumnSet()) {
               buf.append("NameColumn");
               buf.append(String.valueOf(this.bean.getNameColumn()));
            }

            if (this.bean.isResourcesSet()) {
               buf.append("Resources");
               buf.append(String.valueOf(this.bean.getResources()));
            }

            if (this.bean.isScanTopDownSet()) {
               buf.append("ScanTopDown");
               buf.append(String.valueOf(this.bean.getScanTopDown()));
            }

            if (this.bean.isSingleRowSet()) {
               buf.append("SingleRow");
               buf.append(String.valueOf(this.bean.getSingleRow()));
            }

            if (this.bean.isStoreModeSet()) {
               buf.append("StoreMode");
               buf.append(String.valueOf(this.bean.getStoreMode()));
            }

            if (this.bean.isStrictSet()) {
               buf.append("Strict");
               buf.append(String.valueOf(this.bean.getStrict()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            if (this.bean.isTypesSet()) {
               buf.append("Types");
               buf.append(String.valueOf(this.bean.getTypes()));
            }

            if (this.bean.isURLsSet()) {
               buf.append("URLs");
               buf.append(String.valueOf(this.bean.getURLs()));
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
            TableDeprecatedJDOMappingFactoryBeanImpl otherTyped = (TableDeprecatedJDOMappingFactoryBeanImpl)other;
            this.computeDiff("ClasspathScan", this.bean.getClasspathScan(), otherTyped.getClasspathScan(), false);
            this.computeDiff("Files", this.bean.getFiles(), otherTyped.getFiles(), false);
            this.computeDiff("MappingColumn", this.bean.getMappingColumn(), otherTyped.getMappingColumn(), false);
            this.computeDiff("NameColumn", this.bean.getNameColumn(), otherTyped.getNameColumn(), false);
            this.computeDiff("Resources", this.bean.getResources(), otherTyped.getResources(), false);
            this.computeDiff("ScanTopDown", this.bean.getScanTopDown(), otherTyped.getScanTopDown(), false);
            this.computeDiff("SingleRow", this.bean.getSingleRow(), otherTyped.getSingleRow(), false);
            this.computeDiff("StoreMode", this.bean.getStoreMode(), otherTyped.getStoreMode(), false);
            this.computeDiff("Strict", this.bean.getStrict(), otherTyped.getStrict(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeDiff("Types", this.bean.getTypes(), otherTyped.getTypes(), false);
            this.computeDiff("URLs", this.bean.getURLs(), otherTyped.getURLs(), false);
            this.computeDiff("UseSchemaValidation", this.bean.getUseSchemaValidation(), otherTyped.getUseSchemaValidation(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TableDeprecatedJDOMappingFactoryBeanImpl original = (TableDeprecatedJDOMappingFactoryBeanImpl)event.getSourceBean();
            TableDeprecatedJDOMappingFactoryBeanImpl proposed = (TableDeprecatedJDOMappingFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClasspathScan")) {
                  original.setClasspathScan(proposed.getClasspathScan());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Files")) {
                  original.setFiles(proposed.getFiles());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MappingColumn")) {
                  original.setMappingColumn(proposed.getMappingColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("NameColumn")) {
                  original.setNameColumn(proposed.getNameColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Resources")) {
                  original.setResources(proposed.getResources());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ScanTopDown")) {
                  original.setScanTopDown(proposed.getScanTopDown());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("SingleRow")) {
                  original.setSingleRow(proposed.getSingleRow());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("StoreMode")) {
                  original.setStoreMode(proposed.getStoreMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Strict")) {
                  original.setStrict(proposed.getStrict());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Types")) {
                  original.setTypes(proposed.getTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("URLs")) {
                  original.setURLs(proposed.getURLs());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("UseSchemaValidation")) {
                  original.setUseSchemaValidation(proposed.getUseSchemaValidation());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
            TableDeprecatedJDOMappingFactoryBeanImpl copy = (TableDeprecatedJDOMappingFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClasspathScan")) && this.bean.isClasspathScanSet()) {
               copy.setClasspathScan(this.bean.getClasspathScan());
            }

            if ((excludeProps == null || !excludeProps.contains("Files")) && this.bean.isFilesSet()) {
               copy.setFiles(this.bean.getFiles());
            }

            if ((excludeProps == null || !excludeProps.contains("MappingColumn")) && this.bean.isMappingColumnSet()) {
               copy.setMappingColumn(this.bean.getMappingColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("NameColumn")) && this.bean.isNameColumnSet()) {
               copy.setNameColumn(this.bean.getNameColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("Resources")) && this.bean.isResourcesSet()) {
               copy.setResources(this.bean.getResources());
            }

            if ((excludeProps == null || !excludeProps.contains("ScanTopDown")) && this.bean.isScanTopDownSet()) {
               copy.setScanTopDown(this.bean.getScanTopDown());
            }

            if ((excludeProps == null || !excludeProps.contains("SingleRow")) && this.bean.isSingleRowSet()) {
               copy.setSingleRow(this.bean.getSingleRow());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreMode")) && this.bean.isStoreModeSet()) {
               copy.setStoreMode(this.bean.getStoreMode());
            }

            if ((excludeProps == null || !excludeProps.contains("Strict")) && this.bean.isStrictSet()) {
               copy.setStrict(this.bean.getStrict());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("Types")) && this.bean.isTypesSet()) {
               copy.setTypes(this.bean.getTypes());
            }

            if ((excludeProps == null || !excludeProps.contains("URLs")) && this.bean.isURLsSet()) {
               copy.setURLs(this.bean.getURLs());
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
