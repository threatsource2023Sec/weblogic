package weblogic.management.configuration;

public class PartitionWorkManagerValidator {
   public static void validatePartitionWorkManagers(DomainMBean domain) throws IllegalArgumentException {
   }

   static void ensureOnlyOnePartitionWorkManagerConfigured(PartitionMBean partitionMBean) throws IllegalArgumentException {
      PartitionWorkManagerMBean partitionWorkManagerMBean = partitionMBean.getPartitionWorkManager();
      if (partitionWorkManagerMBean != null) {
         PartitionWorkManagerMBean partitionWorkManagerMBeanRef = partitionMBean.getPartitionWorkManagerRef();
         if (partitionWorkManagerMBeanRef != null) {
            throw new IllegalArgumentException("Multiple partition work manager configurations are assigned to " + partitionMBean.getName() + " which is not valid. Assign either partition-work-manager or partition-work-manager-ref.");
         }
      }

   }

   static void ensureNoTargetsConfigured(PartitionMBean partitionMBean) throws IllegalArgumentException {
      SelfTuningMBean selfTuningMBean = partitionMBean.getSelfTuning();
      if (selfTuningMBean != null) {
         ensureNoTargets(selfTuningMBean.getWorkManagers(), partitionMBean);
         ensureNoTargets(selfTuningMBean.getFairShareRequestClasses(), partitionMBean);
         ensureNoTargets(selfTuningMBean.getContextRequestClasses(), partitionMBean);
         ensureNoTargets(selfTuningMBean.getResponseTimeRequestClasses(), partitionMBean);
         ensureNoTargets(selfTuningMBean.getMinThreadsConstraints(), partitionMBean);
         ensureNoTargets(selfTuningMBean.getMaxThreadsConstraints(), partitionMBean);
         ensureNoTargets(selfTuningMBean.getCapacities(), partitionMBean);
      }
   }

   static void ensureNoTargets(DeploymentMBean[] deploymentMBeans, PartitionMBean partitionMBean) throws IllegalArgumentException {
      if (deploymentMBeans != null) {
         DeploymentMBean[] var2 = deploymentMBeans;
         int var3 = deploymentMBeans.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            DeploymentMBean deploymentMBean = var2[var4];
            TargetMBean[] targetMBeans = deploymentMBean.getTargets();
            if (targetMBeans != null && targetMBeans.length > 0) {
               throw new IllegalArgumentException("Work manager component " + deploymentMBean.getName() + " configured in partition " + partitionMBean.getName() + " cannot have targets specified");
            }
         }

      }
   }
}
