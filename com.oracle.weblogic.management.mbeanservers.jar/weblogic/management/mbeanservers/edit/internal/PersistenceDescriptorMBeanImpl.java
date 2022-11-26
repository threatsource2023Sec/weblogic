package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.PersistenceBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.PersistenceDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class PersistenceDescriptorMBeanImpl extends ServiceImpl implements PersistenceDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private PersistenceBean persistenceBean;
   public String objectName = null;

   public PersistenceDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("PersistenceDescriptor", PersistenceDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.persistenceBean = (PersistenceBean)module.getStdDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + PersistenceDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public PersistenceBean getPersistenceDescriptor() {
      return this.persistenceBean;
   }
}
