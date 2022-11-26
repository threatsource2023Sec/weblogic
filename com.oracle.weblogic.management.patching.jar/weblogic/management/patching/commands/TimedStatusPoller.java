package weblogic.management.patching.commands;

import weblogic.management.patching.PatchingDebugLogger;

public class TimedStatusPoller {
   public boolean pollStatusWithTimeout(long timeout, long pollInterval, StatusPoller statusPoller) {
      long pollBegin = System.currentTimeMillis();
      long endTime = System.currentTimeMillis() + timeout;
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Waiting for " + statusPoller.getPollingDescription() + " started at " + pollBegin + " waiting for " + timeout);
      }

      while(!statusPoller.checkStatus()) {
         long remainingWaitTime = endTime - System.currentTimeMillis();
         if (remainingWaitTime < 0L) {
            return true;
         }

         if (pollInterval > remainingWaitTime && remainingWaitTime > 0L) {
            pollInterval = remainingWaitTime;
         }

         try {
            Thread.sleep(pollInterval);
         } catch (InterruptedException var13) {
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("After trying poll and sleeping for " + pollInterval + " the remainingWaitTime is: " + remainingWaitTime + " from start: " + pollBegin + " and timeout: " + timeout + " and now: " + System.currentTimeMillis());
         }
      }

      return false;
   }
}
