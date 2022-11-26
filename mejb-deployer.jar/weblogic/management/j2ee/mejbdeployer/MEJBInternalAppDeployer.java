package weblogic.management.j2ee.mejbdeployer;

import java.security.AccessController;
import java.util.Collections;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.spi.deploy.internal.InternalApp;
import weblogic.deploy.api.spi.deploy.internal.InternalAppFactoryExt;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
public class MEJBInternalAppDeployer implements InternalAppFactoryExt {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public List createInternalApps() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNEL_ID);
      return ra.getDomain().getJMX().isManagementEJBEnabled() && ra.getServerRuntime().isServiceAvailable("EJB") ? Collections.singletonList(new InternalApp("mejb", ".jar", true, false, true)) : Collections.emptyList();
   }

   public boolean requiresRuntimes() {
      return true;
   }
}
