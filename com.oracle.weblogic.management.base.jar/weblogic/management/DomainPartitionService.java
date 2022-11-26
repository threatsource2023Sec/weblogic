package weblogic.management;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class DomainPartitionService extends AbstractServerService {
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   private DomainPartitionHelper helper;
   private static DomainPartitionService singleton;
   private boolean started;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DomainPartitionService() {
      if (singleton != null) {
         throw new AssertionError("DomainPartitionService can be initialized  only once.");
      } else {
         singleton = this;
      }
   }

   public static final DomainPartitionService getInstance() {
      SecurityHelper.assertIfNotKernel();
      if (singleton == null) {
         throw new AssertionError("DomainPartitionService not initialized yet.");
      } else {
         return singleton;
      }
   }

   public final DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes() {
      return this.helper.getDomainPartitionRuntimes();
   }

   public final DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String name) {
      return this.helper.lookupDomainPartitionRuntime(name);
   }

   public void start() throws ServiceFailureException {
      RuntimeAccess config = ManagementService.getRuntimeAccess(KERNEL_ID);
      DomainAccessSettable domainAccessSettable = (DomainAccessSettable)ManagementService.getDomainAccess(KERNEL_ID);
      if (config.isAdminServer()) {
         domainAccessSettable.setDomainPartitionService(this);
         DomainMBean domain = config.getDomain();
         this.helper.init(domain);
         this.started = true;
      }

   }

   public static boolean isStarted() {
      return singleton != null ? singleton.started : false;
   }
}
