package weblogic.diagnostics.module;

import java.io.File;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;

public class WLDFDeploymentFactory implements DeploymentFactory {
   private static WLDFDeploymentFactory singleton = null;

   public static synchronized WLDFDeploymentFactory getInstance() {
      if (singleton == null) {
         singleton = new WLDFDeploymentFactory();
      }

      return singleton;
   }

   private WLDFDeploymentFactory() {
   }

   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return mbean instanceof WLDFSystemResourceMBean;
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      WLDFSystemResourceMBean wldfSystemResource = (WLDFSystemResourceMBean)mbean;
      return new WLDFDeployment(wldfSystemResource, f);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
