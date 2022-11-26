package weblogic.t3.srvr;

import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainPartitionHelper;
import weblogic.management.EditSessionConfigurationManager;
import weblogic.management.ManagementException;
import weblogic.management.PartitionAppRuntimeStateRuntime;
import weblogic.management.PartitionDeployerRuntime;
import weblogic.management.PartitionDeploymentManager;
import weblogic.management.PartitionUserFileSystemManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.PartitionUserFileSystemManagerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
public class DomainPartitionHelperImpl implements DomainPartitionHelper {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Map domainPartitionRuntimes = Collections.synchronizedMap(new HashMap());
   private DebugLogger debugLogger;
   private DomainMBean domain;
   private DomainRuntimeMBean domainRuntime;
   @Inject
   private EditSessionConfigurationManager editSessionConfigurationManager;
   @Inject
   private PartitionAppRuntimeStateRuntime partitionAppRuntimeStateRuntime;
   @Inject
   private PartitionDeploymentManager partitionDeploymentManager;
   @Inject
   private PartitionDeployerRuntime partitionDeployerRuntime;
   @Inject
   private PartitionUserFileSystemManager partitionUserFileSystemManager;

   public void init(DomainMBean domain) {
      this.debugLogger = DebugLogger.getDebugLogger("DebugServerRuntime");
      this.domain = domain;
      domain.addBeanUpdateListener(this.createBeanUpdateListener());
   }

   public DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes() {
      return (DomainPartitionRuntimeMBean[])this.domainPartitionRuntimes.values().toArray(new DomainPartitionRuntimeMBean[this.domainPartitionRuntimes.size()]);
   }

   public DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String name) {
      return (DomainPartitionRuntimeMBean)this.domainPartitionRuntimes.get(name);
   }

   private DomainPartitionRuntimeMBeanImpl createDomainPartitionRuntime(PartitionMBean p) throws ManagementException {
      DomainPartitionRuntimeMBeanImpl result = new DomainPartitionRuntimeMBeanImpl(p.getName(), p.getPartitionID());
      EditSessionConfigurationManagerMBean editSessionConfigurationManagerMBean = this.editSessionConfigurationManager.createEditSessionConfigurationManagerMBean(result, p.getName(), kernelId);
      result.setEditSessionConfigurationManager(editSessionConfigurationManagerMBean);
      AppRuntimeStateRuntimeMBean appRuntimeStateRuntimeMBean = this.partitionAppRuntimeStateRuntime.createAppRuntimeStateRuntimeMBean(result, p.getName(), kernelId);
      result.setAppRuntimeStateRuntime(appRuntimeStateRuntimeMBean);
      DeploymentManagerMBean deploymentManager = this.partitionDeploymentManager.createDeploymentManagerMBean(result, p.getName(), kernelId);
      result.setDeploymentManager(deploymentManager);
      DeployerRuntimeMBean deployerRuntime = this.partitionDeployerRuntime.createDeployerRuntimeMBean(result, p.getName(), kernelId);
      result.setDeployerRuntime(deployerRuntime);
      PartitionUserFileSystemManagerMBean partitionUserFileSystemManagerMBean = this.partitionUserFileSystemManager.createPartitionUserFileSystemManagerMBean(result, p.getName(), kernelId);
      result.setPartitionUserFileSystemManager(partitionUserFileSystemManagerMBean);
      return result;
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new BeanUpdateListener() {
         private final BeanEventPreparer preparer = DomainPartitionHelperImpl.this.new BeanEventPreparer();
         private final BeanEventActivator activator = DomainPartitionHelperImpl.this.new BeanEventActivator();

         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
            try {
               this.preparer.process(event);
            } catch (BeanEventProcessorException var3) {
               throw new BeanUpdateRejectedException(var3.getMessage());
            }
         }

         public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
            try {
               this.activator.process(event);
            } catch (BeanEventProcessorException var3) {
               throw new BeanUpdateFailedException(var3.getMessage());
            }
         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
   }

   private class BeanEventActivator extends BeanEventProcessor {
      private BeanEventActivator() {
         super(null);
      }

      void addedPartition(PartitionMBean p) throws BeanEventProcessorException {
         try {
            DomainPartitionRuntimeMBeanImpl domainPartitionRuntime = DomainPartitionHelperImpl.this.createDomainPartitionRuntime(p);
            DomainPartitionHelperImpl.this.domainPartitionRuntimes.put(p.getName(), domainPartitionRuntime);
         } catch (ManagementException var4) {
            throw new Error("Unexpected exception creating domain partition MBean for partition " + p.getName(), var4);
         }
      }

      void removedPartition(PartitionMBean p) throws BeanEventProcessorException {
         DomainPartitionRuntimeMBeanImpl domainPartitionRuntime = (DomainPartitionRuntimeMBeanImpl)DomainPartitionHelperImpl.this.domainPartitionRuntimes.get(p.getName());
         if (domainPartitionRuntime != null) {
            DomainPartitionHelperImpl.this.editSessionConfigurationManager.destroyAllPartitionEditSessions(p.getName(), DomainPartitionHelperImpl.kernelId);

            try {
               DomainPartitionHelperImpl.this.partitionDeploymentManager.destroyDeploymentManagerMBean(p.getName(), DomainPartitionHelperImpl.kernelId);
            } catch (ManagementException var7) {
               throw new Error(var7.getMessage());
            }

            try {
               DomainPartitionHelperImpl.this.partitionDeployerRuntime.destroyDeployerRuntimeMBean(p.getName(), DomainPartitionHelperImpl.kernelId);
            } catch (ManagementException var6) {
               throw new Error(var6.getMessage());
            }

            try {
               DomainPartitionHelperImpl.this.partitionUserFileSystemManager.destroyPartitionUserFileSystemManagerMBean(p.getName(), DomainPartitionHelperImpl.kernelId);
            } catch (ManagementException var5) {
               throw new Error(var5.getMessage());
            }

            DomainPartitionHelperImpl.this.domainPartitionRuntimes.remove(p.getName());

            try {
               domainPartitionRuntime.unregister();
            } catch (ManagementException var4) {
               throw new Error("Unexpected exception removing partition " + p.getName(), var4);
            }
         }
      }

      // $FF: synthetic method
      BeanEventActivator(Object x1) {
         this();
      }
   }

   private class BeanEventPreparer extends BeanEventProcessor {
      private BeanEventPreparer() {
         super(null);
      }

      void addedPartition(PartitionMBean p) throws BeanEventProcessorException {
         if (DomainPartitionHelperImpl.this.domainPartitionRuntimes.containsKey(p.getName())) {
            DomainPartitionHelperImpl.this.debugLogger.debug("Domain partition object already exists for new partition " + p.getName());
         }

      }

      void removedPartition(PartitionMBean p) throws BeanEventProcessorException {
         if (!DomainPartitionHelperImpl.this.domainPartitionRuntimes.containsKey(p.getName())) {
            DomainPartitionHelperImpl.this.debugLogger.debug("Domain partition object expected but not found for partition " + p.getName());
         }

      }

      // $FF: synthetic method
      BeanEventPreparer(Object x1) {
         this();
      }
   }

   private class BeanEventProcessorException extends Exception {
      private BeanEventProcessorException(String msg) {
         super(msg);
      }
   }

   private abstract class BeanEventProcessor {
      private BeanEventProcessor() {
      }

      void process(BeanUpdateEvent event) throws BeanEventProcessorException {
         BeanUpdateEvent.PropertyUpdate[] var2 = event.getUpdateList();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BeanUpdateEvent.PropertyUpdate update = var2[var4];
            String propName = update.getPropertyName();
            switch (update.getUpdateType()) {
               case 2:
                  switch (propName) {
                     case "Partitions":
                        this.addedPartition((PartitionMBean)update.getAddedObject());
                     default:
                        continue;
                  }
               case 3:
                  switch (propName) {
                     case "Partitions":
                        this.removedPartition((PartitionMBean)update.getRemovedObject());
                  }
            }
         }

      }

      abstract void addedPartition(PartitionMBean var1) throws BeanEventProcessorException;

      abstract void removedPartition(PartitionMBean var1) throws BeanEventProcessorException;

      // $FF: synthetic method
      BeanEventProcessor(Object x1) {
         this();
      }
   }
}
