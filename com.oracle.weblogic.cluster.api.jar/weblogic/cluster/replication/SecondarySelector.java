package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import org.jvnet.hk2.annotations.Contract;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface SecondarySelector {
   HostID getSecondarySrvr();

   ArrayList getSecondaryCandidates();

   void removeDeadSecondarySrvr(HostID var1);

   void addNewServer(ClusterMemberInfo var1);

   public static class Locator {
      static final String LOCALCLUSTER_SELECTOR_SERVICE_NAME = "LocalClusterSecondarySelector";
      static final String MANCLUSTER_SELECTOR_SERVICE_NAME = "RemoteMANClusterSecondarySelector";

      public static SecondarySelector locate(SelectorPolicy policy) {
         return (SecondarySelector)GlobalServiceLocator.getServiceLocator().getService(SecondarySelector.class, policy.toString(), new Annotation[0]);
      }

      public static enum SelectorPolicy {
         LOCAL("LocalClusterSecondarySelector"),
         MAN("RemoteMANClusterSecondarySelector");

         private String name;

         private SelectorPolicy(String name) {
            this.name = name;
         }

         public String toString() {
            return this.name;
         }
      }
   }
}
