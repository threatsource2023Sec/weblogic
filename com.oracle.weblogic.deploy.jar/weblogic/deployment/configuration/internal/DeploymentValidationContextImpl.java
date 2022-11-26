package weblogic.deployment.configuration.internal;

import java.io.File;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.deployment.configuration.DeploymentValidationContext;
import weblogic.deployment.configuration.DeploymentValidationLogger;
import weblogic.management.configuration.AppDeploymentMBean;

public class DeploymentValidationContextImpl implements DeploymentValidationContext {
   private AppDeploymentMBean appMBean;

   public DeploymentValidationContextImpl(AppDeploymentMBean appMBean) {
      this.appMBean = appMBean;
   }

   public SessionHelper getSessionHelper() {
      try {
         SessionHelper helper = SessionHelper.getInstance(SessionHelper.getDisconnectedDeploymentManager());
         String sourcePath = this.appMBean.getSourcePath();
         helper.setApplication(new File(sourcePath));
         String planPath = this.appMBean.getPlanPath();
         if (planPath != null) {
            helper.setPlan(new File(planPath));
         }

         return helper;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public DeploymentValidationLogger getLogger() {
      return new DeploymentValidationLoggerImpl();
   }
}
