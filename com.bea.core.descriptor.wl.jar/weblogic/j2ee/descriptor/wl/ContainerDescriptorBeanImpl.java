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
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.EmptyBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ContainerDescriptorBeanImpl extends AbstractDescriptorBean implements ContainerDescriptorBean, Serializable {
   private boolean _AccessLoggingDisabled;
   private boolean _AllowAllRoles;
   private EmptyBean _CheckAuthOnForward;
   private ClassLoadingBean _ClassLoading;
   private boolean _ClientCertProxyEnabled;
   private boolean _ContainerInitializerEnabled;
   private String _DefaultMimeType;
   private boolean _DisableImplicitServletMappings;
   private boolean _FailDeployOnFilterInitError;
   private boolean _FilterDispatchedRequestsEnabled;
   private GzipCompressionBean _GzipCompression;
   private String _Id;
   private boolean _IndexDirectoryEnabled;
   private String _IndexDirectorySortBy;
   private String _LangtagRevision;
   private long _MinimumNativeFileSize;
   private boolean _NativeIOEnabled;
   private boolean _OptimisticSerialization;
   private PreferApplicationPackagesBean _PreferApplicationPackages;
   private PreferApplicationResourcesBean _PreferApplicationResources;
   private boolean _PreferForwardQueryString;
   private boolean _PreferWebInfClasses;
   private String _RedirectContent;
   private String _RedirectContentType;
   private boolean _RedirectWithAbsoluteUrl;
   private String _RefererValidation;
   private boolean _ReloginEnabled;
   private boolean _RequireAdminTraffic;
   private int _ResourceReloadCheckSecs;
   private boolean _RetainOriginalURL;
   private boolean _SaveSessionsEnabled;
   private boolean _SendPermanentRedirects;
   private int _ServletReloadCheckSecs;
   private boolean _SessionMonitoringEnabled;
   private boolean _ShowArchivedRealPathEnabled;
   private int _SingleThreadedServletPoolSize;
   private String _TempDir;
   private static SchemaHelper2 _schemaHelper;

   public ContainerDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public ContainerDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ContainerDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRefererValidation() {
      return this._RefererValidation;
   }

   public boolean isRefererValidationInherited() {
      return false;
   }

   public boolean isRefererValidationSet() {
      return this._isSet(0);
   }

   public void setRefererValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NONE", "LENIENT", "STRICT"};
      param0 = LegalChecks.checkInEnum("RefererValidation", param0, _set);
      String _oldVal = this._RefererValidation;
      this._RefererValidation = param0;
      this._postSet(0, _oldVal, param0);
   }

   public EmptyBean getCheckAuthOnForward() {
      return this._CheckAuthOnForward;
   }

   public boolean isCheckAuthOnForwardInherited() {
      return false;
   }

   public boolean isCheckAuthOnForwardSet() {
      return this._isSet(1);
   }

   public void setCheckAuthOnForward(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCheckAuthOnForward() != null && param0 != this.getCheckAuthOnForward()) {
         throw new BeanAlreadyExistsException(this.getCheckAuthOnForward() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._CheckAuthOnForward;
         this._CheckAuthOnForward = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public EmptyBean createCheckAuthOnForward() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setCheckAuthOnForward(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCheckAuthOnForward(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CheckAuthOnForward;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCheckAuthOnForward((EmptyBean)null);
               this._unSet(1);
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

   public boolean isFilterDispatchedRequestsEnabled() {
      return this._FilterDispatchedRequestsEnabled;
   }

   public boolean isFilterDispatchedRequestsEnabledInherited() {
      return false;
   }

   public boolean isFilterDispatchedRequestsEnabledSet() {
      return this._isSet(2);
   }

   public void setFilterDispatchedRequestsEnabled(boolean param0) {
      boolean _oldVal = this._FilterDispatchedRequestsEnabled;
      this._FilterDispatchedRequestsEnabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getRedirectContentType() {
      return this._RedirectContentType;
   }

   public boolean isRedirectContentTypeInherited() {
      return false;
   }

   public boolean isRedirectContentTypeSet() {
      return this._isSet(3);
   }

   public void setRedirectContentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RedirectContentType;
      this._RedirectContentType = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getRedirectContent() {
      return this._RedirectContent;
   }

   public boolean isRedirectContentInherited() {
      return false;
   }

   public boolean isRedirectContentSet() {
      return this._isSet(4);
   }

   public void setRedirectContent(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RedirectContent;
      this._RedirectContent = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isRedirectWithAbsoluteUrl() {
      return this._RedirectWithAbsoluteUrl;
   }

   public boolean isRedirectWithAbsoluteUrlInherited() {
      return false;
   }

   public boolean isRedirectWithAbsoluteUrlSet() {
      return this._isSet(5);
   }

   public void setRedirectWithAbsoluteUrl(boolean param0) {
      boolean _oldVal = this._RedirectWithAbsoluteUrl;
      this._RedirectWithAbsoluteUrl = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isIndexDirectoryEnabled() {
      return this._IndexDirectoryEnabled;
   }

   public boolean isIndexDirectoryEnabledInherited() {
      return false;
   }

   public boolean isIndexDirectoryEnabledSet() {
      return this._isSet(6);
   }

   public void setIndexDirectoryEnabled(boolean param0) {
      boolean _oldVal = this._IndexDirectoryEnabled;
      this._IndexDirectoryEnabled = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getIndexDirectorySortBy() {
      return this._IndexDirectorySortBy;
   }

   public boolean isIndexDirectorySortByInherited() {
      return false;
   }

   public boolean isIndexDirectorySortBySet() {
      return this._isSet(7);
   }

   public void setIndexDirectorySortBy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NAME", "LAST_MODIFIED", "SIZE"};
      param0 = LegalChecks.checkInEnum("IndexDirectorySortBy", param0, _set);
      String _oldVal = this._IndexDirectorySortBy;
      this._IndexDirectorySortBy = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getServletReloadCheckSecs() {
      if (!this._isSet(8)) {
         return this._isProductionModeEnabled() ? -1 : 1;
      } else {
         return this._ServletReloadCheckSecs;
      }
   }

   public boolean isServletReloadCheckSecsInherited() {
      return false;
   }

   public boolean isServletReloadCheckSecsSet() {
      return this._isSet(8);
   }

   public void setServletReloadCheckSecs(int param0) {
      int _oldVal = this._ServletReloadCheckSecs;
      this._ServletReloadCheckSecs = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isSendPermanentRedirects() {
      return this._SendPermanentRedirects;
   }

   public boolean isSendPermanentRedirectsInherited() {
      return false;
   }

   public boolean isSendPermanentRedirectsSet() {
      return this._isSet(9);
   }

   public void setSendPermanentRedirects(boolean param0) {
      boolean _oldVal = this._SendPermanentRedirects;
      this._SendPermanentRedirects = param0;
      this._postSet(9, _oldVal, param0);
   }

   public int getResourceReloadCheckSecs() {
      if (!this._isSet(10)) {
         return this._isProductionModeEnabled() ? -1 : 1;
      } else {
         return this._ResourceReloadCheckSecs;
      }
   }

   public boolean isResourceReloadCheckSecsInherited() {
      return false;
   }

   public boolean isResourceReloadCheckSecsSet() {
      return this._isSet(10);
   }

   public void setResourceReloadCheckSecs(int param0) {
      int _oldVal = this._ResourceReloadCheckSecs;
      this._ResourceReloadCheckSecs = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getSingleThreadedServletPoolSize() {
      return this._SingleThreadedServletPoolSize;
   }

   public boolean isSingleThreadedServletPoolSizeInherited() {
      return false;
   }

   public boolean isSingleThreadedServletPoolSizeSet() {
      return this._isSet(11);
   }

   public void setSingleThreadedServletPoolSize(int param0) {
      int _oldVal = this._SingleThreadedServletPoolSize;
      this._SingleThreadedServletPoolSize = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isSessionMonitoringEnabled() {
      return this._SessionMonitoringEnabled;
   }

   public boolean isSessionMonitoringEnabledInherited() {
      return false;
   }

   public boolean isSessionMonitoringEnabledSet() {
      return this._isSet(12);
   }

   public void setSessionMonitoringEnabled(boolean param0) {
      boolean _oldVal = this._SessionMonitoringEnabled;
      this._SessionMonitoringEnabled = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isSaveSessionsEnabled() {
      return this._SaveSessionsEnabled;
   }

   public boolean isSaveSessionsEnabledInherited() {
      return false;
   }

   public boolean isSaveSessionsEnabledSet() {
      return this._isSet(13);
   }

   public void setSaveSessionsEnabled(boolean param0) {
      boolean _oldVal = this._SaveSessionsEnabled;
      this._SaveSessionsEnabled = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isPreferWebInfClasses() {
      return this._PreferWebInfClasses;
   }

   public boolean isPreferWebInfClassesInherited() {
      return false;
   }

   public boolean isPreferWebInfClassesSet() {
      return this._isSet(14);
   }

   public void setPreferWebInfClasses(boolean param0) {
      boolean _oldVal = this._PreferWebInfClasses;
      this._PreferWebInfClasses = param0;
      this._postSet(14, _oldVal, param0);
   }

   public PreferApplicationPackagesBean getPreferApplicationPackages() {
      return this._PreferApplicationPackages;
   }

   public boolean isPreferApplicationPackagesInherited() {
      return false;
   }

   public boolean isPreferApplicationPackagesSet() {
      return this._isSet(15) || this._isAnythingSet((AbstractDescriptorBean)this.getPreferApplicationPackages());
   }

   public void setPreferApplicationPackages(PreferApplicationPackagesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 15)) {
         this._postCreate(_child);
      }

      PreferApplicationPackagesBean _oldVal = this._PreferApplicationPackages;
      this._PreferApplicationPackages = param0;
      this._postSet(15, _oldVal, param0);
   }

   public PreferApplicationResourcesBean getPreferApplicationResources() {
      return this._PreferApplicationResources;
   }

   public boolean isPreferApplicationResourcesInherited() {
      return false;
   }

   public boolean isPreferApplicationResourcesSet() {
      return this._isSet(16) || this._isAnythingSet((AbstractDescriptorBean)this.getPreferApplicationResources());
   }

   public void setPreferApplicationResources(PreferApplicationResourcesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 16)) {
         this._postCreate(_child);
      }

      PreferApplicationResourcesBean _oldVal = this._PreferApplicationResources;
      this._PreferApplicationResources = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getDefaultMimeType() {
      return this._DefaultMimeType;
   }

   public boolean isDefaultMimeTypeInherited() {
      return false;
   }

   public boolean isDefaultMimeTypeSet() {
      return this._isSet(17);
   }

   public void setDefaultMimeType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultMimeType;
      this._DefaultMimeType = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isReloginEnabled() {
      return this._ReloginEnabled;
   }

   public boolean isReloginEnabledInherited() {
      return false;
   }

   public boolean isReloginEnabledSet() {
      return this._isSet(18);
   }

   public void setReloginEnabled(boolean param0) {
      boolean _oldVal = this._ReloginEnabled;
      this._ReloginEnabled = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isAllowAllRoles() {
      return this._AllowAllRoles;
   }

   public boolean isAllowAllRolesInherited() {
      return false;
   }

   public boolean isAllowAllRolesSet() {
      return this._isSet(19);
   }

   public void setAllowAllRoles(boolean param0) {
      boolean _oldVal = this._AllowAllRoles;
      this._AllowAllRoles = param0;
      this._postSet(19, _oldVal, param0);
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

   public void setClientCertProxyEnabled(boolean param0) {
      boolean _oldVal = this._ClientCertProxyEnabled;
      this._ClientCertProxyEnabled = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isNativeIOEnabled() {
      return this._NativeIOEnabled;
   }

   public boolean isNativeIOEnabledInherited() {
      return false;
   }

   public boolean isNativeIOEnabledSet() {
      return this._isSet(21);
   }

   public void setNativeIOEnabled(boolean param0) {
      boolean _oldVal = this._NativeIOEnabled;
      this._NativeIOEnabled = param0;
      this._postSet(21, _oldVal, param0);
   }

   public long getMinimumNativeFileSize() {
      return this._MinimumNativeFileSize;
   }

   public boolean isMinimumNativeFileSizeInherited() {
      return false;
   }

   public boolean isMinimumNativeFileSizeSet() {
      return this._isSet(22);
   }

   public void setMinimumNativeFileSize(long param0) {
      long _oldVal = this._MinimumNativeFileSize;
      this._MinimumNativeFileSize = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isDisableImplicitServletMappings() {
      return this._DisableImplicitServletMappings;
   }

   public boolean isDisableImplicitServletMappingsInherited() {
      return false;
   }

   public boolean isDisableImplicitServletMappingsSet() {
      return this._isSet(23);
   }

   public void setDisableImplicitServletMappings(boolean param0) {
      boolean _oldVal = this._DisableImplicitServletMappings;
      this._DisableImplicitServletMappings = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getTempDir() {
      return this._TempDir;
   }

   public boolean isTempDirInherited() {
      return false;
   }

   public boolean isTempDirSet() {
      return this._isSet(24);
   }

   public void setTempDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TempDir;
      this._TempDir = param0;
      this._postSet(24, _oldVal, param0);
   }

   public boolean isOptimisticSerialization() {
      return this._OptimisticSerialization;
   }

   public boolean isOptimisticSerializationInherited() {
      return false;
   }

   public boolean isOptimisticSerializationSet() {
      return this._isSet(25);
   }

   public void setOptimisticSerialization(boolean param0) {
      boolean _oldVal = this._OptimisticSerialization;
      this._OptimisticSerialization = param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean isRetainOriginalURL() {
      return this._RetainOriginalURL;
   }

   public boolean isRetainOriginalURLInherited() {
      return false;
   }

   public boolean isRetainOriginalURLSet() {
      return this._isSet(26);
   }

   public void setRetainOriginalURL(boolean param0) {
      boolean _oldVal = this._RetainOriginalURL;
      this._RetainOriginalURL = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(27);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(27, _oldVal, param0);
   }

   public boolean isShowArchivedRealPathEnabled() {
      return this._ShowArchivedRealPathEnabled;
   }

   public boolean isShowArchivedRealPathEnabledInherited() {
      return false;
   }

   public boolean isShowArchivedRealPathEnabledSet() {
      return this._isSet(28);
   }

   public void setShowArchivedRealPathEnabled(boolean param0) {
      boolean _oldVal = this._ShowArchivedRealPathEnabled;
      this._ShowArchivedRealPathEnabled = param0;
      this._postSet(28, _oldVal, param0);
   }

   public boolean isRequireAdminTraffic() {
      return this._RequireAdminTraffic;
   }

   public boolean isRequireAdminTrafficInherited() {
      return false;
   }

   public boolean isRequireAdminTrafficSet() {
      return this._isSet(29);
   }

   public void setRequireAdminTraffic(boolean param0) {
      boolean _oldVal = this._RequireAdminTraffic;
      this._RequireAdminTraffic = param0;
      this._postSet(29, _oldVal, param0);
   }

   public boolean isAccessLoggingDisabled() {
      return this._AccessLoggingDisabled;
   }

   public boolean isAccessLoggingDisabledInherited() {
      return false;
   }

   public boolean isAccessLoggingDisabledSet() {
      return this._isSet(30);
   }

   public void setAccessLoggingDisabled(boolean param0) {
      boolean _oldVal = this._AccessLoggingDisabled;
      this._AccessLoggingDisabled = param0;
      this._postSet(30, _oldVal, param0);
   }

   public boolean isPreferForwardQueryString() {
      return this._PreferForwardQueryString;
   }

   public boolean isPreferForwardQueryStringInherited() {
      return false;
   }

   public boolean isPreferForwardQueryStringSet() {
      return this._isSet(31);
   }

   public void setPreferForwardQueryString(boolean param0) {
      boolean _oldVal = this._PreferForwardQueryString;
      this._PreferForwardQueryString = param0;
      this._postSet(31, _oldVal, param0);
   }

   public boolean getFailDeployOnFilterInitError() {
      return this._FailDeployOnFilterInitError;
   }

   public boolean isFailDeployOnFilterInitErrorInherited() {
      return false;
   }

   public boolean isFailDeployOnFilterInitErrorSet() {
      return this._isSet(32);
   }

   public void setFailDeployOnFilterInitError(boolean param0) {
      boolean _oldVal = this._FailDeployOnFilterInitError;
      this._FailDeployOnFilterInitError = param0;
      this._postSet(32, _oldVal, param0);
   }

   public boolean isContainerInitializerEnabled() {
      return this._ContainerInitializerEnabled;
   }

   public boolean isContainerInitializerEnabledInherited() {
      return false;
   }

   public boolean isContainerInitializerEnabledSet() {
      return this._isSet(33);
   }

   public void setContainerInitializerEnabled(boolean param0) {
      boolean _oldVal = this._ContainerInitializerEnabled;
      this._ContainerInitializerEnabled = param0;
      this._postSet(33, _oldVal, param0);
   }

   public String getLangtagRevision() {
      return !this._isSet(34) ? null : this._LangtagRevision;
   }

   public boolean isLangtagRevisionInherited() {
      return false;
   }

   public boolean isLangtagRevisionSet() {
      return this._isSet(34);
   }

   public void setLangtagRevision(String param0) {
      if (param0 == null) {
         this._unSet(34);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"3066", "5646"};
         param0 = LegalChecks.checkInEnum("LangtagRevision", param0, _set);
         String _oldVal = this._LangtagRevision;
         this._LangtagRevision = param0;
         this._postSet(34, _oldVal, param0);
      }
   }

   public GzipCompressionBean getGzipCompression() {
      return this._GzipCompression;
   }

   public boolean isGzipCompressionInherited() {
      return false;
   }

   public boolean isGzipCompressionSet() {
      return this._isSet(35);
   }

   public void setGzipCompression(GzipCompressionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getGzipCompression() != null && param0 != this.getGzipCompression()) {
         throw new BeanAlreadyExistsException(this.getGzipCompression() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 35)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         GzipCompressionBean _oldVal = this._GzipCompression;
         this._GzipCompression = param0;
         this._postSet(35, _oldVal, param0);
      }
   }

   public GzipCompressionBean createGzipCompression() {
      GzipCompressionBeanImpl _val = new GzipCompressionBeanImpl(this, -1);

      try {
         this.setGzipCompression(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyGzipCompression() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._GzipCompression;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGzipCompression((GzipCompressionBean)null);
               this._unSet(35);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ClassLoadingBean getClassLoading() {
      return this._ClassLoading;
   }

   public boolean isClassLoadingInherited() {
      return false;
   }

   public boolean isClassLoadingSet() {
      return this._isSet(36) || this._isAnythingSet((AbstractDescriptorBean)this.getClassLoading());
   }

   public void setClassLoading(ClassLoadingBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 36)) {
         this._postCreate(_child);
      }

      ClassLoadingBean _oldVal = this._ClassLoading;
      this._ClassLoading = param0;
      this._postSet(36, _oldVal, param0);
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
      return super._isAnythingSet() || this.isClassLoadingSet() || this.isPreferApplicationPackagesSet() || this.isPreferApplicationResourcesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._CheckAuthOnForward = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._ClassLoading = new ClassLoadingBeanImpl(this, 36);
               this._postCreate((AbstractDescriptorBean)this._ClassLoading);
               if (initOne) {
                  break;
               }
            case 17:
               this._DefaultMimeType = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._FailDeployOnFilterInitError = true;
               if (initOne) {
                  break;
               }
            case 35:
               this._GzipCompression = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._IndexDirectorySortBy = "NAME";
               if (initOne) {
                  break;
               }
            case 34:
               this._LangtagRevision = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._MinimumNativeFileSize = 4096L;
               if (initOne) {
                  break;
               }
            case 15:
               this._PreferApplicationPackages = new PreferApplicationPackagesBeanImpl(this, 15);
               this._postCreate((AbstractDescriptorBean)this._PreferApplicationPackages);
               if (initOne) {
                  break;
               }
            case 16:
               this._PreferApplicationResources = new PreferApplicationResourcesBeanImpl(this, 16);
               this._postCreate((AbstractDescriptorBean)this._PreferApplicationResources);
               if (initOne) {
                  break;
               }
            case 4:
               this._RedirectContent = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RedirectContentType = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._RefererValidation = "LENIENT";
               if (initOne) {
                  break;
               }
            case 10:
               this._ResourceReloadCheckSecs = 1;
               if (initOne) {
                  break;
               }
            case 8:
               this._ServletReloadCheckSecs = 1;
               if (initOne) {
                  break;
               }
            case 11:
               this._SingleThreadedServletPoolSize = 5;
               if (initOne) {
                  break;
               }
            case 24:
               this._TempDir = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._AccessLoggingDisabled = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._AllowAllRoles = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._ClientCertProxyEnabled = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._ContainerInitializerEnabled = true;
               if (initOne) {
                  break;
               }
            case 23:
               this._DisableImplicitServletMappings = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._FilterDispatchedRequestsEnabled = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._IndexDirectoryEnabled = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._NativeIOEnabled = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._OptimisticSerialization = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._PreferForwardQueryString = true;
               if (initOne) {
                  break;
               }
            case 14:
               this._PreferWebInfClasses = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._RedirectWithAbsoluteUrl = true;
               if (initOne) {
                  break;
               }
            case 18:
               this._ReloginEnabled = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._RequireAdminTraffic = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._RetainOriginalURL = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._SaveSessionsEnabled = false;
               if (initOne) {
                  break;
               }
            case 9:
               this._SendPermanentRedirects = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._SessionMonitoringEnabled = false;
               if (initOne) {
                  break;
               }
            case 28:
               this._ShowArchivedRealPathEnabled = false;
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
            case 2:
               if (s.equals("id")) {
                  return 27;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 20:
            case 30:
            default:
               break;
            case 8:
               if (s.equals("temp-dir")) {
                  return 24;
               }
               break;
            case 13:
               if (s.equals("class-loading")) {
                  return 36;
               }
               break;
            case 15:
               if (s.equals("allow-all-roles")) {
                  return 19;
               }

               if (s.equals("relogin-enabled")) {
                  return 18;
               }
               break;
            case 16:
               if (s.equals("gzip-compression")) {
                  return 35;
               }

               if (s.equals("langtag-revision")) {
                  return 34;
               }

               if (s.equals("redirect-content")) {
                  return 4;
               }
               break;
            case 17:
               if (s.equals("default-mime-type")) {
                  return 17;
               }

               if (s.equals("native-io-enabled")) {
                  return 21;
               }
               break;
            case 18:
               if (s.equals("referer-validation")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("retain-original-url")) {
                  return 26;
               }
               break;
            case 21:
               if (s.equals("check-auth-on-forward")) {
                  return 1;
               }

               if (s.equals("redirect-content-type")) {
                  return 3;
               }

               if (s.equals("require-admin-traffic")) {
                  return 29;
               }

               if (s.equals("save-sessions-enabled")) {
                  return 13;
               }
               break;
            case 22:
               if (s.equals("prefer-web-inf-classes")) {
                  return 14;
               }
               break;
            case 23:
               if (s.equals("index-directory-sort-by")) {
                  return 7;
               }

               if (s.equals("access-logging-disabled")) {
                  return 30;
               }

               if (s.equals("index-directory-enabled")) {
                  return 6;
               }
               break;
            case 24:
               if (s.equals("minimum-native-file-size")) {
                  return 22;
               }

               if (s.equals("optimistic-serialization")) {
                  return 25;
               }

               if (s.equals("send-permanent-redirects")) {
                  return 9;
               }
               break;
            case 25:
               if (s.equals("servlet-reload-check-secs")) {
                  return 8;
               }

               if (s.equals("client-cert-proxy-enabled")) {
                  return 20;
               }
               break;
            case 26:
               if (s.equals("resource-reload-check-secs")) {
                  return 10;
               }

               if (s.equals("redirect-with-absolute-url")) {
                  return 5;
               }

               if (s.equals("session-monitoring-enabled")) {
                  return 12;
               }
               break;
            case 27:
               if (s.equals("prefer-application-packages")) {
                  return 15;
               }

               if (s.equals("prefer-forward-query-string")) {
                  return 31;
               }
               break;
            case 28:
               if (s.equals("prefer-application-resources")) {
                  return 16;
               }
               break;
            case 29:
               if (s.equals("container-initializer-enabled")) {
                  return 33;
               }
               break;
            case 31:
               if (s.equals("show-archived-real-path-enabled")) {
                  return 28;
               }
               break;
            case 32:
               if (s.equals("fail-deploy-on-filter-init-error")) {
                  return 32;
               }
               break;
            case 33:
               if (s.equals("single-threaded-servlet-pool-size")) {
                  return 11;
               }

               if (s.equals("disable-implicit-servlet-mappings")) {
                  return 23;
               }
               break;
            case 34:
               if (s.equals("filter-dispatched-requests-enabled")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new EmptyBeanImpl.SchemaHelper2();
            case 15:
               return new PreferApplicationPackagesBeanImpl.SchemaHelper2();
            case 16:
               return new PreferApplicationResourcesBeanImpl.SchemaHelper2();
            case 35:
               return new GzipCompressionBeanImpl.SchemaHelper2();
            case 36:
               return new ClassLoadingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "referer-validation";
            case 1:
               return "check-auth-on-forward";
            case 2:
               return "filter-dispatched-requests-enabled";
            case 3:
               return "redirect-content-type";
            case 4:
               return "redirect-content";
            case 5:
               return "redirect-with-absolute-url";
            case 6:
               return "index-directory-enabled";
            case 7:
               return "index-directory-sort-by";
            case 8:
               return "servlet-reload-check-secs";
            case 9:
               return "send-permanent-redirects";
            case 10:
               return "resource-reload-check-secs";
            case 11:
               return "single-threaded-servlet-pool-size";
            case 12:
               return "session-monitoring-enabled";
            case 13:
               return "save-sessions-enabled";
            case 14:
               return "prefer-web-inf-classes";
            case 15:
               return "prefer-application-packages";
            case 16:
               return "prefer-application-resources";
            case 17:
               return "default-mime-type";
            case 18:
               return "relogin-enabled";
            case 19:
               return "allow-all-roles";
            case 20:
               return "client-cert-proxy-enabled";
            case 21:
               return "native-io-enabled";
            case 22:
               return "minimum-native-file-size";
            case 23:
               return "disable-implicit-servlet-mappings";
            case 24:
               return "temp-dir";
            case 25:
               return "optimistic-serialization";
            case 26:
               return "retain-original-url";
            case 27:
               return "id";
            case 28:
               return "show-archived-real-path-enabled";
            case 29:
               return "require-admin-traffic";
            case 30:
               return "access-logging-disabled";
            case 31:
               return "prefer-forward-query-string";
            case 32:
               return "fail-deploy-on-filter-init-error";
            case 33:
               return "container-initializer-enabled";
            case 34:
               return "langtag-revision";
            case 35:
               return "gzip-compression";
            case 36:
               return "class-loading";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 35:
               return true;
            case 36:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               return super.isConfigurable(propIndex);
            case 10:
               return true;
            case 12:
               return true;
            case 22:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ContainerDescriptorBeanImpl bean;

      protected Helper(ContainerDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RefererValidation";
            case 1:
               return "CheckAuthOnForward";
            case 2:
               return "FilterDispatchedRequestsEnabled";
            case 3:
               return "RedirectContentType";
            case 4:
               return "RedirectContent";
            case 5:
               return "RedirectWithAbsoluteUrl";
            case 6:
               return "IndexDirectoryEnabled";
            case 7:
               return "IndexDirectorySortBy";
            case 8:
               return "ServletReloadCheckSecs";
            case 9:
               return "SendPermanentRedirects";
            case 10:
               return "ResourceReloadCheckSecs";
            case 11:
               return "SingleThreadedServletPoolSize";
            case 12:
               return "SessionMonitoringEnabled";
            case 13:
               return "SaveSessionsEnabled";
            case 14:
               return "PreferWebInfClasses";
            case 15:
               return "PreferApplicationPackages";
            case 16:
               return "PreferApplicationResources";
            case 17:
               return "DefaultMimeType";
            case 18:
               return "ReloginEnabled";
            case 19:
               return "AllowAllRoles";
            case 20:
               return "ClientCertProxyEnabled";
            case 21:
               return "NativeIOEnabled";
            case 22:
               return "MinimumNativeFileSize";
            case 23:
               return "DisableImplicitServletMappings";
            case 24:
               return "TempDir";
            case 25:
               return "OptimisticSerialization";
            case 26:
               return "RetainOriginalURL";
            case 27:
               return "Id";
            case 28:
               return "ShowArchivedRealPathEnabled";
            case 29:
               return "RequireAdminTraffic";
            case 30:
               return "AccessLoggingDisabled";
            case 31:
               return "PreferForwardQueryString";
            case 32:
               return "FailDeployOnFilterInitError";
            case 33:
               return "ContainerInitializerEnabled";
            case 34:
               return "LangtagRevision";
            case 35:
               return "GzipCompression";
            case 36:
               return "ClassLoading";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CheckAuthOnForward")) {
            return 1;
         } else if (propName.equals("ClassLoading")) {
            return 36;
         } else if (propName.equals("DefaultMimeType")) {
            return 17;
         } else if (propName.equals("FailDeployOnFilterInitError")) {
            return 32;
         } else if (propName.equals("GzipCompression")) {
            return 35;
         } else if (propName.equals("Id")) {
            return 27;
         } else if (propName.equals("IndexDirectorySortBy")) {
            return 7;
         } else if (propName.equals("LangtagRevision")) {
            return 34;
         } else if (propName.equals("MinimumNativeFileSize")) {
            return 22;
         } else if (propName.equals("PreferApplicationPackages")) {
            return 15;
         } else if (propName.equals("PreferApplicationResources")) {
            return 16;
         } else if (propName.equals("RedirectContent")) {
            return 4;
         } else if (propName.equals("RedirectContentType")) {
            return 3;
         } else if (propName.equals("RefererValidation")) {
            return 0;
         } else if (propName.equals("ResourceReloadCheckSecs")) {
            return 10;
         } else if (propName.equals("ServletReloadCheckSecs")) {
            return 8;
         } else if (propName.equals("SingleThreadedServletPoolSize")) {
            return 11;
         } else if (propName.equals("TempDir")) {
            return 24;
         } else if (propName.equals("AccessLoggingDisabled")) {
            return 30;
         } else if (propName.equals("AllowAllRoles")) {
            return 19;
         } else if (propName.equals("ClientCertProxyEnabled")) {
            return 20;
         } else if (propName.equals("ContainerInitializerEnabled")) {
            return 33;
         } else if (propName.equals("DisableImplicitServletMappings")) {
            return 23;
         } else if (propName.equals("FilterDispatchedRequestsEnabled")) {
            return 2;
         } else if (propName.equals("IndexDirectoryEnabled")) {
            return 6;
         } else if (propName.equals("NativeIOEnabled")) {
            return 21;
         } else if (propName.equals("OptimisticSerialization")) {
            return 25;
         } else if (propName.equals("PreferForwardQueryString")) {
            return 31;
         } else if (propName.equals("PreferWebInfClasses")) {
            return 14;
         } else if (propName.equals("RedirectWithAbsoluteUrl")) {
            return 5;
         } else if (propName.equals("ReloginEnabled")) {
            return 18;
         } else if (propName.equals("RequireAdminTraffic")) {
            return 29;
         } else if (propName.equals("RetainOriginalURL")) {
            return 26;
         } else if (propName.equals("SaveSessionsEnabled")) {
            return 13;
         } else if (propName.equals("SendPermanentRedirects")) {
            return 9;
         } else if (propName.equals("SessionMonitoringEnabled")) {
            return 12;
         } else {
            return propName.equals("ShowArchivedRealPathEnabled") ? 28 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCheckAuthOnForward() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getCheckAuthOnForward()}));
         }

         if (this.bean.getClassLoading() != null) {
            iterators.add(new ArrayIterator(new ClassLoadingBean[]{this.bean.getClassLoading()}));
         }

         if (this.bean.getGzipCompression() != null) {
            iterators.add(new ArrayIterator(new GzipCompressionBean[]{this.bean.getGzipCompression()}));
         }

         if (this.bean.getPreferApplicationPackages() != null) {
            iterators.add(new ArrayIterator(new PreferApplicationPackagesBean[]{this.bean.getPreferApplicationPackages()}));
         }

         if (this.bean.getPreferApplicationResources() != null) {
            iterators.add(new ArrayIterator(new PreferApplicationResourcesBean[]{this.bean.getPreferApplicationResources()}));
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
            childValue = this.computeChildHashValue(this.bean.getCheckAuthOnForward());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getClassLoading());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDefaultMimeTypeSet()) {
               buf.append("DefaultMimeType");
               buf.append(String.valueOf(this.bean.getDefaultMimeType()));
            }

            if (this.bean.isFailDeployOnFilterInitErrorSet()) {
               buf.append("FailDeployOnFilterInitError");
               buf.append(String.valueOf(this.bean.getFailDeployOnFilterInitError()));
            }

            childValue = this.computeChildHashValue(this.bean.getGzipCompression());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIndexDirectorySortBySet()) {
               buf.append("IndexDirectorySortBy");
               buf.append(String.valueOf(this.bean.getIndexDirectorySortBy()));
            }

            if (this.bean.isLangtagRevisionSet()) {
               buf.append("LangtagRevision");
               buf.append(String.valueOf(this.bean.getLangtagRevision()));
            }

            if (this.bean.isMinimumNativeFileSizeSet()) {
               buf.append("MinimumNativeFileSize");
               buf.append(String.valueOf(this.bean.getMinimumNativeFileSize()));
            }

            childValue = this.computeChildHashValue(this.bean.getPreferApplicationPackages());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPreferApplicationResources());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRedirectContentSet()) {
               buf.append("RedirectContent");
               buf.append(String.valueOf(this.bean.getRedirectContent()));
            }

            if (this.bean.isRedirectContentTypeSet()) {
               buf.append("RedirectContentType");
               buf.append(String.valueOf(this.bean.getRedirectContentType()));
            }

            if (this.bean.isRefererValidationSet()) {
               buf.append("RefererValidation");
               buf.append(String.valueOf(this.bean.getRefererValidation()));
            }

            if (this.bean.isResourceReloadCheckSecsSet()) {
               buf.append("ResourceReloadCheckSecs");
               buf.append(String.valueOf(this.bean.getResourceReloadCheckSecs()));
            }

            if (this.bean.isServletReloadCheckSecsSet()) {
               buf.append("ServletReloadCheckSecs");
               buf.append(String.valueOf(this.bean.getServletReloadCheckSecs()));
            }

            if (this.bean.isSingleThreadedServletPoolSizeSet()) {
               buf.append("SingleThreadedServletPoolSize");
               buf.append(String.valueOf(this.bean.getSingleThreadedServletPoolSize()));
            }

            if (this.bean.isTempDirSet()) {
               buf.append("TempDir");
               buf.append(String.valueOf(this.bean.getTempDir()));
            }

            if (this.bean.isAccessLoggingDisabledSet()) {
               buf.append("AccessLoggingDisabled");
               buf.append(String.valueOf(this.bean.isAccessLoggingDisabled()));
            }

            if (this.bean.isAllowAllRolesSet()) {
               buf.append("AllowAllRoles");
               buf.append(String.valueOf(this.bean.isAllowAllRoles()));
            }

            if (this.bean.isClientCertProxyEnabledSet()) {
               buf.append("ClientCertProxyEnabled");
               buf.append(String.valueOf(this.bean.isClientCertProxyEnabled()));
            }

            if (this.bean.isContainerInitializerEnabledSet()) {
               buf.append("ContainerInitializerEnabled");
               buf.append(String.valueOf(this.bean.isContainerInitializerEnabled()));
            }

            if (this.bean.isDisableImplicitServletMappingsSet()) {
               buf.append("DisableImplicitServletMappings");
               buf.append(String.valueOf(this.bean.isDisableImplicitServletMappings()));
            }

            if (this.bean.isFilterDispatchedRequestsEnabledSet()) {
               buf.append("FilterDispatchedRequestsEnabled");
               buf.append(String.valueOf(this.bean.isFilterDispatchedRequestsEnabled()));
            }

            if (this.bean.isIndexDirectoryEnabledSet()) {
               buf.append("IndexDirectoryEnabled");
               buf.append(String.valueOf(this.bean.isIndexDirectoryEnabled()));
            }

            if (this.bean.isNativeIOEnabledSet()) {
               buf.append("NativeIOEnabled");
               buf.append(String.valueOf(this.bean.isNativeIOEnabled()));
            }

            if (this.bean.isOptimisticSerializationSet()) {
               buf.append("OptimisticSerialization");
               buf.append(String.valueOf(this.bean.isOptimisticSerialization()));
            }

            if (this.bean.isPreferForwardQueryStringSet()) {
               buf.append("PreferForwardQueryString");
               buf.append(String.valueOf(this.bean.isPreferForwardQueryString()));
            }

            if (this.bean.isPreferWebInfClassesSet()) {
               buf.append("PreferWebInfClasses");
               buf.append(String.valueOf(this.bean.isPreferWebInfClasses()));
            }

            if (this.bean.isRedirectWithAbsoluteUrlSet()) {
               buf.append("RedirectWithAbsoluteUrl");
               buf.append(String.valueOf(this.bean.isRedirectWithAbsoluteUrl()));
            }

            if (this.bean.isReloginEnabledSet()) {
               buf.append("ReloginEnabled");
               buf.append(String.valueOf(this.bean.isReloginEnabled()));
            }

            if (this.bean.isRequireAdminTrafficSet()) {
               buf.append("RequireAdminTraffic");
               buf.append(String.valueOf(this.bean.isRequireAdminTraffic()));
            }

            if (this.bean.isRetainOriginalURLSet()) {
               buf.append("RetainOriginalURL");
               buf.append(String.valueOf(this.bean.isRetainOriginalURL()));
            }

            if (this.bean.isSaveSessionsEnabledSet()) {
               buf.append("SaveSessionsEnabled");
               buf.append(String.valueOf(this.bean.isSaveSessionsEnabled()));
            }

            if (this.bean.isSendPermanentRedirectsSet()) {
               buf.append("SendPermanentRedirects");
               buf.append(String.valueOf(this.bean.isSendPermanentRedirects()));
            }

            if (this.bean.isSessionMonitoringEnabledSet()) {
               buf.append("SessionMonitoringEnabled");
               buf.append(String.valueOf(this.bean.isSessionMonitoringEnabled()));
            }

            if (this.bean.isShowArchivedRealPathEnabledSet()) {
               buf.append("ShowArchivedRealPathEnabled");
               buf.append(String.valueOf(this.bean.isShowArchivedRealPathEnabled()));
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
            ContainerDescriptorBeanImpl otherTyped = (ContainerDescriptorBeanImpl)other;
            this.computeChildDiff("CheckAuthOnForward", this.bean.getCheckAuthOnForward(), otherTyped.getCheckAuthOnForward(), false);
            this.computeSubDiff("ClassLoading", this.bean.getClassLoading(), otherTyped.getClassLoading());
            this.computeDiff("DefaultMimeType", this.bean.getDefaultMimeType(), otherTyped.getDefaultMimeType(), false);
            this.computeDiff("FailDeployOnFilterInitError", this.bean.getFailDeployOnFilterInitError(), otherTyped.getFailDeployOnFilterInitError(), false);
            this.computeChildDiff("GzipCompression", this.bean.getGzipCompression(), otherTyped.getGzipCompression(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IndexDirectorySortBy", this.bean.getIndexDirectorySortBy(), otherTyped.getIndexDirectorySortBy(), true);
            this.computeDiff("LangtagRevision", this.bean.getLangtagRevision(), otherTyped.getLangtagRevision(), false);
            this.computeDiff("MinimumNativeFileSize", this.bean.getMinimumNativeFileSize(), otherTyped.getMinimumNativeFileSize(), true);
            this.computeSubDiff("PreferApplicationPackages", this.bean.getPreferApplicationPackages(), otherTyped.getPreferApplicationPackages());
            this.computeSubDiff("PreferApplicationResources", this.bean.getPreferApplicationResources(), otherTyped.getPreferApplicationResources());
            this.computeDiff("RedirectContent", this.bean.getRedirectContent(), otherTyped.getRedirectContent(), false);
            this.computeDiff("RedirectContentType", this.bean.getRedirectContentType(), otherTyped.getRedirectContentType(), false);
            this.computeDiff("RefererValidation", this.bean.getRefererValidation(), otherTyped.getRefererValidation(), false);
            this.computeDiff("ResourceReloadCheckSecs", this.bean.getResourceReloadCheckSecs(), otherTyped.getResourceReloadCheckSecs(), true);
            this.computeDiff("ServletReloadCheckSecs", this.bean.getServletReloadCheckSecs(), otherTyped.getServletReloadCheckSecs(), true);
            this.computeDiff("SingleThreadedServletPoolSize", this.bean.getSingleThreadedServletPoolSize(), otherTyped.getSingleThreadedServletPoolSize(), false);
            this.computeDiff("TempDir", this.bean.getTempDir(), otherTyped.getTempDir(), false);
            this.computeDiff("AccessLoggingDisabled", this.bean.isAccessLoggingDisabled(), otherTyped.isAccessLoggingDisabled(), true);
            this.computeDiff("AllowAllRoles", this.bean.isAllowAllRoles(), otherTyped.isAllowAllRoles(), false);
            this.computeDiff("ClientCertProxyEnabled", this.bean.isClientCertProxyEnabled(), otherTyped.isClientCertProxyEnabled(), false);
            this.computeDiff("ContainerInitializerEnabled", this.bean.isContainerInitializerEnabled(), otherTyped.isContainerInitializerEnabled(), false);
            this.computeDiff("DisableImplicitServletMappings", this.bean.isDisableImplicitServletMappings(), otherTyped.isDisableImplicitServletMappings(), false);
            this.computeDiff("FilterDispatchedRequestsEnabled", this.bean.isFilterDispatchedRequestsEnabled(), otherTyped.isFilterDispatchedRequestsEnabled(), false);
            this.computeDiff("IndexDirectoryEnabled", this.bean.isIndexDirectoryEnabled(), otherTyped.isIndexDirectoryEnabled(), true);
            this.computeDiff("NativeIOEnabled", this.bean.isNativeIOEnabled(), otherTyped.isNativeIOEnabled(), false);
            this.computeDiff("OptimisticSerialization", this.bean.isOptimisticSerialization(), otherTyped.isOptimisticSerialization(), false);
            this.computeDiff("PreferForwardQueryString", this.bean.isPreferForwardQueryString(), otherTyped.isPreferForwardQueryString(), false);
            this.computeDiff("PreferWebInfClasses", this.bean.isPreferWebInfClasses(), otherTyped.isPreferWebInfClasses(), false);
            this.computeDiff("RedirectWithAbsoluteUrl", this.bean.isRedirectWithAbsoluteUrl(), otherTyped.isRedirectWithAbsoluteUrl(), false);
            this.computeDiff("ReloginEnabled", this.bean.isReloginEnabled(), otherTyped.isReloginEnabled(), false);
            this.computeDiff("RequireAdminTraffic", this.bean.isRequireAdminTraffic(), otherTyped.isRequireAdminTraffic(), true);
            this.computeDiff("RetainOriginalURL", this.bean.isRetainOriginalURL(), otherTyped.isRetainOriginalURL(), false);
            this.computeDiff("SaveSessionsEnabled", this.bean.isSaveSessionsEnabled(), otherTyped.isSaveSessionsEnabled(), false);
            this.computeDiff("SendPermanentRedirects", this.bean.isSendPermanentRedirects(), otherTyped.isSendPermanentRedirects(), false);
            this.computeDiff("SessionMonitoringEnabled", this.bean.isSessionMonitoringEnabled(), otherTyped.isSessionMonitoringEnabled(), true);
            this.computeDiff("ShowArchivedRealPathEnabled", this.bean.isShowArchivedRealPathEnabled(), otherTyped.isShowArchivedRealPathEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ContainerDescriptorBeanImpl original = (ContainerDescriptorBeanImpl)event.getSourceBean();
            ContainerDescriptorBeanImpl proposed = (ContainerDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CheckAuthOnForward")) {
                  if (type == 2) {
                     original.setCheckAuthOnForward((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getCheckAuthOnForward()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CheckAuthOnForward", (DescriptorBean)original.getCheckAuthOnForward());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ClassLoading")) {
                  if (type == 2) {
                     original.setClassLoading((ClassLoadingBean)this.createCopy((AbstractDescriptorBean)proposed.getClassLoading()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClassLoading", (DescriptorBean)original.getClassLoading());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("DefaultMimeType")) {
                  original.setDefaultMimeType(proposed.getDefaultMimeType());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("FailDeployOnFilterInitError")) {
                  original.setFailDeployOnFilterInitError(proposed.getFailDeployOnFilterInitError());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("GzipCompression")) {
                  if (type == 2) {
                     original.setGzipCompression((GzipCompressionBean)this.createCopy((AbstractDescriptorBean)proposed.getGzipCompression()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("GzipCompression", (DescriptorBean)original.getGzipCompression());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("IndexDirectorySortBy")) {
                  original.setIndexDirectorySortBy(proposed.getIndexDirectorySortBy());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("LangtagRevision")) {
                  original.setLangtagRevision(proposed.getLangtagRevision());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("MinimumNativeFileSize")) {
                  original.setMinimumNativeFileSize(proposed.getMinimumNativeFileSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("PreferApplicationPackages")) {
                  if (type == 2) {
                     original.setPreferApplicationPackages((PreferApplicationPackagesBean)this.createCopy((AbstractDescriptorBean)proposed.getPreferApplicationPackages()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PreferApplicationPackages", (DescriptorBean)original.getPreferApplicationPackages());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PreferApplicationResources")) {
                  if (type == 2) {
                     original.setPreferApplicationResources((PreferApplicationResourcesBean)this.createCopy((AbstractDescriptorBean)proposed.getPreferApplicationResources()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PreferApplicationResources", (DescriptorBean)original.getPreferApplicationResources());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("RedirectContent")) {
                  original.setRedirectContent(proposed.getRedirectContent());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("RedirectContentType")) {
                  original.setRedirectContentType(proposed.getRedirectContentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RefererValidation")) {
                  original.setRefererValidation(proposed.getRefererValidation());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ResourceReloadCheckSecs")) {
                  original.setResourceReloadCheckSecs(proposed.getResourceReloadCheckSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ServletReloadCheckSecs")) {
                  original.setServletReloadCheckSecs(proposed.getServletReloadCheckSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("SingleThreadedServletPoolSize")) {
                  original.setSingleThreadedServletPoolSize(proposed.getSingleThreadedServletPoolSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("TempDir")) {
                  original.setTempDir(proposed.getTempDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("AccessLoggingDisabled")) {
                  original.setAccessLoggingDisabled(proposed.isAccessLoggingDisabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("AllowAllRoles")) {
                  original.setAllowAllRoles(proposed.isAllowAllRoles());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ClientCertProxyEnabled")) {
                  original.setClientCertProxyEnabled(proposed.isClientCertProxyEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ContainerInitializerEnabled")) {
                  original.setContainerInitializerEnabled(proposed.isContainerInitializerEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("DisableImplicitServletMappings")) {
                  original.setDisableImplicitServletMappings(proposed.isDisableImplicitServletMappings());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("FilterDispatchedRequestsEnabled")) {
                  original.setFilterDispatchedRequestsEnabled(proposed.isFilterDispatchedRequestsEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("IndexDirectoryEnabled")) {
                  original.setIndexDirectoryEnabled(proposed.isIndexDirectoryEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("NativeIOEnabled")) {
                  original.setNativeIOEnabled(proposed.isNativeIOEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("OptimisticSerialization")) {
                  original.setOptimisticSerialization(proposed.isOptimisticSerialization());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("PreferForwardQueryString")) {
                  original.setPreferForwardQueryString(proposed.isPreferForwardQueryString());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("PreferWebInfClasses")) {
                  original.setPreferWebInfClasses(proposed.isPreferWebInfClasses());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("RedirectWithAbsoluteUrl")) {
                  original.setRedirectWithAbsoluteUrl(proposed.isRedirectWithAbsoluteUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ReloginEnabled")) {
                  original.setReloginEnabled(proposed.isReloginEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("RequireAdminTraffic")) {
                  original.setRequireAdminTraffic(proposed.isRequireAdminTraffic());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("RetainOriginalURL")) {
                  original.setRetainOriginalURL(proposed.isRetainOriginalURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("SaveSessionsEnabled")) {
                  original.setSaveSessionsEnabled(proposed.isSaveSessionsEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SendPermanentRedirects")) {
                  original.setSendPermanentRedirects(proposed.isSendPermanentRedirects());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("SessionMonitoringEnabled")) {
                  original.setSessionMonitoringEnabled(proposed.isSessionMonitoringEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ShowArchivedRealPathEnabled")) {
                  original.setShowArchivedRealPathEnabled(proposed.isShowArchivedRealPathEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
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
            ContainerDescriptorBeanImpl copy = (ContainerDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CheckAuthOnForward")) && this.bean.isCheckAuthOnForwardSet() && !copy._isSet(1)) {
               Object o = this.bean.getCheckAuthOnForward();
               copy.setCheckAuthOnForward((EmptyBean)null);
               copy.setCheckAuthOnForward(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClassLoading")) && this.bean.isClassLoadingSet() && !copy._isSet(36)) {
               Object o = this.bean.getClassLoading();
               copy.setClassLoading((ClassLoadingBean)null);
               copy.setClassLoading(o == null ? null : (ClassLoadingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMimeType")) && this.bean.isDefaultMimeTypeSet()) {
               copy.setDefaultMimeType(this.bean.getDefaultMimeType());
            }

            if ((excludeProps == null || !excludeProps.contains("FailDeployOnFilterInitError")) && this.bean.isFailDeployOnFilterInitErrorSet()) {
               copy.setFailDeployOnFilterInitError(this.bean.getFailDeployOnFilterInitError());
            }

            if ((excludeProps == null || !excludeProps.contains("GzipCompression")) && this.bean.isGzipCompressionSet() && !copy._isSet(35)) {
               Object o = this.bean.getGzipCompression();
               copy.setGzipCompression((GzipCompressionBean)null);
               copy.setGzipCompression(o == null ? null : (GzipCompressionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IndexDirectorySortBy")) && this.bean.isIndexDirectorySortBySet()) {
               copy.setIndexDirectorySortBy(this.bean.getIndexDirectorySortBy());
            }

            if ((excludeProps == null || !excludeProps.contains("LangtagRevision")) && this.bean.isLangtagRevisionSet()) {
               copy.setLangtagRevision(this.bean.getLangtagRevision());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumNativeFileSize")) && this.bean.isMinimumNativeFileSizeSet()) {
               copy.setMinimumNativeFileSize(this.bean.getMinimumNativeFileSize());
            }

            if ((excludeProps == null || !excludeProps.contains("PreferApplicationPackages")) && this.bean.isPreferApplicationPackagesSet() && !copy._isSet(15)) {
               Object o = this.bean.getPreferApplicationPackages();
               copy.setPreferApplicationPackages((PreferApplicationPackagesBean)null);
               copy.setPreferApplicationPackages(o == null ? null : (PreferApplicationPackagesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PreferApplicationResources")) && this.bean.isPreferApplicationResourcesSet() && !copy._isSet(16)) {
               Object o = this.bean.getPreferApplicationResources();
               copy.setPreferApplicationResources((PreferApplicationResourcesBean)null);
               copy.setPreferApplicationResources(o == null ? null : (PreferApplicationResourcesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RedirectContent")) && this.bean.isRedirectContentSet()) {
               copy.setRedirectContent(this.bean.getRedirectContent());
            }

            if ((excludeProps == null || !excludeProps.contains("RedirectContentType")) && this.bean.isRedirectContentTypeSet()) {
               copy.setRedirectContentType(this.bean.getRedirectContentType());
            }

            if ((excludeProps == null || !excludeProps.contains("RefererValidation")) && this.bean.isRefererValidationSet()) {
               copy.setRefererValidation(this.bean.getRefererValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceReloadCheckSecs")) && this.bean.isResourceReloadCheckSecsSet()) {
               copy.setResourceReloadCheckSecs(this.bean.getResourceReloadCheckSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletReloadCheckSecs")) && this.bean.isServletReloadCheckSecsSet()) {
               copy.setServletReloadCheckSecs(this.bean.getServletReloadCheckSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("SingleThreadedServletPoolSize")) && this.bean.isSingleThreadedServletPoolSizeSet()) {
               copy.setSingleThreadedServletPoolSize(this.bean.getSingleThreadedServletPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("TempDir")) && this.bean.isTempDirSet()) {
               copy.setTempDir(this.bean.getTempDir());
            }

            if ((excludeProps == null || !excludeProps.contains("AccessLoggingDisabled")) && this.bean.isAccessLoggingDisabledSet()) {
               copy.setAccessLoggingDisabled(this.bean.isAccessLoggingDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowAllRoles")) && this.bean.isAllowAllRolesSet()) {
               copy.setAllowAllRoles(this.bean.isAllowAllRoles());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertProxyEnabled")) && this.bean.isClientCertProxyEnabledSet()) {
               copy.setClientCertProxyEnabled(this.bean.isClientCertProxyEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ContainerInitializerEnabled")) && this.bean.isContainerInitializerEnabledSet()) {
               copy.setContainerInitializerEnabled(this.bean.isContainerInitializerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DisableImplicitServletMappings")) && this.bean.isDisableImplicitServletMappingsSet()) {
               copy.setDisableImplicitServletMappings(this.bean.isDisableImplicitServletMappings());
            }

            if ((excludeProps == null || !excludeProps.contains("FilterDispatchedRequestsEnabled")) && this.bean.isFilterDispatchedRequestsEnabledSet()) {
               copy.setFilterDispatchedRequestsEnabled(this.bean.isFilterDispatchedRequestsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IndexDirectoryEnabled")) && this.bean.isIndexDirectoryEnabledSet()) {
               copy.setIndexDirectoryEnabled(this.bean.isIndexDirectoryEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("NativeIOEnabled")) && this.bean.isNativeIOEnabledSet()) {
               copy.setNativeIOEnabled(this.bean.isNativeIOEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OptimisticSerialization")) && this.bean.isOptimisticSerializationSet()) {
               copy.setOptimisticSerialization(this.bean.isOptimisticSerialization());
            }

            if ((excludeProps == null || !excludeProps.contains("PreferForwardQueryString")) && this.bean.isPreferForwardQueryStringSet()) {
               copy.setPreferForwardQueryString(this.bean.isPreferForwardQueryString());
            }

            if ((excludeProps == null || !excludeProps.contains("PreferWebInfClasses")) && this.bean.isPreferWebInfClassesSet()) {
               copy.setPreferWebInfClasses(this.bean.isPreferWebInfClasses());
            }

            if ((excludeProps == null || !excludeProps.contains("RedirectWithAbsoluteUrl")) && this.bean.isRedirectWithAbsoluteUrlSet()) {
               copy.setRedirectWithAbsoluteUrl(this.bean.isRedirectWithAbsoluteUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("ReloginEnabled")) && this.bean.isReloginEnabledSet()) {
               copy.setReloginEnabled(this.bean.isReloginEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RequireAdminTraffic")) && this.bean.isRequireAdminTrafficSet()) {
               copy.setRequireAdminTraffic(this.bean.isRequireAdminTraffic());
            }

            if ((excludeProps == null || !excludeProps.contains("RetainOriginalURL")) && this.bean.isRetainOriginalURLSet()) {
               copy.setRetainOriginalURL(this.bean.isRetainOriginalURL());
            }

            if ((excludeProps == null || !excludeProps.contains("SaveSessionsEnabled")) && this.bean.isSaveSessionsEnabledSet()) {
               copy.setSaveSessionsEnabled(this.bean.isSaveSessionsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SendPermanentRedirects")) && this.bean.isSendPermanentRedirectsSet()) {
               copy.setSendPermanentRedirects(this.bean.isSendPermanentRedirects());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionMonitoringEnabled")) && this.bean.isSessionMonitoringEnabledSet()) {
               copy.setSessionMonitoringEnabled(this.bean.isSessionMonitoringEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ShowArchivedRealPathEnabled")) && this.bean.isShowArchivedRealPathEnabledSet()) {
               copy.setShowArchivedRealPathEnabled(this.bean.isShowArchivedRealPathEnabled());
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
         this.inferSubTree(this.bean.getCheckAuthOnForward(), clazz, annotation);
         this.inferSubTree(this.bean.getClassLoading(), clazz, annotation);
         this.inferSubTree(this.bean.getGzipCompression(), clazz, annotation);
         this.inferSubTree(this.bean.getPreferApplicationPackages(), clazz, annotation);
         this.inferSubTree(this.bean.getPreferApplicationResources(), clazz, annotation);
      }
   }
}
