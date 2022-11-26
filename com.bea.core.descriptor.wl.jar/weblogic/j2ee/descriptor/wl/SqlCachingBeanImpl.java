package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.EmptyBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SqlCachingBeanImpl extends AbstractDescriptorBean implements SqlCachingBean, Serializable {
   private String _Description;
   private EmptyBean[] _ResultColumns;
   private String _SqlCachingName;
   private TableBean[] _Tables;
   private static SchemaHelper2 _schemaHelper;

   public SqlCachingBeanImpl() {
      this._initializeProperty(-1);
   }

   public SqlCachingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SqlCachingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getSqlCachingName() {
      return this._SqlCachingName;
   }

   public boolean isSqlCachingNameInherited() {
      return false;
   }

   public boolean isSqlCachingNameSet() {
      return this._isSet(1);
   }

   public void setSqlCachingName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SqlCachingName;
      this._SqlCachingName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addResultColumn(EmptyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         EmptyBean[] _new;
         if (this._isSet(2)) {
            _new = (EmptyBean[])((EmptyBean[])this._getHelper()._extendArray(this.getResultColumns(), EmptyBean.class, param0));
         } else {
            _new = new EmptyBean[]{param0};
         }

         try {
            this.setResultColumns(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EmptyBean[] getResultColumns() {
      return this._ResultColumns;
   }

   public boolean isResultColumnsInherited() {
      return false;
   }

   public boolean isResultColumnsSet() {
      return this._isSet(2);
   }

   public void removeResultColumn(EmptyBean param0) {
      this.destroyResultColumn(param0);
   }

   public void setResultColumns(EmptyBean[] param0) throws InvalidAttributeValueException {
      EmptyBean[] param0 = param0 == null ? new EmptyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EmptyBean[] _oldVal = this._ResultColumns;
      this._ResultColumns = (EmptyBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public EmptyBean createResultColumn() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.addResultColumn(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResultColumn(EmptyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         EmptyBean[] _old = this.getResultColumns();
         EmptyBean[] _new = (EmptyBean[])((EmptyBean[])this._getHelper()._removeElement(_old, EmptyBean.class, param0));
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
               this.setResultColumns(_new);
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

   public void addTable(TableBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         TableBean[] _new;
         if (this._isSet(3)) {
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
      return this._isSet(3);
   }

   public void removeTable(TableBean param0) {
      this.destroyTable(param0);
   }

   public void setTables(TableBean[] param0) throws InvalidAttributeValueException {
      TableBean[] param0 = param0 == null ? new TableBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TableBean[] _oldVal = this._Tables;
      this._Tables = (TableBean[])param0;
      this._postSet(3, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 3);
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

   public Object _getKey() {
      return this.getSqlCachingName();
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
         case 16:
            if (s.equals("sql-caching-name")) {
               return info.compareXpaths(this._getPropertyXpath("sql-caching-name"));
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
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ResultColumns = new EmptyBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._SqlCachingName = null;
               if (initOne) {
                  break;
               }
            case 3:
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
                  return 3;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("result-column")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("sql-caching-name")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new EmptyBeanImpl.SchemaHelper2();
            case 3:
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
               return "sql-caching-name";
            case 2:
               return "result-column";
            case 3:
               return "table";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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
         indices.add("sql-caching-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SqlCachingBeanImpl bean;

      protected Helper(SqlCachingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "SqlCachingName";
            case 2:
               return "ResultColumns";
            case 3:
               return "Tables";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("ResultColumns")) {
            return 2;
         } else if (propName.equals("SqlCachingName")) {
            return 1;
         } else {
            return propName.equals("Tables") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getResultColumns()));
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

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getResultColumns().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResultColumns()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSqlCachingNameSet()) {
               buf.append("SqlCachingName");
               buf.append(String.valueOf(this.bean.getSqlCachingName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTables().length; ++i) {
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
            SqlCachingBeanImpl otherTyped = (SqlCachingBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeChildDiff("ResultColumns", this.bean.getResultColumns(), otherTyped.getResultColumns(), false);
            this.computeDiff("SqlCachingName", this.bean.getSqlCachingName(), otherTyped.getSqlCachingName(), false);
            this.computeChildDiff("Tables", this.bean.getTables(), otherTyped.getTables(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SqlCachingBeanImpl original = (SqlCachingBeanImpl)event.getSourceBean();
            SqlCachingBeanImpl proposed = (SqlCachingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ResultColumns")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResultColumn((EmptyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResultColumn((EmptyBean)update.getRemovedObject());
                  }

                  if (original.getResultColumns() == null || original.getResultColumns().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("SqlCachingName")) {
                  original.setSqlCachingName(proposed.getSqlCachingName());
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
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            SqlCachingBeanImpl copy = (SqlCachingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ResultColumns")) && this.bean.isResultColumnsSet() && !copy._isSet(2)) {
               EmptyBean[] oldResultColumns = this.bean.getResultColumns();
               EmptyBean[] newResultColumns = new EmptyBean[oldResultColumns.length];

               for(i = 0; i < newResultColumns.length; ++i) {
                  newResultColumns[i] = (EmptyBean)((EmptyBean)this.createCopy((AbstractDescriptorBean)oldResultColumns[i], includeObsolete));
               }

               copy.setResultColumns(newResultColumns);
            }

            if ((excludeProps == null || !excludeProps.contains("SqlCachingName")) && this.bean.isSqlCachingNameSet()) {
               copy.setSqlCachingName(this.bean.getSqlCachingName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tables")) && this.bean.isTablesSet() && !copy._isSet(3)) {
               TableBean[] oldTables = this.bean.getTables();
               TableBean[] newTables = new TableBean[oldTables.length];

               for(i = 0; i < newTables.length; ++i) {
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
         this.inferSubTree(this.bean.getResultColumns(), clazz, annotation);
         this.inferSubTree(this.bean.getTables(), clazz, annotation);
      }
   }
}
