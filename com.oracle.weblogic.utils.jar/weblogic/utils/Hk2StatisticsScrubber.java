package weblogic.utils;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.external.runtime.ServiceLocatorRuntimeBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.debug.InstanceGatherer;
import weblogic.utils.cmm.Scrubber;

@Service
public class Hk2StatisticsScrubber implements Scrubber {
   private static final String CONTINUOUS_PRINT_KEY = "weblogic.hk2.instance.counter.continuous";
   private static final boolean CONTINUOUS_PRINT = getContinuousPrint();
   private static final String DEBUG_FILE = "Hk2Stat.txt";
   private static final DebugLogger DEBUG_HK2 = DebugLogger.getDebugLogger("DebugHk2Statistics");
   @Inject
   @Optional
   private InstanceGatherer gatherer;
   @Inject
   private ServiceLocatorRuntimeBean runtime;
   private long lastTimeStamp = 0L;

   public void scrubADubDub() {
      if (this.gatherer != null) {
         if (!CONTINUOUS_PRINT) {
            File statFile = new File("Hk2Stat.txt");
            if (!statFile.exists() || !statFile.canRead()) {
               return;
            }

            long lastModified = statFile.lastModified();
            if (lastModified <= this.lastTimeStamp) {
               return;
            }

            this.lastTimeStamp = lastModified;
         }

         DEBUG_HK2.debug(InstanceGatherer.allInstanceSummary());
      }
   }

   private static boolean getContinuousPrint() {
      try {
         return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
            public Boolean run() {
               return Boolean.getBoolean("weblogic.hk2.instance.counter.continuous");
            }
         });
      } catch (Throwable var1) {
         return false;
      }
   }
}
