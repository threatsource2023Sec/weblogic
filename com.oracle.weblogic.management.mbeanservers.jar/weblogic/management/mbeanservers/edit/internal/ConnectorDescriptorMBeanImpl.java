package weblogic.management.mbeanservers.edit.internal;

import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.ConnectorDescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.internal.ModuleBeanInfo;

public class ConnectorDescriptorMBeanImpl extends ServiceImpl implements ConnectorDescriptorMBean {
   private AppInfo appInfo;
   private ModuleBeanInfo module = null;
   private ConnectorBean connectorBean;
   private WeblogicConnectorBean weblogicConnectorBean;
   public String objectName = null;

   public ConnectorDescriptorMBeanImpl(AppInfo appInfo, ModuleBeanInfo module) {
      super("ConnectorDescriptor", ConnectorDescriptorMBean.class.getName(), (Service)null, (String)null);
      this.appInfo = appInfo;
      this.module = module;
      this.connectorBean = (ConnectorBean)module.getStdDesc();
      this.weblogicConnectorBean = (WeblogicConnectorBean)module.getConfigDesc();
      this.objectName = "com.bea:Name=" + appInfo.getName() + "," + "ModuleName" + "=" + module.getName() + ",Type=" + ConnectorDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

   }

   public ConnectorBean getConnectorDescriptor() {
      return this.connectorBean;
   }

   public WeblogicConnectorBean getWeblogicConnectorDescriptor() {
      return this.weblogicConnectorBean;
   }
}
