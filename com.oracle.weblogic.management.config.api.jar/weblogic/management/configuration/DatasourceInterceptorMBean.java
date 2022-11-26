package weblogic.management.configuration;

public interface DatasourceInterceptorMBean extends InterceptorMBean {
   String DATASOURCE_INTERCEPTOR_TYPE = "DatasourceInterceptor";
   String ELASTIC_SERVICE_MANAGER = "ElasticServiceManager";

   String getInterceptorTypeName();

   String getInterceptedTargetKey();

   String getConnectionUrlsPattern();

   void setConnectionUrlsPattern(String var1);

   int getConnectionQuota();

   void setConnectionQuota(int var1);
}
