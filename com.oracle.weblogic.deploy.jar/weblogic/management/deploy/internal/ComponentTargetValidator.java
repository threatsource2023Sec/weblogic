package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Set;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WebDeploymentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ComponentTargetValidator {
   ComponentMBean component;
   Set clusterTargets;
   Set serverTargets;
   Set hostTargets;
   Set vTargetTargets;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ComponentTargetValidator(ComponentMBean comp) throws ManagementException {
      this(comp, true);
   }

   public ComponentTargetValidator(ComponentMBean comp, boolean throwExceptionIfServerIsContainedInExistingCluster) throws ManagementException {
      this.clusterTargets = new HashSet();
      this.serverTargets = new HashSet();
      this.hostTargets = new HashSet();
      this.vTargetTargets = new HashSet();
      this.component = comp;
      TargetMBean[] targets = comp.getTargets();

      for(int i = 0; i < targets.length; ++i) {
         TargetMBean target = targets[i];
         if (target instanceof ServerMBean) {
            this.addServerTarget((ServerMBean)target, throwExceptionIfServerIsContainedInExistingCluster);
         } else if (target instanceof ClusterMBean) {
            this.addClusterTarget((ClusterMBean)target);
         } else if (target instanceof MigratableTargetMBean) {
         }
      }

      if (comp instanceof WebDeploymentMBean) {
         VirtualHostMBean[] hosts = ((WebDeploymentMBean)comp).getVirtualHosts();

         for(int i = 0; i < hosts.length; ++i) {
            this.addHostTarget(hosts[i]);
         }
      }

   }

   public ComponentMBean getComponent() {
      return this.component;
   }

   public void addClusterTarget(ClusterMBean cluster) throws ManagementException {
      ServerMBean[] servers = cluster.getServers();

      for(int i = 0; i < servers.length; ++i) {
         if (this.serverTargets.contains(servers[i])) {
            Loggable l = DeployerRuntimeLogger.logClusterMemberAlreadyTargetedLoggable(cluster.getName(), servers[i].getName(), this.component.getName());
            l.log();
            throw new ManagementException(l.getMessage());
         }
      }

      this.clusterTargets.add(cluster);
   }

   public void addServerTarget(ServerMBean server, boolean throwExceptionIfServerIsContainedInExistingCluster) throws ManagementException {
      if (throwExceptionIfServerIsContainedInExistingCluster) {
         ClusterMBean cluster = server.getCluster();
         if (cluster != null) {
            if (this.clusterTargets.contains(cluster)) {
               Loggable l = DeployerRuntimeLogger.logServerAlreadyTargetedByClusterLoggable(cluster.getName(), server.getName(), this.component.getName());
               l.log();
               throw new ManagementException(l.getMessage());
            }

            ServerMBean[] servers = cluster.getServers();
            if (servers != null) {
               for(int i = 0; i < servers.length; ++i) {
                  ServerMBean cServer = servers[i];
                  if (!cServer.getName().equals(server.getName()) && this.serverTargets.contains(cServer)) {
                     Loggable l = DeployerRuntimeLogger.logServerAlreadyTargetedByOtherClusterMemberLoggable(cluster.getName(), server.getName(), cServer.getName(), this.component.getName());
                     l.log();
                  }
               }
            }
         }
      }

      this.serverTargets.add(server);
   }

   public void addHostTarget(VirtualHostMBean host) throws ManagementException {
      if (this.component instanceof WebDeploymentMBean) {
         this.hostTargets.add(host);
      } else {
         Loggable l = DeployerRuntimeLogger.logHostTargetForNonWebAppLoggable(host.getName(), this.component.getName());
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   public void addVTargetTarget(VirtualTargetMBean virtualTarget) throws ManagementException {
      if (!(this.component instanceof WebDeploymentMBean)) {
         Loggable l = DeployerRuntimeLogger.logHostTargetForNonWebAppLoggable(virtualTarget.getName(), this.component.getName());
         l.log();
         throw new ManagementException(l.getMessage());
      } else {
         this.vTargetTargets.add(virtualTarget);
      }
   }

   public void addTarget(String target, DomainMBean editDomain, boolean throwExceptionIfServerIsContainedInExistingCluster) throws ManagementException {
      DomainMBean domain = null;
      if (editDomain != null) {
         domain = editDomain;
      } else {
         domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      }

      ServerMBean server = domain.lookupServer(target);
      if (server != null) {
         this.addServerTarget(server, throwExceptionIfServerIsContainedInExistingCluster);
      } else {
         ClusterMBean cluster = domain.lookupCluster(target);
         if (cluster != null) {
            this.addClusterTarget(cluster);
         } else {
            VirtualHostMBean virtualHost = domain.lookupVirtualHost(target);
            if (virtualHost != null) {
               this.addHostTarget(virtualHost);
            } else {
               VirtualTargetMBean virtualTarget = domain.lookupInAllVirtualTargets(target);
               if (virtualTarget != null) {
                  this.addVTargetTarget(virtualTarget);
               } else {
                  Loggable l = DeployerRuntimeLogger.logNoSuchTargetLoggable(target);
                  l.log();
                  throw new ManagementException(l.getMessage());
               }
            }
         }
      }
   }
}
