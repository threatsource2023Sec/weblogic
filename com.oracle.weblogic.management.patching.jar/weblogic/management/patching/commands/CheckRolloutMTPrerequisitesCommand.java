package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.model.Cluster;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.ResourceGroup;
import weblogic.management.patching.model.VirtualTarget;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class CheckRolloutMTPrerequisitesCommand extends CheckPrerequisitesBaseCommand {
   private static final long serialVersionUID = -6526618962787533381L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final int MIN_CLUSTER_SIZE = 2;
   public static final int MIN_RUNNING_GLOBALRG_INSTANCES = 2;
   public static final int MIN_RUNNING_PARTITION_INSTANCES = 2;
   public static final int MIN_RUNNING_RG_INSTANCES = 2;
   public static final String requiredState = "RUNNING";

   public boolean execute() throws Exception {
      boolean result = super.execute();
      if (!result) {
         return result;
      } else {
         String className = this.getClass().getName();
         String workflowId = this.getContext().getWorkflowId();
         PatchingLogger.logExecutingStep(workflowId, className, this.logTarget);

         try {
            this.validateHACluster();
            this.validateHAGlobalRG();
            this.validateHAPartitionAndRG();
            result = true;
            PatchingLogger.logCompletedStep(workflowId, className, this.logTarget);
            return result;
         } catch (Exception var5) {
            PatchingLogger.logFailedStepNoError(workflowId, className, this.logTarget);
            throw var5;
         }
      }
   }

   public void validateHACluster() throws CommandException {
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      while(true) {
         HashSet set;
         Iterator var5;
         VirtualTarget vt;
         Cluster c;
         Map servers;
         int count;
         do {
            do {
               List virtualTargetList;
               if (!iterator.hasNextGlobalResourceGroup()) {
                  while(true) {
                     do {
                        do {
                           if (!iterator.hasNextPartitionApps()) {
                              return;
                           }

                           PartitionApps partitionApps = iterator.nextPartitionApps();
                           virtualTargetList = partitionApps.getVirtualTargets();
                           set = new HashSet(virtualTargetList);
                        } while(set == null);
                     } while(set.size() <= 0);

                     var5 = set.iterator();

                     while(var5.hasNext()) {
                        vt = (VirtualTarget)var5.next();
                        if (vt.getCluster() == null) {
                           throw new CommandException(PatchingMessageTextFormatter.getInstance().getClusterConditionNotMet(vt.getVtName()));
                        }

                        c = vt.getCluster();
                        servers = c.getServers();
                        count = servers.size();
                        if (count < 2) {
                           throw new CommandException(PatchingMessageTextFormatter.getInstance().getMinimumServerConditionNotMet(c.getClusterName(), count, 2));
                        }
                     }
                  }
               }

               ResourceGroup resourceGroup = iterator.nextGlobalResourceGroup();
               virtualTargetList = resourceGroup.getVirtualTargets();
               set = new HashSet(virtualTargetList);
            } while(set == null);
         } while(set.size() <= 0);

         var5 = set.iterator();

         while(var5.hasNext()) {
            vt = (VirtualTarget)var5.next();
            if (vt.getCluster() == null) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getClusterConditionNotMet(vt.getVtName()));
            }

            c = vt.getCluster();
            servers = c.getServers();
            count = servers.size();
            if (count < 2) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getMinimumServerConditionNotMet(c.getClusterName(), count, 2));
            }
         }
      }
   }

   public void validateHAGlobalRG() throws CommandException {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domainMBean = runtimeAccess.getDomain();
      DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      int countGlobalRGInstance;
      String rgName;
      boolean result;
      do {
         if (!iterator.hasNextGlobalResourceGroup()) {
            return;
         }

         countGlobalRGInstance = 0;
         ResourceGroup resourceGroup = iterator.nextGlobalResourceGroup();
         rgName = resourceGroup.getResourceGroupName();
         List virtualTargetList = resourceGroup.getVirtualTargets();
         Set set = new HashSet(virtualTargetList);
         new HashSet();
         if (set != null && set.size() > 0) {
            Iterator var11 = set.iterator();

            label58:
            while(true) {
               VirtualTarget vt;
               do {
                  if (!var11.hasNext()) {
                     break label58;
                  }

                  vt = (VirtualTarget)var11.next();
               } while(vt.getCluster() == null);

               String clusterName = vt.getCluster().getClusterName();
               ClusterMBean clusterMBean = domainMBean.lookupCluster(clusterName);
               ServerMBean[] serverMBeans = clusterMBean.getServers();
               ServerMBean[] var16 = serverMBeans;
               int var17 = serverMBeans.length;

               for(int var18 = 0; var18 < var17; ++var18) {
                  ServerMBean s = var16[var18];
                  ResourceGroupLifeCycleRuntimeMBean rgMBean = domainAccess.lookupResourceGroupLifeCycleRuntime(rgName);

                  try {
                     if (rgMBean.getState(s).equals("RUNNING")) {
                        ++countGlobalRGInstance;
                     }
                  } catch (Exception var22) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("The specified global resource group does not exist ", var22);
                     }
                  }
               }
            }
         }

         result = countGlobalRGInstance < 2;
      } while(!result);

      throw new CommandException(PatchingMessageTextFormatter.getInstance().getHAGlobalRGRequirementNotMet(rgName, countGlobalRGInstance, 2));
   }

   public void validateHAPartitionAndRG() throws CommandException {
      DomainAccess domainAccess = ManagementService.getDomainAccess(kernelId);
      DomainRuntimeServiceMBean domainRuntimeServiceMBean = domainAccess.getDomainRuntimeService();
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      while(iterator.hasNextPartitionApps()) {
         PartitionApps partitionApps = iterator.nextPartitionApps();
         String partitionName = partitionApps.getPartitionName();
         PartitionRuntimeMBean[] partitionRuntimeMBeans = domainRuntimeServiceMBean.findPartitionRuntimes(partitionName);
         boolean result = this.validateRunningPartitions(partitionRuntimeMBeans);
         if (result) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().getHAPartitionRequirementNotMet(partitionName, 2));
         }

         while(iterator.hasNextPartitionResourceGroup()) {
            ResourceGroup resourceGroup = iterator.nextPartitionResourceGroup();
            String rgName = resourceGroup.getResourceGroupName();
            result = this.validateRunningRG(partitionRuntimeMBeans, rgName);
            if (result) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getHAResourceGroupRequirementNotMet(rgName, 2));
            }
         }
      }

   }

   public boolean validateRunningPartitions(PartitionRuntimeMBean[] partitionRuntimeMBeans) {
      int countPartition = 0;
      PartitionRuntimeMBean[] var3 = partitionRuntimeMBeans;
      int var4 = partitionRuntimeMBeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PartitionRuntimeMBean partitionMBean = var3[var5];
         if (partitionMBean.getState().equals("RUNNING")) {
            ++countPartition;
         }
      }

      boolean res = countPartition < 2;
      return res;
   }

   public boolean validateRunningRG(PartitionRuntimeMBean[] partitionRuntimeMBeans, String rgName) throws CommandException {
      int countRG = 0;
      PartitionRuntimeMBean[] var4 = partitionRuntimeMBeans;
      int var5 = partitionRuntimeMBeans.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PartitionRuntimeMBean partitionMBean = var4[var6];

         try {
            String rgState = partitionMBean.getRgState(rgName);
            if (rgState.equals("RUNNING")) {
               ++countRG;
            }
         } catch (Exception var9) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("The resource group does not exist on this partition runtime ", var9);
            }
         }
      }

      boolean res = countRG < 2;
      return res;
   }
}
