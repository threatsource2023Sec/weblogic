package weblogic.application.background;

import weblogic.application.AdminModeCallback;
import weblogic.application.ApplicationContext;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.management.DeploymentException;

public class BackgroundDeployment implements Deployment {
   private final Deployment delegate;
   private boolean onDemandContextPathRegistered = false;
   private boolean completedDeployment = false;
   private boolean startedDeployment = false;
   private boolean completedUnDeployment = false;
   private boolean startedUnDeployment = false;

   public BackgroundDeployment(Deployment delegate) {
      this.delegate = delegate;
   }

   public Deployment getDelegate() {
      return this.delegate;
   }

   public void setOnDemandContextPathRegistered(boolean b) {
      this.onDemandContextPathRegistered = b;
   }

   public boolean getOnDemandContextPathRegistered() {
      return this.onDemandContextPathRegistered;
   }

   public void setCompletedDeployment(boolean b) {
      this.completedDeployment = b;
   }

   public boolean getCompletedDeployment() {
      return this.completedDeployment;
   }

   public void setStartedDeployment(boolean b) {
      this.startedDeployment = b;
   }

   public boolean getStartedDeployment() {
      return this.startedDeployment;
   }

   public boolean getCompletedUnDeployment() {
      return this.completedUnDeployment;
   }

   public boolean getStartedUnDeployment() {
      return this.startedUnDeployment;
   }

   public void prepare(DeploymentContext deploymentContext) {
      this.completedDeployment = false;
      BackgroundDeploymentManager.instance.addBackgroundDeployment(this, deploymentContext);
   }

   public void activate(DeploymentContext deploymentContext) {
   }

   public void adminToProduction(DeploymentContext deploymentContext) {
   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.gracefulProductionToAdmin(deploymentContext);
      } else {
         AdminModeCallback callback = null;
         if (deploymentContext != null) {
            callback = deploymentContext.getAdminModeCallback();
         }

         if (callback != null) {
            callback.completed();
         }
      }

   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.forceProductionToAdmin(deploymentContext);
      }

   }

   public void deactivate(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.deactivate(deploymentContext);
         this.startedUnDeployment = true;
      }

   }

   public void unprepare(DeploymentContext deploymentContext) throws DeploymentException {
      BackgroundDeploymentManager.instance.removeBackgroundDeployment(this, deploymentContext);
      if (this.completedDeployment) {
         this.delegate.unprepare(deploymentContext);
         this.completedUnDeployment = true;
      }

   }

   public void remove(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.remove(deploymentContext);
      }

   }

   public void prepareUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.prepareUpdate(deploymentContext);
      }

   }

   public void activateUpdate(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.activateUpdate(deploymentContext);
      }

   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
      if (this.completedDeployment) {
         this.delegate.rollbackUpdate(deploymentContext);
      }

   }

   public void start(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.start(deploymentContext);
      }

   }

   public void stop(DeploymentContext deploymentContext) throws DeploymentException {
      if (this.completedDeployment) {
         this.delegate.stop(deploymentContext);
      }

   }

   public ApplicationContext getApplicationContext() {
      return this.delegate.getApplicationContext();
   }

   public void assertUndeployable() throws DeploymentException {
   }
}
