package weblogic.messaging.interception.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class InterceptionDeploymentFactory implements DeploymentFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return this.isInterception(file);
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   private boolean isInterception(File f) {
      if (f.isDirectory()) {
         return false;
      } else {
         return f.getName().endsWith("-interception.xml");
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return new InterceptionDeployment(mbean, f);
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
