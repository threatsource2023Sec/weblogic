package weblogic.management.mbeanservers.edit.internal;

import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationModuleMBean;
import weblogic.management.mbeanservers.edit.DescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class AppDeploymentConfigurationModuleMBeanImpl extends ServiceImpl implements AppDeploymentConfigurationModuleMBean {
   private AppInfo appInfo;
   private WLSModelMBeanContext context;
   private ModuleBeanInfo module = null;
   private DescriptorMBean[] descriptors = null;
   public String objectName = null;

   public AppDeploymentConfigurationModuleMBeanImpl(AppInfo appInfo, WLSModelMBeanContext context, ModuleBeanInfo module, DescriptorMBean[] descriptors) {
      super("AppDeploymentConfigurationModule", AppDeploymentConfigurationModuleMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.context = context;
      this.module = module;
      this.descriptors = descriptors;
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + AppDeploymentConfigurationModuleMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public String getName() {
      return this.module.getName();
   }

   public String getType() {
      return this.module.getType();
   }

   public DescriptorMBean[] getDescriptors() {
      return this.descriptors;
   }
}
