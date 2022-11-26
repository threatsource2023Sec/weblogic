package weblogic.upgrade.processors;

import weblogic.upgrade.UpgradeContext;
import weblogic.upgrade.UpgradeLogger;

public class UpgradeProcessorContext {
   private UpgradeContext ctx;
   private UpgradeLogger logger;

   public UpgradeProcessorContext(UpgradeContext upgradeContext, UpgradeLogger logger) {
      this.ctx = upgradeContext;
      this.logger = logger;
   }

   UpgradeLogger getLogger() {
      return this.logger;
   }
}
