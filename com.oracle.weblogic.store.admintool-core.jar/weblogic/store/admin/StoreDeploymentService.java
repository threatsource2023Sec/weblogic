package weblogic.store.admin;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceNotFoundException;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.ReplicatedStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericManagedService;
import weblogic.management.utils.GenericServiceManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.store.common.StoreDebug;

@Service
@Named
@RunLevel(10)
public class StoreDeploymentService extends AbstractServerService {
   @Inject
   @Named("MigrationManagerService")
   private ServerService dependencyOnMigrationService;
   static final String IMAGE_NAME = "PERSISTENT_STORE";
   private final ImageSource IMAGE_SOURCE = new PersistentStoreImageSource();
   private boolean registered;
   private GenericManagedService fileStoreService;
   private GenericManagedService replicatedStoreService;
   private GenericManagedService jdbcStoreService;

   public synchronized void start() throws ServiceFailureException {
      StoreDebug.storeAdmin.debug("StoreService starting");
      if (!this.registered) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         ManagementService.getRuntimeAccess(kernelId).addAccessCallbackClass("weblogic.store.admin.StoreCompatibilityUpgrader");
         GenericServiceManager manager = GenericServiceManager.getManager();
         this.fileStoreService = manager.register(FileStoreMBean.class, FileAdminHandler.class, true);
         this.replicatedStoreService = manager.register(ReplicatedStoreMBean.class, ReplicatedStoreAdminHandler.class, true);
         this.jdbcStoreService = manager.register(JDBCStoreMBean.class, JDBCAdminHandler.class, true);
         this.registered = true;
      }

      this.fileStoreService.start();
      this.replicatedStoreService.start();
      this.jdbcStoreService.start();
      this.registerDiagnosticImageSource();
   }

   public synchronized void halt() throws ServiceFailureException {
      this.fileStoreService.stop();
      this.replicatedStoreService.stop();
      this.jdbcStoreService.stop();
      this.unregisterDiagnosticImageSource();
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   private void registerDiagnosticImageSource() {
      ImageManager im = this.getImageManager();
      im.registerImageSource("PERSISTENT_STORE", this.IMAGE_SOURCE);
   }

   private void unregisterDiagnosticImageSource() {
      ImageManager im = this.getImageManager();

      try {
         im.unregisterImageSource("PERSISTENT_STORE");
      } catch (ImageSourceNotFoundException var3) {
      }

   }

   private ImageManager getImageManager() {
      return (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
   }
}
