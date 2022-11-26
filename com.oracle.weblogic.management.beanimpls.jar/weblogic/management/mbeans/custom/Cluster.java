package weblogic.management.mbeans.custom;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.management.RuntimeOperationsException;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;

public final class Cluster extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = 6825873886824636463L;
   private String multicastAddress;
   private transient CoherenceClusterSystemResourceMBean cohCluster;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public Cluster(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public ServerMBean[] getServers() {
      Set resultSet = new HashSet();
      DomainMBean domain = (DomainMBean)((DomainMBean)this.getMbean().getParent());
      ServerMBean[] servers = domain == null ? new ServerMBean[0] : domain.getServers();
      String clusterName = this.getMbean().getName();

      for(int i = 0; i < servers.length; ++i) {
         ServerMBean server = servers[i];
         if (server.getCluster() != null && server.getCluster().getName().equals(clusterName)) {
            resultSet.add(server);
         }
      }

      ServerMBean[] result = new ServerMBean[resultSet.size()];
      return (ServerMBean[])((ServerMBean[])resultSet.toArray(result));
   }

   public MigratableTargetMBean[] getMigratableTargets() {
      Set resultSet = new HashSet();
      DomainMBean domain = (DomainMBean)((DomainMBean)this.getMbean().getParent());
      MigratableTargetMBean[] migratableTargets = domain.getMigratableTargets();
      String clusterName = this.getMbean().getName();

      for(int i = 0; i < migratableTargets.length; ++i) {
         MigratableTargetMBean migratableTarget = migratableTargets[i];
         if (migratableTarget.getCluster() != null && migratableTarget.getCluster().getName().equals(clusterName)) {
            resultSet.add(migratableTarget);
         }
      }

      MigratableTargetMBean[] result = new MigratableTargetMBean[resultSet.size()];
      return (MigratableTargetMBean[])((MigratableTargetMBean[])resultSet.toArray(result));
   }

   public String getMulticastAddress() {
      String multi = System.getProperty("weblogic.cluster.multicastAddress");
      if (multi != null) {
         return multi;
      } else {
         multi = this.multicastAddress;
         if (multi == null) {
            multi = "237.0.0.1";
            this.multicastAddress = multi;
         }

         return multi;
      }
   }

   public void setMulticastAddress(String multi) {
      this.multicastAddress = multi;
   }

   public HashMap start() throws RuntimeOperationsException {
      if (this.isConfig()) {
         return null;
      } else {
         ServerMBean[] servers = this.getServers();
         HashMap taskMBeans = new HashMap();

         try {
            for(int i = 0; i < servers.length; ++i) {
               ServerMBean server = servers[i];
               taskMBeans.put(server.getName(), this.getServerLifeCycleRuntime(server.getName()).start());

               try {
                  Thread.currentThread();
                  Thread.sleep(1000L);
               } catch (Exception var6) {
               }
            }

            return taskMBeans;
         } catch (ServerLifecycleException var7) {
            RuntimeException re = new RuntimeException(var7);
            throw new RuntimeOperationsException(re);
         }
      }
   }

   public HashMap kill() throws RuntimeOperationsException {
      if (this.isConfig()) {
         return null;
      } else {
         ServerMBean[] servers = this.getServers();
         HashMap taskMBeans = new HashMap();

         try {
            for(int i = 0; i < servers.length; ++i) {
               ServerMBean server = servers[i];
               taskMBeans.put(server.getName(), this.getServerLifeCycleRuntime(server.getName()).forceShutdown());

               try {
                  Thread.currentThread();
                  Thread.sleep(1000L);
               } catch (Exception var6) {
               }
            }

            return taskMBeans;
         } catch (ServerLifecycleException var7) {
            RuntimeException re = new RuntimeException(var7);
            throw new RuntimeOperationsException(re);
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

   public Set getServerNames() {
      ServerMBean[] cServers = this.getServers();
      Set serverNames = new HashSet(cServers.length);

      for(int j = 0; j < cServers.length; ++j) {
         serverNames.add(cServers[j].getName());
      }

      return serverNames;
   }

   public void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean coherenceClusterSystemResource) {
      this.cohCluster = coherenceClusterSystemResource;
   }

   public CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource() {
      return this.cohCluster;
   }
}
