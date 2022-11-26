package weblogic.application.internal;

import java.io.File;
import weblogic.application.ApplicationContext;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.RedeployInfo;
import weblogic.application.archive.ApplicationArchive;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;

final class CarDeployment implements Deployment {
   private final ApplicationContext appCtx;

   CarDeployment(AppDeploymentMBean mbean, File f) {
      this.appCtx = new ApplicationContextImpl(mbean, f);
   }

   CarDeployment(AppDeploymentMBean mbean, ApplicationArchive f) {
      this.appCtx = new ApplicationContextImpl(mbean, f);
   }

   public void prepare(DeploymentContext deploymentContext) {
   }

   public void activate(DeploymentContext deploymentContext) {
   }

   public void deactivate(DeploymentContext deploymentContext) {
   }

   public void unprepare(DeploymentContext deploymentContext) {
   }

   public void remove(DeploymentContext deploymentContext) {
   }

   public void prepareUpdate(DeploymentContext deploymentContext) {
   }

   public void activateUpdate(DeploymentContext deploymentContext) {
   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
   }

   public void adminToProduction(DeploymentContext deploymentContext) {
   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) {
   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) {
   }

   public void stop(DeploymentContext deploymentContext) {
   }

   public void start(DeploymentContext deploymentContext) {
   }

   public RedeployInfo validateRedeploy(DeploymentContext deploymentContext) {
      return new RedeployInfoImpl();
   }

   public boolean deregisterCallback(int tag) {
      return false;
   }

   public ApplicationContext getApplicationContext() {
      return this.appCtx;
   }

   public void assertUndeployable() throws DeploymentException {
   }
}
