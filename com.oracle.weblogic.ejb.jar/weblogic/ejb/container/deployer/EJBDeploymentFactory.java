package weblogic.ejb.container.deployer;

import java.io.File;
import java.io.IOException;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.BaseComponentMBeanFactory;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.ejb.spi.EJBJarUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class EJBDeploymentFactory extends BaseComponentMBeanFactory implements DeploymentFactory, ComponentMBeanFactory {
   private final CoherenceService coherenceService;

   EJBDeploymentFactory(CoherenceService coherenceService) {
      this.coherenceService = coherenceService;
   }

   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) throws DeploymentException {
      try {
         return EJBJarUtils.isEJBBasic(file);
      } catch (IOException var4) {
         throw new DeploymentException(var4);
      }
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) throws DeploymentException {
      try {
         return EJBJarUtils.hasEJBAnnotations(file);
      } catch (IOException var4) {
         throw new DeploymentException(var4);
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return new EJBDeployment(mbean, f, this.coherenceService);
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean appMBean, File f, AppDeploymentMBean appDepMBean) throws DeploymentException {
      String uri = f.getName();
      String name = this.removeExtension(uri);
      if (appDepMBean != null) {
         name = this.getCompatibilityName(name, appDepMBean);
      }

      return new ComponentMBean[]{this.findOrCreateComponentMBean(EJB_COMP, appMBean, name, uri)};
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive aa) {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive aa) {
      return null;
   }
}
