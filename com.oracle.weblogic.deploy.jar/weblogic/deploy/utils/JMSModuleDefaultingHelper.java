package weblogic.deploy.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.TargetableBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.deploy.DeploymentData;

public abstract class JMSModuleDefaultingHelper {
   private static final int QUEUE_TYPE = 0;
   private static final int TOPIC_TYPE = 1;
   private static final int MAX_RESTRICTED_TYPE = 2;
   private static final int CONN_TYPE = 2;
   private static final int FOR_TYPE = 3;
   private static final int UDQ_TYPE = 4;
   private static final int UDT_TYPE = 5;
   private static final int SAF_TYPE = 6;
   private static final int MAX_TYPE = 7;

   private static JMSServerMBean getTheOneJMSServer(ClusterMBean globalCluster, HashMap wlsServers, HashMap jmsServers, DomainMBean domain, DeploymentData info) {
      if (jmsServers.size() > 1) {
         return null;
      } else {
         JMSServerMBean retVal = null;

         for(Iterator it = jmsServers.values().iterator(); it.hasNext(); retVal = (JMSServerMBean)it.next()) {
         }

         if (retVal != null) {
            return retVal;
         } else {
            JMSServerMBean[] allJMSServers = getAllJMSServers(info, domain);
            if (allJMSServers == null) {
               return null;
            } else {
               for(int lcv = 0; lcv < allJMSServers.length; ++lcv) {
                  JMSServerMBean aJMSServer = allJMSServers[lcv];
                  TargetMBean[] jmsServerTargets = getJMSServerTargets(aJMSServer);
                  if (jmsServerTargets != null && jmsServerTargets.length > 0) {
                     TargetMBean jmsServerTarget = jmsServerTargets[0];
                     if (jmsServerTarget != null) {
                        if (jmsServerTarget instanceof VirtualTargetMBean) {
                           TargetMBean[] vtTargets = ((VirtualTargetMBean)jmsServerTarget).getTargets();
                           if (vtTargets.length > 0) {
                              jmsServerTarget = vtTargets[0];
                           }
                        }

                        if (jmsServerTarget instanceof ServerMBean) {
                           ServerMBean wlsJMSServerTarget = (ServerMBean)jmsServerTarget;
                           if (globalCluster != null) {
                              ClusterMBean clusterJMSServerTarget = wlsJMSServerTarget.getCluster();
                              if (clusterJMSServerTarget != null && clusterJMSServerTarget.getName().equals(globalCluster.getName())) {
                                 if (retVal != null) {
                                    return null;
                                 }

                                 retVal = aJMSServer;
                              }
                           } else {
                              Iterator it2 = wlsServers.values().iterator();

                              while(it2.hasNext()) {
                                 ServerMBean aWLSServer = (ServerMBean)it2.next();
                                 if (aWLSServer.getName().equals(wlsJMSServerTarget.getName())) {
                                    if (retVal != null) {
                                       return null;
                                    }

                                    retVal = aJMSServer;
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               return retVal;
            }
         }
      }
   }

   private static JMSServerMBean[] getAllJMSServers(DeploymentData info, DomainMBean domain) {
      JMSServerMBean[] allJMSServers = domain.getJMSServers();
      if (info != null) {
         if (info.getResourceGroupTemplate() != null) {
            ResourceGroupTemplateMBean rgtm = domain.lookupResourceGroupTemplate(info.getResourceGroupTemplate());
            if (rgtm != null) {
               allJMSServers = rgtm.getJMSServers();
            }
         } else if (info.getResourceGroup() != null) {
            ResourceGroupMBean resourceGroupMBean = domain.lookupResourceGroup(info.getResourceGroup());
            if (resourceGroupMBean != null) {
               allJMSServers = resourceGroupMBean.getJMSServers();
            }
         }
      }

      return allJMSServers;
   }

   private static boolean verifyOnenessOfWLSServers(ClusterMBean globalCluster, HashMap wlsServers) {
      if (wlsServers.size() <= 0) {
         return true;
      } else {
         ClusterMBean myCluster;
         if (wlsServers.size() > 1) {
            Iterator it = wlsServers.values().iterator();

            while(it.hasNext()) {
               ServerMBean server = (ServerMBean)it.next();
               myCluster = server.getCluster();
               if (myCluster == null) {
                  return false;
               }

               if (globalCluster == null) {
                  globalCluster = myCluster;
               } else if (!globalCluster.getName().equals(myCluster.getName())) {
                  return false;
               }
            }

            return true;
         } else if (globalCluster == null) {
            return true;
         } else {
            ServerMBean singleWLSServer = null;

            for(Iterator it = wlsServers.values().iterator(); it.hasNext(); singleWLSServer = (ServerMBean)it.next()) {
            }

            myCluster = singleWLSServer.getCluster();
            if (myCluster == null) {
               return true;
            } else {
               return myCluster.getName().equals(globalCluster.getName());
            }
         }
      }
   }

   private static TargetMBean[] getJMSServerTargets(JMSServerMBean jmsServerMBean) {
      TargetMBean[] targetMBeans = jmsServerMBean.getTargets();
      TargetMBean[] targets;
      if (jmsServerMBean.getParent() instanceof ResourceGroupMBean) {
         targets = ((ResourceGroupMBean)jmsServerMBean.getParent()).getTargets();
         if (targets.length > 0) {
            targetMBeans = targets;
         }
      } else if (jmsServerMBean.getParent().getParent() instanceof DomainMBean) {
         targets = ((DomainMBean)jmsServerMBean.getParent().getParent()).getTargets();
         if (targets.length > 0) {
            targetMBeans = targets;
         }
      }

      return targetMBeans;
   }

   public static HashMap getJMSDefaultTargets(JMSBean module, DomainMBean domain, TargetMBean[] moduleTargets, DeploymentData info) {
      HashMap retVal = new HashMap();
      if (module != null && moduleTargets != null && moduleTargets.length > 0) {
         HashMap clustersRepresented = new HashMap();
         HashMap wlsServersRepresented = new HashMap();
         HashMap jmsServersRepresented = new HashMap();

         for(int lcv = 0; lcv < moduleTargets.length; ++lcv) {
            TargetMBean moduleTarget = moduleTargets[lcv];
            if (moduleTarget instanceof JMSServerMBean) {
               JMSServerMBean jmsServer = (JMSServerMBean)moduleTarget;
               jmsServersRepresented.put(jmsServer.getName(), jmsServer);
               TargetMBean[] targets = getJMSServerTargets(jmsServer);
               if (targets != null && targets.length >= 1 && targets[0] instanceof ServerMBean) {
                  moduleTarget = (ServerMBean)targets[0];
               }
            }

            if (moduleTarget instanceof ServerMBean) {
               ServerMBean wlsServer = (ServerMBean)moduleTarget;
               wlsServersRepresented.put(wlsServer.getName(), wlsServer);
               ClusterMBean cluster = wlsServer.getCluster();
               if (cluster != null) {
                  moduleTarget = cluster;
               }
            }

            if (moduleTarget instanceof ClusterMBean) {
               ClusterMBean cluster = (ClusterMBean)moduleTarget;
               clustersRepresented.put(cluster.getName(), cluster);
            }
         }

         if (clustersRepresented.size() > 1) {
            return retVal;
         } else {
            ClusterMBean theCluster = null;

            for(Iterator it = clustersRepresented.values().iterator(); it.hasNext(); theCluster = (ClusterMBean)it.next()) {
            }

            if (!verifyOnenessOfWLSServers(theCluster, wlsServersRepresented)) {
               return retVal;
            } else {
               JMSServerMBean[] jmsServerTargets = new JMSServerMBean[1];
               if (info != null && !ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(info)) {
                  jmsServerTargets[0] = getTheOneJMSServer(theCluster, wlsServersRepresented, jmsServersRepresented, domain, info);
               }

               HashSet restrictedGroups = new HashSet();

               int type;
               TargetableBean[] targetables;
               int lcv;
               String groupName;
               for(type = 0; type < 2; ++type) {
                  targetables = null;
                  ArrayList destinationBeanList;
                  int var16;
                  int var17;
                  switch (type) {
                     case 0:
                        destinationBeanList = new ArrayList();
                        QueueBean[] var28 = module.getQueues();
                        var16 = var28.length;

                        for(var17 = 0; var17 < var16; ++var17) {
                           QueueBean queueBean = var28[var17];
                           if (!queueBean.isDefaultTargetingEnabled()) {
                              destinationBeanList.add(queueBean);
                           }
                        }

                        targetables = (TargetableBean[])destinationBeanList.toArray(new QueueBean[0]);
                        break;
                     case 1:
                        destinationBeanList = new ArrayList();
                        TopicBean[] var15 = module.getTopics();
                        var16 = var15.length;

                        for(var17 = 0; var17 < var16; ++var17) {
                           TopicBean topicBean = var15[var17];
                           if (!topicBean.isDefaultTargetingEnabled()) {
                              destinationBeanList.add(topicBean);
                           }
                        }

                        targetables = (TargetableBean[])destinationBeanList.toArray(new TopicBean[0]);
                        break;
                     default:
                        continue;
                  }

                  for(lcv = 0; lcv < targetables.length; ++lcv) {
                     TargetableBean targetable = targetables[lcv];
                     groupName = targetable.getSubDeploymentName();
                     restrictedGroups.add(groupName);
                  }
               }

               for(type = 0; type < 7; ++type) {
                  targetables = null;
                  Object targetables;
                  switch (type) {
                     case 0:
                        targetables = module.getQueues();
                        break;
                     case 1:
                        targetables = module.getTopics();
                        break;
                     case 2:
                        targetables = module.getConnectionFactories();
                        break;
                     case 3:
                        targetables = module.getForeignServers();
                        break;
                     case 4:
                        targetables = module.getUniformDistributedQueues();
                        break;
                     case 5:
                        targetables = module.getUniformDistributedTopics();
                        break;
                     case 6:
                        targetables = module.getSAFImportedDestinations();
                        break;
                     default:
                        continue;
                  }

                  for(lcv = 0; lcv < ((Object[])targetables).length; ++lcv) {
                     TargetableBean targetable = ((Object[])targetables)[lcv];
                     groupName = ((TargetableBean)targetable).getSubDeploymentName();
                     if (restrictedGroups.contains(groupName)) {
                        if (jmsServerTargets[0] != null) {
                           retVal.put(groupName, jmsServerTargets);
                        }
                     } else {
                        retVal.put(groupName, moduleTargets);
                     }
                  }
               }

               return retVal;
            }
         }
      } else {
         return retVal;
      }
   }

   public static String[] getSubDeploymentNames(JMSBean module) {
      Set subDeployments = new HashSet(7);
      addTargetables(subDeployments, module.getConnectionFactories());
      addTargetables(subDeployments, module.getForeignServers());
      addTargetables(subDeployments, module.getQueues());
      addTargetables(subDeployments, module.getSAFImportedDestinations());
      addTargetables(subDeployments, module.getTopics());
      addTargetables(subDeployments, module.getUniformDistributedQueues());
      addTargetables(subDeployments, module.getUniformDistributedTopics());
      return (String[])((String[])subDeployments.toArray(new String[subDeployments.size()]));
   }

   private static void addTargetables(Set set, TargetableBean[] targets) {
      for(int i = 0; i < targets.length; ++i) {
         set.add(targets[i].getSubDeploymentName());
      }

   }

   public static void getJMSDefaultTargets(JMSBean module, DomainMBean domain, BasicDeploymentMBean deployment) {
      if (deployment != null) {
         SubDeploymentMBean[] subs = deployment.getSubDeployments();
         if (subs.length == 0) {
            TargetMBean[] deploymentTargets = deployment.getTargets();
            if (deploymentTargets != null && deploymentTargets.length > 0) {
               HashMap subDeploymentMap = getJMSDefaultTargets(module, domain, deploymentTargets, (DeploymentData)null);
               Iterator it = subDeploymentMap.keySet().iterator();

               while(it.hasNext()) {
                  String subDeploymentName = (String)it.next();
                  TargetMBean[] targets = (TargetMBean[])((TargetMBean[])subDeploymentMap.get(subDeploymentName));
                  SubDeploymentMBean subDeployment = deployment.createSubDeployment(subDeploymentName);

                  try {
                     subDeployment.setTargets(targets);
                  } catch (InvalidAttributeValueException var11) {
                  } catch (DistributedManagementException var12) {
                  }
               }

            }
         }
      }
   }
}
