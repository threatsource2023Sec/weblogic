package weblogic.ejb.spi;

import weblogic.management.ManagementException;
import weblogic.management.runtime.QueryCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public interface QueryCache {
   QueryCacheRuntimeMBean createRuntimeMBean(String var1, RuntimeMBean var2) throws ManagementException;

   void setRuntimeMBean(QueryCacheRuntimeMBean var1);
}
