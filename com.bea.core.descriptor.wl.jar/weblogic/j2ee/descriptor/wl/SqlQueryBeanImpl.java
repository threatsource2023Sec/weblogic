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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SqlQueryBeanImpl extends AbstractDescriptorBean implements SqlQueryBean, Serializable {
   private DatabaseSpecificSqlBean[] _DatabaseSpecificSqls;
   private String _Sql;
   private String _SqlShapeName;
   private static SchemaHelper2 _schemaHelper;

   public SqlQueryBeanImpl() {
      this._initializeProperty(-1);
   }

   public SqlQueryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SqlQueryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSqlShapeName() {
      return this._SqlShapeName;
   }

   public boolean isSqlShapeNameInherited() {
      return false;
   }

   public boolean isSqlShapeNameSet() {
      return this._isSet(0);
   }

   public void setSqlShapeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SqlShapeName;
      this._SqlShapeName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getSql() {
      return this._Sql;
   }

   public boolean isSqlInherited() {
      return false;
   }

   public boolean isSqlSet() {
      return this._isSet(1);
   }

   public void setSql(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Sql;
      this._Sql = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addDatabaseSpecificSql(DatabaseSpecificSqlBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         DatabaseSpecificSqlBean[] _new;
         if (this._isSet(2)) {
            _new = (DatabaseSpecificSqlBean[])((DatabaseSpecificSqlBean[])this._getHelper()._extendArray(this.getDatabaseSpecificSqls(), DatabaseSpecificSqlBean.class, param0));
         } else {
            _new = new DatabaseSpecificSqlBean[]{param0};
         }

         try {
            this.setDatabaseSpecificSqls(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DatabaseSpecificSqlBean[] getDatabaseSpecificSqls() {
      return this._DatabaseSpecificSqls;
   }

   public boolean isDatabaseSpecificSqlsInherited() {
      return false;
   }

   public boolean isDatabaseSpecificSqlsSet() {
      return this._isSet(2);
   }

   public void removeDatabaseSpecificSql(DatabaseSpecificSqlBean param0) {
      this.destroyDatabaseSpecificSql(param0);
   }

   public void setDatabaseSpecificSqls(DatabaseSpecificSqlBean[] param0) throws InvalidAttributeValueException {
      DatabaseSpecificSqlBean[] param0 = param0 == null ? new DatabaseSpecificSqlBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DatabaseSpecificSqlBean[] _oldVal = this._DatabaseSpecificSqls;
      this._DatabaseSpecificSqls = (DatabaseSpecificSqlBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public DatabaseSpecificSqlBean createDatabaseSpecificSql() {
      DatabaseSpecificSqlBeanImpl _val = new DatabaseSpecificSqlBeanImpl(this, -1);

      try {
         this.addDatabaseSpecificSql(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDatabaseSpecificSql(DatabaseSpecificSqlBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         DatabaseSpecificSqlBean[] _old = this.getDatabaseSpecificSqls();
         DatabaseSpecificSqlBean[] _new = (DatabaseSpecificSqlBean[])((DatabaseSpecificSqlBean[])this._getHelper()._removeElement(_old, DatabaseSpecificSqlBean.class, param0));
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
               this.setDatabaseSpecificSqls(_new);
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
               this._DatabaseSpecificSqls = new DatabaseSpecificSqlBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Sql = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._SqlShapeName = null;
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
            case 3:
               if (s.equals("sql")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("sql-shape-name")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("database-specific-sql")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new DatabaseSpecificSqlBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "sql-shape-name";
            case 1:
               return "sql";
            case 2:
               return "database-specific-sql";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
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
      private SqlQueryBeanImpl bean;

      protected Helper(SqlQueryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SqlShapeName";
            case 1:
               return "Sql";
            case 2:
               return "DatabaseSpecificSqls";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DatabaseSpecificSqls")) {
            return 2;
         } else if (propName.equals("Sql")) {
            return 1;
         } else {
            return propName.equals("SqlShapeName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getDatabaseSpecificSqls()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getDatabaseSpecificSqls().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDatabaseSpecificSqls()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSqlSet()) {
               buf.append("Sql");
               buf.append(String.valueOf(this.bean.getSql()));
            }

            if (this.bean.isSqlShapeNameSet()) {
               buf.append("SqlShapeName");
               buf.append(String.valueOf(this.bean.getSqlShapeName()));
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
            SqlQueryBeanImpl otherTyped = (SqlQueryBeanImpl)other;
            this.computeChildDiff("DatabaseSpecificSqls", this.bean.getDatabaseSpecificSqls(), otherTyped.getDatabaseSpecificSqls(), false);
            this.computeDiff("Sql", this.bean.getSql(), otherTyped.getSql(), false);
            this.computeDiff("SqlShapeName", this.bean.getSqlShapeName(), otherTyped.getSqlShapeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SqlQueryBeanImpl original = (SqlQueryBeanImpl)event.getSourceBean();
            SqlQueryBeanImpl proposed = (SqlQueryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DatabaseSpecificSqls")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDatabaseSpecificSql((DatabaseSpecificSqlBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDatabaseSpecificSql((DatabaseSpecificSqlBean)update.getRemovedObject());
                  }

                  if (original.getDatabaseSpecificSqls() == null || original.getDatabaseSpecificSqls().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Sql")) {
                  original.setSql(proposed.getSql());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SqlShapeName")) {
                  original.setSqlShapeName(proposed.getSqlShapeName());
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
            SqlQueryBeanImpl copy = (SqlQueryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DatabaseSpecificSqls")) && this.bean.isDatabaseSpecificSqlsSet() && !copy._isSet(2)) {
               DatabaseSpecificSqlBean[] oldDatabaseSpecificSqls = this.bean.getDatabaseSpecificSqls();
               DatabaseSpecificSqlBean[] newDatabaseSpecificSqls = new DatabaseSpecificSqlBean[oldDatabaseSpecificSqls.length];

               for(int i = 0; i < newDatabaseSpecificSqls.length; ++i) {
                  newDatabaseSpecificSqls[i] = (DatabaseSpecificSqlBean)((DatabaseSpecificSqlBean)this.createCopy((AbstractDescriptorBean)oldDatabaseSpecificSqls[i], includeObsolete));
               }

               copy.setDatabaseSpecificSqls(newDatabaseSpecificSqls);
            }

            if ((excludeProps == null || !excludeProps.contains("Sql")) && this.bean.isSqlSet()) {
               copy.setSql(this.bean.getSql());
            }

            if ((excludeProps == null || !excludeProps.contains("SqlShapeName")) && this.bean.isSqlShapeNameSet()) {
               copy.setSqlShapeName(this.bean.getSqlShapeName());
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
         this.inferSubTree(this.bean.getDatabaseSpecificSqls(), clazz, annotation);
      }
   }
}
