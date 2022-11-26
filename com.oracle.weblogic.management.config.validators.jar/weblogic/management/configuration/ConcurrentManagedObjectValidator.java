package weblogic.management.configuration;

public class ConcurrentManagedObjectValidator {
   static void validateConcurrentManagedObjects(DomainMBean domain) throws IllegalArgumentException {
   }

   private static void ensureNoTargetsConfiguredForTemplates(PartitionMBean partitionMBean) throws IllegalArgumentException {
      ensureNoTargets(partitionMBean.getManagedExecutorServiceTemplates(), partitionMBean);
      ensureNoTargets(partitionMBean.getManagedScheduledExecutorServiceTemplates(), partitionMBean);
      ensureNoTargets(partitionMBean.getManagedThreadFactoryTemplates(), partitionMBean);
   }

   private static void ensureNoTargets(DeploymentMBean[] deploymentMBeans, PartitionMBean partitionMBean) throws IllegalArgumentException {
      if (deploymentMBeans != null) {
         DeploymentMBean[] var2 = deploymentMBeans;
         int var3 = deploymentMBeans.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            DeploymentMBean deploymentMBean = var2[var4];
            TargetMBean[] targetMBeans = deploymentMBean.getTargets();
            if (targetMBeans != null && targetMBeans.length > 0) {
               throw new IllegalArgumentException("concurrent managed object template " + deploymentMBean.getName() + " configured in partition " + partitionMBean.getName() + " cannot have targets specified");
            }
         }

      }
   }
}
