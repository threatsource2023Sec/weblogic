package weblogic.cluster;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface RemoteClusterMemberManager {
   String LOCALSITE_MANAGER = "LocalSite";
   String REMOTESITE_MANAGER = "RemoteManSite";

   void start();

   void stop();

   void addRemoteClusterMemberListener(RemoteClusterMembersChangeListener var1);

   void removeRemoteClusterMemberListener(RemoteClusterMembersChangeListener var1);

   void setRemoteClusterURL(String var1);

   void setCrossDomainSecurityNeeded(boolean var1);

   public static class Locator {
      public static RemoteClusterMemberManager locateLocalSiteManager() {
         return (RemoteClusterMemberManager)GlobalServiceLocator.getServiceLocator().getService(RemoteClusterMemberManager.class, "LocalSite", new Annotation[0]);
      }

      public static RemoteClusterMemberManager locateRemoteSiteManager() {
         return (RemoteClusterMemberManager)GlobalServiceLocator.getServiceLocator().getService(RemoteClusterMemberManager.class, "RemoteManSite", new Annotation[0]);
      }
   }
}
