package weblogic.diagnostics.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.logging.Loggable;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public final class IteratorCollector implements TimerListener {
   private static final String TIMER_MANAGER = "IteratorCollectorTimerManager";
   private static final long TIMER_INTERVAL = 300000L;
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticCollections");
   private static IteratorCollector singleton = null;
   private boolean initialized;
   private Map registry;

   public static IteratorCollector getInstance() {
      return IteratorCollector.SINGLETON_WRAPPER.SINGLETON;
   }

   private IteratorCollector() {
      this.initialized = false;
      this.registry = new HashMap();
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Creating singleton");
      }

   }

   public synchronized void initialize() {
      if (!this.initialized) {
         TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
         TimerManager tm = tmf.getTimerManager("IteratorCollectorTimerManager");
         tm.scheduleAtFixedRate(this, 0L, 300000L);
         this.initialized = true;
      }

   }

   public synchronized void timerExpired(Timer timer) {
      long currentTime = System.currentTimeMillis();
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("The timer has gone off at " + currentTime);
      }

      Iterator keys = this.registry.keySet().iterator();

      while(keys.hasNext()) {
         String key = (String)keys.next();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Found iterator with name " + key);
         }

         TimedIterator ti = (TimedIterator)this.registry.get(key);
         if (ti.hasTimedout(currentTime)) {
            keys.remove();
            DiagnosticsLogger.logRemovingCursorHandler(key);
         }
      }

   }

   public synchronized Iterator getIterator(String key) throws IteratorNotFoundException {
      if (!this.registry.containsKey(key)) {
         Loggable l = DiagnosticsLogger.logCursorNotFoundLoggable(key);
         throw new IteratorNotFoundException(l.getMessage());
      } else {
         return (Iterator)this.registry.get(key);
      }
   }

   public synchronized void registerIterator(String key, Iterator iter, long timeout) throws IteratorAlreadyExistsException {
      if (this.registry.containsKey(key)) {
         throw new IteratorAlreadyExistsException("Iterator with name " + key + " already exists");
      } else {
         this.registry.put(key, new TimedIterator(iter, timeout));
      }
   }

   public synchronized void deregisterIterator(String key) {
      this.registry.remove(key);
   }

   // $FF: synthetic method
   IteratorCollector(Object x0) {
      this();
   }

   private static class SINGLETON_WRAPPER {
      private static final IteratorCollector SINGLETON = new IteratorCollector();
   }
}
