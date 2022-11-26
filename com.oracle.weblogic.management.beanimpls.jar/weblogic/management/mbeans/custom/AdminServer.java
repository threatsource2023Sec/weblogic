package weblogic.management.mbeans.custom;

import java.security.AccessController;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class AdminServer extends ConfigurationMBeanCustomizer {
   RuntimeAccess runtime;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public AdminServer(ConfigurationMBeanCustomized base) {
      super(base);
      this.runtime = ManagementService.getRuntimeAccess(kernelId);
   }

   public String getName() {
      return "Admin Server";
   }

   public DomainMBean getActiveDomain() {
      return this.runtime.getDomain();
   }

   public ServerMBean getServer() {
      return this.runtime.getServer();
   }
}
