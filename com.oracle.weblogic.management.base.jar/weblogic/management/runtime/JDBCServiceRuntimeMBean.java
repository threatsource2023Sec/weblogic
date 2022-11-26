package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface JDBCServiceRuntimeMBean extends RuntimeMBean, HealthFeedback {
   JDBCDataSourceRuntimeMBean[] getJDBCDataSourceRuntimeMBeans();

   JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String var1);

   JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String var1, String var2, String var3, String var4);

   JDBCMultiDataSourceRuntimeMBean[] getJDBCMultiDataSourceRuntimeMBeans();

   JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String var1);

   JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String var1, String var2, String var3, String var4);

   JDBCDriverRuntimeMBean[] getJDBCDriverRuntimeMBeans();

   JDBCDriverRuntimeMBean lookupJDBCDriverRuntimeMBean(String var1);

   HealthState getHealthState();
}
