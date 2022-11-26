package weblogic.application.internal.flow;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;

public class ApplicationRuntimeMBeanDeactivationFlow extends BaseFlow {
   public ApplicationRuntimeMBeanDeactivationFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("*** gracefulProductionToAdmin " + this.appCtx.getApplicationId());
      }

      if (ApplicationVersionUtils.getAdminModeAppCtxParam(this.appCtx)) {
         ApplicationVersionUtils.setActiveVersionState(this.appCtx, 1);
      } else {
         if (this.isDebugEnabled()) {
            this.debug("*** gracefulProductionToAdmin " + this.appCtx.getApplicationId() + " does not change active version state");
         }

      }
   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("*** forceProductionToAdmin " + this.appCtx.getApplicationId());
      }

      if (ApplicationVersionUtils.getAdminModeAppCtxParam(this.appCtx)) {
         ApplicationVersionUtils.setActiveVersionState(this.appCtx, 1);
      } else {
         if (this.isDebugEnabled()) {
            this.debug("*** forceProductionToAdmin " + this.appCtx.getApplicationId() + " does not change active version state");
         }

      }
   }

   public void deactivate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("*** deactivate " + this.appCtx.getApplicationId());
      }

      ApplicationVersionUtils.setActiveVersionState(this.appCtx, 0);
   }
}
