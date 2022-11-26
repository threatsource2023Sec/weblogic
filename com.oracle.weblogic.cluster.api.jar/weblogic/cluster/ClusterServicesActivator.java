package weblogic.cluster;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface ClusterServicesActivator {
   boolean isServiceRunning();

   public static class Locator {
      public static ClusterServices locateClusterServices() {
         ClusterServices service = null;
         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         ServiceHandle handle = serviceLocator.getServiceHandle(ClusterServicesActivator.class, new Annotation[0]);
         if (handle.isActive() && ((ClusterServicesActivator)serviceLocator.getService(ClusterServicesActivator.class, new Annotation[0])).isServiceRunning()) {
            service = (ClusterServices)serviceLocator.getService(ClusterServices.class, new Annotation[0]);
         }

         return service;
      }
   }
}
