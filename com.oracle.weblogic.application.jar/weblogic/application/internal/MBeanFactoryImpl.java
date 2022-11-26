package weblogic.application.internal;

import java.io.File;
import javax.management.InvalidAttributeValueException;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.DeploymentFactory;
import weblogic.application.DeploymentManager;
import weblogic.application.MBeanFactory;
import weblogic.application.utils.ManagementUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DomainMBean;

public final class MBeanFactoryImpl extends MBeanFactory {
   private ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
   private final DebugLogger deploymentLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   private ComponentMBeanFactory findOrCreateComponentMBeans(ApplicationMBean appMBean, File appPath, AppDeploymentMBean appDepMBean, String appId) throws DeploymentException {
      String applicationId = appDepMBean != null ? appDepMBean.getName() : appId;
      DeploymentFactory deploymentFactory = ((DeploymentManagerImpl)DeploymentManager.getDeploymentManager()).getComponentMBeanFactory(applicationId, appDepMBean, appPath);
      if (deploymentFactory == null) {
         if (this.deploymentLogger.isDebugEnabled()) {
            this.deploymentLogger.debug("ComponentMBeanFactory not found for application id " + applicationId + " path " + appPath);
         }
      } else {
         if (!(deploymentFactory instanceof ComponentMBeanFactory)) {
            throw new AssertionError("Matched deployment factory not an instance ComponentMBeanFactory");
         }

         ComponentMBeanFactory cmf = (ComponentMBeanFactory)deploymentFactory;
         ComponentMBean[] comps = cmf.findOrCreateComponentMBeans(appMBean, appPath, appDepMBean);
         if (comps != null) {
            return cmf;
         }
      }

      return ComponentMBeanFactory.DEFAULT_FACTORY;
   }

   public ApplicationMBean initializeMBeans(DomainMBean domain, File sourcePath, String appId, String appDDPath, String wlsDDPath, AppDeploymentMBean appDepMBean) throws ApplicationException {
      ApplicationMBean appMBean = null;
      boolean wasSuccessful = false;

      ApplicationMBean var10;
      try {
         appMBean = this.createApplicationMBean(domain, appId, appDDPath, wlsDDPath);
         ComponentMBeanFactory factory = this.createComponentMBeans(sourcePath, appMBean, appDepMBean, appId);
         this.initializeAppMBeanPath(factory, appMBean, sourcePath);
         wasSuccessful = true;
         var10 = appMBean;
      } catch (ManagementException var15) {
         throw new ApplicationException(var15);
      } catch (InvalidAttributeValueException var16) {
         throw new AssertionError(var16);
      } finally {
         if (!wasSuccessful) {
            this.cleanupMBeans(domain, appMBean);
         } else if (appMBean != null) {
            appMBean.setDelegationEnabled(true);
         }

      }

      return var10;
   }

   private ComponentMBeanFactory createComponentMBeans(File sourcePath, ApplicationMBean appMBean, AppDeploymentMBean appDeploymentMBean, String appId) throws DeploymentException {
      File realPath = sourcePath;
      if (!sourcePath.isAbsolute()) {
         if (sourcePath.getPath().startsWith(File.separator)) {
            realPath = new File(sourcePath.getAbsolutePath());
         } else {
            realPath = new File(this.resolveWithRootDirectory(sourcePath.getPath()));
         }
      }

      return this.findOrCreateComponentMBeans(appMBean, realPath, appDeploymentMBean, appId);
   }

   private void initializeAppMBeanPath(ComponentMBeanFactory factory, ApplicationMBean appMBean, File sourcePath) throws ManagementException, InvalidAttributeValueException {
      if (factory.needsApplicationPathMunging()) {
         appMBean.setPath(sourcePath.getParent());
      } else {
         appMBean.setPath(sourcePath.getPath());
      }

   }

   private ApplicationMBean createApplicationMBean(DomainMBean domain, String appId, String appDDPath, String wlsDDPath) {
      ApplicationMBean appMBean = domain.lookupApplication(appId);
      if (appMBean == null) {
         appMBean = domain.createApplication(appId);
      }

      appMBean.setAltDescriptorPath(appDDPath);
      appMBean.setAltWLSDescriptorPath(wlsDDPath);
      return appMBean;
   }

   public void reconcileMBeans(AppDeploymentMBean appDepMBean, File appPath) throws ApplicationException {
      try {
         this.findOrCreateComponentMBeans(appDepMBean.getAppMBean(), appPath, appDepMBean, appDepMBean.getName());
      } catch (ManagementException var4) {
         throw new ApplicationException(var4);
      }
   }

   public void cleanupMBeans(DomainMBean domain, ApplicationMBean a) {
      if (a != null) {
         this.deploymentLogger.debug("MBeanFactoryImpl: Destroy " + a.getName() + " from " + domain.getName());
         domain.destroyApplication(a);
      }

   }

   private String resolveWithRootDirectory(String source) {
      return ManagementUtils.getDomainRootDir() + File.separatorChar + source;
   }
}
