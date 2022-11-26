package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.management.j2ee.J2EEApplicationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class J2EEApplicationMBeanImpl extends J2EEDeployedObjectMBeanImpl implements J2EEApplicationMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String domain;

   public J2EEApplicationMBeanImpl(String name, String serverName, ApplicationInfo info) {
      super(name, serverName, info);
   }

   public String[] getmodules() {
      QueryExp query = new QueryExp() {
         public boolean apply(ObjectName name) {
            return JMOTypesHelper.moduleList.contains(name.getKeyProperty("j2eeType"));
         }

         public void setMBeanServer(MBeanServer server) {
         }
      };
      return this.queryNames(query);
   }

   public String getserver() {
      return this.serverName;
   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
   }
}
