package weblogic.jms.saf;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.wl.MessageLoggingParamsBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.SAFDestinationBean;
import weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFQueueBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dd.DDConfig;
import weblogic.jms.dd.DDConstants;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDManager;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.TargetListSave;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.GenericBeanListener;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class IDBeanHandler implements DDConfig, DDConstants {
   private final String name;
   private SAFImportedDestinationsBean idGroupBean;
   private ImportedDestinationGroup idGroup;
   private SAFDestinationBean destBean;
   private ApplicationContextInternal appCtx;
   private String earModuleName;
   private DDHandler ddHandler;
   private static final HashMap targetSignatures;
   private List lotsOfListeners = new LinkedList();
   private HashMap destinations = new HashMap();
   private HashMap activeTargetedServers = new HashMap();
   private HashMap preparedTargetedServers = new HashMap();
   private LinkedList addedLocalDestinations = null;
   private LinkedList preparedForRemovalTargetedServers = new LinkedList();
   private static HashMap idBeanSignatures = new HashMap();
   private static HashMap messageLoggingSignatures;
   private static HashMap domainBeanSignatures;
   private String moduleName;
   private EntityName entityName;
   private String messageLoggingFormat;
   private boolean messageLoggingEnabled = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean reconciled = false;
   private TargetListSave savedTargets = null;
   private static final String PERSISTENT_QOS = "weblogic.jms.saf.persistent.qos";
   private final ComponentInvocationContext cic;

   IDBeanHandler(ImportedDestinationGroup idGroup, ApplicationContext appCtx, EntityName entityName, SAFDestinationBean destBean, List localTargets, DomainMBean domain) throws ModuleException {
      this.idGroup = idGroup;
      this.idGroupBean = idGroup.getBean();
      this.entityName = entityName;
      this.name = JMSBeanHelper.getDecoratedName(entityName.getFullyQualifiedModuleName(), JMSBeanHelper.getDecoratedName(this.idGroupBean.getName(), destBean.getName()));
      this.destBean = destBean;
      this.appCtx = (ApplicationContextInternal)appCtx;
      this.moduleName = entityName.getFullyQualifiedModuleName();
      this.earModuleName = entityName.getEARModuleName();
      this.cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Constructing IDBeanHandler: " + this.name + ": moduleName=" + this.moduleName + " earModuleName = " + this.earModuleName + " applicationid = " + (appCtx != null ? appCtx.getApplicationId() : null) + " messageLoggingParams = " + destBean.getMessageLoggingParams());
      }

      this.messageLoggingEnabled = destBean.getMessageLoggingParams().isMessageLoggingEnabled();
      this.messageLoggingFormat = destBean.getMessageLoggingParams().getMessageLoggingFormat();
      this.savedTargets = new TargetListSave(localTargets);
      if (localTargets != null) {
         this.makeDD(domain, localTargets);
      } else {
         this.makeDD(domain, (List)null);
      }

   }

   private BasicDeploymentMBean getBasicDeployment(DomainMBean domain) {
      BasicDeploymentMBean retVal = null;
      String name;
      if ((retVal = this.appCtx.getAppDeploymentMBean()) != null) {
         name = retVal.getName();
         if ((retVal = domain.lookupAppDeployment(name)) == null) {
            throw new AssertionError("Cannot find my deployment");
         } else {
            return retVal;
         }
      } else {
         SystemResourceMBean retVal;
         if ((retVal = this.appCtx.getSystemResourceMBean()) == null) {
            throw new AssertionError("Cannot find my resource");
         } else {
            name = retVal.getName();
            return domain.lookupJMSSystemResource(name);
         }
      }
   }

   private static void fillWithSAFAgents(String name, HashMap fillMe, DomainMBean domain, ServerMBean[] servers, WebLogicMBean deploymentScope, boolean isDeployedToRGT) {
      if (servers != null) {
         for(int i = 0; i < servers.length; ++i) {
            fillWithSAFAgents(name, fillMe, domain, servers[i], deploymentScope, isDeployedToRGT);
         }

      }
   }

   private static void fillWithSAFAgents(String name, HashMap fillMe, DomainMBean domain, DynamicServersMBean dynamicServers, WebLogicMBean deploymentScope, boolean isDeployedToRGT) {
      if (dynamicServers != null && dynamicServers.getMaximumDynamicServerCount() > 0) {
         SAFAgentMBean[] allSAFAgents = getCandidateSAFAgents(domain, deploymentScope, isDeployedToRGT);

         for(int i = 0; i < allSAFAgents.length; ++i) {
            TargetMBean[] targets = allSAFAgents[i].getTargets();
            if (targets != null && targets.length != 0) {
               TargetMBean target = targets[0];
               ClusterMBean svrCluster = (ClusterMBean)dynamicServers.getParent();
               if (svrCluster != null && target instanceof ClusterMBean && svrCluster.getName().equals(target.getName()) && !hasLocalServEntryInDynCluster(allSAFAgents[i], svrCluster, fillMe)) {
                  PersistentStoreMBean store = allSAFAgents[i].getStore();
                  if (store == null || !store.getDistributionPolicy().equalsIgnoreCase("Singleton")) {
                     addLocalServEntryInDynCluster(name, allSAFAgents[i], svrCluster, fillMe);
                  }
               }
            }
         }

      }
   }

   private static void addLocalServEntryInDynCluster(String name, SAFAgentMBean safAgentConfigMbean, ClusterMBean cluster, HashMap fillMe) {
      DynamicServersMBean dynamicServers = cluster.getDynamicServers();
      if (cluster.getDynamicServers().getMaximumDynamicServerCount() > 0) {
         for(int i = 0; i < cluster.getDynamicServers().getMaximumDynamicServerCount(); ++i) {
            String serverInstanceName = cluster.getDynamicServers().getServerNamePrefix() + (cluster.getDynamicServers().getServerNameStartingIndex() + i);
            String safAgentInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(safAgentConfigMbean, serverInstanceName);
            if (isServerLocal(serverInstanceName)) {
               checkAndAdd(name, fillMe, safAgentInstanceName, safAgentConfigMbean.getName());
               return;
            }
         }
      }

   }

   private static boolean hasLocalServEntryInDynCluster(SAFAgentMBean safAgentConfigMbean, ClusterMBean cluster, HashMap fillMe) {
      DynamicServersMBean dynamicServers = cluster.getDynamicServers();
      if (cluster.getDynamicServers().getMaximumDynamicServerCount() > 0) {
         for(int i = 0; i < cluster.getDynamicServers().getMaximumDynamicServerCount(); ++i) {
            String serverInstanceName = cluster.getDynamicServers().getServerNamePrefix() + (cluster.getDynamicServers().getServerNameStartingIndex() + i);
            String safAgentInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(safAgentConfigMbean, serverInstanceName);
            if (isServerLocal(serverInstanceName)) {
               return fillMe.get(safAgentInstanceName) != null;
            }
         }
      }

      return false;
   }

   private static void checkAndAdd(String name, HashMap fillMe, String addMeKey, String addMeValue) {
      if (fillMe.put(addMeKey, addMeValue) != null) {
         throw new IllegalArgumentException("Targets of imported destination " + name + " overlap");
      }
   }

   private static void checkAndAdd(String name, HashMap fillMe, String addMe) {
      if (fillMe.put(addMe, addMe) != null) {
         throw new IllegalArgumentException("Targets of imported destination " + name + " overlap");
      }
   }

   private static boolean fillFromSAFAgentMBean(String name, HashMap fillMe, SAFAgentMBean safAgentMBean) {
      if (safAgentMBean.getServiceType().equals("Receiving-only")) {
         return false;
      } else {
         TargetMBean[] targets = safAgentMBean.getTargets();

         for(int i = 0; i < targets.length; ++i) {
            TargetMBean targetBean = targets[i];
            if (targetBean instanceof ServerMBean) {
               checkAndAdd(name, fillMe, safAgentMBean.getName());
            } else if (targetBean instanceof MigratableTargetMBean) {
               checkAndAdd(name, fillMe, safAgentMBean.getName());
            } else {
               assert targetBean instanceof ClusterMBean;

               ServerMBean[] servers = ((ClusterMBean)targetBean).getServers();

               for(int j = 0; j < servers.length; ++j) {
                  if (isServerLocal(servers[j].getName())) {
                     checkAndAdd(name, fillMe, GenericDeploymentManager.getDecoratedDistributedInstanceName(safAgentMBean, servers[j].getName()), safAgentMBean.getName());
                  }
               }
            }
         }

         return true;
      }
   }

   private static boolean isServerLocal(String serverName) {
      return ManagementService.getRuntimeAccess(kernelId) == null || ManagementService.getRuntimeAccess(kernelId).isAdminServer() || ManagementService.getRuntimeAccess(kernelId).getServerName().equals(serverName);
   }

   private static void fillWithSAFAgents(String name, HashMap fillMe, DomainMBean domain, ServerMBean server, WebLogicMBean deploymentScope, boolean isDeployedToRGT) {
      if (server != null) {
         SAFAgentMBean[] allSAFAgents = getCandidateSAFAgents(domain, deploymentScope, isDeployedToRGT);

         for(int i = 0; i < allSAFAgents.length; ++i) {
            if (!allSAFAgents[i].getServiceType().equals("Receiving-only")) {
               TargetMBean[] targets = allSAFAgents[i].getTargets();
               if (targets != null) {
                  for(int j = 0; j < targets.length; ++j) {
                     TargetMBean target = targets[j];
                     if (target instanceof ServerMBean) {
                        if (target.getName().equals(server.getName())) {
                           checkAndAdd(name, fillMe, allSAFAgents[i].getName());
                        }
                     } else {
                        ClusterMBean svrCluster = server.getCluster();
                        if (svrCluster != null) {
                           if (svrCluster.getName().equals(target.getName()) && isServerLocal(server.getName())) {
                              checkAndAdd(name, fillMe, GenericDeploymentManager.getDecoratedDistributedInstanceName(allSAFAgents[i], server.getName()), allSAFAgents[i].getName());
                           } else if (target instanceof MigratableTargetMBean) {
                              ClusterMBean mtCluster = ((MigratableTargetMBean)target).getCluster();
                              if (svrCluster.getName().equals(mtCluster.getName()) && fillMe.get(allSAFAgents[i].getName()) == null) {
                                 fillFromSAFAgentMBean(name, fillMe, allSAFAgents[i]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public static void fillWithMyTargets(String name, HashMap fillMe, DomainMBean domain, TargetMBean[] targetBeans, BasicDeploymentMBean basic) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.fillWithMyTargets() at the beginning, name=" + name + ", targetBeans=" + targetBeans);
      }

      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
      boolean isDeployedToRGT = AppDeploymentHelper.isDeployedThroughRGT(basic);
      if (targetBeans != null && targetBeans.length != 0) {
         TargetMBean target;
         for(int i = 0; i < targetBeans.length; ++i) {
            TargetMBean targetBean = targetBeans[i];
            if (targetBean instanceof ClusterMBean) {
               ServerMBean[] servers = ((ClusterMBean)targetBean).getServers();
               fillWithSAFAgents(name, fillMe, domain, servers, deploymentScope, isDeployedToRGT);
               fillWithSAFAgents(name, fillMe, domain, ((ClusterMBean)targetBean).getDynamicServers(), deploymentScope, isDeployedToRGT);
            } else if (targetBean instanceof ServerMBean) {
               fillWithSAFAgents(name, fillMe, domain, (ServerMBean)targetBean, deploymentScope, isDeployedToRGT);
            } else if (targetBean instanceof VirtualTargetMBean) {
               TargetMBean[] var9 = ((VirtualTargetMBean)targetBean).getTargets();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  target = var9[var11];
                  if (target instanceof ServerMBean) {
                     fillWithSAFAgents(name, fillMe, domain, (ServerMBean)target, deploymentScope, isDeployedToRGT);
                  } else if (target instanceof ClusterMBean) {
                     ServerMBean[] servers = ((ClusterMBean)target).getServers();
                     fillWithSAFAgents(name, fillMe, domain, servers, deploymentScope, isDeployedToRGT);
                  }
               }
            } else {
               if (!(targetBean instanceof SAFAgentMBean)) {
                  throw new IllegalArgumentException("The imported destination " + name + " has been targeted to an invalid target: " + targetBean.getName());
               }

               if (!fillFromSAFAgentMBean(name, fillMe, (SAFAgentMBean)targetBean)) {
                  throw new IllegalArgumentException("The imported destination " + name + " has been targeted to a SAF Agent that only supports receiving: " + targetBean.getName());
               }
            }
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBH.fillWithMyTargets() after first pass, name=" + name + ", fillMe=" + fillMe);
         }

         if (fillMe.isEmpty()) {
            JMSLogger.logMatchingSAFAgentNotFound("SAF Imported Destination", name, JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic));
         }

         if (fillMe.size() > 1 && deploymentScope instanceof ResourceGroupTemplateMBean) {
            throw new IllegalArgumentException(JMSExceptionLogger.logMultipleCandidateSAFAgentsLoggable("SAF Imported Destination", name, JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic), JMSModuleHelper.getConfigMBeanShortNames(deploymentScope, fillMe.values())).getMessage());
         } else {
            String safAgentMBeanName;
            if (fillMe.size() != 1) {
               Iterator iter = fillMe.values().iterator();
               String clusterName = null;

               while(iter.hasNext()) {
                  safAgentMBeanName = (String)iter.next();
                  SAFAgentMBean myBean = domain.lookupSAFAgent(safAgentMBeanName);
                  if (myBean.getTargets().length != 0) {
                     TargetMBean target = myBean.getTargets()[0];
                     ClusterMBean cluster;
                     if (target instanceof ClusterMBean) {
                        cluster = (ClusterMBean)target;
                     } else if (target instanceof MigratableTargetMBean) {
                        cluster = ((MigratableTargetMBean)target).getCluster();
                     } else {
                        cluster = ((ServerMBean)target).getCluster();
                     }

                     String myClusterName;
                     if (cluster == null) {
                        myClusterName = "Stand Alone Server " + target.getName();
                     } else {
                        myClusterName = "Cluster " + cluster.getName();
                     }

                     if (clusterName == null) {
                        clusterName = myClusterName;
                     } else if (!myClusterName.equals(clusterName)) {
                        throw new IllegalArgumentException("An Imported Destination must be targeted to SAFAgents within a single cluster scope, rather than " + clusterName + " and " + myClusterName);
                     }
                  }
               }
            }

            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("IDBH.fillWithMyTargets() after second pass, name=" + name + ", fillMe=" + fillMe);
            }

            Map fillMeWithInstances = new HashMap();
            Iterator iter = fillMe.values().iterator();

            while(true) {
               while(true) {
                  SAFAgentMBean myBean;
                  do {
                     if (!iter.hasNext()) {
                        if (JMSDebug.JMSSAF.isDebugEnabled()) {
                           JMSDebug.JMSSAF.debug("IDBH.fillWithMyTargets() after third pass, name=" + name + ", fillMeWithInstances=" + fillMeWithInstances);
                        }

                        if (fillMeWithInstances.size() != 0) {
                           fillMe.clear();
                           Iterator it = fillMeWithInstances.keySet().iterator();

                           while(it.hasNext()) {
                              String instanceName = (String)it.next();
                              fillMe.put(instanceName, fillMeWithInstances.get(instanceName));
                           }
                        }

                        if (JMSDebug.JMSSAF.isDebugEnabled()) {
                           JMSDebug.JMSSAF.debug("IDBH.fillWithMyTargets() after fourth pass, name=" + name + ", fillMe=" + fillMe);
                        }

                        return;
                     }

                     safAgentMBeanName = (String)iter.next();
                     myBean = domain.lookupSAFAgent(safAgentMBeanName);
                  } while(myBean.getTargets().length == 0);

                  target = myBean.getTargets()[0];
                  String safAgentInstanceName;
                  if (target instanceof ClusterMBean) {
                     ClusterMBean cluster = (ClusterMBean)target;
                     if (cluster.getDynamicServers().getMaximumDynamicServerCount() > 0) {
                        for(int i = 0; i < cluster.getDynamicServers().getMaximumDynamicServerCount(); ++i) {
                           String serverInstanceName = cluster.getDynamicServers().getServerNamePrefix() + (cluster.getDynamicServers().getServerNameStartingIndex() + i);
                           safAgentInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(myBean, serverInstanceName);
                           fillMeWithInstances.put(safAgentInstanceName, safAgentMBeanName);
                        }
                     }

                     ServerMBean[] memberServers = cluster.getServers();

                     for(int i = 0; i < memberServers.length; ++i) {
                        safAgentInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(myBean, memberServers[i].getName());
                        fillMeWithInstances.put(safAgentInstanceName, safAgentMBeanName);
                     }
                  } else {
                     safAgentInstanceName = GenericDeploymentManager.getDecoratedDistributedInstanceName(myBean, (String)null);
                     fillMeWithInstances.put(safAgentInstanceName, safAgentMBeanName);
                  }
               }
            }
         }
      }
   }

   private void fillWithMyTargets(HashMap fillMe, DomainMBean domain, List targets) {
      BasicDeploymentMBean basic = this.appCtx.getBasicDeploymentMBean();
      if (targets != null) {
         fillWithMyTargets(this.name, fillMe, domain, (TargetMBean[])((TargetMBean[])targets.toArray(new TargetMBean[0])), basic);
      }

   }

   private void makeDestination(String targetName) throws BeanUpdateRejectedException {
      String agentName = targetName;

      try {
         ImportedDestination dest = new ImportedDestination(this, this.idGroup, this.appCtx != null ? this.appCtx.getApplicationId() : null, new EntityName(this.entityName.getApplicationName(), this.entityName.getEARModuleName(), this.destBean.getName()), this.name, agentName, this.getTimeToLiveDefault(), this.isUseSAFTimeToLiveDefault(), this.destBean instanceof SAFQueueBean ? "queue" : "topic", this.getNonPersistentQos(), this.getMessageLoggingFormat(), this.isMessageLoggingEnabled(), this.getPersistentQos());
         dest.setRemoteJNDIName(this.destBean.getRemoteJNDIName());
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBH.makeDestination(), putting " + targetName + " in destinations");
         }

         this.destinations.put(targetName, dest);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBH.makeDestination, targetName=" + targetName + ", final map of imported destinations=" + this.destinations);
         }

      } catch (ModuleException var4) {
         throw new BeanUpdateRejectedException("Failed to create Destination", var4);
      }
   }

   public String getEntityName() {
      return this.name;
   }

   private void addMember(String targetName) throws BeanUpdateRejectedException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.addMember(), isTargetLocal(" + targetName + ")=" + this.isTargetLocal(targetName));
      }

      if (this.isTargetLocal(targetName)) {
         this.makeDestination(targetName);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBH.addMember() added Member for " + targetName);
         }
      }

   }

   private void removeMember(String targetName) {
      this.destinations.remove(targetName);
   }

   private void makeDD(DomainMBean domain, List targets) throws ModuleException {
      if (domain == null) {
         if (targets != null) {
            domain = JMSLegalHelper.getDomain((TargetMBean)targets.get(0));
         } else {
            domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         }
      }

      this.fillWithMyTargets(this.preparedTargetedServers, domain, targets);
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.makeDD() after calling fillWithMyTargets, name=" + this.name + ", preparedTargtedServers=" + this.preparedTargetedServers);
      }

      Iterator iter = this.preparedTargetedServers.keySet().iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();

         try {
            this.addMember(targetName);
         } catch (BeanUpdateRejectedException var6) {
            throw new ModuleException("Could not create Uniform Distributed Destination", var6);
         }
      }

      this.ddHandler = new DDHandler(this, false, (Context)null, false);
   }

   private boolean isTargetLocal(String targetName) {
      return this.isSAFAgentLocal(targetName);
   }

   private Iterator makeDestinationsIterator(HashMap servers) {
      LinkedList out = new LinkedList();
      if (servers != null) {
         Iterator iterator = servers.keySet().iterator();

         while(iterator.hasNext()) {
            String targetName = (String)iterator.next();
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Trying to find " + targetName + " destinations");
            }

            ImportedDestination dest = (ImportedDestination)this.destinations.get(targetName);
            if (dest != null) {
               out.add(dest);
            } else if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("did not find " + targetName + " in destinations, keys " + this.destinations.keySet());
            }
         }
      }

      return out.listIterator();
   }

   private Iterator preparedDestinationsIterator() {
      return this.makeDestinationsIterator(this.preparedTargetedServers);
   }

   private Iterator activeDestinationsIterator() {
      return this.makeDestinationsIterator(this.activeTargetedServers);
   }

   private ImportedDestination findDestination(String targetName) {
      return (ImportedDestination)this.destinations.get(targetName);
   }

   private void reregisterBeanUpdateListeners() {
      this.unregisterBeanUpdateListeners();
      this.registerBeanUpdateListeners();
   }

   public void activate(NamedEntityBean specificBean) throws ModuleException {
      this.destBean = (SAFDestinationBean)specificBean;
      Iterator iter = this.preparedDestinationsIterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.activate();
      }

      if (this.ddHandler != null) {
         this.ddHandler = DDManager.activateOrUpdate(this.ddHandler);
      }

      String myServerName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      ClusterMBean myCluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (this.preparedTargetedServers != null) {
         Iterator iter = this.preparedTargetedServers.keySet().iterator();
         List memberNameList = new ArrayList();
         List destinationList = new ArrayList();

         while(iter.hasNext()) {
            String targetName = (String)iter.next();
            SAFAgentMBean safAgent = domain.lookupSAFAgent((String)this.preparedTargetedServers.get(targetName));
            TargetMBean[] safAgentTargets = safAgent.getTargets();
            ImportedDestination dest = this.findDestination(targetName);
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("IDBH.activate(), targetName=" + targetName + ", myServerName=" + myServerName + ", myCluster=" + myCluster + ", safTarets[0]=" + safAgentTargets[0].getName());
            }

            if (myCluster == null) {
               if (safAgentTargets[0] instanceof ServerMBean && myServerName.equalsIgnoreCase(safAgentTargets[0].getName())) {
                  memberNameList.add(this.name + "@" + targetName);
                  destinationList.add(dest == null ? null : dest.getManagedDestination());
               }
            } else {
               if (safAgentTargets[0] instanceof MigratableTargetMBean) {
                  memberNameList.add(this.name + "@" + GenericDeploymentManager.getDecoratedDistributedInstanceName(targetName, safAgentTargets[0].getName()));
               } else {
                  memberNameList.add(this.name + "@" + targetName);
               }

               destinationList.add(dest == null ? null : dest.getManagedDestination());
            }
         }

         this.ddHandler.addMembers((String[])memberNameList.toArray(new String[0]), (BEDestinationImpl[])destinationList.toArray(new BEDestinationImpl[0]));
         this.activeTargetedServers = this.preparedTargetedServers;
      }

      this.preparedTargetedServers = new HashMap();
      this.reregisterBeanUpdateListeners();
   }

   private void registerBeanUpdateListeners() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.destBean, this, idBeanSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.destBean.getMessageLoggingParams(), this, messageLoggingSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener(domain, this, (Map)null, domainBeanSignatures));
      SAFAgentMBean[] agents = domain.getSAFAgents();

      for(int i = 0; i < agents.length; ++i) {
         this.lotsOfListeners.add(new GenericBeanListener(agents[i], this, (Map)null, targetSignatures));
      }

   }

   private void unregisterBeanUpdateListeners() {
      Iterator iter = this.lotsOfListeners.listIterator();

      while(iter.hasNext()) {
         ((GenericBeanListener)iter.next()).close();
      }

      this.lotsOfListeners.clear();
   }

   public void deactivate() throws ModuleException {
      this.unregisterBeanUpdateListeners();
      Iterator iter = this.activeDestinationsIterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.deactivate();
      }

      if (this.ddHandler != null) {
         this.ddHandler.deactivate();
      }

   }

   public void destroy() throws ModuleException {
      Iterator iter = this.activeDestinationsIterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.destroy();
      }

   }

   private boolean isSAFAgentLocal(String safAgentName) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.isSAFAgentLocal() for name=" + safAgentName + ", Local SAFAgents in record=" + this.idGroup.getIDEntityHelper().getLocalSAFAgents());
      }

      return this.idGroup.getIDEntityHelper().getLocalSAFAgents().get(safAgentName) != null;
   }

   private boolean onlyRemoteNewSAFAgents(DomainMBean domain) {
      HashMap newTargetedServers = new HashMap();
      this.fillWithMyTargets(newTargetedServers, domain, this.savedTargets.restoreTargets(domain));
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.onlyRemoteNeSAFAgents() after fillWithMyTargets, name=" + this.name + ", newTargetedServers=" + newTargetedServers);
      }

      boolean foundOneNewSAFAgent = false;
      Iterator iter = newTargetedServers.keySet().iterator();

      String targetName;
      while(iter.hasNext()) {
         targetName = (String)iter.next();
         if (!this.activeTargetedServers.containsKey(targetName)) {
            foundOneNewSAFAgent = true;
            if (this.isSAFAgentLocal(targetName)) {
               return false;
            }
         }
      }

      iter = this.activeTargetedServers.keySet().iterator();

      while(iter.hasNext()) {
         targetName = (String)iter.next();
         if (!newTargetedServers.containsKey(targetName)) {
            foundOneNewSAFAgent = true;
         }
      }

      return foundOneNewSAFAgent;
   }

   public void prepareUpdate(DomainMBean domain, int action) throws BeanUpdateRejectedException {
      if (this.onlyRemoteNewSAFAgents(domain)) {
         this.reconcileTargets(domain);
      }
   }

   public void rollbackUpdate() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (this.onlyRemoteNewSAFAgents(domain)) {
         try {
            this.activateTargetUpdates(false);
         } catch (BeanUpdateRejectedException var3) {
            throw new AssertionError("activateTargetUpdates failed");
         }
      }
   }

   public void activateUpdate() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (this.onlyRemoteNewSAFAgents(domain)) {
         try {
            this.activateTargetUpdates(true);
         } catch (BeanUpdateRejectedException var3) {
            throw new AssertionError("activateTargetUpdates failed");
         }
      }
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) throws ModuleException {
      TargetListSave oldSavedTargets = this.savedTargets;
      this.savedTargets = new TargetListSave(targets);
      if (oldSavedTargets == null && this.savedTargets != null || oldSavedTargets != null && this.savedTargets == null || oldSavedTargets.size() != this.savedTargets.size()) {
         synchronized(this) {
            this.reconciled = false;
         }
      }

      if (proposedDomain == null) {
         throw new AssertionError("Cannot find domain!");
      } else {
         try {
            this.reconcileTargets(proposedDomain);
         } catch (BeanUpdateRejectedException var6) {
            throw new ModuleException("Rejected targeting change", var6);
         }
      }
   }

   public void activateChangeOfTargets() throws ModuleException {
      try {
         this.activateTargetUpdates(true);
      } catch (BeanUpdateRejectedException var2) {
         throw new ModuleException("Rejected targeting change", var2);
      }
   }

   public void rollbackChangeOfTargets() {
      try {
         this.activateTargetUpdates(false);
      } catch (BeanUpdateRejectedException var2) {
         throw new AssertionError("Rejected targeting change" + var2);
      }
   }

   public void prepare() throws ModuleException {
      Iterator iter = this.preparedDestinationsIterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.prepare();
      }

   }

   public void remove() throws ModuleException {
      Iterator iter = this.activeDestinationsIterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.remove();
      }

   }

   public void unprepare() throws ModuleException {
      Iterator iter = this.preparedDestinationsIterator();

      ImportedDestination dest;
      while(iter.hasNext()) {
         dest = (ImportedDestination)iter.next();
         dest.unprepare();
      }

      iter = this.activeDestinationsIterator();

      while(iter.hasNext()) {
         dest = (ImportedDestination)iter.next();
         dest.unprepare();
      }

   }

   public void setLocalJNDIName(String localJNDIName) {
      this.ddHandler.setJNDIName(this.getJNDIName());
   }

   private void reconcileAddedLocalDestinations(HashMap newTargetedServers) throws BeanUpdateRejectedException {
      this.addedLocalDestinations = new LinkedList();
      Iterator iter = newTargetedServers.keySet().iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBH.reconcileAddedLocalDestinations(), targetName=" + targetName + ", isTargetLocal=" + this.isTargetLocal(targetName));
         }

         if (!this.destinations.containsKey(targetName) && this.isTargetLocal(targetName)) {
            this.addedLocalDestinations.add(targetName);
            this.makeDestination(targetName);
            ImportedDestination dest = this.findDestination(targetName);
            if (dest != null) {
               try {
                  dest.prepare();
               } catch (ModuleException var6) {
                  throw new BeanUpdateRejectedException("Cannot prepare destination; ", var6);
               }
            }
         }
      }

   }

   private void reconcileAddedMembers(HashMap newTargetedServers) throws BeanUpdateRejectedException {
      this.preparedTargetedServers = new HashMap();
      Iterator iter = newTargetedServers.keySet().iterator();

      while(iter.hasNext()) {
         String safAgentInstanceName = (String)iter.next();
         String safAgentConfigName = (String)newTargetedServers.get(safAgentInstanceName);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBH.reconcileAddedMembers(), safAgentInstanceName=" + safAgentInstanceName + ", safAgentConfigName= " + safAgentConfigName);
         }

         if (!this.activeTargetedServers.containsKey(safAgentInstanceName)) {
            if (!this.destinations.containsKey(safAgentInstanceName)) {
               this.addMember(safAgentInstanceName);
               ImportedDestination dest = this.findDestination(safAgentInstanceName);
               if (dest != null) {
                  try {
                     dest.prepare();
                  } catch (ModuleException var7) {
                     throw new BeanUpdateRejectedException("Cannot prepare destination; ", var7);
                  }
               }
            }

            this.preparedTargetedServers.put(safAgentInstanceName, safAgentConfigName);
         }
      }

   }

   private void reconcileSubtractedMembers(HashMap newTargetedServers) {
      this.preparedForRemovalTargetedServers = new LinkedList();
      Iterator iter = this.activeTargetedServers.keySet().iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         if (!newTargetedServers.containsKey(targetName)) {
            this.preparedForRemovalTargetedServers.add(targetName);
         }
      }

   }

   public void reconcileTargets(DomainMBean domain) throws BeanUpdateRejectedException {
      HashMap newTargetedServers = new HashMap();
      synchronized(this) {
         if (this.reconciled) {
            return;
         }

         this.reconciled = true;
      }

      this.fillWithMyTargets(newTargetedServers, domain, this.savedTargets.restoreTargets(domain));
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.reconcileTargets() after fillWithMyTargets, name=" + this.name + ", newTargetedServers=" + newTargetedServers);
      }

      this.reconcileAddedMembers(newTargetedServers);
      this.reconcileAddedLocalDestinations(newTargetedServers);
      this.reconcileSubtractedMembers(newTargetedServers);
   }

   private void activateAddedLocalDestinations(boolean isActivate) throws BeanUpdateRejectedException {
      if (this.addedLocalDestinations != null) {
         Iterator iter = this.addedLocalDestinations.listIterator();

         while(iter.hasNext()) {
            String targetName = (String)iter.next();
            if (!isActivate) {
               this.destinations.remove(targetName);
            } else {
               ImportedDestination dest = this.findDestination(targetName);
               if (dest != null) {
                  try {
                     dest.activate();
                  } catch (ModuleException var6) {
                     throw new BeanUpdateRejectedException("activate failed", var6);
                  }
               }
            }
         }

         this.addedLocalDestinations = null;
      }
   }

   private void activateAddedMembers(boolean isActivate) throws BeanUpdateRejectedException {
      if (this.preparedTargetedServers != null) {
         String targetName;
         ImportedDestination dest;
         for(Iterator iter = this.preparedTargetedServers.keySet().iterator(); iter.hasNext(); this.ddHandler.addMember(this.name + "@" + targetName, dest == null ? null : dest.getManagedDestination())) {
            targetName = (String)iter.next();
            dest = this.findDestination(targetName);
            if (!isActivate) {
               this.removeMember(targetName);
               if (dest != null) {
                  try {
                     dest.unprepare();
                  } catch (ModuleException var6) {
                     throw new AssertionError("Unprepare failed");
                  }
               }
            } else {
               if (dest != null) {
                  try {
                     dest.activate();
                  } catch (ModuleException var7) {
                     throw new BeanUpdateRejectedException("activate failed", var7);
                  }
               }

               this.activeTargetedServers.put(targetName, targetName);
            }
         }

         this.preparedTargetedServers = null;
      }
   }

   private void activateSubtractedMembers(boolean isActivate) throws BeanUpdateRejectedException {
      if (this.preparedForRemovalTargetedServers != null) {
         String targetName;
         for(Iterator iter = this.preparedForRemovalTargetedServers.listIterator(); iter.hasNext(); this.ddHandler.removeMember(this.name + "@" + targetName)) {
            targetName = (String)iter.next();
            if (isActivate) {
               ImportedDestination dest = this.findDestination(targetName);
               this.removeMember(targetName);
               if (dest != null) {
                  try {
                     dest.deactivate();
                     dest.destroy();
                     DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
                     if (domain.lookupSAFAgent(targetName) == null) {
                        dest.remove();
                     } else {
                        dest.unprepare();
                     }
                  } catch (ModuleException var6) {
                     throw new BeanUpdateRejectedException("Cannot bring down member for " + targetName, var6);
                  }
               }

               this.activeTargetedServers.remove(targetName);
            }
         }

         this.preparedForRemovalTargetedServers = null;
      }
   }

   private void activateSubtractedLocalDestinations() throws BeanUpdateRejectedException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      Iterator iter = this.destinations.keySet().iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         ImportedDestination dest = this.findDestination(targetName);
         if (!this.isTargetLocal(targetName)) {
            try {
               dest.deactivate();
               dest.destroy();
               if (domain.lookupSAFAgent(targetName) == null) {
                  dest.remove();
               } else {
                  dest.unprepare();
               }
            } catch (ModuleException var6) {
               throw new BeanUpdateRejectedException("destroy failed", var6);
            }

            iter.remove();
         }
      }

   }

   public synchronized void activateTargetUpdates(boolean isActivate) throws BeanUpdateRejectedException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.activatetargetUpdates: isActive?" + isActivate + " conciled = " + this.reconciled);
      }

      if (this.reconciled) {
         this.reconciled = false;
         this.activateAddedLocalDestinations(isActivate);
         this.activateAddedMembers(isActivate);
         this.activateSubtractedMembers(isActivate);
         this.activateSubtractedLocalDestinations();
      }
   }

   public void startAny() throws BeanUpdateRejectedException {
      Iterator iter = this.lotsOfListeners.listIterator();
      DomainMBean domain = null;

      while(iter.hasNext()) {
         GenericBeanListener gbl = (GenericBeanListener)iter.next();
         if (gbl.getCurrentEvent() != null) {
            domain = JMSLegalHelper.getDomain((WebLogicMBean)gbl.getCurrentEvent().getProposedBean());
         }
      }

      assert domain != null;

      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var4 = null;

      try {
         this.reconcileTargets(domain);
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void finishAny(boolean isActivate) throws BeanUpdateRejectedException {
      this.activateTargetUpdates(isActivate);
      if (isActivate) {
         this.reregisterBeanUpdateListeners();
      }

   }

   public void startAddTargets(TargetMBean ignore) throws BeanUpdateRejectedException {
      this.startAny();
   }

   public void finishAddTargets(TargetMBean ignore, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAny(isActivate);
   }

   public void startRemoveTargets(TargetMBean ignore) throws BeanUpdateRejectedException {
      this.startAny();
   }

   public void finishRemoveTargets(TargetMBean ignore, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAny(isActivate);
   }

   public void startAddSAFAgents(SAFAgentMBean ignore) throws BeanUpdateRejectedException {
      this.startAny();
   }

   public void finishAddSAFAgents(SAFAgentMBean ignore, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAny(isActivate);
   }

   public void startRemoveSAFAgents(SAFAgentMBean ignore) throws BeanUpdateRejectedException {
      this.startAny();
   }

   public void finishRemoveSAFAgents(SAFAgentMBean ignore, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAny(isActivate);
   }

   public void startAddServers(ServerMBean ignore) throws BeanUpdateRejectedException {
      this.startAny();
   }

   public void finishAddServers(ServerMBean ignore, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAny(isActivate);
   }

   public void startRemoveServers(ServerMBean ignore) throws BeanUpdateRejectedException {
      this.startAny();
   }

   public void finishRemoveServers(ServerMBean ignore, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAny(isActivate);
   }

   public String getApplicationName() {
      return this.appCtx != null ? this.appCtx.getApplicationId() : null;
   }

   public String getEARModuleName() {
      return this.earModuleName;
   }

   public String getReferenceName() {
      String useJNDIName = null;
      useJNDIName = this.destBean.getRemoteJNDIName();
      if (useJNDIName != null) {
         useJNDIName = useJNDIName.replace('/', '.');
      }

      return this.idGroup.getRemoteSAFContextNameWithoutPartition() + "@@" + useJNDIName;
   }

   public int getForwardDelay() {
      return 0;
   }

   public boolean getResetDeliveryCountOnForward() {
      return true;
   }

   public String getJNDIName() {
      String prefix = this.idGroupBean.getJNDIPrefix();
      if (prefix == null) {
         prefix = "";
      }

      String base;
      if (this.destBean.getLocalJNDIName() != null) {
         base = this.destBean.getLocalJNDIName();
      } else {
         base = this.destBean.getRemoteJNDIName();
      }

      return prefix + base;
   }

   public int getLoadBalancingPolicyAsInt() {
      return 2;
   }

   public String getName() {
      return this.name;
   }

   public String getSAFExportPolicy() {
      return null;
   }

   public int getType() {
      return this.destBean instanceof SAFQueueBean ? 0 : 1;
   }

   public String getUnitOfOrderRouting() {
      return this.destBean.getUnitOfOrderRouting();
   }

   public boolean isDefaultUnitOfOrder() {
      return false;
   }

   public void setRemoteJNDIName(String remoteJNDIName) {
      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.setRemoteJNDIName(remoteJNDIName);
      }

   }

   boolean isMessageLoggingParamsCustomized() {
      MessageLoggingParamsBean mlp = this.destBean.getMessageLoggingParams();
      Class mlpClass = mlp.getClass();
      Method[] methods = mlpClass.getMethods();
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         String methodName = method.getName();
         if (methodName.startsWith("set") && methodName.indexOf("MessageLogging") != -1 && mlp.isSet(methodName.substring(3))) {
            return true;
         }
      }

      return false;
   }

   synchronized void updateMessageLoggingEnabled(boolean value) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("updateMessageLoggingEnabled: value = " + value);
      }

      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.getManagedDestination().setMessageLoggingEnabled(value);
      }

   }

   public synchronized void setMessageLoggingEnabled(boolean value) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("setMessageLoggingEnabled: value = " + value);
      }

      this.updateMessageLoggingEnabled(value);
      this.messageLoggingEnabled = value;
   }

   private synchronized boolean isMessageLoggingEnabled() {
      return !this.isMessageLoggingParamsCustomized() ? this.idGroup.isMessageLoggingEnabled() : this.messageLoggingEnabled;
   }

   synchronized void updateMessageLoggingFormat(String value) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("updateMessageLoggingFormat: value = " + value);
      }

      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.getManagedDestination().setMessageLoggingFormat(value);
      }

   }

   public synchronized void setMessageLoggingFormat(String value) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("setMessageLoggingFormat: value = " + value);
      }

      this.updateMessageLoggingFormat(value);
      this.messageLoggingFormat = value;
   }

   private synchronized String getMessageLoggingFormat() {
      return !this.isMessageLoggingParamsCustomized() ? this.idGroup.getMessageLoggingFormat() : this.messageLoggingFormat;
   }

   public void setNonPersistentQos(String nonPersistentQos) {
      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.setNonPersistentQos(nonPersistentQos);
      }

   }

   public String getNonPersistentQos() {
      return this.destBean.getNonPersistentQos();
   }

   public String getPersistentQos() {
      String result = "Exactly-Once";
      String value = System.getProperty("weblogic.jms.saf.persistent.qos");
      if (value != null) {
         if (value.toUpperCase().equals("At-Least-Once".toUpperCase())) {
            result = "At-Least-Once";
         } else if (value.toUpperCase().equals("At-Most-Once".toUpperCase())) {
            result = "At-Most-Once";
         }
      } else {
         result = this.destBean.getPersistentQos();
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDBH.getPersistentQos()  " + result);
      }

      return result;
   }

   public void setSAFErrorHandling(SAFErrorHandlingBean safErrorHandling) {
      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         if (safErrorHandling == null) {
            dest.setSAFErrorHandling(this.idGroupBean.getSAFErrorHandling());
         } else {
            dest.setSAFErrorHandling(safErrorHandling);
         }
      }

   }

   public void setTimeToLiveDefault(long timeToLiveDefault) {
      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.setTimeToLiveDefault(timeToLiveDefault);
      }

   }

   public long getTimeToLiveDefault() {
      return this.destBean.getTimeToLiveDefault();
   }

   public boolean isUseSAFTimeToLiveDefault() {
      return this.destBean.isUseSAFTimeToLiveDefault();
   }

   public void setUseSAFTimeToLiveDefault(boolean useSafTimeToLiveDefault) {
      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.setUseSAFTimeToLiveDefault(useSafTimeToLiveDefault);
      }

   }

   public void setJNDIPrefix(String JNDIPrefix) {
      this.setLocalJNDIName(this.destBean.getLocalJNDIName());
   }

   public void remoteContextChanged() {
      Iterator iter = this.destinations.values().iterator();

      while(iter.hasNext()) {
         ImportedDestination dest = (ImportedDestination)iter.next();
         dest.remoteContextChanged();
      }

   }

   SAFErrorHandlingBean getSafErrorHandling() {
      return this.destBean.getSAFErrorHandling();
   }

   MessageLoggingParamsBean getMessageLoggingParamsBean() {
      return this.destBean.getMessageLoggingParams();
   }

   public static SAFAgentMBean[] getCandidateSAFAgents(DomainMBean domain, WebLogicMBean deploymentScope, boolean isDeployedToRGT) {
      SAFAgentMBean[] allSAFAgents = domain.getSAFAgents();
      if (deploymentScope == null) {
         return allSAFAgents;
      } else {
         List filteredSAFAgents = new ArrayList();
         SAFAgentMBean[] canidateSAFAgents = allSAFAgents;
         int var6 = allSAFAgents.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            SAFAgentMBean safAgentBean = canidateSAFAgents[var7];
            if (JMSModuleHelper.isTargetInDeploymentScope(safAgentBean, deploymentScope)) {
               if (deploymentScope instanceof DomainMBean) {
                  filteredSAFAgents.add(safAgentBean);
               } else {
                  PersistentStoreMBean store = safAgentBean.getStore();
                  if (store != null && store.getDistributionPolicy().equalsIgnoreCase("Distributed")) {
                     filteredSAFAgents.add(safAgentBean);
                  }
               }
            }
         }

         canidateSAFAgents = (SAFAgentMBean[])filteredSAFAgents.toArray(new SAFAgentMBean[0]);
         if (isDeployedToRGT && deploymentScope instanceof ResourceGroupMBean) {
            ResourceGroupTemplateMBean associatedRGT = ((ResourceGroupMBean)deploymentScope).getResourceGroupTemplate();
            if (associatedRGT == null) {
               throw new AssertionError("When a JMS module is deployed to a resource group template, the resource group must have a resource group template associated to it");
            }

            associatedRGT = domain.lookupResourceGroupTemplate(associatedRGT.getName());
            if (associatedRGT == null) {
               throw new AssertionError("When a JMS module is deployed to a resource group template, the resource group template associated to the resource group must exist in the domain.");
            }

            canidateSAFAgents = (SAFAgentMBean[])((SAFAgentMBean[])JMSModuleHelper.excludeRGDefinedJMSTargets((ResourceGroupMBean)deploymentScope, associatedRGT, filteredSAFAgents, SAFAgentMBean.class));
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("IDBeanHandler:getCandidateSAFAgents canidateSAFAgents " + Arrays.toString(canidateSAFAgents));
         }

         return canidateSAFAgents;
      }
   }

   static {
      idBeanSignatures.put("RemoteJNDIName", String.class);
      idBeanSignatures.put("LocalJNDIName", String.class);
      idBeanSignatures.put("NonPersistentQos", String.class);
      idBeanSignatures.put("SAFErrorHandling", SAFErrorHandlingBean.class);
      idBeanSignatures.put("TimeToLiveDefault", Long.TYPE);
      idBeanSignatures.put("UseSAFTimeToLiveDefault", Boolean.TYPE);
      messageLoggingSignatures = new HashMap();
      messageLoggingSignatures.put("MessageLoggingFormat", String.class);
      messageLoggingSignatures.put("MessageLoggingEnabled", Boolean.TYPE);
      domainBeanSignatures = new HashMap();
      domainBeanSignatures.put("SAFAgents", SAFAgentMBean.class);
      domainBeanSignatures.put("Servers", ServerMBean.class);
      targetSignatures = new HashMap();
      targetSignatures.put("Targets", TargetMBean.class);
   }
}
