package weblogic.deploy.internal.targetserver.state;

import weblogic.application.ModuleListener;
import weblogic.application.ModuleListenerCtx;
import weblogic.management.configuration.SystemResourceMBean;

public class ModuleStateTracker implements ModuleListener {
   private final DeploymentState state;
   private final SystemResourceMBean mbean;

   public ModuleStateTracker(DeploymentState s, SystemResourceMBean mbean) {
      this.state = s;
      this.mbean = mbean;
   }

   public void endTransition(ModuleListenerCtx ctx, ModuleListener.State currentState, ModuleListener.State newState) {
      if (ctx.getApplicationId().equals(this.mbean.getName())) {
         TargetModuleState tms = this.state.getOrCreateTargetModuleState(ctx);
         if (tms != null) {
            tms.setCurrentState(newState.toString());
         }

      }
   }

   public void beginTransition(ModuleListenerCtx ctx, ModuleListener.State currentState, ModuleListener.State newState) {
   }

   public void failedTransition(ModuleListenerCtx ctx, ModuleListener.State currentState, ModuleListener.State newState) {
   }
}
