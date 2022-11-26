package weblogic.management.runtime;

public interface JaxRsUriRuntimeMBean extends RuntimeMBean {
   String getPath();

   boolean isExtended();

   JaxRsExecutionStatisticsRuntimeMBean getMethodsStatistics();

   JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics();

   JaxRsResourceMethodRuntimeMBean[] getResourceMethods();

   JaxRsResourceMethodRuntimeMBean lookupResourceMethods(String var1);

   JaxRsSubResourceLocatorRuntimeMBean[] getSubResourceLocators();

   JaxRsSubResourceLocatorRuntimeMBean lookupSubResourceLocators(String var1);
}
