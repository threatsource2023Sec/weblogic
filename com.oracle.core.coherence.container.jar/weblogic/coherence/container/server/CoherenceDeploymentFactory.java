package weblogic.coherence.container.server;

import java.io.File;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.Deployment;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public class CoherenceDeploymentFactory implements ComponentMBeanFactory {
   public static final String COH_ARCHIVE_SUFFIX = ".gar";

   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return isGAR(file);
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   public Deployment createDeployment(AppDeploymentMBean appDeploymentMBean, File file) throws DeploymentException {
      return new CoherenceDeployment(appDeploymentMBean, file);
   }

   public Deployment createDeployment(SystemResourceMBean systemResourceMBean, File file) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }

   public static boolean isGAR(File file) {
      return file.isDirectory() ? (new File(file, "META-INF/coherence-application.xml")).exists() : file.getName().endsWith(".gar");
   }

   public ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean appMBean, File f, AppDeploymentMBean appDeploymentMBean) throws DeploymentException {
      return null;
   }

   public boolean needsApplicationPathMunging() {
      return false;
   }
}
