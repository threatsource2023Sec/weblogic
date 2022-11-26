package weblogic.management;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class PartitionLifeCycleService extends AbstractServerService {
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   @Named("DeploymentServerService")
   private ServerService dependencyOnDeploymentServerService;
   @Inject
   @Named("DomainPartitionService")
   private ServerService dependencyOnPartitionServerService;
   @Inject
   private PartitionLifeCycleHelper helper;
   private static PartitionLifeCycleService singleton;
   private boolean started;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PartitionLifeCycleService() {
      if (singleton != null) {
         throw new AssertionError("PartitionLifeCycleService can be initialized only once");
      } else {
         singleton = this;
      }
   }

   public static final PartitionLifeCycleService getInstance() {
      SecurityHelper.assertIfNotKernel();
      if (singleton == null) {
         throw new AssertionError("PartitionLifeCycleService Not Yet Initialized");
      } else {
         return singleton;
      }
   }

   public final ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes() {
      return this.helper.getResourceGroupLifeCycleRuntimes();
   }

   public final ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String name) {
      return this.helper.lookupResourceGroupLifeCycleRuntime(name);
   }

   public void start() throws ServiceFailureException {
      RuntimeAccess config = ManagementService.getRuntimeAccess(KERNEL_ID);
      DomainAccessSettable domainAccessSettable = (DomainAccessSettable)ManagementService.getDomainAccess(KERNEL_ID);
      DomainAccess domainAccess = ManagementService.getDomainAccess(KERNEL_ID);
      if (config.isAdminServer()) {
         domainAccessSettable.setPartitionLifeCycleService(this);
         DomainMBean domain = config.getDomain();
         DomainRuntimeMBean domainRuntime = domainAccess.getDomainRuntime();
         this.helper.init(domain, domainRuntime);
         this.started = true;
      }

   }

   public static boolean isStarted() {
      return singleton != null ? singleton.started : false;
   }
}
