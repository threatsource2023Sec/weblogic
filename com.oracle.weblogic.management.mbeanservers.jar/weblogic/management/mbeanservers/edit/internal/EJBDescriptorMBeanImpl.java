package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.EJBDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class EJBDescriptorMBeanImpl extends ServiceImpl implements EJBDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private EjbJarBean ejbJarBean;
   private WeblogicEjbJarBean weblogicEjbJarBean;
   public String objectName = null;

   public EJBDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("EJBDescriptor", EJBDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.ejbJarBean = (EjbJarBean)module.getStdDesc();
      this.weblogicEjbJarBean = (WeblogicEjbJarBean)module.getConfigDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + EJBDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public EjbJarBean getEJBDescriptor() {
      return this.ejbJarBean;
   }

   public WeblogicEjbJarBean getWeblogicEJBDescriptor() {
      return this.weblogicEjbJarBean;
   }
}
