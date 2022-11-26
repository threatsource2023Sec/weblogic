package org.apache.openjpa.event;

import java.util.Arrays;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public abstract class AbstractRemoteCommitProvider implements RemoteCommitProvider, Configurable {
   private static final Localizer _loc = Localizer.forPackage(AbstractRemoteCommitProvider.class);
   protected RemoteCommitEventManager eventManager;
   protected Log log;

   public void setConfiguration(Configuration config) {
      this.log = config.getLog("openjpa.Runtime");
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setRemoteCommitEventManager(RemoteCommitEventManager mgr) {
      this.eventManager = mgr;
   }

   protected void fireEvent(RemoteCommitEvent event) {
      Exception[] es = this.eventManager.fireEvent(event);
      if (es.length > 0 && this.log.isWarnEnabled()) {
         this.log.warn(_loc.get("remote-listener-ex", (Object)Arrays.asList(es)));
      }

      if (this.log.isTraceEnabled()) {
         for(int i = 0; i < es.length; ++i) {
            this.log.trace(es[i]);
         }
      }

   }
}
