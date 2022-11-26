package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.BaseComponentMBeanFactory;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.utils.WarUtils;

public final class WarDeploymentFactory extends BaseComponentMBeanFactory implements DeploymentFactory, ComponentMBeanFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) throws DeploymentException {
      try {
         return WarUtils.canBeDeployedAsWarBasicCheck(file);
      } catch (IOException var4) {
         throw new DeploymentException(var4);
      }
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) throws DeploymentException {
      return WarUtils.catchAllCheck(file);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      try {
         if (!WarUtils.isWar(f)) {
            HTTPLogger.logDeployApplicationAsWar(f.getName());
         }

         return new WarDeployment(mbean, f);
      } catch (IOException var4) {
         throw new DeploymentException(var4);
      }
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean appMBean, File f, AppDeploymentMBean appDepMBean) throws DeploymentException {
      try {
         String uri = f.getName();
         String name = this.removeExtension(uri);
         if (appDepMBean != null) {
            name = this.getCompatibilityName(name, appDepMBean);
         }

         ComponentMBeanFactory.MBeanFactory fact = WarUtils.isWebServices(f) ? WEB_SERVICE_COMP : WEB_COMP;
         ComponentMBean c = this.findOrCreateComponentMBean(fact, appMBean, name, uri);
         return new ComponentMBean[]{c};
      } catch (IOException var8) {
         throw new DeploymentException(var8);
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
