package weblogic.jdbc.common.internal;

import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCPartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class JDBCPartitionRuntimeImpl extends RuntimeMBeanDelegate implements JDBCPartitionRuntimeMBean {
   private JDBCPartition jdbcPartition;

   public JDBCPartitionRuntimeImpl(String name, PartitionRuntimeMBean parent, JDBCPartition jdbcPartition) throws ManagementException {
      super(name, parent);
      this.jdbcPartition = jdbcPartition;
   }

   public JDBCDataSourceRuntimeMBean[] getJDBCDataSourceRuntimeMBeans() {
      return this.jdbcPartition.getJDBCDataSourceRuntimeMBeans();
   }

   public JDBCMultiDataSourceRuntimeMBean[] getJDBCMultiDataSourceRuntimeMBeans() {
      return this.jdbcPartition.getJDBCMultiDataSourceRuntimeMBeans();
   }

   public JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name) {
      return this.jdbcPartition.lookupJDBCDataSourceRuntimeMBean(name);
   }

   public JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      return this.jdbcPartition.lookupJDBCDataSourceRuntimeMBean(name, appName, moduleName, componentName);
   }

   public JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name) {
      return this.jdbcPartition.lookupJDBCMultiDataSourceRuntimeMBean(name);
   }

   public JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      return this.jdbcPartition.lookupJDBCMultiDataSourceRuntimeMBean(name, appName, moduleName, componentName);
   }
}
