package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.DatasourceDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class DatasourceDescriptorMBeanImpl extends ServiceImpl implements DatasourceDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private JDBCDataSourceBean jdbcBean;
   public String objectName = null;

   public DatasourceDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("DatasourceDescriptor", DatasourceDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.jdbcBean = (JDBCDataSourceBean)module.getStdDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + DatasourceDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public JDBCDataSourceBean getDatasourceDescriptor() {
      return this.jdbcBean;
   }
}
