package weblogic.management.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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

public class DomainLibraryMBeanImpl extends LibraryMBeanImpl implements DomainLibraryMBean, Serializable {
   private String _AbsoluteAltDescriptorDir;
   private String _AbsoluteAltDescriptorPath;
   private String _AbsoluteInstallDir;
   private String _AbsolutePlanDir;
   private String _AbsolutePlanPath;
   private String _AbsoluteSourcePath;
   private String _AltDescriptorPath;
   private ApplicationMBean _AppMBean;
   private String _ApplicationIdentifier;
   private String _ApplicationName;
   private boolean _AutoDeployedApp;
   private String _ConfiguredApplicationIdentifier;
   private byte[] _DeploymentPlan;
   private DeploymentPlanBean _DeploymentPlanDescriptor;
   private byte[] _DeploymentPlanExternalDescriptors;
   private boolean _DynamicallyCreated;
   private String _LocalAltDescriptorPath;
   private String _LocalInstallDir;
   private String _LocalPlanDir;
   private String _LocalPlanPath;
   private String _LocalSourcePath;
   private boolean _MultiVersionApp;
   private String _Name;
   private String _PlanDir;
   private String _PlanPath;
   private String _PlanStagingMode;
   private String _RootStagingDir;
   private String _SourcePath;
   private String _StagingMode;
   private String[] _Tags;
   private String _VersionIdentifier;
   private transient AppDeployment _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DomainLibraryMBeanImpl() {
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

   public DomainLibraryMBeanImpl(DescriptorBean param0, int param1) {
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

   public DomainLibraryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public String getSourcePath() {
      return this._customizer.getSourcePath();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isSourcePathInherited() {
      return false;
   }

   public boolean isSourcePathSet() {
      return this._isSet(13);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setSourcePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getSourcePath();
      this._customizer.setSourcePath(param0);
      this._postSet(13, _oldVal, param0);
   }

   public String getPlanDir() {
      return this._customizer.getPlanDir();
   }

   public boolean isPlanDirInherited() {
      return false;
   }

   public boolean isPlanDirSet() {
      return this._isSet(19);
   }

   public String getPlanPath() {
      return this._customizer.getPlanPath();
   }

   public boolean isPlanPathInherited() {
      return false;
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
      String _oldVal = this._VersionIdentifier;
      this._VersionIdentifier = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getStagingMode() {
      return this._customizer.getStagingMode();
   }

   public boolean isStagingModeInherited() {
      return false;
   }

   public boolean isStagingModeSet() {
      return this._isSet(24);
   }

   public String getPlanStagingMode() {
      return this._customizer.getPlanStagingMode();
   }

   public boolean isPlanStagingModeInherited() {
      return false;
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

   public void setPlanDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getPlanDir();
      this._customizer.setPlanDir(param0);
      this._postSet(19, _oldVal, param0);
   }

   public void setPlanPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getPlanPath();
      this._customizer.setPlanPath(param0);
      this._postSet(20, _oldVal, param0);
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
      this._DynamicallyCreated = param0;
   }

   public void setStagingMode(String param0) throws ManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{AppDeploymentMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"};
      param0 = LegalChecks.checkInEnum("StagingMode", param0, _set);
      String _oldVal = this.getStagingMode();
      this._customizer.setStagingMode(param0);
      this._postSet(24, _oldVal, param0);
   }

   public void setPlanStagingMode(String param0) throws ManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{AppDeploymentMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"};
      param0 = LegalChecks.checkInEnum("PlanStagingMode", param0, _set);
      String _oldVal = this.getPlanStagingMode();
      this._customizer.setPlanStagingMode(param0);
      this._postSet(25, _oldVal, param0);
   }

   public String getAltDescriptorPath() {
      return this._customizer.getAltDescriptorPath();
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isAltDescriptorPathInherited() {
      return false;
   }

   public boolean isAltDescriptorPathSet() {
      return this._isSet(26);
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setAltDescriptorPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getAltDescriptorPath();
      this._customizer.setAltDescriptorPath(param0);
      this._postSet(26, _oldVal, param0);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
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
      String _oldVal = this._ApplicationIdentifier;
      this._ApplicationIdentifier = param0;
      this._postSet(29, _oldVal, param0);
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
      String _oldVal = this._ApplicationName;
      this._ApplicationName = param0;
      this._postSet(30, _oldVal, param0);
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
      this._AppMBean = param0;
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
      this._AutoDeployedApp = param0;
   }

   public DeploymentPlanBean getDeploymentPlanDescriptor() {
      return this._customizer.getDeploymentPlanDescriptor();
   }

   public boolean isDeploymentPlanDescriptorInherited() {
      return false;
   }

   public boolean isDeploymentPlanDescriptorSet() {
      return this._isSet(36);
   }

   public void setDeploymentPlanDescriptor(DeploymentPlanBean param0) {
      this._customizer.setDeploymentPlanDescriptor(param0);
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
      this._RootStagingDir = param0;
   }

   public String getStagingMode(String param0) {
      return this._customizer.getStagingMode(param0);
   }

   public byte[] getDeploymentPlan() {
      return this._customizer.getDeploymentPlan();
   }

   public boolean isDeploymentPlanInherited() {
      return false;
   }

   public boolean isDeploymentPlanSet() {
      return this._isSet(51);
   }

   public void setDeploymentPlan(byte[] param0) throws InvalidAttributeValueException {
      this._DeploymentPlan = param0;
   }

   public byte[] getDeploymentPlanExternalDescriptors() {
      return this._customizer.getDeploymentPlanExternalDescriptors();
   }

   public boolean isDeploymentPlanExternalDescriptorsInherited() {
      return false;
   }

   public boolean isDeploymentPlanExternalDescriptorsSet() {
      return this._isSet(52);
   }

   public void setDeploymentPlanExternalDescriptors(byte[] param0) throws InvalidAttributeValueException {
      this._DeploymentPlanExternalDescriptors = param0;
   }

   public String getConfiguredApplicationIdentifier() {
      return this._customizer.getConfiguredApplicationIdentifier();
   }

   public boolean isConfiguredApplicationIdentifierInherited() {
      return false;
   }

   public boolean isConfiguredApplicationIdentifierSet() {
      return this._isSet(55);
   }

   public void setConfiguredApplicationIdentifier(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this._customizer.setConfiguredApplicationIdentifier(param0);
   }

   public boolean isMultiVersionApp() {
      return this._customizer.isMultiVersionApp();
   }

   public boolean isMultiVersionAppInherited() {
      return false;
   }

   public boolean isMultiVersionAppSet() {
      return this._isSet(56);
   }

   public void setMultiVersionApp(boolean param0) {
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
            case 26:
               this._customizer.setAltDescriptorPath((String)null);
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
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 56:
               this._customizer.setMultiVersionApp(false);
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
            case 18:
            case 22:
            case 23:
            case 27:
            case 28:
            case 32:
            case 33:
            case 34:
            case 37:
            case 38:
            case 53:
            case 54:
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
      return "DomainLibrary";
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
      } else if (name.equals("AltDescriptorPath")) {
         oldVal = this._AltDescriptorPath;
         this._AltDescriptorPath = (String)v;
         this._postSet(26, oldVal, this._AltDescriptorPath);
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
            } else if (name.equals("SourcePath")) {
               oldVal = this._SourcePath;
               this._SourcePath = (String)v;
               this._postSet(13, oldVal, this._SourcePath);
            } else if (name.equals("StagingMode")) {
               oldVal = this._StagingMode;
               this._StagingMode = (String)v;
               this._postSet(24, oldVal, this._StagingMode);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
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
      } else if (name.equals("AltDescriptorPath")) {
         return this._AltDescriptorPath;
      } else if (name.equals("AppMBean")) {
         return this._AppMBean;
      } else if (name.equals("ApplicationIdentifier")) {
         return this._ApplicationIdentifier;
      } else if (name.equals("ApplicationName")) {
         return this._ApplicationName;
      } else if (name.equals("AutoDeployedApp")) {
         return new Boolean(this._AutoDeployedApp);
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
      } else if (name.equals("PlanDir")) {
         return this._PlanDir;
      } else if (name.equals("PlanPath")) {
         return this._PlanPath;
      } else if (name.equals("PlanStagingMode")) {
         return this._PlanStagingMode;
      } else if (name.equals("RootStagingDir")) {
         return this._RootStagingDir;
      } else if (name.equals("SourcePath")) {
         return this._SourcePath;
      } else if (name.equals("StagingMode")) {
         return this._StagingMode;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("VersionIdentifier")) {
         return this._VersionIdentifier;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends LibraryMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 10:
            case 13:
            case 21:
            case 23:
            case 24:
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
            case 11:
               if (s.equals("source-path")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("staging-mode")) {
                  return 24;
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
            case 22:
               if (s.equals("application-identifier")) {
                  return 29;
               }
               break;
            case 25:
               if (s.equals("local-alt-descriptor-path")) {
                  return 48;
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
            case 18:
            case 22:
            case 23:
            case 27:
            case 28:
            case 32:
            case 33:
            case 34:
            case 37:
            case 38:
            case 53:
            case 54:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 13:
               return "source-path";
            case 19:
               return "plan-dir";
            case 20:
               return "plan-path";
            case 21:
               return "version-identifier";
            case 24:
               return "staging-mode";
            case 25:
               return "plan-staging-mode";
            case 26:
               return "alt-descriptor-path";
            case 29:
               return "application-identifier";
            case 30:
               return "application-name";
            case 31:
               return "appm-bean";
            case 35:
               return "auto-deployed-app";
            case 36:
               return "deployment-plan-descriptor";
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

   protected static class Helper extends LibraryMBeanImpl.Helper {
      private DomainLibraryMBeanImpl bean;

      protected Helper(DomainLibraryMBeanImpl bean) {
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
            case 18:
            case 22:
            case 23:
            case 27:
            case 28:
            case 32:
            case 33:
            case 34:
            case 37:
            case 38:
            case 53:
            case 54:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 13:
               return "SourcePath";
            case 19:
               return "PlanDir";
            case 20:
               return "PlanPath";
            case 21:
               return "VersionIdentifier";
            case 24:
               return "StagingMode";
            case 25:
               return "PlanStagingMode";
            case 26:
               return "AltDescriptorPath";
            case 29:
               return "ApplicationIdentifier";
            case 30:
               return "ApplicationName";
            case 31:
               return "AppMBean";
            case 35:
               return "AutoDeployedApp";
            case 36:
               return "DeploymentPlanDescriptor";
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
         } else if (propName.equals("AltDescriptorPath")) {
            return 26;
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
         } else if (propName.equals("PlanDir")) {
            return 19;
         } else if (propName.equals("PlanPath")) {
            return 20;
         } else if (propName.equals("PlanStagingMode")) {
            return 25;
         } else if (propName.equals("RootStagingDir")) {
            return 50;
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
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("MultiVersionApp") ? 56 : super.getPropertyIndex(propName);
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

            if (this.bean.isAltDescriptorPathSet()) {
               buf.append("AltDescriptorPath");
               buf.append(String.valueOf(this.bean.getAltDescriptorPath()));
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

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isMultiVersionAppSet()) {
               buf.append("MultiVersionApp");
               buf.append(String.valueOf(this.bean.isMultiVersionApp()));
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
            DomainLibraryMBeanImpl otherTyped = (DomainLibraryMBeanImpl)other;
            this.computeDiff("AltDescriptorPath", this.bean.getAltDescriptorPath(), otherTyped.getAltDescriptorPath(), true);
            this.computeDiff("ApplicationIdentifier", this.bean.getApplicationIdentifier(), otherTyped.getApplicationIdentifier(), false);
            this.computeDiff("ApplicationName", this.bean.getApplicationName(), otherTyped.getApplicationName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PlanDir", this.bean.getPlanDir(), otherTyped.getPlanDir(), true);
            this.computeDiff("PlanPath", this.bean.getPlanPath(), otherTyped.getPlanPath(), true);
            this.computeDiff("PlanStagingMode", this.bean.getPlanStagingMode(), otherTyped.getPlanStagingMode(), true);
            this.computeDiff("SourcePath", this.bean.getSourcePath(), otherTyped.getSourcePath(), false);
            this.computeDiff("StagingMode", this.bean.getStagingMode(), otherTyped.getStagingMode(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("VersionIdentifier", this.bean.getVersionIdentifier(), otherTyped.getVersionIdentifier(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DomainLibraryMBeanImpl original = (DomainLibraryMBeanImpl)event.getSourceBean();
            DomainLibraryMBeanImpl proposed = (DomainLibraryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("AbsoluteAltDescriptorDir") && !prop.equals("AbsoluteAltDescriptorPath") && !prop.equals("AbsoluteInstallDir") && !prop.equals("AbsolutePlanDir") && !prop.equals("AbsolutePlanPath") && !prop.equals("AbsoluteSourcePath")) {
                  if (prop.equals("AltDescriptorPath")) {
                     original.setAltDescriptorPath(proposed.getAltDescriptorPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (!prop.equals("AppMBean")) {
                     if (prop.equals("ApplicationIdentifier")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("ApplicationName")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (!prop.equals("ConfiguredApplicationIdentifier") && !prop.equals("DeploymentPlan") && !prop.equals("DeploymentPlanDescriptor") && !prop.equals("DeploymentPlanExternalDescriptors") && !prop.equals("LocalAltDescriptorPath") && !prop.equals("LocalInstallDir") && !prop.equals("LocalPlanDir") && !prop.equals("LocalPlanPath") && !prop.equals("LocalSourcePath")) {
                        if (prop.equals("Name")) {
                           original.setName(proposed.getName());
                           original._conditionalUnset(update.isUnsetUpdate(), 2);
                        } else if (prop.equals("PlanDir")) {
                           original.setPlanDir(proposed.getPlanDir());
                           original._conditionalUnset(update.isUnsetUpdate(), 19);
                        } else if (prop.equals("PlanPath")) {
                           original.setPlanPath(proposed.getPlanPath());
                           original._conditionalUnset(update.isUnsetUpdate(), 20);
                        } else if (prop.equals("PlanStagingMode")) {
                           original.setPlanStagingMode(proposed.getPlanStagingMode());
                           original._conditionalUnset(update.isUnsetUpdate(), 25);
                        } else if (!prop.equals("RootStagingDir")) {
                           if (prop.equals("SourcePath")) {
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
                           } else if (!prop.equals("AutoDeployedApp") && !prop.equals("DynamicallyCreated") && !prop.equals("MultiVersionApp")) {
                              super.applyPropertyUpdate(event, update);
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
            DomainLibraryMBeanImpl copy = (DomainLibraryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AltDescriptorPath")) && this.bean.isAltDescriptorPathSet()) {
               copy.setAltDescriptorPath(this.bean.getAltDescriptorPath());
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationIdentifier")) && this.bean.isApplicationIdentifierSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationName")) && this.bean.isApplicationNameSet()) {
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
