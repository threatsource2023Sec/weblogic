package weblogic.deploy.service;

public interface DeploymentReceiver extends CallbackHandler {
   void updateDeploymentContext(DeploymentContext var1);

   void prepare(DeploymentContext var1);

   void prepareCompleted(DeploymentContext var1, String var2);

   void commit(DeploymentContext var1);

   void commitCompleted(DeploymentContext var1, String var2);

   void cancel(DeploymentContext var1);
}
