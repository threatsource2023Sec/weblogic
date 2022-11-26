package weblogic.application.background;

import weblogic.application.DeploymentContext;

public final class BackgroundApplication {
   private final BackgroundDeployment deployment;
   private final DeploymentContext ctx;
   private Exception failureException;

   BackgroundApplication(BackgroundDeployment d, DeploymentContext ctx) {
      this.deployment = d;
      this.ctx = ctx;
   }

   public BackgroundDeployment getDeployment() {
      return this.deployment;
   }

   public Exception getFailureException() {
      return this.failureException;
   }

   public void setFailureException(Exception e) {
      this.failureException = e;
   }

   public DeploymentContext getDeploymentContext() {
      return this.ctx;
   }
}
