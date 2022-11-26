package weblogic.platform;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class GCMonitorThread extends Thread {
   private static final DebugCategory debugMemory = Debug.getCategory("weblogic.debug.memory");
   private static final long MINIMUM_PERIOD_BETWEEN_REGISTERS = 2000L;
   private static final long TIMEOUT = 60000L;
   private SoftReference sref;
   private ReferenceQueue rq = new ReferenceQueue();
   private boolean continueMonitoring = true;
   private long lastRegisterTime;
   private static boolean started;

   public static synchronized void init() {
      if (!started) {
         Thread th = new GCMonitorThread();
         th.setDaemon(true);
         th.start();
         started = true;
      }
   }

   private GCMonitorThread() {
      super("weblogic.GCMonitor");
   }

   public synchronized void stopMonitoring() {
      this.continueMonitoring = false;
   }

   public void run() {
      while(true) {
         try {
            if (this.continueMonitoring) {
               this.register();
               this.waitForNotification();
               continue;
            }
         } catch (InterruptedException var2) {
            var2.printStackTrace();
         }

         return;
      }
   }

   private void register() throws InterruptedException {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastRegisterTime < 2000L) {
         Thread.sleep(2000L);
      }

      this.sref = new SoftReference(new Object(), this.rq);
      this.lastRegisterTime = currentTime;
   }

   private void waitForNotification() throws InterruptedException {
      while(this.continueMonitoring) {
         Object obj = this.rq.remove(60000L);
         if (obj != null) {
            VM.getVM().sendMajorGCEvent();
            return;
         }

         VM.getVM().sendMinorGCEvent();
      }

   }
}
