package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.customizers.JavaEEModuleNameCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConnectorBeanImpl extends JavaEEModuleNameBeanImpl implements ConnectorBean, Serializable {
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private String _EisType;
   private IconBean[] _Icons;
   private String _Id;
   private String _JavaEEModuleName;
   private LicenseBean _License;
   private boolean _MetadataComplete;
   private String _ModuleName;
   private String[] _RequiredWorkContexts;
   private ResourceAdapterBean _ResourceAdapter;
   private String _ResourceAdapterVersion;
   private String _VendorName;
   private String _Version;
   private transient JavaEEModuleNameCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ConnectorBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new JavaEEModuleNameCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ConnectorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new JavaEEModuleNameCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ConnectorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         this._customizer = new JavaEEModuleNameCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getJavaEEModuleName() {
      return this._customizer.getJavaEEModuleName();
   }

   public String getModuleName() {
      return this._ModuleName;
   }

   public boolean isJavaEEModuleNameInherited() {
      return false;
   }

   public boolean isJavaEEModuleNameSet() {
      return this._isSet(0);
   }

   public boolean isModuleNameInherited() {
      return false;
   }

   public boolean isModuleNameSet() {
      return this._isSet(1);
   }

   public void setJavaEEModuleName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaEEModuleName;
      this._JavaEEModuleName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setModuleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleName;
      this._ModuleName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(2);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(2)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(3);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDisplayNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDisplayNames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDisplayName(String param0) {
      String[] _old = this.getDisplayNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDisplayNames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDisplayNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DisplayNames;
      this._DisplayNames = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addIcon(IconBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         IconBean[] _new;
         if (this._isSet(4)) {
            _new = (IconBean[])((IconBean[])this._getHelper()._extendArray(this.getIcons(), IconBean.class, param0));
         } else {
            _new = new IconBean[]{param0};
         }

         try {
            this.setIcons(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public IconBean[] getIcons() {
      return this._Icons;
   }

   public boolean isIconsInherited() {
      return false;
   }

   public boolean isIconsSet() {
      return this._isSet(4);
   }

   public void removeIcon(IconBean param0) {
      this.destroyIcon(param0);
   }

   public void setIcons(IconBean[] param0) throws InvalidAttributeValueException {
      IconBean[] param0 = param0 == null ? new IconBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      IconBean[] _oldVal = this._Icons;
      this._Icons = (IconBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public IconBean createIcon() {
      IconBeanImpl _val = new IconBeanImpl(this, -1);

      try {
         this.addIcon(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyIcon(IconBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         IconBean[] _old = this.getIcons();
         IconBean[] _new = (IconBean[])((IconBean[])this._getHelper()._removeElement(_old, IconBean.class, param0));
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
               this.setIcons(_new);
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

   public String getVendorName() {
      return this._VendorName;
   }

   public boolean isVendorNameInherited() {
      return false;
   }

   public boolean isVendorNameSet() {
      return this._isSet(5);
   }

   public void setVendorName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VendorName;
      this._VendorName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getEisType() {
      return this._EisType;
   }

   public boolean isEisTypeInherited() {
      return false;
   }

   public boolean isEisTypeSet() {
      return this._isSet(6);
   }

   public void setEisType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EisType;
      this._EisType = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getResourceAdapterVersion() {
      return this._ResourceAdapterVersion;
   }

   public boolean isResourceAdapterVersionInherited() {
      return false;
   }

   public boolean isResourceAdapterVersionSet() {
      return this._isSet(7);
   }

   public void setResourceAdapterVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceAdapterVersion;
      this._ResourceAdapterVersion = param0;
      this._postSet(7, _oldVal, param0);
   }

   public LicenseBean getLicense() {
      return this._License;
   }

   public boolean isLicenseInherited() {
      return false;
   }

   public boolean isLicenseSet() {
      return this._isSet(8);
   }

   public void setLicense(LicenseBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLicense() != null && param0 != this.getLicense()) {
         throw new BeanAlreadyExistsException(this.getLicense() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LicenseBean _oldVal = this._License;
         this._License = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public LicenseBean createLicense() {
      LicenseBeanImpl _val = new LicenseBeanImpl(this, -1);

      try {
         this.setLicense(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLicense(LicenseBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._License;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLicense((LicenseBean)null);
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

   public String[] getRequiredWorkContexts() {
      return this._RequiredWorkContexts;
   }

   public boolean isRequiredWorkContextsInherited() {
      return false;
   }

   public boolean isRequiredWorkContextsSet() {
      return this._isSet(9);
   }

   public void setRequiredWorkContexts(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._RequiredWorkContexts;
      this._RequiredWorkContexts = param0;
      this._postSet(9, _oldVal, param0);
   }

   public void addRequiredWorkContext(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(9)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getRequiredWorkContexts(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setRequiredWorkContexts(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeRequiredWorkContext(String param0) {
      String[] _old = this.getRequiredWorkContexts();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setRequiredWorkContexts(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public ResourceAdapterBean getResourceAdapter() {
      return this._ResourceAdapter;
   }

   public boolean isResourceAdapterInherited() {
      return false;
   }

   public boolean isResourceAdapterSet() {
      return this._isSet(10);
   }

   public void setResourceAdapter(ResourceAdapterBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getResourceAdapter() != null && param0 != this.getResourceAdapter()) {
         throw new BeanAlreadyExistsException(this.getResourceAdapter() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 10)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ResourceAdapterBean _oldVal = this._ResourceAdapter;
         this._ResourceAdapter = param0;
         this._postSet(10, _oldVal, param0);
      }
   }

   public ResourceAdapterBean createResourceAdapter() {
      ResourceAdapterBeanImpl _val = new ResourceAdapterBeanImpl(this, -1);

      try {
         this.setResourceAdapter(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceAdapter(ResourceAdapterBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ResourceAdapter;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResourceAdapter((ResourceAdapterBean)null);
               this._unSet(10);
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

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(11);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isMetadataComplete() {
      return this._MetadataComplete;
   }

   public boolean isMetadataCompleteInherited() {
      return false;
   }

   public boolean isMetadataCompleteSet() {
      return this._isSet(12);
   }

   public void setMetadataComplete(boolean param0) {
      boolean _oldVal = this._MetadataComplete;
      this._MetadataComplete = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(13);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(13, _oldVal, param0);
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
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._EisType = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JavaEEModuleName = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._License = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ModuleName = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._RequiredWorkContexts = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._ResourceAdapter = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._ResourceAdapterVersion = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._VendorName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._Version = "1.6";
               if (initOne) {
                  break;
               }
            case 12:
               this._MetadataComplete = false;
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

   public static class SchemaHelper2 extends JavaEEModuleNameBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 13;
               }
            case 3:
            case 5:
            case 6:
            case 9:
            case 10:
            case 13:
            case 14:
            case 16:
            case 19:
            case 20:
            case 22:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 4;
               }
               break;
            case 7:
               if (s.equals("license")) {
                  return 8;
               }

               if (s.equals("version")) {
                  return 11;
               }
               break;
            case 8:
               if (s.equals("eis-type")) {
                  return 6;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 2;
               }

               if (s.equals("module-name")) {
                  return 1;
               }

               if (s.equals("vendor-name")) {
                  return 5;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("resourceadapter")) {
                  return 10;
               }
               break;
            case 17:
               if (s.equals("metadata-complete")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("javaee-module-name")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("required-work-context")) {
                  return 9;
               }
               break;
            case 23:
               if (s.equals("resourceadapter-version")) {
                  return 7;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new IconBeanImpl.SchemaHelper2();
            case 8:
               return new LicenseBeanImpl.SchemaHelper2();
            case 10:
               return new ResourceAdapterBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "connector";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "javaee-module-name";
            case 1:
               return "module-name";
            case 2:
               return "description";
            case 3:
               return "display-name";
            case 4:
               return "icon";
            case 5:
               return "vendor-name";
            case 6:
               return "eis-type";
            case 7:
               return "resourceadapter-version";
            case 8:
               return "license";
            case 9:
               return "required-work-context";
            case 10:
               return "resourceadapter";
            case 11:
               return "version";
            case 12:
               return "metadata-complete";
            case 13:
               return "id";
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
            case 4:
               return true;
            case 5:
            case 6:
            case 7:
            case 8:
            default:
               return super.isArray(propIndex);
            case 9:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 8:
               return true;
            case 10:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends JavaEEModuleNameBeanImpl.Helper {
      private ConnectorBeanImpl bean;

      protected Helper(ConnectorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JavaEEModuleName";
            case 1:
               return "ModuleName";
            case 2:
               return "Descriptions";
            case 3:
               return "DisplayNames";
            case 4:
               return "Icons";
            case 5:
               return "VendorName";
            case 6:
               return "EisType";
            case 7:
               return "ResourceAdapterVersion";
            case 8:
               return "License";
            case 9:
               return "RequiredWorkContexts";
            case 10:
               return "ResourceAdapter";
            case 11:
               return "Version";
            case 12:
               return "MetadataComplete";
            case 13:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 2;
         } else if (propName.equals("DisplayNames")) {
            return 3;
         } else if (propName.equals("EisType")) {
            return 6;
         } else if (propName.equals("Icons")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 13;
         } else if (propName.equals("JavaEEModuleName")) {
            return 0;
         } else if (propName.equals("License")) {
            return 8;
         } else if (propName.equals("ModuleName")) {
            return 1;
         } else if (propName.equals("RequiredWorkContexts")) {
            return 9;
         } else if (propName.equals("ResourceAdapter")) {
            return 10;
         } else if (propName.equals("ResourceAdapterVersion")) {
            return 7;
         } else if (propName.equals("VendorName")) {
            return 5;
         } else if (propName.equals("Version")) {
            return 11;
         } else {
            return propName.equals("MetadataComplete") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         if (this.bean.getLicense() != null) {
            iterators.add(new ArrayIterator(new LicenseBean[]{this.bean.getLicense()}));
         }

         if (this.bean.getResourceAdapter() != null) {
            iterators.add(new ArrayIterator(new ResourceAdapterBean[]{this.bean.getResourceAdapter()}));
         }

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
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isDisplayNamesSet()) {
               buf.append("DisplayNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisplayNames())));
            }

            if (this.bean.isEisTypeSet()) {
               buf.append("EisType");
               buf.append(String.valueOf(this.bean.getEisType()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getIcons().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getIcons()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJavaEEModuleNameSet()) {
               buf.append("JavaEEModuleName");
               buf.append(String.valueOf(this.bean.getJavaEEModuleName()));
            }

            childValue = this.computeChildHashValue(this.bean.getLicense());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isModuleNameSet()) {
               buf.append("ModuleName");
               buf.append(String.valueOf(this.bean.getModuleName()));
            }

            if (this.bean.isRequiredWorkContextsSet()) {
               buf.append("RequiredWorkContexts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRequiredWorkContexts())));
            }

            childValue = this.computeChildHashValue(this.bean.getResourceAdapter());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResourceAdapterVersionSet()) {
               buf.append("ResourceAdapterVersion");
               buf.append(String.valueOf(this.bean.getResourceAdapterVersion()));
            }

            if (this.bean.isVendorNameSet()) {
               buf.append("VendorName");
               buf.append(String.valueOf(this.bean.getVendorName()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            if (this.bean.isMetadataCompleteSet()) {
               buf.append("MetadataComplete");
               buf.append(String.valueOf(this.bean.isMetadataComplete()));
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
            ConnectorBeanImpl otherTyped = (ConnectorBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeDiff("EisType", this.bean.getEisType(), otherTyped.getEisType(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JavaEEModuleName", this.bean.getJavaEEModuleName(), otherTyped.getJavaEEModuleName(), false);
            this.computeChildDiff("License", this.bean.getLicense(), otherTyped.getLicense(), false);
            this.computeDiff("ModuleName", this.bean.getModuleName(), otherTyped.getModuleName(), false);
            this.computeDiff("RequiredWorkContexts", this.bean.getRequiredWorkContexts(), otherTyped.getRequiredWorkContexts(), false);
            this.computeChildDiff("ResourceAdapter", this.bean.getResourceAdapter(), otherTyped.getResourceAdapter(), false);
            this.computeDiff("ResourceAdapterVersion", this.bean.getResourceAdapterVersion(), otherTyped.getResourceAdapterVersion(), false);
            this.computeDiff("VendorName", this.bean.getVendorName(), otherTyped.getVendorName(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeDiff("MetadataComplete", this.bean.isMetadataComplete(), otherTyped.isMetadataComplete(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectorBeanImpl original = (ConnectorBeanImpl)event.getSourceBean();
            ConnectorBeanImpl proposed = (ConnectorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("DisplayNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDisplayName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDisplayName((String)update.getRemovedObject());
                  }

                  if (original.getDisplayNames() == null || original.getDisplayNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("EisType")) {
                  original.setEisType(proposed.getEisType());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Icons")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addIcon((IconBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeIcon((IconBean)update.getRemovedObject());
                  }

                  if (original.getIcons() == null || original.getIcons().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("JavaEEModuleName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("License")) {
                  if (type == 2) {
                     original.setLicense((LicenseBean)this.createCopy((AbstractDescriptorBean)proposed.getLicense()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("License", (DescriptorBean)original.getLicense());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ModuleName")) {
                  original.setModuleName(proposed.getModuleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("RequiredWorkContexts")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addRequiredWorkContext((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRequiredWorkContext((String)update.getRemovedObject());
                  }

                  if (original.getRequiredWorkContexts() == null || original.getRequiredWorkContexts().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("ResourceAdapter")) {
                  if (type == 2) {
                     original.setResourceAdapter((ResourceAdapterBean)this.createCopy((AbstractDescriptorBean)proposed.getResourceAdapter()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ResourceAdapter", (DescriptorBean)original.getResourceAdapter());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ResourceAdapterVersion")) {
                  original.setResourceAdapterVersion(proposed.getResourceAdapterVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("VendorName")) {
                  original.setVendorName(proposed.getVendorName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MetadataComplete")) {
                  original.setMetadataComplete(proposed.isMetadataComplete());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            ConnectorBeanImpl copy = (ConnectorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EisType")) && this.bean.isEisTypeSet()) {
               copy.setEisType(this.bean.getEisType());
            }

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(4)) {
               IconBean[] oldIcons = this.bean.getIcons();
               IconBean[] newIcons = new IconBean[oldIcons.length];

               for(int i = 0; i < newIcons.length; ++i) {
                  newIcons[i] = (IconBean)((IconBean)this.createCopy((AbstractDescriptorBean)oldIcons[i], includeObsolete));
               }

               copy.setIcons(newIcons);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaEEModuleName")) && this.bean.isJavaEEModuleNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("License")) && this.bean.isLicenseSet() && !copy._isSet(8)) {
               Object o = this.bean.getLicense();
               copy.setLicense((LicenseBean)null);
               copy.setLicense(o == null ? null : (LicenseBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleName")) && this.bean.isModuleNameSet()) {
               copy.setModuleName(this.bean.getModuleName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiredWorkContexts")) && this.bean.isRequiredWorkContextsSet()) {
               o = this.bean.getRequiredWorkContexts();
               copy.setRequiredWorkContexts(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceAdapter")) && this.bean.isResourceAdapterSet() && !copy._isSet(10)) {
               Object o = this.bean.getResourceAdapter();
               copy.setResourceAdapter((ResourceAdapterBean)null);
               copy.setResourceAdapter(o == null ? null : (ResourceAdapterBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceAdapterVersion")) && this.bean.isResourceAdapterVersionSet()) {
               copy.setResourceAdapterVersion(this.bean.getResourceAdapterVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("VendorName")) && this.bean.isVendorNameSet()) {
               copy.setVendorName(this.bean.getVendorName());
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("MetadataComplete")) && this.bean.isMetadataCompleteSet()) {
               copy.setMetadataComplete(this.bean.isMetadataComplete());
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
         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getLicense(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceAdapter(), clazz, annotation);
      }
   }
}
