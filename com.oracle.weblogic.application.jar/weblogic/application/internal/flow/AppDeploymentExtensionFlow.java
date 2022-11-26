package weblogic.application.internal.flow;

import java.util.Iterator;
import java.util.Set;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public abstract class AppDeploymentExtensionFlow extends BaseFlow {
   public AppDeploymentExtensionFlow(FlowContext appCtx) {
      super(appCtx);
   }

   protected abstract Set getExtensions();

   public void prepare() throws DeploymentException {
      Iterator var1 = this.getExtensions().iterator();

      while(var1.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var1.next();
         extension.prepare(this.appCtx);
      }

   }

   public void unprepare() throws DeploymentException {
      Iterator var1 = this.getExtensions().iterator();

      while(var1.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var1.next();
         extension.unprepare(this.appCtx);
      }

   }

   public void activate() throws DeploymentException {
      Iterator var1 = this.getExtensions().iterator();

      while(var1.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var1.next();
         extension.activate(this.appCtx);
      }

   }

   public void deactivate() throws DeploymentException {
      Iterator var1 = this.getExtensions().iterator();

      while(var1.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var1.next();
         extension.deactivate(this.appCtx);
      }

   }

   public void adminToProduction() throws DeploymentException {
      Iterator var1 = this.getExtensions().iterator();

      while(var1.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var1.next();
         extension.adminToProduction(this.appCtx);
      }

   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      Iterator var2 = this.getExtensions().iterator();

      while(var2.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var2.next();
         extension.forceProductionToAdmin(this.appCtx, barrier);
      }

   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      Iterator var2 = this.getExtensions().iterator();

      while(var2.hasNext()) {
         AppDeploymentExtension extension = (AppDeploymentExtension)var2.next();
         extension.gracefulProductionToAdmin(this.appCtx, barrier);
      }

   }
}
