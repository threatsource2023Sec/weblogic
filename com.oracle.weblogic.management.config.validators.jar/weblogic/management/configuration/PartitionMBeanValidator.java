package weblogic.management.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import weblogic.diagnostics.descriptor.validation.WLDFDescriptorValidationTextTextFormatter;
import weblogic.management.ResourceGroupMultitargetingUtils;
import weblogic.management.utils.PartitionUtils;

public class PartitionMBeanValidator {
   private static final ManagementConfigValidatorsTextFormatter TXT_FORMATTER = ManagementConfigValidatorsTextFormatter.getInstance();
   private static WLDFDescriptorValidationTextTextFormatter WLDF_TXT_FORMATTER = WLDFDescriptorValidationTextTextFormatter.getInstance();

   public static void validatePartitionMBean(PartitionMBean partition) {
      TargetMBean[] var1;
      int var2;
      int var3;
      TargetMBean target;
      if (partition.getAvailableTargets() != null) {
         var1 = partition.getAvailableTargets();
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            target = var1[var3];
            validateNoSamePhysicalServer(partition, partition.getAvailableTargets(), target);
         }
      }

      if (partition.getDefaultTargets() != null) {
         var1 = partition.getDefaultTargets();
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            target = var1[var3];
            validateAddDefaultTarget(partition, target);
         }
      }

      validateAdminVirtualTarget(partition);
      validateWLDFWatchActions(partition);
   }

   private static void validateWLDFWatchActions(PartitionMBean partition) {
   }

   public static void validateAddAvailableTarget(PartitionMBean partition, TargetMBean targetToAdd) throws IllegalArgumentException {
      if (!(targetToAdd instanceof VirtualHostMBean) && !(targetToAdd instanceof VirtualTargetMBean)) {
         throw new IllegalArgumentException(TXT_FORMATTER.getTargetOfVTOrVHMsg(targetToAdd.getName(), targetToAdd.getType(), VirtualHostMBean.class.getName(), VirtualTargetMBean.class.getName(), partition.getType()));
      } else {
         validateTargetNotSetForOtherPartitionOrGlobalRG(partition, targetToAdd);
      }
   }

   private static void validateNoSamePhysicalServer(PartitionMBean partition, TargetMBean[] targets, TargetMBean targetToAdd) throws IllegalArgumentException {
      String target = ResourceGroupMultitargetingUtils.sharePhysicalServer(targets, targetToAdd);
      if (target != null) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckRGMultiTargetMsg(partition.getType(), partition.getName(), targetToAdd.getName(), target));
      }
   }

   private static void validateTargetNotSetForOtherPartitionOrGlobalRG(PartitionMBean partition, TargetMBean targetToAdd) throws IllegalArgumentException {
      partition = (PartitionMBean)Objects.requireNonNull(partition);
      DomainMBean domain = (DomainMBean)Objects.requireNonNull(partition.getParent());
      validateTargetNotSetForOtherPartition(domain, partition, targetToAdd);
      validateTargetNotSetForOtherGlobalResourceGroup(domain, targetToAdd);
   }

   public static void validateSetAvailableTargets(PartitionMBean partition, TargetMBean[] targetsToAdd) throws IllegalArgumentException {
      if (targetsToAdd != null) {
         TargetMBean[] var2 = targetsToAdd;
         int var3 = targetsToAdd.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            validateAddAvailableTarget(partition, target);
         }
      }

      validateRemoveAvailableTarget(partition, targetsToAdd);
   }

   private static void validateRemoveAvailableTarget(PartitionMBean partition, TargetMBean[] targetsToAdd) {
      TargetMBean[] currTargets = partition.getAvailableTargets();
      if (currTargets != null) {
         Object targetsToAddList;
         if (targetsToAdd == null) {
            targetsToAddList = new ArrayList();
         } else {
            targetsToAddList = Arrays.asList(targetsToAdd);
         }

         TargetMBean[] targetsRemoved = PartitionUtils.getRemovedTargetArray(currTargets, (TargetMBean[])((List)targetsToAddList).toArray(new TargetMBean[((List)targetsToAddList).size()]));
         if (targetsRemoved.length > 0) {
            List targetsUsed = getUsedTargetsList(partition);
            if (!targetsUsed.isEmpty()) {
               List targetCannotRemove = new ArrayList();
               TargetMBean[] var7 = targetsRemoved;
               int var8 = targetsRemoved.length;

               TargetMBean targetToRemove;
               for(int var9 = 0; var9 < var8; ++var9) {
                  targetToRemove = var7[var9];
                  boolean bFound = false;
                  Iterator var12 = targetsUsed.iterator();

                  while(var12.hasNext()) {
                     TargetMBean targetUsed = (TargetMBean)var12.next();
                     if (targetUsed.getType().compareTo(targetToRemove.getType()) == 0 && targetUsed.getName().compareTo(targetToRemove.getName()) == 0) {
                        bFound = true;
                        break;
                     }
                  }

                  if (bFound) {
                     targetCannotRemove.add(targetToRemove);
                  }
               }

               if (!targetCannotRemove.isEmpty()) {
                  StringBuilder msgString = new StringBuilder();
                  String sep = "";

                  for(Iterator var16 = targetCannotRemove.iterator(); var16.hasNext(); sep = ",") {
                     targetToRemove = (TargetMBean)var16.next();
                     msgString.append(sep);
                     msgString.append(targetToRemove.getName());
                  }

                  throw new IllegalArgumentException(TXT_FORMATTER.getCheckRemoveAvailTargetMsg(msgString.toString(), partition.getName()));
               }
            }
         }
      }

   }

   protected static void validateTargetNotSetForOtherPartition(DomainMBean domain, PartitionMBean partition, TargetMBean targetToAdd) throws IllegalArgumentException {
   }

   private static void validateTargetNotSetForOtherGlobalResourceGroup(DomainMBean domain, TargetMBean targetToAdd) throws IllegalArgumentException {
   }

   public static void validateAddDefaultTarget(PartitionMBean partition, TargetMBean targetToAdd) throws IllegalArgumentException {
      TargetMBean[] partitionAvailableTargets = partition.getAvailableTargets();
      if (partitionAvailableTargets == null) {
         throw new IllegalArgumentException("Cannot add DefaultTarget as AvailableTargets is null");
      } else if (partitionAvailableTargets.length <= 0) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckAddDefTargetMsg(targetToAdd.getName(), partition.getName()));
      } else {
         List availableTargets = Arrays.asList(partitionAvailableTargets);
         if (!availableTargets.contains(targetToAdd)) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckAddDefTargetMsg(targetToAdd.getName(), partition.getName()));
         } else {
            validateNoSamePhysicalServer(partition, partition.getDefaultTargets(), targetToAdd);
         }
      }
   }

   public static void validateSetDefaultTarget(PartitionMBean partition, TargetMBean[] targetsToAdd) throws IllegalArgumentException {
      TargetMBean[] currTargets = partition.getDefaultTargets();
      if (currTargets != null) {
         Object targetsToAddList;
         if (targetsToAdd == null) {
            targetsToAddList = new ArrayList();
         } else {
            targetsToAddList = Arrays.asList(targetsToAdd);
         }

         PartitionUtils.getRemovedTargetArray(currTargets, (TargetMBean[])((List)targetsToAddList).toArray(new TargetMBean[((List)targetsToAddList).size()]));
      }

   }

   private static List getUsedTargetsList(PartitionMBean partition) {
      List allDefTargets = new ArrayList();
      TargetMBean[] defaultTargets = partition.getDefaultTargets();
      if (defaultTargets != null && defaultTargets.length > 0) {
         allDefTargets.addAll(Arrays.asList(defaultTargets));
      }

      return allDefTargets;
   }

   public static void validateRemoveDefaultTarget(PartitionMBean partition, TargetMBean targetToRemove) throws IllegalArgumentException {
   }

   private static void validateAdminVirtualTarget(PartitionMBean partition) throws IllegalArgumentException {
   }
}
