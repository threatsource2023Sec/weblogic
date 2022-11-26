package com.oracle.weblogic.diagnostics.expressions.poller;

import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;

@Service
@Singleton
public class GlobalPollerCache {
   private static final DiagnosticsFrameworkTextTextFormatter txtFormatter = DiagnosticsFrameworkTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionPoller");
   private Map pollerMap = new ConcurrentHashMap();
   @Inject
   private ServiceLocator locator;

   public synchronized Poller findOrCreatePoller(TrackedValueSource spec, int frequency, int size) {
      String key = this.createPollerKey(spec, frequency, size);
      WeakReference ref = (WeakReference)this.pollerMap.get(key);
      PollerImpl poller = ref == null ? null : (PollerImpl)ref.get();
      if (poller == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Global cache, creating new poller object for " + key);
         }

         poller = this.createPollerObject(spec, frequency, size, key);
         ref = new WeakReference(poller);
         this.pollerMap.put(key, ref);
      }

      poller.incrementRefCount();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Global cache, ref count " + poller.refCount() + " for poller object for " + key);
      }

      return poller;
   }

   public void destroyPoller(Poller p) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Global cache, destroying poller " + p.getKey());
      }

      PollerImpl impl = (PollerImpl)p;
      if (impl.decrementRefCount() == 0) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Poller " + impl.getKey() + " hit zero ref count, stopping and removing from cache");
         }

         this.pollerMap.remove(impl.getKey());
         this.locator.preDestroy(impl);
         impl.stopPolling();
         impl.clear();
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Ref count for poller [" + impl.getKey() + "]: " + impl.refCount());
      }

   }

   public Poller createPollerObject(TrackedValueSource spec, int frequency, int maxSamples) {
      String key = this.createPollerKey(spec, frequency, maxSamples);
      return this.createPollerObject(spec, frequency, maxSamples, key);
   }

   public int getNumActivePollers() {
      return this.pollerMap.size();
   }

   public Set getPollerKeys() {
      return new HashSet(this.pollerMap.keySet());
   }

   private PollerImpl createPollerObject(TrackedValueSource spec, int frequency, int size, String key) {
      PollerImpl poller = new PollerImpl(key, spec, frequency, size);
      this.locator.inject(poller);
      this.locator.postConstruct(poller);
      return poller;
   }

   private String createPollerKey(TrackedValueSource spec, int frequency, int size) {
      if (spec == null) {
         throw new IllegalArgumentException(txtFormatter.getNullPolledObject());
      } else {
         return spec.getKey() + "//" + Integer.toString(frequency) + "//" + Integer.toString(size);
      }
   }
}
