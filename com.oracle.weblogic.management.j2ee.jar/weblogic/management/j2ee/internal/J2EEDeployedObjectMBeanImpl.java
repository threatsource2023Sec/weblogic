package weblogic.management.j2ee.internal;

import weblogic.management.j2ee.J2EEDeployedObjectMBean;

public class J2EEDeployedObjectMBeanImpl extends J2EEManagedObjectMBeanImpl implements J2EEDeployedObjectMBean {
   protected final String serverName;
   protected ApplicationInfo info;

   J2EEDeployedObjectMBeanImpl(String name, String server, ApplicationInfo info) {
      super(name);
      this.serverName = server;
      this.info = info;
   }

   public String getdeploymentDescriptor() {
      return this.info.getDescriptor();
   }

   public String getserver() {
      return this.serverName;
   }

   public String getproductSpecificDeploymentDescriptor() {
      return this.info.getWebLogicDescriptor();
   }
}
