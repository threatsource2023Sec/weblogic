package weblogic.server;

import java.security.AccessController;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.PartitionRuntimeMBeanImpl;

@Service
@Named
@RunLevel(5)
public final class PartitionRuntimeBuilderService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   final boolean isDeploymentStartingAll = false;
   @Inject
   @Named("RuntimeAccessService")
   private ServerService dependencyOnRuntimeAccessService;
   @Inject
   @Named("BootService")
   private ServerService dependencyOnBootService;

   public synchronized void start() throws ServiceFailureException {
   }

   void createBeanIfTargeted(PartitionMBean partition, String serverName) throws ManagementException {
      Collection targets = this.collectTargets(partition);
      Iterator var4 = targets.iterator();

      Set serverNames;
      do {
         if (!var4.hasNext()) {
            return;
         }

         TargetMBean target = (TargetMBean)var4.next();
         serverNames = target.getServerNames();
      } while(!serverNames.contains(serverName));

      PartitionRuntimeMBean prbean = new PartitionRuntimeMBeanImpl(partition.getName(), partition.getPartitionID());
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      runtime.getServerRuntime().addPartitionRuntime(prbean);
      prbean.setState(State.SHUTDOWN);
   }

   private Set collectTargets(PartitionMBean partition) {
      Set result = new HashSet();
      return result;
   }

   public synchronized void halt() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
   }
}
