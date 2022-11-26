package weblogic.jdbc.common.internal;

import java.util.Collection;
import java.util.Iterator;
import javax.sql.DataSource;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCPartitionRuntimeMBean;
import weblogic.management.runtime.JDBCProxyDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCUCPDataSourceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public abstract class RuntimeMBeanHelper {
   private static RuntimeMBeanHelper instance;

   public static RuntimeMBeanHelper getHelper() {
      return instance;
   }

   static void setHelper(RuntimeMBeanHelper helper) {
      instance = helper;
   }

   abstract RuntimeMBean getParent(JDBCDataSourceBean var1, String var2);

   abstract RuntimeMBean getParent(JDBCDataSourceBean var1, String var2, boolean var3);

   abstract RuntimeMBean getRestParent(JDBCDataSourceBean var1, String var2);

   abstract JDBCDataSourceRuntimeMBean createDataSourceRuntimeMBean(JDBCConnectionPool var1, ResourcePoolGroup var2, String var3, JDBCDataSourceBean var4, String var5, String var6) throws ManagementException;

   abstract JDBCOracleDataSourceRuntimeMBean createHADataSourceRuntimeMBean(JDBCConnectionPool var1, ResourcePoolGroup var2, String var3, String var4, JDBCDataSourceBean var5, String var6, String var7) throws ManagementException;

   abstract JDBCMultiDataSourceRuntimeMBean createMultiDataSourceRuntimeMBean(MultiPool var1, String var2, JDBCDataSourceBean var3, String var4) throws ManagementException;

   abstract JDBCProxyDataSourceRuntimeMBean createProxyDataSourceRuntimeMBean(DataSource var1, String var2, JDBCDataSourceBean var3, String var4) throws ManagementException;

   abstract JDBCUCPDataSourceRuntimeMBean createUCPDataSourceRuntimeMBean(DataSource var1, String var2, JDBCDataSourceBean var3, String var4) throws ManagementException;

   abstract JDBCPartitionRuntimeMBean createJDBCPartitionRuntimeMBean(String var1, PartitionRuntimeMBean var2, JDBCPartition var3) throws ManagementException;

   abstract void unregister(RuntimeMBean var1) throws ManagementException;

   abstract JDBCDriverRuntimeMBean createDriverRuntimeMBean(String var1) throws ManagementException;

   String getRuntimeMBeanNameAttributeValue(JDBCDataSourceBean dsBean) {
      return JDBCUtil.getPartitionName(dsBean) != null && JDBCUtil.getUnqualifiedName(dsBean) != null ? JDBCUtil.getUnqualifiedName(dsBean) : dsBean.getName();
   }

   void unregister(Collection runtimeMBeans) throws ManagementException {
      Iterator i1 = runtimeMBeans.iterator();

      while(i1.hasNext()) {
         this.unregister((RuntimeMBean)i1.next());
      }

      runtimeMBeans.clear();
   }

   abstract String getDriverRuntimeMBeanName(String var1);
}
