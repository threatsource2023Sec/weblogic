package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class TableBeanImpl extends AbstractDescriptorBean implements TableBean, Serializable {
   private String[] _DbmsColumns;
   private String _EjbRelationshipRoleName;
   private String _TableName;
   private static SchemaHelper2 _schemaHelper;

   public TableBeanImpl() {
      this._initializeProperty(-1);
   }

   public TableBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TableBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String[] getDbmsColumns() {
      return this._DbmsColumns;
   }

   public boolean isDbmsColumnsInherited() {
      return false;
   }

   public boolean isDbmsColumnsSet() {
      return this._isSet(1);
   }

   public void addDbmsColumn(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDbmsColumns(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDbmsColumns(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDbmsColumn(String param0) {
      String[] _old = this.getDbmsColumns();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDbmsColumns(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDbmsColumns(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DbmsColumns;
      this._DbmsColumns = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getEjbRelationshipRoleName() {
      return this._EjbRelationshipRoleName;
   }

   public boolean isEjbRelationshipRoleNameInherited() {
      return false;
   }

   public boolean isEjbRelationshipRoleNameSet() {
      return this._isSet(2);
   }

   public void setEjbRelationshipRoleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbRelationshipRoleName;
      this._EjbRelationshipRoleName = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DbmsColumns = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._EjbRelationshipRoleName = null;
               if (initOne) {
                  break;
               }
            case 0:
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("table-name")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("dbms-column")) {
                  return 1;
               }
               break;
            case 26:
               if (s.equals("ejb-relationship-role-name")) {
                  return 2;
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
               return "dbms-column";
            case 2:
               return "ejb-relationship-role-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private TableBeanImpl bean;

      protected Helper(TableBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TableName";
            case 1:
               return "DbmsColumns";
            case 2:
               return "EjbRelationshipRoleName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DbmsColumns")) {
            return 1;
         } else if (propName.equals("EjbRelationshipRoleName")) {
            return 2;
         } else {
            return propName.equals("TableName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isDbmsColumnsSet()) {
               buf.append("DbmsColumns");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDbmsColumns())));
            }

            if (this.bean.isEjbRelationshipRoleNameSet()) {
               buf.append("EjbRelationshipRoleName");
               buf.append(String.valueOf(this.bean.getEjbRelationshipRoleName()));
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
            TableBeanImpl otherTyped = (TableBeanImpl)other;
            this.computeDiff("DbmsColumns", this.bean.getDbmsColumns(), otherTyped.getDbmsColumns(), false);
            this.computeDiff("EjbRelationshipRoleName", this.bean.getEjbRelationshipRoleName(), otherTyped.getEjbRelationshipRoleName(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TableBeanImpl original = (TableBeanImpl)event.getSourceBean();
            TableBeanImpl proposed = (TableBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DbmsColumns")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDbmsColumn((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDbmsColumn((String)update.getRemovedObject());
                  }

                  if (original.getDbmsColumns() == null || original.getDbmsColumns().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("EjbRelationshipRoleName")) {
                  original.setEjbRelationshipRoleName(proposed.getEjbRelationshipRoleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
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
            TableBeanImpl copy = (TableBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DbmsColumns")) && this.bean.isDbmsColumnsSet()) {
               Object o = this.bean.getDbmsColumns();
               copy.setDbmsColumns(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRelationshipRoleName")) && this.bean.isEjbRelationshipRoleNameSet()) {
               copy.setEjbRelationshipRoleName(this.bean.getEjbRelationshipRoleName());
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
