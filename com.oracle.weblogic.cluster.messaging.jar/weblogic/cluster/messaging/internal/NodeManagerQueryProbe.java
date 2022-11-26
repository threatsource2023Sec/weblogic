package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingFactory;
import weblogic.cluster.singleton.Leasing.LeaseOwnerIdentity;
import weblogic.cluster.singleton.LeasingFactory.Locator;
import weblogic.cluster.singleton.MemberDeathDetector.ServerMigrationStateValidator;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class NodeManagerQueryProbe implements Probe {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();

   public void invoke(ProbeContext context) {
      SuspectedMemberInfo member = context.getSuspectedMemberInfo();
      if (DEBUG) {
         debug("NodeManagerQueryProbe of server: " + member.getServerName());
      }

      NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(context.getSuspectedMemberInfo().getServerName());
      NodeManagerLifecycleService nmRuntime = generator.getInstance(server);
      String state = "UNKNOWN";

      try {
         state = nmRuntime.getState(server);
      } catch (IOException var9) {
         context.setNextAction(1);
         context.setResult(-1);
         return;
      }

      if (DEBUG) {
         debug("NodeManagerQueryProbe runtime state: " + state + " for server: " + server.getName());
      }

      if (!member.hasVoidedSingletonServices() && ServerMigrationStateValidator.canMigrateLease(state)) {
         Leasing singletonServicesLeasingService = this.findOrCreateLeasingService("service");
         String ownerIdentity = LeaseOwnerIdentity.getIdentity(member.getServerIdentity());
         singletonServicesLeasingService.voidLeases(ownerIdentity);
         if (DEBUG) {
            debug("NodeManagerQueryProbe " + ownerIdentity + " is marked as " + state + ". Voiding all its singleton services leases");
         }

         member.voidedSingletonServices();
      }

      if (ServerMigrationStateValidator.canMigrateServer(state)) {
         if (DEBUG) {
            debug("NodeManagerQueryProbe " + server + " is marked as " + state + ". Voiding all its leases");
         }

         context.setNextAction(0);
         context.setResult(-1);
      } else {
         context.setNextAction(0);
         context.setResult(0);
      }
   }

   private static void debug(String s) {
      System.out.println("[NodeManagerQueryProbe] " + s);
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled();
   }

   private Leasing findOrCreateLeasingService(String leasingServiceName) {
      LeasingFactory factory = Locator.locateService();
      return factory.findOrCreateLeasingService(leasingServiceName);
   }
}
