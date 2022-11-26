package com.oracle.weblogic.lifecycle.core;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.Kernel;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.partition.admin.PartitionManagerService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.PartitionLCMHelper;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
@Rank(Integer.MAX_VALUE)
public final class PartitionLCMService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   @Named("PartitionRuntimeBuilderService")
   private ServerService dependencyOnPartitionRuntimeBuilderService;
   @Inject
   @Named("DeploymentServerService")
   private ServerService dependsOnDeploymentService;
   @Inject
   private PartitionRuntimeStateManager stateManager;
   @Inject
   private PartitionManagerService manager;
   private static Logger logger = Logger.getLogger("LifeCycle");
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugLifecycleManager");
   private static ComponentInvocationContext DOMAIN_CIC = null;

   public synchronized void start() throws ServiceFailureException {
      RuntimeAccess config = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domainBean = config.getDomain();
      domainBean.addBeanUpdateListener(new DomainBeanUpdateListener(config.getDomain()));
   }

   public synchronized void halt() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
   }

   private static void addClusterListener(ClusterMBean target) {
      target.addBeanUpdateListener(new ClusterBeanUpdateListener());
   }

   private static void addTargetBeanUpdateListener(TargetMBean targetMBean) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Adding target Listener for : " + targetMBean.getName());
      }

      targetMBean.addBeanUpdateListener(new TargetBeanUpdateListener());
      if (targetMBean instanceof VirtualTargetMBean) {
         TargetMBean[] targets = ((VirtualTargetMBean)targetMBean).getTargets();
         TargetMBean[] var2 = targets;
         int var3 = targets.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            target.addBeanUpdateListener(new TargetBeanUpdateListener());
            if (target instanceof ClusterMBean) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Adding Cluster Listener for : " + target.getName());
               }

               addClusterListener((ClusterMBean)target);
            }
         }
      }

   }

   private static TargetMBean[] collectTargets(PartitionMBean partition) {
      return partition.findEffectiveTargets();
   }

   @Service
   @Interceptor
   @ContractsProvided({PartitionLCMInterceptor.class, MethodInterceptor.class})
   @ServerServiceInterceptor(PartitionLCMService.class)
   public static class PartitionLCMInterceptor extends PartitionManagerInterceptorAdapter {
      public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         methodInvocation.proceed();
         this.informLCMPartitionStarted(partitionName);
      }

      public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
         methodInvocation.proceed();
         this.informLCMPartitionStarted(partitionName);
      }

      private void informLCMPartitionStarted(final String partitionName) {
         ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
         boolean isServer = Kernel.isServer();
         if (isServer) {
            try {
               runAs(new Callable() {
                  public Void call() {
                     try {
                        HashMap propMap = null;
                        if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                           PartitionLCMService.debugLogger.debug("Partition " + partitionName + " started. Set hasNonDynamicProperties to true");
                        }

                        propMap = new HashMap();
                        propMap.put("hasNonDynamicProperties", "true");
                        PartitionLCMHelper.startPartition(partitionName, propMap);
                     } catch (Exception var2) {
                        if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                           PartitionLCMService.debugLogger.debug("partition update notification failed for start partition", var2);
                        }
                     }

                     return null;
                  }
               });
            } catch (Exception var4) {
               if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                  PartitionLCMService.debugLogger.debug("Error informing start partition", var4);
               }
            }
         }

      }

      private static synchronized ComponentInvocationContext getDomainCIC() {
         if (PartitionLCMService.DOMAIN_CIC == null) {
            PartitionLCMService.DOMAIN_CIC = ComponentInvocationContextManager.getInstance().createComponentInvocationContext("DOMAIN");
         }

         return PartitionLCMService.DOMAIN_CIC;
      }

      private static Object runAs(Callable action) throws ExecutionException {
         ComponentInvocationContextManager cicM = ComponentInvocationContextManager.getInstance(PartitionLCMService.kernelId);
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("Switching to global context");
         }

         return ComponentInvocationContextManager.runAs(PartitionLCMService.kernelId, getDomainCIC(), action);
      }
   }

   private static class ServerBeanUpdateListener implements BeanUpdateListener {
      private String oldCluster = null;
      private String newCluster = null;
      private final ServerMBean server;

      public void cleanup() {
         this.server.removeBeanUpdateListener(this);
      }

      public ServerBeanUpdateListener(ServerMBean _server) {
         this.server = _server;
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         ServerMBean currentServerMBean = (ServerMBean)event.getSourceBean();
         if (currentServerMBean != null) {
            ClusterMBean clusterMBean = currentServerMBean.getCluster();
            if (clusterMBean != null) {
               String clusterName = clusterMBean.getName();
               this.oldCluster = clusterName;
            } else {
               this.oldCluster = null;
            }
         }

         ServerMBean updatedServerMBean = (ServerMBean)event.getProposedBean();
         if (updatedServerMBean != null) {
            ClusterMBean clusterMBean = updatedServerMBean.getCluster();
            if (clusterMBean != null) {
               String clusterName = clusterMBean.getName();
               this.newCluster = clusterName;
            } else {
               this.newCluster = null;
            }
         }

      }

      public void activateUpdate(BeanUpdateEvent event) {
         RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
         DomainMBean domainBean = config.getDomain();
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("ServerBean activateUpdate : " + domainBean.getName());
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   private static class ClusterBeanUpdateListener implements BeanUpdateListener {
      private ClusterBeanUpdateListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         PartitionLCMService.logger.warning("ClusterBeanUpdateListener.prepareUpdate");
      }

      public void activateUpdate(BeanUpdateEvent event) {
         ClusterMBean updatedClusterMbean = (ClusterMBean)event.getProposedBean();
         String updatedClusterMbeanNameName = updatedClusterMbean.getName();
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("ClusterBean activateUpdate : " + updatedClusterMbeanNameName);
         }

         RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
         DomainMBean domainBean = config.getDomain();
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      // $FF: synthetic method
      ClusterBeanUpdateListener(Object x0) {
         this();
      }
   }

   private static class TargetBeanUpdateListener implements BeanUpdateListener {
      private TargetBeanUpdateListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) {
         TargetMBean updatedTargetMbean = (TargetMBean)event.getProposedBean();
         String targetName = updatedTargetMbean.getName();
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("TargetBean activateUpdate : " + targetName);
         }

         RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
         DomainMBean domainBean = config.getDomain();
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      // $FF: synthetic method
      TargetBeanUpdateListener(Object x0) {
         this();
      }
   }

   private static class ResourceGroupBeanUpdateListener implements BeanUpdateListener {
      ResourceGroupMBean resourceGroup;

      public ResourceGroupBeanUpdateListener(ResourceGroupMBean _resourceGroup) {
         this.resourceGroup = _resourceGroup;
      }

      private void cleanup() {
         this.resourceGroup.removeBeanUpdateListener(this);
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) {
         ResourceGroupMBean updatedRGMbean = (ResourceGroupMBean)event.getProposedBean();
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("ResourceGroup activateUpdate : " + updatedRGMbean.getName());
         }

         if (updatedRGMbean != null) {
            Object updatedPartitionParentMbeanObj = updatedRGMbean.getParent();
            RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
            if (updatedPartitionParentMbeanObj instanceof PartitionMBean) {
               PartitionMBean updatedPartitionMbean = (PartitionMBean)updatedPartitionParentMbeanObj;
               if (config.isAdminServer()) {
                  HashMap propMap = null;

                  try {
                     BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
                     BeanUpdateEvent.PropertyUpdate[] var8 = updated;
                     int var9 = updated.length;
                     int var10 = 0;

                     while(var10 < var9) {
                        BeanUpdateEvent.PropertyUpdate propertyUpdate = var8[var10];
                        switch (propertyUpdate.getUpdateType()) {
                           case 2:
                              if ("Targets".equals(propertyUpdate.getPropertyName())) {
                                 if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                                    PartitionLCMService.debugLogger.debug("ResourceGroup retargeted: Set hasNonDynamicProperties to true");
                                 }

                                 propMap = new HashMap();
                                 propMap.put("hasNonDynamicProperties", "true");
                              }
                           default:
                              ++var10;
                        }
                     }

                     PartitionLCMHelper.updatePartition(updatedPartitionMbean.getName(), (String)null, propMap);
                  } catch (Exception var13) {
                     PartitionLCMService.logger.log(Level.INFO, "activateUpdate failed", var13);
                  }
               }
            } else {
               try {
                  if (config.isAdminServer()) {
                     HashMap propMap = new HashMap();
                     if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                        PartitionLCMService.debugLogger.debug("Resource group " + updatedRGMbean.getName() + " updated. Set hasNonDynamicProperties to true");
                     }

                     propMap.put("hasNonDynamicProperties", "true");
                     PartitionLCMHelper.updatePartition("DOMAIN");
                  }
               } catch (Exception var12) {
               }
            }
         }

         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var15 = updated;
         int var17 = updated.length;
         int var18 = 0;

         while(var18 < var17) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var15[var18];
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  Object obj = propertyUpdate.getAddedObject();
                  if (obj != null && obj instanceof TargetMBean) {
                     TargetMBean targetMBean = (TargetMBean)propertyUpdate.getAddedObject();
                     if (targetMBean != null) {
                        PartitionLCMService.addTargetBeanUpdateListener(targetMBean);
                     }
                  }
               default:
                  ++var18;
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   private static class PartitionBeanUpdateListener implements BeanUpdateListener {
      PartitionMBean partition;
      private final Map resourceGroupListeners = new HashMap();

      public PartitionBeanUpdateListener(PartitionMBean _partition) {
         this.partition = _partition;
         TargetMBean[] targets = PartitionLCMService.collectTargets(this.partition);
         TargetMBean[] var3 = targets;
         int var4 = targets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean target = var3[var5];
            PartitionLCMService.addTargetBeanUpdateListener(target);
         }

      }

      public void cleanup() {
         this.partition.removeBeanUpdateListener(this);
         Iterator var1 = this.resourceGroupListeners.values().iterator();

         while(var1.hasNext()) {
            ResourceGroupBeanUpdateListener rgl = (ResourceGroupBeanUpdateListener)var1.next();
            rgl.cleanup();
         }

      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) {
         PartitionMBean updatedPartitionMbean = (PartitionMBean)event.getProposedBean();
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("activateUpdate : " + updatedPartitionMbean.getName());
         }

         RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
         if (config.isAdminServer()) {
            try {
               HashMap propMap = new HashMap();
               if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                  PartitionLCMService.debugLogger.debug("Partition " + updatedPartitionMbean.getName() + " updated. Set hasNonDynamicProperties to true");
               }

               propMap.put("hasNonDynamicProperties", "true");
               PartitionLCMHelper.updatePartition(updatedPartitionMbean.getName(), (String)null, propMap);
            } catch (Exception var16) {
               PartitionLCMService.logger.log(Level.INFO, "activateUpdate failed", var16);
            }
         }

         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var5 = updated;
         int var6 = updated.length;

         label62:
         for(int var7 = 0; var7 < var6; ++var7) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var5[var7];
            ResourceGroupBeanUpdateListener listener;
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  Object obj = propertyUpdate.getAddedObject();
                  if (obj != null && obj instanceof TargetMBean) {
                     TargetMBean targetMBean = (TargetMBean)propertyUpdate.getAddedObject();
                     if (targetMBean != null) {
                        PartitionLCMService.addTargetBeanUpdateListener(targetMBean);
                     }
                     break;
                  } else {
                     if (!"ResourceGroups".equalsIgnoreCase(propertyUpdate.getPropertyName())) {
                        break;
                     }

                     ResourceGroupMBean rgMB = (ResourceGroupMBean)propertyUpdate.getAddedObject();
                     if (rgMB != null) {
                        listener = new ResourceGroupBeanUpdateListener(rgMB);
                        this.resourceGroupListeners.put(rgMB.getName(), listener);
                        rgMB.addBeanUpdateListener(listener);
                     }

                     TargetMBean[] targets = PartitionLCMService.collectTargets(this.partition);
                     TargetMBean[] var12 = targets;
                     int var13 = targets.length;
                     int var14 = 0;

                     while(true) {
                        if (var14 >= var13) {
                           continue label62;
                        }

                        TargetMBean target = var12[var14];
                        PartitionLCMService.addTargetBeanUpdateListener(target);
                        ++var14;
                     }
                  }
               case 3:
                  if ("ResourceGroups".equalsIgnoreCase(propertyUpdate.getPropertyName())) {
                     ResourceGroupMBean rgMB = (ResourceGroupMBean)propertyUpdate.getRemovedObject();
                     String rgName = rgMB.getName();
                     listener = (ResourceGroupBeanUpdateListener)this.resourceGroupListeners.get(rgName);
                     if (listener != null) {
                        listener.cleanup();
                        this.resourceGroupListeners.remove(rgName);
                     }
                  }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   private static class DomainBeanUpdateListener implements BeanUpdateListener {
      private final Map partitionListeners = new HashMap();
      private final Map domainResourceGroupListeners = new HashMap();
      private final Map serverListeners = new HashMap();
      private final DomainMBean domain;

      public DomainBeanUpdateListener(DomainMBean _domain) {
         this.domain = _domain;
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("LCM out of band : DomainBeanUpdateListener : " + _domain.getName());
         }

      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) {
         RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionLCMService.kernelId);
         if (PartitionLCMService.debugLogger.isDebugEnabled()) {
            PartitionLCMService.debugLogger.debug("LCM activateUpdate");
         }

         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var4 = updated;
         int var5 = updated.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var4[var6];
            String name;
            ServerMBean oldServer;
            ServerBeanUpdateListener serverListener;
            PartitionMBean oldPartition;
            PartitionBeanUpdateListener partitionListener;
            ResourceGroupMBean oldResourceGroup;
            ResourceGroupBeanUpdateListener resourceGroupListener;
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  name = propertyUpdate.getPropertyName();
                  if (null != name) {
                     switch (name) {
                        case "Servers":
                           oldServer = (ServerMBean)propertyUpdate.getAddedObject();
                           if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                              PartitionLCMService.debugLogger.debug("activateUpdate newServer : " + oldServer.getName());
                           }

                           serverListener = new ServerBeanUpdateListener(oldServer);
                           oldServer.addBeanUpdateListener(serverListener);
                           this.serverListeners.put(oldServer.getName(), serverListener);

                           try {
                              this.serverCreatedOrDeleted(config, oldServer);
                           } catch (Exception var18) {
                              PartitionLCMService.logger.log(Level.INFO, "activateUpdate failed", var18);
                           }
                           break;
                        case "Partitions":
                           oldPartition = (PartitionMBean)propertyUpdate.getAddedObject();
                           partitionListener = new PartitionBeanUpdateListener(oldPartition);
                           oldPartition.addBeanUpdateListener(partitionListener);
                           if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                              PartitionLCMService.debugLogger.debug("activateUpdate newPartition : " + oldPartition.getName());
                           }

                           this.partitionListeners.put(oldPartition.getName(), partitionListener);
                           if (config.isAdminServer()) {
                              PartitionLCMHelper.registerPartition(oldPartition.getName(), oldPartition.getPartitionID());
                           }
                           break;
                        case "ResourceGroups":
                           oldResourceGroup = (ResourceGroupMBean)propertyUpdate.getAddedObject();
                           resourceGroupListener = new ResourceGroupBeanUpdateListener(oldResourceGroup);
                           oldResourceGroup.addBeanUpdateListener(resourceGroupListener);
                           if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                              PartitionLCMService.debugLogger.debug("activateUpdate newPartition : " + oldResourceGroup.getName());
                           }

                           this.domainResourceGroupListeners.put(oldResourceGroup.getName(), resourceGroupListener);
                     }
                  }
                  break;
               case 3:
                  name = propertyUpdate.getPropertyName();
                  if (null != name) {
                     switch (name) {
                        case "Servers":
                           oldServer = (ServerMBean)propertyUpdate.getRemovedObject();
                           if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                              PartitionLCMService.debugLogger.debug("Remove server : " + oldServer.getName());
                           }

                           serverListener = (ServerBeanUpdateListener)this.serverListeners.get(oldServer.getName());
                           if (serverListener != null) {
                              serverListener.cleanup();
                              this.serverListeners.remove(oldServer.getName());
                           }

                           try {
                              this.serverCreatedOrDeleted(config, oldServer);
                           } catch (Exception var17) {
                              PartitionLCMService.logger.log(Level.INFO, "activateUpdate failed", var17);
                           }
                           break;
                        case "Partitions":
                           oldPartition = (PartitionMBean)propertyUpdate.getRemovedObject();
                           partitionListener = (PartitionBeanUpdateListener)this.partitionListeners.get(oldPartition.getName());
                           if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                              PartitionLCMService.debugLogger.debug("Remove partition : " + oldPartition.getName());
                           }

                           if (partitionListener != null) {
                              partitionListener.cleanup();
                              this.partitionListeners.remove(oldPartition.getName());
                           }

                           if (config.isAdminServer()) {
                              PartitionLCMHelper.deletePartition(oldPartition.getName());
                           }
                           break;
                        case "ResourceGroups":
                           oldResourceGroup = (ResourceGroupMBean)propertyUpdate.getRemovedObject();
                           resourceGroupListener = (ResourceGroupBeanUpdateListener)this.domainResourceGroupListeners.get(oldResourceGroup.getName());
                           if (PartitionLCMService.debugLogger.isDebugEnabled()) {
                              PartitionLCMService.debugLogger.debug("Remove RG : " + oldResourceGroup.getName());
                           }

                           if (resourceGroupListener != null) {
                              resourceGroupListener.cleanup();
                              this.domainResourceGroupListeners.remove(oldResourceGroup.getName());
                           }
                     }
                  }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      private void serverCreatedOrDeleted(RuntimeAccess config, ServerMBean server) throws Exception {
      }
   }
}
