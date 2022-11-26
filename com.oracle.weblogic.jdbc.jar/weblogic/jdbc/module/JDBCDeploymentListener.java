package weblogic.jdbc.module;

import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleWrapper;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.deploy.event.VetoableDeploymentEvent;
import weblogic.deploy.event.VetoableDeploymentListener;
import weblogic.management.configuration.BasicDeploymentMBean;

public class JDBCDeploymentListener implements VetoableDeploymentListener {
   public void vetoableApplicationActivate(VetoableDeploymentEvent evt) throws DeploymentVetoException {
   }

   public void vetoableApplicationDeploy(VetoableDeploymentEvent evt) throws DeploymentVetoException {
   }

   public void vetoableApplicationUndeploy(VetoableDeploymentEvent evt) throws DeploymentVetoException {
      BasicDeploymentMBean deployment = evt.getSystemResource();
      if (deployment == null) {
         deployment = evt.getAppDeployment();
      }

      if (deployment != null) {
         ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(((BasicDeploymentMBean)deployment).getName());
         if (appCtx != null) {
            Module[] modules = appCtx.getApplicationModules();
            if (modules != null) {
               for(int lcv = 0; lcv < modules.length; ++lcv) {
                  if (modules[lcv] instanceof ModuleWrapper) {
                     Module delegate = ((ModuleWrapper)modules[lcv]).unwrap();
                     if (delegate instanceof JDBCModule) {
                        ((JDBCModule)delegate).checkDependencies();
                     }
                  }
               }

            }
         }
      }
   }
}
