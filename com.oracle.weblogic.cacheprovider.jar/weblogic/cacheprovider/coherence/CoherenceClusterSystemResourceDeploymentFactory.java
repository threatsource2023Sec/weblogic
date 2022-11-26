package weblogic.cacheprovider.coherence;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class CoherenceClusterSystemResourceDeploymentFactory implements DeploymentFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return mbean instanceof CoherenceClusterSystemResourceMBean;
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      return new CoherenceClusterSystemResourceDeployment(mbean, f);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
