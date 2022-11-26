package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;

public final class ApplicationRuntimeMBeanSetupFlow extends BaseFlow {
   public ApplicationRuntimeMBeanSetupFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void activate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("*** activate " + this.appCtx.getApplicationId());
      }

      if (ApplicationVersionUtils.getAdminModeAppCtxParam(this.appCtx)) {
         ApplicationVersionUtils.setActiveVersionState(this.appCtx, 1);
      } else {
         if (this.isDebugEnabled()) {
            this.debug("*** activate " + this.appCtx.getApplicationId() + " does not change active version state");
         }

      }
   }

   public void adminToProduction() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("*** adminToProduction " + this.appCtx.getApplicationId());
      }

      ApplicationVersionUtils.setActiveVersionState(this.appCtx, 2);
   }
}
