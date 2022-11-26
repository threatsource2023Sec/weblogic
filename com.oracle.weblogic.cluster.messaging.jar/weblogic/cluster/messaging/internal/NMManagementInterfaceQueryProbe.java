package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.cluster.singleton.MemberDeathDetector.ServerMigrationStateValidator;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class NMManagementInterfaceQueryProbe implements Probe {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void invoke(ProbeContext context) {
      if (ManagementService.getRuntimeAccess(kernelId).getDomain().isExalogicOptimizationsEnabled()) {
         debug("probe server: " + context.getSuspectedMemberInfo().getServerName());
         NetworkAccessPointMBean managementNAP = null;
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(context.getSuspectedMemberInfo().getServerName());
         NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();

         for(int i = 0; i < naps.length; ++i) {
            if ("management-interface".equals(naps[i].getName())) {
               managementNAP = naps[i];
               break;
            }
         }

         if (managementNAP != null) {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nmRuntime = generator.getInstance(managementNAP.getListenAddress(), server.getMachine().getNodeManager().getListenPort(), "ssl");
            String state = null;

            try {
               state = nmRuntime.getState(server);
            } catch (IOException var9) {
               context.setNextAction(1);
               context.setResult(-1);
               return;
            }

            debug("runtime state: " + state + " for server: " + server.getName());
            if (ServerMigrationStateValidator.canMigrateServer(state) && ServerMigrationStateValidator.canMigrateLease(state)) {
               debug(server + " is marked as " + state + ". Voiding all its leases");
               context.setNextAction(0);
               context.setResult(-1);
            } else {
               context.setNextAction(0);
               context.setResult(1);
            }
         }
      }
   }

   private static void debug(String s) {
      System.out.println("[NMManagementInterfaceQueryProbe] " + s);
   }
}
