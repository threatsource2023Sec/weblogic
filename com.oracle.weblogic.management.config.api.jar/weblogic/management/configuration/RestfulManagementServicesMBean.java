package weblogic.management.configuration;

public interface RestfulManagementServicesMBean extends ConfigurationMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   boolean isJavaServiceResourcesEnabled();

   void setJavaServiceResourcesEnabled(boolean var1);

   int getFannedOutRequestMaxWaitMillis();

   void setFannedOutRequestMaxWaitMillis(int var1);

   int getDelegatedRequestMaxWaitMillis();

   void setDelegatedRequestMaxWaitMillis(int var1);

   int getDelegatedRequestConnectTimeoutMillis();

   void setDelegatedRequestConnectTimeoutMillis(int var1);

   int getDelegatedRequestReadTimeoutMillis();

   void setDelegatedRequestReadTimeoutMillis(int var1);

   int getDelegatedRequestMinThreads();

   void setDelegatedRequestMinThreads(int var1);

   boolean isCORSEnabled();

   void setCORSEnabled(boolean var1);

   String[] getCORSAllowedOrigins();

   void setCORSAllowedOrigins(String[] var1);

   boolean isCORSAllowedCredentials();

   void setCORSAllowedCredentials(boolean var1);

   String getCORSAllowedMethods();

   void setCORSAllowedMethods(String var1);

   String getCORSAllowedHeaders();

   void setCORSAllowedHeaders(String var1);

   String getCORSExposedHeaders();

   void setCORSExposedHeaders(String var1);

   String getCORSMaxAge();

   void setCORSMaxAge(String var1);
}
