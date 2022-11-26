package weblogic.store.admin;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.store.common.StoreDebug;

@Service
@Named
@RunLevel(10)
public class DefaultStoreService extends AbstractServerService {
   @Inject
   @Named("T3InitializationService")
   ServerService dependencyOnT3InitializationService;
   @Inject
   @Named("TransactionRecoveryNoOpService")
   ServerService dependencyOnTransactionRecoveryNoOpService;
   @Inject
   @Named("TransactionRecoveryFailBackService")
   ServerService dependencyOnTransactionRecoveryFailBackService;
   private boolean running;
   private FileAdminHandler defaultStoreHandler;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public synchronized void start() throws ServiceFailureException {
      StoreDebug.storeAdmin.debug("DefaultStoreService starting");
      this.running = true;

      try {
         if (this.defaultStoreHandler == null) {
            ServerMBean serverBean = ManagementService.getRuntimeAccess(kernelId).getServer();
            this.defaultStoreHandler = new FileAdminHandler();
            this.defaultStoreHandler.prepareDefaultStore(serverBean, true);
         }

         this.defaultStoreHandler.activate((GenericManagedDeployment)null);
      } catch (DeploymentException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public synchronized void halt() throws ServiceFailureException {
      if (this.running) {
         StoreDebug.storeAdmin.debug("DefaultStoreService suspending");
         this.running = false;

         try {
            if (this.defaultStoreHandler != null) {
               this.defaultStoreHandler.deactivate((GenericManagedDeployment)null);
            }

         } catch (UndeploymentException var2) {
            throw new ServiceFailureException(var2);
         }
      }
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }
}
