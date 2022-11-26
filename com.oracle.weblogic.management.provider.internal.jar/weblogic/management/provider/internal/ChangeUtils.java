package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceMemberConfigMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.ContextCaseMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.configuration.FileT3MBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.JoltConnectionPoolMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MaxThreadsConstraintMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.configuration.ShutdownClassMBean;
import weblogic.management.configuration.StartupClassMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WorkManagerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.security.RealmMBean;
import weblogic.management.utils.TargetingUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

final class ChangeUtils {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");

   public static String[] getRestartRequiredPartitions(BeanUpdateEvent event) {
      return getRestartRequired(event, PartitionMBean.class);
   }

   public static String[] getRestartRequiredServers(BeanUpdateEvent event) {
      return getRestartRequired(event, ServerMBean.class);
   }

   private static String[] getRestartRequired(BeanUpdateEvent event, Class entityType) {
      say("getRestartRequired");
      if (!entityType.isAssignableFrom(PartitionMBean.class) && !entityType.isAssignableFrom(ServerMBean.class)) {
         throw new IllegalArgumentException("getRestartRequired supports either ServerMBean or PartitionMBean, invalid type [" + entityType.getSimpleName() + "] provided");
      } else if (!isValidBeanForRestartStatusEvaluation(event)) {
         return new String[0];
      } else {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
         say("getRestartRequired: updates.length=" + updates.length);

         for(int i = 0; i < updates.length; ++i) {
            BeanUpdateEvent.PropertyUpdate update = updates[i];
            String propertyName = update.getPropertyName();
            if (update.isRestartAnnotationDefined()) {
               return getRestartsListFromAnnotation(update, entityType);
            }

            if (!update.isDynamic()) {
               say("getRestartRequired: found restart required property");
               DescriptorBean bean = event.getSourceBean();
               ManagementLogger.logNonDynamicAttributeChange(bean.toString(), propertyName);
               if (entityType.isAssignableFrom(PartitionMBean.class)) {
                  return getAffectedPartitions(event.getProposedBean(), update);
               }

               return getAffectedServers(event.getProposedBean(), update);
            }
         }

         return new String[0];
      }
   }

   private static String[] getRestartsListFromAnnotation(BeanUpdateEvent.PropertyUpdate update, Class type) {
      Collection restartElementsList = update.getRestartElements();
      List restartsList = new ArrayList();
      if (restartElementsList != null) {
         Iterator var4 = restartElementsList.iterator();

         while(var4.hasNext()) {
            SettableBean settableBean = (SettableBean)var4.next();
            if (settableBean instanceof ConfigurationMBean) {
               ConfigurationMBean configBean = (ConfigurationMBean)settableBean;
               if (type.isAssignableFrom(configBean.getClass())) {
                  restartsList.add(configBean.getName());
               }
            }
         }
      }

      return (String[])restartsList.toArray(new String[restartsList.size()]);
   }

   private static boolean isValidBeanForRestartStatusEvaluation(BeanUpdateEvent event) {
      DescriptorBean proposedBean = event.getProposedBean();
      if (proposedBean == null) {
         say("isValidBeanForRestartStatusEvaluation: bean is null. no servers/partitions affected");
         return false;
      } else {
         BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
         BeanInfo beanInfo = beanInfoAccess.getBeanInfoForDescriptorBean(proposedBean);
         if (beanInfo == null) {
            say("isValidBeanForRestartStatusEvaluation: bean info is null. no servers/partitions affected");
            return false;
         } else {
            return true;
         }
      }
   }

   public static Map getPartitionSystemResourcesForRestart(BeanUpdateEvent event) {
      return getSystemResourcesForRestart(event, PartitionMBean.class);
   }

   public static Map getServerSystemResourcesForRestart(BeanUpdateEvent event) {
      return getSystemResourcesForRestart(event, ServerMBean.class);
   }

   private static Map getSystemResourcesForRestart(BeanUpdateEvent event, Class type) {
      Map systemResources = new HashMap();
      if (!isValidBeanForRestartStatusEvaluation(event)) {
         return systemResources;
      } else {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int i = 0; i < updates.length; ++i) {
            BeanUpdateEvent.PropertyUpdate update = updates[i];
            if (update.isRestartAnnotationDefined()) {
               if (type.isAssignableFrom(ServerMBean.class)) {
                  detectServerRestartSystemResources(update, systemResources);
               } else if (type.isAssignableFrom(PartitionMBean.class)) {
                  detectPartitionRestartSystemResources(update, systemResources);
               }
            }
         }

         return systemResources;
      }
   }

   private static void detectPartitionRestartSystemResources(BeanUpdateEvent.PropertyUpdate update, Map restartSystemResources) {
      Collection restartElementsList = update.getRestartElements();
      if (restartElementsList != null) {
         Iterator var3 = restartElementsList.iterator();

         while(var3.hasNext()) {
            SettableBean settableBean = (SettableBean)var3.next();
            if (settableBean instanceof SystemResourceMBean) {
               ConfigurationMBean configBean = (ConfigurationMBean)settableBean;
               PartitionMBean partition = (PartitionMBean)containedBy(configBean, PartitionMBean.class);
               if (partition != null) {
                  String partitionName = partition.getName();
                  addToSystemResources(configBean, restartSystemResources, partitionName);
               }
            }
         }
      }

   }

   private static void detectServerRestartSystemResources(BeanUpdateEvent.PropertyUpdate update, Map restartSystemResources) {
      Collection restartElementsList = update.getRestartElements();
      if (restartElementsList != null) {
         Iterator var3 = restartElementsList.iterator();

         while(true) {
            ConfigurationMBean configBean;
            do {
               SettableBean settableBean;
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  settableBean = (SettableBean)var3.next();
               } while(!(settableBean instanceof SystemResourceMBean));

               configBean = (ConfigurationMBean)settableBean;
            } while(containedBy(configBean, PartitionMBean.class) != null);

            ResourceGroupMBean rg = (ResourceGroupMBean)containedBy(configBean, ResourceGroupMBean.class);
            String[] serversUsingSystemResource;
            int var10;
            if (rg != null) {
               String[] serversUsingRG = getTargetServerNames(rg.getTargets());
               serversUsingSystemResource = serversUsingRG;
               int var9 = serversUsingRG.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  String server = serversUsingSystemResource[var10];
                  addToSystemResources(configBean, restartSystemResources, server);
               }
            }

            SystemResourceMBean systemResource = (SystemResourceMBean)configBean;
            serversUsingSystemResource = getTargetServerNames(systemResource.getTargets());
            String[] var15 = serversUsingSystemResource;
            var10 = serversUsingSystemResource.length;

            for(int var13 = 0; var13 < var10; ++var13) {
               String server = var15[var13];
               addToSystemResources(configBean, restartSystemResources, server);
            }
         }
      }
   }

   private static void addToSystemResources(ConfigurationMBean configBean, Map systemResources, String resourceContainerName) {
      Set resources = (Set)systemResources.get(resourceContainerName);
      if (resources == null) {
         resources = new HashSet();
         systemResources.put(resourceContainerName, resources);
      }

      ((Set)resources).add(configBean.getName());
   }

   private static String[] getAffectedPartitions(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      say("getRestartRequiredPartitions");
      if (!(bean instanceof ConfigurationMBean)) {
         say("getRestartRequiredPartitions: getAffectedPartitions: not a config bean");
         return new String[0];
      } else {
         ConfigurationMBean configBean = (ConfigurationMBean)bean;
         say("checking partitions affected by: " + configBean.getName());
         String[] partitions = getTargetPartitionsIfDeployableNeedsRestart(configBean);
         if (partitions == null) {
            PartitionMBean partition = (PartitionMBean)containedBy(configBean, PartitionMBean.class);
            if (partition != null) {
               say("getRestartRequiredPartitions: affected partition: " + partition.getName());
               return new String[]{partition.getName()};
            } else {
               say("getRestartRequiredPartitions: Not contained by Partition");
               return new String[0];
            }
         } else {
            if (debugLogger.isDebugEnabled()) {
               String msg = "NON-DYNAMIC change for Deployable in partitions: ";

               for(int i = 0; i < partitions.length; ++i) {
                  msg = msg + partitions[i] + (i < partitions.length ? ", " : "");
               }

               debugLogger.debug(msg);
            }

            return partitions;
         }
      }
   }

   private static String[] getAffectedServers(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      say("getRestartRequiredServers");
      if (bean instanceof StandardInterface) {
         Descriptor descriptor = bean.getDescriptor();
         DescriptorBean root = descriptor.getRootBean();
         if (root instanceof DomainMBean) {
            if (isRealmRestartEnabled(bean)) {
               say("getRestartRequiredServers: getAffectedServers: realm change and realm restart enabled");
               return new String[0];
            } else {
               return getAllServers((DomainMBean)root);
            }
         } else {
            return getAllServers(ManagementService.getRuntimeAccess(KERNEL_ID).getDomain());
         }
      } else if (!(bean instanceof ConfigurationMBean)) {
         say("getRestartRequiredServers: getAffectedServers: not a config bean");
         return new String[0];
      } else {
         ConfigurationMBean configBean = (ConfigurationMBean)bean;
         say("checking servers affected by: " + configBean.getName());
         PartitionMBean partition = (PartitionMBean)containedBy(configBean, PartitionMBean.class);
         if (partition != null) {
            say("getRestartRequiredServers: affected partition: " + partition.getName());
            return new String[0];
         } else {
            say("getRestartRequiredServers: Not contained by Partition");
            CoherenceMemberConfigMBean cohConfig = isCoherenceMemberConfigRestartChange(configBean, update);
            if (cohConfig != null) {
               WebLogicMBean parent = cohConfig.getParent();
               ClusterMBean c = parent instanceof ServerMBean ? ((ServerMBean)parent).getCluster() : null;
               if (c != null) {
                  say("getRestartRequiredServers: affected cluster " + c.getName());
                  return getServers(c);
               }

               say("getRestartRequiredServers: No cluster for server " + parent.getName());
            }

            CoherenceClusterSystemResourceMBean cohCluster = isCoherenceClusterRestartRequired(configBean, update);
            if (cohCluster != null) {
               say("getRestartRequiredServers: affected coherence cluster cluster " + cohCluster.getName());
               return getServers(cohCluster);
            } else {
               ServerMBean server = (ServerMBean)containedBy(configBean, ServerMBean.class);
               if (server != null) {
                  say("getRestartRequiredServers: affected server " + server.getName());
                  String[] result = new String[]{server.getName()};
                  return result;
               } else {
                  say("getRestartRequiredServers: Not contained by Server");
                  ClusterMBean c = (ClusterMBean)containedBy(configBean, ClusterMBean.class);
                  if (c != null) {
                     say("getRestartRequiredServers: affected cluster " + c.getName());
                     return getServers(c);
                  } else {
                     say("getRestartRequiredServers: Not contained by Cluster");
                     MachineMBean m = (MachineMBean)containedBy(configBean, MachineMBean.class);
                     if (m != null) {
                        say("getRestartRequiredServers: affected machine " + m.getName());
                        return getServers(m);
                     } else {
                        say("getRestartRequiredServers: Not contained by Machine");
                        ServerTemplateMBean svrTemplate = (ServerTemplateMBean)containedBy(configBean, ServerTemplateMBean.class);
                        if (svrTemplate != null) {
                           say("getRestartRequiredServers: affected server template " + svrTemplate.getName());
                           return getServers(svrTemplate);
                        } else {
                           say("getRestartRequiredServers: Not contained a server template");
                           if (!isSecurityConfiguration(configBean) && !isEmbeddedLDAP(configBean)) {
                              say("getRestartRequiredServers: not security setting");
                              String[] depTargets = getTargetServersIfDeployableNeedsRestart(configBean);
                              if (depTargets == null) {
                                 String[] bridgeTargets = getJMSBridgeTargets(configBean);
                                 if (bridgeTargets != null) {
                                    say("NON-DYNAMIC CHANGE IN JMS bridge configuration");
                                    return bridgeTargets;
                                 } else {
                                    if (debugLogger.isDebugEnabled()) {
                                       String msg = "Warning: NON-DYNAMIC CHANGE IN " + configBean.getType() + "{" + configBean.getName() + "}";
                                       debugLogger.debug(msg);
                                    }

                                    return getAllServers(getDomain(configBean));
                                 }
                              } else {
                                 if (debugLogger.isDebugEnabled()) {
                                    String msg = "NON-DYNAMIC change for Deployable with target servers: ";

                                    for(int i = 0; i < depTargets.length; ++i) {
                                       msg = msg + depTargets[i] + (i < depTargets.length ? ", " : "");
                                    }

                                    debugLogger.debug(msg);
                                 }

                                 return depTargets;
                              }
                           } else {
                              say("getRestartRequiredServers: non-dynamic security setting");
                              return getAllServers(getDomain(configBean));
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static String[] getJMSBridgeTargets(ConfigurationMBean bean) {
      String[] affectedServers = null;
      String jmsDestinationName = null;
      List serverList = null;
      DomainMBean domainMBean = null;
      MessagingBridgeMBean[] bridgeMBeans = null;
      ResourceGroupTemplateMBean resourceGroupTemplateMBean = null;
      if (bean instanceof JMSBridgeDestinationMBean) {
         if (bean.getParentBean() instanceof ResourceGroupTemplateMBean) {
            resourceGroupTemplateMBean = (ResourceGroupTemplateMBean)bean.getParentBean();
            bridgeMBeans = resourceGroupTemplateMBean.getMessagingBridges();
         } else {
            domainMBean = (DomainMBean)bean.getParentBean();
            bridgeMBeans = domainMBean.getMessagingBridges();
         }

         JMSBridgeDestinationMBean jmsBridgeDestinationMBean = (JMSBridgeDestinationMBean)bean;
         jmsDestinationName = jmsBridgeDestinationMBean.getName();
         serverList = new ArrayList();
         MessagingBridgeMBean[] var8 = bridgeMBeans;
         int var9 = bridgeMBeans.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            MessagingBridgeMBean bridgeMBean = var8[var10];
            String source = bridgeMBean.getSourceDestination().getName();
            String destination = bridgeMBean.getTargetDestination().getName();
            if (jmsDestinationName.equals(source) || jmsDestinationName.equals(destination)) {
               TargetMBean[] targetMBeans = bridgeMBean.getTargets();
               if (resourceGroupTemplateMBean != null) {
                  ResourceGroupMBean rg = (ResourceGroupMBean)containedBy(bean, ResourceGroupMBean.class);
                  if (rg != null) {
                     serverList.addAll(Arrays.asList(getTargetServerNames(rg.getTargets())));
                  }
               } else {
                  serverList.addAll(Arrays.asList(getTargetServerNames(targetMBeans)));
               }
            }
         }

         Set serverNames = new HashSet();
         serverNames.addAll(serverList);
         affectedServers = (String[])serverNames.toArray(new String[serverList.size()]);
      }

      return affectedServers;
   }

   private static String[] getTargetPartitionsIfDeployableNeedsRestart(ConfigurationMBean bean) {
      boolean needsRestart = false;

      DeploymentMBean depBean;
      WebLogicMBean o;
      for(depBean = null; bean != null; bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null) {
         if (bean instanceof TargetInfoMBean) {
            return new String[0];
         }

         if (bean instanceof DeploymentMBean) {
            needsRestart = isRestartRequiredForDeploymentBean(bean);
            depBean = (DeploymentMBean)bean;
            break;
         }

         o = bean.getParent();
      }

      return depBean == null ? null : new String[0];
   }

   private static String[] getTargetServersIfDeployableNeedsRestart(ConfigurationMBean bean) {
      boolean needsRestart = false;

      DeploymentMBean depBean;
      WebLogicMBean o;
      for(depBean = null; bean != null; bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null) {
         if (bean instanceof TargetInfoMBean) {
            return new String[0];
         }

         if (bean instanceof DeploymentMBean) {
            needsRestart = isRestartRequiredForDeploymentBean(bean);
            depBean = (DeploymentMBean)bean;
            break;
         }

         o = bean.getParent();
      }

      if (depBean == null) {
         return null;
      } else if (!needsRestart) {
         return new String[0];
      } else {
         TargetMBean[] targets = depBean.getTargets();
         return targets == null ? new String[0] : getTargetServerNames(targets);
      }
   }

   private static String[] getTargetServerNames(TargetMBean[] targets) {
      Set serverNames = new HashSet();

      for(int i = 0; i < targets.length; ++i) {
         serverNames.addAll(targets[i].getServerNames());
         TargetingUtils.addDynamicServerNames(targets[i], serverNames);
      }

      return (String[])serverNames.toArray(new String[serverNames.size()]);
   }

   private static boolean isRestartRequiredForDeploymentBean(ConfigurationMBean bean) {
      boolean needsRestart = false;
      if (bean instanceof ShutdownClassMBean || bean instanceof ContextCaseMBean || bean instanceof FileT3MBean || bean instanceof JoltConnectionPoolMBean || bean instanceof MaxThreadsConstraintMBean || bean instanceof MessagingBridgeMBean || bean instanceof PathServiceMBean || bean instanceof PersistentStoreMBean || bean instanceof WorkManagerMBean) {
         needsRestart = true;
      }

      return needsRestart;
   }

   private static ResourceGroupTemplateMBean containedByRGT(ConfigurationMBean bean) {
      while(bean != null) {
         if (bean instanceof ResourceGroupTemplateMBean && !(bean instanceof ResourceGroupMBean)) {
            return (ResourceGroupTemplateMBean)bean;
         }

         Object o = bean.getParent();
         bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
      }

      return null;
   }

   public static boolean isFromPartition(DescriptorBean bean) {
      if (!(bean instanceof ConfigurationMBean)) {
         return false;
      } else {
         ConfigurationMBean configBean = (ConfigurationMBean)bean;
         return containedBy(configBean, PartitionMBean.class) != null;
      }
   }

   private static Object containedBy(ConfigurationMBean bean, Class type) {
      while(bean != null) {
         if (type.isAssignableFrom(bean.getClass())) {
            return bean;
         }

         Object o = bean.getParent();
         bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
      }

      return null;
   }

   private static boolean isSecurityConfiguration(ConfigurationMBean bean) {
      while(bean != null) {
         if (bean instanceof SecurityConfigurationMBean) {
            return true;
         }

         Object o = bean.getParent();
         bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
      }

      return false;
   }

   private static boolean isEmbeddedLDAP(ConfigurationMBean bean) {
      while(bean != null) {
         if (bean instanceof EmbeddedLDAPMBean) {
            return true;
         }

         Object o = bean.getParent();
         bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
      }

      return false;
   }

   private static String[] getServers(ClusterMBean cluster) {
      Set serverNames = new HashSet();
      ServerMBean[] servers = cluster.getServers();
      if (servers != null) {
         ServerMBean[] var3 = servers;
         int var4 = servers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ServerMBean server = var3[var5];
            serverNames.add(server.getName());
         }
      }

      DomainMBean rtDomain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      ClusterMBean rtCluster = rtDomain.lookupCluster(cluster.getName());
      if (rtCluster != null) {
         servers = rtCluster.getServers();
         if (servers != null) {
            ServerMBean[] var11 = servers;
            int var12 = servers.length;

            for(int var7 = 0; var7 < var12; ++var7) {
               ServerMBean server = var11[var7];
               serverNames.add(server.getName());
            }
         }
      }

      return (String[])serverNames.toArray(new String[serverNames.size()]);
   }

   private static String[] getServers(CoherenceClusterSystemResourceMBean cohCluster) {
      Set serverNames = new HashSet();
      DomainMBean rtDomain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      ServerMBean[] var3 = rtDomain.getServers();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerMBean server = var3[var5];
         ClusterMBean cluster = server.getCluster();
         CoherenceClusterSystemResourceMBean serverCohCluster = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
         if (serverCohCluster != null && serverCohCluster.getName().equals(cohCluster.getName())) {
            serverNames.add(server.getName());
         }
      }

      return (String[])serverNames.toArray(new String[serverNames.size()]);
   }

   private static String[] getServers(MachineMBean machine) {
      ServerMBean[] servers = getDomain(machine).getServers();
      if (servers == null) {
         return new String[0];
      } else {
         int count = 0;
         ArrayList list = new ArrayList();

         for(int i = 0; i < servers.length; ++i) {
            if (servers[i].getMachine() != null && machine.getName().equals(servers[i].getMachine().getName())) {
               list.add(servers[i].getName());
               ++count;
            }
         }

         if (count == 0) {
            return new String[0];
         } else {
            String[] result = new String[count];

            for(int i = 0; i < result.length; ++i) {
               result[i] = (String)list.get(i);
            }

            return result;
         }
      }
   }

   private static String[] getServers(ServerTemplateMBean svrTemplate) {
      ServerMBean[] servers = getDomain(svrTemplate).getServers();
      Set serverNames = new HashSet();
      int var5;
      if (servers != null) {
         ServerMBean[] var3 = servers;
         int var4 = servers.length;

         for(var5 = 0; var5 < var4; ++var5) {
            ServerMBean server = var3[var5];
            if (svrTemplate.equals(server.getServerTemplate())) {
               serverNames.add(server.getName());
            }
         }
      }

      ClusterMBean[] clusters = getDomain(svrTemplate).getClusters();
      if (clusters != null) {
         ClusterMBean[] var12 = clusters;
         var5 = clusters.length;

         for(int var13 = 0; var13 < var5; ++var13) {
            ClusterMBean cluster = var12[var13];
            DynamicServersMBean dynServer = cluster.getDynamicServers();
            if (svrTemplate.equals(dynServer.getServerTemplate())) {
               for(int i = 0; i < dynServer.getMaximumDynamicServerCount(); ++i) {
                  String name = dynServer.getServerNamePrefix() + (i + dynServer.getServerNameStartingIndex());
                  serverNames.add(name);
               }
            }
         }
      }

      return (String[])serverNames.toArray(new String[serverNames.size()]);
   }

   private static String[] getAllServers(DomainMBean d) {
      if (d == null) {
         return new String[0];
      } else {
         ServerMBean[] s = d.getServers();
         String[] result = new String[s.length];

         for(int i = 0; i < s.length; ++i) {
            result[i] = s[i].getName();
         }

         return result;
      }
   }

   private static DomainMBean getDomain(ConfigurationMBean c) {
      while(c != null) {
         if (c instanceof DomainMBean) {
            return (DomainMBean)c;
         }

         Object o = c.getParent();
         if (o instanceof ConfigurationMBean) {
            c = (ConfigurationMBean)o;
         } else {
            c = null;
         }
      }

      return null;
   }

   private static CoherenceMemberConfigMBean isCoherenceMemberConfigRestartChange(ConfigurationMBean c, BeanUpdateEvent.PropertyUpdate update) {
      if (!(c instanceof CoherenceMemberConfigMBean)) {
         say("isCoherenceMemberConfigRestartChange: not a coherence member config bean " + c.getName());
         return null;
      } else {
         CoherenceMemberConfigMBean cohConfig = (CoherenceMemberConfigMBean)c;
         if ("ManagementProxy".equals(update.getPropertyName())) {
            say("isCoherenceMemberConfigRestartChange: management proxy enabled");
            return cohConfig;
         } else {
            say("isCoherenceMemberConfigRestartChange: not the management proxy attribute " + update.getPropertyName());
            return null;
         }
      }
   }

   private static CoherenceClusterSystemResourceMBean isCoherenceClusterRestartRequired(ConfigurationMBean c, BeanUpdateEvent.PropertyUpdate update) {
      if (!(c instanceof CoherenceClusterSystemResourceMBean)) {
         say("isCoherenceClusterRestartRequired: not a coherence cluster system resource bean " + c.getName());
         return null;
      } else {
         String propertyName = update.getPropertyName();
         if (!"CustomConfigFileLastUpdatedTime".equals(propertyName) && !"CustomClusterConfigurationFileName".equals(propertyName) && !"UsingCustomClusterConfigurationFile".equals(propertyName) && (propertyName == null || !propertyName.startsWith("Persistence") && !propertyName.startsWith("Federation"))) {
            return null;
         } else {
            say("isCoherenceClusterRestartRequired: custom config changed restart required");
            return (CoherenceClusterSystemResourceMBean)c;
         }
      }
   }

   public static boolean getRestartValue(PropertyDescriptor propertyDescriptor) {
      if (propertyDescriptor == null) {
         say("getRestartValue: property descriptor is null, return true");
         return true;
      } else {
         say("getRestartValue: checking restart value of " + propertyDescriptor.getName());
         Class clazz = propertyDescriptor.getPropertyType();
         if (clazz.isArray()) {
            clazz = clazz.getComponentType();
         }

         if (!TargetInfoMBean.class.isAssignableFrom(clazz) && (!DeploymentMBean.class.isAssignableFrom(clazz) || StartupClassMBean.class.isAssignableFrom(clazz) || ShutdownClassMBean.class.isAssignableFrom(clazz) || ContextCaseMBean.class.isAssignableFrom(clazz) || FileT3MBean.class.isAssignableFrom(clazz) || JoltConnectionPoolMBean.class.isAssignableFrom(clazz) || MaxThreadsConstraintMBean.class.isAssignableFrom(clazz) || MessagingBridgeMBean.class.isAssignableFrom(clazz) || PathServiceMBean.class.isAssignableFrom(clazz) || PersistentStoreMBean.class.isAssignableFrom(clazz) || WorkManagerMBean.class.isAssignableFrom(clazz))) {
            try {
               say("getRestartValue: value of dynamic is " + propertyDescriptor.getValue("dynamic"));
               Boolean dyn = (Boolean)propertyDescriptor.getValue("dynamic");
               if (dyn != null) {
                  if (dyn) {
                     say("return false");
                     return false;
                  }

                  say("return true");
                  return true;
               }

               say("getRestartValue: value of non-dynamic is " + propertyDescriptor.getValue("non-dynamic"));
               Boolean nondyn = (Boolean)propertyDescriptor.getValue("non-dynamic");
               if (nondyn != null) {
                  if (!nondyn) {
                     say("return false");
                     return false;
                  }

                  say("return true");
                  return true;
               }
            } catch (Exception var4) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("ChangeUtils: " + var4, var4);
               }
            }

            say("return true");
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean isRealmRestartEnabled(DescriptorBean bean) {
      RealmMBean realm = null;

      for(DescriptorBean curBean = bean; curBean != null && realm == null; curBean = curBean.getParentBean()) {
         if (curBean instanceof RealmMBean) {
            realm = (RealmMBean)curBean;
         }
      }

      if (realm != null && realm.isAutoRestartOnNonDynamicChanges()) {
         say("realmRestartEnabled = true");
         return true;
      } else {
         say("realmRestartEnabled = false, realm = " + realm);
         return false;
      }
   }

   private static void say(String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ChangeUtils: " + msg);
      }

   }

   private static class DeploymentRestartInfo {
      boolean restartRequired = false;
      TargetInfoMBean targetInfo = null;

      DeploymentRestartInfo(ConfigurationMBean mbean) {
      }
   }
}
