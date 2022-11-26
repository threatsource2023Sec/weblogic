package weblogic.servlet.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ondemand.Deployer;
import weblogic.application.ondemand.DeploymentProvider;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;

public class OnWebUriDemandDeploymentProvider implements DeploymentProvider {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugBackgroundDeployment");

   private static List getHttpServers(AppDeploymentMBean app) {
      List servers = new ArrayList();
      HttpServerManager mgr = WebServerRegistry.getInstance().getHttpServerManager();
      if (app.isInternalApp() && app.getPartitionName() != null) {
         TargetMBean[] var3 = app.getTargets();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean target = var3[var5];
            HttpServer server = mgr.getHttpServer(target.getName());
            if (server != null) {
               servers.add(server);
            }
         }

         return servers;
      } else {
         servers.add(mgr.defaultHttpServer());
         return servers;
      }
   }

   private static String[] getContextPaths(String uriPath, String[] appContextPaths) {
      if (uriPath != null && !uriPath.isEmpty() && !uriPath.equals("/")) {
         String[] contextPaths = new String[appContextPaths.length];

         for(int i = 0; i < appContextPaths.length; ++i) {
            contextPaths[i] = uriPath;
            if (!appContextPaths[i].startsWith("/")) {
               contextPaths[i] = contextPaths[i] + "/";
            }

            contextPaths[i] = contextPaths[i] + appContextPaths[i];
         }

         return contextPaths;
      } else {
         return appContextPaths;
      }
   }

   public boolean claim(AppDeploymentMBean application, Deployer deployer) {
      List httpServers = getHttpServers(application);
      if (logger.isDebugEnabled()) {
         logger.debug(String.format("OnWebUriDemandDeploymentProvider claim for: %s, httpServers: %s", application.getName(), httpServers));
      }

      if (httpServers.isEmpty()) {
         return false;
      } else {
         HttpServer httpServer;
         String[] contextPaths;
         for(Iterator var4 = httpServers.iterator(); var4.hasNext(); httpServer.getOnDemandManager().registerOnDemandContextPaths(contextPaths, new OnWebUriDemandListener(deployer), application.getName(), application.isOnDemandDisplayRefresh())) {
            httpServer = (HttpServer)var4.next();
            contextPaths = getContextPaths(httpServer.getUriPath(), application.getOnDemandContextPaths());
            if (logger.isDebugEnabled()) {
               logger.debug(String.format("OnWebUriDemandDeploymentProvider registering on demand context paths: %s for: %s on: %s", Arrays.toString(contextPaths), application.getName(), httpServer));
            }
         }

         return true;
      }
   }

   public boolean unclaim(AppDeploymentMBean application) {
      List httpServers = getHttpServers(application);
      if (logger.isDebugEnabled()) {
         logger.debug(String.format("OnWebUriDemandDeploymentProvider unclaim for: %s, httpServers: %s", application.getName(), httpServers));
      }

      if (httpServers.isEmpty()) {
         return false;
      } else {
         HttpServer httpServer;
         String[] contextPaths;
         for(Iterator var3 = httpServers.iterator(); var3.hasNext(); httpServer.getOnDemandManager().unregisterOnDemandContextPaths(contextPaths)) {
            httpServer = (HttpServer)var3.next();
            contextPaths = getContextPaths(httpServer.getUriPath(), application.getOnDemandContextPaths());
            if (logger.isDebugEnabled()) {
               logger.debug(String.format("OnWebUriDemandDeploymentProvider un-registering on demand context paths: %s for: %s on: %s", Arrays.toString(contextPaths), application.getName(), httpServer));
            }
         }

         return true;
      }
   }

   public static class OnWebUriDemandListener implements OnDemandListener {
      private final Deployer deployer;

      OnWebUriDemandListener(Deployer deployer) {
         this.deployer = deployer;
      }

      public void OnDemandURIAccessed(String uri, String appName, boolean loadAsynchronously) throws DeploymentException {
         if (OnWebUriDemandDeploymentProvider.logger.isDebugEnabled()) {
            OnWebUriDemandDeploymentProvider.logger.debug("OnDemand URI accessed : " + uri + " for app: " + appName + " load async: " + loadAsynchronously);
         }

         this.deployer.deploy(appName, loadAsynchronously);
      }
   }
}
