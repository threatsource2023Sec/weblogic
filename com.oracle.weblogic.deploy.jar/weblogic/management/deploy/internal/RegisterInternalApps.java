package weblogic.management.deploy.internal;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.internal.InternalAppProcessor;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.UpdateException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class RegisterInternalApps extends AbstractServerService {
   @Inject
   @Named("DefaultStoreService")
   private ServerService dependencyOnDefaultStoreService;
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;
   @Inject
   @Named("EditSessionConfigurationManagerService")
   private ServerService dependencyOnEditSessionConfigurationManagerService;
   @Inject
   @Named("WebService")
   private ServerService dependencyOnWebService;
   @Inject
   private InternalAppProcessor intProc;

   public void start() throws ServiceFailureException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtime.getDomain();

      try {
         this.intProc.updateConfiguration(domain);
         DeploymentServerService.init();
      } catch (UpdateException var5) {
         throw new ServiceFailureException(var5);
      }
   }
}
