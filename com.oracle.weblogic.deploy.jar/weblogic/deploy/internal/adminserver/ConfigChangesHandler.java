package weblogic.deploy.internal.adminserver;

import java.beans.BeanInfo;
import java.io.File;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import javax.enterprise.deploy.shared.ModuleType;
import javax.management.InvalidAttributeValueException;
import weblogic.application.ApplicationFileManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.beans.factory.DeploymentBeanFactory;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeploymentOrder;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.service.ConfigurationContext;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.BeanUpdateEvent.ParentEntity;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.PartitionRuntimeStateManagerContract;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DomainTargetedMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.management.deploy.internal.DeployerRuntimeImpl;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.DeploymentServerService;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.internal.DescriptorInfoUtils;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.TargetingUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.jars.VirtualJarFile;

final class ConfigChangesHandler {
   private static final String TARGETS_PROP_NAME = "Targets";
   private static final String UNTARGETED_PROP_NAME = "Untargeted";
   private static final String CANDIDATE_SERVERS_PROP_NAME = "ConstrainedCandidateServers";
   private static final String TASK_ID_PREFIX = "weblogic.deploy.configChangeTask.";
   private static int curTaskId = 0;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static AppRuntimeStateRuntimeMBean appRTMBean;
   private static DeploymentBeanFactory beanFactory = DeploymentServerService.getDeploymentBeanFactory();
   private static Map clonedMBeans = new HashMap();
   private static Object lock = new Object();
   private static ActiveBeanUtil activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
   private static PartitionRuntimeStateManagerContract partitionRuntimeStateManager;

   private static PartitionRuntimeStateManagerContract getPartitionRuntimeStateManager() {
      synchronized(lock) {
         if (partitionRuntimeStateManager == null) {
            partitionRuntimeStateManager = (PartitionRuntimeStateManagerContract)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManagerContract.class, new Annotation[0]);
         }
      }

      return partitionRuntimeStateManager;
   }

   public static List[] configChanged(DeploymentRequest request, ConfigurationContext configCtx, DeploymentManager deploymentManager) {
      boolean jmsServerAdded = false;
      boolean subDepAddedToJMSSystemResource = false;
      Iterator configChanges = getDescriptorDiff(configCtx);
      if (configChanges == null) {
         return null;
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.configChanged: deployRequest=" + request.getId() + ", diffs=" + dumpDiff(configCtx));
         }

         OrderedDeployments deployments = new OrderedDeployments(deploymentManager);
         List addedSubDeps = new ArrayList();
         List removedSubDeps = new ArrayList();
         List addedTargets = new ArrayList();
         List removedTargets = new ArrayList();
         List addedOrRemovedSystemResources = new ArrayList();
         List deploymentsAdded = new ArrayList();
         List deploymentsRemoved = new ArrayList();

         while(true) {
            BeanUpdateEvent event;
            BeanUpdateEvent.PropertyUpdate[] updates;
            DescriptorBean sourceBean;
            boolean targetsChanged;
            do {
               if (!configChanges.hasNext()) {
                  handleMTAppRedeployWithSamePath(deploymentManager, deployments);
                  Map externalDescDiffs = getExternalDescritorDiffs(configCtx);
                  handleExternalTreeChanges(deployments, externalDescDiffs, request.getId(), addedOrRemovedSystemResources, deploymentManager);
                  addedOrRemovedSystemResources.clear();
                  boolean okToSwitch = !subDepAddedToJMSSystemResource || !jmsServerAdded;
                  return deployments.getAllDeployments(request, okToSwitch, deploymentsAdded, deploymentsRemoved);
               }

               event = (BeanUpdateEvent)configChanges.next();
               updates = event.getUpdateList();
               sourceBean = event.getSourceBean();
               targetsChanged = false;
            } while(isSkipThisEvent(event, deploymentManager));

            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ConfigChangesHandler.configChanged event: " + event);
            }

            for(int i = 0; i < updates.length; ++i) {
               BeanUpdateEvent.PropertyUpdate curUpdate = updates[i];
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("ConfigChangesHandler.configChanged curUpdate: " + curUpdate);
               }

               Object addedObject;
               if (beanFactory.isDeployerInitiatedBeanUpdate(sourceBean, curUpdate)) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("ConfigChangesHandler.isDeployerInitiatedBeanUpdate " + curUpdate);
                  }

                  AppDeploymentMBean removedApp;
                  if (curUpdate.getUpdateType() == 2) {
                     addedObject = curUpdate.getAddedObject();
                     if (isSkipPartitionOrResourceGroup(addedObject, deploymentManager)) {
                        if (Debug.isDeploymentDebugEnabled()) {
                           Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing event = " + curUpdate);
                        }
                     } else if (addedObject instanceof AppDeploymentMBean) {
                        removedApp = (AppDeploymentMBean)addedObject;
                        deploymentsAdded.add(removedApp);
                     }
                  } else if (curUpdate.getUpdateType() == 3) {
                     addedObject = curUpdate.getRemovedObject();
                     if (isSkipPartitionOrResourceGroup(addedObject, deploymentManager)) {
                        if (Debug.isDeploymentDebugEnabled()) {
                           Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing event = " + curUpdate);
                        }
                     } else if (addedObject instanceof AppDeploymentMBean) {
                        removedApp = (AppDeploymentMBean)addedObject;
                        deploymentsRemoved.add(removedApp);
                     }
                  }
               } else {
                  switch (curUpdate.getUpdateType()) {
                     case 1:
                        DescriptorBean proposedBean = event.getProposedBean();
                        if (isSkipPartitionOrResourceGroup(proposedBean, deploymentManager)) {
                           if (Debug.isDeploymentDebugEnabled()) {
                              Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing event = " + curUpdate);
                           }
                        } else {
                           if (isTargetsChanged(sourceBean, curUpdate) || isCandidateServersChanged(sourceBean, curUpdate)) {
                              targetsChanged = true;
                           }

                           if (sourceBean instanceof AppDeploymentMBean) {
                              boolean addDeploymentToResourceGroup = false;
                              AppDeploymentMBean sourceAppDeploymentMBean = (AppDeploymentMBean)sourceBean;
                              AppDeploymentMBean proposedAppDeploymentMBean = (AppDeploymentMBean)proposedBean;
                              if (Debug.isDeploymentDebugEnabled()) {
                                 Debug.deploymentDebug("sourceAppDeploymentMBean.getPlanPath:   " + sourceAppDeploymentMBean.getPlanPath());
                                 Debug.deploymentDebug("sourceAppDeploymentMBean.getAbsolutePlanPath:   " + sourceAppDeploymentMBean.getAbsolutePlanPath());
                                 Debug.deploymentDebug("sourceAppDeploymentMBean.getPlanDir:   " + sourceAppDeploymentMBean.getPlanDir());
                                 Debug.deploymentDebug("sourceAppDeploymentMBean.getSourcePath:   " + sourceAppDeploymentMBean.getSourcePath());
                                 Debug.deploymentDebug("sourceAppDeploymentMBean.getAbsoluteSourcePath:   " + sourceAppDeploymentMBean.getAbsoluteSourcePath());
                                 Debug.deploymentDebug("proposedAppDeploymentMBean.getPlanPath:   " + proposedAppDeploymentMBean.getPlanPath());
                                 Debug.deploymentDebug("proposedAppDeploymentMBean.getAbsolutePlanPath:   " + proposedAppDeploymentMBean.getAbsolutePlanPath());
                                 Debug.deploymentDebug("proposedAppDeploymentMBean.getPlanDir:   " + proposedAppDeploymentMBean.getPlanDir());
                                 Debug.deploymentDebug("proposedAppDeploymentMBean.getSourcePath:   " + proposedAppDeploymentMBean.getSourcePath());
                                 Debug.deploymentDebug("proposedAppDeploymentMBean.getAbsoluteSourcePath:   " + proposedAppDeploymentMBean.getAbsoluteSourcePath());
                              }

                              if ("PlanPath".equals(curUpdate.getPropertyName())) {
                                 sourceAppDeploymentMBean.setPlanPath(proposedAppDeploymentMBean.getPlanPath());
                                 if (ApplicationUtils.isFromResourceGroup(sourceAppDeploymentMBean)) {
                                    handleRedeployToResourceGroup(deploymentManager, sourceAppDeploymentMBean);
                                    processAddedDeploymentForResourceGroup(proposedAppDeploymentMBean, (ResourceGroupMBean)proposedAppDeploymentMBean.getParent(), deployments);
                                 }

                                 if (Debug.isDeploymentDebugEnabled()) {
                                    Debug.deploymentDebug("sourceAppDeploymentMBean.getPlanPath after:   " + sourceAppDeploymentMBean.getPlanPath());
                                    Debug.deploymentDebug("sourceAppDeploymentMBean.getPlanDir after:   " + sourceAppDeploymentMBean.getPlanDir());
                                 }
                              }

                              if ("SourcePath".equals(curUpdate.getPropertyName())) {
                                 if (!sourceAppDeploymentMBean.getAbsoluteSourcePath().equals(proposedAppDeploymentMBean.getAbsoluteSourcePath())) {
                                    sourceAppDeploymentMBean.setSourcePath(proposedAppDeploymentMBean.getSourcePath());
                                    if (ApplicationUtils.isFromResourceGroup(sourceAppDeploymentMBean)) {
                                       handleRedeployToResourceGroup(deploymentManager, sourceAppDeploymentMBean);
                                       processAddedDeploymentForResourceGroup(proposedAppDeploymentMBean, (ResourceGroupMBean)proposedAppDeploymentMBean.getParent(), deployments);
                                    }

                                    if (Debug.isDeploymentDebugEnabled()) {
                                       Debug.deploymentDebug("sourceAppDeploymentMBean.getSourcePath after:   " + sourceAppDeploymentMBean.getSourcePath() + " for event " + event);
                                    }
                                 } else if (Debug.isDeploymentDebugEnabled()) {
                                    Debug.deploymentDebug("ConfigChangesHandler.configChanged absoluteSourcePath for " + sourceAppDeploymentMBean + " has not changed");
                                 }
                              }
                           }
                        }
                        break;
                     case 2:
                        addedObject = curUpdate.getAddedObject();
                        if (isSkipPartitionOrResourceGroup(addedObject, deploymentManager)) {
                           if (Debug.isDeploymentDebugEnabled()) {
                              Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing event = " + curUpdate);
                           }
                        } else {
                           if (addedObject instanceof BasicDeploymentMBean) {
                              BasicDeploymentMBean bdm = (BasicDeploymentMBean)addedObject;
                              if (ApplicationUtils.isFromResourceGroup(bdm)) {
                                 processAddedDeploymentForResourceGroup(bdm, (ResourceGroupMBean)bdm.getParent(), deployments);
                              } else if (!ApplicationUtils.isFromResourceGroupTemplate(bdm)) {
                                 deploymentAdded(deployments, (BasicDeploymentMBean)addedObject);
                              }

                              if (addedObject instanceof SystemResourceMBean) {
                                 if (addedObject instanceof JMSSystemResourceMBean) {
                                    SubDeploymentMBean[] subs = ((JMSSystemResourceMBean)addedObject).getSubDeployments();
                                    if (subs != null && subs.length > 0) {
                                       subDepAddedToJMSSystemResource = true;
                                    }
                                 }

                                 addedOrRemovedSystemResources.add(addedObject);
                              }
                           } else if (addedObject instanceof SubDeploymentMBean) {
                              addedSubDeps.add(addedObject);
                              if (event.getProposedBean() instanceof JMSSystemResourceMBean) {
                                 subDepAddedToJMSSystemResource = true;
                              }
                           } else if (isTargetsChanged(sourceBean, curUpdate)) {
                              addedTargets.add(addedObject);
                           } else if (isCandidateServersChanged(sourceBean, curUpdate)) {
                              targetsChanged = true;
                           }

                           if (addedObject instanceof JMSServerMBean) {
                              jmsServerAdded = true;
                           }
                        }
                        break;
                     case 3:
                        Object removedObject = curUpdate.getRemovedObject();
                        if (removedObject instanceof PartitionMBean) {
                           cleanClonedMBeansForPartition((PartitionMBean)removedObject);
                        }

                        if (isSkipPartitionOrResourceGroup(removedObject, deploymentManager)) {
                           if (Debug.isDeploymentDebugEnabled()) {
                              Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing event = " + curUpdate);
                           }
                        } else if (removedObject instanceof BasicDeploymentMBean) {
                           BasicDeploymentMBean bdm = (BasicDeploymentMBean)removedObject;
                           if (ApplicationUtils.isFromResourceGroup(bdm)) {
                              processRemovedDeploymentForResourceGroup(bdm, deployments);
                           } else if (!ApplicationUtils.isFromResourceGroupTemplate(bdm)) {
                              deploymentRemoved(deployments, bdm);
                           }

                           if (removedObject instanceof SystemResourceMBean) {
                              addedOrRemovedSystemResources.add(removedObject);
                           }
                        } else if (removedObject instanceof SubDeploymentMBean) {
                           removedSubDeps.add(removedObject);
                        } else if (isTargetsChanged(sourceBean, curUpdate)) {
                           removedTargets.add(removedObject);
                        } else if (isCandidateServersChanged(sourceBean, curUpdate)) {
                           targetsChanged = true;
                        }
                  }
               }
            }

            if (targetsChanged) {
               if (ApplicationUtils.isFromResourceGroup(event.getProposedBean())) {
                  processSubDeploymentChangesInResourceGroup(deployments, sourceBean, event.getProposedBean());
               } else if (!ApplicationUtils.isFromResourceGroupTemplate(event.getProposedBean())) {
                  targetsChanged(deployments, getTargets((WebLogicMBean)sourceBean, deploymentManager), getTargets((WebLogicMBean)event.getProposedBean(), deploymentManager), (WebLogicMBean)event.getProposedBean());
               }
            }

            if (removedTargets.size() > 0) {
               targetsRemoved(deployments, (WebLogicMBean)event.getProposedBean(), removedTargets);
               removedTargets.clear();
            }

            if (addedTargets.size() > 0) {
               targetsAdded(deployments, (WebLogicMBean)event.getProposedBean(), Arrays.asList(getTargets((WebLogicMBean)sourceBean, deploymentManager)), addedTargets);
               addedTargets.clear();
            }

            if (removedSubDeps.size() > 0) {
               subDepsRemoved(deployments, (TargetInfoMBean)event.getProposedBean(), removedSubDeps);
               removedSubDeps.clear();
            }

            if (addedSubDeps.size() > 0) {
               DescriptorBean parentBean = event.getProposedBean();
               if (ApplicationUtils.isAppDeploymentFromResourceGroup(parentBean)) {
                  List clonedBeans = new ArrayList();
                  TargetInfoMBean parentOfClones = processAddedSubDeploymentToResourceGroup(parentBean, addedSubDeps, clonedBeans, deploymentManager);
                  subDepsAdded(deployments, parentOfClones, clonedBeans);
               } else if (!ApplicationUtils.isAppDeploymentFromResourceGroupTemplate(parentBean)) {
                  subDepsAdded(deployments, (TargetInfoMBean)parentBean, addedSubDeps);
               }

               addedSubDeps.clear();
            }
         }
      }
   }

   private static void cleanClonedMBeansForPartition(PartitionMBean partitionMBean) {
      Iterator it = clonedMBeans.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         BasicDeploymentMBean depl = (BasicDeploymentMBean)entry.getValue();
         String partitionName = ApplicationVersionUtils.getPartitionName(depl.getName());
         if (partitionName != null && partitionName.equals(partitionMBean.getName())) {
            it.remove();
         }
      }

   }

   private static String getPartitionName(Object bean, DeploymentManager deploymentManager) {
      String partitionName = null;
      if (!(bean instanceof DescriptorBean)) {
         return null;
      } else {
         DescriptorBean parent;
         if (ApplicationUtils.isFromResourceGroup((DescriptorBean)bean)) {
            for(parent = (DescriptorBean)bean; !(parent instanceof ResourceGroupMBean); parent = parent.getParentBean()) {
            }

            if (parent instanceof ResourceGroupMBean) {
               ResourceGroupMBean resourceGroupMBean = (ResourceGroupMBean)parent;
               if (resourceGroupMBean.getParent() instanceof PartitionMBean) {
                  partitionName = resourceGroupMBean.getParent().getName();
               }
            }
         } else if (ApplicationUtils.isFromPartition((DescriptorBean)bean)) {
            for(parent = (DescriptorBean)bean; !(parent instanceof PartitionMBean); parent = parent.getParentBean()) {
            }

            if (parent instanceof PartitionMBean) {
               PartitionMBean partitionMBean = (PartitionMBean)parent;
               partitionName = partitionMBean.getName();
            }
         }

         return partitionName;
      }
   }

   private static ResourceGroupMBean findResourceGroupMBean(Object bean, DeploymentManager deploymentManager) {
      ResourceGroupMBean resourceGroupMBean = null;
      if (!(bean instanceof DescriptorBean)) {
         return null;
      } else {
         DescriptorBean parent;
         if (ApplicationUtils.isFromResourceGroup((DescriptorBean)bean)) {
            for(parent = (DescriptorBean)bean; !(parent instanceof ResourceGroupMBean); parent = parent.getParentBean()) {
            }

            if (parent instanceof ResourceGroupMBean) {
               resourceGroupMBean = (ResourceGroupMBean)parent;
            }
         } else if (ApplicationUtils.isFromResourceGroupTemplate((DescriptorBean)bean)) {
            for(parent = (DescriptorBean)bean; !(parent instanceof ResourceGroupTemplateMBean); parent = parent.getParentBean()) {
            }

            if (parent instanceof ResourceGroupTemplateMBean) {
               resourceGroupMBean = findResourceGroup((ResourceGroupTemplateMBean)parent, deploymentManager);
            }
         }

         return resourceGroupMBean;
      }
   }

   private static ResourceGroupLifecycleOperations.RGState getResourceGroupState(ResourceGroupMBean resourceGroupMBean) {
      ResourceGroupLifecycleOperations.RGState rgState = null;
      String partitionName = null;
      if (resourceGroupMBean != null) {
         String rgName = resourceGroupMBean.getName();
         if (resourceGroupMBean.getParent() instanceof PartitionMBean) {
            partitionName = resourceGroupMBean.getParent().getName();
         }

         rgState = ApplicationUtils.getResourceGroupState(partitionName, rgName);
      }

      return rgState;
   }

   private static ResourceGroupMBean findResourceGroup(ResourceGroupTemplateMBean rgt, DeploymentManager deploymentManager) {
      return null;
   }

   private static boolean isSkipPartitionOrResourceGroup(Object updatedObject, DeploymentManager deploymentManager) {
      boolean isSkip = false;
      String partitionName = getPartitionName(updatedObject, deploymentManager);
      PartitionRuntimeMBean.State partitionState = ApplicationUtils.getPartitionState(partitionName);
      ResourceGroupMBean rgmb = findResourceGroupMBean(updatedObject, deploymentManager);
      ResourceGroupLifecycleOperations.RGState rgState = getResourceGroupState(rgmb);
      if (partitionName != null && Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.configChanged partition " + partitionName + "'s state is " + partitionState);
      }

      if (rgmb != null && Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.configChanged resourcegroup " + rgmb.getName() + "'s state is " + rgState);
      }

      if (!State.isShutdown(partitionState) && !State.isShutdownBooted(partitionState)) {
         if (!State.isShutdownBooted(partitionState) && RGState.isShutdown(rgState)) {
            isSkip = true;
         }
      } else {
         isSkip = ApplicationUtils.checkForAdminRGWhenPartitionInShutdown(partitionName, rgmb);
      }

      if (isSkip && Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing " + updatedObject);
      }

      return isSkip;
   }

   private static boolean isSkipVirtualTarget(VirtualTargetMBean vtBean, DeploymentManager deploymentManager) {
      return false;
   }

   private static boolean isSkipThisEvent(BeanUpdateEvent event, DeploymentManager deploymentManager) {
      DescriptorBean sourceBean = event.getSourceBean();
      if (sourceBean instanceof VirtualTargetMBean && isSkipVirtualTarget((VirtualTargetMBean)sourceBean, deploymentManager)) {
         return true;
      } else {
         if (sourceBean instanceof ResourceGroupMBean || sourceBean instanceof PartitionMBean) {
            BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

            for(int i = 0; i < updates.length; ++i) {
               if (updates[i].getPropertyName().equals("Targets")) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("ConfigChangesHandler.configChanged skip processing event = " + event + " as it will be handled by " + sourceBean);
                  }

                  return true;
               }
            }
         }

         return sourceBean instanceof CoherenceClusterSystemResourceMBean;
      }
   }

   public static void restartSystemResource(SystemResourceMBean systemResource, DeploymentManager deploymentManager) throws ManagementException {
      TargetMBean[] targets = getTargets(systemResource, deploymentManager);
      if (targets != null && targets.length != 0) {
         DeploymentData info = new DeploymentData();

         for(int i = 0; i < targets.length; ++i) {
            info.addTarget(targets[i].getName(), (String[])null);
         }

         Deployment deployment = findOrCreateDeployment(9, systemResource, info, deploymentManager, false, false, true);
         DeploymentTaskRuntimeMBean task = ((weblogic.deploy.internal.Deployment)deployment).getDeploymentTaskRuntime();
         task.start();
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.restartSystemResource returning immediately since SystemResource '" + systemResource.getName() + "' does not have any targets");
         }

      }
   }

   private static void deploymentAdded(OrderedDeployments deployments, BasicDeploymentMBean addedBean) {
      deploymentAdded(deployments, addedBean, getTargets(addedBean, deployments.deploymentManager));
   }

   private static void deploymentAdded(OrderedDeployments deployments, BasicDeploymentMBean addedBean, TargetMBean[] targets) {
      if (targets != null && targets.length != 0) {
         DeployInfo deployInfo = createDeploymentInfo(addedBean, Arrays.asList(targets), 1, true);
         deployInfo.deployData.setNewApp(true);
         deployInfo.deployData.setPartition(addedBean.getPartitionName());
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.deploymentAdded for " + addedBean.getName() + ", deployData=" + deployInfo.deployData);
         }

         deployInfo.requireRestart = false;
         deployments.addDeploymentInfo(deployInfo);
      }
   }

   private static void deploymentRemoved(OrderedDeployments deployments, BasicDeploymentMBean removedBean) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.deploymentRemoved for " + removedBean.getName() + ", bean: " + removedBean);
         TargetMBean[] targets = getTargets(removedBean, deployments.deploymentManager);

         for(int i = 0; i < targets.length; ++i) {
            Set removedTargets = targets[i].getServerNames();
            String[] dummy = new String[0];
            String[] targetList = (String[])((String[])removedTargets.toArray(dummy));
            Debug.deploymentDebug("target[" + i + "] = " + targets[i]);

            for(int j = 0; j < targetList.length; ++j) {
               Debug.deploymentDebug("\t sub-target[" + j + "] = " + targetList[j]);
            }
         }
      }

      DeploymentData info = new DeploymentData();
      String[] dummy = new String[0];
      String[] targets = (String[])((String[])TargetHelper.getAllTargetedServers(removedBean).toArray(dummy));
      if (targets != null && targets.length > 0) {
         info.setGlobalTargets(targets);
         DeployInfo deployInfo = new UndeployDeployInfo(removedBean, info, false);
         deployInfo.requireRestart = false;
         deployments.addDeploymentInfo(deployInfo);
      }

   }

   private static void subDepsAdded(OrderedDeployments deployments, TargetInfoMBean subDepsParent, List subDeps) {
      if (subDeps.size() != 0) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.subDepsAdded for " + subDepsParent + ", subDeps=" + subDeps + ", isJMSModule=" + isJMSModule(subDepsParent));
         }

         if (isJMSModule(subDepsParent)) {
            jmsSubDepsChanged(deployments, subDeps, true);
         } else {
            appSubDepsChanged(deployments, subDepsParent, subDeps, true);
         }

      }
   }

   private static void subDepsRemoved(OrderedDeployments deployments, TargetInfoMBean subDepsParent, List subDeps) {
      if (subDeps.size() != 0) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.subDepsRemoved for " + subDepsParent + ", subDeps=" + subDeps + ", isJMSModule=" + isJMSModule(subDepsParent));
         }

         if (isJMSModule(subDepsParent)) {
            jmsSubDepsChanged(deployments, subDeps, false);
         } else {
            appSubDepsChanged(deployments, subDepsParent, subDeps, false);
         }

      }
   }

   private static void jmsSubDepsChanged(OrderedDeployments deployments, List subDeps, boolean isDeploy) {
      DeployInfo deployInfo = new RedeployDeployInfo((BasicDeploymentMBean)null, new DeploymentData(), isDeploy);
      Iterator iter = subDeps.iterator();

      while(iter.hasNext()) {
         SubDeploymentMBean subDep = (SubDeploymentMBean)iter.next();
         TargetMBean[] targets = subDep.getTargets();
         if (targets != null && targets.length != 0) {
            JMSSystemResourceMBean parent = (JMSSystemResourceMBean)subDep.getParent();
            SystemResourceMBean[] clones = findClonedMBeans(parent, deployments.deploymentManager);
            if (clones != null) {
               parent = (JMSSystemResourceMBean)clones[0];
               ((AbstractDescriptorBean)parent).markSet("Name");
            }

            if (parent.getParent().getParent() instanceof PartitionMBean) {
               PartitionMBean partition = (PartitionMBean)parent.getParent().getParent();
               deployInfo.deployData.setPartition(partition.getName());
            }

            populateDeploymentInfo(deployInfo, subDep, Arrays.asList(targets));
         }
      }

      deployInfo.requireRestart = false;
      deployments.addDeploymentInfo(deployInfo);
   }

   private static void appSubDepsChanged(OrderedDeployments deployments, TargetInfoMBean subDepsParent, List subDeps, boolean isDeploy) {
      Iterator iter = subDeps.iterator();

      while(iter.hasNext()) {
         SubDeploymentMBean subDep = (SubDeploymentMBean)iter.next();
         TargetMBean[] oldTargets;
         TargetMBean[] newTargets;
         if (isDeploy) {
            oldTargets = getTargets(subDepsParent, deployments.deploymentManager);
            newTargets = getTargets(subDep, deployments.deploymentManager);
         } else {
            oldTargets = getTargets(subDep, deployments.deploymentManager);
            newTargets = getTargets(subDepsParent, deployments.deploymentManager);
         }

         List undeployTargets = getTargetsNotIn(oldTargets, newTargets, deployments.deploymentManager);
         List deployTargets = getTargetsNotIn(newTargets, oldTargets, deployments.deploymentManager);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.appSubDepsChanged('" + subDep + ") undeployTargets=" + undeployTargets + ", deployTargets=" + deployTargets);
         }

         if (undeployTargets.size() > 0) {
            targetsRemoved(deployments, subDep, undeployTargets);
         }

         if (deployTargets.size() > 0) {
            targetsAdded(deployments, subDep, Arrays.asList(oldTargets), deployTargets);
         }
      }

   }

   private static void targetsAdded(OrderedDeployments deployments, WebLogicMBean newBean, List oldTargets, List addedTargets) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.targetsAdded for " + newBean + ", addedTargets=" + getTargetsString(addedTargets));
      }

      if (newBean instanceof TargetInfoMBean) {
         deploymentTargetsAdded(deployments, (TargetInfoMBean)newBean, oldTargets, addedTargets);
      } else {
         WebLogicMBean source = newBean;
         boolean isMigratableTarget = newBean instanceof MigratableTargetMBean;
         if (isMigratableTarget) {
            source = getJMSServer((MigratableTargetMBean)newBean, deployments.deploymentManager);
         }

         if (source instanceof DeploymentMBean && source instanceof TargetMBean) {
            deploymentMBeanTargetChanged(deployments, (DeploymentMBean)source, Collections.EMPTY_LIST, addedTargets, isMigratableTarget || source instanceof VirtualHostMBean || source instanceof VirtualTargetMBean, 9);
         }
      }

   }

   private static void targetsRemoved(OrderedDeployments deployments, WebLogicMBean newBean, List removedTargets) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.targetsRemoved for " + newBean + ", removedTargets=" + getTargetsString(removedTargets));
      }

      if (newBean instanceof TargetInfoMBean) {
         deploymentTargetsRemoved(deployments, (TargetInfoMBean)newBean, removedTargets);
      } else {
         WebLogicMBean source = newBean;
         boolean isMigratableTarget = newBean instanceof MigratableTargetMBean;
         if (isMigratableTarget) {
            source = getJMSServer((MigratableTargetMBean)newBean, deployments.deploymentManager);
         }

         if (source instanceof DeploymentMBean && source instanceof TargetMBean) {
            deploymentMBeanTargetChanged(deployments, (DeploymentMBean)source, removedTargets, Collections.EMPTY_LIST, isMigratableTarget || source instanceof VirtualHostMBean || source instanceof VirtualTargetMBean, 12);
         }
      }

   }

   private static void targetsChanged(OrderedDeployments deployments, TargetMBean[] oldTargets, TargetMBean[] newTargets, WebLogicMBean bean) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler.targetsChanged for " + bean + ", oldTargets=" + getTargetsString(oldTargets) + ", newTargets=" + getTargetsString(newTargets));
      }

      List undeployTargets = getTargetsNotIn(oldTargets, newTargets, deployments.deploymentManager);
      List deployTargets = getTargetsNotIn(newTargets, oldTargets, deployments.deploymentManager);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ConfigChangesHandler undeployTargets=" + undeployTargets + ", deployTargets=" + deployTargets);
      }

      if (undeployTargets.size() > 0) {
         targetsRemoved(deployments, bean, undeployTargets);
      }

      if (deployTargets.size() > 0) {
         targetsAdded(deployments, bean, Arrays.asList(oldTargets), deployTargets);
      }

      if (undeployTargets.size() == 0 && deployTargets.size() == 0) {
         targetsAdded(deployments, bean, Arrays.asList(oldTargets), Arrays.asList(newTargets));
      }

   }

   private static void deploymentTargetsAdded(OrderedDeployments deployments, TargetInfoMBean newBean, List oldTargets, List deployTargets) {
      if (deployTargets.size() != 0) {
         List deployTargetsTemp = new ArrayList(deployTargets);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.depTargetsAdded for " + newBean + ", targets=" + deployTargetsTemp + ", isJMSModule=" + isJMSModule(newBean));
         }

         if (!(newBean instanceof SystemResourceMBean)) {
            Iterator dItr = deployTargetsTemp.iterator();

            label57:
            while(true) {
               TargetMBean targetBean;
               ClusterMBean cBean;
               do {
                  if (!dItr.hasNext()) {
                     if (deployTargetsTemp.size() == 0) {
                        return;
                     }
                     break label57;
                  }

                  targetBean = (TargetMBean)dItr.next();
                  String targetName = targetBean.getName();
                  cBean = TargetingUtils.getTargetCluster(targetName);
               } while(cBean == null);

               ClusterDeployInfo clusterDeployInfo = new ClusterDeployInfo((BasicDeploymentMBean)null, new DeploymentData(), 1, true, cBean.getName());
               clusterDeployInfo.setDeploymentManager(deployments.deploymentManager);
               DeployInfo delegateDeployInfo = clusterDeployInfo.delegate;
               List clusterTargetList = new ArrayList();
               clusterTargetList.add(targetBean);
               populateDeploymentInfo(delegateDeployInfo, newBean, clusterTargetList);
               dItr.remove();
               if ((delegateDeployInfo.hasModuleTargets() || delegateDeployInfo.hasSubModuleTargets()) && (isJMSModule(newBean) || isJMSModule((TargetInfoMBean)newBean.getParent()))) {
                  clusterDeployInfo.setOp(9);
               }

               if (isTargetedToCluster(oldTargets, cBean)) {
                  clusterDeployInfo.setOp(9);
               }

               clusterDeployInfo.setRequireRestart(false);
               deployments.addDeploymentInfo(clusterDeployInfo);
            }
         }

         DeployInfo deployInfo = createDeploymentInfo(newBean, deployTargetsTemp, 1, true);
         if ((deployInfo.hasModuleTargets() || deployInfo.hasSubModuleTargets()) && (isJMSModule(newBean) || isJMSModule((TargetInfoMBean)newBean.getParent()))) {
            deployInfo.op = 9;
         }

         deployInfo.requireRestart = false;
         deployments.addDeploymentInfo(deployInfo);
      }
   }

   private static void deploymentTargetsRemoved(OrderedDeployments deployments, TargetInfoMBean newBean, List undeployTargets) {
      if (undeployTargets.size() != 0) {
         List undeployTargetsTemp = new ArrayList(undeployTargets);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.depTargetsRemoved for " + newBean + ", targets=" + undeployTargetsTemp + ", isJMSModule=" + isJMSModule(newBean));
         }

         if (!(newBean instanceof SystemResourceMBean)) {
            Iterator udItr = undeployTargetsTemp.iterator();

            label77:
            while(true) {
               TargetMBean targetBean;
               ClusterMBean cBean;
               do {
                  if (!udItr.hasNext()) {
                     if (undeployTargetsTemp.size() == 0) {
                        return;
                     }
                     break label77;
                  }

                  targetBean = (TargetMBean)udItr.next();
                  String targetName = targetBean.getName();
                  cBean = TargetingUtils.getTargetCluster(targetName);
               } while(cBean == null);

               ClusterDeployInfo clusterDeployInfo = new ClusterDeployInfo((BasicDeploymentMBean)null, new DeploymentData(), 4, false, cBean.getName());
               clusterDeployInfo.setDeploymentManager(deployments.deploymentManager);
               DeployInfo delegateDeployInfo = clusterDeployInfo.delegate;
               List clusterTargetList = new ArrayList();
               clusterTargetList.add(targetBean);
               populateDeploymentInfo(delegateDeployInfo, newBean, clusterTargetList);
               udItr.remove();
               if ((delegateDeployInfo.hasModuleTargets() || delegateDeployInfo.hasSubModuleTargets()) && (isJMSModule(newBean) || isJMSModule((TargetInfoMBean)newBean.getParent()))) {
                  clusterDeployInfo.setOp(9);
               }

               if (isTargetedToCluster(Arrays.asList(getTargets(newBean, deployments.deploymentManager)), cBean)) {
                  clusterDeployInfo.setOp(9);
               }

               clusterDeployInfo.setRequireRestart(false);
               deployments.addDeploymentInfo(clusterDeployInfo);
            }
         }

         DeployInfo deployInfo = createDeploymentInfo(newBean, undeployTargetsTemp, 4, false);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.depTargetsRemoved : created DeploymentInfo : " + deployInfo);
         }

         if ((deployInfo.hasModuleTargets() || deployInfo.hasSubModuleTargets()) && (isJMSModule(newBean) || isJMSModule((TargetInfoMBean)newBean.getParent()))) {
            deployInfo.op = 9;
         }

         TargetMBean[] newTargets = getTargets(newBean, deployments.deploymentManager);
         if (newTargets != null && newTargets.length > 0) {
            Set newNames = getTargetServerNames(Arrays.asList(newTargets));
            Set undeployNames = getTargetServerNames(undeployTargetsTemp);
            Iterator var15 = undeployNames.iterator();

            while(var15.hasNext()) {
               String undeployName = (String)var15.next();
               if (newNames.contains(undeployName)) {
                  deployInfo.op = 9;
                  break;
               }
            }
         }

         deployInfo.requireRestart = false;
         deployments.addDeploymentInfo(deployInfo);
      }
   }

   private static void createAndAddClusterDeploymentInfos(BasicDeploymentMBean mbean, List addedTargets, List removedTargets) {
   }

   private static void deploymentMBeanTargetChanged(OrderedDeployments deployments, DeploymentMBean target, List undeployTargets, List deployTargets, boolean isMigratableTarget, int operation) {
      DomainMBean domain = getDomainBean(false, deployments.deploymentManager);
      String targetName = target.getName();
      DeploymentData deployData = null;
      DeploymentData undeployData = null;
      JMSSystemResourceMBean[] sysResources = domain.getJMSSystemResources();

      for(int i = 0; i < sysResources.length; ++i) {
         JMSSystemResourceMBean sysResource = sysResources[i];
         if (deployData == null) {
            deployData = new DeploymentData();
         }

         if (undeployData == null) {
            undeployData = new DeploymentData();
         }

         if (isModuleTargeted((BasicDeploymentMBean)sysResource, targetName, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget)) {
            createAndAddDeploymentInfos(deployments, sysResource, undeployTargets, deployTargets, undeployData, deployData, operation);
            deployData = null;
            undeployData = null;
         }
      }

      AppDeploymentMBean[] deps = AppDeploymentHelper.getAppsAndLibs(domain);

      for(int i = 0; i < deps.length; ++i) {
         AppDeploymentMBean dep = deps[i];
         if (!isEar(dep)) {
            if (deployData == null) {
               deployData = new DeploymentData();
            }

            if (undeployData == null) {
               undeployData = new DeploymentData();
            }

            if (isModuleTargeted((BasicDeploymentMBean)dep, targetName, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget)) {
               createAndAddDeploymentInfos(deployments, dep, undeployTargets, deployTargets, undeployData, deployData, operation);
               deployData = null;
               undeployData = null;
            }
         } else {
            SubDeploymentMBean[] subDeps = dep.getSubDeployments();

            for(int j = 0; j < subDeps.length; ++j) {
               SubDeploymentMBean subDep = subDeps[j];
               if (deployData == null) {
                  deployData = new DeploymentData();
               }

               if (undeployData == null) {
                  undeployData = new DeploymentData();
               }

               if (isModuleTargeted(subDep, targetName, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget)) {
                  createAndAddDeploymentInfos(deployments, dep, undeployTargets, deployTargets, undeployData, deployData, operation);
                  deployData = null;
                  undeployData = null;
               }
            }
         }
      }

   }

   private static boolean isModuleTargeted(BasicDeploymentMBean module, String target, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, boolean isMigratableTarget) {
      if (isTargetIn(target, module.getTargets())) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("isModuleTargeted addTarget " + target + " for " + module.getName());
         }

         addTargetsToData(target, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget);
         return true;
      } else {
         return isJMSModule(module) && isSubModulesTargeted((String)null, module.getSubDeployments(), target, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget);
      }
   }

   private static boolean isModuleTargeted(SubDeploymentMBean module, String target, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, boolean isMigratableTarget) {
      if (isTargetIn(target, module.getTargets())) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("isModuleTargeted addModuleTarget(" + module.getName() + "," + target + ")");
         }

         addModuleTargetsToData(module.getName(), target, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget);
         return true;
      } else {
         return isJMSModule(module) && isSubModulesTargeted(module.getName(), module.getSubDeployments(), target, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget);
      }
   }

   private static boolean isSubModulesTargeted(String module, SubDeploymentMBean[] subModules, String target, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, boolean isMigratableTarget) {
      if (subModules != null && subModules.length != 0) {
         boolean targeted = false;

         for(int i = 0; i < subModules.length; ++i) {
            SubDeploymentMBean subModule = subModules[i];
            if (isTargetIn(target, subModule.getTargets())) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("isSubModulesTargeted addSubModuleTarget(" + module + "," + subModule.getName() + "," + target + ")");
               }

               addSubModuleTargetsToData(module, subModule.getName(), target, undeployTargets, deployTargets, undeployData, deployData, isMigratableTarget);
               targeted = true;
            }
         }

         return targeted;
      } else {
         return false;
      }
   }

   private static void handleExternalTreeChanges(OrderedDeployments deployments, Map externalDiffMap, long requestId, List addedOrRemovedSystemResources, DeploymentManager deploymentManager) {
      if (externalDiffMap != null && externalDiffMap.size() > 0) {
         Iterator externalDiffIterator = externalDiffMap.keySet().iterator();

         while(externalDiffIterator.hasNext()) {
            DescriptorBean desc = (DescriptorBean)externalDiffIterator.next();
            SystemResourceMBean sysResource = (SystemResourceMBean)DescriptorInfoUtils.getDescriptorConfigExtension(desc.getDescriptor());
            if (addedOrRemovedSystemResources != null && !addedOrRemovedSystemResources.contains(sysResource)) {
               handleGlobalDescriptorChanges(deployments, sysResource, externalDiffMap, requestId, deploymentManager);
            }
         }

      }
   }

   private static void handleGlobalDescriptorChanges(OrderedDeployments deployments, SystemResourceMBean bean, Map externalDescriptorDiffs, long requestId, DeploymentManager deploymentManager) {
      if (!isSkipPartitionOrResourceGroup(bean, deploymentManager)) {
         if (!(bean instanceof CoherenceClusterSystemResourceMBean)) {
            EditDirectoryManager pendingMgr = EditDirectoryManager.getDirectoryManager(deployments.deploymentManager.getPartitionName(), deployments.deploymentManager.getEditSessionName());
            String descFileName = bean.getDescriptorFileName();
            if (pendingMgr.pendingFileExists(descFileName)) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("handleGlobalDescriptorChanges: bean=" + bean + ", file=" + descFileName);
               }

               Object descriptorChanges = externalDescriptorDiffs.get(bean.getResource());
               boolean requiresRestart = false;
               if (descriptorChanges != null) {
                  requiresRestart = hasNonDynamicChanges(bean, (ArrayList)descriptorChanges, requestId);
                  if (requiresRestart && Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("bean=" + bean + " has non dynamic changes");
                  }
               }

               SystemResourceMBean[] clonedSystemResources = findClonedMBeans(bean, deployments.deploymentManager);
               if (clonedSystemResources == null) {
                  PartitionMBean partition = activeBeanUtil.findContainingPartition(bean);
                  if (partition != null) {
                     clonedSystemResources = new SystemResourceMBean[]{(SystemResourceMBean)PartitionProcessor.findExistingClone(getDomainBean(false, deployments.deploymentManager), partition, bean, bean.getClass())};
                  }
               }

               if (clonedSystemResources != null) {
                  SystemResourceMBean[] var15 = clonedSystemResources;
                  int var12 = clonedSystemResources.length;

                  for(int var13 = 0; var13 < var12; ++var13) {
                     SystemResourceMBean clonedSystemResource = var15[var13];
                     addSystemResourceDeploymentInfo(deployments, clonedSystemResource, requiresRestart, descFileName);
                  }
               } else {
                  addSystemResourceDeploymentInfo(deployments, bean, requiresRestart, descFileName);
               }
            }

         }
      }
   }

   private static SystemResourceMBean findCloneFromRGT(SystemResourceMBean bean, ResourceGroupMBean[] resourceGroupMBeans, DeploymentManager deploymentManager) {
      if (bean.getParent() instanceof ResourceGroupTemplateMBean) {
         ResourceGroupTemplateMBean owningRGT = (ResourceGroupTemplateMBean)bean.getParent();
         ResourceGroupMBean[] var4 = resourceGroupMBeans;
         int var5 = resourceGroupMBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ResourceGroupMBean rg = var4[var6];
            if (owningRGT.equals(rg.getResourceGroupTemplate())) {
               SystemResourceMBean[] var8 = rg.getSystemResources();
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  SystemResourceMBean systemResourceMBean = var8[var10];
                  if (bean.getName().equals(ApplicationVersionUtils.getNonPartitionName(systemResourceMBean.getName()))) {
                     SystemResourceMBean clonedFromRGT = (SystemResourceMBean)clonedMBeans.get(systemResourceMBean);
                     if (clonedFromRGT != null) {
                        return clonedFromRGT;
                     }

                     PartitionMBean partition = null;
                     if (rg.getParent() instanceof PartitionMBean) {
                        partition = (PartitionMBean)rg.getParent();
                     }

                     if (partition != null) {
                        clonedFromRGT = (SystemResourceMBean)PartitionProcessor.findExistingClone(getDomainBean(false, deploymentManager), partition, systemResourceMBean, systemResourceMBean.getClass());
                     }

                     return clonedFromRGT;
                  }
               }
            }
         }
      }

      return null;
   }

   private static SystemResourceMBean[] findClonesFromRGT(SystemResourceMBean bean, DeploymentManager deploymentManager) {
      return null;
   }

   private static SystemResourceMBean[] findClonedMBeans(SystemResourceMBean bean, DeploymentManager deploymentManager) {
      SystemResourceMBean clonedSystemResource = (SystemResourceMBean)clonedMBeans.get(bean);
      if (clonedSystemResource == null) {
         clonedSystemResource = (SystemResourceMBean)activeBeanUtil.toActiveBean(bean);
         if (clonedSystemResource != null && clonedSystemResource.getParent() instanceof ResourceGroupTemplateMBean) {
            return findClonesFromRGT(clonedSystemResource, deploymentManager);
         }
      }

      return clonedSystemResource != null ? new SystemResourceMBean[]{clonedSystemResource} : findClonesFromRGT(bean, deploymentManager);
   }

   private static void addSystemResourceDeploymentInfo(OrderedDeployments deployments, SystemResourceMBean bean, boolean requiresRestart, String descFileName) {
      DeploymentData data = new DeploymentData();
      TargetMBean[] targets = getTargets(bean, deployments.deploymentManager);
      if (targets != null && targets.length != 0) {
         List targetList = Arrays.asList(targets);
         data.addSubModuleTarget((String)null, descFileName, getTargetNames(targetList));
         DeployInfo deployInfo = createNewDeploymentInfo(bean, data, 9, true);
         deployInfo.requireRestart = requiresRestart;
         deployments.addDeploymentInfo(deployInfo);
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("bean=" + bean + " has no targets");
         }

      }
   }

   private static BeanInfo getBeanInfo(DescriptorBean bean) {
      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInstance(bean, true, (String)null);
      return beanInfo;
   }

   private static boolean hasNonDynamicChanges(SystemResourceMBean bean, ArrayList diffs, long requestId) {
      if (!diffs.isEmpty()) {
         Iterator iterator = diffs.iterator();

         while(iterator.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)iterator.next();
            DescriptorBean proposedBean = event.getProposedBean();
            if (proposedBean == null) {
               return false;
            }

            BeanInfo beanInfo = getBeanInfo(proposedBean);
            if (beanInfo == null) {
               return false;
            }

            BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

            for(int i = 0; i < updates.length; ++i) {
               if (!updates[i].isDynamic()) {
                  DeployerRuntimeLogger.logNonDynamicPropertyChange(requestId, bean.getName(), updates[i].getPropertyName(), event.getSourceBean().toString());
                  if (bean.getPartitionName() != null && !"DOMAIN".equals(bean.getPartitionName())) {
                     event.setParentEntity(ParentEntity.partition);
                  }

                  return true;
               }
            }
         }
      }

      return false;
   }

   private static Iterator getDescriptorDiff(ConfigurationContext configCtx) {
      if (configCtx == null) {
         return Collections.EMPTY_LIST.iterator();
      } else {
         DescriptorDiff diff = (DescriptorDiff)configCtx.getContextComponent("beanUpdateDescriptorDiffId");
         return diff.iterator();
      }
   }

   private static Map getExternalDescritorDiffs(ConfigurationContext configCtx) {
      return configCtx == null ? null : (Map)configCtx.getContextComponent("externalDescritorDiffId");
   }

   private static boolean isTargetsChanged(DescriptorBean source, BeanUpdateEvent.PropertyUpdate update) {
      if ((source instanceof TargetInfoMBean || source instanceof DeploymentMBean && source instanceof TargetMBean) && update.getPropertyName().equals("Targets")) {
         return true;
      } else {
         return source instanceof SubDeploymentMBean && update.getPropertyName().equals("Untargeted");
      }
   }

   private static boolean isCandidateServersChanged(DescriptorBean source, BeanUpdateEvent.PropertyUpdate update) {
      return source instanceof MigratableTargetMBean && update.getPropertyName().equals("ConstrainedCandidateServers");
   }

   private static DeployInfo createDeploymentInfo(TargetInfoMBean newBean, List targets, int op, boolean isDeploy) {
      DeployInfo info = createNewDeploymentInfo(op, isDeploy);
      populateDeploymentInfo(info, newBean, targets);
      return info;
   }

   private static DeployInfo createNewDeploymentInfo(int op, boolean isDeploy) {
      return createNewDeploymentInfo((BasicDeploymentMBean)null, new DeploymentData(), op, isDeploy);
   }

   private static DeployInfo createNewDeploymentInfo(BasicDeploymentMBean dep, DeploymentData data, int op, boolean isDeploy) {
      if (op == 1) {
         return new DeployDeployInfo(dep, data, isDeploy);
      } else {
         return (DeployInfo)(op == 9 ? new RedeployDeployInfo(dep, data, isDeploy) : new UndeployDeployInfo(dep, data, isDeploy));
      }
   }

   private static void populateDeploymentInfo(DeployInfo info, TargetInfoMBean newBean, List targets) {
      DeploymentData data = info.deployData;
      BasicDeploymentMBean topLevelDepBean;
      if (newBean instanceof BasicDeploymentMBean) {
         topLevelDepBean = (BasicDeploymentMBean)newBean;
         if (targets != null && !targets.isEmpty()) {
            Iterator iter = targets.iterator();

            while(iter.hasNext()) {
               TargetMBean curTarget = (TargetMBean)iter.next();
               data.addTarget(curTarget.getName(), (String[])null);
            }
         }
      } else {
         WebLogicMBean parent = newBean.getParent();
         String moduleName;
         if (parent instanceof BasicDeploymentMBean) {
            topLevelDepBean = (BasicDeploymentMBean)parent;
            if (isEar(topLevelDepBean)) {
               moduleName = newBean.getName();
               if (targets != null && !targets.isEmpty()) {
                  Iterator iter = targets.iterator();

                  while(iter.hasNext()) {
                     TargetMBean curTarget = (TargetMBean)iter.next();
                     data.addModuleTarget(moduleName, curTarget.getName());
                  }
               }
            } else {
               moduleName = newBean.getName();
               data.addSubModuleTarget((String)null, moduleName, getTargetNames(targets));
            }
         } else {
            topLevelDepBean = (BasicDeploymentMBean)parent.getParent();
            moduleName = newBean.getName();
            String moduleName = parent.getName();
            data.addSubModuleTarget(moduleName, moduleName, getTargetNames(targets));
         }
      }

      info.topLevelDepBean = topLevelDepBean;
   }

   private static String[] getTargetNames(List targets) {
      if (targets != null && targets.size() != 0) {
         String[] names = new String[targets.size()];

         for(int i = 0; i < targets.size(); ++i) {
            names[i] = ((TargetMBean)targets.get(i)).getName();
         }

         return names;
      } else {
         return null;
      }
   }

   private static Deployment findOrCreateDeployment(int deployTaskOp, BasicDeploymentMBean newBean, DeploymentData data, DeploymentManager deploymentManager, boolean proposedDomain, boolean requiresRestart, boolean isAControlOperation) {
      try {
         String source = newBean.getSourcePath();
         String taskId = "weblogic.deploy.configChangeTask." + getTaskId();
         DeploymentTaskRuntime task = createDeploymentTask(source, newBean, data, deploymentManager, taskId, deployTaskOp, proposedDomain, isAControlOperation);
         AuthenticatedSubject initiator = SecurityServiceManager.getCurrentSubject(kernelId);
         return deploymentManager.createDeployment(taskId, data, deployTaskOp, task, getDomainBean(proposedDomain, deploymentManager), true, initiator, true, isAControlOperation, requiresRestart);
      } catch (Throwable var11) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug(StackTraceUtils.throwable2StackTrace(var11));
         }

         return null;
      }
   }

   public static synchronized int getTaskId() {
      return curTaskId++;
   }

   private static DeploymentTaskRuntime createDeploymentTask(String source, BasicDeploymentMBean depMBean, DeploymentData data, DeploymentManager deploymentManager, String taskId, int operation, boolean proposedDomain, boolean isAControlOperation) throws ManagementException {
      DomainMBean domain = getDomainBean(proposedDomain, deploymentManager);
      DeploymentTaskRuntime dtr = new DeploymentTaskRuntime(source, depMBean, data, taskId, operation, domain, isAControlOperation, true);
      return dtr;
   }

   private static TargetMBean[] getTargets(WebLogicMBean bean, DeploymentManager deploymentManager) {
      if (bean instanceof TargetInfoMBean) {
         return (TargetMBean[])(bean instanceof DomainTargetedMBean ? getDomainBean(true, deploymentManager).getServers() : ApplicationUtils.getActualTargets((TargetInfoMBean)bean));
      } else if (bean instanceof DeploymentMBean && bean instanceof TargetMBean) {
         return ((DeploymentMBean)bean).getTargets();
      } else {
         return bean instanceof MigratableTargetMBean ? ((MigratableTargetMBean)bean).getAllCandidateServers() : null;
      }
   }

   private static TargetMBean[] getAllTargets(BasicDeploymentMBean bean) {
      TargetMBean[] initialTargets = bean.getTargets();
      SubDeploymentMBean[] subDeploymentMBeans = bean.getSubDeployments();
      if (subDeploymentMBeans != null && subDeploymentMBeans.length > 0) {
         List targets = new ArrayList();
         TargetMBean[] var4 = initialTargets;
         int var5 = initialTargets.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            TargetMBean target = var4[var6];
            targets.add(target);
         }

         SubDeploymentMBean[] var8 = subDeploymentMBeans;
         var5 = subDeploymentMBeans.length;

         for(var6 = 0; var6 < var5; ++var6) {
            SubDeploymentMBean subDeploymentMBean = var8[var6];
            collectTargets(subDeploymentMBean, targets);
         }

         return (TargetMBean[])targets.toArray(new TargetMBean[0]);
      } else {
         return initialTargets;
      }
   }

   private static void collectTargets(SubDeploymentMBean mbean, List targets) {
      TargetMBean[] var2 = mbean.getTargets();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         targets.add(target);
      }

      SubDeploymentMBean[] var6 = mbean.getSubDeployments();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         SubDeploymentMBean subDeploymentMBean = var6[var4];
         collectTargets(subDeploymentMBean, targets);
      }

   }

   private static DomainMBean getDomainBean(boolean useProposed, DeploymentManager deploymentManager) {
      if (useProposed) {
         String partitionName = deploymentManager != null ? deploymentManager.getPartitionName() : null;
         String editSessionName = deploymentManager != null ? deploymentManager.getEditSessionName() : null;

         try {
            if (isDomainDefaultEditSession(partitionName, editSessionName)) {
               return ManagementServiceRestricted.getEditAccess(kernelId).getDomainBean();
            }

            return ManagementServiceRestricted.getEditSession(partitionName, editSessionName).getDomainBean();
         } catch (Throwable var5) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Failed to get proposed DomainBean" + StackTraceUtils.throwable2StackTrace(var5));
            }
         }
      }

      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   private static boolean isDomainDefaultEditSession(String partitionName, String editSessionName) {
      return (partitionName == null || "DOMAIN".equals(partitionName)) && (editSessionName == null || "default".equals(editSessionName));
   }

   private static List getTargetsNotIn(TargetMBean[] sourceTargets, TargetMBean[] destTargets, DeploymentManager deploymentManager) {
      if (sourceTargets != null && sourceTargets.length != 0) {
         if (destTargets != null && destTargets.length != 0) {
            HashSet destTargetNameSet = new HashSet();
            HashSet destServerNameSet = new HashSet();

            for(int i = 0; i < destTargets.length; ++i) {
               destTargetNameSet.add(destTargets[i].getName());
               destServerNameSet.addAll(destTargets[i].getServerNames());
               TargetingUtils.addDynamicServerNames(destTargets[i], destServerNameSet);
            }

            List rtnTargets = new ArrayList();

            for(int i = 0; i < sourceTargets.length; ++i) {
               TargetMBean curTarget = sourceTargets[i];
               if (isPhysicalTarget(curTarget)) {
                  if (!destServerNameSet.contains(curTarget.getName())) {
                     rtnTargets.add(curTarget);
                  }
               } else {
                  Set curServerNameSet = curTarget.getServerNames();
                  TargetingUtils.addDynamicServerNames(curTarget, curServerNameSet);
                  Iterator iter = curServerNameSet.iterator();

                  while(iter.hasNext()) {
                     String curServerName = (String)iter.next();
                     if (!destServerNameSet.contains(curServerName)) {
                        TargetMBean curServer = getDomainBean(false, deploymentManager).lookupInAllTargets(curServerName);
                        if (curServer != null) {
                           rtnTargets.add(curServer);
                        }
                     }
                  }
               }
            }

            return rtnTargets;
         } else {
            return Arrays.asList(sourceTargets);
         }
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   private static boolean isPhysicalTarget(TargetMBean target) {
      return target instanceof ServerMBean;
   }

   private static boolean isEar(TargetInfoMBean bean) {
      if (!(bean instanceof AppDeploymentMBean)) {
         return false;
      } else if (ModuleType.EAR.toString().equals(bean.getModuleType())) {
         return true;
      } else {
         String path = ((AppDeploymentMBean)bean).getAbsoluteSourcePath();
         if (path != null) {
            if (path.endsWith(".ear")) {
               return true;
            }

            if ((new File(path, "META-INF/application.xml")).exists()) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isJMSModule(TargetInfoMBean bean) {
      if (bean instanceof JMSSystemResourceMBean) {
         return true;
      } else if (!(bean instanceof AppDeploymentMBean)) {
         if (bean instanceof SubDeploymentMBean) {
            TargetInfoMBean parent = (TargetInfoMBean)bean.getParent();
            if (parent instanceof JMSSystemResourceMBean) {
               return true;
            }

            if (parent instanceof AppDeploymentMBean) {
               if (isJMSModule(parent)) {
                  return true;
               }

               return isJMSModule(parent, bean);
            }

            if (parent instanceof SubDeploymentMBean) {
               TargetInfoMBean app = (TargetInfoMBean)parent.getParent();
               return isJMSModule(app, parent);
            }
         }

         return false;
      } else {
         String path = ((AppDeploymentMBean)bean).getAbsoluteSourcePath();
         return path != null && path.endsWith("-jms.xml");
      }
   }

   private static boolean isJMSModule(TargetInfoMBean app, TargetInfoMBean module) {
      String type = getAppRuntimeState().getModuleType(app.getName(), module.getName());
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Module type for " + module + ": " + type);
      }

      if (type.equals(WebLogicModuleType.MODULETYPE_UNKNOWN) && app instanceof AppDeploymentMBean) {
         VirtualJarFile vjf = null;

         try {
            ApplicationFileManager afm = ApplicationFileManager.newInstance(((AppDeploymentMBean)app).getAbsoluteSourcePath());
            vjf = afm.getVirtualJarFile();
            Iterator iter = vjf.entries();

            JarEntry entry;
            do {
               if (!iter.hasNext()) {
                  return false;
               }

               entry = (JarEntry)iter.next();
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Entry: " + entry.getName());
               }
            } while(!entry.getName().endsWith("-jms.xml"));

            boolean var7 = true;
            return var7;
         } catch (Throwable var18) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Failed to get module type", var18);
            }

            return false;
         } finally {
            if (vjf != null) {
               try {
                  vjf.close();
               } catch (Throwable var17) {
               }
            }

         }
      } else {
         return false;
      }
   }

   private static AppRuntimeStateRuntimeMBean getAppRuntimeState() {
      if (appRTMBean == null) {
         appRTMBean = ManagementService.getDomainAccess(kernelId).getAppRuntimeStateRuntime();
      }

      return appRTMBean;
   }

   private static JMSServerMBean getJMSServer(MigratableTargetMBean mTarget, DeploymentManager deploymentManager) {
      DomainMBean domain = getDomainBean(true, deploymentManager);
      JMSServerMBean[] servers = domain.getJMSServers();
      if (servers == null) {
         return null;
      } else {
         for(int i = 0; i < servers.length; ++i) {
            TargetMBean[] targets = servers[i].getTargets();
            if (targets.length == 1 && targets[0].equals(mTarget)) {
               return servers[i];
            }
         }

         return null;
      }
   }

   private static void createAndAddDeploymentInfos(OrderedDeployments deployments, BasicDeploymentMBean dep, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, int op) {
      DeployInfo deployInfo;
      if (undeployTargets.size() > 0) {
         deployInfo = createNewDeploymentInfo(dep, undeployData, op, false);
         deployInfo.requireRestart = false;
         deployments.addDeploymentInfo(deployInfo);
      }

      if (deployTargets.size() > 0) {
         deployInfo = createNewDeploymentInfo(dep, deployData, op, true);
         deployInfo.requireRestart = false;
         deployments.addDeploymentInfo(deployInfo);
      }

   }

   private static void addTargetsToData(String jmsServer, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, boolean isMigratableTarget) {
      if (isMigratableTarget) {
         Iterator iter = undeployTargets.iterator();

         TargetMBean curTarget;
         while(iter.hasNext()) {
            curTarget = (TargetMBean)iter.next();
            undeployData.addTarget(curTarget.getName(), (String[])null);
         }

         iter = deployTargets.iterator();

         while(iter.hasNext()) {
            curTarget = (TargetMBean)iter.next();
            deployData.addTarget(curTarget.getName(), (String[])null);
         }
      } else {
         if (undeployTargets.size() > 0) {
            undeployData.addTarget(jmsServer, (String[])null);
         }

         if (deployTargets.size() > 0) {
            deployData.addTarget(jmsServer, (String[])null);
         }
      }

   }

   private static void addModuleTargetsToData(String module, String jmsServer, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, boolean isMigratableTarget) {
      if (isMigratableTarget) {
         Iterator iter = undeployTargets.iterator();

         TargetMBean curTarget;
         while(iter.hasNext()) {
            curTarget = (TargetMBean)iter.next();
            undeployData.addModuleTarget(module, curTarget.getName());
         }

         iter = deployTargets.iterator();

         while(iter.hasNext()) {
            curTarget = (TargetMBean)iter.next();
            deployData.addModuleTarget(module, curTarget.getName());
         }
      } else {
         if (undeployTargets.size() > 0) {
            undeployData.addModuleTarget(module, jmsServer);
         }

         if (deployTargets.size() > 0) {
            deployData.addModuleTarget(module, jmsServer);
         }
      }

   }

   private static void addSubModuleTargetsToData(String module, String subModule, String jmsServerOrSafAgent, List undeployTargets, List deployTargets, DeploymentData undeployData, DeploymentData deployData, boolean isMigratableTarget) {
      String[] undeployTargetNames = isMigratableTarget ? getTargetNames(undeployTargets) : new String[]{jmsServerOrSafAgent};
      String[] deployTargetNames = isMigratableTarget ? getTargetNames(deployTargets) : new String[]{jmsServerOrSafAgent};
      if (undeployTargets.size() > 0) {
         undeployData.addSubModuleTarget(module, subModule, undeployTargetNames);
      }

      if (deployTargets.size() > 0) {
         deployData.addSubModuleTarget(module, subModule, deployTargetNames);
      }

   }

   private static boolean isTargetIn(String target, TargetMBean[] targets) {
      if (targets != null && targets.length != 0) {
         for(int i = 0; i < targets.length; ++i) {
            if (targets[i].getName().equals(target)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static String dumpDiff(ConfigurationContext configCtx) {
      Iterator iter = getDescriptorDiff(configCtx);
      StringBuffer sb = new StringBuffer("{");

      while(iter.hasNext()) {
         BeanUpdateEvent event = (BeanUpdateEvent)iter.next();
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
         DescriptorBean sourceBean = event.getSourceBean();
         sb.append("\n=> Event for ").append(sourceBean).append("[updateID=").append(event.getUpdateID()).append("]={");

         for(int i = 0; i < updates.length; ++i) {
            BeanUpdateEvent.PropertyUpdate curUpdate = updates[i];
            sb.append("\n[").append(curUpdate).append("]");
            if (i < updates.length - 1) {
               sb.append(",");
            }
         }

         sb.append("} ");
      }

      sb.append("}");
      return sb.toString();
   }

   private static String dumpTargets(DeploymentData data) {
      if (data == null) {
         return "{}";
      } else {
         StringBuffer sb = new StringBuffer("{");
         String[] targets = data.getGlobalTargets();
         if (targets != null && targets.length > 0) {
            sb.append("GlobalTargets=");

            for(int i = 0; i < targets.length; ++i) {
               sb.append(targets[i]);
               if (i < targets.length - 1) {
                  sb.append(",");
               }
            }
         }

         Map moduleTargets = data.getAllModuleTargets();
         if (moduleTargets != null && moduleTargets.size() > 0) {
            Iterator iter = moduleTargets.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               sb.append("\nModuleTargets(").append(entry.getKey()).append(")=");
               targets = (String[])((String[])entry.getValue());

               for(int i = 0; i < targets.length; ++i) {
                  sb.append(targets[i]);
                  if (i < targets.length - 1) {
                     sb.append(",");
                  }
               }
            }
         }

         Map allSubModuleTargets = data.getAllSubModuleTargets();
         if (allSubModuleTargets != null && allSubModuleTargets.size() > 0) {
            Iterator iter = allSubModuleTargets.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               String module = (String)entry.getKey();
               Map subModuleTargets = (Map)entry.getValue();
               Iterator iter2 = subModuleTargets.entrySet().iterator();

               while(iter2.hasNext()) {
                  Map.Entry entry2 = (Map.Entry)iter2.next();
                  sb.append("\nSubModuleTargets(").append(module).append(",").append(entry2.getKey()).append(")=");
                  targets = (String[])((String[])entry2.getValue());

                  for(int i = 0; i < targets.length; ++i) {
                     sb.append(targets[i]);
                     if (i < targets.length - 1) {
                        sb.append(",");
                     }
                  }
               }
            }
         }

         sb.append("}");
         return sb.toString();
      }
   }

   private static String getTargetsString(TargetMBean[] targets) {
      StringBuffer sb = new StringBuffer("(");
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            sb.append(targets[i].getName());
            if (i < targets.length - 1) {
               sb.append(",");
            }
         }
      }

      sb.append(")");
      return sb.toString();
   }

   private static String getTargetsString(List targets) {
      StringBuffer sb = new StringBuffer("(");
      if (targets != null && !targets.isEmpty()) {
         for(int i = 0; i < targets.size(); ++i) {
            TargetMBean targetMBean = (TargetMBean)targets.get(i);
            if (targetMBean != null) {
               sb.append(targetMBean.getName());
            }

            if (i < targets.size() - 1) {
               sb.append(",");
            }
         }
      }

      sb.append(")");
      return sb.toString();
   }

   private static String getTaskString(int op) {
      switch (op) {
         case 1:
            return "Activate";
         case 4:
            return "Remove";
         case 9:
            return "Redeploy";
         default:
            return "Unknown";
      }
   }

   private static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   private static void debugSay(String msg) {
      Debug.deploymentDebug(msg);
   }

   private static boolean areTargetsSame(DeploymentData curData, DeploymentData otherData) {
      List curGlobalTargets = Arrays.asList(curData.getGlobalTargets());
      List otherGlobalTargets = Arrays.asList(otherData.getGlobalTargets());
      boolean targetsAreSame = curGlobalTargets.equals(otherGlobalTargets);
      if (isDebugEnabled()) {
         debugSay(" Global Targets are same : " + targetsAreSame);
      }

      if (!targetsAreSame) {
         return false;
      } else {
         targetsAreSame = curData.getAllModuleTargets().equals(otherData.getAllModuleTargets());
         if (isDebugEnabled()) {
            debugSay(" Module Targets are same : " + targetsAreSame);
         }

         if (!targetsAreSame) {
            return false;
         } else {
            targetsAreSame = curData.getAllSubModuleTargets().equals(otherData.getAllSubModuleTargets());
            if (isDebugEnabled()) {
               debugSay(" Submodule Targets are same : " + targetsAreSame);
               debugSay(" Targets are same : " + targetsAreSame);
            }

            return targetsAreSame;
         }
      }
   }

   private static boolean haveCommonTargets(DeploymentData data1, DeploymentData data2) {
      if (haveCommonGlobalTargets(data1, data2)) {
         return true;
      } else if (haveCommonModuleTargets(data1, data2)) {
         return true;
      } else {
         return haveCommonSubModuleTargets(data1, data2);
      }
   }

   private static boolean haveCommonGlobalTargets(DeploymentData data1, DeploymentData data2) {
      String[] data1GTs = data1.getGlobalTargets();
      if (data1GTs != null && data1GTs.length != 0) {
         ExtendedArrayList data1List = new ExtendedArrayList(data1GTs);
         String[] data2GTs = data2.getGlobalTargets();
         if (data2GTs != null && data2GTs.length != 0) {
            if (data1List.containsOne(data2GTs)) {
               return true;
            } else {
               HashSet set1 = new HashSet();
               set1.addAll(Arrays.asList(data1GTs));
               HashSet set2 = new HashSet();
               set2.addAll(Arrays.asList(data2GTs));

               Set data1TSs;
               Set data2TSs;
               try {
                  data1TSs = data1.getAllTargetedServers(set1);
                  data2TSs = data2.getAllTargetedServers(set2);
               } catch (InvalidTargetException var11) {
                  return false;
               }

               if (!data1TSs.isEmpty() && !data2TSs.isEmpty()) {
                  Iterator it = data1TSs.iterator();

                  Object ts;
                  do {
                     if (!it.hasNext()) {
                        return false;
                     }

                     ts = it.next();
                  } while(!data2TSs.contains(ts));

                  data1.setGlobalTargets((String[])((String[])data1TSs.toArray(new String[0])));
                  data2.setGlobalTargets((String[])((String[])data2TSs.toArray(new String[0])));
                  return true;
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean haveCommonModuleTargets(DeploymentData data1, DeploymentData data2) {
      Map moduleTargets = data1.getAllModuleTargets();
      Map otherMTs = data2.getAllModuleTargets();
      if (!otherMTs.isEmpty() && !moduleTargets.isEmpty()) {
         Set otherModules = otherMTs.keySet();
         Iterator otherModulesIter = otherModules.iterator();

         while(otherModulesIter.hasNext()) {
            String otherModule = (String)otherModulesIter.next();
            if (moduleTargets.containsKey(otherModule)) {
               String[] otherTargets = (String[])((String[])otherMTs.get(otherModule));
               ExtendedArrayList currentTargets = new ExtendedArrayList((String[])((String[])moduleTargets.get(otherModule)));
               if (currentTargets.containsOne(otherTargets)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean haveCommonSubModuleTargets(DeploymentData data1, DeploymentData data2) {
      Map allSubModuleTargets = data1.getAllSubModuleTargets();
      Map otherSMTs = data2.getAllSubModuleTargets();
      if (!otherSMTs.isEmpty() && !allSubModuleTargets.isEmpty()) {
         Set otherModules = otherSMTs.keySet();
         Iterator otherModulesIter = otherModules.iterator();

         while(true) {
            Map otherSubs;
            Map currentSubs;
            do {
               do {
                  String otherModule;
                  do {
                     do {
                        do {
                           if (!otherModulesIter.hasNext()) {
                              return false;
                           }

                           otherModule = (String)otherModulesIter.next();
                        } while(!allSubModuleTargets.containsKey(otherModule));

                        otherSubs = (Map)otherSMTs.get(otherModule);
                     } while(otherSubs == null);
                  } while(otherSubs.isEmpty());

                  currentSubs = (Map)allSubModuleTargets.get(otherModule);
               } while(currentSubs == null);
            } while(currentSubs.isEmpty());

            Iterator iter = otherSubs.keySet().iterator();

            while(iter.hasNext()) {
               String otherSubModule = (String)iter.next();
               if (currentSubs.containsKey(otherSubModule)) {
                  String[] otherTargets = (String[])((String[])otherSubs.get(otherSubModule));
                  ExtendedArrayList currentTargets = new ExtendedArrayList((String[])((String[])currentSubs.get(otherSubModule)));
                  if (currentTargets.containsOne(otherTargets)) {
                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   private static boolean isTargetedToCluster(List targets, ClusterMBean cluster) {
      if (targets.isEmpty()) {
         return false;
      } else {
         Set targetNames = getTargetServerNames(targets);
         if (targetNames.contains(cluster.getName())) {
            return true;
         } else {
            ServerMBean[] servers = cluster.getServers();

            for(int i = 0; i < servers.length; ++i) {
               if (targetNames.contains(servers[i].getName())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private static Set getTargetServerNames(Collection targets) {
      Set serverNames = new HashSet();
      Iterator var2 = targets.iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         TargetMBean target = (TargetMBean)o;
         serverNames.add(target.getName());
         serverNames.addAll(target.getServerNames());
      }

      return serverNames;
   }

   public static BasicDeploymentMBean clone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, BasicDeploymentMBean bean) {
      try {
         BasicDeploymentMBean clone = (BasicDeploymentMBean)PartitionProcessor.findExistingClone(domain, partition, bean, bean.getClass());
         if (clone != null) {
            return clone;
         } else {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ConfigChangesHandler.clone: Cloning " + bean + " for partition " + partition);
            }

            clone = (BasicDeploymentMBean)((AbstractDescriptorBean)resourceGroup)._createChildBean(bean.getClass(), -1);
            ((DescriptorImpl)bean.getDescriptor()).resolveReferences();
            clone.unSet("Name");
            String name = PartitionProcessor.addSuffix(partition, bean.getName());
            clone.setName(name);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Updating clone name to " + name);
            }

            clone.setParent(resourceGroup);
            PartitionProcessor.cloneDelegate(bean, clone);
            ((AbstractDescriptorBean)clone)._setTransient(true);
            if (partition != null && partition.getParent() instanceof DomainMBean) {
               domain = (DomainMBean)partition.getParent();
            }

            return (BasicDeploymentMBean)PartitionProcessor.setEffectiveTargetsOnClonedBean(domain, partition, resourceGroup, bean, clone);
         }
      } catch (ManagementException | InvalidAttributeValueException var6) {
         throw new AssertionError(var6);
      }
   }

   public static SubDeploymentMBean clone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, BasicDeploymentMBean parentOfBeanToBeCloned, SubDeploymentMBean toBeCloned, BasicDeploymentMBean cloneTargetParent) {
      try {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.clone: Cloning " + toBeCloned + " for partition " + partition);
         }

         AbstractDescriptorBean _parent = (AbstractDescriptorBean)toBeCloned.getParentBean();
         SubDeploymentMBean clone = (SubDeploymentMBean)_parent._createChildBean(toBeCloned.getClass(), -1);
         ((DescriptorImpl)toBeCloned.getDescriptor()).resolveReferences();
         clone.setParent(cloneTargetParent);
         cloneTargetParent.addSubDeployment(clone);
         PartitionProcessor.cloneDelegate(toBeCloned, clone);
         ((AbstractDescriptorBean)clone)._setTransient(true);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Cloned " + toBeCloned.getName());
         }

         if (partition != null && partition.getParent() instanceof DomainMBean) {
            domain = (DomainMBean)partition.getParent();
         }

         return (SubDeploymentMBean)PartitionProcessor.setEffectiveTargetsOnClonedBean(domain, partition, resourceGroup, toBeCloned, clone);
      } catch (ManagementException | InvalidAttributeValueException var8) {
         throw new AssertionError(var8);
      }
   }

   private static void processNewPartition(PartitionMBean partition, OrderedDeployments deployments) {
   }

   private static void processNewResourceGroup(ResourceGroupMBean resourceGroup, OrderedDeployments deployments) {
      DomainMBean domain = getDomainBean(true, deployments.deploymentManager);
      String resourceGroupName = resourceGroup.getName();
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("processing new resource group:  " + resourceGroupName);
      }

      ResourceGroupTemplateMBean template = resourceGroup.getResourceGroupTemplate();
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("resource group template name: " + template);
      }

      PartitionMBean partition = null;
      if (resourceGroup.getParent() instanceof PartitionMBean) {
         partition = (PartitionMBean)resourceGroup.getParent();
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("resource group parent: " + partition);
      }

      if (template != null) {
         addDeployments(template, domain, partition, resourceGroup, deployments);
      }

   }

   private static void addDeployments(ResourceGroupTemplateMBean template, DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, OrderedDeployments deployments) {
      LibraryMBean[] libs = template.getLibraries();
      if (libs != null) {
         for(int n = 0; n < libs.length; ++n) {
            LibraryMBean newLibraryDeployment = (LibraryMBean)clone(domain, partition, resourceGroup, libs[n]);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("adding LbraryMBean:  " + newLibraryDeployment.getName());
            }

            deploymentAdded(deployments, newLibraryDeployment, getAllTargets(newLibraryDeployment));
         }
      }

      AppDeploymentMBean[] apps = template.getAppDeployments();
      if (apps != null) {
         for(int n = 0; n < apps.length; ++n) {
            AppDeploymentMBean newAppDeployment = (AppDeploymentMBean)clone(domain, partition, resourceGroup, apps[n]);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("adding AppDeploymentMBean:  " + newAppDeployment.getApplicationIdentifier());
            }

            deploymentAdded(deployments, newAppDeployment, getAllTargets(newAppDeployment));
            clonedMBeans.put(apps[n], newAppDeployment);
         }
      }

      JMSSystemResourceMBean[] JMSSystemResources = template.getJMSSystemResources();
      if (JMSSystemResources != null) {
         for(int n = 0; n < JMSSystemResources.length; ++n) {
            JMSSystemResourceMBean newJMSSystemResource = (JMSSystemResourceMBean)clone(domain, partition, resourceGroup, JMSSystemResources[n]);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("adding JMSSystemResource:  " + newJMSSystemResource);
            }

            deploymentAdded(deployments, newJMSSystemResource);
            clonedMBeans.put(JMSSystemResources[n], newJMSSystemResource);
         }
      }

      JDBCSystemResourceMBean[] JDBCSystemResources = template.getJDBCSystemResources();
      if (JDBCSystemResources != null) {
         for(int n = 0; n < JDBCSystemResources.length; ++n) {
            JDBCSystemResourceMBean newJDBCSystemResource = (JDBCSystemResourceMBean)clone(domain, partition, resourceGroup, JDBCSystemResources[n]);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("adding JDBCSystemResource:  " + newJDBCSystemResource);
            }

            deploymentAdded(deployments, newJDBCSystemResource);
            clonedMBeans.put(JDBCSystemResources[n], newJDBCSystemResource);
         }
      }

      CoherenceClusterSystemResourceMBean[] CoherenceClusterSystemResources = template.getCoherenceClusterSystemResources();
      if (CoherenceClusterSystemResources != null) {
         for(int n = 0; n < CoherenceClusterSystemResources.length; ++n) {
            CoherenceClusterSystemResourceMBean newCoherenceClusterSystemResource = (CoherenceClusterSystemResourceMBean)clone(domain, partition, resourceGroup, CoherenceClusterSystemResources[n]);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("adding CoherenceClusterSystemResource:  " + newCoherenceClusterSystemResource);
            }

            deploymentAdded(deployments, newCoherenceClusterSystemResource);
            clonedMBeans.put(CoherenceClusterSystemResources[n], newCoherenceClusterSystemResource);
         }
      }

      WLDFSystemResourceMBean[] WLDFSystemResources = template.getWLDFSystemResources();
      if (WLDFSystemResources != null) {
         for(int n = 0; n < WLDFSystemResources.length; ++n) {
            WLDFSystemResourceMBean newWLDFSystemResource = (WLDFSystemResourceMBean)clone(domain, partition, resourceGroup, WLDFSystemResources[n]);
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("adding WLDFSystemResource:  " + newWLDFSystemResource);
            }

            deploymentAdded(deployments, newWLDFSystemResource);
            clonedMBeans.put(WLDFSystemResources[n], newWLDFSystemResource);
         }
      }

   }

   private static void processAddedDeploymentForResourceGroup(BasicDeploymentMBean bdm, ResourceGroupMBean resourceGroup, OrderedDeployments deployments) {
      DomainMBean domain = getDomainBean(false, deployments.deploymentManager);
      String deploymentName = bdm.getName();
      PartitionMBean partition = null;
      Object parent = bdm.getParent().getParent();
      if (parent instanceof PartitionMBean) {
         partition = (PartitionMBean)parent;
         String partitionName = partition.getName();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("processing added deployment: " + deploymentName + " on partition: " + partitionName);
         }
      }

      if (bdm instanceof AppDeploymentMBean) {
         AppDeploymentMBean newAppDeployment = (AppDeploymentMBean)clone(domain, partition, resourceGroup, bdm);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("adding AppDeploymentMBean:  " + newAppDeployment.getApplicationIdentifier());
         }

         deploymentAdded(deployments, newAppDeployment, getAllTargets(newAppDeployment));
         clonedMBeans.put(bdm, newAppDeployment);
      } else if (bdm instanceof JMSSystemResourceMBean) {
         JMSSystemResourceMBean newJMSSystemResource = (JMSSystemResourceMBean)clone(domain, partition, resourceGroup, bdm);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("adding JMSSystemResource:  " + newJMSSystemResource);
         }

         deploymentAdded(deployments, newJMSSystemResource);
         clonedMBeans.put(bdm, newJMSSystemResource);
         if (bdm.getParent() instanceof ResourceGroupMBean) {
            ResourceGroupTemplateMBean rgtm = ((ResourceGroupMBean)bdm.getParent()).getResourceGroupTemplate();
            if (rgtm != null) {
               clonedMBeans.put(rgtm, newJMSSystemResource);
            }
         }
      } else if (bdm instanceof JDBCSystemResourceMBean) {
         JDBCSystemResourceMBean newJDBCSystemResource = (JDBCSystemResourceMBean)clone(domain, partition, resourceGroup, bdm);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("adding JDBCSystemResource:  " + newJDBCSystemResource);
         }

         deploymentAdded(deployments, newJDBCSystemResource);
         clonedMBeans.put(bdm, newJDBCSystemResource);
      } else if (bdm instanceof CoherenceClusterSystemResourceMBean) {
         CoherenceClusterSystemResourceMBean newCoherenceClusterSystemResource = (CoherenceClusterSystemResourceMBean)clone(domain, partition, resourceGroup, bdm);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("adding CoherenceClusterSystemResource:  " + newCoherenceClusterSystemResource);
         }

         deploymentAdded(deployments, newCoherenceClusterSystemResource);
         clonedMBeans.put(bdm, newCoherenceClusterSystemResource);
      } else if (bdm instanceof WLDFSystemResourceMBean) {
         WLDFSystemResourceMBean newWLDFSystemResource = (WLDFSystemResourceMBean)clone(domain, partition, resourceGroup, bdm);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("adding WLDFSystemResource:  " + newWLDFSystemResource);
         }

         deploymentAdded(deployments, newWLDFSystemResource);
         clonedMBeans.put(bdm, newWLDFSystemResource);
      }

   }

   private static void processRemovedDeploymentForResourceGroup(BasicDeploymentMBean bdm, OrderedDeployments deployments) {
      DomainMBean domain = getDomainBean(false, deployments.deploymentManager);
      String deploymentName = bdm.getName();
      PartitionMBean partition = null;
      Object parent = bdm.getParent().getParent();
      String partitionName = null;
      if (parent instanceof PartitionMBean) {
         partition = (PartitionMBean)parent;
         partitionName = partition.getName();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("processing removed deployment: " + deploymentName + " on partition: " + partitionName);
         }
      }

      String appId = ApplicationVersionUtils.getApplicationIdWithPartition(deploymentName, partitionName);
      BasicDeploymentMBean newBasicDeployment = AppDeploymentHelper.lookupBasicDeployment(appId, domain);
      if (newBasicDeployment != null) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("removing BasicDeploymentMBean:  " + newBasicDeployment.getName());
         }

         deploymentRemoved(deployments, newBasicDeployment);
         clonedMBeans.remove(bdm);
      }

   }

   private static BasicDeploymentMBean processAddedSubDeploymentToResourceGroup(DescriptorBean proposedBean, List addedBeans, List clonedBeans, DeploymentManager deploymentManager) {
      DomainMBean domain = getDomainBean(false, deploymentManager);
      if (!(proposedBean instanceof BasicDeploymentMBean)) {
         return null;
      } else {
         BasicDeploymentMBean bdm = (BasicDeploymentMBean)proposedBean;
         ResourceGroupMBean resourceGroup = (ResourceGroupMBean)bdm.getParentBean();
         PartitionMBean partition = null;
         Object parent = bdm.getParent().getParent();
         if (parent instanceof PartitionMBean) {
            partition = (PartitionMBean)parent;
         }

         BasicDeploymentMBean existingCloneParent = (BasicDeploymentMBean)PartitionProcessor.findExistingClone(domain, partition, bdm, BasicDeploymentMBean.class);
         if (existingCloneParent == null) {
            throw new AssertionError("Missing cloned deployment for modified deployment " + bdm.getName());
         } else {
            SubDeploymentMBean subDeploymentMBean;
            for(Iterator var10 = addedBeans.iterator(); var10.hasNext(); clonedBeans.add(clone(domain, partition, resourceGroup, bdm, subDeploymentMBean, existingCloneParent))) {
               subDeploymentMBean = (SubDeploymentMBean)var10.next();
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("processing added sub-deployment: " + subDeploymentMBean.getName() + " to deployment " + bdm.getName() + " on partition: " + partition.getName());
               }
            }

            return existingCloneParent;
         }
      }
   }

   private static void processSubDeploymentChangesInResourceGroup(OrderedDeployments deployments, DescriptorBean oldBean, DescriptorBean newBean) {
      if (oldBean instanceof SubDeploymentMBean && newBean instanceof SubDeploymentMBean) {
         SubDeploymentMBean originalBean = (SubDeploymentMBean)oldBean;
         SubDeploymentMBean proposedBean = (SubDeploymentMBean)newBean;
         boolean targetAdded = originalBean.isUntargeted() && !proposedBean.isUntargeted();
         boolean targetRemoved = !originalBean.isUntargeted() && proposedBean.isUntargeted();
         if (!targetAdded && !targetRemoved) {
            throw new AssertionError("Appears to be a false change event, property unchanged for " + originalBean.getName() + ": " + originalBean.isUntargeted());
         } else {
            DomainMBean domain = getDomainBean(false, deployments.deploymentManager);
            BasicDeploymentMBean bdm = (BasicDeploymentMBean)originalBean.getParentBean();
            ResourceGroupMBean rg = (ResourceGroupMBean)bdm.getParentBean();
            PartitionMBean partition = null;
            Object parent = bdm.getParent().getParent();
            if (parent instanceof PartitionMBean) {
               partition = (PartitionMBean)parent;
            }

            BasicDeploymentMBean bdmClone = (BasicDeploymentMBean)PartitionProcessor.findExistingClone(domain, partition, bdm, BasicDeploymentMBean.class);
            if (bdmClone == null) {
               throw new AssertionError("Missing cloned deployment for modified deployment " + bdm.getName());
            } else {
               SubDeploymentMBean sdmClone = bdmClone.lookupSubDeployment(originalBean.getName());
               if (sdmClone == null) {
                  throw new AssertionError("Missing cloned sub-deployment for modified deployment " + originalBean.getName());
               } else {
                  if (targetRemoved) {
                     TargetMBean[] oldTargets = sdmClone.getTargets();
                     sdmClone.setUntargeted(true);

                     try {
                        sdmClone.setTargets((TargetMBean[])null);
                     } catch (InvalidAttributeValueException var16) {
                        throw new AssertionError("Setting to no targets cannot go wrong", var16);
                     } catch (DistributedManagementException var17) {
                        throw new AssertionError("Setting to no targets cannot go wrong", var17);
                     }

                     targetsChanged(deployments, oldTargets, new TargetMBean[0], sdmClone);
                  } else if (targetAdded) {
                     sdmClone.setUntargeted(false);

                     try {
                        PartitionMBean runtimePartition = domain.lookupPartition(partition.getName());
                        ResourceGroupMBean runtimeRG = runtimePartition.lookupResourceGroup(rg.getName());
                        if (partition != null && partition.getParent() instanceof DomainMBean) {
                           domain = (DomainMBean)partition.getParent();
                        }

                        PartitionProcessor.setEffectiveTargetsOnClonedBean(domain, partition, runtimeRG, proposedBean, sdmClone);
                     } catch (InvalidAttributeValueException var18) {
                        throw new AssertionError("Setting to known partition targets cannot go wrong", var18);
                     } catch (ManagementException var19) {
                        throw new AssertionError("Setting to known partition targets cannot go wrong", var19);
                     }

                     targetsChanged(deployments, new TargetMBean[0], sdmClone.getTargets(), sdmClone);
                  }

               }
            }
         }
      } else {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Only sub-deployment target changes is supported for RG or RGTs, others are ignored: old, new: " + oldBean + ", " + newBean);
         }

      }
   }

   private static AppDeploymentMBean handleDeploymentForResourceGroup(AppDeploymentMBean appAddedOrRemoved, weblogic.deploy.internal.Deployment deployment, ResourceGroupMBean resourceGroup, DeploymentManager deploymentManager) {
      DomainMBean domain = getDomainBean(false, deploymentManager);
      PartitionMBean partition = null;
      Object parent = appAddedOrRemoved.getParent().getParent();
      String partitionName = null;
      if (parent instanceof PartitionMBean) {
         partition = (PartitionMBean)parent;
         partitionName = partition.getName();
      }

      String versionedName = appAddedOrRemoved.getName();
      int operation = deployment.getOperation();
      AppDeploymentMBean newAppDeployment;
      if (operation != 11 && operation != 1 && operation != 6) {
         String appId = ApplicationVersionUtils.getApplicationIdWithPartition(versionedName, partitionName);
         newAppDeployment = AppDeploymentHelper.lookupAppOrLib(appId, getDomainBean(false, deploymentManager));
      } else {
         newAppDeployment = (AppDeploymentMBean)clone(domain, partition, resourceGroup, appAddedOrRemoved);
      }

      DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
      taskRuntime.initMBeans(newAppDeployment);
      taskRuntime.setApplicationId(newAppDeployment.getName());
      deployment.setIdentity(newAppDeployment.getName());
      InternalDeploymentData internalData = deployment.getInternalDeploymentData();
      internalData.setDeploymentName(taskRuntime.getApplicationId());
      return newAppDeployment;
   }

   private static boolean isSkipDeployment(weblogic.deploy.internal.Deployment deployment, DeploymentManager deploymentManager) {
      DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
      BasicDeploymentMBean bdm = taskRuntime.getDeploymentMBean();
      if (ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(taskRuntime.getDeploymentData()) && bdm != null && !((AbstractDescriptorBean)bdm)._isTransient() && ApplicationUtils.isFromResourceGroup(bdm)) {
         String taskId = taskRuntime.getId();
         DeployerRuntimeImpl deployerRuntime = deploymentManager.getDeployerRuntime();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("removing original task if skipping its deployment:  " + taskId);
         }

         deployerRuntime.removeTask(taskId);
         taskRuntime.setState(2);
         return true;
      } else {
         return false;
      }
   }

   private static void handleRedeployToResourceGroup(DeploymentManager deploymentManager, AppDeploymentMBean sourceAppDeploymentMBean) {
      DeployerRuntimeImpl deployerRuntime = deploymentManager.getDeployerRuntime();
      DeploymentTaskRuntimeMBean[] tasks = deployerRuntime.getDeploymentTaskRuntimes();
      if (tasks != null) {
         for(int n = 0; n < tasks.length; ++n) {
            int taskState = tasks[n].getState();
            String taskAppId = tasks[n].getApplicationId();
            String taskId = tasks[n].getId();
            if (taskAppId.equals(sourceAppDeploymentMBean.getApplicationIdentifier()) && taskState == 0) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("removing original task:  " + taskId);
               }

               deployerRuntime.removeTask(taskId);
            }
         }
      }

   }

   private static void handleMTAppRedeployWithSamePath(DeploymentManager deploymentManager, OrderedDeployments deployments) {
      List pendingDeploymentsForLockAcquirer = deploymentManager.getPendingDeploymentsForLockAcquirer();
      if (pendingDeploymentsForLockAcquirer.isEmpty()) {
         Collection pendingDeploymentsForLockOwner = deploymentManager.getPendingDeploymentsForEditLockOwner();
         if (pendingDeploymentsForLockOwner != null && pendingDeploymentsForLockOwner.size() > 0) {
            Iterator iterator = pendingDeploymentsForLockOwner.iterator();

            while(iterator.hasNext()) {
               weblogic.deploy.internal.Deployment deployment = (weblogic.deploy.internal.Deployment)iterator.next();
               handleMTAppRedeployWithSamePath(deploymentManager, deployment, deployments);
            }
         }
      } else {
         weblogic.deploy.internal.Deployment deployment = (weblogic.deploy.internal.Deployment)pendingDeploymentsForLockAcquirer.get(0);
         deploymentManager.clearPendingDeploymentsForLockAcquirer();
         handleMTAppRedeployWithSamePath(deploymentManager, deployment, deployments);
      }

   }

   private static void handleMTAppRedeployWithSamePath(DeploymentManager manager, weblogic.deploy.internal.Deployment deployment, OrderedDeployments deployments) {
      InternalDeploymentData internalDeploymentData = deployment.getInternalDeploymentData();
      if (internalDeploymentData != null) {
         DeploymentData data = internalDeploymentData.getExternalDeploymentData();
         if (ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(data)) {
            DeploymentTaskRuntime taskRuntime = deployment.getDeploymentTaskRuntime();
            BasicDeploymentMBean bdm = taskRuntime.getDeploymentMBean();
            if (bdm instanceof AppDeploymentMBean) {
               AppDeploymentMBean adm = (AppDeploymentMBean)bdm;
               if (isAppRedeployWithSamePath(deployment, data, adm)) {
                  ResourceGroupMBean[] var8 = getResourceGroupsForOperation(data);
                  int var9 = var8.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     ResourceGroupMBean rg = var8[var10];
                     AppDeploymentMBean admRG = rg.lookupAppDeployment(adm.getApplicationName());
                     if (admRG != null && !isSkipPartitionOrResourceGroup(admRG, manager)) {
                        handleRedeployToResourceGroup(manager, admRG);
                        processAddedDeploymentForResourceGroup(admRG, rg, deployments);
                     }
                  }
               }

            }
         }
      }
   }

   private static boolean isAppRedeployWithSamePath(weblogic.deploy.internal.Deployment deployment, DeploymentData data, AppDeploymentMBean adm) {
      String appId = deployment.getIdentity();
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      AppDeploymentMBean existingAppDeployment = ApplicationUtils.lookupAppDeploymentInGivenScope(appName, data, domain);
      int operation = deployment.getOperation();
      if ((operation == 11 || operation == 9) && existingAppDeployment != null) {
         String sourcePath = adm.getSourcePath();
         String planPath = adm.getPlanPath();
         if (sourcePath != null && sourcePath.equals(existingAppDeployment.getSourcePath()) || planPath != null && planPath.equals(existingAppDeployment.getPlanPath())) {
            return true;
         }
      }

      return false;
   }

   private static ResourceGroupMBean[] getResourceGroupsForOperation(DeploymentData data) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (data == null) {
         return new ResourceGroupMBean[0];
      } else if (data.getResourceGroup() != null) {
         ResourceGroupMBean[] resourceGroups = new ResourceGroupMBean[1];
         if (data.getPartition() != null) {
            PartitionMBean pm = domain.lookupPartition(data.getPartition());
            ResourceGroupMBean rgm = pm.lookupResourceGroup(data.getResourceGroup());
            resourceGroups[0] = rgm;
         } else {
            ResourceGroupMBean rgm = domain.lookupResourceGroup(data.getResourceGroup());
            resourceGroups[0] = rgm;
         }

         return resourceGroups;
      } else {
         return new ResourceGroupMBean[0];
      }
   }

   private static class ExtendedArrayList extends ArrayList {
      ExtendedArrayList(Collection c) {
         super(c);
      }

      ExtendedArrayList(String[] given) {
         this((Collection)(given != null ? Arrays.asList(given) : Collections.EMPTY_LIST));
      }

      boolean containsOne(String[] objs) {
         if (this.isEmpty()) {
            return false;
         } else if (objs != null && objs.length != 0) {
            for(int i = 0; i < objs.length; ++i) {
               if (this.contains(objs[i])) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private static final class OrderedDeployments {
      private static final Comparator LOCAL_COMPARATOR = new Comparator() {
         public int compare(Object o1, Object o2) {
            if (o1 == o2) {
               return 0;
            } else {
               int order = ConfigChangesHandler.OrderedDeployments.compareDeployment((DeployInfo)o1, (DeployInfo)o2);
               return order != 0 ? order : -1;
            }
         }
      };
      private final TreeSet preDeploymentHandlerDeployments;
      private final TreeSet postDeploymentHandlerDeployments;
      private final DeploymentManager deploymentManager;
      private final List tempInfoList;

      public OrderedDeployments(DeploymentManager deploymentManager) {
         this.preDeploymentHandlerDeployments = new TreeSet(LOCAL_COMPARATOR);
         this.postDeploymentHandlerDeployments = new TreeSet(LOCAL_COMPARATOR);
         this.tempInfoList = new ArrayList() {
            public boolean add(Object obj) {
               if (this.contains(obj)) {
                  return false;
               } else {
                  DeployInfo info = (DeployInfo)obj;
                  List sameInfos = this.getDeployInfosOnSameApp(info);
                  if (ConfigChangesHandler.isDebugEnabled()) {
                     ConfigChangesHandler.debugSay(" MergedList.addDeployInfo() : sameInfos of kind '" + info + "' =========== " + sameInfos);
                  }

                  if (sameInfos.size() < 1) {
                     return super.add(info);
                  } else {
                     Iterator sameInfoIter = sameInfos.iterator();

                     boolean merged;
                     do {
                        if (!sameInfoIter.hasNext()) {
                           return super.add(info);
                        }

                        DeployInfo otherInfo = (DeployInfo)sameInfoIter.next();
                        merged = info.mergeWithOtherOperation(otherInfo, this);
                     } while(!merged);

                     return true;
                  }
               }
            }

            private List getDeployInfosOnSameApp(DeployInfo theInfo) {
               List result = new ArrayList();
               BasicDeploymentMBean app = theInfo.getTopLevelDepBean();
               if (app == null) {
                  return result;
               } else if (this.isEmpty()) {
                  return result;
               } else {
                  Iterator iter = this.iterator();

                  while(iter.hasNext()) {
                     DeployInfo eachInfo = (DeployInfo)iter.next();
                     BasicDeploymentMBean eachApp = eachInfo.getTopLevelDepBean();
                     if (eachApp != null && eachApp == app && theInfo != eachInfo) {
                        result.add(eachInfo);
                     }
                  }

                  return result;
               }
            }
         };
         this.deploymentManager = deploymentManager;
      }

      private boolean addDeploymentInfo(DeployInfo info) {
         info.setDeploymentManager(this.deploymentManager);
         return this.tempInfoList.add(info);
      }

      private void add(DeployInfo info) {
         boolean isBeforeDeploymentHandler = DeploymentOrder.isBeforeDeploymentHandler(info.getTopLevelDepBean());
         boolean isDeploy = info.isDeploy();
         if (isBeforeDeploymentHandler && isDeploy || !isBeforeDeploymentHandler && !isDeploy) {
            this.preDeploymentHandlerDeployments.add(info);
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" ++ preDeploymentHandlerDeployments after add : " + this.preDeploymentHandlerDeployments);
            }

            weblogic.deploy.internal.Deployment thisDeployment = (weblogic.deploy.internal.Deployment)info.getDeployment();
            thisDeployment.setBeforeDeploymentHandler();
            if (isDeploy) {
               thisDeployment.setIsDeploy();
            }
         } else {
            this.postDeploymentHandlerDeployments.add(info);
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" ++ postDeploymentHandlerDeployments after add : " + this.postDeploymentHandlerDeployments);
            }
         }

      }

      private static int compareDeployment(DeployInfo info1, DeployInfo info2) {
         boolean isDeploy1 = info1.isDeploy();
         boolean isDeploy2 = info2.isDeploy();
         if (isDeploy1 && isDeploy2) {
            return compare(info1, info2);
         } else if (!isDeploy1 && !isDeploy2) {
            return compare(info1, info2) * -1;
         } else {
            return !isDeploy1 && isDeploy2 ? -1 : 1;
         }
      }

      private static int compare(DeployInfo info1, DeployInfo info2) {
         return DeploymentOrder.COMPARATOR.compare(info1.getTopLevelDepBean(), info2.getTopLevelDepBean());
      }

      private void processDeploymentInfos() {
         try {
            Iterator iter = this.tempInfoList.iterator();

            while(iter.hasNext()) {
               DeployInfo info = (DeployInfo)iter.next();
               if (info.deploymentManager == null) {
                  info.setDeploymentManager(this.deploymentManager);
               }

               Deployment dep = info.createDeployment();
               if (dep != null) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("ConfigChangesHandler.addDeployment for " + info.getTopLevelDepBean().getName() + ", deployOp=" + ConfigChangesHandler.getTaskString(info.getOp()) + ", isDeploy=" + info.isDeploy() + ", deployData=" + info.getDeployData() + ", targets=" + ConfigChangesHandler.dumpTargets(info.getDeployData()) + ", deployment=" + info.getDeployment());
                  }

                  this.add(info);
               }
            }
         } finally {
            this.tempInfoList.clear();
         }

      }

      private void addDeployments(Collection deployments, List depList) {
         if (deployments != null && deployments.size() != 0) {
            Iterator iter = deployments.iterator();

            while(iter.hasNext()) {
               DeployInfo info = (DeployInfo)iter.next();
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("  MBean=" + info.getTopLevelDepBean() + ", op=" + ConfigChangesHandler.getTaskString(info.getOp()));
               }

               if (info.getDeployment() != null) {
                  depList.add(info.getDeployment());
               }
            }

         }
      }

      private List[] getAllDeployments(DeploymentRequest request, boolean okToSwitch, List appsAdded, List appsRemoved) {
         boolean debug = Debug.isDeploymentDebugEnabled();
         this.processDeploymentInfos();
         List preList = new ArrayList();
         List postList = new ArrayList();
         if (debug) {
            Debug.deploymentDebug("ConfigChangesHandler PreDepHandlerDeployments:" + this.preDeploymentHandlerDeployments);
         }

         this.addDeployments(this.preDeploymentHandlerDeployments, preList);
         if (okToSwitch && preList.size() > 0) {
            request.setCallConfigurationProviderLast();
         }

         if (debug) {
            Debug.deploymentDebug("ConfigChangesHandler PostDepHandlerDeployments:" + this.postDeploymentHandlerDeployments);
         }

         this.addDeployments(this.postDeploymentHandlerDeployments, postList);
         Collection deploymentsWithoutConfigChanges = this.deploymentManager.getPendingDeploymentsForEditLockOwner();
         if (deploymentsWithoutConfigChanges != null && deploymentsWithoutConfigChanges.size() > 0) {
            if (debug) {
               Debug.deploymentDebug("ConfigChangesHandler PostDepHandlerDeployments with no configuration side-effects:");
            }

            Iterator iterator = deploymentsWithoutConfigChanges.iterator();

            label76:
            while(iterator.hasNext()) {
               weblogic.deploy.internal.Deployment deployment = (weblogic.deploy.internal.Deployment)iterator.next();
               if (debug) {
                  Debug.deploymentDebug(deployment.getDeploymentTaskRuntimeId());
               }

               Iterator appsAddedOrRemovedIt = appsAdded.iterator();
               if (appsAdded.isEmpty()) {
                  appsAddedOrRemovedIt = appsRemoved.iterator();
               }

               while(true) {
                  AppDeploymentMBean app;
                  do {
                     if (!appsAddedOrRemovedIt.hasNext()) {
                        if (!ConfigChangesHandler.isSkipDeployment(deployment, this.deploymentManager)) {
                           postList.add(deployment);
                        }
                        continue label76;
                     }

                     app = (AppDeploymentMBean)appsAddedOrRemovedIt.next();
                  } while(!app.getName().equals(deployment.getIdentity()));

                  if (ApplicationUtils.isFromResourceGroup(app)) {
                     app = ConfigChangesHandler.handleDeploymentForResourceGroup(app, deployment, (ResourceGroupMBean)app.getParent(), this.deploymentManager);
                  }

                  TargetMBean[] appTargets = app.getTargets();
                  List newTargets = new ArrayList();

                  for(int n = 0; n < appTargets.length; ++n) {
                     Set newTargetServerNames = appTargets[n].getServerNames();
                     TargetingUtils.addDynamicServerNames(appTargets[n], newTargetServerNames);
                     Iterator newTargetServerNamesIt = newTargetServerNames.iterator();

                     while(newTargetServerNamesIt.hasNext()) {
                        String newTgt = (String)newTargetServerNamesIt.next();
                        if (!newTargets.contains(newTgt)) {
                           newTargets.add(newTgt);
                        }
                     }
                  }

                  deployment.setTargets(newTargets);
               }
            }
         }

         return new List[]{preList, postList};
      }
   }

   private abstract static class DeployInfo {
      protected BasicDeploymentMBean topLevelDepBean;
      protected final DeploymentData deployData;
      protected int op;
      protected boolean isDeploy;
      private Deployment deployment;
      protected boolean requireRestart;
      protected DeploymentManager deploymentManager;

      private DeployInfo() {
         this.isDeploy = true;
         this.topLevelDepBean = null;
         this.deployData = null;
         this.deployment = null;
      }

      private DeployInfo(BasicDeploymentMBean deployBean, DeploymentData deployData, int operation, boolean isDeploy) {
         this.isDeploy = true;
         this.topLevelDepBean = deployBean;
         this.deployData = deployData != null ? deployData : new DeploymentData();
         this.op = operation;
         this.isDeploy = isDeploy;
      }

      public void setDeploymentManager(DeploymentManager deploymentManager) {
         this.deploymentManager = deploymentManager;
      }

      public boolean isDeploy() {
         return this.isDeploy;
      }

      public DeploymentData getDeployData() {
         return this.deployData;
      }

      public void setRequireRestart(boolean flag) {
         this.requireRestart = flag;
      }

      public int getOp() {
         return this.op;
      }

      public void setOp(int operation) {
         this.op = operation;
      }

      public Deployment getDeployment() {
         return this.deployment;
      }

      public BasicDeploymentMBean getTopLevelDepBean() {
         return this.topLevelDepBean;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString()).append("[");
         sb.append("topLevelBean=").append(this.topLevelDepBean).append(",deployData=").append(this.deployData).append(",op=").append(this.op).append(",isDeploy=").append(this.isDeploy).append(",hasTargetedServers=").append(this.hasTargetedServers(ConfigChangesHandler.getDomainBean(true, this.deploymentManager))).append("]");
         return sb.toString();
      }

      private boolean hasModuleTargets() {
         return this.deployData != null && this.deployData.hasModuleTargets();
      }

      private boolean hasSubModuleTargets() {
         return this.deployData != null && this.deployData.hasSubModuleTargets();
      }

      private boolean hasTargetedServers(DomainMBean domain) {
         if (this.deployData == null) {
            return false;
         } else {
            boolean hasTargets = false;
            Iterator var3 = this.deployData.getAllLogicalTargets().iterator();

            while(var3.hasNext()) {
               Object target = var3.next();
               Set targets = new HashSet();
               targets.add(target);

               try {
                  Set servers = this.deployData.getAllTargetedServers(targets, domain);
                  hasTargets = servers != null && servers.size() > 0;
               } catch (Throwable var7) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("getAllTargetedServers failed", var7);
                  }
               }

               if (hasTargets) {
                  break;
               }
            }

            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ConfigChangesHandler.hasTargets=" + hasTargets);
            }

            return hasTargets;
         }
      }

      protected boolean mergeWithRedeploy(DeployInfo otherInfo, Collection existingInfos) {
         if (!(otherInfo instanceof RedeployDeployInfo)) {
            throw new AssertionError("Other DeployInfo is not Redeploy info");
         } else {
            return false;
         }
      }

      protected boolean mergeWithUndeploy(DeployInfo otherInfo, Collection existingInfos) {
         if (!(otherInfo instanceof UndeployDeployInfo)) {
            throw new AssertionError("Other DeployInfo is not Undeploy info");
         } else {
            return false;
         }
      }

      protected boolean mergeWithDeploy(DeployInfo otherInfo, Collection existingInfos) {
         if (!(otherInfo instanceof DeployDeployInfo)) {
            throw new AssertionError("Other DeployInfo is not Deploy info");
         } else {
            return false;
         }
      }

      protected boolean mergeWithSameInfo(DeployInfo otherInfo, Collection existingInfos) {
         if (!this.isSameInfoType(otherInfo)) {
            throw new AssertionError(" DeployInfo instances are different types");
         } else {
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ DeployInfos are of same type. Merging their data and making one info...");
            }

            DeploymentData curData = this.deployData;
            DeploymentData otherData = otherInfo.deployData;
            DeploymentData mergedData = curData.copy();
            mergedData.setDeploymentPlan(otherData.getDeploymentPlan());
            mergedData.addGlobalTargets(otherData.getGlobalTargets());
            if (otherData.hasModuleTargets()) {
               mergedData.addOrUpdateModuleTargets(otherData.getAllModuleTargets());
            }

            if (otherData.hasSubModuleTargets()) {
               mergedData.addOrUpdateSubModuleTargets(otherData.getAllSubModuleTargets());
            }

            existingInfos.remove(otherInfo);
            existingInfos.remove(this);
            DeployInfo newInfo = ConfigChangesHandler.createNewDeploymentInfo(this.topLevelDepBean, mergedData, this.op, this.isDeploy);
            newInfo.requireRestart = this.requireRestart;
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Merged into Info : " + newInfo);
            }

            existingInfos.add(newInfo);
            return true;
         }
      }

      protected boolean mergeWithOtherOperation(DeployInfo otherInfo, Collection existingOrderedInfos) {
         if (ConfigChangesHandler.isDebugEnabled()) {
            ConfigChangesHandler.debugSay("DeployInfo.mergeWithOtherOperation() invoked on '" + this + "' ------ with parameter '" + otherInfo);
         }

         if (otherInfo instanceof ClusterDeployInfo) {
            return false;
         } else if (otherInfo instanceof DeployDeployInfo) {
            return this.mergeWithDeploy(otherInfo, existingOrderedInfos);
         } else {
            return otherInfo instanceof RedeployDeployInfo ? this.mergeWithRedeploy(otherInfo, existingOrderedInfos) : this.mergeWithUndeploy(otherInfo, existingOrderedInfos);
         }
      }

      protected Deployment createDeployment() {
         if (this.deployData != null && this.topLevelDepBean != null && this.hasTargetedServers(ConfigChangesHandler.getDomainBean(this.isDeploy, this.deploymentManager))) {
            this.deployment = ConfigChangesHandler.findOrCreateDeployment(this.op, this.topLevelDepBean, this.deployData, this.deploymentManager, this.isDeploy, this.requireRestart, false);
         } else {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("ConfigChangesHandler.addDeployment skipped, deployInfo=" + this);
            }

            this.deployment = null;
         }

         return this.deployment;
      }

      private boolean isSameInfoType(DeployInfo otherInfo) {
         return this.getClass().equals(otherInfo.getClass());
      }

      // $FF: synthetic method
      DeployInfo(Object x0) {
         this();
      }

      // $FF: synthetic method
      DeployInfo(BasicDeploymentMBean x0, DeploymentData x1, int x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static final class UndeployDeployInfo extends DeployInfo {
      private UndeployDeployInfo(BasicDeploymentMBean deployBean, DeploymentData deployData, boolean isDeploy) {
         super(deployBean, deployData, 4, isDeploy, null);
      }

      protected boolean mergeWithRedeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithRedeploy(otherInfo, existingInfos);
         return otherInfo.mergeWithUndeploy(this, existingInfos);
      }

      protected boolean mergeWithUndeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithUndeploy(otherInfo, existingInfos);
         return this.mergeWithSameInfo(otherInfo, existingInfos);
      }

      protected boolean mergeWithDeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithDeploy(otherInfo, existingInfos);
         return otherInfo.mergeWithUndeploy(this, existingInfos);
      }

      // $FF: synthetic method
      UndeployDeployInfo(BasicDeploymentMBean x0, DeploymentData x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class RedeployDeployInfo extends DeployInfo {
      private RedeployDeployInfo(BasicDeploymentMBean deployBean, DeploymentData deployData, boolean isDeploy) {
         super(deployBean, deployData, 9, isDeploy, null);
      }

      protected boolean mergeWithRedeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithRedeploy(otherInfo, existingInfos);
         return this.mergeWithSameInfo(otherInfo, existingInfos);
      }

      protected boolean mergeWithUndeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithUndeploy(otherInfo, existingInfos);
         if (ConfigChangesHandler.areTargetsSame(this.deployData, otherInfo.deployData)) {
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Targets are same...");
               ConfigChangesHandler.debugSay(" +++ Removing from the list : " + otherInfo);
            }

            existingInfos.remove(otherInfo);
            existingInfos.remove(this);
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Adding to the list : " + this);
            }

            existingInfos.add(this);
            return true;
         } else {
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Targets are *NOT* same...");
            }

            if (ConfigChangesHandler.haveCommonTargets(this.deployData, otherInfo.deployData)) {
               DeploymentData curData = this.deployData;
               DeploymentData otherData = otherInfo.deployData;
               curData.removeCommonTargets(otherData, false);
               if (ConfigChangesHandler.isDebugEnabled()) {
                  ConfigChangesHandler.debugSay(" +++ Have common targets...");
                  ConfigChangesHandler.debugSay(" +++ Removing info : " + otherInfo);
                  ConfigChangesHandler.debugSay(" +++ Removing info : " + this);
               }

               existingInfos.remove(otherInfo);
               existingInfos.remove(this);
               DeployInfo undeployInfo;
               if (!otherData.hasNoTargets()) {
                  if (ConfigChangesHandler.isDebugEnabled()) {
                     ConfigChangesHandler.debugSay(" +++ Other data HAS targets after removing common targets...");
                  }

                  undeployInfo = ConfigChangesHandler.createNewDeploymentInfo(otherInfo.topLevelDepBean, otherData, 4, otherInfo.isDeploy);
                  undeployInfo.requireRestart = otherInfo.requireRestart;
                  if (ConfigChangesHandler.isDebugEnabled()) {
                     ConfigChangesHandler.debugSay(" +++ Adding new UndeployInfo : " + undeployInfo);
                  }

                  existingInfos.add(undeployInfo);
               }

               undeployInfo = ConfigChangesHandler.createNewDeploymentInfo(this.topLevelDepBean, curData, 9, this.isDeploy);
               undeployInfo.requireRestart = this.requireRestart;
               if (ConfigChangesHandler.isDebugEnabled()) {
                  ConfigChangesHandler.debugSay(" +++ Adding new RedeployInfo : " + undeployInfo);
               }

               existingInfos.add(undeployInfo);
               return true;
            } else {
               if (ConfigChangesHandler.isDebugEnabled()) {
                  ConfigChangesHandler.debugSay(" +++ Have *NO* common targets... So returning false");
               }

               return false;
            }
         }
      }

      protected boolean mergeWithDeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithDeploy(otherInfo, existingInfos);
         if (ConfigChangesHandler.areTargetsSame(this.deployData, otherInfo.deployData)) {
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Targets are same... Need not have to add this");
            }

            return true;
         } else {
            DeploymentData curData = this.deployData;
            DeploymentData otherData = otherInfo.deployData;
            curData.setDeploymentPlan(otherData.getDeploymentPlan());
            curData.addGlobalTargets(otherData.getGlobalTargets());
            if (otherData.hasModuleTargets()) {
               curData.addOrUpdateModuleTargets(otherData.getAllModuleTargets());
            }

            if (otherData.hasSubModuleTargets()) {
               curData.addOrUpdateSubModuleTargets(otherData.getAllSubModuleTargets());
            }

            existingInfos.remove(otherInfo);
            existingInfos.remove(this);
            DeployInfo redeployInfo = ConfigChangesHandler.createNewDeploymentInfo(this.topLevelDepBean, curData, this.op, this.isDeploy);
            redeployInfo.requireRestart = this.requireRestart;
            existingInfos.add(redeployInfo);
            return true;
         }
      }

      // $FF: synthetic method
      RedeployDeployInfo(BasicDeploymentMBean x0, DeploymentData x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class DeployDeployInfo extends DeployInfo {
      private DeployDeployInfo(BasicDeploymentMBean deployBean, DeploymentData deployData, boolean isDeploy) {
         super(deployBean, deployData, 1, isDeploy, null);
      }

      protected boolean mergeWithRedeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithRedeploy(otherInfo, existingInfos);
         return otherInfo.mergeWithDeploy(this, existingInfos);
      }

      protected boolean mergeWithUndeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithUndeploy(otherInfo, existingInfos);
         if (ConfigChangesHandler.areTargetsSame(this.deployData, otherInfo.deployData)) {
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Targets are same...");
               ConfigChangesHandler.debugSay(" +++ Removing from the list : " + otherInfo);
            }

            existingInfos.remove(otherInfo);
            existingInfos.remove(this);
            DeployInfo redeployInfo = ConfigChangesHandler.createNewDeploymentInfo(this.topLevelDepBean, this.deployData, 9, this.isDeploy);
            redeployInfo.requireRestart = this.requireRestart;
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Adding to the list : " + redeployInfo);
            }

            existingInfos.add(redeployInfo);
            return true;
         } else {
            if (ConfigChangesHandler.isDebugEnabled()) {
               ConfigChangesHandler.debugSay(" +++ Targets are *NOT* same...");
            }

            if (ConfigChangesHandler.haveCommonTargets(this.deployData, otherInfo.deployData)) {
               DeploymentData curData = this.deployData;
               DeploymentData otherData = otherInfo.deployData;
               curData.removeCommonTargets(otherData, false);
               if (ConfigChangesHandler.isDebugEnabled()) {
                  ConfigChangesHandler.debugSay(" +++ Have common targets...");
                  ConfigChangesHandler.debugSay(" +++ Removing info : " + otherInfo);
                  ConfigChangesHandler.debugSay(" +++ Removing info : " + this);
               }

               existingInfos.remove(otherInfo);
               existingInfos.remove(this);
               DeployInfo undeployInfo;
               if (!otherData.hasNoTargets()) {
                  if (ConfigChangesHandler.isDebugEnabled()) {
                     ConfigChangesHandler.debugSay(" +++ Other data HAS targets after removing common targets...");
                  }

                  undeployInfo = ConfigChangesHandler.createNewDeploymentInfo(otherInfo.topLevelDepBean, otherData, 4, otherInfo.isDeploy);
                  undeployInfo.requireRestart = otherInfo.requireRestart;
                  if (ConfigChangesHandler.isDebugEnabled()) {
                     ConfigChangesHandler.debugSay(" +++ Adding new UndeployInfo : " + undeployInfo);
                  }

                  existingInfos.add(undeployInfo);
               }

               undeployInfo = ConfigChangesHandler.createNewDeploymentInfo(this.topLevelDepBean, curData, 9, this.isDeploy);
               undeployInfo.requireRestart = this.requireRestart;
               if (ConfigChangesHandler.isDebugEnabled()) {
                  ConfigChangesHandler.debugSay(" +++ Adding new RedeployInfo : " + undeployInfo);
               }

               existingInfos.add(undeployInfo);
               return true;
            } else {
               if (ConfigChangesHandler.isDebugEnabled()) {
                  ConfigChangesHandler.debugSay(" +++ Have *NO* common targets... So returning false");
               }

               return false;
            }
         }
      }

      protected boolean mergeWithDeploy(DeployInfo otherInfo, Collection existingInfos) {
         super.mergeWithDeploy(otherInfo, existingInfos);
         return this.mergeWithSameInfo(otherInfo, existingInfos);
      }

      // $FF: synthetic method
      DeployDeployInfo(BasicDeploymentMBean x0, DeploymentData x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class ClusterDeployInfo extends DeployInfo {
      private DeployInfo delegate;
      String associatedClusterName;

      private ClusterDeployInfo(BasicDeploymentMBean deployBean, DeploymentData deployData, int op, boolean isDeploy, String clusterName) {
         super(null);
         if (op == 1) {
            this.delegate = new DeployDeployInfo(deployBean, deployData, isDeploy);
         } else if (op == 9) {
            this.delegate = new RedeployDeployInfo(deployBean, deployData, isDeploy);
         } else {
            this.delegate = new UndeployDeployInfo(deployBean, deployData, isDeploy);
         }

         this.associatedClusterName = clusterName;
      }

      public void setDeploymentManager(DeploymentManager deploymentManager) {
         super.setDeploymentManager(deploymentManager);
         this.delegate.setDeploymentManager(deploymentManager);
      }

      public boolean isDeploy() {
         return this.delegate.isDeploy();
      }

      public DeploymentData getDeployData() {
         return this.delegate.getDeployData();
      }

      public int getOp() {
         return this.delegate.getOp();
      }

      public void setRequireRestart(boolean flag) {
         this.delegate.setRequireRestart(flag);
      }

      public void setOp(int operation) {
         this.delegate.setOp(operation);
      }

      public final String toString() {
         return "ClusterDeplyInfo for cluster [" + this.associatedClusterName + "] with delegate " + this.delegate;
      }

      protected final boolean mergeWithOtherOperation(DeployInfo otherInfo, Collection existingOrderedInfos) {
         if (ConfigChangesHandler.isDebugEnabled()) {
            ConfigChangesHandler.debugSay("ClusterDeployInfo.mergeWithOtherOperation() invoked on '" + this + "' ------ with otherInfo '" + otherInfo);
         }

         if (!(otherInfo instanceof ClusterDeployInfo)) {
            return false;
         } else {
            ClusterDeployInfo otherClusterInfo = (ClusterDeployInfo)otherInfo;
            if (!this.associatedClusterName.equals(otherClusterInfo.associatedClusterName)) {
               return false;
            } else {
               DeployInfo otherDelegate = otherClusterInfo.delegate;
               if (this.delegate instanceof DeployDeployInfo) {
                  if (otherDelegate instanceof DeployDeployInfo) {
                     otherClusterInfo.mergeTargets(this.delegate);
                     existingOrderedInfos.remove(this);
                     return true;
                  } else if (otherDelegate instanceof UndeployDeployInfo) {
                     existingOrderedInfos.remove(otherClusterInfo);
                     return false;
                  } else {
                     otherClusterInfo.mergeTargets(this.delegate);
                     existingOrderedInfos.remove(this);
                     return true;
                  }
               } else if (this.delegate instanceof RedeployDeployInfo) {
                  if (otherDelegate instanceof DeployDeployInfo) {
                     this.mergeTargets(otherDelegate);
                     existingOrderedInfos.remove(otherClusterInfo);
                     return false;
                  } else if (otherDelegate instanceof UndeployDeployInfo) {
                     existingOrderedInfos.remove(otherClusterInfo);
                     return false;
                  } else {
                     otherClusterInfo.mergeTargets(this.delegate);
                     existingOrderedInfos.remove(this);
                     return true;
                  }
               } else if (otherDelegate instanceof DeployDeployInfo) {
                  existingOrderedInfos.remove(this);
                  return true;
               } else if (otherDelegate instanceof UndeployDeployInfo) {
                  otherClusterInfo.mergeTargets(this.delegate);
                  existingOrderedInfos.remove(this);
                  return true;
               } else {
                  existingOrderedInfos.remove(this);
                  return true;
               }
            }
         }
      }

      void mergeTargets(DeployInfo otherDelegate) {
         DeploymentData curData = this.delegate.deployData;
         DeploymentData otherData = otherDelegate.deployData;
         curData.setDeploymentPlan(otherData.getDeploymentPlan());
         curData.addGlobalTargets(otherData.getGlobalTargets());
         if (otherData.hasModuleTargets()) {
            curData.addOrUpdateModuleTargets(otherData.getAllModuleTargets());
         }

         if (otherData.hasSubModuleTargets()) {
            curData.addOrUpdateSubModuleTargets(otherData.getAllSubModuleTargets());
         }

         this.delegate.requireRestart = this.delegate.requireRestart || otherDelegate.requireRestart;
         if (ConfigChangesHandler.isDebugEnabled()) {
            ConfigChangesHandler.debugSay(" +++ Merged into Info : " + this);
         }

      }

      protected Deployment createDeployment() {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("ConfigChangesHandler.ClusterDeployInfo.createDeployment --> " + this);
         }

         return this.delegate.createDeployment();
      }

      public Deployment getDeployment() {
         return this.delegate.getDeployment();
      }

      public BasicDeploymentMBean getTopLevelDepBean() {
         return this.delegate.getTopLevelDepBean();
      }

      // $FF: synthetic method
      ClusterDeployInfo(BasicDeploymentMBean x0, DeploymentData x1, int x2, boolean x3, String x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
