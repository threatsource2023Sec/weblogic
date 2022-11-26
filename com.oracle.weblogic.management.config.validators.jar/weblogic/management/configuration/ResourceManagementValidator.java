package weblogic.management.configuration;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceManagementValidator {
   private static final ManagementConfigValidatorsTextFormatter textFormatter = ManagementConfigValidatorsTextFormatter.getInstance();
   static List validActions1 = new ArrayList();
   static List validActions2 = new ArrayList();
   static String[] validation1Array = null;
   static String[] validation2Array = null;
   public static final String SHUTDOWN = "shutdown";
   public static final String SLOW = "slow";
   public static final String NOTIFY = "notify";
   public static final String FAIL = "fail";
   public static final String RESTART = "restart";
   private static final boolean isCPUResourceTypeSupported = isValidOSBean();

   public static void validateResourceManagement(DomainMBean domain) throws IllegalArgumentException {
      ResourceManagementMBean resourceManagementMBean = domain.getResourceManagement();
      if (resourceManagementMBean != null) {
         ResourceManagerMBean[] resourceManagerMBeans = resourceManagementMBean.getResourceManagers();
         ResourceManagerMBean[] var3 = resourceManagerMBeans;
         int var4 = resourceManagerMBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceManagerMBean resourceManagerMBean = var3[var5];
            if (resourceManagerMBean != null) {
               validateResourceManager(resourceManagerMBean);
            }
         }
      }

   }

   private static void validateResourceManager(ResourceManagerMBean resourceManagerMBean) {
      RCMResourceMBean fileOpen = resourceManagerMBean.getFileOpen();
      if (fileOpen != null) {
         validateRCMResourceFile(fileOpen);
      }

      RCMResourceMBean cpuTime = resourceManagerMBean.getCpuUtilization();
      if (cpuTime != null) {
         validateRCMResourceCpu(cpuTime);
      }

      RCMResourceFairShareMBean fairShareMBean = resourceManagerMBean.getCpuUtilization();
      if (fairShareMBean != null) {
         validateSlowAndFairShare(cpuTime, fairShareMBean);
      }

      RCMResourceMBean heapRetained = resourceManagerMBean.getHeapRetained();
      if (heapRetained != null) {
         validateRCMResourceHeap(heapRetained);
      }

      RCMResourceFairShareMBean fairShareMBean = resourceManagerMBean.getHeapRetained();
      if (fairShareMBean != null) {
         validateSlowAndFairShare(heapRetained, fairShareMBean);
      }

      RestartLoopProtectionMBean restartLoopProtectionMBean = resourceManagerMBean.getRestartLoopProtection();
      if (restartLoopProtectionMBean != null) {
         validateRestartLoopProtection(restartLoopProtectionMBean);
      }

   }

   private static void validateRestartLoopProtection(RestartLoopProtectionMBean restartLoopProtectionMBean) {
      if (restartLoopProtectionMBean.getMaxRestartAllowed() == 0L && restartLoopProtectionMBean.getMaxRestartAllowedInterval() != 0L || restartLoopProtectionMBean.getMaxRestartAllowed() != 0L && restartLoopProtectionMBean.getMaxRestartAllowedInterval() == 0L) {
         String msg = textFormatter.getRestartLoopProtectionError(restartLoopProtectionMBean.getName());
         throw new IllegalArgumentException(msg);
      }
   }

   private static void validateRCMResourceFile(RCMResourceMBean rcmResourceMBean) {
      int failCounter = 0;
      int shutdownCounter = 0;
      int slowCounter = 0;
      int restartCounter = 0;
      TriggerMBean[] triggerMBeans = rcmResourceMBean.getTriggers();
      TriggerMBean[] var6 = triggerMBeans;
      int var7 = triggerMBeans.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         TriggerMBean triggerMBean = var6[var8];
         String action = triggerMBean.getAction();
         if (action.equals("shutdown")) {
            ++shutdownCounter;
         } else if (action.equals("fail")) {
            ++failCounter;
         } else if (action.equals("slow")) {
            ++slowCounter;
         } else if (action.equals("restart")) {
            ++restartCounter;
         }

         String msg;
         if (!validActions1.contains(action)) {
            msg = textFormatter.getInvalidActionForResource(action, triggerMBean.getName(), rcmResourceMBean.getName(), rcmResourceMBean.getType(), Arrays.toString(validation1Array));
            throw new IllegalArgumentException(msg);
         }

         if (failCounter > 0 && shutdownCounter > 0) {
            msg = textFormatter.getFailAndShutdownSpecifiedTogether(rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }

         if (failCounter > 0 && restartCounter > 0) {
            msg = textFormatter.getFailAndRestartSpecifiedTogether(rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }

         if (restartCounter > 0 && shutdownCounter > 0) {
            msg = textFormatter.getRestartAndShutdownSpecifiedTogether(rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }

         if (failCounter > 1 || shutdownCounter > 1 || slowCounter > 1 || restartCounter > 1) {
            msg = textFormatter.getSameActionSpecifiedTwice(action, triggerMBean.getName(), rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }
      }

      validateNotifyAndSlowValues(rcmResourceMBean);
   }

   private static void validateRCMResourceCpu(RCMResourceMBean rcmResourceMBean) {
      if (!isCPUResourceTypeSupported) {
         String msg = textFormatter.getCpuUtilizationNotSupportedOnPlatform(rcmResourceMBean.getName(), rcmResourceMBean.getType());
         throw new IllegalArgumentException(msg);
      } else {
         validateActionValue(rcmResourceMBean);
         validateCpuTimeTriggerValue(rcmResourceMBean);
         validateNotifyAndSlowValues(rcmResourceMBean);
      }
   }

   private static boolean isValidOSBean() {
      boolean isCPUResourceTypeSupported = false;
      OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
      if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
         isCPUResourceTypeSupported = true;
      }

      return isCPUResourceTypeSupported;
   }

   private static void validateRCMResourceHeap(RCMResourceMBean rcmResourceMBean) {
      validateActionValue(rcmResourceMBean);
      validateNotifyAndSlowValues(rcmResourceMBean);
   }

   private static void validateRCMResourceActiveThread(RCMResourceMBean rcmResourceMBean) {
      validateActionValue(rcmResourceMBean);
   }

   private static void validateSlowAndFairShare(RCMResourceMBean rcmResourceMBean, RCMResourceFairShareMBean fairShareMBean) {
      if (fairShareMBean.getFairShareConstraint() != null) {
         TriggerMBean[] triggerMBeans = rcmResourceMBean.getTriggers();
         TriggerMBean[] var3 = triggerMBeans;
         int var4 = triggerMBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TriggerMBean triggerMBean = var3[var5];
            if (triggerMBean.getAction().equals("slow")) {
               String msg = textFormatter.getSlowSpecifiedTogetherForTriggerAndFairShareConstraint(rcmResourceMBean.getName(), triggerMBean.getName(), rcmResourceMBean.getType());
               throw new IllegalArgumentException(msg);
            }
         }
      }

   }

   private static void validateActionValue(RCMResourceMBean rcmResourceMBean) {
      int shutdownCounter = 0;
      int slowCounter = 0;
      int restartCounter = 0;
      TriggerMBean[] triggerMBeans = rcmResourceMBean.getTriggers();
      TriggerMBean[] var5 = triggerMBeans;
      int var6 = triggerMBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         TriggerMBean triggerMBean = var5[var7];
         String action = triggerMBean.getAction();
         if (action.equals("shutdown")) {
            ++shutdownCounter;
         } else if (action.equals("slow")) {
            ++slowCounter;
         } else if (action.equals("restart")) {
            ++restartCounter;
         }

         String msg;
         if (!validActions2.contains(action)) {
            msg = textFormatter.getInvalidActionForResource(action, triggerMBean.getName(), rcmResourceMBean.getName(), rcmResourceMBean.getType(), Arrays.toString(validation2Array));
            throw new IllegalArgumentException(msg);
         }

         if (restartCounter > 0 && shutdownCounter > 0) {
            msg = textFormatter.getRestartAndShutdownSpecifiedTogether(rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }

         if (slowCounter > 1 || shutdownCounter > 1 || restartCounter > 1) {
            msg = textFormatter.getSameActionSpecifiedTwice(action, triggerMBean.getName(), rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }
      }

   }

   private static void validateCpuTimeTriggerValue(RCMResourceMBean rcmResourceMBean) {
      TriggerMBean[] triggerMBeans = rcmResourceMBean.getTriggers();
      TriggerMBean[] var2 = triggerMBeans;
      int var3 = triggerMBeans.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TriggerMBean triggerMBean = var2[var4];
         long value = triggerMBean.getValue();
         if (value > 100L) {
            String msg = textFormatter.getInvalidTriggerValueRange(String.valueOf(value), triggerMBean.getName(), rcmResourceMBean.getName(), rcmResourceMBean.getType());
            throw new IllegalArgumentException(msg);
         }
      }

   }

   private static void validateNotifyAndSlowValues(RCMResourceMBean rcmResourceMBean) {
      String constrainedTriggerName = "";
      String constrainedTriggerType = "";
      long constrainedTriggerValue = Long.MAX_VALUE;
      TriggerMBean[] var5 = rcmResourceMBean.getTriggers();
      int var6 = var5.length;

      int var7;
      TriggerMBean triggerMBean;
      String triggerName;
      for(var7 = 0; var7 < var6; ++var7) {
         triggerMBean = var5[var7];
         triggerName = triggerMBean.getAction();
         if (triggerName.equals("shutdown") || triggerName.equals("restart") || triggerName.equals("fail")) {
            constrainedTriggerName = triggerMBean.getName();
            constrainedTriggerType = triggerMBean.getAction();
            constrainedTriggerValue = triggerMBean.getValue();
            break;
         }
      }

      var5 = rcmResourceMBean.getTriggers();
      var6 = var5.length;

      for(var7 = 0; var7 < var6; ++var7) {
         triggerMBean = var5[var7];
         triggerName = triggerMBean.getName();
         String triggerType = triggerMBean.getAction();
         long triggerValue = triggerMBean.getValue();
         if ((triggerType.equals("notify") || triggerType.equals("slow")) && triggerValue >= constrainedTriggerValue) {
            String msg = textFormatter.getInvalidNotifyOrSLowValues(triggerName, constrainedTriggerName, triggerType, constrainedTriggerType, String.valueOf(triggerValue), String.valueOf(constrainedTriggerValue), rcmResourceMBean.getName());
            throw new IllegalArgumentException(msg);
         }
      }

   }

   static {
      validActions1.add("notify");
      validActions1.add("slow");
      validActions1.add("fail");
      validActions1.add("shutdown");
      validation1Array = new String[validActions1.size()];
      validActions1.toArray(validation1Array);
      validActions2.add("notify");
      validActions2.add("slow");
      validActions2.add("shutdown");
      validation2Array = new String[validActions2.size()];
      validActions2.toArray(validation2Array);
   }
}
