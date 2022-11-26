package weblogic.cluster.migration;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterTextTextFormatter;
import weblogic.cluster.migration.management.MigratableServiceCoordinatorRuntime;
import weblogic.cluster.migration.management.MigratableServiceUpdateBeanListener;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class MigrationService extends AbstractServerService {
   @Inject
   @Named("DomainAccessService")
   private ServerService domainAccessService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ClusterTextTextFormatter fmt = new ClusterTextTextFormatter();
   private static MigratableServiceCoordinatorRuntimeMBean coordinator;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private void initialize() throws ServiceFailureException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         try {
            coordinator = new MigratableServiceCoordinatorRuntime();
            ((DomainAccessSettable)ManagementService.getDomainAccess(kernelId)).setServerMigrationCoordinator(coordinator);
         } catch (ManagementException var6) {
            throw new ServiceFailureException(var6);
         }
      }

      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ClusterMBean[] clusters = domain.getClusters();

      for(int i = 0; i < clusters.length; ++i) {
         this.validateMTConfigs(clusters[i]);
      }

      MigratableTargetMBean[] migratables = domain.getMigratableTargets();
      if (migratables != null) {
         for(int i = 0; i < migratables.length; ++i) {
            DescriptorBean db = migratables[i];
            db.addBeanUpdateListener(new MigratableServiceUpdateBeanListener());
         }

         domain.addBeanUpdateListener(new MigratableConfiguredListener());
      }
   }

   public void start() throws ServiceFailureException {
      this.initialize();
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }

   private void validateMTConfigs(ClusterMBean cluster) throws ServiceFailureException {
      MigratableTargetMBean[] targets = cluster.getMigratableTargets();
      boolean autoMigrationEnabled = false;
      HashSet servers = new HashSet();

      for(int i = 0; i < targets.length; ++i) {
         if (!"manual".equals(targets[i].getMigrationPolicy())) {
            autoMigrationEnabled = true;
            ServerMBean[] thisTargetsServers = targets[i].getConstrainedCandidateServers();

            for(int j = 0; j < thisTargetsServers.length; ++j) {
               servers.add(thisTargetsServers[j]);
            }
         }
      }

      if (autoMigrationEnabled) {
         String basisType = cluster.getMigrationBasis();
         if ("database".equals(basisType) && cluster.getDataSourceForAutomaticMigration() == null) {
            throw new ServiceFailureException(fmt.getCannotEnableAutoMigrationWithoutLeasing2());
         } else {
            if ("consensus".equals(basisType)) {
               Iterator iter = servers.iterator();

               while(iter.hasNext()) {
                  ServerMBean s = (ServerMBean)iter.next();
                  MachineMBean machine = s.getMachine();
                  if (machine == null) {
                     throw new ServiceFailureException(fmt.getNodemanagerRequiredOnCandidateServers(s.getName()));
                  }

                  if (machine.getNodeManager() == null) {
                     throw new ServiceFailureException(fmt.getNodemanagerRequiredOnCandidateServers(s.getName()));
                  }
               }
            }

         }
      }
   }

   class MigratableConfiguredListener implements BeanUpdateListener {
      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

         for(int i = 0; i < updated.length; ++i) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            if (propertyUpdate.getUpdateType() == 2 && propertyUpdate.getPropertyName().equals("MigratableTargets")) {
               MigratableTargetMBean newMigratableTarget = (MigratableTargetMBean)propertyUpdate.getAddedObject();
               newMigratableTarget.addBeanUpdateListener(new MigratableServiceUpdateBeanListener());
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }
}
