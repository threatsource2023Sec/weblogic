package weblogic.deploy.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ModuleListener;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.TargetHelperInterface;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.TargetingUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.collections.ArraySet;

public class TargetHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final ServerMBean localServer;

   public static String[] getModulesForTarget(DeploymentData data, DomainMBean proposedDomain) {
      if (data.isTargetsFromConfig()) {
         return null;
      } else if (data.hasGlobalTarget(localServer.getName())) {
         return null;
      } else {
         Map moduleTargetSpecs = data.getAllModuleTargets();
         Iterator it = moduleTargetSpecs.keySet().iterator();
         ArrayList result = new ArrayList();

         while(it.hasNext()) {
            String moduleTargetSpec = (String)it.next();
            String[] thismodulesTargets = (String[])((String[])moduleTargetSpecs.get(moduleTargetSpec));
            if (targetsContainLocalServer(thismodulesTargets, proposedDomain)) {
               result.add(moduleTargetSpec);
            }
         }

         if (result.size() > 0) {
            return (String[])((String[])result.toArray(new String[0]));
         } else {
            return null;
         }
      }
   }

   private static boolean targetsContainLocalServer(String[] targetNames, DomainMBean proposedDomain) {
      TargetMBean[] targets;
      try {
         DomainMBean domain = proposedDomain;
         if (proposedDomain == null) {
            domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         }

         targets = lookupTargetMBeans(domain, targetNames);
      } catch (InvalidTargetException var4) {
         throw new AssertionError(var4);
      } catch (Exception var5) {
         throw new AssertionError(var5);
      }

      return TargetUtils.isDeployedLocally(targets);
   }

   public static boolean isAppTargetedToCurrentServer(BasicDeploymentMBean mbean) {
      TargetMBean[] appTargets = mbean.getTargets();
      String thisServer = localServer.getName();
      Set allRealTargets = new ArraySet();

      for(int i = 0; i < appTargets.length; ++i) {
         allRealTargets.addAll(appTargets[i].getServerNames());
      }

      return allRealTargets.contains(thisServer) ? true : areSubDeploymentsTargetedToCurrentServer(mbean);
   }

   private static boolean areSubDeploymentsTargetedToCurrentServer(BasicDeploymentMBean mbean) {
      List allSubDeployments = getAllSubDeployments(mbean);
      String thisServer = localServer.getName();
      Iterator iter = allSubDeployments.iterator();

      while(true) {
         SubDeploymentMBean eachDep;
         do {
            if (!iter.hasNext()) {
               return false;
            }

            eachDep = (SubDeploymentMBean)iter.next();
         } while(eachDep == null);

         TargetMBean[] subTargets = eachDep.getTargets();

         for(int i = 0; i < subTargets.length; ++i) {
            Set eachTargetNames = subTargets[i].getServerNames();
            if (eachTargetNames.contains(thisServer)) {
               return true;
            }
         }
      }
   }

   private static List getAllSubDeployments(BasicDeploymentMBean dep) {
      List allDeployments = new ArrayList();
      SubDeploymentMBean[] subDeployments = dep.getSubDeployments();
      if (subDeployments != null && subDeployments.length != 0) {
         for(int i = 0; i < subDeployments.length; ++i) {
            allDeployments.add(subDeployments[i]);
            List innerSubDeployments = getAllSubDeployments(subDeployments[i]);
            allDeployments.addAll(innerSubDeployments);
         }

         return allDeployments;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   private static List getAllSubDeployments(SubDeploymentMBean dep) {
      List allDeployments = new ArrayList();
      SubDeploymentMBean[] subDeployments = dep.getSubDeployments();
      if (subDeployments != null && subDeployments.length != 0) {
         for(int i = 0; i < subDeployments.length; ++i) {
            allDeployments.add(subDeployments[i]);
            List innerSubDeployments = getAllSubDeployments(subDeployments[i]);
            allDeployments.addAll(innerSubDeployments);
         }

         return allDeployments;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public static TargetMBean[] lookupTargetMBeans(DomainMBean domain, String[] targetNames, String partition) throws InvalidTargetException {
      if (targetNames.length == 1 && targetNames[0].trim().length() == 0) {
         return new TargetMBean[0];
      } else {
         TargetMBean[] tbeans = new TargetMBean[targetNames.length];

         for(int i = 0; i < targetNames.length; ++i) {
            try {
               if (partition != null) {
                  PartitionMBean pm = domain.lookupPartition(partition);
                  if (pm != null) {
                     tbeans[i] = pm.lookupEffectiveAvailableTarget(targetNames[i]);
                     if (tbeans[i] == null) {
                        tbeans[i] = domain.lookupServer(targetNames[i]);
                     }
                  }
               } else {
                  tbeans[i] = domain.lookupInAllTargets(targetNames[i]);
               }

               if (tbeans[i] == null) {
                  String name = targetNames[i];
                  if (partition != null) {
                     name = ApplicationVersionUtils.getApplicationIdWithPartition(targetNames[i], partition);
                  }

                  JMSServerMBean jmsServer = domain.lookupJMSServer(name);
                  if (jmsServer != null) {
                     tbeans[i] = jmsServer;
                  } else {
                     SAFAgentMBean safAgentMBean = domain.lookupSAFAgent(name);
                     if (safAgentMBean == null) {
                        throw new InvalidTargetException(targetNames[i]);
                     }

                     tbeans[i] = safAgentMBean;
                  }
               }
            } catch (IllegalArgumentException var8) {
               throw new InvalidTargetException(targetNames[i]);
            }
         }

         return tbeans;
      }
   }

   public static TargetMBean[] lookupTargetMBeans(DomainMBean domain, String[] targetNames) throws InvalidTargetException {
      return lookupTargetMBeans(domain, targetNames, (String)null);
   }

   public static TargetMBean[] lookupTargetMBeansFromDD(DomainMBean domain, String[] targetNames, DeploymentData data) throws InvalidTargetException {
      return data.getResourceGroupTemplate() == null && data.getResourceGroup() == null ? lookupTargetMBeans(domain, targetNames, data.getPartition()) : lookupTargetMBeansfromRGTorRG(domain, targetNames, data);
   }

   private static TargetMBean[] lookupTargetMBeansfromRGTorRG(DomainMBean domain, String[] targetNames, DeploymentData data) throws InvalidTargetException {
      if (targetNames.length == 1 && targetNames[0].trim().length() == 0) {
         return new TargetMBean[0];
      } else {
         TargetMBean[] tbeans = new TargetMBean[targetNames.length];
         String rgtName = data.getResourceGroupTemplate();
         if (rgtName != null && domain.lookupResourceGroupTemplate(rgtName) != null) {
            ResourceGroupTemplateMBean rgtBean = domain.lookupResourceGroupTemplate(rgtName);
            if (rgtBean != null) {
               for(int i = 0; i < targetNames.length; ++i) {
                  try {
                     tbeans[i] = rgtBean.lookupJMSServer(targetNames[i]);
                     if (tbeans[i] == null) {
                        tbeans[i] = rgtBean.lookupSAFAgent(targetNames[i]);
                     }
                  } catch (IllegalArgumentException var11) {
                     throw new InvalidTargetException(targetNames[i]);
                  }
               }
            }
         } else {
            String rgName = data.getResourceGroup();
            String partitionName = data.getPartition();
            if (rgName != null) {
               ResourceGroupMBean rgBean = domain.lookupResourceGroup(rgName);
               if (rgBean == null && partitionName != null) {
                  PartitionMBean partition = domain.lookupPartition(partitionName);
                  rgBean = partition.lookupResourceGroup(rgName);
               }

               if (rgBean != null) {
                  for(int i = 0; i < targetNames.length; ++i) {
                     try {
                        tbeans[i] = rgBean.lookupJMSServer(targetNames[i]);
                        if (tbeans[i] == null) {
                           tbeans[i] = rgBean.lookupSAFAgent(targetNames[i]);
                        }
                     } catch (IllegalArgumentException var10) {
                        throw new InvalidTargetException(targetNames[i]);
                     }
                  }
               }
            }
         }

         return tbeans;
      }
   }

   public static String[] getNonGlobalModules(DeploymentData data, BasicDeployment depl, DomainMBean proposedDomain) {
      if (data.isTargetsFromConfig()) {
         return null;
      } else {
         TargetMBean[] tmbs = depl.getDeploymentMBean().getTargets();
         if (TargetUtils.isDeployedLocally(tmbs)) {
            for(int i = 0; i < tmbs.length; ++i) {
               TargetMBean tmb = tmbs[i];
               if (data.hasGlobalTarget(tmb.getName())) {
                  return null;
               }
            }

            return getModulesForTarget(data, proposedDomain);
         } else {
            DeploymentState appState = depl.getState();
            if (null == appState) {
               return getModulesForTarget(data, proposedDomain);
            } else {
               TargetModuleState[] moduleStates = appState.getTargetModules();
               boolean removeAll = true;
               String[] deplData = getModulesForTarget(data, proposedDomain);
               if (deplData != null && moduleStates != null) {
                  String suffix = ".war";
                  List modulesInDeplData = new ArrayList();
                  String[] var10 = deplData;
                  int var11 = deplData.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     String module = var10[var12];
                     if (module.endsWith(".war")) {
                        modulesInDeplData.add(module);
                        module = module.substring(0, module.indexOf(".war"));
                     }

                     modulesInDeplData.add(module);
                  }

                  for(int i = 0; i < moduleStates.length; ++i) {
                     if (!moduleStates[i].getCurrentState().equals(ModuleListener.STATE_NEW.toString()) && !modulesInDeplData.contains(moduleStates[i].getModuleId())) {
                        removeAll = false;
                     }
                  }
               }

               return !removeAll ? deplData : null;
            }
         }
      }
   }

   public static String[] getNonGlobalSubModules(DeploymentData data, BasicDeployment depl) {
      if (data.isTargetsFromConfig()) {
         return null;
      } else {
         DeploymentState state = depl.getState();
         if (state == null) {
            return null;
         } else {
            TargetModuleState[] tmState = state.getTargetModules();
            ArrayList subModNamesFromRTState = new ArrayList();

            for(int i = 0; i < tmState.length; ++i) {
               if (tmState[i].isSubmodule() && !ModuleListener.STATE_NEW.toString().equals(tmState[i].getCurrentState())) {
                  subModNamesFromRTState.add(tmState[i].getSubmoduleId());
               }
            }

            Map modData = data.getAllSubModuleTargets();
            ArrayList subModFromData = new ArrayList();
            Iterator iter = modData.keySet().iterator();

            while(iter.hasNext()) {
               String modName = (String)iter.next();
               HashMap subModData = (HashMap)modData.get(modName);
               Iterator innerIter = subModData.keySet().iterator();

               while(innerIter.hasNext()) {
                  subModFromData.add((String)innerIter.next());
               }
            }

            if (subModFromData.containsAll(subModNamesFromRTState)) {
               return null;
            } else {
               Object[] objs = subModFromData.toArray();
               if (0 == objs.length) {
                  return null;
               } else {
                  String[] result = new String[objs.length];

                  for(int i = 0; i < objs.length; ++i) {
                     result[i] = (String)objs[i];
                  }

                  return result;
               }
            }
         }
      }
   }

   public static String[] getAllTargetedServersForVirtualTarget(String target) {
      Set serverNames = new HashSet();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      VirtualTargetMBean virtualTarget = domain.lookupInAllVirtualTargets(target);
      if (virtualTarget != null) {
         String[] vtTargets = virtualTarget.getHostNames();
         if (vtTargets != null) {
            for(int n = 0; n < vtTargets.length; ++n) {
               serverNames.add(vtTargets[n]);
            }
         }
      }

      return serverNames.size() > 0 ? (String[])((String[])serverNames.toArray(new String[0])) : null;
   }

   public static Set getAllTargetedServers(BasicDeploymentMBean deployable) {
      Set serverNames = new HashSet();
      serverNames.addAll(resolveTargetMBeans(deployable.getTargets()));
      SubDeploymentMBean[] mtargets = deployable.getSubDeployments();

      for(int i = 0; i < mtargets.length; ++i) {
         serverNames.addAll(resolveTargetMBeans(mtargets[i].getTargets()));
         serverNames.addAll(resolveTargetInfos(mtargets[i].getSubDeployments()));
      }

      return serverNames;
   }

   private static Set resolveTargetMBeans(TargetMBean[] targets) {
      Set serverNames = new HashSet();
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            serverNames.addAll(targets[i].getServerNames());
            TargetingUtils.addDynamicServerNames(targets[i], serverNames);
         }
      }

      return serverNames;
   }

   private static Set resolveTargetInfos(TargetInfoMBean[] tinfos) {
      Set serverNames = new HashSet();

      for(int j = 0; j < tinfos.length; ++j) {
         serverNames.addAll(resolveTargetMBeans(tinfos[j].getTargets()));
      }

      return serverNames;
   }

   public static int getTypeForTarget(String target) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (domain.lookupCluster(target) != null) {
         return 1;
      } else if (domain.lookupServer(target) != null) {
         return 2;
      } else if (domain.lookupVirtualHost(target) != null) {
         return 3;
      } else if (domain.lookupJMSServer(target) != null) {
         return 4;
      } else if (domain.lookupSAFAgent(target) != null) {
         return 5;
      } else {
         return domain.lookupInAllVirtualTargets(target) != null ? 6 : 0;
      }
   }

   public static boolean isTargetedLocaly(BasicDeploymentMBean deployment) {
      Set servernames = getAllTargetedServers(deployment);
      printTargetList(deployment, servernames);
      return servernames.contains(ManagementService.getRuntimeAccess(kernelId).getServer().getName());
   }

   public static boolean isTargetedLocally(DeploymentMBean deployment) {
      Set servernames = resolveTargetMBeans(deployment.getTargets());
      return servernames.contains(ManagementService.getRuntimeAccess(kernelId).getServer().getName());
   }

   public static boolean isPinnedToServerInCluster(BasicDeploymentMBean deployment) {
      ServerMBean thisServer = ManagementService.getRuntimeAccess(kernelId).getServer();
      String thisServerName = thisServer.getName();
      ClusterMBean thisCluster = thisServer.getCluster();
      if (thisCluster == null) {
         return false;
      } else {
         Set otherServersInCluster = thisCluster.getServerNames();
         otherServersInCluster.remove(thisServerName);
         Set servernames = getAllTargetedServers(deployment);
         printTargetList(deployment, servernames);
         Iterator otherServersInClusterIterator = otherServersInCluster.iterator();

         do {
            if (!otherServersInClusterIterator.hasNext()) {
               return false;
            }
         } while(!servernames.contains(otherServersInClusterIterator.next()));

         return true;
      }
   }

   private static void printTargetList(BasicDeploymentMBean deployment, Set servernames) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Targets for app, " + deployment.getName());
         Iterator names = servernames.iterator();

         while(names.hasNext()) {
            Debug.deploymentDebug("   " + names.next());
         }
      }

   }

   public static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   public static String[] getTargetNames(TargetMBean[] targets) {
      if (targets == null) {
         return null;
      } else {
         String[] names = new String[targets.length];

         for(int i = 0; i < targets.length; ++i) {
            names[i] = targets[i].getName();
         }

         return names;
      }
   }

   public static boolean allTargetsSpecified(BasicDeploymentMBean deployable, DeploymentData info) {
      if (!info.hasModuleTargets() && !info.hasSubModuleTargets()) {
         Set serverNames = new HashSet();
         TargetMBean[] deployableTargets = deployable.getTargets();
         if (deployableTargets != null) {
            for(int n = 0; n < deployableTargets.length; ++n) {
               Set servers = deployableTargets[n].getServerNames();
               Iterator it = servers.iterator();

               while(it.hasNext()) {
                  String serverName = (String)it.next();
                  if (!serverNames.contains(serverName)) {
                     serverNames.add(serverName);
                  }
               }
            }
         }

         String[] infoTargets = info.getTargets();
         if (infoTargets != null) {
            for(int n = 0; n < infoTargets.length; ++n) {
               serverNames.remove(infoTargets[n]);
            }
         }

         return serverNames.size() == 0;
      } else {
         return false;
      }
   }

   static {
      localServer = ManagementService.getRuntimeAccess(kernelId).getServer();
   }

   @Service
   public static class TargetHelperImpl implements TargetHelperInterface {
      public TargetMBean[] lookupTargetMBeans(DomainMBean domain, String[] targetNames, String partition) throws InvalidTargetException {
         return TargetHelper.lookupTargetMBeans(domain, targetNames, partition);
      }

      public TargetMBean[] lookupTargetMBeans(DomainMBean domain, String[] targetNames) throws InvalidTargetException {
         return TargetHelper.lookupTargetMBeans(domain, targetNames);
      }

      public int getTypeForTarget(String target) {
         return TargetHelper.getTypeForTarget(target);
      }
   }
}
