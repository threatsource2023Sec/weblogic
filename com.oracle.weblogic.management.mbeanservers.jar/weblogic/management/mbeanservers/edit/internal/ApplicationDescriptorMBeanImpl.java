package weblogic.management.mbeanservers.edit.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.AppInfo;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.ApplicationDescriptorMBean;

public class ApplicationDescriptorMBeanImpl extends DescriptorMBeanImpl implements ApplicationDescriptorMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   public String objectName = null;
   private DomainMBean domain;
   private WeblogicApplicationBean weblogicApplication;
   private ApplicationBean application;
   private String appName = null;

   ApplicationDescriptorMBeanImpl(AppInfo appInfo, DomainMBean domain, Service parent, String parentAttribute, WeblogicApplicationBean weblogicApplication, ApplicationBean application) {
      super("ApplicationDescriptor", ApplicationDescriptorMBean.class.getName(), parent, parentAttribute);
      this.objectName = "com.bea:Name=" + appInfo.getName() + ",Type=" + ApplicationDescriptorMBean.class.getName();
      if (appInfo.getPartitionName() != null) {
         this.objectName = this.objectName + ",Partition=" + appInfo.getPartitionName();
      }

      if (appInfo.getResourceGroupName() != null) {
         this.objectName = this.objectName + ",ResourceGroup=" + appInfo.getResourceGroupName();
      }

      this.domain = domain;
      this.weblogicApplication = weblogicApplication;
      this.application = application;
      this.appName = appInfo.getName();
   }

   public WeblogicApplicationBean getWeblogicApplicationDescriptor() {
      return this.weblogicApplication;
   }

   public ApplicationBean getApplicationDescriptor() {
      return this.application;
   }
}
