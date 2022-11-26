package weblogic.management.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface JaxRsApplicationRuntimeMBean extends JaxRsMonitoringInfoRuntimeMBean {
   String getApplicationName();

   String getApplicationClass();

   Map getProperties();

   long getStartTime();

   Set getRegisteredClasses();

   Set getRegisteredInstances();

   Set getAllRegisteredClasses();

   JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics();

   JaxRsResponseStatisticsRuntimeMBean getResponseStatistics();

   JaxRsExceptionMapperStatisticsRuntimeMBean getExceptionMapperStatistics();

   JaxRsResourceRuntimeMBean[] getRootResourcesByClass();

   JaxRsResourceRuntimeMBean lookupRootResourcesByClass(String var1);

   JaxRsUriRuntimeMBean[] getRootResourcesByUri();

   JaxRsUriRuntimeMBean lookupRootResourcesByUri(String var1);

   ServletRuntimeMBean getServlet();

   boolean isWadlGenerationEnabled();

   void setWadlGenerationEnabled(boolean var1);

   String getWadlUrl();

   String getRootPath();

   String getResourcePattern();

   /** @deprecated */
   @Deprecated
   JaxRsResourceConfigTypeRuntimeMBean getResourceConfig();

   /** @deprecated */
   @Deprecated
   JaxRsResourceRuntimeMBean[] getRootResources();

   /** @deprecated */
   @Deprecated
   JaxRsResourceRuntimeMBean lookupRootResource(String var1);

   /** @deprecated */
   @Deprecated
   long getErrorCount();

   /** @deprecated */
   @Deprecated
   String[] getLastErrorDetails();

   /** @deprecated */
   @Deprecated
   String getLastErrorMapper();

   /** @deprecated */
   @Deprecated
   long getLastErrorTime();

   /** @deprecated */
   @Deprecated
   String getLastHttpMethod();

   /** @deprecated */
   @Deprecated
   int getLastResponseCode();

   /** @deprecated */
   @Deprecated
   HashMap getResponseCodeCounts();

   /** @deprecated */
   @Deprecated
   HashMap getHttpMethodCounts();

   /** @deprecated */
   @Deprecated
   boolean isApplicationEnabled();

   /** @deprecated */
   @Deprecated
   void setApplicationEnabled(boolean var1);
}
