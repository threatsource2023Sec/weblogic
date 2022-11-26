package weblogic.application.internal.flow;

import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public final class TailLifecycleFlow extends BaseLifecycleFlow {
   public TailLifecycleFlow(FlowContext appCtx) {
      super(appCtx);
   }

   public void activate() throws DeploymentException {
      this.postStart.invoke();
   }

   public void deactivate() throws DeploymentException {
      this.preStop.invoke();
   }
}
