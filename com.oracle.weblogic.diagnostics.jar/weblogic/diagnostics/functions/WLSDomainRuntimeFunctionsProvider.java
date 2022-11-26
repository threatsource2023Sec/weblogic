package weblogic.diagnostics.functions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.Function;
import com.oracle.weblogic.diagnostics.expressions.FunctionProvider;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import java.security.AccessController;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@FunctionProvider(
   prefix = "wls"
)
@Singleton
@Service
@AdminServer
@MetricRuleType
public class WLSDomainRuntimeFunctionsProvider {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionFunctions");
   public static final String PREFIX = "wls";
   private static DomainAccess domainAccess = null;
   private static RuntimeAccess runtimeAccess = null;

   @Function
   @WLDFI18n(
      value = "aliveServersCount.desc",
      displayName = "aliveServersCount.displayName"
   )
   public static int aliveServersCount(@WLDFI18n(name = "clusterName",value = "aliveServersCount.clusterName.short") String clusterName) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Invoked function wls:aliveServersCount(" + clusterName + ")");
      }

      if (clusterName != null && !clusterName.equals("")) {
         ensureManagementAccessInitialized();
         int count = 0;
         ServerLifeCycleRuntimeMBean[] serverLifecycles = domainAccess.getServerLifecycleRuntimes();
         ServerLifeCycleRuntimeMBean[] var3 = serverLifecycles;
         int var4 = serverLifecycles.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ServerLifeCycleRuntimeMBean slc = var3[var5];
            String serverName = slc.getName();
            ServerMBean server = runtimeAccess.getDomain().lookupServer(serverName);
            if (server != null) {
               ClusterMBean cluster = server.getCluster();
               if (cluster != null && cluster.getName().equals(clusterName) && slc.getStateVal() == 2) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("Found server " + server.getName() + " running in cluster " + clusterName);
                  }

                  ++count;
               }
            }
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Found " + count + " server(s) running in cluster " + clusterName);
         }

         return count;
      } else {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Cluster name is empty.");
         }

         return 0;
      }
   }

   private static synchronized void ensureManagementAccessInitialized() {
      if (domainAccess == null) {
         domainAccess = ManagementService.getDomainAccess(KERNEL_ID);
      }

      if (runtimeAccess == null) {
         runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      }

   }
}
