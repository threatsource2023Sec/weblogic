package weblogic.cluster.messaging.internal;

import weblogic.cluster.ClusterServices.Locator;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingException;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class LeaseTableReachabilityProbe implements Probe {
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();
   private Leasing leasingService = Locator.locate().getSingletonLeasingService();

   public void invoke(ProbeContext context) {
      if (DEBUG) {
         debug(" Checking Lease Table Reachability");
      }

      try {
         String var2 = this.leasingService.findOwner("SINGLETON_MASTER");
      } catch (LeasingException var4) {
         String reason = "Server cannot reach Lease table";
         context.setMessage(reason);
         context.setNextAction(0);
         context.setResult(-1);
         return;
      }

      context.setNextAction(1);
      context.setResult(1);
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled();
   }

   private static void debug(String s) {
      System.out.println("[LeaseTableReachabilityProbe] " + s);
   }
}
