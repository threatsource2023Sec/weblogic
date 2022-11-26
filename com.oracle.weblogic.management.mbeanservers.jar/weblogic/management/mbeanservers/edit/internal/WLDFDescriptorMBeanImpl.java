package weblogic.management.mbeanservers.edit.internal;

import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.WLDFDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class WLDFDescriptorMBeanImpl extends ServiceImpl implements WLDFDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private WLDFResourceBean WLDFResourceBean;
   public String objectName = null;

   public WLDFDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("WLDFDescriptor", WLDFDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.WLDFResourceBean = (WLDFResourceBean)module.getConfigDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + WLDFDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public WLDFResourceBean getWLDFDescriptor() {
      return this.WLDFResourceBean;
   }
}
