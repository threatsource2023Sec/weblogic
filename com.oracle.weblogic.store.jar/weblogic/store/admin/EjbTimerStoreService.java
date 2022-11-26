package weblogic.store.admin;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreManager;
import weblogic.store.common.StoreDebug;
import weblogic.work.PartitionUtility;

@Service
@Named
@RunLevel(10)
public class EjbTimerStoreService extends AbstractServerService {
   private static final boolean DEPLOYSERVICE_START_ALL = false;
   private static final String GLOBAL_NAME = "DOMAIN";
   private static final String EJBTIMER_NAME_PREFIX = "_WLS_EJBTIMER_";
   private final RuntimeAccess runtimeAccess;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final String IMAGE_NAME = "EJBTIMER_STORE";
   @Inject
   @Named("PartitionRuntimeBuilderService")
   ServerService dependencyOnPartitionService;
   private boolean registered = false;
   private DomainBeanUpdateListener domainBeanUpdateListener;
   private Map handlerMap = new HashMap();

   @Inject
   public EjbTimerStoreService(RuntimeAccess runtimeAccess) {
      this.runtimeAccess = runtimeAccess;
   }

   public synchronized void start() throws ServiceFailureException {
   }

   DomainBeanUpdateListener getDomainBeanUpdateListener() {
      return this.domainBeanUpdateListener;
   }

   private void registerPartitionNotificationListener() {
      DomainMBean domainBean = this.runtimeAccess.getDomain();
      this.domainBeanUpdateListener = new DomainBeanUpdateListener(this);
      domainBean.addBeanUpdateListener(this.domainBeanUpdateListener);
   }

   private void createEjbTimerStore(String partitionName, String partitionId) throws DeploymentException {
      PersistentStoreManager persistentStoreManager = PersistentStoreManager.getManager();
      ComponentInvocationContext cic = PartitionUtility.createComponentInvocationContext(partitionName, (String)null, (String)null, (String)null, (String)null);
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(cic);
      Throwable var7 = null;

      try {
         PersistentStore ejbTimerStore = persistentStoreManager.getEjbTimerStore();
         if (ejbTimerStore == null) {
            if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
               StoreDebug.ejbTimerStore.debug("Creating EJB Timer store for parition " + partitionName);
            }

            ServerMBean serverBean = this.runtimeAccess.getServer();
            String serverName = serverBean.getName();
            String name = "_WLS_EJBTIMER_" + serverName;
            FileAdminHandler fileAdminHandler = new FileAdminHandler();
            fileAdminHandler.prepareEjbTimerStore(serverBean, name);
            fileAdminHandler.activate((GenericManagedDeployment)null);
            this.handlerMap.put(this.getLongName(name, partitionName), fileAdminHandler);
            persistentStoreManager.addEjbTimerStore(partitionId, serverName, fileAdminHandler.getStore());
            if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
               StoreDebug.ejbTimerStore.debug("EJB Timer store created for parition " + partitionName);
            }
         } else if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("EJB Timer store already created for parition " + partitionName);
         }
      } catch (Throwable var20) {
         var7 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var7 != null) {
               try {
                  mic.close();
               } catch (Throwable var19) {
                  var7.addSuppressed(var19);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void removeEjbTimerStore(String partitionName, String partitionId) {
      PersistentStoreManager persistentStoreManager = PersistentStoreManager.getManager();
      ServerMBean serverBean = this.runtimeAccess.getServer();
      String serverName = serverBean.getName();
      String name = "_WLS_EJBTIMER_" + serverName;
      FileAdminHandler fileAdminHandler = (FileAdminHandler)this.handlerMap.get(this.getLongName(name, partitionName));
      ComponentInvocationContext cic = PartitionUtility.createComponentInvocationContext(partitionName, (String)null, (String)null, (String)null, (String)null);
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(cic);
      Throwable var11 = null;

      try {
         if (fileAdminHandler != null) {
            fileAdminHandler.unprepareEjbTimerStore(serverBean);
         }

         persistentStoreManager.removeEjbTimerStore(partitionId, serverName);
         persistentStoreManager.removeStore(name);
      } catch (Throwable var20) {
         var11 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var11 != null) {
               try {
                  mic.close();
               } catch (Throwable var19) {
                  var11.addSuppressed(var19);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public synchronized void halt() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
   }

   private String getLongName(String ejbTimerStoreName, String partitionName) {
      StringBuilder sb = new StringBuilder();
      sb.append(ejbTimerStoreName);
      if (!"DOMAIN".equals(partitionName)) {
         sb.append("$").append(partitionName);
      }

      return sb.toString();
   }

   static class DomainBeanUpdateListener implements BeanUpdateListener {
      private static String PARTITION_PROPERTY = "Partitions";
      EjbTimerStoreService ejbTimerStoreService;

      DomainBeanUpdateListener(EjbTimerStoreService timerStoreService) {
         this.ejbTimerStoreService = timerStoreService;
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         int i = 0;

         while(i < updated.length) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            switch (propertyUpdate.getUpdateType()) {
               case 3:
                  String name = propertyUpdate.getPropertyName();
                  if (PARTITION_PROPERTY.equals(name)) {
                     PartitionMBean removedPartition = (PartitionMBean)propertyUpdate.getRemovedObject();
                     this.ejbTimerStoreService.removeEjbTimerStore(removedPartition.getName(), removedPartition.getPartitionID());
                  }
               default:
                  ++i;
            }
         }

      }

      public void activateUpdate(BeanUpdateEvent event) {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

         for(int i = 0; i < updated.length; ++i) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  String name = propertyUpdate.getPropertyName();
                  if (PARTITION_PROPERTY.equals(name)) {
                     PartitionMBean newPartition = (PartitionMBean)propertyUpdate.getAddedObject();

                     try {
                        this.ejbTimerStoreService.createEjbTimerStore(newPartition.getName(), newPartition.getPartitionID());
                     } catch (DeploymentException var8) {
                        if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
                           StoreDebug.ejbTimerStore.debug(var8.getMessage());
                        }

                        var8.printStackTrace();
                     }
                  }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

         for(int i = 0; i < updated.length; ++i) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            switch (propertyUpdate.getUpdateType()) {
               case 3:
                  String name = propertyUpdate.getPropertyName();
                  if (PARTITION_PROPERTY.equals(name)) {
                     PartitionMBean removedPartition = (PartitionMBean)propertyUpdate.getRemovedObject();

                     try {
                        this.ejbTimerStoreService.createEjbTimerStore(removedPartition.getName(), removedPartition.getPartitionID());
                     } catch (DeploymentException var8) {
                        if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
                           StoreDebug.ejbTimerStore.debug(var8.getMessage());
                        }

                        var8.printStackTrace();
                     }
                  }
            }
         }

      }
   }
}
