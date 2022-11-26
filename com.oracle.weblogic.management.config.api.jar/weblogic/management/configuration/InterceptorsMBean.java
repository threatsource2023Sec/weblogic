package weblogic.management.configuration;

public interface InterceptorsMBean extends ConfigurationMBean {
   boolean isWhiteListingEnabled();

   void setWhiteListingEnabled(boolean var1);

   InterceptorMBean[] getInterceptors();

   InterceptorMBean lookupInterceptor(String var1);

   InterceptorMBean createInterceptor(String var1);

   ScriptInterceptorMBean createScriptInterceptor(String var1);

   DatasourceInterceptorMBean createDatasourceInterceptor(String var1);

   void destroyInterceptor(InterceptorMBean var1);
}
