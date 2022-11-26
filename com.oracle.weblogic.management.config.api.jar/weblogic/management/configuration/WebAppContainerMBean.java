package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface WebAppContainerMBean extends ConfigurationMBean {
   boolean isReloginEnabled();

   void setReloginEnabled(boolean var1);

   boolean isAllowAllRoles();

   void setAllowAllRoles(boolean var1) throws InvalidAttributeValueException;

   boolean isFilterDispatchedRequestsEnabled();

   void setFilterDispatchedRequestsEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isOverloadProtectionEnabled();

   void setOverloadProtectionEnabled(boolean var1);

   String getXPoweredByHeaderLevel();

   void setXPoweredByHeaderLevel(String var1);

   String getMimeMappingFile();

   void setMimeMappingFile(String var1);

   boolean isOptimisticSerialization();

   void setOptimisticSerialization(boolean var1);

   boolean isRetainOriginalURL();

   void setRetainOriginalURL(boolean var1);

   boolean isServletAuthenticationFormURL();

   void setServletAuthenticationFormURL(boolean var1);

   boolean isRtexprvalueJspParamName();

   void setRtexprvalueJspParamName(boolean var1);

   void setClientCertProxyEnabled(boolean var1);

   boolean isClientCertProxyEnabled();

   void setHttpTraceSupportEnabled(boolean var1);

   boolean isHttpTraceSupportEnabled();

   void setWeblogicPluginEnabled(boolean var1);

   boolean isWeblogicPluginEnabled();

   void setAuthCookieEnabled(boolean var1);

   boolean isAuthCookieEnabled();

   void setWAPEnabled(boolean var1);

   boolean isWAPEnabled();

   void setPostTimeoutSecs(int var1) throws InvalidAttributeValueException;

   int getPostTimeoutSecs();

   void setMaxPostTimeSecs(int var1) throws InvalidAttributeValueException;

   int getMaxPostTimeSecs();

   void setMaxPostSize(int var1) throws InvalidAttributeValueException;

   int getMaxPostSize();

   void setMaxRequestParameterCount(int var1) throws InvalidAttributeValueException;

   int getMaxRequestParameterCount();

   boolean isMaxRequestParameterCountSet();

   /** @deprecated */
   @Deprecated
   void setMaxRequestParamterCount(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getMaxRequestParamterCount();

   /** @deprecated */
   @Deprecated
   boolean isMaxRequestParamterCountSet();

   boolean isWorkContextPropagationEnabled();

   void setWorkContextPropagationEnabled(boolean var1);

   void setP3PHeaderValue(String var1);

   String getP3PHeaderValue();

   boolean isJSPCompilerBackwardsCompatible();

   void setJSPCompilerBackwardsCompatible(boolean var1);

   int getServletReloadCheckSecs();

   void setServletReloadCheckSecs(int var1);

   boolean isServletReloadCheckSecsSet();

   boolean isShowArchivedRealPathEnabled();

   void setShowArchivedRealPathEnabled(boolean var1);

   boolean isChangeSessionIDOnAuthentication();

   void setChangeSessionIDOnAuthentication(boolean var1);

   GzipCompressionMBean getGzipCompression();

   Http2ConfigMBean getHttp2Config();

   void setJaxRsMonitoringDefaultBehavior(boolean var1);

   boolean isJaxRsMonitoringDefaultBehavior();

   boolean isJaxRsMonitoringDefaultBehaviorSet();
}
