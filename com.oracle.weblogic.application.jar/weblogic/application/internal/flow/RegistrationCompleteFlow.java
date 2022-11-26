package weblogic.application.internal.flow;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;
import weblogic.management.DeploymentException;

public final class RegistrationCompleteFlow extends BaseFlow {
   public RegistrationCompleteFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      barrier.registrationComplete();
   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      barrier.registrationComplete();
   }
}
