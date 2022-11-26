package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class FieldMapBeanImpl extends AbstractDescriptorBean implements FieldMapBean, Serializable {
   private String _CmpField;
   private String _DbmsColumn;
   private String _DbmsColumnType;
   private boolean _DbmsDefaultValue;
   private String _GroupName;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public FieldMapBeanImpl() {
      this._initializeProperty(-1);
   }

   public FieldMapBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FieldMapBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCmpField() {
      return this._CmpField;
   }

   public boolean isCmpFieldInherited() {
      return false;
   }

   public boolean isCmpFieldSet() {
      return this._isSet(0);
   }

   public void setCmpField(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CmpField;
      this._CmpField = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDbmsColumn() {
      return this._DbmsColumn;
   }

   public boolean isDbmsColumnInherited() {
      return false;
   }

   public boolean isDbmsColumnSet() {
      return this._isSet(1);
   }

   public void setDbmsColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DbmsColumn;
      this._DbmsColumn = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getDbmsColumnType() {
      return !this._isSet(2) ? null : this._DbmsColumnType;
   }

   public boolean isDbmsColumnTypeInherited() {
      return false;
   }

   public boolean isDbmsColumnTypeSet() {
      return this._isSet(2);
   }

   public void setDbmsColumnType(String param0) {
      if (param0 == null) {
         this._unSet(2);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Clob", "Blob", "NClob", "NChar", "NVarchar2", "LongString", "SybaseBinary", "SQLXML"};
         param0 = LegalChecks.checkInEnum("DbmsColumnType", param0, _set);
         String _oldVal = this._DbmsColumnType;
         this._DbmsColumnType = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public boolean isDbmsDefaultValue() {
      return this._DbmsDefaultValue;
   }

   public boolean isDbmsDefaultValueInherited() {
      return false;
   }

   public boolean isDbmsDefaultValueSet() {
      return this._isSet(3);
   }

   public void setDbmsDefaultValue(boolean param0) {
      boolean _oldVal = this._DbmsDefaultValue;
      this._DbmsDefaultValue = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getGroupName() {
      return this._GroupName;
   }

   public boolean isGroupNameInherited() {
      return false;
   }

   public boolean isGroupNameSet() {
      return this._isSet(4);
   }

   public void setGroupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GroupName;
      this._GroupName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getCmpField();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 9:
            if (s.equals("cmp-field")) {
               return info.compareXpaths(this._getPropertyXpath("cmp-field"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._CmpField = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._DbmsColumn = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._DbmsColumnType = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._GroupName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._DbmsDefaultValue = false;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 5;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            default:
               break;
            case 9:
               if (s.equals("cmp-field")) {
                  return 0;
               }
               break;
            case 10:
               if (s.equals("group-name")) {
                  return 4;
               }
               break;
            case 11:
               if (s.equals("dbms-column")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("dbms-column-type")) {
                  return 2;
               }
               break;
            case 18:
               if (s.equals("dbms-default-value")) {
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
               return "cmp-field";
            case 1:
               return "dbms-column";
            case 2:
               return "dbms-column-type";
            case 3:
               return "dbms-default-value";
            case 4:
               return "group-name";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("cmp-field");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private FieldMapBeanImpl bean;

      protected Helper(FieldMapBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CmpField";
            case 1:
               return "DbmsColumn";
            case 2:
               return "DbmsColumnType";
            case 3:
               return "DbmsDefaultValue";
            case 4:
               return "GroupName";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CmpField")) {
            return 0;
         } else if (propName.equals("DbmsColumn")) {
            return 1;
         } else if (propName.equals("DbmsColumnType")) {
            return 2;
         } else if (propName.equals("GroupName")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 5;
         } else {
            return propName.equals("DbmsDefaultValue") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isCmpFieldSet()) {
               buf.append("CmpField");
               buf.append(String.valueOf(this.bean.getCmpField()));
            }

            if (this.bean.isDbmsColumnSet()) {
               buf.append("DbmsColumn");
               buf.append(String.valueOf(this.bean.getDbmsColumn()));
            }

            if (this.bean.isDbmsColumnTypeSet()) {
               buf.append("DbmsColumnType");
               buf.append(String.valueOf(this.bean.getDbmsColumnType()));
            }

            if (this.bean.isGroupNameSet()) {
               buf.append("GroupName");
               buf.append(String.valueOf(this.bean.getGroupName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isDbmsDefaultValueSet()) {
               buf.append("DbmsDefaultValue");
               buf.append(String.valueOf(this.bean.isDbmsDefaultValue()));
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
            FieldMapBeanImpl otherTyped = (FieldMapBeanImpl)other;
            this.computeDiff("CmpField", this.bean.getCmpField(), otherTyped.getCmpField(), false);
            this.computeDiff("DbmsColumn", this.bean.getDbmsColumn(), otherTyped.getDbmsColumn(), false);
            this.computeDiff("DbmsColumnType", this.bean.getDbmsColumnType(), otherTyped.getDbmsColumnType(), false);
            this.computeDiff("GroupName", this.bean.getGroupName(), otherTyped.getGroupName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("DbmsDefaultValue", this.bean.isDbmsDefaultValue(), otherTyped.isDbmsDefaultValue(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FieldMapBeanImpl original = (FieldMapBeanImpl)event.getSourceBean();
            FieldMapBeanImpl proposed = (FieldMapBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CmpField")) {
                  original.setCmpField(proposed.getCmpField());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DbmsColumn")) {
                  original.setDbmsColumn(proposed.getDbmsColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DbmsColumnType")) {
                  original.setDbmsColumnType(proposed.getDbmsColumnType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("GroupName")) {
                  original.setGroupName(proposed.getGroupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("DbmsDefaultValue")) {
                  original.setDbmsDefaultValue(proposed.isDbmsDefaultValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            FieldMapBeanImpl copy = (FieldMapBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CmpField")) && this.bean.isCmpFieldSet()) {
               copy.setCmpField(this.bean.getCmpField());
            }

            if ((excludeProps == null || !excludeProps.contains("DbmsColumn")) && this.bean.isDbmsColumnSet()) {
               copy.setDbmsColumn(this.bean.getDbmsColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("DbmsColumnType")) && this.bean.isDbmsColumnTypeSet()) {
               copy.setDbmsColumnType(this.bean.getDbmsColumnType());
            }

            if ((excludeProps == null || !excludeProps.contains("GroupName")) && this.bean.isGroupNameSet()) {
               copy.setGroupName(this.bean.getGroupName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("DbmsDefaultValue")) && this.bean.isDbmsDefaultValueSet()) {
               copy.setDbmsDefaultValue(this.bean.isDbmsDefaultValue());
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
