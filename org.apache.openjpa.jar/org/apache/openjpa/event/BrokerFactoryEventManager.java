package org.apache.openjpa.event;

import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.AbstractConcurrentEventManager;

public class BrokerFactoryEventManager extends AbstractConcurrentEventManager {
   private static final Localizer _loc = Localizer.forPackage(BrokerFactoryEventManager.class);
   private final Configuration _conf;

   public BrokerFactoryEventManager(Configuration conf) {
      this._conf = conf;
   }

   protected void fireEvent(Object event, Object listener) {
      try {
         BrokerFactoryEvent e = (BrokerFactoryEvent)event;
         ((BrokerFactoryListener)listener).eventFired(e);
      } catch (Exception var4) {
         this._conf.getLog("openjpa.Runtime").warn(_loc.get("broker-factory-listener-exception"), var4);
      }

   }
}
