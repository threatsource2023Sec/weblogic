package weblogic.cluster.migration;

import java.security.AccessController;
import java.util.List;
import weblogic.cluster.singleton.AbstractServiceLocationSelector;
import weblogic.cluster.singleton.BasicServiceLocationSelector;
import weblogic.cluster.singleton.ServiceLocationSelector;
import weblogic.cluster.singleton.SingletonServicesDebugLogger;
import weblogic.cluster.singleton.SingletonServicesState;
import weblogic.cluster.singleton.SingletonServicesStateManager;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ExactlyOnceServiceLocationSelector extends AbstractServiceLocationSelector {
   private MigratableTargetMBean mtBean;
   private ServerMBean upsServer;
   private SingletonServicesStateManager stateManager;
   private List serverList;
   private ServerMBean lastHost;
   private boolean triedOnUPS = false;
   private ServiceLocationSelector sls;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ExactlyOnceServiceLocationSelector(MigratableTargetMBean mt, SingletonServicesStateManager sm) {
      this.mtBean = mt;
      this.upsServer = mt.getUserPreferredServer();
      this.stateManager = sm;
      this.sls = new BasicServiceLocationSelector(mt.getName(), sm);
   }

   public ServerMBean chooseServer() {
      ServerMBean target = null;
      boolean postScriptExecution = false;
      if (this.lastHost == null) {
         target = this.startOnUPServer(postScriptExecution);
      } else {
         if (!this.stateManager.checkServiceState(this.mtBean.getName(), 0)) {
            postScriptExecution = true;
         }

         boolean uncleanShutdown = false;
         if (this.lastHost == ManagementService.getRuntimeAccess(kernelId).getServer() && this.lastHost.getName().equals(this.upsServer.getName()) && this.stateManager.getServiceState(this.mtBean.getName()) == null) {
            uncleanShutdown = true;
         }

         if (!this.lastHost.getName().equals(this.upsServer.getName()) || uncleanShutdown) {
            target = this.startOnUPServer(postScriptExecution);
         }
      }

      if (target == null) {
         target = this.startOnNonUPServer(postScriptExecution);
      }

      return target;
   }

   public void setLastHost(ServerMBean server) {
      this.lastHost = server;
      this.sls.setLastHost(server);
   }

   public void setServerList(List servers) {
      this.serverList = this.getAcceptableServers(servers);
      if (this.serverList.size() > 1) {
         this.serverList.remove(this.upsServer);
      }

      this.sls.setServerList(this.serverList);
   }

   public void migrationSuccessful(ServerMBean server, boolean automaticMigration) {
      if (!automaticMigration) {
         this.upsServer = server;
      }

      this.stateManager.storeServiceState(this.mtBean.getName(), new SingletonServicesState(1));
   }

   private ServerMBean startOnUPServer(boolean doPostScriptExecution) {
      ServerMBean target = null;
      if (this.isServerRunning(this.upsServer) && !this.triedOnUPS) {
         target = this.chooseUPSServer();
      }

      if (target != null) {
         if (DEBUG) {
            p(this.mtBean.getName() + "- Going to select (UPS) " + target);
         }

         if (doPostScriptExecution && !this.executePostScript(this.mtBean, target, this.lastHost)) {
            this.stateManager.storeServiceState(this.mtBean.getName(), new SingletonServicesState(4));
            target = null;
         }
      } else if (DEBUG) {
         p(this.mtBean.getName() + " - UPS Server " + this.upsServer + " not available");
      }

      return target;
   }

   private ServerMBean startOnNonUPServer(boolean doPostScriptExecution) {
      if (this.serverList.size() == 0) {
         return null;
      } else {
         ServerMBean target = this.sls.chooseServer();
         if (target != null) {
            if (DEBUG) {
               p(this.mtBean.getName() + " - Going to select (Non-UPS) " + target);
            }

            if (doPostScriptExecution && !this.executePostScript(this.mtBean, target, this.lastHost)) {
               this.stateManager.storeServiceState(this.mtBean.getName(), new SingletonServicesState(4));
               target = null;
            }
         }

         return target;
      }
   }

   private ServerMBean chooseUPSServer() {
      this.sls.setLastHost(this.upsServer);
      this.triedOnUPS = true;
      return this.upsServer;
   }

   protected static void p(Object o) {
      SingletonServicesDebugLogger.debug("ExactlyOnceServiceLocationSelector: " + o);
   }
}
