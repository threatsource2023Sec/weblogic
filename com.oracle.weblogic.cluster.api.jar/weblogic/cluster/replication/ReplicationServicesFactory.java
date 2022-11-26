package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface ReplicationServicesFactory {
   ReplicationServices getReplicationService(ServiceType var1);

   public static class Locator {
      public static ReplicationServicesFactory locate() {
         return (ReplicationServicesFactory)GlobalServiceLocator.getServiceLocator().getService(ReplicationServicesFactory.class, new Annotation[0]);
      }
   }

   public static enum ServiceType {
      SYNC,
      ASYNC,
      MAN_SYNC,
      MAN_ASYNC;
   }
}
