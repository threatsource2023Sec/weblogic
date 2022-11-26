package weblogic.management.configuration;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.internal.VersionInfo;
import weblogic.common.internal.VersioningError;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.WLDFScalingActionBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleModel;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;

@Service
@RunLevel(5)
@Named
public class DomainValidator extends AbstractServerService {
   private static final ManagementConfigValidatorsTextFormatter TXT_FORMATTER = ManagementConfigValidatorsTextFormatter.getInstance();
   private static final String nonSecureAdministrationProtocolsEnabled = "weblogic.nonSecureAdministrationProtocolsEnabled";
   private static boolean enableNonSecureAdministrationProtocols = Boolean.getBoolean("weblogic.nonSecureAdministrationProtocolsEnabled");
   @Inject
   private IterableProvider domainMBeanValidators;
   private static DomainMBeanValidator compositeValidator;

   static void initCompositeValidator(final IterableProvider validators) {
      compositeValidator = new DomainMBeanValidator() {
         public void validate(DomainMBean domain) {
            if (validators != null) {
               Iterator var2 = validators.iterator();

               while(var2.hasNext()) {
                  DomainMBeanValidator validator = (DomainMBeanValidator)var2.next();
                  validator.validate(domain);
               }
            }

         }
      };
   }

   @PostConstruct
   public void start() {
      initCompositeValidator(this.domainMBeanValidators);
   }

   public static void validateDomain(DomainMBean domain) throws IllegalArgumentException {
      if (compositeValidator != null) {
         compositeValidator.validate(domain);
      }

      validateName(domain.getName());
      validateDomainResourceGroup(domain);
      validateUniqueResourceName(domain);
      validateUniqueReferenceToAResourceGroupTemplate(domain);
      validateUniqueVirtualTargetName(domain);
      validateSystemResources(domain.getSystemResources());
      validateSystemComponents(domain.getSystemComponents(), domain);
      validateComponentConfigurations(domain.getSystemComponentConfigurations(), domain);
      CoherenceValidator.validateCoherenceCluster(domain);
      ResourceManagementValidator.validateResourceManagement(domain);
      PartitionWorkManagerValidator.validatePartitionWorkManagers(domain);
      ConcurrentManagedObjectValidator.validateConcurrentManagedObjects(domain);
      validateResourceNames(domain);
      validateWLDFSystemResources(domain);
   }

   private static void validateWLDFSystemResources(DomainMBean domain) {
      Set clusterNames = new HashSet();
      ClusterMBean[] var2 = domain.getClusters();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         ClusterMBean cluster = var2[var4];
         DynamicServersMBean dynamicServers = cluster.getDynamicServers();
         if (dynamicServers.getServerTemplate() != null && dynamicServers.getDynamicClusterSize() > 0) {
            clusterNames.add(cluster.getName());
         }
      }

      WLDFSystemResourceMBean[] var13 = domain.getWLDFSystemResources();
      var3 = var13.length;

      for(var4 = 0; var4 < var3; ++var4) {
         WLDFSystemResourceMBean wldfResource = var13[var4];
         WLDFResourceBean resourceBean = wldfResource.getWLDFResource();
         if (resourceBean != null) {
            WLDFNotificationBean[] var7 = resourceBean.getWatchNotification().getNotifications();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               WLDFNotificationBean action = var7[var9];
               if (action instanceof WLDFScalingActionBean) {
                  WLDFScalingActionBean scalingAction = (WLDFScalingActionBean)action;
                  String actionClusterName = scalingAction.getClusterName();
                  if (!clusterNames.contains(actionClusterName)) {
                     throw new IllegalArgumentException(TXT_FORMATTER.getInvalidWLDFScalingActionText(action.getName(), wldfResource.getName(), actionClusterName));
                  }
               }
            }
         }
      }

   }

   static void validateDomainResourceGroup(DomainMBean domain) {
   }

   public static void validateName(String name) throws IllegalArgumentException {
      if (name == null || name.length() == 0) {
         throw new IllegalArgumentException(TXT_FORMATTER.getNameNotNullOrEmptyMsg());
      }
   }

   private static void validateSystemResources(SystemResourceMBean[] r) throws IllegalArgumentException {
      String[] names = new String[r.length];

      int i;
      for(i = 0; i < r.length; ++i) {
         names[i] = r[i].getName();
      }

      Arrays.sort(names);

      for(i = 0; i < names.length - 1; ++i) {
         if (names[i].equals(names[i + 1])) {
            throw new IllegalArgumentException(TXT_FORMATTER.getSystemResourceNameConflictMsg(names[i]));
         }
      }

   }

   public static void validateVersionString(String version) throws IllegalArgumentException {
      try {
         new VersionInfo(version);
      } catch (Exception var2) {
         throw new IllegalArgumentException("Invalid version string: " + version, var2);
      } catch (VersioningError var3) {
         throw new IllegalArgumentException("Invalid version string: " + version, var3);
      }
   }

   public static void validateAdministrationProtocol(String protocol) throws IllegalArgumentException {
      if (protocol != null && protocol.length() != 0) {
         if (!protocol.equals("t3s") && !protocol.equals("https") && !protocol.equals("iiops")) {
            if (!enableNonSecureAdministrationProtocols || !protocol.equals("t3") && !protocol.equals("http") && !protocol.equals("iiop")) {
               throw new IllegalArgumentException(TXT_FORMATTER.getValidAdminProtocolMsg(protocol));
            }
         }
      } else {
         throw new IllegalArgumentException(TXT_FORMATTER.getAdminProtocolNotNullMsg());
      }
   }

   private static void validateSystemComponents(SystemComponentMBean[] components, DomainMBean domain) throws IllegalArgumentException {
      if (components != null && components.length != 0) {
         for(int i = 0; i < components.length; ++i) {
            String name = components[i].getName();
            if (domain.lookupServer(name) != null) {
               throw new IllegalArgumentException(TXT_FORMATTER.getSystemComponentsMsg(name));
            }
         }

      }
   }

   private static void validateComponentConfigurations(SystemComponentConfigurationMBean[] compConfs, DomainMBean domain) throws IllegalArgumentException {
      if (compConfs != null && compConfs.length != 0) {
         for(int i = 0; i < compConfs.length; ++i) {
            String name = compConfs[i].getName();
            if (domain.lookupServer(name) != null) {
               throw new IllegalArgumentException(TXT_FORMATTER.getComponentConfigurationsMsg(name));
            }
         }

      }
   }

   private static void validateUniqueReferenceToAResourceGroupTemplate(DomainMBean domain) throws IllegalArgumentException {
   }

   private static void validateUniqueResourceName(DomainMBean domain) throws IllegalArgumentException {
   }

   private static void validateUniqueVirtualTargetName(DomainMBean domain) throws IllegalArgumentException {
      VirtualTargetMBean[] vts = domain.getVirtualTargets();
      List pAdminTargets = new ArrayList();
      StringBuilder message = new StringBuilder();
      VirtualTargetMBean[] var4 = vts;
      int var5 = vts.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         VirtualTargetMBean vt = var4[var6];
         if (pAdminTargets.contains(vt.getName())) {
            if (message.length() > 0) {
               message.append(" , ");
            }

            message.append(vt.getName());
         }
      }

      if (message.length() > 0) {
         throw new IllegalArgumentException(TXT_FORMATTER.getcheckVTNameClashWithPartitionAdminVT(message.toString()));
      }
   }

   public static void validateDestroyPartition(PartitionMBean partition) {
      if (!PartitionLifeCycleModel.ALLOW_DESTROY_WO_SHUTDOWN) {
         if (partition == null) {
            throw new IllegalArgumentException(TXT_FORMATTER.getPartitionToDestroyNotNullMsg());
         } else {
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
               String statesToMatch = State.SHUTDOWN.toString() + "|" + State.UNKNOWN.toString();
               DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
               if (domainRuntime != null) {
                  DomainPartitionRuntimeMBean partitionRuntime = domainRuntime.lookupDomainPartitionRuntime(partition.getName());
                  if (partitionRuntime != null) {
                     PartitionLifeCycleRuntimeMBean partitionLifeCycleRuntime = partitionRuntime.getPartitionLifeCycleRuntime();
                     if (partitionLifeCycleRuntime != null) {
                        String partitionState = partitionLifeCycleRuntime.getState();
                        if (!partitionState.matches(statesToMatch)) {
                           throw new IllegalArgumentException(TXT_FORMATTER.getPartitionToDestroyShutdownOrHaltedMsg(partition.getName(), partitionState));
                        }
                     }
                  }
               }

            }
         }
      }
   }

   public static void validateDestroyResourceGroup(ResourceGroupMBean resourceGroup) {
      if (!PartitionLifeCycleModel.ALLOW_DESTROY_WO_SHUTDOWN) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
            DomainRuntimeMBean domainRuntime = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            if (domainRuntime != null) {
               ConfigurationMBean parent = (ConfigurationMBean)resourceGroup.getParent();
               if (parent != null && parent instanceof PartitionMBean) {
                  DomainPartitionRuntimeMBean partitionRuntime = domainRuntime.lookupDomainPartitionRuntime(parent.getName());
                  if (partitionRuntime != null) {
                     PartitionLifeCycleRuntimeMBean partitionLifeCycleRuntime = partitionRuntime.getPartitionLifeCycleRuntime();
                     if (partitionLifeCycleRuntime != null) {
                        ResourceGroupLifeCycleRuntimeMBean resourceGroupLifeCycleRuntime = partitionLifeCycleRuntime.lookupResourceGroupLifeCycleRuntime(resourceGroup.getName());
                        validateResourceGroupStateForDestroy(resourceGroupLifeCycleRuntime);
                     }
                  }
               } else if (parent != null && parent instanceof DomainMBean) {
                  ResourceGroupLifeCycleRuntimeMBean resourceGroupLifeCycleRuntime = domainRuntime.lookupResourceGroupLifeCycleRuntime(resourceGroup.getName());
                  validateResourceGroupStateForDestroy(resourceGroupLifeCycleRuntime);
               }
            }

         }
      }
   }

   private static void validateResourceGroupStateForDestroy(ResourceGroupLifeCycleRuntimeMBean resourceGroupLifeCycleRuntime) throws IllegalArgumentException {
      if (resourceGroupLifeCycleRuntime != null) {
         String resourceGroupState = resourceGroupLifeCycleRuntime.getState();
         String statesToMatch = State.SHUTDOWN.toString() + "|" + State.UNKNOWN.toString();
         if (!resourceGroupState.matches(statesToMatch)) {
            throw new IllegalArgumentException(TXT_FORMATTER.getRGToDestroyShutdownMsg(resourceGroupLifeCycleRuntime.getName(), resourceGroupState));
         }
      }

   }

   private static void validateResourceNames(DomainMBean domain) throws IllegalArgumentException {
   }

   private static void validateDollarSignNamePartitionName(Set partitionNames, String name) throws IllegalArgumentException {
      String afterDollar = getDollarSignName(name);
      if (afterDollar != null && partitionNames.contains(afterDollar)) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckAmbiguousResNameMsg(afterDollar, name, afterDollar));
      }
   }

   private static String getDollarSignName(String name) {
      String afterDollar = null;
      int beginIndex = name.indexOf(36);
      if (beginIndex > -1 && name.length() > beginIndex) {
         ++beginIndex;
         afterDollar = name.substring(beginIndex);
      }

      return afterDollar;
   }
}
