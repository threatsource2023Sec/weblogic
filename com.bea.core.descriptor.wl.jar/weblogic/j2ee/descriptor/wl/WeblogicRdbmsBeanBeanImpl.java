package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl60.BaseWeblogicRdbmsBeanBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicRdbmsBeanBeanImpl extends BaseWeblogicRdbmsBeanBeanImpl implements WeblogicRdbmsBeanBean, Serializable {
   private AutomaticKeyGenerationBean _AutomaticKeyGeneration;
   private String _CategoryCmpField;
   private boolean _CheckExistsOnMethod;
   private boolean _ClusterInvalidationDisabled;
   private String _DataSourceJNDIName;
   private String _DelayDatabaseInsertUntil;
   private String _EjbName;
   private FieldGroupBean[] _FieldGroups;
   private String _Id;
   private String _InstanceLockOrder;
   private int _LockOrder;
   private RelationshipCachingBean[] _RelationshipCachings;
   private SqlShapeBean[] _SqlShapes;
   private TableMapBean[] _TableMaps;
   private UnknownPrimaryKeyFieldBean _UnknownPrimaryKeyField;
   private boolean _UseInnerJoin;
   private boolean _UseSelectForUpdate;
   private WeblogicQueryBean[] _WeblogicQueries;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicRdbmsBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(0);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDataSourceJNDIName() {
      return this._DataSourceJNDIName;
   }

   public boolean isDataSourceJNDINameInherited() {
      return false;
   }

   public boolean isDataSourceJNDINameSet() {
      return this._isSet(2);
   }

   public void setDataSourceJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceJNDIName;
      this._DataSourceJNDIName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public UnknownPrimaryKeyFieldBean getUnknownPrimaryKeyField() {
      return this._UnknownPrimaryKeyField;
   }

   public boolean isUnknownPrimaryKeyFieldInherited() {
      return false;
   }

   public boolean isUnknownPrimaryKeyFieldSet() {
      return this._isSet(3);
   }

   public void setUnknownPrimaryKeyField(UnknownPrimaryKeyFieldBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getUnknownPrimaryKeyField() != null && param0 != this.getUnknownPrimaryKeyField()) {
         throw new BeanAlreadyExistsException(this.getUnknownPrimaryKeyField() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         UnknownPrimaryKeyFieldBean _oldVal = this._UnknownPrimaryKeyField;
         this._UnknownPrimaryKeyField = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public UnknownPrimaryKeyFieldBean createUnknownPrimaryKeyField() {
      UnknownPrimaryKeyFieldBeanImpl _val = new UnknownPrimaryKeyFieldBeanImpl(this, -1);

      try {
         this.setUnknownPrimaryKeyField(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyUnknownPrimaryKeyField(UnknownPrimaryKeyFieldBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._UnknownPrimaryKeyField;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setUnknownPrimaryKeyField((UnknownPrimaryKeyFieldBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addTableMap(TableMapBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         TableMapBean[] _new;
         if (this._isSet(4)) {
            _new = (TableMapBean[])((TableMapBean[])this._getHelper()._extendArray(this.getTableMaps(), TableMapBean.class, param0));
         } else {
            _new = new TableMapBean[]{param0};
         }

         try {
            this.setTableMaps(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TableMapBean[] getTableMaps() {
      return this._TableMaps;
   }

   public boolean isTableMapsInherited() {
      return false;
   }

   public boolean isTableMapsSet() {
      return this._isSet(4);
   }

   public void removeTableMap(TableMapBean param0) {
      this.destroyTableMap(param0);
   }

   public void setTableMaps(TableMapBean[] param0) throws InvalidAttributeValueException {
      TableMapBean[] param0 = param0 == null ? new TableMapBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TableMapBean[] _oldVal = this._TableMaps;
      this._TableMaps = (TableMapBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public TableMapBean createTableMap() {
      TableMapBeanImpl _val = new TableMapBeanImpl(this, -1);

      try {
         this.addTableMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTableMap(TableMapBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         TableMapBean[] _old = this.getTableMaps();
         TableMapBean[] _new = (TableMapBean[])((TableMapBean[])this._getHelper()._removeElement(_old, TableMapBean.class, param0));
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
               this.setTableMaps(_new);
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

   public void addFieldGroup(FieldGroupBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         FieldGroupBean[] _new;
         if (this._isSet(5)) {
            _new = (FieldGroupBean[])((FieldGroupBean[])this._getHelper()._extendArray(this.getFieldGroups(), FieldGroupBean.class, param0));
         } else {
            _new = new FieldGroupBean[]{param0};
         }

         try {
            this.setFieldGroups(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FieldGroupBean[] getFieldGroups() {
      return this._FieldGroups;
   }

   public boolean isFieldGroupsInherited() {
      return false;
   }

   public boolean isFieldGroupsSet() {
      return this._isSet(5);
   }

   public void removeFieldGroup(FieldGroupBean param0) {
      this.destroyFieldGroup(param0);
   }

   public void setFieldGroups(FieldGroupBean[] param0) throws InvalidAttributeValueException {
      FieldGroupBean[] param0 = param0 == null ? new FieldGroupBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FieldGroupBean[] _oldVal = this._FieldGroups;
      this._FieldGroups = (FieldGroupBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public FieldGroupBean createFieldGroup() {
      FieldGroupBeanImpl _val = new FieldGroupBeanImpl(this, -1);

      try {
         this.addFieldGroup(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFieldGroup(FieldGroupBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         FieldGroupBean[] _old = this.getFieldGroups();
         FieldGroupBean[] _new = (FieldGroupBean[])((FieldGroupBean[])this._getHelper()._removeElement(_old, FieldGroupBean.class, param0));
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
               this.setFieldGroups(_new);
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

   public void addRelationshipCaching(RelationshipCachingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         RelationshipCachingBean[] _new;
         if (this._isSet(6)) {
            _new = (RelationshipCachingBean[])((RelationshipCachingBean[])this._getHelper()._extendArray(this.getRelationshipCachings(), RelationshipCachingBean.class, param0));
         } else {
            _new = new RelationshipCachingBean[]{param0};
         }

         try {
            this.setRelationshipCachings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RelationshipCachingBean[] getRelationshipCachings() {
      return this._RelationshipCachings;
   }

   public boolean isRelationshipCachingsInherited() {
      return false;
   }

   public boolean isRelationshipCachingsSet() {
      return this._isSet(6);
   }

   public void removeRelationshipCaching(RelationshipCachingBean param0) {
      this.destroyRelationshipCaching(param0);
   }

   public void setRelationshipCachings(RelationshipCachingBean[] param0) throws InvalidAttributeValueException {
      RelationshipCachingBean[] param0 = param0 == null ? new RelationshipCachingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      RelationshipCachingBean[] _oldVal = this._RelationshipCachings;
      this._RelationshipCachings = (RelationshipCachingBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public RelationshipCachingBean createRelationshipCaching() {
      RelationshipCachingBeanImpl _val = new RelationshipCachingBeanImpl(this, -1);

      try {
         this.addRelationshipCaching(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRelationshipCaching(RelationshipCachingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         RelationshipCachingBean[] _old = this.getRelationshipCachings();
         RelationshipCachingBean[] _new = (RelationshipCachingBean[])((RelationshipCachingBean[])this._getHelper()._removeElement(_old, RelationshipCachingBean.class, param0));
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
               this.setRelationshipCachings(_new);
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

   public void addSqlShape(SqlShapeBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         SqlShapeBean[] _new;
         if (this._isSet(7)) {
            _new = (SqlShapeBean[])((SqlShapeBean[])this._getHelper()._extendArray(this.getSqlShapes(), SqlShapeBean.class, param0));
         } else {
            _new = new SqlShapeBean[]{param0};
         }

         try {
            this.setSqlShapes(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SqlShapeBean[] getSqlShapes() {
      return this._SqlShapes;
   }

   public boolean isSqlShapesInherited() {
      return false;
   }

   public boolean isSqlShapesSet() {
      return this._isSet(7);
   }

   public void removeSqlShape(SqlShapeBean param0) {
      this.destroySqlShape(param0);
   }

   public void setSqlShapes(SqlShapeBean[] param0) throws InvalidAttributeValueException {
      SqlShapeBean[] param0 = param0 == null ? new SqlShapeBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SqlShapeBean[] _oldVal = this._SqlShapes;
      this._SqlShapes = (SqlShapeBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public SqlShapeBean createSqlShape() {
      SqlShapeBeanImpl _val = new SqlShapeBeanImpl(this, -1);

      try {
         this.addSqlShape(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySqlShape(SqlShapeBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         SqlShapeBean[] _old = this.getSqlShapes();
         SqlShapeBean[] _new = (SqlShapeBean[])((SqlShapeBean[])this._getHelper()._removeElement(_old, SqlShapeBean.class, param0));
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
               this.setSqlShapes(_new);
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

   public void addWeblogicQuery(WeblogicQueryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         WeblogicQueryBean[] _new;
         if (this._isSet(8)) {
            _new = (WeblogicQueryBean[])((WeblogicQueryBean[])this._getHelper()._extendArray(this.getWeblogicQueries(), WeblogicQueryBean.class, param0));
         } else {
            _new = new WeblogicQueryBean[]{param0};
         }

         try {
            this.setWeblogicQueries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WeblogicQueryBean[] getWeblogicQueries() {
      return this._WeblogicQueries;
   }

   public boolean isWeblogicQueriesInherited() {
      return false;
   }

   public boolean isWeblogicQueriesSet() {
      return this._isSet(8);
   }

   public void removeWeblogicQuery(WeblogicQueryBean param0) {
      this.destroyWeblogicQuery(param0);
   }

   public void setWeblogicQueries(WeblogicQueryBean[] param0) throws InvalidAttributeValueException {
      WeblogicQueryBean[] param0 = param0 == null ? new WeblogicQueryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WeblogicQueryBean[] _oldVal = this._WeblogicQueries;
      this._WeblogicQueries = (WeblogicQueryBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public WeblogicQueryBean createWeblogicQuery() {
      WeblogicQueryBeanImpl _val = new WeblogicQueryBeanImpl(this, -1);

      try {
         this.addWeblogicQuery(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWeblogicQuery(WeblogicQueryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         WeblogicQueryBean[] _old = this.getWeblogicQueries();
         WeblogicQueryBean[] _new = (WeblogicQueryBean[])((WeblogicQueryBean[])this._getHelper()._removeElement(_old, WeblogicQueryBean.class, param0));
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
               this.setWeblogicQueries(_new);
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

   public String getDelayDatabaseInsertUntil() {
      return this._DelayDatabaseInsertUntil;
   }

   public boolean isDelayDatabaseInsertUntilInherited() {
      return false;
   }

   public boolean isDelayDatabaseInsertUntilSet() {
      return this._isSet(9);
   }

   public void setDelayDatabaseInsertUntil(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ejbCreate", "ejbPostCreate"};
      param0 = LegalChecks.checkInEnum("DelayDatabaseInsertUntil", param0, _set);
      String _oldVal = this._DelayDatabaseInsertUntil;
      this._DelayDatabaseInsertUntil = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isUseSelectForUpdate() {
      return this._UseSelectForUpdate;
   }

   public boolean isUseSelectForUpdateInherited() {
      return false;
   }

   public boolean isUseSelectForUpdateSet() {
      return this._isSet(10);
   }

   public void setUseSelectForUpdate(boolean param0) {
      boolean _oldVal = this._UseSelectForUpdate;
      this._UseSelectForUpdate = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getLockOrder() {
      return this._LockOrder;
   }

   public boolean isLockOrderInherited() {
      return false;
   }

   public boolean isLockOrderSet() {
      return this._isSet(11);
   }

   public void setLockOrder(int param0) {
      LegalChecks.checkMin("LockOrder", param0, 0);
      int _oldVal = this._LockOrder;
      this._LockOrder = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getInstanceLockOrder() {
      return this._InstanceLockOrder;
   }

   public boolean isInstanceLockOrderInherited() {
      return false;
   }

   public boolean isInstanceLockOrderSet() {
      return this._isSet(12);
   }

   public void setInstanceLockOrder(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"AccessOrder", "ValueOrder"};
      param0 = LegalChecks.checkInEnum("InstanceLockOrder", param0, _set);
      String _oldVal = this._InstanceLockOrder;
      this._InstanceLockOrder = param0;
      this._postSet(12, _oldVal, param0);
   }

   public AutomaticKeyGenerationBean getAutomaticKeyGeneration() {
      return this._AutomaticKeyGeneration;
   }

   public boolean isAutomaticKeyGenerationInherited() {
      return false;
   }

   public boolean isAutomaticKeyGenerationSet() {
      return this._isSet(13);
   }

   public void setAutomaticKeyGeneration(AutomaticKeyGenerationBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAutomaticKeyGeneration() != null && param0 != this.getAutomaticKeyGeneration()) {
         throw new BeanAlreadyExistsException(this.getAutomaticKeyGeneration() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 13)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AutomaticKeyGenerationBean _oldVal = this._AutomaticKeyGeneration;
         this._AutomaticKeyGeneration = param0;
         this._postSet(13, _oldVal, param0);
      }
   }

   public AutomaticKeyGenerationBean createAutomaticKeyGeneration() {
      AutomaticKeyGenerationBeanImpl _val = new AutomaticKeyGenerationBeanImpl(this, -1);

      try {
         this.setAutomaticKeyGeneration(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAutomaticKeyGeneration(AutomaticKeyGenerationBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AutomaticKeyGeneration;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAutomaticKeyGeneration((AutomaticKeyGenerationBean)null);
               this._unSet(13);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean isCheckExistsOnMethod() {
      return this._CheckExistsOnMethod;
   }

   public boolean isCheckExistsOnMethodInherited() {
      return false;
   }

   public boolean isCheckExistsOnMethodSet() {
      return this._isSet(14);
   }

   public void setCheckExistsOnMethod(boolean param0) {
      boolean _oldVal = this._CheckExistsOnMethod;
      this._CheckExistsOnMethod = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(15);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isClusterInvalidationDisabled() {
      return this._ClusterInvalidationDisabled;
   }

   public boolean isClusterInvalidationDisabledInherited() {
      return false;
   }

   public boolean isClusterInvalidationDisabledSet() {
      return this._isSet(16);
   }

   public void setClusterInvalidationDisabled(boolean param0) {
      boolean _oldVal = this._ClusterInvalidationDisabled;
      this._ClusterInvalidationDisabled = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isUseInnerJoin() {
      return this._UseInnerJoin;
   }

   public boolean isUseInnerJoinInherited() {
      return false;
   }

   public boolean isUseInnerJoinSet() {
      return this._isSet(17);
   }

   public void setUseInnerJoin(boolean param0) {
      boolean _oldVal = this._UseInnerJoin;
      this._UseInnerJoin = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getCategoryCmpField() {
      return this._CategoryCmpField;
   }

   public boolean isCategoryCmpFieldInherited() {
      return false;
   }

   public boolean isCategoryCmpFieldSet() {
      return this._isSet(18);
   }

   public void setCategoryCmpField(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CategoryCmpField;
      this._CategoryCmpField = param0;
      this._postSet(18, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEjbName();
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
         case 8:
            if (s.equals("ejb-name")) {
               return info.compareXpaths(this._getPropertyXpath("ejb-name"));
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._AutomaticKeyGeneration = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._CategoryCmpField = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._DataSourceJNDIName = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._DelayDatabaseInsertUntil = "ejbPostCreate";
               if (initOne) {
                  break;
               }
            case 0:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._FieldGroups = new FieldGroupBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._InstanceLockOrder = "AccessOrder";
               if (initOne) {
                  break;
               }
            case 11:
               this._LockOrder = 0;
               if (initOne) {
                  break;
               }
            case 6:
               this._RelationshipCachings = new RelationshipCachingBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._SqlShapes = new SqlShapeBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._TableMaps = new TableMapBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._UnknownPrimaryKeyField = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._WeblogicQueries = new WeblogicQueryBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._CheckExistsOnMethod = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._ClusterInvalidationDisabled = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._UseInnerJoin = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._UseSelectForUpdate = false;
               if (initOne) {
                  break;
               }
            case 1:
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

   public static class SchemaHelper2 extends BaseWeblogicRdbmsBeanBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 15;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 23:
            case 26:
            case 28:
            default:
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 0;
               }
               break;
            case 9:
               if (s.equals("sql-shape")) {
                  return 7;
               }

               if (s.equals("table-map")) {
                  return 4;
               }
               break;
            case 10:
               if (s.equals("lock-order")) {
                  return 11;
               }
               break;
            case 11:
               if (s.equals("field-group")) {
                  return 5;
               }
               break;
            case 14:
               if (s.equals("weblogic-query")) {
                  return 8;
               }

               if (s.equals("use-inner-join")) {
                  return 17;
               }
               break;
            case 18:
               if (s.equals("category-cmp-field")) {
                  return 18;
               }
               break;
            case 19:
               if (s.equals("instance-lock-order")) {
                  return 12;
               }
               break;
            case 20:
               if (s.equals("relationship-caching")) {
                  return 6;
               }
               break;
            case 21:
               if (s.equals("data-source-jndi-name")) {
                  return 2;
               }

               if (s.equals("use-select-for-update")) {
                  return 10;
               }
               break;
            case 22:
               if (s.equals("check-exists-on-method")) {
                  return 14;
               }
               break;
            case 24:
               if (s.equals("automatic-key-generation")) {
                  return 13;
               }
               break;
            case 25:
               if (s.equals("unknown-primary-key-field")) {
                  return 3;
               }
               break;
            case 27:
               if (s.equals("delay-database-insert-until")) {
                  return 9;
               }
               break;
            case 29:
               if (s.equals("cluster-invalidation-disabled")) {
                  return 16;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new UnknownPrimaryKeyFieldBeanImpl.SchemaHelper2();
            case 4:
               return new TableMapBeanImpl.SchemaHelper2();
            case 5:
               return new FieldGroupBeanImpl.SchemaHelper2();
            case 6:
               return new RelationshipCachingBeanImpl.SchemaHelper2();
            case 7:
               return new SqlShapeBeanImpl.SchemaHelper2();
            case 8:
               return new WeblogicQueryBeanImpl.SchemaHelper2();
            case 9:
            case 10:
            case 11:
            case 12:
            default:
               return super.getSchemaHelper(propIndex);
            case 13:
               return new AutomaticKeyGenerationBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ejb-name";
            case 1:
            default:
               return super.getElementName(propIndex);
            case 2:
               return "data-source-jndi-name";
            case 3:
               return "unknown-primary-key-field";
            case 4:
               return "table-map";
            case 5:
               return "field-group";
            case 6:
               return "relationship-caching";
            case 7:
               return "sql-shape";
            case 8:
               return "weblogic-query";
            case 9:
               return "delay-database-insert-until";
            case 10:
               return "use-select-for-update";
            case 11:
               return "lock-order";
            case 12:
               return "instance-lock-order";
            case 13:
               return "automatic-key-generation";
            case 14:
               return "check-exists-on-method";
            case 15:
               return "id";
            case 16:
               return "cluster-invalidation-disabled";
            case 17:
               return "use-inner-join";
            case 18:
               return "category-cmp-field";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
            case 10:
            case 11:
            case 12:
            default:
               return super.isBean(propIndex);
            case 13:
               return true;
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
         indices.add("ejb-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends BaseWeblogicRdbmsBeanBeanImpl.Helper {
      private WeblogicRdbmsBeanBeanImpl bean;

      protected Helper(WeblogicRdbmsBeanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EjbName";
            case 1:
            default:
               return super.getPropertyName(propIndex);
            case 2:
               return "DataSourceJNDIName";
            case 3:
               return "UnknownPrimaryKeyField";
            case 4:
               return "TableMaps";
            case 5:
               return "FieldGroups";
            case 6:
               return "RelationshipCachings";
            case 7:
               return "SqlShapes";
            case 8:
               return "WeblogicQueries";
            case 9:
               return "DelayDatabaseInsertUntil";
            case 10:
               return "UseSelectForUpdate";
            case 11:
               return "LockOrder";
            case 12:
               return "InstanceLockOrder";
            case 13:
               return "AutomaticKeyGeneration";
            case 14:
               return "CheckExistsOnMethod";
            case 15:
               return "Id";
            case 16:
               return "ClusterInvalidationDisabled";
            case 17:
               return "UseInnerJoin";
            case 18:
               return "CategoryCmpField";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutomaticKeyGeneration")) {
            return 13;
         } else if (propName.equals("CategoryCmpField")) {
            return 18;
         } else if (propName.equals("DataSourceJNDIName")) {
            return 2;
         } else if (propName.equals("DelayDatabaseInsertUntil")) {
            return 9;
         } else if (propName.equals("EjbName")) {
            return 0;
         } else if (propName.equals("FieldGroups")) {
            return 5;
         } else if (propName.equals("Id")) {
            return 15;
         } else if (propName.equals("InstanceLockOrder")) {
            return 12;
         } else if (propName.equals("LockOrder")) {
            return 11;
         } else if (propName.equals("RelationshipCachings")) {
            return 6;
         } else if (propName.equals("SqlShapes")) {
            return 7;
         } else if (propName.equals("TableMaps")) {
            return 4;
         } else if (propName.equals("UnknownPrimaryKeyField")) {
            return 3;
         } else if (propName.equals("WeblogicQueries")) {
            return 8;
         } else if (propName.equals("CheckExistsOnMethod")) {
            return 14;
         } else if (propName.equals("ClusterInvalidationDisabled")) {
            return 16;
         } else if (propName.equals("UseInnerJoin")) {
            return 17;
         } else {
            return propName.equals("UseSelectForUpdate") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAutomaticKeyGeneration() != null) {
            iterators.add(new ArrayIterator(new AutomaticKeyGenerationBean[]{this.bean.getAutomaticKeyGeneration()}));
         }

         iterators.add(new ArrayIterator(this.bean.getFieldGroups()));
         iterators.add(new ArrayIterator(this.bean.getRelationshipCachings()));
         iterators.add(new ArrayIterator(this.bean.getSqlShapes()));
         iterators.add(new ArrayIterator(this.bean.getTableMaps()));
         if (this.bean.getUnknownPrimaryKeyField() != null) {
            iterators.add(new ArrayIterator(new UnknownPrimaryKeyFieldBean[]{this.bean.getUnknownPrimaryKeyField()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWeblogicQueries()));
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
            childValue = this.computeChildHashValue(this.bean.getAutomaticKeyGeneration());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCategoryCmpFieldSet()) {
               buf.append("CategoryCmpField");
               buf.append(String.valueOf(this.bean.getCategoryCmpField()));
            }

            if (this.bean.isDataSourceJNDINameSet()) {
               buf.append("DataSourceJNDIName");
               buf.append(String.valueOf(this.bean.getDataSourceJNDIName()));
            }

            if (this.bean.isDelayDatabaseInsertUntilSet()) {
               buf.append("DelayDatabaseInsertUntil");
               buf.append(String.valueOf(this.bean.getDelayDatabaseInsertUntil()));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getFieldGroups().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFieldGroups()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInstanceLockOrderSet()) {
               buf.append("InstanceLockOrder");
               buf.append(String.valueOf(this.bean.getInstanceLockOrder()));
            }

            if (this.bean.isLockOrderSet()) {
               buf.append("LockOrder");
               buf.append(String.valueOf(this.bean.getLockOrder()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRelationshipCachings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRelationshipCachings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSqlShapes().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSqlShapes()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTableMaps().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTableMaps()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getUnknownPrimaryKeyField());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWeblogicQueries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWeblogicQueries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCheckExistsOnMethodSet()) {
               buf.append("CheckExistsOnMethod");
               buf.append(String.valueOf(this.bean.isCheckExistsOnMethod()));
            }

            if (this.bean.isClusterInvalidationDisabledSet()) {
               buf.append("ClusterInvalidationDisabled");
               buf.append(String.valueOf(this.bean.isClusterInvalidationDisabled()));
            }

            if (this.bean.isUseInnerJoinSet()) {
               buf.append("UseInnerJoin");
               buf.append(String.valueOf(this.bean.isUseInnerJoin()));
            }

            if (this.bean.isUseSelectForUpdateSet()) {
               buf.append("UseSelectForUpdate");
               buf.append(String.valueOf(this.bean.isUseSelectForUpdate()));
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
            WeblogicRdbmsBeanBeanImpl otherTyped = (WeblogicRdbmsBeanBeanImpl)other;
            this.computeChildDiff("AutomaticKeyGeneration", this.bean.getAutomaticKeyGeneration(), otherTyped.getAutomaticKeyGeneration(), false);
            this.computeDiff("CategoryCmpField", this.bean.getCategoryCmpField(), otherTyped.getCategoryCmpField(), false);
            this.computeDiff("DataSourceJNDIName", this.bean.getDataSourceJNDIName(), otherTyped.getDataSourceJNDIName(), false);
            this.computeDiff("DelayDatabaseInsertUntil", this.bean.getDelayDatabaseInsertUntil(), otherTyped.getDelayDatabaseInsertUntil(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeChildDiff("FieldGroups", this.bean.getFieldGroups(), otherTyped.getFieldGroups(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InstanceLockOrder", this.bean.getInstanceLockOrder(), otherTyped.getInstanceLockOrder(), false);
            this.computeDiff("LockOrder", this.bean.getLockOrder(), otherTyped.getLockOrder(), false);
            this.computeChildDiff("RelationshipCachings", this.bean.getRelationshipCachings(), otherTyped.getRelationshipCachings(), false);
            this.computeChildDiff("SqlShapes", this.bean.getSqlShapes(), otherTyped.getSqlShapes(), false);
            this.computeChildDiff("TableMaps", this.bean.getTableMaps(), otherTyped.getTableMaps(), false);
            this.computeChildDiff("UnknownPrimaryKeyField", this.bean.getUnknownPrimaryKeyField(), otherTyped.getUnknownPrimaryKeyField(), false);
            this.computeChildDiff("WeblogicQueries", this.bean.getWeblogicQueries(), otherTyped.getWeblogicQueries(), false);
            this.computeDiff("CheckExistsOnMethod", this.bean.isCheckExistsOnMethod(), otherTyped.isCheckExistsOnMethod(), false);
            this.computeDiff("ClusterInvalidationDisabled", this.bean.isClusterInvalidationDisabled(), otherTyped.isClusterInvalidationDisabled(), false);
            this.computeDiff("UseInnerJoin", this.bean.isUseInnerJoin(), otherTyped.isUseInnerJoin(), false);
            this.computeDiff("UseSelectForUpdate", this.bean.isUseSelectForUpdate(), otherTyped.isUseSelectForUpdate(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicRdbmsBeanBeanImpl original = (WeblogicRdbmsBeanBeanImpl)event.getSourceBean();
            WeblogicRdbmsBeanBeanImpl proposed = (WeblogicRdbmsBeanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutomaticKeyGeneration")) {
                  if (type == 2) {
                     original.setAutomaticKeyGeneration((AutomaticKeyGenerationBean)this.createCopy((AbstractDescriptorBean)proposed.getAutomaticKeyGeneration()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AutomaticKeyGeneration", (DescriptorBean)original.getAutomaticKeyGeneration());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("CategoryCmpField")) {
                  original.setCategoryCmpField(proposed.getCategoryCmpField());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DataSourceJNDIName")) {
                  original.setDataSourceJNDIName(proposed.getDataSourceJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DelayDatabaseInsertUntil")) {
                  original.setDelayDatabaseInsertUntil(proposed.getDelayDatabaseInsertUntil());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("FieldGroups")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFieldGroup((FieldGroupBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFieldGroup((FieldGroupBean)update.getRemovedObject());
                  }

                  if (original.getFieldGroups() == null || original.getFieldGroups().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("InstanceLockOrder")) {
                  original.setInstanceLockOrder(proposed.getInstanceLockOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LockOrder")) {
                  original.setLockOrder(proposed.getLockOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RelationshipCachings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addRelationshipCaching((RelationshipCachingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRelationshipCaching((RelationshipCachingBean)update.getRemovedObject());
                  }

                  if (original.getRelationshipCachings() == null || original.getRelationshipCachings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("SqlShapes")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSqlShape((SqlShapeBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSqlShape((SqlShapeBean)update.getRemovedObject());
                  }

                  if (original.getSqlShapes() == null || original.getSqlShapes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("TableMaps")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTableMap((TableMapBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTableMap((TableMapBean)update.getRemovedObject());
                  }

                  if (original.getTableMaps() == null || original.getTableMaps().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("UnknownPrimaryKeyField")) {
                  if (type == 2) {
                     original.setUnknownPrimaryKeyField((UnknownPrimaryKeyFieldBean)this.createCopy((AbstractDescriptorBean)proposed.getUnknownPrimaryKeyField()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("UnknownPrimaryKeyField", (DescriptorBean)original.getUnknownPrimaryKeyField());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("WeblogicQueries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWeblogicQuery((WeblogicQueryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWeblogicQuery((WeblogicQueryBean)update.getRemovedObject());
                  }

                  if (original.getWeblogicQueries() == null || original.getWeblogicQueries().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("CheckExistsOnMethod")) {
                  original.setCheckExistsOnMethod(proposed.isCheckExistsOnMethod());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ClusterInvalidationDisabled")) {
                  original.setClusterInvalidationDisabled(proposed.isClusterInvalidationDisabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("UseInnerJoin")) {
                  original.setUseInnerJoin(proposed.isUseInnerJoin());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("UseSelectForUpdate")) {
                  original.setUseSelectForUpdate(proposed.isUseSelectForUpdate());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            WeblogicRdbmsBeanBeanImpl copy = (WeblogicRdbmsBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutomaticKeyGeneration")) && this.bean.isAutomaticKeyGenerationSet() && !copy._isSet(13)) {
               Object o = this.bean.getAutomaticKeyGeneration();
               copy.setAutomaticKeyGeneration((AutomaticKeyGenerationBean)null);
               copy.setAutomaticKeyGeneration(o == null ? null : (AutomaticKeyGenerationBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CategoryCmpField")) && this.bean.isCategoryCmpFieldSet()) {
               copy.setCategoryCmpField(this.bean.getCategoryCmpField());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceJNDIName")) && this.bean.isDataSourceJNDINameSet()) {
               copy.setDataSourceJNDIName(this.bean.getDataSourceJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("DelayDatabaseInsertUntil")) && this.bean.isDelayDatabaseInsertUntilSet()) {
               copy.setDelayDatabaseInsertUntil(this.bean.getDelayDatabaseInsertUntil());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("FieldGroups")) && this.bean.isFieldGroupsSet() && !copy._isSet(5)) {
               FieldGroupBean[] oldFieldGroups = this.bean.getFieldGroups();
               FieldGroupBean[] newFieldGroups = new FieldGroupBean[oldFieldGroups.length];

               for(i = 0; i < newFieldGroups.length; ++i) {
                  newFieldGroups[i] = (FieldGroupBean)((FieldGroupBean)this.createCopy((AbstractDescriptorBean)oldFieldGroups[i], includeObsolete));
               }

               copy.setFieldGroups(newFieldGroups);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InstanceLockOrder")) && this.bean.isInstanceLockOrderSet()) {
               copy.setInstanceLockOrder(this.bean.getInstanceLockOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("LockOrder")) && this.bean.isLockOrderSet()) {
               copy.setLockOrder(this.bean.getLockOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("RelationshipCachings")) && this.bean.isRelationshipCachingsSet() && !copy._isSet(6)) {
               RelationshipCachingBean[] oldRelationshipCachings = this.bean.getRelationshipCachings();
               RelationshipCachingBean[] newRelationshipCachings = new RelationshipCachingBean[oldRelationshipCachings.length];

               for(i = 0; i < newRelationshipCachings.length; ++i) {
                  newRelationshipCachings[i] = (RelationshipCachingBean)((RelationshipCachingBean)this.createCopy((AbstractDescriptorBean)oldRelationshipCachings[i], includeObsolete));
               }

               copy.setRelationshipCachings(newRelationshipCachings);
            }

            if ((excludeProps == null || !excludeProps.contains("SqlShapes")) && this.bean.isSqlShapesSet() && !copy._isSet(7)) {
               SqlShapeBean[] oldSqlShapes = this.bean.getSqlShapes();
               SqlShapeBean[] newSqlShapes = new SqlShapeBean[oldSqlShapes.length];

               for(i = 0; i < newSqlShapes.length; ++i) {
                  newSqlShapes[i] = (SqlShapeBean)((SqlShapeBean)this.createCopy((AbstractDescriptorBean)oldSqlShapes[i], includeObsolete));
               }

               copy.setSqlShapes(newSqlShapes);
            }

            if ((excludeProps == null || !excludeProps.contains("TableMaps")) && this.bean.isTableMapsSet() && !copy._isSet(4)) {
               TableMapBean[] oldTableMaps = this.bean.getTableMaps();
               TableMapBean[] newTableMaps = new TableMapBean[oldTableMaps.length];

               for(i = 0; i < newTableMaps.length; ++i) {
                  newTableMaps[i] = (TableMapBean)((TableMapBean)this.createCopy((AbstractDescriptorBean)oldTableMaps[i], includeObsolete));
               }

               copy.setTableMaps(newTableMaps);
            }

            if ((excludeProps == null || !excludeProps.contains("UnknownPrimaryKeyField")) && this.bean.isUnknownPrimaryKeyFieldSet() && !copy._isSet(3)) {
               Object o = this.bean.getUnknownPrimaryKeyField();
               copy.setUnknownPrimaryKeyField((UnknownPrimaryKeyFieldBean)null);
               copy.setUnknownPrimaryKeyField(o == null ? null : (UnknownPrimaryKeyFieldBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicQueries")) && this.bean.isWeblogicQueriesSet() && !copy._isSet(8)) {
               WeblogicQueryBean[] oldWeblogicQueries = this.bean.getWeblogicQueries();
               WeblogicQueryBean[] newWeblogicQueries = new WeblogicQueryBean[oldWeblogicQueries.length];

               for(i = 0; i < newWeblogicQueries.length; ++i) {
                  newWeblogicQueries[i] = (WeblogicQueryBean)((WeblogicQueryBean)this.createCopy((AbstractDescriptorBean)oldWeblogicQueries[i], includeObsolete));
               }

               copy.setWeblogicQueries(newWeblogicQueries);
            }

            if ((excludeProps == null || !excludeProps.contains("CheckExistsOnMethod")) && this.bean.isCheckExistsOnMethodSet()) {
               copy.setCheckExistsOnMethod(this.bean.isCheckExistsOnMethod());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterInvalidationDisabled")) && this.bean.isClusterInvalidationDisabledSet()) {
               copy.setClusterInvalidationDisabled(this.bean.isClusterInvalidationDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UseInnerJoin")) && this.bean.isUseInnerJoinSet()) {
               copy.setUseInnerJoin(this.bean.isUseInnerJoin());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSelectForUpdate")) && this.bean.isUseSelectForUpdateSet()) {
               copy.setUseSelectForUpdate(this.bean.isUseSelectForUpdate());
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
         this.inferSubTree(this.bean.getAutomaticKeyGeneration(), clazz, annotation);
         this.inferSubTree(this.bean.getFieldGroups(), clazz, annotation);
         this.inferSubTree(this.bean.getRelationshipCachings(), clazz, annotation);
         this.inferSubTree(this.bean.getSqlShapes(), clazz, annotation);
         this.inferSubTree(this.bean.getTableMaps(), clazz, annotation);
         this.inferSubTree(this.bean.getUnknownPrimaryKeyField(), clazz, annotation);
         this.inferSubTree(this.bean.getWeblogicQueries(), clazz, annotation);
      }
   }
}
