package weblogic.cluster;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ActivatedService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceActivator;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class ClusterServiceActivator extends ServiceActivator implements ClusterServicesActivator {
   public static final String SERVICE_CLASS = "weblogic.cluster.ClusterService";
   @Inject
   @Named("DatabaseLessLeasingService")
   private ServerService dependencyOnDatabaseLessLeasingService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("RemoteBinderFactoryService")
   private ServerService dependencyOnRemoteBinderFactoryService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ClusterServiceActivator INSTANCE;

   public static synchronized ClusterServiceActivator getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ClusterServiceActivator();
      }

      return INSTANCE;
   }

   private ClusterServiceActivator() {
      super("weblogic.cluster.ClusterService");
   }

   protected ActivatedService instantiateService() throws ServiceFailureException {
      return super.instantiateService();
   }

   ClusterServices getClusterService() {
      return (ClusterServices)this.getServiceObj();
   }

   public boolean isServiceRunning() {
      return this.isStarted();
   }

   public static class ClusterServiceLocator {
      public static ClusterServices getClusterService() {
         try {
            ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
            ClusterService cs = (ClusterService)serviceLocator.getService(Class.forName("weblogic.cluster.ClusterService"), new Annotation[0]);
            return cs.getActivator() != null && cs.getActivator().isStarted() ? cs : null;
         } catch (IllegalStateException var2) {
            return ClusterServiceActivator.getInstance().getClusterService();
         } catch (ClassNotFoundException var3) {
            throw new RuntimeException(var3);
         }
      }
   }
}
