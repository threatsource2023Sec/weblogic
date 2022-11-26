package weblogic.management.configuration;

public interface WebserviceSecurityMBean extends ConfigurationMBean {
   WebserviceTokenHandlerMBean[] getWebserviceTokenHandlers();

   WebserviceTokenHandlerMBean lookupWebserviceTokenHandler(String var1);

   WebserviceTokenHandlerMBean createWebserviceTokenHandler(String var1);

   void destroyWebserviceTokenHandler(WebserviceTokenHandlerMBean var1);

   WebserviceCredentialProviderMBean[] getWebserviceCredentialProviders();

   WebserviceCredentialProviderMBean lookupWebserviceCredentialProvider(String var1);

   WebserviceCredentialProviderMBean createWebserviceCredentialProvider(String var1);

   void destroyWebserviceCredentialProvider(WebserviceCredentialProviderMBean var1);

   WebserviceSecurityTokenMBean[] getWebserviceSecurityTokens();

   WebserviceSecurityTokenMBean lookupWebserviceSecurityToken(String var1);

   WebserviceSecurityTokenMBean createWebserviceSecurityToken(String var1);

   void destroyWebserviceSecurityToken(WebserviceSecurityTokenMBean var1);

   WebserviceTimestampMBean getWebserviceTimestamp();

   String getDefaultCredentialProviderSTSURI();

   void setDefaultCredentialProviderSTSURI(String var1);

   String getPolicySelectionPreference();

   void setPolicySelectionPreference(String var1);

   void setCompatibilityPreference(String var1);

   String getCompatibilityPreference();

   void setCompatibilityOrderingPreference(String var1);

   String getCompatibilityOrderingPreference();
}
