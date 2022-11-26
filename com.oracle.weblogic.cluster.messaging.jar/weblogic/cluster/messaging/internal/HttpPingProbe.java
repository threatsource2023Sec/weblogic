package weblogic.cluster.messaging.internal;

import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class HttpPingProbe implements Probe {
   private static final DebugCategory debugDisconnectMonitor = Debug.getCategory("weblogic.cluster.leasing.DisconnectMonitor");
   private static final boolean DEBUG = debugEnabled();

   public void invoke(ProbeContext context) {
      boolean result = this.doHttpPing(context.getSuspectedMemberInfo());
      if (result) {
         context.setNextAction(0);
         context.setResult(1);
      } else {
         context.setNextAction(1);
         context.setResult(-1);
      }

   }

   protected boolean doHttpPing(SuspectedMemberInfo context) {
      PingRoutine pinger = HttpPingRoutineImpl.getInstance();
      return pinger.ping(context.getServerConfigurationInformation()) > 0L;
   }

   private static boolean debugEnabled() {
      return debugDisconnectMonitor.isEnabled();
   }

   private static void debug(String s) {
      System.out.println("[HttpPingProbe] " + s);
   }
}
