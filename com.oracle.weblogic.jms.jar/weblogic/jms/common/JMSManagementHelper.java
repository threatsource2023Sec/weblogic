package weblogic.jms.common;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class JMSManagementHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DomainMBean domainMBean;
   private ServerMBean serverMBean;
   private static JMSManagementHelper helper;

   public JMSManagementHelper() throws ManagementException {
      this.serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      this.domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   public static JMSManagementHelper getHelper() {
      return helper;
   }

   public ServerMBean getServerMBean() {
      return this.serverMBean;
   }

   public String getServerName() {
      return this.serverMBean == null ? null : this.serverMBean.getName();
   }

   public String getClusterName() {
      return this.serverMBean == null ? null : (this.serverMBean.getCluster() == null ? null : this.serverMBean.getCluster().getName());
   }

   public String getDomainName() {
      return this.domainMBean == null ? null : this.domainMBean.getName();
   }

   static {
      try {
         helper = new JMSManagementHelper();
      } catch (ManagementException var1) {
      }

   }
}
