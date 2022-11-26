package weblogic.jdbc.common.internal;

import weblogic.health.HealthState;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.JDBCMultiDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCServiceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class ServiceRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JDBCServiceRuntimeMBean {
   private JDBCService service;

   public ServiceRuntimeMBeanImpl(JDBCService service) throws ManagementException {
      this.service = service;
   }

   public JDBCDataSourceRuntimeMBean[] getJDBCDataSourceRuntimeMBeans() {
      JDBCService var10000 = this.service;
      return JDBCService.getJDBCDataSourceRuntimeMBeans();
   }

   public JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name) {
      JDBCService var10000 = this.service;
      return JDBCService.lookupJDBCDataSourceRuntimeMBean(name);
   }

   public JDBCDataSourceRuntimeMBean lookupJDBCDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      JDBCService var10000 = this.service;
      return JDBCService.lookupJDBCDataSourceRuntimeMBean(name, appName, moduleName, componentName);
   }

   public JDBCMultiDataSourceRuntimeMBean[] getJDBCMultiDataSourceRuntimeMBeans() {
      JDBCService var10000 = this.service;
      return JDBCService.getJDBCMultiDataSourceRuntimeMBeans();
   }

   public JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name) {
      JDBCService var10000 = this.service;
      return JDBCService.lookupJDBCMultiDataSourceRuntimeMBean(name);
   }

   public JDBCMultiDataSourceRuntimeMBean lookupJDBCMultiDataSourceRuntimeMBean(String name, String appName, String moduleName, String componentName) {
      JDBCService var10000 = this.service;
      return JDBCService.lookupJDBCMultiDataSourceRuntimeMBean(name, appName, moduleName, componentName);
   }

   public JDBCDriverRuntimeMBean[] getJDBCDriverRuntimeMBeans() {
      JDBCService var10000 = this.service;
      return JDBCService.getJDBCDriverRuntimeMBeans();
   }

   public JDBCDriverRuntimeMBean lookupJDBCDriverRuntimeMBean(String name) {
      JDBCService var10000 = this.service;
      return JDBCService.getJDBCDriverRuntimeMBean(name);
   }

   public HealthState getHealthState() {
      return this.service.getHealthState();
   }
}
