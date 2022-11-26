package weblogic.management.mbeans.custom;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.singleton.LeasingException;
import weblogic.cluster.singleton.SingletonMonitorRemote;
import weblogic.cluster.singleton.MigratableServer.Locator;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.protocol.URLManagerService;
import weblogic.server.GlobalServiceLocator;

public class ClusterSingletonMonitorFinder {
   private static ClusterSingletonMonitorFinder finder = new ClusterSingletonMonitorFinder();

   static SingletonMonitorRemote findRemoteMonitor(ClusterMBean clusterMBean) {
      return finder.findRemoteMonitorInternal(clusterMBean);
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private SingletonMonitorRemote findRemoteMonitorInternal(ClusterMBean cluster) {
      if (cluster == null) {
         return null;
      } else {
         SingletonMonitorRemote smr = null;

         try {
            if (!this.isClusterServiceRunning()) {
               ServerMBean[] servers = cluster.getServers();

               for(int i = 0; i < servers.length; ++i) {
                  try {
                     String url = getURLManagerService().findAdministrationURL(servers[i].getName());
                     smr = this.lookupMonitorFromJndi(url);
                     if (smr != null) {
                        return smr;
                     }
                  } catch (UnknownHostException var6) {
                  }
               }
            } else {
               smr = Locator.locate().getSingletonMasterRemote();
            }
         } catch (LeasingException var7) {
         }

         return smr;
      }
   }

   private boolean isClusterServiceRunning() {
      return weblogic.cluster.ClusterServicesActivator.Locator.locateClusterServices() != null;
   }

   private SingletonMonitorRemote lookupMonitorFromJndi(String url) {
      Environment env = new Environment();
      Context ctx = null;

      SingletonMonitorRemote var4;
      try {
         if (url != null) {
            env.setProviderUrl(url);
            ctx = env.getInitialContext();
            var4 = (SingletonMonitorRemote)ctx.lookup("weblogic/cluster/singleton/SingletonMonitorRemote");
            return var4;
         }

         var4 = null;
      } catch (NamingException var16) {
         Object var5 = null;
         return (SingletonMonitorRemote)var5;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var15) {
            }
         }

      }

      return var4;
   }
}
