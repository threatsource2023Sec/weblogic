package weblogic.management.configuration;

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
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.WebAppContainer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebAppContainerMBeanImpl extends ConfigurationMBeanImpl implements WebAppContainerMBean, Serializable {
   private boolean _AllowAllRoles;
   private boolean _AuthCookieEnabled;
   private boolean _ChangeSessionIDOnAuthentication;
   private boolean _ClientCertProxyEnabled;
   private boolean _DynamicallyCreated;
   private boolean _FilterDispatchedRequestsEnabled;
   private GzipCompressionMBean _GzipCompression;
   private Http2ConfigMBean _Http2Config;
   private boolean _HttpTraceSupportEnabled;
   private boolean _JSPCompilerBackwardsCompatible;
   private boolean _JaxRsMonitoringDefaultBehavior;
   private int _MaxPostSize;
   private int _MaxPostTimeSecs;
   private int _MaxRequestParameterCount;
   private int _MaxRequestParamterCount;
   private String _MimeMappingFile;
   private String _Name;
   private boolean _OptimisticSerialization;
   private boolean _OverloadProtectionEnabled;
   private String _P3PHeaderValue;
   private int _PostTimeoutSecs;
   private boolean _ReloginEnabled;
   private boolean _RetainOriginalURL;
   private boolean _RtexprvalueJspParamName;
   private boolean _ServletAuthenticationFormURL;
   private int _ServletReloadCheckSecs;
   private boolean _ShowArchivedRealPathEnabled;
   private String[] _Tags;
   private boolean _WAPEnabled;
   private boolean _WeblogicPluginEnabled;
   private boolean _WorkContextPropagationEnabled;
   private String _XPoweredByHeaderLevel;
   private transient WebAppContainer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WebAppContainerMBeanImpl() {
      try {
         this._customizer = new WebAppContainer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WebAppContainerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WebAppContainer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WebAppContainerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WebAppContainer(this);
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

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isReloginEnabled() {
      return this._ReloginEnabled;
   }

   public boolean isReloginEnabledInherited() {
      return false;
   }

   public boolean isReloginEnabledSet() {
      return this._isSet(10);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setReloginEnabled(boolean param0) {
      boolean _oldVal = this._ReloginEnabled;
      this._ReloginEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isAllowAllRoles() {
      return this._AllowAllRoles;
   }

   public boolean isAllowAllRolesInherited() {
      return false;
   }

   public boolean isAllowAllRolesSet() {
      return this._isSet(11);
   }

   public void setAllowAllRoles(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._AllowAllRoles;
      this._AllowAllRoles = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isFilterDispatchedRequestsEnabled() {
      return this._FilterDispatchedRequestsEnabled;
   }

   public boolean isFilterDispatchedRequestsEnabledInherited() {
      return false;
   }

   public boolean isFilterDispatchedRequestsEnabledSet() {
      return this._isSet(12);
   }

   public void setFilterDispatchedRequestsEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._FilterDispatchedRequestsEnabled;
      this._FilterDispatchedRequestsEnabled = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isOverloadProtectionEnabled() {
      return this._OverloadProtectionEnabled;
   }

   public boolean isOverloadProtectionEnabledInherited() {
      return false;
   }

   public boolean isOverloadProtectionEnabledSet() {
      return this._isSet(13);
   }

   public void setOverloadProtectionEnabled(boolean param0) {
      boolean _oldVal = this._OverloadProtectionEnabled;
      this._OverloadProtectionEnabled = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getXPoweredByHeaderLevel() {
      return this._XPoweredByHeaderLevel;
   }

   public boolean isXPoweredByHeaderLevelInherited() {
      return false;
   }

   public boolean isXPoweredByHeaderLevelSet() {
      return this._isSet(14);
   }

   public void setXPoweredByHeaderLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NONE", "SHORT", "MEDIUM", "FULL"};
      param0 = LegalChecks.checkInEnum("XPoweredByHeaderLevel", param0, _set);
      String _oldVal = this._XPoweredByHeaderLevel;
      this._XPoweredByHeaderLevel = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getMimeMappingFile() {
      return this._MimeMappingFile;
   }

   public boolean isMimeMappingFileInherited() {
      return false;
   }

   public boolean isMimeMappingFileSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMimeMappingFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MimeMappingFile;
      this._MimeMappingFile = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isOptimisticSerialization() {
      return this._OptimisticSerialization;
   }

   public boolean isOptimisticSerializationInherited() {
      return false;
   }

   public boolean isOptimisticSerializationSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setOptimisticSerialization(boolean param0) {
      boolean _oldVal = this._OptimisticSerialization;
      this._OptimisticSerialization = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isRetainOriginalURL() {
      return this._RetainOriginalURL;
   }

   public boolean isRetainOriginalURLInherited() {
      return false;
   }

   public boolean isRetainOriginalURLSet() {
      return this._isSet(17);
   }

   public void setRetainOriginalURL(boolean param0) {
      boolean _oldVal = this._RetainOriginalURL;
      this._RetainOriginalURL = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isServletAuthenticationFormURL() {
      return this._ServletAuthenticationFormURL;
   }

   public boolean isServletAuthenticationFormURLInherited() {
      return false;
   }

   public boolean isServletAuthenticationFormURLSet() {
      return this._isSet(18);
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

   public void setServletAuthenticationFormURL(boolean param0) {
      boolean _oldVal = this._ServletAuthenticationFormURL;
      this._ServletAuthenticationFormURL = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isRtexprvalueJspParamName() {
      return this._RtexprvalueJspParamName;
   }

   public boolean isRtexprvalueJspParamNameInherited() {
      return false;
   }

   public boolean isRtexprvalueJspParamNameSet() {
      return this._isSet(19);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setRtexprvalueJspParamName(boolean param0) {
      boolean _oldVal = this._RtexprvalueJspParamName;
      this._RtexprvalueJspParamName = param0;
      this._postSet(19, _oldVal, param0);
   }

   public void setClientCertProxyEnabled(boolean param0) {
      boolean _oldVal = this._ClientCertProxyEnabled;
      this._ClientCertProxyEnabled = param0;
      this._postSet(20, _oldVal, param0);
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

   public boolean isClientCertProxyEnabled() {
      return this._ClientCertProxyEnabled;
   }

   public boolean isClientCertProxyEnabledInherited() {
      return false;
   }

   public boolean isClientCertProxyEnabledSet() {
      return this._isSet(20);
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

   public void setHttpTraceSupportEnabled(boolean param0) {
      boolean _oldVal = this._HttpTraceSupportEnabled;
      this._HttpTraceSupportEnabled = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean isHttpTraceSupportEnabled() {
      return this._HttpTraceSupportEnabled;
   }

   public boolean isHttpTraceSupportEnabledInherited() {
      return false;
   }

   public boolean isHttpTraceSupportEnabledSet() {
      return this._isSet(21);
   }

   public void setWeblogicPluginEnabled(boolean param0) {
      boolean _oldVal = this._WeblogicPluginEnabled;
      this._WeblogicPluginEnabled = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isWeblogicPluginEnabled() {
      return this._WeblogicPluginEnabled;
   }

   public boolean isWeblogicPluginEnabledInherited() {
      return false;
   }

   public boolean isWeblogicPluginEnabledSet() {
      return this._isSet(22);
   }

   public void setAuthCookieEnabled(boolean param0) {
      boolean _oldVal = this._AuthCookieEnabled;
      this._AuthCookieEnabled = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isAuthCookieEnabled() {
      return this._AuthCookieEnabled;
   }

   public boolean isAuthCookieEnabledInherited() {
      return false;
   }

   public boolean isAuthCookieEnabledSet() {
      return this._isSet(23);
   }

   public void setWAPEnabled(boolean param0) {
      boolean _oldVal = this._WAPEnabled;
      this._WAPEnabled = param0;
      this._postSet(24, _oldVal, param0);
   }

   public boolean isWAPEnabled() {
      return this._WAPEnabled;
   }

   public boolean isWAPEnabledInherited() {
      return false;
   }

   public boolean isWAPEnabledSet() {
      return this._isSet(24);
   }

   public void setPostTimeoutSecs(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("PostTimeoutSecs", (long)param0, 0L, 120L);
      int _oldVal = this._PostTimeoutSecs;
      this._PostTimeoutSecs = param0;
      this._postSet(25, _oldVal, param0);
   }

   public int getPostTimeoutSecs() {
      return this._PostTimeoutSecs;
   }

   public boolean isPostTimeoutSecsInherited() {
      return false;
   }

   public boolean isPostTimeoutSecsSet() {
      return this._isSet(25);
   }

   public void setMaxPostTimeSecs(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._MaxPostTimeSecs;
      this._MaxPostTimeSecs = param0;
      this._postSet(26, _oldVal, param0);
   }

   public int getMaxPostTimeSecs() {
      return this._MaxPostTimeSecs;
   }

   public boolean isMaxPostTimeSecsInherited() {
      return false;
   }

   public boolean isMaxPostTimeSecsSet() {
      return this._isSet(26);
   }

   public void setMaxPostSize(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._MaxPostSize;
      this._MaxPostSize = param0;
      this._postSet(27, _oldVal, param0);
   }

   public int getMaxPostSize() {
      return this._MaxPostSize;
   }

   public boolean isMaxPostSizeInherited() {
      return false;
   }

   public boolean isMaxPostSizeSet() {
      return this._isSet(27);
   }

   public void setMaxRequestParameterCount(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._MaxRequestParameterCount;
      this._MaxRequestParameterCount = param0;
      this._postSet(28, _oldVal, param0);
   }

   public int getMaxRequestParameterCount() {
      return this._MaxRequestParameterCount;
   }

   public boolean isMaxRequestParameterCountInherited() {
      return false;
   }

   public boolean isMaxRequestParameterCountSet() {
      return this._isSet(28);
   }

   public void setMaxRequestParamterCount(int param0) throws InvalidAttributeValueException {
      int _oldVal = this.getMaxRequestParamterCount();
      this._customizer.setMaxRequestParamterCount(param0);
      this._postSet(29, _oldVal, param0);
   }

   public int getMaxRequestParamterCount() {
      return this._customizer.getMaxRequestParamterCount();
   }

   public boolean isMaxRequestParamterCountInherited() {
      return false;
   }

   public boolean isMaxRequestParamterCountSet() {
      return this._isSet(29);
   }

   public boolean isWorkContextPropagationEnabled() {
      return this._WorkContextPropagationEnabled;
   }

   public boolean isWorkContextPropagationEnabledInherited() {
      return false;
   }

   public boolean isWorkContextPropagationEnabledSet() {
      return this._isSet(30);
   }

   public void setWorkContextPropagationEnabled(boolean param0) {
      boolean _oldVal = this._WorkContextPropagationEnabled;
      this._WorkContextPropagationEnabled = param0;
      this._postSet(30, _oldVal, param0);
   }

   public void setP3PHeaderValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._P3PHeaderValue;
      this._P3PHeaderValue = param0;
      this._postSet(31, _oldVal, param0);
   }

   public String getP3PHeaderValue() {
      return this._P3PHeaderValue;
   }

   public boolean isP3PHeaderValueInherited() {
      return false;
   }

   public boolean isP3PHeaderValueSet() {
      return this._isSet(31);
   }

   public boolean isJSPCompilerBackwardsCompatible() {
      return this._JSPCompilerBackwardsCompatible;
   }

   public boolean isJSPCompilerBackwardsCompatibleInherited() {
      return false;
   }

   public boolean isJSPCompilerBackwardsCompatibleSet() {
      return this._isSet(32);
   }

   public void setJSPCompilerBackwardsCompatible(boolean param0) {
      boolean _oldVal = this._JSPCompilerBackwardsCompatible;
      this._JSPCompilerBackwardsCompatible = param0;
      this._postSet(32, _oldVal, param0);
   }

   public int getServletReloadCheckSecs() {
      if (!this._isSet(33)) {
         return this._isProductionModeEnabled() ? -1 : 1;
      } else {
         return this._ServletReloadCheckSecs;
      }
   }

   public boolean isServletReloadCheckSecsInherited() {
      return false;
   }

   public boolean isServletReloadCheckSecsSet() {
      return this._isSet(33);
   }

   public void setServletReloadCheckSecs(int param0) {
      int _oldVal = this._ServletReloadCheckSecs;
      this._ServletReloadCheckSecs = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean isShowArchivedRealPathEnabled() {
      return this._ShowArchivedRealPathEnabled;
   }

   public boolean isShowArchivedRealPathEnabledInherited() {
      return false;
   }

   public boolean isShowArchivedRealPathEnabledSet() {
      return this._isSet(34);
   }

   public void setShowArchivedRealPathEnabled(boolean param0) {
      boolean _oldVal = this._ShowArchivedRealPathEnabled;
      this._ShowArchivedRealPathEnabled = param0;
      this._postSet(34, _oldVal, param0);
   }

   public boolean isChangeSessionIDOnAuthentication() {
      return this._ChangeSessionIDOnAuthentication;
   }

   public boolean isChangeSessionIDOnAuthenticationInherited() {
      return false;
   }

   public boolean isChangeSessionIDOnAuthenticationSet() {
      return this._isSet(35);
   }

   public void setChangeSessionIDOnAuthentication(boolean param0) {
      boolean _oldVal = this._ChangeSessionIDOnAuthentication;
      this._ChangeSessionIDOnAuthentication = param0;
      this._postSet(35, _oldVal, param0);
   }

   public GzipCompressionMBean getGzipCompression() {
      return this._GzipCompression;
   }

   public boolean isGzipCompressionInherited() {
      return false;
   }

   public boolean isGzipCompressionSet() {
      return this._isSet(36) || this._isAnythingSet((AbstractDescriptorBean)this.getGzipCompression());
   }

   public void setGzipCompression(GzipCompressionMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 36)) {
         this._postCreate(_child);
      }

      GzipCompressionMBean _oldVal = this._GzipCompression;
      this._GzipCompression = param0;
      this._postSet(36, _oldVal, param0);
   }

   public Http2ConfigMBean getHttp2Config() {
      return this._Http2Config;
   }

   public boolean isHttp2ConfigInherited() {
      return false;
   }

   public boolean isHttp2ConfigSet() {
      return this._isSet(37) || this._isAnythingSet((AbstractDescriptorBean)this.getHttp2Config());
   }

   public void setHttp2Config(Http2ConfigMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 37)) {
         this._postCreate(_child);
      }

      Http2ConfigMBean _oldVal = this._Http2Config;
      this._Http2Config = param0;
      this._postSet(37, _oldVal, param0);
   }

   public void setJaxRsMonitoringDefaultBehavior(boolean param0) {
      boolean _oldVal = this._JaxRsMonitoringDefaultBehavior;
      this._JaxRsMonitoringDefaultBehavior = param0;
      this._postSet(38, _oldVal, param0);
   }

   public boolean isJaxRsMonitoringDefaultBehavior() {
      return this._JaxRsMonitoringDefaultBehavior;
   }

   public boolean isJaxRsMonitoringDefaultBehaviorInherited() {
      return false;
   }

   public boolean isJaxRsMonitoringDefaultBehaviorSet() {
      return this._isSet(38);
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
      return super._isAnythingSet() || this.isGzipCompressionSet() || this.isHttp2ConfigSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 36;
      }

      try {
         switch (idx) {
            case 36:
               this._GzipCompression = new GzipCompressionMBeanImpl(this, 36);
               this._postCreate((AbstractDescriptorBean)this._GzipCompression);
               if (initOne) {
                  break;
               }
            case 37:
               this._Http2Config = new Http2ConfigMBeanImpl(this, 37);
               this._postCreate((AbstractDescriptorBean)this._Http2Config);
               if (initOne) {
                  break;
               }
            case 27:
               this._MaxPostSize = -1;
               if (initOne) {
                  break;
               }
            case 26:
               this._MaxPostTimeSecs = -1;
               if (initOne) {
                  break;
               }
            case 28:
               this._MaxRequestParameterCount = 10000;
               if (initOne) {
                  break;
               }
            case 29:
               this._customizer.setMaxRequestParamterCount(10000);
               if (initOne) {
                  break;
               }
            case 15:
               this._MimeMappingFile = "./config/mimemappings.properties";
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 31:
               this._P3PHeaderValue = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._PostTimeoutSecs = 30;
               if (initOne) {
                  break;
               }
            case 33:
               this._ServletReloadCheckSecs = 1;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 14:
               this._XPoweredByHeaderLevel = "NONE";
               if (initOne) {
                  break;
               }
            case 11:
               this._AllowAllRoles = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._AuthCookieEnabled = true;
               if (initOne) {
                  break;
               }
            case 35:
               this._ChangeSessionIDOnAuthentication = true;
               if (initOne) {
                  break;
               }
            case 20:
               this._ClientCertProxyEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._FilterDispatchedRequestsEnabled = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._HttpTraceSupportEnabled = false;
               if (initOne) {
                  break;
               }
            case 32:
               this._JSPCompilerBackwardsCompatible = false;
               if (initOne) {
                  break;
               }
            case 38:
               this._JaxRsMonitoringDefaultBehavior = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._OptimisticSerialization = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._OverloadProtectionEnabled = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._ReloginEnabled = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._RetainOriginalURL = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._RtexprvalueJspParamName = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._ServletAuthenticationFormURL = true;
               if (initOne) {
                  break;
               }
            case 34:
               this._ShowArchivedRealPathEnabled = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._WAPEnabled = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._WeblogicPluginEnabled = false;
               if (initOne) {
                  break;
               }
            case 30:
               this._WorkContextPropagationEnabled = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "WebAppContainer";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AllowAllRoles")) {
         oldVal = this._AllowAllRoles;
         this._AllowAllRoles = (Boolean)v;
         this._postSet(11, oldVal, this._AllowAllRoles);
      } else if (name.equals("AuthCookieEnabled")) {
         oldVal = this._AuthCookieEnabled;
         this._AuthCookieEnabled = (Boolean)v;
         this._postSet(23, oldVal, this._AuthCookieEnabled);
      } else if (name.equals("ChangeSessionIDOnAuthentication")) {
         oldVal = this._ChangeSessionIDOnAuthentication;
         this._ChangeSessionIDOnAuthentication = (Boolean)v;
         this._postSet(35, oldVal, this._ChangeSessionIDOnAuthentication);
      } else if (name.equals("ClientCertProxyEnabled")) {
         oldVal = this._ClientCertProxyEnabled;
         this._ClientCertProxyEnabled = (Boolean)v;
         this._postSet(20, oldVal, this._ClientCertProxyEnabled);
      } else if (name.equals("DynamicallyCreated")) {
         oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("FilterDispatchedRequestsEnabled")) {
         oldVal = this._FilterDispatchedRequestsEnabled;
         this._FilterDispatchedRequestsEnabled = (Boolean)v;
         this._postSet(12, oldVal, this._FilterDispatchedRequestsEnabled);
      } else if (name.equals("GzipCompression")) {
         GzipCompressionMBean oldVal = this._GzipCompression;
         this._GzipCompression = (GzipCompressionMBean)v;
         this._postSet(36, oldVal, this._GzipCompression);
      } else if (name.equals("Http2Config")) {
         Http2ConfigMBean oldVal = this._Http2Config;
         this._Http2Config = (Http2ConfigMBean)v;
         this._postSet(37, oldVal, this._Http2Config);
      } else if (name.equals("HttpTraceSupportEnabled")) {
         oldVal = this._HttpTraceSupportEnabled;
         this._HttpTraceSupportEnabled = (Boolean)v;
         this._postSet(21, oldVal, this._HttpTraceSupportEnabled);
      } else if (name.equals("JSPCompilerBackwardsCompatible")) {
         oldVal = this._JSPCompilerBackwardsCompatible;
         this._JSPCompilerBackwardsCompatible = (Boolean)v;
         this._postSet(32, oldVal, this._JSPCompilerBackwardsCompatible);
      } else if (name.equals("JaxRsMonitoringDefaultBehavior")) {
         oldVal = this._JaxRsMonitoringDefaultBehavior;
         this._JaxRsMonitoringDefaultBehavior = (Boolean)v;
         this._postSet(38, oldVal, this._JaxRsMonitoringDefaultBehavior);
      } else {
         int oldVal;
         if (name.equals("MaxPostSize")) {
            oldVal = this._MaxPostSize;
            this._MaxPostSize = (Integer)v;
            this._postSet(27, oldVal, this._MaxPostSize);
         } else if (name.equals("MaxPostTimeSecs")) {
            oldVal = this._MaxPostTimeSecs;
            this._MaxPostTimeSecs = (Integer)v;
            this._postSet(26, oldVal, this._MaxPostTimeSecs);
         } else if (name.equals("MaxRequestParameterCount")) {
            oldVal = this._MaxRequestParameterCount;
            this._MaxRequestParameterCount = (Integer)v;
            this._postSet(28, oldVal, this._MaxRequestParameterCount);
         } else if (name.equals("MaxRequestParamterCount")) {
            oldVal = this._MaxRequestParamterCount;
            this._MaxRequestParamterCount = (Integer)v;
            this._postSet(29, oldVal, this._MaxRequestParamterCount);
         } else {
            String oldVal;
            if (name.equals("MimeMappingFile")) {
               oldVal = this._MimeMappingFile;
               this._MimeMappingFile = (String)v;
               this._postSet(15, oldVal, this._MimeMappingFile);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("OptimisticSerialization")) {
               oldVal = this._OptimisticSerialization;
               this._OptimisticSerialization = (Boolean)v;
               this._postSet(16, oldVal, this._OptimisticSerialization);
            } else if (name.equals("OverloadProtectionEnabled")) {
               oldVal = this._OverloadProtectionEnabled;
               this._OverloadProtectionEnabled = (Boolean)v;
               this._postSet(13, oldVal, this._OverloadProtectionEnabled);
            } else if (name.equals("P3PHeaderValue")) {
               oldVal = this._P3PHeaderValue;
               this._P3PHeaderValue = (String)v;
               this._postSet(31, oldVal, this._P3PHeaderValue);
            } else if (name.equals("PostTimeoutSecs")) {
               oldVal = this._PostTimeoutSecs;
               this._PostTimeoutSecs = (Integer)v;
               this._postSet(25, oldVal, this._PostTimeoutSecs);
            } else if (name.equals("ReloginEnabled")) {
               oldVal = this._ReloginEnabled;
               this._ReloginEnabled = (Boolean)v;
               this._postSet(10, oldVal, this._ReloginEnabled);
            } else if (name.equals("RetainOriginalURL")) {
               oldVal = this._RetainOriginalURL;
               this._RetainOriginalURL = (Boolean)v;
               this._postSet(17, oldVal, this._RetainOriginalURL);
            } else if (name.equals("RtexprvalueJspParamName")) {
               oldVal = this._RtexprvalueJspParamName;
               this._RtexprvalueJspParamName = (Boolean)v;
               this._postSet(19, oldVal, this._RtexprvalueJspParamName);
            } else if (name.equals("ServletAuthenticationFormURL")) {
               oldVal = this._ServletAuthenticationFormURL;
               this._ServletAuthenticationFormURL = (Boolean)v;
               this._postSet(18, oldVal, this._ServletAuthenticationFormURL);
            } else if (name.equals("ServletReloadCheckSecs")) {
               oldVal = this._ServletReloadCheckSecs;
               this._ServletReloadCheckSecs = (Integer)v;
               this._postSet(33, oldVal, this._ServletReloadCheckSecs);
            } else if (name.equals("ShowArchivedRealPathEnabled")) {
               oldVal = this._ShowArchivedRealPathEnabled;
               this._ShowArchivedRealPathEnabled = (Boolean)v;
               this._postSet(34, oldVal, this._ShowArchivedRealPathEnabled);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("WAPEnabled")) {
               oldVal = this._WAPEnabled;
               this._WAPEnabled = (Boolean)v;
               this._postSet(24, oldVal, this._WAPEnabled);
            } else if (name.equals("WeblogicPluginEnabled")) {
               oldVal = this._WeblogicPluginEnabled;
               this._WeblogicPluginEnabled = (Boolean)v;
               this._postSet(22, oldVal, this._WeblogicPluginEnabled);
            } else if (name.equals("WorkContextPropagationEnabled")) {
               oldVal = this._WorkContextPropagationEnabled;
               this._WorkContextPropagationEnabled = (Boolean)v;
               this._postSet(30, oldVal, this._WorkContextPropagationEnabled);
            } else if (name.equals("XPoweredByHeaderLevel")) {
               oldVal = this._XPoweredByHeaderLevel;
               this._XPoweredByHeaderLevel = (String)v;
               this._postSet(14, oldVal, this._XPoweredByHeaderLevel);
            } else if (name.equals("customizer")) {
               WebAppContainer oldVal = this._customizer;
               this._customizer = (WebAppContainer)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllowAllRoles")) {
         return new Boolean(this._AllowAllRoles);
      } else if (name.equals("AuthCookieEnabled")) {
         return new Boolean(this._AuthCookieEnabled);
      } else if (name.equals("ChangeSessionIDOnAuthentication")) {
         return new Boolean(this._ChangeSessionIDOnAuthentication);
      } else if (name.equals("ClientCertProxyEnabled")) {
         return new Boolean(this._ClientCertProxyEnabled);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FilterDispatchedRequestsEnabled")) {
         return new Boolean(this._FilterDispatchedRequestsEnabled);
      } else if (name.equals("GzipCompression")) {
         return this._GzipCompression;
      } else if (name.equals("Http2Config")) {
         return this._Http2Config;
      } else if (name.equals("HttpTraceSupportEnabled")) {
         return new Boolean(this._HttpTraceSupportEnabled);
      } else if (name.equals("JSPCompilerBackwardsCompatible")) {
         return new Boolean(this._JSPCompilerBackwardsCompatible);
      } else if (name.equals("JaxRsMonitoringDefaultBehavior")) {
         return new Boolean(this._JaxRsMonitoringDefaultBehavior);
      } else if (name.equals("MaxPostSize")) {
         return new Integer(this._MaxPostSize);
      } else if (name.equals("MaxPostTimeSecs")) {
         return new Integer(this._MaxPostTimeSecs);
      } else if (name.equals("MaxRequestParameterCount")) {
         return new Integer(this._MaxRequestParameterCount);
      } else if (name.equals("MaxRequestParamterCount")) {
         return new Integer(this._MaxRequestParamterCount);
      } else if (name.equals("MimeMappingFile")) {
         return this._MimeMappingFile;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OptimisticSerialization")) {
         return new Boolean(this._OptimisticSerialization);
      } else if (name.equals("OverloadProtectionEnabled")) {
         return new Boolean(this._OverloadProtectionEnabled);
      } else if (name.equals("P3PHeaderValue")) {
         return this._P3PHeaderValue;
      } else if (name.equals("PostTimeoutSecs")) {
         return new Integer(this._PostTimeoutSecs);
      } else if (name.equals("ReloginEnabled")) {
         return new Boolean(this._ReloginEnabled);
      } else if (name.equals("RetainOriginalURL")) {
         return new Boolean(this._RetainOriginalURL);
      } else if (name.equals("RtexprvalueJspParamName")) {
         return new Boolean(this._RtexprvalueJspParamName);
      } else if (name.equals("ServletAuthenticationFormURL")) {
         return new Boolean(this._ServletAuthenticationFormURL);
      } else if (name.equals("ServletReloadCheckSecs")) {
         return new Integer(this._ServletReloadCheckSecs);
      } else if (name.equals("ShowArchivedRealPathEnabled")) {
         return new Boolean(this._ShowArchivedRealPathEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("WAPEnabled")) {
         return new Boolean(this._WAPEnabled);
      } else if (name.equals("WeblogicPluginEnabled")) {
         return new Boolean(this._WeblogicPluginEnabled);
      } else if (name.equals("WorkContextPropagationEnabled")) {
         return new Boolean(this._WorkContextPropagationEnabled);
      } else if (name.equals("XPoweredByHeaderLevel")) {
         return this._XPoweredByHeaderLevel;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 8:
            case 9:
            case 10:
            case 14:
            case 20:
            case 21:
            case 22:
            case 28:
            case 29:
            case 30:
            default:
               break;
            case 11:
               if (s.equals("wap-enabled")) {
                  return 24;
               }
               break;
            case 12:
               if (s.equals("http2-config")) {
                  return 37;
               }
               break;
            case 13:
               if (s.equals("max-post-size")) {
                  return 27;
               }
               break;
            case 15:
               if (s.equals("allow-all-roles")) {
                  return 11;
               }

               if (s.equals("relogin-enabled")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("gzip-compression")) {
                  return 36;
               }

               if (s.equals("p3p-header-value")) {
                  return 31;
               }
               break;
            case 17:
               if (s.equals("mime-mapping-file")) {
                  return 15;
               }

               if (s.equals("post-timeout-secs")) {
                  return 25;
               }
               break;
            case 18:
               if (s.equals("max-post-time-secs")) {
                  return 26;
               }
               break;
            case 19:
               if (s.equals("auth-cookie-enabled")) {
                  return 23;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("retain-original-url")) {
                  return 17;
               }
               break;
            case 23:
               if (s.equals("weblogic-plugin-enabled")) {
                  return 22;
               }
               break;
            case 24:
               if (s.equals("optimistic-serialization")) {
                  return 16;
               }
               break;
            case 25:
               if (s.equals("servlet-reload-check-secs")) {
                  return 33;
               }

               if (s.equals("x-powered-by-header-level")) {
                  return 14;
               }

               if (s.equals("client-cert-proxy-enabled")) {
                  return 20;
               }
               break;
            case 26:
               if (s.equals("max-request-paramter-count")) {
                  return 29;
               }

               if (s.equals("http-trace-support-enabled")) {
                  return 21;
               }

               if (s.equals("rtexprvalue-jsp-param-name")) {
                  return 19;
               }
               break;
            case 27:
               if (s.equals("max-request-parameter-count")) {
                  return 28;
               }

               if (s.equals("overload-protection-enabled")) {
                  return 13;
               }
               break;
            case 31:
               if (s.equals("servlet-authentication-form-url")) {
                  return 18;
               }

               if (s.equals("show-archived-real-path-enabled")) {
                  return 34;
               }
               break;
            case 32:
               if (s.equals("work-context-propagation-enabled")) {
                  return 30;
               }
               break;
            case 33:
               if (s.equals("jsp-compiler-backwards-compatible")) {
                  return 32;
               }
               break;
            case 34:
               if (s.equals("change-sessionid-on-authentication")) {
                  return 35;
               }

               if (s.equals("filter-dispatched-requests-enabled")) {
                  return 12;
               }

               if (s.equals("jax-rs-monitoring-default-behavior")) {
                  return 38;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 36:
               return new GzipCompressionMBeanImpl.SchemaHelper2();
            case 37:
               return new Http2ConfigMBeanImpl.SchemaHelper2();
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "relogin-enabled";
            case 11:
               return "allow-all-roles";
            case 12:
               return "filter-dispatched-requests-enabled";
            case 13:
               return "overload-protection-enabled";
            case 14:
               return "x-powered-by-header-level";
            case 15:
               return "mime-mapping-file";
            case 16:
               return "optimistic-serialization";
            case 17:
               return "retain-original-url";
            case 18:
               return "servlet-authentication-form-url";
            case 19:
               return "rtexprvalue-jsp-param-name";
            case 20:
               return "client-cert-proxy-enabled";
            case 21:
               return "http-trace-support-enabled";
            case 22:
               return "weblogic-plugin-enabled";
            case 23:
               return "auth-cookie-enabled";
            case 24:
               return "wap-enabled";
            case 25:
               return "post-timeout-secs";
            case 26:
               return "max-post-time-secs";
            case 27:
               return "max-post-size";
            case 28:
               return "max-request-parameter-count";
            case 29:
               return "max-request-paramter-count";
            case 30:
               return "work-context-propagation-enabled";
            case 31:
               return "p3p-header-value";
            case 32:
               return "jsp-compiler-backwards-compatible";
            case 33:
               return "servlet-reload-check-secs";
            case 34:
               return "show-archived-real-path-enabled";
            case 35:
               return "change-sessionid-on-authentication";
            case 36:
               return "gzip-compression";
            case 37:
               return "http2-config";
            case 38:
               return "jax-rs-monitoring-default-behavior";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 36:
               return true;
            case 37:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
            case 29:
            case 32:
            case 34:
            case 35:
            case 36:
            default:
               return super.isConfigurable(propIndex);
            case 30:
               return true;
            case 31:
               return true;
            case 33:
               return true;
            case 37:
               return true;
            case 38:
               return true;
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebAppContainerMBeanImpl bean;

      protected Helper(WebAppContainerMBeanImpl bean) {
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
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "ReloginEnabled";
            case 11:
               return "AllowAllRoles";
            case 12:
               return "FilterDispatchedRequestsEnabled";
            case 13:
               return "OverloadProtectionEnabled";
            case 14:
               return "XPoweredByHeaderLevel";
            case 15:
               return "MimeMappingFile";
            case 16:
               return "OptimisticSerialization";
            case 17:
               return "RetainOriginalURL";
            case 18:
               return "ServletAuthenticationFormURL";
            case 19:
               return "RtexprvalueJspParamName";
            case 20:
               return "ClientCertProxyEnabled";
            case 21:
               return "HttpTraceSupportEnabled";
            case 22:
               return "WeblogicPluginEnabled";
            case 23:
               return "AuthCookieEnabled";
            case 24:
               return "WAPEnabled";
            case 25:
               return "PostTimeoutSecs";
            case 26:
               return "MaxPostTimeSecs";
            case 27:
               return "MaxPostSize";
            case 28:
               return "MaxRequestParameterCount";
            case 29:
               return "MaxRequestParamterCount";
            case 30:
               return "WorkContextPropagationEnabled";
            case 31:
               return "P3PHeaderValue";
            case 32:
               return "JSPCompilerBackwardsCompatible";
            case 33:
               return "ServletReloadCheckSecs";
            case 34:
               return "ShowArchivedRealPathEnabled";
            case 35:
               return "ChangeSessionIDOnAuthentication";
            case 36:
               return "GzipCompression";
            case 37:
               return "Http2Config";
            case 38:
               return "JaxRsMonitoringDefaultBehavior";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("GzipCompression")) {
            return 36;
         } else if (propName.equals("Http2Config")) {
            return 37;
         } else if (propName.equals("MaxPostSize")) {
            return 27;
         } else if (propName.equals("MaxPostTimeSecs")) {
            return 26;
         } else if (propName.equals("MaxRequestParameterCount")) {
            return 28;
         } else if (propName.equals("MaxRequestParamterCount")) {
            return 29;
         } else if (propName.equals("MimeMappingFile")) {
            return 15;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("P3PHeaderValue")) {
            return 31;
         } else if (propName.equals("PostTimeoutSecs")) {
            return 25;
         } else if (propName.equals("ServletReloadCheckSecs")) {
            return 33;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("XPoweredByHeaderLevel")) {
            return 14;
         } else if (propName.equals("AllowAllRoles")) {
            return 11;
         } else if (propName.equals("AuthCookieEnabled")) {
            return 23;
         } else if (propName.equals("ChangeSessionIDOnAuthentication")) {
            return 35;
         } else if (propName.equals("ClientCertProxyEnabled")) {
            return 20;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("FilterDispatchedRequestsEnabled")) {
            return 12;
         } else if (propName.equals("HttpTraceSupportEnabled")) {
            return 21;
         } else if (propName.equals("JSPCompilerBackwardsCompatible")) {
            return 32;
         } else if (propName.equals("JaxRsMonitoringDefaultBehavior")) {
            return 38;
         } else if (propName.equals("OptimisticSerialization")) {
            return 16;
         } else if (propName.equals("OverloadProtectionEnabled")) {
            return 13;
         } else if (propName.equals("ReloginEnabled")) {
            return 10;
         } else if (propName.equals("RetainOriginalURL")) {
            return 17;
         } else if (propName.equals("RtexprvalueJspParamName")) {
            return 19;
         } else if (propName.equals("ServletAuthenticationFormURL")) {
            return 18;
         } else if (propName.equals("ShowArchivedRealPathEnabled")) {
            return 34;
         } else if (propName.equals("WAPEnabled")) {
            return 24;
         } else if (propName.equals("WeblogicPluginEnabled")) {
            return 22;
         } else {
            return propName.equals("WorkContextPropagationEnabled") ? 30 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getGzipCompression() != null) {
            iterators.add(new ArrayIterator(new GzipCompressionMBean[]{this.bean.getGzipCompression()}));
         }

         if (this.bean.getHttp2Config() != null) {
            iterators.add(new ArrayIterator(new Http2ConfigMBean[]{this.bean.getHttp2Config()}));
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
            childValue = this.computeChildHashValue(this.bean.getGzipCompression());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getHttp2Config());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaxPostSizeSet()) {
               buf.append("MaxPostSize");
               buf.append(String.valueOf(this.bean.getMaxPostSize()));
            }

            if (this.bean.isMaxPostTimeSecsSet()) {
               buf.append("MaxPostTimeSecs");
               buf.append(String.valueOf(this.bean.getMaxPostTimeSecs()));
            }

            if (this.bean.isMaxRequestParameterCountSet()) {
               buf.append("MaxRequestParameterCount");
               buf.append(String.valueOf(this.bean.getMaxRequestParameterCount()));
            }

            if (this.bean.isMaxRequestParamterCountSet()) {
               buf.append("MaxRequestParamterCount");
               buf.append(String.valueOf(this.bean.getMaxRequestParamterCount()));
            }

            if (this.bean.isMimeMappingFileSet()) {
               buf.append("MimeMappingFile");
               buf.append(String.valueOf(this.bean.getMimeMappingFile()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isP3PHeaderValueSet()) {
               buf.append("P3PHeaderValue");
               buf.append(String.valueOf(this.bean.getP3PHeaderValue()));
            }

            if (this.bean.isPostTimeoutSecsSet()) {
               buf.append("PostTimeoutSecs");
               buf.append(String.valueOf(this.bean.getPostTimeoutSecs()));
            }

            if (this.bean.isServletReloadCheckSecsSet()) {
               buf.append("ServletReloadCheckSecs");
               buf.append(String.valueOf(this.bean.getServletReloadCheckSecs()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isXPoweredByHeaderLevelSet()) {
               buf.append("XPoweredByHeaderLevel");
               buf.append(String.valueOf(this.bean.getXPoweredByHeaderLevel()));
            }

            if (this.bean.isAllowAllRolesSet()) {
               buf.append("AllowAllRoles");
               buf.append(String.valueOf(this.bean.isAllowAllRoles()));
            }

            if (this.bean.isAuthCookieEnabledSet()) {
               buf.append("AuthCookieEnabled");
               buf.append(String.valueOf(this.bean.isAuthCookieEnabled()));
            }

            if (this.bean.isChangeSessionIDOnAuthenticationSet()) {
               buf.append("ChangeSessionIDOnAuthentication");
               buf.append(String.valueOf(this.bean.isChangeSessionIDOnAuthentication()));
            }

            if (this.bean.isClientCertProxyEnabledSet()) {
               buf.append("ClientCertProxyEnabled");
               buf.append(String.valueOf(this.bean.isClientCertProxyEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isFilterDispatchedRequestsEnabledSet()) {
               buf.append("FilterDispatchedRequestsEnabled");
               buf.append(String.valueOf(this.bean.isFilterDispatchedRequestsEnabled()));
            }

            if (this.bean.isHttpTraceSupportEnabledSet()) {
               buf.append("HttpTraceSupportEnabled");
               buf.append(String.valueOf(this.bean.isHttpTraceSupportEnabled()));
            }

            if (this.bean.isJSPCompilerBackwardsCompatibleSet()) {
               buf.append("JSPCompilerBackwardsCompatible");
               buf.append(String.valueOf(this.bean.isJSPCompilerBackwardsCompatible()));
            }

            if (this.bean.isJaxRsMonitoringDefaultBehaviorSet()) {
               buf.append("JaxRsMonitoringDefaultBehavior");
               buf.append(String.valueOf(this.bean.isJaxRsMonitoringDefaultBehavior()));
            }

            if (this.bean.isOptimisticSerializationSet()) {
               buf.append("OptimisticSerialization");
               buf.append(String.valueOf(this.bean.isOptimisticSerialization()));
            }

            if (this.bean.isOverloadProtectionEnabledSet()) {
               buf.append("OverloadProtectionEnabled");
               buf.append(String.valueOf(this.bean.isOverloadProtectionEnabled()));
            }

            if (this.bean.isReloginEnabledSet()) {
               buf.append("ReloginEnabled");
               buf.append(String.valueOf(this.bean.isReloginEnabled()));
            }

            if (this.bean.isRetainOriginalURLSet()) {
               buf.append("RetainOriginalURL");
               buf.append(String.valueOf(this.bean.isRetainOriginalURL()));
            }

            if (this.bean.isRtexprvalueJspParamNameSet()) {
               buf.append("RtexprvalueJspParamName");
               buf.append(String.valueOf(this.bean.isRtexprvalueJspParamName()));
            }

            if (this.bean.isServletAuthenticationFormURLSet()) {
               buf.append("ServletAuthenticationFormURL");
               buf.append(String.valueOf(this.bean.isServletAuthenticationFormURL()));
            }

            if (this.bean.isShowArchivedRealPathEnabledSet()) {
               buf.append("ShowArchivedRealPathEnabled");
               buf.append(String.valueOf(this.bean.isShowArchivedRealPathEnabled()));
            }

            if (this.bean.isWAPEnabledSet()) {
               buf.append("WAPEnabled");
               buf.append(String.valueOf(this.bean.isWAPEnabled()));
            }

            if (this.bean.isWeblogicPluginEnabledSet()) {
               buf.append("WeblogicPluginEnabled");
               buf.append(String.valueOf(this.bean.isWeblogicPluginEnabled()));
            }

            if (this.bean.isWorkContextPropagationEnabledSet()) {
               buf.append("WorkContextPropagationEnabled");
               buf.append(String.valueOf(this.bean.isWorkContextPropagationEnabled()));
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
            WebAppContainerMBeanImpl otherTyped = (WebAppContainerMBeanImpl)other;
            this.computeSubDiff("GzipCompression", this.bean.getGzipCompression(), otherTyped.getGzipCompression());
            this.computeSubDiff("Http2Config", this.bean.getHttp2Config(), otherTyped.getHttp2Config());
            this.computeDiff("MaxPostSize", this.bean.getMaxPostSize(), otherTyped.getMaxPostSize(), true);
            this.computeDiff("MaxPostTimeSecs", this.bean.getMaxPostTimeSecs(), otherTyped.getMaxPostTimeSecs(), true);
            this.computeDiff("MaxRequestParameterCount", this.bean.getMaxRequestParameterCount(), otherTyped.getMaxRequestParameterCount(), true);
            this.computeDiff("MaxRequestParamterCount", this.bean.getMaxRequestParamterCount(), otherTyped.getMaxRequestParamterCount(), true);
            this.computeDiff("MimeMappingFile", this.bean.getMimeMappingFile(), otherTyped.getMimeMappingFile(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("P3PHeaderValue", this.bean.getP3PHeaderValue(), otherTyped.getP3PHeaderValue(), false);
            this.computeDiff("PostTimeoutSecs", this.bean.getPostTimeoutSecs(), otherTyped.getPostTimeoutSecs(), true);
            this.computeDiff("ServletReloadCheckSecs", this.bean.getServletReloadCheckSecs(), otherTyped.getServletReloadCheckSecs(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("XPoweredByHeaderLevel", this.bean.getXPoweredByHeaderLevel(), otherTyped.getXPoweredByHeaderLevel(), true);
            this.computeDiff("AllowAllRoles", this.bean.isAllowAllRoles(), otherTyped.isAllowAllRoles(), false);
            this.computeDiff("AuthCookieEnabled", this.bean.isAuthCookieEnabled(), otherTyped.isAuthCookieEnabled(), true);
            this.computeDiff("ChangeSessionIDOnAuthentication", this.bean.isChangeSessionIDOnAuthentication(), otherTyped.isChangeSessionIDOnAuthentication(), false);
            this.computeDiff("ClientCertProxyEnabled", this.bean.isClientCertProxyEnabled(), otherTyped.isClientCertProxyEnabled(), false);
            this.computeDiff("FilterDispatchedRequestsEnabled", this.bean.isFilterDispatchedRequestsEnabled(), otherTyped.isFilterDispatchedRequestsEnabled(), false);
            this.computeDiff("HttpTraceSupportEnabled", this.bean.isHttpTraceSupportEnabled(), otherTyped.isHttpTraceSupportEnabled(), true);
            this.computeDiff("JSPCompilerBackwardsCompatible", this.bean.isJSPCompilerBackwardsCompatible(), otherTyped.isJSPCompilerBackwardsCompatible(), false);
            this.computeDiff("JaxRsMonitoringDefaultBehavior", this.bean.isJaxRsMonitoringDefaultBehavior(), otherTyped.isJaxRsMonitoringDefaultBehavior(), true);
            this.computeDiff("OptimisticSerialization", this.bean.isOptimisticSerialization(), otherTyped.isOptimisticSerialization(), false);
            this.computeDiff("OverloadProtectionEnabled", this.bean.isOverloadProtectionEnabled(), otherTyped.isOverloadProtectionEnabled(), true);
            this.computeDiff("ReloginEnabled", this.bean.isReloginEnabled(), otherTyped.isReloginEnabled(), false);
            this.computeDiff("RetainOriginalURL", this.bean.isRetainOriginalURL(), otherTyped.isRetainOriginalURL(), false);
            this.computeDiff("RtexprvalueJspParamName", this.bean.isRtexprvalueJspParamName(), otherTyped.isRtexprvalueJspParamName(), false);
            this.computeDiff("ServletAuthenticationFormURL", this.bean.isServletAuthenticationFormURL(), otherTyped.isServletAuthenticationFormURL(), false);
            this.computeDiff("ShowArchivedRealPathEnabled", this.bean.isShowArchivedRealPathEnabled(), otherTyped.isShowArchivedRealPathEnabled(), false);
            this.computeDiff("WAPEnabled", this.bean.isWAPEnabled(), otherTyped.isWAPEnabled(), true);
            this.computeDiff("WeblogicPluginEnabled", this.bean.isWeblogicPluginEnabled(), otherTyped.isWeblogicPluginEnabled(), true);
            this.computeDiff("WorkContextPropagationEnabled", this.bean.isWorkContextPropagationEnabled(), otherTyped.isWorkContextPropagationEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebAppContainerMBeanImpl original = (WebAppContainerMBeanImpl)event.getSourceBean();
            WebAppContainerMBeanImpl proposed = (WebAppContainerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("GzipCompression")) {
                  if (type == 2) {
                     original.setGzipCompression((GzipCompressionMBean)this.createCopy((AbstractDescriptorBean)proposed.getGzipCompression()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("GzipCompression", original.getGzipCompression());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("Http2Config")) {
                  if (type == 2) {
                     original.setHttp2Config((Http2ConfigMBean)this.createCopy((AbstractDescriptorBean)proposed.getHttp2Config()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Http2Config", original.getHttp2Config());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("MaxPostSize")) {
                  original.setMaxPostSize(proposed.getMaxPostSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("MaxPostTimeSecs")) {
                  original.setMaxPostTimeSecs(proposed.getMaxPostTimeSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("MaxRequestParameterCount")) {
                  original.setMaxRequestParameterCount(proposed.getMaxRequestParameterCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("MaxRequestParamterCount")) {
                  original.setMaxRequestParamterCount(proposed.getMaxRequestParamterCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("MimeMappingFile")) {
                  original.setMimeMappingFile(proposed.getMimeMappingFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("P3PHeaderValue")) {
                  original.setP3PHeaderValue(proposed.getP3PHeaderValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("PostTimeoutSecs")) {
                  original.setPostTimeoutSecs(proposed.getPostTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("ServletReloadCheckSecs")) {
                  original.setServletReloadCheckSecs(proposed.getServletReloadCheckSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
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
               } else if (prop.equals("XPoweredByHeaderLevel")) {
                  original.setXPoweredByHeaderLevel(proposed.getXPoweredByHeaderLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("AllowAllRoles")) {
                  original.setAllowAllRoles(proposed.isAllowAllRoles());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("AuthCookieEnabled")) {
                  original.setAuthCookieEnabled(proposed.isAuthCookieEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ChangeSessionIDOnAuthentication")) {
                  original.setChangeSessionIDOnAuthentication(proposed.isChangeSessionIDOnAuthentication());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("ClientCertProxyEnabled")) {
                  original.setClientCertProxyEnabled(proposed.isClientCertProxyEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("FilterDispatchedRequestsEnabled")) {
                     original.setFilterDispatchedRequestsEnabled(proposed.isFilterDispatchedRequestsEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("HttpTraceSupportEnabled")) {
                     original.setHttpTraceSupportEnabled(proposed.isHttpTraceSupportEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("JSPCompilerBackwardsCompatible")) {
                     original.setJSPCompilerBackwardsCompatible(proposed.isJSPCompilerBackwardsCompatible());
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
                  } else if (prop.equals("JaxRsMonitoringDefaultBehavior")) {
                     original.setJaxRsMonitoringDefaultBehavior(proposed.isJaxRsMonitoringDefaultBehavior());
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
                  } else if (prop.equals("OptimisticSerialization")) {
                     original.setOptimisticSerialization(proposed.isOptimisticSerialization());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("OverloadProtectionEnabled")) {
                     original.setOverloadProtectionEnabled(proposed.isOverloadProtectionEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("ReloginEnabled")) {
                     original.setReloginEnabled(proposed.isReloginEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("RetainOriginalURL")) {
                     original.setRetainOriginalURL(proposed.isRetainOriginalURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("RtexprvalueJspParamName")) {
                     original.setRtexprvalueJspParamName(proposed.isRtexprvalueJspParamName());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("ServletAuthenticationFormURL")) {
                     original.setServletAuthenticationFormURL(proposed.isServletAuthenticationFormURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("ShowArchivedRealPathEnabled")) {
                     original.setShowArchivedRealPathEnabled(proposed.isShowArchivedRealPathEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  } else if (prop.equals("WAPEnabled")) {
                     original.setWAPEnabled(proposed.isWAPEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("WeblogicPluginEnabled")) {
                     original.setWeblogicPluginEnabled(proposed.isWeblogicPluginEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("WorkContextPropagationEnabled")) {
                     original.setWorkContextPropagationEnabled(proposed.isWorkContextPropagationEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  } else {
                     super.applyPropertyUpdate(event, update);
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
            WebAppContainerMBeanImpl copy = (WebAppContainerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("GzipCompression")) && this.bean.isGzipCompressionSet() && !copy._isSet(36)) {
               Object o = this.bean.getGzipCompression();
               copy.setGzipCompression((GzipCompressionMBean)null);
               copy.setGzipCompression(o == null ? null : (GzipCompressionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Http2Config")) && this.bean.isHttp2ConfigSet() && !copy._isSet(37)) {
               Object o = this.bean.getHttp2Config();
               copy.setHttp2Config((Http2ConfigMBean)null);
               copy.setHttp2Config(o == null ? null : (Http2ConfigMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MaxPostSize")) && this.bean.isMaxPostSizeSet()) {
               copy.setMaxPostSize(this.bean.getMaxPostSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxPostTimeSecs")) && this.bean.isMaxPostTimeSecsSet()) {
               copy.setMaxPostTimeSecs(this.bean.getMaxPostTimeSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRequestParameterCount")) && this.bean.isMaxRequestParameterCountSet()) {
               copy.setMaxRequestParameterCount(this.bean.getMaxRequestParameterCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRequestParamterCount")) && this.bean.isMaxRequestParamterCountSet()) {
               copy.setMaxRequestParamterCount(this.bean.getMaxRequestParamterCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MimeMappingFile")) && this.bean.isMimeMappingFileSet()) {
               copy.setMimeMappingFile(this.bean.getMimeMappingFile());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("P3PHeaderValue")) && this.bean.isP3PHeaderValueSet()) {
               copy.setP3PHeaderValue(this.bean.getP3PHeaderValue());
            }

            if ((excludeProps == null || !excludeProps.contains("PostTimeoutSecs")) && this.bean.isPostTimeoutSecsSet()) {
               copy.setPostTimeoutSecs(this.bean.getPostTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletReloadCheckSecs")) && this.bean.isServletReloadCheckSecsSet()) {
               copy.setServletReloadCheckSecs(this.bean.getServletReloadCheckSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("XPoweredByHeaderLevel")) && this.bean.isXPoweredByHeaderLevelSet()) {
               copy.setXPoweredByHeaderLevel(this.bean.getXPoweredByHeaderLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowAllRoles")) && this.bean.isAllowAllRolesSet()) {
               copy.setAllowAllRoles(this.bean.isAllowAllRoles());
            }

            if ((excludeProps == null || !excludeProps.contains("AuthCookieEnabled")) && this.bean.isAuthCookieEnabledSet()) {
               copy.setAuthCookieEnabled(this.bean.isAuthCookieEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ChangeSessionIDOnAuthentication")) && this.bean.isChangeSessionIDOnAuthenticationSet()) {
               copy.setChangeSessionIDOnAuthentication(this.bean.isChangeSessionIDOnAuthentication());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertProxyEnabled")) && this.bean.isClientCertProxyEnabledSet()) {
               copy.setClientCertProxyEnabled(this.bean.isClientCertProxyEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("FilterDispatchedRequestsEnabled")) && this.bean.isFilterDispatchedRequestsEnabledSet()) {
               copy.setFilterDispatchedRequestsEnabled(this.bean.isFilterDispatchedRequestsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpTraceSupportEnabled")) && this.bean.isHttpTraceSupportEnabledSet()) {
               copy.setHttpTraceSupportEnabled(this.bean.isHttpTraceSupportEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JSPCompilerBackwardsCompatible")) && this.bean.isJSPCompilerBackwardsCompatibleSet()) {
               copy.setJSPCompilerBackwardsCompatible(this.bean.isJSPCompilerBackwardsCompatible());
            }

            if ((excludeProps == null || !excludeProps.contains("JaxRsMonitoringDefaultBehavior")) && this.bean.isJaxRsMonitoringDefaultBehaviorSet()) {
               copy.setJaxRsMonitoringDefaultBehavior(this.bean.isJaxRsMonitoringDefaultBehavior());
            }

            if ((excludeProps == null || !excludeProps.contains("OptimisticSerialization")) && this.bean.isOptimisticSerializationSet()) {
               copy.setOptimisticSerialization(this.bean.isOptimisticSerialization());
            }

            if ((excludeProps == null || !excludeProps.contains("OverloadProtectionEnabled")) && this.bean.isOverloadProtectionEnabledSet()) {
               copy.setOverloadProtectionEnabled(this.bean.isOverloadProtectionEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ReloginEnabled")) && this.bean.isReloginEnabledSet()) {
               copy.setReloginEnabled(this.bean.isReloginEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RetainOriginalURL")) && this.bean.isRetainOriginalURLSet()) {
               copy.setRetainOriginalURL(this.bean.isRetainOriginalURL());
            }

            if ((excludeProps == null || !excludeProps.contains("RtexprvalueJspParamName")) && this.bean.isRtexprvalueJspParamNameSet()) {
               copy.setRtexprvalueJspParamName(this.bean.isRtexprvalueJspParamName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletAuthenticationFormURL")) && this.bean.isServletAuthenticationFormURLSet()) {
               copy.setServletAuthenticationFormURL(this.bean.isServletAuthenticationFormURL());
            }

            if ((excludeProps == null || !excludeProps.contains("ShowArchivedRealPathEnabled")) && this.bean.isShowArchivedRealPathEnabledSet()) {
               copy.setShowArchivedRealPathEnabled(this.bean.isShowArchivedRealPathEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WAPEnabled")) && this.bean.isWAPEnabledSet()) {
               copy.setWAPEnabled(this.bean.isWAPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicPluginEnabled")) && this.bean.isWeblogicPluginEnabledSet()) {
               copy.setWeblogicPluginEnabled(this.bean.isWeblogicPluginEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkContextPropagationEnabled")) && this.bean.isWorkContextPropagationEnabledSet()) {
               copy.setWorkContextPropagationEnabled(this.bean.isWorkContextPropagationEnabled());
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
         this.inferSubTree(this.bean.getGzipCompression(), clazz, annotation);
         this.inferSubTree(this.bean.getHttp2Config(), clazz, annotation);
      }
   }
}
