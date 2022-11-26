package weblogic.jms.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.jms.common.JMSDebug;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class JMSDeploymentFactory implements DeploymentFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return mbean instanceof AppDeploymentMBean ? this.isJMS(file) : mbean instanceof JMSSystemResourceMBean;
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   private boolean isJMS(File f) {
      if (f.isDirectory()) {
         return false;
      } else {
         return f.getName().endsWith("-jms.xml");
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Creating a standalone JMS Module with file : " + f.getAbsolutePath());
      }

      return new JMSDeployment(mbean, f);
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Creating a system resource JMS Module with file : " + f.getAbsolutePath());
      }

      return new JMSDeployment(mbean, f);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
