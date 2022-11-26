package weblogic.server;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.ManagementException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.internal.PartitionFileSystemHelper;
import weblogic.management.partition.admin.PartitionVirtualTargetBeanUpdateListener;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.t3.srvr.PartitionLifecycleLogger;

@Service
@Named
@RunLevel(15)
@Rank(100)
public final class PartitionService extends AbstractServerService {
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
   @Optional
   @Named("JpsPostStartService")
   private ServerService dependsOnJpsPostStart;
   private static final Map partitionListeners = new HashMap();
   private static final Map partitionFSListeners = new HashMap();
   static final boolean isDeploymentStartingAll = false;

   public synchronized void start() throws ServiceFailureException {
      RuntimeAccess config = ManagementService.getRuntimeAccess(kernelId);
      if (!config.isAdminServer()) {
         this.syncPartitionStatesWithAdminServer(config);
      }

      DomainMBean domainBean = config.getDomain();
      domainBean.getDescriptor().addUpdateListener(new PartitionVirtualTargetBeanUpdateListener());
      domainBean.addBeanUpdateListener(new DomainBeanUpdateListener(config.getDomain(), this.stateManager));
      domainBean.addBeanUpdateListener(new PartitionInterceptorBeanUpdateListener());
      domainBean.addBeanUpdateListener(new ResourceGroupTargetingChangeBeanUpdateListener());
      ResourceGroupTemplateMBean[] var3 = domainBean.getResourceGroupTemplates();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceGroupTemplateMBean rgt = var3[var5];
         rgt.addBeanUpdateListener(new ImmutableRGTBeanUpdateListener());
      }

   }

   static boolean createBeanIfTargeted(PartitionMBean partition, String serverName) throws ManagementException {
      Collection targets = collectTargets(partition);
      Iterator var3 = targets.iterator();

      Set serverNames;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         TargetMBean target = (TargetMBean)var3.next();
         serverNames = target.getServerNames();
      } while(!serverNames.contains(serverName));

      return true;
   }

   private static Set collectTargets(PartitionMBean partition) {
      Set result = new HashSet();
      return result;
   }

   public synchronized void halt() throws ServiceFailureException {
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();

      try {
         PartitionRuntimeMBean[] var2 = serverRuntime.getPartitionRuntimes();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PartitionRuntimeMBean partR = var2[var4];
            if (State.isAdmin(partR.getInternalState())) {
               try {
                  partR.halt();
               } catch (Exception var7) {
                  PartitionLifecycleLogger.logPartitionShutdownFailedDuringServerShutdown(partR.getName(), serverRuntime.getName(), var7);
               }
            }
         }

      } catch (Exception var8) {
         throw new ServiceFailureException(var8);
      }
   }

   public void stop() throws ServiceFailureException {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      ServerRuntimeMBean server = ra.getServerRuntime();

      try {
         PartitionRuntimeMBean[] var3 = server.getPartitionRuntimes();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PartitionRuntimeMBean partR = var3[var5];
            if (State.isAdmin(partR.getInternalState())) {
               try {
                  partR.shutdown(60, false, false);
                  partR.halt();
               } catch (Exception var8) {
                  PartitionLifecycleLogger.logPartitionShutdownFailedDuringServerShutdown(partR.getName(), server.getName(), var8);
               }
            }
         }

      } catch (Exception var9) {
         throw new ServiceFailureException(var9);
      }
   }

   private void syncPartitionStatesWithAdminServer(RuntimeAccess runtimeAccess) {
      PartitionRuntimeStateManager _stateManager = (PartitionRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManager.class, new Annotation[0]);
      RemoteLifeCycleOperations ops = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(runtimeAccess.getAdminServerName());

      try {
         Map states = ops.getRuntimeStates();
         _stateManager.load(states);
      } catch (Exception var5) {
      }

   }

   private static class PartitionBeanUpdateListener implements BeanUpdateListener {
      PartitionMBean partition;

      public PartitionBeanUpdateListener(PartitionMBean _partition) {
         this.partition = _partition;
      }

      public void cleanup() {
         this.partition.removeBeanUpdateListener(this);
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var3 = updated;
         int var4 = updated.length;
         int var5 = 0;

         while(var5 < var4) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var3[var5];
            switch (propertyUpdate.getUpdateType()) {
               case 1:
                  String name = propertyUpdate.getPropertyName();
                  if ("Realm".equals(name)) {
                     PartitionMBean updatePartition = (PartitionMBean)event.getProposedBean();
                     ServerRuntimeMBean serverRuntimeBean = ManagementService.getRuntimeAccess(PartitionService.kernelId).getServerRuntime();
                     PartitionRuntimeMBean partitionRuntime = serverRuntimeBean.lookupPartitionRuntime(updatePartition.getName());
                     if (partitionRuntime != null && !State.isShutdown(partitionRuntime.getInternalState()) && partitionRuntime.getInternalState() != State.SHUTTING_DOWN) {
                        throw new BeanUpdateRejectedException("Partition realm can not be set when the partition is not shut down");
                     }
                  }
               default:
                  ++var5;
            }
         }

      }

      public void activateUpdate(BeanUpdateEvent event) {
         ServerRuntimeMBean serverRuntimeBean = ManagementService.getRuntimeAccess(PartitionService.kernelId).getServerRuntime();
         String thisServerName = serverRuntimeBean.getName();
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var5 = updated;
         int var6 = updated.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var5[var7];
            String name;
            PartitionMBean updatedPartition;
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  name = propertyUpdate.getPropertyName();
                  if (!"ResourceGroups".equals(name) && !"AvailableTargets".equals(name) && !"DefaultTargets".equals(name)) {
                     break;
                  }

                  updatedPartition = (PartitionMBean)event.getProposedBean();

                  try {
                     PartitionService.createBeanIfTargeted(updatedPartition, thisServerName);
                     break;
                  } catch (ManagementException var16) {
                     throw new Error("Unexpected exception creating partition runtime mbean", var16);
                  }
               case 3:
                  name = propertyUpdate.getPropertyName();
                  if ("ResourceGroups".equals(name) || "AvailableTargets".equals(name) || "DefaultTargets".equals(name)) {
                     updatedPartition = (PartitionMBean)event.getProposedBean();

                     try {
                        boolean partitionStillTargeted = false;
                        Collection targets = PartitionService.collectTargets(updatedPartition);
                        Iterator var13 = targets.iterator();

                        while(var13.hasNext()) {
                           TargetMBean target = (TargetMBean)var13.next();
                           Set serverNames = target.getServerNames();
                           if (serverNames.contains(thisServerName)) {
                              partitionStillTargeted = true;
                           }
                        }

                        if (!partitionStillTargeted) {
                           PartitionRuntimeMBean partitionRuntimeBean = serverRuntimeBean.lookupPartitionRuntime(updatedPartition.getName());
                           if (partitionRuntimeBean != null) {
                           }
                        }
                     } catch (Exception var17) {
                        throw new Error("Unexpected exception removing partition runtime mbean", var17);
                     }
                  }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   private static class DomainBeanUpdateListener implements BeanUpdateListener {
      public DomainBeanUpdateListener(DomainMBean domain, PartitionRuntimeStateManager stateManager) throws ServiceFailureException {
      }

      private void addPartitionFileSystemBeanUpdateListener(PartitionMBean partition) {
         PartitionFileSystemBeanUpdateListener partitionFileSystemBeanUpdateListener = new PartitionFileSystemBeanUpdateListener(partition);
         PartitionFileSystemMBean partitionFileSystemMBean = partition.getSystemFileSystem();
         partitionFileSystemMBean.addBeanUpdateListener(partitionFileSystemBeanUpdateListener);
         PartitionService.partitionFSListeners.put(partition.getName(), partitionFileSystemBeanUpdateListener);
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) {
         RuntimeAccess config = ManagementService.getRuntimeAccess(PartitionService.kernelId);
         ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(PartitionService.kernelId).getServerRuntime();
         String thisServerName = serverRuntime.getName();
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var6 = updated;
         int var7 = updated.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var6[var8];
            String name;
            PartitionMBean newPartition;
            ResourceGroupTemplateMBean oldRGT;
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  name = propertyUpdate.getPropertyName();
                  if ("Partitions".equals(name)) {
                     newPartition = (PartitionMBean)propertyUpdate.getAddedObject();
                     this.addPartitionFileSystemBeanUpdateListener(newPartition);
                     PartitionBeanUpdateListener partitionListener = new PartitionBeanUpdateListener(newPartition);
                     newPartition.addBeanUpdateListener(partitionListener);
                     PartitionService.partitionListeners.put(newPartition.getName(), partitionListener);
                     boolean istargeted = false;

                     try {
                        PartitionService.createBeanIfTargeted(newPartition, thisServerName);
                        if (config.isAdminServer()) {
                           PartitionFileSystemHelper.checkDomainContentFile();
                           PartitionFileSystemHelper.checkPartitionFileSystem(newPartition);
                        }
                     } catch (ManagementException var16) {
                        throw new Error("Unexpected exception creating partition file system: " + var16.getMessage(), var16);
                     }
                  }

                  if ("ResourceGroupTemplates".equals(name)) {
                     oldRGT = (ResourceGroupTemplateMBean)propertyUpdate.getAddedObject();
                     if (config.isAdminServer()) {
                        PartitionFileSystemHelper.createRGTDirectory(oldRGT);
                     }
                  }
                  break;
               case 3:
                  name = propertyUpdate.getPropertyName();
                  if ("Partitions".equals(name)) {
                     newPartition = (PartitionMBean)propertyUpdate.getRemovedObject();

                     try {
                        String partitionName = newPartition.getName();
                        PartitionBeanUpdateListener listener = (PartitionBeanUpdateListener)PartitionService.partitionListeners.get(partitionName);
                        if (listener != null) {
                           listener.cleanup();
                        }

                        PartitionService.partitionListeners.remove(partitionName);
                        PartitionFileSystemBeanUpdateListener pfsListener = (PartitionFileSystemBeanUpdateListener)PartitionService.partitionFSListeners.get(partitionName);
                        if (pfsListener != null) {
                           pfsListener.cleanup();
                        }

                        PartitionService.partitionFSListeners.remove(partitionName);
                     } catch (Exception var15) {
                        throw new Error("Unexpected exception removing partition runtime mbean", var15);
                     }
                  }

                  if ("ResourceGroupTemplates".equals(name)) {
                     oldRGT = (ResourceGroupTemplateMBean)propertyUpdate.getRemovedObject();
                     if (config.isAdminServer()) {
                        PartitionFileSystemHelper.deleteRGTDirectory(oldRGT);
                     }
                  }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }
}
