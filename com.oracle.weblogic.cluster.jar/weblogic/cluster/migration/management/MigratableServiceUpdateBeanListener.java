package weblogic.cluster.migration.management;

import java.security.AccessController;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManagerService;
import weblogic.cluster.singleton.SingletonServicesDebugLogger;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class MigratableServiceUpdateBeanListener implements BeanUpdateListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      if (DEBUG) {
         this.p("Rolling back update: " + event);
      }

      MigratableTargetMBean proposedTarget = (MigratableTargetMBean)event.getProposedBean();
      MigratableTargetMBean existingTarget = this.getMigratableTarget(proposedTarget.getName());
      boolean deactivate = this.isConfigUpdatedForUserPreferredServer(proposedTarget, existingTarget);
      if (deactivate) {
         MigrationManagerService mm = MigrationManagerService.singleton();

         try {
            try {
               mm.deactivateTarget(proposedTarget.getName(), (String)null);
            } catch (MigrationException var10) {
               ClusterLogger.logFailedToDeactivateMigratableServicesDuringRollback(var10);
            }

         } finally {
            ;
         }
      }
   }

   private MigratableTargetMBean getMigratableTarget(String name) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      return domain.lookupMigratableTarget(name);
   }

   private boolean isConfigUpdatedForUserPreferredServer(MigratableTargetMBean proposedMBean, MigratableTargetMBean existingMBean) {
      boolean result = false;
      ServerMBean intendedUPSServer = proposedMBean.getUserPreferredServer();
      if (intendedUPSServer == null) {
         return result;
      } else {
         ServerMBean existingUPSServer = existingMBean.getUserPreferredServer();
         if (DEBUG) {
            this.p("Intended User Preferred Server - " + intendedUPSServer + " Existing User Preferred Server - " + existingUPSServer);
         }

         ServerMBean thisServer = ManagementService.getRuntimeAccess(kernelId).getServer();
         if (existingUPSServer != null) {
            if (!intendedUPSServer.getName().equals(existingUPSServer.getName()) && intendedUPSServer.getName().equals(thisServer.getName())) {
               result = true;
            }
         } else if (intendedUPSServer.getName().equals(thisServer.getName())) {
            result = true;
         }

         return result;
      }
   }

   private void p(Object o) {
      SingletonServicesDebugLogger.debug("MigratableServiceUpdateBeanListener: " + o.toString());
   }
}
