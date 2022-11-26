package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.MigrationManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class PreAdminSingletonServicesService implements ServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public String getName() {
      return "Pre Admin Singleton Services Service";
   }

   public String getVersion() {
      return "1.0";
   }

   @PostConstruct
   public synchronized void start() throws ServiceFailureException {
   }

   @PreDestroy
   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      MigrationManagerService mms = (MigrationManagerService)GlobalServiceLocator.getServiceLocator().getService(MigrationManagerService.class, new Annotation[0]);
      mms.handlePriorityShutDownTasks();
   }
}
