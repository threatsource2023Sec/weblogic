package weblogic.cluster;

import java.security.AccessController;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Singleton
public class ClusterConfigurationValidatorImpl implements ClusterConfigurationValidator {
   private static final ClusterTextTextFormatter fmt = new ClusterTextTextFormatter();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void validateMulticastAddress(String address) throws IllegalArgumentException {
      String[] tokens = address.split("\\.");
      int[] octets = new int[tokens.length];

      try {
         for(int i = 0; i < tokens.length; ++i) {
            octets[i] = Integer.parseInt(tokens[i]);
         }
      } catch (NumberFormatException var5) {
         return;
      }

      if (octets.length != 4) {
         throw new IllegalArgumentException("Multicast addresses must be of format: ddd.ddd.ddd.ddd where d is a digit.");
      } else if (octets[0] < 224 || octets[0] > 239 || octets[1] < 0 || octets[1] > 255 || octets[2] < 0 || octets[2] > 255 || octets[3] < 0 || octets[3] > 255) {
         throw new IllegalArgumentException("Illegal value for MulticastAddress, the address must be in the range 224.0.0.0 - 239.255.255.255.");
      }
   }

   public void canSetCluster(ServerMBean server, ClusterMBean cluster) throws IllegalArgumentException {
      if (ManagementService.isRuntimeAccessInitialized()) {
         RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
         if (rt != null && !rt.isAdminServer()) {
            return;
         }

         DomainMBean domain = rt.getDomain();
         ServerMBean existingConfig = domain.lookupServer(server.getName());
         if (existingConfig == null) {
            return;
         }

         MigratableTargetMBean[] mts = domain.getMigratableTargets();

         for(int i = 0; i < mts.length; ++i) {
            if (mts[i].getParent().getType().equals("Domain")) {
               String defaultName = server.getName() + " (migratable)";
               if (!mts[i].getName().equals(defaultName)) {
                  boolean exists = mts[i].getUserPreferredServer().getName().equals(server.getName());
                  String oldClusterName = existingConfig.getCluster() == null ? null : existingConfig.getCluster().getName();
                  String newClusterName = cluster == null ? null : cluster.getName();
                  if (exists && (oldClusterName != null || newClusterName != null)) {
                     if (oldClusterName != null && newClusterName != null && !oldClusterName.equals(newClusterName)) {
                        throw new IllegalArgumentException(fmt.getCannotChangeClusterWhileServerReferredToInMigratableTarget(oldClusterName, server.getName()));
                     }

                     if (oldClusterName != null && newClusterName == null) {
                        throw new IllegalArgumentException(fmt.getCannotChangeClusterWhileServerReferredToInMigratableTarget(oldClusterName, server.getName()));
                     }
                  }
               }
            }
         }
      }

   }

   public void validateUnicastCluster(ServerTemplateMBean server, ClusterMBean cluster) throws IllegalArgumentException {
      if (ManagementService.isRuntimeAccessInitialized()) {
         if (cluster == null || !cluster.getClusterMessagingMode().equals("unicast")) {
            return;
         }

         String broadcastChannelName = cluster.getClusterBroadcastChannel();
         if (broadcastChannelName != null && !broadcastChannelName.isEmpty()) {
            NetworkAccessPointMBean[] channels = server.getNetworkAccessPoints();
            NetworkAccessPointMBean[] var10 = channels;
            int var6 = channels.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               NetworkAccessPointMBean ch = var10[var7];
               if (broadcastChannelName.equals(ch.getName())) {
                  return;
               }
            }

            throw new IllegalArgumentException("Cluster broadcast channel '" + broadcastChannelName + "' not configured for server " + server.getName());
         }

         if (!(server instanceof ServerMBean)) {
            return;
         }

         String listenAddr = server.getListenAddress();
         if ((listenAddr == null || listenAddr.isEmpty()) && server.getMachine() == null) {
            RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
            if (rt != null && !rt.isAdminServer()) {
               ClusterExtensionLogger.logServerMissingListenAddress(server.getName());
            } else {
               ClusterExtensionLogger.logMissingListenAddress(server.getName());
            }
         }
      }

   }
}
