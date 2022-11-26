package weblogic.application;

public enum DeploymentOperationType {
   ACTIVATE(1),
   DEACTIVATE(3),
   PREPARE(2),
   UNPREPARE(5),
   DISTRIBUTE(6),
   REMOVE(4),
   START(7),
   STOP(8),
   DEPLOY(11),
   UNDEPLOY(12),
   REDEPLOY(9),
   UPDATE(10),
   RETIRE(13);

   private final int deploymentTask;

   private DeploymentOperationType(int task) {
      this.deploymentTask = task;
   }

   public static DeploymentOperationType valueOf(int deploymentTask) {
      DeploymentOperationType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         DeploymentOperationType task = var1[var3];
         if (task.deploymentTask == deploymentTask) {
            return task;
         }
      }

      return null;
   }
}
