package weblogic.utils;

import java.security.AccessController;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.CapabilitiesService;
import weblogic.descriptor.BootstrapProperties;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.upgrade.ConfigFileHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
public class CapabilitiesServiceImpl implements CapabilitiesService {
   private static final AuthenticatedSubject KERNEL_ID = getKernelId();

   public boolean isProductionMode() {
      if (System.getProperty(BootstrapProperties.PRODUCTION_MODE.getName()) != null) {
         return BootstrapProperties.PRODUCTION_MODE.isEnabled();
      } else if (ManagementService.isRuntimeAccessInitialized()) {
         DomainMBean domainBean = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
         return domainBean.isProductionModeEnabled();
      } else {
         return ConfigFileHelper.getProductionModeEnabled();
      }
   }

   private static AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
