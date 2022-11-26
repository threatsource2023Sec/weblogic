package weblogic.management.provider.internal.situationalconfig;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.utils.situationalconfig.SituationalConfigFile;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public class SituationalConfigExpirationListener implements TimerListener {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private SituationalConfigManager situationalConfigManager;
   private SituationalConfigFile situationalConfigFile;

   public SituationalConfigExpirationListener(SituationalConfigManager situationalConfigManager, SituationalConfigFile situationalConfigFile) {
      this.situationalConfigManager = situationalConfigManager;
      this.situationalConfigFile = situationalConfigFile;
   }

   public final void timerExpired(Timer timer) {
      try {
         if (debug.isDebugEnabled()) {
            debug.debug("Situational config expired: " + this.situationalConfigFile);
         }

         this.situationalConfigManager.expireSituationalConfig(this.situationalConfigFile);
      } catch (Throwable var6) {
         if (debug.isDebugEnabled()) {
            debug.debug("Exception occurred expiring Situational Config file: " + this.situationalConfigFile.getFile(), var6);
         }
      } finally {
         timer.cancel();
      }

   }
}
