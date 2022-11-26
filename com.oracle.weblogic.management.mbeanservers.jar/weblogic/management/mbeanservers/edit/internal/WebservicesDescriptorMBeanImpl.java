package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.WebservicesBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebservicesBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.WebservicesDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class WebservicesDescriptorMBeanImpl extends ServiceImpl implements WebservicesDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private WebservicesBean webservicesBean;
   private WeblogicWebservicesBean weblogicWebservicesBean;
   public String objectName = null;

   public WebservicesDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("WebservicesDescriptor", WebservicesDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.webservicesBean = (WebservicesBean)module.getStdDesc();
      this.weblogicWebservicesBean = (WeblogicWebservicesBean)module.getConfigDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + WebservicesDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public WebservicesBean getWebservicesDescriptor() {
      return this.webservicesBean;
   }

   public WeblogicWebservicesBean getWeblogicWebservicesDescriptor() {
      return this.weblogicWebservicesBean;
   }

   ModuleBeanInfo getModuleBeanInfo() {
      return this.module;
   }
}
