package weblogic.messaging.saf.internal;

import weblogic.messaging.common.ThresholdHandler;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.Threshold;
import weblogic.messaging.saf.SAFLogger;

public final class SAFThresholdHandler extends ThresholdHandler {
   public SAFThresholdHandler(Kernel target, String agentName) {
      super(target, agentName);
   }

   public synchronized void onThreshold(Threshold threshold, boolean armed) {
      if (armed) {
         ++this.count;
         if (threshold == this.bytes) {
            SAFLogger.logBytesThresholdHighAgent(this.targetName);
         } else {
            SAFLogger.logMessagesThresholdHighAgent(this.targetName);
         }
      } else {
         --this.count;
         if (threshold == this.bytes) {
            SAFLogger.logBytesThresholdLowAgent(this.targetName);
         } else {
            SAFLogger.logMessagesThresholdLowAgent(this.targetName);
         }
      }

   }
}
