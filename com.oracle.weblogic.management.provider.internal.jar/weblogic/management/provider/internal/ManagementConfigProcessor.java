package weblogic.management.provider.internal;

import java.security.AccessController;
import weblogic.version;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ManagementConfigProcessor implements ConfigurationProcessor {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void updateConfiguration(DomainMBean domain) {
      String currentVersion = version.getReleaseBuildVersion();
      domain.setConfigurationVersion(currentVersion);
      ServerMBean[] servers = domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         servers[i].unSet("ServerVersion");
      }

      if (ManagementService.getPropertyService(kernelId).isAdminServer()) {
         domain.setAdminServerName(ManagementService.getPropertyService(kernelId).getServerName());
      }
   }
}
