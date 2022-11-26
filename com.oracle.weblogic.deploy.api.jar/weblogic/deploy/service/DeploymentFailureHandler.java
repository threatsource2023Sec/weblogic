package weblogic.deploy.service;

public interface DeploymentFailureHandler {
   void deployFailed(long var1, DeploymentException var3);

   void appPrepareFailed(long var1, DeploymentException var3);

   void cancelSucceeded(long var1, FailureDescription[] var3);

   void cancelFailed(long var1, DeploymentException var3);
}
