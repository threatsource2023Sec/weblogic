package weblogic.management.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.AppDeployment;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AppDeploymentMBeanImpl extends BasicDeploymentMBeanImpl implements AppDeploymentMBean, Serializable {
   private String _AbsoluteAltDescriptorDir;
   private String _AbsoluteAltDescriptorPath;
   private String _AbsoluteInstallDir;
   private String _AbsolutePlanDir;
   private String _AbsolutePlanPath;
   private String _AbsoluteSourcePath;
   private String _AltDescriptorDir;
   private String _AltDescriptorPath;
   private String _AltWLSDescriptorPath;
   private ApplicationMBean _AppMBean;
   private String _ApplicationIdentifier;
   private String _ApplicationName;
   private boolean _AutoDeployedApp;
   private boolean _BackgroundDeployment;
   private boolean _CacheInAppDirectory;
   private String _ConfiguredApplicationIdentifier;
   private byte[] _DeploymentPlan;
   private DeploymentPlanBean _DeploymentPlanDescriptor;
   private byte[] _DeploymentPlanExternalDescriptors;
   private boolean _DynamicallyCreated;
   private String _InstallDir;
   private boolean _InternalApp;
   private String _LocalAltDescriptorPath;
   private String _LocalInstallDir;
   private String _LocalPlanDir;
   private String _LocalPlanPath;
   private String _LocalSourcePath;
   private boolean _MultiVersionApp;
   private String _Name;
   private String[] _OnDemandContextPaths;
   private boolean _OnDemandDisplayRefresh;
   private boolean _ParallelDeployModules;
   private String _PlanDir;
   private String _PlanPath;
   private String _PlanStagingMode;
   private String _RootStagingDir;
   private String _SecurityDDModel;
   private String _SourcePath;
   private String _StagingMode;
   private String[] _Tags;
   private boolean _Untargeted;
   private boolean _ValidateDDSecurityData;
   private String _VersionIdentifier;
   private transient AppDeployment _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private AppDeploymentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(AppDeploymentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(AppDeploymentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public AppDeploymentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(AppDeploymentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      AppDeploymentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._AppMBean instanceof ApplicationMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getAppMBean() != null) {
            this._getReferenceManager().unregisterBean((ApplicationMBeanImpl)oldDelegate.getAppMBean());
         }

         if (delegate != null && delegate.getAppMBean() != null) {
            this._getReferenceManager().registerBean((ApplicationMBeanImpl)delegate.getAppMBean(), false);
         }

         ((ApplicationMBeanImpl)this._AppMBean)._setDelegateBean((ApplicationMBeanImpl)((ApplicationMBeanImpl)(delegate == null ? null : delegate.getAppMBean())));
      }

   }

   public AppDeploymentMBeanImpl() {
      try {
         this._customizer = new AppDeployment(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public AppDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new AppDeployment(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public AppDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new AppDeployment(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

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

         return this._customizer.getName();
      }
   }

   public String getSourcePath() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getSourcePath(), this) : this._customizer.getSourcePath();
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isSourcePathInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isSourcePathSet() {
      return this._isSet(13);
   }

   public String getInstallDir() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getInstallDir(), this) : this._InstallDir;
   }

   public boolean isInstallDirInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isInstallDirSet() {
      return this._isSet(18);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSourcePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this.getSourcePath();
      this._customizer.setSourcePath(param0);
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPlanDir() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getPlanDir(), this) : this._customizer.getPlanDir();
   }

   public boolean isPlanDirInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isPlanDirSet() {
      return this._isSet(19);
   }

   public String getPlanPath() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getPlanPath(), this) : this._customizer.getPlanPath();
   }

   public boolean isPlanPathInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isPlanPathSet() {
      return this._isSet(20);
   }

   public String getVersionIdentifier() {
      return this._customizer.getVersionIdentifier();
   }

   public boolean isVersionIdentifierInherited() {
      return false;
   }

   public boolean isVersionIdentifierSet() {
      return this._isSet(21);
   }

   public void setVersionIdentifier(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      String _oldVal = this._VersionIdentifier;
      this._VersionIdentifier = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isValidateDDSecurityData() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().isValidateDDSecurityData() : this._ValidateDDSecurityData;
   }

   public boolean isValidateDDSecurityDataInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isValidateDDSecurityDataSet() {
      return this._isSet(22);
   }

   public void setValidateDDSecurityData(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      boolean _oldVal = this._ValidateDDSecurityData;
      this._ValidateDDSecurityData = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSecurityDDModel() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getSecurityDDModel(), this) : this._SecurityDDModel;
   }

   public boolean isSecurityDDModelInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isSecurityDDModelSet() {
      return this._isSet(23);
   }

   public String getStagingMode() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getStagingMode(), this) : this._customizer.getStagingMode();
   }

   public boolean isStagingModeInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isStagingModeSet() {
      return this._isSet(24);
   }

   public String getPlanStagingMode() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getPlanStagingMode(), this) : this._customizer.getPlanStagingMode();
   }

   public boolean isPlanStagingModeInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isPlanStagingModeSet() {
      return this._isSet(25);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public String createPlan(String param0) {
      return this._customizer.createPlan(param0);
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String createPlan() {
      return this._customizer.createPlan();
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setInstallDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      String _oldVal = this._InstallDir;
      this._InstallDir = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPlanDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      String _oldVal = this.getPlanDir();
      this._customizer.setPlanDir(param0);
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPlanPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      String _oldVal = this.getPlanPath();
      this._customizer.setPlanPath(param0);
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setStagingMode(String param0) throws ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{AppDeploymentMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"};
      param0 = LegalChecks.checkInEnum("StagingMode", param0, _set);
      boolean wasSet = this._isSet(24);
      String _oldVal = this.getStagingMode();
      this._customizer.setStagingMode(param0);
      this._postSet(24, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var5.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPlanStagingMode(String param0) throws ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{AppDeploymentMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"};
      param0 = LegalChecks.checkInEnum("PlanStagingMode", param0, _set);
      boolean wasSet = this._isSet(25);
      String _oldVal = this.getPlanStagingMode();
      this._customizer.setPlanStagingMode(param0);
      this._postSet(25, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var5.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAltDescriptorPath() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._performMacroSubstitution(this._getDelegateBean().getAltDescriptorPath(), this) : this._customizer.getAltDescriptorPath();
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isAltDescriptorPathInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isAltDescriptorPathSet() {
      return this._isSet(26);
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setAltDescriptorPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      String _oldVal = this.getAltDescriptorPath();
      this._customizer.setAltDescriptorPath(param0);
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getAltDescriptorDir() {
      return this._AltDescriptorDir;
   }

   public boolean isAltDescriptorDirInherited() {
      return false;
   }

   public boolean isAltDescriptorDirSet() {
      return this._isSet(27);
   }

   public void setAltDescriptorDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      String _oldVal = this._AltDescriptorDir;
      this._AltDescriptorDir = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAltWLSDescriptorPath() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._performMacroSubstitution(this._getDelegateBean().getAltWLSDescriptorPath(), this) : this._AltWLSDescriptorPath;
   }

   public boolean isAltWLSDescriptorPathInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isAltWLSDescriptorPathSet() {
      return this._isSet(28);
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setAltWLSDescriptorPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      String _oldVal = this._AltWLSDescriptorPath;
      this._AltWLSDescriptorPath = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSecurityDDModel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"DDOnly", "CustomRoles", "CustomRolesAndPolicies", "Advanced"};
      param0 = LegalChecks.checkInEnum("SecurityDDModel", param0, _set);
      boolean wasSet = this._isSet(23);
      String _oldVal = this._SecurityDDModel;
      this._SecurityDDModel = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var5.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public String getApplicationIdentifier() {
      return this._customizer.getApplicationIdentifier();
   }

   public boolean isApplicationIdentifierInherited() {
      return false;
   }

   public boolean isApplicationIdentifierSet() {
      return this._isSet(29);
   }

   public void setApplicationIdentifier(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      String _oldVal = this._ApplicationIdentifier;
      this._ApplicationIdentifier = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public String getApplicationName() {
      return this._customizer.getApplicationName();
   }

   public boolean isApplicationNameInherited() {
      return false;
   }

   public boolean isApplicationNameSet() {
      return this._isSet(30);
   }

   public void setApplicationName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      String _oldVal = this._ApplicationName;
      this._ApplicationName = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public ApplicationMBean getAppMBean() {
      return this._customizer.getAppMBean();
   }

   public boolean isAppMBeanInherited() {
      return false;
   }

   public boolean isAppMBeanSet() {
      return this._isSet(31);
   }

   public void setAppMBean(ApplicationMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AppMBean = param0;
   }

   public boolean isInternalApp() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isInternalApp() : this._InternalApp;
   }

   public boolean isInternalAppInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isInternalAppSet() {
      return this._isSet(32);
   }

   public void setInternalApp(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._InternalApp = param0;
   }

   public boolean isBackgroundDeployment() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().isBackgroundDeployment() : this._BackgroundDeployment;
   }

   public boolean isBackgroundDeploymentInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isBackgroundDeploymentSet() {
      return this._isSet(33);
   }

   public void setBackgroundDeployment(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._BackgroundDeployment = param0;
   }

   public boolean isParallelDeployModules() {
      if (!this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34)) {
         return this._getDelegateBean().isParallelDeployModules();
      } else {
         if (!this._isSet(34)) {
            try {
               return AppDeployment.deriveDefaultIsParallelDeployModules(this.getParent());
            } catch (NullPointerException var2) {
            }
         }

         return this._ParallelDeployModules;
      }
   }

   public boolean isParallelDeployModulesInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isParallelDeployModulesSet() {
      return this._isSet(34);
   }

   public void setParallelDeployModules(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      boolean _oldVal = this._ParallelDeployModules;
      this._ParallelDeployModules = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAutoDeployedApp() {
      return this._customizer.isAutoDeployedApp();
   }

   public boolean isAutoDeployedAppInherited() {
      return false;
   }

   public boolean isAutoDeployedAppSet() {
      return this._isSet(35);
   }

   public void setAutoDeployedApp(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AutoDeployedApp = param0;
   }

   public DeploymentPlanBean getDeploymentPlanDescriptor() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().getDeploymentPlanDescriptor() : this._customizer.getDeploymentPlanDescriptor();
   }

   public boolean isDeploymentPlanDescriptorInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isDeploymentPlanDescriptorSet() {
      return this._isSet(36);
   }

   public void setDeploymentPlanDescriptor(DeploymentPlanBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setDeploymentPlanDescriptor(param0);
   }

   public String[] getOnDemandContextPaths() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getOnDemandContextPaths() : this._OnDemandContextPaths;
   }

   public boolean isOnDemandContextPathsInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isOnDemandContextPathsSet() {
      return this._isSet(37);
   }

   public void setOnDemandContextPaths(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._OnDemandContextPaths = param0;
   }

   public boolean isOnDemandDisplayRefresh() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().isOnDemandDisplayRefresh() : this._OnDemandDisplayRefresh;
   }

   public boolean isOnDemandDisplayRefreshInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isOnDemandDisplayRefreshSet() {
      return this._isSet(38);
   }

   public void setOnDemandDisplayRefresh(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._OnDemandDisplayRefresh = param0;
   }

   public String getAbsoluteInstallDir() {
      return this._customizer.getAbsoluteInstallDir();
   }

   public boolean isAbsoluteInstallDirInherited() {
      return false;
   }

   public boolean isAbsoluteInstallDirSet() {
      return this._isSet(39);
   }

   public void setAbsoluteInstallDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AbsoluteInstallDir = param0;
   }

   public String getAbsolutePlanPath() {
      return this._customizer.getAbsolutePlanPath();
   }

   public boolean isAbsolutePlanPathInherited() {
      return false;
   }

   public boolean isAbsolutePlanPathSet() {
      return this._isSet(40);
   }

   public void setAbsolutePlanPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AbsolutePlanPath = param0;
   }

   public String getAbsolutePlanDir() {
      return this._customizer.getAbsolutePlanDir();
   }

   public boolean isAbsolutePlanDirInherited() {
      return false;
   }

   public boolean isAbsolutePlanDirSet() {
      return this._isSet(41);
   }

   public void setAbsolutePlanDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AbsolutePlanDir = param0;
   }

   public String getAbsoluteAltDescriptorPath() {
      return this._customizer.getAbsoluteAltDescriptorPath();
   }

   public boolean isAbsoluteAltDescriptorPathInherited() {
      return false;
   }

   public boolean isAbsoluteAltDescriptorPathSet() {
      return this._isSet(42);
   }

   public void setAbsoluteAltDescriptorPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AbsoluteAltDescriptorPath = param0;
   }

   public String getAbsoluteAltDescriptorDir() {
      return this._customizer.getAbsoluteAltDescriptorDir();
   }

   public boolean isAbsoluteAltDescriptorDirInherited() {
      return false;
   }

   public boolean isAbsoluteAltDescriptorDirSet() {
      return this._isSet(43);
   }

   public void setAbsoluteAltDescriptorDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AbsoluteAltDescriptorDir = param0;
   }

   public String getAbsoluteSourcePath() {
      return this._customizer.getAbsoluteSourcePath();
   }

   public boolean isAbsoluteSourcePathInherited() {
      return false;
   }

   public boolean isAbsoluteSourcePathSet() {
      return this._isSet(44);
   }

   public void setAbsoluteSourcePath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AbsoluteSourcePath = param0;
   }

   public String getLocalInstallDir() {
      return this._customizer.getLocalInstallDir();
   }

   public boolean isLocalInstallDirInherited() {
      return false;
   }

   public boolean isLocalInstallDirSet() {
      return this._isSet(45);
   }

   public void setLocalInstallDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LocalInstallDir = param0;
   }

   public String getLocalPlanPath() {
      return this._customizer.getLocalPlanPath();
   }

   public boolean isLocalPlanPathInherited() {
      return false;
   }

   public boolean isLocalPlanPathSet() {
      return this._isSet(46);
   }

   public void setLocalPlanPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LocalPlanPath = param0;
   }

   public String getLocalPlanDir() {
      try {
         return this._customizer.getLocalPlanDir();
      } catch (IOException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isLocalPlanDirInherited() {
      return false;
   }

   public boolean isLocalPlanDirSet() {
      return this._isSet(47);
   }

   public void setLocalPlanDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LocalPlanDir = param0;
   }

   public String getLocalAltDescriptorPath() {
      return this._customizer.getLocalAltDescriptorPath();
   }

   public boolean isLocalAltDescriptorPathInherited() {
      return false;
   }

   public boolean isLocalAltDescriptorPathSet() {
      return this._isSet(48);
   }

   public void setLocalAltDescriptorPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LocalAltDescriptorPath = param0;
   }

   public String getLocalSourcePath() {
      return this._customizer.getLocalSourcePath();
   }

   public boolean isLocalSourcePathInherited() {
      return false;
   }

   public boolean isLocalSourcePathSet() {
      return this._isSet(49);
   }

   public void setLocalSourcePath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LocalSourcePath = param0;
   }

   public String getRootStagingDir() {
      return this._customizer.getRootStagingDir();
   }

   public boolean isRootStagingDirInherited() {
      return false;
   }

   public boolean isRootStagingDirSet() {
      return this._isSet(50);
   }

   public void setRootStagingDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._RootStagingDir = param0;
   }

   public String getStagingMode(String param0) {
      return this._customizer.getStagingMode(param0);
   }

   public byte[] getDeploymentPlan() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51) ? this._getDelegateBean().getDeploymentPlan() : this._customizer.getDeploymentPlan();
   }

   public boolean isDeploymentPlanInherited() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51);
   }

   public boolean isDeploymentPlanSet() {
      return this._isSet(51);
   }

   public void setDeploymentPlan(byte[] param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DeploymentPlan = param0;
   }

   public byte[] getDeploymentPlanExternalDescriptors() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._getDelegateBean().getDeploymentPlanExternalDescriptors() : this._customizer.getDeploymentPlanExternalDescriptors();
   }

   public boolean isDeploymentPlanExternalDescriptorsInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isDeploymentPlanExternalDescriptorsSet() {
      return this._isSet(52);
   }

   public void setDeploymentPlanExternalDescriptors(byte[] param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DeploymentPlanExternalDescriptors = param0;
   }

   public void setCacheInAppDirectory(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(53);
      boolean _oldVal = this._CacheInAppDirectory;
      this._CacheInAppDirectory = param0;
      this._postSet(53, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(53)) {
            source._postSetFirePropertyChange(53, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isCacheInAppDirectory() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53) ? this._getDelegateBean().isCacheInAppDirectory() : this._CacheInAppDirectory;
   }

   public boolean isCacheInAppDirectoryInherited() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53);
   }

   public boolean isCacheInAppDirectorySet() {
      return this._isSet(53);
   }

   public boolean isUntargeted() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54) ? this._getDelegateBean().isUntargeted() : this._Untargeted;
   }

   public boolean isUntargetedInherited() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54);
   }

   public boolean isUntargetedSet() {
      return this._isSet(54);
   }

   public void setUntargeted(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(54);
      boolean _oldVal = this._Untargeted;
      this._Untargeted = param0;
      this._postSet(54, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         AppDeploymentMBeanImpl source = (AppDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(54)) {
            source._postSetFirePropertyChange(54, wasSet, _oldVal, param0);
         }
      }

   }

   public String getConfiguredApplicationIdentifier() {
      return !this._isSet(55) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(55) ? this._performMacroSubstitution(this._getDelegateBean().getConfiguredApplicationIdentifier(), this) : this._customizer.getConfiguredApplicationIdentifier();
   }

   public boolean isConfiguredApplicationIdentifierInherited() {
      return !this._isSet(55) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(55);
   }

   public boolean isConfiguredApplicationIdentifierSet() {
      return this._isSet(55);
   }

   public void setConfiguredApplicationIdentifier(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setConfiguredApplicationIdentifier(param0);
   }

   public boolean isMultiVersionApp() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56) ? this._getDelegateBean().isMultiVersionApp() : this._customizer.isMultiVersionApp();
   }

   public boolean isMultiVersionAppInherited() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56);
   }

   public boolean isMultiVersionAppSet() {
      return this._isSet(56);
   }

   public void setMultiVersionApp(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setMultiVersionApp(param0);
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
         idx = 43;
      }

      try {
         switch (idx) {
            case 43:
               this._AbsoluteAltDescriptorDir = null;
               if (initOne) {
                  break;
               }
            case 42:
               this._AbsoluteAltDescriptorPath = null;
               if (initOne) {
                  break;
               }
            case 39:
               this._AbsoluteInstallDir = null;
               if (initOne) {
                  break;
               }
            case 41:
               this._AbsolutePlanDir = null;
               if (initOne) {
                  break;
               }
            case 40:
               this._AbsolutePlanPath = null;
               if (initOne) {
                  break;
               }
            case 44:
               this._AbsoluteSourcePath = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._AltDescriptorDir = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._customizer.setAltDescriptorPath((String)null);
               if (initOne) {
                  break;
               }
            case 28:
               this._AltWLSDescriptorPath = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._AppMBean = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._ApplicationIdentifier = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._ApplicationName = null;
               if (initOne) {
                  break;
               }
            case 55:
               this._customizer.setConfiguredApplicationIdentifier((String)null);
               if (initOne) {
                  break;
               }
            case 51:
               this._DeploymentPlan = new byte[0];
               if (initOne) {
                  break;
               }
            case 36:
               this._customizer.setDeploymentPlanDescriptor((DeploymentPlanBean)null);
               if (initOne) {
                  break;
               }
            case 52:
               this._DeploymentPlanExternalDescriptors = new byte[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._InstallDir = null;
               if (initOne) {
                  break;
               }
            case 48:
               this._LocalAltDescriptorPath = null;
               if (initOne) {
                  break;
               }
            case 45:
               this._LocalInstallDir = null;
               if (initOne) {
                  break;
               }
            case 47:
               this._LocalPlanDir = null;
               if (initOne) {
                  break;
               }
            case 46:
               this._LocalPlanPath = null;
               if (initOne) {
                  break;
               }
            case 49:
               this._LocalSourcePath = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 37:
               this._OnDemandContextPaths = new String[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._customizer.setPlanDir((String)null);
               if (initOne) {
                  break;
               }
            case 20:
               this._customizer.setPlanPath((String)null);
               if (initOne) {
                  break;
               }
            case 25:
               this._customizer.setPlanStagingMode(AppDeploymentMBean.DEFAULT_STAGE);
               if (initOne) {
                  break;
               }
            case 50:
               this._RootStagingDir = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._SecurityDDModel = this.isEditable() ? AppDeployment.getInitialSecurityDDModel(this) : "DDOnly";
               if (this.isEditable() && !this._isTransient()) {
                  this._markSet(23, true);
               }

               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setSourcePath((String)null);
               if (initOne) {
                  break;
               }
            case 24:
               this._customizer.setStagingMode(AppDeploymentMBean.DEFAULT_STAGE);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 21:
               this._VersionIdentifier = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._AutoDeployedApp = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._BackgroundDeployment = false;
               if (initOne) {
                  break;
               }
            case 53:
               this._CacheInAppDirectory = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 32:
               this._InternalApp = false;
               if (initOne) {
                  break;
               }
            case 56:
               this._customizer.setMultiVersionApp(false);
               if (initOne) {
                  break;
               }
            case 38:
               this._OnDemandDisplayRefresh = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._ParallelDeployModules = false;
               if (initOne) {
                  break;
               }
            case 54:
               this._Untargeted = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._ValidateDDSecurityData = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
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
      return "AppDeployment";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AbsoluteAltDescriptorDir")) {
         oldVal = this._AbsoluteAltDescriptorDir;
         this._AbsoluteAltDescriptorDir = (String)v;
         this._postSet(43, oldVal, this._AbsoluteAltDescriptorDir);
      } else if (name.equals("AbsoluteAltDescriptorPath")) {
         oldVal = this._AbsoluteAltDescriptorPath;
         this._AbsoluteAltDescriptorPath = (String)v;
         this._postSet(42, oldVal, this._AbsoluteAltDescriptorPath);
      } else if (name.equals("AbsoluteInstallDir")) {
         oldVal = this._AbsoluteInstallDir;
         this._AbsoluteInstallDir = (String)v;
         this._postSet(39, oldVal, this._AbsoluteInstallDir);
      } else if (name.equals("AbsolutePlanDir")) {
         oldVal = this._AbsolutePlanDir;
         this._AbsolutePlanDir = (String)v;
         this._postSet(41, oldVal, this._AbsolutePlanDir);
      } else if (name.equals("AbsolutePlanPath")) {
         oldVal = this._AbsolutePlanPath;
         this._AbsolutePlanPath = (String)v;
         this._postSet(40, oldVal, this._AbsolutePlanPath);
      } else if (name.equals("AbsoluteSourcePath")) {
         oldVal = this._AbsoluteSourcePath;
         this._AbsoluteSourcePath = (String)v;
         this._postSet(44, oldVal, this._AbsoluteSourcePath);
      } else if (name.equals("AltDescriptorDir")) {
         oldVal = this._AltDescriptorDir;
         this._AltDescriptorDir = (String)v;
         this._postSet(27, oldVal, this._AltDescriptorDir);
      } else if (name.equals("AltDescriptorPath")) {
         oldVal = this._AltDescriptorPath;
         this._AltDescriptorPath = (String)v;
         this._postSet(26, oldVal, this._AltDescriptorPath);
      } else if (name.equals("AltWLSDescriptorPath")) {
         oldVal = this._AltWLSDescriptorPath;
         this._AltWLSDescriptorPath = (String)v;
         this._postSet(28, oldVal, this._AltWLSDescriptorPath);
      } else if (name.equals("AppMBean")) {
         ApplicationMBean oldVal = this._AppMBean;
         this._AppMBean = (ApplicationMBean)v;
         this._postSet(31, oldVal, this._AppMBean);
      } else if (name.equals("ApplicationIdentifier")) {
         oldVal = this._ApplicationIdentifier;
         this._ApplicationIdentifier = (String)v;
         this._postSet(29, oldVal, this._ApplicationIdentifier);
      } else if (name.equals("ApplicationName")) {
         oldVal = this._ApplicationName;
         this._ApplicationName = (String)v;
         this._postSet(30, oldVal, this._ApplicationName);
      } else {
         boolean oldVal;
         if (name.equals("AutoDeployedApp")) {
            oldVal = this._AutoDeployedApp;
            this._AutoDeployedApp = (Boolean)v;
            this._postSet(35, oldVal, this._AutoDeployedApp);
         } else if (name.equals("BackgroundDeployment")) {
            oldVal = this._BackgroundDeployment;
            this._BackgroundDeployment = (Boolean)v;
            this._postSet(33, oldVal, this._BackgroundDeployment);
         } else if (name.equals("CacheInAppDirectory")) {
            oldVal = this._CacheInAppDirectory;
            this._CacheInAppDirectory = (Boolean)v;
            this._postSet(53, oldVal, this._CacheInAppDirectory);
         } else if (name.equals("ConfiguredApplicationIdentifier")) {
            oldVal = this._ConfiguredApplicationIdentifier;
            this._ConfiguredApplicationIdentifier = (String)v;
            this._postSet(55, oldVal, this._ConfiguredApplicationIdentifier);
         } else {
            byte[] oldVal;
            if (name.equals("DeploymentPlan")) {
               oldVal = this._DeploymentPlan;
               this._DeploymentPlan = (byte[])((byte[])v);
               this._postSet(51, oldVal, this._DeploymentPlan);
            } else if (name.equals("DeploymentPlanDescriptor")) {
               DeploymentPlanBean oldVal = this._DeploymentPlanDescriptor;
               this._DeploymentPlanDescriptor = (DeploymentPlanBean)v;
               this._postSet(36, oldVal, this._DeploymentPlanDescriptor);
            } else if (name.equals("DeploymentPlanExternalDescriptors")) {
               oldVal = this._DeploymentPlanExternalDescriptors;
               this._DeploymentPlanExternalDescriptors = (byte[])((byte[])v);
               this._postSet(52, oldVal, this._DeploymentPlanExternalDescriptors);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("InstallDir")) {
               oldVal = this._InstallDir;
               this._InstallDir = (String)v;
               this._postSet(18, oldVal, this._InstallDir);
            } else if (name.equals("InternalApp")) {
               oldVal = this._InternalApp;
               this._InternalApp = (Boolean)v;
               this._postSet(32, oldVal, this._InternalApp);
            } else if (name.equals("LocalAltDescriptorPath")) {
               oldVal = this._LocalAltDescriptorPath;
               this._LocalAltDescriptorPath = (String)v;
               this._postSet(48, oldVal, this._LocalAltDescriptorPath);
            } else if (name.equals("LocalInstallDir")) {
               oldVal = this._LocalInstallDir;
               this._LocalInstallDir = (String)v;
               this._postSet(45, oldVal, this._LocalInstallDir);
            } else if (name.equals("LocalPlanDir")) {
               oldVal = this._LocalPlanDir;
               this._LocalPlanDir = (String)v;
               this._postSet(47, oldVal, this._LocalPlanDir);
            } else if (name.equals("LocalPlanPath")) {
               oldVal = this._LocalPlanPath;
               this._LocalPlanPath = (String)v;
               this._postSet(46, oldVal, this._LocalPlanPath);
            } else if (name.equals("LocalSourcePath")) {
               oldVal = this._LocalSourcePath;
               this._LocalSourcePath = (String)v;
               this._postSet(49, oldVal, this._LocalSourcePath);
            } else if (name.equals("MultiVersionApp")) {
               oldVal = this._MultiVersionApp;
               this._MultiVersionApp = (Boolean)v;
               this._postSet(56, oldVal, this._MultiVersionApp);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else {
               String[] oldVal;
               if (name.equals("OnDemandContextPaths")) {
                  oldVal = this._OnDemandContextPaths;
                  this._OnDemandContextPaths = (String[])((String[])v);
                  this._postSet(37, oldVal, this._OnDemandContextPaths);
               } else if (name.equals("OnDemandDisplayRefresh")) {
                  oldVal = this._OnDemandDisplayRefresh;
                  this._OnDemandDisplayRefresh = (Boolean)v;
                  this._postSet(38, oldVal, this._OnDemandDisplayRefresh);
               } else if (name.equals("ParallelDeployModules")) {
                  oldVal = this._ParallelDeployModules;
                  this._ParallelDeployModules = (Boolean)v;
                  this._postSet(34, oldVal, this._ParallelDeployModules);
               } else if (name.equals("PlanDir")) {
                  oldVal = this._PlanDir;
                  this._PlanDir = (String)v;
                  this._postSet(19, oldVal, this._PlanDir);
               } else if (name.equals("PlanPath")) {
                  oldVal = this._PlanPath;
                  this._PlanPath = (String)v;
                  this._postSet(20, oldVal, this._PlanPath);
               } else if (name.equals("PlanStagingMode")) {
                  oldVal = this._PlanStagingMode;
                  this._PlanStagingMode = (String)v;
                  this._postSet(25, oldVal, this._PlanStagingMode);
               } else if (name.equals("RootStagingDir")) {
                  oldVal = this._RootStagingDir;
                  this._RootStagingDir = (String)v;
                  this._postSet(50, oldVal, this._RootStagingDir);
               } else if (name.equals("SecurityDDModel")) {
                  oldVal = this._SecurityDDModel;
                  this._SecurityDDModel = (String)v;
                  this._postSet(23, oldVal, this._SecurityDDModel);
               } else if (name.equals("SourcePath")) {
                  oldVal = this._SourcePath;
                  this._SourcePath = (String)v;
                  this._postSet(13, oldVal, this._SourcePath);
               } else if (name.equals("StagingMode")) {
                  oldVal = this._StagingMode;
                  this._StagingMode = (String)v;
                  this._postSet(24, oldVal, this._StagingMode);
               } else if (name.equals("Tags")) {
                  oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Untargeted")) {
                  oldVal = this._Untargeted;
                  this._Untargeted = (Boolean)v;
                  this._postSet(54, oldVal, this._Untargeted);
               } else if (name.equals("ValidateDDSecurityData")) {
                  oldVal = this._ValidateDDSecurityData;
                  this._ValidateDDSecurityData = (Boolean)v;
                  this._postSet(22, oldVal, this._ValidateDDSecurityData);
               } else if (name.equals("VersionIdentifier")) {
                  oldVal = this._VersionIdentifier;
                  this._VersionIdentifier = (String)v;
                  this._postSet(21, oldVal, this._VersionIdentifier);
               } else if (name.equals("customizer")) {
                  AppDeployment oldVal = this._customizer;
                  this._customizer = (AppDeployment)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AbsoluteAltDescriptorDir")) {
         return this._AbsoluteAltDescriptorDir;
      } else if (name.equals("AbsoluteAltDescriptorPath")) {
         return this._AbsoluteAltDescriptorPath;
      } else if (name.equals("AbsoluteInstallDir")) {
         return this._AbsoluteInstallDir;
      } else if (name.equals("AbsolutePlanDir")) {
         return this._AbsolutePlanDir;
      } else if (name.equals("AbsolutePlanPath")) {
         return this._AbsolutePlanPath;
      } else if (name.equals("AbsoluteSourcePath")) {
         return this._AbsoluteSourcePath;
      } else if (name.equals("AltDescriptorDir")) {
         return this._AltDescriptorDir;
      } else if (name.equals("AltDescriptorPath")) {
         return this._AltDescriptorPath;
      } else if (name.equals("AltWLSDescriptorPath")) {
         return this._AltWLSDescriptorPath;
      } else if (name.equals("AppMBean")) {
         return this._AppMBean;
      } else if (name.equals("ApplicationIdentifier")) {
         return this._ApplicationIdentifier;
      } else if (name.equals("ApplicationName")) {
         return this._ApplicationName;
      } else if (name.equals("AutoDeployedApp")) {
         return new Boolean(this._AutoDeployedApp);
      } else if (name.equals("BackgroundDeployment")) {
         return new Boolean(this._BackgroundDeployment);
      } else if (name.equals("CacheInAppDirectory")) {
         return new Boolean(this._CacheInAppDirectory);
      } else if (name.equals("ConfiguredApplicationIdentifier")) {
         return this._ConfiguredApplicationIdentifier;
      } else if (name.equals("DeploymentPlan")) {
         return this._DeploymentPlan;
      } else if (name.equals("DeploymentPlanDescriptor")) {
         return this._DeploymentPlanDescriptor;
      } else if (name.equals("DeploymentPlanExternalDescriptors")) {
         return this._DeploymentPlanExternalDescriptors;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("InstallDir")) {
         return this._InstallDir;
      } else if (name.equals("InternalApp")) {
         return new Boolean(this._InternalApp);
      } else if (name.equals("LocalAltDescriptorPath")) {
         return this._LocalAltDescriptorPath;
      } else if (name.equals("LocalInstallDir")) {
         return this._LocalInstallDir;
      } else if (name.equals("LocalPlanDir")) {
         return this._LocalPlanDir;
      } else if (name.equals("LocalPlanPath")) {
         return this._LocalPlanPath;
      } else if (name.equals("LocalSourcePath")) {
         return this._LocalSourcePath;
      } else if (name.equals("MultiVersionApp")) {
         return new Boolean(this._MultiVersionApp);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OnDemandContextPaths")) {
         return this._OnDemandContextPaths;
      } else if (name.equals("OnDemandDisplayRefresh")) {
         return new Boolean(this._OnDemandDisplayRefresh);
      } else if (name.equals("ParallelDeployModules")) {
         return new Boolean(this._ParallelDeployModules);
      } else if (name.equals("PlanDir")) {
         return this._PlanDir;
      } else if (name.equals("PlanPath")) {
         return this._PlanPath;
      } else if (name.equals("PlanStagingMode")) {
         return this._PlanStagingMode;
      } else if (name.equals("RootStagingDir")) {
         return this._RootStagingDir;
      } else if (name.equals("SecurityDDModel")) {
         return this._SecurityDDModel;
      } else if (name.equals("SourcePath")) {
         return this._SourcePath;
      } else if (name.equals("StagingMode")) {
         return this._StagingMode;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Untargeted")) {
         return new Boolean(this._Untargeted);
      } else if (name.equals("ValidateDDSecurityData")) {
         return new Boolean(this._ValidateDDSecurityData);
      } else if (name.equals("VersionIdentifier")) {
         return this._VersionIdentifier;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends BasicDeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 7:
            case 13:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            default:
               break;
            case 8:
               if (s.equals("plan-dir")) {
                  return 19;
               }
               break;
            case 9:
               if (s.equals("appm-bean")) {
                  return 31;
               }

               if (s.equals("plan-path")) {
                  return 20;
               }
               break;
            case 10:
               if (s.equals("untargeted")) {
                  return 54;
               }
               break;
            case 11:
               if (s.equals("install-dir")) {
                  return 18;
               }

               if (s.equals("source-path")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("staging-mode")) {
                  return 24;
               }

               if (s.equals("internal-app")) {
                  return 32;
               }
               break;
            case 14:
               if (s.equals("local-plan-dir")) {
                  return 47;
               }
               break;
            case 15:
               if (s.equals("deployment-plan")) {
                  return 51;
               }

               if (s.equals("local-plan-path")) {
                  return 46;
               }
               break;
            case 16:
               if (s.equals("application-name")) {
                  return 30;
               }

               if (s.equals("root-staging-dir")) {
                  return 50;
               }

               if (s.equals("securitydd-model")) {
                  return 23;
               }
               break;
            case 17:
               if (s.equals("absolute-plan-dir")) {
                  return 41;
               }

               if (s.equals("local-install-dir")) {
                  return 45;
               }

               if (s.equals("local-source-path")) {
                  return 49;
               }

               if (s.equals("plan-staging-mode")) {
                  return 25;
               }

               if (s.equals("auto-deployed-app")) {
                  return 35;
               }

               if (s.equals("multi-version-app")) {
                  return 56;
               }
               break;
            case 18:
               if (s.equals("absolute-plan-path")) {
                  return 40;
               }

               if (s.equals("alt-descriptor-dir")) {
                  return 27;
               }

               if (s.equals("version-identifier")) {
                  return 21;
               }
               break;
            case 19:
               if (s.equals("alt-descriptor-path")) {
                  return 26;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("absolute-install-dir")) {
                  return 39;
               }

               if (s.equals("absolute-source-path")) {
                  return 44;
               }
               break;
            case 21:
               if (s.equals("background-deployment")) {
                  return 33;
               }
               break;
            case 22:
               if (s.equals("altwls-descriptor-path")) {
                  return 28;
               }

               if (s.equals("application-identifier")) {
                  return 29;
               }

               if (s.equals("on-demand-context-path")) {
                  return 37;
               }

               if (s.equals("cache-in-app-directory")) {
                  return 53;
               }
               break;
            case 23:
               if (s.equals("parallel-deploy-modules")) {
                  return 34;
               }
               break;
            case 24:
               if (s.equals("validatedd-security-data")) {
                  return 22;
               }
               break;
            case 25:
               if (s.equals("local-alt-descriptor-path")) {
                  return 48;
               }

               if (s.equals("on-demand-display-refresh")) {
                  return 38;
               }
               break;
            case 26:
               if (s.equals("deployment-plan-descriptor")) {
                  return 36;
               }
               break;
            case 27:
               if (s.equals("absolute-alt-descriptor-dir")) {
                  return 43;
               }
               break;
            case 28:
               if (s.equals("absolute-alt-descriptor-path")) {
                  return 42;
               }
               break;
            case 33:
               if (s.equals("configured-application-identifier")) {
                  return 55;
               }
               break;
            case 35:
               if (s.equals("deployment-plan-external-descriptor")) {
                  return 52;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 14:
               return new SubDeploymentMBeanImpl.SchemaHelper2();
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
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 13:
               return "source-path";
            case 18:
               return "install-dir";
            case 19:
               return "plan-dir";
            case 20:
               return "plan-path";
            case 21:
               return "version-identifier";
            case 22:
               return "validatedd-security-data";
            case 23:
               return "securitydd-model";
            case 24:
               return "staging-mode";
            case 25:
               return "plan-staging-mode";
            case 26:
               return "alt-descriptor-path";
            case 27:
               return "alt-descriptor-dir";
            case 28:
               return "altwls-descriptor-path";
            case 29:
               return "application-identifier";
            case 30:
               return "application-name";
            case 31:
               return "appm-bean";
            case 32:
               return "internal-app";
            case 33:
               return "background-deployment";
            case 34:
               return "parallel-deploy-modules";
            case 35:
               return "auto-deployed-app";
            case 36:
               return "deployment-plan-descriptor";
            case 37:
               return "on-demand-context-path";
            case 38:
               return "on-demand-display-refresh";
            case 39:
               return "absolute-install-dir";
            case 40:
               return "absolute-plan-path";
            case 41:
               return "absolute-plan-dir";
            case 42:
               return "absolute-alt-descriptor-path";
            case 43:
               return "absolute-alt-descriptor-dir";
            case 44:
               return "absolute-source-path";
            case 45:
               return "local-install-dir";
            case 46:
               return "local-plan-path";
            case 47:
               return "local-plan-dir";
            case 48:
               return "local-alt-descriptor-path";
            case 49:
               return "local-source-path";
            case 50:
               return "root-staging-dir";
            case 51:
               return "deployment-plan";
            case 52:
               return "deployment-plan-external-descriptor";
            case 53:
               return "cache-in-app-directory";
            case 54:
               return "untargeted";
            case 55:
               return "configured-application-identifier";
            case 56:
               return "multi-version-app";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 14:
               return true;
            case 37:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 14:
               return true;
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

   protected static class Helper extends BasicDeploymentMBeanImpl.Helper {
      private AppDeploymentMBeanImpl bean;

      protected Helper(AppDeploymentMBeanImpl bean) {
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
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 13:
               return "SourcePath";
            case 18:
               return "InstallDir";
            case 19:
               return "PlanDir";
            case 20:
               return "PlanPath";
            case 21:
               return "VersionIdentifier";
            case 22:
               return "ValidateDDSecurityData";
            case 23:
               return "SecurityDDModel";
            case 24:
               return "StagingMode";
            case 25:
               return "PlanStagingMode";
            case 26:
               return "AltDescriptorPath";
            case 27:
               return "AltDescriptorDir";
            case 28:
               return "AltWLSDescriptorPath";
            case 29:
               return "ApplicationIdentifier";
            case 30:
               return "ApplicationName";
            case 31:
               return "AppMBean";
            case 32:
               return "InternalApp";
            case 33:
               return "BackgroundDeployment";
            case 34:
               return "ParallelDeployModules";
            case 35:
               return "AutoDeployedApp";
            case 36:
               return "DeploymentPlanDescriptor";
            case 37:
               return "OnDemandContextPaths";
            case 38:
               return "OnDemandDisplayRefresh";
            case 39:
               return "AbsoluteInstallDir";
            case 40:
               return "AbsolutePlanPath";
            case 41:
               return "AbsolutePlanDir";
            case 42:
               return "AbsoluteAltDescriptorPath";
            case 43:
               return "AbsoluteAltDescriptorDir";
            case 44:
               return "AbsoluteSourcePath";
            case 45:
               return "LocalInstallDir";
            case 46:
               return "LocalPlanPath";
            case 47:
               return "LocalPlanDir";
            case 48:
               return "LocalAltDescriptorPath";
            case 49:
               return "LocalSourcePath";
            case 50:
               return "RootStagingDir";
            case 51:
               return "DeploymentPlan";
            case 52:
               return "DeploymentPlanExternalDescriptors";
            case 53:
               return "CacheInAppDirectory";
            case 54:
               return "Untargeted";
            case 55:
               return "ConfiguredApplicationIdentifier";
            case 56:
               return "MultiVersionApp";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AbsoluteAltDescriptorDir")) {
            return 43;
         } else if (propName.equals("AbsoluteAltDescriptorPath")) {
            return 42;
         } else if (propName.equals("AbsoluteInstallDir")) {
            return 39;
         } else if (propName.equals("AbsolutePlanDir")) {
            return 41;
         } else if (propName.equals("AbsolutePlanPath")) {
            return 40;
         } else if (propName.equals("AbsoluteSourcePath")) {
            return 44;
         } else if (propName.equals("AltDescriptorDir")) {
            return 27;
         } else if (propName.equals("AltDescriptorPath")) {
            return 26;
         } else if (propName.equals("AltWLSDescriptorPath")) {
            return 28;
         } else if (propName.equals("AppMBean")) {
            return 31;
         } else if (propName.equals("ApplicationIdentifier")) {
            return 29;
         } else if (propName.equals("ApplicationName")) {
            return 30;
         } else if (propName.equals("ConfiguredApplicationIdentifier")) {
            return 55;
         } else if (propName.equals("DeploymentPlan")) {
            return 51;
         } else if (propName.equals("DeploymentPlanDescriptor")) {
            return 36;
         } else if (propName.equals("DeploymentPlanExternalDescriptors")) {
            return 52;
         } else if (propName.equals("InstallDir")) {
            return 18;
         } else if (propName.equals("LocalAltDescriptorPath")) {
            return 48;
         } else if (propName.equals("LocalInstallDir")) {
            return 45;
         } else if (propName.equals("LocalPlanDir")) {
            return 47;
         } else if (propName.equals("LocalPlanPath")) {
            return 46;
         } else if (propName.equals("LocalSourcePath")) {
            return 49;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OnDemandContextPaths")) {
            return 37;
         } else if (propName.equals("PlanDir")) {
            return 19;
         } else if (propName.equals("PlanPath")) {
            return 20;
         } else if (propName.equals("PlanStagingMode")) {
            return 25;
         } else if (propName.equals("RootStagingDir")) {
            return 50;
         } else if (propName.equals("SecurityDDModel")) {
            return 23;
         } else if (propName.equals("SourcePath")) {
            return 13;
         } else if (propName.equals("StagingMode")) {
            return 24;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("VersionIdentifier")) {
            return 21;
         } else if (propName.equals("AutoDeployedApp")) {
            return 35;
         } else if (propName.equals("BackgroundDeployment")) {
            return 33;
         } else if (propName.equals("CacheInAppDirectory")) {
            return 53;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("InternalApp")) {
            return 32;
         } else if (propName.equals("MultiVersionApp")) {
            return 56;
         } else if (propName.equals("OnDemandDisplayRefresh")) {
            return 38;
         } else if (propName.equals("ParallelDeployModules")) {
            return 34;
         } else if (propName.equals("Untargeted")) {
            return 54;
         } else {
            return propName.equals("ValidateDDSecurityData") ? 22 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSubDeployments()));
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
            if (this.bean.isAbsoluteAltDescriptorDirSet()) {
               buf.append("AbsoluteAltDescriptorDir");
               buf.append(String.valueOf(this.bean.getAbsoluteAltDescriptorDir()));
            }

            if (this.bean.isAbsoluteAltDescriptorPathSet()) {
               buf.append("AbsoluteAltDescriptorPath");
               buf.append(String.valueOf(this.bean.getAbsoluteAltDescriptorPath()));
            }

            if (this.bean.isAbsoluteInstallDirSet()) {
               buf.append("AbsoluteInstallDir");
               buf.append(String.valueOf(this.bean.getAbsoluteInstallDir()));
            }

            if (this.bean.isAbsolutePlanDirSet()) {
               buf.append("AbsolutePlanDir");
               buf.append(String.valueOf(this.bean.getAbsolutePlanDir()));
            }

            if (this.bean.isAbsolutePlanPathSet()) {
               buf.append("AbsolutePlanPath");
               buf.append(String.valueOf(this.bean.getAbsolutePlanPath()));
            }

            if (this.bean.isAbsoluteSourcePathSet()) {
               buf.append("AbsoluteSourcePath");
               buf.append(String.valueOf(this.bean.getAbsoluteSourcePath()));
            }

            if (this.bean.isAltDescriptorDirSet()) {
               buf.append("AltDescriptorDir");
               buf.append(String.valueOf(this.bean.getAltDescriptorDir()));
            }

            if (this.bean.isAltDescriptorPathSet()) {
               buf.append("AltDescriptorPath");
               buf.append(String.valueOf(this.bean.getAltDescriptorPath()));
            }

            if (this.bean.isAltWLSDescriptorPathSet()) {
               buf.append("AltWLSDescriptorPath");
               buf.append(String.valueOf(this.bean.getAltWLSDescriptorPath()));
            }

            if (this.bean.isAppMBeanSet()) {
               buf.append("AppMBean");
               buf.append(String.valueOf(this.bean.getAppMBean()));
            }

            if (this.bean.isApplicationIdentifierSet()) {
               buf.append("ApplicationIdentifier");
               buf.append(String.valueOf(this.bean.getApplicationIdentifier()));
            }

            if (this.bean.isApplicationNameSet()) {
               buf.append("ApplicationName");
               buf.append(String.valueOf(this.bean.getApplicationName()));
            }

            if (this.bean.isConfiguredApplicationIdentifierSet()) {
               buf.append("ConfiguredApplicationIdentifier");
               buf.append(String.valueOf(this.bean.getConfiguredApplicationIdentifier()));
            }

            if (this.bean.isDeploymentPlanSet()) {
               buf.append("DeploymentPlan");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeploymentPlan())));
            }

            if (this.bean.isDeploymentPlanDescriptorSet()) {
               buf.append("DeploymentPlanDescriptor");
               buf.append(String.valueOf(this.bean.getDeploymentPlanDescriptor()));
            }

            if (this.bean.isDeploymentPlanExternalDescriptorsSet()) {
               buf.append("DeploymentPlanExternalDescriptors");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeploymentPlanExternalDescriptors())));
            }

            if (this.bean.isInstallDirSet()) {
               buf.append("InstallDir");
               buf.append(String.valueOf(this.bean.getInstallDir()));
            }

            if (this.bean.isLocalAltDescriptorPathSet()) {
               buf.append("LocalAltDescriptorPath");
               buf.append(String.valueOf(this.bean.getLocalAltDescriptorPath()));
            }

            if (this.bean.isLocalInstallDirSet()) {
               buf.append("LocalInstallDir");
               buf.append(String.valueOf(this.bean.getLocalInstallDir()));
            }

            if (this.bean.isLocalPlanDirSet()) {
               buf.append("LocalPlanDir");
               buf.append(String.valueOf(this.bean.getLocalPlanDir()));
            }

            if (this.bean.isLocalPlanPathSet()) {
               buf.append("LocalPlanPath");
               buf.append(String.valueOf(this.bean.getLocalPlanPath()));
            }

            if (this.bean.isLocalSourcePathSet()) {
               buf.append("LocalSourcePath");
               buf.append(String.valueOf(this.bean.getLocalSourcePath()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOnDemandContextPathsSet()) {
               buf.append("OnDemandContextPaths");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getOnDemandContextPaths())));
            }

            if (this.bean.isPlanDirSet()) {
               buf.append("PlanDir");
               buf.append(String.valueOf(this.bean.getPlanDir()));
            }

            if (this.bean.isPlanPathSet()) {
               buf.append("PlanPath");
               buf.append(String.valueOf(this.bean.getPlanPath()));
            }

            if (this.bean.isPlanStagingModeSet()) {
               buf.append("PlanStagingMode");
               buf.append(String.valueOf(this.bean.getPlanStagingMode()));
            }

            if (this.bean.isRootStagingDirSet()) {
               buf.append("RootStagingDir");
               buf.append(String.valueOf(this.bean.getRootStagingDir()));
            }

            if (this.bean.isSecurityDDModelSet()) {
               buf.append("SecurityDDModel");
               buf.append(String.valueOf(this.bean.getSecurityDDModel()));
            }

            if (this.bean.isSourcePathSet()) {
               buf.append("SourcePath");
               buf.append(String.valueOf(this.bean.getSourcePath()));
            }

            if (this.bean.isStagingModeSet()) {
               buf.append("StagingMode");
               buf.append(String.valueOf(this.bean.getStagingMode()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isVersionIdentifierSet()) {
               buf.append("VersionIdentifier");
               buf.append(String.valueOf(this.bean.getVersionIdentifier()));
            }

            if (this.bean.isAutoDeployedAppSet()) {
               buf.append("AutoDeployedApp");
               buf.append(String.valueOf(this.bean.isAutoDeployedApp()));
            }

            if (this.bean.isBackgroundDeploymentSet()) {
               buf.append("BackgroundDeployment");
               buf.append(String.valueOf(this.bean.isBackgroundDeployment()));
            }

            if (this.bean.isCacheInAppDirectorySet()) {
               buf.append("CacheInAppDirectory");
               buf.append(String.valueOf(this.bean.isCacheInAppDirectory()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isInternalAppSet()) {
               buf.append("InternalApp");
               buf.append(String.valueOf(this.bean.isInternalApp()));
            }

            if (this.bean.isMultiVersionAppSet()) {
               buf.append("MultiVersionApp");
               buf.append(String.valueOf(this.bean.isMultiVersionApp()));
            }

            if (this.bean.isOnDemandDisplayRefreshSet()) {
               buf.append("OnDemandDisplayRefresh");
               buf.append(String.valueOf(this.bean.isOnDemandDisplayRefresh()));
            }

            if (this.bean.isParallelDeployModulesSet()) {
               buf.append("ParallelDeployModules");
               buf.append(String.valueOf(this.bean.isParallelDeployModules()));
            }

            if (this.bean.isUntargetedSet()) {
               buf.append("Untargeted");
               buf.append(String.valueOf(this.bean.isUntargeted()));
            }

            if (this.bean.isValidateDDSecurityDataSet()) {
               buf.append("ValidateDDSecurityData");
               buf.append(String.valueOf(this.bean.isValidateDDSecurityData()));
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
            AppDeploymentMBeanImpl otherTyped = (AppDeploymentMBeanImpl)other;
            this.computeDiff("AltDescriptorDir", this.bean.getAltDescriptorDir(), otherTyped.getAltDescriptorDir(), true);
            this.computeDiff("AltDescriptorPath", this.bean.getAltDescriptorPath(), otherTyped.getAltDescriptorPath(), true);
            this.computeDiff("AltWLSDescriptorPath", this.bean.getAltWLSDescriptorPath(), otherTyped.getAltWLSDescriptorPath(), true);
            this.computeDiff("ApplicationIdentifier", this.bean.getApplicationIdentifier(), otherTyped.getApplicationIdentifier(), false);
            this.computeDiff("ApplicationName", this.bean.getApplicationName(), otherTyped.getApplicationName(), false);
            this.computeDiff("InstallDir", this.bean.getInstallDir(), otherTyped.getInstallDir(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PlanDir", this.bean.getPlanDir(), otherTyped.getPlanDir(), true);
            this.computeDiff("PlanPath", this.bean.getPlanPath(), otherTyped.getPlanPath(), true);
            this.computeDiff("PlanStagingMode", this.bean.getPlanStagingMode(), otherTyped.getPlanStagingMode(), true);
            this.computeDiff("SecurityDDModel", this.bean.getSecurityDDModel(), otherTyped.getSecurityDDModel(), false);
            this.computeDiff("SourcePath", this.bean.getSourcePath(), otherTyped.getSourcePath(), false);
            this.computeDiff("StagingMode", this.bean.getStagingMode(), otherTyped.getStagingMode(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("VersionIdentifier", this.bean.getVersionIdentifier(), otherTyped.getVersionIdentifier(), false);
            this.computeDiff("CacheInAppDirectory", this.bean.isCacheInAppDirectory(), otherTyped.isCacheInAppDirectory(), false);
            this.computeDiff("ParallelDeployModules", this.bean.isParallelDeployModules(), otherTyped.isParallelDeployModules(), true);
            this.computeDiff("Untargeted", this.bean.isUntargeted(), otherTyped.isUntargeted(), false);
            this.computeDiff("ValidateDDSecurityData", this.bean.isValidateDDSecurityData(), otherTyped.isValidateDDSecurityData(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AppDeploymentMBeanImpl original = (AppDeploymentMBeanImpl)event.getSourceBean();
            AppDeploymentMBeanImpl proposed = (AppDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("AbsoluteAltDescriptorDir") && !prop.equals("AbsoluteAltDescriptorPath") && !prop.equals("AbsoluteInstallDir") && !prop.equals("AbsolutePlanDir") && !prop.equals("AbsolutePlanPath") && !prop.equals("AbsoluteSourcePath")) {
                  if (prop.equals("AltDescriptorDir")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("AltDescriptorPath")) {
                     original.setAltDescriptorPath(proposed.getAltDescriptorPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (prop.equals("AltWLSDescriptorPath")) {
                     original.setAltWLSDescriptorPath(proposed.getAltWLSDescriptorPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  } else if (!prop.equals("AppMBean")) {
                     if (prop.equals("ApplicationIdentifier")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("ApplicationName")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (!prop.equals("ConfiguredApplicationIdentifier") && !prop.equals("DeploymentPlan") && !prop.equals("DeploymentPlanDescriptor") && !prop.equals("DeploymentPlanExternalDescriptors")) {
                        if (prop.equals("InstallDir")) {
                           original.setInstallDir(proposed.getInstallDir());
                           original._conditionalUnset(update.isUnsetUpdate(), 18);
                        } else if (!prop.equals("LocalAltDescriptorPath") && !prop.equals("LocalInstallDir") && !prop.equals("LocalPlanDir") && !prop.equals("LocalPlanPath") && !prop.equals("LocalSourcePath")) {
                           if (prop.equals("Name")) {
                              original.setName(proposed.getName());
                              original._conditionalUnset(update.isUnsetUpdate(), 2);
                           } else if (!prop.equals("OnDemandContextPaths")) {
                              if (prop.equals("PlanDir")) {
                                 original.setPlanDir(proposed.getPlanDir());
                                 original._conditionalUnset(update.isUnsetUpdate(), 19);
                              } else if (prop.equals("PlanPath")) {
                                 original.setPlanPath(proposed.getPlanPath());
                                 original._conditionalUnset(update.isUnsetUpdate(), 20);
                              } else if (prop.equals("PlanStagingMode")) {
                                 original.setPlanStagingMode(proposed.getPlanStagingMode());
                                 original._conditionalUnset(update.isUnsetUpdate(), 25);
                              } else if (!prop.equals("RootStagingDir")) {
                                 if (prop.equals("SecurityDDModel")) {
                                    original.setSecurityDDModel(proposed.getSecurityDDModel());
                                    original._conditionalUnset(update.isUnsetUpdate(), 23);
                                 } else if (prop.equals("SourcePath")) {
                                    original.setSourcePath(proposed.getSourcePath());
                                    original._conditionalUnset(update.isUnsetUpdate(), 13);
                                 } else if (prop.equals("StagingMode")) {
                                    original.setStagingMode(proposed.getStagingMode());
                                    original._conditionalUnset(update.isUnsetUpdate(), 24);
                                 } else if (prop.equals("Tags")) {
                                    if (type == 2) {
                                       update.resetAddedObject(update.getAddedObject());
                                       original.addTag((String)update.getAddedObject());
                                    } else {
                                       if (type != 3) {
                                          throw new AssertionError("Invalid type: " + type);
                                       }

                                       original.removeTag((String)update.getRemovedObject());
                                    }

                                    if (original.getTags() == null || original.getTags().length == 0) {
                                       original._conditionalUnset(update.isUnsetUpdate(), 9);
                                    }
                                 } else if (prop.equals("VersionIdentifier")) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 21);
                                 } else if (!prop.equals("AutoDeployedApp") && !prop.equals("BackgroundDeployment")) {
                                    if (prop.equals("CacheInAppDirectory")) {
                                       original.setCacheInAppDirectory(proposed.isCacheInAppDirectory());
                                       original._conditionalUnset(update.isUnsetUpdate(), 53);
                                    } else if (!prop.equals("DynamicallyCreated") && !prop.equals("InternalApp") && !prop.equals("MultiVersionApp") && !prop.equals("OnDemandDisplayRefresh")) {
                                       if (prop.equals("ParallelDeployModules")) {
                                          original.setParallelDeployModules(proposed.isParallelDeployModules());
                                          original._conditionalUnset(update.isUnsetUpdate(), 34);
                                       } else if (prop.equals("Untargeted")) {
                                          original.setUntargeted(proposed.isUntargeted());
                                          original._conditionalUnset(update.isUnsetUpdate(), 54);
                                       } else if (prop.equals("ValidateDDSecurityData")) {
                                          original.setValidateDDSecurityData(proposed.isValidateDDSecurityData());
                                          original._conditionalUnset(update.isUnsetUpdate(), 22);
                                       } else {
                                          super.applyPropertyUpdate(event, update);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
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
            AppDeploymentMBeanImpl copy = (AppDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AltDescriptorDir")) && this.bean.isAltDescriptorDirSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("AltDescriptorPath")) && this.bean.isAltDescriptorPathSet()) {
               copy.setAltDescriptorPath(this.bean.getAltDescriptorPath());
            }

            if ((excludeProps == null || !excludeProps.contains("AltWLSDescriptorPath")) && this.bean.isAltWLSDescriptorPathSet()) {
               copy.setAltWLSDescriptorPath(this.bean.getAltWLSDescriptorPath());
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationIdentifier")) && this.bean.isApplicationIdentifierSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationName")) && this.bean.isApplicationNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("InstallDir")) && this.bean.isInstallDirSet()) {
               copy.setInstallDir(this.bean.getInstallDir());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PlanDir")) && this.bean.isPlanDirSet()) {
               copy.setPlanDir(this.bean.getPlanDir());
            }

            if ((excludeProps == null || !excludeProps.contains("PlanPath")) && this.bean.isPlanPathSet()) {
               copy.setPlanPath(this.bean.getPlanPath());
            }

            if ((excludeProps == null || !excludeProps.contains("PlanStagingMode")) && this.bean.isPlanStagingModeSet()) {
               copy.setPlanStagingMode(this.bean.getPlanStagingMode());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityDDModel")) && this.bean.isSecurityDDModelSet()) {
               copy.setSecurityDDModel(this.bean.getSecurityDDModel());
            }

            if ((excludeProps == null || !excludeProps.contains("SourcePath")) && this.bean.isSourcePathSet()) {
               copy.setSourcePath(this.bean.getSourcePath());
            }

            if ((excludeProps == null || !excludeProps.contains("StagingMode")) && this.bean.isStagingModeSet()) {
               copy.setStagingMode(this.bean.getStagingMode());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("VersionIdentifier")) && this.bean.isVersionIdentifierSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("CacheInAppDirectory")) && this.bean.isCacheInAppDirectorySet()) {
               copy.setCacheInAppDirectory(this.bean.isCacheInAppDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelDeployModules")) && this.bean.isParallelDeployModulesSet()) {
               copy.setParallelDeployModules(this.bean.isParallelDeployModules());
            }

            if ((excludeProps == null || !excludeProps.contains("Untargeted")) && this.bean.isUntargetedSet()) {
               copy.setUntargeted(this.bean.isUntargeted());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateDDSecurityData")) && this.bean.isValidateDDSecurityDataSet()) {
               copy.setValidateDDSecurityData(this.bean.isValidateDDSecurityData());
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
         this.inferSubTree(this.bean.getAppMBean(), clazz, annotation);
      }
   }
}
