package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.coherence.servlet.SplitHttpSessionModel;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.internal.RemoveEntryProcessor;
import com.tangosol.net.internal.SessionExpiryExtractor;
import com.tangosol.util.Filter;
import com.tangosol.util.filter.InFilter;
import com.tangosol.util.filter.LessFilter;
import java.util.Map;
import java.util.Set;
import weblogic.coherence.service.internal.coherenceLogger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class CoherenceWebReapingManager {
   private static final String WEBLOGIC_COHERENCE_WEB_REAP_INTERVAL = "weblogic.coherence.web.reap.interval";
   private static final String WEBLOGIC_COHERENCE_WEB_REAP_ELAPSEDTIME = "weblogic.coherence.web.reap.elapsedtime";
   private static final String WORK_NAME = "CoherenceWebServerSessionReaper";
   private final Long reapInterval = Long.getLong("weblogic.coherence.web.reap.interval", 1800L) * 1000L;
   private final long timeoutElapsedTime = Long.getLong("weblogic.coherence.web.reap.elapsedtime", 1800L) * 1000L;
   private static final CoherenceWebReapingManager singleton = new CoherenceWebReapingManager();
   private TimerManager timerManager;
   public static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugCWebGlobalReaper");

   private CoherenceWebReapingManager() {
   }

   public static CoherenceWebReapingManager getInstance() {
      return singleton;
   }

   public synchronized void ensureReapTimer() {
      if (this.timerManager == null) {
         debug("Instantiating Global C*Web Session Reaper");
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("CoherenceWebServerSessionReaper", WorkManagerFactory.getInstance().getSystem());
         SessionInvalidator invalidator = new SessionInvalidator();

         try {
            this.timerManager.schedule(invalidator, this.reapInterval, this.reapInterval);
         } catch (Exception var3) {
            coherenceLogger.logMessageErrorCreatingTimerManager(var3);
            this.timerManager = null;
         }

      }
   }

   public synchronized void stopReapTimer() {
      if (this.timerManager != null) {
         this.timerManager.stop();
      }
   }

   public static boolean isDebugEnabled() {
      return DEBUG_LOGGER.isDebugEnabled();
   }

   public static void debug(String msg) {
      if (isDebugEnabled()) {
         DEBUG_LOGGER.debug(msg);
      }

   }

   public static void debug(String msg, Throwable t) {
      if (isDebugEnabled()) {
         DEBUG_LOGGER.debug(msg, t);
      }

   }

   private class SessionInvalidator implements NakedTimerListener {
      private NamedCache sessionCache = null;
      private NamedCache overFlowCache = null;

      public SessionInvalidator() {
      }

      public void timerExpired(Timer timer) {
         try {
            this.init();
            CoherenceWebReapingManager.debug("Global Reaper cycle start");
            Filter filter = new LessFilter(new SessionExpiryExtractor(), System.currentTimeMillis() - CoherenceWebReapingManager.this.timeoutElapsedTime);
            Map sessionsMap = this.sessionCache.invokeAll(filter, new RemoveEntryProcessor(true));
            Set deletedSessions = sessionsMap != null ? sessionsMap.keySet() : null;
            if (deletedSessions != null && deletedSessions.size() > 0) {
               this.overFlowCache.invokeAll(new InFilter(SplitHttpSessionModel.SESSION_ID_EXTRACTOR, deletedSessions), new RemoveEntryProcessor(true));
               CoherenceWebReapingManager.debug("Number of session cleaned up by Global Session Reaper is " + deletedSessions.size());
            }

            CoherenceWebReapingManager.debug("Global Reaper cycle end");
         } catch (Exception var5) {
            coherenceLogger.logMessageErrorDuringReapingProcess(var5);
         }

      }

      public void init() {
         if (this.sessionCache == null || !this.sessionCache.isActive() || this.overFlowCache == null || !this.overFlowCache.isActive()) {
            CacheFactoryBuilder cfb = CacheFactory.getCacheFactoryBuilder();
            ClassLoader loader = SessionHelper.class.getClassLoader();
            ConfigurableCacheFactory factory = cfb.getConfigurableCacheFactory("default-session-cache-config.xml", loader);
            this.sessionCache = this.sessionCache != null && this.sessionCache.isActive() ? this.sessionCache : factory.ensureCache("session-storage", loader);
            this.overFlowCache = this.overFlowCache != null && this.overFlowCache.isActive() ? this.overFlowCache : factory.ensureCache("session-overflow", loader);
         }
      }
   }
}
