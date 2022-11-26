package weblogic.utils.cmm.serverservice;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.AbstractServerService;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.cmm.Scrubber;

@Service
@Named
@RunLevel(15)
public class ScrubberStartService extends AbstractServerService {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugScrubberStartService");
   private static final long DEFAULT_INTERVAL = 300000L;
   private static final long INTERVAL = Long.parseLong(System.getProperty("com.oracle.weblogic.scrubber.milliseconds", "300000"));
   @Inject
   private ServiceLocator locator;
   private Timer timer;

   public synchronized void start() {
      TimerManager manager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      this.timer = manager.schedule(new ScrubWorker(this.locator), new Date(), INTERVAL);
   }

   private synchronized void shutdownTimer() {
      if (this.timer != null) {
         this.timer.cancel();
         this.timer = null;
      }
   }

   public void stop() {
      this.shutdownTimer();
   }

   public void halt() {
      this.shutdownTimer();
   }

   private static class ScrubWorker implements TimerListener {
      private final ServiceLocator locator;

      private ScrubWorker(ServiceLocator locator) {
         this.locator = locator;
      }

      public void timerExpired(Timer timer) {
         List scrubbers = this.locator.getAllServices(Scrubber.class, new Annotation[0]);
         ScrubberStartService.debugLogger.debug("Scrubber timer has gone off, scrubbing " + scrubbers.size() + " subsystems");
         Iterator var3 = scrubbers.iterator();

         while(var3.hasNext()) {
            Scrubber scrubber = (Scrubber)var3.next();

            try {
               scrubber.scrubADubDub();
            } catch (Throwable var6) {
               ScrubberStartService.debugLogger.debug("A scrubber failed " + scrubber, var6);
            }
         }

      }

      // $FF: synthetic method
      ScrubWorker(ServiceLocator x0, Object x1) {
         this(x0);
      }
   }
}
