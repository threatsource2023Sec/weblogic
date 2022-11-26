package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.JMSDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class JMSDescriptorMBeanImpl extends ServiceImpl implements JMSDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private JMSBean jmsBean;
   public String objectName = null;

   public JMSDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("JMSDescriptor", JMSDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.jmsBean = (JMSBean)module.getStdDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + JMSDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public JMSBean getJMSDescriptor() {
      return this.jmsBean;
   }
}
