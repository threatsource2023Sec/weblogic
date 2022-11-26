package weblogic.management.mbeanservers;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanInfo;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.CoherencePartitionCacheConfigMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RealmMBean;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.MBeanVisibilityResult;
import weblogic.management.visibility.WLSMBeanVisibility;
import weblogic.management.visibility.utils.MBeanNameUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class MBeanPartitionUtil {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final DebugLogger debug = DebugLogger.getDebugLogger("DebugPartitionJMX");
   public static final boolean jdkMBeansVisibleToPartitions = new Boolean(System.getProperty("JDKMBeansVisibleToPartitions", "true"));

   public static boolean isReservedMBean(ObjectName objectName) {
      return isGlobalWLSMBean(objectName) || MBeanNameUtil.isCoherenceMBean(objectName);
   }

   public static boolean isReservedMBeanInRevisedModel(ObjectName objectName) {
      if (MBeanNameUtil.isCoherenceMBean(objectName)) {
         return true;
      } else if (getApplicationId() != null && !isGlobalRuntime()) {
         return false;
      } else {
         return MBeanNameUtil.isWLSMBean(objectName) || isGlobalRuntime();
      }
   }

   public static boolean isGlobalMBean(ObjectName oname) {
      if (MBeanNameUtil.isWLSMBean(oname)) {
         if (oname.getKeyProperty("Partition") != null || oname.getKeyProperty("PartitionRuntime") != null || oname.getKeyProperty("DomainPartitionRuntime") != null || MBeanNameUtil.getPartitionNameFromParentKey(oname) != null) {
            return false;
         }
      } else if (oname.getKeyProperty("Partition") != null || oname.getKeyProperty("domainPartition") != null) {
         return false;
      }

      return true;
   }

   public static boolean isGlobalWLSMBean(ObjectName objectName) {
      return MBeanNameUtil.isWLSMBean(objectName) && (getApplicationId() == null || MBeanNameUtil.containsDecorator(objectName));
   }

   public static boolean isAppMBeanUsingWLSDomain(ObjectName objectName) {
      return !MBeanNameUtil.isWLSMBean(objectName) || getApplicationId() != null && !MBeanNameUtil.containsDecorator(objectName);
   }

   public static String getApplicationId() {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
      return cic.getApplicationId();
   }

   public static boolean isGlobalRuntime() {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
      return cic.isGlobalRuntime();
   }

   public static Set getCoherenceMBeanPartitions(ObjectName name) {
      Set partitions = new HashSet();
      PartitionMBean[] partitionMBeans = null;
      return partitions;
   }

   public static boolean showCoherenceMBeanInPartitionContext(String partitionName, ObjectName objectName) {
      if (MBeanNameUtil.isCoherenceMBean(objectName)) {
         if (MBeanNameUtil.isCoherenceMBeanInSamePartition(partitionName, objectName)) {
            return true;
         }

         String appName = getAppNameFromCoherenceSharedServiceMBean(objectName);
         if (appName != null) {
            AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(kernelId);
            return "oracle.coherence.web".equals(appName) && (SecurityServiceManager.isKernelIdentity(currentSubject) || SecurityServiceManager.isServerIdentity(currentSubject)) || isCoherenceSharedMBeanBelongToCurrentPartition(partitionName, appName);
         }
      }

      return false;
   }

   public static boolean shouldShowCoherenceMBeanToPartitionUser(String partitionName, ObjectName objectName) {
      if (MBeanNameUtil.isCoherenceMBean(objectName)) {
         if (showCoherenceMBeanInPartitionContext(partitionName, objectName)) {
            return true;
         }

         if (!MBeanNameUtil.isCoherenceMBeanInSamePartition(partitionName, objectName)) {
            return false;
         }
      }

      return true;
   }

   public static Set getVTMBeanPartitions(ObjectName name) {
      Set partitions = new HashSet();
      return partitions;
   }

   public static boolean isValidVirtualTargetForPartition(String partitionName, ObjectName oname) {
      if (MBeanNameUtil.isWLSVirtualTargetMBean(oname)) {
         String[] targetNames = getAvailableTargetForPartition(partitionName);
         String type;
         if (MBeanNameUtil.isWLSVirtualTargetMBean(oname)) {
            type = oname.getKeyProperty("Type");
            if (type != null && type.equals("VirtualTarget")) {
               String name = oname.getKeyProperty("Name");
               if (name != null && MBeanNameUtil.isAvailableTarget(name, targetNames)) {
                  return true;
               }
            }
         }

         type = oname.getKeyProperty("VirtualTarget");
         return type != null && MBeanNameUtil.isAvailableTarget(type, targetNames);
      } else {
         return false;
      }
   }

   public static String[] getAvailableTargetForPartition(String partitionName) {
      PartitionMBean partitionMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      if (partitionMBean == null) {
         return new String[0];
      } else {
         TargetMBean[] targetMBeans;
         try {
            targetMBeans = partitionMBean.getAvailableTargets();
         } catch (Exception var5) {
            targetMBeans = new TargetMBean[0];
         }

         ArrayList nameList = new ArrayList();

         for(int i = 0; i < targetMBeans.length; ++i) {
            nameList.add(targetMBeans[i].getName());
         }

         return (String[])nameList.toArray(new String[nameList.size()]);
      }
   }

   public static AttributeList rebuildReturnedAttribuuteListForPartition(String partitionName, AttributeList attrList) {
      if (partitionName != null && !partitionName.equals("DOMAIN")) {
         AttributeList newAttrList = new AttributeList();

         for(int i = 0; i < attrList.size(); ++i) {
            Attribute newAttr = null;
            Attribute attr = (Attribute)attrList.get(i);
            Object oldAttrValue = attr.getValue();
            if (oldAttrValue == null) {
               newAttrList.add(attr);
            } else {
               Object newAttrValue = getObjectInPartitionContext(partitionName, oldAttrValue);
               if (newAttrValue != null) {
                  newAttr = new Attribute(attr.getName(), newAttrValue);
               }

               if (newAttr != null) {
                  newAttrList.add(newAttr);
               }
            }
         }

         return newAttrList;
      } else {
         return attrList;
      }
   }

   public static Object getObjectInPartitionContext(String partitionName, Object oldAttrValue) {
      if (partitionName != null && !partitionName.equals("DOMAIN")) {
         Object newAttrValue = null;
         Set filteredSet;
         Iterator iter;
         if (oldAttrValue instanceof ObjectName) {
            filteredSet = Collections.synchronizedSet(new HashSet());
            buildQueryResultSetForPartition(filteredSet, partitionName, oldAttrValue, (ObjectName)oldAttrValue);
            iter = filteredSet.iterator();
            if (iter.hasNext()) {
               newAttrValue = iter.next();
            } else {
               newAttrValue = null;
            }
         } else if (oldAttrValue instanceof ObjectInstance) {
            filteredSet = Collections.synchronizedSet(new HashSet());
            buildQueryResultSetForPartition(filteredSet, partitionName, oldAttrValue, ((ObjectInstance)oldAttrValue).getObjectName());
            iter = filteredSet.iterator();
            if (iter.hasNext()) {
               newAttrValue = iter.next();
            } else {
               newAttrValue = null;
            }
         } else {
            int j;
            HashSet filteredSet;
            Iterator iter;
            ArrayList newAttrValueList;
            if (oldAttrValue instanceof ObjectName[]) {
               newAttrValueList = new ArrayList();
               ObjectName[] oldAttrValueArray = (ObjectName[])((ObjectName[])oldAttrValue);

               for(j = 0; j < oldAttrValueArray.length; ++j) {
                  filteredSet = new HashSet();
                  buildQueryResultSetForPartition(filteredSet, partitionName, oldAttrValueArray[j], oldAttrValueArray[j]);
                  iter = filteredSet.iterator();
                  if (iter.hasNext()) {
                     newAttrValueList.add((ObjectName)iter.next());
                  }
               }

               newAttrValue = newAttrValueList.toArray(new ObjectName[newAttrValueList.size()]);
            } else if (oldAttrValue instanceof ObjectInstance[]) {
               newAttrValueList = new ArrayList();
               ObjectInstance[] oldAttrValueArray = (ObjectInstance[])((ObjectInstance[])oldAttrValue);

               for(j = 0; j < oldAttrValueArray.length; ++j) {
                  filteredSet = new HashSet();
                  buildQueryResultSetForPartition(filteredSet, partitionName, oldAttrValueArray[j], oldAttrValueArray[j].getObjectName());
                  iter = filteredSet.iterator();
                  if (iter.hasNext()) {
                     newAttrValueList.add((ObjectInstance)iter.next());
                  }
               }

               newAttrValue = newAttrValueList.toArray(new ObjectInstance[newAttrValueList.size()]);
            } else {
               newAttrValue = oldAttrValue;
            }
         }

         return newAttrValue;
      } else {
         return oldAttrValue;
      }
   }

   public static MBeanVisibilityResult getMBeanVisibility(ObjectName oname, MBeanInfo info) {
      boolean isAnnotated;
      if (!MBeanNameUtil.isSecurityMBean(oname)) {
         if (MBeanNameUtil.isJDKMBean(oname)) {
            return new MBeanVisibilityResult(jdkMBeansVisibleToPartitions ? WLSMBeanVisibility.ALL : WLSMBeanVisibility.NONE, MBeanType.JDK);
         } else if (MBeanNameUtil.isCoherenceMBean(oname)) {
            return new MBeanVisibilityResult(WLSMBeanVisibility.SOME, MBeanType.WLS_COHERENCE);
         } else if (MBeanNameUtil.isWLSMBean(oname)) {
            if (MBeanNameUtil.isGlobalMBean(oname)) {
               isAnnotated = MBeanInfoBuilder.sureMBeanVisibleToPartitions(oname, info);
               if (MBeanNameUtil.isWLSDeploymentRuntime(oname)) {
                  return new MBeanVisibilityResult(isAnnotated ? WLSMBeanVisibility.SOME : WLSMBeanVisibility.NONE, MBeanType.WLS_DEPLOYMENT_RUNTIME);
               } else if (MBeanNameUtil.isWLSPartitionConfigurationMBean(oname)) {
                  return new MBeanVisibilityResult(isAnnotated ? WLSMBeanVisibility.SOME : WLSMBeanVisibility.NONE, MBeanType.WLS_CONFIGURATION_RUNTIME);
               } else {
                  return MBeanNameUtil.isWLSVirtualTargetMBean(oname) ? new MBeanVisibilityResult(isAnnotated ? WLSMBeanVisibility.SOME : WLSMBeanVisibility.NONE, MBeanType.WLS_VIRTUAL_TARGET) : new MBeanVisibilityResult(isAnnotated ? WLSMBeanVisibility.ALL : WLSMBeanVisibility.NONE, MBeanType.WLS_GLOBAL_OTHER);
               }
            } else {
               return new MBeanVisibilityResult(WLSMBeanVisibility.SOME, MBeanType.WLS_PARTITION);
            }
         } else {
            return MBeanNameUtil.containsDecorator(oname) ? new MBeanVisibilityResult(WLSMBeanVisibility.SOME, MBeanType.JRF_PARTITION) : new MBeanVisibilityResult(MBeanInfoBuilder.sureMBeanVisibleToPartitions(oname, info) ? WLSMBeanVisibility.ALL : WLSMBeanVisibility.NONE, MBeanType.JRF_GLOBAL);
         }
      } else {
         isAnnotated = MBeanInfoBuilder.sureMBeanVisibleToPartitions(oname, info);
         return new MBeanVisibilityResult(isAnnotated ? WLSMBeanVisibility.SOME : WLSMBeanVisibility.NONE, MBeanType.SECURITY);
      }
   }

   public static boolean isMBeanVisibleToPartition(ObjectName oname, String partitionName, MBeanInfo info) {
      if (MBeanNameUtil.isSecurityMBean(oname)) {
         return realmMatchesPartitionRealm(partitionName, oname);
      } else if (MBeanNameUtil.isJDKMBean(oname)) {
         return jdkMBeansVisibleToPartitions;
      } else if (MBeanNameUtil.isCoherenceMBean(oname)) {
         return MBeanNameUtil.isCoherenceMBeanInSamePartition(partitionName, oname);
      } else if (MBeanNameUtil.isWLSMBean(oname)) {
         if (!MBeanNameUtil.isGlobalMBean(oname)) {
            return MBeanNameUtil.isMBeanInSamePartition(partitionName, oname);
         } else {
            boolean isAnnotated = MBeanInfoBuilder.sureMBeanVisibleToPartitions(oname, info);
            if (MBeanNameUtil.isWLSDeploymentRuntime(oname)) {
               return isAnnotated && MBeanNameUtil.isPartitionOwnedWLSDeploymentMBeanInSamePartition(partitionName, oname);
            } else if (MBeanNameUtil.isWLSPartitionConfigurationMBean(oname)) {
               return isAnnotated && MBeanNameUtil.isWLSPartitionConfigurationMBeanInSamePartition(partitionName, oname);
            } else if (MBeanNameUtil.isWLSVirtualTargetMBean(oname)) {
               return isAnnotated && isValidVirtualTargetForPartition(partitionName, oname);
            } else if (!MBeanNameUtil.isWLSRealmRuntimeMBean(oname)) {
               return isAnnotated;
            } else {
               return isAnnotated && isValidRealmRuntimeForPartition(partitionName, oname);
            }
         }
      } else {
         return MBeanNameUtil.containsDecorator(oname) ? MBeanNameUtil.isMBeanInSamePartition(partitionName, oname) : MBeanInfoBuilder.sureMBeanVisibleToPartitions(oname, info);
      }
   }

   public static void buildQueryResultSetForPartition(Set filteredSet, String partitionName, Object object, ObjectName oname) {
      if ("Security".equals(oname.getDomain())) {
         if (realmMatchesPartitionRealm(partitionName, oname)) {
            filteredSet.add(object);
         }

      } else if (jdkMBeansVisibleToPartitions || !MBeanNameUtil.isJDKMBean(oname)) {
         if (MBeanNameUtil.isCoherenceMBean(oname)) {
            if (MBeanNameUtil.isGlobalMBean(oname) || MBeanNameUtil.isCoherenceMBeanInSamePartition(partitionName, oname)) {
               filteredSet.add(object);
            }
         } else if (MBeanNameUtil.isWLSMBean(oname) && JMXContextUtil.getPartitionNameForMBean(oname).equals("DOMAIN") && JMXContextUtil.getMBeanCIC(oname) != null && JMXContextUtil.getMBeanCIC(oname).getApplicationId() == null) {
            if (MBeanNameUtil.isGlobalMBean(oname) && !MBeanNameUtil.isWLSDeploymentRuntime(oname) && !MBeanNameUtil.isWLSPartitionConfigurationMBean(oname) && !MBeanNameUtil.isWLSVirtualTargetMBean(oname) && !MBeanNameUtil.isWLSRealmRuntimeMBean(oname) && !MBeanNameUtil.isResourceGroupMBean(oname)) {
               filteredSet.add(object);
            } else if (MBeanNameUtil.isWLSMBeanInSamePartition(partitionName, oname) || MBeanNameUtil.isPartitionOwnedWLSDeploymentMBeanInSamePartition(partitionName, oname) || MBeanNameUtil.isWLSPartitionConfigurationMBeanInSamePartition(partitionName, oname) || isValidVirtualTargetForPartition(partitionName, oname) || isValidRealmRuntimeForPartition(partitionName, oname) || MBeanNameUtil.isResourceGroupMBeanInSamePartition(oname, getRGNamesInCurrentPartition(partitionName))) {
               filteredSet.add(object);
            }
         } else {
            ComponentInvocationContext mbeanCIC = JMXContextUtil.getMBeanCIC(oname);
            if (mbeanCIC == null) {
               filteredSet.add(object);
            } else {
               String partitionNameFromCache = mbeanCIC.getPartitionName();
               if (partitionNameFromCache != null && (!partitionNameFromCache.equals("DOMAIN") || mbeanCIC.getApplicationId() != null)) {
                  if (partitionNameFromCache == null || partitionNameFromCache.equals(partitionName)) {
                     if (object instanceof ObjectInstance) {
                        ObjectInstance oInstance = new ObjectInstance(PartitionDecorator.removePartitionFromObjectName(oname), ((ObjectInstance)object).getClassName());
                        filteredSet.add(oInstance);
                     } else {
                        filteredSet.add(PartitionDecorator.removePartitionFromObjectName(oname));
                     }
                  }
               } else {
                  filteredSet.add(object);
               }
            }
         }
      }
   }

   public static String[] getRGNamesInCurrentPartition(String partitionName) {
      ResourceGroupMBean[] rgMBeans = getResourceGroupMBeansInCurrentPartition(partitionName);
      if (rgMBeans == null) {
         return null;
      } else {
         ArrayList nameList = new ArrayList();

         for(int i = 0; i < rgMBeans.length; ++i) {
            nameList.add(rgMBeans[i].getName());
         }

         return (String[])nameList.toArray(new String[nameList.size()]);
      }
   }

   public static ResourceGroupMBean[] getResourceGroupMBeansInCurrentPartition(String partitionName) {
      return null;
   }

   public static CoherencePartitionCacheConfigMBean[] getCoherenceCacheConfigMBeans(String partitionName) {
      return null;
   }

   public static String getAppNameFromCoherenceSharedServiceMBean(ObjectName objectName) {
      if (!MBeanNameUtil.isCoherenceMBean(objectName)) {
         return null;
      } else {
         String type = objectName.getKeyProperty("type");
         String service;
         int index;
         if (type != null && (type.equals("Service") || type.equals("ConnectionManager"))) {
            service = objectName.getKeyProperty("name");
            index = service.indexOf(":");
            if (service.startsWith("\"")) {
               return index != -1 ? service.substring(1, index) : service.substring(1, service.length() - 1);
            } else {
               return index != -1 ? service.substring(0, index) : service;
            }
         } else {
            service = objectName.getKeyProperty("service");
            if (service != null) {
               index = service.indexOf(":");
               if (service.startsWith("\"")) {
                  return index != -1 ? service.substring(1, index) : service.substring(1, service.length() - 1);
               } else {
                  return index != -1 ? service.substring(0, index) : service;
               }
            } else {
               return null;
            }
         }
      }
   }

   public static boolean isCoherenceSharedMBeanBelongToCurrentPartition(String partitionName, String appName) {
      CoherencePartitionCacheConfigMBean[] cacheConfigs = getCoherenceCacheConfigMBeans(partitionName);
      if (cacheConfigs != null && cacheConfigs.length != 0) {
         for(int i = 0; i < cacheConfigs.length; ++i) {
            CoherencePartitionCacheConfigMBean cacheConfig = cacheConfigs[i];
            if (cacheConfig.getApplicationName().equals(appName) && cacheConfig.isShared()) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean isNonAppMBeanRegistered(ObjectName objectName, String partitionName) {
      HashSet resultSet = new HashSet();
      buildQueryResultSetForPartition(resultSet, partitionName, objectName, objectName);
      if (!resultSet.isEmpty()) {
         if (debug.isDebugEnabled()) {
            debug.debug("Calling isNonAppMBeanRegistered(): the non-app MBean is visible to partitions, returns true : " + objectName);
         }

         return true;
      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("Calling isNonAppMBeanRegistered(): the non-app MBean is not visible to partitions, return false : " + objectName);
         }

         return false;
      }
   }

   private static boolean realmMatchesPartitionRealm(String partitionName, ObjectName objectName) {
      PartitionMBean partition = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      SecurityConfigurationMBean secCfg = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration();
      String name = objectName.getKeyProperty("Name");
      if (partition != null && name != null) {
         RealmMBean realm = partition.getRealm();
         if (realm == null) {
            realm = secCfg.getDefaultRealm();
         }

         String realmName = realm.getName();
         if (name.startsWith(realmName) && realm.getManagementIdentityDomain() != null && !realm.getManagementIdentityDomain().isEmpty()) {
            if (name.equals(realmName)) {
               return true;
            } else {
               String providerName = name.substring(realmName.length());
               return realm.lookupAuditor(providerName) != null || realm.lookupAuthenticationProvider(providerName) != null || realm.lookupAuthorizer(providerName) != null || realm.lookupCredentialMapper(providerName) != null || realm.lookupCertPathProvider(providerName) != null || realm.lookupPasswordValidator(providerName) != null || realm.lookupRoleMapper(providerName) != null || realm.getAdjudicator().getName().equals(providerName) || realm.getUserLockoutManager().getName().equals(providerName);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean isValidRealmRuntimeForPartition(String partitionName, ObjectName oname) {
      if (!MBeanNameUtil.isWLSRealmRuntimeMBean(oname)) {
         return false;
      } else {
         PartitionMBean partition = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
         SecurityConfigurationMBean secCfg = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration();
         RealmMBean realm = partition.getRealm();
         if (realm == null) {
            realm = secCfg.getDefaultRealm();
         }

         String realmName = realm.getName();
         String realmRuntimeName = oname.getKeyProperty("RealmRuntime");
         if (realmRuntimeName == null) {
            String type = oname.getKeyProperty("Type");
            if ("RealmRuntime".equals(type)) {
               realmRuntimeName = oname.getKeyProperty("Name");
            }
         }

         return realmRuntimeName != null && realmRuntimeName.equals(realmName) && realm.getManagementIdentityDomain() != null && !realm.getManagementIdentityDomain().isEmpty();
      }
   }
}
