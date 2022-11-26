package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.WebAppDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class WebAppDescriptorMBeanImpl extends ServiceImpl implements WebAppDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private WebAppBean webAppBean;
   private WeblogicWebAppBean weblogicWebAppBean;
   public String objectName = null;

   public WebAppDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("WebAppDescriptor", WebAppDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.webAppBean = (WebAppBean)module.getStdDesc();
      this.weblogicWebAppBean = (WeblogicWebAppBean)module.getConfigDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + WebAppDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public WebAppBean getWebAppDescriptor() {
      return this.webAppBean;
   }

   public WeblogicWebAppBean getWeblogicWebAppDescriptor() {
      return this.weblogicWebAppBean;
   }
}
