package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.management.DeploymentException;

public class TailFreeMemoryFlow extends BaseFlow {
   public TailFreeMemoryFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void activate() throws DeploymentException {
      try {
         if (this.isDebugEnabled()) {
            this.debug("About to free deployment memory");
         }

         this.appCtx.freeDeploymentMemory();
         if (this.isDebugEnabled()) {
            this.debug("Freed deployment memory");
         }
      } catch (Throwable var2) {
         if (this.isDebugEnabled()) {
            this.debug("Unable to free deployment memory", var2);
         }
      }

   }
}
