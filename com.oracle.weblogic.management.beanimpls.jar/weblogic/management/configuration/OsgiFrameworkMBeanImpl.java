package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class OsgiFrameworkMBeanImpl extends DeploymentMBeanImpl implements OsgiFrameworkMBean, Serializable {
   private String _DeployInstallationBundles;
   private String _FactoryImplementationClass;
   private Properties _InitProperties;
   private String _Name;
   private String _OrgOsgiFrameworkBootdelegation;
   private String _OrgOsgiFrameworkSystemPackagesExtra;
   private String _OsgiImplementationLocation;
   private boolean _RegisterGlobalDataSources;
   private boolean _RegisterGlobalWorkManagers;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private OsgiFrameworkMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(OsgiFrameworkMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(OsgiFrameworkMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public OsgiFrameworkMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(OsgiFrameworkMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      OsgiFrameworkMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public OsgiFrameworkMBeanImpl() {
      this._initializeProperty(-1);
   }

   public OsgiFrameworkMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OsgiFrameworkMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._Name;
      }
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public String getOsgiImplementationLocation() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getOsgiImplementationLocation(), this) : this._OsgiImplementationLocation;
   }

   public boolean isOsgiImplementationLocationInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isOsgiImplementationLocationSet() {
      return this._isSet(12);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setOsgiImplementationLocation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._OsgiImplementationLocation;
      this._OsgiImplementationLocation = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFactoryImplementationClass() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getFactoryImplementationClass(), this) : this._FactoryImplementationClass;
   }

   public boolean isFactoryImplementationClassInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isFactoryImplementationClassSet() {
      return this._isSet(13);
   }

   public void setFactoryImplementationClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._FactoryImplementationClass;
      this._FactoryImplementationClass = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getInitProperties() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getInitProperties() : this._InitProperties;
   }

   public String getInitPropertiesAsString() {
      return StringHelper.objectToString(this.getInitProperties());
   }

   public boolean isInitPropertiesInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isInitPropertiesSet() {
      return this._isSet(14);
   }

   public void setInitPropertiesAsString(String param0) {
      try {
         this.setInitProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setInitProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      Properties _oldVal = this._InitProperties;
      this._InitProperties = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isRegisterGlobalWorkManagers() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().isRegisterGlobalWorkManagers() : this._RegisterGlobalWorkManagers;
   }

   public boolean isRegisterGlobalWorkManagersInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isRegisterGlobalWorkManagersSet() {
      return this._isSet(15);
   }

   public void setRegisterGlobalWorkManagers(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this._RegisterGlobalWorkManagers;
      this._RegisterGlobalWorkManagers = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isRegisterGlobalDataSources() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().isRegisterGlobalDataSources() : this._RegisterGlobalDataSources;
   }

   public boolean isRegisterGlobalDataSourcesInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isRegisterGlobalDataSourcesSet() {
      return this._isSet(16);
   }

   public void setRegisterGlobalDataSources(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      boolean _oldVal = this._RegisterGlobalDataSources;
      this._RegisterGlobalDataSources = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOrgOsgiFrameworkBootdelegation() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getOrgOsgiFrameworkBootdelegation(), this) : this._OrgOsgiFrameworkBootdelegation;
   }

   public boolean isOrgOsgiFrameworkBootdelegationInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isOrgOsgiFrameworkBootdelegationSet() {
      return this._isSet(17);
   }

   public void setOrgOsgiFrameworkBootdelegation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._OrgOsgiFrameworkBootdelegation;
      this._OrgOsgiFrameworkBootdelegation = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOrgOsgiFrameworkSystemPackagesExtra() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getOrgOsgiFrameworkSystemPackagesExtra(), this) : this._OrgOsgiFrameworkSystemPackagesExtra;
   }

   public boolean isOrgOsgiFrameworkSystemPackagesExtraInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isOrgOsgiFrameworkSystemPackagesExtraSet() {
      return this._isSet(18);
   }

   public void setOrgOsgiFrameworkSystemPackagesExtra(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      String _oldVal = this._OrgOsgiFrameworkSystemPackagesExtra;
      this._OrgOsgiFrameworkSystemPackagesExtra = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDeployInstallationBundles() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getDeployInstallationBundles(), this) : this._DeployInstallationBundles;
   }

   public boolean isDeployInstallationBundlesInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isDeployInstallationBundlesSet() {
      return this._isSet(19);
   }

   public void setDeployInstallationBundles(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"populate", "ignore"};
      param0 = LegalChecks.checkInEnum("DeployInstallationBundles", param0, _set);
      boolean wasSet = this._isSet(19);
      String _oldVal = this._DeployInstallationBundles;
      this._DeployInstallationBundles = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         OsgiFrameworkMBeanImpl source = (OsgiFrameworkMBeanImpl)var5.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
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
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 19;
      }

      try {
         switch (idx) {
            case 19:
               this._DeployInstallationBundles = "populate";
               if (initOne) {
                  break;
               }
            case 13:
               this._FactoryImplementationClass = "org.apache.felix.framework.FrameworkFactory";
               if (initOne) {
                  break;
               }
            case 14:
               this._InitProperties = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._OrgOsgiFrameworkBootdelegation = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._OrgOsgiFrameworkSystemPackagesExtra = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._OsgiImplementationLocation = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._RegisterGlobalDataSources = true;
               if (initOne) {
                  break;
               }
            case 15:
               this._RegisterGlobalWorkManagers = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "OsgiFramework";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("DeployInstallationBundles")) {
         oldVal = this._DeployInstallationBundles;
         this._DeployInstallationBundles = (String)v;
         this._postSet(19, oldVal, this._DeployInstallationBundles);
      } else if (name.equals("FactoryImplementationClass")) {
         oldVal = this._FactoryImplementationClass;
         this._FactoryImplementationClass = (String)v;
         this._postSet(13, oldVal, this._FactoryImplementationClass);
      } else if (name.equals("InitProperties")) {
         Properties oldVal = this._InitProperties;
         this._InitProperties = (Properties)v;
         this._postSet(14, oldVal, this._InitProperties);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("OrgOsgiFrameworkBootdelegation")) {
         oldVal = this._OrgOsgiFrameworkBootdelegation;
         this._OrgOsgiFrameworkBootdelegation = (String)v;
         this._postSet(17, oldVal, this._OrgOsgiFrameworkBootdelegation);
      } else if (name.equals("OrgOsgiFrameworkSystemPackagesExtra")) {
         oldVal = this._OrgOsgiFrameworkSystemPackagesExtra;
         this._OrgOsgiFrameworkSystemPackagesExtra = (String)v;
         this._postSet(18, oldVal, this._OrgOsgiFrameworkSystemPackagesExtra);
      } else if (name.equals("OsgiImplementationLocation")) {
         oldVal = this._OsgiImplementationLocation;
         this._OsgiImplementationLocation = (String)v;
         this._postSet(12, oldVal, this._OsgiImplementationLocation);
      } else {
         boolean oldVal;
         if (name.equals("RegisterGlobalDataSources")) {
            oldVal = this._RegisterGlobalDataSources;
            this._RegisterGlobalDataSources = (Boolean)v;
            this._postSet(16, oldVal, this._RegisterGlobalDataSources);
         } else if (name.equals("RegisterGlobalWorkManagers")) {
            oldVal = this._RegisterGlobalWorkManagers;
            this._RegisterGlobalWorkManagers = (Boolean)v;
            this._postSet(15, oldVal, this._RegisterGlobalWorkManagers);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DeployInstallationBundles")) {
         return this._DeployInstallationBundles;
      } else if (name.equals("FactoryImplementationClass")) {
         return this._FactoryImplementationClass;
      } else if (name.equals("InitProperties")) {
         return this._InitProperties;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OrgOsgiFrameworkBootdelegation")) {
         return this._OrgOsgiFrameworkBootdelegation;
      } else if (name.equals("OrgOsgiFrameworkSystemPackagesExtra")) {
         return this._OrgOsgiFrameworkSystemPackagesExtra;
      } else if (name.equals("OsgiImplementationLocation")) {
         return this._OsgiImplementationLocation;
      } else if (name.equals("RegisterGlobalDataSources")) {
         return new Boolean(this._RegisterGlobalDataSources);
      } else {
         return name.equals("RegisterGlobalWorkManagers") ? new Boolean(this._RegisterGlobalWorkManagers) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 15:
               if (s.equals("init-properties")) {
                  return 14;
               }
               break;
            case 27:
               if (s.equals("deploy-installation-bundles")) {
                  return 19;
               }
               break;
            case 28:
               if (s.equals("factory-implementation-class")) {
                  return 13;
               }

               if (s.equals("osgi-implementation-location")) {
                  return 12;
               }

               if (s.equals("register-global-data-sources")) {
                  return 16;
               }
               break;
            case 29:
               if (s.equals("register-global-work-managers")) {
                  return 15;
               }
               break;
            case 33:
               if (s.equals("org-osgi-framework-bootdelegation")) {
                  return 17;
               }
               break;
            case 40:
               if (s.equals("org-osgi-framework-system-packages-extra")) {
                  return 18;
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
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 12:
               return "osgi-implementation-location";
            case 13:
               return "factory-implementation-class";
            case 14:
               return "init-properties";
            case 15:
               return "register-global-work-managers";
            case 16:
               return "register-global-data-sources";
            case 17:
               return "org-osgi-framework-bootdelegation";
            case 18:
               return "org-osgi-framework-system-packages-extra";
            case 19:
               return "deploy-installation-bundles";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private OsgiFrameworkMBeanImpl bean;

      protected Helper(OsgiFrameworkMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 12:
               return "OsgiImplementationLocation";
            case 13:
               return "FactoryImplementationClass";
            case 14:
               return "InitProperties";
            case 15:
               return "RegisterGlobalWorkManagers";
            case 16:
               return "RegisterGlobalDataSources";
            case 17:
               return "OrgOsgiFrameworkBootdelegation";
            case 18:
               return "OrgOsgiFrameworkSystemPackagesExtra";
            case 19:
               return "DeployInstallationBundles";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DeployInstallationBundles")) {
            return 19;
         } else if (propName.equals("FactoryImplementationClass")) {
            return 13;
         } else if (propName.equals("InitProperties")) {
            return 14;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OrgOsgiFrameworkBootdelegation")) {
            return 17;
         } else if (propName.equals("OrgOsgiFrameworkSystemPackagesExtra")) {
            return 18;
         } else if (propName.equals("OsgiImplementationLocation")) {
            return 12;
         } else if (propName.equals("RegisterGlobalDataSources")) {
            return 16;
         } else {
            return propName.equals("RegisterGlobalWorkManagers") ? 15 : super.getPropertyIndex(propName);
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
            if (this.bean.isDeployInstallationBundlesSet()) {
               buf.append("DeployInstallationBundles");
               buf.append(String.valueOf(this.bean.getDeployInstallationBundles()));
            }

            if (this.bean.isFactoryImplementationClassSet()) {
               buf.append("FactoryImplementationClass");
               buf.append(String.valueOf(this.bean.getFactoryImplementationClass()));
            }

            if (this.bean.isInitPropertiesSet()) {
               buf.append("InitProperties");
               buf.append(String.valueOf(this.bean.getInitProperties()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOrgOsgiFrameworkBootdelegationSet()) {
               buf.append("OrgOsgiFrameworkBootdelegation");
               buf.append(String.valueOf(this.bean.getOrgOsgiFrameworkBootdelegation()));
            }

            if (this.bean.isOrgOsgiFrameworkSystemPackagesExtraSet()) {
               buf.append("OrgOsgiFrameworkSystemPackagesExtra");
               buf.append(String.valueOf(this.bean.getOrgOsgiFrameworkSystemPackagesExtra()));
            }

            if (this.bean.isOsgiImplementationLocationSet()) {
               buf.append("OsgiImplementationLocation");
               buf.append(String.valueOf(this.bean.getOsgiImplementationLocation()));
            }

            if (this.bean.isRegisterGlobalDataSourcesSet()) {
               buf.append("RegisterGlobalDataSources");
               buf.append(String.valueOf(this.bean.isRegisterGlobalDataSources()));
            }

            if (this.bean.isRegisterGlobalWorkManagersSet()) {
               buf.append("RegisterGlobalWorkManagers");
               buf.append(String.valueOf(this.bean.isRegisterGlobalWorkManagers()));
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
            OsgiFrameworkMBeanImpl otherTyped = (OsgiFrameworkMBeanImpl)other;
            this.computeDiff("DeployInstallationBundles", this.bean.getDeployInstallationBundles(), otherTyped.getDeployInstallationBundles(), false);
            this.computeDiff("FactoryImplementationClass", this.bean.getFactoryImplementationClass(), otherTyped.getFactoryImplementationClass(), false);
            this.computeDiff("InitProperties", this.bean.getInitProperties(), otherTyped.getInitProperties(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("OrgOsgiFrameworkBootdelegation", this.bean.getOrgOsgiFrameworkBootdelegation(), otherTyped.getOrgOsgiFrameworkBootdelegation(), false);
            this.computeDiff("OrgOsgiFrameworkSystemPackagesExtra", this.bean.getOrgOsgiFrameworkSystemPackagesExtra(), otherTyped.getOrgOsgiFrameworkSystemPackagesExtra(), false);
            this.computeDiff("OsgiImplementationLocation", this.bean.getOsgiImplementationLocation(), otherTyped.getOsgiImplementationLocation(), false);
            this.computeDiff("RegisterGlobalDataSources", this.bean.isRegisterGlobalDataSources(), otherTyped.isRegisterGlobalDataSources(), false);
            this.computeDiff("RegisterGlobalWorkManagers", this.bean.isRegisterGlobalWorkManagers(), otherTyped.isRegisterGlobalWorkManagers(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OsgiFrameworkMBeanImpl original = (OsgiFrameworkMBeanImpl)event.getSourceBean();
            OsgiFrameworkMBeanImpl proposed = (OsgiFrameworkMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DeployInstallationBundles")) {
                  original.setDeployInstallationBundles(proposed.getDeployInstallationBundles());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("FactoryImplementationClass")) {
                  original.setFactoryImplementationClass(proposed.getFactoryImplementationClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("InitProperties")) {
                  original.setInitProperties(proposed.getInitProperties() == null ? null : (Properties)proposed.getInitProperties().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("OrgOsgiFrameworkBootdelegation")) {
                  original.setOrgOsgiFrameworkBootdelegation(proposed.getOrgOsgiFrameworkBootdelegation());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("OrgOsgiFrameworkSystemPackagesExtra")) {
                  original.setOrgOsgiFrameworkSystemPackagesExtra(proposed.getOrgOsgiFrameworkSystemPackagesExtra());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("OsgiImplementationLocation")) {
                  original.setOsgiImplementationLocation(proposed.getOsgiImplementationLocation());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RegisterGlobalDataSources")) {
                  original.setRegisterGlobalDataSources(proposed.isRegisterGlobalDataSources());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("RegisterGlobalWorkManagers")) {
                  original.setRegisterGlobalWorkManagers(proposed.isRegisterGlobalWorkManagers());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
            OsgiFrameworkMBeanImpl copy = (OsgiFrameworkMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DeployInstallationBundles")) && this.bean.isDeployInstallationBundlesSet()) {
               copy.setDeployInstallationBundles(this.bean.getDeployInstallationBundles());
            }

            if ((excludeProps == null || !excludeProps.contains("FactoryImplementationClass")) && this.bean.isFactoryImplementationClassSet()) {
               copy.setFactoryImplementationClass(this.bean.getFactoryImplementationClass());
            }

            if ((excludeProps == null || !excludeProps.contains("InitProperties")) && this.bean.isInitPropertiesSet()) {
               copy.setInitProperties(this.bean.getInitProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("OrgOsgiFrameworkBootdelegation")) && this.bean.isOrgOsgiFrameworkBootdelegationSet()) {
               copy.setOrgOsgiFrameworkBootdelegation(this.bean.getOrgOsgiFrameworkBootdelegation());
            }

            if ((excludeProps == null || !excludeProps.contains("OrgOsgiFrameworkSystemPackagesExtra")) && this.bean.isOrgOsgiFrameworkSystemPackagesExtraSet()) {
               copy.setOrgOsgiFrameworkSystemPackagesExtra(this.bean.getOrgOsgiFrameworkSystemPackagesExtra());
            }

            if ((excludeProps == null || !excludeProps.contains("OsgiImplementationLocation")) && this.bean.isOsgiImplementationLocationSet()) {
               copy.setOsgiImplementationLocation(this.bean.getOsgiImplementationLocation());
            }

            if ((excludeProps == null || !excludeProps.contains("RegisterGlobalDataSources")) && this.bean.isRegisterGlobalDataSourcesSet()) {
               copy.setRegisterGlobalDataSources(this.bean.isRegisterGlobalDataSources());
            }

            if ((excludeProps == null || !excludeProps.contains("RegisterGlobalWorkManagers")) && this.bean.isRegisterGlobalWorkManagersSet()) {
               copy.setRegisterGlobalWorkManagers(this.bean.isRegisterGlobalWorkManagers());
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
