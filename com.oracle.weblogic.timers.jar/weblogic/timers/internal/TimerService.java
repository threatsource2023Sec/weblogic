package weblogic.timers.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class TimerService extends AbstractServerService {
   private static final int STARTED = 0;
   private static final int STOPPED = 1;
   private static final int HALTED = 2;
   private static TimerService singleton;
   private TimerThread timerThread;
   private boolean initialized;
   private int state = 1;

   public TimerService() {
      Class var1 = TimerService.class;
      synchronized(TimerService.class) {
         if (singleton != null) {
            throw new IllegalStateException();
         } else {
            singleton = this;
         }
      }
   }

   public synchronized void initialize() {
      if (!this.initialized) {
         this.timerThread = TimerThread.getTimerThread();

         try {
            new TimerRuntimeMBeanImpl();
         } catch (ManagementException var2) {
         }

         this.initialized = true;
      }

   }

   public String getVersion() {
      return "Commonj TimerManager v1.1";
   }

   public synchronized void start() throws ServiceFailureException {
      this.initialize();
      this.timerThread.start();
      if (this.state != 0) {
         this.notifyAll();
      }

      this.state = 0;
   }

   public synchronized void stop() throws ServiceFailureException {
      try {
         this.checkInitialized();
      } catch (IllegalStateException var2) {
         throw new ServiceFailureException("Error stopping service", var2);
      }

      this.timerThread.halt();
      this.state = 2;
   }

   public synchronized void halt() throws ServiceFailureException {
      this.stop();
   }

   private void checkInitialized() {
      if (!this.initialized) {
         throw new IllegalStateException("Service is not initialized");
      }
   }
}
