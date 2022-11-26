package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.EmptyBean;

public interface ContainerDescriptorBean {
   String getRefererValidation();

   void setRefererValidation(String var1);

   EmptyBean getCheckAuthOnForward();

   EmptyBean createCheckAuthOnForward();

   void destroyCheckAuthOnForward(EmptyBean var1);

   boolean isFilterDispatchedRequestsEnabled();

   void setFilterDispatchedRequestsEnabled(boolean var1);

   String getRedirectContentType();

   void setRedirectContentType(String var1);

   String getRedirectContent();

   void setRedirectContent(String var1);

   boolean isRedirectWithAbsoluteUrl();

   void setRedirectWithAbsoluteUrl(boolean var1);

   boolean isIndexDirectoryEnabled();

   void setIndexDirectoryEnabled(boolean var1);

   boolean isIndexDirectoryEnabledSet();

   String getIndexDirectorySortBy();

   void setIndexDirectorySortBy(String var1);

   int getServletReloadCheckSecs();

   void setServletReloadCheckSecs(int var1);

   boolean isServletReloadCheckSecsSet();

   boolean isSendPermanentRedirects();

   void setSendPermanentRedirects(boolean var1);

   int getResourceReloadCheckSecs();

   void setResourceReloadCheckSecs(int var1);

   /** @deprecated */
   @Deprecated
   int getSingleThreadedServletPoolSize();

   void setSingleThreadedServletPoolSize(int var1);

   /** @deprecated */
   @Deprecated
   boolean isSessionMonitoringEnabled();

   void setSessionMonitoringEnabled(boolean var1);

   boolean isSaveSessionsEnabled();

   void setSaveSessionsEnabled(boolean var1);

   boolean isPreferWebInfClasses();

   void setPreferWebInfClasses(boolean var1);

   PreferApplicationPackagesBean getPreferApplicationPackages();

   PreferApplicationResourcesBean getPreferApplicationResources();

   String getDefaultMimeType();

   void setDefaultMimeType(String var1);

   boolean isReloginEnabled();

   void setReloginEnabled(boolean var1);

   boolean isAllowAllRoles();

   void setAllowAllRoles(boolean var1);

   boolean isClientCertProxyEnabled();

   void setClientCertProxyEnabled(boolean var1);

   boolean isNativeIOEnabled();

   void setNativeIOEnabled(boolean var1);

   long getMinimumNativeFileSize();

   void setMinimumNativeFileSize(long var1);

   boolean isDisableImplicitServletMappings();

   void setDisableImplicitServletMappings(boolean var1);

   String getTempDir();

   void setTempDir(String var1);

   boolean isOptimisticSerialization();

   void setOptimisticSerialization(boolean var1);

   boolean isRetainOriginalURL();

   void setRetainOriginalURL(boolean var1);

   String getId();

   void setId(String var1);

   boolean isShowArchivedRealPathEnabled();

   void setShowArchivedRealPathEnabled(boolean var1);

   boolean isShowArchivedRealPathEnabledSet();

   boolean isRequireAdminTraffic();

   void setRequireAdminTraffic(boolean var1);

   boolean isAccessLoggingDisabled();

   void setAccessLoggingDisabled(boolean var1);

   boolean isAccessLoggingDisabledSet();

   boolean isPreferForwardQueryString();

   void setPreferForwardQueryString(boolean var1);

   boolean isPreferForwardQueryStringSet();

   boolean getFailDeployOnFilterInitError();

   void setFailDeployOnFilterInitError(boolean var1);

   boolean isContainerInitializerEnabled();

   void setContainerInitializerEnabled(boolean var1);

   boolean isContainerInitializerEnabledSet();

   String getLangtagRevision();

   void setLangtagRevision(String var1);

   GzipCompressionBean getGzipCompression();

   GzipCompressionBean createGzipCompression();

   void destroyGzipCompression();

   ClassLoadingBean getClassLoading();
}
