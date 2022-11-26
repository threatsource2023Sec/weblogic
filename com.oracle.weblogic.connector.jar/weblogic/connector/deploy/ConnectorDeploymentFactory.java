package weblogic.connector.deploy;

import java.io.File;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.BaseComponentMBeanFactory;
import weblogic.connector.external.RarUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public final class ConnectorDeploymentFactory extends BaseComponentMBeanFactory implements DeploymentFactory, ComponentMBeanFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return RarUtils.isRarBasic(file);
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return RarUtils.isRarAdvanced(file);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return new ConnectorDeployment(mbean, f);
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

      return new ComponentMBean[]{this.findOrCreateComponentMBean(CONNECTOR_COMP, appMBean, name, uri)};
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
