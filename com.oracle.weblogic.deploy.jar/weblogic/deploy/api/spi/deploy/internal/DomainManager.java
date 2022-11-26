package weblogic.deploy.api.spi.deploy.internal;

import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.deploy.ServerConnection;
import weblogic.management.configuration.DomainMBean;

public class DomainManager {
   private ServerConnection sc;
   private DomainMBean domain = null;
   private boolean isPendingDomain;
   private static final boolean debug = Debug.isDebug("deploy");

   public DomainManager(ServerConnection sc) {
      this.sc = sc;
   }

   public DomainMBean getDomain() {
      if (this.domain == null || this.isPendingDomain != this.isInEditSession()) {
         this.resetDomain();
      }

      return this.domain;
   }

   private boolean isInEditSession() {
      return this.sc.getHelper().isEditing();
   }

   private void resetDomain() {
      if (debug) {
         Debug.say("Getting new domain");
      }

      this.domain = this.sc.getHelper().getDomain();
      this.isPendingDomain = this.isInEditSession();
      if (debug) {
         Debug.say("Using pending domain: " + this.isPendingDomain);
      }

      this.sc.resetDomain(this.domain);
   }

   public void close() {
      this.sc = null;
      this.domain = null;
   }
}
