package weblogic.management.mbeanservers.edit.internal;

import weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.GarDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class GarDescriptorMBeanImpl extends ServiceImpl implements GarDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private CoherenceApplicationBean coherenceApplicationBean;
   public String objectName = null;

   public GarDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("GarDescriptor", GarDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.coherenceApplicationBean = (CoherenceApplicationBean)module.getStdDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + GarDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public CoherenceApplicationBean getCoherenceApplicationDescriptor() {
      return this.coherenceApplicationBean;
   }
}
