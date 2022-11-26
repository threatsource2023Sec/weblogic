package weblogic.application;

public class ApplicationLifecycleEvent {
   private final DeploymentOperationType deploymentOperation;
   private final boolean isStatic;
   private ApplicationContext context;

   /** @deprecated */
   @Deprecated
   public ApplicationLifecycleEvent(ApplicationContext ctx) {
      this.context = ctx;
      this.isStatic = false;
      this.deploymentOperation = null;
   }

   public ApplicationLifecycleEvent(ApplicationContext ctx, DeploymentOperationType deploymentOperation, boolean isStatic) {
      this.context = ctx;
      this.deploymentOperation = deploymentOperation;
      this.isStatic = isStatic;
   }

   public ApplicationContext getApplicationContext() {
      return this.context;
   }

   public String toString() {
      return "ApplicationLifecycleEvent{" + this.context + "}";
   }

   public DeploymentOperationType getDeploymentOperation() {
      return this.deploymentOperation;
   }

   public boolean isStaticOperation() {
      return this.isStatic;
   }
}
