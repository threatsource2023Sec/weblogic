package com.oracle.weblogic.diagnostics.expressions.poller;

import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;

@Service
@PerLookup
public class PollerFactory {
   private static final DiagnosticsFrameworkTextTextFormatter txtFormatter = DiagnosticsFrameworkTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionPoller");
   private Map pollerMap = new HashMap();
   @Inject
   private GlobalPollerCache globalCache;

   public Collection getPollers() {
      ArrayList pollers = new ArrayList(this.pollerMap.values());
      return pollers;
   }

   public Poller findOrCreatePoller(TrackedValueSource spec, int frequency, int maxSamples) {
      if (spec == null) {
         throw new IllegalArgumentException(txtFormatter.getPollerFactoryNullSourceObject());
      } else if (frequency <= 0) {
         throw new IllegalArgumentException(txtFormatter.getPollerFrequencyMustBeGreaterThanZero(frequency));
      } else if (maxSamples <= 0) {
         throw new IllegalArgumentException(txtFormatter.getPollerMaxSamplesMustBeGreaterThanZero(maxSamples));
      } else {
         Poller poller = (Poller)this.pollerMap.get(spec.getKey());
         if (poller == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Obtaining poller from global cache");
            }

            poller = this.globalCache.findOrCreatePoller(spec, frequency, maxSamples);
            if (!poller.isPolling()) {
               poller.startPolling();
            }

            this.pollerMap.put(spec.getKey(), poller);
         }

         return poller;
      }
   }

   public void destroyPoller(Poller poller) {
      if (poller != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("destroyPoller(): " + poller.getKey());
         }

         Poller found = (Poller)this.pollerMap.remove(poller.getKey());
         if (found != null) {
            this.globalCache.destroyPoller(poller);
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("destroyPoller(): Poller not found: " + poller.getKey());
         }

      }
   }

   public void shutdown() {
      for(Iterator it = this.pollerMap.entrySet().iterator(); it.hasNext(); it.remove()) {
         Map.Entry nextEntry = (Map.Entry)it.next();
         Poller poller = (Poller)nextEntry.getValue();
         if (poller != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("shutdown(): removed poller from local map: " + (String)nextEntry.getKey());
            }

            this.globalCache.destroyPoller(poller);
         }
      }

   }
}
