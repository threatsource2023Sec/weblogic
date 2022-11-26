package weblogic.management.extension.internal;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.CustomResourceMBean;
import weblogic.management.configuration.SystemResourceMBean;

public class CustomResourceDeploymentFactory implements DeploymentFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return mbean instanceof CustomResourceMBean;
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      CustomResourceMBean customResource = (CustomResourceMBean)mbean;
      return new CustomResourceDeployment(customResource, f);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
