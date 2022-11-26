package weblogic.management.runtime;

import java.util.Map;

public interface JaxRsExceptionMapperStatisticsRuntimeMBean extends RuntimeMBean {
   Map getExceptionMapperCount();

   long getSuccessfulMappings();

   long getUnsuccessfulMappings();

   long getTotalMappings();
}
