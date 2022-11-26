package weblogic.osgi.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.management.internal.DeploymentHandlerExtended;
import weblogic.osgi.OSGiLogger;
import weblogic.osgi.OSGiServerManagerFactory;
import weblogic.server.ServiceFailureException;

public class OSGiDeploymentHandlerExtended implements DeploymentHandlerExtended {
   private final List serviceProviders;
   private final LinkedList preparedDeployments = new LinkedList();

   public OSGiDeploymentHandlerExtended(List providers) {
      this.serviceProviders = providers;
   }

   public void prepareDeployments(Collection added, boolean initialUpdate) throws BeanUpdateRejectedException {
      this.preparedDeployments.clear();
      Iterator var3 = added.iterator();

      while(var3.hasNext()) {
         DeploymentMBean deployment = (DeploymentMBean)var3.next();
         if (deployment instanceof OsgiFrameworkMBean) {
            OsgiFrameworkMBean osgiFramework = (OsgiFrameworkMBean)deployment;
            if (Logger.isDebugEnabled()) {
               Logger.getLogger().debug("Starting OSGi framework " + osgiFramework.getName());
            }

            OSGiServerImpl osgiServer = new OSGiServerImpl(osgiFramework, this.serviceProviders);

            try {
               osgiServer.start();
            } catch (ServiceFailureException var10) {
               Iterator var8 = this.preparedDeployments.iterator();

               while(var8.hasNext()) {
                  OSGiServerImpl toKill = (OSGiServerImpl)var8.next();
                  toKill.stop();
               }

               this.preparedDeployments.clear();
               throw new BeanUpdateRejectedException(var10.getMessage(), var10);
            }

            OSGiLogger.logStartedServer(osgiFramework.getName());
            this.preparedDeployments.add(osgiServer);
         }
      }

   }

   public void activateDeployments() throws BeanUpdateFailedException {
      OSGiServerManagerImpl serverManager = (OSGiServerManagerImpl)OSGiServerManagerFactory.getInstance().getOSGiServerManager();
      Iterator var2 = this.preparedDeployments.iterator();

      while(var2.hasNext()) {
         OSGiServerImpl osgiServer = (OSGiServerImpl)var2.next();
         serverManager.add(osgiServer);
      }

      this.preparedDeployments.clear();
   }

   public void rollbackDeployments() {
      Iterator var1 = this.preparedDeployments.iterator();

      while(var1.hasNext()) {
         OSGiServerImpl osgiServer = (OSGiServerImpl)var1.next();
         osgiServer.stop();
      }

      this.preparedDeployments.clear();
   }

   public void destroyDeployments(Collection removed) {
      OSGiServerManagerImpl serverManager = (OSGiServerManagerImpl)OSGiServerManagerFactory.getInstance().getOSGiServerManager();
      Iterator var3 = removed.iterator();

      while(var3.hasNext()) {
         DeploymentMBean deployment = (DeploymentMBean)var3.next();
         if (deployment instanceof OsgiFrameworkMBean) {
            OsgiFrameworkMBean osgiFramework = (OsgiFrameworkMBean)deployment;
            OSGiServerImpl osgiServer = serverManager.remove(osgiFramework.getName());
            if (osgiServer != null) {
               osgiServer.stop();
            }
         }
      }

   }
}
