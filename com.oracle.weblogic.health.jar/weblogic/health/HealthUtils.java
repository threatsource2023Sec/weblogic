package weblogic.health;

import java.lang.annotation.Annotation;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.cmm.MemoryNotificationService;

public class HealthUtils {
   private static final long OOME_IMMINENT = 5000000L;
   private static final int FREE_MEM_PERCENT_DIFF_THRESHOLD = 10;
   private static final DebugCategory debugMemory = Debug.getCategory("weblogic.debug.memory");
   private static int previousFreeMemPercent;
   private static boolean sendNotificationOOME = true;

   public static int logAndGetFreeMemoryPercent() {
      long freeMemory = Runtime.getRuntime().freeMemory();
      long totalMemory = Runtime.getRuntime().totalMemory();
      long maxMemory = Runtime.getRuntime().maxMemory();
      int percent = (int)(freeMemory * 100L / totalMemory);
      logDebug(percent, freeMemory, totalMemory, maxMemory);
      if (freeMemory + (maxMemory - totalMemory) < 5000000L) {
         if (sendNotificationOOME) {
            HealthLogger.logOOMEImminent(freeMemory);
            ((MemoryNotificationService)GlobalServiceLocator.getServiceLocator().getService(MemoryNotificationService.class, new Annotation[0])).sendMemoryNotification(previousFreeMemPercent, percent);
            previousFreeMemPercent = percent;
            sendNotificationOOME = false;
         }

         return percent;
      } else {
         int diff = percent - previousFreeMemPercent;
         sendNotificationOOME = true;
         if (Math.abs(diff) > 10) {
            HealthLogger.logFreeMemoryChanged(percent);
            ((MemoryNotificationService)GlobalServiceLocator.getServiceLocator().getService(MemoryNotificationService.class, new Annotation[0])).sendMemoryNotification(previousFreeMemPercent, percent);
            previousFreeMemPercent = percent;
         }

         return percent;
      }
   }

   public static void logDebug(int percent, long freeMemory, long totalMemory, long maxMemory) {
      if (debugMemory.isEnabled()) {
         HealthLogger.logDebugMsg("free mem " + percent + "%, free mem bytes " + freeMemory + ", total mem " + totalMemory + ", max mem " + maxMemory);
      }

   }
}
