package weblogic.management.runtime;

public interface JDBCPartitionRuntimeMBean extends RuntimeMBean {
   JDBCDataSourceRuntimeMBean[] getJDBCDataSourceRuntimeMBeans();

   JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String var1);

   JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String var1, String var2, String var3, String var4);

   JDBCMultiDataSourceRuntimeMBean[] getJDBCMultiDataSourceRuntimeMBeans();

   JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String var1);

   JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String var1, String var2, String var3, String var4);
}
