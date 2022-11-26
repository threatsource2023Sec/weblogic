package weblogic.cluster.migration;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterTextTextFormatter;
import weblogic.common.internal.VersionInfo;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Singleton
public class MigratableTargetValidatorImpl implements MigratableTargetValidator {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final ClusterTextTextFormatter fmt = new ClusterTextTextFormatter();

   public void validateTarget(MigratableTargetMBean migratableTarget) throws IllegalArgumentException {
      String type = migratableTarget.getType();
      ClusterMBean cluster = migratableTarget.getCluster();
      ServerMBean server = migratableTarget.getUserPreferredServer();
      String name = migratableTarget.getName();
      if (migratableTarget.getParent() instanceof ServerMBean || !(migratableTarget instanceof JTAMigratableTargetMBean)) {
         if (!(migratableTarget instanceof JTAMigratableTargetMBean) && migratableTarget.getMigrationPolicy().equals("shutdown-recovery")) {
            throw new IllegalArgumentException(this.fmt.getIllegalMigrationPolicy(migratableTarget.getMigrationPolicy()));
         } else if (server == null) {
            throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_1B(name));
         } else {
            String defaultName = server.getName() + " (migratable)";
            if (server.getCluster() != null || !name.equals(defaultName) && !(migratableTarget instanceof JTAMigratableTargetMBean)) {
               if (cluster == null) {
                  throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_1A(name));
               } else {
                  String userPreferredServerName = server.getName();
                  if (!isUserPreferredServerIsPartOfCluster(userPreferredServerName, cluster.getServerNames())) {
                     throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_1C(name));
                  } else if (!server.getCluster().getName().equals(cluster.getName())) {
                     throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_2(name));
                  } else {
                     ServerMBean[] constrainedCandidateServers = migratableTarget.getConstrainedCandidateServers();
                     if (constrainedCandidateServers.length > 0) {
                        DomainMBean domain = (DomainMBean)server.getParent();
                        String version = domain.getConfigurationVersion();
                        if ((version == null || (new VersionInfo(version)).getMajor() > 8) && !isUserPreferredServersIsPartOfCandidateServers(server, constrainedCandidateServers)) {
                           throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_3(name));
                        }

                        if (!areAllCandidateServersPartOfCluster(constrainedCandidateServers, cluster)) {
                           throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_4(name));
                        }
                     }

                  }
               }
            }
         }
      }
   }

   public void destroyMigratableTarget(MigratableTargetMBean mt) throws IllegalArgumentException {
      ServerMBean server = mt.getUserPreferredServer();
      if (ManagementService.isRuntimeAccessInitialized()) {
         ServerLifeCycleRuntimeMBean serverLifeCycle = this.getServerLifeCycleRuntime(server.getName());
         String state = serverLifeCycle == null ? "UNKNOWN" : serverLifeCycle.getState();
         if (state.equals("RUNNING") && ManagementService.getDomainAccess(kernelId).getDeployerRuntime().getDeployments(mt).length != 0) {
            throw new IllegalArgumentException(this.fmt.getCannotDeleteMigratableTargetException(mt.getName()));
         }
      }
   }

   private ServerLifeCycleRuntimeMBean getServerLifeCycleRuntime(String server) {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
         return runtime.lookupServerLifecycleRuntime(server);
      } else {
         return null;
      }
   }

   public void destroyServer(ServerMBean server) throws IllegalArgumentException {
      MigratableTargetMBean[] mts = ManagementService.getRuntimeAccess(kernelId).getDomain().getMigratableTargets();

      for(int i = 0; i < mts.length; ++i) {
         ServerMBean userPreferredServer = mts[i].getUserPreferredServer();
         if (server.getName().equals(userPreferredServer.getName())) {
            throw new IllegalArgumentException(this.fmt.getCannotDeleteServerException(server.getName()));
         }
      }

   }

   public void destroyCluster(ClusterMBean cluster) throws IllegalArgumentException {
      MigratableTargetMBean[] mts = ManagementService.getRuntimeAccess(kernelId).getDomain().getMigratableTargets();

      for(int i = 0; i < mts.length; ++i) {
         ClusterMBean targetCluster = mts[i].getCluster();
         if (targetCluster.getName().equals(cluster.getName()) && ManagementService.getDomainAccess(kernelId).getDeployerRuntime().getDeployments(mts[i]).length > 0) {
            throw new IllegalArgumentException(this.fmt.getCannotDeleteClusterException(cluster.getName()));
         }
      }

   }

   public void removeConstrainedCandidateServer(MigratableTargetMBean mt, ServerMBean server) throws IllegalArgumentException {
      ServerMBean userPreferredServer = mt.getUserPreferredServer();
      if (userPreferredServer.getName().equals(server.getName())) {
         throw new IllegalArgumentException(this.fmt.getCannotRemoveUserPreferredServerException(mt.getName()));
      }
   }

   public void canSetCluster(MigratableTargetMBean mt, ClusterMBean cluster) throws IllegalArgumentException {
      ClusterMBean existing = mt.getCluster();
      if (existing != null && !existing.getName().equals(cluster.getName())) {
         throw new IllegalArgumentException(this.fmt.getCannotSetClusterException(mt.getName()));
      }
   }

   public void validateMigrationPolicy(MigratableTargetMBean mt, String value) throws IllegalArgumentException {
      if (!"manual".equals(value)) {
         Object bean = mt.getParent();
         if (!(mt instanceof JTAMigratableTargetMBean) || bean == null || !(bean instanceof ServerTemplateMBean) || bean instanceof ServerMBean) {
            if (!(mt instanceof JTAMigratableTargetMBean) && value.equals("shutdown-recovery")) {
               throw new IllegalArgumentException(this.fmt.getIllegalMigrationPolicy(value));
            } else {
               ClusterMBean cluster = mt.getCluster();
               if (cluster == null) {
                  if (!(bean instanceof ServerMBean)) {
                     return;
                  }

                  ServerMBean server = (ServerMBean)bean;
                  cluster = server.getCluster();
                  if (cluster == null) {
                     return;
                  }
               }

               String basisType = cluster.getMigrationBasis();
               if ("database".equals(basisType) && cluster.getDataSourceForAutomaticMigration() == null) {
                  throw new IllegalArgumentException(this.fmt.getCannotEnableAutoMigrationWithoutLeasing(mt.getName()));
               } else {
                  if ("consensus".equals(basisType)) {
                     ServerMBean[] servers = mt.getConstrainedCandidateServers();
                     if (servers == null) {
                        return;
                     }

                     for(int i = 0; i < servers.length; ++i) {
                        MachineMBean machine = servers[i].getMachine();
                        if (machine == null) {
                           throw new IllegalArgumentException(this.fmt.getNodemanagerRequiredOnCandidateServers(servers[i].getName()));
                        }

                        if (machine.getNodeManager() == null) {
                           throw new IllegalArgumentException(this.fmt.getNodemanagerRequiredOnCandidateServers(servers[i].getName()));
                        }
                     }
                  }

                  if (mt.isCritical()) {
                     throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_5(mt.getName()));
                  }
               }
            }
         }
      }
   }

   public void validateCritical(MigratableTargetMBean mt, boolean value) throws IllegalArgumentException {
      if (!"manual".equals(mt.getMigrationPolicy())) {
         if (value) {
            throw new IllegalArgumentException(this.fmt.getMigratableTargetInvViolation_5(mt.getName()));
         }
      }
   }

   private static boolean isUserPreferredServerIsPartOfCluster(String serverName, Set serversSet) {
      boolean found = false;
      Iterator iterator = serversSet.iterator();

      while(iterator.hasNext()) {
         String name = (String)iterator.next();
         if (name.equals(serverName)) {
            found = true;
            break;
         }
      }

      return found;
   }

   private static boolean isUserPreferredServersIsPartOfCandidateServers(ServerMBean server, ServerMBean[] candidateServers) {
      String serverName = server.getName();

      for(int i = 0; i < candidateServers.length; ++i) {
         if (serverName.equals(candidateServers[i].getName())) {
            return true;
         }
      }

      return false;
   }

   private static boolean areAllCandidateServersPartOfCluster(ServerMBean[] candidateServers, ClusterMBean cluster) {
      HashSet clusteredServers = new HashSet();

      int i;
      for(i = 0; i < candidateServers.length; ++i) {
         clusteredServers.add(candidateServers[i].getName());
      }

      for(i = 0; i < candidateServers.length; ++i) {
         if (!clusteredServers.contains(candidateServers[i].getName())) {
            return false;
         }
      }

      return true;
   }
}
