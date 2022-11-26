package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.utils.Debug;

public class MigratableTargetConfigProcessor implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean domain) {
      ServerMBean[] servers = domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         ServerMBean server = servers[i];
         if (server.getCluster() != null) {
            createDefaultMigratableTargets(domain, server);
         }
      }

   }

   public static void destroyDefaultMigratableTarget(ServerTemplateMBean server) {
      String targetName = getNameOfDefaultMigratableTargetFor(server);
      DomainMBean domain = (DomainMBean)server.getParent();
      MigratableTargetMBean target = domain.lookupMigratableTarget(targetName);
      if (target != null) {
         domain.destroyMigratableTarget(target);
      }

   }

   public static void createDefaultMigratableTargets(DomainMBean domain, ServerMBean server) {
      createDefaultMigratableTargets(domain, server, server.getCluster());
   }

   public static void createDefaultMigratableTargets(DomainMBean domain, ServerMBean server, ClusterMBean cluster) {
      if (server.getJTAMigratableTarget() == null) {
         JTAMigratableTargetMBean jmt = server.createJTAMigratableTarget();
         if (server.isDynamicallyCreated()) {
            ((AbstractDescriptorBean)jmt)._setTransient(true);
         }

         jmt.setCluster(cluster);
         jmt.setUserPreferredServer(server);
         if (server.getServerTemplate() != null && server.getServerTemplate().getJTAMigratableTarget() != null) {
            jmt.setStrictOwnershipCheck(server.getServerTemplate().getJTAMigratableTarget().isStrictOwnershipCheck());
            jmt.setMigrationPolicy(server.getServerTemplate().getJTAMigratableTarget().getMigrationPolicy());
         }
      }

      String targetName = getNameOfDefaultMigratableTargetFor(server);
      MigratableTargetMBean migTarget = domain.lookupMigratableTarget(targetName);
      if (migTarget == null) {
         migTarget = domain.createMigratableTarget(targetName);
      }

      try {
         migTarget.setUserPreferredServer(server);
         migTarget.setCluster(cluster);
         migTarget.setNotes(ManagementTextTextFormatter.getInstance().getDefaultServerMigratableTargetNote());
         if (server.isDynamicallyCreated()) {
            ((AbstractDescriptorBean)migTarget)._setTransient(true);
         }
      } catch (InvalidAttributeValueException var6) {
         Debug.assertion(false, var6.toString());
      } catch (DistributedManagementException var7) {
         ManagementLogger.logDomainSaveFailed(var7);
      }

   }

   public static String getNameOfDefaultMigratableTargetFor(ServerTemplateMBean s) {
      return s.getName() + " (migratable)";
   }
}
