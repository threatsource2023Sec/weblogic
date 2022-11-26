package weblogic.j2ee.descriptor.wl60;

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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicRdbmsJarBeanImpl extends AbstractDescriptorBean implements WeblogicRdbmsJarBean, Serializable {
   private boolean _CreateDefaultDbmsTables;
   private String _DatabaseType;
   private String _ValidateDbSchemaWith;
   private WeblogicRdbmsBeanBean[] _WeblogicRdbmsBeans;
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

   public boolean isCreateDefaultDbmsTables() {
      return this._CreateDefaultDbmsTables;
   }

   public boolean isCreateDefaultDbmsTablesInherited() {
      return false;
   }

   public boolean isCreateDefaultDbmsTablesSet() {
      return this._isSet(1);
   }

   public void setCreateDefaultDbmsTables(boolean param0) {
      boolean _oldVal = this._CreateDefaultDbmsTables;
      this._CreateDefaultDbmsTables = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getValidateDbSchemaWith() {
      return this._ValidateDbSchemaWith;
   }

   public boolean isValidateDbSchemaWithInherited() {
      return false;
   }

   public boolean isValidateDbSchemaWithSet() {
      return this._isSet(2);
   }

   public void setValidateDbSchemaWith(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"MetaData", "TableQuery"};
      param0 = LegalChecks.checkInEnum("ValidateDbSchemaWith", param0, _set);
      String _oldVal = this._ValidateDbSchemaWith;
      this._ValidateDbSchemaWith = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getDatabaseType() {
      return !this._isSet(3) ? null : this._DatabaseType;
   }

   public boolean isDatabaseTypeInherited() {
      return false;
   }

   public boolean isDatabaseTypeSet() {
      return this._isSet(3);
   }

   public void setDatabaseType(String param0) {
      if (param0 == null) {
         this._unSet(3);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"DB2", "ORACLE", "SYBASE", "INFORMIX", "POINTBASE", "SQL_SERVER", "SQLSERVER"};
         param0 = LegalChecks.checkInEnum("DatabaseType", param0, _set);
         String _oldVal = this._DatabaseType;
         this._DatabaseType = param0;
         this._postSet(3, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._DatabaseType = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ValidateDbSchemaWith = "TableQuery";
               if (initOne) {
                  break;
               }
            case 0:
               this._WeblogicRdbmsBeans = new WeblogicRdbmsBeanBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._CreateDefaultDbmsTables = false;
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
            case 13:
               if (s.equals("database-type")) {
                  return 3;
               }
               break;
            case 19:
               if (s.equals("weblogic-rdbms-bean")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("validate-db-schema-with")) {
                  return 2;
               }
               break;
            case 26:
               if (s.equals("create-default-dbms-tables")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new WeblogicRdbmsBeanBeanImpl.SchemaHelper2();
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
               return "create-default-dbms-tables";
            case 2:
               return "validate-db-schema-with";
            case 3:
               return "database-type";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
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
               return "CreateDefaultDbmsTables";
            case 2:
               return "ValidateDbSchemaWith";
            case 3:
               return "DatabaseType";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DatabaseType")) {
            return 3;
         } else if (propName.equals("ValidateDbSchemaWith")) {
            return 2;
         } else if (propName.equals("WeblogicRdbmsBeans")) {
            return 0;
         } else {
            return propName.equals("CreateDefaultDbmsTables") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWeblogicRdbmsBeans()));
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
            if (this.bean.isDatabaseTypeSet()) {
               buf.append("DatabaseType");
               buf.append(String.valueOf(this.bean.getDatabaseType()));
            }

            if (this.bean.isValidateDbSchemaWithSet()) {
               buf.append("ValidateDbSchemaWith");
               buf.append(String.valueOf(this.bean.getValidateDbSchemaWith()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWeblogicRdbmsBeans().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWeblogicRdbmsBeans()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCreateDefaultDbmsTablesSet()) {
               buf.append("CreateDefaultDbmsTables");
               buf.append(String.valueOf(this.bean.isCreateDefaultDbmsTables()));
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
            this.computeDiff("DatabaseType", this.bean.getDatabaseType(), otherTyped.getDatabaseType(), false);
            this.computeDiff("ValidateDbSchemaWith", this.bean.getValidateDbSchemaWith(), otherTyped.getValidateDbSchemaWith(), false);
            this.computeChildDiff("WeblogicRdbmsBeans", this.bean.getWeblogicRdbmsBeans(), otherTyped.getWeblogicRdbmsBeans(), false);
            this.computeDiff("CreateDefaultDbmsTables", this.bean.isCreateDefaultDbmsTables(), otherTyped.isCreateDefaultDbmsTables(), false);
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
               if (prop.equals("DatabaseType")) {
                  original.setDatabaseType(proposed.getDatabaseType());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ValidateDbSchemaWith")) {
                  original.setValidateDbSchemaWith(proposed.getValidateDbSchemaWith());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
               } else if (prop.equals("CreateDefaultDbmsTables")) {
                  original.setCreateDefaultDbmsTables(proposed.isCreateDefaultDbmsTables());
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
            WeblogicRdbmsJarBeanImpl copy = (WeblogicRdbmsJarBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DatabaseType")) && this.bean.isDatabaseTypeSet()) {
               copy.setDatabaseType(this.bean.getDatabaseType());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateDbSchemaWith")) && this.bean.isValidateDbSchemaWithSet()) {
               copy.setValidateDbSchemaWith(this.bean.getValidateDbSchemaWith());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicRdbmsBeans")) && this.bean.isWeblogicRdbmsBeansSet() && !copy._isSet(0)) {
               WeblogicRdbmsBeanBean[] oldWeblogicRdbmsBeans = this.bean.getWeblogicRdbmsBeans();
               WeblogicRdbmsBeanBean[] newWeblogicRdbmsBeans = new WeblogicRdbmsBeanBean[oldWeblogicRdbmsBeans.length];

               for(int i = 0; i < newWeblogicRdbmsBeans.length; ++i) {
                  newWeblogicRdbmsBeans[i] = (WeblogicRdbmsBeanBean)((WeblogicRdbmsBeanBean)this.createCopy((AbstractDescriptorBean)oldWeblogicRdbmsBeans[i], includeObsolete));
               }

               copy.setWeblogicRdbmsBeans(newWeblogicRdbmsBeans);
            }

            if ((excludeProps == null || !excludeProps.contains("CreateDefaultDbmsTables")) && this.bean.isCreateDefaultDbmsTablesSet()) {
               copy.setCreateDefaultDbmsTables(this.bean.isCreateDefaultDbmsTables());
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
         this.inferSubTree(this.bean.getWeblogicRdbmsBeans(), clazz, annotation);
      }
   }
}
