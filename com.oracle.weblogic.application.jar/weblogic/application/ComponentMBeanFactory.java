package weblogic.application;

import java.io.File;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.management.configuration.EJBComponentMBean;
import weblogic.management.configuration.JDBCPoolComponentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.configuration.WebServiceComponentMBean;

public interface ComponentMBeanFactory extends DeploymentFactory {
   MBeanFactory EJB_COMP = new MBeanFactory() {
      public ComponentMBean newCompMBean(ApplicationMBean appMBean, String uri) {
         return appMBean.createEJBComponent(uri);
      }

      public Class getComponentMBeanType() {
         return EJBComponentMBean.class;
      }
   };
   MBeanFactory WEB_COMP = new MBeanFactory() {
      public ComponentMBean newCompMBean(ApplicationMBean appMBean, String uri) {
         return appMBean.createWebAppComponent(uri);
      }

      public Class getComponentMBeanType() {
         return WebAppComponentMBean.class;
      }
   };
   MBeanFactory WEB_SERVICE_COMP = new MBeanFactory() {
      public ComponentMBean newCompMBean(ApplicationMBean appMBean, String uri) {
         return appMBean.createWebServiceComponent(uri);
      }

      public Class getComponentMBeanType() {
         return WebServiceComponentMBean.class;
      }
   };
   MBeanFactory CONNECTOR_COMP = new MBeanFactory() {
      public ComponentMBean newCompMBean(ApplicationMBean appMBean, String uri) {
         return appMBean.createConnectorComponent(uri);
      }

      public Class getComponentMBeanType() {
         return ConnectorComponentMBean.class;
      }
   };
   MBeanFactory JDBC_COMP = new MBeanFactory() {
      public ComponentMBean newCompMBean(ApplicationMBean appMBean, String uri) {
         return appMBean.createJDBCPoolComponent(uri);
      }

      public Class getComponentMBeanType() {
         return JDBCPoolComponentMBean.class;
      }
   };
   ComponentMBeanFactory DEFAULT_FACTORY = new ComponentMBeanFactory() {
      public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) throws DeploymentException {
         return false;
      }

      public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) throws DeploymentException {
         return false;
      }

      public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
         return null;
      }

      public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
         return null;
      }

      public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
         return null;
      }

      public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
         return null;
      }

      public ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean appMBean, File f) {
         return new ComponentMBean[0];
      }

      public ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean appMBean, File f, AppDeploymentMBean appDeploymentMBean) {
         return new ComponentMBean[0];
      }

      public boolean needsApplicationPathMunging() {
         return false;
      }
   };

   ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean var1, File var2, AppDeploymentMBean var3) throws DeploymentException;

   boolean needsApplicationPathMunging();

   public abstract static class MBeanFactory {
      public abstract ComponentMBean newCompMBean(ApplicationMBean var1, String var2);

      public abstract Class getComponentMBeanType();
   }
}
