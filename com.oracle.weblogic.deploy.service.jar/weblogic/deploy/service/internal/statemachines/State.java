package weblogic.deploy.service.internal.statemachines;

import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.service.internal.transport.CommonMessageSender;

public class State {
   protected final CommonMessageSender sender = CommonMessageSender.getInstance();
   private static final String SYS_PROP_CONCURRENT_APP_PREPARE_ENABLED = "weblogic.deploy.internal.CrossPartitionConcurrentAppPrepareEnabled";
   private static final String DEFAULT_CONCURRENT_APP_PREPARE_ENABLED = "false";

   public final boolean equals(Object inObj) {
      if (this == inObj) {
         return true;
      } else if (inObj instanceof State) {
         State state = (State)inObj;
         return this.toString().equals(state.toString());
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.toString().hashCode();
   }

   protected final void debug(String message) {
      Debug.serviceDebug(message);
   }

   protected final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   protected void fireStateTransitionEvent(State state, String method, long id) {
      if (this.isDebugEnabled()) {
         this.debug("DeploymentService event : '" + state.toString() + "." + method + "()' for deployment id '" + id + "'");
      }

   }

   protected boolean concurrentAppPrepareEnabled(DeploymentRequest request) {
      return request.concurrentAppPrepareEnabled();
   }
}
