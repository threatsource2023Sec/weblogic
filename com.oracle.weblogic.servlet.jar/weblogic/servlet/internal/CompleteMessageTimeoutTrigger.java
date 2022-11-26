package weblogic.servlet.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.kernel.Kernel;
import weblogic.servlet.HTTPLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class CompleteMessageTimeoutTrigger implements TimerListener {
   private static final int CLEANUP_TRIGGER_TIMEPERIOD_LOW = 2000;
   private static final int CLEANUP_TRIGGER_TIMEPERIOD_HIGH = 30000;
   private ConcurrentHashMap sockets;
   private final long writeTimeoutInterval = (long)(1000 * Kernel.getConfig().getCompleteWriteTimeout());
   private final boolean isEnabled;

   public CompleteMessageTimeoutTrigger() {
      this.isEnabled = this.writeTimeoutInterval > 0L;
      if (this.isEnabled) {
         this.sockets = new ConcurrentHashMap(1024);
         long triggerInterval = this.writeTimeoutInterval / 10L;
         if (triggerInterval < 2000L) {
            triggerInterval = 2000L;
         } else if (triggerInterval > 30000L) {
            triggerInterval = 30000L;
         }

         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logDebug("Setting the writetimeout interval to " + this.writeTimeoutInterval);
         }

         TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager(CompleteMessageTimeoutTrigger.class.getName(), WorkManagerFactory.getInstance().getSystem());
         timerManager.schedule(this, 0L, triggerInterval);
      }

   }

   void register(OutputStream sos) {
      if (this.isEnabled) {
         this.sockets.put(sos, new Long(System.currentTimeMillis()));
      }

   }

   void unregister(OutputStream sos) {
      if (this.isEnabled) {
         this.sockets.remove(sos);
      }

   }

   public void timerExpired(Timer timer) {
      long checkPoint = System.currentTimeMillis();
      Iterator it = this.sockets.keySet().iterator();

      while(it.hasNext()) {
         OutputStream os = (OutputStream)it.next();
         Long ts = (Long)this.sockets.get(os);
         if (ts != null && checkPoint - ts > this.writeTimeoutInterval && this.sockets.remove(os, ts)) {
            try {
               os.close();
            } catch (IOException var8) {
            }

            HTTPLogger.logClosingTimeoutSocket();
         }
      }

   }
}
