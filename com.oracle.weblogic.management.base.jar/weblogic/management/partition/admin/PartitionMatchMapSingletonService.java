package weblogic.management.partition.admin;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public final class PartitionMatchMapSingletonService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   @Named("RuntimeAccessService")
   private ServerService dependencyOnRuntimeAccessService;
   @Inject
   @Named("BootService")
   private ServerService dependencyOnBootService;
   static final boolean USE_MATCH_MAP_SINGLETON = Boolean.parseBoolean(System.getProperty("partitiontable.useMatchMapSingleton", "true"));

   public synchronized void start() throws ServiceFailureException {
      RuntimeAccess config = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domainBean = config.getDomain();
      if (USE_MATCH_MAP_SINGLETON) {
         PartitionMatchMapSingleton.getInstance();
         PartitionMatchMapSingleton.createMatchMap(domainBean);
         domainBean.getDescriptor().addUpdateListener(new PartitionMatchMapSingletonDescriptorUpdateListener());
      }

   }

   public synchronized void halt() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
   }
}
