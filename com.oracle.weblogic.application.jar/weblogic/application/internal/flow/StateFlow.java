package weblogic.application.internal.flow;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;

public final class StateFlow extends BaseFlow {
   public StateFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void activate() {
      this.appCtx.setAdminState(true);
   }

   public void deactivate() {
      this.appCtx.setAdminState(false);
   }

   public void adminToProduction() {
      this.appCtx.setAdminState(false);
   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) {
      this.appCtx.setAdminState(true);
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
      this.appCtx.setAdminState(true);
   }
}
