package weblogic.application.utils;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public abstract class BaseAppDeploymentExtension implements AppDeploymentExtension {
   public String getName() {
      return this.getClass().getName();
   }

   public void init(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   public void activate(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   public void prepare(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   public void unprepare(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   public void deactivate(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   public void adminToProduction(ApplicationContextInternal appCtx) throws DeploymentException {
   }

   public void forceProductionToAdmin(ApplicationContextInternal appCtx, AdminModeCompletionBarrier barrier) throws DeploymentException {
   }

   public void gracefulProductionToAdmin(ApplicationContextInternal appCtx, AdminModeCompletionBarrier barrier) throws DeploymentException {
   }

   protected void setApplicationModules(ApplicationContextInternal appCtx, Module[] modules) {
      ((FlowContext)appCtx).setApplicationModules(modules);
   }
}
