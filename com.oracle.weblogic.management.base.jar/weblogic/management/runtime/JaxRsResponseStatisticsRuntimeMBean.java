package weblogic.management.runtime;

import java.util.Map;

public interface JaxRsResponseStatisticsRuntimeMBean extends RuntimeMBean {
   Integer getLastResponseCode();

   Map getResponseCodes();
}
