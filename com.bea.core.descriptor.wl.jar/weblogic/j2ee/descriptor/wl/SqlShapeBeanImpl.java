package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SqlShapeBeanImpl extends AbstractDescriptorBean implements SqlShapeBean, Serializable {
   private String _Description;
   private String[] _EjbRelationNames;
   private int _PassThroughColumns;
   private String _SqlShapeName;
   private TableBean[] _Tables;
   private static SchemaHelper2 _schemaHelper;

   public SqlShapeBeanImpl() {
      this._initializeProperty(-1);
   }

   public SqlShapeBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SqlShapeBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getSqlShapeName() {
      return this._SqlShapeName;
   }

   public boolean isSqlShapeNameInherited() {
      return false;
   }

   public boolean isSqlShapeNameSet() {
      return this._isSet(1);
   }

   public void setSqlShapeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SqlShapeName;
      this._SqlShapeName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addTable(TableBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         TableBean[] _new;
         if (this._isSet(2)) {
            _new = (TableBean[])((TableBean[])this._getHelper()._extendArray(this.getTables(), TableBean.class, param0));
         } else {
            _new = new TableBean[]{param0};
         }

         try {
            this.setTables(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TableBean[] getTables() {
      return this._Tables;
   }

   public boolean isTablesInherited() {
      return false;
   }

   public boolean isTablesSet() {
      return this._isSet(2);
   }

   public void removeTable(TableBean param0) {
      this.destroyTable(param0);
   }

   public void setTables(TableBean[] param0) throws InvalidAttributeValueException {
      TableBean[] param0 = param0 == null ? new TableBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TableBean[] _oldVal = this._Tables;
      this._Tables = (TableBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public TableBean createTable() {
      TableBeanImpl _val = new TableBeanImpl(this, -1);

      try {
         this.addTable(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTable(TableBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         TableBean[] _old = this.getTables();
         TableBean[] _new = (TableBean[])((TableBean[])this._getHelper()._removeElement(_old, TableBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTables(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public int getPassThroughColumns() {
      return this._PassThroughColumns;
   }

   public boolean isPassThroughColumnsInherited() {
      return false;
   }

   public boolean isPassThroughColumnsSet() {
      return this._isSet(3);
   }

   public void setPassThroughColumns(int param0) {
      LegalChecks.checkMin("PassThroughColumns", param0, 0);
      int _oldVal = this._PassThroughColumns;
      this._PassThroughColumns = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String[] getEjbRelationNames() {
      return this._EjbRelationNames;
   }

   public boolean isEjbRelationNamesInherited() {
      return false;
   }

   public boolean isEjbRelationNamesSet() {
      return this._isSet(4);
   }

   public void addEjbRelationName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(4)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getEjbRelationNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setEjbRelationNames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeEjbRelationName(String param0) {
      String[] _old = this.getEjbRelationNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setEjbRelationNames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEjbRelationNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._EjbRelationNames;
      this._EjbRelationNames = param0;
      this._postSet(4, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._EjbRelationNames = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._PassThroughColumns = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._SqlShapeName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Tables = new TableBean[0];
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
            case 5:
               if (s.equals("table")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("sql-shape-name")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("ejb-relation-name")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("pass-through-columns")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new TableBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "sql-shape-name";
            case 2:
               return "table";
            case 3:
               return "pass-through-columns";
            case 4:
               return "ejb-relation-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SqlShapeBeanImpl bean;

      protected Helper(SqlShapeBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "SqlShapeName";
            case 2:
               return "Tables";
            case 3:
               return "PassThroughColumns";
            case 4:
               return "EjbRelationNames";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("EjbRelationNames")) {
            return 4;
         } else if (propName.equals("PassThroughColumns")) {
            return 3;
         } else if (propName.equals("SqlShapeName")) {
            return 1;
         } else {
            return propName.equals("Tables") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getTables()));
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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isEjbRelationNamesSet()) {
               buf.append("EjbRelationNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEjbRelationNames())));
            }

            if (this.bean.isPassThroughColumnsSet()) {
               buf.append("PassThroughColumns");
               buf.append(String.valueOf(this.bean.getPassThroughColumns()));
            }

            if (this.bean.isSqlShapeNameSet()) {
               buf.append("SqlShapeName");
               buf.append(String.valueOf(this.bean.getSqlShapeName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getTables().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTables()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SqlShapeBeanImpl otherTyped = (SqlShapeBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("EjbRelationNames", this.bean.getEjbRelationNames(), otherTyped.getEjbRelationNames(), false);
            this.computeDiff("PassThroughColumns", this.bean.getPassThroughColumns(), otherTyped.getPassThroughColumns(), false);
            this.computeDiff("SqlShapeName", this.bean.getSqlShapeName(), otherTyped.getSqlShapeName(), false);
            this.computeChildDiff("Tables", this.bean.getTables(), otherTyped.getTables(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SqlShapeBeanImpl original = (SqlShapeBeanImpl)event.getSourceBean();
            SqlShapeBeanImpl proposed = (SqlShapeBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EjbRelationNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addEjbRelationName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbRelationName((String)update.getRemovedObject());
                  }

                  if (original.getEjbRelationNames() == null || original.getEjbRelationNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("PassThroughColumns")) {
                  original.setPassThroughColumns(proposed.getPassThroughColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SqlShapeName")) {
                  original.setSqlShapeName(proposed.getSqlShapeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Tables")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTable((TableBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTable((TableBean)update.getRemovedObject());
                  }

                  if (original.getTables() == null || original.getTables().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
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
            SqlShapeBeanImpl copy = (SqlShapeBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRelationNames")) && this.bean.isEjbRelationNamesSet()) {
               Object o = this.bean.getEjbRelationNames();
               copy.setEjbRelationNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("PassThroughColumns")) && this.bean.isPassThroughColumnsSet()) {
               copy.setPassThroughColumns(this.bean.getPassThroughColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("SqlShapeName")) && this.bean.isSqlShapeNameSet()) {
               copy.setSqlShapeName(this.bean.getSqlShapeName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tables")) && this.bean.isTablesSet() && !copy._isSet(2)) {
               TableBean[] oldTables = this.bean.getTables();
               TableBean[] newTables = new TableBean[oldTables.length];

               for(int i = 0; i < newTables.length; ++i) {
                  newTables[i] = (TableBean)((TableBean)this.createCopy((AbstractDescriptorBean)oldTables[i], includeObsolete));
               }

               copy.setTables(newTables);
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getTables(), clazz, annotation);
      }
   }
}
