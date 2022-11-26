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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicRdbmsJarBeanImpl extends AbstractDescriptorBean implements WeblogicRdbmsJarBean, Serializable {
   private CompatibilityBean _Compatibility;
   private String _CreateDefaultDbmsTables;
   private String _DatabaseType;
   private String _DefaultDbmsTablesDdl;
   private boolean _EnableBatchOperations;
   private String _Id;
   private boolean _OrderDatabaseOperations;
   private String _ValidateDbSchemaWith;
   private String _Version;
   private WeblogicRdbmsBeanBean[] _WeblogicRdbmsBeans;
   private WeblogicRdbmsRelationBean[] _WeblogicRdbmsRelations;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicRdbmsJarBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsJarBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsJarBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addWeblogicRdbmsBean(WeblogicRdbmsBeanBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         WeblogicRdbmsBeanBean[] _new;
         if (this._isSet(0)) {
            _new = (WeblogicRdbmsBeanBean[])((WeblogicRdbmsBeanBean[])this._getHelper()._extendArray(this.getWeblogicRdbmsBeans(), WeblogicRdbmsBeanBean.class, param0));
         } else {
            _new = new WeblogicRdbmsBeanBean[]{param0};
         }

         try {
            this.setWeblogicRdbmsBeans(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WeblogicRdbmsBeanBean[] getWeblogicRdbmsBeans() {
      return this._WeblogicRdbmsBeans;
   }

   public boolean isWeblogicRdbmsBeansInherited() {
      return false;
   }

   public boolean isWeblogicRdbmsBeansSet() {
      return this._isSet(0);
   }

   public void removeWeblogicRdbmsBean(WeblogicRdbmsBeanBean param0) {
      this.destroyWeblogicRdbmsBean(param0);
   }

   public void setWeblogicRdbmsBeans(WeblogicRdbmsBeanBean[] param0) throws InvalidAttributeValueException {
      WeblogicRdbmsBeanBean[] param0 = param0 == null ? new WeblogicRdbmsBeanBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WeblogicRdbmsBeanBean[] _oldVal = this._WeblogicRdbmsBeans;
      this._WeblogicRdbmsBeans = (WeblogicRdbmsBeanBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public WeblogicRdbmsBeanBean createWeblogicRdbmsBean() {
      WeblogicRdbmsBeanBeanImpl _val = new WeblogicRdbmsBeanBeanImpl(this, -1);

      try {
         this.addWeblogicRdbmsBean(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWeblogicRdbmsBean(WeblogicRdbmsBeanBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         WeblogicRdbmsBeanBean[] _old = this.getWeblogicRdbmsBeans();
         WeblogicRdbmsBeanBean[] _new = (WeblogicRdbmsBeanBean[])((WeblogicRdbmsBeanBean[])this._getHelper()._removeElement(_old, WeblogicRdbmsBeanBean.class, param0));
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
               this.setWeblogicRdbmsBeans(_new);
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

   public void addWeblogicRdbmsRelation(WeblogicRdbmsRelationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         WeblogicRdbmsRelationBean[] _new;
         if (this._isSet(1)) {
            _new = (WeblogicRdbmsRelationBean[])((WeblogicRdbmsRelationBean[])this._getHelper()._extendArray(this.getWeblogicRdbmsRelations(), WeblogicRdbmsRelationBean.class, param0));
         } else {
            _new = new WeblogicRdbmsRelationBean[]{param0};
         }

         try {
            this.setWeblogicRdbmsRelations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WeblogicRdbmsRelationBean[] getWeblogicRdbmsRelations() {
      return this._WeblogicRdbmsRelations;
   }

   public boolean isWeblogicRdbmsRelationsInherited() {
      return false;
   }

   public boolean isWeblogicRdbmsRelationsSet() {
      return this._isSet(1);
   }

   public void removeWeblogicRdbmsRelation(WeblogicRdbmsRelationBean param0) {
      this.destroyWeblogicRdbmsRelation(param0);
   }

   public void setWeblogicRdbmsRelations(WeblogicRdbmsRelationBean[] param0) throws InvalidAttributeValueException {
      WeblogicRdbmsRelationBean[] param0 = param0 == null ? new WeblogicRdbmsRelationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WeblogicRdbmsRelationBean[] _oldVal = this._WeblogicRdbmsRelations;
      this._WeblogicRdbmsRelations = (WeblogicRdbmsRelationBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public WeblogicRdbmsRelationBean createWeblogicRdbmsRelation() {
      WeblogicRdbmsRelationBeanImpl _val = new WeblogicRdbmsRelationBeanImpl(this, -1);

      try {
         this.addWeblogicRdbmsRelation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWeblogicRdbmsRelation(WeblogicRdbmsRelationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         WeblogicRdbmsRelationBean[] _old = this.getWeblogicRdbmsRelations();
         WeblogicRdbmsRelationBean[] _new = (WeblogicRdbmsRelationBean[])((WeblogicRdbmsRelationBean[])this._getHelper()._removeElement(_old, WeblogicRdbmsRelationBean.class, param0));
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
               this.setWeblogicRdbmsRelations(_new);
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

   public boolean isOrderDatabaseOperations() {
      return this._OrderDatabaseOperations;
   }

   public boolean isOrderDatabaseOperationsInherited() {
      return false;
   }

   public boolean isOrderDatabaseOperationsSet() {
      return this._isSet(2);
   }

   public void setOrderDatabaseOperations(boolean param0) {
      boolean _oldVal = this._OrderDatabaseOperations;
      this._OrderDatabaseOperations = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isEnableBatchOperations() {
      return this._EnableBatchOperations;
   }

   public boolean isEnableBatchOperationsInherited() {
      return false;
   }

   public boolean isEnableBatchOperationsSet() {
      return this._isSet(3);
   }

   public void setEnableBatchOperations(boolean param0) {
      boolean _oldVal = this._EnableBatchOperations;
      this._EnableBatchOperations = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getCreateDefaultDbmsTables() {
      return this._CreateDefaultDbmsTables;
   }

   public boolean isCreateDefaultDbmsTablesInherited() {
      return false;
   }

   public boolean isCreateDefaultDbmsTablesSet() {
      return this._isSet(4);
   }

   public void setCreateDefaultDbmsTables(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"CreateOnly", "Disabled", "DropAndCreate", "DropAndCreateAlways", "AlterOrCreate"};
      param0 = LegalChecks.checkInEnum("CreateDefaultDbmsTables", param0, _set);
      String _oldVal = this._CreateDefaultDbmsTables;
      this._CreateDefaultDbmsTables = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getValidateDbSchemaWith() {
      return this._ValidateDbSchemaWith;
   }

   public boolean isValidateDbSchemaWithInherited() {
      return false;
   }

   public boolean isValidateDbSchemaWithSet() {
      return this._isSet(5);
   }

   public void setValidateDbSchemaWith(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"MetaData", "TableQuery"};
      param0 = LegalChecks.checkInEnum("ValidateDbSchemaWith", param0, _set);
      String _oldVal = this._ValidateDbSchemaWith;
      this._ValidateDbSchemaWith = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getDatabaseType() {
      return !this._isSet(6) ? null : this._DatabaseType;
   }

   public boolean isDatabaseTypeInherited() {
      return false;
   }

   public boolean isDatabaseTypeSet() {
      return this._isSet(6);
   }

   public void setDatabaseType(String param0) {
      if (param0 == null) {
         this._unSet(6);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"DB2", "Informix", "Oracle", "SQLServer", "SQLServer2000", "Sybase", "PointBase", "MySQL", "Derby", "TimesTen"};
         param0 = LegalChecks.checkInEnum("DatabaseType", param0, _set);
         String _oldVal = this._DatabaseType;
         this._DatabaseType = param0;
         this._postSet(6, _oldVal, param0);
      }
   }

   public String getDefaultDbmsTablesDdl() {
      return this._DefaultDbmsTablesDdl;
   }

   public boolean isDefaultDbmsTablesDdlInherited() {
      return false;
   }

   public boolean isDefaultDbmsTablesDdlSet() {
      return this._isSet(7);
   }

   public void setDefaultDbmsTablesDdl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultDbmsTablesDdl;
      this._DefaultDbmsTablesDdl = param0;
      this._postSet(7, _oldVal, param0);
   }

   public CompatibilityBean getCompatibility() {
      return this._Compatibility;
   }

   public boolean isCompatibilityInherited() {
      return false;
   }

   public boolean isCompatibilitySet() {
      return this._isSet(8);
   }

   public void setCompatibility(CompatibilityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCompatibility() != null && param0 != this.getCompatibility()) {
         throw new BeanAlreadyExistsException(this.getCompatibility() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CompatibilityBean _oldVal = this._Compatibility;
         this._Compatibility = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public CompatibilityBean createCompatibility() {
      CompatibilityBeanImpl _val = new CompatibilityBeanImpl(this, -1);

      try {
         this.setCompatibility(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCompatibility(CompatibilityBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Compatibility;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCompatibility((CompatibilityBean)null);
               this._unSet(8);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(9);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(10);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(10, _oldVal, param0);
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._Compatibility = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._CreateDefaultDbmsTables = "Disabled";
               if (initOne) {
                  break;
               }
            case 6:
               this._DatabaseType = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._DefaultDbmsTablesDdl = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ValidateDbSchemaWith = "TableQuery";
               if (initOne) {
                  break;
               }
            case 10:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._WeblogicRdbmsBeans = new WeblogicRdbmsBeanBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._WeblogicRdbmsRelations = new WeblogicRdbmsRelationBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._EnableBatchOperations = true;
               if (initOne) {
                  break;
               }
            case 2:
               this._OrderDatabaseOperations = true;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar/1.0/weblogic-rdbms20-persistence.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar";
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
                  return 9;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 24:
            default:
               break;
            case 7:
               if (s.equals("version")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("compatibility")) {
                  return 8;
               }

               if (s.equals("database-type")) {
                  return 6;
               }
               break;
            case 19:
               if (s.equals("weblogic-rdbms-bean")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("default-dbms-tables-ddl")) {
                  return 7;
               }

               if (s.equals("validate-db-schema-with")) {
                  return 5;
               }

               if (s.equals("weblogic-rdbms-relation")) {
                  return 1;
               }

               if (s.equals("enable-batch-operations")) {
                  return 3;
               }
               break;
            case 25:
               if (s.equals("order-database-operations")) {
                  return 2;
               }
               break;
            case 26:
               if (s.equals("create-default-dbms-tables")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new WeblogicRdbmsBeanBeanImpl.SchemaHelper2();
            case 1:
               return new WeblogicRdbmsRelationBeanImpl.SchemaHelper2();
            case 8:
               return new CompatibilityBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-rdbms-jar";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "weblogic-rdbms-bean";
            case 1:
               return "weblogic-rdbms-relation";
            case 2:
               return "order-database-operations";
            case 3:
               return "enable-batch-operations";
            case 4:
               return "create-default-dbms-tables";
            case 5:
               return "validate-db-schema-with";
            case 6:
               return "database-type";
            case 7:
               return "default-dbms-tables-ddl";
            case 8:
               return "compatibility";
            case 9:
               return "id";
            case 10:
               return "version";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 8:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicRdbmsJarBeanImpl bean;

      protected Helper(WeblogicRdbmsJarBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WeblogicRdbmsBeans";
            case 1:
               return "WeblogicRdbmsRelations";
            case 2:
               return "OrderDatabaseOperations";
            case 3:
               return "EnableBatchOperations";
            case 4:
               return "CreateDefaultDbmsTables";
            case 5:
               return "ValidateDbSchemaWith";
            case 6:
               return "DatabaseType";
            case 7:
               return "DefaultDbmsTablesDdl";
            case 8:
               return "Compatibility";
            case 9:
               return "Id";
            case 10:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Compatibility")) {
            return 8;
         } else if (propName.equals("CreateDefaultDbmsTables")) {
            return 4;
         } else if (propName.equals("DatabaseType")) {
            return 6;
         } else if (propName.equals("DefaultDbmsTablesDdl")) {
            return 7;
         } else if (propName.equals("Id")) {
            return 9;
         } else if (propName.equals("ValidateDbSchemaWith")) {
            return 5;
         } else if (propName.equals("Version")) {
            return 10;
         } else if (propName.equals("WeblogicRdbmsBeans")) {
            return 0;
         } else if (propName.equals("WeblogicRdbmsRelations")) {
            return 1;
         } else if (propName.equals("EnableBatchOperations")) {
            return 3;
         } else {
            return propName.equals("OrderDatabaseOperations") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCompatibility() != null) {
            iterators.add(new ArrayIterator(new CompatibilityBean[]{this.bean.getCompatibility()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWeblogicRdbmsBeans()));
         iterators.add(new ArrayIterator(this.bean.getWeblogicRdbmsRelations()));
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
            childValue = this.computeChildHashValue(this.bean.getCompatibility());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCreateDefaultDbmsTablesSet()) {
               buf.append("CreateDefaultDbmsTables");
               buf.append(String.valueOf(this.bean.getCreateDefaultDbmsTables()));
            }

            if (this.bean.isDatabaseTypeSet()) {
               buf.append("DatabaseType");
               buf.append(String.valueOf(this.bean.getDatabaseType()));
            }

            if (this.bean.isDefaultDbmsTablesDdlSet()) {
               buf.append("DefaultDbmsTablesDdl");
               buf.append(String.valueOf(this.bean.getDefaultDbmsTablesDdl()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isValidateDbSchemaWithSet()) {
               buf.append("ValidateDbSchemaWith");
               buf.append(String.valueOf(this.bean.getValidateDbSchemaWith()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getWeblogicRdbmsBeans().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWeblogicRdbmsBeans()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWeblogicRdbmsRelations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWeblogicRdbmsRelations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnableBatchOperationsSet()) {
               buf.append("EnableBatchOperations");
               buf.append(String.valueOf(this.bean.isEnableBatchOperations()));
            }

            if (this.bean.isOrderDatabaseOperationsSet()) {
               buf.append("OrderDatabaseOperations");
               buf.append(String.valueOf(this.bean.isOrderDatabaseOperations()));
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
            WeblogicRdbmsJarBeanImpl otherTyped = (WeblogicRdbmsJarBeanImpl)other;
            this.computeChildDiff("Compatibility", this.bean.getCompatibility(), otherTyped.getCompatibility(), false);
            this.computeDiff("CreateDefaultDbmsTables", this.bean.getCreateDefaultDbmsTables(), otherTyped.getCreateDefaultDbmsTables(), false);
            this.computeDiff("DatabaseType", this.bean.getDatabaseType(), otherTyped.getDatabaseType(), false);
            this.computeDiff("DefaultDbmsTablesDdl", this.bean.getDefaultDbmsTablesDdl(), otherTyped.getDefaultDbmsTablesDdl(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ValidateDbSchemaWith", this.bean.getValidateDbSchemaWith(), otherTyped.getValidateDbSchemaWith(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeChildDiff("WeblogicRdbmsBeans", this.bean.getWeblogicRdbmsBeans(), otherTyped.getWeblogicRdbmsBeans(), false);
            this.computeChildDiff("WeblogicRdbmsRelations", this.bean.getWeblogicRdbmsRelations(), otherTyped.getWeblogicRdbmsRelations(), false);
            this.computeDiff("EnableBatchOperations", this.bean.isEnableBatchOperations(), otherTyped.isEnableBatchOperations(), false);
            this.computeDiff("OrderDatabaseOperations", this.bean.isOrderDatabaseOperations(), otherTyped.isOrderDatabaseOperations(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicRdbmsJarBeanImpl original = (WeblogicRdbmsJarBeanImpl)event.getSourceBean();
            WeblogicRdbmsJarBeanImpl proposed = (WeblogicRdbmsJarBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Compatibility")) {
                  if (type == 2) {
                     original.setCompatibility((CompatibilityBean)this.createCopy((AbstractDescriptorBean)proposed.getCompatibility()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Compatibility", (DescriptorBean)original.getCompatibility());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("CreateDefaultDbmsTables")) {
                  original.setCreateDefaultDbmsTables(proposed.getCreateDefaultDbmsTables());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("DatabaseType")) {
                  original.setDatabaseType(proposed.getDatabaseType());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DefaultDbmsTablesDdl")) {
                  original.setDefaultDbmsTablesDdl(proposed.getDefaultDbmsTablesDdl());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("ValidateDbSchemaWith")) {
                  original.setValidateDbSchemaWith(proposed.getValidateDbSchemaWith());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("WeblogicRdbmsBeans")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWeblogicRdbmsBean((WeblogicRdbmsBeanBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWeblogicRdbmsBean((WeblogicRdbmsBeanBean)update.getRemovedObject());
                  }

                  if (original.getWeblogicRdbmsBeans() == null || original.getWeblogicRdbmsBeans().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("WeblogicRdbmsRelations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWeblogicRdbmsRelation((WeblogicRdbmsRelationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWeblogicRdbmsRelation((WeblogicRdbmsRelationBean)update.getRemovedObject());
                  }

                  if (original.getWeblogicRdbmsRelations() == null || original.getWeblogicRdbmsRelations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("EnableBatchOperations")) {
                  original.setEnableBatchOperations(proposed.isEnableBatchOperations());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("OrderDatabaseOperations")) {
                  original.setOrderDatabaseOperations(proposed.isOrderDatabaseOperations());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            WeblogicRdbmsJarBeanImpl copy = (WeblogicRdbmsJarBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Compatibility")) && this.bean.isCompatibilitySet() && !copy._isSet(8)) {
               Object o = this.bean.getCompatibility();
               copy.setCompatibility((CompatibilityBean)null);
               copy.setCompatibility(o == null ? null : (CompatibilityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CreateDefaultDbmsTables")) && this.bean.isCreateDefaultDbmsTablesSet()) {
               copy.setCreateDefaultDbmsTables(this.bean.getCreateDefaultDbmsTables());
            }

            if ((excludeProps == null || !excludeProps.contains("DatabaseType")) && this.bean.isDatabaseTypeSet()) {
               copy.setDatabaseType(this.bean.getDatabaseType());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDbmsTablesDdl")) && this.bean.isDefaultDbmsTablesDdlSet()) {
               copy.setDefaultDbmsTablesDdl(this.bean.getDefaultDbmsTablesDdl());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateDbSchemaWith")) && this.bean.isValidateDbSchemaWithSet()) {
               copy.setValidateDbSchemaWith(this.bean.getValidateDbSchemaWith());
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("WeblogicRdbmsBeans")) && this.bean.isWeblogicRdbmsBeansSet() && !copy._isSet(0)) {
               WeblogicRdbmsBeanBean[] oldWeblogicRdbmsBeans = this.bean.getWeblogicRdbmsBeans();
               WeblogicRdbmsBeanBean[] newWeblogicRdbmsBeans = new WeblogicRdbmsBeanBean[oldWeblogicRdbmsBeans.length];

               for(i = 0; i < newWeblogicRdbmsBeans.length; ++i) {
                  newWeblogicRdbmsBeans[i] = (WeblogicRdbmsBeanBean)((WeblogicRdbmsBeanBean)this.createCopy((AbstractDescriptorBean)oldWeblogicRdbmsBeans[i], includeObsolete));
               }

               copy.setWeblogicRdbmsBeans(newWeblogicRdbmsBeans);
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicRdbmsRelations")) && this.bean.isWeblogicRdbmsRelationsSet() && !copy._isSet(1)) {
               WeblogicRdbmsRelationBean[] oldWeblogicRdbmsRelations = this.bean.getWeblogicRdbmsRelations();
               WeblogicRdbmsRelationBean[] newWeblogicRdbmsRelations = new WeblogicRdbmsRelationBean[oldWeblogicRdbmsRelations.length];

               for(i = 0; i < newWeblogicRdbmsRelations.length; ++i) {
                  newWeblogicRdbmsRelations[i] = (WeblogicRdbmsRelationBean)((WeblogicRdbmsRelationBean)this.createCopy((AbstractDescriptorBean)oldWeblogicRdbmsRelations[i], includeObsolete));
               }

               copy.setWeblogicRdbmsRelations(newWeblogicRdbmsRelations);
            }

            if ((excludeProps == null || !excludeProps.contains("EnableBatchOperations")) && this.bean.isEnableBatchOperationsSet()) {
               copy.setEnableBatchOperations(this.bean.isEnableBatchOperations());
            }

            if ((excludeProps == null || !excludeProps.contains("OrderDatabaseOperations")) && this.bean.isOrderDatabaseOperationsSet()) {
               copy.setOrderDatabaseOperations(this.bean.isOrderDatabaseOperations());
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
         this.inferSubTree(this.bean.getCompatibility(), clazz, annotation);
         this.inferSubTree(this.bean.getWeblogicRdbmsBeans(), clazz, annotation);
         this.inferSubTree(this.bean.getWeblogicRdbmsRelations(), clazz, annotation);
      }
   }
}
