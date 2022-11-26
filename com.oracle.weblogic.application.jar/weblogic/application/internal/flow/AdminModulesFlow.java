package weblogic.application.internal.flow;

import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;
import weblogic.utils.ErrorCollectionException;

public final class AdminModulesFlow extends BaseFlow {
   private ModuleStateDriver driver;

   public AdminModulesFlow(FlowContext appCtx) {
      super(appCtx);
      this.driver = new ModuleStateDriver(this.appCtx);
   }

   public void start(String[] uris) throws DeploymentException {
      if (!this.appCtx.isAdminState()) {
         Module[] startingModules = this.appCtx.getStartingModules();

         try {
            this.driver.adminToProduction(startingModules);
         } catch (Throwable var9) {
            ErrorCollectionException ece = new ErrorCollectionException(var9);

            try {
               this.driver.deactivate(startingModules);
            } catch (Throwable var8) {
               ece.add(var8);
            }

            try {
               this.driver.unprepare(startingModules);
            } catch (Throwable var7) {
               ece.add(var7);
            }

            try {
               this.driver.destroy(startingModules);
            } catch (Throwable var6) {
               ece.add(var6);
            }

            this.throwAppException(ece);
         }

      }
   }

   public void stop(String[] uris) {
      if (!this.appCtx.isAdminState()) {
         Module[] stoppingModules = this.appCtx.getStoppingModules();

         try {
            this.driver.forceProductionToAdmin(stoppingModules);
         } catch (DeploymentException var4) {
            if (this.isDebugEnabled()) {
               this.debug("Ignoring productionToAdmin error ", var4);
            }
         }

      }
   }
}
