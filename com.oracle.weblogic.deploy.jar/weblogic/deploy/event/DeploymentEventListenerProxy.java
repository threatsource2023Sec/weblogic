package weblogic.deploy.event;

import java.util.EventListener;
import java.util.EventListenerProxy;

class DeploymentEventListenerProxy extends EventListenerProxy {
   private String appName;

   DeploymentEventListenerProxy(String appName, EventListener listener) {
      super(listener);
      this.appName = appName;
   }

   public String getAppName() {
      return this.appName;
   }
}
