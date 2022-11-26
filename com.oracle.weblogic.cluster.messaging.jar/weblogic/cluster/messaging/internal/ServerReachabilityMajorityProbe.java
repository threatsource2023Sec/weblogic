package weblogic.cluster.messaging.internal;

import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class ServerReachabilityMajorityProbe implements Probe {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();

   public void invoke(ProbeContext context) {
      if (DEBUG) {
         debug("check ServerReachabilityMajority");
      }

      ServerReachabilityMajorityService srmService = ServerReachabilityMajorityServiceImpl.getInstance();
      String clusterName = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getName();
      SRMResult result = srmService.performSRMCheck((ServerInformation)null, clusterName);
      if (!result.hasReachabilityMajority()) {
         String reason = "Server is not in the majority cluster partition";
         context.setMessage(reason);
         context.setNextAction(0);
         context.setResult(-1);
      } else {
         context.setNextAction(1);
         context.setResult(1);
      }
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled();
   }

   private static void debug(String s) {
      System.out.println("[ServerReachabilityMajorityProbe] " + s);
   }
}
