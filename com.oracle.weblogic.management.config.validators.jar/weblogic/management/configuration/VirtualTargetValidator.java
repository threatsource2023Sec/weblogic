package weblogic.management.configuration;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.SettableBean;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class VirtualTargetValidator {
   private static final ManagementConfigValidatorsTextFormatter TXT_FORMATTER = ManagementConfigValidatorsTextFormatter.getInstance();
   private static final Map vts = new HashMap();

   public static void addVirtualTargetMBean(VirtualTargetMBean vt) {
      synchronized(vts) {
         vts.put(vt.getName(), vt);
      }
   }

   public static void removeVirtualTargetMBean(VirtualTargetMBean vt) {
      synchronized(vts) {
         vts.remove(vt.getName());
      }
   }

   public static void validateUriPrefix(VirtualTargetMBean vt, String uriPrefix) throws IllegalArgumentException {
      if (uriPrefix != null && uriPrefix.length() != 0) {
         if (uriPrefix.charAt(0) != '/') {
            throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTUriPrefixMsg(vt.getName()));
         } else {
            if (vt.getParent() instanceof DomainMBean) {
               DomainMBean domain = (DomainMBean)vt.getParent();
               String reservedPrefix = domain.getPartitionUriSpace();
               if (uriPrefix.equalsIgnoreCase(reservedPrefix)) {
                  throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTUriPrefixPartitionNamespaceMsg(vt.getName(), reservedPrefix));
               }
            }

         }
      }
   }

   public static void validateVirtualTarget(VirtualTargetMBean vt) {
      checkVTPort(vt);
      checkVTTargets(vt);
      checkVTAsDefault(vt);
      checkVTWithSameConfigurations(vt);
   }

   private static void checkVTPort(VirtualTargetMBean vt) {
      if (vt.getExplicitPort() != 0 && vt.getPortOffset() != 0) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTPortMsg(vt.getName()));
      }
   }

   private static void checkVTTargets(VirtualTargetMBean vt) {
      TargetMBean[] vtTargets = vt.getTargets();
      if (!vt.isMoreThanOneTargetAllowed() && vtTargets != null && vtTargets.length > 1) {
         throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTOnlyOneTargetMsg());
      } else {
         if (vtTargets != null) {
            TargetMBean[] var2 = vtTargets;
            int var3 = vtTargets.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               TargetMBean vtTarget = var2[var4];
               if (vtTarget instanceof MigratableTargetMBean) {
                  throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTMigratableTargetMsg(vtTarget.getType(), vtTarget.getName(), vt.getType(), vt.getName()));
               }

               if (vtTarget instanceof ServerMBean) {
                  ServerMBean server = (ServerMBean)vtTarget;
                  ClusterMBean cluster = server.getCluster();
                  if (cluster != null && cluster.getName() != null) {
                     throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTTargetNotPartOfClusterMSg(server.getName(), cluster.getName(), vt.getName()));
                  }
               }
            }
         }

      }
   }

   private static void checkVTAsDefault(VirtualTargetMBean vt) {
      boolean allowVTAsDefault = Boolean.getBoolean("weblogic.servlet.mt.allowVTAsDefault");
      if (!allowVTAsDefault && ManagementService.isRuntimeAccessInitialized() && "/".equals(vt.getUriPrefix()) && vt.getPortOffset() <= 0) {
         ServerRuntimeMBean runtimeMBean = ManagementService.getRuntimeAccess((AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction())).getServerRuntime();
         String listenAddr = runtimeMBean.getURL("http");
         String[] addr = listenAddr.split(":");
         if (addr.length >= 3) {
            String host = addr[1].substring(2);
            int port = Integer.parseInt(addr[2]);
            int vtPort = vt.getExplicitPort();
            if (vtPort <= 0 || vtPort == port) {
               String[] hostNames = vt.getHostNames();
               if (hostNames == null || hostNames.length == 0) {
                  throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTAsDefaultMsg(getHostNamesString(vt), vt.getUriPrefix(), (new Integer(vt.getExplicitPort())).toString(), (new Integer(vt.getPortOffset())).toString()));
               }

               String[] var9 = hostNames;
               int var10 = hostNames.length;

               int var11;
               for(var11 = 0; var11 < var10; ++var11) {
                  String vtHost = var9[var11];
                  if (host.equals(vtHost)) {
                     throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTAsDefaultMsg(getHostNamesString(vt), vt.getUriPrefix(), (new Integer(vt.getExplicitPort())).toString(), (new Integer(vt.getPortOffset())).toString()));
                  }
               }

               String ipAddr = runtimeMBean.getServerChannel("http").getAddress().getHostAddress();
               if (!ipAddr.equals(host)) {
                  String[] var15 = hostNames;
                  var11 = hostNames.length;

                  for(int var16 = 0; var16 < var11; ++var16) {
                     String vtHost = var15[var16];
                     if (ipAddr.equals(vtHost)) {
                        throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTAsDefaultMsg(getHostNamesString(vt), vt.getUriPrefix(), (new Integer(vt.getExplicitPort())).toString(), (new Integer(vt.getPortOffset())).toString()));
                     }
                  }
               }
            }
         }
      }

   }

   private static void checkVTWithSameConfigurations(VirtualTargetMBean newVT) {
      if (newVT.getTargets() != null && newVT.getTargets().length != 0) {
         List targetsForNewVT = getServers(newVT.getTargets());
         synchronized(vts) {
            Iterator var3 = vts.values().iterator();

            while(true) {
               VirtualTargetMBean vt;
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var3.hasNext()) {
                                 return;
                              }

                              vt = (VirtualTargetMBean)var3.next();
                           } while(newVT.getName().equals(vt.getName()));
                        } while(vt.getExplicitPort() != newVT.getExplicitPort());
                     } while(vt.getUriPrefix() != null && newVT.getUriPrefix() != null && !vt.getUriPrefix().equals(newVT.getUriPrefix()));
                  } while(vt.getTargets() == null);
               } while(vt.getTargets().length == 0);

               List targets = getServers(vt.getTargets());
               Iterator var6 = targetsForNewVT.iterator();

               label114:
               while(var6.hasNext()) {
                  ServerMBean targetForNewVT = (ServerMBean)var6.next();
                  Iterator var8 = targets.iterator();

                  while(true) {
                     ServerMBean target;
                     do {
                        do {
                           do {
                              do {
                                 if (!var8.hasNext()) {
                                    continue label114;
                                 }

                                 target = (ServerMBean)var8.next();
                              } while(target.getListenAddress() == null);
                           } while(targetForNewVT.getListenAddress() == null);
                        } while(!target.getListenAddress().equals(targetForNewVT.getListenAddress()));
                     } while(target.getListenPort() + vt.getPortOffset() != targetForNewVT.getListenPort() + newVT.getPortOffset());

                     String[] hostNamesForNewVT = newVT.getHostNames();
                     String[] hostNames = vt.getHostNames();
                     if (hostNamesForNewVT == null || hostNamesForNewVT.length == 0 || hostNames == null || hostNames.length == 0) {
                        throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTNamePortUriTargetMsg(vt.getName(), newVT.getName()));
                     }

                     String[] var12 = hostNames;
                     int var13 = hostNames.length;

                     for(int var14 = 0; var14 < var13; ++var14) {
                        String hostName = var12[var14];
                        if (Arrays.binarySearch(hostNamesForNewVT, hostName) >= 0) {
                           throw new IllegalArgumentException(TXT_FORMATTER.getCheckVTNamePortUriTargetMsg(vt.getName(), newVT.getName()));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static List getServers(TargetMBean[] targets) {
      List servers = new ArrayList();
      TargetMBean[] var2 = targets;
      int var3 = targets.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         if (target instanceof ClusterMBean) {
            ServerMBean[] beans = ((ClusterMBean)target).getServers();
            if (beans != null) {
               servers.addAll(Arrays.asList(beans));
            }
         }

         if (target instanceof ServerMBean) {
            servers.add((ServerMBean)target);
         }
      }

      return servers;
   }

   private static String getHostNamesString(VirtualTargetMBean vt) {
      String[] vtHostNames = vt.getHostNames();
      String hostNames = "";
      if (vtHostNames != null && vtHostNames.length > 0) {
         StringBuilder sb = new StringBuilder();
         String[] var4 = vtHostNames;
         int var5 = vtHostNames.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String hostName = var4[var6];
            if (sb.length() > 0) {
               sb.append(",");
            }

            sb.append(hostName);
         }

         hostNames = sb.toString();
      }

      return hostNames;
   }

   public static Set getRestarts(VirtualTargetMBean vt) {
      TargetMBean[] vTargets = vt.getTargets();
      if (vTargets != null && vTargets.length != 0) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         Set restartElements = new HashSet();
         return restartElements;
      } else {
         return Collections.EMPTY_SET;
      }
   }

   private static void addRestartRequiredMBeansByDomainRGTarget(VirtualTargetMBean vt, ResourceGroupMBean[] resourceGroups, Set restartElements) {
      ResourceGroupMBean[] var3 = resourceGroups;
      int var4 = resourceGroups.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceGroupMBean resourceGroup = var3[var5];
         TargetMBean[] var7 = resourceGroup.findEffectiveTargets();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            TargetMBean target = var7[var9];
            if (target instanceof VirtualTargetMBean && vt.getName().equalsIgnoreCase(target.getName())) {
               restartElements.add(resourceGroup);
            }
         }
      }

   }

   private static void addRestartRequiredMBeans(VirtualTargetMBean vt, Set restartElements, TargetMBean[] targets, SettableBean restartElement) {
      TargetMBean[] var4 = targets;
      int var5 = targets.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetMBean target = var4[var6];
         if (target instanceof VirtualTargetMBean && vt.getName().equalsIgnoreCase(target.getName())) {
            restartElements.add(restartElement);
         }
      }

   }

   public static void validateSetTargets(VirtualTargetMBean virtualTarget, TargetMBean[] targetsToSet) {
      if (virtualTarget != null) {
         if (virtualTarget.isMoreThanOneTargetAllowed()) {
            return;
         }

         List currTargets = Arrays.asList(virtualTarget.getTargets());
         Object targetsToSetList;
         if (targetsToSet == null) {
            targetsToSetList = new ArrayList();
         } else {
            targetsToSetList = Arrays.asList(targetsToSet);
         }

         if (currTargets.size() == 0) {
            return;
         }

         if (currTargets.equals(targetsToSetList)) {
            return;
         }

         DomainMBean domain = PartitionUtils.getDomain(virtualTarget);
         Descriptor domDescriptor = domain.getDescriptor();
         List referents = null;
         if (domDescriptor != null) {
            referents = domDescriptor.getResolvedReferences(virtualTarget);
         }

         if (referents != null) {
            TargetMBean[] removedTargets = PartitionUtils.getRemovedTargetArray((TargetMBean[])currTargets.toArray(new TargetMBean[currTargets.size()]), (TargetMBean[])((List)targetsToSetList).toArray(new TargetMBean[((List)targetsToSetList).size()]));
            Iterator var8 = referents.iterator();

            while(var8.hasNext()) {
               ResolvedReference referent = (ResolvedReference)var8.next();
               if (referent.getBean() instanceof ResourceGroupMBean) {
                  try {
                     ResourceGroupValidator.validateRemoveTargetFromRG((ResourceGroupMBean)referent.getBean(), removedTargets);
                  } catch (BeanUpdateRejectedException var11) {
                     throw new IllegalArgumentException(var11);
                  }
               }
            }
         }
      }

   }
}
