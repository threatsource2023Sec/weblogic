package weblogic.jms.backend;

import weblogic.jms.JMSLogger;
import weblogic.messaging.common.ThresholdHandler;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.Threshold;

final class BEThresholdHandler extends ThresholdHandler {
   private final boolean isDestination;
   private String destName;

   BEThresholdHandler(String backEndName, String destName) {
      super(backEndName);
      this.isDestination = true;
      this.destName = destName;
   }

   BEThresholdHandler(Kernel kernel, String backEndName) {
      super(kernel, backEndName);
      this.isDestination = false;
   }

   void setTarget(Destination target) {
      this.statistics = target.getStatistics();
      this.replaceBytesThreshold();
      this.replaceMessagesThreshold();
   }

   public synchronized void onThreshold(Threshold threshold, boolean armed) {
      if (armed) {
         ++this.count;
         if (threshold == this.bytes) {
            if (this.isDestination) {
               JMSLogger.logBytesThresholdHighDestination(this.targetName, this.destName);
            } else {
               JMSLogger.logBytesThresholdHighServer(this.targetName);
            }
         } else if (this.isDestination) {
            JMSLogger.logMessagesThresholdHighDestination(this.targetName, this.destName);
         } else {
            JMSLogger.logMessagesThresholdHighServer(this.targetName);
         }
      } else {
         --this.count;
         if (threshold == this.bytes) {
            if (this.isDestination) {
               JMSLogger.logBytesThresholdLowDestination(this.targetName, this.destName);
            } else {
               JMSLogger.logBytesThresholdLowServer(this.targetName);
            }
         } else if (this.isDestination) {
            JMSLogger.logMessagesThresholdLowDestination(this.targetName, this.destName);
         } else {
            JMSLogger.logMessagesThresholdLowServer(this.targetName);
         }
      }

   }
}
