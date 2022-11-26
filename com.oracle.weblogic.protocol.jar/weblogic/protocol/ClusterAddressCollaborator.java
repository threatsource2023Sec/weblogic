package weblogic.protocol;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface ClusterAddressCollaborator {
   void configure(String var1, int var2);

   void addMember(ServerIdentity var1);

   void removeMember(ServerIdentity var1);

   String getClusterAddressURL(ServerChannel var1);

   public static class Locator {
      public static ClusterAddressCollaborator locate() {
         return (ClusterAddressCollaborator)GlobalServiceLocator.getServiceLocator().getService(ClusterAddressCollaborator.class, new Annotation[0]);
      }
   }
}
