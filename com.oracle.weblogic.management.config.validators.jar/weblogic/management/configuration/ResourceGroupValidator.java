package weblogic.management.configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.ResourceGroupMultitargetingUtils;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ResourceGroupValidator {
   private static final ManagementConfigValidatorsTextFormatter TXT_FORMATTER = ManagementConfigValidatorsTextFormatter.getInstance();
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugPartitionJMX");

   public static void validateResourceGroup(ResourceGroupMBean resourceGroup) throws IllegalArgumentException {
      resourceGroup = (ResourceGroupMBean)Objects.requireNonNull(resourceGroup);

      try {
         validateRGName(resourceGroup);
         validateResourceTargeting(resourceGroup);
         validateMultitargeting(resourceGroup);
         validateAutoTargetAdminServerFlag(resourceGroup);
      } catch (InvocationTargetException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static void validateSetResourceGroupTemplate(ResourceGroupTemplateMBean rgt) {
      if (rgt != null && rgt instanceof ResourceGroupMBean) {
         String msg = TXT_FORMATTER.getSetRGTValidationMsg(rgt.getName());
         throw new IllegalArgumentException(msg);
      }
   }

   public static void validateDefaultTargetFlag(ResourceGroupMBean resourceGroup) throws IllegalArgumentException {
      ConfigurationMBean parent = (ConfigurationMBean)resourceGroup.getParent();
      if (parent != null && parent instanceof PartitionMBean && resourceGroup.isUseDefaultTarget() && resourceGroup.getTargets().length > 0) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckDefaultTargetFlagMsg());
      }
   }

   public static void validateAutoTargetAdminServerFlag(ResourceGroupMBean resourceGroup) throws IllegalArgumentException {
      ConfigurationMBean parent = (ConfigurationMBean)resourceGroup.getParent();
      if (parent != null && parent instanceof PartitionMBean) {
         if (resourceGroup.isUseDefaultTarget() && resourceGroup.isAutoTargetAdminServer()) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckDefaultTargetFlagAutoTargetMsg());
         }

         TargetMBean[] var2 = resourceGroup.findEffectiveTargets();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            if (target != null && resourceGroup.isAutoTargetAdminServer() && !target.equals(((PartitionMBean)parent).getAdminVirtualTarget()) && target.getServerNames().contains(((DomainMBean)parent.getParent()).getAdminServerName())) {
               throw new IllegalArgumentException(TXT_FORMATTER.getcheckAutoTargetAdminFlagWithExplicitTarget(resourceGroup.getName(), target.getName()));
            }
         }
      }

   }

   public static void validateAddTarget(ResourceGroupMBean resourceGroup, TargetMBean targetToAdd) throws IllegalArgumentException {
      resourceGroup = (ResourceGroupMBean)Objects.requireNonNull(resourceGroup);
      ConfigurationMBean parent = (ConfigurationMBean)resourceGroup.getParent();
      if (parent != null && parent instanceof PartitionMBean) {
         PartitionMBean partition = (PartitionMBean)parent;
         TargetMBean[] targets = partition.getAvailableTargets();
         if (targets == null) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckRGAddTargetMsg(targetToAdd.getName(), resourceGroup.getName(), partition.getName(), "any"));
         }

         if (targets.length <= 0) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckRGAddTargetMsg(targetToAdd.getName(), resourceGroup.getName(), partition.getName(), "any"));
         }

         List availableTargets = Arrays.asList(targets);
         if (availableTargets != null && availableTargets.size() > 0 && !availableTargets.contains(targetToAdd)) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckRGAddTargetMsg(targetToAdd.getName(), resourceGroup.getName(), partition.getName(), targetToAdd.getName()));
         }

         if (targetToAdd != null) {
            resourceGroup.setUseDefaultTarget(false);
         }
      }

      TargetMBean[] targets = resourceGroup.findEffectiveTargets();
      String target = ResourceGroupMultitargetingUtils.sharePhysicalServer(targets, targetToAdd);
      if (target != null) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckRGMultiTargetMsg(resourceGroup.getType(), resourceGroup.getName(), targetToAdd.getName(), target));
      } else {
         if (parent != null && parent instanceof DomainMBean) {
            PartitionMBeanValidator.validateTargetNotSetForOtherPartition((DomainMBean)parent, (PartitionMBean)null, targetToAdd);
         }

      }
   }

   public static void validateSetTargets(ResourceGroupMBean resourceGroup, TargetMBean[] targetsToSet) throws IllegalArgumentException {
      if (targetsToSet != null) {
         TargetMBean[] var2 = targetsToSet;
         int var3 = targetsToSet.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            validateAddTarget(resourceGroup, target);
         }
      }

   }

   public static void validateMultitargeting(ResourceGroupMBean resourceGroupMBean) throws IOException, InvocationTargetException, IllegalAccessException {
      if (resourceGroupMBean.findEffectiveTargets().length > 1) {
         new ArrayList();
         List blResources = ResourceGroupMultitargetingUtils.findBlacklistedResources(resourceGroupMBean);
         if (blResources.size() > 0) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckBlackListResMsg(resourceGroupMBean.getName(), blResources.toString()));
         }
      }

   }

   public static void validateRGName(ResourceGroupMBean rg) {
      String name = rg.getName();
      if (name.contains("$")) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckNameNotContainDollarSignMsg(rg.getType()));
      }
   }

   private static void validateResourceTargeting(ResourceGroupMBean resourceGroup) {
      TargetMBean[] targetsToSet = resourceGroup.getTargets();
      int var3;
      int var4;
      if (targetsToSet != null) {
         TargetMBean[] var2 = targetsToSet;
         var3 = targetsToSet.length;

         for(var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            validateAddTarget(resourceGroup, target);
         }
      }

      DeploymentMBean[] var6 = resourceGroup.getDeployments();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         DeploymentMBean deployable = var6[var4];
         if (deployable.getTargets().length > 0) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckResIndTargetMsg(deployable.getName(), resourceGroup.getName()));
         }
      }

      BasicDeploymentMBean[] var7 = resourceGroup.getBasicDeployments();
      var3 = var7.length;

      for(var4 = 0; var4 < var3; ++var4) {
         BasicDeploymentMBean deployable = var7[var4];
         if (deployable.getTargets().length > 0) {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckResIndTargetMsg(deployable.getName(), resourceGroup.getName()));
         }
      }

   }

   public static void validateRemoveTargetFromRG(ResourceGroupMBean resourceGroup, TargetMBean[] targetsToRemove) throws BeanUpdateRejectedException {
      if (ManagementService.isRuntimeAccessInitialized()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         DomainMBean domain = null;
         if (kernelId != null && ManagementService.getPropertyService(kernelId) != null && ManagementService.getPropertyService(kernelId).isAdminServer()) {
            ConfigurationMBean parent = (ConfigurationMBean)resourceGroup.getParent();
            if (parent != null) {
               ResourceGroupLifeCycleRuntimeMBean rgLCRMBean = null;
               if (parent instanceof DomainMBean) {
                  rgLCRMBean = ManagementService.getDomainAccess(kernelId).lookupResourceGroupLifeCycleRuntime(resourceGroup.getName());
                  domain = (DomainMBean)parent;
               } else if (parent instanceof PartitionMBean) {
                  rgLCRMBean = ManagementService.getDomainAccess(kernelId).lookupDomainPartitionRuntime(resourceGroup.getParent().getName()).getPartitionLifeCycleRuntime().lookupResourceGroupLifeCycleRuntime(resourceGroup.getName());
                  domain = (DomainMBean)parent.getParent();
               }

               if (rgLCRMBean != null) {
                  StringBuilder msgString = new StringBuilder();
                  String sep = "";
                  Set RGRunningTargList = new HashSet();
                  TargetMBean[] var9 = targetsToRemove;
                  int var10 = targetsToRemove.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     TargetMBean target = var9[var11];
                     Set serverNames = target.getServerNames();
                     if (serverNames != null) {
                        Set serverNamesWhereRGIsRunning = new HashSet();
                        Iterator var15 = serverNames.iterator();

                        while(var15.hasNext()) {
                           String serverName = (String)var15.next();
                           ServerMBean server = domain.lookupServer(serverName);

                           try {
                              if (RGState.isRunning(rgLCRMBean.getState(server))) {
                                 serverNamesWhereRGIsRunning.add(serverName);
                              }
                           } catch (ResourceGroupLifecycleException var19) {
                              if (DEBUG_LOGGER.isDebugEnabled()) {
                                 DEBUG_LOGGER.debug("Cannot get state of the partition on server " + serverName + ".", var19);
                              }
                           } catch (Exception var20) {
                              throw new BeanUpdateRejectedException(var20.getMessage(), var20);
                           }
                        }

                        if (serverNamesWhereRGIsRunning.size() > 0) {
                           msgString.append(sep);
                           msgString.append(target.getName());
                           msgString.append(serverNamesWhereRGIsRunning.toString());
                           sep = ",";
                           RGRunningTargList.add(target);
                        }
                     }
                  }

                  if (RGRunningTargList.size() > 0) {
                     throw new BeanUpdateRejectedException(TXT_FORMATTER.getCheckDelTargetWhenRGisRunning(msgString.toString(), resourceGroup.getName()));
                  }
               }
            }
         }
      }

   }
}
